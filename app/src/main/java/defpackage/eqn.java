package defpackage;

import com.autonavi.vcs.NativeVcsManager;

/* renamed from: eqn reason: default package */
/* compiled from: VcsAudioRecord */
public final class eqn {
    /* access modifiers changed from: private */
    public static boolean e = false;
    public volatile int a;
    public Thread b;
    public volatile boolean c;
    /* access modifiers changed from: private */
    public boolean d;

    public final void a() {
        bfh.a("VcsAudioRecord", " doEnd");
        this.c = false;
        this.d = true;
        if (this.b != null) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder(" doStart thread state = ");
                sb.append(this.b.getState());
                bfh.a("VcsAudioRecord", sb.toString());
            }
            this.b.interrupt();
            try {
                long currentTimeMillis = System.currentTimeMillis();
                this.b.join();
                long currentTimeMillis2 = System.currentTimeMillis();
                StringBuilder sb2 = new StringBuilder("mARThread.join time=");
                sb2.append(currentTimeMillis2 - currentTimeMillis);
                bfh.a("VcsAudioRecord", sb2.toString());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    static /* synthetic */ void a(byte[] bArr, int i) {
        if (NativeVcsManager.getInstance().isInit()) {
            NativeVcsManager.getInstance().pushAudioData(bArr, i);
        }
    }

    static /* synthetic */ void a(eqn eqn, byte[] bArr, int i) {
        int i2 = 0;
        long j = 0;
        for (int i3 = 0; i3 < 1024; i3++) {
            j += (long) (bArr[i3] * bArr[i3]);
        }
        double log10 = Math.log10(((double) j) / ((double) i)) * 10.0d;
        if (log10 >= 34.0d) {
            i2 = 3;
        } else if (log10 >= 27.0d) {
            i2 = 2;
        } else if (log10 >= 20.0d) {
            i2 = 1;
        }
        eqn.a = i2;
    }
}
