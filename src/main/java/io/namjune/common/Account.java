package io.namjune.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Account {
    private int id;
    private String name;

    @Builder
    public Account(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}
