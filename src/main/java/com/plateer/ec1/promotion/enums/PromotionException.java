package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PromotionException {

    INVALID_PROMOTION_TYPE("프로모션 유형이 올바르지 않습니다!"),
    INVALID_CUP_DWL_TYPE("다운로드 쿠폰 유형이 올바르지 않습니다!");

    private final String MSG;
}
