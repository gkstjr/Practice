package com.info.platform.domain.profile;

import com.info.platform.controller.profile.dto.SearchProfilesCondition;
import com.info.platform.domain.profile.model.Profile;
import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    @Transactional(readOnly = true)
    public Page<Profile> searchProfiles(SearchProfilesCondition condition , Pageable pageable) {
        return profileRepository.findAllByCondition(condition,pageable);
    }
    //review - update 단일 쿼리로 날려서 MySql의 row-level lock 으로 동시성 해결 -> 서비스가 대용량 트래픽(약 초당 5000건) 정도로 증가하게 되면 점진적으로 Redis로 write buffer 형태로 기능 개선
    @Transactional
    public void increaseViewCount(Long profileId) {
        int updated = profileRepository.increaseViewCount(profileId);
        if (updated == 0) {
            throw new BusinessException(ErrorCode.PROFILE_NOT_FOUND);
        }
    }
}