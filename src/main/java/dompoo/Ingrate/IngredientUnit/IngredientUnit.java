package dompoo.Ingrate.IngredientUnit;

import dompoo.Ingrate.config.enums.Unit;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Unit unit;

    @Builder
    public IngredientUnit(Long id, String name, Unit unit) {
        this.name = name;
        this.unit = unit;
    }
}
