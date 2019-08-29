package com.alipay.mobile.common.transport.http.multipart;

import java.io.InputStream;

public interface PartSource {
    InputStream createInputStream();

    String getFileName();

    long getLength();
}
