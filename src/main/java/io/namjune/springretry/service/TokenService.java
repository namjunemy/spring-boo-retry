package io.namjune.springretry.service;

import io.namjune.common.Token;
import io.namjune.springretry.exception.SaveTokenFailException;
import io.namjune.springretry.exception.UpdateTokenFailException;
import io.namjune.springretry.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import static io.namjune.springretry.config.SpringRetryConfig.TOKEN_REMOVE_RETRY_TEMPLATE;
import static io.namjune.springretry.config.SpringRetryConfig.TOKEN_SAVE_RETRY_TEMPLATE;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Qualifier(TOKEN_SAVE_RETRY_TEMPLATE)
    private final RetryTemplate tokenSaveRetryTemplate;

    @Qualifier(TOKEN_REMOVE_RETRY_TEMPLATE)
    private final RetryTemplate tokenRemoveRetryTemplate;

    /**
     * RetryTemplate 방식
     */
    public Token save(Token token) {
        return tokenSaveRetryTemplate.execute(
            (RetryCallback<Token, SaveTokenFailException>) context -> {
                log.info("call token save method..");
                return tokenRepository.save(token);
            },
            recoveryContext -> {
                log.error("call save retry recovery callback. save fail");
                throw new SaveTokenFailException();
            }
        );
    }

    /**
     * Annotation 방식
     */
    @Retryable(value = {UpdateTokenFailException.class}, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public Token update(Token token, String tokenValue) {
        log.info("call token update method..");
        return tokenRepository.update(token, tokenValue);
    }

    @Retryable(value = {UpdateTokenFailException.class}, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public Token updateFail(Token token, String tokenValue) {
        log.info("call token update method..");
        return tokenRepository.updateThrowException(token, tokenValue);
    }

    @Recover
    public Token updateFailRecover(UpdateTokenFailException saveTokenFailException) {
        log.error("call update retry recovery callback. update fail");
        throw new UpdateTokenFailException();
    }
}
