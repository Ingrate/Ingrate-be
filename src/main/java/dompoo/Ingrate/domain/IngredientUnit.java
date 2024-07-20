package dompoo.Ingrate.domain;

import dompoo.Ingrate.domain.enums.Unit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Unit unit;

    @Builder
    public IngredientUnit(String name, Unit unit) {
        this.name = name;
        this.unit = unit;
    }
}
