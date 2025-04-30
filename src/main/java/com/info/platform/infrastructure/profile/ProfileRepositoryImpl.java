package com.info.platform.infrastructure.profile;

import com.info.platform.controller.profile.dto.SearchProfilesCondition;
import com.info.platform.domain.profile.model.Profile;
import com.info.platform.domain.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileQueryDSL profileQueryDSL;
    private final ProfileJpaRepo profileJpaRepo;
    @Override
    public Page<Profile> findAllByCondition(SearchProfilesCondition condition, Pageable pageable) {
        return profileQueryDSL.findAllByCondition(condition,pageable);
    }

    @Override
    public void deleteAll() {
        profileJpaRepo.deleteAll();
    }

    @Override
    public Profile save(Profile profile) {
        return profileJpaRepo.save(profile);
    }

    @Override
    public int increaseViewCount(Long profileId) {
        return profileJpaRepo.increaseViewCount(profileId);
    }
}
