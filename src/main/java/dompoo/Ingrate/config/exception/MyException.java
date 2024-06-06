package dompoo.Ingrate.config.exception;

import lombok.Getter;

@Getter
public abstract class MyException extends RuntimeException {

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getCode();
}