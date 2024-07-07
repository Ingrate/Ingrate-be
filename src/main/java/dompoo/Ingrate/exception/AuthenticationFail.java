package dompoo.Ingrate.exception;

import dompoo.Ingrate.config.exception.MyException;

public class AuthenticationFail extends MyException {

    private static final String MESSAGE = "아이디 혹은 비밀번호가 올바르지 않습니다.";
    private static final String CODE = "400";

    public AuthenticationFail() {
        super(MESSAGE);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
