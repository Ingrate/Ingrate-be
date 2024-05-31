package dompoo.Ingrate.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PasswordChangeRequest {

    private final String oldPassword;
    private final String newPassword;
    private final String newPasswordCheck;
}
