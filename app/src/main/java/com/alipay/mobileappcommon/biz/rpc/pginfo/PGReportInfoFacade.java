package com.alipay.mobileappcommon.biz.rpc.pginfo;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.ClientPGReportReqPB;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.ClientPGReportRespPB;

public interface PGReportInfoFacade {
    @OperationType("alipay.mobileappcommon.pg.pgReprot")
    @SignCheck
    ClientPGReportRespPB pgReport(ClientPGReportReqPB clientPGReportReqPB);
}
