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
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private Integer posts;
    
    @Column(nullable = false)
    private Integer point;
    
    @Enumerated(value = EnumType.STRING)
    private Role role;
    
    @Column(nullable = false)
    private Integer failedAttempts;
    
    @Column(nullable = false)
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

    public void addPostAndPoint(int num) {
        this.posts += num;
        this.point += num;
    }

    public boolean isAccountLocked() {
        return LocalDateTime.now().isBefore(lockTime);
    }
}
