package NerTask;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {

    public static final String TASK_QUEUE_NAME = "task_queue";
    public static final String TASK_EXC_NAME = "task_exc";


    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(TASK_EXC_NAME, "fanout");
            /**
             * 参数意义
             *队列名
             *是否持久化
             *是否独占队列，创建者可以使用的私有队列，断开后自动删除
             *当所有消费者客户端连接断开时是否自动删除队列、第五个参数为队列的其他参数
             */
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            channel.queueBind(TASK_QUEUE_NAME, TASK_EXC_NAME, "que-exc");

            for (int i = 0; i < 4; i++) {
                String msg = "NewTask send " + i + ":Hello RabbitMq " + i;
                System.out.println(msg);
                /**
                 * 交换机名
                 * 路由key
                 * 其他属性
                 * 发送信息的主体
                 */
                channel.basicPublish(TASK_EXC_NAME, TASK_QUEUE_NAME, null, msg.getBytes("UTF-8"));
            }
            channel.close();
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
