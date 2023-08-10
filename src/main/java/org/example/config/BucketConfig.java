package org.example.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BucketConfig {
  private String name;
  private long capacity;
  private long refillMinutes;
  private long refillSeconds;
}
