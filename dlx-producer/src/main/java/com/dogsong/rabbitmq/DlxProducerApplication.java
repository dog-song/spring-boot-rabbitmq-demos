package com.dogsong.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author domisong.
 * @description: TODO
 * @date 2021/5/19.
 */
@SpringBootApplication
public class DlxProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlxProducerApplication.class, args);
    }

}
