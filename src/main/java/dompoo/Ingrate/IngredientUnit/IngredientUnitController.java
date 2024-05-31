package dompoo.Ingrate.IngredientUnit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IngredientUnitController {

    private final IngredientUnitService ingredientUnitService;

    //비회원 기능

    //회원 기능
    @GetMapping("/ingredient/unit")
    public List<UnitResponse> getUnitList() {
        return ingredientUnitService.getUnitList();
    }

    //어드민 기능
}
