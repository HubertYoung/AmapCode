package com.alipay.mobile.common.nbnet.api.upload;

import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.sdk.util.h;
import java.util.Collections;
import java.util.Map;

public class NBNetUploadResponse {
    private String a;
    private String b;
    private String c;
    private String d;
    private NBNetException e;
    private boolean f;
    private int g = 0;
    private Map<String, String> h = Collections.EMPTY_MAP;

    public NBNetException getNbNetException() {
        return this.e;
    }

    public void setNbNetException(NBNetException nbNetException) {
        this.e = nbNetException;
    }

    public int getErrorCode() {
        if (this.e == null) {
            return 0;
        }
        return this.e.getErrorCode();
    }

    public String getErrorMsg() {
        if (this.e == null) {
            return "";
        }
        return this.e.toString();
    }

    public boolean isSuccess() {
        return this.e == null;
    }

    public String getFileId() {
        return this.a;
    }

    public void setFileId(String fileId) {
        this.a = fileId;
    }

    public String getContent() {
        return this.d;
    }

    public void setContent(String content) {
        this.d = content;
    }

    public String getTraceId() {
        return this.b;
    }

    public void setTraceId(String traceId) {
        this.b = traceId;
    }

    public String getMd5() {
        return this.c;
    }

    public void setMd5(String md5) {
        this.c = md5;
    }

    public boolean isQuic() {
        return this.f;
    }

    public void setQuic(boolean quic) {
        this.f = quic;
    }

    public int getLimitedSleep() {
        return this.g;
    }

    public void setLimitedSleep(int limitedSleep) {
        this.g = limitedSleep;
    }

    public Map<String, String> getRespHeader() {
        return this.h;
    }

    public void setRespHeader(Map<String, String> respHeader) {
        this.h = respHeader;
    }

    public String toString() {
        return "NBNetUploadResponse{fileId='" + this.a + '\'' + ", traceId='" + this.b + '\'' + ", md5='" + this.c + '\'' + ", content='" + this.d + '\'' + ", nbNetException=" + this.e + ", limitedSleep=" + this.g + h.d;
    }
}
