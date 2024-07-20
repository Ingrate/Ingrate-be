package dompoo.Ingrate.service;

import dompoo.Ingrate.domain.Member;
import dompoo.Ingrate.api.exception.PasswordCheckFail;
import dompoo.Ingrate.api.exception.PasswordCheckLock;
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
        if (failedAttempts % 5 == 0) {
            member.setLockTime(LocalDateTime.now().plusSeconds(calculatePlusSeconds(failedAttempts)));
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

    private int calculatePlusSeconds(int failedAttempts) {
        //5번    2^1 * 15초 = 30초
        //10번   2^2 * 15초 = 60초
        //15번   2^3 * 15초 = 120초
        //20번   2^4 * 15초 = 240초
        //...

        int power = failedAttempts / 5;
        return (int) Math.pow(2, power) * 15;
    }
}
