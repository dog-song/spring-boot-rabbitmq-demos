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
        return new DirectExchange("d_ttl_direct_exchange2", true, false);
    }

    /**
     *  声明队列
     *  ttl队列过期时间
     */
    @Bean
    public Queue ttlQueue() {
        Map<String, Object> args = new HashMap<>();
        // 设置过期时间
        args.put("x-message-ttl", 5000);
        // 队列长度 大于 5 的部分也会进入到死信队列
        args.put("x-max-length", 5);
        // 设置接盘侠，过期之后，放进死信队列中
        args.put("x-dead-letter-exchange", "d_dlx_direct_exchange");
        // 如果是fanout模式，不需要配置routingkey
        args.put("x-dead-letter-routing-key", "dlx");

        return new Queue("d.ttl.direct.queue2", true, false, false, args);
    }

    /**
     *  声明队列
     *  ttl消息过期时间
     */
    @Bean
    public Queue ttlMessageQueue() {
        return new Queue("d.ttl.msg.direct.queue2", true);
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
