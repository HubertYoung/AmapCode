package com.autonavi.minimap.offline.auto.protocol.request;

import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientManager;
import com.autonavi.link.protocol.http.HttpProgresser;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkPackage;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadApkPackageRequest;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;

public class AutoUploadApkRequest extends UseCase<AutoUploadApkParam, AutoUploadApkResponse, Integer> {
    /* access modifiers changed from: private */
    public volatile boolean mCanceled = false;
    /* access modifiers changed from: private */
    public HttpClientManager mHttpClientManager = new HttpClientManager();
    /* access modifiers changed from: private */
    public int mLastProgress = -1;

    public static final class AutoUploadApkParam implements RequestValues {
        ATApkPackage mApkPackage;
        String mFilePath;

        public AutoUploadApkParam(String str, ATApkPackage aTApkPackage) {
            this.mFilePath = str;
            this.mApkPackage = aTApkPackage;
        }
    }

    public static final class AutoUploadApkResponse implements ResponseValue {
        public int code;
        public int progress;
    }

    public void cancel() {
        this.mCanceled = true;
    }

    private <T> String toJson(T t) {
        try {
            return JsonUtil.toString(t);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(AutoUploadApkParam autoUploadApkParam) {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        DiscoverInfo wifiDiscoverInfo = iAutoRemoteController != null ? iAutoRemoteController.getWifiDiscoverInfo() : null;
        if (wifiDiscoverInfo == null || !iAutoRemoteController.IsWifiConnected()) {
            getUseCaseCallback().onError(Integer.valueOf(301));
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(wifiDiscoverInfo.IP);
        sb.append(":");
        sb.append(wifiDiscoverInfo.httpPort);
        String sb2 = sb.toString();
        ATUploadApkPackageRequest aTUploadApkPackageRequest = new ATUploadApkPackageRequest();
        aTUploadApkPackageRequest.setApkInfo(autoUploadApkParam.mApkPackage);
        HashMap hashMap = new HashMap();
        hashMap.put("content", toJson(aTUploadApkPackageRequest));
        HashMap hashMap2 = new HashMap();
        hashMap2.put(new String[]{"uploadFile", autoUploadApkParam.mFilePath}, Long.valueOf(0));
        final long length = new File(autoUploadApkParam.mFilePath).length();
        this.mCanceled = false;
        this.mLastProgress = -1;
        try {
            byte[] postFiles = this.mHttpClientManager.postFiles(sb2, "/dataservice/uploadapk", hashMap, hashMap2, new HttpProgresser() {
                public final void onProgress(long j, long j2, float f) {
                    if (AutoUploadApkRequest.this.mCanceled) {
                        AutoUploadApkRequest.this.mHttpClientManager.cancel();
                        return;
                    }
                    AutoUploadApkResponse autoUploadApkResponse = new AutoUploadApkResponse();
                    autoUploadApkResponse.code = 1;
                    autoUploadApkResponse.progress = (int) (((((double) j2) * 1.0d) / ((double) length)) * 100.0d);
                    if (autoUploadApkResponse.progress != AutoUploadApkRequest.this.mLastProgress) {
                        AutoUploadApkRequest.this.mLastProgress = autoUploadApkResponse.progress;
                        AutoUploadApkRequest.this.getUseCaseCallback().onSuccess(autoUploadApkResponse);
                    }
                }
            });
            if (postFiles == null || postFiles.length <= 0) {
                getUseCaseCallback().onError(Integer.valueOf(4));
                return;
            }
            AutoUploadApkResponse autoUploadApkResponse = (AutoUploadApkResponse) JsonUtil.fromString(new String(postFiles, Charset.forName("utf-8")), AutoUploadApkResponse.class);
            if (autoUploadApkResponse.code == 1) {
                autoUploadApkResponse.code = 2;
                getUseCaseCallback().onSuccess(autoUploadApkResponse);
                return;
            }
            getUseCaseCallback().onError(Integer.valueOf(4));
        } catch (Exception e) {
            e.printStackTrace();
            getUseCaseCallback().onError(Integer.valueOf(4));
        }
    }
}
