package com.plateer.ec1.promotion.com.vo;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.vo.BaseVO;
import com.plateer.ec1.promotion.enums.PromotionException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import javax.print.attribute.standard.PrinterMakeAndModel;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CupInfoVO extends BaseVO {

    private Long prmNo;
    private Long cpnIssNo;
    private String prmNm;
    private String cpnKindCd;
    private String degrCcd;
    private String useYn;
    private LocalDate dwlAvlEndDd;
    private LocalDateTime prmEndDt;
    private Long dwlPsbCnt;
    private Long psnDwlPsbCnt;
    private Long totalCnt;
    private Long mbrCnt;
    private String mbrNo;
    private LocalDateTime cpnUseDt;

    public void isValidPrmPeriod() {
        if (LocalDateTime.now().isAfter(this.getPrmEndDt())) {
            throw new BusinessException(PromotionException.INVALID_PRM_PERIOD.getMSG());
        }
    }

    public void isValidCupDwlPeriod() {
        if (LocalDate.now().isAfter(this.getDwlAvlEndDd())) {
            throw new BusinessException(PromotionException.INVALID_CUP_DWL_PERIOD.getMSG());
        }
    }

    public void isValidCnt() {
        final Long INFINITE_CNT = 0L;
        Long dwlPsbCnt = INFINITE_CNT.equals(this.getDwlPsbCnt()) ? Long.MAX_VALUE : this.getDwlPsbCnt();
        Long psnDwlPsbCnt = INFINITE_CNT.equals(this.getPsnDwlPsbCnt()) ? Long.MAX_VALUE : this.getPsnDwlPsbCnt();

        boolean isValid = dwlPsbCnt > this.getTotalCnt() && psnDwlPsbCnt > this.getMbrCnt();

        if (!isValid) {
            throw new BusinessException(PromotionException.INVALID_CUP_DWL_CNT.getMSG());
        }
    }

    public void isNotUsed(){
        if(!ObjectUtils.isEmpty(this.getCpnUseDt())){
            throw new BusinessException(PromotionException.ALREADY_USED_CUP.getMSG());
        }
    }

    public void isUsed(){
        if(ObjectUtils.isEmpty(this.getCpnUseDt())){
            throw new BusinessException(PromotionException.NOT_USED_CUP.getMSG());
        }
    }

    public void isEqualMbrNo(String mbrNo){
        if(!this.mbrNo.equals(mbrNo)){
            throw new BusinessException(PromotionException.NOT_SAME_MBR_NO.getMSG());
        }
    }

    public void dwlValidate(){
        isValidCupDwlPeriod();
        isValidCnt();
    }

    public void cupUseValidate(String mbrNo){
        isEqualMbrNo(mbrNo);
        isNotUsed();
        isValidPrmPeriod();
    }

    public void restoreCupValidate(String mbrNo){
        isEqualMbrNo(mbrNo);
        isUsed();
        isValidPrmPeriod();
    }



}
