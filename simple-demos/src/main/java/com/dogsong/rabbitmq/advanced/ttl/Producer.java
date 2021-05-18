package com.dogsong.rabbitmq.advanced.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author domisong.
 * @description: TODO
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
            String  exchangeName = "d_ttl_exchange";

            // 交换机的类型 direct/topic/fanout/headers
            String exchangeType = "direct";

            // Direct模式是fanout模式上的一种叠加，增加了路由RoutingKey的模式
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

            Map<String,Object> args2 = new HashMap<>();
            // 参数 x-message-ttl 的值 必须是非负 32 位整数 (0 <= n <= 2^32-1)
            // 以毫秒为单位表示 TTL 的值
            // 这样，值 6000 表示存在于 队列 中的当前 消息 将最多只存活 6 秒钟
            args2.put("x-message-ttl",5000);
            // 设置队列TTL
            channel.queueDeclare("d_ttl_queue", true, false, false, args2);


            // 9：绑定队列和交换机的关系
            channel.queueBind("d_ttl_queue",exchangeName,routingKey1);


            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("x", "1");
            headers.put("y", "1");
            // 属性配置
            // 设置消息TTL
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2) // 传送方式
                    .priority(1)
                    .contentEncoding("UTF-8") // 编码方式
                    .expiration("3000") // 过期时间
                    .headers(headers).build(); //自定义属性

            // 10：发送消息给中间件rabbitmq-server
            // @params1: 交换机exchange
            // @params2: 队列名称/routingkey, 有时候是routingkey有时候是队列名称，是因为当交换机使用的是默认的时，用的是队列名称
            // @params3: 属性配置
            // @params4: 发送消息的内容
            for (int i = 0; i < 100; i++) {
                channel.basicPublish(exchangeName, routingKey, basicProperties, message.getBytes());
                System.out.println("消息发送成功!");
                Thread.sleep(1000);
            }

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
