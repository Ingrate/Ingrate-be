package dompoo.Ingrate.member;

import dompoo.Ingrate.member.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public MemberDetailResponse getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new MemberDetailResponse(member);
    }

    public PasswordCheckResponse checkMyPassword(Long memberId, PasswordCheckRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        //TODO: passwordEncoder를 사용하여 비밀번호 확인
        if (member.getPassword().equals(request.getPassword())) {
            return new PasswordCheckResponse(true);
        } else {
            //TODO: password 확인 실패시 지수형태의 timeout 발생
            return new PasswordCheckResponse(false);
        }
    }

    public MemberDetailResponse changeMyPassword(Long memberId, PasswordChangeRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        //TODO: passwordEncoder를 사용하여 비밀번호 확인
        if (!member.getPassword().equals(request.getOldPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (!request.getNewPassword().equals(request.getNewPasswordCheck())) {
            throw new IllegalArgumentException("새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        return new MemberDetailResponse(member);
    }

    public WithdrawalResponse withdrawal(Long memberId, PasswordCheckRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        //TODO: passwordEncoder를 사용하여 비밀번호 확인
        if (!member.getPassword().equals(request.getPassword())) {
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
}
