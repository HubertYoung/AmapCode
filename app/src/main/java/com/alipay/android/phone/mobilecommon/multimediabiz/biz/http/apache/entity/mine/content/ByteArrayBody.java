package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.ContentType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import java.io.OutputStream;

public class ByteArrayBody extends AbstractContentBody {
    private final byte[] a;
    private final String b;

    @Deprecated
    public ByteArrayBody(byte[] data, String mimeType, String filename) {
        this(data, ContentType.create(mimeType), filename);
    }

    public ByteArrayBody(byte[] data, ContentType contentType, String filename) {
        super(contentType);
        Args.notNull(data, "byte[]");
        this.a = data;
        this.b = filename;
    }

    public ByteArrayBody(byte[] data, String filename) {
        this(data, (String) FilePart.DEFAULT_CONTENT_TYPE, filename);
    }

    public String getFilename() {
        return this.b;
    }

    public void writeTo(OutputStream out) {
        out.write(this.a);
    }

    public String getCharset() {
        return null;
    }

    public String getTransferEncoding() {
        return "binary";
    }

    public long getContentLength() {
        return (long) this.a.length;
    }

    /* access modifiers changed from: protected */
    public byte[] getData() {
        return this.a;
    }
}
