package com.zipte.member.server.application.service.exception;

public class DuplicationUserException extends RuntimeException {

    public DuplicationUserException(String message) {
        super(message);
    }
}
