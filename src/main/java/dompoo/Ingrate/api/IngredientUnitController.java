package dompoo.Ingrate.api;

import dompoo.Ingrate.service.IngredientUnitService;
import dompoo.Ingrate.api.request.UnitAddRequest;
import dompoo.Ingrate.api.response.UnitResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ingredient/unit")
    public UnitResponse addUnit(@RequestBody @Valid UnitAddRequest request) {
        return ingredientUnitService.addUnit(request);
    }
}
