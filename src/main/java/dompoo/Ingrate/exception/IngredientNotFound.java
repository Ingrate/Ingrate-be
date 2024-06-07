package dompoo.Ingrate.exception;

import dompoo.Ingrate.config.exception.MyException;

public class IngredientNotFound extends MyException {

    private static final String MESSAGE = "존재하지 않는 식재료입니다.";
    private static final String CODE = "404";

    public IngredientNotFound() {
        super(MESSAGE);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
