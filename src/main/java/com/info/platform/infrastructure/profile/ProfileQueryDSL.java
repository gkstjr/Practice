package com.info.platform.infrastructure.profile;

import com.info.platform.controller.profile.dto.SearchProfilesCondition;
import com.info.platform.domain.profile.model.Profile;
import com.info.platform.domain.profile.model.QProfile;
import com.info.platform.infrastructure.sort.ProfilesSortUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfileQueryDSL {

        private final JPAQueryFactory jpaQueryFactory;
        private final QProfile qProfile = QProfile.profile;
        private final ProfilesSortUtil profilesSortUtil;

        public Page<Profile> findAllByCondition(SearchProfilesCondition condition, Pageable pageable) {
                BooleanBuilder where = new BooleanBuilder()
                        .and(gteIfPresent(qProfile.viewCount,condition.minViewCount()))
                        .and(lteIfPresent(qProfile.viewCount, condition.maxViewCount()))
                        .and(likeIfPresent(qProfile.nickname, condition.nickname()));

                List<? extends OrderSpecifier<?>> orderSpecifiers = profilesSortUtil.toOrderSpecifiers(pageable.getSort());

                List<Profile> content = jpaQueryFactory
                        .selectFrom(qProfile)
                        .where(where)
                        .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

                long total = Optional.ofNullable(
                        jpaQueryFactory
                                .select(qProfile.count())
                                .from(qProfile)
                                .where(where)
                                .fetchOne()
                ).orElse(0L);

                return new PageImpl<>(content,pageable,total);
        }

        private <T extends Number & Comparable<T>> BooleanExpression gteIfPresent(
                NumberPath<T> path, T value) {
                return value != null ? path.goe(value) : null;
        }

        private <T extends Number & Comparable<T>> BooleanExpression lteIfPresent(
                NumberPath<T> path, T value) {
                return value != null ? path.loe(value) : null;
        }

        private BooleanExpression likeIfPresent(StringPath path, String keyword) {
                return (keyword != null && !keyword.isBlank()) ? path.containsIgnoreCase(keyword) : null;
        }
}
