package dompoo.Ingrate.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PasswordChangeRequest {

    @NotEmpty(message = "기존 비밀번호를 입력해주세요.")
    private String oldPassword;

    @NotEmpty(message = "새로운 비밀번호를 입력해주세요.")
    private String newPassword;

    @NotEmpty(message = "새로운 비밀번호를 다시 한번 입력해주세요.")
    private String newPasswordCheck;
}
