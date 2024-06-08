package dompoo.Ingrate.ingredient;

import com.fasterxml.jackson.databind.ObjectMapper;
import dompoo.Ingrate.config.WithMockMember;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static dompoo.Ingrate.config.enums.Role.ADMIN;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class IngredientControllerTest {

    @Autowired IngredientRepository ingredientRepository;
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("식재료 추가")
    @WithMockMember
    void addOne() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("비회원은 식재료 추가할 수 없다.")
    void addOneFail1() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("식재료 추가시 식재료명은 필수이다.")
    @WithMockMember
    void addOneFail2() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("식재료 추가시 가격은 필수이다.")
    @WithMockMember
    void addOneFail3() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("식재료 추가시 양은 필수이다.")
    @WithMockMember
    void addOneFail4() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("식재료 추가시 단위는 필수이다.")
    @WithMockMember
    void addOneFail5() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("존재하지 않는 회원이 식재료 추가 시도")
    @WithMockMember
    void addOneFail6() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("존재하지 않는 단위로 식재료 추가 시도")
    @WithMockMember
    void addOneFail7() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("내 식재료 리스트 조회")
    @WithMockMember
    void getMyList() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("비회원은 내 식재료 리스트 조회할 수 없다.")
    void getMyListFail1() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("내 식재료 상세 검색")
    @WithMockMember
    void getMyOne() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("비회원은 내 식재료 상세 검색할 수 없다.")
    void getMyOneFail1() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("존재하지 않는 내 식재료 상세 검색")
    @WithMockMember
    void getMyOneFail2() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("내 것이 아닌 식재료 상세 검색")
    @WithMockMember
    void getMyOneFail3() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("내 식재료 수정")
    @WithMockMember
    void editMy() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("비회원은 내 식재료 수정할 수 없다.")
    void editMyFail1() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("존재하지 않는 내 식재료 수정")
    @WithMockMember
    void editMyFail2() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("내 것이 아닌 식재료 수정")
    @WithMockMember
    void editMyFail3() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("내 식재료 삭제")
    @WithMockMember
    void deleteMy() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("비회원은 내 식재료 삭제할 수 없다.")
    void deleteMyFail1() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("존재하지 않는 내 식재료 삭제")
    @WithMockMember
    void deleteMyFail2() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("내 것이 아닌 식재료 삭제")
    @WithMockMember
    void deleteMyFail3() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("관리자 - 전체 식재료 검색")
    @WithMockMember(role = ADMIN)
    void getAll() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("일반 회원은 전체 식재료 검색할 수 없다.")
    @WithMockMember
    void getAllFail1() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("비회원은 전체 식재료 검색할 수 없다.")
    void getAllFail2() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("관리자 - 식재료 상세 검색")
    @WithMockMember(role = ADMIN)
    void getDetail() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("일반 회원은 식재료 상세 검색할 수 없다.")
    @WithMockMember
    void getDetailFail1() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("비회원은 식재료 상세 검색할 수 없다.")
    void getDetailFail2() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("존재하지 않는 식재료 상세 검색")
    @WithMockMember(role = ADMIN)
    void getDetailFail3() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("관리자 - 식재료 수정")
    @WithMockMember(role = ADMIN)
    void edit() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("일반 회원은 식재료 수정할 수 없다.")
    @WithMockMember
    void editFail1() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("비회원은 식재료 수정할 수 없다.")
    void editFail2() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("존재하지 않는 식재료 수정")
    @WithMockMember(role = ADMIN)
    void editFail3() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("존재하지 단위로 식재료 수정")
    @WithMockMember(role = ADMIN)
    void editFail4() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("관리자 - 식재료 삭제")
    @WithMockMember(role = ADMIN)
    void delete() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("일반 회원은 식재료 삭제할 수 없다.")
    @WithMockMember
    void deleteFail1() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("비회원은 식재료 삭제할 수 없다.")
    void deleteFail2() {
        //given


        //when

        //then

    }

    @Test
    @DisplayName("존재하지 않는 식재료 삭제")
    @WithMockMember(role = ADMIN)
    void deleteFail3() {
        //given


        //when

        //then

    }

}