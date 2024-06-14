package dompoo.Ingrate.IngredientUnit.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnitAddRequest {

    @NotEmpty(message = "식재료명을 입력해주세요.")
    private String name;
    @NotEmpty(message = "단위를 입력해주세요.")
    private String unit;

    @Builder
    public UnitAddRequest(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }
}
