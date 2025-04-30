package com.info.platform.controller.profile.dto;

import com.info.platform.domain.profile.model.Profile;

import java.time.LocalDateTime;

public record ProfileResponse(
      Long id,
      Long memberId,
      String nickname,
      long viewCount,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
) {

    public static ProfileResponse from(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getMemberId(),
                profile.getNickname(),
                profile.getViewCount(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}
