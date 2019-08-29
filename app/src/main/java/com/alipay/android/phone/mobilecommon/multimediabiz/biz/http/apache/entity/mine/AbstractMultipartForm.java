package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.http.util.ByteArrayBuffer;

abstract class AbstractMultipartForm {
    private static final ByteArrayBuffer a = a(MIME.DEFAULT_CHARSET, (String) ": ");
    private static final ByteArrayBuffer b = a(MIME.DEFAULT_CHARSET, (String) MultipartUtility.LINE_FEED);
    private static final ByteArrayBuffer c = a(MIME.DEFAULT_CHARSET, (String) "--");
    protected final Charset charset;
    private final String d;
    private final String e;

    /* access modifiers changed from: protected */
    public abstract void formatMultipartHeader(FormBodyPart formBodyPart, OutputStream outputStream);

    public abstract List<FormBodyPart> getBodyParts();

    private static ByteArrayBuffer a(Charset charset2, String string) {
        ByteBuffer encoded = charset2.encode(CharBuffer.wrap(string));
        ByteArrayBuffer bab = new ByteArrayBuffer(encoded.remaining());
        bab.append(encoded.array(), encoded.position(), encoded.remaining());
        return bab;
    }

    private static void a(ByteArrayBuffer b2, OutputStream out) {
        out.write(b2.buffer(), 0, b2.length());
    }

    private static void a(String s, Charset charset2, OutputStream out) {
        a(a(charset2, s), out);
    }

    private static void a(String s, OutputStream out) {
        a(a(MIME.DEFAULT_CHARSET, s), out);
    }

    protected static void writeField(MinimalField field, OutputStream out) {
        a(field.getName(), out);
        a(a, out);
        a(field.getBody(), out);
        a(b, out);
    }

    protected static void writeField(MinimalField field, Charset charset2, OutputStream out) {
        a(field.getName(), charset2, out);
        a(a, out);
        a(field.getBody(), charset2, out);
        a(b, out);
    }

    public AbstractMultipartForm(String subType, Charset charset2, String boundary) {
        Args.notNull(subType, "Multipart subtype");
        Args.notNull(boundary, "Multipart boundary");
        this.d = subType;
        this.charset = charset2 == null ? MIME.DEFAULT_CHARSET : charset2;
        this.e = boundary;
    }

    public AbstractMultipartForm(String subType, String boundary) {
        this(subType, null, boundary);
    }

    public String getSubType() {
        return this.d;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getBoundary() {
        return this.e;
    }

    private void a(OutputStream out, boolean writeContent) {
        ByteArrayBuffer boundary = a(this.charset, getBoundary());
        for (FormBodyPart part : getBodyParts()) {
            a(c, out);
            a(boundary, out);
            a(b, out);
            formatMultipartHeader(part, out);
            a(b, out);
            if (writeContent) {
                part.getBody().writeTo(out);
            }
            a(b, out);
        }
        a(c, out);
        a(boundary, out);
        a(c, out);
        a(b, out);
    }

    public void writeTo(OutputStream out) {
        a(out, true);
    }

    public long getTotalLength() {
        long contentLen = 0;
        for (FormBodyPart body : getBodyParts()) {
            long len = body.getBody().getContentLength();
            if (len < 0) {
                return -1;
            }
            contentLen += len;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            a((OutputStream) out, false);
            return ((long) out.toByteArray().length) + contentLen;
        } catch (IOException e2) {
            return -1;
        }
    }
}
