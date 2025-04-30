package com.info.platform.service.point;

import com.info.platform.domain.point.model.Point;
import com.info.platform.domain.point.PointRepository;
import com.info.platform.domain.point.PointService;
import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PointServiceUnitTest {

    @InjectMocks
    private PointService pointService;
    @Mock
    private PointRepository pointRepository;

   @Test
   void 포인트충전시_0이하의금액은로_충전_시_예외() {
       // given
       Point point = new Point(1L);

       // when & then
       assertThatThrownBy(() -> point.charge(-500L))
               .isInstanceOf(BusinessException.class)
               .hasFieldOrPropertyWithValue("errorCode",ErrorCode.INVALID_CHARGE_AMOUNT);
   }

    @Test
    void 포인트_충전_시_회원포인트_정보없으면_예외() {

        given(pointRepository.findByMemberIdWithLock(anyLong())).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> pointService.chargePoint(1L, 10000L))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.POINT_NOT_FOUND);
    }

    @Test
    void 포인트_충전_정상_처리() {
        // given
        Long memberId = 1L;
        Point point = new Point(memberId); //기존 포인트 0
        given(pointRepository.findByMemberIdWithLock(memberId)).willReturn(Optional.of(point));

        //when
        long CurrentAmount = pointService.chargePoint(memberId, 10000);

        //then
        assertThat(CurrentAmount).isEqualTo(10000);
        verify(pointRepository).findByMemberIdWithLock(memberId);
    }
}
