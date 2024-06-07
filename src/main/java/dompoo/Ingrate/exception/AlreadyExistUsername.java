package dompoo.Ingrate.exception;

import dompoo.Ingrate.config.exception.MyException;

public class AlreadyExistUsername extends MyException {

    private static final String MESSAGE = "이미 존재하는 닉네임입니다.";
    private static final String CODE = "400";

    public AlreadyExistUsername() {
        super(MESSAGE);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
