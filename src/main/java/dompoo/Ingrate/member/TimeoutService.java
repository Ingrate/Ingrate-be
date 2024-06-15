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
        member.setFailedAttempts(member.getFailedAttempts() + 1);

        Integer failedAttempts = member.getFailedAttempts();
        if (failedAttempts != 0 && failedAttempts % 5 == 0) {
            member.setLockTime(LocalDateTime.now().plusSeconds(failedAttempts * 6));
        }

        if (member.isAccountLocked()) {
            Long remainLock = Duration
                    .between(LocalDateTime.now(), member.getLockTime())
                    .toSeconds();
            throw new PasswordCheckLock(remainLock);
        } else {
            throw new PasswordCheckFail(failedAttempts);
        }
    }

    public void checkSuccess(Member member) {
        member.setFailedAttempts(0);
    }
}
