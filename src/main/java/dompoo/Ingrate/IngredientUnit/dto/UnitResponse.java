package dompoo.Ingrate.IngredientUnit.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UnitResponse {

    private final String name;
    private final String unit; //읽기 쉬운 한글명
    private final String enumUnit; //request로 받을 값
}
