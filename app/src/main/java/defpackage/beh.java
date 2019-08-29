package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.utils.Constant.a;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.Collections;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: beh reason: default package */
/* compiled from: RedesignFloorWidgetController */
public final class beh implements bea, cdf {
    beb a;
    ami b;
    bed c;
    bef d;
    agl<bei> e = new agl<>();
    Float f = null;
    Runnable g = new Runnable() {
        public final void run() {
            if (beh.this.c != null) {
                beh.this.c.resetPosByCurrentFloor();
            }
        }
    };
    private ami h;
    private ami i;
    private ami j;
    private ami k;
    private bee l = new bee() {
        public final void a(bdz bdz, bdz bdz2) {
            if (beh.this.b != null) {
                beh beh = beh.this;
                if (beh.b != null) {
                    String str = beh.b.e;
                    String str2 = beh.b.a;
                    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put(TrafficUtil.POIID, str);
                            jSONObject.put("poiName", str2);
                            jSONObject.put("status", bnv.d());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_ON_FLOOR_CHANGED, jSONObject);
                    }
                }
                bdx bdx = (bdx) bdz;
                beh.this.e.a((a<T>) new a<bei>(bdx, (bdx) bdz2) {
                    public final /* synthetic */ void onNotify(Object obj) {
                        ((bei) obj).onFloorChanged(r3, r4);
                    }
                });
                if (bdx != null) {
                    beh.this.a(beh.this.b.e, bdx.b, bdx.a);
                }
            }
        }

        public final void a(boolean z, boolean z2) {
            beh beh = beh.this;
            beh.e.a((a<T>) new a<bei>(z, z2) {
                final /* synthetic */ boolean a;
                final /* synthetic */ boolean b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public final /* synthetic */ void onNotify(Object obj) {
                    ((bei) obj).onFloorWidgetVisibleChanged(this.a, this.b);
                }
            });
        }
    };
    private beg m = new beg() {
        public final void a() {
            if (!agr.b(beh.this.a.a())) {
                ToastHelper.showToast(beh.this.a.a().getString(R.string.net_error_message));
                return;
            }
            String str = "indoor.html";
            bty mapView = DoNotUseTool.getMapView();
            final boolean z = false;
            try {
                ami ami = beh.this.b;
                if (ami != null) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("status", bnv.d());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (a.b(ami.e)) {
                        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_TRAIN_STATION_GUIDE_BTN_CLICK, jSONObject);
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, ami.e);
                        jSONObject2.put("poiname", ami.a);
                        jSONObject2.put(AutoJsonUtils.JSON_ADCODE, mapView != null ? Integer.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getAdCode()) : "");
                        new blh();
                        blh.a((String) "railwayIndoor", jSONObject2.toString());
                        str = "railwayIndoor.html";
                    } else {
                        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_GUIDE_BTN_CLICK, jSONObject);
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, ami.e);
                        jSONObject3.put("poiname", ami.a);
                        new blh();
                        blh.a((String) "indoorGuide", jSONObject3.toString());
                        z = true;
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (ConfigerHelper.getInstance().isLoadPoiPageFromInternet()) {
                str = "http://tpl.testing.amap.com/and/".concat(String.valueOf(str));
            } else {
                bgx bgx = (bgx) a.a.a(bgx.class);
                if (bgx != null) {
                    str = bgx.getUrl(str);
                }
            }
            aja aja = new aja(str);
            aja.b = new ajf() {
                public final boolean f() {
                    return true;
                }

                public final boolean g() {
                    return z;
                }
            };
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a(AMapPageUtil.getPageContext(), aja);
            }
            a.k();
        }

        public final void a(boolean z) {
            if (z && beh.this.d != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("status", bnv.d());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (beh.this.d.isTrainStation()) {
                    LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_TRAIN_STATION_GUIDE_BTN_VISIBLE, jSONObject);
                    return;
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_GUIDE_BTN_VISIBLE, jSONObject);
            }
        }
    };

    public beh(beb beb) {
        this.a = beb;
    }

    public final void c() {
        this.h = null;
        this.i = null;
        this.b = null;
        this.j = null;
        this.k = null;
        f();
        g();
        this.c = null;
        this.d = null;
        agl<bei> agl = this.e;
        synchronized (agl.b) {
            agl.a.clear();
        }
        this.f = null;
    }

    public final void a(ami ami) {
        this.i = this.b;
        if (this.b != null) {
            this.h = this.b;
        }
        this.b = ami;
        if (this.b != null && this.j != null && !TextUtils.isEmpty(this.b.e) && !TextUtils.isEmpty(this.j.e) && this.b.e.equals(this.j.e) && this.b.d != this.j.d) {
            a(this.j.e, this.j.d, this.j.c);
        }
        if (a(this.b, this.i)) {
            aho.b(this.g);
        }
        if (this.b != null) {
            if (this.j == null || a(ami, this.j)) {
                this.j = ami;
            }
            boolean a2 = a(ami, this.i);
            if (a2) {
                String str = ami.e;
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(TrafficUtil.POIID, str);
                    jSONObject.put("status", bnv.d());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B050", jSONObject);
            }
            if (a(this.b, this.k)) {
                b(this.b);
                c(this.b);
            } else if (this.c != null) {
                bdz currentFloor = this.c.getCurrentFloor();
                if (currentFloor == null || currentFloor.b() != this.b.d) {
                    this.c.setCurrentFloorByNumber(this.b.d);
                } else if (a2) {
                    this.c.resetPosByCurrentFloor();
                }
            }
        }
        if (a(this.b, this.i)) {
            this.e.a((a<T>) new a<bei>() {
                public final /* synthetic */ void onNotify(Object obj) {
                    ((bei) obj).onIndoorChanged(beh.this.b != null, beh.this.b);
                }
            });
        }
        d();
        e();
    }

    /* access modifiers changed from: 0000 */
    public final void d() {
        if (this.c != null) {
            ami ami = this.b;
            bty mapView = DoNotUseTool.getMapView();
            this.c.setVisible((mapView == null || ami == null || mapView.J() != 0.0f) ? false : true);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        if (this.d != null) {
            ami ami = this.b;
            boolean z = false;
            boolean z2 = ami != null && a.b(ami.e);
            boolean z3 = ami != null && a.a(ami.f);
            bty mapView = DoNotUseTool.getMapView();
            if (!(mapView == null || ami == null || ((!z2 && !z3) || mapView.J() != 0.0f))) {
                z = true;
            }
            this.d.setVisible(z);
        }
    }

    public final void a(bed bed) {
        if (bed != this.c) {
            f();
            this.c = bed;
            if (this.c != null) {
                this.c.addListener(this.l);
                b(this.b);
                d();
            }
        }
    }

    private void f() {
        if (this.c != null) {
            this.c.removeListener(this.l);
        }
    }

    public final void a(bef bef) {
        if (bef != this.d) {
            g();
            this.d = bef;
            if (this.d != null) {
                this.d.addListener(this.m);
                c(this.b);
                e();
            }
        }
    }

    private void g() {
        if (this.d != null) {
            this.d.removeListener(this.m);
        }
    }

    public final void a(String str, int i2, String str2) {
        if (this.b != null && !TextUtils.isEmpty(this.b.e) && this.b.e.equals(str)) {
            this.b.d = i2;
            if (TextUtils.isEmpty(str2)) {
                bdx a2 = a(i2);
                str2 = a2 != null ? a2.a : "";
            }
            this.b.c = str2;
            bty mapView = DoNotUseTool.getMapView();
            if (mapView != null) {
                mapView.a(str2, i2, str);
            }
            b(str, i2, str2);
        }
    }

    private bdx a(int i2) {
        if (this.c != null) {
            return (bdx) this.c.getFloorByNumber(i2);
        }
        return null;
    }

    public final void a(bei bei) {
        this.e.a(bei);
    }

    public final void b(bei bei) {
        this.e.b(bei);
    }

    public final ami a() {
        return this.b;
    }

    public final boolean b() {
        return this.b != null;
    }

    private void b(String str, int i2, String str2) {
        if (this.j != null && !TextUtils.isEmpty(this.j.e) && this.j.e.equals(str)) {
            this.j.d = i2;
            this.j.c = str2;
        }
    }

    private static boolean a(ami ami, ami ami2) {
        String str = null;
        String str2 = ami != null ? ami.e : null;
        if (ami2 != null) {
            str = ami2.e;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        return !str2.equals(str);
    }

    private void b(ami ami) {
        if (this.c != null && ami != null) {
            this.k = ami;
            String[] strArr = ami.i;
            int[] iArr = ami.h;
            int i2 = ami.d;
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < strArr.length; i3++) {
                bdx bdx = new bdx();
                bdx.a = strArr[i3];
                bdx.b = iArr[i3];
                arrayList.add(bdx);
            }
            Collections.sort(arrayList, new bdy());
            this.c.setListData(arrayList);
            this.c.setCurrentFloorByNumber(i2);
            h();
        }
    }

    private void h() {
        aho.b(this.g);
        aho.a(this.g, 200);
    }

    private void c(ami ami) {
        if (this.d != null && ami != null) {
            this.d.setIsTrainStation(a.b(ami.e));
        }
    }

    public final void paintCompass() {
        final bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            float J = mapView.J();
            if (this.f == null || ((this.f.floatValue() == 0.0f && J > 0.0f) || (this.f.floatValue() > 0.0f && J == 0.0f))) {
                aho.a(new Runnable() {
                    public final void run() {
                        float J = mapView.J();
                        if (beh.this.f == null || ((beh.this.f.floatValue() == 0.0f && J > 0.0f) || (beh.this.f.floatValue() > 0.0f && J == 0.0f))) {
                            beh.this.f = Float.valueOf(J);
                            beh.this.d();
                            beh.this.e();
                        }
                    }
                });
            }
        }
    }
}
