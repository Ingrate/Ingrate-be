package dompoo.Ingrate.member;

import dompoo.Ingrate.exception.PasswordCheckFail;
import dompoo.Ingrate.exception.PasswordCheckLock;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeoutService {

    public void checkFail(Member member) {
        Integer failedAttempts = member.getFailedAttempts();

        member.setFailedAttempts(++failedAttempts);

        //5번 실패할때마다 락을 건다.
        if (failedAttempts != 0 && failedAttempts % 5 == 0) {
            member.setLockTime(LocalDateTime.now().plusSeconds(failedAttempts * 6));
        }

        if (member.isAccountLocked()) {
            throw new PasswordCheckLock(remainLockTime(member.getLockTime()));
        } else {
            throw new PasswordCheckFail(failedAttempts);
        }
    }

    public void checkSuccess(Member member) {
        //아직 락이라면 예외를 던진다.
        if (member.isAccountLocked()) {
            throw new PasswordCheckLock(remainLockTime(member.getLockTime()));
        }

        member.setFailedAttempts(0);
    }

    private Long remainLockTime(LocalDateTime lockTime) {
        return Duration
                .between(LocalDateTime.now(), lockTime)
                .toSeconds();
    }
}
