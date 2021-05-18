package com.dogsong.rabbitmq.work.fair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author domisong.
 * @description: 公平分发（Fair Dispatch）
 *          根据消费者的消费能力进行公平分发，处理快的处理的多，处理慢的处理的少；按劳分配；
 * @date 2021/5/18.
 */
public class Producer {


    public static void main(String[] args) {
        // 1: 创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.0.9.173");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;

        try {
            // 3: 从连接工厂中获取连接
            connection = connectionFactory.newConnection("生产者");
            // 4: 从连接中获取通道channel
            channel = connection.createChannel();
            // 6： 准备发送消息的内容
            String message = "hello dogsong";
            // 交换机
            String  exchangeName = "work-fair-exchange";
            // 交换机的类型 direct/topic/fanout/headers
            String exchangeType = "direct";
            // Topic模式是direct模式上的一种叠加，增加了模糊路由RoutingKey的模式
            String routingKey = "";
            // 队列的
            String routingKey1 = "";

            // 如果你用界面把queueu 和 exchange的关系先绑定话，你代码就不需要在编写这些声明代码可以让代码变得更加简洁，但是不容读懂
            // 如果用代码的方式去声明，我们要学习一下
            // 7： 声明交换机 所谓的持久化就是指，交换机会不会随着服务器重启造成丢失，如果是true代表不丢失，false重启就会丢失
            channel.exchangeDeclare(exchangeName,exchangeType,true);

            // 8： 声明队列
            /*
             *  如果队列不存在，则会创建
             *  Rabbitmq不允许创建两个相同的队列名称，否则会报错。
             *
             *  @params1： queue 队列的名称
             *  @params2： durable 队列是否持久化
             *  @params3： exclusive 是否排他，即是否私有的，如果为true,会对当前队列加锁，其他的通道不能访问，并且连接自动关闭
             *  @params4： autoDelete 是否自动删除，当最后一个消费者断开连接之后是否自动删除消息。
             *  @params5： arguments 可以设置队列附加参数，设置队列的有效期，消息的最大长度，队列的消息生命周期等等。
             * */
            channel.queueDeclare("queue_work_fair_1",true,false,false,null);
            // 9：绑定队列和交换机的关系
            /*
             *  @params1： queue 队列的名称
             *  @params2： exchange 交换机名称
             *  @params3： routingKey 用于绑定的路由密钥
             * */
            channel.queueBind("queue_work_fair_1",exchangeName,routingKey1);



            // 10：发送消息给中间件rabbitmq-server
            // @params1: 交换机exchange
            // @params2: 队列名称/routingkey
            // @params3: 属性配置
            // @params4: 发送消息的内容
            for (int i = 1; i <= 20; i++) {
                //消息的内容
                String msg = "hello dogsong" + i;
                channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
            }

            System.out.println("消息发送成功!");
        } catch (Exception ex) {
            System.out.println("发送消息出现异常...");
            ex.printStackTrace();
        } finally {
            // 7: 释放连接关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
