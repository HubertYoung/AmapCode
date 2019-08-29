package defpackage;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.autonavi.common.imageloader.ImageLoader;

/* renamed from: bkd reason: default package */
/* compiled from: Stats */
public final class bkd {
    final HandlerThread a = new HandlerThread("ImageLoader-Stats", 10);
    final bjv b;
    public final Handler c;
    long d;
    long e;
    long f;
    long g;
    long h;
    long i;
    long j;
    long k;
    int l;
    int m;
    int n;

    /* renamed from: bkd$a */
    /* compiled from: Stats */
    static class a extends Handler {
        private final bkd a;

        public a(Looper looper, bkd bkd) {
            super(looper);
            this.a = bkd;
        }

        public final void handleMessage(final Message message) {
            switch (message.what) {
                case 0:
                    this.a.d++;
                    return;
                case 1:
                    this.a.e++;
                    return;
                case 2:
                    bkd bkd = this.a;
                    bkd.m++;
                    bkd.g += (long) message.arg1;
                    bkd.j = bkd.g / ((long) bkd.m);
                    return;
                case 3:
                    bkd bkd2 = this.a;
                    bkd2.n++;
                    bkd2.h += (long) message.arg1;
                    bkd2.k = bkd2.h / ((long) bkd2.m);
                    return;
                case 4:
                    bkd bkd3 = this.a;
                    bkd3.l++;
                    bkd3.f += ((Long) message.obj).longValue();
                    bkd3.i = bkd3.f / ((long) bkd3.l);
                    return;
                default:
                    ImageLoader.a.post(new Runnable() {
                        public final void run() {
                            StringBuilder sb = new StringBuilder("Unhandled stats message.");
                            sb.append(message.what);
                            throw new AssertionError(sb.toString());
                        }
                    });
                    return;
            }
        }
    }

    public bkd(bjv bjv) {
        this.b = bjv;
        this.a.start();
        bkh.a(this.a.getLooper());
        this.c = new a(this.a.getLooper(), this);
    }

    public final void a() {
        this.c.sendEmptyMessage(0);
    }

    public final void b() {
        this.c.sendEmptyMessage(1);
    }

    /* access modifiers changed from: 0000 */
    public final void a(Bitmap bitmap, int i2) {
        this.c.sendMessage(this.c.obtainMessage(i2, bkh.a(bitmap), 0));
    }
}
