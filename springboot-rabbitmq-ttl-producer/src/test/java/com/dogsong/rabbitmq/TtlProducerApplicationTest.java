package com.dogsong.rabbitmq;

import com.dogsong.rabbitmq.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TtlProducerApplicationTest {

    @Autowired
    private OrderService orderService;

    // 测试过期队列
    @Test
    public void contextLoads()  throws  Exception{

        for (int i = 1; i <= 10; i++) {
            String result = orderService.makeTtl("100", "100", i);
            Thread.sleep(1000);
            System.out.println(result);
        }
    }


    // 测试过期消息
    @Test
    public void contextLoads2()  throws  Exception{

        for (int i = 1; i <= 10; i++) {
            String result = orderService.makeTtlMessage("100", "100", i);
            Thread.sleep(1000);
            System.out.println(result);
        }
    }
}