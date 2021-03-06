package com.plateer.ec1.promotion.download.mapper;

import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class CupDwlMapperTest {

    @Autowired
    private CupDwlMapper cupDwlMapper;

    @Test
    void test(){
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().prmNo(1L).mbrNo("33").build();
        Optional<CupInfoVO> cupDwlInfo = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);
    }
}