package com.alipay.mobile.nebula.appcenter.downloadImpl;

import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.appcenter.download.H5ExternalDownloadManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.List;

public class H5AppDownLoadImpl implements H5ExternalDownloadManager {
    private static final String TAG = "H5AppDownLoadImpl";

    public void addDownload(H5DownloadRequest h5DownloadRequest, H5DownloadCallback callback) {
        H5Log.d(TAG, "addDownload");
        if (h5DownloadRequest == null) {
            return;
        }
        if (h5DownloadRequest.getDownloadUrl() == null || "".equalsIgnoreCase(h5DownloadRequest.getDownloadUrl().trim())) {
            H5Log.d(TAG, "download url is empty");
            callback.onFailed(h5DownloadRequest, 9999, "download failed,the url is empty");
            return;
        }
        H5DownLoadCallBackList.registerCallback(h5DownloadRequest.getDownloadUrl(), callback);
        List callbackList = null;
        if (!(h5DownloadRequest.getDownloadUrl() == null || H5DownLoadCallBackList.callbackData == null)) {
            callbackList = H5DownLoadCallBackList.callbackData.get(h5DownloadRequest.getDownloadUrl());
        }
        if (callbackList == null) {
            H5Log.d(TAG, "callbackList==null");
        } else {
            H5Utils.getExecutor("RPC").execute(new H5AppDownLoader(h5DownloadRequest, callbackList));
        }
    }

    public void cancel(String downloadUrl) {
    }

    public boolean isDownloading(String downloadUrl) {
        return H5DownLoadCallBackList.isDownloadTaskExists(downloadUrl);
    }
}
