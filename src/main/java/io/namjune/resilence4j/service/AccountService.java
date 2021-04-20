package io.namjune.resilence4j.service;


import io.github.resilience4j.retry.Retry;
import io.namjune.common.Account;
import io.namjune.common.exception.SaveFailException;
import io.namjune.common.exception.UpdateFailException;
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

    /**
     * Java Config 방식
     */
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

    public Account saveFail(Account account) {
        Supplier<Account> accountSupplier = Retry.decorateSupplier(accountSaveRetry, () -> {
            log.info("call account saveFail method..");
            return accountRepository.saveFail(account);
        });

        Try<Account> result = Try.ofSupplier(accountSupplier)
            .recover(e -> {
                log.error("call account saveFail fail recover. save fail");
                throw new SaveFailException();
            });

        return result.get();
    }

    /**
     * Annotation 방식
     */
    @io.github.resilience4j.retry.annotation.Retry(name = "accountUpdate", fallbackMethod = "updateFail")
    public Account update(Account account, String name) {
        log.info("call account updateFail method..");
        throw new UpdateFailException();
    }

    private Account updateFail(Account account, String name, Exception e) {
        log.error("call account updateFail fail recover. update fail");
        throw new UpdateFailException();
    }
}
