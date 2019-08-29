package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AudioData {
    private static final String a = AudioData.class.getSimpleName();
    private List<DataPacket> b = new CopyOnWriteArrayList();
    private Object c = new Object();

    public void add(byte[] data, int size) {
        this.b.add(new DataPacket(data, size));
        a();
    }

    public void add(short[] data, int size) {
        this.b.add(new DataPacket(data, size));
        a();
        Logger.D(a, "AudioEncoder add codesize=" + size, new Object[0]);
    }

    public void clear() {
        this.b.clear();
    }

    public boolean isEmpty() {
        return this.b.isEmpty();
    }

    public int size() {
        return this.b.size();
    }

    public DataPacket removeFirst() {
        return this.b.remove(0);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean waitForData() {
        /*
            r5 = this;
            r0 = 0
            java.lang.Object r1 = r5.c
            monitor-enter(r1)
        L_0x0004:
            boolean r2 = r5.isEmpty()     // Catch:{ all -> 0x001a }
            if (r2 == 0) goto L_0x0020
            java.lang.Object r2 = r5.c     // Catch:{ InterruptedException -> 0x001d }
            r2.wait()     // Catch:{ InterruptedException -> 0x001d }
            java.lang.String r2 = a     // Catch:{ all -> 0x001a }
            java.lang.String r3 = "AudioEncoder waiting loop"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x001a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r2, r3, r4)     // Catch:{ all -> 0x001a }
            goto L_0x0004
        L_0x001a:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001a }
            throw r0
        L_0x001d:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001a }
        L_0x001f:
            return r0
        L_0x0020:
            monitor-exit(r1)     // Catch:{ all -> 0x001a }
            r0 = 1
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.bean.AudioData.waitForData():boolean");
    }

    private void a() {
        synchronized (this.c) {
            this.c.notifyAll();
        }
    }
}
