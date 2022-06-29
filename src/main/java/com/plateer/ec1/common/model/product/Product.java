package com.plateer.ec1.common.model.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Product {

    private String goodsNo;
    private String goodsNm;
    private String itemNo;
    private String itemNm;
    private String goodsTpCd;
    private Long salePrc;
    private Long prmPrc;

}
