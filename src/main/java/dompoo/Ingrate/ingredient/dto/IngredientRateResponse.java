package dompoo.Ingrate.ingredient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientRateResponse {

    private final Float rate;
    private final Integer volume;

    @Builder
    public IngredientRateResponse(Float rate, Integer volume) {
        this.rate = rate;
        this.volume = volume;
    }
}
