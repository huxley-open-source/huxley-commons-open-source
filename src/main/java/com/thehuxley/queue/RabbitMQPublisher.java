package com.thehuxley.queue;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;

public class RabbitMQPublisher extends RabbitMQAbstraction{

    private static Logger logger = LoggerFactory
            .getLogger(RabbitMQPublisher.class);

    public RabbitMQPublisher(String username, String password, String queueName, String hostName, int serverDownInterval) throws IOException {
        super(username, password, queueName, hostName, serverDownInterval);
    }

    @Override
    protected void configure() throws IOException {
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, durable, false, false, null);
        channel.basicQos(1);
    }


    public void sendPlain(String message) {
        send(message, "text/plain");
    }

    public void sendJson(String message) {
        send(message, "application/json");
    }

    private void send(String message, String contentType) {
        boolean sent = false;
        while (!sent) {
            try {
                // configuraćões de envio
                AMQP.BasicProperties.Builder props = new AMQP.BasicProperties
                        .Builder();
                props.contentType(contentType);
                // persistente
                props.deliveryMode(2);
                // prioridade padrão
                props.priority(0);

                channel.basicPublish("", queueName,
                        props.build(),
                        message.getBytes());
                sent = true;

            } catch (IOException e) {
                logger.error("Could not send message to queue: " + queueName, e);
                if (logger.isDebugEnabled()) {
                    logger.debug("Trying again to send the message after "
                            + sleepTime + " milliseconds");
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e1) {
                    logger.error(e1.getMessage(), e1);
                }
            }
        }
    }
}
