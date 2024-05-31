package dompoo.Ingrate.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PasswordCheckResponse {

    private final Boolean isCorrect;
}
