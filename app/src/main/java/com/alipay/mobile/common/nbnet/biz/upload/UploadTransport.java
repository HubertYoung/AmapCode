package com.alipay.mobile.common.nbnet.biz.upload;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetCancelException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetProtocolException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetServerLimitFlowException;
import com.alipay.mobile.common.nbnet.biz.io.LengthInputStream;
import com.alipay.mobile.common.nbnet.biz.log.MonitorLogUtil;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.netlib.BasicNBNetContext;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetConnectionEntity;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetConntionManager;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetConntionManagerFactory;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetInputStream;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetOutputStream;
import com.alipay.mobile.common.nbnet.biz.netlib.NBNetReqConn;
import com.alipay.mobile.common.nbnet.biz.transport.Transport;
import com.alipay.mobile.common.nbnet.biz.util.IOUtils;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.nbnet.biz.util.ProtocolUtils;
import com.alipay.mobile.common.nbnet.biz.util.ServerLimitedFlowHelper;
import com.alipay.mobile.common.nbnet.biz.util.URLConfigUtil;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UploadTransport implements Transport<UploadRequestEntity, UploadResponseEntity> {
    private NBNetConnectionEntity a;
    private UploadRequestEntity b;
    private BasicNBNetContext c;
    private boolean d = false;
    private int e;
    private NBNetException f;
    private Lock g = new ReentrantLock();
    private int h = 0;
    private int i = 0;
    private boolean j = false;

    public final void a(UploadRequestEntity request) {
        OutputStream requestBody;
        long startTime;
        if (this.d) {
            throw new NBNetCancelException("Request released or abort");
        }
        this.b = request;
        requestBody = k();
        this.g.lock();
        startTime = System.currentTimeMillis();
        try {
            this.b.a(requestBody);
            try {
                requestBody.flush();
            } catch (Throwable e2) {
                NBNetLogCat.d("UploadTransport", "flush exception: " + e2.toString());
            }
            if (this.b.i()) {
                this.b.g();
            }
            this.g.unlock();
            if (requestBody instanceof NBNetOutputStream) {
                this.i = ((NBNetOutputStream) requestBody).a();
            }
            long writeTime = System.currentTimeMillis() - startTime;
            MonitorLogUtil.f((NBNetContext) this.c, writeTime);
            NBNetLogCat.a((String) "UploadTransport", "writeRequestBody timing: " + writeTime);
            return;
        } catch (Throwable e3) {
            NBNetLogCat.d("UploadTransport", "flush exception: " + e3.toString());
        }
        if (this.b.i()) {
            this.b.g();
        }
        this.g.unlock();
        if (requestBody instanceof NBNetOutputStream) {
            this.i = ((NBNetOutputStream) requestBody).a();
        }
        long writeTime2 = System.currentTimeMillis() - startTime;
        MonitorLogUtil.f((NBNetContext) this.c, writeTime2);
        NBNetLogCat.a((String) "UploadTransport", "writeRequestBody timing: " + writeTime2);
    }

    public final synchronized void a() {
        if (this.d) {
            throw new NBNetCancelException("Request released or abort");
        } else if (this.a == null) {
            NBNetConntionManager conntionManager = NBNetConntionManagerFactory.a();
            URL uploadServerURL = URLConfigUtil.b();
            NBNetReqConn nbNetReqConn = new NBNetReqConn();
            nbNetReqConn.a = uploadServerURL.getHost();
            nbNetReqConn.b = NBNetCommonUtil.a(uploadServerURL.getPort(), uploadServerURL.getProtocol());
            if (TextUtils.equals(uploadServerURL.getProtocol(), "https") || n()) {
                nbNetReqConn.c = true;
            }
            nbNetReqConn.e = 2;
            this.a = conntionManager.a(nbNetReqConn, m());
            this.e = this.a.c().i().getSendBufferSize();
            NBNetLogCat.a((String) "UploadTransport", "sendBufferSize: " + this.e);
            if (this.d && this.a != null) {
                d();
                throw new NBNetCancelException("Request released or abort");
            }
        }
    }

    private OutputStream k() {
        if (this.d) {
            throw new NBNetCancelException("Request released or abort");
        }
        if (this.a == null) {
            a();
        }
        return this.a.b();
    }

    public final UploadResponseEntity b() {
        UploadResponseEntity responseEntity = null;
        long startReadTime = System.currentTimeMillis();
        try {
            UploadResponseEntity responseEntity2 = l();
            if (responseEntity2 == null) {
                this.b.g();
            }
            this.b.c();
            MonitorLogUtil.m(this.c, System.currentTimeMillis() - startReadTime);
            return responseEntity2;
        } catch (Throwable th) {
            if (responseEntity == null) {
                this.b.g();
            }
            this.b.c();
            MonitorLogUtil.m(this.c, System.currentTimeMillis() - startReadTime);
            throw th;
        }
    }

    private UploadResponseEntity l() {
        if (this.d) {
            throw new NBNetCancelException("Request released or abort");
        }
        InputStream responseBody = c();
        try {
            return a(responseBody);
        } finally {
            if (responseBody instanceof NBNetInputStream) {
                this.h = ((NBNetInputStream) responseBody).a();
            } else {
                r3 = "UploadTransport";
                r4 = "responseBody not instance of NBNetInputStream";
                NBNetLogCat.d(r3, r4);
            }
        }
    }

    @NonNull
    private UploadResponseEntity a(InputStream responseBody) {
        while (!this.d) {
            Map headers = ProtocolUtils.a(responseBody);
            if (this.b.l() == -1) {
                this.b.a(System.currentTimeMillis() - this.b.k());
                MonitorLogUtil.g((NBNetContext) this.c, this.b.l());
            }
            String responseCode = headers.get("responseCode");
            if (TextUtils.equals(responseCode, "100")) {
                b(headers);
                a(headers, this.b.b());
            } else {
                NBNetLogCat.a((String) "UploadTransport", "response header: " + headers.toString());
                a(responseCode, headers);
                a(headers, this.b.b());
                UploadResponseEntity responseEntity = a(responseBody, headers);
                if (responseEntity != null) {
                    return responseEntity;
                }
                String status = headers.get("x-arup-file-status");
                if (!TextUtils.equals(status, CaptureParam.ACTION_DONE_CAPTURE)) {
                    NBNetLogCat.a((String) "UploadTransport", "Illegal status. detail : " + status);
                    throw new NBNetProtocolException("Illegal status. detail : " + status);
                }
                String fileId = b(headers);
                if (TextUtils.isEmpty(fileId)) {
                    throw new NBNetProtocolException("fileId maybe empty");
                }
                UploadResponseEntity doneResponseEntity = b(responseBody, headers);
                doneResponseEntity.a = fileId;
                UploadActionSession uploadActionSession = this.b.d();
                if (this.b.f() <= 0) {
                    doneResponseEntity.e = true;
                } else {
                    doneResponseEntity.e = false;
                }
                uploadActionSession.i();
                doneResponseEntity.c = uploadActionSession;
                return doneResponseEntity;
            }
        }
        throw new NBNetCancelException("Request released or abort");
    }

    private static void a(String responseCode, Map<String, String> headers) {
        ServerLimitedFlowHelper.a(headers, NBNetEnvUtils.a());
        String errorCode = headers.get("x-arup-error-code");
        String errorMsg = headers.get("x-arup-error-msg");
        if (TextUtils.isEmpty(errorMsg) && TextUtils.isEmpty(errorCode)) {
            return;
        }
        if (TextUtils.isEmpty(errorCode)) {
            throw new NBNetException("[" + responseCode + "] errorMsg: " + errorMsg, 1000);
        } else if (TextUtils.equals(errorCode, Result.TAOBAO_ACTIVE)) {
            String sleepStr = headers.get("x-mmup-sleep");
            int sleep = 0;
            if (!TextUtils.isEmpty(sleepStr)) {
                try {
                    sleep = Integer.parseInt(sleepStr);
                } catch (Throwable th) {
                    NBNetLogCat.d("UploadTransport", "[checkServerForErrors] parseInt fail. sleep: " + sleepStr);
                }
            }
            if (sleep > 0) {
                throw new NBNetServerLimitFlowException(sleep, errorMsg);
            }
            throw new NBNetServerLimitFlowException(errorMsg);
        } else {
            throw new NBNetException("[" + responseCode + "," + errorCode + "] errorMsg: " + errorMsg, Integer.parseInt(errorCode));
        }
    }

    private void a(Map<String, String> headers, NBNetUploadRequest nbNetUploadRequest) {
        String process = headers.get("x-arup-process");
        if (TextUtils.isEmpty(process)) {
            NBNetLogCat.a((String) "UploadTransport", (String) "onUploadProgress. process is empty.");
            return;
        }
        try {
            int progress = Integer.parseInt(process);
            int fileLength = (int) this.b.e().b();
            nbNetUploadRequest.getCallbackWrapper().onUploadProgress(nbNetUploadRequest, progress, fileLength, (int) ((((float) progress) / 100.0f) * ((float) fileLength)));
        } catch (Throwable e2) {
            NBNetLogCat.f("UploadTransport", "100 headers : " + headers.toString());
            StringBuilder sb = new StringBuilder("Illegal process: ");
            if (TextUtils.isEmpty(process)) {
                process = "is null";
            }
            throw new NBNetProtocolException(sb.append(process).append(", ").append(e2.toString()).toString(), e2);
        }
    }

    private UploadResponseEntity a(InputStream responseBody, Map<String, String> headers) {
        if (!a(headers)) {
            return null;
        }
        UploadResponseEntity responseEntity = b(responseBody, headers);
        responseEntity.c = this.b.d();
        return responseEntity;
    }

    private UploadResponseEntity b(InputStream responseBody, Map<String, String> headers) {
        String contentLength = headers.get("Content-Length");
        if (TextUtils.isEmpty(contentLength)) {
            NBNetLogCat.a((String) "UploadTransport", "Response header missing the content-length. detail headers: " + headers.toString());
            throw new NBNetProtocolException("Response header missing the content-length");
        }
        int responseContentLength = Integer.parseInt(contentLength);
        String responseContent = "";
        if (responseContentLength > 0) {
            responseContent = ProtocolUtils.a(headers, (InputStream) new LengthInputStream(responseBody, responseContentLength));
        }
        UploadResponseEntity responseEntity = new UploadResponseEntity();
        responseEntity.g = Collections.unmodifiableMap(headers);
        responseEntity.b = responseContent;
        responseEntity.c = this.b.d();
        return responseEntity;
    }

    public final InputStream c() {
        if (this.d) {
            throw new NBNetCancelException("Request released or abort");
        }
        if (this.a == null) {
            a();
        }
        return this.a.a();
    }

    public final boolean d() {
        if (this.b != null) {
            this.b.c();
        }
        if (this.a != null) {
            this.g.lock();
            try {
                if (this.a != null) {
                    this.d = true;
                    this.a.d();
                    this.a = null;
                    NBNetLogCat.a((String) "UploadTransport", (String) "UploadTransport released");
                    this.g.unlock();
                }
            } finally {
                this.g.unlock();
            }
        }
        return true;
    }

    public final boolean e() {
        if (this.b != null) {
            this.b.c();
        }
        if (this.a != null) {
            this.d = true;
            this.a.e();
            this.a = null;
            NBNetLogCat.a((String) "UploadTransport", (String) "UploadTransport aborted");
        }
        return true;
    }

    public final int f() {
        if (this.a == null) {
            a();
        }
        return this.e;
    }

    private boolean a(Map<String, String> headers) {
        String offsetValue = headers.get("x-arup-offset");
        NBNetLogCat.a((String) "UploadTransport", "offset: " + offsetValue);
        if (TextUtils.isEmpty(offsetValue)) {
            return false;
        }
        String[] offsetValueArray = offsetValue.split("=|,");
        if (offsetValueArray.length == 3) {
            return a(offsetValue, offsetValueArray[1], offsetValueArray[2]);
        }
        String errorMessage = "Illegal offset length. offset: " + offsetValue;
        NBNetLogCat.a((String) "UploadTransport", errorMessage);
        throw new NBNetProtocolException(errorMessage);
    }

    private void a(Throwable e2) {
        NBNetException lWriteException;
        String errMessage = "UploadRequestEntity writeTo exception: " + e2.toString();
        NBNetLogCat.d("UploadTransport", errMessage);
        if (e2 instanceof NBNetException) {
            lWriteException = (NBNetException) e2;
        } else {
            lWriteException = new NBNetException(errMessage, e2);
        }
        if (this.b.i()) {
            throw lWriteException;
        } else if (!this.b.h()) {
            this.f = lWriteException;
            e();
        }
    }

    public final boolean g() {
        return this.d;
    }

    private String b(Map<String, String> headers) {
        String lFileId = headers.get("x-mmup-file-id");
        if (!TextUtils.isEmpty(lFileId)) {
            this.b.e().a(lFileId);
            return lFileId;
        } else if (!TextUtils.isEmpty(this.b.e().f())) {
            return this.b.e().f();
        } else {
            return lFileId;
        }
    }

    private boolean a(String offsetValue, String offsetString, String lengthString) {
        try {
            int offset = Integer.parseInt(offsetString);
            int length = Integer.parseInt(lengthString);
            IOUtils.a((int) this.b.e().b(), offset, length);
            UploadActionSession uploadActionSession = this.b.d();
            NBNetLogCat.a((String) "UploadTransport", "update upload action " + uploadActionSession.a + " -> 2");
            uploadActionSession.b(new Pair(Integer.valueOf(offset), Integer.valueOf(length)));
            return true;
        } catch (Throwable e2) {
            String errorInfo = "Illegal offset . offset: " + offsetValue;
            NBNetLogCat.b("UploadTransport", errorInfo, e2);
            throw new NBNetProtocolException(errorInfo, e2);
        }
    }

    private BasicNBNetContext m() {
        if (this.c == null) {
            this.c = new BasicNBNetContext();
        }
        return this.c;
    }

    public final void a(BasicNBNetContext nbNetContext) {
        this.c = nbNetContext;
    }

    public final int h() {
        return this.h;
    }

    public final int i() {
        return this.i;
    }

    private boolean n() {
        return this.j;
    }

    public final void j() {
        this.j = true;
    }
}
