package dompoo.Ingrate.IngredientUnit;

import dompoo.Ingrate.ingredient.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientUnitRepository extends JpaRepository<IngredientUnit, Long> {

    boolean existsByNameAndUnit(String name, Unit unit);
}
