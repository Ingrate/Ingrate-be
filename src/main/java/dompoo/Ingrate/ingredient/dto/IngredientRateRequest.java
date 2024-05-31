package dompoo.Ingrate.ingredient.dto;

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

    @NotNull(message = "식재료명을 입력해주세요.")
    @NotEmpty(message = "식재료명은 비어있을 수 없습니다.")
    private String name;

    @NotNull(message = "가격을 입력해주세요.")
    @Positive(message = "가격은 0보다 커야합니다.")
    private Float cost;

    @NotNull(message = "양을 입력해주세요.")
    @Positive(message = "양은 0보다 커야합니다.")
    private Float amount;

    @NotNull(message = "단위를 입력해주세요.")
    @NotEmpty(message = "단위는 비어있을 수 없습니다.")
    private String unit;
}
