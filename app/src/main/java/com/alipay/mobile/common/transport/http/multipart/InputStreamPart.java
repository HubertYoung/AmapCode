package com.alipay.mobile.common.transport.http.multipart;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamPart extends PartBase {
    private String a;
    private InputStream b;
    private int c;

    public InputStreamPart(String name, InputStream inputStream, int contentLength, String fileName, String contentType) {
        super(name, contentType == null ? FilePart.DEFAULT_CONTENT_TYPE : contentType, null, "binary");
        this.c = -1;
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream may not be null");
        }
        this.a = fileName;
        this.b = inputStream;
        this.c = contentLength;
    }

    public InputStreamPart(String name, InputStream inputStream, String fileName, String contentType) {
        this(name, inputStream, -1, fileName, contentType);
    }

    public InputStreamPart(String name, InputStream inputStream, String contentType) {
        this(name, inputStream, (String) null, contentType);
    }

    public InputStreamPart(String name, InputStream inputStream, String contentType, int contentLength) {
        this(name, inputStream, (String) null, contentType);
        this.c = contentLength;
    }

    public InputStreamPart(String name, InputStream inputStream) {
        this(name, inputStream, (String) FilePart.DEFAULT_CONTENT_TYPE);
    }

    public InputStreamPart(String name, InputStream inputStream, int contentLength) {
        this(name, inputStream, (String) FilePart.DEFAULT_CONTENT_TYPE);
        this.c = contentLength;
    }

    public void setContentLength(int contentLength) {
        this.c = contentLength;
    }

    /* access modifiers changed from: protected */
    public void sendData(OutputStream out) {
        if (lengthOfData() == 0) {
            LogCatUtil.info("InputStreamPart", "No data to send.");
            return;
        }
        byte[] tmp = new byte[4096];
        int sumLen = 0;
        while (true) {
            try {
                int len = this.b.read(tmp);
                if (len != -1) {
                    out.write(tmp, 0, len);
                    sumLen += len;
                } else {
                    out.flush();
                    return;
                }
            } finally {
                r4 = "InputStreamPart";
                r6 = "sendData. Sum len: ";
                LogCatUtil.info(r4, sumLen);
                this.b.close();
            }
        }
    }

    /* access modifiers changed from: protected */
    public long lengthOfData() {
        return (long) this.c;
    }

    public String getFileName() {
        return this.a;
    }

    public boolean isRepeatable() {
        return false;
    }
}
