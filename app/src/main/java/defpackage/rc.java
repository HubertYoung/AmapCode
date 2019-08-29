package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: rc reason: default package */
/* compiled from: DriveAjxSpUtils */
public final class rc {
    public static String a() {
        return String.valueOf(b(new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue("car_method", "1")));
    }

    public static void a(String str) {
        String I = I(str);
        if (!TextUtils.isEmpty(I)) {
            new MapSharePreference(SharePreferenceName.user_route_method_info).putStringValue("car_method", I);
        }
    }

    public static String b() {
        return String.valueOf(b(new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue(DriveSpUtil.MOTOR_PATH_PREFERENCE, "1")));
    }

    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "1";
        }
        int i = 0;
        if (str.contains("2")) {
            i = 2;
        }
        if (str.contains("4")) {
            i += 4;
        }
        if (str.contains("8")) {
            i += 8;
        }
        if (str.contains("16")) {
            i += 16;
        }
        if (i == 0) {
            i = 1;
        }
        return String.valueOf(i);
    }

    private static String I(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.contains(".")) {
            str = str.substring(0, str.indexOf("."));
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String str2 = "";
        int parseInt = Integer.parseInt("0");
        int parseInt2 = Integer.parseInt("1");
        int parseInt3 = Integer.parseInt("2");
        int parseInt4 = Integer.parseInt("4");
        int parseInt5 = Integer.parseInt("8");
        int parseInt6 = Integer.parseInt("16");
        int parseInt7 = Integer.parseInt(str);
        if (parseInt7 == parseInt) {
            return "0";
        }
        if (parseInt7 == parseInt2) {
            return "1";
        }
        if (parseInt7 == parseInt3) {
            return "2";
        }
        if (parseInt7 == parseInt4) {
            return "4";
        }
        if (parseInt7 == parseInt5) {
            return "8";
        }
        if (parseInt7 == parseInt6) {
            return "16";
        }
        if ((parseInt7 & parseInt3) == parseInt3) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("|2");
            str2 = sb.toString();
        }
        if ((parseInt7 & parseInt4) == parseInt4) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append("|4");
            str2 = sb2.toString();
        }
        if ((parseInt7 & parseInt5) == parseInt5) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str2);
            sb3.append("|8");
            str2 = sb3.toString();
        }
        if ((parseInt7 & parseInt6) == parseInt6) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str2);
            sb4.append("|16");
            str2 = sb4.toString();
        }
        if (!TextUtils.isEmpty(str2) && str2.startsWith(MergeUtil.SEPARATOR_KV)) {
            str2 = str2.substring(1, str2.length());
        }
        return str2;
    }

    public static void c(String str) {
        DriveUtil.setTruckAvoidSwitch(Float.parseFloat(str) == 1.0f);
    }

    public static void d(String str) {
        DriveUtil.setTruckAvoidLimitedLoad(Float.parseFloat(str) == 1.0f);
    }

    public static void e(String str) {
        String I = I(str);
        if (!TextUtils.isEmpty(I)) {
            new MapSharePreference(SharePreferenceName.user_route_method_info).putStringValue(DriveSpUtil.TRUCK_METHOD, I);
        }
    }

    public static void f(String str) {
        re.a((Context) AMapAppGlobal.getApplication(), (int) Float.parseFloat(str));
    }

    public static void g(String str) {
        tf.a(Float.parseFloat(str) == 1.0f);
    }

    public static void h(String str) {
        re.a((Context) AMapAppGlobal.getApplication(), Float.parseFloat(str) == 1.0f);
    }

    public static void i(String str) {
        re.b((Context) AMapAppGlobal.getApplication(), Float.parseFloat(str) == 1.0f);
    }

    public static void j(String str) {
        re.c(AMapAppGlobal.getApplication(), Float.parseFloat(str) == 1.0f);
    }

    public static void k(String str) {
        boolean z = Float.parseFloat(str) == 1.0f;
        re.h(AMapAppGlobal.getApplication(), z);
        PlaySoundUtils.getInstance().setCallingSpeakTTS(z);
    }

    public static void l(String str) {
        re.b((String) "eagle_setting_switch", Float.parseFloat(str) == 1.0f);
    }

    public static void m(String str) {
        re.g(AMapAppGlobal.getApplication(), Float.parseFloat(str) == 1.0f);
    }

    public static void n(String str) {
        re.f(AMapAppGlobal.getApplication(), Float.parseFloat(str) == 1.0f);
    }

    public static void o(String str) {
        re.e(AMapAppGlobal.getApplication(), Float.parseFloat(str) == 1.0f);
    }

    public static void p(String str) {
        float parseFloat = Float.parseFloat(str);
        int i = 0.0f == parseFloat ? 16 : 1.0f == parseFloat ? 17 : 18;
        re.b((Context) AMapAppGlobal.getApplication(), i);
    }

    public static String c() {
        return String.valueOf(ro.a() ^ true ? 1 : 0);
    }

    public static String d() {
        new rq();
        return String.valueOf(rq.b() ? 1 : 0);
    }

    public static void q(String str) {
        re.b((String) "tmc_guide_switch", Float.parseFloat(str) == 1.0f);
    }

    public static void r(String str) {
        boolean z = Float.parseFloat(str) == 1.0f;
        re.a(z);
        PlaySoundUtils.getInstance().setTTSMixedMusic(z);
    }

    public static void s(String str) {
        DriveSpUtil.setDriveRadderCameraPlay(AMapAppGlobal.getApplication(), Float.parseFloat(str) == 1.0f);
    }

    public static String h() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("code", g());
            jSONObject.putOpt("name", f());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static String i() {
        int safeHomeEndp20x = DriveSpUtil.getSafeHomeEndp20x(AMapAppGlobal.getApplication());
        int safeHomeEndp20y = DriveSpUtil.getSafeHomeEndp20y(AMapAppGlobal.getApplication());
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(DictionaryKeys.CTRLXY_X, safeHomeEndp20x);
            jSONObject.put(DictionaryKeys.CTRLXY_Y, safeHomeEndp20y);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static void t(String str) {
        DriveUtil.SetIsPlaySafeHomeResponseInfo(Float.parseFloat(str) != 1.0f);
    }

    public static void u(String str) {
        if (!TextUtils.isEmpty(str)) {
            DriveUtil.setCarPlateSettingShowCount(Integer.parseInt(str));
        }
    }

    public static void v(String str) {
        if (!TextUtils.isEmpty(str)) {
            DriveUtil.setCarPlateOpenAvoidLimitedNoticeCount(Integer.parseInt(str));
        }
    }

    public static void w(String str) {
        if (!TextUtils.isEmpty(str)) {
            DriveUtil.setCarPlateLastSettingTime(Long.parseLong(str));
        }
    }

    public static void x(String str) {
        if (!TextUtils.isEmpty(str)) {
            DriveUtil.setCarPlateOpenAvoidLimitedLastNoticeTime(Long.parseLong(str));
        }
    }

    public static void y(String str) {
        if (!TextUtils.isEmpty(str)) {
            re.k(AMapAppGlobal.getApplication(), Float.valueOf(str).floatValue() == 1.0f);
        }
    }

    public static void z(String str) {
        if (!TextUtils.isEmpty(str)) {
            DriveUtil.setVoiceGuideIsShown(Float.parseFloat(str));
        }
    }

    public static void A(String str) {
        if (!TextUtils.isEmpty(str)) {
            DriveUtil.setVUISwitchToastCount(Integer.parseInt(str));
        }
    }

    public static void B(String str) {
        if (!TextUtils.isEmpty(str)) {
            DriveUtil.setVUISwitchToastTime(Long.parseLong(str));
        }
    }

    public static void C(String str) {
        if (!TextUtils.isEmpty(str)) {
            DriveUtil.setVUIAudioPermissionDlgCount(Integer.parseInt(str));
        }
    }

    public static void D(String str) {
        if (!TextUtils.isEmpty(str)) {
            DriveUtil.setVUIAudioPermissionDlgTime(Long.parseLong(str));
        }
    }

    public static void E(String str) {
        float parseFloat = Float.parseFloat(str);
        int a = nk.a(AMapAppGlobal.getApplication());
        nk.a((Context) AMapAppGlobal.getApplication(), parseFloat == 1.0f ? a | 1 : a & 6);
    }

    public static void F(String str) {
        float parseFloat = Float.parseFloat(str);
        int a = nk.a(AMapAppGlobal.getApplication());
        nk.a((Context) AMapAppGlobal.getApplication(), parseFloat == 1.0f ? a | 4 : a & 3);
    }

    public static void G(String str) {
        float parseFloat = Float.parseFloat(str);
        int a = nk.a(AMapAppGlobal.getApplication());
        nk.a((Context) AMapAppGlobal.getApplication(), parseFloat == 1.0f ? a | 2 : a & 5);
    }

    public static void H(String str) {
        float parseFloat = Float.parseFloat(str);
        AMapAppGlobal.getApplication().getSharedPreferences("Traffic_Config", 0).edit().putInt("key_edog_show_camera_layer", (int) parseFloat).apply();
    }

    public static String e() {
        return String.valueOf(a.a.a() ? 1 : 0);
    }

    public static String f() {
        afz afz = (afz) a.a.a(afz.class);
        if (afz != null) {
            return afz.a();
        }
        return "";
    }

    public static String g() {
        afz afz = (afz) a.a.a(afz.class);
        if (afz != null) {
            return afz.b();
        }
        return "";
    }
}
