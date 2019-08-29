package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content;

public interface ContentDescriptor {
    String getCharset();

    long getContentLength();

    String getMediaType();

    String getMimeType();

    String getSubType();

    String getTransferEncoding();
}
