package dompoo.Ingrate.api.response;

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
