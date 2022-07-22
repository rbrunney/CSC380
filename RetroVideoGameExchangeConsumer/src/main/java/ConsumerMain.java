import com.rabbitmq.client.*;
import email.SendMail;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class ConsumerMain {
    public static void main(String[] args) {
        System.out.println("This is working :)");
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("rabbitMQ");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("email_queue", false, false, false, null);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    HashMap<String, String> message = new HashMap<>();
                    String subject = "";
                    String emailBody = "";

                    try {
                        ByteArrayInputStream bais = new ByteArrayInputStream(body);
                        ObjectInputStream ois = new ObjectInputStream(bais);

                        message = (HashMap<String, String>) ois.readObject();

                        bais.close();
                        ois.close();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println(message.toString());

                    switch(message.get("type")) {
                        case "passChange":
                            subject = "Password Changed";
                            emailBody = "Your password has been changed!";
                            new SendMail(message.get("email"), subject, emailBody);
                            break;
                        case "passReset":
                            subject = "Password Reset";
                            emailBody = String.format("Here is your temporary password %s so you can resetPassword", message.get("tempPass"));
                            new SendMail(message.get("email"), subject, emailBody);
                            break;
                        case "offerMade":
                            subject = "Offer Made";
                            emailBody = String.format("You have made and offer to %s.", message.get("email"));
                            new SendMail(message.get("email"), subject, emailBody);
                            new SendMail(message.get("receivingUser"), "New Offer made on Game", "Login to see the new offer made for one of your games");
                            break;
                        case "offerAccepted":
                            subject = "Offer Accepted";
                            emailBody = String.format("You have accepted the offer for %s.", message.get("offeringUser"));
                            new SendMail(message.get("email"), subject, emailBody);
                            new SendMail(message.get("offeringUser"), subject, "Your offer has been accepted enjoy your new games.");
                            break;
                        case "offerRejected":
                            subject = "Offer Rejected";
                            emailBody = String.format("You have rejected the offer for %s.", message.get("offeringUser"));
                            new SendMail(message.get("email"), subject, emailBody);
                            new SendMail(message.get("offeringUser"), subject, String.format("Your offer has been rejected by %s", message.get("email")));
                            break;
                        case "event":
                            new SendMail(message.get("email"), message.get("subject"), message.get("emailBody"));
                            break;
                    }
                }
            };
            channel.basicConsume("email_queue", true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
