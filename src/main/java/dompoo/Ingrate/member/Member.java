package dompoo.Ingrate.member;

import dompoo.Ingrate.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member")
    private List<Ingredient> ingredients = new ArrayList<>();

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
        this.posts = 0;
        this.point = 5;
    }

    public void addPost(Integer num) {
        this.posts += num;
    }

    public void addPoint(Integer num) {
        this.point += num;
    }
}
