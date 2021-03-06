package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.enums.OPT0009Code;
import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.payment.enums.PaymentType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayInfoVO {

    @NotNull
    private Long payAmount;
    private String bankCode;
    private String depositorName;
    @NotNull
    private PaymentType paymentType;
    @NotNull
    private OPT0009Code methodType;

    public PaymentBusiness getPaymentBusiness(){
        return PaymentBusiness.of(this.getPaymentType(), this.getMethodType());
    }
}
