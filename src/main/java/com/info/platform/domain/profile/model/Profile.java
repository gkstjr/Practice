package com.info.platform.domain.profile.model;

import com.info.platform.support.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String nickname;
    private long viewCount;

    /** @implNote 테스트에서만 사용. createdAt 수동 주입 용도 */
    public Profile(Long memberId,String nickname, int viewCount, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.viewCount = viewCount;
        this.setCreatedAt(createdAt);;
    }
}