package defpackage;

import android.media.AudioTrack;
import com.gauss.speex.encode.Speex;
import java.io.File;

/* renamed from: euo reason: default package */
/* compiled from: SpeexDecoder */
public final class euo {
    protected Speex a;
    public a b = null;
    private final Object c = new Object();
    private boolean d = false;
    private File e;
    private AudioTrack f;

    public euo(File file) throws Exception {
        this.e = file;
    }

    public final void a() {
        synchronized (this.c) {
            this.d = true;
            if (this.a != null) {
                Speex speex = this.a;
                speex.close(5, speex.a);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:104:0x020b, code lost:
        if (r1.a != null) goto L_0x020d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x020d, code lost:
        r1.a.b();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0212, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x023d, code lost:
        if (r1.a != null) goto L_0x020d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0240, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0227  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0238  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0245  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x0256  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x025d  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0194 A[Catch:{ Exception -> 0x0216, all -> 0x0213 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b() throws java.lang.Exception {
        /*
            r25 = this;
            r1 = r25
            r2 = 2048(0x800, float:2.87E-42)
            byte[] r2 = new byte[r2]
            r3 = 65536(0x10000, float:9.18355E-41)
            byte[] r3 = new byte[r3]
            com.gauss.speex.encode.Speex r4 = new com.gauss.speex.encode.Speex
            r4.<init>()
            r1.a = r4
            com.gauss.speex.encode.Speex r4 = r1.a
            r4.a()
            r4 = 0
            java.io.RandomAccessFile r10 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x021e }
            java.io.File r5 = r1.e     // Catch:{ Exception -> 0x021e }
            java.lang.String r6 = "r"
            r10.<init>(r5, r6)     // Catch:{ Exception -> 0x021e }
            r11 = 0
            r4 = 0
        L_0x0022:
            boolean r5 = r1.d     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            if (r5 != 0) goto L_0x01f3
            eum$a r5 = r1.b     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            if (r5 == 0) goto L_0x002f
            eum$a r5 = r1.b     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r5.onStart()     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
        L_0x002f:
            boolean r5 = java.lang.Thread.interrupted()     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            if (r5 == 0) goto L_0x005d
            r10.close()     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            android.media.AudioTrack r2 = r1.f     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r2.stop()     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            eum$a r2 = r1.b
            if (r2 == 0) goto L_0x0046
            eum$a r2 = r1.b
            r2.onFinish()
        L_0x0046:
            android.media.AudioTrack r2 = r1.f
            r2.stop()
            android.media.AudioTrack r2 = r1.f
            r2.release()
            r10.close()
            com.gauss.speex.encode.Speex r2 = r1.a
            if (r2 == 0) goto L_0x005c
            com.gauss.speex.encode.Speex r2 = r1.a
            r2.b()
        L_0x005c:
            return
        L_0x005d:
            r5 = 27
            r10.readFully(r2, r11, r5)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r6 = 22
            int r12 = a(r2, r6)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r2[r6] = r11     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r6 = 23
            r2[r6] = r11     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r6 = 24
            r2[r6] = r11     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r6 = 25
            r2[r6] = r11     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            int r6 = defpackage.eur.a(r11, r2, r11, r5)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            java.lang.String r7 = "OggS"
            java.lang.String r8 = new java.lang.String     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r13 = 4
            r8.<init>(r2, r11, r13)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            boolean r7 = r7.equals(r8)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            if (r7 != 0) goto L_0x00aa
            java.io.PrintStream r2 = java.lang.System.err     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            eum$a r2 = r1.b
            if (r2 == 0) goto L_0x0093
            eum$a r2 = r1.b
            r2.onFinish()
        L_0x0093:
            android.media.AudioTrack r2 = r1.f
            r2.stop()
            android.media.AudioTrack r2 = r1.f
            r2.release()
            r10.close()
            com.gauss.speex.encode.Speex r2 = r1.a
            if (r2 == 0) goto L_0x00a9
            com.gauss.speex.encode.Speex r2 = r1.a
            r2.b()
        L_0x00a9:
            return
        L_0x00aa:
            r7 = 26
            byte r7 = r2[r7]     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r14 = 255(0xff, float:3.57E-43)
            r15 = r7 & 255(0xff, float:3.57E-43)
            r10.readFully(r2, r5, r15)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            int r5 = defpackage.eur.a(r6, r2, r5, r15)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r9 = r4
            r8 = 0
        L_0x00bb:
            if (r8 >= r15) goto L_0x01e3
            boolean r4 = r1.d     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            if (r4 == 0) goto L_0x00e1
            eum$a r2 = r1.b
            if (r2 == 0) goto L_0x00ca
            eum$a r2 = r1.b
            r2.onFinish()
        L_0x00ca:
            android.media.AudioTrack r2 = r1.f
            r2.stop()
            android.media.AudioTrack r2 = r1.f
            r2.release()
            r10.close()
            com.gauss.speex.encode.Speex r2 = r1.a
            if (r2 == 0) goto L_0x00e0
            com.gauss.speex.encode.Speex r2 = r1.a
            r2.b()
        L_0x00e0:
            return
        L_0x00e1:
            boolean r4 = java.lang.Thread.interrupted()     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            if (r4 == 0) goto L_0x010f
            r10.close()     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            android.media.AudioTrack r2 = r1.f     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r2.stop()     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            eum$a r2 = r1.b
            if (r2 == 0) goto L_0x00f8
            eum$a r2 = r1.b
            r2.onFinish()
        L_0x00f8:
            android.media.AudioTrack r2 = r1.f
            r2.stop()
            android.media.AudioTrack r2 = r1.f
            r2.release()
            r10.close()
            com.gauss.speex.encode.Speex r2 = r1.a
            if (r2 == 0) goto L_0x010e
            com.gauss.speex.encode.Speex r2 = r1.a
            r2.b()
        L_0x010e:
            return
        L_0x010f:
            int r4 = r8 + 27
            byte r4 = r2[r4]     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r4 = r4 & r14
            if (r4 != r14) goto L_0x0138
            java.io.PrintStream r2 = java.lang.System.err     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            eum$a r2 = r1.b
            if (r2 == 0) goto L_0x0121
            eum$a r2 = r1.b
            r2.onFinish()
        L_0x0121:
            android.media.AudioTrack r2 = r1.f
            r2.stop()
            android.media.AudioTrack r2 = r1.f
            r2.release()
            r10.close()
            com.gauss.speex.encode.Speex r2 = r1.a
            if (r2 == 0) goto L_0x0137
            com.gauss.speex.encode.Speex r2 = r1.a
            r2.b()
        L_0x0137:
            return
        L_0x0138:
            r10.readFully(r3, r11, r4)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            int r16 = defpackage.eur.a(r5, r3, r11, r4)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r5 = 1
            if (r9 != 0) goto L_0x0198
            r6 = 80
            if (r4 == r6) goto L_0x0148
        L_0x0146:
            r5 = 0
            goto L_0x0192
        L_0x0148:
            java.lang.String r4 = "Speex   "
            java.lang.String r6 = new java.lang.String     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r7 = 8
            r6.<init>(r3, r11, r7)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            if (r4 != 0) goto L_0x0158
            goto L_0x0146
        L_0x0158:
            r4 = 36
            int r4 = a(r3, r4)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r6 = 2
            int r6 = android.media.AudioTrack.getMinBufferSize(r4, r13, r6)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            if (r6 >= 0) goto L_0x017d
            java.lang.Exception r2 = new java.lang.Exception     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            java.lang.String r4 = "Failed to get minimum buffer size: "
            r3.<init>(r4)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            java.lang.String r4 = java.lang.Integer.toString(r6)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r3.append(r4)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            throw r2     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
        L_0x017d:
            android.media.AudioTrack r7 = new android.media.AudioTrack     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r18 = 3
            r20 = 4
            r21 = 2
            r23 = 1
            r17 = r7
            r19 = r4
            r22 = r6
            r17.<init>(r18, r19, r20, r21, r22, r23)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r1.f = r7     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
        L_0x0192:
            if (r5 != 0) goto L_0x01d5
            r17 = r8
            r9 = 0
            goto L_0x01dc
        L_0x0198:
            if (r9 == r5) goto L_0x01d5
            r4 = 160(0xa0, float:2.24E-43)
            short[] r7 = new short[r4]     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            com.gauss.speex.encode.Speex r4 = r1.a     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r17 = 160(0xa0, float:2.24E-43)
            long r5 = r4.a     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r18 = r5
            r5 = r3
            r6 = r7
            r13 = r7
            r7 = r17
            r17 = r8
            r20 = r9
            r8 = r18
            int r4 = r4.decode(r5, r6, r7, r8)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            if (r4 <= 0) goto L_0x01d9
            android.media.AudioTrack r5 = r1.f     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r5.write(r13, r11, r4)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r5 = 21
            r6 = 1065353216(0x3f800000, float:1.0)
            if (r4 < r5) goto L_0x01ca
            android.media.AudioTrack r4 = r1.f     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r4.setVolume(r6)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            goto L_0x01cf
        L_0x01ca:
            android.media.AudioTrack r4 = r1.f     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r4.setStereoVolume(r6, r6)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
        L_0x01cf:
            android.media.AudioTrack r4 = r1.f     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            r4.play()     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            goto L_0x01d9
        L_0x01d5:
            r17 = r8
            r20 = r9
        L_0x01d9:
            int r4 = r20 + 1
            r9 = r4
        L_0x01dc:
            int r8 = r17 + 1
            r5 = r16
            r13 = 4
            goto L_0x00bb
        L_0x01e3:
            r20 = r9
            if (r5 == r12) goto L_0x01ef
            java.io.IOException r2 = new java.io.IOException     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            java.lang.String r3 = "Ogg CheckSums do not match"
            r2.<init>(r3)     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
            throw r2     // Catch:{ Exception -> 0x0216, all -> 0x0213 }
        L_0x01ef:
            r4 = r20
            goto L_0x0022
        L_0x01f3:
            eum$a r2 = r1.b
            if (r2 == 0) goto L_0x01fc
            eum$a r2 = r1.b
            r2.onFinish()
        L_0x01fc:
            android.media.AudioTrack r2 = r1.f
            r2.stop()
            android.media.AudioTrack r2 = r1.f
            r2.release()
            r10.close()
            com.gauss.speex.encode.Speex r2 = r1.a
            if (r2 == 0) goto L_0x0240
        L_0x020d:
            com.gauss.speex.encode.Speex r2 = r1.a
            r2.b()
            return
        L_0x0213:
            r0 = move-exception
            r2 = r0
            goto L_0x0241
        L_0x0216:
            r0 = move-exception
            r2 = r0
            r4 = r10
            goto L_0x0220
        L_0x021a:
            r0 = move-exception
            r2 = r0
            r10 = r4
            goto L_0x0241
        L_0x021e:
            r0 = move-exception
            r2 = r0
        L_0x0220:
            r2.printStackTrace()     // Catch:{ all -> 0x021a }
            eum$a r2 = r1.b
            if (r2 == 0) goto L_0x022c
            eum$a r2 = r1.b
            r2.onFinish()
        L_0x022c:
            android.media.AudioTrack r2 = r1.f
            r2.stop()
            android.media.AudioTrack r2 = r1.f
            r2.release()
            if (r4 == 0) goto L_0x023b
            r4.close()
        L_0x023b:
            com.gauss.speex.encode.Speex r2 = r1.a
            if (r2 == 0) goto L_0x0240
            goto L_0x020d
        L_0x0240:
            return
        L_0x0241:
            eum$a r3 = r1.b
            if (r3 == 0) goto L_0x024a
            eum$a r3 = r1.b
            r3.onFinish()
        L_0x024a:
            android.media.AudioTrack r3 = r1.f
            r3.stop()
            android.media.AudioTrack r3 = r1.f
            r3.release()
            if (r10 == 0) goto L_0x0259
            r10.close()
        L_0x0259:
            com.gauss.speex.encode.Speex r3 = r1.a
            if (r3 == 0) goto L_0x0262
            com.gauss.speex.encode.Speex r3 = r1.a
            r3.b()
        L_0x0262:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.euo.b():void");
    }

    private static int a(byte[] bArr, int i) {
        return (bArr[i + 3] << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }
}
