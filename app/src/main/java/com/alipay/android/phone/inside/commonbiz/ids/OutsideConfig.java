package com.alipay.android.phone.inside.commonbiz.ids;

import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.ids.model.BluetoothInfo;
import com.alipay.android.phone.inside.commonbiz.ids.model.CdmaModel;
import com.alipay.android.phone.inside.commonbiz.ids.model.GsmModel;
import com.alipay.android.phone.inside.commonbiz.ids.model.LocationInfo;
import com.alipay.android.phone.inside.commonbiz.ids.model.TelephoneInfo;
import com.alipay.android.phone.inside.commonbiz.ids.model.WifiInfo;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class OutsideConfig {
    private static LocationInfo a = null;
    private static BluetoothInfo b = null;
    private static TelephoneInfo c = null;
    private static List<WifiInfo> d = null;
    private static String e = null;
    private static boolean f = false;
    private static String g = "";
    private static String h = "";
    private static boolean i = false;
    private static boolean j = false;
    private static String k = "";
    private static String l = "";
    private static String m = "";
    private static boolean n = false;
    private static boolean o = false;
    private static String p;
    private static String q;

    public static String a() {
        return p;
    }

    public static String b() {
        return q;
    }

    public static LocationInfo c() {
        return a;
    }

    public static BluetoothInfo d() {
        return b;
    }

    public static TelephoneInfo e() {
        return c;
    }

    public static List<WifiInfo> f() {
        return d;
    }

    public static String g() {
        return g;
    }

    public static String h() {
        return h;
    }

    public static boolean i() {
        return i;
    }

    public static boolean j() {
        return j;
    }

    public static boolean k() {
        return f;
    }

    public static String l() {
        return e;
    }

    public static String m() {
        return k;
    }

    public static String n() {
        return m;
    }

    public static String o() {
        return l;
    }

    public static boolean p() {
        return o;
    }

    public static boolean q() {
        return n;
    }

    public static void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                i = jSONObject.optBoolean("isPrisonBreak", false);
                j = jSONObject.optBoolean("isTrojan", false);
                k = jSONObject.optString(Constants.KEY_SID, "");
                l = jSONObject.optString("havanaId", "");
                m = jSONObject.optString("appKey", "");
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "commonbiz", (String) "GetBaseModelFieldEx", th);
            }
            try {
                n = jSONObject.optBoolean("isThirdPartyApp", false);
                o = jSONObject.optBoolean("isOpenAuthLogin", false);
                p = jSONObject.optString("authToken", "");
                q = jSONObject.optString("alipayUserId", "");
            } catch (Throwable th2) {
                LoggerFactory.f().c((String) "inside", th2);
            }
            try {
                String optString = jSONObject.optString(GlobalConstants.SDK_ENV_INFO);
                if (!TextUtils.isEmpty(optString)) {
                    JSONObject jSONObject2 = new JSONObject(optString);
                    JSONObject optJSONObject = jSONObject2.optJSONObject("lbsInfo");
                    if (optJSONObject != null) {
                        LocationInfo locationInfo = new LocationInfo(optJSONObject.optString("speed", ""), optJSONObject.optString(CameraControllerManager.MY_POILOCATION_ACR, ""), optJSONObject.optString("altitude", ""), optJSONObject.optString("bearing", ""), optJSONObject.optString("latitude", ""), optJSONObject.optString("longitude", ""), optJSONObject.optString("time", ""), jSONObject2.optBoolean("lbsOpen"));
                        a = locationInfo;
                    } else {
                        LocationInfo locationInfo2 = new LocationInfo("", "", "", "", "", "", "", jSONObject2.optBoolean("lbsOpen"));
                        a = locationInfo2;
                    }
                    BluetoothInfo bluetoothInfo = new BluetoothInfo(jSONObject2.optString("bluetoothOpen"), jSONObject2.optString("bluetoothMac"));
                    if (bluetoothInfo.c()) {
                        b = bluetoothInfo;
                    }
                    ArrayList arrayList = null;
                    JSONArray optJSONArray = jSONObject2.optJSONArray("gsmInfos");
                    if (optJSONArray != null && optJSONArray.length() > 0) {
                        arrayList = new ArrayList();
                        for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                            JSONObject jSONObject3 = optJSONArray.getJSONObject(i2);
                            GsmModel gsmModel = new GsmModel(jSONObject3.optString("rssi"), jSONObject3.optString("mnc"), jSONObject3.optString("mcc"), jSONObject3.optString("cid"), jSONObject3.optString("lac"));
                            arrayList.add(gsmModel);
                        }
                    }
                    TelephoneInfo telephoneInfo = new TelephoneInfo(jSONObject2.optString("cellConn"), jSONObject2.optString("cellType"), arrayList, new CdmaModel(jSONObject2.optString("bsid"), jSONObject2.optString("nid"), jSONObject2.optString("rssi"), jSONObject2.optString(Constants.KEY_SID)), jSONObject2.optString("simOperator"));
                    if (telephoneInfo.d()) {
                        c = telephoneInfo;
                    }
                    JSONArray optJSONArray2 = jSONObject2.optJSONArray("wifis");
                    if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                        d = new ArrayList();
                        for (int i3 = 0; i3 < optJSONArray2.length(); i3++) {
                            JSONObject jSONObject4 = optJSONArray2.getJSONObject(i3);
                            d.add(new WifiInfo(jSONObject4.optString("wifiMac"), jSONObject4.optString("ssid"), jSONObject4.optString("rssi")));
                        }
                    }
                    f = jSONObject2.optBoolean("isWifiConn");
                    e = jSONObject2.optString("netType");
                    g = jSONObject.optString("pushDeviceId", "");
                    String optString2 = jSONObject.optString("currentUserPhoneNo");
                    if (!TextUtils.isEmpty(optString2)) {
                        h = optString2;
                    }
                }
            } catch (Throwable th3) {
                LoggerFactory.e().a((String) "commonbiz", (String) "BuildEnvInfoEx", th3);
            }
        }
    }
}
