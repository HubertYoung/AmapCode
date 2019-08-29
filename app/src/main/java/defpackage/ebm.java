package defpackage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.db.helper.RouteHistoryDBHelper;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ebm reason: default package */
/* compiled from: RouteUtil */
public final class ebm {
    public static POI a() {
        try {
            com com2 = (com) ank.a(com.class);
            if (com2 == null) {
                return null;
            }
            cop b = com2.b(com2.a());
            if (b != null) {
                return b.c();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static POI b() {
        try {
            com com2 = (com) ank.a(com.class);
            if (com2 == null) {
                return null;
            }
            String a = com2.a();
            if (a != null) {
                cop b = com2.b(a);
                if (b != null) {
                    return b.d();
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(POI poi, POI poi2) {
        if (poi != null && !TextUtils.equals(poi.getName(), "") && poi2 != null && !TextUtils.equals(poi2.getName(), "") && !TextUtils.equals(poi.getName(), poi2.getName())) {
            btf btf = new btf();
            btf.c = Integer.valueOf(RouteType.COACH.getValue());
            btf.e = Integer.valueOf(poi.getPoint().x);
            btf.f = Integer.valueOf(poi.getPoint().y);
            btf.g = Integer.valueOf(poi2.getPoint().x);
            btf.h = Integer.valueOf(poi2.getPoint().y);
            btf.i = btf.a(poi);
            btf.k = btf.a(poi2);
            StringBuilder sb = new StringBuilder();
            sb.append(poi.getName());
            sb.append(" → ");
            sb.append(poi2.getName());
            btf.b = sb.toString();
            btf.d = "Null";
            a(btf, RouteType.COACH);
        }
    }

    public static void a(btf btf, RouteType routeType) {
        StringBuilder sb = new StringBuilder();
        sb.append(btf.b);
        sb.append(btf.c);
        btf.a = agy.a(sb.toString());
        RouteHistoryDBHelper instance = RouteHistoryDBHelper.getInstance(AMapPageUtil.getAppContext());
        instance.saveRouteHistory(btf);
        List<btf> historyList = RouteHistoryDBHelper.getInstance(AMapPageUtil.getAppContext()).getHistoryList(routeType.getValue());
        if (routeType != RouteType.TRAIN) {
            RouteType routeType2 = RouteType.COACH;
        }
        if (historyList != null) {
            if (historyList.size() > 20) {
                for (int i = 20; i < historyList.size(); i++) {
                    instance.deleteRouteHistory(historyList.get(i));
                }
            }
        }
    }

    public static <T> T a(T[] tArr, int i, T t) {
        return (tArr == null || i < 0 || i >= tArr.length) ? t : tArr[i];
    }

    private static boolean b(final Activity activity) {
        if (!agf.a() || agf.a(activity)) {
            return true;
        }
        AMapPageUtil.startAlertDialogPage(new Builder(activity).setTitle(R.string.open_navi_per).setPositiveButton(R.string.sure, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                try {
                    Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
                    intent.putExtra("extra_pkgname", "com.autonavi.minimap");
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    ToastHelper.showToast(activity.getString(R.string.autonavi_dlg_open_setting_failed));
                } catch (SecurityException e2) {
                    e2.printStackTrace();
                    ToastHelper.showToast(activity.getString(R.string.autonavi_dlg_open_setting_failed));
                }
            }
        }).setNegativeButton(R.string.cancel, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
            }
        }));
        return false;
    }

    public static boolean a(final Activity activity) {
        int c = bnz.c(activity);
        if (c != 100) {
            AMapPageUtil.startAlertDialogPage(new Builder(AMapPageUtil.getAppContext()).setTitle(c).setPositiveButton(R.string.route_setting, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    try {
                        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                        intent.setFlags(268435456);
                        activity.startActivityForResult(intent, 4098);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        ToastHelper.showLongToast(activity.getString(R.string.autonavi_dlg_open_setting_failed));
                    } catch (SecurityException e2) {
                        e2.printStackTrace();
                        ToastHelper.showLongToast(activity.getString(R.string.autonavi_dlg_open_setting_failed));
                    }
                }
            }).setNegativeButton(R.string.filter_cancel, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                }
            }));
            return false;
        } else if (!b(activity)) {
            return false;
        } else {
            return true;
        }
    }

    public static void a(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00018", "B034", jSONObject);
    }

    public static boolean c() {
        return edr.a() == 1;
    }

    public static void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            String str2 = "";
            IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
            if (iVoicePackageManager != null) {
                str2 = iVoicePackageManager.getCurrentTtsName2();
                if (TextUtils.isEmpty(str2)) {
                    str2 = iVoicePackageManager.getCurrentTtsName();
                }
            }
            if (TextUtils.isEmpty(str2)) {
                str2 = AMapPageUtil.getAppContext().getString(R.string.navi_voice_default);
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(TrafficUtil.KEYWORD, str2);
                jSONObject.put("from", str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00067", "B031", jSONObject);
        }
    }

    public static String d() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        return iVoicePackageManager != null ? iVoicePackageManager.getCurrentTtsSubName() : "";
    }

    public static boolean b(String str) {
        if (!TextUtils.isEmpty(str) && !Pattern.compile("[0-9]*").matcher(str).matches()) {
            return true;
        }
        return false;
    }

    public static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (!str.contains("(") || !str.contains(")")) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(40);
        if (lastIndexOf == 0) {
            return str;
        }
        return str.substring(0, lastIndexOf);
    }

    public static String e() {
        String str = "0";
        String a = lo.a().a((String) "switchtodrive");
        if (TextUtils.isEmpty(a)) {
            return str;
        }
        try {
            str = new JSONObject(a).optString("switchtodrive");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String f() {
        String str = "0";
        String a = lo.a().a((String) "timewarn");
        if (TextUtils.isEmpty(a)) {
            return str;
        }
        try {
            str = new JSONObject(a).optString("timewarn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static int a(POI poi) {
        int i = b(poi.getId()) ? 2 : -1;
        if ("我的位置".equals(poi.getName())) {
            return 0;
        }
        if (DriveUtil.MAP_PLACE_DES.equals(poi.getName()) || "地图选定位置".equals(poi.getName()) || DriveUtil.MAP_PLACE_DES_3.equals(poi.getName())) {
            return 1;
        }
        return i;
    }

    public static boolean g() {
        String a = lo.a().a((String) "electric_bike");
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        try {
            if (Integer.parseInt(new JSONObject(a).optString("electric_bike", "0")) == 1) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
