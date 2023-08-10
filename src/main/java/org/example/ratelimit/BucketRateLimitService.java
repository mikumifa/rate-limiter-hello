package org.example.ratelimit;

import com.hazelcast.core.HazelcastInstance;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.grid.hazelcast.HazelcastProxyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.example.config.BucketConfig;
import org.example.config.BucketProperties;
import org.example.exception.RateLimitExceededException;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.time.Duration;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class BucketRateLimitService {

  private final HazelcastInstance hzInstance;
  private final BucketProperties bucketProperties;
  private HazelcastProxyManager<String> proxyManager;

  @PostConstruct
  public void init() {
    this.proxyManager = new HazelcastProxyManager<>(hzInstance.getMap("bucket-map"));
  }

  @Around("@annotation(org.example.ratelimit.BucketRateLimit)")
  public Object applyRateLimit(ProceedingJoinPoint point) throws Throwable {
    BucketRateLimit rateLimit = getAnnotation(point);

    BucketConfig selectedBucket = getCorrectBucket(rateLimit);
    BucketConfiguration bucketConfiguration = getBucketConfiguration(selectedBucket);

    Bucket requestBucket = this.proxyManager.builder().build(selectedBucket.getName(), bucketConfiguration);

    ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(rateLimit.consumeAmount());

    Object object;
    if (probe.isConsumed()) {
      object = point.proceed();
      log.info("Consume success - Remaining {}", probe.getRemainingTokens());
    } else {
      log.warn("Consume failure - Refill in {} ns", probe.getNanosToWaitForRefill());
      throw new RateLimitExceededException("Limit exceeded");
    }

    return object;
  }

  /**
   * Builds the configuration of the bucket containing the capacity and refill rate
   *
   * @param selectedBucket The bucket selected by the annotation
   * @return BucketConfiguration
   */
  private BucketConfiguration getBucketConfiguration(BucketConfig selectedBucket) {
    if (selectedBucket.getCapacity() == 0) {
      log.error("No capacity specified for bucket '{}'", selectedBucket);
      throw new RuntimeException("Error - Check logs");
    }

    if (selectedBucket.getRefillSeconds() == 0 && selectedBucket.getRefillMinutes() == 0) {
      log.error("No refill specified for bucket '{}'", selectedBucket);
      throw new RuntimeException("Error - Check logs");
    }

    //Calculate everything in seconds
    long seconds = selectedBucket.getRefillSeconds() + (selectedBucket.getRefillMinutes() * 60);

    return BucketConfiguration.builder()
            .addLimit(Bandwidth.simple(selectedBucket.getCapacity(), Duration.ofSeconds(seconds)))
            .build();
  }

  /**
   * Uses the bucket configured in the annotation to get the configuration for the bucket
   *
   * @return BucketConfig if found, throws RuntimeException if not found
   */
  private BucketConfig getCorrectBucket(BucketRateLimit myAnnotation) {
    String bucketToUse = myAnnotation.bucket();

    BucketConfig selectedBucket = null;

    if (bucketToUse != null) {
      for (BucketConfig bucketConfig : bucketProperties.getConfig()) {
        if (bucketToUse.equals(bucketConfig.getName())) {
          selectedBucket = bucketConfig;
        }
      }
    }

    if (selectedBucket == null) {
      log.error("No matching bucket configured for bucket '{}'", bucketToUse);
      throw new RuntimeException("No matching bucket configured for bucket '" + bucketToUse + "'");
    }
    return selectedBucket;
  }

  private BucketRateLimit getAnnotation(ProceedingJoinPoint point) {
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    return method.getAnnotation(BucketRateLimit.class);
  }
}