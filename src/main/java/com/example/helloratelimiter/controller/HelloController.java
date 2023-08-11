package com.example.helloratelimiter.controller;

import com.example.helloratelimiter.dao.HelloMapper;
import com.example.helloratelimiter.service.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: hello-rate-limiter
 * @description:
 * @author: Xie
 * @create: 2023-08-11 14:43
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;
    private final HelloMapper helloMapper;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        int i = helloMapper.token();
        if(i>0){
            helloMapper.update();
            log.info(helloService.hello()+". "+(i-1)+" times left.");
            return ResponseEntity.ok(helloService.hello()+".\n"+(i-1)+" times left.\n");

        }
        else{
            log.info("Too many requests");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
        }
    }
}
