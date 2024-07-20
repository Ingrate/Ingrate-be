package dompoo.Ingrate.api.exception;

public class UsernameNotFoundException extends org.springframework.security.core.userdetails.UsernameNotFoundException {

    public UsernameNotFoundException(String username) {
        super("[인증오류] " + username + "을 찾을 수 없습니다.");
    }
}
