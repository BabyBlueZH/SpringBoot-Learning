package com.example.learnspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController// 表示该类是一个控制器
public class HelloController {

    @GetMapping("/hello")// 表示该方法处理/hello请求
    public String hello() {

        return "Hello Spring Boot!";
    }
}
