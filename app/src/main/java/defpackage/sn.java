package defpackage;

import android.content.res.Resources;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.route.model.AvoidTrafficJamInfo;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: sn reason: default package */
/* compiled from: AvoidCongestInfo */
public final class sn {
    public int a;
    public float b;
    public float c;
    public String d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public List<GeoPoint[]> j;
    public int[] k;
    public int l;
    public int[] m;
    public int[] n;
    public String o = null;
    public String p;
    public int q;

    public sn(AvoidTrafficJamInfo avoidTrafficJamInfo) {
        String str;
        String str2;
        String str3 = null;
        this.b = avoidTrafficJamInfo.lon;
        this.c = avoidTrafficJamInfo.lat;
        this.d = avoidTrafficJamInfo.roadName;
        this.e = avoidTrafficJamInfo.length;
        this.f = avoidTrafficJamInfo.state;
        this.g = avoidTrafficJamInfo.priority;
        this.h = avoidTrafficJamInfo.averageSpeed;
        this.i = avoidTrafficJamInfo.travelTime;
        if (!TextUtils.isEmpty(this.d) && !"null".equals(this.d)) {
            Resources resources = AMapAppGlobal.getApplication().getResources();
            StringBuilder sb = new StringBuilder();
            if (this.q == RouteType.MOTOR.getValue()) {
                sb.append(resources.getString(R.string.autonavi_alert_avoid_jam));
            } else {
                sb.append(resources.getString(R.string.autonavi_already_avoid_jam));
            }
            sb.append(this.d);
            switch (this.f) {
                case 2:
                    sb.append(resources.getString(R.string.autonavi_avoid_jam_slow_suff));
                    break;
                case 3:
                    sb.append(resources.getString(R.string.autonavi_avoid_jam_bad_suff));
                    break;
                case 4:
                    sb.append(resources.getString(R.string.autonavi_avoid_jam_very_bad_suff));
                    break;
            }
            str3 = sb.toString();
        }
        this.o = str3;
        if (this.e > 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_jam_tip_length_pref));
            int i2 = this.e;
            Resources resources2 = AMapAppGlobal.getApplication().getResources();
            if (i2 < 1000) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(i2);
                sb3.append(resources2.getString(R.string.autonavi_end_meter));
                str2 = sb3.toString();
            } else {
                int i3 = i2 / 1000;
                int i4 = (i2 % 1000) / 100;
                String valueOf = String.valueOf(i3);
                if (i4 > 0) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(valueOf);
                    sb4.append(".");
                    sb4.append(i4);
                    sb4.append(resources2.getString(R.string.km));
                    str2 = sb4.toString();
                } else {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(valueOf);
                    sb5.append(resources2.getString(R.string.km));
                    str2 = sb5.toString();
                }
            }
            sb2.append(str2);
            str = sb2.toString();
        } else {
            str = "";
        }
        String str4 = "";
        str4 = this.i > 0 ? lf.d(this.i) : str4;
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str4)) {
            str = "";
        } else if (!TextUtils.isEmpty(str4)) {
            if (TextUtils.isEmpty(str)) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(AMapPageUtil.getAppContext().getString(R.string.approx));
                sb6.append(str4);
                sb6.append(AMapPageUtil.getAppContext().getString(R.string.pass));
                str = sb6.toString();
            } else {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(str);
                sb7.append("，");
                sb7.append(AMapPageUtil.getAppContext().getString(R.string.approx));
                sb7.append(str4);
                sb7.append(AMapPageUtil.getAppContext().getString(R.string.pass));
                str = sb7.toString();
            }
        }
        this.p = str;
        this.k = avoidTrafficJamInfo.statusList;
        this.n = avoidTrafficJamInfo.coorList;
        this.l = avoidTrafficJamInfo.coorCnt;
        this.m = avoidTrafficJamInfo.segStartCoorIndexs;
        try {
            this.j = a(avoidTrafficJamInfo.coorList, avoidTrafficJamInfo.segStartCoorIndexs);
        } catch (Exception unused) {
            AMapLog.e("AvoidCongestInfo", "TBT data is error");
        }
    }

    private static List<GeoPoint[]> a(int[] iArr, int[] iArr2) {
        int i2;
        if (iArr == null || iArr.length == 0 || iArr.length % 2 != 0 || iArr2 == null || iArr2.length == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(iArr2.length);
        for (int i3 = 0; i3 < iArr2.length; i3++) {
            int i4 = iArr2[i3];
            if (i3 < iArr2.length - 1) {
                i2 = iArr2[i3 + 1] - i4;
            } else {
                i2 = (iArr.length / 2) - i4;
            }
            GeoPoint[] a2 = a(iArr, i4, i2);
            if (a2 == null) {
                return null;
            }
            arrayList.add(a2);
        }
        return arrayList;
    }

    private static GeoPoint[] a(int[] iArr, int i2, int i3) {
        GeoPoint geoPoint;
        if (i2 >= 0 && i3 > 0) {
            int i4 = i2 + i3;
            if (i4 - 1 < iArr.length / 2) {
                GeoPoint[] geoPointArr = new GeoPoint[(i3 + 1)];
                for (int i5 = i2; i5 <= i4; i5++) {
                    if (i5 >= iArr.length / 2) {
                        geoPoint = new GeoPoint(((double) iArr[iArr.length - 2]) / 3600000.0d, ((double) iArr[iArr.length - 1]) / 3600000.0d);
                    } else {
                        int i6 = i5 * 2;
                        geoPoint = new GeoPoint(((double) iArr[i6]) / 3600000.0d, ((double) iArr[i6 + 1]) / 3600000.0d);
                    }
                    geoPointArr[i5 - i2] = geoPoint;
                }
                return geoPointArr;
            }
        }
        return null;
    }
}
