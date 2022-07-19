package com.plateer.ec1.common.model.order;

import com.plateer.ec1.common.model.BaseModel;
import com.plateer.ec1.common.utils.LocalDateTimeUtil;
import com.plateer.ec1.payment.enums.OPT0009Code;
import com.plateer.ec1.payment.enums.OPT0010Code;
import com.plateer.ec1.payment.enums.OPT0011Code;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctDpstCmtResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctSeqResVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OpPayInfoModel extends BaseModel {

    private String payNo;
    private String ordNo;
    private String clmNo;
    private String payMnCd;
    private String payCcd;
    private String payPrgsScd;
    private long payAmt;
    private long cnclAmt;
    private long rfndAvlAmt;
    private String trsnId;
    private LocalDateTime payCmtDtime;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String orgPayNo;
    private String vrAcct;
    private String vrAcctNm;
    private String vrBnkCd;
    private String vrValDt;
    private String vrValTt;

    public static OpPayInfoModel getInsertData(OrderInfoVO orderInfoVO, VacctSeqResVO resVO){
        return OpPayInfoModel.builder()
                .ordNo(orderInfoVO.getOrdNo())
                .clmNo(orderInfoVO.getClmNo())
                .payMnCd(OPT0009Code.VIRTUAL_ACCOUNT.getCode())
                .payCcd(OPT0010Code.PAY.getCode())
                .payPrgsScd(OPT0011Code.PAY_REQUEST.getCode())
                .payAmt(Long.parseLong(resVO.getPrice()))
                .cnclAmt(0)
                .rfndAvlAmt(Long.parseLong(resVO.getPrice()))
                .trsnId(resVO.getTid())
                .vrAcct(resVO.getVacct())
                .vrBnkCd(resVO.getVacctBankCode())
                .vrAcctNm(resVO.getVacctName())
                .vrBnkCd(resVO.getVacctBankCode())
                .vrValDt(resVO.getValidDate())
                .vrValTt(resVO.getValidTime())
                .build();
    }

    public static OpPayInfoModel getPayCmpUpdateData(VacctDpstCmtResVO resVO){
        return OpPayInfoModel.builder()
                .trsnId(resVO.getNo_req_tid())
                .payCmtDtime(LocalDateTimeUtil.fromStringYearToSeconds(resVO.getDt_trans() + resVO.getTm_trans()))
                .payPrgsScd(OPT0011Code.PAY_COMPLETE.getCode())
                .rfndAvlAmt(Long.parseLong(resVO.getAmt_input()))
                .vrAcct(resVO.getNo_vacct())
                .build();
    }

    public static OpPayInfoModel getCnlUpdateData(OrderPayInfoVO orderPayInfoVO){
        OpPayInfoModel opPayInfoModel = convertModel(orderPayInfoVO);
        opPayInfoModel.setCnclAmt(orderPayInfoVO.getCnclReqAmt());
        opPayInfoModel.setRfndAvlAmt(calculateRfndAvlAmt(orderPayInfoVO.getPayAmt(), orderPayInfoVO.getCnclReqAmt()));
        return opPayInfoModel;
    }

    public static OpPayInfoModel getCnlCmpInsertData(OrderPayInfoVO orderPayInfoVO){
        OpPayInfoModel opPayInfoModel = convertModel(orderPayInfoVO);
        opPayInfoModel.setPayCcd(OPT0010Code.CANCEL.getCode());
        opPayInfoModel.setPayPrgsScd(OPT0011Code.CANCEL_COMPLETE.getCode());
        opPayInfoModel.setCnclAmt(orderPayInfoVO.getCnclReqAmt());
        opPayInfoModel.setRfndAvlAmt(calculateRfndAvlAmt(orderPayInfoVO.getPayAmt(), orderPayInfoVO.getCnclReqAmt()));
        return opPayInfoModel;
    }

    private static long calculateRfndAvlAmt(long payAmt, long cnclReqAmt){
        return payAmt - cnclReqAmt;
    }

    public static <T> OpPayInfoModel convertModel(T t){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder().build();
        BeanUtils.copyProperties(t, opPayInfoModel);
        return opPayInfoModel;

    }


}
