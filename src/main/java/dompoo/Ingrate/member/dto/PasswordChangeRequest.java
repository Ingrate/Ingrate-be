package dompoo.Ingrate.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PasswordChangeRequest {

    private String oldPassword;
    private String newPassword;
    private String newPasswordCheck;
}
