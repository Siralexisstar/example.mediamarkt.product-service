package com.example.mediamarkt.product.infraestructure.config.aop;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
@Component
public class LoggingAspect {

  @Around("execution(* com.example.mediamarkt.product.application.impl..*(..))")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

    Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().toShortString();
    String args = Arrays.toString(joinPoint.getArgs());

    long start = System.currentTimeMillis();

    log.info("Starting {}.{} with arguments: {}", className, methodName, args);

    Object proceed = joinPoint.proceed();

    if (proceed instanceof Mono<?> mono) {
      return mono.doOnSuccess(
              value ->
                  log.info(
                      "SUCCESS {}.{} durationMs={} message={}",
                      className,
                      methodName,
                      System.currentTimeMillis() - start,
                      sanitize(value)))
          .doOnError(
              error ->
                  log.info(
                      "ERROR {}.{} durationMs={} message={}",
                      className,
                      methodName,
                      System.currentTimeMillis() - start,
                      error.getMessage()));

    } else if (proceed instanceof Flux<?> flux) {

      return flux.doOnComplete(
              () ->
                  log.info(
                      "SUCCESS {}.{} durationMs={} message={}",
                      className,
                      methodName,
                      System.currentTimeMillis() - start))
          .doOnError(
              error ->
                  log.info(
                      "ERROR {}.{} durationMs={} message={}",
                      className,
                      methodName,
                      System.currentTimeMillis() - start,
                      error.getMessage()));
    }

    log.info(
        "SUCCESS {}.{} durationMs={} result={}",
        className,
        methodName,
        System.currentTimeMillis() - start,
        sanitize(proceed));
    return proceed;
  }

  private Object sanitize(Object value) {
    if (value == null) {
      return null;
    }
    return value;
  }
}
