package com.alipay.mobileapp.biz.rpc.taobao.bind.vo;

import com.alipay.inside.mobile.framework.service.annotation.OperationType;

public interface TaobaoBindFacade {
    @OperationType("alipay.client.bindTaobaoAccount")
    BindTaobaoRes alipayAccountBinding(BindTaobaoReq bindTaobaoReq);
}
