package com.autonavi.map.core;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.sdk.location.LocationInstrument;
import com.tencent.connect.common.Constants;
import java.util.ArrayList;

public class Real3DManager {
    public static final String a = String.format("amapuri://map/real3d?switch=on&sourceApplication=%s&zoom=%d&lat=%.6f&lon=%.6f&camAngle=%d", new Object[]{BaseIntentDispatcher.INTENT_CALL_SPLASH, Integer.valueOf(18), Double.valueOf(39.90831d), Double.valueOf(116.461756d), Integer.valueOf(75)});
    public static final String b = String.format("amapuri://map/real3d?switch=on&sourceApplication=%s&zoom=%d&lat=%.6f&lon=%.6f&camAngle=%d", new Object[]{"push", Integer.valueOf(18), Double.valueOf(39.90831d), Double.valueOf(116.461756d), Integer.valueOf(75)});
    public static final String c = String.format("amapuri://real3dmap/real3d?isclean=%d", new Object[]{Integer.valueOf(0)});
    private static final String i = "com.autonavi.map.core.Real3DManager";
    private static final GeoPoint j = new GeoPoint(116.461756d, 39.90831d);
    private static volatile Real3DManager k;
    public ArrayList<String> d;
    public boolean e;
    public boolean f = false;
    public boolean g = false;
    public boolean h = false;
    private int l;
    private volatile boolean m;
    private volatile boolean n;
    private String o;
    private boolean p;

    public enum ActionLogFromEnum {
        SWITCH(0),
        HOTKEYWORD(1),
        PUSH(2),
        SPLASH(3);
        
        private int mValue;

        private ActionLogFromEnum(int i) {
            this.mValue = i;
        }

        public final String toString() {
            return String.valueOf(this.mValue);
        }
    }

    public enum ActionLogStateEnum {
        CLOSE(0),
        OPEN(1);
        
        private int mValue;

        private ActionLogStateEnum(int i) {
            this.mValue = i;
        }

        public final String toString() {
            return String.valueOf(this.mValue);
        }
    }

    private Real3DManager() {
        b();
        this.m = a(true);
        this.n = b(true);
        this.l = -1;
        this.e = true;
        this.o = null;
        this.p = false;
    }

    public static Real3DManager a() {
        if (k == null) {
            synchronized (Real3DManager.class) {
                try {
                    if (k == null) {
                        k = new Real3DManager();
                    }
                }
            }
        }
        return k;
    }

    private void b() {
        String[] split;
        this.d = new ArrayList<>();
        String str = "";
        ls lsVar = lt.a().c;
        if (lsVar.v != null) {
            str = lsVar.v;
        }
        boolean z = false;
        if (!TextUtils.isEmpty(str)) {
            boolean z2 = false;
            for (String str2 : str.split(",")) {
                if (!TextUtils.isEmpty(str2)) {
                    String trim = str2.trim();
                    z2 = z2 || "110000".equals(trim);
                    this.d.add(trim);
                }
            }
            z = z2;
        }
        if (!z) {
            this.d.add("110000");
        }
    }

    public final boolean a(boolean z) {
        if (z) {
            this.m = lt.a().e();
        }
        return this.m;
    }

    public static void b(bro bro) {
        if (bro != null) {
            bty mapView = bro.getMapView();
            if (mapView != null) {
                int j2 = mapView.e().j();
                if (mapView.j(j2)) {
                    AMapLog.i(i, "Disable Real3D show.");
                    mapView.a(j2, false);
                    cdd.n().g();
                }
            }
        }
    }

    public static void a(cdb cdb, bro bro) {
        if (cdb != null && bro != null) {
            bty mapView = bro.getMapView();
            mapView.f(18.0f);
            mapView.a(j.x, j.y);
            mapView.g(75.0f);
            b(cdb, bro);
        }
    }

    public static PageBundle a(Uri uri) {
        if (uri == null) {
            return null;
        }
        String queryParameter = uri.getQueryParameter(FunctionSupportConfiger.SWITCH_TAG);
        if (TextUtils.isEmpty(queryParameter)) {
            return null;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString(Constants.KEY_ACTION, "action_real3d");
        pageBundle.putBoolean("open", CameraParams.FLASH_MODE_ON.equals(queryParameter));
        try {
            pageBundle.putInt("camera_angle", Integer.parseInt(uri.getQueryParameter("camAngle")));
        } catch (Exception unused) {
        }
        String queryParameter2 = uri.getQueryParameter("lat");
        String queryParameter3 = uri.getQueryParameter(LocationParams.PARA_FLP_AUTONAVI_LON);
        String queryParameter4 = uri.getQueryParameter("zoom");
        try {
            double parseDouble = Double.parseDouble(queryParameter3);
            double parseDouble2 = Double.parseDouble(queryParameter2);
            pageBundle.putDouble(LocationParams.PARA_FLP_AUTONAVI_LON, parseDouble);
            pageBundle.putDouble("lat", parseDouble2);
            pageBundle.putInt("zoom", Integer.parseInt(queryParameter4));
        } catch (Exception unused2) {
        }
        pageBundle.putString(DriveUtil.SOURCE_APPLICATION, uri.getQueryParameter(DriveUtil.SOURCE_APPLICATION));
        return pageBundle;
    }

    private static void b(@NonNull cdb cdb, bro bro) {
        if (bro != null) {
            bty mapView = bro.getMapView();
            if (mapView != null) {
                cdz d2 = cdb.d();
                if (d2 != null) {
                    if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                        d2.b(1);
                    }
                    bro.getOverlayManager().setGPSShowMode(0);
                    bro.getOverlayManager().setGPSCenterLocked(false);
                    mapView.i(0);
                }
            }
        }
    }

    public static void a(String str) {
        AMapLog.i(i, str);
    }

    public final boolean b(boolean z) {
        if (!a(false)) {
            return false;
        }
        if (z) {
            this.n = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("real_3d_is_open_new", true);
        }
        return this.n;
    }

    public final void c(boolean z) {
        if (a(false)) {
            Object[] objArr = new Object[1];
            objArr[0] = z ? "open" : DataflowMonitorModel.METHOD_NAME_CLOSE;
            AMapLog.i(i, String.format("Set switch state is %s.", objArr));
            this.n = z;
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("real_3d_is_open_new", z);
        }
    }

    public final boolean a(bro bro, int i2) {
        if (!a(false) || !b(false) || bro == null) {
            return false;
        }
        if (i2 == -2) {
            i2 = bin.a.p("101");
        }
        if (i2 == 0) {
            return true;
        }
        return false;
    }

    public final void a(bro bro) {
        if (a(false) && bro != null) {
            bty mapView = bro.getMapView();
            if (mapView != null) {
                int p2 = bin.a.p("101");
                int j2 = mapView.e().j();
                if (p2 != 0) {
                    if (a(false)) {
                        bty mapView2 = bro.getMapView();
                        if (mapView2 != null) {
                            if (!mapView2.j(j2)) {
                                AMapLog.i(i, "Enable Real3D show.");
                                mapView2.a(j2, 0, mapView2.ae(), mapView2.o(false), 1);
                                mapView2.a(j2, true);
                                cdd.n().g();
                            } else {
                                mapView2.a(0, mapView2.ae(), mapView2.o(false));
                            }
                            bin.a.d((String) "101", 0);
                            mapView2.q(true);
                        }
                    }
                    return;
                }
                if (!mapView.j(j2)) {
                    AMapLog.i(i, "Enable Real3D show.");
                    mapView.a(j2, true);
                    cdd.n().g();
                }
            }
        }
    }

    public final void c(bro bro) {
        if (a(false) && bro != null) {
            this.e = true;
            this.p = false;
            if (a(bro, -2)) {
                a(bro);
                this.f = true;
                return;
            }
            b(bro);
        }
    }

    public final void d(bro bro) {
        if (a(false) && bro != null) {
            if (!this.p) {
                this.e = false;
            }
            this.f = false;
        }
    }

    public final void a(PageBundle pageBundle, bro bro, cde cde) {
        if (a(false) && cde != null && bro != null) {
            bty mapView = bro.getMapView();
            if (mapView != null) {
                boolean z = pageBundle.getBoolean("open", false);
                if (z) {
                    c(true);
                    a(bro);
                    double d2 = pageBundle.getDouble("lat", -1.0d);
                    double d3 = pageBundle.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON, -1.0d);
                    if (d2 > 0.0d && d3 > 0.0d) {
                        GeoPoint geoPoint = new GeoPoint();
                        geoPoint.setLonLat(d3, d2);
                        mapView.a(geoPoint.x, geoPoint.y);
                        b(cde, bro);
                    }
                    int i2 = pageBundle.getInt("zoom", -1);
                    if (i2 > 0) {
                        mapView.f((float) i2);
                    }
                    int i3 = pageBundle.getInt("camera_angle", -1);
                    if (i3 >= 0) {
                        mapView.g((float) i3);
                    }
                } else {
                    c(false);
                    b(bro);
                }
                String string = pageBundle.getString(DriveUtil.SOURCE_APPLICATION);
                if (BaseIntentDispatcher.INTENT_CALL_SPLASH.equals(string)) {
                    ActionLogFromEnum actionLogFromEnum = ActionLogFromEnum.SPLASH;
                    if (z) {
                        ActionLogStateEnum actionLogStateEnum = ActionLogStateEnum.OPEN;
                    } else {
                        ActionLogStateEnum actionLogStateEnum2 = ActionLogStateEnum.CLOSE;
                    }
                } else if ("push".equals(string)) {
                    ActionLogFromEnum actionLogFromEnum2 = ActionLogFromEnum.PUSH;
                    if (z) {
                        ActionLogStateEnum actionLogStateEnum3 = ActionLogStateEnum.OPEN;
                    } else {
                        ActionLogStateEnum actionLogStateEnum4 = ActionLogStateEnum.CLOSE;
                    }
                } else {
                    if ("hotkeyword".equals(string)) {
                        ActionLogFromEnum actionLogFromEnum3 = ActionLogFromEnum.HOTKEYWORD;
                        if (z) {
                            ActionLogStateEnum actionLogStateEnum5 = ActionLogStateEnum.OPEN;
                            return;
                        }
                        ActionLogStateEnum actionLogStateEnum6 = ActionLogStateEnum.CLOSE;
                    }
                }
            }
        }
    }
}
