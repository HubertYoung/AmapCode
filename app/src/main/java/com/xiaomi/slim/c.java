package com.xiaomi.slim;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

class c {
    private ByteBuffer a = ByteBuffer.allocate(2048);
    private ByteBuffer b = ByteBuffer.allocate(4);
    private Adler32 c = new Adler32();
    private e d;
    private InputStream e;
    private f f;
    private volatile boolean g;
    private byte[] h;

    c(InputStream inputStream, f fVar) {
        this.e = new BufferedInputStream(inputStream);
        this.f = fVar;
        this.d = new e();
    }

    private void a(ByteBuffer byteBuffer, int i) {
        int position = byteBuffer.position();
        do {
            int read = this.e.read(byteBuffer.array(), position, i);
            if (read != -1) {
                i -= read;
                position += read;
            } else {
                throw new EOFException();
            }
        } while (i > 0);
        byteBuffer.position(position);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0122, code lost:
        r6.f.a(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d() {
        /*
            r6 = this;
            r0 = 0
            r6.g = r0
            com.xiaomi.slim.b r1 = r6.c()
            java.lang.String r2 = "CONN"
            java.lang.String r3 = r1.a()
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0063
            byte[] r1 = r1.k()
            com.xiaomi.push.protobuf.b$f r1 = com.xiaomi.push.protobuf.b.f.b(r1)
            boolean r2 = r1.e()
            if (r2 == 0) goto L_0x002b
            com.xiaomi.slim.f r0 = r6.f
            java.lang.String r2 = r1.d()
            r0.a(r2)
            r0 = 1
        L_0x002b:
            boolean r2 = r1.h()
            if (r2 == 0) goto L_0x004e
            com.xiaomi.push.protobuf.b$b r2 = r1.i()
            com.xiaomi.slim.b r3 = new com.xiaomi.slim.b
            r3.<init>()
            java.lang.String r4 = "SYNC"
            java.lang.String r5 = "CONF"
            r3.a(r4, r5)
            byte[] r2 = r2.c()
            r4 = 0
            r3.a(r2, r4)
            com.xiaomi.slim.f r2 = r6.f
            r2.a(r3)
        L_0x004e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "[Slim] CONN: host = "
            r2.<init>(r3)
            java.lang.String r1 = r1.f()
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            com.xiaomi.channel.commonutils.logger.b.a(r1)
        L_0x0063:
            if (r0 != 0) goto L_0x0072
            java.lang.String r0 = "[Slim] Invalid CONN"
            com.xiaomi.channel.commonutils.logger.b.a(r0)
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Invalid Connection"
            r0.<init>(r1)
            throw r0
        L_0x0072:
            com.xiaomi.slim.f r0 = r6.f
            byte[] r0 = r0.a()
            r6.h = r0
        L_0x007a:
            boolean r0 = r6.g
            if (r0 != 0) goto L_0x0129
            com.xiaomi.slim.b r0 = r6.c()
            com.xiaomi.slim.f r1 = r6.f
            r1.o()
            short r1 = r0.m()
            switch(r1) {
                case 1: goto L_0x0122;
                case 2: goto L_0x00d6;
                case 3: goto L_0x00a4;
                default: goto L_0x008e;
            }
        L_0x008e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "[Slim] unknow blob type "
            r1.<init>(r2)
            short r0 = r0.m()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
        L_0x00a0:
            com.xiaomi.channel.commonutils.logger.b.a(r0)
            goto L_0x007a
        L_0x00a4:
            com.xiaomi.slim.e r1 = r6.d     // Catch:{ Exception -> 0x00b6 }
            byte[] r2 = r0.k()     // Catch:{ Exception -> 0x00b6 }
            com.xiaomi.slim.f r3 = r6.f     // Catch:{ Exception -> 0x00b6 }
            com.xiaomi.smack.packet.d r1 = r1.a(r2, r3)     // Catch:{ Exception -> 0x00b6 }
            com.xiaomi.slim.f r2 = r6.f     // Catch:{ Exception -> 0x00b6 }
            r2.b(r1)     // Catch:{ Exception -> 0x00b6 }
            goto L_0x007a
        L_0x00b6:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "[Slim] Parse packet from Blob "
            r2.<init>(r3)
        L_0x00be:
            java.lang.String r0 = r0.toString()
            r2.append(r0)
            java.lang.String r0 = " failure:"
            r2.append(r0)
            java.lang.String r0 = r1.getMessage()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            goto L_0x00a0
        L_0x00d6:
            java.lang.String r1 = "SECMSG"
            java.lang.String r2 = r0.a()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0122
            java.lang.String r1 = r0.b()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x0122
            int r1 = r0.c()     // Catch:{ Exception -> 0x0119 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0119 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0119 }
            java.lang.String r2 = r0.j()     // Catch:{ Exception -> 0x0119 }
            com.xiaomi.push.service.aq r3 = com.xiaomi.push.service.aq.a()     // Catch:{ Exception -> 0x0119 }
            com.xiaomi.push.service.aq$b r1 = r3.b(r1, r2)     // Catch:{ Exception -> 0x0119 }
            com.xiaomi.slim.e r2 = r6.d     // Catch:{ Exception -> 0x0119 }
            java.lang.String r1 = r1.i     // Catch:{ Exception -> 0x0119 }
            byte[] r1 = r0.d(r1)     // Catch:{ Exception -> 0x0119 }
            com.xiaomi.slim.f r3 = r6.f     // Catch:{ Exception -> 0x0119 }
            com.xiaomi.smack.packet.d r1 = r2.a(r1, r3)     // Catch:{ Exception -> 0x0119 }
            com.xiaomi.slim.f r2 = r6.f     // Catch:{ Exception -> 0x0119 }
            r2.b(r1)     // Catch:{ Exception -> 0x0119 }
            goto L_0x007a
        L_0x0119:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "[Slim] Parse packet from Blob "
            r2.<init>(r3)
            goto L_0x00be
        L_0x0122:
            com.xiaomi.slim.f r1 = r6.f
            r1.a(r0)
            goto L_0x007a
        L_0x0129:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.slim.c.d():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0072, code lost:
        if (r0 < 2048) goto L_0x0049;
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00d6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.nio.ByteBuffer e() {
        /*
            r8 = this;
            java.nio.ByteBuffer r0 = r8.a
            r0.clear()
            java.nio.ByteBuffer r0 = r8.a
            r1 = 8
            r8.a(r0, r1)
            java.nio.ByteBuffer r0 = r8.a
            r1 = 0
            short r0 = r0.getShort(r1)
            java.nio.ByteBuffer r2 = r8.a
            r3 = 2
            short r2 = r2.getShort(r3)
            r3 = -15618(0xffffffffffffc2fe, float:NaN)
            if (r0 != r3) goto L_0x00e9
            r0 = 5
            if (r2 == r0) goto L_0x0023
            goto L_0x00e9
        L_0x0023:
            java.nio.ByteBuffer r0 = r8.a
            r2 = 4
            int r0 = r0.getInt(r2)
            java.nio.ByteBuffer r3 = r8.a
            int r3 = r3.position()
            r4 = 32768(0x8000, float:4.5918E-41)
            if (r0 <= r4) goto L_0x003d
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Blob size too large"
            r0.<init>(r1)
            throw r0
        L_0x003d:
            int r4 = r0 + 4
            java.nio.ByteBuffer r5 = r8.a
            int r5 = r5.remaining()
            if (r4 <= r5) goto L_0x0066
            int r4 = r0 + 2048
        L_0x0049:
            java.nio.ByteBuffer r4 = java.nio.ByteBuffer.allocate(r4)
            java.nio.ByteBuffer r5 = r8.a
            byte[] r5 = r5.array()
            java.nio.ByteBuffer r6 = r8.a
            int r6 = r6.arrayOffset()
            java.nio.ByteBuffer r7 = r8.a
            int r7 = r7.position()
            int r6 = r6 + r7
            r4.put(r5, r1, r6)
            r8.a = r4
            goto L_0x0075
        L_0x0066:
            java.nio.ByteBuffer r4 = r8.a
            int r4 = r4.capacity()
            r5 = 4096(0x1000, float:5.74E-42)
            if (r4 <= r5) goto L_0x0075
            r4 = 2048(0x800, float:2.87E-42)
            if (r0 >= r4) goto L_0x0075
            goto L_0x0049
        L_0x0075:
            java.nio.ByteBuffer r4 = r8.a
            r8.a(r4, r0)
            java.nio.ByteBuffer r4 = r8.b
            r4.clear()
            java.nio.ByteBuffer r4 = r8.b
            r8.a(r4, r2)
            java.nio.ByteBuffer r2 = r8.b
            r2.position(r1)
            java.nio.ByteBuffer r2 = r8.b
            int r2 = r2.getInt()
            java.util.zip.Adler32 r4 = r8.c
            r4.reset()
            java.util.zip.Adler32 r4 = r8.c
            java.nio.ByteBuffer r5 = r8.a
            byte[] r5 = r5.array()
            java.nio.ByteBuffer r6 = r8.a
            int r6 = r6.position()
            r4.update(r5, r1, r6)
            java.util.zip.Adler32 r1 = r8.c
            long r4 = r1.getValue()
            int r1 = (int) r4
            if (r2 == r1) goto L_0x00d6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "CRC = "
            r0.<init>(r1)
            java.util.zip.Adler32 r1 = r8.c
            long r3 = r1.getValue()
            int r1 = (int) r3
            r0.append(r1)
            java.lang.String r1 = " and "
            r0.append(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.xiaomi.channel.commonutils.logger.b.a(r0)
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Corrupted Blob bad CRC"
            r0.<init>(r1)
            throw r0
        L_0x00d6:
            byte[] r1 = r8.h
            if (r1 == 0) goto L_0x00e6
            byte[] r1 = r8.h
            java.nio.ByteBuffer r2 = r8.a
            byte[] r2 = r2.array()
            r4 = 1
            com.xiaomi.push.service.ax.a(r1, r2, r4, r3, r0)
        L_0x00e6:
            java.nio.ByteBuffer r0 = r8.a
            return r0
        L_0x00e9:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Malformed Input"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.slim.c.e():java.nio.ByteBuffer");
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        try {
            d();
        } catch (IOException e2) {
            if (!this.g) {
                throw e2;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.g = true;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0065  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.xiaomi.slim.b c() {
        /*
            r6 = this;
            r0 = 0
            java.nio.ByteBuffer r1 = r6.e()     // Catch:{ IOException -> 0x004a }
            int r2 = r1.position()     // Catch:{ IOException -> 0x004a }
            r1.flip()     // Catch:{ IOException -> 0x0048 }
            r3 = 8
            r1.position(r3)     // Catch:{ IOException -> 0x0048 }
            java.nio.ByteBuffer r1 = r1.slice()     // Catch:{ IOException -> 0x0048 }
            com.xiaomi.slim.b r1 = com.xiaomi.slim.b.b(r1)     // Catch:{ IOException -> 0x0048 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0048 }
            java.lang.String r4 = "[Slim] Read {cmd="
            r3.<init>(r4)     // Catch:{ IOException -> 0x0048 }
            java.lang.String r4 = r1.a()     // Catch:{ IOException -> 0x0048 }
            r3.append(r4)     // Catch:{ IOException -> 0x0048 }
            java.lang.String r4 = ";chid="
            r3.append(r4)     // Catch:{ IOException -> 0x0048 }
            int r4 = r1.c()     // Catch:{ IOException -> 0x0048 }
            r3.append(r4)     // Catch:{ IOException -> 0x0048 }
            java.lang.String r4 = ";len="
            r3.append(r4)     // Catch:{ IOException -> 0x0048 }
            r3.append(r2)     // Catch:{ IOException -> 0x0048 }
            java.lang.String r4 = "}"
            r3.append(r4)     // Catch:{ IOException -> 0x0048 }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x0048 }
            com.xiaomi.channel.commonutils.logger.b.c(r3)     // Catch:{ IOException -> 0x0048 }
            return r1
        L_0x0048:
            r1 = move-exception
            goto L_0x004c
        L_0x004a:
            r1 = move-exception
            r2 = 0
        L_0x004c:
            if (r2 != 0) goto L_0x0054
            java.nio.ByteBuffer r2 = r6.a
            int r2 = r2.position()
        L_0x0054:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "[Slim] read Blob ["
            r3.<init>(r4)
            java.nio.ByteBuffer r4 = r6.a
            byte[] r4 = r4.array()
            r5 = 128(0x80, float:1.794E-43)
            if (r2 <= r5) goto L_0x0067
            r2 = 128(0x80, float:1.794E-43)
        L_0x0067:
            java.lang.String r0 = com.xiaomi.channel.commonutils.misc.e.a(r4, r0, r2)
            r3.append(r0)
            java.lang.String r0 = "] Err:"
            r3.append(r0)
            java.lang.String r0 = r1.getMessage()
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            com.xiaomi.channel.commonutils.logger.b.a(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.slim.c.c():com.xiaomi.slim.b");
    }
}
