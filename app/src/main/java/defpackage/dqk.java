package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dqk reason: default package */
/* compiled from: ViewPointOrderListEntity */
public final class dqk implements dpl {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public boolean l = false;
    public String m;
    public String n;
    private boolean o;

    public final String g() {
        return "FLAG_RED";
    }

    public final String b() {
        return this.d;
    }

    public final String c() {
        if (TextUtils.isEmpty(this.g)) {
            return null;
        }
        return AMapAppGlobal.getApplication().getResources().getString(R.string.life_order_total, new Object[]{Integer.valueOf(Integer.parseInt(this.g) / 100)});
    }

    public final String e() {
        if (!TextUtils.isEmpty(this.h)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.h);
            sb.append(" : ");
            sb.append(this.b);
            sb.append("张");
            return sb.toString();
        }
        return AMapAppGlobal.getApplication().getResources().getString(R.string.life_order_viewpoint_amout, new Object[]{this.b});
    }

    public final String h() {
        return "1".equals(this.c) ? "FLAG_ORANGE" : "FLAG_GRAY";
    }

    public final int i() {
        int parseInt = Integer.parseInt(this.c);
        if (parseInt == 200) {
            return R.color.font_ff6732;
        }
        if (parseInt != 400) {
            return R.color.font_cb;
        }
        return R.color.bg_i;
    }

    public final String f() {
        if (this.l) {
            if ("200".equals(this.c)) {
                return AMapAppGlobal.getApplication().getString(R.string.life_order_viewpoint_status_new_2);
            }
            if ("300".equals(this.c)) {
                return AMapAppGlobal.getApplication().getString(R.string.life_order_viewpoint_status_new_3);
            }
            if ("400".equals(this.c)) {
                return AMapAppGlobal.getApplication().getString(R.string.life_order_viewpoint_status_7);
            }
        } else if ("1".equals(this.c)) {
            return AMapAppGlobal.getApplication().getString(R.string.life_order_viewpoint_status_1);
        } else {
            if ("5".equals(this.c)) {
                return AMapAppGlobal.getApplication().getString(R.string.life_order_viewpoint_status_5);
            }
            if ("6".equals(this.c)) {
                return AMapAppGlobal.getApplication().getString(R.string.life_order_viewpoint_status_6);
            }
            if ("7".equals(this.c)) {
                return AMapAppGlobal.getApplication().getString(R.string.life_order_viewpoint_status_7);
            }
        }
        return "";
    }

    public final String j() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("OrderID", this.a);
            if (!TextUtils.isEmpty(this.m) && !TextUtils.isEmpty(this.n)) {
                jSONObject.put("PhoneNum", this.m);
                jSONObject.put("VerificationCode", this.n);
            }
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
        }
        return jSONObject.toString();
    }

    public final void a(String str, String str2) {
        this.m = str;
        this.n = str2;
    }

    public final POI k() {
        POI createPOI = POIFactory.createPOI();
        createPOI.setId(this.i);
        createPOI.setName(this.d);
        if (!TextUtils.isEmpty(this.j) && !TextUtils.isEmpty(this.k)) {
            createPOI.setPoint(new GeoPoint(Double.parseDouble(this.j), Double.parseDouble(this.k)));
        }
        return createPOI;
    }

    public final String l() {
        return this.e;
    }

    public final boolean a() {
        return this.o;
    }

    public final void a(boolean z) {
        this.o = z;
    }

    public final String m() {
        return this.a;
    }

    public final String d() {
        return AMapAppGlobal.getApplication().getString(R.string.life_order_viewpoint_date, new Object[]{this.f});
    }
}
