package io.namjune;

import io.namjune.resilence4j.service.AccountService;
import io.namjune.springretry.service.TokenService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class ServiceTestContext {

    @Autowired
    protected TokenService tokenService;

    @Autowired
    protected AccountService accountService;
}
