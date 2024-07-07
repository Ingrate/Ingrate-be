package dompoo.Ingrate.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "사용자명을 입력해주세요.")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    private Boolean rememberme = false;

    @Builder
    public LoginRequest(String username, String password, Boolean rememberme) {
        this.username = username;
        this.password = password;
        if (rememberme != null) this.rememberme = rememberme;
    }
}
