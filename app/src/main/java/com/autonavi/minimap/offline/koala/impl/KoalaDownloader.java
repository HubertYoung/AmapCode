package com.autonavi.minimap.offline.koala.impl;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.exception.KoalaDownloadAccessException;
import com.autonavi.minimap.offline.koala.exception.KoalaDownloadSizeException;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloader;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloader.Callback;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloader.Config;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class KoalaDownloader implements IKoalaDownloader {
    protected Callback mCallback;
    protected volatile boolean mCancel = false;
    protected Config mConfig;
    protected int mDownloadId;
    protected String mLocalPath;
    protected volatile boolean mRunning = false;
    protected long mTotalBytes = 0;
    protected String mUrl;

    public void setConfig(Config config) {
        this.mConfig = config;
    }

    public void setDownloadData(int i, String str, String str2) {
        if (this.mRunning) {
            throw new KoalaDownloadAccessException("the download is running, don't set download data");
        }
        checkDownloadData(str, str2);
        this.mDownloadId = i;
        this.mUrl = str;
        this.mLocalPath = str2;
        this.mTotalBytes = 0;
        this.mCancel = false;
    }

    public long getTotalBytes() throws KoalaDownloadSizeException {
        checkDownloadData(this.mUrl, this.mLocalPath);
        if (this.mTotalBytes > 0) {
            return this.mTotalBytes;
        }
        long j = 0;
        int i = 0;
        while (true) {
            try {
                HttpURLConnection createConnection = createConnection(this.mUrl);
                createConnection.setRequestMethod(RequestMethodConstants.HEAD_METHOD);
                createConnection.connect();
                if (createConnection.getResponseCode() == 200) {
                    j = Long.parseLong(createConnection.getHeaderField("Content-Length"));
                }
                createConnection.disconnect();
            } catch (Throwable th) {
                th.printStackTrace();
                i++;
                if (i >= this.mConfig.maxAutoRetryTimes) {
                    break;
                }
            }
        }
        if (j <= 0) {
            throw new KoalaDownloadSizeException(KoalaUtils.formatString("can't know the size of the download file(%s)!", this.mUrl));
        }
        this.mTotalBytes = j;
        return j;
    }

    private long getDownloadBytes() {
        File file = new File(this.mLocalPath);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection createConnection(String str) throws IOException {
        checkConfig(this.mConfig);
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(this.mConfig.connectTimeoutMillis);
        httpURLConnection.setReadTimeout(this.mConfig.readTimeoutMillis);
        httpURLConnection.setChunkedStreamingMode(0);
        addHeader(httpURLConnection);
        return httpURLConnection;
    }

    /* access modifiers changed from: protected */
    public void addHeader(HttpURLConnection httpURLConnection) {
        StringBuilder sb = new StringBuilder("Android ");
        sb.append(VERSION.RELEASE);
        httpURLConnection.setRequestProperty(H5AppHttpRequest.HEADER_UA, sb.toString());
        httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
    }

    /* access modifiers changed from: protected */
    public void resumeBrokenDownloads(HttpURLConnection httpURLConnection, long j) {
        if (j > 0) {
            StringBuilder sb = new StringBuilder("bytes=");
            sb.append(j);
            sb.append("-");
            httpURLConnection.setRequestProperty("Range", sb.toString());
        }
    }

    private boolean isServerSupportResume(HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("Accept-Ranges");
        if (headerField != null) {
            return headerField.contains("bytes");
        }
        String headerField2 = httpURLConnection.getHeaderField("Content-Range");
        return headerField2 != null && headerField2.contains("bytes");
    }

    private void checkConfig(Config config) {
        if (config == null) {
            throw new IllegalArgumentException("config is null! ");
        }
    }

    private void checkDownloadData(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("url is empty!");
        } else if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("localPath is empty!");
        }
    }

    public void run(Callback callback) {
        if (this.mRunning) {
            throw new KoalaDownloadAccessException("the download is running, don't run again");
        }
        this.mCallback = callback;
        checkDownloadData(this.mUrl, this.mLocalPath);
        this.mRunning = true;
        this.mCancel = false;
        try {
            loop();
        } finally {
            this.mRunning = false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x006c A[EDGE_INSN: B:35:0x006c->B:28:0x006c ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void loop() {
        /*
            r14 = this;
            r0 = 0
            r1 = 0
            r4 = r0
            r2 = 0
            r3 = 0
        L_0x0005:
            r5 = 1
            long r9 = r14.getTotalBytes()     // Catch:{ Throwable -> 0x0061 }
            long r6 = r14.getDownloadBytes()     // Catch:{ Throwable -> 0x0061 }
            java.lang.String r8 = r14.mUrl     // Catch:{ Throwable -> 0x0061 }
            java.net.HttpURLConnection r8 = r14.createConnection(r8)     // Catch:{ Throwable -> 0x0061 }
            java.lang.String r11 = "GET"
            r8.setRequestMethod(r11)     // Catch:{ Throwable -> 0x0061 }
            r14.resumeBrokenDownloads(r8, r6)     // Catch:{ Throwable -> 0x0061 }
            r8.connect()     // Catch:{ Throwable -> 0x0061 }
            boolean r11 = r14.mCancel     // Catch:{ Throwable -> 0x0061 }
            if (r11 == 0) goto L_0x0024
            goto L_0x006c
        L_0x0024:
            int r11 = r8.getResponseCode()     // Catch:{ Throwable -> 0x0061 }
            r12 = 200(0xc8, float:2.8E-43)
            if (r11 == r12) goto L_0x0030
            r12 = 206(0xce, float:2.89E-43)
            if (r11 != r12) goto L_0x0065
        L_0x0030:
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Callback r4 = r14.mCallback     // Catch:{ Throwable -> 0x0061 }
            if (r4 == 0) goto L_0x0045
            if (r3 != 0) goto L_0x0045
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Callback r3 = r14.mCallback     // Catch:{ Throwable -> 0x0041 }
            int r4 = r14.mDownloadId     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r11 = r14.mUrl     // Catch:{ Throwable -> 0x0041 }
            r3.onConnect(r4, r11)     // Catch:{ Throwable -> 0x0041 }
            r3 = 1
            goto L_0x0045
        L_0x0041:
            r3 = move-exception
            r4 = r3
            r3 = 1
            goto L_0x0062
        L_0x0045:
            boolean r4 = r14.isServerSupportResume(r8)     // Catch:{ Throwable -> 0x0061 }
            java.io.FileOutputStream r11 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0061 }
            java.lang.String r12 = r14.mLocalPath     // Catch:{ Throwable -> 0x0061 }
            r11.<init>(r12, r4)     // Catch:{ Throwable -> 0x0061 }
            if (r4 != 0) goto L_0x0054
            r6 = 0
        L_0x0054:
            r12 = r6
            java.io.InputStream r7 = r8.getInputStream()     // Catch:{ Throwable -> 0x0061 }
            r6 = r14
            r8 = r11
            r11 = r12
            r6.writeToFile(r7, r8, r9, r11)     // Catch:{ Throwable -> 0x0061 }
            r4 = r0
            goto L_0x006c
        L_0x0061:
            r4 = move-exception
        L_0x0062:
            r4.printStackTrace()
        L_0x0065:
            int r2 = r2 + r5
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Config r5 = r14.mConfig
            int r5 = r5.maxAutoRetryTimes
            if (r2 < r5) goto L_0x0005
        L_0x006c:
            if (r4 == 0) goto L_0x0081
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Callback r0 = r14.mCallback
            if (r0 == 0) goto L_0x0081
            boolean r0 = r14.mCancel
            if (r0 != 0) goto L_0x0081
            r14.mRunning = r1
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Callback r0 = r14.mCallback
            int r1 = r14.mDownloadId
            java.lang.String r2 = r14.mUrl
            r0.onError(r1, r2, r4)
        L_0x0081:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.koala.impl.KoalaDownloader.loop():void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeToFile(java.io.InputStream r17, java.io.OutputStream r18, long r19, long r21) throws java.lang.Throwable {
        /*
            r16 = this;
            r1 = r16
            r2 = 0
            java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0084 }
            r4 = r18
            r3.<init>(r4)     // Catch:{ all -> 0x0084 }
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ all -> 0x0081 }
            r5 = r17
            r4.<init>(r5)     // Catch:{ all -> 0x0081 }
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Config r2 = r1.mConfig     // Catch:{ all -> 0x007f }
            int r2 = r2.bufferSize     // Catch:{ all -> 0x007f }
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x007f }
            r5 = r21
        L_0x0019:
            int r7 = r4.read(r2)     // Catch:{ all -> 0x007f }
            r8 = 0
            if (r7 <= 0) goto L_0x0041
            boolean r9 = r1.mCancel     // Catch:{ all -> 0x007f }
            if (r9 == 0) goto L_0x002b
            r3.close()
            r4.close()
            return
        L_0x002b:
            r3.write(r2, r8, r7)     // Catch:{ all -> 0x007f }
            long r7 = (long) r7     // Catch:{ all -> 0x007f }
            long r5 = r5 + r7
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Callback r7 = r1.mCallback     // Catch:{ all -> 0x007f }
            if (r7 == 0) goto L_0x0019
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Callback r9 = r1.mCallback     // Catch:{ all -> 0x007f }
            int r10 = r1.mDownloadId     // Catch:{ all -> 0x007f }
            java.lang.String r11 = r1.mUrl     // Catch:{ all -> 0x007f }
            r12 = r19
            r14 = r5
            r9.onProgress(r10, r11, r12, r14)     // Catch:{ all -> 0x007f }
            goto L_0x0019
        L_0x0041:
            r3.flush()     // Catch:{ all -> 0x007f }
            int r2 = (r5 > r19 ? 1 : (r5 == r19 ? 0 : -1))
            if (r2 == 0) goto L_0x0069
            com.autonavi.minimap.offline.koala.exception.KoalaDownloadInvalidException r2 = new com.autonavi.minimap.offline.koala.exception.KoalaDownloadInvalidException     // Catch:{ all -> 0x007f }
            java.lang.String r7 = "download bytes[%d] not equal total[%d] in the download file(%s)"
            r11 = 3
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x007f }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x007f }
            r11[r8] = r5     // Catch:{ all -> 0x007f }
            r5 = 1
            java.lang.Long r6 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x007f }
            r11[r5] = r6     // Catch:{ all -> 0x007f }
            r5 = 2
            java.lang.String r6 = r1.mUrl     // Catch:{ all -> 0x007f }
            r11[r5] = r6     // Catch:{ all -> 0x007f }
            java.lang.String r5 = com.autonavi.minimap.offline.koala.KoalaUtils.formatString(r7, r11)     // Catch:{ all -> 0x007f }
            r2.<init>(r5)     // Catch:{ all -> 0x007f }
            throw r2     // Catch:{ all -> 0x007f }
        L_0x0069:
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Callback r2 = r1.mCallback     // Catch:{ all -> 0x007f }
            if (r2 == 0) goto L_0x0078
            r1.mRunning = r8     // Catch:{ all -> 0x007f }
            com.autonavi.minimap.offline.koala.intf.IKoalaDownloader$Callback r2 = r1.mCallback     // Catch:{ all -> 0x007f }
            int r5 = r1.mDownloadId     // Catch:{ all -> 0x007f }
            java.lang.String r6 = r1.mUrl     // Catch:{ all -> 0x007f }
            r2.onComplete(r5, r6)     // Catch:{ all -> 0x007f }
        L_0x0078:
            r3.close()
            r4.close()
            return
        L_0x007f:
            r0 = move-exception
            goto L_0x0087
        L_0x0081:
            r0 = move-exception
            r4 = r2
            goto L_0x0087
        L_0x0084:
            r0 = move-exception
            r3 = r2
            r4 = r3
        L_0x0087:
            r2 = r0
            if (r3 == 0) goto L_0x008d
            r3.close()
        L_0x008d:
            if (r4 == 0) goto L_0x0092
            r4.close()
        L_0x0092:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.koala.impl.KoalaDownloader.writeToFile(java.io.InputStream, java.io.OutputStream, long, long):void");
    }

    public void cancel() {
        this.mCancel = true;
        if (this.mCallback != null) {
            this.mRunning = false;
            this.mCallback.onCancel(this.mDownloadId, this.mUrl);
        }
    }

    public boolean isCanceled() {
        return this.mCancel;
    }

    public boolean isRunning() {
        return this.mRunning;
    }
}
