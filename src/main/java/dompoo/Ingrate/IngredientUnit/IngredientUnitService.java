package dompoo.Ingrate.IngredientUnit;

import dompoo.Ingrate.IngredientUnit.dto.UnitAddRequest;
import dompoo.Ingrate.IngredientUnit.dto.UnitResponse;
import dompoo.Ingrate.config.enums.Unit;
import dompoo.Ingrate.exception.AlreadyExistUnit;
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
                .map(unit -> new UnitResponse(unit.getName(), unit.getUnit().getName(), unit.getUnit().toString()))
                .toList();
    }

    public Boolean unitExistCheck(String name, Unit unit) {
        return ingredientUnitRepository.existsByNameAndUnit(name, unit);
    }

    public UnitResponse addUnit(UnitAddRequest request) {
        if (ingredientUnitRepository.existsByNameAndUnit(request.getName(), Unit.valueOf(request.getUnit()))) {
            throw new AlreadyExistUnit();
        }

        IngredientUnit savedUnit = ingredientUnitRepository.save(IngredientUnit.builder()
                .name(request.getName())
                .unit(Unit.valueOf(request.getUnit()))
                .build());

        return new UnitResponse(savedUnit.getName(), savedUnit.getUnit().getName(), savedUnit.getUnit().toString());
    }
}
