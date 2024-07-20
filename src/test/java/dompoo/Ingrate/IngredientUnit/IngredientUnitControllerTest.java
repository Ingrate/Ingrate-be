package dompoo.Ingrate.IngredientUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import dompoo.Ingrate.api.request.UnitAddRequest;
import dompoo.Ingrate.config.WithMockMember;
import dompoo.Ingrate.domain.enums.Role;
import dompoo.Ingrate.domain.IngredientUnit;
import dompoo.Ingrate.service.repository.IngredientUnitRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static dompoo.Ingrate.domain.enums.Unit.DAN;
import static dompoo.Ingrate.domain.enums.Unit.GRAM;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class IngredientUnitControllerTest {

    @Autowired private IngredientUnitRepository unitRepository;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @AfterEach
    void after() {
        unitRepository.deleteAll();
    }

    @Test
    @DisplayName("식재료-단위 전체 조회")
    @WithMockMember
    void getList() throws Exception {
        //given
        unitRepository.save(IngredientUnit.builder()
                .name("파")
                .unit(GRAM)
                .build());

        unitRepository.save(IngredientUnit.builder()
                .name("파")
                .unit(DAN)
                .build());

        //expected
        mockMvc.perform(get("/ingredient/unit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("파"))
                .andExpect(jsonPath("$[0].unit").value("g"))
                .andExpect(jsonPath("$[0].enumUnit").value("GRAM"))
                .andExpect(jsonPath("$[1].name").value("파"))
                .andExpect(jsonPath("$[1].unit").value("단"))
                .andExpect(jsonPath("$[1].enumUnit").value("DAN"))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 식재료-단위 전체 조회할 수 없다.")
    void getListFail1() throws Exception {
        //given
        unitRepository.save(IngredientUnit.builder()
                .name("파")
                .unit(GRAM)
                .build());

        unitRepository.save(IngredientUnit.builder()
                .name("파")
                .unit(DAN)
                .build());

        //expected
        mockMvc.perform(get("/ingredient/unit"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료-단위 추가")
    @WithMockMember(role = Role.ADMIN)
    void addOne() throws Exception {
        //given
        UnitAddRequest request = UnitAddRequest.builder()
                .name("파")
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient/unit")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("파"))
                .andExpect(jsonPath("$.unit").value("g"))
                .andDo(print());
    }

    @Test
    @DisplayName("비회원은 식재료-단위 추가할 수 없다.")
    void addOneFail1() throws Exception {
        //given
        UnitAddRequest request = UnitAddRequest.builder()
                .name("파")
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient/unit")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("401"))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("일반회원은 식재료-단위 추가할 수 없다.")
    @WithMockMember
    void addOneFail2() throws Exception {
        //given
        UnitAddRequest request = UnitAddRequest.builder()
                .name("파")
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient/unit")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("code").value("403"))
                .andExpect(jsonPath("message").value("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료-단위 추가시 식재료명은 필수이다.")
    @WithMockMember(role = Role.ADMIN)
    void addOneFail3() throws Exception {
        //given
        UnitAddRequest request = UnitAddRequest.builder()
                .unit("GRAM")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient/unit")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("식재료명을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("식재료-단위 추가시 단위는 필수이다.")
    @WithMockMember(role = Role.ADMIN)
    void addOneFail4() throws Exception {
        //given
        UnitAddRequest request = UnitAddRequest.builder()
                .name("파")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/ingredient/unit")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("단위를 입력해주세요."))
                .andDo(print());
    }
}