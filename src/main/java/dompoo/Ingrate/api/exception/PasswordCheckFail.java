package dompoo.Ingrate.api.exception;

import dompoo.Ingrate.config.exception.MyException;

public class PasswordCheckFail extends MyException {

    private static final String CODE = "400";

    public PasswordCheckFail(Integer failCount) {
        super("비밀번호가 일치하지 않습니다. (시도: " + failCount + "회)");
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
