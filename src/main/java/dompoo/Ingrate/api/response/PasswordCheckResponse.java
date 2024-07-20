package dompoo.Ingrate.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordCheckResponse {

    private final Boolean isCorrect;

    public PasswordCheckResponse(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
