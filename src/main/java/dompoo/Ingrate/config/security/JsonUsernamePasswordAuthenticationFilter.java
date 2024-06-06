package dompoo.Ingrate.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePassword json = objectMapper.readValue(request.getInputStream(), UsernamePassword.class);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(json.username, json.password);

        setDetails(request, token);

        return this.getAuthenticationManager().authenticate(token);
    }

    @Getter
    private static class UsernamePassword {
        private String username;
        private String password;
    }
}
