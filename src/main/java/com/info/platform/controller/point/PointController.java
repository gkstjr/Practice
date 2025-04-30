package com.info.platform.controller.point;

import com.info.platform.application.point.PointFacade;
import com.info.platform.controller.point.dto.PointChargeRequest;
import com.info.platform.controller.point.dto.PointChargeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointController implements PointApi {

    private final PointFacade pointFacade;

    @PostMapping("/charge")
    public ResponseEntity<PointChargeResponse> chargePoint(@RequestBody @Valid PointChargeRequest request) {
        //테스트용
        Long testUserId = 1L;

        return ResponseEntity.ok(PointChargeResponse.from(pointFacade.chargePoint(request.toCommand(),testUserId)));
    }
}
