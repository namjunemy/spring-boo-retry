package io.namjune.resilence4j.config;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.namjune.common.exception.SaveFailException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {
    public static final String ACCOUNT_SAVE_RETRY = "accountSaveRetry";

    @Bean(name = ACCOUNT_SAVE_RETRY)
    public Retry accountSaveRetry() {
        Boolean retryFlag = Boolean.TRUE;
        RetryConfig retryConfig = RetryConfig.custom()
            // 최대 시도 횟수
            .maxAttempts(3)
            // 실패시 wait 시간
            .waitDuration(Duration.ofSeconds(1))
            // 재시도 결정 Predicate
            .retryOnResult(retryOn -> retryFlag == retryOn)
            // 재시도 대상 Exceptions
            .retryExceptions(SaveFailException.class)
            // 재시도 하지 않을 Exceptions
            .ignoreExceptions(IllegalStateException.class)
            .build();

        RetryRegistry retryRegistry = RetryRegistry.of(retryConfig);
        return retryRegistry.retry(ACCOUNT_SAVE_RETRY);
    }
}
