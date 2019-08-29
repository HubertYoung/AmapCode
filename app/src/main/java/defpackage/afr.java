package defpackage;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.tools.AmapBluetoothAdapter$1;
import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: afr reason: default package */
/* compiled from: AmapBluetoothAdapter */
public class afr {
    private static volatile afr e;
    public Context a;
    public volatile BluetoothDevice b;
    public final CopyOnWriteArrayList<a> c = new CopyOnWriteArrayList<>();
    public BroadcastReceiver d = new AmapBluetoothAdapter$1(this);

    /* renamed from: afr$a */
    /* compiled from: AmapBluetoothAdapter */
    public interface a {
        void b(boolean z);
    }

    public static afr a(Context context) throws InvalidParameterException {
        if (context == null) {
            AMapLog.fatal("paas.tools", "AmapBluetoothAdapter", "Context参数错误");
            throw new InvalidParameterException("Context参数错误");
        }
        if (e == null) {
            synchronized (afr.class) {
                try {
                    if (e == null) {
                        e = new afr(context.getApplicationContext());
                    }
                }
            }
        }
        return e;
    }

    private afr(Context context) {
        this.a = context;
    }

    public static boolean a() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            return false;
        }
        int profileConnectionState = defaultAdapter.getProfileConnectionState(2);
        int profileConnectionState2 = defaultAdapter.getProfileConnectionState(1);
        if (defaultAdapter == null || (profileConnectionState != 2 && profileConnectionState2 != 2)) {
            return false;
        }
        return true;
    }

    public static /* synthetic */ void a(afr afr, boolean z) {
        synchronized (afr.c) {
            Iterator<a> it = afr.c.iterator();
            while (it.hasNext()) {
                it.next().b(z);
            }
        }
    }
}
