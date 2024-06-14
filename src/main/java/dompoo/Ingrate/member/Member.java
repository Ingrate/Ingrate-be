package dompoo.Ingrate.member;

import dompoo.Ingrate.config.enums.Role;
import dompoo.Ingrate.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    private Integer failedAttempts;
    private LocalDateTime lockTime;

    @OneToMany(mappedBy = "member")
    private List<Ingredient> ingredients = new ArrayList<>();

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
        this.posts = 0;
        this.point = 5;
        this.role = MEMBER;
        this.failedAttempts = 0;
        this.lockTime = LocalDateTime.now();
    }

    public void addPost(Integer num) {
        this.posts += num;
    }

    public void addPoint(Integer num) {
        this.point += num;
    }

    public void successPasswordCheck() {
        this.failedAttempts = 0;
    }

    public void failPasswordCheck() {
        this.failedAttempts++;

        //연속해서 5번 실패할 때마다 timeout
        if (this.failedAttempts != 0 && this.failedAttempts % 5 == 0) {
            this.lockTime = LocalDateTime.now().plusSeconds(failedAttempts * 6);
        }
    }

    public boolean isAccountLocked() {
        return LocalDateTime.now().isBefore(lockTime);
    }
}
