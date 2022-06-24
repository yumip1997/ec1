package com.plateer.ec1.promotion.factory;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.promotion.enums.DcCode;
import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.vo.PromotionVO;
import com.plateer.ec1.promotion.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.vo.response.ResponseBaseVO;

import java.util.Comparator;
import java.util.List;

public interface Calculation extends CustomFactory<PromotionType> {

    ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO);

    default PromotionVO getMaxBenefitPrm(List<PromotionVO> promotionVOList){
        return promotionVOList.stream()
                .min(Comparator.comparingLong(PromotionVO::getDiscountedPrice))
                .orElse(PromotionVO.builder().build());
    }

    default PromotionVO setDiscountedPrice(Long productPrice, PromotionVO promotionVO){
        Long discountedPrice = getDiscountPrice(productPrice, promotionVO);
        promotionVO.setDiscountedPrice(discountedPrice);

        return promotionVO;
    }

    default Long getDiscountPrice(Long productPrice, PromotionVO promotionVO){
        String code = promotionVO.getDcCode();
        Long discountValue = promotionVO.getDiscountValue();

        return DcCode.getDiscountFunction(code).apply(productPrice, discountValue);
    }

}
