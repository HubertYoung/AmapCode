package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.amap.bundle.blutils.platform.ShortCutUtil;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.minimap.R;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: cru reason: default package */
/* compiled from: SavePointUtils */
public final class cru {
    public static String a(bth bth) {
        if (bth == null || bth.a() == null) {
            return "";
        }
        FavoritePOI favoritePOI = (FavoritePOI) bth.a().as(FavoritePOI.class);
        if (!TextUtils.isEmpty(favoritePOI.getCommonName())) {
            return favoritePOI.getCommonName();
        }
        if (!TextUtils.isEmpty(favoritePOI.getCustomName())) {
            return favoritePOI.getCustomName();
        }
        return favoritePOI.getName();
    }

    private static String e(cpb cpb) {
        String str;
        if (cpb == null) {
            return crt.l;
        }
        String str2 = cpb.h;
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        String str3 = cpb.j;
        if (!TextUtils.isEmpty(str3)) {
            return a(str3);
        }
        if (cpb == null) {
            str = "8";
        } else {
            str = c(cpb.i);
        }
        return a(str);
    }

    public static String a(String str) {
        if (str.equals("1")) {
            return crt.e;
        }
        if (str.equals("2")) {
            return crt.f;
        }
        if (str.equals("3")) {
            return crt.g;
        }
        if (str.equals("4")) {
            return crt.h;
        }
        if (str.equals("5")) {
            return crt.i;
        }
        if (str.equals("6")) {
            return crt.j;
        }
        if (str.equals("7")) {
            return crt.k;
        }
        if (str.equals("8")) {
            return crt.l;
        }
        if (str.equals("9")) {
            return crt.m;
        }
        return crt.l;
    }

    private static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return "8";
        }
        String trim = str.trim();
        if (trim.length() < 6) {
            return "8";
        }
        String substring = trim.substring(0, 2);
        if (substring.equals("06") || substring.equals("07") || substring.equals("08") || substring.equals("09") || substring.equals("14") || substring.equals("16") || substring.equals("20") || substring.equals("97")) {
            return "2";
        }
        if (substring.equals("05")) {
            return "1";
        }
        if (substring.equals("11")) {
            return "3";
        }
        if (substring.equals("10")) {
            return "9";
        }
        if (substring.equals("12")) {
            String substring2 = trim.substring(2, 4);
            if (substring2.equals("00") || substring2.equals("01") || substring2.equals("02")) {
                return "4";
            }
            return substring2.equals("03") ? "7" : "8";
        } else if (substring.equals("17")) {
            return "4";
        } else {
            if (substring.equals("01") || substring.equals("02") || substring.equals("03") || substring.equals("04")) {
                return "5";
            }
            return (substring.equals("15") || substring.equals("18") || substring.equals("19") || substring.equals("99")) ? "6" : "8";
        }
    }

    public static String b(cpb cpb) {
        String e = e(cpb);
        String d = d(cpb);
        if (!TextUtils.isEmpty(d)) {
            StringBuilder sb = new StringBuilder();
            sb.append(e);
            sb.append("·");
            sb.append(d);
            return sb.toString();
        }
        String c = c(cpb);
        if (TextUtils.isEmpty(c)) {
            return e;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(e);
        sb2.append("·");
        sb2.append(c);
        return sb2.toString();
    }

    public static String c(cpb cpb) {
        if (cpb == null) {
            return "";
        }
        li a = li.a();
        String str = cpb.f;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String str2 = cpb.g;
        if (TextUtils.isEmpty(str2)) {
            return a(cpb, a);
        }
        try {
            lj a2 = a.a((int) Long.parseLong(str2));
            if (a2 == null) {
                a2 = a.a(str2);
            }
            if (a2 == null) {
                return a(cpb, a);
            }
            String str3 = a2.a;
            cpb.f = str3;
            return str3;
        } catch (Exception unused) {
            return "";
        }
    }

    public static int b(String str) {
        if (TextUtils.isEmpty(str)) {
            return R.drawable.save_qita;
        }
        String a = a(str);
        if (crt.e.equals(a)) {
            return R.drawable.save_meishi;
        }
        if (crt.f.equals(a)) {
            return R.drawable.save_shenghuo;
        }
        if (crt.g.equals(a)) {
            return R.drawable.save_lvyou;
        }
        if (crt.h.equals(a)) {
            return R.drawable.save_gongsi;
        }
        if (crt.i.equals(a)) {
            return R.drawable.save_qiche;
        }
        if (crt.j.equals(a)) {
            return R.drawable.save_chuxing;
        }
        if (crt.k.equals(a)) {
            return R.drawable.save_zhuzhai;
        }
        if (crt.m.equals(a)) {
            return R.drawable.save_jiudian;
        }
        return R.drawable.save_qita;
    }

    public static boolean b(bth bth) {
        if (bth == null || bth.a() == null) {
            return false;
        }
        String topTime = ((FavoritePOI) bth.a().as(FavoritePOI.class)).getTopTime();
        if (TextUtils.isEmpty(topTime)) {
            return false;
        }
        try {
            if (Double.parseDouble(topTime) <= 0.0d) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static void a(String str, bth bth) {
        if (bth != null && bth.a() != null) {
            FavoritePOI favoritePOI = (FavoritePOI) bth.a().as(FavoritePOI.class);
            if (TextUtils.isEmpty(str)) {
                str = favoritePOI.getName();
            }
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.isEmpty(favoritePOI.getCommonName())) {
                    favoritePOI.setCommonName(str);
                }
                favoritePOI.setCustomName(str);
            }
        }
    }

    public static void c(bth bth) {
        if (bth != null && bth.a() != null) {
            FavoritePOI favoritePOI = (FavoritePOI) bth.a().as(FavoritePOI.class);
            if (b(bth)) {
                String commonName = favoritePOI.getCommonName();
                if (!TextUtils.isEmpty(commonName) && !crt.b.equals(commonName) && !crt.c.equals(commonName)) {
                    favoritePOI.setCommonName(null);
                    favoritePOI.setCustomName(commonName);
                }
                favoritePOI.setTopTime(null);
                return;
            }
            favoritePOI.setTopTime(new DecimalFormat("#.######").format(System.currentTimeMillis() / 1000));
            String a = a(bth);
            if (!TextUtils.isEmpty(a)) {
                if (!TextUtils.isEmpty(favoritePOI.getCommonName())) {
                    favoritePOI.setCommonName(a);
                }
                favoritePOI.setCustomName(a);
            }
        }
    }

    public static Set<String> a(List<FavoritePOI> list) {
        HashSet hashSet = new HashSet();
        for (FavoritePOI next : list) {
            if (next != null && !crt.b.equals(next.getCommonName()) && !crt.c.equals(next.getCommonName())) {
                String tag = next.getTag();
                if (!TextUtils.isEmpty(tag)) {
                    hashSet.add(tag);
                }
            }
        }
        return hashSet;
    }

    public static boolean d(bth bth) {
        if (bth == null) {
            return false;
        }
        if (bth.a() == null) {
            return crt.a(bth.d);
        }
        return crt.a(((FavoritePOI) bth.a().as(FavoritePOI.class)).getCommonName());
    }

    private static boolean e(bth bth) {
        return bth == null || (bth.a() == null && crt.a(bth.d));
    }

    public static void a(POI poi) {
        if (poi != null) {
            FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
            String newType = favoritePOI.getNewType();
            if (TextUtils.isEmpty(newType)) {
                newType = favoritePOI.getType();
                if (!TextUtils.isEmpty(newType)) {
                    favoritePOI.setNewType(newType);
                }
            }
            if (TextUtils.isEmpty(favoritePOI.getClassification()) && !TextUtils.isEmpty(newType)) {
                String c = c(newType);
                if (!TextUtils.equals(c, "8")) {
                    favoritePOI.setClassification(c);
                }
            }
            li a = li.a();
            if (TextUtils.isEmpty(favoritePOI.getCityCode()) && favoritePOI.getPoint() != null) {
                String valueOf = String.valueOf(a.a(favoritePOI.getPoint().getLongitude(), favoritePOI.getPoint().getLatitude()));
                if (!TextUtils.isEmpty(valueOf)) {
                    favoritePOI.setCityCode(valueOf);
                }
            }
        }
    }

    public static void a(bth bth, final Context context) {
        final String str;
        if (bth != null && bth.a() != null) {
            FavoritePOI favoritePOI = (FavoritePOI) bth.a().as(FavoritePOI.class);
            if (e(bth)) {
                str = favoritePOI.getCommonName();
            } else {
                str = a(bth);
            }
            if (TextUtils.isEmpty(str)) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.shortcut_name_not_allowed_empty));
            } else if (!ShortCutUtil.isSupportCompat(context) || !b()) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.shortcut_not_support));
            } else {
                Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
                intent.putExtra("duplicate", false);
                intent.putExtra("android.intent.extra.shortcut.NAME", str);
                intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(AMapAppGlobal.getApplication(), R.drawable.ic_save_shortcut));
                bax bax = (bax) a.a.a(bax.class);
                String str2 = "";
                if (bax != null) {
                    str2 = bax.b();
                }
                Intent intent2 = new Intent("com.autonavi.minimap.ACTION", Uri.parse(a(favoritePOI, String.valueOf(dhw.d(str2)), String.valueOf(dhw.c(str2)))));
                intent2.putExtra("clearStack", "1");
                intent2.setClassName(AMapAppGlobal.getApplication(), Constant.LAUNCHER_ACTIVITY_NAME);
                intent2.setFlags(268435456);
                intent2.putExtra("name", str);
                intent2.putExtra("isFromShortcutNavi", true);
                intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
                ShortCutUtil.addShortcutCompat(context, intent);
                if (a()) {
                    aho.a(new Runnable() {
                        public final void run() {
                            Context context;
                            int i;
                            if (cru.a()) {
                                if (ShortCutUtil.hasShortCutCompat(context, str)) {
                                    context = context;
                                    i = R.string.shortcut_creat_success;
                                } else {
                                    context = context;
                                    i = R.string.shortcut_not_support;
                                }
                                ToastHelper.showLongToast(context.getString(i));
                            }
                        }
                    }, 1500);
                }
            }
        }
    }

    public static boolean a() {
        if (VERSION.SDK_INT >= 26) {
            return false;
        }
        String str = Build.MODEL;
        String str2 = Build.DEVICE;
        String str3 = Build.MANUFACTURER;
        if ("samsung".equals(str3) || ((LeakCanaryInternals.MOTOROLA.equals(str3) && "albus".equals(str2) && "XT1710-08".equals(str)) || (("Meizu".equals(str3) && "m2cnote".equals(str2) && "M571C".equals(str)) || "HUAWEI".equals(str3) || DeviceProperty.ALIAS_VIVO.equals(str3)))) {
            return false;
        }
        return true;
    }

    private static boolean b() {
        return !"smartisan".equals(Build.MANUFACTURER);
    }

    public static String a(POI poi, String str, String str2) {
        FavoritePOI favoritePOI;
        if (poi instanceof FavoritePOI) {
            favoritePOI = (FavoritePOI) poi;
        } else {
            favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
        }
        String str3 = "";
        String str4 = "";
        if (favoritePOI.getEntranceList() != null && favoritePOI.getEntranceList().size() > 0) {
            GeoPoint geoPoint = favoritePOI.getEntranceList().get(0);
            if (geoPoint != null) {
                str3 = String.valueOf(geoPoint.getLatitude());
                str4 = String.valueOf(geoPoint.getLongitude());
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("amapuri://drive/navi?sourceApplication=amap");
        stringBuffer.append("&poiid=");
        stringBuffer.append(favoritePOI.getId());
        stringBuffer.append("&poiname=");
        stringBuffer.append(favoritePOI.getName());
        stringBuffer.append("&lat=");
        stringBuffer.append(favoritePOI.getPoint().getLatitude());
        stringBuffer.append("&lon=");
        stringBuffer.append(favoritePOI.getPoint().getLongitude());
        stringBuffer.append("&dlat=");
        stringBuffer.append(str3);
        stringBuffer.append("&dlon=");
        stringBuffer.append(str4);
        stringBuffer.append("&newtype=");
        stringBuffer.append(favoritePOI.getNewType());
        stringBuffer.append("&parentid=");
        stringBuffer.append(favoritePOI.getParent());
        stringBuffer.append("&childtype=");
        stringBuffer.append(favoritePOI.getChildType());
        stringBuffer.append("&towardsangle=");
        stringBuffer.append(favoritePOI.getTowardsAngle());
        stringBuffer.append("&fnona=");
        stringBuffer.append(favoritePOI.getFnona());
        stringBuffer.append("&endpoiextension=");
        stringBuffer.append(favoritePOI.getEndPoiExtension());
        stringBuffer.append("&navitype=");
        stringBuffer.append(str);
        stringBuffer.append("&naviflag=");
        stringBuffer.append(str2);
        return stringBuffer.toString();
    }

    private static String d(cpb cpb) {
        if (cpb == null) {
            return "";
        }
        String str = cpb.l;
        return !TextUtils.isEmpty(str) ? str : "";
    }

    public static String a(cpb cpb) {
        if (cpb == null) {
            return "";
        }
        if (!TextUtils.isEmpty(cpb.k)) {
            return cpb.k;
        }
        if (!TextUtils.isEmpty(cpb.m)) {
            return cpb.m;
        }
        return cpb.c;
    }

    private static String a(cpb cpb, li liVar) {
        String valueOf = String.valueOf(liVar.a(cpb.d, cpb.e));
        cpb.g = valueOf;
        try {
            lj a = liVar.a((int) Long.parseLong(valueOf));
            if (a == null) {
                return "";
            }
            String str = a.a;
            cpb.f = str;
            return str;
        } catch (Exception unused) {
            return "";
        }
    }
}
