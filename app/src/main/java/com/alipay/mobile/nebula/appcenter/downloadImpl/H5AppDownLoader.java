package com.alipay.mobile.nebula.appcenter.downloadImpl;

import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class H5AppDownLoader implements Runnable {
    public static final String TAG = "H5AppLoader";
    private static final int TIMEOUT = 10000;
    private List<H5DownloadCallback> callbackList;
    private H5DownloadRequest downloadRequest;
    private String downloadUrl;

    public H5AppDownLoader(H5DownloadRequest h5DownloadRequest, List<H5DownloadCallback> listener) {
        this.downloadRequest = h5DownloadRequest;
        this.callbackList = listener;
        if (this.downloadRequest != null) {
            this.downloadUrl = h5DownloadRequest.getDownloadUrl();
        }
    }

    public void run() {
        InputStream inputStream;
        String fileName;
        FileOutputStream fos;
        byte[] buffer;
        if (this.downloadRequest == null) {
            downLoadFail("downloadRequest == null");
        } else if (TextUtils.isEmpty(this.downloadRequest.getDownloadUrl())) {
            downLoadFail("downloadRequest.getDownloadUrl() == null");
            H5Log.d(TAG, "downloadRequest.getDownloadUrl() == null");
        } else {
            H5Log.d(TAG, "download app " + this.downloadRequest.getDownloadUrl());
            try {
                URLConnection conn = new URL(this.downloadRequest.getDownloadUrl()).openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                inputStream = conn.getInputStream();
                String fileName2 = this.downloadRequest.getFileName();
                if (fileName2 == null || "".equals(fileName2.trim())) {
                    fileName = this.downloadRequest.getDownloadUrl().substring(this.downloadRequest.getDownloadUrl().lastIndexOf("/"));
                } else {
                    fileName = "/" + fileName2;
                }
                String filePath = H5DownloadRequest.getDefaultDownloadDir(H5Utils.getContext()) + fileName;
                H5Log.d(TAG, "path:" + filePath);
                if (H5FileUtil.exists(new File(filePath))) {
                    downLoadSuc(filePath);
                } else if (H5FileUtil.create(filePath)) {
                    fos = null;
                    buffer = H5IOUtils.getBuf(1024);
                    try {
                        FileOutputStream fos2 = new FileOutputStream(filePath);
                        while (true) {
                            try {
                                int byteRead = inputStream.read(buffer);
                                if (byteRead != -1) {
                                    fos2.write(buffer, 0, byteRead);
                                } else {
                                    downLoadSuc(filePath);
                                    H5IOUtils.returnBuf(buffer);
                                    H5IOUtils.closeQuietly(fos2);
                                    H5IOUtils.closeQuietly(inputStream);
                                    return;
                                }
                            } catch (Exception e) {
                                e = e;
                                fos = fos2;
                                H5Log.e((String) TAG, "input error" + e);
                                downLoadFail(String.valueOf(e));
                                H5IOUtils.returnBuf(buffer);
                                H5IOUtils.closeQuietly(fos);
                                H5IOUtils.closeQuietly(inputStream);
                            } catch (Throwable th) {
                                th = th;
                                fos = fos2;
                                H5IOUtils.returnBuf(buffer);
                                H5IOUtils.closeQuietly(fos);
                                H5IOUtils.closeQuietly(inputStream);
                                throw th;
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        H5Log.e((String) TAG, "input error" + e);
                        downLoadFail(String.valueOf(e));
                        H5IOUtils.returnBuf(buffer);
                        H5IOUtils.closeQuietly(fos);
                        H5IOUtils.closeQuietly(inputStream);
                    }
                }
            } catch (Throwable e3) {
                H5Log.e((String) TAG, "download app exception." + e3);
                downLoadFail(String.valueOf(e3));
            }
        }
    }

    private void downLoadFail(String t) {
        if (this.callbackList != null) {
            for (H5DownloadCallback onFailed : this.callbackList) {
                onFailed.onFailed(null, 0, t);
            }
            H5DownLoadCallBackList.unRegisterCallbacks(this.downloadUrl);
        }
    }

    private void downLoadSuc(String filePath) {
        if (this.callbackList != null) {
            for (H5DownloadCallback onFinish : this.callbackList) {
                onFinish.onFinish(null, filePath);
            }
            H5DownLoadCallBackList.unRegisterCallbacks(this.downloadUrl);
        }
    }
}
