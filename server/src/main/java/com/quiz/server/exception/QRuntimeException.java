package com.quiz.server.exception;

import lombok.Getter;

import static com.quiz.server.util.HostName.currentHostName;

@Getter
public class QRuntimeException extends RuntimeException {

    private final ExceptionCode code;

    public QRuntimeException(ExceptionCode code) {
        super(String.format("%s: code: %s message: %s",
                currentHostName(), code.name(), code.getMessage()));
        this.code = code;
    }

    public QRuntimeException(ExceptionCode code, Throwable cause) {
        super(String.format("%s: code: %s message: %s cause: %s",
                currentHostName(), code.name(), code.getMessage(), cause.getMessage()), cause);
        this.code = code;
    }
}
