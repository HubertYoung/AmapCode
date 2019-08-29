package com.alipay.android.phone.mobilesdk.socketcraft;

import java.io.EOFException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLSession;

public class SSLSocketChannel2 implements WrappedByteChannel, ByteChannel {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected static ByteBuffer emptybuffer = ByteBuffer.allocate(0);
    protected int bufferallocations = 0;
    protected ExecutorService exec;
    protected ByteBuffer inCrypt;
    protected ByteBuffer inData;
    protected ByteBuffer outCrypt;
    protected SSLEngineResult readEngineResult;
    protected SelectionKey selectionKey;
    protected SocketChannel socketChannel;
    protected SSLEngine sslEngine;
    protected List<Future<?>> tasks;
    protected SSLEngineResult writeEngineResult;

    static {
        boolean z;
        if (!SSLSocketChannel2.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        $assertionsDisabled = z;
    }

    public SSLSocketChannel2(SocketChannel channel, SSLEngine sslEngine2, ExecutorService exec2, SelectionKey key) {
        if (channel == null || sslEngine2 == null || exec2 == null) {
            throw new IllegalArgumentException("parameter must not be null");
        }
        this.socketChannel = channel;
        this.sslEngine = sslEngine2;
        this.exec = exec2;
        SSLEngineResult sSLEngineResult = new SSLEngineResult(Status.BUFFER_UNDERFLOW, sslEngine2.getHandshakeStatus(), 0, 0);
        this.writeEngineResult = sSLEngineResult;
        this.readEngineResult = sSLEngineResult;
        this.tasks = new ArrayList(3);
        if (key != null) {
            key.interestOps(key.interestOps() | 4);
            this.selectionKey = key;
        }
        createBuffers(sslEngine2.getSession());
        this.socketChannel.write(wrap(emptybuffer));
        processHandshake();
    }

    private void consumeFutureUninterruptible(Future<?> f) {
        boolean interrupted = false;
        while (true) {
            try {
                f.get();
                break;
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        if (interrupted) {
            try {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0038, code lost:
        if (isBlocking() == false) goto L_0x000b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003a, code lost:
        consumeFutureUninterruptible(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void processHandshake() {
        /*
            r4 = this;
            monitor-enter(r4)
            javax.net.ssl.SSLEngine r2 = r4.sslEngine     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r2 = r2.getHandshakeStatus()     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x0031 }
            if (r2 != r3) goto L_0x000d
        L_0x000b:
            monitor-exit(r4)
            return
        L_0x000d:
            java.util.List<java.util.concurrent.Future<?>> r2 = r4.tasks     // Catch:{ all -> 0x0031 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0031 }
            if (r2 != 0) goto L_0x003e
            java.util.List<java.util.concurrent.Future<?>> r2 = r4.tasks     // Catch:{ all -> 0x0031 }
            java.util.Iterator r1 = r2.iterator()     // Catch:{ all -> 0x0031 }
        L_0x001b:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x003e
            java.lang.Object r0 = r1.next()     // Catch:{ all -> 0x0031 }
            java.util.concurrent.Future r0 = (java.util.concurrent.Future) r0     // Catch:{ all -> 0x0031 }
            boolean r2 = r0.isDone()     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x0034
            r1.remove()     // Catch:{ all -> 0x0031 }
            goto L_0x001b
        L_0x0031:
            r2 = move-exception
            monitor-exit(r4)
            throw r2
        L_0x0034:
            boolean r2 = r4.isBlocking()     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x000b
            r4.consumeFutureUninterruptible(r0)     // Catch:{ all -> 0x0031 }
            goto L_0x000b
        L_0x003e:
            javax.net.ssl.SSLEngine r2 = r4.sslEngine     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r2 = r2.getHandshakeStatus()     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP     // Catch:{ all -> 0x0031 }
            if (r2 != r3) goto L_0x0092
            boolean r2 = r4.isBlocking()     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x0058
            javax.net.ssl.SSLEngineResult r2 = r4.readEngineResult     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$Status r2 = r2.getStatus()     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$Status r3 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ all -> 0x0031 }
            if (r2 != r3) goto L_0x0075
        L_0x0058:
            java.nio.ByteBuffer r2 = r4.inCrypt     // Catch:{ all -> 0x0031 }
            r2.compact()     // Catch:{ all -> 0x0031 }
            java.nio.channels.SocketChannel r2 = r4.socketChannel     // Catch:{ all -> 0x0031 }
            java.nio.ByteBuffer r3 = r4.inCrypt     // Catch:{ all -> 0x0031 }
            int r2 = r2.read(r3)     // Catch:{ all -> 0x0031 }
            r3 = -1
            if (r2 != r3) goto L_0x0070
            java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0031 }
            java.lang.String r3 = "connection closed unexpectedly by peer"
            r2.<init>(r3)     // Catch:{ all -> 0x0031 }
            throw r2     // Catch:{ all -> 0x0031 }
        L_0x0070:
            java.nio.ByteBuffer r2 = r4.inCrypt     // Catch:{ all -> 0x0031 }
            r2.flip()     // Catch:{ all -> 0x0031 }
        L_0x0075:
            java.nio.ByteBuffer r2 = r4.inData     // Catch:{ all -> 0x0031 }
            r2.compact()     // Catch:{ all -> 0x0031 }
            r4.unwrap()     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult r2 = r4.readEngineResult     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r2 = r2.getHandshakeStatus()     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x0031 }
            if (r2 != r3) goto L_0x0092
            javax.net.ssl.SSLEngine r2 = r4.sslEngine     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLSession r2 = r2.getSession()     // Catch:{ all -> 0x0031 }
            r4.createBuffers(r2)     // Catch:{ all -> 0x0031 }
            goto L_0x000b
        L_0x0092:
            r4.consumeDelegatedTasks()     // Catch:{ all -> 0x0031 }
            java.util.List<java.util.concurrent.Future<?>> r2 = r4.tasks     // Catch:{ all -> 0x0031 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0031 }
            if (r2 != 0) goto L_0x00a7
            javax.net.ssl.SSLEngine r2 = r4.sslEngine     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r2 = r2.getHandshakeStatus()     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x0031 }
            if (r2 != r3) goto L_0x00c7
        L_0x00a7:
            java.nio.channels.SocketChannel r2 = r4.socketChannel     // Catch:{ all -> 0x0031 }
            java.nio.ByteBuffer r3 = emptybuffer     // Catch:{ all -> 0x0031 }
            java.nio.ByteBuffer r3 = r4.wrap(r3)     // Catch:{ all -> 0x0031 }
            r2.write(r3)     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult r2 = r4.writeEngineResult     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r2 = r2.getHandshakeStatus()     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x0031 }
            if (r2 != r3) goto L_0x00c7
            javax.net.ssl.SSLEngine r2 = r4.sslEngine     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLSession r2 = r2.getSession()     // Catch:{ all -> 0x0031 }
            r4.createBuffers(r2)     // Catch:{ all -> 0x0031 }
            goto L_0x000b
        L_0x00c7:
            boolean r2 = $assertionsDisabled     // Catch:{ all -> 0x0031 }
            if (r2 != 0) goto L_0x00db
            javax.net.ssl.SSLEngine r2 = r4.sslEngine     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r2 = r2.getHandshakeStatus()     // Catch:{ all -> 0x0031 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x0031 }
            if (r2 != r3) goto L_0x00db
            java.lang.AssertionError r2 = new java.lang.AssertionError     // Catch:{ all -> 0x0031 }
            r2.<init>()     // Catch:{ all -> 0x0031 }
            throw r2     // Catch:{ all -> 0x0031 }
        L_0x00db:
            r2 = 1
            r4.bufferallocations = r2     // Catch:{ all -> 0x0031 }
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilesdk.socketcraft.SSLSocketChannel2.processHandshake():void");
    }

    private synchronized ByteBuffer wrap(ByteBuffer b) {
        this.outCrypt.compact();
        this.writeEngineResult = this.sslEngine.wrap(b, this.outCrypt);
        this.outCrypt.flip();
        return this.outCrypt;
    }

    private synchronized ByteBuffer unwrap() {
        while (true) {
            int rem = this.inData.remaining();
            this.readEngineResult = this.sslEngine.unwrap(this.inCrypt, this.inData);
            if (this.readEngineResult.getStatus() != Status.OK || (rem == this.inData.remaining() && this.sslEngine.getHandshakeStatus() != HandshakeStatus.NEED_UNWRAP)) {
                this.inData.flip();
            }
        }
        this.inData.flip();
        return this.inData;
    }

    /* access modifiers changed from: protected */
    public void consumeDelegatedTasks() {
        while (true) {
            Runnable task = this.sslEngine.getDelegatedTask();
            if (task != null) {
                this.tasks.add(this.exec.submit(task));
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void createBuffers(SSLSession session) {
        int netBufferMax = session.getPacketBufferSize();
        int appBufferMax = Math.max(session.getApplicationBufferSize(), netBufferMax);
        if (this.inData == null) {
            this.inData = ByteBuffer.allocate(appBufferMax);
            this.outCrypt = ByteBuffer.allocate(netBufferMax);
            this.inCrypt = ByteBuffer.allocate(netBufferMax);
        } else {
            if (this.inData.capacity() != appBufferMax) {
                this.inData = ByteBuffer.allocate(appBufferMax);
            }
            if (this.outCrypt.capacity() != netBufferMax) {
                this.outCrypt = ByteBuffer.allocate(netBufferMax);
            }
            if (this.inCrypt.capacity() != netBufferMax) {
                this.inCrypt = ByteBuffer.allocate(netBufferMax);
            }
        }
        this.inData.rewind();
        this.inData.flip();
        this.inCrypt.rewind();
        this.inCrypt.flip();
        this.outCrypt.rewind();
        this.outCrypt.flip();
        this.bufferallocations++;
    }

    public int write(ByteBuffer src) {
        if (!isHandShakeComplete()) {
            processHandshake();
            return 0;
        }
        int write = this.socketChannel.write(wrap(src));
        if (this.writeEngineResult.getStatus() != Status.CLOSED) {
            return write;
        }
        throw new EOFException("Connection is closed");
    }

    public int read(ByteBuffer dst) {
        if (!dst.hasRemaining()) {
            return 0;
        }
        if (!isHandShakeComplete()) {
            if (isBlocking()) {
                while (!isHandShakeComplete()) {
                    processHandshake();
                }
            } else {
                processHandshake();
                if (!isHandShakeComplete()) {
                    return 0;
                }
            }
        }
        int purged = readRemaining(dst);
        if (purged != 0) {
            return purged;
        }
        if ($assertionsDisabled || this.inData.position() == 0) {
            this.inData.clear();
            if (!this.inCrypt.hasRemaining()) {
                this.inCrypt.clear();
            } else {
                this.inCrypt.compact();
            }
            if ((isBlocking() || this.readEngineResult.getStatus() == Status.BUFFER_UNDERFLOW) && this.socketChannel.read(this.inCrypt) == -1) {
                return -1;
            }
            this.inCrypt.flip();
            unwrap();
            int transfered = transfereTo(this.inData, dst);
            return (transfered != 0 || !isBlocking()) ? transfered : read(dst);
        }
        throw new AssertionError();
    }

    private int readRemaining(ByteBuffer dst) {
        if (this.inData.hasRemaining()) {
            return transfereTo(this.inData, dst);
        }
        if (!this.inData.hasRemaining()) {
            this.inData.clear();
        }
        if (this.inCrypt.hasRemaining()) {
            unwrap();
            int amount = transfereTo(this.inData, dst);
            if (this.readEngineResult.getStatus() == Status.CLOSED) {
                return -1;
            }
            if (amount > 0) {
                return amount;
            }
        }
        return 0;
    }

    public boolean isConnected() {
        return this.socketChannel.isConnected();
    }

    public void close() {
        this.sslEngine.closeOutbound();
        this.sslEngine.getSession().invalidate();
        if (this.socketChannel.isOpen()) {
            this.socketChannel.write(wrap(emptybuffer));
        }
        this.socketChannel.close();
        this.exec.shutdownNow();
    }

    private boolean isHandShakeComplete() {
        HandshakeStatus status = this.sslEngine.getHandshakeStatus();
        return status == HandshakeStatus.FINISHED || status == HandshakeStatus.NOT_HANDSHAKING;
    }

    public SelectableChannel configureBlocking(boolean b) {
        return this.socketChannel.configureBlocking(b);
    }

    public boolean connect(SocketAddress remote) {
        return this.socketChannel.connect(remote);
    }

    public boolean finishConnect() {
        return this.socketChannel.finishConnect();
    }

    public Socket socket() {
        return this.socketChannel.socket();
    }

    public boolean isInboundDone() {
        return this.sslEngine.isInboundDone();
    }

    public boolean isOpen() {
        return this.socketChannel.isOpen();
    }

    public boolean isNeedWrite() {
        return this.outCrypt.hasRemaining() || !isHandShakeComplete();
    }

    public void writeMore() {
        write(this.outCrypt);
    }

    public boolean isNeedRead() {
        return this.inData.hasRemaining() || !(!this.inCrypt.hasRemaining() || this.readEngineResult.getStatus() == Status.BUFFER_UNDERFLOW || this.readEngineResult.getStatus() == Status.CLOSED);
    }

    public int readMore(ByteBuffer dst) {
        return readRemaining(dst);
    }

    private int transfereTo(ByteBuffer from, ByteBuffer to) {
        int fremain = from.remaining();
        int toremain = to.remaining();
        if (fremain > toremain) {
            int limit = Math.min(fremain, toremain);
            for (int i = 0; i < limit; i++) {
                to.put(from.get());
            }
            return limit;
        }
        to.put(from);
        return fremain;
    }

    public boolean isBlocking() {
        return this.socketChannel.isBlocking();
    }
}
