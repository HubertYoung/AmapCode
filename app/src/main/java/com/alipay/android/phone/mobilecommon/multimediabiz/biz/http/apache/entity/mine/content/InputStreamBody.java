package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.ContentType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamBody extends AbstractContentBody {
    private final InputStream a;
    private final String b;

    @Deprecated
    public InputStreamBody(InputStream in, String mimeType, String filename) {
        this(in, ContentType.create(mimeType), filename);
    }

    public InputStreamBody(InputStream in, String filename) {
        this(in, ContentType.DEFAULT_BINARY, filename);
    }

    public InputStreamBody(InputStream in, ContentType contentType, String filename) {
        super(contentType);
        Args.notNull(in, "Input stream");
        this.a = in;
        this.b = filename;
    }

    public InputStreamBody(InputStream in, ContentType contentType) {
        this(in, contentType, (String) null);
    }

    public InputStream getInputStream() {
        return this.a;
    }

    public void writeTo(OutputStream out) {
        Args.notNull(out, "Output stream");
        try {
            byte[] tmp = new byte[4096];
            while (true) {
                int l = this.a.read(tmp);
                if (l != -1) {
                    out.write(tmp, 0, l);
                } else {
                    out.flush();
                    return;
                }
            }
        } finally {
            this.a.close();
        }
    }

    public String getTransferEncoding() {
        return "binary";
    }

    public long getContentLength() {
        return -1;
    }

    public String getFilename() {
        return this.b;
    }
}
