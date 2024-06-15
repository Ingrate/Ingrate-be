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

    public void manageTimeout(Member member) {
        member.failPasswordCheck();

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
}
