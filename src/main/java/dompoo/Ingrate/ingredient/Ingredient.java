package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.config.enums.Unit;
import dompoo.Ingrate.member.Member;
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
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Float cost;
    
    @Column(nullable = false)
    private Float amount;
    
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Unit unit;
    
    @Column(nullable = false)
    private String memo;
    
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Ingredient(String name, Float cost, Float amount, Unit unit, String memo, LocalDate date, Member member) {
        this.name = name;
        this.cost = cost;
        this.amount = amount;
        this.unit = unit;
        this.memo = memo;
        this.date = date;
        setMember(member);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getIngredients().add(this);
        member.addPost(1);
    }
}
