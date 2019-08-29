package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.autonavi.minimap.bl.NetworkMonitorImpl$1;
import com.autonavi.minimap.bl.net.INetworkMonitor;
import com.autonavi.minimap.bl.net.INetworkMonitorObserver;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ctc reason: default package */
/* compiled from: NetworkMonitorImpl */
public final class ctc implements INetworkMonitor {
    public Context a;
    public final BroadcastReceiver b = new NetworkMonitorImpl$1(this);
    /* access modifiers changed from: private */
    public int c;
    /* access modifiers changed from: private */
    public List<INetworkMonitorObserver> d = new ArrayList();

    public ctc(Context context) {
        this.a = context.getApplicationContext();
        this.c = getCurrentStatus();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.a.registerReceiver(this.b, intentFilter);
    }

    public final void addObserver(INetworkMonitorObserver iNetworkMonitorObserver) {
        this.d.add(iNetworkMonitorObserver);
    }

    public final void removeObserver(INetworkMonitorObserver iNetworkMonitorObserver) {
        this.d.remove(iNetworkMonitorObserver);
    }

    public final int getCurrentStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.a.getSystemService("connectivity");
        if (connectivityManager == null) {
            return 0;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(activeNetworkInfo == null || !activeNetworkInfo.isConnected() || activeNetworkInfo == null)) {
            switch (activeNetworkInfo.getType()) {
                case 0:
                    return 5;
                case 1:
                    return 2;
            }
        }
        return 1;
    }
}
