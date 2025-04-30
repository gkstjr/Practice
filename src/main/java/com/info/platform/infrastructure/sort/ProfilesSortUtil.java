package com.info.platform.infrastructure.sort;

import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfilesSortUtil {



    public List<? extends OrderSpecifier<?>> toOrderSpecifiers(Sort sort) {
        return sort.stream()
                .map(order -> {
                    ProfilesSortField field = parseField(order.getProperty());
                    return new OrderSpecifier<>(
                            order.isAscending() ? Order.ASC : Order.DESC,
                            field.getPath()
                    );
                })
                .toList();
    }

    //review - private 함수를 지양하지만 내부에서만 사용하기 때문에 가독성을 위해 private 으로 추출
    private ProfilesSortField parseField(String property) {
        try {
            return ProfilesSortField.valueOf(property);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.PROFILE_INVALID_SORT_FIELD, String.format("지원하지 않는 정렬 필드 : {%s}", property)); //todo - 예외처리
        }
    }
}
