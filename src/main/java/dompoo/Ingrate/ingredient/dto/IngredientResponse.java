package dompoo.Ingrate.ingredient.dto;

import dompoo.Ingrate.ingredient.Ingredient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class IngredientResponse {

    private final String name;
    private final Float cost;
    private final Float amount;
    private final String unit;

    public IngredientResponse(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.cost = ingredient.getCost();
        this.amount = ingredient.getAmount();
        this.unit = ingredient.getUnit().getName();
    }
}
