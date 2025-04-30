package com.info.platform.controller.profile.dto;


import jakarta.validation.constraints.Min;

//todo - swagger 문서 쪽에 설명 파일 따로 정리 방법 모색
public record SearchProfilesCondition(
        String nickname,
        @Min(value = 0, message = "조회수는 0 이상이어야 합니다.")
        Long minViewCount,
        @Min(value = 0, message = "조회수는 0 이상이어야 합니다.")
        Long maxViewCount
) {
}
