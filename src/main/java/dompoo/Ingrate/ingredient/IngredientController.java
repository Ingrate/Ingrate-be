package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.ingredient.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/ingredient/{ingredientId}")
    public IngredientDetailResponse editMyIngredient(Long memberId, @PathVariable Long ingredientId, IngredientEditRequest request) {
        return ingredientService.editMyIngredient(memberId, ingredientId, request);
    }

    @DeleteMapping("/ingredient/{ingredientId}")
    public void deleteMyIngredient(Long memberId, @PathVariable Long ingredientId) {
        ingredientService.deleteMyIngredient(memberId, ingredientId);
    }

    //어드민 기능
    @GetMapping("/manage/ingredient")
    public List<IngredientResponse> getAllIngredient() {
        return ingredientService.getAllIngredient();
    }

    @GetMapping("/manage/ingredient/{ingredientId}")
    public IngredientAdminDetailResponse getIngredientDetail(@PathVariable Long ingredientId) {
        return ingredientService.getIngredientDetail(ingredientId);
    }

    @PutMapping("/manage/ingredient/{ingredientId}")
    public IngredientAdminDetailResponse editIngredient(@PathVariable Long ingredientId, IngredientEditRequest request) {
        return ingredientService.editIngredient(ingredientId, request);
    }
}
