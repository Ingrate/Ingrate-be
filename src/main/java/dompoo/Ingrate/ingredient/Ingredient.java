package dompoo.Ingrate.ingredient;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Float cost;
    private Unit unit;
    private String memo;
    private LocalDate date;

    //TODO : member와 연관관계 필요

    public Ingredient(String name, Float cost, Unit unit, String memo, LocalDate date) {
        this.name = name;
        this.cost = cost;
        this.unit = unit;
        this.memo = memo;
        this.date = date;
    }
}
