// src/main/java/com/luckywinner/dto/CodeEntryRequest.java
package com.luckywinner.dto;

public class CodeEntryRequest {

    private String code;

    public CodeEntryRequest() {}

    public CodeEntryRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
