package dompoo.Ingrate.config.enums;

import lombok.Getter;

@Getter
public enum Role {
    MEMBER("ROLE_MEMBER"), ADMIN("ROLE_ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
