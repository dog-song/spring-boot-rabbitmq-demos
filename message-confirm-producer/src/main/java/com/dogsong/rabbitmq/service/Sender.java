package com.dogsong.rabbitmq.service;

import com.rabbitmq.tools.json.JSONUtil;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author domisong.
 * @description: TODO
 * @date 2021/5/19.
 */
@Service
public class Sender {

    /**
     * 被发送的数据，由于需求中需要将发送失败的消息记录下来，所以这里需接受发送的数据
     */
    private Object data;

    // 交换机

    private String exchangeName = "d_ttl_direct_exchange2";
    // 路由key

    private String routingKey = "ttl";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(ack){
                System.out.println("消息确认成功!!!!");
            }else{
                System.out.println("失败的消息 --> " + data);
                System.out.println("消息确认失败!!!!");
            }
        });
    }




    public String makeTtl(String userId, String productId, Integer num) {
        // 1: 根据用户查询用户是否存在
        // 2: 根据产品id查询产品信息
        String orderId = num + "";
        // 3: 保存订单
        // 4: 发送邮件，sms,短信
        System.out.println("用户：" + userId + ",购买了一个产品：" + productId + "保存订单是：" + orderId);

        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);

        return "success";
    }

}
