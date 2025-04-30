package com.info.platform.infrastructure.coupon;

import com.info.platform.domain.coupon.model.IssuedCoupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IssuedCouponJpaRepo extends JpaRepository<IssuedCoupon,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select ic from IssuedCoupon ic where ic.id = :id")
    Optional<IssuedCoupon> findByIdWithLock(@Param("id") Long id);}
