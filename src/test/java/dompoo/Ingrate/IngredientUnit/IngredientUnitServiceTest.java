package dompoo.Ingrate.IngredientUnit;

import dompoo.Ingrate.IngredientUnit.dto.UnitAddRequest;
import dompoo.Ingrate.IngredientUnit.dto.UnitResponse;
import dompoo.Ingrate.exception.AlreadyExistUnit;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static dompoo.Ingrate.config.enums.Unit.DAN;
import static dompoo.Ingrate.config.enums.Unit.GRAM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class IngredientUnitServiceTest {

    @Autowired private IngredientUnitService service;
    @Autowired private IngredientUnitRepository repository;

    @AfterEach
    void setUp() {
        repository.deleteAll();
    }
    
    @Test
    @DisplayName("식재료-단위 리스트 조회")
    void getList() {
        //given
        repository.save(IngredientUnit.builder()
                .name("파")
                .unit(DAN)
                .build());
        repository.save(IngredientUnit.builder()
                .name("파")
                .unit(GRAM)
                .build());
        repository.save(IngredientUnit.builder()
                .name("양파")
                .unit(GRAM)
                .build());

        //when
        List<UnitResponse> response = service.getUnitList();

        //then
        assertThat(response).hasSize(3);
        assertThat(response.get(0).getName()).isEqualTo("파");
        assertThat(response.get(0).getUnit()).isEqualTo("단");
        assertThat(response.get(1).getName()).isEqualTo("파");
        assertThat(response.get(1).getUnit()).isEqualTo("g");
        assertThat(response.get(2).getName()).isEqualTo("양파");
        assertThat(response.get(2).getUnit()).isEqualTo("g");
    }

    @Test
    @DisplayName("식재료-단위 리스트 존재 확인")
    void check() {
        //given
        repository.save(IngredientUnit.builder()
                .name("파")
                .unit(DAN)
                .build());
        repository.save(IngredientUnit.builder()
                .name("파")
                .unit(GRAM)
                .build());
        repository.save(IngredientUnit.builder()
                .name("양파")
                .unit(GRAM)
                .build());

        //when
        Boolean response = service.unitExistCheck("파", GRAM);

        //then
        assertThat(response).isTrue();
    }

    @Test
    @DisplayName("식재료-단위 리스트 존재하지 않음")
    void check2() {
        //given
        repository.save(IngredientUnit.builder()
                .name("파")
                .unit(DAN)
                .build());
        repository.save(IngredientUnit.builder()
                .name("파")
                .unit(GRAM)
                .build());
        repository.save(IngredientUnit.builder()
                .name("양파")
                .unit(GRAM)
                .build());

        //when
        Boolean response = service.unitExistCheck("양파", DAN);

        //then
        assertThat(response).isFalse();
    }

    @Test
    @DisplayName("식재료-단위 추가")
    void add() {
        //given
        UnitAddRequest request = UnitAddRequest.builder()
                .name("파")
                .unit("DAN")
                .build();

        //when
        UnitResponse response = service.addUnit(request);

        //then
        assertThat(response.getName()).isEqualTo("파");
        assertThat(response.getUnit()).isEqualTo("단");
    }

    @Test
    @DisplayName("이미 존재하는 식재료-단위 추가")
    void addFail() {
        //given
        repository.save(IngredientUnit.builder()
                .name("파")
                .unit(DAN)
                .build());

        UnitAddRequest request = UnitAddRequest.builder()
                .name("파")
                .unit("DAN")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.addUnit(request))
                .isExactlyInstanceOf(AlreadyExistUnit.class);
    }
}