package defpackage;

import android.os.Looper;
import android.telephony.TelephonyManager;

/* renamed from: zo reason: default package */
/* compiled from: ApmStrengthFetcher */
public final class zo {
    a a;

    /* renamed from: zo$a */
    /* compiled from: ApmStrengthFetcher */
    static final class a extends Thread {
        zn a;
        private Looper b;
        private TelephonyManager c;

        /* synthetic */ a(TelephonyManager telephonyManager, byte b2) {
            this(telephonyManager);
        }

        private a(TelephonyManager telephonyManager) {
            super("ApmSignalThread");
            this.c = telephonyManager;
        }

        public final void run() {
            super.run();
            Looper.prepare();
            this.b = Looper.myLooper();
            this.a = new zn(this.c, this.b);
            zn znVar = this.a;
            if (znVar.b != null) {
                znVar.b.listen(znVar, 256);
            }
            Looper.loop();
        }
    }

    public zo(TelephonyManager telephonyManager) {
        this.a = new a(telephonyManager, 0);
    }

    public final String toString() {
        if (this.a == null) {
            return "";
        }
        a aVar = this.a;
        return aVar.a != null ? aVar.a.a : "";
    }
}
