package com.info.platform.infrastructure.sort;

import com.info.platform.domain.profile.model.QProfile;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

public enum ProfilesSortField {
    nickname(QProfile.profile.nickname),
    viewCount(QProfile.profile.viewCount),
    createdAt(QProfile.profile.createdAt);

    private final ComparableExpressionBase<?> path;

    ProfilesSortField(ComparableExpressionBase<?> path) {
        this.path = path;
    }

    public ComparableExpressionBase<?> getPath() {
        return path;
    }
}
