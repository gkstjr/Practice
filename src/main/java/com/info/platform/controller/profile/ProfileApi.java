package com.info.platform.controller.profile;

import com.info.platform.controller.common.PageResponse;
import com.info.platform.controller.profile.dto.ProfileResponse;
import com.info.platform.controller.profile.dto.SearchProfilesCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "프로필", description = "회원 프로필 관련 API")
@RequestMapping("/api/v1/profiles")
public interface ProfileApi {

    @Operation(summary = "회원 프로필 목록 조회", description = "회원 이름, 조회수, 등록일 기준으로 정렬 및 페이징 조회가 가능합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "요청 조건 오류")
    })
    @GetMapping
    ResponseEntity<PageResponse<ProfileResponse>> searchProfiles(
            @Valid @ModelAttribute SearchProfilesCondition condition,
            Pageable pageable
    );

    @Operation(summary = "프로필 상세 조회수 증가", description = "프로필 상세 페이지 조회 시 조회수를 1 증가시킵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회수 증가 성공"),
            @ApiResponse(responseCode = "404", description = "프로필 ID가 존재하지 않음")
    })
    @PatchMapping("/{profileId}/view-count")
    ResponseEntity<Void> increaseViewCount(
            @Parameter(description = "조회수 증가 대상 프로필 ID", example = "1")
            @PathVariable Long profileId
    );
}