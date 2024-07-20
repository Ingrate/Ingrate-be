package dompoo.Ingrate.auth;

import dompoo.Ingrate.api.request.LoginRequest;
import dompoo.Ingrate.api.response.LoginResponse;
import dompoo.Ingrate.api.exception.AlreadyExistUsername;
import dompoo.Ingrate.api.exception.AuthenticationFail;
import dompoo.Ingrate.api.exception.PasswordCheckIncorrect;
import dompoo.Ingrate.domain.Member;
import dompoo.Ingrate.service.repository.MemberRepository;
import dompoo.Ingrate.api.request.SignUpRequest;
import dompoo.Ingrate.api.response.SignUpResponse;
import dompoo.Ingrate.service.AuthService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    AuthService authService;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder encoder;

    @AfterEach
    void setUp() {
        memberRepository.deleteAll();
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
        SignUpResponse response = authService.signUp(request);

        //then
        Member find = memberRepository.findAll().get(0);
        assertThat(response.getId()).isEqualTo(find.getId());
        assertThat(response.getUsername()).isEqualTo("창근");
    }

    @Test
    @DisplayName("이미 존재하는 사용자명으로 회원가입")
    void signUpFail1() {
        //given
        memberRepository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        SignUpRequest request = SignUpRequest.builder()
                .username("창근")
                .password("1234")
                .passwordCheck("1234")
                .build();

        //expected
        assertThatThrownBy(() ->
                authService.signUp(request))
                .isInstanceOf(AlreadyExistUsername.class);
    }

    @Test
    @DisplayName("패스워드 확인 일치하지 않는 회원가입")
    void signUpFail2() {
        //given
        SignUpRequest request = SignUpRequest.builder()
                .username("창근")
                .password("1234")
                .passwordCheck("5678")
                .build();

        //expected
        assertThatThrownBy(() ->
                authService.signUp(request))
                .isInstanceOf(PasswordCheckIncorrect.class);
    }

    @Test
    @DisplayName("로그인")
    void login() {
        //given
        Member savedMember = memberRepository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        LoginRequest request = LoginRequest.builder()
                .username("창근")
                .password("1234")
                .rememberme(false)
                .build();

        //when
        LoginResponse response = authService.login(request);

        //then
        assertThat(response.getId()).isEqualTo(savedMember.getId());
        assertThat(response.getUsername()).isEqualTo("창근");
        assertThat(response.getAccessToken()).isNotNull();
    }

    @Test
    @DisplayName("유저명을 잘못 입력한 로그인")
    void loginFail() {
        //given
        Member savedMember = memberRepository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        LoginRequest request = LoginRequest.builder()
                .username("틀린유저명")
                .password("1234")
                .rememberme(false)
                .build();

        //expected
        assertThatThrownBy(() ->
                authService.login(request))
                .isExactlyInstanceOf(AuthenticationFail.class);
    }

    @Test
    @DisplayName("비밀번호를 잘못 입력한 로그인")
    void loginFail2() {
        //given
        Member savedMember = memberRepository.save(Member.builder()
                .username("창근")
                .password(encoder.encode("1234"))
                .build());

        LoginRequest request = LoginRequest.builder()
                .username("창근")
                .password("틀린비밀번호")
                .rememberme(false)
                .build();

        //expected
        assertThatThrownBy(() ->
                authService.login(request))
                .isExactlyInstanceOf(AuthenticationFail.class);
    }

}