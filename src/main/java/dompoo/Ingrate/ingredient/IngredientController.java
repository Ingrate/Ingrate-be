package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.ingredient.dto.IngredientAddRequest;
import dompoo.Ingrate.ingredient.dto.IngredientDetailResponse;
import dompoo.Ingrate.ingredient.dto.IngredientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<IngredientResponse> getMyIngredient(Long memberId) {
        return ingredientService.getMyIngredient(memberId);
    }

    @GetMapping("/ingredient/{ingredientId}")
    public IngredientDetailResponse getMyIngredient(Long memberId, @PathVariable Long ingredientId) {
        return ingredientService.getMyIngredientDetail(memberId, ingredientId);
    }

    //어드민 기능
}
