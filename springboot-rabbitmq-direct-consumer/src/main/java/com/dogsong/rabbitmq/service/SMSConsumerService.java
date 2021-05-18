package com.dogsong.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author domisong.
 * @description: TODO
 * @date 2021/5/18.
 */
@Service
@RabbitListener(queues = {"d.sms.direct.queue"})
public class SMSConsumerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // @RabbitHandler 代表该方法会当做消费者的方法，如果有消息过来就会进入此方法
    @RabbitHandler
    public void reciverMessage(String message) {
        System.out.println("sms开始接收消息：" + message);
    }


}
