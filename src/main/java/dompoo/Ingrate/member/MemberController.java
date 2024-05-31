package dompoo.Ingrate.member;

import dompoo.Ingrate.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @GetMapping("/member")
    public MemberDetailResponse getMyInfo(Long memberId) {
        return memberService.getMyInfo(memberId);
    }

    @PostMapping("/member")
    public PasswordCheckResponse checkMyPassword(Long memberId, PasswordCheckRequest request) {
        return memberService.checkMyPassword(memberId, request);
    }

    @PutMapping("/member")
    public MemberDetailResponse changeMyPassword(Long memberId, PasswordChangeRequest request) {
        return memberService.changeMyPassword(memberId, request);
    }

    //어드민 기능
}
