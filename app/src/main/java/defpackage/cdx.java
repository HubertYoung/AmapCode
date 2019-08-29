package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.maps.MapViewManager;
import com.autonavi.jni.eyrie.amap.tbt.CustomTextureObserver;
import com.autonavi.jni.eyrie.amap.tbt.NaviEventReceiver;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tbt.TextureLoader;
import com.autonavi.jni.eyrie.amap.tbt.TextureParam;
import com.autonavi.jni.eyrie.amap.tbt.TextureWrapper;
import com.autonavi.jni.eyrie.amap.tbt.scene.basemap.NaviBasemapMain;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cdx reason: default package */
/* compiled from: GpsLayer */
public final class cdx implements btt, xo {
    private static a n = new a();
    public NaviBasemapMain a;
    public int b = 0;
    public int c = -1;
    public boolean d = true;
    public a e = n;
    public NaviEventReceiver f = new NaviEventReceiver() {
        public final void onNaviEvent(String str) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    final JSONObject jSONObject = new JSONObject(str);
                    aho.a(new Runnable() {
                        public final void run() {
                            if (jSONObject.optInt("eventType") == 9010) {
                                cdx.a(cdx.this, jSONObject);
                                return;
                            }
                            if (jSONObject.optInt("eventType") == 9012) {
                                cdx.this.k = false;
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public TextureLoader g = new TextureLoader() {
        public final boolean loadCustomTextureData(int i, TextureParam textureParam, CustomTextureObserver customTextureObserver) {
            return false;
        }

        public final boolean loadTextureData(int i, TextureParam textureParam, TextureWrapper textureWrapper) {
            a b = cdx.this.e;
            int i2 = textureParam.resId;
            int intValue = b.a.containsKey(Integer.valueOf(i2)) ? b.a.get(Integer.valueOf(i2)).intValue() : -1;
            if (intValue < 0) {
                return false;
            }
            textureWrapper.setData(2, cdx.c(intValue));
            return true;
        }
    };
    private boolean h = true;
    private boolean i;
    private boolean j;
    /* access modifiers changed from: private */
    public boolean k = true;
    private int l = -1;
    private int m = -1;
    private int o = 0;

    /* renamed from: cdx$a */
    /* compiled from: GpsLayer */
    public static class a {
        Map<Integer, Integer> a = new HashMap();
        private b b = new b(210130, R.drawable.navi_map_flash);
        private b c = new b(210131, R.drawable.navi_map_flash_grey);
        private b d = new b(210100, R.drawable.marker_gps_no_sensor);
        private b e = new b(210101, R.drawable.navi_map_gps_locked);
        private b f = new b(210102, R.drawable.navi_map_gps_3d);
        private b g = new b(210103, R.drawable.marker_gps_no_sensor_grey);
        private b h = new b(210104, R.drawable.navi_map_gps_locked_grey);
        private b i = new b(210105, R.drawable.navi_map_gps_3d_grey);

        public a() {
            this.a.put(Integer.valueOf(a().a), Integer.valueOf(a().b));
            this.a.put(Integer.valueOf(b().a), Integer.valueOf(b().b));
            this.a.put(Integer.valueOf(c().a), Integer.valueOf(c().b));
            this.a.put(Integer.valueOf(d().a), Integer.valueOf(d().b));
            this.a.put(Integer.valueOf(e().a), Integer.valueOf(e().b));
            this.a.put(Integer.valueOf(f().a), Integer.valueOf(f().b));
            this.a.put(Integer.valueOf(g().a), Integer.valueOf(g().b));
            this.a.put(Integer.valueOf(h().a), Integer.valueOf(h().b));
        }

        public b a() {
            return this.b;
        }

        public b b() {
            return this.c;
        }

        public b c() {
            return this.d;
        }

        public b d() {
            return this.e;
        }

        public b e() {
            return this.f;
        }

        public b f() {
            return this.g;
        }

        public b g() {
            return this.h;
        }

        public b h() {
            return this.i;
        }
    }

    /* renamed from: cdx$b */
    /* compiled from: GpsLayer */
    public static class b {
        int a;
        int b;

        public b(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }

    public final void setClickable(boolean z) {
    }

    public cdx() {
        MapViewManager.addTextureLoader(this.g, 80);
        xp xpVar = (xp) defpackage.esb.a.a.a(xp.class);
        if (xpVar != null) {
            this.a = xpVar.d();
        }
        this.i = agq.c(AMapAppGlobal.getApplication());
        this.j = ahr.b();
        a();
        NaviManager.registerEventReceiver(this.f);
        setVisible(true);
    }

    public final void a() {
        int i2 = 5;
        if (this.i || this.j) {
            int i3 = 3;
            if (this.j) {
                if (this.b == 1) {
                    if (this.h) {
                        i3 = 2;
                    }
                    d(i3);
                    return;
                }
                d(this.h ? 1 : 0);
            } else if (!this.k || !LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS)) {
                if (this.h) {
                    i2 = 4;
                }
                d(i2);
            } else if (this.b == 1) {
                if (this.h) {
                    i3 = 2;
                }
                d(i3);
            } else {
                d(this.h ? 1 : 0);
            }
        } else {
            if (this.h) {
                i2 = 4;
            }
            d(i2);
        }
    }

    public final void setVisible(boolean z) {
        if (z) {
            this.a.sendCommand(102200);
        } else {
            this.a.sendCommand(102201);
        }
        this.d = z;
    }

    public final void a(int i2) {
        int i3 = 1;
        if (i2 != 1) {
            i3 = 0;
        }
        this.b = i2;
        a();
        a(this.c, i3);
    }

    private void d(int i2) {
        int i3;
        int i4 = 0;
        switch (i2) {
            case 0:
                i4 = this.e.b().a;
                i3 = this.e.g().a;
                break;
            case 1:
                i4 = this.e.a().a;
                i3 = this.e.d().a;
                break;
            case 2:
                i4 = this.e.a().a;
                i3 = this.e.e().a;
                break;
            case 3:
                i4 = this.e.b().a;
                i3 = this.e.h().a;
                break;
            case 4:
                i4 = this.e.a().a;
                i3 = this.e.c().a;
                break;
            case 5:
                i4 = this.e.a().a;
                i3 = this.e.f().a;
                break;
            default:
                i3 = 0;
                break;
        }
        if (this.l != i4) {
            this.l = i4;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("resID", i4);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            this.a.sendCommand(102205, jSONObject.toString());
        }
        if (this.m != i3) {
            this.m = i3;
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("resID", i3);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            this.a.sendCommand(102204, jSONObject2.toString());
        }
    }

    public final void a(boolean z) {
        this.o++;
        if (this.c != z || this.o % 10 == 0) {
            int i2 = 0;
            this.o = 0;
            this.c = z ? 1 : 0;
            if (this.b != 0) {
                i2 = 1;
            }
            a((int) z, i2);
        }
    }

    private void a(int i2, int i3) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put("enable", i2);
            jSONObject2.put("enable", i3);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        this.a.sendCommand(102202, jSONObject.toString());
        this.a.sendCommand(102203, jSONObject2.toString());
    }

    public final void b(boolean z) {
        if (this.h != z) {
            this.h = z;
            a();
        }
    }

    public static a c() {
        return n;
    }

    /* access modifiers changed from: private */
    public static byte[] c(int i2) {
        try {
            Bitmap decodeResource = BitmapFactory.decodeResource(AMapAppGlobal.getApplication().getResources(), i2);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            decodeResource.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static cdy b() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
        cdy cdy = new cdy(latestPosition);
        cdy.a = 0;
        cdy.a = (int) latestLocation.getBearing();
        cdy.b = 0;
        if (LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS)) {
            cdy.c = 0;
        } else {
            cdy.c = 1;
        }
        cdy.f = latestLocation.getExtras();
        cdy.d = lh.a(DoNotUseTool.getContext(), R.string.LocationMe);
        return cdy;
    }

    static /* synthetic */ void a(cdx cdx, JSONObject jSONObject) {
        boolean z = false;
        if (jSONObject.optInt("changeType") == 5) {
            if (jSONObject.optInt("newValue") == 1) {
                z = true;
            }
            cdx.d = z;
        } else if (jSONObject.optInt("changeType") == 1) {
            if (jSONObject.optInt("newValue") == 1) {
                z = true;
            }
            cdx.b = z ? 1 : 0;
        } else {
            if (jSONObject.optInt("changeType") == 2) {
                cdx.c = jSONObject.optInt("newValue");
            }
        }
    }
}
