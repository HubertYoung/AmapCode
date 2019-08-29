package com.alipay.android.phone.mobilecommon.multimediabiz.biz.service;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaMaterialService;
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
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.FRWBroadcastReceiver;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.material.MaterialManager;
import java.util.List;

public class MultimediaMaterialServiceImpl extends MultimediaMaterialService {
    private MaterialManager mManager;

    public void downloadMaterial(APMaterialDownloadRequest request) {
        this.mManager.downloadMaterial(request);
    }

    public void cancelDownloadMaterial(String id) {
        this.mManager.cancelDownloadMaterial(id);
    }

    public void registerDownloadProgressListener(String taskId, APOnProgressListener listener) {
        this.mManager.registerDownloadProgressListener(taskId, listener);
    }

    public void unregisterDownloadProgressListener(String taskId, APOnProgressListener listener) {
        this.mManager.unregisterDownloadProgressListener(taskId, listener);
    }

    public void registerDownloadCompleteListener(String taskId, APOnCompleteListener listener) {
        this.mManager.registerDownloadCompleteListener(taskId, listener);
    }

    public void unregisterDownloadCompleteListener(String taskId, APOnCompleteListener listener) {
        this.mManager.unregisterDownloadCompleteListener(taskId, listener);
    }

    public void registerDownloadErrorListener(String taskId, APOnErrorListener listener) {
        this.mManager.registerDownloadErrorListener(taskId, listener);
    }

    public void unregisterDownloadErrorListener(String taskId, APOnErrorListener listener) {
        this.mManager.unregisterDownloadErrorListener(taskId, listener);
    }

    public void registerDownloadCancelListener(String taskId, APOnCancelListener listener) {
        this.mManager.registerDownloadCancelListener(taskId, listener);
    }

    public void unregisterDownloadCancelListener(String taskId, APOnCancelListener listener) {
        this.mManager.unregisterDownloadCancelListener(taskId, listener);
    }

    public APBizMaterialPackage getPresetBizMaterialPackage(String businessId) {
        return this.mManager.getPresetBizMaterialPackage(businessId);
    }

    public APDownloadStatus getMaterialStatus(String id) {
        return this.mManager.getMaterialStatus(id);
    }

    public APBizMaterialPackage getBizMaterialPackage(String businessId, APBizMaterialPackageQueryCallback cb) {
        return this.mManager.getBizMaterialPackage(businessId, cb);
    }

    public APPackageInfo getPackageInfo(String id, APPackageQueryCallback callback) {
        return this.mManager.getPackageInfo(id, callback);
    }

    public APMaterialInfo getMaterialInfo(String id) {
        return this.mManager.getMaterialInfo(id);
    }

    public List<APFilterInfo> getSupportedFilters() {
        return this.mManager.getSupportedFilters();
    }

    public APFalconAbility getAbility() {
        return this.mManager.getAbility();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.mManager = MaterialManager.get();
        FRWBroadcastReceiver.initOnce();
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
