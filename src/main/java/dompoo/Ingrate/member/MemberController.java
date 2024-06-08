package dompoo.Ingrate.member;

import dompoo.Ingrate.config.security.UserPrincipal;
import dompoo.Ingrate.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //비회원 기능
    @PostMapping("/auth/signup")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return memberService.signUp(signUpRequest);
    }

    //회원 기능
    @GetMapping("/member")
    public MemberDetailResponse getMyInfo(@AuthenticationPrincipal UserPrincipal principal) {
        return memberService.getMyInfo(principal.getMemberId());
    }

    @PostMapping("/member")
    public PasswordCheckResponse checkMyPassword(@AuthenticationPrincipal UserPrincipal principal, @RequestBody PasswordCheckRequest request) {
        return memberService.checkMyPassword(principal.getMemberId(), request);
    }

    @PutMapping("/member")
    public MemberDetailResponse changeMyPassword(@AuthenticationPrincipal UserPrincipal principal, @RequestBody PasswordChangeRequest request) {
        return memberService.changeMyPassword(principal.getMemberId(), request);
    }

    @DeleteMapping("/member")
    public WithdrawalResponse withdrawal(@AuthenticationPrincipal UserPrincipal principal, @RequestBody PasswordCheckRequest request) {
        return memberService.withdrawal(principal.getMemberId(), request);
    }

    //어드민 기능
    //TODO: 어드민만 접근 가능하도록 수정
    @GetMapping("/manage/member")
    public List<MemberResponse> getAllMember() {
        return memberService.getAllMember();
    }

    @GetMapping("/manage/member/{memberId}")
    public MemberAdminDetailResponse getMemberDetail(@PathVariable Long memberId) {
        return memberService.getMemberDetail(memberId);
    }

    @DeleteMapping("/manage/member/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }
}
