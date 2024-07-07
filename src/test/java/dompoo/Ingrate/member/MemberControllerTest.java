package dompoo.Ingrate.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import dompoo.Ingrate.config.WithMockMember;
import dompoo.Ingrate.member.dto.PasswordChangeRequest;
import dompoo.Ingrate.member.dto.PasswordCheckRequest;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static dompoo.Ingrate.config.enums.Role.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @AfterEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("내 정보 보기")
    @WithMockMember
    void getMyInfo() throws Exception {
        //given

        //expected
        mockMvc.perform(get("/member"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("창근"))
                .andExpect(jsonPath("posts").value(0))
                .andExpect(jsonPath("point").value(5))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 내 정보를 볼 수 없다.")
    void getMyInfoFail1() throws Exception {
        //given

        //expected
        mockMvc.perform(get("/member"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 비밀번호 확인 - 성공")
    @WithMockMember
    void checkMyPassword() throws Exception {
        //given
        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("isCorrect").value(true))
                .andDo(print());
    }

    @Test
    @DisplayName("내 비밀번호 확인 - 실패")
    @WithMockMember
    void checkMyPassword2() throws Exception {
        //given
        int failedAttempts = memberRepository.findAll().getLast().getFailedAttempts() + 1;

        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("비밀번호가 일치하지 않습니다. (시도: " + failedAttempts + "회)"))
                .andDo(print());
    }

    @Test
    @DisplayName("내 비밀번호 확인 - 락")
    @WithMockMember
    void checkMyPasswordLock() throws Exception {
        //given
        memberRepository.findAll().getLast().setFailedAttempts(4);

        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value(Matchers.endsWith("초 후 시도해주세요.")))
                .andDo(print());

    }

    @Test
    @DisplayName("비회원은 내 비밀번호 확인할 수 없다.")
    void checkMyPasswordFail1() throws Exception {
        //given
        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 비밀번호 확인시 비밀번호는 필수이다.")
    @WithMockMember
    void checkMyPasswordFail2() throws Exception {
        //given
        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("비밀번호를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 비밀번호 변경")
    @WithMockMember
    void changeMyPassword() throws Exception {
        //given
        PasswordChangeRequest request = PasswordChangeRequest.builder()
                .newPassword("5678")
                .newPasswordCheck("5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("창근"))
                .andExpect(jsonPath("posts").value(0))
                .andExpect(jsonPath("point").value(5))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 내 비밀번호 변경할 수 없다.")
    void changeMyPasswordFail1() throws Exception {
        //given
        PasswordChangeRequest request = PasswordChangeRequest.builder()
                .newPassword("5678")
                .newPasswordCheck("5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 비밀번호 변경시 새로운 비밀번호는 필수이다.")
    @WithMockMember
    void changeMyPasswordFail3() throws Exception {
        //given
        PasswordChangeRequest request = PasswordChangeRequest.builder()
                .newPasswordCheck("5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("새로운 비밀번호를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 비밀번호 변경시 새로운 비밀번호 확인은 필수이다.")
    @WithMockMember
    void changeMyPasswordFail4() throws Exception {
        //given
        PasswordChangeRequest request = PasswordChangeRequest.builder()
                .newPassword("5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("새로운 비밀번호를 다시 한번 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 비밀번호 변경시 새로운 비밀번호와 비밀번호 확인은 일치해야 한다.")
    @WithMockMember
    void changeMyPasswordFail6() throws Exception {
        //given
        PasswordChangeRequest request = PasswordChangeRequest.builder()
                .newPassword("5678")
                .newPasswordCheck("qwer")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("비밀번호 확인이 일치하지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("회원탈퇴 - 성공")
    @WithMockMember
    void withDraw1() throws Exception {
        //given
        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(delete("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("withdrawalSuccess").value(true))
                .andDo(print());
    }

    @Test
    @DisplayName("회원탈퇴 - 실패")
    @WithMockMember
    void withDraw2() throws Exception {
        //given
        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .password("5678")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(delete("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("withdrawalSuccess").value(false))
                .andDo(print());
    }

    @Test
    @DisplayName("회원탈퇴시 비밀번호는 필수이다.")
    @WithMockMember
    void withDrawFail1() throws Exception {
        //given
        PasswordCheckRequest request = PasswordCheckRequest.builder()
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(delete("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(400))
                .andExpect(jsonPath("message").value("비밀번호를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 - 회원 전체 조회")
    @WithMockMember(role = ADMIN)
    void getMemberList() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        memberRepository.save(Member.builder()
                .username("아영")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(get("/manage/member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("창근"))
                .andExpect(jsonPath("$[1].username").value("Dompoo"))
                .andExpect(jsonPath("$[2].username").value("아영"))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 회원 전체 조회할 수 없다.")
    void getMemberListFail1() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        memberRepository.save(Member.builder()
                .username("아영")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(get("/manage/member"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("일반회원은 회원 전체 조회할 수 없다.")
    @WithMockMember
    void getMemberListFail2() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        memberRepository.save(Member.builder()
                .username("아영")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(get("/manage/member"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("code").value("403"))
                .andExpect(jsonPath("message").value("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 - 회원 상세 조회")
    @WithMockMember(role = ADMIN)
    void getMemberDetail() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(get("/manage/member/{memberId}", member.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("Dompoo"))
                .andExpect(jsonPath("posts").value(0))
                .andExpect(jsonPath("point").value(5))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 회원 상세 조회할 수 없다.")
    void getMemberDetailFail1() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(get("/manage/member/{memberId}", member.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("일반회원은 회원 상세 조회할 수 없다.")
    @WithMockMember
    void getMemberDetailFail2() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(get("/manage/member/{memberId}", member.getId()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("code").value("403"))
                .andExpect(jsonPath("message").value("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 회원 상세 조회")
    @WithMockMember(role = ADMIN)
    void getMemberDetailFail3() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(get("/manage/member/{memberId}", member.getId() + 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("존재하지 않는 회원입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 - 회원 삭제")
    @WithMockMember(role = ADMIN)
    void deleteOne() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(delete("/manage/member/{memberId}", member.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 회원 삭제할 수 없다.")
    void deleteOneFail1() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(delete("/manage/member/{memberId}", member.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("일반회원은 회원 삭제할 수 없다.")
    @WithMockMember
    void deleteOneFail2() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        //expected
        mockMvc.perform(delete("/manage/member/{memberId}", member.getId()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("code").value("403"))
                .andExpect(jsonPath("message").value("권한이 없습니다."))
                .andDo(print());
    }
}