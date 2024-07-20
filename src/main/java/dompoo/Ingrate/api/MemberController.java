package dompoo.Ingrate.api;

import dompoo.Ingrate.api.request.PasswordChangeRequest;
import dompoo.Ingrate.api.request.PasswordCheckRequest;
import dompoo.Ingrate.api.response.*;
import dompoo.Ingrate.config.security.UserPrincipal;
import dompoo.Ingrate.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원 기능
    @GetMapping("/member")
    public MemberDetailResponse getMyInfo(@AuthenticationPrincipal UserPrincipal principal) {
        return memberService.getMyInfo(principal.getMemberId());
    }

    @PostMapping("/member")
    public PasswordCheckResponse checkMyPassword(@AuthenticationPrincipal UserPrincipal principal, @RequestBody @Valid PasswordCheckRequest request) {
        return memberService.checkMyPassword(principal.getMemberId(), request);
    }

    @PutMapping("/member")
    public MemberDetailResponse changeMyPassword(@AuthenticationPrincipal UserPrincipal principal, @RequestBody @Valid PasswordChangeRequest request) {
        return memberService.changeMyPassword(principal.getMemberId(), request);
    }

    @DeleteMapping("/member")
    public WithdrawalResponse withdrawal(@AuthenticationPrincipal UserPrincipal principal, @RequestBody @Valid PasswordCheckRequest request) {
        return memberService.withdrawal(principal.getMemberId(), request);
    }

    //어드민 기능
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage/member")
    public List<MemberResponse> getAllMember() {
        return memberService.getAllMember();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage/member/{memberId}")
    public MemberAdminDetailResponse getMemberDetail(@PathVariable Long memberId) {
        return memberService.getMemberDetail(memberId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/manage/member/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }
}
