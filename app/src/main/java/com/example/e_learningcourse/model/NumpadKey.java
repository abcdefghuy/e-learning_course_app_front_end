package com.example.e_learningcourse.model;

import com.example.e_learningcourse.enums.KeyType;

public class NumpadKey {
    private final String label;
    private final int code;
    private final KeyType type;

    public NumpadKey(String label, int code, KeyType type) {
        this.label = label;
        this.code = code;
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public int getCode() {
        return code;
    }

    public KeyType getType() {
        return type;
    }
}
