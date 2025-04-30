package com.info.platform.infrastructure.point;

import com.info.platform.domain.point.model.Point;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PointJpaRepo extends JpaRepository<Point,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Point p where p.memberId = :memberId")
    Optional<Point> findByMemberIdWithLock(@Param("memberId") Long memberId);
}
