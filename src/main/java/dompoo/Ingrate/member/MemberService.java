package dompoo.Ingrate.member;

import dompoo.Ingrate.member.dto.SignUpRequest;
import dompoo.Ingrate.member.dto.SignUpResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Member member = memberRepository.save(Member.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword()) //TODO : 비밀번호 암호화
                .build());

        return new SignUpResponse(member);
    }
}
