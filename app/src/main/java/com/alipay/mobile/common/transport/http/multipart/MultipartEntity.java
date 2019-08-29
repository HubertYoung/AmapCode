package com.alipay.mobile.common.transport.http.multipart;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EncodingUtils;

public class MultipartEntity extends AbstractHttpEntity {
    public static final String MULTIPART_BOUNDARY = "http.method.multipart.boundary";
    private static byte[] a = EncodingUtils.getAsciiBytes("-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    private byte[] b;
    private HttpParams c;
    private boolean d;
    protected Part[] parts;

    private static byte[] a() {
        Random rand = new Random();
        byte[] bytes = new byte[(rand.nextInt(11) + 30)];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = a[rand.nextInt(a.length)];
        }
        return bytes;
    }

    public MultipartEntity(Part[] parts2, HttpParams params) {
        this.d = false;
        if (parts2 == null) {
            throw new IllegalArgumentException("parts cannot be null");
        } else if (params == null) {
            throw new IllegalArgumentException("params cannot be null");
        } else {
            this.parts = parts2;
            this.c = params;
        }
    }

    public MultipartEntity(Part[] parts2) {
        this.d = false;
        setContentType("multipart/form-data");
        if (parts2 == null) {
            throw new IllegalArgumentException("parts cannot be null");
        }
        this.parts = parts2;
        this.c = null;
    }

    public MultipartEntity(List<Part> parts2) {
        this((Part[]) parts2.toArray(new Part[parts2.size()]));
    }

    public MultipartEntity(List<Part> parts2, HttpParams params) {
        this((Part[]) parts2.toArray(new Part[parts2.size()]), params);
    }

    /* access modifiers changed from: protected */
    public byte[] getMultipartBoundary() {
        if (this.b == null) {
            String temp = null;
            if (this.c != null) {
                temp = (String) this.c.getParameter(MULTIPART_BOUNDARY);
            }
            if (temp != null) {
                this.b = EncodingUtils.getAsciiBytes(temp);
            } else {
                this.b = a();
            }
        }
        return this.b;
    }

    public boolean isRepeatable() {
        for (Part isRepeatable : this.parts) {
            if (!isRepeatable.isRepeatable()) {
                return false;
            }
        }
        return true;
    }

    public void writeTo(OutputStream out) {
        LogCatUtil.verbose("MultipartEntity", "writeTo(OutputStream out)");
        Part.sendParts(out, this.parts, getMultipartBoundary());
    }

    public Header getContentType() {
        StringBuffer buffer = new StringBuffer("multipart/form-data");
        buffer.append("; boundary=");
        buffer.append(EncodingUtils.getAsciiString(getMultipartBoundary()));
        return new BasicHeader("Content-Type", buffer.toString());
    }

    public long getContentLength() {
        try {
            return Part.getLengthOfParts(this.parts, getMultipartBoundary());
        } catch (Exception e) {
            LogCatUtil.warn("MultipartEntity", "An exception occurred while getting the length of the parts", e);
            return 0;
        }
    }

    public InputStream getContent() {
        if (isRepeatable() || !this.d) {
            this.d = true;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Part.sendParts(baos, this.parts, this.b);
            return new ByteArrayInputStream(baos.toByteArray());
        }
        throw new IllegalStateException("Content has been consumed");
    }

    public boolean isStreaming() {
        return false;
    }
}
