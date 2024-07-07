package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.IngredientUnit.IngredientUnit;
import dompoo.Ingrate.IngredientUnit.IngredientUnitRepository;
import dompoo.Ingrate.exception.IngredientNotFound;
import dompoo.Ingrate.exception.MemberNotFound;
import dompoo.Ingrate.exception.NotMyIngredient;
import dompoo.Ingrate.exception.UnitNotFound;
import dompoo.Ingrate.ingredient.dto.*;
import dompoo.Ingrate.member.Member;
import dompoo.Ingrate.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static dompoo.Ingrate.config.enums.Unit.DAN;
import static dompoo.Ingrate.config.enums.Unit.GRAM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class IngredientServiceTest {

    @Autowired private IngredientService service;
    @Autowired private IngredientRepository repository;
    @Autowired private IngredientUnitRepository unitRepository;
    @Autowired private MemberRepository memberRepository;

    private Member me;
    private Member other;

    @BeforeEach
    void setUpBefore() {
        unitRepository.save(IngredientUnit.builder()
                .name("파")
                .unit(DAN)
                .build());
        unitRepository.save(IngredientUnit.builder()
                .name("파")
                .unit(GRAM)
                .build());
        me = memberRepository.save(Member.builder()
                .username("창근")
                .password("1234")
                .build());
        other = memberRepository.save(Member.builder()
                .username("아영")
                .password("1234")
                .build());
    }

    @AfterEach
    void setUp() {
        repository.deleteAll();
        unitRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("식재료 추가")
    void addOne() {
        //given
        IngredientAddRequest request = IngredientAddRequest.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .enumUnit("DAN")
                .memo("GS더프레시")
                .build();

        //when
        service.addIngredient(me.getId(), request);

        //then
        Ingredient ingredient = repository.findAll().getFirst();
        assertThat(ingredient.getName()).isEqualTo("파");
        assertThat(ingredient.getCost()).isEqualTo(1200F);
        assertThat(ingredient.getAmount()).isEqualTo(1F);
        assertThat(ingredient.getUnit()).isEqualTo(DAN);
        assertThat(ingredient.getMemo()).isEqualTo("GS더프레시");
        assertThat(ingredient.getMember().getId()).isEqualTo(me.getId());
    }

    @Test
    @DisplayName("존재하지 않는 회원의 식재료 추가 실패")
    void addOneFail1() {
        //given
        IngredientAddRequest request = IngredientAddRequest.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .enumUnit("DAN")
                .memo("GS더프레시")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.addIngredient(other.getId() + 1, request))
                .isExactlyInstanceOf(MemberNotFound.class);
    }

    @Test
    @DisplayName("존재하지 않는 단위로 식재료 추가 실패")
    void addOneFail2() {
        //given
        IngredientAddRequest request = IngredientAddRequest.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .enumUnit("MILILITER")
                .memo("GS더프레시")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.addIngredient(me.getId(), request))
                .isExactlyInstanceOf(UnitNotFound.class);
    }

    @Test
    @DisplayName("내 식재료 리스트 검색")
    void getMyList() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(me)
                .build());

        Ingredient in2 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .memo("쿠팡")
                .date(LocalDate.now())
                .member(me)
                .build());

        Ingredient in3 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1400F)
                .amount(300F)
                .unit(GRAM)
                .memo("컬리")
                .date(LocalDate.now())
                .member(other)
                .build());

        //when
        List<IngredientResponse> response = service.getMyIngredient(me.getId());

        //then
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getName()).isEqualTo("파");
        assertThat(response.get(0).getCost()).isEqualTo(1200F);
        assertThat(response.get(0).getAmount()).isEqualTo(1F);
        assertThat(response.get(0).getUnit()).isEqualTo("단");
        assertThat(response.get(1).getName()).isEqualTo("파");
        assertThat(response.get(1).getCost()).isEqualTo(1300F);
        assertThat(response.get(1).getAmount()).isEqualTo(300F);
        assertThat(response.get(1).getUnit()).isEqualTo("g");
    }

    @Test
    @DisplayName("내 식재료 상세 검새")
    void getMyDetail() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(me)
                .build());

        //when
        IngredientDetailResponse response = service.getMyIngredientDetail(me.getId(), in1.getId());

        //then
        assertThat(response.getName()).isEqualTo("파");
        assertThat(response.getCost()).isEqualTo(1200F);
        assertThat(response.getAmount()).isEqualTo(1F);
        assertThat(response.getUnit()).isEqualTo("단");
        assertThat(response.getMemo()).isEqualTo("GS더프레시");
    }

    @Test
    @DisplayName("존재하지 않는 식재료 상세 검색 실패")
    void getMyDetailFail1() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(me)
                .build());

        //when
        assertThatThrownBy(() ->
                service.getMyIngredientDetail(me.getId(), in1.getId() + 1))
                .isExactlyInstanceOf(IngredientNotFound.class);
    }

    @Test
    @DisplayName("다른 회원의 식재료 상세 검색 실패")
    void getMyDetailFail2() {
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        //when
        assertThatThrownBy(() ->
                service.getMyIngredientDetail(me.getId(), in1.getId()))
                .isExactlyInstanceOf(NotMyIngredient.class);
    }

    @Test
    @DisplayName("내 식재료 수정")
    void editMy() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(me)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .enumUnit("GRAM")
                .memo("쿠팡")
                .build();

        //when
        IngredientDetailResponse response = service.editMyIngredient(me.getId(), in1.getId(), request);

        //then
        assertThat(response.getName()).isEqualTo("파");
        assertThat(response.getCost()).isEqualTo(1300F);
        assertThat(response.getAmount()).isEqualTo(300F);
        assertThat(response.getUnit()).isEqualTo("g");
        assertThat(response.getMemo()).isEqualTo("쿠팡");
        Ingredient find = repository.findById(in1.getId()).orElseThrow();
        assertThat(find.getName()).isEqualTo("파");
        assertThat(find.getCost()).isEqualTo(1300F);
        assertThat(find.getAmount()).isEqualTo(300F);
        assertThat(find.getUnit()).isEqualTo(GRAM);
        assertThat(find.getMemo()).isEqualTo("쿠팡");
    }

    @Test
    @DisplayName("존재하지 않는 식재료 수정 실패")
    void editMyFail1() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(me)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .enumUnit("GRAM")
                .memo("쿠팡")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.editMyIngredient(me.getId(), in1.getId() + 1, request))
                .isExactlyInstanceOf(IngredientNotFound.class);
    }

    @Test
    @DisplayName("다른 회원의 식재료 수정 실패")
    void editMyFail2() {
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .enumUnit("GRAM")
                .memo("쿠팡")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.editMyIngredient(me.getId(), in1.getId(), request))
                .isExactlyInstanceOf(NotMyIngredient.class);
    }

    @Test
    @DisplayName("존재하지 않는 식재료 단위로 수정 실패")
    void editMyFail3() {
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(me)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .enumUnit("MILILITER")
                .memo("쿠팡")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.editMyIngredient(me.getId(), in1.getId(), request))
                .isExactlyInstanceOf(UnitNotFound.class);
    }

    @Test
    @DisplayName("내 식재료 삭제")
    void deleteMy() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(me)
                .build());

        //when
        service.deleteMyIngredient(me.getId(), in1.getId());

        //then
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("내 존재하지 않는 식재료 삭제 실패")
    void deleteMyFail1() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(me)
                .build());

        //expected
        assertThatThrownBy(() ->
                service.deleteMyIngredient(me.getId(), in1.getId() + 1))
                .isExactlyInstanceOf(IngredientNotFound.class);
    }

    @Test
    @DisplayName("다른 회원의 식재료 삭제 실패")
    void deleteMyFail2() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        //expected
        assertThatThrownBy(() ->
                service.deleteMyIngredient(me.getId(), in1.getId()))
                .isExactlyInstanceOf(NotMyIngredient.class);
    }

    @Test
    @DisplayName("관리자 - 식재료 리스트 검색")
    void findAll() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(me)
                .build());

        Ingredient in2 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .memo("쿠팡")
                .date(LocalDate.now())
                .member(me)
                .build());

        Ingredient in3 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1400F)
                .amount(300F)
                .unit(GRAM)
                .memo("컬리")
                .date(LocalDate.now())
                .member(other)
                .build());

        //when
        List<IngredientResponse> response = service.getAllIngredient();

        //then
        assertThat(response).hasSize(3);
        assertThat(response.get(0).getName()).isEqualTo("파");
        assertThat(response.get(0).getCost()).isEqualTo(1200F);
        assertThat(response.get(0).getAmount()).isEqualTo(1F);
        assertThat(response.get(0).getUnit()).isEqualTo("단");
        assertThat(response.get(1).getName()).isEqualTo("파");
        assertThat(response.get(1).getCost()).isEqualTo(1300F);
        assertThat(response.get(1).getAmount()).isEqualTo(300F);
        assertThat(response.get(1).getUnit()).isEqualTo("g");
        assertThat(response.get(2).getName()).isEqualTo("파");
        assertThat(response.get(2).getCost()).isEqualTo(1400F);
        assertThat(response.get(2).getAmount()).isEqualTo(300F);
        assertThat(response.get(2).getUnit()).isEqualTo("g");
    }

    @Test
    @DisplayName("관리자 - 식재료 상세 검색")
    void getOne() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        //when
        IngredientAdminDetailResponse response = service.getIngredientDetail(in1.getId());

        //then
        assertThat(response.getName()).isEqualTo("파");
        assertThat(response.getCost()).isEqualTo(1200F);
        assertThat(response.getAmount()).isEqualTo(1F);
        assertThat(response.getUnit()).isEqualTo("단");
        assertThat(response.getMemo()).isEqualTo("GS더프레시");
        assertThat(response.getDate()).isEqualTo(in1.getDate());
        assertThat(response.getMemberId()).isEqualTo(other.getId());
    }

    @Test
    @DisplayName("관리자 - 존재하지 않는 식재료 상세 검색 실패")
    void getOneFail() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        //expected
        assertThatThrownBy(() ->
                service.getIngredientDetail(in1.getId() + 1))
                .isExactlyInstanceOf(IngredientNotFound.class);
    }

    @Test
    @DisplayName("관리자 - 식재료 수정")
    void edit() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .enumUnit("GRAM")
                .memo("쿠팡")
                .build();

        //when
        IngredientAdminDetailResponse response = service.editIngredient(in1.getId(), request);

        //then
        assertThat(response.getName()).isEqualTo("파");
        assertThat(response.getCost()).isEqualTo(1300F);
        assertThat(response.getAmount()).isEqualTo(300F);
        assertThat(response.getUnit()).isEqualTo("g");
        assertThat(response.getMemo()).isEqualTo("쿠팡");
        assertThat(response.getMemberId()).isEqualTo(other.getId());
        Ingredient find = repository.findById(in1.getId()).orElseThrow();
        assertThat(find.getName()).isEqualTo("파");
        assertThat(find.getCost()).isEqualTo(1300F);
        assertThat(find.getAmount()).isEqualTo(300F);
        assertThat(find.getUnit()).isEqualTo(GRAM);
        assertThat(find.getMemo()).isEqualTo("쿠팡");
        assertThat(response.getMemberId()).isEqualTo(other.getId());
    }

    @Test
    @DisplayName("관리자 - 존재하지 않는 식재료 수정 실패")
    void editFail1() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .enumUnit("GRAM")
                .memo("쿠팡")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.editIngredient(in1.getId() + 1, request))
                .isExactlyInstanceOf(IngredientNotFound.class);

    }

    @Test
    @DisplayName("관리자 - 존재하지 않는 식재료 단위로 수정 실패")
    void editFail2() {
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .enumUnit("MILILITER")
                .memo("쿠팡")
                .build();

        //expected
        assertThatThrownBy(() ->
                service.editIngredient(in1.getId(), request))
                .isExactlyInstanceOf(UnitNotFound.class);
    }

    @Test
    @DisplayName("관리자 - 식재료 삭제")
    void delete() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        //when
        service.deleteIngredient(in1.getId());

        //then
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("관리자 - 존재하지 않는 식재료 삭제 실패")
    void deleteFail1() {
        //given
        Ingredient in1 = repository.save(Ingredient.builder()
                .name("파")
                .cost(1200F)
                .amount(1F)
                .unit(DAN)
                .memo("GS더프레시")
                .date(LocalDate.now())
                .member(other)
                .build());

        //expected
        assertThatThrownBy(() ->
                service.deleteIngredient(in1.getId() + 1))
                .isExactlyInstanceOf(IngredientNotFound.class);
    }


}