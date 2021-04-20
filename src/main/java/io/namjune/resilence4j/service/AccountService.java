package io.namjune.resilence4j.service;


import io.github.resilience4j.retry.Retry;
import io.namjune.common.Account;
import io.namjune.common.exception.SaveFailException;
import io.namjune.resilence4j.repository.AccountRepository;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static io.namjune.resilence4j.config.Resilience4jConfig.ACCOUNT_SAVE_RETRY;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Qualifier(ACCOUNT_SAVE_RETRY)
    private final Retry accountSaveRetry;

    public Account save(Account account) {
        Supplier<Account> accountSupplier = Retry.decorateSupplier(accountSaveRetry, () -> {
            log.info("call account save method..");
            return accountRepository.save(account);
        });

        Try<Account> result = Try.ofSupplier(accountSupplier)
            .recover(e -> {
                log.error("call account save fail recover. save fail");
                throw new SaveFailException();
            });

        return result.get();
    }
}
