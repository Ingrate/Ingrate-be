package dompoo.Ingrate.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpRequest {

    @NotEmpty(message = "사용자명을 입력해주세요.")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotEmpty(message = "비밀번호를 다시 한번 입력해주세요.")
    private String passwordCheck;

    @Builder
    public SignUpRequest(String username, String password, String passwordCheck) {
        this.username = username;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
