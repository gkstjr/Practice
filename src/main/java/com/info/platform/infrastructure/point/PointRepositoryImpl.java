package com.info.platform.infrastructure.point;

import com.info.platform.domain.point.model.Point;
import com.info.platform.domain.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {
    private final PointJpaRepo pointJpaRepo;
    @Override
    public Optional<Point> findByMemberIdWithLock(Long memberId) {
        return pointJpaRepo.findByMemberIdWithLock(memberId);
    }

    @Override
    public Point save(Point point) {
        return pointJpaRepo.save(point);
    }
}
