package dompoo.Ingrate.exception;

import dompoo.Ingrate.config.exception.MyException;

public class NotMyIngredient extends MyException {

    private static final String MESSAGE = "본인의 식재료가 아닙니다.";
    private static final String CODE = "404";

    public NotMyIngredient() {
        super(MESSAGE);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
