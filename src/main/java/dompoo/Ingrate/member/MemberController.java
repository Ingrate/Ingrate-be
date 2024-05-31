package dompoo.Ingrate.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //비회원 기능
    @PostMapping("/auth/signup")
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        return memberService.signUp(signUpRequest);
    }

    //회원 기능

    //어드민 기능
}
