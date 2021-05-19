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
public class TtlRabbitMqConfiguration {


    /**
     *  声明交换机
     */
    @Bean
    public DirectExchange ttlExchange() {
        return new DirectExchange("d_ttl_direct_exchange", true, false);
    }

    /**
     *  声明队列
     *  ttl队列过期时间
     */
    @Bean
    public Queue ttlQueue() {
        // 设置过期时间
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 5000);
        return new Queue("d.ttl.direct.queue", true, false, false, args);
    }

    /**
     *  声明队列
     *  ttl消息过期时间
     */
    @Bean
    public Queue ttlMessageQueue() {
        return new Queue("d.ttl.msg.direct.queue", true);
    }

    /**
     *  绑定
     */
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with("ttl");
    }

    @Bean
    public Binding bindingDirect2() {
        return BindingBuilder.bind(ttlMessageQueue()).to(ttlExchange()).with("ttlmsg");
    }


}
