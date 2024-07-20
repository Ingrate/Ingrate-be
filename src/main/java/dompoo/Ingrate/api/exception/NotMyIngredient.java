package dompoo.Ingrate.api.exception;

import dompoo.Ingrate.config.exception.MyException;

public class NotMyIngredient extends MyException {

    private static final String MESSAGE = "내 식재료가 아닙니다.";
    private static final String CODE = "400";

    public NotMyIngredient() {
        super(MESSAGE);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
