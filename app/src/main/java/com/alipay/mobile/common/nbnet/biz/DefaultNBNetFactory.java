package com.alipay.mobile.common.nbnet.biz;

import com.alipay.mobile.common.nbnet.api.NBNetConfigHelper;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.NBNetFactory;
import com.alipay.mobile.common.nbnet.api.NBNetLog;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadClient;
import com.alipay.mobile.common.nbnet.biz.constants.DefaultNBNetConfigHelper;
import com.alipay.mobile.common.nbnet.biz.download.DownloadCacheManager;
import com.alipay.mobile.common.nbnet.biz.download.NBNetDownloadClientImpl;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogFactory;
import com.alipay.mobile.common.nbnet.biz.upload.DefaultNBNetUploadClient;

public class DefaultNBNetFactory extends NBNetFactory {
    public NBNetContext getNBNetContext() {
        return GlobalNBNetContext.a();
    }

    public NBNetDownloadClient getDownloadClient() {
        return NBNetDownloadClientImpl.a();
    }

    public NBNetUploadClient getUploadClient() {
        return DefaultNBNetUploadClient.a();
    }

    public NBNetLog getNBNetLog() {
        return NBNetLogFactory.a();
    }

    public void init() {
        DownloadCacheManager.a().b();
    }

    public NBNetConfigHelper getNBNetConfigHelper() {
        return DefaultNBNetConfigHelper.a();
    }
}
