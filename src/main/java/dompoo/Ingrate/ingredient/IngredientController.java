package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.ingredient.dto.IngredientAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    //비회원 기능

    //회원 기능
    @PostMapping("/ingredient")
    public void addIngredient(Long memberId, IngredientAddRequest request) {
        ingredientService.addIngredient(memberId, request);
    }

    //어드민 기능
}
