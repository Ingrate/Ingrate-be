package dompoo.Ingrate.member;

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
    private final PasswordEncoder encoder;

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Member member = memberRepository.save(Member.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build());

        return new SignUpResponse(member);
    }

    public MemberDetailResponse getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new MemberDetailResponse(member);
    }

    public PasswordCheckResponse checkMyPassword(Long memberId, PasswordCheckRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (encoder.matches(request.getPassword(), member.getPassword())) {
            return new PasswordCheckResponse(true);
        } else {
            //TODO: password 확인 실패시 지수형태의 timeout 발생
            return new PasswordCheckResponse(false);
        }
    }

    public MemberDetailResponse changeMyPassword(Long memberId, PasswordChangeRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!encoder.matches(request.getOldPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (!request.getNewPassword().equals(request.getNewPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }

        member.setPassword(encoder.encode(request.getNewPassword()));

        return new MemberDetailResponse(member);
    }

    public WithdrawalResponse withdrawal(Long memberId, PasswordCheckRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new MemberAdminDetailResponse(member);
    }

    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        memberRepository.delete(member);
    }
}
