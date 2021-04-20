package io.namjune.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {
    private int id;
    private String value;

    @Builder
    public Token(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public void update(String tokenValue) {
        this.value = tokenValue;
    }
}
