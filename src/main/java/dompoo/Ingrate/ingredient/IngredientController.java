package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.ingredient.dto.IngredientAddRequest;
import dompoo.Ingrate.ingredient.dto.IngredientFindResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/ingredient")
    public List<IngredientFindResponse> getMyIngredient(Long memberId) {
        return ingredientService.getMyIngredient(memberId);
    }

    //어드민 기능
}
