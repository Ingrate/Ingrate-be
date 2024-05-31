package dompoo.Ingrate.IngredientUnit;

import dompoo.Ingrate.IngredientUnit.dto.UnitResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientUnitService {

    private final IngredientUnitRepository ingredientUnitRepository;

    public List<UnitResponse> getUnitList() {
        return ingredientUnitRepository.findAll().stream()
                .map(unit -> new UnitResponse(unit.getName(), unit.getUnit().getName()))
                .toList();
    }
}
