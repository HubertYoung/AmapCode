package com.alipay.android.phone.mobilecommon.multimedia.api;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APDecryptCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APCacheRecord;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APCacheReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APDecryptReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class MultimediaFileService extends ExternalService {
    public abstract void cancelLoad(String str);

    public abstract void cancelUp(String str);

    public abstract void decryptFile(APDecryptReq aPDecryptReq, APDecryptCallback aPDecryptCallback);

    public abstract boolean deleteFileCache(String str);

    @Deprecated
    public abstract APMultimediaTaskModel downLoad(APFileReq aPFileReq, APFileDownCallback aPFileDownCallback);

    public abstract APMultimediaTaskModel downLoad(APFileReq aPFileReq, APFileDownCallback aPFileDownCallback, String str);

    @Deprecated
    public abstract APMultimediaTaskModel downLoad(String str, APFileDownCallback aPFileDownCallback);

    public abstract APMultimediaTaskModel downLoad(String str, APFileDownCallback aPFileDownCallback, String str2);

    @Deprecated
    public abstract APMultimediaTaskModel downLoad(String str, String str2, APFileDownCallback aPFileDownCallback);

    public abstract APMultimediaTaskModel downLoad(String str, String str2, APFileDownCallback aPFileDownCallback, String str3);

    @Deprecated
    public abstract APFileDownloadRsp downLoadSync(APFileReq aPFileReq, APFileDownCallback aPFileDownCallback);

    public abstract APFileDownloadRsp downLoadSync(APFileReq aPFileReq, APFileDownCallback aPFileDownCallback, String str);

    public abstract APFileDownloadRsp downloadOffline(APFileReq aPFileReq);

    public abstract String getCacheFileNameByKey(String str);

    public abstract APMultimediaTaskModel getLoadTaskStatus(String str);

    public abstract APMultimediaTaskModel getLoadTaskStatusByCloudId(String str);

    public abstract APMultimediaTaskModel getTaskStatusByCloudId(String str);

    public abstract APMultimediaTaskModel getUpTaskStatus(String str);

    public abstract APMultimediaTaskModel getUpTaskStatusByCloudId(String str);

    public abstract APFileQueryResult queryCacheFile(String str);

    public abstract APCacheRecord queryCacheRecord(String str);

    public abstract APFileQueryResult queryEncryptCacheFile(String str);

    public abstract APFileQueryResult queryTempFile(String str);

    public abstract void registeLoadCallBack(String str, APFileDownCallback aPFileDownCallback);

    public abstract void registeUpCallBack(String str, APFileUploadCallback aPFileUploadCallback);

    public abstract int saveToCache(APCacheReq aPCacheReq);

    public abstract void unregisteLoadCallBack(String str, APFileDownCallback aPFileDownCallback);

    public abstract void unregisteUpCallBack(String str, APFileUploadCallback aPFileUploadCallback);

    @Deprecated
    public abstract APMultimediaTaskModel upLoad(APFileReq aPFileReq, APFileUploadCallback aPFileUploadCallback);

    public abstract APMultimediaTaskModel upLoad(APFileReq aPFileReq, APFileUploadCallback aPFileUploadCallback, String str);

    @Deprecated
    public abstract APMultimediaTaskModel upLoad(String str, APFileUploadCallback aPFileUploadCallback);

    public abstract APMultimediaTaskModel upLoad(String str, APFileUploadCallback aPFileUploadCallback, String str2);

    @Deprecated
    public abstract APFileUploadRsp upLoadSync(APFileReq aPFileReq, APFileUploadCallback aPFileUploadCallback);

    public abstract APFileUploadRsp upLoadSync(APFileReq aPFileReq, APFileUploadCallback aPFileUploadCallback, String str);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
