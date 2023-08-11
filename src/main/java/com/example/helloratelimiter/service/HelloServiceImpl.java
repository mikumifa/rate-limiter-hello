package com.example.helloratelimiter.service;

import com.example.helloratelimiter.dao.HelloMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: hello-rate-limiter
 * @description:
 * @author: Xie
 * @create: 2023-08-11 14:39
 **/
@Service
@RequiredArgsConstructor
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello() {
         return "Hello";
    }
}
