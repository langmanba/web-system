package producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Producer {
    public final static String QUEUE_NAME = "test";
    public final static String EXCHANGE_NAME = "rabbitMq.exchange1";
    public final static String ROUTING_KEY = "test-exchange1-key";

    public static void main(String[] args) {
        //创建连接工厂
        try {
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ相关信息
            factory.setHost("localhost");
            //factory.setUsername("lp");
            //factory.setPassword("");
            // factory.setPort(2088);
            //创建一个新的连接
            Connection connection = null;

            connection = factory.newConnection();

            //创建一个通道
            Channel channel = connection.createChannel();
            //声明一个exchange
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            //  声明一个队列
//            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
            String message = "Hello RabbitMQ";
            //发送消息到队列中

            //创建一个对象
            User user=new User();
            user.setId("1");
            user.setName("dema");
            user.setPassword("123");

            channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, SerializationUtils.serialize(user));
            System.out.println("Producer Send +'" + user.toString() + "'");
            //关闭通道和连接
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
