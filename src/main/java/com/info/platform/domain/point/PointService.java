package com.info.platform.domain.point;

import com.info.platform.domain.point.model.Point;
import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    @Transactional
    public long chargePoint(Long memberId, long chargePoint) {
        Point point = pointRepository.findByMemberIdWithLock(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POINT_NOT_FOUND));

        point.charge(chargePoint);

        return point.getBalance();
    }
}
