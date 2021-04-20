package io.namjune.springretry.service;

import io.namjune.ServiceTestContext;
import io.namjune.common.Token;
import io.namjune.common.exception.SaveFailException;
import io.namjune.common.exception.UpdateFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenServiceTest extends ServiceTestContext {

    @Test
    @DisplayName("토큰 저장 실패 - 재시도 3회")
    void saveTokenFail() {
        assertThrows(SaveFailException.class, () -> {
            tokenService.save(Token.builder().id(1).value("code").build());
        });
    }

    @Test
    @DisplayName("토큰 업데이트 실패 - 재시도 2회")
    void updateTokenFail() {
        assertThrows(UpdateFailException.class, () -> {
            tokenService.updateFail(null, "updateTokenValue");
        });
    }

    @Test
    @DisplayName("토큰 업데이트 성공 - 재시도 없음")
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