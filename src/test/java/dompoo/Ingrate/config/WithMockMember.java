package dompoo.Ingrate.config;

import dompoo.Ingrate.config.enums.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

import static dompoo.Ingrate.config.enums.Role.MEMBER;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(
        factory = WithMockMemberSecurityContextFactory.class
)
public @interface WithMockMember {

    String username() default "창근";
    String password() default "1234";
    int posts() default 0;
    int point() default 5;
    Role role() default MEMBER;
}
