package com.dogsong.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author domisong.
 * @description: TODO
 * @date 2021/5/18.
 */
@Configuration
public class RabbitMqConfiguration {


    /**
     *  声明交换机
     */
    @Bean
    public DirectExchange fanoutExchange() {
        // 等价于  channel.exchangeDeclare(exchangeName,exchangeType,true);
        return new DirectExchange("fanout_order_exchange", true, false);
    }

    /**
     *  声明队列
     */
    @Bean
    public Queue emailQueue() {
        // 等价于  channel.exchangeDeclare(exchangeName,exchangeType,true);
        return new Queue("email.fanout.queue");
    }

    @Bean
    public Queue smsQueue() {
        return new Queue("sms.fanout.queue");
    }

    @Bean
    public Queue wechatQueue() {
        return new Queue("wechat.fanout.queue");
    }


    /**
     *  绑定
     */
    @Bean
    public Binding bindingDirect11() {
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange()).with("");
    }

    @Bean
    public Binding bindingDirect12() {
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange()).with("");
    }

    @Bean
    public Binding bindingDirect13() {
        return BindingBuilder.bind(wechatQueue()).to(fanoutExchange()).with("");
    }


}
