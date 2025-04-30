package com.info.platform.controller.point;

import com.info.platform.controller.point.dto.PointChargeRequest;
import com.info.platform.controller.point.dto.PointChargeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "포인트", description = "포인트 충전 관련 API")
@RequestMapping("/api/v1/points")
public interface PointApi {

    @Operation(summary = "포인트 충전", description = "결제 및 쿠폰 적용을 통해 포인트를 충전합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "충전 성공"),
            @ApiResponse(responseCode = "400", description = "요청 데이터 오류"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/charge")
    ResponseEntity<PointChargeResponse> chargePoint(
            @RequestBody @Valid PointChargeRequest request
    );
}