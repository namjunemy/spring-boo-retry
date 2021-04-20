package io.namjune.resilence4j.service;

import io.namjune.ServiceTestContext;
import io.namjune.common.Account;
import io.namjune.common.exception.SaveFailException;
import io.namjune.common.exception.UpdateFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AccountServiceTest extends ServiceTestContext {

    @Test
    @DisplayName("Account 저장 성공")
    void saveAccount() {
        Account account = Account.builder()
            .id(1)
            .name("njkim")
            .build();

        Account savedAccount = accountService.save(account);

        assertThat(savedAccount.getName()).isEqualTo("njkim");
    }

    @Test
    @DisplayName("Account 저장 실패 - 재시도 3번")
    void saveAccountFail() {
        assertThrows(SaveFailException.class, () -> {
            accountService.saveFail(Account.builder().build());
        });
    }

    @Test
    @DisplayName("Account 업데이트 실패")
    void updateAccountFail() {
        assertThrows(UpdateFailException.class, () -> {
            accountService.update(Account.builder().build(), "updateName");
        });
    }
}