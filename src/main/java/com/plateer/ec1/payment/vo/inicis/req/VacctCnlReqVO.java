package com.plateer.ec1.payment.vo.inicis.req;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.common.utils.CipherUtil;
import com.plateer.ec1.payment.utils.inicis.InicisApiConstants;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacctCnlReqVO extends InicisReqBase{

    private String tid;
    private String msg;
    private String price;
    private String confirmPrice;
    private String refundAcctNum;
    private String refundBankCode;
    private String refundAcctName;
    private String hashData;

    public VacctCnlReqVO(String type, String paymethod){
        super(type, paymethod);
    }

    public static VacctCnlReqVO of(String type, OrderPayInfoVO orderPayInfoVO){
        VacctCnlReqVO reqVO = new VacctCnlReqVO(type, InicisApiConstants.PAYMETHOD_VACCT);
        reqVO.setTid(orderPayInfoVO.getTrsnId());
        reqVO.setMsg("");
        reqVO.setPrice(String.valueOf(orderPayInfoVO.getCnclReqAmt()));
        reqVO.setConfirmPrice(String.valueOf(orderPayInfoVO.getPayAmt() - orderPayInfoVO.getCnclReqAmt()));
        reqVO.setRefundAcctNum(orderPayInfoVO.getRfndAcctNo());
        reqVO.setRefundBankCode(orderPayInfoVO.getRfndBnkCk());
        reqVO.setRefundAcctName(orderPayInfoVO.getRfndAcctOwnNm());
        return reqVO;
    }

    public void setUpVacctCnlReqVO(String MID, String API_KEY, String IV){
        super.setUp(MID, API_KEY);
        this.setRefundAcctNum(CipherUtil.encryptByAES(this.getRefundAcctNum(), API_KEY, IV));
        String hashData = InicisApiConstants.TYPE_REFUND.equals(this.getType()) ? getAllCancelHashData() : getPartialRefundHashData();
        this.setHashData(hashData);
    }

    public String getAllCancelHashData(){
        String input = super.getBasicHashData()
                .append(this.getTid())
                .append(this.getRefundAcctNum())
                .toString();

        return CipherUtil.encryptBySHA512(input);
    }

    public String getPartialRefundHashData(){
        String input = super.getBasicHashData()
                .append(this.getTid())
                .append(this.getPrice())
                .append(this.getConfirmPrice())
                .append(this.getRefundAcctNum())
                .toString();

        return CipherUtil.encryptBySHA512(input);
    }


}
