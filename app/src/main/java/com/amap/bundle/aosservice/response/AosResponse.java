package com.amap.bundle.aosservice.response;

import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.core.network.inter.response.InputStreamResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public abstract class AosResponse<T> extends bpk<T> {
    private AosRequest mAosRequest;
    private InputStreamResponse mInputStreamResponse;

    public void setAosRequest(AosRequest aosRequest) {
        this.mAosRequest = aosRequest;
    }

    public AosRequest getAosRequest() {
        return this.mAosRequest;
    }

    public bph getRequest() {
        if (this.mInputStreamResponse == null) {
            return super.getRequest();
        }
        return this.mInputStreamResponse.getRequest();
    }

    public void setInputStreamResponse(InputStreamResponse inputStreamResponse) {
        this.mInputStreamResponse = inputStreamResponse;
    }

    public int getStatusCode() {
        if (this.mInputStreamResponse == null) {
            return super.getStatusCode();
        }
        return this.mInputStreamResponse.getStatusCode();
    }

    public long getContentLength() {
        if (this.mInputStreamResponse == null) {
            return super.getContentLength();
        }
        return this.mInputStreamResponse.getContentLength();
    }

    public String getHeader(String str) {
        if (this.mInputStreamResponse == null) {
            return super.getHeader(str);
        }
        return this.mInputStreamResponse.getHeader(str);
    }

    public Map<String, List<String>> getHeaders() {
        if (this.mInputStreamResponse == null) {
            return super.getHeaders();
        }
        return this.mInputStreamResponse.getHeaders();
    }

    public InputStream getBodyInputStream() {
        if (this.mInputStreamResponse == null) {
            return super.getBodyInputStream();
        }
        return this.mInputStreamResponse.getBodyInputStream();
    }
}
