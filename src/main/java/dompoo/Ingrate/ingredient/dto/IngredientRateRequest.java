package dompoo.Ingrate.ingredient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class IngredientRateRequest {

    private String name;
    private Float cost;
    private Float amount;
    private String unit;
}
