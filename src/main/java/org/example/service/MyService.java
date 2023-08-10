package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyService {

    private final RateLimitedService rateLimitedService;

    public String hello() {
        return rateLimitedService.hello();
    }

}
