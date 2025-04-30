package com.info.platform.domain.profile;

import com.info.platform.controller.profile.dto.SearchProfilesCondition;
import com.info.platform.domain.profile.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileRepository {
    Page<Profile> findAllByCondition(SearchProfilesCondition condition, Pageable pageable);

    void deleteAll();

    Profile save(Profile profile);

    int increaseViewCount(Long profileId);
}
