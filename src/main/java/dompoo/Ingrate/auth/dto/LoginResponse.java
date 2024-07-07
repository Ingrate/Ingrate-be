package dompoo.Ingrate.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private final Long id;
    private final String username;
    private final String accessToken;

    @Builder
    public LoginResponse(Long id, String username, String accessToken) {
        this.id = id;
        this.username = username;
        this.accessToken = accessToken;
    }
}
