package dompoo.Ingrate.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest {
    private String username;
    private String password;

    @Override
    public String toString() {
        return ">>>>>>>>>>>>>>>>>>>>>>>>>> LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
