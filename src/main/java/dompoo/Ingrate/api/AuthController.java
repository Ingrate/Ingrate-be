package dompoo.Ingrate.api;

import dompoo.Ingrate.service.AuthService;
import dompoo.Ingrate.api.request.LoginRequest;
import dompoo.Ingrate.api.response.LoginResponse;
import dompoo.Ingrate.api.request.SignUpRequest;
import dompoo.Ingrate.api.response.SignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public SignUpResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
