package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.Consts;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.ContentType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class StringBody extends AbstractContentBody {
    private final byte[] a;

    @Deprecated
    public static StringBody create(String text, String mimeType, Charset charset) {
        try {
            return new StringBody(text, mimeType, charset);
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Charset " + charset + " is not supported", ex);
        }
    }

    @Deprecated
    public static StringBody create(String text, Charset charset) {
        return create(text, null, charset);
    }

    @Deprecated
    public static StringBody create(String text) {
        return create(text, null, null);
    }

    @Deprecated
    public StringBody(String text, String mimeType, Charset charset) {
        this(text, ContentType.create(mimeType, charset));
    }

    @Deprecated
    public StringBody(String text, Charset charset) {
        this(text, "text/plain", charset);
    }

    @Deprecated
    public StringBody(String text) {
        this(text, "text/plain", Consts.ASCII);
    }

    public StringBody(String text, ContentType contentType) {
        super(contentType);
        Charset charset = contentType.getCharset();
        String csname = charset != null ? charset.name() : Consts.ASCII.name();
        try {
            this.a = text.getBytes(csname);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedCharsetException(csname);
        }
    }

    public Reader getReader() {
        Charset charset = getContentType().getCharset();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.a);
        if (charset == null) {
            charset = Consts.ASCII;
        }
        return new InputStreamReader(byteArrayInputStream, charset);
    }

    public void writeTo(OutputStream out) {
        Args.notNull(out, "Output stream");
        InputStream in = new ByteArrayInputStream(this.a);
        byte[] tmp = new byte[4096];
        while (true) {
            int l = in.read(tmp);
            if (l != -1) {
                out.write(tmp, 0, l);
            } else {
                out.flush();
                return;
            }
        }
    }

    public String getTransferEncoding() {
        return "8bit";
    }

    public long getContentLength() {
        return (long) this.a.length;
    }

    public String getFilename() {
        return null;
    }
}
