package dompoo.Ingrate.member;

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

    @Test
    @DisplayName("회원가입")
    void signUp() {
        //given
        SignUpRequest request = SignUpRequest.builder()
                .username("창근")
                .password("1234")
                .passwordCheck("1234")
                .build();

        //when
        SignUpResponse response = service.signUp(request);

        //then
        Member find = repository.findAll().get(0);
        assertThat(response.getId()).isEqualTo(find.getId());
        assertThat(response.getUsername()).isEqualTo("창근");
    }

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

        //when
        PasswordCheckResponse response = service.checkMyPassword(me.getId(), request);

        //then
        assertThat(response.getIsCorrect()).isFalse();
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
                .oldPassword("1234")
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
}