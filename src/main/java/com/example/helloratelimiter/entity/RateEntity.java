package com.example.helloratelimiter.entity;

import lombok.Data;


/**
 * @program: hello-rate-limiter
 * @description:
 * @author: Xie
 * @create: 2023-08-11 14:32
 **/
@Data
public class RateEntity {
    private Integer id;
    private Integer token;
    private Integer lastUpdated;
}
