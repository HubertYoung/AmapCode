package com.alipay.android.phone.mobilecommon.multimedia.api;

import com.alipay.android.phone.mobilecommon.multimedia.material.APBizMaterialPackage;
import com.alipay.android.phone.mobilecommon.multimedia.material.APDownloadStatus;
import com.alipay.android.phone.mobilecommon.multimedia.material.APFalconAbility;
import com.alipay.android.phone.mobilecommon.multimedia.material.APFilterInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialDownloadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.APPackageInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APBizMaterialPackageQueryCallback;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCancelListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCompleteListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnProgressListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APPackageQueryCallback;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.List;

public abstract class MultimediaMaterialService extends ExternalService {
    public abstract void cancelDownloadMaterial(String str);

    public abstract void downloadMaterial(APMaterialDownloadRequest aPMaterialDownloadRequest);

    public abstract APFalconAbility getAbility();

    public abstract APBizMaterialPackage getBizMaterialPackage(String str, APBizMaterialPackageQueryCallback aPBizMaterialPackageQueryCallback);

    public abstract APMaterialInfo getMaterialInfo(String str);

    public abstract APDownloadStatus getMaterialStatus(String str);

    public abstract APPackageInfo getPackageInfo(String str, APPackageQueryCallback aPPackageQueryCallback);

    public abstract APBizMaterialPackage getPresetBizMaterialPackage(String str);

    public abstract List<APFilterInfo> getSupportedFilters();

    public abstract void registerDownloadCancelListener(String str, APOnCancelListener aPOnCancelListener);

    public abstract void registerDownloadCompleteListener(String str, APOnCompleteListener aPOnCompleteListener);

    public abstract void registerDownloadErrorListener(String str, APOnErrorListener aPOnErrorListener);

    public abstract void registerDownloadProgressListener(String str, APOnProgressListener aPOnProgressListener);

    public abstract void unregisterDownloadCancelListener(String str, APOnCancelListener aPOnCancelListener);

    public abstract void unregisterDownloadCompleteListener(String str, APOnCompleteListener aPOnCompleteListener);

    public abstract void unregisterDownloadErrorListener(String str, APOnErrorListener aPOnErrorListener);

    public abstract void unregisterDownloadProgressListener(String str, APOnProgressListener aPOnProgressListener);
}
