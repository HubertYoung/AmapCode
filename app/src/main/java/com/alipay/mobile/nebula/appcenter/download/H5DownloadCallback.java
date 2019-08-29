package com.alipay.mobile.nebula.appcenter.download;

import android.support.annotation.Nullable;
import com.alipay.mobile.nebula.callback.H5AppInstallCallback;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5DownloadCallback {
    private static final String TAG = "H5DownloadCallback";
    /* access modifiers changed from: private */
    public H5AppInstallCallback callback;
    private boolean needInstall;
    private boolean urgentInstall;

    public H5DownloadCallback() {
        this(false, false, null);
    }

    public H5DownloadCallback(boolean needInstall2, boolean urgentInstall2, @Nullable H5AppInstallCallback installCallback) {
        this.needInstall = needInstall2;
        this.urgentInstall = urgentInstall2;
        this.callback = installCallback;
    }

    public void onPrepare(@Nullable H5DownloadRequest h5DownloadRequest) {
        if (h5DownloadRequest != null) {
            H5Log.d(TAG, "onPrepare: " + h5DownloadRequest.getDownloadUrl());
        }
    }

    public void onProgress(@Nullable H5DownloadRequest h5DownloadRequest, int progress) {
    }

    public void onCancel(@Nullable H5DownloadRequest h5DownloadRequest) {
        if (h5DownloadRequest != null) {
            H5Log.d(TAG, "onCancel: " + h5DownloadRequest.getDownloadUrl());
        }
    }

    public void onFinish(@Nullable H5DownloadRequest h5DownloadRequest, String savePath) {
        if (h5DownloadRequest != null) {
            H5Log.d(TAG, "onFinish: " + h5DownloadRequest.getDownloadUrl() + ", savePath: " + savePath);
            if (this.needInstall) {
                final H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                if (provider != null) {
                    final String appId = h5DownloadRequest.getAppId();
                    final String version = h5DownloadRequest.getVersion();
                    H5Utils.runNotOnMain(this.urgentInstall ? H5ThreadType.URGENT_DISPLAY : H5ThreadType.IO, new Runnable() {
                        public void run() {
                            provider.installApp(appId, version, H5DownloadCallback.this.callback);
                        }
                    });
                }
            }
        }
    }

    public void onFailed(@Nullable H5DownloadRequest h5DownloadRequest, int errorCode, String errorMsg) {
        if (h5DownloadRequest != null) {
            H5Log.d(TAG, "onFinish: " + h5DownloadRequest.getDownloadUrl() + ", errorCode: " + errorCode + ", errorMsg: " + errorMsg);
        }
    }
}
