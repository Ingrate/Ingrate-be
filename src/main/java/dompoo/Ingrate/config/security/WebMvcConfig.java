package dompoo.Ingrate.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("http://localhost:5173") // 허용할 오리진(프론트엔드 서버 주소)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드 설정
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true) // 쿠키를 포함한 요청 허용
                .maxAge(3600); // pre-flight 요청의 캐시 시간(초)
    }
}
