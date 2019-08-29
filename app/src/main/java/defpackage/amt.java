package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.autonavi.ae.gmap.maploader.NetworkState$1;

/* renamed from: amt reason: default package */
/* compiled from: NetworkState */
public final class amt {
    public a a;
    private Integer b = Integer.valueOf(0);
    private final Object c = new Object();
    private final BroadcastReceiver d = new NetworkState$1(this);

    /* renamed from: amt$a */
    /* compiled from: NetworkState */
    public interface a {
        void a(Context context);
    }

    public final void a(Context context, boolean z) {
        synchronized (this.c) {
            if (z) {
                try {
                    if (this.b.intValue() == 0) {
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                        context.registerReceiver(this.d, intentFilter);
                    }
                } finally {
                }
            } else if (this.b.intValue() == 1) {
                context.unregisterReceiver(this.d);
            }
            this.b = Integer.valueOf(z ? 1 : 0);
        }
    }

    public static boolean a(Context context) {
        NetworkInfo b2 = b(context);
        return b2 != null && b2.isConnected();
    }

    private static NetworkInfo b(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return activeNetworkInfo;
            }
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo == null) {
                return null;
            }
            int i = 0;
            while (true) {
                if (i < allNetworkInfo.length) {
                    if (allNetworkInfo[i] != null && allNetworkInfo[i].isConnected()) {
                        activeNetworkInfo = allNetworkInfo[i];
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            return activeNetworkInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
