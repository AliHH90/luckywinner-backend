package com.luckywinner.dto;

public class AnswerRequest {

    // مثلا: "A" یا "B" یا "C" یا "D"
    private String option;

    public AnswerRequest() {
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
