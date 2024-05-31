package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.config.enums.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByNameAndUnit(String name, Unit unit);
}
