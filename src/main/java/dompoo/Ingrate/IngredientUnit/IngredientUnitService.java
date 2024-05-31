package dompoo.Ingrate.IngredientUnit;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientUnitService {

    private final IngredientUnitRepository ingredientUnitRepository;
}
