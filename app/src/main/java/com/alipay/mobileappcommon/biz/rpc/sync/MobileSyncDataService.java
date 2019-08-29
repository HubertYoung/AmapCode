package com.alipay.mobileappcommon.biz.rpc.sync;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobileappcommon.biz.rpc.sync.model.proto.BatchSyncDataReq;
import com.alipay.mobileappcommon.biz.rpc.sync.model.proto.BatchSyncDataResp;
import com.alipay.mobileappcommon.biz.rpc.sync.model.proto.SyncDataReq;
import com.alipay.mobileappcommon.biz.rpc.sync.model.proto.SyncDataResp;

public interface MobileSyncDataService {
    @OperationType("alipay.client.getBatchSyncData")
    BatchSyncDataResp getBatchSyncData(BatchSyncDataReq batchSyncDataReq);

    @OperationType("alipay.client.getSyncData")
    SyncDataResp getSyncData(SyncDataReq syncDataReq);
}
