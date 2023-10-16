package com.example.arexmytest.pojo;

import lombok.Getter;

public enum RecordEnvType {
    /**
     * production
     */
    PRO(0),
    /**
     * testing
     */
    TEST(1);
    @Getter
    private final int codeValue;

    RecordEnvType(int codeValue) {
        this.codeValue = codeValue;
    }
}
