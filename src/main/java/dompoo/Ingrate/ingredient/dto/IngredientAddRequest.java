package dompoo.Ingrate.ingredient.dto;

import dompoo.Ingrate.config.enums.Unit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class IngredientAddRequest {

    private String name;
    private Float cost;
    private Float amount;
    private String unit;
    private String memo;
    private LocalDate date;
}
