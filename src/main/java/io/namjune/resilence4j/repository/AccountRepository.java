package io.namjune.resilence4j.repository;

import io.namjune.common.Account;
import io.namjune.common.exception.SaveFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AccountRepository {

    public Account save(Account account) {
        return account;
    }

    public Account saveFail(Account account) {
        throw new SaveFailException();
    }
}
