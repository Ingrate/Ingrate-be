package dompoo.Ingrate.ingredient;

import com.fasterxml.jackson.databind.ObjectMapper;
import dompoo.Ingrate.IngredientUnit.IngredientUnit;
import dompoo.Ingrate.IngredientUnit.IngredientUnitRepository;
import dompoo.Ingrate.config.WithMockMember;
import dompoo.Ingrate.ingredient.dto.IngredientAddRequest;
import dompoo.Ingrate.ingredient.dto.IngredientEditRequest;
import dompoo.Ingrate.member.Member;
import dompoo.Ingrate.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static dompoo.Ingrate.config.enums.Role.ADMIN;
import static dompoo.Ingrate.config.enums.Unit.DAN;
import static dompoo.Ingrate.config.enums.Unit.GRAM;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class IngredientControllerTest {

    @Autowired private IngredientRepository ingredientRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private IngredientUnitRepository unitRepository;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void setUpAll() {
        unitRepository.save(IngredientUnit.builder()
                .name("파")
                .unit(DAN)
                .build());
        unitRepository.save(IngredientUnit.builder()
                .name("파")
                .unit(GRAM)
                .build());
    }

    @AfterEach
    void setUp() {
        ingredientRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("식재료 추가")
    @WithMockMember
    void addOne() throws Exception {
        //given
        IngredientAddRequest request = IngredientAddRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 식재료 추가할 수 없다.")
    void addOneFail1() throws Exception {
        //given
        IngredientAddRequest request = IngredientAddRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료 추가시 식재료명은 필수이다.")
    @WithMockMember
    void addOneFail2() throws Exception {
        IngredientAddRequest request = IngredientAddRequest.builder()
                .cost(1300F)
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("식재료명을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료 추가시 가격은 필수이다.")
    @WithMockMember
    void addOneFail3() throws Exception {
        //given
        IngredientAddRequest request = IngredientAddRequest.builder()
                .name("파")
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("가격을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료 추가시 양은 필수이다.")
    @WithMockMember
    void addOneFail4() throws Exception {
        //given
        IngredientAddRequest request = IngredientAddRequest.builder()
                .name("파")
                .cost(1300F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("양을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료 추가시 단위는 필수이다.")
    @WithMockMember
    void addOneFail5() throws Exception {
        IngredientAddRequest request = IngredientAddRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("단위를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 단위로 식재료 추가 시도")
    @WithMockMember
    void addOneFail7() throws Exception {
        IngredientAddRequest request = IngredientAddRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit("MILILITER")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("존재하지 않는 단위입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 식재료 리스트 조회")
    @WithMockMember
    void getMyList() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(me)
                .build());

        //expected
        mockMvc.perform(get("/ingredient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("파"))
                .andExpect(jsonPath("$[0].cost").value(1300F))
                .andExpect(jsonPath("$[0].amount").value(1F))
                .andExpect(jsonPath("$[0].unit").value("단"))
                .andExpect(jsonPath("$[1].name").value("파"))
                .andExpect(jsonPath("$[1].cost").value(1300F))
                .andExpect(jsonPath("$[1].amount").value(300F))
                .andExpect(jsonPath("$[1].unit").value("g"))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 내 식재료 리스트 조회할 수 없다.")
    void getMyListFail1() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(other)
                .build());

        //expected
        mockMvc.perform(get("/ingredient"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 식재료 상세 검색")
    @WithMockMember
    void getMyOne() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        //expected
        mockMvc.perform(get("/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("파"))
                .andExpect(jsonPath("$.cost").value(1300F))
                .andExpect(jsonPath("$.amount").value(1F))
                .andExpect(jsonPath("$.unit").value("단"))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 내 식재료 상세 검색할 수 없다.")
    void getMyOneFail1() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(other)
                .build());

        //expected
        mockMvc.perform(get("/ingredient/{ingredientId}", other.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 내 식재료 상세 검색")
    @WithMockMember
    void getMyOneFail2() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        //expected
        mockMvc.perform(get("/ingredient/{ingredientId}", in.getId() + 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("존재하지 않는 식재료입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 것이 아닌 식재료 상세 검색")
    @WithMockMember
    void getMyOneFail3() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(other)
                .build());

        //expected
        mockMvc.perform(get("/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("내 식재료가 아닙니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 식재료 수정")
    @WithMockMember
    void editMy() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("파"))
                .andExpect(jsonPath("$.cost").value(1300F))
                .andExpect(jsonPath("$.amount").value(300F))
                .andExpect(jsonPath("$.unit").value("g"))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 내 식재료 수정할 수 없다.")
    void editMyFail1() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 내 식재료 수정")
    @WithMockMember
    void editMyFail2() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/ingredient/{ingredientId}", in.getId() + 1)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("존재하지 않는 식재료입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 것이 아닌 식재료 수정")
    @WithMockMember
    void editMyFail3() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("내 식재료가 아닙니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료 수정시 식재료명은 필수이다.")
    @WithMockMember
    void editMyFail4() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .cost(1300F)
                .amount(300F)
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("식재료명을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료 수정시 가격은 필수이다.")
    @WithMockMember
    void editMyFail5() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .amount(300F)
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("가격을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료 수정시 양은 필수이다.")
    @WithMockMember
    void editMyFail6() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("양을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료 수정시 단위는 필수이다.")
    @WithMockMember
    void editMyFail7() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("단위를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 식재료 삭제")
    @WithMockMember
    void deleteMy() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        //expected
        mockMvc.perform(delete("/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 내 식재료 삭제할 수 없다.")
    void deleteMyFail1() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(other)
                .build());

        //expected
        mockMvc.perform(delete("/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 내 식재료 삭제")
    @WithMockMember
    void deleteMyFail2() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        //expected
        mockMvc.perform(delete("/ingredient/{ingredientId}", in.getId() + 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("존재하지 않는 식재료입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("내 것이 아닌 식재료 삭제")
    @WithMockMember
    void deleteMyFail3() throws Exception {
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(other)
                .build());

        //expected
        mockMvc.perform(delete("/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("내 식재료가 아닙니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 - 전체 식재료 검색")
    @WithMockMember(role = ADMIN)
    void getAll() throws Exception {
        //given
        Member me = memberRepository.findAll().getFirst();
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit(DAN)
                .member(me)
                .build());

        ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        //expected
        mockMvc.perform(get("/manage/ingredient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("파"))
                .andExpect(jsonPath("$[0].cost").value(1300F))
                .andExpect(jsonPath("$[0].amount").value(1F))
                .andExpect(jsonPath("$[0].unit").value("단"))
                .andExpect(jsonPath("$[1].name").value("파"))
                .andExpect(jsonPath("$[1].cost").value(1300F))
                .andExpect(jsonPath("$[1].amount").value(300F))
                .andExpect(jsonPath("$[1].unit").value("g"))
                .andDo(print());
    }

    @Test
    @DisplayName("일반 회원은 전체 식재료 검색할 수 없다.")
    @WithMockMember
    void getAllFail1() throws Exception {
        //given

        //expected
        mockMvc.perform(get("/manage/ingredient"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("code").value("403"))
                .andExpect(jsonPath("message").value("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 전체 식재료 검색할 수 없다.")
    void getAllFail2() throws Exception {
        //given

        //expected
        mockMvc.perform(get("/manage/ingredient"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 - 식재료 상세 검색")
    @WithMockMember(role = ADMIN)
    void getDetail() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        //expected
        mockMvc.perform(get("/manage/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("파"))
                .andExpect(jsonPath("$.cost").value(1300F))
                .andExpect(jsonPath("$.amount").value(300F))
                .andExpect(jsonPath("$.unit").value("g"))
                .andDo(print());
    }

    @Test
    @DisplayName("일반 회원은 식재료 상세 검색할 수 없다.")
    @WithMockMember
    void getDetailFail1() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        //expected
        mockMvc.perform(get("/manage/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("code").value("403"))
                .andExpect(jsonPath("message").value("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 식재료 상세 검색할 수 없다.")
    void getDetailFail2() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        //expected
        mockMvc.perform(get("/manage/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 식재료 상세 검색")
    @WithMockMember(role = ADMIN)
    void getDetailFail3() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        //expected
        mockMvc.perform(get("/manage/ingredient/{ingredientId}", in.getId() + 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("존재하지 않는 식재료입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 - 식재료 수정")
    @WithMockMember(role = ADMIN)
    void edit() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/manage/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("파"))
                .andExpect(jsonPath("$.cost").value(1300F))
                .andExpect(jsonPath("$.amount").value(1F))
                .andExpect(jsonPath("$.unit").value("단"))
                .andDo(print());
    }

    @Test
    @DisplayName("일반 회원은 식재료 수정할 수 없다.")
    @WithMockMember
    void editFail1() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/manage/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("code").value("403"))
                .andExpect(jsonPath("message").value("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 식재료 수정할 수 없다.")
    void editFail2() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/manage/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 식재료 수정")
    @WithMockMember(role = ADMIN)
    void editFail3() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/manage/ingredient/{ingredientId}", in.getId() + 1)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("존재하지 않는 식재료입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 단위로 식재료 수정")
    @WithMockMember(role = ADMIN)
    void editFail4() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .unit("MILILITER")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/manage/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("존재하지 않는 단위입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료명 없이 식재료 수정")
    @WithMockMember(role = ADMIN)
    void editFail5() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .cost(1300F)
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/manage/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("식재료명을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("가격 없이 식재료 수정")
    @WithMockMember(role = ADMIN)
    void editFail6() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .amount(1F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/manage/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("가격을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("양 없이 식재료 수정")
    @WithMockMember(role = ADMIN)
    void editFail7() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .unit("DAN")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/manage/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("양을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("단위 없이 식재료 수정")
    @WithMockMember(role = ADMIN)
    void editFail8() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        IngredientEditRequest request = IngredientEditRequest.builder()
                .name("파")
                .cost(1300F)
                .amount(1F)
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(put("/manage/ingredient/{ingredientId}", in.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("단위를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 - 식재료 삭제")
    @WithMockMember(role = ADMIN)
    void deleteAdmin() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        //expected
        mockMvc.perform(delete("/manage/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("일반 회원은 식재료 삭제할 수 없다.")
    @WithMockMember
    void deleteFail1() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        //expected
        mockMvc.perform(delete("/manage/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("code").value("403"))
                .andExpect(jsonPath("message").value("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 식재료 삭제할 수 없다.")
    void deleteFail2() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        //expected
        mockMvc.perform(delete("/manage/ingredient/{ingredientId}", in.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 식재료 삭제")
    @WithMockMember(role = ADMIN)
    void deleteFail3() throws Exception {
        //given
        Member other = memberRepository.save(Member.builder()
                .username("other")
                .password("1234")
                .build());

        Ingredient in = ingredientRepository.save(Ingredient.builder()
                .name("파")
                .cost(1300F)
                .amount(300F)
                .unit(GRAM)
                .member(other)
                .build());

        //expected
        mockMvc.perform(delete("/manage/ingredient/{ingredientId}", in.getId() + 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("존재하지 않는 식재료입니다."))
                .andDo(print());
    }

}