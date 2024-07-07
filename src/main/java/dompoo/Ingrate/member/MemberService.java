package dompoo.Ingrate.member;

import dompoo.Ingrate.exception.MemberNotFound;
import dompoo.Ingrate.exception.PasswordCheckIncorrect;
import dompoo.Ingrate.member.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TimeoutService timeoutService;
    private final PasswordEncoder encoder;

    public MemberDetailResponse getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        return new MemberDetailResponse(member);
    }

    public PasswordCheckResponse checkMyPassword(Long memberId, PasswordCheckRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        //비밀번호가 틀리다면 timeout 관리를 한다.
        if (!encoder.matches(request.getPassword(), member.getPassword())) {
            timeoutService.checkFail(member);
        }

        //비밀번호가 맞다면 정상 처리한다.
        timeoutService.checkSuccess(member);
        return new PasswordCheckResponse(true);
    }

    public MemberDetailResponse changeMyPassword(Long memberId, PasswordChangeRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        if (!request.getNewPassword().equals(request.getNewPasswordCheck())) {
            throw new PasswordCheckIncorrect();
        }

        member.setPassword(encoder.encode(request.getNewPassword()));

        return new MemberDetailResponse(member);
    }

    public WithdrawalResponse withdrawal(Long memberId, PasswordCheckRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        if (!encoder.matches(request.getPassword(), member.getPassword())) {
            return new WithdrawalResponse(false);
        }

        memberRepository.delete(member);
        return new WithdrawalResponse(true);
    }

    public List<MemberResponse> getAllMember() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::new)
                .toList();
    }

    public MemberAdminDetailResponse getMemberDetail(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        return new MemberAdminDetailResponse(member);
    }

    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        memberRepository.delete(member);
    }
}
