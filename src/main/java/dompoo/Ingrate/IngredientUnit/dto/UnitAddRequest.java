package dompoo.Ingrate.IngredientUnit.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnitAddRequest {

    @NotEmpty(message = "식재료명을 입력해주세요.")
    private String name;
    @NotEmpty(message = "단위명을 입력해주세요.")
    private String unit;
}
