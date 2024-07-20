package dompoo.Ingrate.domain.enums;

import lombok.Getter;

@Getter
public enum Unit {
    GE("개"),
    GRAM("g"),
    MILILITER("mL"),
    DAN("단"),
    POGI("포기"),
    ;

    private final String name;

    Unit(String name) {
        this.name = name;
    }
}
