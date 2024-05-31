package dompoo.Ingrate.member;

import dompoo.Ingrate.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "member")
    private List<Ingredient> ingredients = new ArrayList<>();

    public Member(String username, String password, Integer posts) {
        this.username = username;
        this.password = password;
        this.posts = posts;
    }

    public void addPost(Integer num) {
        this.posts += num;
    }
}
