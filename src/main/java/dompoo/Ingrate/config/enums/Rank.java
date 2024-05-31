package dompoo.Ingrate.config.enums;

import lombok.Getter;

public class Rank {

    public static String getRank(Integer posts, Integer point) {
        int check = posts + point;

        if (check < 10) {
            return Rank1.getRandom().getName();
        } else if (check < 20) {
            return Rank2.getRandom().getName();
        } else {
            return Rank3.getRandom().getName();
        }
    }

    @Getter
    public enum Rank1 {
        chobo("마트 초보자"), ;

        private final String name;

        Rank1(String name) {
            this.name = name;
        }

        public static Rank1 getRandom() {
            return Rank1.values()[(int) (Math.random() * Rank1.values().length)];
        }
    }

    @Getter
    public enum Rank2 {
        joongsu("마트 중수"), ;

        private final String name;

        Rank2(String name) {
            this.name = name;
        }

        public static Rank2 getRandom() {
            return Rank2.values()[(int) (Math.random() * Rank2.values().length)];
        }
    }

    @Getter
    public enum Rank3 {
        hwasin("마트의화신"), ;

        private final String name;

        Rank3(String name) {
            this.name = name;
        }

        public static Rank3 getRandom() {
            return Rank3.values()[(int) (Math.random() * Rank3.values().length)];
        }
    }
}
