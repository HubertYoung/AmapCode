package com.alipay.mobile.common.transport.http.multipart;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

public class StringPart extends PartBase {
    public static final String DEFAULT_CHARSET = "US-ASCII";
    public static final String DEFAULT_CONTENT_TYPE = "text/plain";
    public static final String DEFAULT_TRANSFER_ENCODING = "8bit";
    private byte[] a;
    private String b;

    public StringPart(String name, String value, String charset) {
        super(name, "text/plain", charset == null ? "US-ASCII" : charset, "8bit");
        if (value == null) {
            throw new IllegalArgumentException("Value may not be null");
        } else if (value.indexOf(0) != -1) {
            throw new IllegalArgumentException("NULs may not be present in string parts");
        } else {
            this.b = value;
        }
    }

    public StringPart(String name, String value) {
        this(name, value, null);
    }

    private byte[] a() {
        if (this.a == null) {
            this.a = EncodingUtils.getBytes(this.b, getCharSet());
        }
        return this.a;
    }

    public String getFileName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void sendData(OutputStream out) {
        LogCatUtil.info("StringPart", "enter sendData(OutputStream)");
        out.write(a());
    }

    /* access modifiers changed from: protected */
    public long lengthOfData() {
        LogCatUtil.info("StringPart", "enter lengthOfData()");
        return (long) a().length;
    }

    public void setCharSet(String charSet) {
        super.setCharSet(charSet);
        this.a = null;
    }
}
