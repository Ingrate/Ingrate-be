package dompoo.Ingrate.ingredient.dto;

import dompoo.Ingrate.config.enums.Unit;
import dompoo.Ingrate.ingredient.Ingredient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class IngredientFindResponse {

    private final String name;
    private final Float cost;
    private final Float amount;
    private final Unit unit;

    public IngredientFindResponse(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.cost = ingredient.getCost();
        this.amount = ingredient.getAmount();
        this.unit = ingredient.getUnit();
    }
}
