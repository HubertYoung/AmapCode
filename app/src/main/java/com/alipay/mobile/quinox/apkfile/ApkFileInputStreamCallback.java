package com.alipay.mobile.quinox.apkfile;

import java.io.IOException;
import java.io.InputStream;

public interface ApkFileInputStreamCallback {
    boolean onInputStream(InputStream inputStream) throws IOException;
}
