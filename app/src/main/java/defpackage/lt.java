package defpackage;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import com.amap.bundle.cloudconfig.appinit.request.AppSwitchCallback;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.component.mergerequest.MergeRequest;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

/* renamed from: lt reason: default package */
/* compiled from: AppInitService */
public class lt {
    public static final String a;
    private static volatile lt e;
    public JSONObject b;
    public ls c = new ls();
    public lu d = new lu();
    private MapSharePreference f = new MapSharePreference((String) "app_init");
    private String g = this.f.getStringValue("app_init_config", null);
    private String h = this.f.getStringValue("app_init_switch_config", null);
    private JSONObject i;
    private agl<a> j = new agl<>();

    /* renamed from: lt$a */
    /* compiled from: AppInitService */
    public interface a {
        void a(JSONObject jSONObject);
    }

    public static void b() {
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(aaf.b(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/shield/dsp/app/startup/init");
        a = sb.toString();
    }

    public static lt a() {
        if (e == null) {
            synchronized (lt.class) {
                if (e == null) {
                    e = new lt();
                }
            }
        }
        return e;
    }

    private lt() {
        a(this.g, true);
        b(this.h, true);
    }

    public static void a(String str, String str2, String str3, String str4) {
        zz zzVar = new zz();
        MergeRequest mergeRequest = new MergeRequest();
        mergeRequest.setUrl(a);
        mergeRequest.setKey("app_conf_switch");
        mergeRequest.setPath("ws/mapapi/conf/switch/?");
        mergeRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        mergeRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        mergeRequest.addReqParam("latitude", str);
        mergeRequest.addReqParam("longitude", str2);
        zzVar.a(mergeRequest, new AppSwitchCallback());
        MergeRequest mergeRequest2 = new MergeRequest();
        mergeRequest2.setUrl(a);
        mergeRequest2.setKey("app_conf_init");
        mergeRequest2.setPath("ws/mapapi/conf/init/?");
        mergeRequest2.addSignParam(LocationParams.PARA_COMMON_DIU);
        mergeRequest2.addSignParam(LocationParams.PARA_COMMON_DIV);
        FunctionSupportConfiger inst = FunctionSupportConfiger.getInst();
        String version = inst.getVersion(FunctionSupportConfiger.ROUTE_BUS_TAG);
        String version2 = inst.getVersion(FunctionSupportConfiger.TAXI_TAG);
        DisplayMetrics displayMetrics = AMapAppGlobal.getApplication().getResources().getDisplayMetrics();
        if (displayMetrics.densityDpi <= 240) {
            mergeRequest2.addReqParam("guide", "1");
        } else if (displayMetrics.densityDpi <= 320) {
            mergeRequest2.addReqParam("guide", "2");
        } else {
            mergeRequest2.addReqParam("guide", "3");
        }
        mergeRequest2.addReqParam("type", "auto");
        mergeRequest2.addReqParam("taxi_servicever", version2);
        mergeRequest2.addReqParam("realtime_busver", version);
        mergeRequest2.addReqParam("latitude", str);
        mergeRequest2.addReqParam("longitude", str2);
        mergeRequest2.addReqParam("build", str3);
        mergeRequest2.addReqParam("newmapflag", "0x1");
        mergeRequest2.addReqParam("schemever", str4);
        mergeRequest2.addReqParam("appver", String.valueOf(defpackage.ahp.a.a().b));
        mergeRequest2.addReqParam("product", "4");
        zzVar.a(mergeRequest2, new AppInitCallback());
        zzVar.a();
    }

    public final boolean a(String str, final boolean z) {
        new Object[1][0] = Boolean.valueOf(z);
        if (!TextUtils.isEmpty(str)) {
            final AtomicReference atomicReference = new AtomicReference();
            if (this.c.a(str, atomicReference, Boolean.valueOf(z))) {
                if (!z) {
                    this.g = str;
                    this.f.putStringValue("app_init_config", str);
                }
                this.b = (JSONObject) atomicReference.get();
                this.j.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                    public final /* synthetic */ void onNotify(Object obj) {
                        ((a) obj).a((JSONObject) atomicReference.get());
                    }
                });
                return true;
            }
        }
        return false;
    }

    public final boolean b(String str, final boolean z) {
        new Object[1][0] = Boolean.valueOf(z);
        if (!TextUtils.isEmpty(str)) {
            final AtomicReference atomicReference = new AtomicReference();
            if (this.d.a(str, atomicReference, Boolean.valueOf(z))) {
                if (!z) {
                    this.h = str;
                    this.f.putStringValue("app_init_switch_config", str);
                }
                this.i = (JSONObject) atomicReference.get();
                this.j.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                    public final /* synthetic */ void onNotify(Object obj) {
                        atomicReference.get();
                    }
                });
                return true;
            }
        }
        return false;
    }

    public final void a(a aVar) {
        this.j.a(aVar);
    }

    public final void b(a aVar) {
        this.j.b(aVar);
    }

    public final boolean c() {
        ls lsVar = this.c;
        if (lsVar.g != null) {
            return lsVar.g.booleanValue();
        }
        return false;
    }

    public final boolean d() {
        ls lsVar = this.c;
        if (lsVar.s != null) {
            return lsVar.s.booleanValue();
        }
        return false;
    }

    public final boolean e() {
        ls lsVar = this.c;
        if (lsVar.t != null) {
            return lsVar.t.booleanValue();
        }
        return true;
    }

    public final String a(String str) {
        ls lsVar = this.c;
        return lsVar.u != null ? lsVar.u : str;
    }

    public final boolean f() {
        lu luVar = this.d;
        if (luVar.e != null) {
            return luVar.e.booleanValue();
        }
        return true;
    }

    public final boolean g() {
        lu luVar = this.d;
        if (luVar.f != null) {
            return luVar.f.booleanValue();
        }
        return true;
    }

    public final String b(String str) {
        lu luVar = this.d;
        return luVar.h != null ? luVar.h : str;
    }
}
