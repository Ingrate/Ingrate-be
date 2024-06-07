package dompoo.Ingrate.exception;

import dompoo.Ingrate.config.exception.MyException;

public class MemberNotFound extends MyException {

    private static final String MESSAGE = "존재하지 않는 회원입니다.";
    private static final String CODE = "404";

    public MemberNotFound() {
        super(MESSAGE);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
