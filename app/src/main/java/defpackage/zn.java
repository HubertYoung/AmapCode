package defpackage;

import android.os.Build.VERSION;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

/* renamed from: zn reason: default package */
/* compiled from: ApmSignalListener */
public final class zn extends PhoneStateListener {
    String a = "";
    TelephonyManager b;
    private Looper c;

    public zn(TelephonyManager telephonyManager, Looper looper) {
        this.b = telephonyManager;
        this.c = looper;
    }

    public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        if (signalStrength != null) {
            if (VERSION.SDK_INT >= 23) {
                StringBuilder sb = new StringBuilder("data level[");
                sb.append(signalStrength.getLevel());
                sb.append("]");
                this.a = sb.toString();
            }
            if (signalStrength.getGsmSignalStrength() != 99) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.a);
                sb2.append(" SignalStrength [");
                sb2.append(signalStrength.toString());
                sb2.append("]");
                this.a = sb2.toString();
            }
        }
        if (this.b != null) {
            this.b.listen(this, 0);
            this.b = null;
        }
        if (this.c != null) {
            this.c.quit();
            this.c = null;
        }
    }
}
