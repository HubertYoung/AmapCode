package defpackage;

import android.content.Context;
import android.os.Build.VERSION;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.minimap.uiperformance.page.UIPerformanceConfigPage;
import java.util.ArrayList;
import java.util.List;

/* renamed from: eml reason: default package */
/* compiled from: SamplerIntercepter */
public final class eml implements a {
    public List<emi> a = new ArrayList();
    public emh b;
    public emg c;
    public emk d;
    public emj e;
    public emq f;

    public eml(emq emq, Context context) {
        this.f = emq;
        if (VERSION.SDK_INT >= 16) {
            this.b = new emh(emq);
        }
        this.d = new emk(emq, context);
        this.c = new emg(emq);
        this.e = new emj(emq);
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        if (mapSharePreference.getBooleanValue(UIPerformanceConfigPage.SP_KEY_CPU, true)) {
            this.a.add(this.c);
        }
        if (mapSharePreference.getBooleanValue(UIPerformanceConfigPage.SP_KEY_FPS, true)) {
            this.a.add(this.b);
        }
        if (mapSharePreference.getBooleanValue(UIPerformanceConfigPage.SP_KEY_MAP_FPS, true)) {
            this.a.add(this.e);
        }
        if (mapSharePreference.getBooleanValue(UIPerformanceConfigPage.SP_KEY_MEM, true)) {
            this.a.add(this.d);
        }
    }

    public final void a() {
        if (this.a != null) {
            for (emi a2 : this.a) {
                a2.a();
            }
        }
    }
}
