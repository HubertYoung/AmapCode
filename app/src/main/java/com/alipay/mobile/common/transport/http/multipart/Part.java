package com.alipay.mobile.common.transport.http.multipart;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

public abstract class Part {
    protected static final String BOUNDARY = "----------------314159265358979323846";
    protected static final byte[] BOUNDARY_BYTES;
    protected static final String CHARSET = "; charset=";
    protected static final byte[] CHARSET_BYTES = EncodingUtils.getAsciiBytes(CHARSET);
    protected static final String CONTENT_DISPOSITION = "Content-Disposition: form-data; name=";
    protected static final byte[] CONTENT_DISPOSITION_BYTES = EncodingUtils.getAsciiBytes(CONTENT_DISPOSITION);
    protected static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding: ";
    protected static final byte[] CONTENT_TRANSFER_ENCODING_BYTES = EncodingUtils.getAsciiBytes(CONTENT_TRANSFER_ENCODING);
    protected static final String CONTENT_TYPE = "Content-Type: ";
    protected static final byte[] CONTENT_TYPE_BYTES = EncodingUtils.getAsciiBytes(CONTENT_TYPE);
    protected static final String CRLF = "\r\n";
    protected static final byte[] CRLF_BYTES = EncodingUtils.getAsciiBytes("\r\n");
    protected static final String EXTRA = "--";
    protected static final byte[] EXTRA_BYTES = EncodingUtils.getAsciiBytes(EXTRA);
    protected static final String FILE_NAME = "; filename=";
    public static final byte[] FILE_NAME_BYTES = EncodingUtils.getAsciiBytes(FILE_NAME);
    protected static final String QUOTE = "\"";
    protected static final byte[] QUOTE_BYTES = EncodingUtils.getAsciiBytes("\"");
    private static final byte[] a;
    private byte[] b;

    public abstract String getCharSet();

    public abstract String getContentType();

    public abstract String getFileName();

    public abstract String getName();

    public abstract String getTransferEncoding();

    /* access modifiers changed from: protected */
    public abstract long lengthOfData();

    /* access modifiers changed from: protected */
    public abstract void sendData(OutputStream outputStream);

    static {
        byte[] asciiBytes = EncodingUtils.getAsciiBytes(BOUNDARY);
        BOUNDARY_BYTES = asciiBytes;
        a = asciiBytes;
    }

    public static String getBoundary() {
        return BOUNDARY;
    }

    /* access modifiers changed from: protected */
    public byte[] getPartBoundary() {
        if (this.b == null) {
            return a;
        }
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public void setPartBoundary(byte[] boundaryBytes) {
        this.b = boundaryBytes;
    }

    public boolean isRepeatable() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void sendStart(OutputStream out) {
        out.write(EXTRA_BYTES);
        out.write(getPartBoundary());
        out.write(CRLF_BYTES);
    }

    /* access modifiers changed from: protected */
    public void sendDispositionHeader(OutputStream out) {
        out.write(CONTENT_DISPOSITION_BYTES);
        out.write(QUOTE_BYTES);
        out.write(EncodingUtils.getAsciiBytes(getName()));
        out.write(QUOTE_BYTES);
        String fileName = getFileName();
        if (fileName != null) {
            String fileName2 = fileName.trim();
            if (fileName2.length() > 0) {
                out.write(FILE_NAME_BYTES);
                out.write(QUOTE_BYTES);
                out.write(EncodingUtils.getAsciiBytes(fileName2));
                out.write(QUOTE_BYTES);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void sendContentTypeHeader(OutputStream out) {
        String contentType = getContentType();
        if (contentType != null) {
            out.write(CRLF_BYTES);
            out.write(CONTENT_TYPE_BYTES);
            out.write(EncodingUtils.getAsciiBytes(contentType));
            String charSet = getCharSet();
            if (charSet != null) {
                out.write(CHARSET_BYTES);
                out.write(EncodingUtils.getAsciiBytes(charSet));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void sendTransferEncodingHeader(OutputStream out) {
        String transferEncoding = getTransferEncoding();
        if (transferEncoding != null) {
            out.write(CRLF_BYTES);
            out.write(CONTENT_TRANSFER_ENCODING_BYTES);
            out.write(EncodingUtils.getAsciiBytes(transferEncoding));
        }
    }

    /* access modifiers changed from: protected */
    public void sendEndOfHeader(OutputStream out) {
        out.write(CRLF_BYTES);
        out.write(CRLF_BYTES);
    }

    /* access modifiers changed from: protected */
    public void sendEnd(OutputStream out) {
        out.write(CRLF_BYTES);
    }

    public void send(OutputStream out) {
        sendStart(out);
        sendDispositionHeader(out);
        sendContentTypeHeader(out);
        sendTransferEncodingHeader(out);
        sendEndOfHeader(out);
        sendData(out);
        sendEnd(out);
    }

    public long length() {
        if (lengthOfData() < 0) {
            return -1;
        }
        ByteArrayOutputStream overhead = new ByteArrayOutputStream();
        sendStart(overhead);
        sendDispositionHeader(overhead);
        sendContentTypeHeader(overhead);
        sendTransferEncodingHeader(overhead);
        sendEndOfHeader(overhead);
        sendEnd(overhead);
        return ((long) overhead.size()) + lengthOfData();
    }

    public String toString() {
        return getName();
    }

    public static void sendParts(OutputStream out, Part[] parts) {
        sendParts(out, parts, a);
    }

    public static void sendParts(OutputStream out, Part[] parts, byte[] partBoundary) {
        if (parts == null) {
            throw new IllegalArgumentException("Parts may not be null");
        } else if (partBoundary == null || partBoundary.length == 0) {
            throw new IllegalArgumentException("partBoundary may not be empty");
        } else {
            for (int i = 0; i < parts.length; i++) {
                parts[i].setPartBoundary(partBoundary);
                parts[i].send(out);
            }
            out.write(EXTRA_BYTES);
            out.write(partBoundary);
            out.write(EXTRA_BYTES);
            out.write(CRLF_BYTES);
        }
    }

    public static long getLengthOfParts(Part[] parts) {
        return getLengthOfParts(parts, a);
    }

    public static long getLengthOfParts(Part[] parts, byte[] partBoundary) {
        if (parts == null) {
            throw new IllegalArgumentException("Parts may not be null");
        }
        long total = 0;
        for (int i = 0; i < parts.length; i++) {
            parts[i].setPartBoundary(partBoundary);
            long l = parts[i].length();
            if (l < 0) {
                return -1;
            }
            total += l;
        }
        return ((long) EXTRA_BYTES.length) + total + ((long) partBoundary.length) + ((long) EXTRA_BYTES.length) + ((long) CRLF_BYTES.length);
    }
}
