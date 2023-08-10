package org.example.service;

import org.example.ratelimit.BucketRateLimit;
import org.springframework.stereotype.Service;

@Service
public class RateLimitedService {
    @BucketRateLimit(bucket = "A")
    public String hello() {
        return "hello";
    }

}
