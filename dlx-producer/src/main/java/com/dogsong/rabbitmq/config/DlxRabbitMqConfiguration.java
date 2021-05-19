package com.dogsong.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author domisong.
 * @description: TODO
 * @date 2021/5/18.
 */
@Configuration
public class DlxRabbitMqConfiguration {


    /**
     *  声明交换机
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange("d_dlx_direct_exchange", true, false);
    }

    /**
     *  声明队列  死信队列
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue("d.dlx.direct.queue", true);
    }

    /**
     *  绑定
     */
    @Bean
    public Binding bindingDirectDlx() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("dlx");
    }



}
