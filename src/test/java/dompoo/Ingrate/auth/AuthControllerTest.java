package dompoo.Ingrate.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import dompoo.Ingrate.auth.dto.LoginRequest;
import dompoo.Ingrate.member.Member;
import dompoo.Ingrate.member.MemberRepository;
import dompoo.Ingrate.member.dto.SignUpRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PasswordEncoder encoder;

    @AfterEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception {
        //given
        SignUpRequest request = SignUpRequest.builder()
                .username("Dompoo")
                .password("1234")
                .passwordCheck("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("Dompoo"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입시 사용자명은 필수이다.")
    void signUpFail1() throws Exception {
        //given
        SignUpRequest request = SignUpRequest.builder()
                .password("1234")
                .passwordCheck("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("사용자명을 입력해주세요."))
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입시 비밀번호는 필수이다.")
    void signUpFail2() throws Exception {
        //given
        SignUpRequest request = SignUpRequest.builder()
                .username("Dompoo")
                .passwordCheck("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("비밀번호를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입시 비밀번호 확인은 필수이다.")
    void signUpFail3() throws Exception {
        //given
        SignUpRequest request = SignUpRequest.builder()
                .username("Dompoo")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("비밀번호를 다시 한번 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입시 사용자명은 중복될 수 없다.")
    void signUpFail4() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        SignUpRequest request = SignUpRequest.builder()
                .username("Dompoo")
                .password("1234")
                .passwordCheck("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("이미 존재하는 사용자명입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입시 비밀번호와 비밀번호 확인은 일치해야 한다.")
    void signUpFail5() throws Exception {
        //given
        SignUpRequest request = SignUpRequest.builder()
                .username("Dompoo")
                .password("1234")
                .passwordCheck("5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("비밀번호 확인이 일치하지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        //given
        Member savedMember = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password(encoder.encode("1234"))
                .build());

        LoginRequest request = LoginRequest.builder()
                .username("Dompoo")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(savedMember.getId()))
                .andExpect(jsonPath("username").value("Dompoo"))
                .andExpect(jsonPath("accessToken").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인시 사용자명은 필수이다.")
    void loginFail1() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        LoginRequest request = LoginRequest.builder()
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("사용자명을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인시 비밀번호는 필수이다.")
    void loginFail2() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        LoginRequest request = LoginRequest.builder()
                .username("Dompoo")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("비밀번호를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 유저명으로 로그인")
    void loginFail3() throws Exception {
        //given
        LoginRequest request = LoginRequest.builder()
                .username("Dompoo")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("아이디 혹은 비밀번호가 올바르지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인")
    void loginFail4() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        LoginRequest request = LoginRequest.builder()
                .username("Dompoo")
                .password("12345")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("아이디 혹은 비밀번호가 올바르지 않습니다."))
                .andDo(print());
    }
}
