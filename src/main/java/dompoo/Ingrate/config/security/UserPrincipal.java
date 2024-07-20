package dompoo.Ingrate.config.security;

import dompoo.Ingrate.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserPrincipal extends User {

    private final Long memberId;
    private final String username;
    private final String role;

    public UserPrincipal(Member member) {
        super(member.getUsername(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        this.memberId = member.getId();
        this.username = member.getUsername();
        this.role = member.getRole().getName();
    }
}
