package dompoo.Ingrate.member;

import dompoo.Ingrate.config.enums.Role;
import dompoo.Ingrate.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static dompoo.Ingrate.config.enums.Role.MEMBER;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Integer posts;
    private Integer point;
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Ingredient> ingredients = new ArrayList<>();

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
        this.posts = 0;
        this.point = 5;
        this.role = MEMBER;
    }

    public void addPost(Integer num) {
        this.posts += num;
    }

    public void addPoint(Integer num) {
        this.point += num;
    }
}
