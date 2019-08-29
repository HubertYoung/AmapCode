package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.TransferredListener;
import java.io.InputStream;

public class InputStreamUpReq extends BaseUpReq<InputStream> {
    public InputStreamUpReq(InputStream inputStream, String fileName) {
        this.inputSource = inputStream;
        this.fileName = fileName;
    }

    public InputStreamUpReq(InputStream inputStream, String fileName, TransferredListener listener) {
        this(inputStream, fileName, listener, -1);
        try {
            this.totalLength = (long) inputStream.available();
        } catch (Exception e) {
            this.totalLength = -1;
        }
    }

    public InputStreamUpReq(InputStream inputStream, String fileName, TransferredListener transferredListener, long length) {
        this.inputSource = inputStream;
        this.fileName = fileName;
        this.transferedListener = transferredListener;
        this.totalLength = length;
    }

    public InputStream getInputStream() {
        return (InputStream) getInputSource();
    }

    public void setInputStream(InputStream inputStream) {
        setInputSource(inputStream);
    }

    public long getLength() {
        return getTotalLength();
    }
}
