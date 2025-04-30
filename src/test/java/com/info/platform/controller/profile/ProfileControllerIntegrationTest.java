package com.info.platform.controller.profile;

import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import com.info.platform.domain.profile.model.Profile;
import com.info.platform.domain.profile.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProfileControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileRepository profileRepository;
    @Test
    void 프로필_조회수증가_성공후_프로필_목록조회() throws Exception {
        //given
        Profile profile = profileRepository.save(new Profile(1L, "김한석", 10, LocalDateTime.now()));

        //when
        mockMvc.perform(patch("/api/v1/profiles/{profileId}/view-count", profile.getId()))
                .andExpect(status().isOk());

        //then
        mockMvc.perform(get("/api/v1/profiles")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].viewCount").value(11));
    }

    //=================== profile 목록 조회 시작 ====================================
    @Test
    void 존재하지않는_정렬필드_입력시_예외반환() throws Exception {
        // given
        profileRepository.save(new Profile(1L, "김한석1",10, LocalDateTime.now()));
        profileRepository.save(new Profile(2L, "김한석2",20, LocalDateTime.now().minusDays(1)));
        profileRepository.save(new Profile(3L, "김한석3",30, LocalDateTime.now().minusDays(2)));

        // when & then
        mockMvc.perform(get("/api/v1/profiles")
                        .param("sort", "invalidField,asc"))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(BusinessException.class)
                        .hasFieldOrPropertyWithValue("errorCode",ErrorCode.PROFILE_INVALID_SORT_FIELD));
    }

    @Test
    void 프로필_페이징_테스트() throws Exception {
        // given
        profileRepository.save(new Profile(1L, "김한석1",10, LocalDateTime.now()));
        profileRepository.save(new Profile(2L, "김한석2",20, LocalDateTime.now().minusDays(1)));
        profileRepository.save(new Profile(3L, "김한석3",30, LocalDateTime.now().minusDays(2)));

        // when & then
        mockMvc.perform(get("/api/v1/profiles")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.page").value(0));

        mockMvc.perform(get("/api/v1/profiles")
                        .param("page", "1")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.page").value(1));
    }

    @Test
    void 프로필이름순_정렬_테스트() throws Exception {
        // given
        profileRepository.save(new Profile(1L, "김한석2", 10, LocalDateTime.now()));
        profileRepository.save(new Profile(2L, "김한석1", 20, LocalDateTime.now()));
        profileRepository.save(new Profile(3L, "김한석3", 30, LocalDateTime.now()));

        // when & then
        mockMvc.perform(get("/api/v1/profiles")
                        .param("sort", "nickname,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nickname").value("김한석1"))
                .andExpect(jsonPath("$.content[1].nickname").value("김한석2"))
                .andExpect(jsonPath("$.content[2].nickname").value("김한석3"));
    }

    @Test
    void 조회순_정렬_테스트() throws Exception {
        // given
        profileRepository.save(new Profile(1L, "김한석1",50, LocalDateTime.now().minusDays(3)));
        profileRepository.save(new Profile(2L, "김한석2",10, LocalDateTime.now()));
        profileRepository.save(new Profile(3L, "김한석3",30, LocalDateTime.now().minusDays(1)));

        // when & then
        mockMvc.perform(get("/api/v1/profiles")
                        .param("sort", "viewCount,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].viewCount").value(10))
                .andExpect(jsonPath("$.content[1].viewCount").value(30))
                .andExpect(jsonPath("$.content[2].viewCount").value(50));
    }
    @Test
    void 등록순_정렬_테스트() throws Exception {
        // given
        profileRepository.save(new Profile(1L, "김한석1", 10, LocalDateTime.now().minusDays(3))); // 오래됨
        profileRepository.save(new Profile(2L, "김한석2", 20, LocalDateTime.now()));              // 최신
        profileRepository.save(new Profile(3L, "김한석3", 30, LocalDateTime.now().minusDays(1)));  // 중간

        // when & then
        mockMvc.perform(get("/api/v1/profiles")
                        .param("sort", "createdAt,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].createdAt").exists())
                .andExpect(jsonPath("$.content.length()").value(3));
    }
    @Test
    void 프로필_조건필터_테스트() throws Exception {
        // given
        profileRepository.save(new Profile(1L, "김한석1",5, LocalDateTime.now()));
        profileRepository.save(new Profile(2L, "김한석2",15, LocalDateTime.now()));
        profileRepository.save(new Profile(3L, "김한석3",25, LocalDateTime.now()));

        // when & then
        mockMvc.perform(get("/api/v1/profiles")
                        .param("minViewCount", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nickname").value("김한석2"))
                .andExpect(jsonPath("$.content[1].nickname").value("김한석3"));

        mockMvc.perform(get("/api/v1/profiles")
                        .param("maxViewCount", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nickname").value("김한석1"))
                .andExpect(jsonPath("$.content[1].nickname").value("김한석2"));
    }
    //=================== profile 목록 조회 끝 ====================================

}



