package com.autonavi.minimap.nativesupport.platform;

import android.os.SystemClock;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.core.network.inter.response.InputStreamResponse;
import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.minimap.ackor.ackorplatform.IHttpRequest;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.Closeable;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpRequestImpl extends IHttpRequest implements bpl<InputStreamResponse> {
    private static final int BUFFER_SIZE = 4096;
    private static final int ERROR_NETWORK = -2;
    private static final int IOERROR_NETWORK = -3;
    private static final String LOGFILENAME = "offlinedownload";
    private static final long LOGFILE_MAXLENGTH = 512000;
    private static final String LOGTYPE_CANCEL = "cancel";
    private static final String LOGTYPE_NETWORKERROR = "network_error";
    private static final String LOGTYPE_OFFLINEERROR = "offline_error";
    private static final int USER_CANCELD = -1;
    private bph baseRequest;
    protected bpk mBaseResponse;
    private volatile boolean mCancel;
    private Map<String, String> mHeaders = new HashMap();
    int mLength = 0;
    private long mPtrTag;
    int mStatusCode = 0;
    private long mTimeOut = 0;
    private int mTimeoutInSecond = 30;
    private long mTotalLength = 0;

    enum RequestType {
        RequestTypeHead,
        RequestTypeGet,
        RequestTypePost
    }

    private int getBufferSize(long j) {
        if (j < 8192) {
            return 8192;
        }
        int i = (j > 16384 ? 1 : (j == 16384 ? 0 : -1));
        return i < 0 ? (int) j : i > 0 ? 16384 : 8192;
    }

    /* access modifiers changed from: protected */
    public void appendParams(bph bph) {
    }

    /* access modifiers changed from: protected */
    public void processRequest(String str, bph bph) {
    }

    public void start(String str, RequestType requestType) {
        this.mCancel = false;
        this.mStatusCode = 0;
        if (requestType == RequestType.RequestTypeGet) {
            this.baseRequest = new bpf();
        } else if (requestType == RequestType.RequestTypePost) {
            this.baseRequest = new bpj();
        } else {
            this.baseRequest = new bpg();
        }
        this.baseRequest.setUrl(str);
        appendHeaders(this.baseRequest);
        appendParams(this.baseRequest);
        this.baseRequest.setTimeout(this.mTimeoutInSecond * 1000);
        processRequest(str, this.baseRequest);
        HttpRequestClient.getInstance().send(this.baseRequest, (bpl<T>) this);
        AMapLog.logFatalNative(AMapLog.GROUP_COMMON, "P0003", ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, "ackor request, url: ".concat(String.valueOf(str)));
    }

    public void cancel() {
        this.mCancel = true;
        HttpRequestClient.getInstance().cancel(this.baseRequest);
    }

    public String getResponseHeader(String str) {
        if (this.mBaseResponse == null) {
            return null;
        }
        if (DebugLog.isDebug()) {
            Map<String, List<String>> headers = this.mBaseResponse.getHeaders();
            if (headers != null) {
                Set<String> keySet = headers.keySet();
                if (keySet != null) {
                    for (String isEmpty : keySet) {
                        TextUtils.isEmpty(isEmpty);
                    }
                }
            }
        }
        return this.mBaseResponse.getHeader(str);
    }

    public int getResponseCode() {
        if (this.mStatusCode != 0) {
            return this.mStatusCode;
        }
        if (this.mBaseResponse == null) {
            return -1;
        }
        return this.mBaseResponse.getStatusCode();
    }

    public void setUserData(long j) {
        this.mPtrTag = j;
    }

    public long getUserData() {
        return this.mPtrTag;
    }

    public IHttpRequest addHeader(String str, String str2) {
        this.mHeaders.put(str, str2);
        return this;
    }

    public void setTimeOut(int i) {
        this.mTimeoutInSecond = i;
        if (this.mTimeoutInSecond < 0) {
            throw new IllegalArgumentException("timeout must large than 0 second.");
        }
    }

    public void head(String str) {
        start(str, RequestType.RequestTypeHead);
    }

    public void get(String str) {
        start(str, RequestType.RequestTypeGet);
    }

    public void post(String str, String str2, int i) {
        start(str, RequestType.RequestTypePost);
    }

    /* access modifiers changed from: protected */
    public void appendHeaders(bph bph) {
        for (Entry next : this.mHeaders.entrySet()) {
            bph.addHeader((String) next.getKey(), (String) next.getValue());
        }
    }

    public void onSuccess(InputStreamResponse inputStreamResponse) {
        long j;
        long elapsedRealtime;
        int read;
        this.mBaseResponse = inputStreamResponse;
        bph request = this.mBaseResponse.getRequest();
        int method = request != null ? request.getMethod() : -1;
        InputStream bodyInputStream = this.mBaseResponse.getBodyInputStream();
        if (bodyInputStream == null || method == 2) {
            onRequestFinished();
            return;
        }
        byte[] bArr = new byte[4096];
        long j2 = 0;
        while (true) {
            try {
                elapsedRealtime = SystemClock.elapsedRealtime();
                try {
                    read = bodyInputStream.read(bArr);
                    if (read > 0 && !this.mCancel) {
                        this.mTotalLength += (long) read;
                        onRequestReceiveData(bArr, read);
                        j2 = elapsedRealtime;
                    }
                } catch (Exception e) {
                    e = e;
                    j = elapsedRealtime;
                    try {
                        this.mTimeOut = j - SystemClock.elapsedRealtime();
                        e.printStackTrace();
                        this.mStatusCode = -3;
                        writeLogToFile(LOGTYPE_OFFLINEERROR, e);
                        onRequestFailed(-3);
                    } finally {
                        ahe.a((Closeable) bodyInputStream);
                    }
                }
            } catch (Exception e2) {
                e = e2;
                j = j2;
                this.mTimeOut = j - SystemClock.elapsedRealtime();
                e.printStackTrace();
                this.mStatusCode = -3;
                writeLogToFile(LOGTYPE_OFFLINEERROR, e);
                onRequestFailed(-3);
            }
        }
        j = elapsedRealtime - SystemClock.elapsedRealtime();
        try {
            if (!this.mCancel || read == -1) {
                onRequestFinished();
            } else {
                this.mStatusCode = -1;
                this.mLength = read;
                writeLogToFile("cancel", null);
                onRequestFailed(-1);
            }
            ahe.a((Closeable) bodyInputStream);
        } catch (Exception e3) {
            e = e3;
            this.mTimeOut = j - SystemClock.elapsedRealtime();
            e.printStackTrace();
            this.mStatusCode = -3;
            writeLogToFile(LOGTYPE_OFFLINEERROR, e);
            onRequestFailed(-3);
        }
    }

    public void onFailure(bph bph, ResponseException responseException) {
        if (this.mStatusCode == 0) {
            this.mStatusCode = responseException.errorCode;
        }
        writeLogToFile(LOGTYPE_OFFLINEERROR, responseException.exception);
        onRequestFailed(this.mStatusCode);
    }

    private void writeLogToFile(String str, Exception exc) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            StringBuilder sb = new StringBuilder("time:");
            sb.append(getTime());
            sb.append("\n");
            stringBuffer.append(sb.toString());
            if (this.baseRequest != null) {
                StringBuilder sb2 = new StringBuilder("request url :");
                sb2.append(this.baseRequest.getUrl());
                sb2.append("\n");
                stringBuffer.append(sb2.toString());
            }
            StringBuilder sb3 = new StringBuilder("read time :");
            sb3.append(Math.abs(this.mTimeOut));
            sb3.append("\n");
            stringBuffer.append(sb3.toString());
            StringBuilder sb4 = new StringBuilder("read total length :");
            sb4.append(this.mTotalLength);
            sb4.append("\n");
            stringBuffer.append(sb4.toString());
            StringBuilder sb5 = new StringBuilder("content length ");
            sb5.append(this.mBaseResponse.getContentLength());
            sb5.append("\n");
            stringBuffer.append(sb5.toString());
            if ("cancel".equals(str)) {
                stringBuffer.append("errortype:cancel");
                stringBuffer.append("\n");
                if (this.mLength != 0) {
                    StringBuilder sb6 = new StringBuilder("length :");
                    sb6.append(this.mLength);
                    stringBuffer.append(sb6.toString());
                    stringBuffer.append("\n");
                }
            } else if (LOGTYPE_OFFLINEERROR.equals(str)) {
                stringBuffer.append("errortype:offline");
                stringBuffer.append("\n");
            } else if (LOGTYPE_NETWORKERROR.equals(str)) {
                stringBuffer.append("errortype:network");
                stringBuffer.append("\n");
            }
            if (this.baseRequest != null) {
                StringBuilder sb7 = new StringBuilder("method :");
                sb7.append(this.baseRequest.getMethod());
                stringBuffer.append(sb7.toString());
                stringBuffer.append("\n");
            }
            if (exc != null) {
                StringBuilder sb8 = new StringBuilder("exception :");
                sb8.append(getStackTrace(exc));
                stringBuffer.append(sb8.toString());
                stringBuffer.append("\n");
            }
            if (this.mStatusCode != 0) {
                StringBuilder sb9 = new StringBuilder("mStatusCode :");
                sb9.append(this.mStatusCode);
                stringBuffer.append(sb9.toString());
                stringBuffer.append("\n");
            }
            String upLoadLogPath = LogPathManager.getUpLoadLogPath();
            if (!TextUtils.isEmpty(upLoadLogPath)) {
                FileUtil.saveLogToPath(stringBuffer.toString(), upLoadLogPath);
            }
        } catch (Exception unused) {
        }
    }

    private String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
    }

    private String getStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        try {
            th.printStackTrace(printWriter);
            return stringWriter.toString();
        } finally {
            printWriter.close();
        }
    }
}
