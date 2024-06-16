package dompoo.Ingrate;

import dompoo.Ingrate.IngredientUnit.IngredientUnit;
import dompoo.Ingrate.IngredientUnit.IngredientUnitRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

import static dompoo.Ingrate.config.enums.Unit.*;

@Component
@Profile("local")
@RequiredArgsConstructor
public class UnitInit {

    private final IngredientUnitRepository repository;

    @PostConstruct
    public void init() {
        repository.saveAll(List.of(
                IngredientUnit.builder().name("대파").unit(GRAM).build(),
                IngredientUnit.builder().name("양파").unit(GRAM).build(),
                IngredientUnit.builder().name("쪽파").unit(GRAM).build(),
                IngredientUnit.builder().name("소금").unit(GRAM).build(),
                IngredientUnit.builder().name("설탕").unit(GRAM).build(),
                IngredientUnit.builder().name("마늘").unit(GRAM).build(),
                IngredientUnit.builder().name("다진마늘").unit(GRAM).build(),
                IngredientUnit.builder().name("당근").unit(GRAM).build(),
                IngredientUnit.builder().name("감자").unit(GRAM).build(),
                IngredientUnit.builder().name("고구마").unit(GRAM).build(),
                IngredientUnit.builder().name("느타리버섯").unit(GRAM).build(),
                IngredientUnit.builder().name("양송이버섯").unit(GRAM).build(),
                IngredientUnit.builder().name("새송이버섯").unit(GRAM).build(),
                IngredientUnit.builder().name("표고버섯").unit(GRAM).build(),
                IngredientUnit.builder().name("팽이버섯").unit(GRAM).build(),
                IngredientUnit.builder().name("청경채").unit(GRAM).build(),
                IngredientUnit.builder().name("청양고추").unit(GRAM).build(),
                IngredientUnit.builder().name("부추").unit(GRAM).build(),
                IngredientUnit.builder().name("두부").unit(GRAM).build(),
                IngredientUnit.builder().name("순두부").unit(GRAM).build(),
                IngredientUnit.builder().name("숙주나물").unit(GRAM).build(),
                IngredientUnit.builder().name("콩나물").unit(GRAM).build(),
                IngredientUnit.builder().name("방울토마토").unit(GRAM).build(),
                IngredientUnit.builder().name("포도").unit(GRAM).build(),
                IngredientUnit.builder().name("블루베리").unit(GRAM).build(),
                IngredientUnit.builder().name("귤").unit(GRAM).build(),
                IngredientUnit.builder().name("고추장").unit(GRAM).build(),
                IngredientUnit.builder().name("된장").unit(GRAM).build(),
                IngredientUnit.builder().name("쌈장").unit(GRAM).build(),
                IngredientUnit.builder().name("진간장").unit(GRAM).build(),
                IngredientUnit.builder().name("양조간장").unit(GRAM).build(),
                IngredientUnit.builder().name("국간장").unit(GRAM).build(),
                IngredientUnit.builder().name("미림").unit(GRAM).build(),
                IngredientUnit.builder().name("식용유").unit(GRAM).build(),
                IngredientUnit.builder().name("올리브유").unit(GRAM).build(),
                IngredientUnit.builder().name("올리고당").unit(GRAM).build(),

                IngredientUnit.builder().name("계란").unit(GE).build(),
                IngredientUnit.builder().name("양상추").unit(GE).build(),
                IngredientUnit.builder().name("애호박").unit(GE).build(),
                IngredientUnit.builder().name("파프리카").unit(GE).build(),
                IngredientUnit.builder().name("배추").unit(GE).build(),
                IngredientUnit.builder().name("양배추").unit(GE).build(),
                IngredientUnit.builder().name("오이").unit(GE).build(),
                IngredientUnit.builder().name("가지").unit(GE).build(),
                IngredientUnit.builder().name("무").unit(GE).build(),
                IngredientUnit.builder().name("옥수수").unit(GE).build(),
                IngredientUnit.builder().name("참외").unit(GE).build(),
                IngredientUnit.builder().name("토마토").unit(GE).build(),
                IngredientUnit.builder().name("키위").unit(GE).build(),
                IngredientUnit.builder().name("메론").unit(GE).build(),
                IngredientUnit.builder().name("포도").unit(GE).build(),
                IngredientUnit.builder().name("배").unit(GE).build(),
                IngredientUnit.builder().name("사과").unit(GE).build(),
                IngredientUnit.builder().name("복숭아").unit(GE).build(),
                IngredientUnit.builder().name("감").unit(GE).build(),
                IngredientUnit.builder().name("바나나").unit(GE).build(),

                IngredientUnit.builder().name("진간장").unit(MILILITER).build(),
                IngredientUnit.builder().name("양조간장").unit(MILILITER).build(),
                IngredientUnit.builder().name("국간장").unit(MILILITER).build(),
                IngredientUnit.builder().name("미림").unit(MILILITER).build(),
                IngredientUnit.builder().name("식용유").unit(MILILITER).build(),
                IngredientUnit.builder().name("올리브유").unit(MILILITER).build(),
                IngredientUnit.builder().name("올리고당").unit(MILILITER).build(),

                IngredientUnit.builder().name("대파").unit(DAN).build()
        ));
    }
}
