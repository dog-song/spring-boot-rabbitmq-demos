package com.dogsong.rabbitmq.web;

import com.dogsong.rabbitmq.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author domisong.
 * @description: TODO
 * @date 2021/5/18.
 */
@RestController
public class TestController {


    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public String contextLoads() throws Exception {

        for (int i = 1; i <= 10; i++) {
            String result = orderService.makeorder("100", "100", i);
            Thread.sleep(1000);
            System.out.println(result);
        }

        return "success";
    }

}
