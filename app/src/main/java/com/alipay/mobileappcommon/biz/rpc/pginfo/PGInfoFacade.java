package com.alipay.mobileappcommon.biz.rpc.pginfo;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.ClientPGInfoReqPB;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.ClientPGInfoRespPB;

public interface PGInfoFacade {
    @OperationType("alipay.mobileappcommon.pg.pgPGInfo")
    @SignCheck
    ClientPGInfoRespPB getPGInfo(ClientPGInfoReqPB clientPGInfoReqPB);
}
