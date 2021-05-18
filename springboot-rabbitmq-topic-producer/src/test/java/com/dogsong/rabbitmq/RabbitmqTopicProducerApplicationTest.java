package com.dogsong.rabbitmq;

import com.dogsong.rabbitmq.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RabbitmqTopicProducerApplicationTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void contextLoads()  throws  Exception{

        for (int i = 1; i <= 10; i++) {
            String result = orderService.makeorder("100", "100", i);
            Thread.sleep(1000);
            System.out.println(result);
        }
    }

}