package dompoo.Ingrate.IngredientUnit;

import dompoo.Ingrate.IngredientUnit.dto.UnitResponse;
import dompoo.Ingrate.config.enums.Unit;
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

    public Boolean unitExistCheck(String name, Unit unit) {
        return ingredientUnitRepository.existsByNameAndUnit(name, unit);
    }
}
