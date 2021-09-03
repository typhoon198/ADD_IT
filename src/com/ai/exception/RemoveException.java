package com.ai.exception;

public class RemoveException extends Exception {
    public RemoveException(String message) {
        super(message); //부모의 생성자 호출
    }
}
