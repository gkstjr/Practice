package com.info.platform.service.profile;

import com.info.platform.domain.profile.ProfileRepository;
import com.info.platform.domain.profile.ProfileService;
import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceUnitTest {
    @InjectMocks
    private ProfileService profileService;
    @Mock
    private ProfileRepository profileRepository;
    @Test
    void 프로필조회수증가_프로필없음_PROFILE_NOT_FOUND_반환() {
        // given
        Long profileId = 999L;
        given(profileRepository.increaseViewCount(profileId)).willReturn(0);

        // when & then
        assertThatThrownBy(() -> profileService.increaseViewCount(profileId))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.PROFILE_NOT_FOUND);
    }

}
