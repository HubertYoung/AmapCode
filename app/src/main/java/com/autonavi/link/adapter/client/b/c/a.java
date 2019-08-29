package com.autonavi.link.adapter.client.b.c;

import com.autonavi.link.adapter.b.c;
import com.autonavi.link.adapter.endian.ByteOrderedOutputStream;
import com.autonavi.link.adapter.endian.LittleEndianOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: UsbNetControlTransmit */
public class a {
    private static a f;
    public final int a = 1;
    public final int b = -1;
    public OutputStream c;
    public ByteArrayOutputStream d;
    public ByteOrderedOutputStream e;
    /* access modifiers changed from: private */
    public Queue<byte[]> g;
    /* access modifiers changed from: private */
    public Queue<byte[]> h;
    /* access modifiers changed from: private */
    public Object i = new Object();
    /* access modifiers changed from: private */
    public Object j = new Object();
    /* access modifiers changed from: private */
    public C0044a k = null;

    /* renamed from: com.autonavi.link.adapter.client.b.c.a$a reason: collision with other inner class name */
    /* compiled from: UsbNetControlTransmit */
    class C0044a implements Runnable {
        private boolean b;

        private C0044a() {
            this.b = true;
        }

        public void a() {
            this.b = false;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(3:12|13|14) */
        /* JADX WARNING: Can't wrap try/catch for region: R(3:28|29|30) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0033 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x0065 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
            L_0x0000:
                boolean r0 = r3.b
                if (r0 == 0) goto L_0x0074
                com.autonavi.link.adapter.client.b.c.a r0 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ InterruptedException -> 0x0074 }
                java.lang.Object r0 = r0.j     // Catch:{ InterruptedException -> 0x0074 }
                monitor-enter(r0)     // Catch:{ InterruptedException -> 0x0074 }
                com.autonavi.link.adapter.client.b.c.a r1 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ all -> 0x0071 }
                java.util.Queue r1 = r1.h     // Catch:{ all -> 0x0071 }
                if (r1 == 0) goto L_0x0035
                com.autonavi.link.adapter.client.b.c.a r1 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ all -> 0x0071 }
                java.util.Queue r1 = r1.h     // Catch:{ all -> 0x0071 }
                boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0071 }
                if (r1 != 0) goto L_0x0035
                com.autonavi.link.adapter.client.b.c.a r1 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ all -> 0x0071 }
                java.util.Queue r1 = r1.h     // Catch:{ all -> 0x0071 }
                java.lang.Object r1 = r1.poll()     // Catch:{ all -> 0x0071 }
                byte[] r1 = (byte[]) r1     // Catch:{ all -> 0x0071 }
                if (r1 == 0) goto L_0x0035
                com.autonavi.link.adapter.client.b.c.a r2 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ IOException -> 0x0033 }
                r2.d(r1)     // Catch:{ IOException -> 0x0033 }
                goto L_0x0035
            L_0x0033:
                monitor-exit(r0)     // Catch:{ all -> 0x0071 }
                goto L_0x0074
            L_0x0035:
                monitor-exit(r0)     // Catch:{ all -> 0x0071 }
                com.autonavi.link.adapter.client.b.c.a r0 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ InterruptedException -> 0x0074 }
                java.lang.Object r0 = r0.i     // Catch:{ InterruptedException -> 0x0074 }
                monitor-enter(r0)     // Catch:{ InterruptedException -> 0x0074 }
                com.autonavi.link.adapter.client.b.c.a r1 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ all -> 0x006e }
                java.util.Queue r1 = r1.g     // Catch:{ all -> 0x006e }
                if (r1 == 0) goto L_0x0067
                com.autonavi.link.adapter.client.b.c.a r1 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ all -> 0x006e }
                java.util.Queue r1 = r1.g     // Catch:{ all -> 0x006e }
                boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x006e }
                if (r1 != 0) goto L_0x0067
                com.autonavi.link.adapter.client.b.c.a r1 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ all -> 0x006e }
                java.util.Queue r1 = r1.g     // Catch:{ all -> 0x006e }
                java.lang.Object r1 = r1.poll()     // Catch:{ all -> 0x006e }
                byte[] r1 = (byte[]) r1     // Catch:{ all -> 0x006e }
                if (r1 == 0) goto L_0x0067
                com.autonavi.link.adapter.client.b.c.a r2 = com.autonavi.link.adapter.client.b.c.a.this     // Catch:{ IOException -> 0x0065 }
                r2.d(r1)     // Catch:{ IOException -> 0x0065 }
                goto L_0x0067
            L_0x0065:
                monitor-exit(r0)     // Catch:{ all -> 0x006e }
                goto L_0x0074
            L_0x0067:
                monitor-exit(r0)     // Catch:{ all -> 0x006e }
                r0 = 2
                java.lang.Thread.sleep(r0)     // Catch:{ InterruptedException -> 0x0074 }
                goto L_0x0000
            L_0x006e:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x006e }
                throw r1     // Catch:{ InterruptedException -> 0x0074 }
            L_0x0071:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0071 }
                throw r1     // Catch:{ InterruptedException -> 0x0074 }
            L_0x0074:
                com.autonavi.link.adapter.client.b.c.a r0 = com.autonavi.link.adapter.client.b.c.a.this
                r1 = 0
                r0.k = r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.adapter.client.b.c.a.C0044a.run():void");
        }
    }

    private a() {
    }

    public static a a() {
        if (f == null) {
            f = new a();
        }
        return f;
    }

    public void b() throws IOException {
        this.d.reset();
        this.e.writeShort(51);
        this.e.writeByte(1);
        byte[] bytes = "android".getBytes("UTF-8");
        this.e.writeInt(bytes.length);
        this.e.write(bytes);
        this.e.writeByte(1);
        this.e.writeInt(0);
        this.e.flush();
        c(this.d.toByteArray());
    }

    public void a(int i2) throws IOException {
        this.d.reset();
        this.e.writeShort(54);
        this.e.writeInt(i2);
        this.e.flush();
        c(this.d.toByteArray());
    }

    public void c() throws IOException {
        this.d.reset();
        this.e.writeShort(34);
        this.e.write(1);
        this.e.flush();
        c(this.d.toByteArray());
    }

    public void a(int i2, int i3) throws IOException {
        this.d.reset();
        this.e.writeShort(i2);
        this.e.writeInt(i3);
        this.e.flush();
        c(this.d.toByteArray());
    }

    public void a(byte[] bArr) throws Exception {
        if (bArr == null || bArr.length <= 0) {
            throw new IllegalArgumentException("requestdata is null or length less than 0!");
        }
        this.d.reset();
        this.e.writeShort(46);
        this.e.writeInt(bArr.length);
        this.e.write(bArr);
        this.e.writeInt(0);
        this.e.flush();
        b(this.d.toByteArray());
    }

    public void a(OutputStream outputStream) {
        this.g = new ConcurrentLinkedQueue();
        this.h = new ConcurrentLinkedQueue();
        this.d = new ByteArrayOutputStream();
        this.e = new LittleEndianOutputStream(this.d);
        this.c = outputStream;
        this.k = new C0044a();
        new Thread(this.k).start();
    }

    public void b(byte[] bArr) {
        synchronized (this.i) {
            if (!(this.g == null || bArr == null)) {
                this.g.offer(bArr);
            }
        }
    }

    public void c(byte[] bArr) {
        synchronized (this.j) {
            if (!(this.h == null || bArr == null)) {
                this.h.offer(bArr);
            }
        }
    }

    /* access modifiers changed from: private */
    public void d(byte[] bArr) throws IOException {
        if (this.c != null) {
            try {
                this.c.write(c.a(bArr, true));
                this.c.flush();
            } catch (IOException unused) {
                com.autonavi.link.adapter.client.b.a.a.a().d();
            }
        }
    }

    public void d() throws IOException {
        synchronized (this.i) {
            if (this.g != null && !this.g.isEmpty()) {
                this.g.clear();
                this.g = null;
            }
        }
        synchronized (this.j) {
            if (this.h != null && !this.h.isEmpty()) {
                this.h.clear();
                this.h = null;
            }
        }
        if (this.k != null) {
            this.k.a();
        }
        try {
            if (this.c != null) {
                this.c.close();
            }
            if (this.d != null) {
                this.d.close();
            }
            if (this.e != null) {
                this.e.close();
            }
        } catch (IOException e2) {
            throw e2;
        }
    }
}
