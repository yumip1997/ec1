package com.plateer.ec1.promotion.service;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.factory.CalculationFactory;
import com.plateer.ec1.promotion.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final CalculationFactory calculationFactory;

    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO){
        Calculation calculation = calculationFactory.get(PromotionType.findPromotionType(prmRequestBaseVO.getPrmTypeCode()));
        return calculation.getCalculationData(prmRequestBaseVO);
    }

}
