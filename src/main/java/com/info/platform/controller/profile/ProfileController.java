package com.info.platform.controller.profile;

import com.info.platform.controller.common.PageResponse;
import com.info.platform.controller.profile.dto.ProfileResponse;
import com.info.platform.controller.profile.dto.SearchProfilesCondition;
import com.info.platform.domain.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController implements ProfileApi {

    private final ProfileService profileService;
    @GetMapping
    public ResponseEntity<PageResponse<ProfileResponse>> searchProfiles(
            @Valid @ModelAttribute SearchProfilesCondition condition,
            Pageable pageable
    ) {

        return ResponseEntity.ok(PageResponse.from(
                profileService.searchProfiles(condition,pageable)
                        .map(ProfileResponse::from)));
    }

    @PatchMapping("/{profileId}/view-count")
    public ResponseEntity<Void> increaseViewCount(@PathVariable Long profileId) {
        profileService.increaseViewCount(profileId);
        return ResponseEntity.ok().build();
    }
}
