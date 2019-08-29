package com.alipay.mobile.common.transport.http;

import android.annotation.SuppressLint;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import org.apache.http.impl.io.AbstractSessionOutputBuffer;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.params.HttpParams;
import org.apache.http.util.ByteArrayBuffer;

public class ZHttpClientSocketOutputBuffer extends AbstractSessionOutputBuffer {
    private ByteArrayBuffer a;
    private OutputStream b;
    private HttpTransportMetricsImpl c;

    public ZHttpClientSocketOutputBuffer(Socket socket, int buffersize, HttpParams params) {
        if (socket == null) {
            throw new IllegalArgumentException("Socket may not be null");
        }
        init(socket.getOutputStream(), 8192, params);
        getInnerMetrics();
        a();
    }

    /* access modifiers changed from: protected */
    public void init(OutputStream outstream, int buffersize, HttpParams params) {
        ZHttpClientSocketOutputBuffer.super.init(outstream, buffersize, params);
        this.b = outstream;
    }

    public void write(byte[] b2, int off, int len) {
        if (b2 != null) {
            if (len > a().capacity()) {
                flushBuffer();
                this.b.write(b2, off, len);
                HttpTransportMetricsImpl metricsImpl = getInnerMetrics();
                if (metricsImpl != null) {
                    metricsImpl.incrementBytesTransferred((long) len);
                    return;
                }
                return;
            }
            if (len > a().capacity() - a().length()) {
                flushBuffer();
            }
            a().append(b2, off, len);
        }
    }

    public HttpTransportMetricsImpl getInnerMetrics() {
        if (this.c != null) {
            return this.c;
        }
        HttpTransportMetrics metrics = ZHttpClientSocketOutputBuffer.super.getMetrics();
        if (metrics == null || !(metrics instanceof HttpTransportMetricsImpl)) {
            throw new IOException("getInnerMetrics fail");
        }
        this.c = (HttpTransportMetricsImpl) metrics;
        return this.c;
    }

    @SuppressLint({"LongLogTag"})
    private ByteArrayBuffer a() {
        if (this.a != null) {
            return this.a;
        }
        try {
            Field buffField = getClass().getSuperclass().getDeclaredField("buffer");
            buffField.setAccessible(true);
            this.a = (ByteArrayBuffer) buffField.get(this);
            return this.a;
        } catch (Throwable e) {
            LogCatUtil.error("ZHttpClientSocketOutputBuffer", "getInnerBuffer fail", e);
            InterruptedIOException interruptedIOException = new InterruptedIOException();
            interruptedIOException.initCause(e);
            throw interruptedIOException;
        }
    }
}
