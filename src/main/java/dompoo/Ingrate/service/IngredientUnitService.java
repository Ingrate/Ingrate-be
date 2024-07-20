package dompoo.Ingrate.service;

import dompoo.Ingrate.service.repository.IngredientUnitRepository;
import dompoo.Ingrate.api.request.UnitAddRequest;
import dompoo.Ingrate.api.response.UnitResponse;
import dompoo.Ingrate.domain.enums.Unit;
import dompoo.Ingrate.domain.IngredientUnit;
import dompoo.Ingrate.api.exception.AlreadyExistUnit;
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
