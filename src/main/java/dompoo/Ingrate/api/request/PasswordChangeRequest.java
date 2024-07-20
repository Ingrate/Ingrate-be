package dompoo.Ingrate.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PasswordChangeRequest {

    @NotEmpty(message = "새로운 비밀번호를 입력해주세요.")
    private String newPassword;

    @NotEmpty(message = "새로운 비밀번호를 다시 한번 입력해주세요.")
    private String newPasswordCheck;

    @Builder
    public PasswordChangeRequest(String newPassword, String newPasswordCheck) {
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }
}
