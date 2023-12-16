package com.example.colorbalancecalculator.exception;

public class InvalidDnaSequenceException extends Exception {
    public InvalidDnaSequenceException() {
        super();
    }

    public InvalidDnaSequenceException(String message) {
        super(message);
    }

    public InvalidDnaSequenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
