package defpackage;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.ExTrainPath;
import com.autonavi.bundle.routecommon.entity.Station;
import com.autonavi.minimap.R;
import java.util.ArrayList;

/* renamed from: dvs reason: default package */
/* compiled from: BusStationDesItem */
public final class dvs {
    public int A = 0;
    public ExTrainPath B;
    public boolean C;
    public int D = 0;
    public int E = 0;
    public int F;
    public int G = 0;
    public int H = -1;
    public String I;
    private String J = null;
    private String K = null;
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public int g;
    public int h;
    public int i;
    public int j;
    public BusPathSection k;
    public ArrayList<Station> l;
    public int[] m;
    public int[] n;
    public int o;
    public boolean p = false;
    public int q;
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;
    public int w;
    public boolean x;
    public boolean y;
    public int z = 1;

    public final int a() {
        if (this.H >= 0) {
            return this.H;
        }
        if (this.k != null) {
            return this.k.mTransferType;
        }
        return 0;
    }

    public final String b() {
        String str;
        if (this.i <= 0) {
            return null;
        }
        int i2 = this.i % 60 == 0 ? this.i / 60 : (this.i / 60) + 1;
        if (i2 >= 60) {
            StringBuilder sb = new StringBuilder();
            sb.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
            sb.append(i2 / 60);
            sb.append(AMapAppGlobal.getApplication().getString(R.string.route_hour));
            String sb2 = sb.toString();
            int i3 = i2 % 60;
            if (i3 > 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(i3);
                sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_minutes));
                str = sb3.toString();
            } else {
                str = sb2;
            }
        } else if (i2 == 0) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
            sb4.append("1");
            sb4.append(AMapAppGlobal.getApplication().getString(R.string.route_minutes));
            str = sb4.toString();
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
            sb5.append(i2);
            sb5.append(AMapAppGlobal.getApplication().getString(R.string.route_minutes));
            str = sb5.toString();
        }
        return str;
    }

    public final String c() {
        String string = AMapAppGlobal.getApplication().getString(R.string.route_ext_taxi_des);
        if (this.v > 0) {
            int round = Math.round(((float) this.v) / 1000.0f);
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(Token.SEPARATOR);
            sb.append(AMapAppGlobal.getApplication().getString(R.string.route_ext_taxi_des_length));
            sb.append(round);
            sb.append(AMapAppGlobal.getApplication().getString(R.string.km));
            string = sb.toString();
        }
        if (this.w <= 0) {
            return string;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append(Token.SEPARATOR);
        sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
        sb2.append(a(this.w));
        sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_hour));
        return sb2.toString();
    }

    private static String a(int i2) {
        int i3 = i2 / 60;
        int i4 = i3 / 60;
        int i5 = i3 % 60;
        if (i5 <= 0 || i5 > 30) {
            if (i5 > 30) {
                i4++;
            }
            return String.valueOf(i4);
        } else if (i4 == 0) {
            return "0.5";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(i4));
            sb.append(".5");
            return sb.toString();
        }
    }
}
