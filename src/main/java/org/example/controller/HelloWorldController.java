package org.example.controller;

import org.example.service.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
class HelloWorldController {

    private final MyService myService;

    @GetMapping("hello")
    String sayHello() {
        return myService.hello();
    }

}
