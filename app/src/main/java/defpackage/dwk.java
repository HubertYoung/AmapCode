package defpackage;

import android.content.res.Resources;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.BusPathSection.Emergency;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dwk reason: default package */
/* compiled from: RouteBusPathHelper */
public final class dwk {
    private static boolean a(int i) {
        return i == 1 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 16 || i == 17 || i == 18;
    }

    private static boolean b(int i) {
        return i == 2 || i == 3 || i == 10;
    }

    public static boolean a(BusPath busPath, int i) {
        if (busPath == null || busPath.mPathSections == null || i >= busPath.mPathSections.length) {
            return false;
        }
        BusPathSection busPathSection = busPath.mPathSections[i];
        if (busPathSection.mTransferType == 1 || busPathSection.mTransferType == 4) {
            return true;
        }
        return false;
    }

    private static String b(BusPath busPath) {
        if (busPath == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            if (busPath.taxiBusPath != null && busPath.taxiBusPath.isStart) {
                if (busPath.taxiBusPath.mCost > 0) {
                    StringBuilder sb = new StringBuilder("打车(约");
                    sb.append(busPath.taxiBusPath.mCost);
                    sb.append("元) > ");
                    stringBuffer.append(sb.toString());
                } else {
                    stringBuffer.append("打车 > ");
                }
            }
            for (int i = 0; i < busPath.mSectionNum; i++) {
                if (busPath.mPathSections[i] != null) {
                    stringBuffer.append(ebm.c(busPath.mPathSections[i].mExactSectionName));
                    String a = a(busPath.mPathSections[i].alter_list);
                    if (!TextUtils.isEmpty(a)) {
                        stringBuffer.append(a);
                    }
                    if (busPath.mSectionNum > 1 && i < busPath.mSectionNum - 1) {
                        stringBuffer.append(" > ");
                    }
                }
            }
            if (busPath.taxiBusPath != null && !busPath.taxiBusPath.isStart) {
                if (busPath.taxiBusPath.mCost > 0) {
                    StringBuilder sb2 = new StringBuilder(" > 打车(约");
                    sb2.append(busPath.taxiBusPath.mCost);
                    sb2.append("元)");
                    stringBuffer.append(sb2.toString());
                } else {
                    stringBuffer.append(" > 打车");
                }
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return stringBuffer.toString();
    }

    private static String c(BusPath busPath) {
        if (busPath == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            if (busPath.taxiBusPath != null && busPath.taxiBusPath.isStart) {
                stringBuffer.append(100);
                stringBuffer.append(SimpleComparison.GREATER_THAN_OPERATION);
            }
            for (int i = 0; i < busPath.mSectionNum; i++) {
                if (busPath.mPathSections[i] != null) {
                    stringBuffer.append(busPath.mPathSections[i].mBusType);
                    if (i < busPath.mSectionNum - 1) {
                        stringBuffer.append(SimpleComparison.GREATER_THAN_OPERATION);
                    }
                }
            }
            if (busPath.taxiBusPath != null && !busPath.taxiBusPath.isStart) {
                stringBuffer.append(SimpleComparison.GREATER_THAN_OPERATION);
                stringBuffer.append(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    private static String a(BusPathSection[] busPathSectionArr) {
        StringBuffer stringBuffer = new StringBuffer();
        if (busPathSectionArr != null) {
            try {
                int length = busPathSectionArr.length;
                ArrayList arrayList = new ArrayList();
                int i = 0;
                while (i < length && i < 2) {
                    String c = ebm.c(busPathSectionArr[i].mSectionName);
                    if (!TextUtils.isEmpty(c) && !arrayList.contains(c)) {
                        stringBuffer.append("/".concat(String.valueOf(c)));
                        arrayList.add(c);
                    }
                    i++;
                }
                if (length > 2) {
                    stringBuffer.append(AMapPageUtil.getAppContext().getString(R.string.etc));
                    return stringBuffer.toString();
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        return stringBuffer.toString();
    }

    private static String d(BusPath busPath) {
        String str;
        if (busPath == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            int i = busPath.mEndWalkLength;
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < busPath.mSectionNum; i4++) {
                i += busPath.mPathSections[i4].mFootLength;
                if (a(busPath.mPathSections[i4].mBusType)) {
                    i2 += busPath.mPathSections[i4].mStationNum - 1;
                } else if (b(busPath.mPathSections[i4].mBusType)) {
                    i3 += busPath.mPathSections[i4].mStationNum - 1;
                }
            }
            if (AMapPageUtil.getAppContext() != null) {
                int i5 = i2 + i3;
                Resources resources = AMapPageUtil.getAppContext().getResources();
                if (i5 > 0) {
                    StringBuilder sb2 = new StringBuilder(Token.SEPARATOR);
                    sb2.append(resources.getString(R.string.take_bus));
                    sb.append(sb2.toString());
                    sb.append(i5);
                    sb.append(resources.getString(R.string.bus_stop));
                }
                if (i > 0) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    sb3.append(Token.SEPARATOR);
                    sb3.append(resources.getString(R.string.by_foot));
                    sb3.append(cfe.a(i));
                    str = sb3.toString();
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(resources.getString(R.string.not_by_foot));
                    sb4.append(Token.SEPARATOR);
                    str = sb4.toString();
                }
                if (str.length() > 0) {
                    sb.append(str);
                }
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return sb.toString();
    }

    private static String e(BusPath busPath) {
        if (busPath == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < busPath.mSectionNum) {
            try {
                if (a(busPath.mPathSections[i].mBusType)) {
                    i2 += busPath.mPathSections[i].mStationNum - 1;
                } else if (b(busPath.mPathSections[i].mBusType)) {
                    i3 += busPath.mPathSections[i].mStationNum - 1;
                }
                i++;
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        int i4 = i2 + i3;
        if (i4 > 0) {
            sb.append(i4);
            sb.append(AMapPageUtil.getAppContext().getString(R.string.bus_stop));
        }
        return sb.toString();
    }

    private static String f(BusPath busPath) {
        String str;
        if (busPath == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            if (AMapPageUtil.getAppContext() == null) {
                return sb.toString();
            }
            Resources resources = AMapPageUtil.getAppContext().getResources();
            int i = busPath.mEndWalkLength;
            for (int i2 = 0; i2 < busPath.mSectionNum; i2++) {
                i += busPath.mPathSections[i2].mFootLength;
            }
            if (i > 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(resources.getString(R.string.walking));
                sb2.append(cfe.a(i));
                str = sb2.toString();
            } else {
                str = resources.getString(R.string.no_walking);
            }
            if (str.length() > 0) {
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    public static dvv a(BusPath busPath) {
        String str;
        int i;
        if (busPath == null) {
            return null;
        }
        dvv dvv = new dvv();
        dvv.a = axt.a(busPath);
        dvv.b = ebj.a(AMapPageUtil.getAppContext(), busPath);
        dvv.c = b(busPath);
        dvv.d = c(busPath);
        dvv.e = d(busPath);
        dvv.g = busPath.time_tag;
        dvv.h = busPath.time_tag_des;
        dvv.i = busPath.risk_time_des;
        dvv.j = busPath.min_tag;
        dvv.t = busPath.etastatus;
        StringBuilder sb = new StringBuilder();
        if (busPath != null && busPath.expense > 0.0d) {
            double d = busPath.expense;
            if (d < 0.0d) {
                throw new IllegalArgumentException("wrong cost: ".concat(String.valueOf(d)));
            }
            StringBuilder sb2 = new StringBuilder();
            long round = Math.round(d);
            if (Double.compare(d, (double) round) == 0) {
                sb2.append(round);
            } else {
                sb2.append(d);
            }
            sb.append(sb2.toString());
            sb.append(AMapPageUtil.getAppContext().getString(R.string.rmb));
        }
        dvv.f = sb.toString();
        dvv.l = e(busPath);
        dvv.m = f(busPath);
        if (busPath == null) {
            str = "";
        } else {
            str = cfe.a(busPath.mTotalLength);
        }
        dvv.k = str;
        dvv.r = g(busPath);
        dvv.s = h(busPath);
        String str2 = dvv.b;
        BusPathSection[] busPathSectionArr = busPath.mPathSections;
        int i2 = (busPathSectionArr == null || busPathSectionArr.length <= 0 || !busPathSectionArr[0].isRealTime) ? 0 : TextUtils.isEmpty(str2) ? 2 : 1;
        dvv.z = i2;
        dvv.A = busPath.mRealTimeStatus;
        if (busPath.mBusTags != null) {
            int i3 = -16776961;
            if (busPath.mBusTags.length > 0) {
                String str3 = busPath.mBusTags[0].a;
                if (str3.length() > 5) {
                    str3 = str3.substring(0, 5);
                }
                try {
                    i = Integer.parseInt(busPath.mBusTags[0].b, 16) | -16777216;
                } catch (NumberFormatException unused) {
                    i = -16776961;
                }
                dvv.v = str3;
                dvv.w = i;
            }
            if (busPath.mBusTags.length > 1) {
                String str4 = busPath.mBusTags[1].a;
                if (str4.length() > 5) {
                    str4 = str4.substring(0, 5);
                }
                try {
                    i3 = Integer.parseInt(busPath.mBusTags[0].b, 16) | -16777216;
                } catch (NumberFormatException unused2) {
                }
                dvv.x = str4;
                dvv.y = i3;
            }
        }
        String str5 = "";
        if (busPath.mPathSections != null) {
            int i4 = 0;
            while (true) {
                if (i4 >= busPath.mPathSections.length) {
                    break;
                }
                BusPathSection busPathSection = busPath.mPathSections[i4];
                if (busPathSection.mStations != null) {
                    str5 = busPathSection.mStations[0].mName;
                    if (busPathSection.subway_inport != null && !TextUtils.isEmpty(busPathSection.subway_inport.name)) {
                        Resources resources = AMapPageUtil.getAppContext().getResources();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str5);
                        sb3.append(resources.getString(R.string.route_item_subway_inport_left));
                        sb3.append(busPathSection.subway_inport.name);
                        sb3.append(resources.getString(R.string.route_item_subway_inport_right));
                        str5 = sb3.toString();
                    }
                } else {
                    i4++;
                }
            }
            if (busPath.mPathSections.length > 0) {
                dvv.B = busPath.mPathSections[0].mExactSectionName;
            }
        }
        dvv.n = str5;
        StringBuilder sb4 = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();
        if (busPath.mPathSections != null) {
            for (BusPathSection busPathSection2 : busPath.mPathSections) {
                if (busPathSection2.busTimeTag == 4) {
                    sb4.append(busPathSection2.mExactSectionName);
                    sb4.append("、");
                }
                if (busPathSection2.busTimeTag == 3) {
                    stringBuffer.append(busPathSection2.mExactSectionName);
                    stringBuffer.append("、");
                }
                if (busPathSection2.alter_list != null) {
                    int i5 = 0;
                    int i6 = 0;
                    for (BusPathSection busPathSection3 : busPathSection2.alter_list) {
                        if (busPathSection3.busTimeTag == 4 && i5 < 2) {
                            sb4.append(busPathSection3.mExactSectionName);
                            sb4.append("、");
                            i5++;
                        }
                        if (busPathSection3.busTimeTag == 3 && i6 < 2) {
                            stringBuffer.append(busPathSection3.mExactSectionName);
                            stringBuffer.append("、");
                            i6++;
                        }
                    }
                }
            }
        }
        String stringBuffer2 = stringBuffer.toString();
        String sb5 = sb4.toString();
        if (sb5.endsWith("、")) {
            sb5 = sb5.substring(0, sb5.length() - 1);
        }
        if (stringBuffer2.endsWith("、")) {
            stringBuffer2 = stringBuffer2.substring(0, stringBuffer2.length() - 1);
        }
        dvv.o = sb5;
        dvv.p = stringBuffer2;
        if (busPath.mPathSections == null) {
            dvv.u = false;
        } else if (busPath.mPathSections.length > 1 || !(busPath.mPathSections[0] == null || busPath.mPathSections[0].alter_list == null || busPath.mPathSections[0].alter_list.length <= 0)) {
            dvv.u = true;
        } else {
            dvv.u = false;
        }
        return dvv;
    }

    private static int g(BusPath busPath) {
        int i;
        if (busPath == null) {
            return -1;
        }
        if (busPath.mPathSections != null) {
            int length = busPath.mPathSections.length;
            int i2 = 0;
            i = -1;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                BusPathSection busPathSection = busPath.mPathSections[i2];
                if (busPathSection.mEmergency != null) {
                    i = busPathSection.mEmergency.lineType;
                    if (i == 1) {
                        break;
                    }
                }
                if (busPathSection.alter_list != null) {
                    int length2 = busPathSection.alter_list.length;
                    int i3 = i;
                    int i4 = 0;
                    while (i4 < length2 && i4 != 2) {
                        BusPathSection busPathSection2 = busPathSection.alter_list[i4];
                        if (busPathSection2.mEmergency != null) {
                            i3 = busPathSection2.mEmergency.lineType;
                            if (i3 == 1) {
                                break;
                            }
                        }
                        i4++;
                    }
                    if (i3 == 1) {
                        i = i3;
                        break;
                    }
                    i = i3;
                }
                i2++;
            }
        } else {
            i = -1;
        }
        return i;
    }

    private static List<String> h(BusPath busPath) {
        BusPathSection[] busPathSectionArr;
        ArrayList arrayList = new ArrayList();
        if (!(busPath == null || busPath.mPathSections == null)) {
            for (BusPathSection busPathSection : busPath.mPathSections) {
                a((List<String>) arrayList, busPathSection.mEmergency);
                if (busPathSection.alter_list != null) {
                    int min = Math.min(busPathSection.alter_list.length, 2);
                    for (int i = 0; i < min; i++) {
                        a((List<String>) arrayList, busPathSection.alter_list[i].mEmergency);
                    }
                }
            }
        }
        return arrayList;
    }

    private static void a(List<String> list, Emergency emergency) {
        if (emergency != null && emergency.lineType == 1) {
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(emergency.eventTagDesc)) {
                sb.append(emergency.eventTagDesc);
                sb.append("：");
            }
            if (!TextUtils.isEmpty(emergency.ldescription)) {
                sb.append(emergency.ldescription);
            } else {
                if (!TextUtils.isEmpty(emergency.ssdescription)) {
                    sb.append(emergency.ssdescription);
                }
                if (!TextUtils.isEmpty(emergency.esdescription)) {
                    sb.append(emergency.esdescription);
                }
            }
            if (sb.length() != 0) {
                list.add(sb.toString());
            }
        }
    }

    public static long a() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).sharedPrefs().getLong("bus_time_setting", -1);
    }
}
