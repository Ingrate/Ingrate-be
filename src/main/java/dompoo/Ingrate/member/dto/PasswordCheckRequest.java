package dompoo.Ingrate.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PasswordCheckRequest {

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
}
