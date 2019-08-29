package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content;

import java.io.OutputStream;

public interface ContentBody extends ContentDescriptor {
    String getFilename();

    void writeTo(OutputStream outputStream);
}
