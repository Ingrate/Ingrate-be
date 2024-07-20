package dompoo.Ingrate.service.repository;

import dompoo.Ingrate.domain.enums.Unit;
import dompoo.Ingrate.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByNameAndUnit(String name, Unit unit);
}
