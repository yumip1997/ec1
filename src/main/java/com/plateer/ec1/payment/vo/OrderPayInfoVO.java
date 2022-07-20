package com.plateer.ec1.payment.vo;

import com.plateer.ec1.common.validation.annotation.PayCancel;
import com.plateer.ec1.payment.enums.OPT0011Code;
import com.plateer.ec1.payment.enums.PaymentType;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PayCancel
public class OrderPayInfoVO {

    private String ordNo;
    private String ordNm;
    private String ordEmail;
    private String rfndBnkCk;
    private String rfndAcctNo;
    private String rfndAcctOwnNm;
    private String clmNo;
    private long cnclReqAmt;
    private String payNo;
    private String payMnCd;
    private String payCcd;
    private String payPrgsScd;
    private long payAmt;
    private long cnclAmt;
    private long rfndAvlAmt;
    private String trsnId;
    private String orgPayNo;
    private String vrAcct;
    private String vrAcctNm;
    private String vrBnkCd;
    private String vrValDt;
    private String vrValTt;
    private String goodsNm;

    public boolean isPayRequest(){
        return OPT0011Code.PAY_REQUEST.getCode().equals(this.getPayPrgsScd());
    }

    public boolean isPayComplete(){
        return OPT0011Code.PAY_COMPLETE.getCode().equals(this.getPayPrgsScd());
    }

    public boolean isPartialCancel(){
        return this.getPayAmt() != this.getCnclReqAmt();
    }

    public OrderPayInfoVO changeCnlReqAmt(long payAmt){
        OrderPayInfoVO orderPayInfoVO = new OrderPayInfoVO();
        BeanUtils.copyProperties(this, orderPayInfoVO);
        orderPayInfoVO.setCnclReqAmt(payAmt);
        return orderPayInfoVO;
    }

    public OrderInfoVO toOrderInfoVO(){
        return OrderInfoVO.builder()
                .ordNo(this.getOrdNo())
                .clmNo(this.getClmNo())
                .goodName(this.getGoodsNm())
                .buyerName(this.getOrdNm())
                .buyerEmail(this.getOrdEmail())
                .build();
    }

    public PayInfoVO toPayInfoVO(){
        return PayInfoVO.builder()
                .payAmount(this.getPayAmt() - this.getCnclReqAmt())
                .bankCode(this.getRfndBnkCk())
                .depositorName(this.getRfndAcctOwnNm())
                .paymentType(PaymentType.INICIS)
                .build();
    }
}