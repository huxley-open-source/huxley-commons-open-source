package com.thehuxley.queue;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public abstract class RabbitMQAbstraction {

    private static Logger logger = LoggerFactory
            .getLogger(RabbitMQAbstraction.class);

    protected String queueName;
    protected int sleepTime;
    protected Connection connection;
    protected Channel channel;

    protected ConnectionFactory factory;

    protected boolean durable = true;
    protected boolean autoAck = false;


    public RabbitMQAbstraction(String username, String password, String queueName, String hostName,
                               int serverDownInterval) throws IOException {

        this.queueName = queueName;
        this.sleepTime = serverDownInterval;

        factory = new ConnectionFactory();
        factory.setHost(hostName);
        factory.setAutomaticRecoveryEnabled(true);
        factory.setUsername(username);
        factory.setPassword(password);
        configure();
    }

    protected abstract void configure() throws IOException ;

    public  int getSize() throws IOException{
        Channel passiveChannel = connection.createChannel();
        AMQP.Queue.DeclareOk queuePassive = passiveChannel.queueDeclarePassive(queueName);
        int messageCount = queuePassive.getMessageCount();
        passiveChannel.close();
        return messageCount;
    }

    public void clearQueue() {
        try {
            channel.queuePurge(queueName);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

}
