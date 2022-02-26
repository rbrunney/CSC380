package com.example.retrovideogameexchangeapi.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;

public class MyUtils {
    public static String[] decodeAuth(String encodedString) {
        encodedString = encodedString.substring(encodedString.indexOf(" ") + 1);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        return decodedString.split(":", 2);
    }

    public static void createQueueMessage(String queueName, HashMap<String, String> message) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("172.18.0.3");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);

            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(boas);
            oos.writeObject(message);

            channel.basicPublish("", queueName, null, boas.toByteArray());

            boas.close();
            oos.close();
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
