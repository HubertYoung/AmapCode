package com.autonavi.link.connect.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.link.connect.listener.Connection.OnDiscoverHostListener;
import com.autonavi.link.connect.listener.Connection.OnUdpBroadcastListener;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.connect.wifi.a.C0050a;
import com.autonavi.link.connect.wifi.b.a;
import com.autonavi.link.utils.Logger;
import com.tencent.connect.common.Constants;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.apache.http.conn.util.InetAddressUtils;

public class ShareNetManager {
    public static final int MSG_CLIENT_FIND_DEVICE = 100;
    public static final int MSG_CLIENT_INFO_DEVICE = 101;
    public static final int MSG_CLIENT_STATE_DISCONNECT = 102;
    public static final int MSG_SERVER_FINE_DEVICE = 201;
    public static final int MSG_SERVER_INFO_DEVICE = 202;
    public static final int MSG_SERVER_STATE_DISCONNECT = 200;
    /* access modifiers changed from: private */
    public static final String a = "ShareNetManager";
    private static volatile ShareNetManager b;
    /* access modifiers changed from: private */
    public OnDiscoverHostListener c;
    /* access modifiers changed from: private */
    public OnUdpBroadcastListener d;
    /* access modifiers changed from: private */
    public a e;
    /* access modifiers changed from: private */
    public b f;
    private a g;
    private com.autonavi.link.connect.wifi.a.a h;
    /* access modifiers changed from: private */
    public List<DiscoverInfo> i;
    /* access modifiers changed from: private */
    public final Handler j = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            int i = message.what;
            switch (i) {
                case 100:
                    if (ShareNetManager.this.c != null) {
                        ShareNetManager.this.c.onFindDevice(ShareNetManager.this.i);
                        return;
                    }
                    break;
                case 101:
                    ShareNetManager.this.k = true;
                    if (ShareNetManager.this.c != null) {
                        ShareNetManager.this.c.onDiscoverHost((DiscoverInfo) message.obj);
                    }
                    if (ShareNetManager.this.e != null) {
                        ShareNetManager.this.e.a();
                        return;
                    }
                    break;
                case 102:
                    Logger.d(ShareNetManager.a, (String) "MSG_CLIENT_STATE_DISCONNECT-----> ", new Object[0]);
                    ShareNetManager.this.k = false;
                    Logger.d(ShareNetManager.a, (String) "MSG_CLIENT_STATE_DISCONNECT-----> removeMessages", new Object[0]);
                    ShareNetManager.this.j.removeMessages(102);
                    Logger.d(ShareNetManager.a, (String) "MSG_CLIENT_STATE_DISCONNECT-----> quitMult ", new Object[0]);
                    ShareNetManager.this.b();
                    String a2 = ShareNetManager.a;
                    StringBuilder sb = new StringBuilder("MSG_CLIENT_STATE_DISCONNECT-----> mDiscoverHostListener--> ");
                    sb.append(ShareNetManager.this.c);
                    Logger.d(a2, sb.toString(), new Object[0]);
                    if (ShareNetManager.this.c != null) {
                        Logger.d(ShareNetManager.a, (String) "MSG_CLIENT_STATE_DISCONNECT-----> notify UI --> onDisconnect", new Object[0]);
                        ShareNetManager.this.c.onDisconnect();
                    }
                    Logger.d(ShareNetManager.a, (String) "MSG_CLIENT_STATE_DISCONNECT-----> notify UI --> end", new Object[0]);
                    return;
                default:
                    switch (i) {
                        case 200:
                            ShareNetManager.this.k = false;
                            ShareNetManager.this.j.removeMessages(200);
                            ShareNetManager.this.b();
                            if (ShareNetManager.this.d != null) {
                                ShareNetManager.this.d.onDisconnect();
                                break;
                            }
                            break;
                        case 201:
                            if (ShareNetManager.this.d != null) {
                                ShareNetManager.this.d.onFindDevice(ShareNetManager.this.i);
                                return;
                            }
                            break;
                        case 202:
                            ShareNetManager.this.k = true;
                            if (ShareNetManager.this.d != null) {
                                ShareNetManager.this.d.onBroadcastEnd((DiscoverInfo) message.obj);
                            }
                            if (ShareNetManager.this.f != null) {
                                ShareNetManager.this.f.b();
                                return;
                            }
                            break;
                    }
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean k;
    private WeakReference<Context> l;
    private String m = "";
    private String n = "";
    private String o = "";
    private BroadcastReceiver p = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String a2 = ShareNetManager.a;
            StringBuilder sb = new StringBuilder("BroadcastReceiver onReceive-----> ");
            sb.append(intent.getAction());
            Logger.d(a2, sb.toString(), new Object[0]);
            try {
                if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                    if (connectivityManager != null) {
                        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
                        if (Logger.getIsLog() && networkInfo != null) {
                            String a3 = ShareNetManager.a;
                            StringBuilder sb2 = new StringBuilder("BroadcastReceiver onReceive-----> wifiNetInfo.isConnected()--> ");
                            sb2.append(networkInfo.isConnected());
                            Logger.d(a3, sb2.toString(), new Object[0]);
                        }
                        if (networkInfo == null || !networkInfo.isConnected()) {
                            ShareNetManager.this.q = null;
                            Logger.d(ShareNetManager.a, "BroadcastReceiver onReceive-----> 断开 111--> wifiNetInfo--> ".concat(String.valueOf(networkInfo)), new Object[0]);
                        }
                        return;
                    }
                    ShareNetManager.this.q = null;
                    Logger.d(ShareNetManager.a, (String) "BroadcastReceiver onReceive-----> 断开 connectivityManager is null", new Object[0]);
                }
            } catch (Exception e) {
                ShareNetManager.this.q = null;
                Logger.d(ShareNetManager.a, "BroadcastReceiver onReceive-----> 断开 333--> ".concat(String.valueOf(e)), new Object[0]);
            }
        }
    };
    /* access modifiers changed from: private */
    public InetAddress q;

    public static ShareNetManager getInstance() {
        if (b == null) {
            synchronized (ShareNetManager.class) {
                try {
                    if (b == null) {
                        b = new ShareNetManager();
                    }
                }
            }
        }
        return b;
    }

    public synchronized void startDiscoverHost(OnDiscoverHostListener onDiscoverHostListener) throws SocketException {
        Logger.d(a, "startDiscoverHost-----> mDiscoverHostListener--> ".concat(String.valueOf(onDiscoverHostListener)), new Object[0]);
        this.c = onDiscoverHostListener;
        if (this.e == null) {
            this.i = new ArrayList();
            this.e = new a();
        }
        this.e.b();
        this.e.a((C0050a) new C0050a() {
            public void a(List<DiscoverInfo> list) {
                ShareNetManager.this.i = list;
                ShareNetManager.this.j.obtainMessage(100).sendToTarget();
            }
        });
    }

    public void stopDiscoverHost() {
        Logger.d(a, (String) "stopDiscoverHost-----> stopDiscoverHost--> mDiscoverHostListener--> null", new Object[0]);
        this.c = null;
        b();
    }

    public void startLink(String str) {
        if (this.h == null) {
            this.h = new com.autonavi.link.connect.wifi.a.a(str, Constants.CODE_REQUEST_MIN, this.j);
            this.h.start();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.g != null) {
            this.g.interrupt();
            this.g = null;
        }
        if (this.h != null) {
            this.h.interrupt();
            this.h = null;
        }
        if (this.f != null) {
            this.f.b();
        }
        if (this.e != null) {
            this.e.a();
        }
    }

    public synchronized void startBroadcast(OnUdpBroadcastListener onUdpBroadcastListener) throws IOException {
        this.d = onUdpBroadcastListener;
        if (this.f == null) {
            this.i = new ArrayList();
            this.f = new b();
        }
        this.f.a();
        this.f.a((b.a) new b.a() {
            public void a(List<DiscoverInfo> list) {
                ShareNetManager.this.i = list;
                ShareNetManager.this.j.obtainMessage(201).sendToTarget();
            }
        });
        if (this.g == null) {
            this.g = new a(Constants.CODE_REQUEST_MIN, this.j);
            this.g.start();
        }
    }

    public synchronized void stopBroadcast() {
        this.d = null;
        b();
    }

    public boolean getIsConnect() {
        return this.k;
    }

    public String getLocalAddress() throws SocketException {
        InetAddress ipAddress = getIpAddress(false);
        if (ipAddress == null) {
            return null;
        }
        return ipAddress.getHostAddress();
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x002a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void init(android.content.Context r4) {
        /*
            r3 = this;
            java.lang.ref.WeakReference r0 = new java.lang.ref.WeakReference
            r0.<init>(r4)
            r3.l = r0
            java.lang.String r0 = r4.getPackageName()     // Catch:{ Exception -> 0x002a }
            r3.m = r0     // Catch:{ Exception -> 0x002a }
            android.content.pm.PackageManager r0 = r4.getPackageManager()     // Catch:{ Exception -> 0x002a }
            java.lang.String r1 = r3.m     // Catch:{ Exception -> 0x002a }
            r2 = 0
            android.content.pm.PackageInfo r1 = r0.getPackageInfo(r1, r2)     // Catch:{ Exception -> 0x002a }
            java.lang.String r1 = r1.versionName     // Catch:{ Exception -> 0x002a }
            r3.o = r1     // Catch:{ Exception -> 0x002a }
            java.lang.String r1 = r3.m     // Catch:{ Exception -> 0x002a }
            android.content.pm.ApplicationInfo r1 = r0.getApplicationInfo(r1, r2)     // Catch:{ Exception -> 0x002a }
            java.lang.CharSequence r0 = r0.getApplicationLabel(r1)     // Catch:{ Exception -> 0x002a }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x002a }
            r3.n = r0     // Catch:{ Exception -> 0x002a }
        L_0x002a:
            android.content.IntentFilter r0 = new android.content.IntentFilter     // Catch:{ Exception -> 0x003a }
            r0.<init>()     // Catch:{ Exception -> 0x003a }
            java.lang.String r1 = "android.net.conn.CONNECTIVITY_CHANGE"
            r0.addAction(r1)     // Catch:{ Exception -> 0x003a }
            android.content.BroadcastReceiver r1 = r3.p     // Catch:{ Exception -> 0x003a }
            r4.registerReceiver(r1, r0)     // Catch:{ Exception -> 0x003a }
            return
        L_0x003a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.connect.wifi.ShareNetManager.init(android.content.Context):void");
    }

    public void release() {
        try {
            if (this.l != null) {
                Context context = (Context) this.l.get();
                if (context != null) {
                    context.unregisterReceiver(this.p);
                    this.l.clear();
                }
                this.l = null;
            }
        } catch (Exception unused) {
        }
        this.q = null;
    }

    public synchronized InetAddress getIpAddress(boolean z) {
        StringBuilder sb = new StringBuilder(" ----------------------------------------------------------------------- mInetAddress--> ");
        sb.append(this.q);
        Logger.d((String) "hanwei", sb.toString(), new Object[0]);
        if (this.q == null) {
            Logger.d((String) "hanwei", (String) " huo qu getIpAddress--> 为null--> ", new Object[0]);
            this.q = c();
            StringBuilder sb2 = new StringBuilder(" huo qu getIpAddress success--> ");
            sb2.append(this.q);
            Logger.d((String) "hanwei", sb2.toString(), new Object[0]);
        }
        InetAddress a2 = a(this.q);
        Logger.d((String) "hanwei", " huo qu getIpAddress--> no null--> zu bo is -->  ".concat(String.valueOf(a2)), new Object[0]);
        if (a2 == null) {
            this.q = null;
            return this.q;
        } else if (z) {
            return a2;
        } else {
            return this.q;
        }
    }

    private synchronized InetAddress c() {
        Logger.d((String) "hanwei", (String) " getInetAddress begin--------------", new Object[0]);
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Logger.d((String) "hanwei", (String) " getInetAddress begin11111--------------", new Object[0]);
                NetworkInterface nextElement = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        Logger.d((String) "hanwei", (String) " getInetAddress begin22222--------------", new Object[0]);
                        InetAddress nextElement2 = inetAddresses.nextElement();
                        if (!nextElement2.isLoopbackAddress() && ((nextElement.getDisplayName().contains("wlan0") || nextElement.getDisplayName().contains("mlan0")) && InetAddressUtils.isIPv4Address(nextElement2.getHostAddress()))) {
                            StringBuilder sb = new StringBuilder(" getIpAddress mInetAddress --> success --> ");
                            sb.append(this.q);
                            Logger.d((String) "hanwei", sb.toString(), new Object[0]);
                            return nextElement2;
                        }
                    }
                }
            }
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder(" getInetAddress Exception-------------->");
            sb2.append(e2.toString());
            Logger.d((String) "hanwei", sb2.toString(), new Object[0]);
        }
        return null;
    }

    private InetAddress a(InetAddress inetAddress) {
        if (inetAddress == null) {
            return null;
        }
        try {
            NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(inetAddress);
            if (byInetAddress != null) {
                for (InterfaceAddress broadcast : byInetAddress.getInterfaceAddresses()) {
                    InetAddress broadcast2 = broadcast.getBroadcast();
                    if (broadcast2 != null) {
                        return broadcast2;
                    }
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public String getAppPackageName() {
        return this.m;
    }

    public String getAppName() {
        return this.n;
    }

    public String getAppVersion() {
        return this.o;
    }
}
