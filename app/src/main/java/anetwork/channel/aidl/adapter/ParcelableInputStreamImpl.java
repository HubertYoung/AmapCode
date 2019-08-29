package anetwork.channel.aidl.adapter;

import android.os.RemoteException;
import anetwork.channel.aidl.ParcelableInputStream.Stub;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ParcelableInputStreamImpl extends Stub {
    private static final aa EOS = aa.a(0);
    private static final String TAG = "anet.ParcelableInputStreamImpl";
    private int blockIndex;
    private int blockOffset;
    private LinkedList<aa> byteList = new LinkedList<>();
    private int contentLength;
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    final ReentrantLock lock = new ReentrantLock();
    final Condition newDataArrive = this.lock.newCondition();
    private int rto = 10000;
    private String seqNo = "";

    public void write(aa aaVar) {
        if (!this.isClosed.get()) {
            this.lock.lock();
            try {
                this.byteList.add(aaVar);
                this.newDataArrive.signal();
            } finally {
                this.lock.unlock();
            }
        }
    }

    public void writeEnd() {
        write(EOS);
    }

    private void recycleCurrentItem() {
        this.lock.lock();
        try {
            this.byteList.set(this.blockIndex, EOS).a();
        } finally {
            this.lock.unlock();
        }
    }

    public int available() throws RemoteException {
        if (this.isClosed.get()) {
            throw new RuntimeException("Stream is closed");
        }
        this.lock.lock();
        try {
            int i = 0;
            if (this.blockIndex == this.byteList.size()) {
                return 0;
            }
            ListIterator<aa> listIterator = this.byteList.listIterator(this.blockIndex);
            while (listIterator.hasNext()) {
                i += listIterator.next().c;
            }
            int i2 = i - this.blockOffset;
            this.lock.unlock();
            return i2;
        } finally {
            this.lock.unlock();
        }
    }

    public void close() throws RemoteException {
        if (this.isClosed.compareAndSet(false, true)) {
            this.lock.lock();
            try {
                Iterator it = this.byteList.iterator();
                while (it.hasNext()) {
                    aa aaVar = (aa) it.next();
                    if (aaVar != EOS) {
                        aaVar.a();
                    }
                }
                this.byteList.clear();
                this.byteList = null;
                this.blockIndex = -1;
                this.blockOffset = -1;
                this.contentLength = 0;
            } finally {
                this.lock.unlock();
            }
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x006e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readByte() throws android.os.RemoteException {
        /*
            r4 = this;
            java.util.concurrent.atomic.AtomicBoolean r0 = r4.isClosed
            boolean r0 = r0.get()
            if (r0 == 0) goto L_0x0010
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Stream is closed"
            r0.<init>(r1)
            throw r0
        L_0x0010:
            java.util.concurrent.locks.ReentrantLock r0 = r4.lock
            r0.lock()
        L_0x0015:
            int r0 = r4.blockIndex     // Catch:{ InterruptedException -> 0x006e }
            java.util.LinkedList<aa> r1 = r4.byteList     // Catch:{ InterruptedException -> 0x006e }
            int r1 = r1.size()     // Catch:{ InterruptedException -> 0x006e }
            if (r0 != r1) goto L_0x0037
            java.util.concurrent.locks.Condition r0 = r4.newDataArrive     // Catch:{ InterruptedException -> 0x006e }
            int r1 = r4.rto     // Catch:{ InterruptedException -> 0x006e }
            long r1 = (long) r1     // Catch:{ InterruptedException -> 0x006e }
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x006e }
            boolean r0 = r0.await(r1, r3)     // Catch:{ InterruptedException -> 0x006e }
            if (r0 != 0) goto L_0x0037
            r4.close()     // Catch:{ InterruptedException -> 0x006e }
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ InterruptedException -> 0x006e }
            java.lang.String r1 = "await timeout."
            r0.<init>(r1)     // Catch:{ InterruptedException -> 0x006e }
            throw r0     // Catch:{ InterruptedException -> 0x006e }
        L_0x0037:
            java.util.LinkedList<aa> r0 = r4.byteList     // Catch:{ InterruptedException -> 0x006e }
            int r1 = r4.blockIndex     // Catch:{ InterruptedException -> 0x006e }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ InterruptedException -> 0x006e }
            aa r0 = (defpackage.aa) r0     // Catch:{ InterruptedException -> 0x006e }
            aa r1 = EOS     // Catch:{ InterruptedException -> 0x006e }
            if (r0 != r1) goto L_0x0047
            r0 = -1
            goto L_0x0059
        L_0x0047:
            int r1 = r4.blockOffset     // Catch:{ InterruptedException -> 0x006e }
            int r2 = r0.c     // Catch:{ InterruptedException -> 0x006e }
            if (r1 >= r2) goto L_0x005f
            byte[] r0 = r0.a     // Catch:{ InterruptedException -> 0x006e }
            int r1 = r4.blockOffset     // Catch:{ InterruptedException -> 0x006e }
            byte r0 = r0[r1]     // Catch:{ InterruptedException -> 0x006e }
            int r1 = r4.blockOffset     // Catch:{ InterruptedException -> 0x006e }
            int r1 = r1 + 1
            r4.blockOffset = r1     // Catch:{ InterruptedException -> 0x006e }
        L_0x0059:
            java.util.concurrent.locks.ReentrantLock r1 = r4.lock
            r1.unlock()
            return r0
        L_0x005f:
            r4.recycleCurrentItem()     // Catch:{ InterruptedException -> 0x006e }
            int r0 = r4.blockIndex     // Catch:{ InterruptedException -> 0x006e }
            int r0 = r0 + 1
            r4.blockIndex = r0     // Catch:{ InterruptedException -> 0x006e }
            r0 = 0
            r4.blockOffset = r0     // Catch:{ InterruptedException -> 0x006e }
            goto L_0x0015
        L_0x006c:
            r0 = move-exception
            goto L_0x0079
        L_0x006e:
            r4.close()     // Catch:{ all -> 0x006c }
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ all -> 0x006c }
            java.lang.String r1 = "await interrupt"
            r0.<init>(r1)     // Catch:{ all -> 0x006c }
            throw r0     // Catch:{ all -> 0x006c }
        L_0x0079:
            java.util.concurrent.locks.ReentrantLock r1 = r4.lock
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.aidl.adapter.ParcelableInputStreamImpl.readByte():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0086, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0092, code lost:
        throw new java.lang.RuntimeException("await interrupt");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0093, code lost:
        r5.lock.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0098, code lost:
        throw r6;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x0088 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readBytes(byte[] r6, int r7, int r8) throws android.os.RemoteException {
        /*
            r5 = this;
            java.util.concurrent.atomic.AtomicBoolean r0 = r5.isClosed
            boolean r0 = r0.get()
            if (r0 == 0) goto L_0x0010
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.String r7 = "Stream is closed"
            r6.<init>(r7)
            throw r6
        L_0x0010:
            if (r6 != 0) goto L_0x0018
            java.lang.NullPointerException r6 = new java.lang.NullPointerException
            r6.<init>()
            throw r6
        L_0x0018:
            if (r7 < 0) goto L_0x00a4
            if (r8 < 0) goto L_0x00a4
            int r8 = r8 + r7
            int r0 = r6.length
            if (r8 <= r0) goto L_0x0022
            goto L_0x00a4
        L_0x0022:
            java.util.concurrent.locks.ReentrantLock r0 = r5.lock
            r0.lock()
            r0 = r7
        L_0x0028:
            if (r0 >= r8) goto L_0x0099
            int r1 = r5.blockIndex     // Catch:{ InterruptedException -> 0x0088 }
            java.util.LinkedList<aa> r2 = r5.byteList     // Catch:{ InterruptedException -> 0x0088 }
            int r2 = r2.size()     // Catch:{ InterruptedException -> 0x0088 }
            if (r1 != r2) goto L_0x004c
            java.util.concurrent.locks.Condition r1 = r5.newDataArrive     // Catch:{ InterruptedException -> 0x0088 }
            int r2 = r5.rto     // Catch:{ InterruptedException -> 0x0088 }
            long r2 = (long) r2     // Catch:{ InterruptedException -> 0x0088 }
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x0088 }
            boolean r1 = r1.await(r2, r4)     // Catch:{ InterruptedException -> 0x0088 }
            if (r1 != 0) goto L_0x004c
            r5.close()     // Catch:{ InterruptedException -> 0x0088 }
            java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch:{ InterruptedException -> 0x0088 }
            java.lang.String r7 = "await timeout."
            r6.<init>(r7)     // Catch:{ InterruptedException -> 0x0088 }
            throw r6     // Catch:{ InterruptedException -> 0x0088 }
        L_0x004c:
            java.util.LinkedList<aa> r1 = r5.byteList     // Catch:{ InterruptedException -> 0x0088 }
            int r2 = r5.blockIndex     // Catch:{ InterruptedException -> 0x0088 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ InterruptedException -> 0x0088 }
            aa r1 = (defpackage.aa) r1     // Catch:{ InterruptedException -> 0x0088 }
            aa r2 = EOS     // Catch:{ InterruptedException -> 0x0088 }
            if (r1 == r2) goto L_0x0099
            int r2 = r1.c     // Catch:{ InterruptedException -> 0x0088 }
            int r3 = r5.blockOffset     // Catch:{ InterruptedException -> 0x0088 }
            int r2 = r2 - r3
            int r3 = r8 - r0
            if (r2 >= r3) goto L_0x0078
            byte[] r1 = r1.a     // Catch:{ InterruptedException -> 0x0088 }
            int r3 = r5.blockOffset     // Catch:{ InterruptedException -> 0x0088 }
            java.lang.System.arraycopy(r1, r3, r6, r0, r2)     // Catch:{ InterruptedException -> 0x0088 }
            int r0 = r0 + r2
            r5.recycleCurrentItem()     // Catch:{ InterruptedException -> 0x0088 }
            int r1 = r5.blockIndex     // Catch:{ InterruptedException -> 0x0088 }
            int r1 = r1 + 1
            r5.blockIndex = r1     // Catch:{ InterruptedException -> 0x0088 }
            r1 = 0
            r5.blockOffset = r1     // Catch:{ InterruptedException -> 0x0088 }
            goto L_0x0028
        L_0x0078:
            byte[] r1 = r1.a     // Catch:{ InterruptedException -> 0x0088 }
            int r2 = r5.blockOffset     // Catch:{ InterruptedException -> 0x0088 }
            java.lang.System.arraycopy(r1, r2, r6, r0, r3)     // Catch:{ InterruptedException -> 0x0088 }
            int r1 = r5.blockOffset     // Catch:{ InterruptedException -> 0x0088 }
            int r1 = r1 + r3
            r5.blockOffset = r1     // Catch:{ InterruptedException -> 0x0088 }
            int r0 = r0 + r3
            goto L_0x0028
        L_0x0086:
            r6 = move-exception
            goto L_0x0093
        L_0x0088:
            r5.close()     // Catch:{ all -> 0x0086 }
            java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch:{ all -> 0x0086 }
            java.lang.String r7 = "await interrupt"
            r6.<init>(r7)     // Catch:{ all -> 0x0086 }
            throw r6     // Catch:{ all -> 0x0086 }
        L_0x0093:
            java.util.concurrent.locks.ReentrantLock r7 = r5.lock
            r7.unlock()
            throw r6
        L_0x0099:
            java.util.concurrent.locks.ReentrantLock r6 = r5.lock
            r6.unlock()
            int r0 = r0 - r7
            if (r0 <= 0) goto L_0x00a2
            return r0
        L_0x00a2:
            r6 = -1
            return r6
        L_0x00a4:
            java.lang.ArrayIndexOutOfBoundsException r6 = new java.lang.ArrayIndexOutOfBoundsException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.aidl.adapter.ParcelableInputStreamImpl.readBytes(byte[], int, int):int");
    }

    public int read(byte[] bArr) throws RemoteException {
        return readBytes(bArr, 0, bArr.length);
    }

    public long skip(int i) throws RemoteException {
        this.lock.lock();
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                break;
            }
            try {
                if (this.blockIndex == this.byteList.size()) {
                    break;
                }
                aa aaVar = this.byteList.get(this.blockIndex);
                if (aaVar == EOS) {
                    break;
                }
                int i3 = aaVar.c;
                if (i3 - this.blockOffset < i + 0) {
                    i2 = (i3 - this.blockOffset) + 0;
                    recycleCurrentItem();
                    this.blockIndex++;
                    this.blockOffset = 0;
                    break;
                }
                this.blockOffset += 0;
                i2 = i;
            } catch (Throwable th) {
                this.lock.unlock();
                throw th;
            }
        }
        this.lock.unlock();
        return (long) i2;
    }

    public int length() throws RemoteException {
        return this.contentLength;
    }

    public void init(dy dyVar, int i) {
        this.contentLength = i;
        this.seqNo = dyVar.i;
        this.rto = dyVar.h;
    }
}
