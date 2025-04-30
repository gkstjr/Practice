package com.info.platform.controller.profile;

import com.info.platform.domain.profile.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProfileService profileService;
    @Test
    void 프로필목록조회_최대최소조회수_음수이면_검증실패예외반환() throws Exception {
        //when
        ResultActions minResult = mockMvc.perform(get("/api/v1/profiles")
                .param("minViewCount","-1"));

        ResultActions maxResult = mockMvc.perform(get("/api/v1/profiles")
                .param("maxViewCount","-1"));

        //then
        minResult.andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MethodArgumentNotValidException.class)
                        .hasMessageContaining("조회수는 0 이상이어야 합니다."));

        maxResult.andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MethodArgumentNotValidException.class)
                        .hasMessageContaining("조회수는 0 이상이어야 합니다."));
    }
}
