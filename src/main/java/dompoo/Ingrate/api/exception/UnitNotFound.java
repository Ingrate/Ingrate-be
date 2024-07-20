package dompoo.Ingrate.api.exception;

import dompoo.Ingrate.config.exception.MyException;

public class UnitNotFound extends MyException {

    private static final String MESSAGE = "존재하지 않는 단위입니다.";
    private static final String CODE = "404";

    public UnitNotFound() {
        super(MESSAGE);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
