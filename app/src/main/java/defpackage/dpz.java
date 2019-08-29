package defpackage;

import android.app.Application;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import java.util.Date;

/* renamed from: dpz reason: default package */
/* compiled from: OrderHotelListEntityNew */
public final class dpz implements dpl {
    public int a;
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
    public String l;
    public String m;
    public boolean n = false;
    private boolean o = false;

    public final void a(String str, String str2) {
    }

    public final String c() {
        return null;
    }

    public final String g() {
        return null;
    }

    public final String h() {
        return null;
    }

    public final String j() {
        return null;
    }

    public final boolean a() {
        return this.o;
    }

    public final void a(boolean z) {
        this.o = z;
    }

    public final String b() {
        return this.g;
    }

    public final String d() {
        return this.h;
    }

    public final int i() {
        int i2 = this.a;
        if (i2 == 4) {
            return R.color.bg_i;
        }
        switch (i2) {
            case 1:
            case 2:
                return R.color.font_ff6732;
            default:
                return R.color.font_cb;
        }
    }

    public final String m() {
        return this.b;
    }

    public final POI k() {
        POI createPOI = POIFactory.createPOI();
        createPOI.setName(this.g);
        createPOI.setAddr(this.h);
        if (!TextUtils.isEmpty(this.j) && !TextUtils.isEmpty(this.k)) {
            createPOI.setPoint(new GeoPoint(Double.parseDouble(this.j), Double.parseDouble(this.k)));
        }
        return createPOI;
    }

    public final String l() {
        return this.i;
    }

    public final String e() {
        Date a2 = lf.a(this.e);
        Date a3 = lf.a(this.d);
        StringBuffer stringBuffer = new StringBuffer();
        if (!TextUtils.isEmpty(this.e)) {
            stringBuffer.append(ahw.a(a2));
        }
        if (!TextUtils.isEmpty(this.d)) {
            stringBuffer.append("-");
            stringBuffer.append(ahw.a(a3));
        }
        if (!TextUtils.isEmpty(this.e) && !TextUtils.isEmpty(this.d)) {
            stringBuffer.append("  ");
            stringBuffer.append(ahw.a(this.d, this.e));
            stringBuffer.append("晚");
        }
        if (!TextUtils.isEmpty(this.f)) {
            stringBuffer.append("/");
            stringBuffer.append(this.f);
            stringBuffer.append("间");
        }
        return stringBuffer.toString();
    }

    public final String f() {
        int i2;
        Application application = AMapAppGlobal.getApplication();
        if (this.n) {
            switch (this.a) {
                case 1:
                    i2 = R.string.life_order_hotel_status_new_1;
                    break;
                case 2:
                    i2 = R.string.life_order_hotel_status_new_2;
                    break;
                case 3:
                    i2 = R.string.life_order_hotel_status_new_3;
                    break;
                case 4:
                    i2 = R.string.life_order_hotel_status_new_4;
                    break;
                default:
                    i2 = R.string.life_order_hotel_status_new_0;
                    break;
            }
        } else {
            i2 = R.string.life_order_hotel_status_1001;
        }
        return application.getString(i2);
    }
}
