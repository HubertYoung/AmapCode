package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.bicycle.param.BicycleStatusCmd1Param;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.model.CityInfo;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest;
import com.autonavi.minimap.route.sharebike.net.request.CityInfoBicycleStatusRequest;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: ehr reason: default package */
/* compiled from: ShareBikeCpSource */
public final class ehr {
    /* access modifiers changed from: private */
    public static ehr a = new ehr();
    private final Object b = new Object();
    private a c;

    /* renamed from: ehr$a */
    /* compiled from: ShareBikeCpSource */
    public static class a {
        public List<egr> a;
        long b;
        String c;
        public String d;
        String e;
        public JSONArray f;

        /* synthetic */ a(String str, byte b2) {
            this(str);
        }

        private a(String str) {
            this.d = "";
            this.e = "";
            this.c = str;
        }

        public final String toString() {
            JSONArray jSONArray;
            StringBuilder sb = new StringBuilder();
            sb.append(this.d);
            sb.append(AUScreenAdaptTool.PREFIX_ID);
            sb.append(this.e);
            sb.append(AUScreenAdaptTool.PREFIX_ID);
            sb.append(this.c);
            sb.append(AUScreenAdaptTool.PREFIX_ID);
            sb.append(this.b);
            sb.append(AUScreenAdaptTool.PREFIX_ID);
            if (this.f != null) {
                jSONArray = this.f;
            } else {
                jSONArray = new JSONArray();
            }
            sb.append(jSONArray.toString());
            return sb.toString();
        }

        public final boolean a(String str) {
            try {
                if (TextUtils.isEmpty(str)) {
                    return false;
                }
                String[] split = str.split(AUScreenAdaptTool.PREFIX_ID);
                this.d = split[0];
                this.e = split[1];
                this.c = split[2];
                this.b = Long.parseLong(split[3]);
                this.f = new JSONArray(split[4]);
                String[] split2 = this.d.split("、");
                String[] split3 = this.e.split(",");
                if (split2 == null || split3 == null || split2.length != split3.length) {
                    return false;
                }
                this.a = new ArrayList();
                for (int i = 0; i < split2.length; i++) {
                    egr egr = new egr();
                    egr.b = split2[i];
                    egr.a = split3[i];
                    this.a.add(egr);
                }
                return true;
            } catch (Exception unused) {
                return false;
            }
        }

        public final boolean a(BaseNetResult baseNetResult) {
            try {
                JSONArray jSONArray = new JSONArray();
                if (!(baseNetResult instanceof CityInfo)) {
                    return false;
                }
                this.a = ((CityInfo) baseNetResult).cpInfo;
                if (this.a != null) {
                    for (egr next : this.a) {
                        JSONObject jSONObject = new JSONObject();
                        if (!TextUtils.isEmpty(this.d)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(this.d);
                            sb.append("、");
                            this.d = sb.toString();
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(this.d);
                        sb2.append(next.b);
                        this.d = sb2.toString();
                        jSONObject.put("name", next.b);
                        if (!TextUtils.isEmpty(this.e)) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(this.e);
                            sb3.append(",");
                            this.e = sb3.toString();
                        }
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(this.e);
                        sb4.append(next.a);
                        this.e = sb4.toString();
                        jSONObject.put("source", next.a);
                        jSONArray.put(jSONObject);
                    }
                }
                this.f = jSONArray;
                this.b = System.currentTimeMillis();
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }
    }

    /* renamed from: ehr$b */
    /* compiled from: ShareBikeCpSource */
    public interface b {
        void a(a aVar);
    }

    public static void a(GeoPoint geoPoint, final b bVar) {
        a aVar;
        eao.a((String) "update cpsource", "call getCpSource ".concat(String.valueOf(bVar)));
        ehr ehr = a;
        synchronized (ehr.b) {
            if (ehr.c == null) {
                String b2 = ehs.b("share_bike_cp_source_list_new");
                if (!TextUtils.isEmpty(b2)) {
                    a aVar2 = new a("", 0);
                    if (aVar2.a(b2)) {
                        synchronized (ehr.b) {
                            ehr.c = aVar2;
                        }
                        eao.a((String) "update cpsource", "loaded cache ".concat(String.valueOf(aVar2)));
                    } else {
                        ehr.b();
                    }
                }
            }
        }
        if (geoPoint == null) {
            geoPoint = LocationInstrument.getInstance().getLatestPosition();
        }
        if (geoPoint == null) {
            b(null, bVar);
        } else if (!a.a(geoPoint)) {
            synchronized (a.b) {
                aVar = a.c;
            }
            eao.a((String) "update cpsource", (String) "return cache");
            b(aVar, bVar);
        } else {
            ehr ehr2 = a;
            final String b3 = b(geoPoint);
            if (TextUtils.isEmpty(b3)) {
                b(null, bVar);
                return;
            }
            eao.a((String) "update cpsource", (String) "requestBicycleForCity");
            BicycleStatusCmd1Param bicycleStatusCmd1Param = new BicycleStatusCmd1Param();
            bicycleStatusCmd1Param.b = b3;
            defpackage.egu.a.a((BaseRequest) new CityInfoBicycleStatusRequest(bicycleStatusCmd1Param, new com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a() {
                public final void a(BaseNetResult baseNetResult) {
                    a aVar = new a(b3, 0);
                    if (baseNetResult == null || !aVar.a(baseNetResult)) {
                        ehr.b(null, bVar);
                        return;
                    }
                    ehr.a(ehr.a, aVar);
                    ehr.b(aVar, bVar);
                }
            }));
        }
    }

    /* access modifiers changed from: private */
    public static void b(a aVar, b bVar) {
        if (aVar == null) {
            a.b();
        }
        StringBuilder sb = new StringBuilder("notifyDataChanged ");
        sb.append(aVar);
        sb.append(", ");
        sb.append(bVar);
        eao.a((String) "update cpsource", sb.toString());
        if (bVar != null) {
            bVar.a(aVar);
        }
    }

    private boolean a(GeoPoint geoPoint) {
        a aVar;
        synchronized (this.b) {
            aVar = this.c;
        }
        if (aVar == null || System.currentTimeMillis() - aVar.b >= 300000 || TextUtils.isEmpty(aVar.c)) {
            return true;
        }
        return !aVar.c.equals(b(geoPoint));
    }

    private static String b(GeoPoint geoPoint) {
        if (geoPoint == null) {
            return null;
        }
        try {
            li a2 = li.a();
            if (a2 == null) {
                return null;
            }
            lj b2 = a2.b(geoPoint.x, geoPoint.y);
            if (b2 != null) {
                return b2.i;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void b() {
        synchronized (this.b) {
            this.c = null;
        }
        ehs.a((String) "share_bike_cp_source_list_new", (String) "");
        ehs.a((String) "share_bike_cpsource_list", (String) "");
        ehs.a((String) "share_bike_cpsource_name_list", (String) "");
    }

    static /* synthetic */ void a(ehr ehr, a aVar) {
        synchronized (ehr.b) {
            ehr.c = aVar;
        }
        ehs.a((String) "share_bike_cp_source_list_new", aVar.toString());
        ehs.a((String) "share_bike_cpsource_list", aVar.e);
        ehs.a((String) "share_bike_cpsource_name_list", aVar.d);
    }
}
