package com.plateer.ec1.order.strategy.after.impl;

import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class BOAfterStrategy implements AfterStrategy {

    @Override
    public void call(OrderRequestVO orderRequestVO, OrderVO orderVO) {
        log.info("BO 주문 후처리");
    }

    @Override
    public SystemType getType() {
        return SystemType.BO;
    }
}
