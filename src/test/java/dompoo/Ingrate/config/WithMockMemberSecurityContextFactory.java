package dompoo.Ingrate.config;

import dompoo.Ingrate.config.security.UserPrincipal;
import dompoo.Ingrate.member.Member;
import dompoo.Ingrate.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

@RequiredArgsConstructor
public class WithMockMemberSecurityContextFactory implements WithSecurityContextFactory<WithMockMember> {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Override
    public SecurityContext createSecurityContext(WithMockMember annotation) {

        Member member = memberRepository.save(Member.builder()
                .username(annotation.username())
                .password(encoder.encode(annotation.password()))
                .build());

        UserPrincipal principal = new UserPrincipal(member);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        principal,
                        member.getPassword(),
                        List.of(new SimpleGrantedAuthority(annotation.role().getName())));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        return context;
    }
}
