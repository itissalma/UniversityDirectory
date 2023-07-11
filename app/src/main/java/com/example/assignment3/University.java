package com.example.assignment3;

import java.io.Serializable;

public class University implements Serializable {
    private String name;
    private String domain;

    public University(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }
}

