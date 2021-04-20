package io.namjune.springretry.repository;

import io.namjune.common.Token;
import io.namjune.springretry.exception.SaveTokenFailException;
import io.namjune.springretry.exception.UpdateTokenFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TokenRepository {

    public Token save(Token token) {
        throw new SaveTokenFailException();
    }

    public Token update(Token token, String tokenValue) {
        token.update(tokenValue);
        return token;
    }

    public Token updateThrowException(Token token, String tokenValue) {
        throw new UpdateTokenFailException();
    }
}
