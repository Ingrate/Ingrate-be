package dompoo.Ingrate.member;

import dompoo.Ingrate.exception.MemberNotFound;
import dompoo.Ingrate.exception.PasswordCheckFail;
import dompoo.Ingrate.exception.PasswordCheckIncorrect;
import dompoo.Ingrate.exception.PasswordCheckLock;
import dompoo.Ingrate.member.dto.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService service;
    @Autowired MemberRepository repository;
    @Autowired PasswordEncoder encoder;

    @AfterEach
    void setUp() {
        repository.deleteAll();
    }

//    @Test
//    @DisplayName("회원가입")
//    void signUp() {
//        //given
//        SignUpRequest request = SignUpRequest.builder()
//                .username("창근")
//                .password("1234")
//                .passwordCheck("1234")
//                .build();
//
//        //when
//        SignUpResponse response = service.signUp(request);
//
//        //then
//        Member find = repository.findAll().get(0);
//        assertThat(response.getId()).isEqualTo(find.getId());
//        assertThat(response.getUsername()).isEqualTo("창근");
//    }
//
//    @Test
//    @DisplayName("이미 존재하는 사용자명으로 회원가입")
//    void signUpFail1() {
//        //given
//        repository.save(Member.builder()
//                .username("창근")
//                .password(encoder.encode("1234"))
//                .build());
//
//        SignUpRequest request = SignUpRequest.builder()
//                .username("창근")
//                .password("1234")
//                .passwordCheck("1234")
//                .build();
//
//        //expected
//        assertThatThrownBy(() ->
//                service.signUp(request))
//                .isInstanceOf(AlreadyExistUsername.class);
//    }
//
//    @Test
//    @DisplayName("패스워드 확인 일치하지 않는 회원가입")
//    void signUpFail2() {
//        //given
//        SignUpRequest request = SignUpRequest.builder()
//                .username("창근")
//                .password("1234")
//                .passwordCheck("5678")
//                .build();
//
//        //expected
//        assertThatThrownBy(() ->
//                service.signUp(request))
//                .isInstanceOf(PasswordICheckIncorrect.class);
//    }

    @Test
    @DisplayName("내 정보 보기")
    void getMyInfo() {
        //given
        Member me = repository.save(Member.builder()
                .username("창근")
                .password("1234")
                .build());

        //when
        MemberDetailResponse response = service.getMyInfo(me.getId());

        //then
        assertThat(response.getUsername()).isEqualTo("창근");
        assertThat(response.getPosts()).isEqualTo(0);
        assertThat(response.getPoint()).isEqualTo(5);
    }

    @Test
    @DisplayName("존재하지 않는 회원 정보 보기")
    void getMyInfoFail1() {
        //given
        Member me = repository.save(Member.builder()
                .username("창근")
                .password("1234")
                .build());

        //expected
        assertThatThrownBy(() ->
                service.getMyInfo(me.getId() + 1))
                .isInstanceOf(MemberNotFound.class);
    }

    @Test
    @DisplayName("비밀번호 확인 맞음")
    void checkPassword() {
        //given
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("1234")
                .build();

        //when
        PasswordCheckResponse response = service.checkMyPassword(me.getId(), request);

        //then
        assertThat(response.getIsCorrect()).isTrue();
    }

    @Test
    @DisplayName("비밀번호 확인 틀림")
    void checkPasswordWrong() {
        //given
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("5678")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.checkMyPassword(me.getId(), request))
                .isInstanceOf(PasswordCheckFail.class);
    }

    @Test
    @DisplayName("비밀번호 확인 틀린 후에 맞으면 시도 회수 초기화된다.")
    void checkPasswordWrongRefresh() {
        //given
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordCheckRequest wrongRequest = PasswordCheckRequest.builder()
                .password("5678")
                .build();

        PasswordCheckRequest rightRequest = PasswordCheckRequest.builder()
                .password("1234")
                .build();

        for (int i = 0; i < 4; i++) {
            try {
                service.checkMyPassword(me.getId(), wrongRequest);
            } catch (PasswordCheckFail ignored) {}
        }
        service.checkMyPassword(me.getId(), rightRequest);

        //expected
        assertThat(me.getFailedAttempts()).isEqualTo(0);
    }

    @Test
    @DisplayName("비밀번호 확인 틀림 - 락")
    void checkPasswordLock() {
        //given
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("5678")
                .build();

        for (int i = 0; i < 4; i++) {
            try {
                service.checkMyPassword(me.getId(), request);
            } catch (PasswordCheckFail ignored) {}
        }

        //expected
        assertThatThrownBy(() ->
                service.checkMyPassword(me.getId(), request))
                .isInstanceOf(PasswordCheckLock.class);
    }

    @Test
    @DisplayName("존재하지 않는 회원의 비밀번호 확인")
    void checkPasswordFail1() {
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("5678")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.checkMyPassword(me.getId() + 1, request))
                .isInstanceOf(MemberNotFound.class);
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword() {
        //given
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordChangeRequest request = PasswordChangeRequest.builder()
                .newPassword("5678")
                .newPasswordCheck("5678")
                .build();

        //when
        MemberDetailResponse response = service.changeMyPassword(me.getId(), request);

        //then
        assertThat(response.getUsername()).isEqualTo("창근");
        assertThat(response.getPosts()).isEqualTo(0);
        assertThat(response.getPoint()).isEqualTo(5);
    }

    @Test
    @DisplayName("존재하지 않는 회원의 비밀번호 변경")
    void changPasswordFail1() {
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordChangeRequest request = PasswordChangeRequest.builder()
                .newPassword("5678")
                .newPasswordCheck("5678")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.changeMyPassword(me.getId() + 1, request))
                .isInstanceOf(MemberNotFound.class);
    }

    @Test
    @DisplayName("비밀번호 확인 틀린 비밀번호 변경")
    void changePasswordFail3() {
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordChangeRequest request = PasswordChangeRequest.builder()
                .newPassword("5678")
                .newPasswordCheck("qwer")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.changeMyPassword(me.getId(), request))
                .isInstanceOf(PasswordCheckIncorrect.class);
    }

    @Test
    @DisplayName("회원탈퇴")
    void withDrwal() {
        //given
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("1234")
                .build();

        //when
        WithdrawalResponse response = service.withdrawal(me.getId(), request);

        //then
        assertThat(response.getWithdrawalSuccess()).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 회원의 회원탈퇴")
    void withDrawalFail1() {
        Member me = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("1234")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.withdrawal(me.getId() + 1, request))
                .isInstanceOf(MemberNotFound.class);
    }

    @Test
    @DisplayName("관리자 - 회원목록")
    void getList() {
        //given
        Member other1 = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());
        Member other2 = repository.save(Member.builder()
                .username("아영")
                .password(encoder.encode("1234"))
                .build());

        //when
        List<MemberResponse> response = service.getAllMember();

        //then
        assertThat(response).hasSize(2);

        assertThat(response.get(0).getId()).isEqualTo(other1.getId());
        assertThat(response.get(0).getUsername()).isEqualTo("창근");
        assertThat(response.get(1).getId()).isEqualTo(other2.getId());
        assertThat(response.get(1).getUsername()).isEqualTo("아영");
    }

    @Test
    @DisplayName("관리자 - 회원상세")
    void getDetail() {
        //given
        Member other = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        //when
        MemberAdminDetailResponse response = service.getMemberDetail(other.getId());

        //then
        assertThat(response.getId()).isEqualTo(other.getId());
        assertThat(response.getUsername()).isEqualTo("창근");
        assertThat(response.getPosts()).isEqualTo(0);
        assertThat(response.getPoint()).isEqualTo(5);
    }

    @Test
    @DisplayName("관리자 - 존재하지 않는 회원의 회원상세")
    void getDetailFail1() {
        //given
        Member other = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        //expected
        assertThatThrownBy(() ->
                service.getMemberDetail(other.getId() + 1))
                .isInstanceOf(MemberNotFound.class);
    }

    @Test
    @DisplayName("관리자 - 회원삭제")
    void delete() {
        //given
        Member other = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        //when
        service.deleteMember(other.getId());

        //then
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("관리자 - 존재하지 않는 회원의 회원삭제")
    void deleteFail1() {
        //given
        Member other = repository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        //expected
        assertThatThrownBy(() ->
                service.deleteMember(other.getId() + 1))
                .isInstanceOf(MemberNotFound.class);
    }
}