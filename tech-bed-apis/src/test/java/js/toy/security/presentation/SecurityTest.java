package js.toy.security.presentation;

import js.toy.response.ResponseCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "김자바", roles = { "USER" })
    void api_요청은_USER_역할을_가지는_경우에_성공() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/security/api/v1/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("code").value(ResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("message").value(ResponseCode.SUCCESS.getMessage()))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    void api_요청은_로그인_안한_경우에_실패() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/security/api/v1/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "김자바", roles = { "ADMIN" })
    void api_요청은_USER_이외의_역할을_가지는_경우에_실패() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/security/api/v1/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isForbidden());
    }
}