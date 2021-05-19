package com.dogsong.rabbitmq.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author domisong.
 * @description:
 *      如果上述两种方法同时使用，则消息的过期时间以两者之间TTL较小的那个数值为准。
 *      消息在队列的生存时间一旦超过设置的TTL值，就称为dead message被投递到死信队列， 消费者将无法再收到该消息。
 * @date 2021/5/18.
 */
@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 交换机

    private String exchangeName = "d_ttl_direct_exchange2";
    // 路由key

    private String routingKey = "ttl";



    public String makeTtl(String userId, String productId, Integer num) {
        // 1: 根据用户查询用户是否存在
        // 2: 根据产品id查询产品信息
        String orderId = num + "";
        // 3: 保存订单
        // 4: 发送邮件，sms,短信
        System.out.println("用户：" + userId + ",购买了一个产品：" + productId + "保存订单是：" + orderId);

        // 设置消息确认机制
        /**
            这种确认机制只能 确认一条消息
         Only one ConfirmCallback is supported by each RabbitTemplate

         */
        rabbitTemplate.setConfirmCallback(new MessageConfirmCallback());

        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);

        return "success";
    }

    public String makeTtlMessage(String userId, String productId, Integer num) {
        // 1: 根据用户查询用户是否存在
        // 2: 根据产品id查询产品信息
        String orderId = num + "";
        // 3: 保存订单
        // 4: 发送邮件，sms,短信
        System.out.println("用户：" + userId + ",购买了一个产品：" + productId + "保存订单是：" + orderId);

        // 给消息设置过期时间
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("5000");
                message.getMessageProperties().setContentEncoding("UTF-8");
                return message;
            }
        };

        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "ttlmsg", orderId, messagePostProcessor);

        return "success";
    }
}
