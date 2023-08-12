package com.example.helloratelimiter.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
@Slf4j
public class HelloControllerTest {

    @Autowired
    private HelloController helloController;

    @Test
    void test(){
//        List<Thread> threadList=new ArrayList<>();
//        AtomicBoolean flag= new AtomicBoolean(false);
//        for (int i=0;i<24;i++){
//            Thread t = new Thread(()->{
//                for(int j=0;j<10;j++){
//                    if (Objects.equals(helloController.hello().getBody(), "Too many requests")){
//                        flag.set(true);
//                    };
//                }
//            });
//            threadList.add(t);
//        }
//        for (var t:threadList){
//            t.start();
//        }
//
//        for (var t:threadList){
//            try {
//                t.join();
//            }catch (InterruptedException e){
//                log.info(String.valueOf(e));
//            }
//        }
//        assert flag.get();
    }

}
