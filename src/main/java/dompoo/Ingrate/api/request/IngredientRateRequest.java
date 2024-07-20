package dompoo.Ingrate.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class IngredientRateRequest {

    @NotEmpty(message = "식재료명을 입력해주세요.")
    private String name;

    @NotNull(message = "가격을 입력해주세요.")
    @Positive(message = "가격은 0보다 커야합니다.")
    private Float cost;

    @NotNull(message = "양을 입력해주세요.")
    @Positive(message = "양은 0보다 커야합니다.")
    private Float amount;

    @NotEmpty(message = "단위를 입력해주세요.")
    private String unit;
}
