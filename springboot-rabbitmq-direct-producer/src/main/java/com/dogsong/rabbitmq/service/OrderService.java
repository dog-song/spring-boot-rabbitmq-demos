package com.dogsong.rabbitmq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author domisong.
 * @description: TODO
 * @date 2021/5/18.
 */
@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 交换机

    private String exchangeName = "d_direct_order_exchange";
    // 路由key

    private String emailRoutingKey = "email";
    private String smsRoutingKey = "sms";


    public String makeorder(String userId, String productId, Integer num) {
        // 1: 根据用户查询用户是否存在
        // 2: 根据产品id查询产品信息
        String orderId = num+"";
        // 3: 保存订单
        // 4: 发送邮件，sms,短信
        System.out.println("用户：" + userId + ",购买了一个产品：" + productId + "保存订单是：" + orderId);
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, emailRoutingKey, orderId);
        rabbitTemplate.convertAndSend(exchangeName, smsRoutingKey, orderId);

        return "success";
    }
}
