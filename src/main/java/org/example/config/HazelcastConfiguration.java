package org.example.config;

import com.hazelcast.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {
  @Bean
  public Config hazelCastConfig() {
    Config config = new Config();
    config.setInstanceName("my-hazelcast-instance");
    return config;
  }
}