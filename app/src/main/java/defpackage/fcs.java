package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import com.yunos.carkitservice.WifiAdmin$1;

/* renamed from: fcs reason: default package */
/* compiled from: WifiAdmin */
public final class fcs {
    public WifiManager a;
    public Context b;
    public Handler c;
    public BroadcastReceiver d = new WifiAdmin$1(this);
    private WifiInfo e;

    public fcs(Context context, Handler handler) {
        this.b = context;
        this.c = handler;
        this.a = (WifiManager) context.getSystemService("wifi");
        this.e = this.a.getConnectionInfo();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.b.registerReceiver(this.d, intentFilter);
        if (this.e != null && this.e.getSSID() != null) {
            new StringBuilder("wifi connected ").append(this.e.getSSID());
            this.c.obtainMessage(4102, a(this.e.getSSID())).sendToTarget();
        }
    }

    public final String a() {
        this.e = this.a.getConnectionInfo();
        if (this.e == null) {
            return null;
        }
        return a(this.e.getIpAddress());
    }

    private static String a(int i) {
        StringBuilder sb = new StringBuilder(String.valueOf(i & 255));
        sb.append(".");
        sb.append((i >> 8) & 255);
        sb.append(".");
        sb.append((i >> 16) & 255);
        sb.append(".");
        sb.append((i >> 24) & 255);
        return sb.toString();
    }

    public static String a(String str) {
        return (str == null || str.length() <= 0 || str.charAt(0) != '\"') ? str : str.substring(1, str.length() - 1);
    }
}
