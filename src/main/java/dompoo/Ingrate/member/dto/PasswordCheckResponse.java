package dompoo.Ingrate.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordCheckResponse {

    private final Boolean isCorrect;
    private final Boolean isLocked;
    private final Integer failedAttempts;
    private final Long remainLockTime;

    @Builder
    public PasswordCheckResponse(Boolean isCorrect, Boolean isLocked, Integer failedAttempts, Long remainLockTime) {
        this.isCorrect = isCorrect;
        this.isLocked = isLocked;
        this.failedAttempts = failedAttempts;
        this.remainLockTime = remainLockTime;
    }
}
