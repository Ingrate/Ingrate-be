package dompoo.Ingrate.api.exception;

import dompoo.Ingrate.config.exception.MyException;

public class PasswordCheckLock extends MyException {

    private static final String CODE = "400";

    public PasswordCheckLock(Long timeout) {
        super(timeout + "초 후 시도해주세요.");
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
