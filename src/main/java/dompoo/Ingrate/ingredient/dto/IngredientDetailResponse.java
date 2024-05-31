package dompoo.Ingrate.ingredient.dto;

import dompoo.Ingrate.ingredient.Ingredient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class IngredientDetailResponse {

    private final String name;
    private final Float cost;
    private final Float amount;
    private final String unit;
    private final String memo;
    private final LocalDate date;

    public IngredientDetailResponse(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.cost = ingredient.getCost();
        this.amount = ingredient.getAmount();
        this.unit = ingredient.getUnit().getName();
        this.memo = ingredient.getMemo();
        this.date = ingredient.getDate();
    }
}
