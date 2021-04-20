package io.namjune.springretry.service;

import io.namjune.common.Token;
import io.namjune.springretry.exception.SaveTokenFailException;
import io.namjune.springretry.exception.UpdateTokenFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    @DisplayName("토큰 저장시 재시도 3회")
    void saveToken() {
        assertThrows(SaveTokenFailException.class, () -> {
            tokenService.save(Token.builder().id(1).value("code").build());
        });
    }

    @Test
    @DisplayName("토큰 업데이트시 재시도 2회")
    void updateTokenFail() {
        assertThrows(UpdateTokenFailException.class, () -> {
            tokenService.updateFail(null, "updateTokenValue");
        });
    }

    @Test
    @DisplayName("토큰 업데이트시 재시도 없이 성공")
    void updateToken() {
        Token token = Token.builder()
            .id(1)
            .value("value")
            .build();
        String updateTokenValue = "updateTokenValue";
        tokenService.update(token, updateTokenValue);
        assertThat(token.getValue()).isEqualTo(updateTokenValue);
    }
}