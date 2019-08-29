package defpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommute.common.CommuteHelper;
import com.autonavi.bundle.routecommute.common.bean.AddressMatrix;
import com.autonavi.bundle.routecommute.common.bean.AddressMatrix.CompanyType;
import com.autonavi.bundle.routecommute.common.bean.AddressMatrix.HomeType;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.bundle.routecommute.common.bean.TipsMatrix;
import com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType;
import com.autonavi.bundle.routecommute.common.bean.TipsMatrix.TimeType;
import com.autonavi.bundle.routecommute.common.bean.TipsMatrix.UserType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: azk reason: default package */
/* compiled from: RouteCommuteRulesUtil */
public final class azk {
    public static boolean a(int i) {
        return i == 11 || i == 12;
    }

    public static boolean a(String str, NaviAddress naviAddress, GeoPoint geoPoint) {
        azb.a(CommuteHelper.a, "isWeekRule jsonData = ".concat(String.valueOf(str)));
        if (TextUtils.isEmpty(str) || !b(str) || !a(str)) {
            return false;
        }
        int a = a(naviAddress, str, geoPoint);
        azb.a(CommuteHelper.a, "isWithTheRulesDriver getCommuteTipsType = ".concat(String.valueOf(a)));
        if (a == 0) {
            return false;
        }
        return true;
    }

    public static boolean a(String str) {
        try {
            JSONArray optJSONArray = new JSONObject(str).optJSONArray("time");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            String str2 = CommuteHelper.a;
            StringBuilder sb = new StringBuilder("isWeekRule now = ");
            sb.append(simpleDateFormat.format(parse));
            azb.a(str2, sb.toString());
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                String optString = optJSONObject.optString("startTime");
                String optString2 = optJSONObject.optString(AppInitCallback.SP_KEY_endTime);
                if (optString.matches("([0-1][0-9]|[2][0-3])(:)([0-5][0-9])") && optString2.matches("([0-1][0-9]|[2][0-3])(:)([0-5][0-9])")) {
                    Date parse2 = simpleDateFormat.parse(optString);
                    String str3 = CommuteHelper.a;
                    StringBuilder sb2 = new StringBuilder("isWeekRule begin = ");
                    sb2.append(simpleDateFormat.format(parse2));
                    azb.a(str3, sb2.toString());
                    Date parse3 = simpleDateFormat.parse(optString2);
                    String str4 = CommuteHelper.a;
                    StringBuilder sb3 = new StringBuilder("isWeekRule end = ");
                    sb3.append(simpleDateFormat.format(parse3));
                    azb.a(str4, sb3.toString());
                    if (a(parse, parse2, parse3)) {
                        azb.a(CommuteHelper.a, "compareCalendar  = true");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static HomeType a(NaviAddress naviAddress) {
        if (naviAddress == null) {
            return HomeType.HOME_NOTHING;
        }
        if (naviAddress.home == null) {
            return HomeType.HOME_NOTHING;
        }
        if (naviAddress.home.getHome() == null) {
            return HomeType.HOME_NOTHING;
        }
        if (naviAddress.home.source != 0) {
            return HomeType.HOME_SET;
        }
        return HomeType.HOME_MINE;
    }

    public static int a(NaviAddress naviAddress, String str, GeoPoint geoPoint) {
        UserType a = AddressMatrix.a(b(naviAddress), a(naviAddress));
        TimeType f = f();
        DisType b = b(naviAddress, str, geoPoint);
        int a2 = TipsMatrix.a(a, f, b);
        String str2 = CommuteHelper.a;
        StringBuilder sb = new StringBuilder("RouteCommuteRulesUtil getCommuteTipsType tipsType = ");
        sb.append(a2);
        sb.append(" userType = ");
        sb.append(a);
        sb.append(" timeType = ");
        sb.append(f);
        sb.append(" disType = ");
        sb.append(b);
        sb.append(" currentPoint.lon = ");
        double d = 0.0d;
        sb.append(geoPoint != null ? geoPoint.getLongitude() : 0.0d);
        sb.append(" currentPoint.lat = ");
        if (geoPoint != null) {
            d = geoPoint.getLatitude();
        }
        sb.append(d);
        azb.a(str2, sb.toString());
        return a2;
    }

    private static CompanyType b(NaviAddress naviAddress) {
        if (naviAddress == null) {
            return CompanyType.COMPANY_NOTHING;
        }
        if (naviAddress.company == null) {
            return CompanyType.COMPANY_NOTHING;
        }
        if (naviAddress.company.getCompany() == null) {
            return CompanyType.COMPANY_NOTHING;
        }
        if (naviAddress.company.source != 0) {
            return CompanyType.COMPANY_SET;
        }
        return CompanyType.COMPANY_MINE;
    }

    public static boolean b() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof AbstractBasePage) {
            return ((AbstractBasePage) pageContext).isResumed();
        }
        return false;
    }

    @Nullable
    public static Date a(String str, SimpleDateFormat simpleDateFormat) {
        Date date;
        if (TextUtils.isEmpty(str) || !str.matches("([0-1][0-9]|[2][0-3])(:)([0-5][0-9])")) {
            return null;
        }
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return date;
    }

    @Nullable
    public static Date c() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean d() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            String str = CommuteHelper.a;
            StringBuilder sb = new StringBuilder("isTimeToWork now = ");
            sb.append(simpleDateFormat.format(parse));
            azb.a(str, sb.toString());
            Date parse2 = simpleDateFormat.parse("6:00");
            String str2 = CommuteHelper.a;
            StringBuilder sb2 = new StringBuilder("isTimeToWork begin = ");
            sb2.append(simpleDateFormat.format(parse2));
            azb.a(str2, sb2.toString());
            Date parse3 = simpleDateFormat.parse("10:00");
            String str3 = CommuteHelper.a;
            StringBuilder sb3 = new StringBuilder("isTimeToWork end = ");
            sb3.append(simpleDateFormat.format(parse3));
            azb.a(str3, sb3.toString());
            if (a(parse, parse2, parse3)) {
                azb.a(CommuteHelper.a, "compareCalendar  = true");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean e() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            String str = CommuteHelper.a;
            StringBuilder sb = new StringBuilder("isTimeWorkOff now = ");
            sb.append(simpleDateFormat.format(parse));
            azb.a(str, sb.toString());
            Date parse2 = simpleDateFormat.parse("15:30");
            String str2 = CommuteHelper.a;
            StringBuilder sb2 = new StringBuilder("isTimeWorkOff begin = ");
            sb2.append(simpleDateFormat.format(parse2));
            azb.a(str2, sb2.toString());
            Date parse3 = simpleDateFormat.parse("23:59");
            String str3 = CommuteHelper.a;
            StringBuilder sb3 = new StringBuilder("isTimeWorkOff end = ");
            sb3.append(simpleDateFormat.format(parse3));
            azb.a(str3, sb3.toString());
            if (a(parse, parse2, parse3)) {
                azb.a(CommuteHelper.a, "compareCalendar  = true");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean a(Date date, Date date2, Date date3) {
        return b(date, date2, date3) || c(date, date2, date3);
    }

    private static TimeType f() {
        if (e()) {
            return TimeType.WORKOFF_TIME;
        }
        if (d()) {
            return TimeType.WORKTO_TIME;
        }
        return TimeType.INVALID__TIME;
    }

    public static boolean b(int i) {
        if (i != 0 && a(i)) {
            return c(azi.j());
        }
        return false;
    }

    private static boolean c(int i) {
        long j;
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            j = 259200000;
        } else if (i != 2) {
            return false;
        } else {
            j = FileCache.EXPIRE_TIME;
        }
        return a(azi.k(), j);
    }

    private static boolean a(long j, long j2) {
        if (j == 0) {
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis() - j;
        AMapLog.d(CommuteHelper.a, "checkDateDeprecated dValue: ".concat(String.valueOf(currentTimeMillis)));
        if (currentTimeMillis >= j2) {
            return true;
        }
        return false;
    }

    private static boolean b(Date date, Date date2, Date date3) {
        if (date == null) {
            return false;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        Calendar instance3 = Calendar.getInstance();
        instance3.setTime(date3);
        if (!instance.after(instance2) || !instance.before(instance3)) {
            return false;
        }
        return true;
    }

    private static boolean c(Date date, Date date2, Date date3) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        Calendar instance3 = Calendar.getInstance();
        instance3.setTime(date3);
        return instance.equals(instance2) || instance.equals(instance3);
    }

    public static boolean b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONArray optJSONArray = jSONObject.optJSONArray("workWeekend");
            JSONArray optJSONArray2 = jSONObject.optJSONArray("restWeekday");
            int i = Calendar.getInstance().get(7);
            if (i > 0) {
                if (i <= 7) {
                    String format = new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime());
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder(" (isWeekRule) day: ");
                        sb.append(format);
                        sb.append(" -week: ");
                        sb.append(i);
                        azb.a("Daniel-27", sb.toString());
                        if (optJSONArray != null) {
                            StringBuilder sb2 = new StringBuilder(" (isWeekRule) workWeekend: ");
                            sb2.append(optJSONArray.toString());
                            azb.a("Daniel-27", sb2.toString());
                        }
                        if (optJSONArray2 != null) {
                            StringBuilder sb3 = new StringBuilder(" (isWeekRule) restWeekday: ");
                            sb3.append(optJSONArray2.toString());
                            azb.a("Daniel-27", sb3.toString());
                        }
                    }
                    boolean z = true;
                    if (i != 1) {
                        if (i != 7) {
                            if (optJSONArray2 != null) {
                                if (optJSONArray2.length() != 0) {
                                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                                        if (format.equals(optJSONArray2.optString(i2))) {
                                            z = false;
                                        }
                                    }
                                    return z;
                                }
                            }
                            return true;
                        }
                    }
                    if (optJSONArray != null) {
                        if (optJSONArray.length() != 0) {
                            boolean z2 = false;
                            for (int i3 = 0; i3 < optJSONArray.length(); i3++) {
                                if (format.equals(optJSONArray.optString(i3))) {
                                    z2 = true;
                                }
                            }
                            return z2;
                        }
                    }
                    return false;
                }
            }
            if (bno.a) {
                azb.a("Daniel-27", " (isWeekRule) week illegal");
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean a(String str, POI poi, POI poi2) {
        boolean z = false;
        if (!bnx.a(poi) || !bnx.a(poi2)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            double optDouble = jSONObject.optDouble("minDistance");
            double optDouble2 = jSONObject.optDouble("maxDistance");
            if (optDouble > 0.0d) {
                if (optDouble2 > 0.0d) {
                    double d = optDouble * 1000.0d;
                    double d2 = optDouble2 * 1000.0d;
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
                    if (latestPosition == null) {
                        return false;
                    }
                    POI createPOI = POIFactory.createPOI();
                    createPOI.setName("我的位置");
                    createPOI.setPoint(latestPosition);
                    float a = cfe.a(createPOI.getPoint(), poi.getPoint());
                    float a2 = cfe.a(createPOI.getPoint(), poi2.getPoint());
                    azb.a(CommuteHelper.a, "isDistanceRule 距离家直线距离 homeDistance = ".concat(String.valueOf(a)));
                    azb.a(CommuteHelper.a, "isDistanceRule 距离公司直线距离 companyDistance = ".concat(String.valueOf(a2)));
                    azb.a(CommuteHelper.a, "isDistanceRule 规则最大距离 maxM = ".concat(String.valueOf(d2)));
                    azb.a(CommuteHelper.a, "isDistanceRule 规则最小距离 minM = ".concat(String.valueOf(d)));
                    int i = (a > a2 ? 1 : (a == a2 ? 0 : -1));
                    if (i <= 0 ? !(((double) a) >= d || a2 <= a || ((double) a2) >= d2) : !(((double) a2) >= d || i <= 0 || ((double) a) >= d2)) {
                        z = true;
                    }
                    return z;
                }
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00ba, code lost:
        if (r6 < r4) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00d2, code lost:
        if (r6 < r4) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00e5, code lost:
        if (r8 < r4) goto L_0x00bc;
     */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x013b  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0106  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType b(com.autonavi.bundle.routecommute.common.bean.NaviAddress r11, java.lang.String r12, com.autonavi.common.model.GeoPoint r13) {
        /*
            java.lang.String r0 = com.autonavi.bundle.routecommute.common.CommuteHelper.a
            java.lang.String r1 = "getDisType jsonData = "
            java.lang.String r2 = java.lang.String.valueOf(r12)
            java.lang.String r1 = r1.concat(r2)
            defpackage.azb.a(r0, r1)
            boolean r0 = android.text.TextUtils.isEmpty(r12)
            if (r0 == 0) goto L_0x0018
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x0018:
            com.autonavi.bundle.routecommute.common.bean.AddressMatrix$CompanyType r0 = b(r11)
            com.autonavi.bundle.routecommute.common.bean.AddressMatrix$HomeType r1 = a(r11)
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$UserType r0 = com.autonavi.bundle.routecommute.common.bean.AddressMatrix.a(r0, r1)
            float r1 = a(r11, r13)
            float r11 = b(r11, r13)
            double r2 = c(r12)
            double r4 = d(r12)
            r6 = 0
            int r12 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r12 <= 0) goto L_0x0172
            int r12 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r12 > 0) goto L_0x0040
            goto L_0x0172
        L_0x0040:
            if (r13 != 0) goto L_0x004b
            com.autonavi.sdk.location.LocationInstrument r12 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            r13 = 5
            com.autonavi.common.model.GeoPoint r13 = r12.getLatestPosition(r13)
        L_0x004b:
            if (r13 != 0) goto L_0x0050
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x0050:
            com.autonavi.common.model.POI r12 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            java.lang.String r6 = "我的位置"
            r12.setName(r6)
            r12.setPoint(r13)
            int[] r12 = defpackage.azk.AnonymousClass1.a
            int r13 = r0.ordinal()
            r12 = r12[r13]
            r13 = 0
            r0 = 1
            switch(r12) {
                case 1: goto L_0x016f;
                case 2: goto L_0x016f;
                case 3: goto L_0x016f;
                case 4: goto L_0x0122;
                case 5: goto L_0x00a8;
                case 6: goto L_0x00a8;
                case 7: goto L_0x00a8;
                case 8: goto L_0x008a;
                case 9: goto L_0x006c;
                default: goto L_0x0069;
            }
        L_0x0069:
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x006c:
            double r11 = (double) r11
            int r1 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x0077
            int r1 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r1 >= 0) goto L_0x0077
            r1 = 1
            goto L_0x0078
        L_0x0077:
            r1 = 0
        L_0x0078:
            int r11 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r11 >= 0) goto L_0x007d
            r13 = 1
        L_0x007d:
            if (r13 == 0) goto L_0x0082
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.WORK_DIS
            return r11
        L_0x0082:
            if (r1 == 0) goto L_0x0087
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.CPOINT_DIS
            return r11
        L_0x0087:
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x008a:
            double r11 = (double) r1
            int r1 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x0095
            int r1 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r1 >= 0) goto L_0x0095
            r1 = 1
            goto L_0x0096
        L_0x0095:
            r1 = 0
        L_0x0096:
            int r11 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r11 >= 0) goto L_0x009b
            r13 = 1
        L_0x009b:
            if (r13 == 0) goto L_0x00a0
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.HOME_DIS
            return r11
        L_0x00a0:
            if (r1 == 0) goto L_0x00a5
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.CPOINT_DIS
            return r11
        L_0x00a5:
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x00a8:
            boolean r12 = d()
            if (r12 == 0) goto L_0x00c0
            double r6 = (double) r1
            int r12 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r12 <= 0) goto L_0x00be
            double r6 = (double) r11
            int r12 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r12 <= 0) goto L_0x00be
            int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r12 >= 0) goto L_0x00be
        L_0x00bc:
            r12 = 1
            goto L_0x00e8
        L_0x00be:
            r12 = 0
            goto L_0x00e8
        L_0x00c0:
            boolean r12 = e()
            if (r12 == 0) goto L_0x00d5
            double r6 = (double) r1
            int r12 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r12 <= 0) goto L_0x00be
            double r8 = (double) r11
            int r12 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r12 <= 0) goto L_0x00be
            int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r12 >= 0) goto L_0x00be
            goto L_0x00bc
        L_0x00d5:
            double r6 = (double) r1
            int r12 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r12 <= 0) goto L_0x00be
            double r8 = (double) r11
            int r12 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r12 <= 0) goto L_0x00be
            int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r12 >= 0) goto L_0x00be
            int r12 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r12 >= 0) goto L_0x00be
            goto L_0x00bc
        L_0x00e8:
            int r6 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r6 <= 0) goto L_0x0106
            double r7 = (double) r11
            int r11 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r11 >= 0) goto L_0x00f9
            if (r6 <= 0) goto L_0x00f9
            double r1 = (double) r1
            int r11 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r11 >= 0) goto L_0x00f9
            r13 = 1
        L_0x00f9:
            if (r13 == 0) goto L_0x00fe
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.WORK_DIS
            return r11
        L_0x00fe:
            if (r12 == 0) goto L_0x0103
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.CPOINT_DIS
            return r11
        L_0x0103:
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x0106:
            double r6 = (double) r1
            int r2 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0115
            int r1 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0115
            double r1 = (double) r11
            int r11 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r11 >= 0) goto L_0x0115
            r13 = 1
        L_0x0115:
            if (r13 == 0) goto L_0x011a
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.HOME_DIS
            return r11
        L_0x011a:
            if (r12 == 0) goto L_0x011f
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.CPOINT_DIS
            return r11
        L_0x011f:
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x0122:
            double r6 = (double) r1
            int r12 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r12 <= 0) goto L_0x0136
            double r8 = (double) r11
            int r12 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r12 <= 0) goto L_0x0136
            int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r12 >= 0) goto L_0x0136
            int r12 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r12 >= 0) goto L_0x0136
            r12 = 1
            goto L_0x0137
        L_0x0136:
            r12 = 0
        L_0x0137:
            int r8 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r8 <= 0) goto L_0x0154
            double r9 = (double) r11
            int r11 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r11 >= 0) goto L_0x0147
            if (r8 <= 0) goto L_0x0147
            int r11 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r11 >= 0) goto L_0x0147
            r13 = 1
        L_0x0147:
            if (r13 == 0) goto L_0x014c
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.WORK_DIS
            return r11
        L_0x014c:
            if (r12 == 0) goto L_0x0151
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.CPOINT_DIS
            return r11
        L_0x0151:
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x0154:
            int r2 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0162
            int r1 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0162
            double r1 = (double) r11
            int r11 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r11 >= 0) goto L_0x0162
            r13 = 1
        L_0x0162:
            if (r13 == 0) goto L_0x0167
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.HOME_DIS
            return r11
        L_0x0167:
            if (r12 == 0) goto L_0x016c
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.CPOINT_DIS
            return r11
        L_0x016c:
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x016f:
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        L_0x0172:
            com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType r11 = com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType.INVALID_DIS
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.azk.b(com.autonavi.bundle.routecommute.common.bean.NaviAddress, java.lang.String, com.autonavi.common.model.GeoPoint):com.autonavi.bundle.routecommute.common.bean.TipsMatrix$DisType");
    }

    private static double c(String str) {
        try {
            double optDouble = new JSONObject(str).optDouble("minDistance");
            if (optDouble <= 0.0d) {
                return -1.0d;
            }
            double d = optDouble * 1000.0d;
            azb.a(CommuteHelper.a, "isDistanceRule 规则最小距离 minM = ".concat(String.valueOf(d)));
            return d;
        } catch (JSONException e) {
            e.printStackTrace();
            return -1.0d;
        }
    }

    private static double d(String str) {
        try {
            double optDouble = new JSONObject(str).optDouble("maxDistance");
            if (optDouble <= 0.0d) {
                return -1.0d;
            }
            double d = optDouble * 1000.0d;
            azb.a(CommuteHelper.a, "isDistanceRule 规则最大距离 maxM = ".concat(String.valueOf(d)));
            return d;
        } catch (JSONException e) {
            e.printStackTrace();
            return -1.0d;
        }
    }

    private static float a(NaviAddress naviAddress, GeoPoint geoPoint) {
        POI poi;
        UserType a = AddressMatrix.a(b(naviAddress), a(naviAddress));
        if (a == UserType.NOTING_SET || a == UserType.HOME_MINE || a == UserType.WORK_MINE) {
            poi = null;
        } else {
            if (geoPoint == null) {
                geoPoint = LocationInstrument.getInstance().getLatestPosition(5);
            }
            if (geoPoint == null) {
                return -1.0f;
            }
            poi = POIFactory.createPOI();
            poi.setName("我的位置");
            poi.setPoint(geoPoint);
        }
        switch (a) {
            case NOTING_SET:
            case HOME_MINE:
            case WORK_MINE:
                return -1.0f;
            case DOUBLE_SET:
            case DOUBLE_MINE:
            case HOMESET_WORKMINE:
            case WORKSET_HOMEMINE:
            case HOME_SET:
                POI home = naviAddress.home.getHome();
                if (!bnx.a(home)) {
                    return -1.0f;
                }
                float a2 = cfe.a(poi.getPoint(), home.getPoint());
                azb.a(CommuteHelper.a, "isDistanceRule 距离家直线距离 homeDistance = ".concat(String.valueOf(a2)));
                return a2;
            case WORK_SET:
                return -1.0f;
            default:
                return -1.0f;
        }
    }

    private static float b(NaviAddress naviAddress, GeoPoint geoPoint) {
        POI poi;
        UserType a = AddressMatrix.a(b(naviAddress), a(naviAddress));
        if (a == UserType.NOTING_SET || a == UserType.HOME_MINE || a == UserType.WORK_MINE) {
            poi = null;
        } else {
            if (geoPoint == null) {
                geoPoint = LocationInstrument.getInstance().getLatestPosition(5);
            }
            if (geoPoint == null) {
                return -1.0f;
            }
            poi = POIFactory.createPOI();
            poi.setName("我的位置");
            poi.setPoint(geoPoint);
        }
        switch (a) {
            case NOTING_SET:
            case HOME_MINE:
            case WORK_MINE:
                return -1.0f;
            case DOUBLE_SET:
            case DOUBLE_MINE:
            case HOMESET_WORKMINE:
            case WORKSET_HOMEMINE:
            case WORK_SET:
                POI company = naviAddress.company.getCompany();
                if (!bnx.a(company)) {
                    return -1.0f;
                }
                float a2 = cfe.a(poi.getPoint(), company.getPoint());
                azb.a(CommuteHelper.a, "isDistanceRule 距离公司直线距离 companyDistance = ".concat(String.valueOf(a2)));
                return a2;
            case HOME_SET:
                return -1.0f;
            default:
                return -1.0f;
        }
    }

    public static JSONObject a(GeoPoint geoPoint) {
        JSONObject jSONObject;
        if (geoPoint == null) {
            return null;
        }
        try {
            jSONObject = new JSONObject();
            try {
                jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, "");
                jSONObject.put("name", "我的位置");
                jSONObject.put("address", "");
                jSONObject.put("new_type", "");
                jSONObject.put(DictionaryKeys.CTRLXY_X, geoPoint.x);
                jSONObject.put(DictionaryKeys.CTRLXY_Y, geoPoint.y);
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, geoPoint.getLongitude());
                jSONObject.put("lat", geoPoint.getLatitude());
                jSONObject.put("parentID", "");
                jSONObject.put("cityCode", geoPoint.getCity());
                jSONObject.put(AutoJsonUtils.JSON_ADCODE, geoPoint.getAdCode());
                jSONObject.put("end_poi_extension", "");
                jSONObject.put(H5Param.LONG_TRANSPARENT, "");
            } catch (JSONException e) {
                e = e;
            }
        } catch (JSONException e2) {
            e = e2;
            jSONObject = null;
            e.printStackTrace();
            return jSONObject;
        }
        return jSONObject;
    }

    public static JSONObject a(POI poi) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            try {
                jSONObject.putOpt(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId());
                jSONObject.putOpt("name", poi.getName());
                jSONObject.putOpt("address", poi.getAddr());
                jSONObject.putOpt("phoneNumbers", poi.getPhone());
                jSONObject.putOpt(DictionaryKeys.CTRLXY_X, Integer.valueOf(poi.getPoint().x));
                jSONObject.putOpt(DictionaryKeys.CTRLXY_Y, Integer.valueOf(poi.getPoint().y));
                jSONObject.putOpt(LocationParams.PARA_FLP_AUTONAVI_LON, Double.valueOf(poi.getPoint().getLongitude()));
                jSONObject.putOpt("lat", Double.valueOf(poi.getPoint().getLatitude()));
                jSONObject.putOpt("cityCode", poi.getCityCode());
                jSONObject.putOpt(AutoJsonUtils.JSON_ADCODE, poi.getAdCode());
                jSONObject.putOpt("end_poi_extension", poi.getEndPoiExtension());
                jSONObject.putOpt(H5Param.LONG_TRANSPARENT, poi.getTransparent());
            } catch (JSONException e) {
                e = e;
            }
        } catch (JSONException e2) {
            e = e2;
            jSONObject = null;
            e.printStackTrace();
            return jSONObject;
        }
        return jSONObject;
    }

    public static boolean a() {
        axv axv = (axv) a.a.a(axv.class);
        if (axv == null || axv.m() != 1) {
            return false;
        }
        return true;
    }
}
