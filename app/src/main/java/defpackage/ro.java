package defpackage;

import android.location.Location;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alipay.mobile.security.faceauth.api.AntDetector;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3DialogPage;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ro reason: default package */
/* compiled from: NavigationUtil */
public final class ro {
    public static boolean a(Location location) {
        return location != null && location.getLatitude() > 0.0d && location.getLongitude() > 0.0d;
    }

    public static GeoPoint[] a(double[] dArr) {
        if (dArr == null || dArr.length <= 1) {
            return null;
        }
        int length = dArr.length;
        GeoPoint[] geoPointArr = new GeoPoint[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            geoPointArr[i / 2] = new GeoPoint(dArr[i], dArr[i + 1]);
        }
        return geoPointArr;
    }

    public static void b() {
        new re();
        re.a((POI) null);
        re.a((List<POI>) null);
        re.a(-1, -1);
        re.b((String) "navigation_navitype_at_exception", (String) "");
        re.b((String) "car_navi_sourceapplication", (String) "");
    }

    public static void c() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("url", ModuleRouteDriveResult.MOTOR_ADD);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult(Ajx3DialogPage.class, pageBundle, (int) AntDetector.ACTION_ID_LOGIN);
        }
    }

    private static void c(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            new MapSharePreference(str).putStringValue(str2, str3);
        }
    }

    public static void a(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            if (TextUtils.equals(str, DriveSpUtil.NAMESPACE_CAR_ADAPTER)) {
                char c = 65535;
                switch (str2.hashCode()) {
                    case -2128183717:
                        if (str2.equals(DriveSpUtil.KEY_VUI_SWITCH_TOAST_TIME)) {
                            c = 20;
                            break;
                        }
                        break;
                    case -1961690291:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_DIALECT_VOICE_OPEN)) {
                            c = 25;
                            break;
                        }
                        break;
                    case -1874837164:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_AUXILIARY_TRAFFICT_BROADCAST)) {
                            c = 13;
                            break;
                        }
                        break;
                    case -1872129037:
                        if (str2.equals(DriveSpUtil.KEY_CRUISE_SETUP_TRAFFIC_BROADCAST)) {
                            c = 15;
                            break;
                        }
                        break;
                    case -1816975776:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_CAR_HEADER_UP)) {
                            c = 4;
                            break;
                        }
                        break;
                    case -1642832865:
                        if (str2.equals(DriveSpUtil.KEY_VUI_AUDIO_PERMISSION_DLG_TIME)) {
                            c = 18;
                            break;
                        }
                        break;
                    case -1579740154:
                        if (str2.equals(DriveSpUtil.KEY_CRUISE_CONFIG_SHOW_CAMERA_LAYER)) {
                            c = 17;
                            break;
                        }
                        break;
                    case -1564698847:
                        if (str2.equals(DriveSpUtil.KEY_VUI_SWITCH_TOAST_COUNT)) {
                            c = 21;
                            break;
                        }
                        break;
                    case -1496162289:
                        if (str2.equals(DriveSpUtil.KEY_ROUTE_BOARD_RED_POINT_TIP)) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1172734868:
                        if (str2.equals(DriveSpUtil.KEY_VOICE_GUIDE_IS_SHOWN)) {
                            c = 22;
                            break;
                        }
                        break;
                    case -918767263:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_ENABLE_CAMERA_BROADCAST)) {
                            c = 5;
                            break;
                        }
                        break;
                    case -896460031:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_DRIVE_RADDAR_CAMERA_PLAY)) {
                            c = 27;
                            break;
                        }
                        break;
                    case -653089197:
                        if (str2.equals(DriveSpUtil.KEY_CRUISE_SETUP_CAMERA_BROADCAST)) {
                            c = 14;
                            break;
                        }
                        break;
                    case -334873538:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_CAR_PLATE_OPEN_AVOID_LIMITED_LAST_NOTICE_TIME)) {
                            c = ' ';
                            break;
                        }
                        break;
                    case -295031565:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_TRUCK_WEIGHT_RESTRICT_STATE)) {
                            c = '\'';
                            break;
                        }
                        break;
                    case -51008560:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_TRUCK_RESTRICT_STATE)) {
                            c = '&';
                            break;
                        }
                        break;
                    case 12299917:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_CAR_PLATE_SETTING_SHOW_COUNT)) {
                            c = 29;
                            break;
                        }
                        break;
                    case 30362667:
                        if (str2.equals(DriveSpUtil.KEY_CRUISE_SETUP_SAFE_REMIND)) {
                            c = 16;
                            break;
                        }
                        break;
                    case 60099291:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_CAR_PLATE_OPEN_AVOID_LIMITED_NOTICE_COUNT)) {
                            c = 31;
                            break;
                        }
                        break;
                    case 500165374:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_SAFETY_SHARE_POPUP_ALL)) {
                            c = '#';
                            break;
                        }
                        break;
                    case 543437940:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_CAR_PLATE_LAST_SETTING_TIME)) {
                            c = 30;
                            break;
                        }
                        break;
                    case 596275677:
                        if (str2.equals(DriveSpUtil.KEY_VUI_AUDIO_PERMISSION_DLG_COUNT)) {
                            c = 19;
                            break;
                        }
                        break;
                    case 599970277:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_VOICE_CONTROL_SWITCH)) {
                            c = 23;
                            break;
                        }
                        break;
                    case 715314924:
                        if (str2.equals(DriveSpUtil.KEY_CONFGI_SAFETY_SHARE_FUNCTION)) {
                            c = '!';
                            break;
                        }
                        break;
                    case 736701954:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_VOICE_BOARD_TYPE)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 743606530:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_INTELLIGENT_ZOOM_LEVEL)) {
                            c = 9;
                            break;
                        }
                        break;
                    case 762090317:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_DAY_NIGHT_CHOICE)) {
                            c = 8;
                            break;
                        }
                        break;
                    case 900078236:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_TTS_MIXD_MUSIC)) {
                            c = 7;
                            break;
                        }
                        break;
                    case 1065791054:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_CAR_PATH_METHOD)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1072346279:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_SHOW_TMC_GUIDE)) {
                            c = 24;
                            break;
                        }
                        break;
                    case 1173900213:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_REAL_3D_NAVI)) {
                            c = 11;
                            break;
                        }
                        break;
                    case 1367008985:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_TRUCK_PATH_METHOD)) {
                            c = '%';
                            break;
                        }
                        break;
                    case 1441534923:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_SAFETY_SHARE_POPUP_OVER_100KM)) {
                            c = '$';
                            break;
                        }
                        break;
                    case 1459654689:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_SAFETY_SHARE_STATE)) {
                            c = '\"';
                            break;
                        }
                        break;
                    case 1521055326:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_NORESPONSIBILITY_SHOWN)) {
                            c = JSONLexer.EOI;
                            break;
                        }
                        break;
                    case 1524118486:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_ACCEPT_BOARD_CALLING)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 1692871857:
                        if (str2.equals(DriveSpUtil.KEY_CONFIG_SAFETY_SILENCE)) {
                            c = 28;
                            break;
                        }
                        break;
                    case 1770577224:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_CROSS_REAL_DOWNLOAD)) {
                            c = 10;
                            break;
                        }
                        break;
                    case 1841968549:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_IS_EAGLEYE_MODE)) {
                            c = 12;
                            break;
                        }
                        break;
                    case 1890077140:
                        if (str2.equals(DriveSpUtil.KEY_SETUP_MAP_DIRECT_MODE)) {
                            c = 3;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        DriveUtil.setRouteBoardRedPointTip(str3);
                        return;
                    case 1:
                        rc.a(str3);
                        return;
                    case 2:
                        rc.f(str3);
                        return;
                    case 3:
                        rc.g(str3);
                        return;
                    case 4:
                        rc.h(str3);
                        return;
                    case 5:
                        rc.i(str3);
                        return;
                    case 6:
                        rc.k(str3);
                        return;
                    case 7:
                        rc.r(str3);
                        return;
                    case 8:
                        rc.p(str3);
                        return;
                    case 9:
                        rc.o(str3);
                        return;
                    case 10:
                        rc.n(str3);
                        return;
                    case 11:
                        rc.m(str3);
                        return;
                    case 12:
                        rc.l(str3);
                        return;
                    case 13:
                        rc.j(str3);
                        return;
                    case 14:
                        rc.E(str3);
                        return;
                    case 15:
                        rc.G(str3);
                        return;
                    case 16:
                        rc.F(str3);
                        return;
                    case 17:
                        rc.H(str3);
                        return;
                    case 18:
                        rc.D(str3);
                        return;
                    case 19:
                        rc.C(str3);
                        return;
                    case 20:
                        rc.B(str3);
                        return;
                    case 21:
                        rc.A(str3);
                        return;
                    case 22:
                        rc.z(str3);
                        return;
                    case 23:
                        rc.y(str3);
                        return;
                    case 24:
                        rc.q(str3);
                        return;
                    case 25:
                        return;
                    case 26:
                        return;
                    case 27:
                        rc.s(str3);
                        return;
                    case 28:
                        rc.t(str3);
                        return;
                    case 29:
                        rc.u(str3);
                        return;
                    case 30:
                        rc.w(str3);
                        return;
                    case 31:
                        rc.v(str3);
                        return;
                    case ' ':
                        rc.x(str3);
                        return;
                    case '!':
                        return;
                    case '\"':
                        return;
                    case '#':
                        return;
                    case '$':
                        return;
                    case '%':
                        rc.e(str3);
                        return;
                    case '&':
                        rc.c(str3);
                        return;
                    case '\'':
                        rc.d(str3);
                        return;
                }
            }
            c(str, str2, str3);
        }
    }

    public static void b(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            new MapSharePreference(str).putStringValue(str2, str3);
        }
    }

    public static int d() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("motorPlateNum", "");
            jSONObject.put("sweptVolume", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bim.aa().a((String) "201", (String) "601", jSONObject.toString(), 1);
    }

    public static String e() {
        String motorConfigValue = DriveUtil.getMotorConfigValue(DriveSpUtil.MOTOR_PATH_PREFERENCE);
        return TextUtils.isEmpty(motorConfigValue) ? "1" : motorConfigValue;
    }

    public static void a(int i) {
        b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_LIMIT_KEY, String.valueOf(i));
    }

    public static boolean f() {
        TelephonyManager telephonyManager = (TelephonyManager) AMapAppGlobal.getApplication().getSystemService("phone");
        return (telephonyManager == null || telephonyManager.getCallState() == 0) ? false : true;
    }

    public static boolean g() {
        String a = lo.a().a((String) "navi_cloud");
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        try {
            return TextUtils.equals("1", new JSONObject(a).optString("orientation_change"));
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean h() {
        String a = lo.a().a((String) "locked_show_able");
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        try {
            if (!TextUtils.equals("1", new JSONObject(a).optString("isShow")) || VERSION.SDK_INT < 28) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean a() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (latestPosition == null) {
            bty mapView = DoNotUseTool.getMapView();
            if (mapView != null) {
                return iOfflineManager.checkOfflineCity((int) li.a().a(mapView.p(), mapView.q()));
            }
        }
        int i = 0;
        if (latestPosition != null) {
            i = latestPosition.getAdCode();
        }
        return iOfflineManager.checkOfflineCity(i);
    }
}
