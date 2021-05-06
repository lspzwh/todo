package com.example.todolist.util;

import org.litepal.crud.LitePalSupport;

public class Person extends LitePalSupport {
    private String name;
    private String key;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
