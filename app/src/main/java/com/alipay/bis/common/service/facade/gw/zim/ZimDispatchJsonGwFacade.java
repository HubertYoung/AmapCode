package com.alipay.bis.common.service.facade.gw.zim;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.SignCheck;

public interface ZimDispatchJsonGwFacade {
    @OperationType("zoloz.zim.dispatch.init.json")
    ZimInitGwResponse init(ZimInitGwRequest zimInitGwRequest);

    @OperationType("com.zoloz.zhub.zim.init.json")
    @SignCheck
    ZimInitGwResponse initStandard(ZimInitGwRequest zimInitGwRequest);

    @OperationType("zoloz.zim.dispatch.validate.json")
    ZimValidateGwResponse validate(ZimValidateJsonGwRequest zimValidateJsonGwRequest);

    @OperationType("com.zoloz.zhub.zim.verify.json")
    @SignCheck
    ZimValidateGwResponse validateStandard(ZimValidateJsonGwRequest zimValidateJsonGwRequest);
}
