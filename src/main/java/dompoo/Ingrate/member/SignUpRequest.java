package dompoo.Ingrate.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpRequest {

    private final String username;
    private final String password;
    private final String passwordCheck;
}
