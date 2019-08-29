package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherAnimationState;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType;
import com.autonavi.annotation.MainMapFeature;
import com.autonavi.bundle.uitemplate.container.SlideContainer.b;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.sdk.log.util.LogConstant;
import de.greenrobot.event.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

@MainMapFeature
/* renamed from: csw reason: default package */
/* compiled from: WeatherManager */
public class csw implements czs, czu, czz {
    b a;
    private boolean b;
    private Runnable c;
    private OnTouchListener d;
    private View e;
    private boolean f;
    private WeatherType g;
    private boolean h;

    public final void j_() {
    }

    public void onCreate() {
        EventBus.getDefault().register(this);
        lo.a().a((String) "map_weather_switch", (lp) new lp() {
            public final void onConfigCallBack(int i) {
            }

            public final void onConfigResultCallBack(int i, String str) {
                if (i == 3) {
                    new MapSharePreference((String) "weather_effect_sp").remove("weather_cloud_config");
                    return;
                }
                if (!TextUtils.isEmpty(str)) {
                    new MapSharePreference((String) "weather_effect_sp").putStringValue("weather_cloud_config", str);
                }
            }
        });
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public ON_BACK_TYPE onBackPressed() {
        if (this.b && a(false)) {
            a((String) "2");
        }
        return ON_BACK_TYPE.TYPE_NORMAL;
    }

    public csw() {
        this.f = true;
        this.g = null;
        this.h = false;
        this.a = new b() {
            public final void a() {
                csw.b(csw.this);
            }
        };
        this.h = true ^ new bnv().a();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0041  */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onEventMainThread(defpackage.csy r8) {
        /*
            r7 = this;
            de.greenrobot.event.EventBus r0 = de.greenrobot.event.EventBus.getDefault()
            r0.unregister(r7)
            com.autonavi.minimap.basemap.weather.net.entity.WeatherResponse r0 = r8.a
            boolean r8 = r8.b
            if (r8 == 0) goto L_0x00e2
            if (r0 == 0) goto L_0x00e2
            java.lang.String r8 = r0.m
            if (r8 == 0) goto L_0x00e2
            java.lang.String r8 = r0.a
            if (r8 != 0) goto L_0x001b
            java.lang.String r8 = r0.l
            if (r8 == 0) goto L_0x00e2
        L_0x001b:
            boolean r8 = r7.f
            if (r8 == 0) goto L_0x00e1
            boolean r8 = r7.b
            if (r8 == 0) goto L_0x0025
            goto L_0x00e1
        L_0x0025:
            r8 = 1
            r7.b = r8
            java.lang.Class<com.autonavi.minimap.bundle.maphome.service.IMainMapService> r1 = com.autonavi.minimap.bundle.maphome.service.IMainMapService.class
            java.lang.Object r1 = defpackage.ank.a(r1)
            com.autonavi.minimap.bundle.maphome.service.IMainMapService r1 = (com.autonavi.minimap.bundle.maphome.service.IMainMapService) r1
            r2 = 0
            if (r1 == 0) goto L_0x003e
            bid r1 = r1.e()
            if (r1 == 0) goto L_0x003e
            android.content.Context r1 = r1.getContext()
            goto L_0x003f
        L_0x003e:
            r1 = r2
        L_0x003f:
            if (r1 == 0) goto L_0x00e2
            com.autonavi.ae.gmap.utils.GLMapStaticValue$WeatherType r3 = a(r0)
            if (r3 == 0) goto L_0x00e2
            boolean r4 = r7.h
            r5 = 0
            if (r4 == 0) goto L_0x007d
            android.view.ViewGroup r4 = d()
            if (r4 == 0) goto L_0x00a6
            android.widget.RelativeLayout r5 = new android.widget.RelativeLayout
            r5.<init>(r1)
            r7.e = r5
            android.widget.RelativeLayout$LayoutParams r5 = new android.widget.RelativeLayout$LayoutParams
            r6 = -1
            r5.<init>(r6, r6)
            android.view.View r6 = r7.e
            r4.addView(r6, r5)
            android.view.View r4 = r7.e
            r4.bringToFront()
            android.view.View$OnTouchListener r4 = r7.d
            if (r4 != 0) goto L_0x0074
            csw$3 r4 = new csw$3
            r4.<init>()
            r7.d = r4
        L_0x0074:
            android.view.View r4 = r7.e
            android.view.View$OnTouchListener r5 = r7.d
            r4.setOnTouchListener(r5)
            r5 = 1
            goto L_0x00a6
        L_0x007d:
            java.lang.Class<com.autonavi.minimap.bundle.maphome.service.IMainMapService> r8 = com.autonavi.minimap.bundle.maphome.service.IMainMapService.class
            java.lang.Object r8 = defpackage.ank.a(r8)
            com.autonavi.minimap.bundle.maphome.service.IMainMapService r8 = (com.autonavi.minimap.bundle.maphome.service.IMainMapService) r8
            if (r8 == 0) goto L_0x00a6
            java.lang.Class<czh> r4 = defpackage.czh.class
            czi r8 = r8.a(r4)
            czh r8 = (defpackage.czh) r8
            boolean r4 = r8 instanceof defpackage.aqe
            if (r4 == 0) goto L_0x00a6
            aqe r8 = (defpackage.aqe) r8
            com.autonavi.bundle.uitemplate.container.SlideContainer$b r4 = r7.a
            java.lang.ref.WeakReference<arn> r8 = r8.a
            java.lang.Object r8 = r8.get()
            arn r8 = (defpackage.arn) r8
            if (r8 == 0) goto L_0x00a6
            boolean r8 = r8.a(r4)
            r5 = r8
        L_0x00a6:
            if (r5 == 0) goto L_0x00e2
            bty r8 = c()
            if (r8 == 0) goto L_0x00ce
            com.autonavi.ae.gmap.utils.GLMapStaticValue$WeatherType r4 = com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType.FROG
            if (r3 != r4) goto L_0x00c9
            com.autonavi.minimap.basemap.weather.view.WeatherSmogView r2 = new com.autonavi.minimap.basemap.weather.view.WeatherSmogView
            r2.<init>(r1)
            android.view.ViewGroup$LayoutParams r1 = new android.view.ViewGroup$LayoutParams
            r4 = -2
            r1.<init>(r4, r4)
            r2.setLayoutParams(r1)
            java.lang.String r0 = r0.n
            r2.setAqiValue(r0)
            android.graphics.Bitmap r2 = com.autonavi.minimap.map.overlayholder.OverlayUtil.convertViewToBitmap(r2)
        L_0x00c9:
            r8.a(r3, r2)
            r7.g = r3
        L_0x00ce:
            java.lang.Runnable r8 = r7.c
            if (r8 != 0) goto L_0x00d9
            csw$2 r8 = new csw$2
            r8.<init>()
            r7.c = r8
        L_0x00d9:
            java.lang.Runnable r8 = r7.c
            r0 = 5200(0x1450, double:2.569E-320)
            defpackage.aho.a(r8, r0)
            goto L_0x00e2
        L_0x00e1:
            return
        L_0x00e2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.csw.onEventMainThread(csy):void");
    }

    public final void k_() {
        this.f = false;
        if (this.b) {
            aho.b(this.c);
            if (a(true)) {
                a((String) "3");
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean a(boolean z) {
        if (!this.b) {
            return false;
        }
        bty c2 = c();
        if (c2 != null) {
            WeatherAnimationState Z = c2.Z();
            this.b = false;
            if (this.c != null) {
                aho.b(this.c);
            }
            e();
            if (Z == WeatherAnimationState.FADE_IN || Z == WeatherAnimationState.RUNNNING || Z == WeatherAnimationState.FADE_OUT) {
                c2.n(z);
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x008e A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00b8 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType a(com.autonavi.minimap.basemap.weather.net.entity.WeatherResponse r10) {
        /*
            boolean r0 = defpackage.bno.a
            r1 = 0
            if (r0 == 0) goto L_0x0030
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r2 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r0.<init>(r2)
            java.lang.String r2 = "weather_effect_open"
            boolean r2 = r0.getBooleanValue(r2, r1)
            if (r2 == 0) goto L_0x0030
            java.lang.String r10 = "weather_last_status"
            int r10 = r0.getIntValue(r10, r1)
            int r1 = r10 % 3
            r2 = 1
            if (r1 != 0) goto L_0x0022
            com.autonavi.ae.gmap.utils.GLMapStaticValue$WeatherType r1 = com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType.RAIN
            goto L_0x0029
        L_0x0022:
            if (r1 != r2) goto L_0x0027
            com.autonavi.ae.gmap.utils.GLMapStaticValue$WeatherType r1 = com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType.SNOW
            goto L_0x0029
        L_0x0027:
            com.autonavi.ae.gmap.utils.GLMapStaticValue$WeatherType r1 = com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType.FROG
        L_0x0029:
            java.lang.String r3 = "weather_last_status"
            int r10 = r10 + r2
            r0.putIntValue(r3, r10)
            return r1
        L_0x0030:
            lo r0 = defpackage.lo.a()
            java.lang.String r2 = "map_weather_switch"
            java.lang.String r0 = r0.a(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L_0x004f
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r2 = "weather_effect_sp"
            r0.<init>(r2)
            java.lang.String r2 = "weather_cloud_config"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.getStringValue(r2, r3)
        L_0x004f:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            r3 = 0
            if (r2 == 0) goto L_0x0057
            return r3
        L_0x0057:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0086 }
            r2.<init>(r0)     // Catch:{ JSONException -> 0x0086 }
            java.lang.String r0 = "1"
            java.lang.String r4 = "rain"
            java.lang.String r4 = r2.optString(r4)     // Catch:{ JSONException -> 0x0086 }
            boolean r0 = r0.equals(r4)     // Catch:{ JSONException -> 0x0086 }
            java.lang.String r4 = "1"
            java.lang.String r5 = "snow"
            java.lang.String r5 = r2.optString(r5)     // Catch:{ JSONException -> 0x0084 }
            boolean r4 = r4.equals(r5)     // Catch:{ JSONException -> 0x0084 }
            java.lang.String r5 = "1"
            java.lang.String r6 = "fog"
            java.lang.String r2 = r2.optString(r6)     // Catch:{ JSONException -> 0x0082 }
            boolean r2 = r5.equals(r2)     // Catch:{ JSONException -> 0x0082 }
            r1 = r2
            goto L_0x008c
        L_0x0082:
            r2 = move-exception
            goto L_0x0089
        L_0x0084:
            r2 = move-exception
            goto L_0x0088
        L_0x0086:
            r2 = move-exception
            r0 = 0
        L_0x0088:
            r4 = 0
        L_0x0089:
            r2.printStackTrace()
        L_0x008c:
            if (r0 != 0) goto L_0x0093
            if (r4 != 0) goto L_0x0093
            if (r1 != 0) goto L_0x0093
            return r3
        L_0x0093:
            if (r10 == 0) goto L_0x0116
            org.json.JSONObject r2 = r10.o
            if (r2 != 0) goto L_0x009b
            goto L_0x0116
        L_0x009b:
            com.amap.bundle.mapstorage.MapSharePreference r2 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r5 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r2.<init>(r5)
            org.json.JSONObject r5 = r10.o
            java.lang.String r6 = "state"
            java.lang.String r5 = r5.optString(r6)
            org.json.JSONObject r10 = r10.o
            java.lang.String r6 = "time"
            org.json.JSONObject r10 = r10.optJSONObject(r6)
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x0115
            if (r10 != 0) goto L_0x00bb
            goto L_0x0115
        L_0x00bb:
            if (r0 == 0) goto L_0x00d0
            java.lang.String r0 = "rain"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x00d0
            java.lang.String r0 = "weather_last_rain_ts"
            java.lang.String r1 = "rain"
            int r10 = r10.optInt(r1)
            com.autonavi.ae.gmap.utils.GLMapStaticValue$WeatherType r1 = com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType.RAIN
            goto L_0x00f9
        L_0x00d0:
            if (r4 == 0) goto L_0x00e5
            java.lang.String r0 = "snow"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x00e5
            java.lang.String r0 = "weather_last_snow_ts"
            java.lang.String r1 = "snow"
            int r10 = r10.optInt(r1)
            com.autonavi.ae.gmap.utils.GLMapStaticValue$WeatherType r1 = com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType.SNOW
            goto L_0x00f9
        L_0x00e5:
            if (r1 == 0) goto L_0x0114
            java.lang.String r0 = "fog"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x0114
            java.lang.String r0 = "weather_last_fog_ts"
            java.lang.String r1 = "fog"
            int r10 = r10.optInt(r1)
            com.autonavi.ae.gmap.utils.GLMapStaticValue$WeatherType r1 = com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType.FROG
        L_0x00f9:
            long r4 = java.lang.System.currentTimeMillis()
            r6 = 0
            long r6 = r2.getLongValue(r0, r6)
            int r10 = r10 * 60
            int r10 = r10 * 60
            int r10 = r10 * 1000
            long r8 = (long) r10
            long r6 = r6 + r8
            int r10 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r10 >= 0) goto L_0x0113
            r2.putLongValue(r0, r4)
            return r1
        L_0x0113:
            return r3
        L_0x0114:
            return r3
        L_0x0115:
            return r3
        L_0x0116:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.csw.a(com.autonavi.minimap.basemap.weather.net.entity.WeatherResponse):com.autonavi.ae.gmap.utils.GLMapStaticValue$WeatherType");
    }

    private static bty c() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService == null) {
            return null;
        }
        return iMainMapService.g();
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if (!TextUtils.isEmpty(str) && this.g != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("action", str);
                jSONObject.put("type", String.valueOf(this.g.value()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00001", LogConstant.MAIN_WEATHER_EFFECT_STOP, jSONObject);
        }
    }

    private static ViewGroup d() {
        czh czh = (czh) ((IMainMapService) ank.a(IMainMapService.class)).a(czh.class);
        if (czh != null) {
            return (ViewGroup) czh.b();
        }
        return null;
    }

    private void e() {
        if (!this.h) {
            IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
            if (iMainMapService != null) {
                czh czh = (czh) iMainMapService.a(czh.class);
                if (czh instanceof aqe) {
                    ((aqe) czh).a(this.a);
                }
            }
        } else if (this.e != null) {
            this.e.setOnTouchListener(null);
            this.e.setVisibility(8);
            ViewGroup d2 = d();
            if (d2 != null) {
                d2.removeView(this.e);
            }
        }
    }

    static /* synthetic */ void b(csw csw) {
        if (csw.b && csw.a(false)) {
            csw.a((String) "2");
        }
    }
}
