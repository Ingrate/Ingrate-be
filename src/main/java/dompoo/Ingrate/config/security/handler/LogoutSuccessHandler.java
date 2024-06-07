package dompoo.Ingrate.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dompoo.Ingrate.config.security.UserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Map<String, Object> data = new HashMap<>();

        data.put("username", principal.getUsername());
        data.put("memberId", principal.getMemberId());
        String json = objectMapper.writeValueAsString(data);

        log.info("[로그아웃 성공] 사용자명 : {}", principal.getUsername());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(SC_OK);
    }
}
