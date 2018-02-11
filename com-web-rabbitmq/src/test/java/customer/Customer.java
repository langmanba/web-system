package customer;

import com.rabbitmq.client.*;
import org.apache.commons.lang.SerializationUtils;
import producer.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

public class Customer {
    public final static String QUEUE_NAME = "test";
    public final static String EXCHANGE_NAME = "rabbitMq.exchange1";
    public final static String ROUTING_KEY = "test-exchange1-key";

    public static void main(String[] args) {
        try {
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ地址
            factory.setHost("localhost");
            //创建一个新的连接
            Connection connection = factory.newConnection();
            //创建一个通道
            Channel channel = connection.createChannel();
//            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            //声明要关注的队列
//            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            String queueName=channel.queueDeclare().getQueue();
            channel.queueBind(queueName,EXCHANGE_NAME,QUEUE_NAME);
            System.out.println("Customer Waiting Received messages："+queueName);
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);
            //自动回复队列应答 -- RabbitMQ中的消息确认机制
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                User user=(User) SerializationUtils.deserialize(delivery.getBody());
                String routingKey = delivery.getEnvelope().getRoutingKey();

                System.out.println(" [x] Received '" + routingKey + "':'" + user.toString() + "'");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
