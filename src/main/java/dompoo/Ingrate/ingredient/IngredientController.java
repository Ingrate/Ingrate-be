package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.config.security.UserPrincipal;
import dompoo.Ingrate.ingredient.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    //비회원 기능
    @GetMapping("/ingredient/rate")
    public IngredientRateResponse rateIngredient(@RequestBody @Valid IngredientRateRequest request) {
        return ingredientService.rateIngredient(request);
    }

    //회원 기능
    @PostMapping("/ingredient")
    public void addIngredient(@AuthenticationPrincipal UserPrincipal principal, @RequestBody @Valid IngredientAddRequest request) {
        ingredientService.addIngredient(principal.getMemberId(), request);
    }

    @GetMapping("/ingredient")
    public List<IngredientResponse> getMyIngredient(@AuthenticationPrincipal UserPrincipal principal) {
        return ingredientService.getMyIngredient(principal.getMemberId());
    }

    @GetMapping("/ingredient/{ingredientId}")
    public IngredientDetailResponse getMyIngredient(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long ingredientId) {
        return ingredientService.getMyIngredientDetail(principal.getMemberId(), ingredientId);
    }

    @PutMapping("/ingredient/{ingredientId}")
    public IngredientDetailResponse editMyIngredient(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long ingredientId, @RequestBody @Valid IngredientEditRequest request) {
        return ingredientService.editMyIngredient(principal.getMemberId(), ingredientId, request);
    }

    @DeleteMapping("/ingredient/{ingredientId}")
    public void deleteMyIngredient(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long ingredientId) {
        ingredientService.deleteMyIngredient(principal.getMemberId(), ingredientId);
    }

    //어드민 기능
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage/ingredient")
    public List<IngredientResponse> getAllIngredient() {
        return ingredientService.getAllIngredient();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage/ingredient/{ingredientId}")
    public IngredientAdminDetailResponse getIngredientDetail(@PathVariable Long ingredientId) {
        return ingredientService.getIngredientDetail(ingredientId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/manage/ingredient/{ingredientId}")
    public IngredientAdminDetailResponse editIngredient(@PathVariable Long ingredientId, @RequestBody @Valid IngredientEditRequest request) {
        return ingredientService.editIngredient(ingredientId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/manage/ingredient/{ingredientId}")
    public void deleteIngredient(@PathVariable Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
    }
}
