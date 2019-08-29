package com.amap.location.d;

import android.content.Context;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.api.service.IndoorLocationProvider;
import com.autonavi.indoor.constant.Configuration;
import com.autonavi.indoor.constant.Configuration.Builder;
import com.autonavi.indoor.constant.Configuration.LocationProvider;
import com.autonavi.indoor.constant.Configuration.PDRProvider;
import com.autonavi.indoor.constant.Configuration.ServerType;
import com.autonavi.indoor.entity.LocationResult;
import com.autonavi.indoor.onlinelocation.OnlineLocator;
import com.autonavi.indoor.util.MapUtils;
import com.autonavi.indoor.util.MapUtils.AosEncryptor;
import com.autonavi.indooroutdoordetectorsdk.BuildingLocationResult;
import com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector;
import com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.DetectListener;
import com.autonavi.sdk.location.LocationInstrument;

/* compiled from: IndoorLocationManager */
public class c {
    /* access modifiers changed from: private */
    public int a = 3;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public b c;
    /* access modifiers changed from: private */
    public Builder d;
    /* access modifiers changed from: private */
    public Configuration e;
    /* access modifiers changed from: private */
    public Configuration f;
    /* access modifiers changed from: private */
    public b g;
    /* access modifiers changed from: private */
    public C0016c h;
    /* access modifiers changed from: private */
    public Handler i;
    /* access modifiers changed from: private */
    public a j;
    /* access modifiers changed from: private */
    public boolean k = true;
    /* access modifiers changed from: private */
    public DetectListener l = new DetectListener() {
        public void detectorStatus(boolean z, String str, int i, BuildingLocationResult buildingLocationResult) {
            com.amap.location.common.d.a.b("locindoor", "indoor status：".concat(String.valueOf(z)));
            c.this.g.a((long) (IndoorOutdoorDetector.getInstance().getN1() * 1000));
            c.this.h.a((long) (IndoorOutdoorDetector.getInstance().getN2() * 1000));
            if (z) {
                c.this.b = str;
            }
            c.this.i.obtainMessage(2, z ? 0 : 3, i).sendToTarget();
        }
    };

    /* compiled from: IndoorLocationManager */
    public interface a {
        String a(String str);

        byte[] a(byte[] bArr);
    }

    /* compiled from: IndoorLocationManager */
    class b extends a {
        public b(Handler handler) {
            super(handler);
        }

        public void run() {
            StringBuilder sb = new StringBuilder("indoor n1：");
            sb.append(this.a);
            com.amap.location.common.d.a.b("locindoor", sb.toString());
            if (this.a) {
                c.this.h.a();
                c.this.i.obtainMessage(2, 1, 0).sendToTarget();
            }
        }
    }

    /* renamed from: com.amap.location.d.c$c reason: collision with other inner class name */
    /* compiled from: IndoorLocationManager */
    static class C0016c extends a {
        public C0016c(Handler handler) {
            super(handler);
        }

        public void run() {
            StringBuilder sb = new StringBuilder("indoor n2：");
            sb.append(this.a);
            com.amap.location.common.d.a.b("locindoor", sb.toString());
            if (this.a) {
                IndoorOutdoorDetector.getInstance().setIndoorOutdoorState(-1);
            }
        }
    }

    /* compiled from: IndoorLocationManager */
    class d extends Handler {
        private boolean b;
        /* access modifiers changed from: private */
        public Handler c = new Handler(getLooper()) {
            public void handleMessage(Message message) {
                if (message.what == 208 && c.this.k && message.obj != null) {
                    if (c.this.a == 1) {
                        c.this.i.obtainMessage(2, 2, 0).sendToTarget();
                    }
                    c.this.g.a();
                    c.this.h.b();
                    LocationResult locationResult = (LocationResult) message.obj;
                    Location location = new Location(IndoorLocationProvider.NAME);
                    location.setLatitude(locationResult.y);
                    location.setLongitude(locationResult.x);
                    double[] dArr = new double[1];
                    double[] dArr2 = new double[1];
                    if (com.amap.location.common.f.b.b(locationResult.y, locationResult.x, dArr, dArr2)) {
                        location.setLatitude(dArr[0]);
                        location.setLongitude(dArr2[0]);
                    }
                    location.setAccuracy(locationResult.r);
                    location.setBearing(locationResult.a);
                    location.setTime(System.currentTimeMillis());
                    if (VERSION.SDK_INT >= 17) {
                        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, TextUtils.isEmpty(locationResult.bid) ? c.this.b : locationResult.bid);
                    bundle.putString("floor", Integer.toString(locationResult.z));
                    bundle.putString("coord", "GCJ02");
                    bundle.putDouble(LocationInstrument.INDOOR_LOCATION_LAT, locationResult.y);
                    bundle.putDouble(LocationInstrument.INDOOR_LOCATION_LON, locationResult.x);
                    bundle.putInt("idrLocType", 0);
                    bundle.putString("flpProvider", IndoorLocationProvider.NAME);
                    bundle.putString("srcProvider", IndoorLocationProvider.NAME);
                    location.setExtras(bundle);
                    d.this.obtainMessage(3, location).sendToTarget();
                }
            }
        };

        public d(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (!this.b) {
                        if (c.this.e == null) {
                            c.this.e = c.this.d.build();
                        }
                        IndoorOutdoorDetector.getInstance().initDetect(c.this.e);
                        IndoorOutdoorDetector.getInstance().registerListener(c.this.l);
                        this.b = true;
                        return;
                    }
                    break;
                case 1:
                    if (this.b) {
                        a();
                        IndoorOutdoorDetector.getInstance().unregisterListener(c.this.l);
                        this.b = false;
                        c.this.a = 3;
                        return;
                    }
                    break;
                case 2:
                    c.this.a = message.arg1;
                    if (c.this.c != null) {
                        c.this.c.a(c.this.a);
                    }
                    if (c.this.a == 0) {
                        a(message.arg2);
                        return;
                    } else if (c.this.a == 3) {
                        a();
                        return;
                    }
                    break;
                case 3:
                    Location location = (Location) message.obj;
                    if (c.this.c != null) {
                        c.this.c.a(location);
                        break;
                    }
                    break;
            }
        }

        private void a(int i) {
            if (OnlineLocator.getInstance().isInited()) {
                a();
            }
            c.this.g.a();
            c.this.h.b();
            try {
                c.this.d.setLocationProvider(i == 2 ? LocationProvider.BLE : LocationProvider.WIFI);
                c.this.f = c.this.d.build();
                OnlineLocator.getInstance().init(c.this.b, c.this.f, new Handler(getLooper()) {
                    public void handleMessage(Message message) {
                        if (message.what == 203) {
                            try {
                                com.amap.location.common.d.a.b("locindoor", "req indoor");
                                OnlineLocator.getInstance().requestLocationUpdates(d.this.c);
                            } catch (Exception unused) {
                            }
                        }
                    }
                });
            } catch (Exception unused) {
            }
        }

        private void a() {
            if (OnlineLocator.getInstance().isInited()) {
                try {
                    OnlineLocator.getInstance().removeUpdates(this.c);
                    OnlineLocator.getInstance().destroy();
                } catch (Exception unused) {
                }
            }
            c.this.g.b();
            c.this.h.b();
        }
    }

    public c(@NonNull Context context, b bVar, String str, String str2, a aVar, Looper looper, boolean z) {
        this.c = bVar;
        this.i = new d(looper == null ? Looper.getMainLooper() : looper);
        this.g = new b(this.i);
        this.h = new C0016c(this.i);
        this.g.a((long) (IndoorOutdoorDetector.getInstance().getN1() * 1000));
        this.h.a((long) (IndoorOutdoorDetector.getInstance().getN2() * 1000));
        this.d = new Builder(context);
        this.d.setAOSParam(str, null);
        this.d.setServer(ServerType.SERVER_AOS, str2);
        this.d.setReportInterval(1000);
        if (z) {
            this.d.setPDRProvider(PDRProvider.STEPANGLE);
        }
        this.j = aVar;
        MapUtils.setAosEncryptorImpl(new AosEncryptor() {
            public String amapEncode(String str) {
                if (c.this.j != null) {
                    return c.this.j.a(str);
                }
                return null;
            }

            public byte[] amapEncode(byte[] bArr) {
                if (c.this.j != null) {
                    return c.this.j.a(bArr);
                }
                return null;
            }
        });
    }

    public void a(String str) {
        this.d.setAOSParam(str, null);
        if (this.e != null) {
            this.e.setAOSParam(str);
        }
        if (this.f != null) {
            this.f.setAOSParam(str);
        }
    }

    public void a() {
        this.i.sendEmptyMessage(0);
        com.amap.location.common.d.a.b("locindoor", "req ioddtector");
    }

    public void b() {
        this.i.sendEmptyMessage(1);
        com.amap.location.common.d.a.b("locindoor", "remove ioddtector");
    }

    public int c() {
        return this.a;
    }

    public void a(Location location) {
        if (this.a == 3) {
            if (location != null) {
                IndoorOutdoorDetector.getInstance().setOutdoorLocation(true, location.getLongitude(), location.getLatitude());
            }
        } else if (this.a == 1 && location != null) {
            Bundle extras = location.getExtras();
            if (extras == null) {
                extras = new Bundle();
            } else if (IndoorLocationProvider.NAME.equals(extras.getString("flpProvider"))) {
                return;
            }
            location.setProvider(IndoorLocationProvider.NAME);
            extras.putString("flpProvider", IndoorLocationProvider.NAME);
            extras.putInt("idrLocType", 0);
            double[] dArr = new double[1];
            double[] dArr2 = new double[1];
            if (com.amap.location.common.f.b.a(location.getLatitude(), location.getLongitude(), dArr, dArr2)) {
                extras.putString("coord", "GCJ02");
                extras.putDouble(LocationInstrument.INDOOR_LOCATION_LAT, dArr[0]);
                extras.putDouble(LocationInstrument.INDOOR_LOCATION_LON, dArr2[0]);
            } else {
                extras.putString("coord", "WGS84");
                extras.putDouble(LocationInstrument.INDOOR_LOCATION_LAT, location.getLatitude());
                extras.putDouble(LocationInstrument.INDOOR_LOCATION_LON, location.getLongitude());
            }
            location.setExtras(extras);
            b(location);
        }
    }

    private void b(Location location) {
        if (this.c != null) {
            this.c.a(location);
        }
    }
}
