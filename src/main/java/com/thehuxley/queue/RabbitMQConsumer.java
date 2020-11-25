package com.thehuxley.queue;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.QueueingConsumer;

public class RabbitMQConsumer extends RabbitMQAbstraction {

    private static Logger logger = LoggerFactory
            .getLogger(RabbitMQConsumer.class);


    private QueueingConsumer consumer;

    public RabbitMQConsumer(String username, String password, String queueName, String hostName, int serverDownInterval) throws IOException {
        super(username, password, queueName, hostName, serverDownInterval);
    }

    @Override
    protected void configure() throws IOException {
        connection = factory.newConnection();

        channel = connection.createChannel();
        channel.queueDeclare(queueName, durable, false, false, null);
        channel.basicQos(1);

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, autoAck, consumer);
    }


    public QueueingConsumer.Delivery nextDelivery(long timeout){
        return getNextMessage(true,timeout);
    }

    public QueueingConsumer.Delivery nextDelivery() {
        return getNextMessage(false,0);
    }

    private QueueingConsumer.Delivery getNextMessage(boolean timeout, long timeoutInMillis){
        QueueingConsumer.Delivery delivery = null;
        boolean tryAgain;
        do {
            tryAgain = false;
            try {
                if (!timeout) {
                    delivery = consumer.nextDelivery();
                }else{
                    delivery = consumer.nextDelivery(timeoutInMillis);
                }
            } catch (Exception e) {
                tryAgain = true;
                if (logger.isErrorEnabled()) {
                    logger.error("RabbitMq server is down!. Sleeping for "
                            + (sleepTime / 60000) + " minutes to try again", e);
                }
                try {
                    Thread.sleep(sleepTime);
                    configure();
                } catch (Exception e1) {
                    if (logger.isErrorEnabled()) {
                        logger.error("RabbitMq Server is still down");
                    }
                }
            }
        } while (tryAgain);
        return delivery;
    }



    public void taskCompleted(QueueingConsumer.Delivery delivery) {
        try {
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error(
                        "It was not possible to send an ack message to the rabbitmq server. "
                                + "As a consequence, the task will be executed again by some evaluator",
                        e);
            }
        }
    }
}
