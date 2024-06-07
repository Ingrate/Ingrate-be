package dompoo.Ingrate.exception;

import dompoo.Ingrate.config.exception.MyException;

public class PasswordICheckIncorrect extends MyException {

    private static final String MESSAGE = "비밀번호 확인이 일치하지 않습니다.";
    private static final String CODE = "400";

    public PasswordICheckIncorrect() {
        super(MESSAGE);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
