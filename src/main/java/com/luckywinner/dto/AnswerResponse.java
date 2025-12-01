package com.luckywinner.dto;

public class AnswerResponse {

    private boolean correct;
    private String message;
    private String code;   // ← کد جایزه (مثلاً WIN-123)

    public AnswerResponse() {
    }

    public AnswerResponse(boolean correct, String message, String code) {
        this.correct = correct;
        this.message = message;
        this.code = code;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
