package com.dogsong.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author domisong.
 * @description: TODO
 * @date 2021/5/18.
 */
@SpringBootApplication
public class RabbitmqFanoutConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqFanoutConsumerApplication.class, args);
    }

}
