package dompoo.Ingrate.member;

import dompoo.Ingrate.exception.PasswordCheckFail;
import dompoo.Ingrate.exception.PasswordCheckLock;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class TimeoutServiceTest {

    @Autowired TimeoutService timeoutService;
    @Autowired MemberRepository memberRepository;

    @AfterEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("checkFail 되었을 때, 실패 횟수가 1회 증가하고 Fail예외가 발생한다.")
    void fail1() {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        //when
        assertThatThrownBy(() ->
                        timeoutService.checkFail(member))
                .isInstanceOf(PasswordCheckFail.class);

        //then
        assertThat(member.getFailedAttempts()).isEqualTo(1);
    }

    @Test
    @DisplayName("연속으로 4번 checkFail 되었을 때, 다음에는 Timeout된다.")
    void fail2() {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        //when
        for (int i = 0; i < 4; i++) {
            assertThatThrownBy(() ->
                    timeoutService.checkFail(member))
                    .isInstanceOf(PasswordCheckFail.class);
        }

        //then
        assertThatThrownBy(() ->
                timeoutService.checkFail(member))
                .isInstanceOf(PasswordCheckLock.class);
        assertThat(member.getFailedAttempts()).isEqualTo(5);
    }

    @Test
    @DisplayName("check 성공하면, 실패 횟수가 초기화된다.")
    void failAndSuccess() {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("Dompoo")
                .password("1234")
                .build());

        member.setFailedAttempts(4);

        //when
        timeoutService.checkSuccess(member);

        //then
        assertThat(member.getFailedAttempts()).isEqualTo(0);
    }
}