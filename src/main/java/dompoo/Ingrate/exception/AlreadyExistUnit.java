package dompoo.Ingrate.exception;

import dompoo.Ingrate.config.exception.MyException;

public class AlreadyExistUnit extends MyException {

    private static final String MESSAGE = "이미 존재하는 식재료-단위입니다.";
    private static final String CODE = "400";

    public AlreadyExistUnit() {
        super(MESSAGE);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
