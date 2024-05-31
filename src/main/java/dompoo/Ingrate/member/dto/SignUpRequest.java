package dompoo.Ingrate.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpRequest {

    private String username;
    private String password;
    private String passwordCheck;
}
