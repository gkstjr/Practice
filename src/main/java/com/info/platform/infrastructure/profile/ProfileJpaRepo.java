package com.info.platform.infrastructure.profile;

import com.info.platform.domain.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileJpaRepo extends JpaRepository<Profile,Long> {

    @Modifying(clearAutomatically = true) //review - 해당 쿼리 이후 현재 트랜잭션의 영속성 컨에티너 캐시 초기화 방식이므로 다른 쓰기,변경 로직과 한 트랜잭션에서 진행은 지양
    @Query("update Profile p set p.viewCount = p.viewCount + 1 where p.id = :profileId")
    int increaseViewCount(@Param("profileId") Long profileId);

}
