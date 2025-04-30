package com.info.platform.domain.point;

import com.info.platform.domain.point.model.Point;

import java.util.Optional;

public interface PointRepository {
    Optional<Point> findByMemberIdWithLock(Long memberId);

    Point save(Point point);
}
