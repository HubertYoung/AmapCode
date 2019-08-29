package com.amap.location.icecream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.GnssStatus.Callback;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.IAmapBroadcastDispatcher;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.IAmapBroadcastListener;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.IAmapLocationDispatcher;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.IAmapParamDispatcher;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.IAmapParamListener;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.ICommon;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.ICore;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.INetWork;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.ISignal;
import com.amap.location.icecream.interfaces.IIcecreamBaseLib.IUptunnel;
import com.amap.location.security.Core;
import com.amap.location.uptunnel.UpTunnel;
import com.autonavi.core.network.inter.response.ByteResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: IcecreamBaseLibImpl */
public class a implements IIcecreamBaseLib {
    static ICommon a;
    static IUptunnel b;
    static ICore c;
    static ISignal d;
    static INetWork e;
    static IAmapBroadcastDispatcher f;
    static IAmapLocationDispatcher g;
    static IAmapParamDispatcher h;
    static Context i;
    private static volatile a j;

    /* renamed from: com.amap.location.icecream.a$a reason: collision with other inner class name */
    /* compiled from: IcecreamBaseLibImpl */
    static class C0031a implements IAmapBroadcastDispatcher {
        List<IAmapBroadcastListener> a;

        private C0031a() {
            this.a = new ArrayList();
        }

        public void addListener(IAmapBroadcastListener iAmapBroadcastListener) {
            if (iAmapBroadcastListener != null) {
                synchronized (this.a) {
                    this.a.add(iAmapBroadcastListener);
                }
            }
        }

        public void removeListener(IAmapBroadcastListener iAmapBroadcastListener) {
            if (iAmapBroadcastListener != null) {
                synchronized (this.a) {
                    this.a.remove(iAmapBroadcastListener);
                }
            }
        }

        /* JADX INFO: finally extract failed */
        public void handleMessage(int i, long j, long j2, Object obj) {
            synchronized (this.a) {
                try {
                    for (IAmapBroadcastListener next : this.a) {
                        if (next != null) {
                            int i2 = i;
                            if (next.getAction() == i2) {
                                next.handleMessage(i2, j, j2, obj);
                            }
                        } else {
                            int i3 = i;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public int getListenerCountByAction(int i) {
            int i2;
            synchronized (this.a) {
                i2 = 0;
                for (IAmapBroadcastListener next : this.a) {
                    if (next != null && next.getAction() == i) {
                        i2++;
                    }
                }
            }
            return i2;
        }
    }

    /* compiled from: IcecreamBaseLibImpl */
    static class b implements IAmapLocationDispatcher {
        List<LocationListener> a;

        private b() {
            this.a = new ArrayList();
        }

        public void addLocationListener(LocationListener locationListener) {
            if (locationListener != null) {
                synchronized (this.a) {
                    this.a.add(locationListener);
                }
            }
        }

        public void removeLocationListener(LocationListener locationListener) {
            if (locationListener != null) {
                synchronized (this.a) {
                    this.a.remove(locationListener);
                }
            }
        }

        public void onLocationChanged(Location location) {
            synchronized (this.a) {
                for (LocationListener onLocationChanged : this.a) {
                    onLocationChanged.onLocationChanged(location);
                }
            }
        }
    }

    /* compiled from: IcecreamBaseLibImpl */
    static class c implements IAmapParamDispatcher {
        List<IAmapParamListener> a;

        private c() {
            this.a = new ArrayList();
        }

        public void addParamListener(IAmapParamListener iAmapParamListener) {
            if (iAmapParamListener != null) {
                synchronized (this.a) {
                    this.a.add(iAmapParamListener);
                }
            }
        }

        public void removeParamListener(IAmapParamListener iAmapParamListener) {
            if (iAmapParamListener != null) {
                synchronized (this.a) {
                    this.a.remove(iAmapParamListener);
                }
            }
        }

        public void onParamChanged(String str) {
            synchronized (this.a) {
                for (IAmapParamListener onParamChanged : this.a) {
                    onParamChanged.onParamChanged(str);
                }
            }
        }
    }

    /* compiled from: IcecreamBaseLibImpl */
    static class d implements ICommon {
        private d() {
        }

        public void i(String str, String str2) {
            com.amap.location.common.d.a.a(str, str2);
        }

        public void i(String str, String str2, boolean z) {
            com.amap.location.common.d.a.a(str, str2);
        }

        public void e(String str, String str2) {
            com.amap.location.common.d.a.a(str, str2);
        }

        public void e(String str, String str2, boolean z) {
            com.amap.location.common.d.a.a(str, str2);
        }

        public void trace(String str, String str2) {
            com.amap.location.common.d.a.b(str, str2);
        }

        public String logEncode(String str) {
            return com.amap.location.common.d.a.a(str);
        }

        public String getImei(Context context) {
            return com.amap.location.common.a.a(context);
        }

        public String getTid(Context context) {
            return com.amap.location.common.a.b(context);
        }

        public String getAdiu(Context context) {
            return com.amap.location.common.a.a();
        }

        public String getModel() {
            return com.amap.location.common.a.b();
        }

        public String getManufacturer() {
            return com.amap.location.common.a.c();
        }

        public String longToMac(long j) {
            return com.amap.location.common.f.h.a(j);
        }

        public long macToLong(String str) {
            return com.amap.location.common.f.h.a(str);
        }
    }

    /* compiled from: IcecreamBaseLibImpl */
    static class e implements ICore {
        private e() {
        }

        public String saos(String str, String str2, String str3) {
            return Core.saos(str, str2, str3);
        }

        public byte[] xxt(byte[] bArr, int i) {
            return Core.xxt(bArr, i);
        }
    }

    /* compiled from: IcecreamBaseLibImpl */
    static class f implements INetWork {
        private f() {
        }

        public byte[] doHttpPost(String str, HashMap<String, String> hashMap, byte[] bArr) {
            try {
                boy boy = new boy();
                bpj bpj = new bpj();
                bpj.setUrl(str);
                if (hashMap != null) {
                    for (Entry next : hashMap.entrySet()) {
                        if (!(next == null || ((String) next.getKey()) == null)) {
                            bpj.addHeader((String) next.getKey(), (String) next.getValue());
                        }
                    }
                }
                bpj.setBody(bArr);
                ByteResponse byteResponse = (ByteResponse) boy.a((bph) bpj, ByteResponse.class);
                if (byteResponse != null) {
                    return byteResponse.getResponseBodyData();
                }
                return null;
            } catch (Throwable unused) {
                return null;
            }
        }

        public byte[] doHttpGet(String str, HashMap<String, String> hashMap) {
            try {
                boy boy = new boy();
                bpf bpf = new bpf();
                bpf.setUrl(str);
                if (hashMap != null) {
                    for (Entry next : hashMap.entrySet()) {
                        if (!(next == null || ((String) next.getKey()) == null)) {
                            bpf.addHeader((String) next.getKey(), (String) next.getValue());
                        }
                    }
                }
                ByteResponse byteResponse = (ByteResponse) boy.a((bph) bpf, ByteResponse.class);
                if (byteResponse != null) {
                    return byteResponse.getResponseBodyData();
                }
                return null;
            } catch (Throwable unused) {
                return null;
            }
        }
    }

    /* compiled from: IcecreamBaseLibImpl */
    static class g implements ISignal {
        @SuppressLint({"MissingPermission"})
        public boolean addNmeaListener(NmeaListener nmeaListener, Looper looper) {
            return false;
        }

        public void removeNmeaListener(NmeaListener nmeaListener) {
        }

        private g() {
        }

        @SuppressLint({"MissingPermission"})
        public void requestLocationUpdates(String str, long j, float f, LocationListener locationListener, Looper looper) {
            com.amap.location.g.b.a.a(a.i).a(str, j, f, locationListener, looper);
        }

        @SuppressLint({"MissingPermission"})
        @RequiresApi(api = 24)
        public boolean registerGnssStatusCallback(Callback callback, Looper looper) {
            return com.amap.location.g.b.a.a(a.i).a(callback, looper);
        }

        @RequiresApi(api = 24)
        public void unregisterGnssStatusCallback(Callback callback) {
            com.amap.location.g.b.a.a(a.i).a(callback);
        }

        @SuppressLint({"MissingPermission"})
        public boolean addGpsStatusListener(Listener listener, Looper looper) {
            return com.amap.location.g.b.a.a(a.i).a(listener, looper);
        }

        @RequiresApi(api = 24)
        public boolean addNmeaListener(OnNmeaMessageListener onNmeaMessageListener) {
            if (a.i != null) {
                try {
                    return ((LocationManager) a.i.getSystemService("location")).addNmeaListener(onNmeaMessageListener);
                } catch (SecurityException unused) {
                }
            }
            return false;
        }

        public void removeUpdates(LocationListener locationListener) {
            com.amap.location.g.b.a.a(a.i).a(locationListener);
        }

        public void removeGpsStatusListener(Listener listener) {
            com.amap.location.g.b.a.a(a.i).a(listener);
        }

        @RequiresApi(api = 24)
        public void removeNmeaListener(OnNmeaMessageListener onNmeaMessageListener) {
            if (a.i != null) {
                ((LocationManager) a.i.getSystemService("location")).removeNmeaListener(onNmeaMessageListener);
            }
        }

        public boolean isProviderEnabled(String str) {
            return com.amap.location.g.b.a.a(a.i).a(str);
        }

        public GpsStatus getGpsStatus(GpsStatus gpsStatus) {
            return com.amap.location.g.b.a.a(a.i).a(gpsStatus);
        }

        public List<String> getAllProviders() {
            return com.amap.location.g.b.a.a(a.i).a();
        }

        public void listen(PhoneStateListener phoneStateListener, int i, long j, Looper looper) {
            com.amap.location.g.a.a.a(a.i).a(phoneStateListener, i, j, looper);
        }

        @SuppressLint({"MissingPermission"})
        public CellLocation getCellLocation() {
            return com.amap.location.g.a.a.a(a.i).a();
        }

        @SuppressLint({"MissingPermission"})
        public List<CellInfo> getAllCellInfo() {
            return com.amap.location.g.a.a.a(a.i).b();
        }

        public int getNetworkType() {
            return com.amap.location.g.a.a.a(a.i).c();
        }

        public int getPhoneType() {
            return com.amap.location.g.a.a.a(a.i).d();
        }

        public String getNetworkOperator() {
            return com.amap.location.g.a.a.a(a.i).e();
        }

        @SuppressLint({"MissingPermission"})
        public List<NeighboringCellInfo> getNeighboringCellInfo() {
            return com.amap.location.g.a.a.a(a.i).f();
        }

        public boolean startScan() {
            return com.amap.location.g.d.a.a(a.i).a();
        }

        public boolean enableWifiAlwaysScan(Context context) {
            return com.amap.location.g.d.a.a(a.i).b(context);
        }

        public List<ScanResult> getScanResults() {
            return com.amap.location.g.d.a.a(a.i).b();
        }

        public WifiInfo getConnectionInfo() {
            return com.amap.location.g.d.a.a(a.i).c();
        }

        @RequiresApi(api = 18)
        public boolean isScanAlwaysAvailable() {
            return com.amap.location.g.d.a.a(a.i).d();
        }

        public int getWifiState() {
            return com.amap.location.g.d.a.a(a.i).e();
        }

        public boolean isWifiEnabled() {
            return com.amap.location.g.d.a.a(a.i).g();
        }
    }

    /* compiled from: IcecreamBaseLibImpl */
    static class h implements IUptunnel {
        private h() {
        }

        public void addCount(int i) {
            UpTunnel.addCount(i);
        }

        public void reportEvent(int i, byte[] bArr) {
            UpTunnel.reportEvent(i, bArr);
        }

        public void reportLog(int i, String str) {
            UpTunnel.reportLog(i, str);
        }

        public void reportKeyLog(int i, byte[] bArr) {
            UpTunnel.reportKeyLog(i, bArr);
        }

        public void execCMD(JSONObject jSONObject) {
            UpTunnel.execCMD(jSONObject);
        }

        public void execCMD(JSONArray jSONArray) {
            UpTunnel.execCMD(jSONArray);
        }

        public void reportBlockData(int i, byte[] bArr) {
            UpTunnel.reportBlockData(i, bArr);
        }

        public long getTableSize(int i) {
            return UpTunnel.getTableSize(i);
        }
    }

    protected static a a(Context context) {
        if (j == null) {
            synchronized (a.class) {
                try {
                    if (j == null) {
                        j = new a(context);
                    }
                }
            }
        }
        return j;
    }

    public ICommon getCommonInstance() {
        return a;
    }

    public ISignal getSignalInstance() {
        return d;
    }

    public IUptunnel getUptunnelInstance() {
        return b;
    }

    public ICore getCoreInstance() {
        return c;
    }

    public INetWork getNetWorkInstance() {
        return e;
    }

    public IAmapBroadcastDispatcher getAmapBroadcastInstance() {
        return f;
    }

    public IAmapLocationDispatcher getAmapLocationInstance() {
        return g;
    }

    public IAmapParamDispatcher getAmapParamInstance() {
        return h;
    }

    private a(Context context) {
        i = context;
        a = new d();
        b = new h();
        c = new e();
        d = new g();
        e = new f();
        f = new C0031a();
        g = new b();
        h = new c();
    }
}
