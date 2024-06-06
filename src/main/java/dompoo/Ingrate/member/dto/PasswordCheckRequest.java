package dompoo.Ingrate.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PasswordCheckRequest {

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    public PasswordCheckRequest(String password) {
        this.password = password;
    }
}
