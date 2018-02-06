package NerTask;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker1 {

    public static final String TASK_QUEUE_NAME = "task_queue";
    public static final String TASK_EXC_NAME = "task_exc";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(TASK_EXC_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
//            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            channel.queueBind(queueName, TASK_EXC_NAME, "que-exc");
            System.out.println("Worker1  Waiting for messages...");

            QueueingConsumer  consumer = new QueueingConsumer(channel);

//          消息消费完成确认
            channel.basicConsume(TASK_QUEUE_NAME,true,consumer);
            while (true){
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println(" [x] Received '" + message + "'");
                doWork(message);
            }


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
