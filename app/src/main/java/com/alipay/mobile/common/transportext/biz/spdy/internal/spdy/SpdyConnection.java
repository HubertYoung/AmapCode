package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.internal.NamedRunnable;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.FrameReader.Handler;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.LongLinkTransportManager;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl;
import com.alipay.mobile.common.transportext.biz.util.TlvUtil;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class SpdyConnection implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled;
    /* access modifiers changed from: private */
    public static final ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.daemonThreadFactory("OkHttp SpdyConnection"));
    final boolean client;
    private Connection connection;
    /* access modifiers changed from: private */
    public final FrameReader frameReader;
    private final FrameWriter frameWriter;
    /* access modifiers changed from: private */
    public final IncomingStreamHandler handler;
    /* access modifiers changed from: private */
    public final String hostName;
    private long idleStartTimeNs;
    /* access modifiers changed from: private */
    public int lastGoodStreamId;
    private long lastStreamTime;
    private int nextPingId;
    /* access modifiers changed from: private */
    public int nextStreamId;
    private Map<Integer, Ping> pings;
    Settings settings;
    /* access modifiers changed from: private */
    public boolean shutdown;
    /* access modifiers changed from: private */
    public final Map<Integer, SpdyStream> streams;
    final Variant variant;

    public class Builder {
        /* access modifiers changed from: private */
        public boolean client;
        /* access modifiers changed from: private */
        public Connection connection;
        /* access modifiers changed from: private */
        public IncomingStreamHandler handler;
        /* access modifiers changed from: private */
        public String hostName;
        /* access modifiers changed from: private */
        public InputStream in;
        /* access modifiers changed from: private */
        public OutputStream out;
        /* access modifiers changed from: private */
        public Variant variant;

        public Builder(boolean client2, Socket socket) {
            this("", client2, socket.getInputStream(), socket.getOutputStream());
        }

        public Builder(boolean client2, InputStream in2, OutputStream out2) {
            this("", client2, in2, out2);
        }

        public Builder(String hostName2, boolean client2, Socket socket) {
            this(hostName2, client2, socket.getInputStream(), socket.getOutputStream());
        }

        public Builder(String hostName2, boolean client2, InputStream in2, OutputStream out2) {
            this.handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
            this.variant = Variant.SPDY3;
            this.hostName = hostName2;
            this.client = client2;
            this.in = in2;
            this.out = out2;
        }

        public Builder handler(IncomingStreamHandler handler2) {
            this.handler = handler2;
            return this;
        }

        public Builder spdy3() {
            this.variant = Variant.SPDY3;
            return this;
        }

        public Builder http20Draft06() {
            return this;
        }

        public Builder connection(Connection connection2) {
            this.connection = connection2;
            return this;
        }

        public SpdyConnection build() {
            return new SpdyConnection(this);
        }
    }

    class Reader implements Handler, Runnable {
        private Reader() {
        }

        public void run() {
            ErrorCode connectionErrorCode = ErrorCode.INTERNAL_ERROR;
            ErrorCode streamErrorCode = ErrorCode.INTERNAL_ERROR;
            do {
                try {
                } catch (IOException e) {
                    connectionErrorCode = ErrorCode.PROTOCOL_ERROR;
                    streamErrorCode = ErrorCode.PROTOCOL_ERROR;
                    try {
                        return;
                    } catch (Exception ignored) {
                        LogCatUtil.error((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) ignored);
                        return;
                    }
                } finally {
                    try {
                        SpdyConnection.this.close(connectionErrorCode, streamErrorCode);
                    } catch (Exception ignored2) {
                        LogCatUtil.error((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) ignored2);
                    }
                }
            } while (SpdyConnection.this.frameReader.nextFrame(this));
            connectionErrorCode = ErrorCode.NO_ERROR;
            streamErrorCode = ErrorCode.CANCEL;
            try {
            } catch (Exception ignored3) {
                LogCatUtil.error((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) ignored3);
            }
        }

        public void data(boolean inFinished, int streamId, InputStream in, int length) {
            SpdyStream dataStream = SpdyConnection.this.getStream(streamId);
            if (dataStream == null) {
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "nextFrame dataFrame. dataStream(" + streamId + ") is null.");
                SpdyConnection.this.writeSynResetLater(streamId, ErrorCode.INVALID_STREAM);
                Util.skipByReading(in, (long) length);
                return;
            }
            dataStream.receiveData(in, length);
            if (inFinished) {
                dataStream.receiveFin();
            }
        }

        public void tcpData(InputStream in, int length) {
            try {
                byte[] tcpData = new byte[length];
                TlvUtil.readDataFully(in, length, tcpData, 0);
                LongLinkTransportManager.getInstance().onRecvData(tcpData);
                LogCatUtil.info(LongLinkTransportManager.TAG, "LongLink Receive Tcp Data:" + length);
            } catch (Throwable e) {
                if (e instanceof IOException) {
                    throw ((IOException) e);
                }
                InterruptedIOException ioException = new InterruptedIOException();
                ioException.initCause(e);
                throw ioException;
            }
        }

        public void preReadSynReplyHeaders(int streamId) {
            try {
                SpdyStream stream = SpdyConnection.this.getStream(streamId);
                if (stream == null) {
                    LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "readSynReply. preReadSynReplyHeaders  stream is null.  streamId=" + streamId + ", current streams=[" + SpdyConnection.this.streams.keySet().toString() + "]");
                    return;
                }
                TransportContext netContext = stream.getNetContext();
                if (netContext != null) {
                    netContext.getCurrentDataContainer().timeItemRelease(RPCDataItems.WAIT_TIME);
                }
            } catch (Exception e) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) e);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00dc, code lost:
            if (r19.failIfStreamPresent() == false) goto L_0x00f1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00de, code lost:
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(com.alipay.mobile.common.transportext.util.InnerLogUtil.MWALLET_SPDY_TAG, "readSynReply: headers handler: failIfStreamPresent.");
            r10.closeLater(com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.ErrorCode.PROTOCOL_ERROR);
            r12.this$0.removeStream(r15);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00f1, code lost:
            r10.receiveHeaders(r18, r19);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00f8, code lost:
            if (r14 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00fa, code lost:
            r10.receiveFin();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void headers(boolean r13, boolean r14, int r15, int r16, int r17, java.util.List<java.lang.String> r18, com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.HeadersMode r19) {
            /*
                r12 = this;
                android.content.Context r3 = com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv.getAppContext()
                boolean r3 = com.alipay.mobile.common.transport.utils.MiscUtils.isDebugger(r3)
                if (r3 == 0) goto L_0x0022
                java.lang.String r3 = "MWALLET_SPDY_LOG"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                java.lang.String r5 = "Response Headers: "
                r4.<init>(r5)
                java.lang.String r5 = r18.toString()
                java.lang.StringBuilder r4 = r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.alipay.mobile.common.transport.utils.LogCatUtil.info(r3, r4)
            L_0x0022:
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r11 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this
                monitor-enter(r11)
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                boolean r3 = r3.shutdown     // Catch:{ all -> 0x0074 }
                if (r3 == 0) goto L_0x002f
                monitor-exit(r11)     // Catch:{ all -> 0x0074 }
            L_0x002e:
                return
            L_0x002f:
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream r10 = r3.getStream(r15)     // Catch:{ all -> 0x0074 }
                java.lang.String r3 = "MWALLET_SPDY_LOG"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0074 }
                java.lang.String r5 = "readSynReply: headers handler: streamId = "
                r4.<init>(r5)     // Catch:{ all -> 0x0074 }
                java.lang.StringBuilder r4 = r4.append(r15)     // Catch:{ all -> 0x0074 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transport.utils.LogCatUtil.info(r3, r4)     // Catch:{ all -> 0x0074 }
                if (r10 != 0) goto L_0x00d7
                java.lang.String r3 = "MWALLET_SPDY_LOG"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0074 }
                java.lang.String r5 = "readSynReply: headers handler: stream("
                r4.<init>(r5)     // Catch:{ all -> 0x0074 }
                java.lang.StringBuilder r4 = r4.append(r15)     // Catch:{ all -> 0x0074 }
                java.lang.String r5 = ") is null"
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0074 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transport.utils.LogCatUtil.info(r3, r4)     // Catch:{ all -> 0x0074 }
                boolean r3 = r19.failIfStreamAbsent()     // Catch:{ all -> 0x0074 }
                if (r3 == 0) goto L_0x0077
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.ErrorCode r4 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.ErrorCode.INVALID_STREAM     // Catch:{ all -> 0x0074 }
                r3.writeSynResetLater(r15, r4)     // Catch:{ all -> 0x0074 }
                monitor-exit(r11)     // Catch:{ all -> 0x0074 }
                goto L_0x002e
            L_0x0074:
                r3 = move-exception
                monitor-exit(r11)     // Catch:{ all -> 0x0074 }
                throw r3
            L_0x0077:
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                int r3 = r3.lastGoodStreamId     // Catch:{ all -> 0x0074 }
                if (r15 > r3) goto L_0x0081
                monitor-exit(r11)     // Catch:{ all -> 0x0074 }
                goto L_0x002e
            L_0x0081:
                int r3 = r15 % 2
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r4 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                int r4 = r4.nextStreamId     // Catch:{ all -> 0x0074 }
                int r4 = r4 % 2
                if (r3 != r4) goto L_0x008f
                monitor-exit(r11)     // Catch:{ all -> 0x0074 }
                goto L_0x002e
            L_0x008f:
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream r2 = new com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r4 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.Settings r9 = r3.settings     // Catch:{ all -> 0x0074 }
                r3 = r15
                r5 = r13
                r6 = r14
                r7 = r17
                r8 = r18
                r2.<init>(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                r3.lastGoodStreamId = r15     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                java.util.Map r3 = r3.streams     // Catch:{ all -> 0x0074 }
                java.lang.Integer r4 = java.lang.Integer.valueOf(r15)     // Catch:{ all -> 0x0074 }
                r3.put(r4, r2)     // Catch:{ all -> 0x0074 }
                java.util.concurrent.ExecutorService r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.executor     // Catch:{ all -> 0x0074 }
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection$Reader$1 r4 = new com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection$Reader$1     // Catch:{ all -> 0x0074 }
                java.lang.String r5 = "OkHttp Callback %s stream %d"
                r6 = 2
                java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x0074 }
                r7 = 0
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r8 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x0074 }
                java.lang.String r8 = r8.hostName     // Catch:{ all -> 0x0074 }
                r6[r7] = r8     // Catch:{ all -> 0x0074 }
                r7 = 1
                java.lang.Integer r8 = java.lang.Integer.valueOf(r15)     // Catch:{ all -> 0x0074 }
                r6[r7] = r8     // Catch:{ all -> 0x0074 }
                r4.<init>(r5, r6, r2)     // Catch:{ all -> 0x0074 }
                r3.submit(r4)     // Catch:{ all -> 0x0074 }
                monitor-exit(r11)     // Catch:{ all -> 0x0074 }
                goto L_0x002e
            L_0x00d7:
                monitor-exit(r11)     // Catch:{ all -> 0x0074 }
                boolean r3 = r19.failIfStreamPresent()
                if (r3 == 0) goto L_0x00f1
                java.lang.String r3 = "MWALLET_SPDY_LOG"
                java.lang.String r4 = "readSynReply: headers handler: failIfStreamPresent."
                com.alipay.mobile.common.transport.utils.LogCatUtil.info(r3, r4)
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.ErrorCode r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.ErrorCode.PROTOCOL_ERROR
                r10.closeLater(r3)
                com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection r3 = com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.this
                r3.removeStream(r15)
                goto L_0x002e
            L_0x00f1:
                r0 = r18
                r1 = r19
                r10.receiveHeaders(r0, r1)
                if (r14 == 0) goto L_0x002e
                r10.receiveFin()
                goto L_0x002e
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection.Reader.headers(boolean, boolean, int, int, int, java.util.List, com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.HeadersMode):void");
        }

        public void rstStream(int streamId, ErrorCode errorCode) {
            SpdyStream rstStream = SpdyConnection.this.removeStream(streamId);
            if (rstStream != null) {
                rstStream.receiveRstStream(errorCode);
            }
        }

        public void settings(boolean clearPrevious, Settings newSettings) {
            SpdyStream[] streamsToNotify = null;
            synchronized (SpdyConnection.this) {
                if (SpdyConnection.this.settings == null || clearPrevious) {
                    SpdyConnection.this.settings = newSettings;
                } else {
                    SpdyConnection.this.settings.merge(newSettings);
                }
                if (!SpdyConnection.this.streams.isEmpty()) {
                    streamsToNotify = (SpdyStream[]) SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
                }
            }
            if (streamsToNotify != null) {
                for (SpdyStream stream : streamsToNotify) {
                    synchronized (stream) {
                        synchronized (SpdyConnection.this) {
                            stream.receiveSettings(SpdyConnection.this.settings);
                        }
                    }
                }
            }
        }

        public void noop() {
        }

        public void ping(boolean reply, int payload1, int payload2) {
            if (reply) {
                Ping ping = SpdyConnection.this.removePing(payload1);
                if (ping != null) {
                    ping.receive();
                }
                SpdyLongLinkConnManagerImpl.getInstance().notifyPingResponse(ping);
                SpdyConnection.this.setIdle(false);
                return;
            }
            SpdyConnection.this.writePingLater(true, payload1, payload2, null);
        }

        public void goAway(int lastGoodStreamId, ErrorCode errorCode) {
            synchronized (SpdyConnection.this) {
                SpdyConnection.this.shutdown = true;
                Iterator i = SpdyConnection.this.streams.entrySet().iterator();
                while (i.hasNext()) {
                    Entry entry = (Entry) i.next();
                    if (((Integer) entry.getKey()).intValue() > lastGoodStreamId && ((SpdyStream) entry.getValue()).isLocallyInitiated()) {
                        ((SpdyStream) entry.getValue()).receiveRstStream(ErrorCode.REFUSED_STREAM);
                        i.remove();
                    }
                }
            }
        }

        public void windowUpdate(int streamId, int deltaWindowSize, boolean endFlowControl) {
            if (streamId != 0) {
                SpdyStream stream = SpdyConnection.this.getStream(streamId);
                if (stream != null) {
                    stream.receiveWindowUpdate(deltaWindowSize);
                }
            }
        }

        public void priority(int streamId, int priority) {
        }
    }

    static {
        boolean z;
        if (!SpdyConnection.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        $assertionsDisabled = z;
    }

    private SpdyConnection(Builder builder) {
        int i = 1;
        this.streams = new HashMap();
        this.idleStartTimeNs = System.nanoTime();
        this.lastStreamTime = System.currentTimeMillis();
        this.variant = builder.variant;
        this.client = builder.client;
        this.handler = builder.handler;
        this.frameReader = this.variant.newReader(builder.in, this.client);
        this.frameWriter = this.variant.newWriter(builder.out, this.client);
        this.nextStreamId = builder.client ? 1 : 2;
        this.nextPingId = !builder.client ? 2 : i;
        this.connection = builder.connection;
        this.hostName = builder.hostName;
        new Thread(new Reader(), "Spdy Reader " + this.hostName).start();
    }

    public final synchronized int openStreamCount() {
        return this.streams.size();
    }

    /* access modifiers changed from: private */
    public synchronized SpdyStream getStream(int id) {
        return this.streams.get(Integer.valueOf(id));
    }

    /* access modifiers changed from: 0000 */
    public final synchronized SpdyStream removeStream(int streamId) {
        SpdyStream stream;
        stream = this.streams.remove(Integer.valueOf(streamId));
        if (stream != null && this.streams.isEmpty()) {
            setIdle(true);
        }
        return stream;
    }

    /* access modifiers changed from: private */
    public synchronized void setIdle(boolean value) {
        this.idleStartTimeNs = value ? System.nanoTime() : Long.MAX_VALUE;
    }

    public final synchronized boolean isIdle() {
        return this.idleStartTimeNs != Long.MAX_VALUE;
    }

    public final synchronized long getIdleStartTimeNs() {
        try {
        }
        return this.idleStartTimeNs;
    }

    public final SpdyStream newStream(List<String> requestHeaders, boolean out, boolean in, TransportContext netContext) {
        boolean outFinished;
        int streamId;
        SpdyStream stream;
        boolean inFinished = true;
        if (!out) {
            outFinished = true;
        } else {
            outFinished = false;
        }
        if (in) {
            inFinished = false;
        }
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (this.shutdown) {
                    throw new IOException("shutdown");
                }
                streamId = this.nextStreamId;
                this.nextStreamId += 2;
                stream = new SpdyStream(streamId, this, outFinished, inFinished, 0, requestHeaders, this.settings, netContext);
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "syn_stream create streamId=" + streamId);
                if (stream.isOpen()) {
                    this.streams.put(Integer.valueOf(streamId), stream);
                    setIdle(false);
                    setLastStreamTime();
                    LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "syn_stream save streamId=" + streamId);
                }
            }
            this.frameWriter.synStream(outFinished, inFinished, streamId, 0, 0, 0, requestHeaders);
        }
        return stream;
    }

    /* access modifiers changed from: 0000 */
    public final void writeSynReply(int streamId, boolean outFinished, List<String> alternating) {
        this.frameWriter.synReply(outFinished, streamId, alternating);
    }

    public final void writeData(int streamId, boolean outFinished, byte[] buffer, int offset, int byteCount) {
        this.frameWriter.data(outFinished, streamId, buffer, offset, byteCount);
    }

    public final void writeTcpData(byte[] data) {
        this.frameWriter.tcpData(data);
    }

    /* access modifiers changed from: 0000 */
    public final void writeSynResetLater(int streamId, ErrorCode errorCode) {
        final int i = streamId;
        final ErrorCode errorCode2 = errorCode;
        executor.submit(new NamedRunnable("OkHttp SPDY Writer %s stream %d", new Object[]{this.hostName, Integer.valueOf(streamId)}) {
            public void execute() {
                try {
                    SpdyConnection.this.writeSynReset(i, errorCode2);
                } catch (IOException ignored) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "writeSynReset exception : " + ignored.toString());
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public final void writeSynReset(int streamId, ErrorCode statusCode) {
        this.frameWriter.rstStream(streamId, statusCode);
    }

    /* access modifiers changed from: 0000 */
    public final void writeWindowUpdateLater(int streamId, int deltaWindowSize) {
        final int i = streamId;
        final int i2 = deltaWindowSize;
        executor.submit(new NamedRunnable("OkHttp SPDY Writer %s stream %d", new Object[]{this.hostName, Integer.valueOf(streamId)}) {
            public void execute() {
                try {
                    SpdyConnection.this.writeWindowUpdate(i, i2);
                } catch (IOException ignored) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "writeWindowUpdate exception : " + ignored.toString());
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public final void writeWindowUpdate(int streamId, int deltaWindowSize) {
        this.frameWriter.windowUpdate(streamId, deltaWindowSize);
    }

    public final Ping ping() {
        int pingId;
        Ping ping = new Ping();
        synchronized (this) {
            try {
                if (this.shutdown) {
                    throw new IOException("shutdown");
                }
                pingId = this.nextPingId;
                this.nextPingId += 2;
                if (this.pings == null) {
                    this.pings = new HashMap();
                }
                this.pings.put(Integer.valueOf(pingId), ping);
                setIdle(false);
            }
        }
        writePing(false, pingId, 1330343787, ping);
        return ping;
    }

    /* access modifiers changed from: private */
    public void writePingLater(boolean reply, int payload1, int payload2, Ping ping) {
        final boolean z = reply;
        final int i = payload1;
        final int i2 = payload2;
        final Ping ping2 = ping;
        executor.submit(new NamedRunnable("OkHttp SPDY Writer %s ping %08x%08x", new Object[]{this.hostName, Integer.valueOf(payload1), Integer.valueOf(payload2)}) {
            public void execute() {
                try {
                    SpdyConnection.this.writePing(z, i, i2, ping2);
                } catch (IOException ignored) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "writePing exception : " + ignored.toString());
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void writePing(boolean reply, int payload1, int payload2, Ping ping) {
        synchronized (this.frameWriter) {
            if (ping != null) {
                ping.send();
            }
            this.frameWriter.ping(reply, payload1, payload2);
        }
    }

    /* access modifiers changed from: private */
    public synchronized Ping removePing(int id) {
        return this.pings != null ? this.pings.remove(Integer.valueOf(id)) : null;
    }

    public final void noop() {
        this.frameWriter.noop();
    }

    public final void flush() {
        this.frameWriter.flush();
    }

    public final void shutdown(ErrorCode statusCode) {
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (!this.shutdown) {
                    this.shutdown = true;
                    int lastGoodStreamId2 = this.lastGoodStreamId;
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "shutdown lastGoodStreamId=[" + lastGoodStreamId2 + "]  statusCode=[" + (statusCode != null ? statusCode.toString() : "is null") + "]");
                    this.frameWriter.goAway(lastGoodStreamId2, statusCode);
                }
            }
        }
    }

    public final void close() {
        close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    /* access modifiers changed from: private */
    public void close(ErrorCode connectionCode, ErrorCode streamCode) {
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            IOException thrown = null;
            try {
                shutdown(connectionCode);
            } catch (IOException e) {
                LogCatUtil.error(InnerLogUtil.MWALLET_SPDY_TAG, "SpdyConnection#close  shutdown error", e);
                thrown = e;
            }
            SpdyStream[] streamsToClose = null;
            Ping[] pingsToCancel = null;
            synchronized (this) {
                if (!this.streams.isEmpty()) {
                    streamsToClose = (SpdyStream[]) this.streams.values().toArray(new SpdyStream[this.streams.size()]);
                    this.streams.clear();
                    setIdle(false);
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (String) "SpdyConnection#close  shutdown error. streams clear");
                }
                if (this.pings != null) {
                    pingsToCancel = (Ping[]) this.pings.values().toArray(new Ping[this.pings.size()]);
                    this.pings = null;
                }
            }
            if (streamsToClose != null) {
                for (SpdyStream stream : streamsToClose) {
                    try {
                        stream.close(streamCode);
                        LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "SpdyConnection#close  stream.close,  streamId=[" + stream.getId() + "] ErrorCode=[" + (streamCode != null ? streamCode.toString() : "is null") + "]");
                    } catch (IOException e2) {
                        if (thrown != null) {
                            thrown = e2;
                        }
                    }
                }
            }
            if (pingsToCancel != null) {
                for (Ping cancel : pingsToCancel) {
                    cancel.cancel();
                }
            }
            try {
                this.frameReader.close();
            } catch (IOException e3) {
                LogCatUtil.error(InnerLogUtil.MWALLET_SPDY_TAG, "SpdyConnection#close  frameReader.close() error", e3);
                thrown = e3;
            }
            try {
                this.frameWriter.close();
            } catch (IOException e4) {
                LogCatUtil.error(InnerLogUtil.MWALLET_SPDY_TAG, "SpdyConnection#close  frameWriter.close() error", e4);
                if (thrown == null) {
                    thrown = e4;
                }
            }
            try {
                this.connection.close();
            } catch (IOException e5) {
                LogCatUtil.error(InnerLogUtil.MWALLET_SPDY_TAG, "SpdyConnection#close  connection.close() error", e5);
                if (thrown == null) {
                    thrown = e5;
                }
            }
            if (thrown != null) {
                throw thrown;
            }
            return;
        }
        throw new AssertionError();
    }

    public final void sendConnectionHeader() {
        this.frameWriter.connectionHeader();
        this.frameWriter.settings(new Settings());
    }

    public final void readConnectionHeader() {
        this.frameReader.readConnectionHeader();
    }

    public final void setLastStreamTime() {
        this.lastStreamTime = System.currentTimeMillis();
    }

    public final boolean isShutdown() {
        return this.shutdown;
    }
}
