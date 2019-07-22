package com.example.customerservice.support;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@Aspect
public class CircuitBreakerAspect {

  private static final int FAIL_THRESHOLD = 2;
  private static final int BREAKER_THRESHOLD = 2;

  private Map<String, AtomicInteger> failCounter = new ConcurrentHashMap<>();
  private Map<String, AtomicInteger> breakerCounter = new ConcurrentHashMap<>();

  @Around("execution(* com.example.customerservice.integration..*(..))")
  public Object doWithCircuitBreaker(ProceedingJoinPoint pjp) throws Throwable {
    String sig = pjp.getSignature().getName();

    Object res;
    try {
      if (!failCounter.containsKey(sig)) {
        failCounter.put(sig, new AtomicInteger(0));
      }
      if (!breakerCounter.containsKey(sig)) {
        breakerCounter.put(sig, new AtomicInteger(0));
      }

      // check if failure number larger than threshold
      if (failCounter.get(sig).get() > FAIL_THRESHOLD) {
        // circuit break
        if (breakerCounter.get(sig).get() <= BREAKER_THRESHOLD) {
          int breakTimes = breakerCounter.get(sig).incrementAndGet();
          log.info("Circuit break return null, break {} times", breakTimes);
          return null;
        }
      }

      res = pjp.proceed();
      failCounter.get(sig).set(0);
      breakerCounter.get(sig).set(0);
    } catch (Throwable t) {
      int failureTimes = failCounter.get(sig).incrementAndGet();
      log.error("Mehod call failure {} times, error message {}", failureTimes, t.getMessage());
      breakerCounter.get(sig).set(0);
      throw t;
    }
    return res;
  }
}
