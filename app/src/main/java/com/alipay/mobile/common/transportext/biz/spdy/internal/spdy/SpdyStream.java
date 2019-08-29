package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public final class SpdyStream {
    static final /* synthetic */ boolean $assertionsDisabled = (!SpdyStream.class.desiredAssertionStatus());
    public static final int WINDOW_UPDATE_THRESHOLD = 32768;
    /* access modifiers changed from: private */
    public final SpdyConnection connection;
    /* access modifiers changed from: private */
    public ErrorCode errorCode;
    /* access modifiers changed from: private */
    public final int id;
    private final SpdyDataInputStream in;
    private TransportContext mNetContext;
    /* access modifiers changed from: private */
    public final SpdyDataOutputStream out;
    private final int priority;
    /* access modifiers changed from: private */
    public long readTimeoutMillis;
    private final List<String> requestHeaders;
    private List<String> responseHeaders;
    /* access modifiers changed from: private */
    public int writeWindowSize;

    final class SpdyDataInputStream extends InputStream {
        static final /* synthetic */ boolean $assertionsDisabled = (!SpdyStream.class.desiredAssertionStatus());
        private final byte[] buffer;
        /* access modifiers changed from: private */
        public boolean closed;
        /* access modifiers changed from: private */
        public boolean finished;
        private int limit;
        private int pos;
        private int unacknowledgedBytes;

        private SpdyDataInputStream() {
            this.buffer = new byte[65536];
            this.pos = -1;
            this.unacknowledgedBytes = 0;
        }

        public final int available() {
            int length;
            synchronized (SpdyStream.this) {
                checkNotClosed();
                if (this.pos == -1) {
                    length = 0;
                } else if (this.limit > this.pos) {
                    length = this.limit - this.pos;
                } else {
                    length = this.limit + (this.buffer.length - this.pos);
                }
            }
            return length;
        }

        public final int read() {
            return Util.readSingleByte(this);
        }

        public final int read(byte[] b, int offset, int count) {
            int copied = -1;
            synchronized (SpdyStream.this) {
                Util.checkOffsetAndCount(b.length, offset, count);
                waitUntilReadable();
                checkNotClosed();
                if (this.pos != -1) {
                    copied = 0;
                    if (this.limit <= this.pos) {
                        int bytesToCopy = Math.min(count, this.buffer.length - this.pos);
                        System.arraycopy(this.buffer, this.pos, b, offset, bytesToCopy);
                        this.pos += bytesToCopy;
                        copied = bytesToCopy + 0;
                        if (this.pos == this.buffer.length) {
                            this.pos = 0;
                        }
                    }
                    if (copied < count) {
                        int bytesToCopy2 = Math.min(this.limit - this.pos, count - copied);
                        System.arraycopy(this.buffer, this.pos, b, offset + copied, bytesToCopy2);
                        this.pos += bytesToCopy2;
                        copied += bytesToCopy2;
                    }
                    this.unacknowledgedBytes += copied;
                    if (this.unacknowledgedBytes >= 32768) {
                        SpdyStream.this.connection.writeWindowUpdateLater(SpdyStream.this.id, this.unacknowledgedBytes);
                        this.unacknowledgedBytes = 0;
                    }
                    if (this.pos == this.limit) {
                        this.pos = -1;
                        this.limit = 0;
                    }
                }
            }
            return copied;
        }

        private void waitUntilReadable() {
            long start = 0;
            long remaining = 0;
            if (SpdyStream.this.readTimeoutMillis != 0) {
                start = System.nanoTime() / 1000000;
                remaining = SpdyStream.this.readTimeoutMillis;
            }
            while (this.pos == -1 && !this.finished && !this.closed && SpdyStream.this.errorCode == null) {
                try {
                    if (SpdyStream.this.readTimeoutMillis == 0) {
                        SpdyStream.this.wait();
                    } else if (remaining > 0) {
                        LogCatUtil.verbose(InnerLogUtil.MWALLET_SPDY_TAG, "START waitUntilReadable remaining=" + remaining);
                        SpdyStream.this.wait(remaining);
                        LogCatUtil.verbose(InnerLogUtil.MWALLET_SPDY_TAG, "END waitUntilReadable remaining=" + remaining);
                        remaining = (SpdyStream.this.readTimeoutMillis + start) - (System.nanoTime() / 1000000);
                    } else {
                        throw new SocketTimeoutException();
                    }
                } catch (InterruptedException e) {
                    throw new InterruptedIOException();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void receive(InputStream in, int byteCount) {
            boolean finished2;
            int pos2;
            int firstNewByte;
            int limit2;
            boolean flowControlError;
            if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                throw new AssertionError();
            } else if (byteCount != 0) {
                synchronized (SpdyStream.this) {
                    finished2 = this.finished;
                    pos2 = this.pos;
                    firstNewByte = this.limit;
                    limit2 = this.limit;
                    flowControlError = byteCount > this.buffer.length - available();
                }
                if (flowControlError) {
                    Util.skipByReading(in, (long) byteCount);
                    SpdyStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                } else if (finished2) {
                    Util.skipByReading(in, (long) byteCount);
                } else {
                    if (pos2 < limit2) {
                        int firstCopyCount = Math.min(byteCount, this.buffer.length - limit2);
                        Util.readFully(in, this.buffer, limit2, firstCopyCount);
                        limit2 += firstCopyCount;
                        byteCount -= firstCopyCount;
                        if (limit2 == this.buffer.length) {
                            limit2 = 0;
                        }
                    }
                    if (byteCount > 0) {
                        Util.readFully(in, this.buffer, limit2, byteCount);
                        limit2 += byteCount;
                    }
                    synchronized (SpdyStream.this) {
                        this.limit = limit2;
                        if (this.pos == -1) {
                            this.pos = firstNewByte;
                            SpdyStream.this.notifyAll();
                        }
                    }
                }
            }
        }

        public final void close() {
            synchronized (SpdyStream.this) {
                this.closed = true;
                SpdyStream.this.notifyAll();
            }
            SpdyStream.this.cancelStreamIfNecessary();
        }

        private void checkNotClosed() {
            if (this.closed) {
                throw new IOException("stream closed");
            } else if (SpdyStream.this.errorCode != null) {
                throw new IOException("stream was reset:3 " + SpdyStream.this.errorCode);
            }
        }
    }

    final class SpdyDataOutputStream extends OutputStream {
        static final /* synthetic */ boolean $assertionsDisabled = (!SpdyStream.class.desiredAssertionStatus());
        private final byte[] buffer;
        /* access modifiers changed from: private */
        public boolean closed;
        /* access modifiers changed from: private */
        public boolean finished;
        private int pos;
        /* access modifiers changed from: private */
        public int unacknowledgedBytes;

        private SpdyDataOutputStream() {
            this.buffer = new byte[8192];
            this.pos = 0;
            this.unacknowledgedBytes = 0;
        }

        public final void write(int b) {
            Util.writeSingleByte(this, b);
        }

        public final void write(byte[] bytes, int offset, int count) {
            if ($assertionsDisabled || !Thread.holdsLock(SpdyStream.this)) {
                Util.checkOffsetAndCount(bytes.length, offset, count);
                checkNotClosed();
                while (count > 0) {
                    if (this.pos == this.buffer.length) {
                        writeFrame(false);
                    }
                    int bytesToCopy = Math.min(count, this.buffer.length - this.pos);
                    System.arraycopy(bytes, offset, this.buffer, this.pos, bytesToCopy);
                    this.pos += bytesToCopy;
                    offset += bytesToCopy;
                    count -= bytesToCopy;
                }
                return;
            }
            throw new AssertionError();
        }

        public final void flush() {
            if ($assertionsDisabled || !Thread.holdsLock(SpdyStream.this)) {
                checkNotClosed();
                if (this.pos > 0) {
                    writeFrame(false);
                    SpdyStream.this.connection.flush();
                    return;
                }
                return;
            }
            throw new AssertionError();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0028, code lost:
            if (com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream.access$1200(r3.this$0).finished != false) goto L_0x002d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x002a, code lost:
            writeFrame(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x002d, code lost:
            com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream.access$800(r3.this$0).flush();
            com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream.access$1100(r3.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void close() {
            /*
                r3 = this;
                r2 = 1
                boolean r0 = $assertionsDisabled
                if (r0 != 0) goto L_0x0013
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream r0 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream.this
                boolean r0 = java.lang.Thread.holdsLock(r0)
                if (r0 == 0) goto L_0x0013
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            L_0x0013:
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream r1 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream.this
                monitor-enter(r1)
                boolean r0 = r3.closed     // Catch:{ all -> 0x003c }
                if (r0 == 0) goto L_0x001c
                monitor-exit(r1)     // Catch:{ all -> 0x003c }
            L_0x001b:
                return
            L_0x001c:
                r0 = 1
                r3.closed = r0     // Catch:{ all -> 0x003c }
                monitor-exit(r1)     // Catch:{ all -> 0x003c }
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream r0 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream.this
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream$SpdyDataOutputStream r0 = r0.out
                boolean r0 = r0.finished
                if (r0 != 0) goto L_0x002d
                r3.writeFrame(r2)
            L_0x002d:
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream r0 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream.this
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r0 = r0.connection
                r0.flush()
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream r0 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream.this
                r0.cancelStreamIfNecessary()
                goto L_0x001b
            L_0x003c:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x003c }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream.SpdyDataOutputStream.close():void");
        }

        private void writeFrame(boolean outFinished) {
            if ($assertionsDisabled || !Thread.holdsLock(SpdyStream.this)) {
                int length = this.pos;
                synchronized (SpdyStream.this) {
                    waitUntilWritable(length, outFinished);
                    this.unacknowledgedBytes += length;
                }
                SpdyStream.this.connection.writeData(SpdyStream.this.id, outFinished, this.buffer, 0, this.pos);
                this.pos = 0;
                return;
            }
            throw new AssertionError();
        }

        private void waitUntilWritable(int count, boolean last) {
            do {
                try {
                    if (this.unacknowledgedBytes + count >= SpdyStream.this.writeWindowSize) {
                        SpdyStream.this.wait();
                        if (!last && this.closed) {
                            throw new IOException("stream closed");
                        } else if (this.finished) {
                            throw new IOException("stream finished");
                        }
                    } else {
                        return;
                    }
                } catch (InterruptedException e) {
                    throw new InterruptedIOException();
                }
            } while (SpdyStream.this.errorCode == null);
            throw new IOException("stream was reset:2 " + SpdyStream.this.errorCode);
        }

        private void checkNotClosed() {
            synchronized (SpdyStream.this) {
                if (this.closed) {
                    throw new IOException("stream closed");
                } else if (this.finished) {
                    throw new IOException("stream finished");
                } else if (SpdyStream.this.errorCode != null) {
                    throw new IOException("stream was reset:1 " + SpdyStream.this.errorCode);
                }
            }
        }
    }

    SpdyStream(int id2, SpdyConnection connection2, boolean outFinished, boolean inFinished, int priority2, List<String> requestHeaders2, Settings settings) {
        this.readTimeoutMillis = 0;
        this.in = new SpdyDataInputStream();
        this.out = new SpdyDataOutputStream();
        this.errorCode = null;
        if (connection2 == null) {
            throw new NullPointerException("connection == null");
        } else if (requestHeaders2 == null) {
            throw new NullPointerException("requestHeaders == null");
        } else {
            this.id = id2;
            this.connection = connection2;
            this.in.finished = inFinished;
            this.out.finished = outFinished;
            this.priority = priority2;
            this.requestHeaders = requestHeaders2;
            setSettings(settings);
        }
    }

    SpdyStream(int id2, SpdyConnection connection2, boolean outFinished, boolean inFinished, int priority2, List<String> requestHeaders2, Settings settings, TransportContext netContext) {
        this(id2, connection2, outFinished, inFinished, priority2, requestHeaders2, settings);
        this.mNetContext = netContext;
    }

    public final synchronized boolean isOpen() {
        boolean z = false;
        synchronized (this) {
            try {
                if (this.errorCode == null) {
                    if ((!this.in.finished && !this.in.closed) || ((!this.out.finished && !this.out.closed) || this.responseHeaders == null)) {
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    public final boolean isLocallyInitiated() {
        boolean streamIsClient;
        if (this.id % 2 == 1) {
            streamIsClient = true;
        } else {
            streamIsClient = false;
        }
        return this.connection.client == streamIsClient;
    }

    public final SpdyConnection getConnection() {
        return this.connection;
    }

    public final List<String> getRequestHeaders() {
        return this.requestHeaders;
    }

    public final synchronized List<String> getResponseHeaders() {
        long end = 0;
        long remaining = 0;
        try {
            if (this.readTimeoutMillis > 0) {
                remaining = this.readTimeoutMillis;
                end = System.currentTimeMillis() + this.readTimeoutMillis;
            }
            long monitorStartTime = System.currentTimeMillis();
            while (true) {
                if (this.responseHeaders == null && this.errorCode == null) {
                    if (this.readTimeoutMillis > 0) {
                        if (remaining <= 0) {
                            LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "SpdyStream#getResponseHeaders streamId=[" + getId() + "]  Wait [" + remaining + "ms]  break ");
                            break;
                        }
                        LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "SpdyStream#getResponseHeaders streamId=[" + getId() + "]  Wait [" + remaining + "ms]  ");
                        wait(remaining);
                        remaining = end - System.currentTimeMillis();
                    } else {
                        LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "SpdyStream#getResponseHeaders streamId=[" + getId() + "]  readTimeoutMillis=[" + this.readTimeoutMillis + "].  waiting ");
                        wait();
                    }
                } else {
                    break;
                }
            }
            long monitorEndTime = System.currentTimeMillis();
            if (this.responseHeaders != null) {
                LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "SpdyStream#getResponseHeaders  return responseHeaders");
            } else if (this.errorCode != null) {
                throw new IOException("stream was reset:4 " + this.errorCode);
            } else if (this.readTimeoutMillis > 0) {
                throw new SocketTimeoutException("stream was reset:4 Spdy Stream Read Timeout. readTimeoutMillis=[" + this.readTimeoutMillis + "ms] realReadTimeoutMillis=[" + (monitorEndTime - monitorStartTime) + "ms] ");
            } else {
                throw new SocketTimeoutException("stream was reset:4 Unknown Error.");
            }
        } catch (InterruptedException e) {
            InterruptedIOException rethrow = new InterruptedIOException();
            rethrow.initCause(e);
            throw rethrow;
        }
        return this.responseHeaders;
    }

    public final synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public final void reply(List<String> responseHeaders2, boolean out2) {
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            boolean outFinished = false;
            synchronized (this) {
                if (responseHeaders2 == null) {
                    throw new NullPointerException("responseHeaders == null");
                } else if (isLocallyInitiated()) {
                    throw new IllegalStateException("cannot reply to a locally initiated stream");
                } else if (this.responseHeaders != null) {
                    throw new IllegalStateException("reply already sent");
                } else {
                    this.responseHeaders = responseHeaders2;
                    if (!out2) {
                        this.out.finished = true;
                        outFinished = true;
                    }
                }
            }
            this.connection.writeSynReply(this.id, outFinished, responseHeaders2);
            return;
        }
        throw new AssertionError();
    }

    public final void setReadTimeout(long readTimeoutMillis2) {
        this.readTimeoutMillis = readTimeoutMillis2;
    }

    public final long getReadTimeoutMillis() {
        return this.readTimeoutMillis;
    }

    public final InputStream getInputStream() {
        return this.in;
    }

    public final OutputStream getOutputStream() {
        synchronized (this) {
            try {
                if (this.responseHeaders == null && !isLocallyInitiated()) {
                    throw new IllegalStateException("reply before requesting the output stream");
                }
            }
        }
        return this.out;
    }

    public final void close(ErrorCode rstStatusCode) {
        if (closeInternal(rstStatusCode)) {
            this.connection.writeSynReset(this.id, rstStatusCode);
        }
    }

    public final void closeLater(ErrorCode errorCode2) {
        if (closeInternal(errorCode2)) {
            this.connection.writeSynResetLater(this.id, errorCode2);
        }
    }

    private boolean closeInternal(ErrorCode errorCode2) {
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            synchronized (this) {
                if (this.errorCode != null) {
                    return false;
                }
                if (this.in.finished && this.out.finished) {
                    return false;
                }
                LogCatUtil.error((String) InnerLogUtil.MWALLET_SPDY_TAG, "SpdyStream#closeInternal  errorCode=" + errorCode2);
                this.errorCode = errorCode2;
                notifyAll();
                this.connection.removeStream(this.id);
                return true;
            }
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public final void receiveHeaders(List<String> headers, HeadersMode headersMode) {
        LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "readSynReply: receiveHeaders: streamId=" + this.id);
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            ErrorCode errorCode2 = null;
            boolean open = true;
            synchronized (this) {
                if (this.responseHeaders == null) {
                    if (headersMode.failIfHeadersAbsent()) {
                        errorCode2 = ErrorCode.PROTOCOL_ERROR;
                    } else {
                        this.responseHeaders = headers;
                        open = isOpen();
                        notifyAll();
                    }
                } else if (headersMode.failIfHeadersPresent()) {
                    errorCode2 = ErrorCode.STREAM_IN_USE;
                } else {
                    List newHeaders = new ArrayList();
                    newHeaders.addAll(this.responseHeaders);
                    newHeaders.addAll(headers);
                    this.responseHeaders = newHeaders;
                }
            }
            if (errorCode2 != null) {
                closeLater(errorCode2);
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "readSynReply: receiveHeaders -> errorCode=[" + errorCode2.toString() + "] streamId=" + this.id);
            } else if (!open) {
                this.connection.removeStream(this.id);
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "readSynReply: receiveHeaders -> removeStream streamId=" + this.id);
            }
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void receiveData(InputStream in2, int length) {
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            this.in.receive(in2, length);
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public final void receiveFin() {
        boolean open;
        LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "readSynReply: receiveFin streamId=" + this.id);
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            synchronized (this) {
                this.in.finished = true;
                open = isOpen();
                notifyAll();
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "readSynReply: receiveFin notifyAll streamId=" + this.id);
            }
            if (!open) {
                this.connection.removeStream(this.id);
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "readSynReply: receiveFin remove streamId=" + this.id);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void receiveRstStream(ErrorCode errorCode2) {
        if (this.errorCode == null) {
            this.errorCode = errorCode2;
            LogCatUtil.error((String) InnerLogUtil.MWALLET_SPDY_TAG, "SpdyStream#receiveRstStream   errorCode=" + errorCode2);
            notifyAll();
        }
    }

    private void setSettings(Settings settings) {
        int i = 65536;
        if ($assertionsDisabled || Thread.holdsLock(this.connection)) {
            if (settings != null) {
                i = settings.getInitialWindowSize(65536);
            }
            this.writeWindowSize = i;
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public final void receiveSettings(Settings settings) {
        if ($assertionsDisabled || Thread.holdsLock(this)) {
            setSettings(settings);
            notifyAll();
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void receiveWindowUpdate(int deltaWindowSize) {
        SpdyDataOutputStream spdyDataOutputStream = this.out;
        spdyDataOutputStream.unacknowledgedBytes = spdyDataOutputStream.unacknowledgedBytes - deltaWindowSize;
        notifyAll();
    }

    /* access modifiers changed from: 0000 */
    public final int getPriority() {
        return this.priority;
    }

    /* access modifiers changed from: private */
    public void cancelStreamIfNecessary() {
        boolean cancel;
        boolean open;
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            synchronized (this) {
                cancel = !this.in.finished && this.in.closed && (this.out.finished || this.out.closed);
                open = isOpen();
            }
            if (cancel) {
                close(ErrorCode.CANCEL);
            } else if (!open) {
                this.connection.removeStream(this.id);
            }
        } else {
            throw new AssertionError();
        }
    }

    public final TransportContext getNetContext() {
        return this.mNetContext;
    }

    public final int getId() {
        return this.id;
    }
}
