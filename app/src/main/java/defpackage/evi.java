package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/* renamed from: evi reason: default package */
/* compiled from: OpenUDID_manager_hmt */
public final class evi implements ServiceConnection {
    private static final boolean a = evd.b;
    private static String g = null;
    private static boolean h = false;
    private final Context b;
    private List<ResolveInfo> c;
    /* access modifiers changed from: private */
    public Map<String, Integer> d = new HashMap();
    private final SharedPreferences e;
    private final Random f = new Random();

    /* renamed from: evi$a */
    /* compiled from: OpenUDID_manager_hmt */
    class a implements Comparator {
        private a() {
        }

        /* synthetic */ a(evi evi, byte b) {
            this();
        }

        public final int compare(Object obj, Object obj2) {
            if (((Integer) evi.this.d.get(obj)).intValue() < ((Integer) evi.this.d.get(obj2)).intValue()) {
                return 1;
            }
            return evi.this.d.get(obj) == evi.this.d.get(obj2) ? 0 : -1;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
    }

    private evi(Context context) {
        this.e = context.getSharedPreferences("openudid_prefs", 0);
        this.b = context;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            Parcel obtain = Parcel.obtain();
            obtain.writeInt(this.f.nextInt());
            Parcel obtain2 = Parcel.obtain();
            iBinder.transact(1, Parcel.obtain(), obtain2, 0);
            if (obtain.readInt() == obtain2.readInt()) {
                String readString = obtain2.readString();
                if (readString != null) {
                    if (this.d.containsKey(readString)) {
                        this.d.put(readString, Integer.valueOf(this.d.get(readString).intValue() + 1));
                    } else {
                        this.d.put(readString, Integer.valueOf(1));
                    }
                }
            }
        } catch (RemoteException e2) {
            if (a) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(e2.getMessage());
                euw.a(sb.toString());
            }
        }
        this.b.unbindService(this);
        euw.a((String) "unbind");
        c();
    }

    private void c() {
        while (this.c.size() > 0) {
            if (a) {
                new StringBuilder("Trying service ").append(this.c.get(0).loadLabel(this.b.getPackageManager()));
            }
            ServiceInfo serviceInfo = this.c.get(0).serviceInfo;
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(serviceInfo.applicationInfo.packageName, serviceInfo.name));
            this.c.remove(0);
            try {
                if (!this.b.bindService(intent, this, 1)) {
                    euw.a((String) "bind opendudid service faill_hmt");
                    this.b.unbindService(this);
                    c();
                    return;
                }
                euw.a((String) "bind opendudid service success_hmt");
                return;
            } catch (SecurityException unused) {
            } catch (NullPointerException e2) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(e2.getMessage());
                euw.a(sb.toString());
                return;
            } catch (IllegalArgumentException e3) {
                StringBuilder sb2 = new StringBuilder("Collected:");
                sb2.append(e3.getMessage());
                euw.a(sb2.toString());
                return;
            }
        }
        if (!this.d.isEmpty()) {
            TreeMap treeMap = new TreeMap(new a(this, 0));
            treeMap.putAll(this.d);
            g = (String) treeMap.firstKey();
        }
        if (g == null) {
            String string = Secure.getString(this.b.getContentResolver(), "android_id");
            g = string;
            if (string == null || g.equals("9774d56d682e549c") || g.length() < 15) {
                g = new BigInteger(64, new SecureRandom()).toString(16);
            }
        }
        if (a) {
            new StringBuilder("OpenUDID: ").append(g);
        }
        Editor edit = this.e.edit();
        edit.putString("openudid", g);
        edit.commit();
        h = true;
    }

    public static String a() {
        if (!h) {
            euw.a((String) "Initialisation isn't done");
        }
        return g;
    }

    public static boolean b() {
        return h;
    }

    public static void a(Context context) {
        evi evi = new evi(context);
        String string = evi.e.getString("openudid", null);
        g = string;
        if (string == null) {
            evi.c = context.getPackageManager().queryIntentServices(new Intent("org.OpenUDID.GETUDID"), 0);
            if (a) {
                StringBuilder sb = new StringBuilder();
                sb.append(evi.c.size());
                sb.append(" services matches OpenUDID");
            }
            if (evi.c != null) {
                evi.c();
            }
        } else {
            if (a) {
                new StringBuilder("OpenUDID: ").append(g);
            }
            h = true;
        }
    }
}
