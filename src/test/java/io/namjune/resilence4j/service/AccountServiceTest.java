package io.namjune.resilence4j.service;

import io.namjune.ServiceTestContext;
import io.namjune.common.Account;
import io.namjune.common.exception.SaveFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


class AccountServiceTest extends ServiceTestContext {

    @Test
    @DisplayName("Account 저장 실패 - 재시도 3번")
    void saveAccountFail() {
        assertThrows(SaveFailException.class, () -> {
            accountService.save(Account.builder().build());
        });
    }
}