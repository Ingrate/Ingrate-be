package dompoo.Ingrate.service;

import dompoo.Ingrate.api.request.LoginRequest;
import dompoo.Ingrate.api.response.LoginResponse;
import dompoo.Ingrate.config.security.JwtTokenUtil;
import dompoo.Ingrate.api.exception.AlreadyExistUsername;
import dompoo.Ingrate.api.exception.AuthenticationFail;
import dompoo.Ingrate.api.exception.PasswordCheckIncorrect;
import dompoo.Ingrate.domain.Member;
import dompoo.Ingrate.service.repository.MemberRepository;
import dompoo.Ingrate.api.request.SignUpRequest;
import dompoo.Ingrate.api.response.SignUpResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new AlreadyExistUsername();
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordCheck())) {
            throw new PasswordCheckIncorrect();
        }

        Member member = memberRepository.save(Member.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build());

        return new SignUpResponse(member);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(AuthenticationFail::new);

        //비밀번호가 틀리면 예외 발생
        if (!encoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new AuthenticationFail();
        }

        String token = jwtTokenUtil.generateToken(member.getUsername(), loginRequest.getRememberme());

        return LoginResponse
                .builder()
                .id(member.getId())
                .username(member.getUsername())
                .accessToken(token)
                .build();
    }

}
