package NerTask;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker2 {

    public static final String TASK_QUEUE_NAME_B = "task_queue_b";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(TASK_QUEUE_NAME_B, true, false, false, null);
            System.out.println("Worker2  Waiting for messages...");
//            channel.basicQos(6);

            QueueingConsumer  consumer = new QueueingConsumer(channel);

            channel.basicConsume(TASK_QUEUE_NAME_B,true,consumer);
            while (true){
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println(" [x] Received '" + message + "'");
                doWork(message);
            }
//            boolean autoAck = true;
//            //消息消费完成确认
//            channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void doWork(String task) {
        try {
            Thread.sleep(1000); // 暂停1秒钟
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
