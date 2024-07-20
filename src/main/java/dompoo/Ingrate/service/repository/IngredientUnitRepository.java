package dompoo.Ingrate.service.repository;

import dompoo.Ingrate.domain.enums.Unit;
import dompoo.Ingrate.domain.IngredientUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientUnitRepository extends JpaRepository<IngredientUnit, Long> {

    boolean existsByNameAndUnit(String name, Unit unit);
}
