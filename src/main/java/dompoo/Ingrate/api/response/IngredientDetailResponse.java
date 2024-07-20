package dompoo.Ingrate.api.response;

import dompoo.Ingrate.domain.Ingredient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class IngredientDetailResponse {

    private final Long id;
    private final String name;
    private final Float cost;
    private final Float amount;
    private final String unit;
    private final String memo;
    private final LocalDate date;

    public IngredientDetailResponse(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.cost = ingredient.getCost();
        this.amount = ingredient.getAmount();
        this.unit = ingredient.getUnit().getName();
        this.memo = ingredient.getMemo();
        this.date = ingredient.getDate();
    }
}
