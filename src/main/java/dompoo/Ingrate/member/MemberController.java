package dompoo.Ingrate.member;

import dompoo.Ingrate.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/member")
    public WithdrawalResponse withdrawal(Long memberId, PasswordCheckRequest request) {
        return memberService.withdrawal(memberId, request);
    }

    //어드민 기능
    //TODO: 어드민만 접근 가능하도록 수정
    @GetMapping("/manage/member")
    public List<MemberResponse> getAllMember() {
        return memberService.getAllMember();
    }

    @GetMapping("/manage/member/{memberId}")
    public MemberAdminDetailResponse getMemberDetail(Long memberId) {
        return memberService.getMemberDetail(memberId);
    }
}
