package com.alipay.mobile.common.transport.http.multipart;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class FilePart extends PartBase {
    public static final String DEFAULT_CHARSET = "ISO-8859-1";
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    public static final String DEFAULT_TRANSFER_ENCODING = "binary";
    private PartSource a;

    public FilePart(String name, PartSource partSource, String contentType) {
        super(name, contentType == null ? DEFAULT_CONTENT_TYPE : contentType, null, "binary");
        if (partSource == null) {
            throw new IllegalArgumentException("Source may not be null");
        }
        this.a = partSource;
    }

    public FilePart(String name, PartSource partSource) {
        this(name, partSource, (String) null);
    }

    public FilePart(String name, File file) {
        this(name, (PartSource) new FilePartSource(file), (String) null);
    }

    public FilePart(String name, File file, String contentType) {
        this(name, (PartSource) new FilePartSource(file), contentType);
    }

    public FilePart(String name, String fileName, File file) {
        this(name, (PartSource) new FilePartSource(fileName, file), (String) null);
    }

    public FilePart(String name, String fileName, File file, String contentType) {
        this(name, (PartSource) new FilePartSource(fileName, file), contentType);
    }

    public String getFileName() {
        return this.a.getFileName();
    }

    /* access modifiers changed from: protected */
    public void sendData(OutputStream out) {
        LogCatUtil.info("FilePart", "enter sendData(OutputStream out)");
        if (lengthOfData() == 0) {
            LogCatUtil.info("FilePart", "No data to send.");
            return;
        }
        byte[] tmp = new byte[4096];
        InputStream instream = this.a.createInputStream();
        while (true) {
            try {
                int len = instream.read(tmp);
                if (len >= 0) {
                    out.write(tmp, 0, len);
                } else {
                    return;
                }
            } finally {
                instream.close();
            }
        }
    }

    /* access modifiers changed from: protected */
    public PartSource getSource() {
        LogCatUtil.info("FilePart", "enter getSource()");
        return this.a;
    }

    /* access modifiers changed from: protected */
    public long lengthOfData() {
        LogCatUtil.info("FilePart", "enter lengthOfData()");
        return this.a.getLength();
    }
}
