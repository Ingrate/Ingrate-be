package dompoo.Ingrate.config.enums;

import lombok.Getter;

@Getter
public enum Unit {
    GRAM("g"), MILILITER("mL"), DAN("단");

    private final String name;

    Unit(String name) {
        this.name = name;
    }
}
