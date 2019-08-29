package com.amap.bundle.drive.ajx.module;

import android.app.Application;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.drive.ajx.inter.CompleteReportInfoCallBack;
import com.amap.bundle.drive.ajx.inter.ICruiseEvent;
import com.amap.bundle.drive.ajx.inter.JsCommandCallback;
import com.amap.bundle.drive.ajx.inter.OnJsOpenCarSettingCallback;
import com.amap.bundle.drive.ajx.tools.DriveCarOwnerAjxTools;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.ae.ajx.tbt.CAjxBLBinaryCenter;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.db.model.Car;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.TtsManagerUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("drive_common_business")
@Keep
@KeepClassMembers
public class ModuleDriveCommonBusiness extends AbstractModule {
    public static final String MODULE_NAME = "drive_common_business";
    private static final String TAG = "ModuleDriveCommonBusiness";
    private CompleteReportInfoCallBack mCompleteReportInfoCallBack = null;
    private ICruiseEvent mCruiseEvent;
    private JsFunctionCallback mJsCarSettingChangeCallback = null;
    private ModuleDriveCommonBusinessImpl mModuleDriveCommonBusinessImpl = new ModuleDriveCommonBusinessImpl();
    private OnJsOpenCarSettingCallback mOnJsOpenCarSettingCallback = null;

    public ModuleDriveCommonBusiness(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "saveRouteHistory")
    public void saveRouteHistory(String str) {
        this.mModuleDriveCommonBusinessImpl.saveRouteHistory(str);
    }

    @AjxMethod(invokeMode = "sync", value = "syncGetIndividuationCar")
    public String syncGetIndividuationCar() {
        return this.mModuleDriveCommonBusinessImpl.syncGetIndividuationCar();
    }

    @AjxMethod(invokeMode = "sync", value = "usingMockLocation")
    public boolean usingMockLocation() {
        return this.mModuleDriveCommonBusinessImpl.usingMockLocation();
    }

    @AjxMethod(invokeMode = "sync", value = "saveContinueNavi")
    public void saveContinueNavi(String str) {
        this.mModuleDriveCommonBusinessImpl.saveContinueNavi(str);
    }

    @AjxMethod(invokeMode = "sync", value = "getHomeAndCompanyParamInfo")
    public String getHomeAndCompanyParamInfo() {
        return this.mModuleDriveCommonBusinessImpl.getHomeAndCompanyParamInfo();
    }

    @AjxMethod(invokeMode = "sync", value = "completeReportInfo")
    public void completeReportInfo() {
        this.mCompleteReportInfoCallBack.completeReportInfo();
    }

    @AjxMethod(invokeMode = "sync", value = "startFootPage")
    public void startFootPage(String str) {
        this.mModuleDriveCommonBusinessImpl.startFootPage(str);
    }

    @AjxMethod(invokeMode = "sync", value = "performanceLog")
    public void performanceLog(String str) {
        tj.a(str);
    }

    @AjxMethod("openCarSettingPage")
    public void openCarSettingPage(JsFunctionCallback jsFunctionCallback) {
        this.mJsCarSettingChangeCallback = jsFunctionCallback;
        if (this.mOnJsOpenCarSettingCallback != null) {
            this.mOnJsOpenCarSettingCallback.onOpenCarSetting();
        }
    }

    public void notifyCarSettingSuccess() {
        if (this.mJsCarSettingChangeCallback != null) {
            this.mJsCarSettingChangeCallback.callback(new Object[0]);
        }
    }

    public void setOnJsOpenCarSettingCallback(OnJsOpenCarSettingCallback onJsOpenCarSettingCallback) {
        this.mOnJsOpenCarSettingCallback = onJsOpenCarSettingCallback;
    }

    @AjxMethod("openTaxi")
    public void openTaxi(boolean z) {
        this.mModuleDriveCommonBusinessImpl.openTaxi(z);
    }

    @com.autonavi.minimap.ajx3.modules.AjxMethod(invokeMode = "sync", value = "getItem")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getItem(java.lang.String r18, java.lang.String r19) {
        /*
            r17 = this;
            r0 = r18
            r1 = r19
            boolean r2 = android.text.TextUtils.isEmpty(r18)
            if (r2 != 0) goto L_0x0631
            boolean r2 = android.text.TextUtils.isEmpty(r19)
            if (r2 == 0) goto L_0x0012
            goto L_0x0631
        L_0x0012:
            java.lang.String r2 = com.amap.bundle.drivecommon.tools.DriveSpUtil.NAMESPACE_MOTOR_SETTING
            boolean r2 = android.text.TextUtils.equals(r0, r2)
            r5 = 5
            r6 = 8
            r7 = 11
            r8 = 14
            r9 = 15
            r10 = 3
            r11 = 10
            r12 = -1
            r13 = 16
            r14 = 4
            r15 = 2
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L_0x01da
            int r2 = r19.hashCode()
            switch(r2) {
                case -2114037695: goto L_0x00e6;
                case -2005604470: goto L_0x00db;
                case -1099875196: goto L_0x00d0;
                case -1099277991: goto L_0x00c6;
                case -1094182908: goto L_0x00bb;
                case -782934843: goto L_0x00b1;
                case -687516341: goto L_0x00a7;
                case -646070427: goto L_0x009c;
                case -553449547: goto L_0x0092;
                case -326895843: goto L_0x0087;
                case 129329167: goto L_0x007c;
                case 456380466: goto L_0x0070;
                case 608042229: goto L_0x0064;
                case 977539764: goto L_0x0058;
                case 1586603448: goto L_0x004d;
                case 1700370468: goto L_0x0042;
                case 2124976815: goto L_0x0036;
                default: goto L_0x0034;
            }
        L_0x0034:
            goto L_0x00f0
        L_0x0036:
            java.lang.String r2 = "MOTOR_PATH_MAPVIEW_SCALEZOOM_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 10
            goto L_0x00f1
        L_0x0042:
            java.lang.String r2 = "MOTOR_PATH_BOARDCAST_TYPE_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 1
            goto L_0x00f1
        L_0x004d:
            java.lang.String r2 = "MOTOR_PATH_BOARDCAST_ROADINFO_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 3
            goto L_0x00f1
        L_0x0058:
            java.lang.String r2 = "MOTOR_PATH_NAVI_TRAFFIC"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 15
            goto L_0x00f1
        L_0x0064:
            java.lang.String r2 = "MOTOR_PATH_SHOW_TMC_GUIDE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 14
            goto L_0x00f1
        L_0x0070:
            java.lang.String r2 = "MOTOR_PATH_AUXILIARY_REALMAP_DOWNLOAD_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 11
            goto L_0x00f1
        L_0x007c:
            java.lang.String r2 = "MOTOR_PATH_BOARDCAST_CAMERA_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 2
            goto L_0x00f1
        L_0x0087:
            java.lang.String r2 = "MOTOR_PATH_HEADER_UP"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 8
            goto L_0x00f1
        L_0x0092:
            java.lang.String r2 = "MOTOR_PATH_BOARDCAST_INCALLING_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 5
            goto L_0x00f1
        L_0x009c:
            java.lang.String r2 = "MOTOR_PATH_AUXILIARY_LOWBRIGHT_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 13
            goto L_0x00f1
        L_0x00a7:
            java.lang.String r2 = "MOTOR_PATH_PREFERENCE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 0
            goto L_0x00f1
        L_0x00b1:
            java.lang.String r2 = "MOTOR_PATH_TTS_MIXED_MUSIC_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 6
            goto L_0x00f1
        L_0x00bb:
            java.lang.String r2 = "MOTOR_PATH_AUXILIARY_3DREALMAP_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 12
            goto L_0x00f1
        L_0x00c6:
            java.lang.String r2 = "MOTOR_PATH_MAPVIEW_NAVIVIEW_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 7
            goto L_0x00f1
        L_0x00d0:
            java.lang.String r2 = "MOTOR_PATH_SAFETY_SILENCE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 16
            goto L_0x00f1
        L_0x00db:
            java.lang.String r2 = "MOTOR_PATH_MAPVIEW_DAYNIGHT_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 9
            goto L_0x00f1
        L_0x00e6:
            java.lang.String r2 = "MOTOR_PATH_BOARDCAST_IMPROVE_VOICE_KEY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00f0
            r2 = 4
            goto L_0x00f1
        L_0x00f0:
            r2 = -1
        L_0x00f1:
            switch(r2) {
                case 0: goto L_0x01d5;
                case 1: goto L_0x01cc;
                case 2: goto L_0x01c3;
                case 3: goto L_0x01ba;
                case 4: goto L_0x01b1;
                case 5: goto L_0x01a8;
                case 6: goto L_0x019f;
                case 7: goto L_0x0196;
                case 8: goto L_0x017d;
                case 9: goto L_0x0174;
                case 10: goto L_0x016b;
                case 11: goto L_0x0162;
                case 12: goto L_0x0159;
                case 13: goto L_0x0150;
                case 14: goto L_0x0132;
                case 15: goto L_0x0114;
                case 16: goto L_0x00f6;
                default: goto L_0x00f4;
            }
        L_0x00f4:
            goto L_0x0617
        L_0x00f6:
            java.lang.String r0 = "MOTOR_PATH_SAFETY_SILENCE"
            java.lang.String r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getMotorConfigValue(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x010b
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            int r0 = r0.intValue()
            goto L_0x010c
        L_0x010b:
            r0 = 0
        L_0x010c:
            if (r0 != r4) goto L_0x010f
            r3 = 1
        L_0x010f:
            java.lang.String r0 = java.lang.String.valueOf(r3)
            return r0
        L_0x0114:
            java.lang.String r0 = "MOTOR_PATH_NAVI_TRAFFIC"
            java.lang.String r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getMotorConfigValue(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0129
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            int r0 = r0.intValue()
            goto L_0x012a
        L_0x0129:
            r0 = 0
        L_0x012a:
            if (r0 != r4) goto L_0x012d
            r3 = 1
        L_0x012d:
            java.lang.String r0 = java.lang.String.valueOf(r3)
            return r0
        L_0x0132:
            java.lang.String r0 = "MOTOR_PATH_SHOW_TMC_GUIDE"
            java.lang.String r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getMotorConfigValue(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0147
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            int r0 = r0.intValue()
            goto L_0x0148
        L_0x0147:
            r0 = 1
        L_0x0148:
            if (r0 != r4) goto L_0x014b
            r3 = 1
        L_0x014b:
            java.lang.String r0 = java.lang.String.valueOf(r3)
            return r0
        L_0x0150:
            int r0 = defpackage.re.g()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0159:
            int r0 = defpackage.re.j()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0162:
            int r0 = defpackage.re.i()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x016b:
            int r0 = defpackage.re.h()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0174:
            int r0 = defpackage.re.c()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x017d:
            java.lang.String r0 = "MOTOR_PATH_MAPVIEW_NAVIVIEW_KEY"
            java.lang.String r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getMotorConfigValue(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0191
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            int r4 = r0.intValue()
        L_0x0191:
            java.lang.String r0 = java.lang.String.valueOf(r4)
            return r0
        L_0x0196:
            int r0 = defpackage.re.d()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x019f:
            int r0 = defpackage.re.k()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x01a8:
            int r0 = defpackage.re.m()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x01b1:
            int r0 = defpackage.re.n()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x01ba:
            int r0 = defpackage.re.f()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x01c3:
            int r0 = defpackage.re.e()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x01cc:
            int r0 = defpackage.re.b()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x01d5:
            java.lang.String r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getMotorRoutingChoice()
            return r0
        L_0x01da:
            java.lang.String r2 = "NAMESPACE_CAR_ADAPTER"
            boolean r2 = android.text.TextUtils.equals(r0, r2)
            if (r2 == 0) goto L_0x0617
            int r2 = r19.hashCode()
            switch(r2) {
                case -2128183717: goto L_0x03bb;
                case -1961690291: goto L_0x03b0;
                case -1874837164: goto L_0x03a5;
                case -1872129037: goto L_0x039a;
                case -1816975776: goto L_0x0390;
                case -1642832865: goto L_0x0385;
                case -1579740154: goto L_0x037a;
                case -1564698847: goto L_0x036f;
                case -1496162289: goto L_0x0365;
                case -1172734868: goto L_0x0359;
                case -918767263: goto L_0x034e;
                case -896460031: goto L_0x0342;
                case -653089197: goto L_0x0336;
                case -334873538: goto L_0x032a;
                case -295031565: goto L_0x031e;
                case -51008560: goto L_0x0312;
                case 12299917: goto L_0x0306;
                case 30362667: goto L_0x02fa;
                case 60099291: goto L_0x02ee;
                case 264796226: goto L_0x02e2;
                case 500165374: goto L_0x02d6;
                case 543437940: goto L_0x02ca;
                case 596275677: goto L_0x02be;
                case 599970277: goto L_0x02b2;
                case 715314924: goto L_0x02a6;
                case 736701954: goto L_0x029b;
                case 743606530: goto L_0x028f;
                case 762090317: goto L_0x0283;
                case 900078236: goto L_0x0278;
                case 1065791054: goto L_0x026d;
                case 1072346279: goto L_0x0261;
                case 1173900213: goto L_0x0255;
                case 1367008985: goto L_0x0249;
                case 1441534923: goto L_0x023d;
                case 1459654689: goto L_0x0231;
                case 1521055326: goto L_0x0225;
                case 1524118486: goto L_0x021a;
                case 1692871857: goto L_0x020e;
                case 1770577224: goto L_0x0202;
                case 1841968549: goto L_0x01f6;
                case 1890077140: goto L_0x01eb;
                default: goto L_0x01e9;
            }
        L_0x01e9:
            goto L_0x03c6
        L_0x01eb:
            java.lang.String r2 = "KEY_SETUP_MAP_DIRECT_MODE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 3
            goto L_0x03c7
        L_0x01f6:
            java.lang.String r2 = "KEY_SETUP_IS_EAGLEYE_MODE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 12
            goto L_0x03c7
        L_0x0202:
            java.lang.String r2 = "KEY_SETUP_CROSS_REAL_DOWNLOAD"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 10
            goto L_0x03c7
        L_0x020e:
            java.lang.String r2 = "KEY_CONFIG_SAFETY_SILENCE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 29
            goto L_0x03c7
        L_0x021a:
            java.lang.String r2 = "KEY_SETUP_ACCEPT_BOARD_CALLING"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 6
            goto L_0x03c7
        L_0x0225:
            java.lang.String r2 = "KEY_CONFIG_NORESPONSIBILITY_SHOWN"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 27
            goto L_0x03c7
        L_0x0231:
            java.lang.String r2 = "KEY_CONFIG_SAFETY_SHARE_STATE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 35
            goto L_0x03c7
        L_0x023d:
            java.lang.String r2 = "KEY_CONFIG_SAFETY_SHARE_POPUP_OVER_100KM"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 37
            goto L_0x03c7
        L_0x0249:
            java.lang.String r2 = "KEY_SETUP_TRUCK_PATH_METHOD"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 38
            goto L_0x03c7
        L_0x0255:
            java.lang.String r2 = "KEY_SETUP_REAL_3D_NAVI"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 11
            goto L_0x03c7
        L_0x0261:
            java.lang.String r2 = "KEY_SETUP_SHOW_TMC_GUIDE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 24
            goto L_0x03c7
        L_0x026d:
            java.lang.String r2 = "KEY_SETUP_CAR_PATH_METHOD"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 1
            goto L_0x03c7
        L_0x0278:
            java.lang.String r2 = "KEY_SETUP_TTS_MIXD_MUSIC"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 7
            goto L_0x03c7
        L_0x0283:
            java.lang.String r2 = "KEY_SETUP_DAY_NIGHT_CHOICE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 8
            goto L_0x03c7
        L_0x028f:
            java.lang.String r2 = "KEY_SETUP_INTELLIGENT_ZOOM_LEVEL"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 9
            goto L_0x03c7
        L_0x029b:
            java.lang.String r2 = "KEY_SETUP_VOICE_BOARD_TYPE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 2
            goto L_0x03c7
        L_0x02a6:
            java.lang.String r2 = "KEY_CONFGI_SAFETY_SHARE_FUNCTION"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 34
            goto L_0x03c7
        L_0x02b2:
            java.lang.String r2 = "KEY_SETUP_VOICE_CONTROL_SWITCH"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 23
            goto L_0x03c7
        L_0x02be:
            java.lang.String r2 = "KEY_VUI_AUDIO_PERMISSION_DLG_COUNT"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 19
            goto L_0x03c7
        L_0x02ca:
            java.lang.String r2 = "KEY_CONFIG_CAR_PLATE_LAST_SETTING_TIME"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 31
            goto L_0x03c7
        L_0x02d6:
            java.lang.String r2 = "KEY_CONFIG_SAFETY_SHARE_POPUP_ALL"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 36
            goto L_0x03c7
        L_0x02e2:
            java.lang.String r2 = "KEY_SETUP_OFFLINE_PRIORITY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 25
            goto L_0x03c7
        L_0x02ee:
            java.lang.String r2 = "KEY_CONFIG_CAR_PLATE_OPEN_AVOID_LIMITED_NOTICE_COUNT"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 32
            goto L_0x03c7
        L_0x02fa:
            java.lang.String r2 = "KEY_CRUISE_SETUP_SAFE_REMIND"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 16
            goto L_0x03c7
        L_0x0306:
            java.lang.String r2 = "KEY_CONFIG_CAR_PLATE_SETTING_SHOW_COUNT"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 30
            goto L_0x03c7
        L_0x0312:
            java.lang.String r2 = "KEY_SETUP_TRUCK_RESTRICT_STATE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 39
            goto L_0x03c7
        L_0x031e:
            java.lang.String r2 = "KEY_SETUP_TRUCK_WEIGHT_RESTRICT_STATE"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 40
            goto L_0x03c7
        L_0x032a:
            java.lang.String r2 = "KEY_CONFIG_CAR_PLATE_OPEN_AVOID_LIMITED_LAST_NOTICE_TIME"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 33
            goto L_0x03c7
        L_0x0336:
            java.lang.String r2 = "KEY_CRUISE_SETUP_CAMERA_BROADCAST"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 14
            goto L_0x03c7
        L_0x0342:
            java.lang.String r2 = "KEY_CONFIG_DRIVE_RADDAR_CAMERA_PLAY"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 28
            goto L_0x03c7
        L_0x034e:
            java.lang.String r2 = "KEY_SETUP_ENABLE_CAMERA_BROADCAST"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 5
            goto L_0x03c7
        L_0x0359:
            java.lang.String r2 = "KEY_VOICE_GUIDE_IS_SHOWN"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 22
            goto L_0x03c7
        L_0x0365:
            java.lang.String r2 = "KEY_ROUTE_BOARD_RED_POINT_TIP"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 0
            goto L_0x03c7
        L_0x036f:
            java.lang.String r2 = "KEY_VUI_SWITCH_TOAST_COUNT"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 21
            goto L_0x03c7
        L_0x037a:
            java.lang.String r2 = "KEY_CRUISE_CONFIG_SHOW_CAMERA_LAYER"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 17
            goto L_0x03c7
        L_0x0385:
            java.lang.String r2 = "KEY_VUI_AUDIO_PERMISSION_DLG_TIME"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 18
            goto L_0x03c7
        L_0x0390:
            java.lang.String r2 = "KEY_SETUP_CAR_HEADER_UP"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 4
            goto L_0x03c7
        L_0x039a:
            java.lang.String r2 = "KEY_CRUISE_SETUP_TRAFFIC_BROADCAST"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 15
            goto L_0x03c7
        L_0x03a5:
            java.lang.String r2 = "KEY_SETUP_AUXILIARY_TRAFFICT_BROADCAST"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 13
            goto L_0x03c7
        L_0x03b0:
            java.lang.String r2 = "KEY_CONFIG_DIALECT_VOICE_OPEN"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 26
            goto L_0x03c7
        L_0x03bb:
            java.lang.String r2 = "KEY_VUI_SWITCH_TOAST_TIME"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x03c6
            r2 = 20
            goto L_0x03c7
        L_0x03c6:
            r2 = -1
        L_0x03c7:
            switch(r2) {
                case 0: goto L_0x0612;
                case 1: goto L_0x060d;
                case 2: goto L_0x05fe;
                case 3: goto L_0x05f1;
                case 4: goto L_0x05e2;
                case 5: goto L_0x05d3;
                case 6: goto L_0x05c4;
                case 7: goto L_0x05bb;
                case 8: goto L_0x05a1;
                case 9: goto L_0x0592;
                case 10: goto L_0x0583;
                case 11: goto L_0x0574;
                case 12: goto L_0x0569;
                case 13: goto L_0x055a;
                case 14: goto L_0x0549;
                case 15: goto L_0x0538;
                case 16: goto L_0x0527;
                case 17: goto L_0x051a;
                case 18: goto L_0x0509;
                case 19: goto L_0x04f8;
                case 20: goto L_0x04e7;
                case 21: goto L_0x04d6;
                case 22: goto L_0x04c5;
                case 23: goto L_0x04b2;
                case 24: goto L_0x04a6;
                case 25: goto L_0x049c;
                case 26: goto L_0x0497;
                case 27: goto L_0x048e;
                case 28: goto L_0x0481;
                case 29: goto L_0x0477;
                case 30: goto L_0x0466;
                case 31: goto L_0x0455;
                case 32: goto L_0x0444;
                case 33: goto L_0x0433;
                case 34: goto L_0x0426;
                case 35: goto L_0x0419;
                case 36: goto L_0x040c;
                case 37: goto L_0x03ff;
                case 38: goto L_0x03de;
                case 39: goto L_0x03d5;
                case 40: goto L_0x03cc;
                default: goto L_0x03ca;
            }
        L_0x03ca:
            goto L_0x0617
        L_0x03cc:
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getTruckAvoidLimitedLoad()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x03d5:
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getTruckAvoidSwitch()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x03de:
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r1 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.user_route_method_info
            r0.<init>(r1)
            java.lang.String r1 = "truck_method"
            java.lang.String r2 = "1"
            java.lang.String r0 = r0.getStringValue(r1, r2)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x03f6
            java.lang.String r0 = "1"
        L_0x03f6:
            java.lang.String r0 = defpackage.rc.b(r0)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x03ff:
            com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger r0 = com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger.getInst()
            boolean r0 = r0.isSharePopupOver100kmAlive()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x040c:
            com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger r0 = com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger.getInst()
            boolean r0 = r0.isSharePopupAllActive()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0419:
            com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger r0 = com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger.getInst()
            boolean r0 = r0.isShareStateActive()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0426:
            com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger r0 = com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger.getInst()
            boolean r0 = r0.isShareFunctionActive()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0433:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            long r1 = com.amap.bundle.drivecommon.tools.DriveUtil.getCarPlateOpenAvoidLimitedLastNoticeTime()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x0444:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            int r1 = com.amap.bundle.drivecommon.tools.DriveUtil.getCarPlateOpenAvoidLimitedNoticeCount()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x0455:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            long r1 = com.amap.bundle.drivecommon.tools.DriveUtil.getCarPlateLastSettingTime()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x0466:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            int r1 = com.amap.bundle.drivecommon.tools.DriveUtil.getCarPlateSettingShowCount()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x0477:
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveUtil.isPlaySafeHomeResponseInfo()
            r0 = r0 ^ r4
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0481:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getDriveRadderCameraPlay(r0)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x048e:
            boolean r0 = defpackage.tf.a()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0497:
            java.lang.String r0 = defpackage.rc.e()
            return r0
        L_0x049c:
            boolean r0 = defpackage.re.a()
            r0 = r0 ^ r4
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x04a6:
            java.lang.String r0 = "tmc_guide_switch"
            boolean r0 = defpackage.re.a(r0, r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x04b2:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "vcs_switch"
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getBool(r0, r1, r3)
            if (r0 == 0) goto L_0x04c2
            java.lang.String r0 = "1"
            return r0
        L_0x04c2:
            java.lang.String r0 = "0"
            return r0
        L_0x04c5:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = com.amap.bundle.drivecommon.tools.DriveUtil.getVoiceGuideIsShown()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x04d6:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = com.amap.bundle.drivecommon.tools.DriveUtil.getVUISwitchToastCount()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x04e7:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = com.amap.bundle.drivecommon.tools.DriveUtil.getVUISwitchToastTime()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x04f8:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = com.amap.bundle.drivecommon.tools.DriveUtil.getVUIAudioPermissionDlgCount()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x0509:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = com.amap.bundle.drivecommon.tools.DriveUtil.getVUIAudioPermissionDlgTime()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x051a:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = defpackage.nk.b(r0)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0527:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = defpackage.nk.a(r0)
            r0 = r0 & r14
            if (r0 != r14) goto L_0x0533
            r3 = 1
        L_0x0533:
            java.lang.String r0 = java.lang.String.valueOf(r3)
            return r0
        L_0x0538:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = defpackage.nk.a(r0)
            r0 = r0 & r15
            if (r0 != r15) goto L_0x0544
            r3 = 1
        L_0x0544:
            java.lang.String r0 = java.lang.String.valueOf(r3)
            return r0
        L_0x0549:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = defpackage.nk.a(r0)
            r0 = r0 & r4
            if (r0 != r4) goto L_0x0555
            r3 = 1
        L_0x0555:
            java.lang.String r0 = java.lang.String.valueOf(r3)
            return r0
        L_0x055a:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "play_route_traffic"
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getBool(r0, r1, r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0569:
            java.lang.String r0 = "eagle_setting_switch"
            boolean r0 = defpackage.re.a(r0, r3)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0574:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "real_3d_navigation"
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getBool(r0, r1, r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0583:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "DownloadIntersectionOfRealMap"
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getBool(r0, r1, r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x0592:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "ScaleAutoChange"
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getBool(r0, r1, r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x05a1:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = defpackage.re.b(r0)
            if (r13 != r0) goto L_0x05ad
            r0 = 0
            goto L_0x05b6
        L_0x05ad:
            r1 = 17
            if (r1 != r0) goto L_0x05b4
            r0 = 1065353216(0x3f800000, float:1.0)
            goto L_0x05b6
        L_0x05b4:
            r0 = 1073741824(0x40000000, float:2.0)
        L_0x05b6:
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x05bb:
            boolean r0 = defpackage.re.l()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x05c4:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "CallingSpeakTTS"
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getBool(r0, r1, r3)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x05d3:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "PlayEleEye"
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getBool(r0, r1, r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x05e2:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "NaviMapMode"
            boolean r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getBool(r0, r1, r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x05f1:
            java.lang.String r0 = "NaviMapMode"
            java.lang.String r1 = "SharedPreferences"
            boolean r0 = defpackage.tf.a(r1, r0, r4)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x05fe:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r1 = "ReportMode"
            int r0 = com.amap.bundle.drivecommon.tools.DriveSpUtil.getInt(r0, r1, r15)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            return r0
        L_0x060d:
            java.lang.String r0 = defpackage.rc.a()
            return r0
        L_0x0612:
            java.lang.String r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getRouteBoardRedPointTip()
            return r0
        L_0x0617:
            java.lang.String r2 = ""
            boolean r3 = android.text.TextUtils.isEmpty(r18)
            if (r3 != 0) goto L_0x0630
            boolean r3 = android.text.TextUtils.isEmpty(r19)
            if (r3 == 0) goto L_0x0626
            goto L_0x0630
        L_0x0626:
            com.amap.bundle.mapstorage.MapSharePreference r3 = new com.amap.bundle.mapstorage.MapSharePreference
            r3.<init>(r0)
            java.lang.String r0 = r3.getStringValue(r1, r2)
            return r0
        L_0x0630:
            return r2
        L_0x0631:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.ajx.module.ModuleDriveCommonBusiness.getItem(java.lang.String, java.lang.String):java.lang.String");
    }

    @AjxMethod(invokeMode = "sync", value = "getItems")
    public String getItems(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                JSONArray jSONArray = new JSONArray(str2);
                for (int i = 0; i < jSONArray.length(); i++) {
                    String str3 = (String) jSONArray.get(i);
                    String customItem = getCustomItem(str3);
                    if (customItem == null) {
                        customItem = getItem(str, str3);
                    }
                    jSONObject.put(str3, customItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    @AjxMethod(invokeMode = "sync", value = "setItem")
    public void setItem(String str, String str2, String str3) {
        ro.a(str, str2, str3);
    }

    @AjxMethod(invokeMode = "sync", value = "getCustomItem")
    public String getCustomItem(String str) {
        boolean z;
        String str2;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        Car car = null;
        if (TextUtils.equals(DriveSpUtil.NAMESPACE_CAR_ADAPTER, DriveSpUtil.NAMESPACE_CAR_ADAPTER)) {
            char c = 65535;
            int i = 1;
            switch (str.hashCode()) {
                case -2072007844:
                    if (str.equals(DriveSpUtil.KEY_SETUP_LOW_LIGHT_NAVI)) {
                        c = 13;
                        break;
                    }
                    break;
                case -1550975412:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_CAR_NAVI_TRAFFIC)) {
                        c = 8;
                        break;
                    }
                    break;
                case -1447709637:
                    if (str.equals(DriveSpUtil.KEY_SETUP_CAR_RESTRICT_STATE)) {
                        c = 10;
                        break;
                    }
                    break;
                case -1293358050:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_SAFETY_DESTINATION)) {
                        c = 6;
                        break;
                    }
                    break;
                case -1242356301:
                    if (str.equals(DriveSpUtil.KEY_SETUP_AROUND_SEARCH_ATM)) {
                        c = 15;
                        break;
                    }
                    break;
                case -1242351118:
                    if (str.equals(DriveSpUtil.KEY_SETUP_AROUND_SEARCH_GAS)) {
                        c = 14;
                        break;
                    }
                    break;
                case -1110601068:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_AIR_PRESSURE_GAUG)) {
                        c = 19;
                        break;
                    }
                    break;
                case -76575141:
                    if (str.equals(DriveSpUtil.KEY_SETUP_DIALECT_PLAY_NAME)) {
                        c = 21;
                        break;
                    }
                    break;
                case -55195325:
                    if (str.equals(DriveSpUtil.KEY_SETUP_DIALECT_SRC_CODE)) {
                        c = 20;
                        break;
                    }
                    break;
                case 65449759:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_SAFETY_REPORTID)) {
                        c = 7;
                        break;
                    }
                    break;
                case 229106041:
                    if (str.equals(DriveSpUtil.KEY_CONFGI_SAFETY_SHARE_POPUP_TIMEINTERVAL)) {
                        c = 4;
                        break;
                    }
                    break;
                case 336500750:
                    if (str.equals(DriveSpUtil.KEY_CRUISE_CONFIG_SHOW_TRAFFIC)) {
                        c = 2;
                        break;
                    }
                    break;
                case 400243320:
                    if (str.equals(DriveSpUtil.KEY_CRUISE_CONFIG_ENABLE_SILENCE)) {
                        c = 1;
                        break;
                    }
                    break;
                case 433090297:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_IS_SUPPORT_3D)) {
                        c = 17;
                        break;
                    }
                    break;
                case 611018010:
                    if (str.equals(DriveSpUtil.KEY_SETUP_RESTRICT_CAR_INFO)) {
                        c = 11;
                        break;
                    }
                    break;
                case 626217537:
                    if (str.equals(DriveSpUtil.KEY_SETUP_INCREASE_TTS_VOLUME)) {
                        c = 12;
                        break;
                    }
                    break;
                case 642279867:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_USER_INFO_CIFA)) {
                        c = 18;
                        break;
                    }
                    break;
                case 784561250:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_SAFETY_TIMEINTERVAL)) {
                        c = 5;
                        break;
                    }
                    break;
                case 996284207:
                    if (str.equals(DriveSpUtil.KEY_SETUP_RESTRICT_TRUCK_INFO)) {
                        c = 0;
                        break;
                    }
                    break;
                case 1201549320:
                    if (str.equals(DriveSpUtil.KEY_SETUP_BT_SOUND_CHANNEL)) {
                        c = 3;
                        break;
                    }
                    break;
                case 1359030877:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_MAP_TRAFFIC)) {
                        c = 9;
                        break;
                    }
                    break;
                case 1915570414:
                    if (str.equals(DriveSpUtil.KEY_REAL_DAY_NIGHT_MODE)) {
                        c = 16;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    ato ato = (ato) a.a.a(ato.class);
                    if (ato != null) {
                        car = ato.a().b(2);
                    }
                    String str3 = "";
                    if (car != null) {
                        str3 = DriveCarOwnerAjxTools.toJson(car, true);
                    }
                    return TextUtils.isEmpty(str3) ? "" : str3;
                case 1:
                    return PlaySoundUtils.getInstance().isSilent() ? "1" : "0";
                case 2:
                    return String.valueOf(DriveUtil.getTrafficMode(AMapAppGlobal.getApplication()) ? 1 : 0);
                case 3:
                    return String.valueOf(re.a((String) "speaker_paly_sound", false) ? 1 : 0);
                case 4:
                    return String.valueOf(DriveUtil.getSafeHomeActivityShownTime());
                case 5:
                    return String.valueOf(DriveSpUtil.getSafeHomeUploadTime(AMapAppGlobal.getApplication()) / 1000);
                case 6:
                    return rc.i();
                case 7:
                    String safeHomeShareId = DriveSpUtil.getSafeHomeShareId(AMapAppGlobal.getApplication());
                    if (TextUtils.isEmpty(safeHomeShareId)) {
                        safeHomeShareId = DriveSpUtil.getSafeHomeShareIdIn782(AMapAppGlobal.getApplication());
                    }
                    return safeHomeShareId;
                case 8:
                    if (new MapSharePreference((String) "SharedPreferences").contains("navi_traffic_state")) {
                        z = tf.a("SharedPreferences", "navi_traffic_state", false);
                    } else {
                        z = tf.a("SharedPreferences", "traffic_for_drive", false);
                        tf.b("SharedPreferences", "navi_traffic_state", z);
                    }
                    if (!z) {
                        i = 0;
                    }
                    return String.valueOf(i);
                case 9:
                    bqx bqx = (bqx) ank.a(bqx.class);
                    return bqx != null ? String.valueOf(bqx.a() ? 1 : 0) : "";
                case 10:
                    return String.valueOf(DriveUtil.isAvoidLimitedPath() ? 1 : 0);
                case 11:
                    Car carInfo = DriveUtil.getCarInfo();
                    String str4 = "";
                    if (carInfo != null) {
                        str4 = ((com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(carInfo)).toString();
                    }
                    return str4;
                case 12:
                    return String.valueOf(DriveSpUtil.getBool(AMapAppGlobal.getApplication(), DriveSpUtil.NAVIGATION_VOLUME_GAIN_SWITCH, false) ? 1 : 0);
                case 13:
                    return String.valueOf(DriveSpUtil.getBool(AMapAppGlobal.getApplication(), DriveSpUtil.LIGHT_INTENSITY, false) ? 1 : 0);
                case 14:
                    return bic.a((String) "ae8_oil_station_data").get("oil_station_value");
                case 15:
                    return bic.a((String) "ae8_bank_storage_data").get("bank_storage_value");
                case 16:
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
                    if (latestPosition == null || latestPosition.x == 0 || latestPosition.y == 0) {
                        ku.a().a((String) "DriveAjxSpUtils", (String) "getRealDayNight 定位点为null 默认为白天 ");
                        str2 = "1";
                    } else {
                        DPoint a = cfg.a((long) latestPosition.x, (long) latestPosition.y);
                        str2 = qz.a(a.x, a.y, LocationInstrument.getInstance().getLatestLocation()) ? "1" : "0";
                    }
                    return str2;
                case 17:
                    return rc.c();
                case 18:
                    return NetworkParam.getCifa();
                case 19:
                    return rc.d();
                case 20:
                    return rc.g();
                case 21:
                    return rc.f();
            }
        }
        return null;
    }

    @AjxMethod(invokeMode = "sync", value = "getMotorInfo")
    public static String getMotorInfo() {
        return DriveUtil.getMotorInfo();
    }

    @AjxMethod(invokeMode = "sync", value = "setMotorVoiceGain")
    public void setMotorVoiceGain(int i) {
        if (i == 1) {
            TtsManagerUtil.setVolumeGain(9);
        } else {
            rj.a().b();
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getCurrentVoiceSrcInfo")
    public String getCurrentVoiceSrcInfo() {
        return rc.h();
    }

    @AjxMethod(invokeMode = "sync", value = "getIsSupport3D")
    public String getIsSupport3D() {
        return rc.c();
    }

    @AjxMethod(invokeMode = "sync", value = "isUsingCustomSound")
    public String isUsingCustomSound() {
        return rc.e();
    }

    @AjxMethod(invokeMode = "sync", value = "isSupportHwPressure")
    public String isSupportHwPressure() {
        return rc.d();
    }

    @AjxMethod("removeBinaryDataS")
    public void removeBinaryDataS(int i) {
        CAjxBLBinaryCenter.removeBinaryDataS(i);
    }

    public void setCompleteReportInfoCallBack(CompleteReportInfoCallBack completeReportInfoCallBack) {
        this.mCompleteReportInfoCallBack = completeReportInfoCallBack;
    }

    public void setJsCommandCallback(JsCommandCallback jsCommandCallback) {
        this.mModuleDriveCommonBusinessImpl.setJsCommandCallback(jsCommandCallback);
    }

    public void setCruiseEventListener(ICruiseEvent iCruiseEvent) {
        this.mCruiseEvent = iCruiseEvent;
    }

    @AjxMethod(invokeMode = "sync", value = "getCutoutPosition")
    public int getCutoutPosition() {
        String b = lo.a().b("entirely_screen_config");
        int i = 4;
        try {
            if (!TextUtils.isEmpty(b)) {
                return new JSONObject(b).optInt("position", 4);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (VERSION.SDK_INT >= 28) {
            i = tt.c(AMapAppGlobal.getTopActivity());
        } else if (ts.a()) {
            i = 2;
        }
        return i;
    }

    @AjxMethod(invokeMode = "sync", value = "setCustomItem")
    public void setCustomItem(String str, String str2) {
        if (TextUtils.equals(DriveSpUtil.NAMESPACE_CAR_ADAPTER, DriveSpUtil.NAMESPACE_CAR_ADAPTER)) {
            char c = 65535;
            boolean z = false;
            switch (str.hashCode()) {
                case -2072007844:
                    if (str.equals(DriveSpUtil.KEY_SETUP_LOW_LIGHT_NAVI)) {
                        c = 19;
                        break;
                    }
                    break;
                case -1550975412:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_CAR_NAVI_TRAFFIC)) {
                        c = 8;
                        break;
                    }
                    break;
                case -1447709637:
                    if (str.equals(DriveSpUtil.KEY_SETUP_CAR_RESTRICT_STATE)) {
                        c = 22;
                        break;
                    }
                    break;
                case -1293358050:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_SAFETY_DESTINATION)) {
                        c = 6;
                        break;
                    }
                    break;
                case -1242356301:
                    if (str.equals(DriveSpUtil.KEY_SETUP_AROUND_SEARCH_ATM)) {
                        c = 17;
                        break;
                    }
                    break;
                case -1242351118:
                    if (str.equals(DriveSpUtil.KEY_SETUP_AROUND_SEARCH_GAS)) {
                        c = 18;
                        break;
                    }
                    break;
                case -1110601068:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_AIR_PRESSURE_GAUG)) {
                        c = 15;
                        break;
                    }
                    break;
                case -76575141:
                    if (str.equals(DriveSpUtil.KEY_SETUP_DIALECT_PLAY_NAME)) {
                        c = 11;
                        break;
                    }
                    break;
                case -55195325:
                    if (str.equals(DriveSpUtil.KEY_SETUP_DIALECT_SRC_CODE)) {
                        c = 10;
                        break;
                    }
                    break;
                case 65449759:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_SAFETY_REPORTID)) {
                        c = 7;
                        break;
                    }
                    break;
                case 229106041:
                    if (str.equals(DriveSpUtil.KEY_CONFGI_SAFETY_SHARE_POPUP_TIMEINTERVAL)) {
                        c = 4;
                        break;
                    }
                    break;
                case 264796226:
                    if (str.equals(DriveSpUtil.KEY_SETUP_OFFLINE_PRIORITY)) {
                        c = 12;
                        break;
                    }
                    break;
                case 336500750:
                    if (str.equals(DriveSpUtil.KEY_CRUISE_CONFIG_SHOW_TRAFFIC)) {
                        c = 2;
                        break;
                    }
                    break;
                case 400243320:
                    if (str.equals(DriveSpUtil.KEY_CRUISE_CONFIG_ENABLE_SILENCE)) {
                        c = 1;
                        break;
                    }
                    break;
                case 433090297:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_IS_SUPPORT_3D)) {
                        c = 13;
                        break;
                    }
                    break;
                case 611018010:
                    if (str.equals(DriveSpUtil.KEY_SETUP_RESTRICT_CAR_INFO)) {
                        c = 21;
                        break;
                    }
                    break;
                case 626217537:
                    if (str.equals(DriveSpUtil.KEY_SETUP_INCREASE_TTS_VOLUME)) {
                        c = 20;
                        break;
                    }
                    break;
                case 642279867:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_USER_INFO_CIFA)) {
                        c = 14;
                        break;
                    }
                    break;
                case 784561250:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_SAFETY_TIMEINTERVAL)) {
                        c = 5;
                        break;
                    }
                    break;
                case 996284207:
                    if (str.equals(DriveSpUtil.KEY_SETUP_RESTRICT_TRUCK_INFO)) {
                        c = 0;
                        break;
                    }
                    break;
                case 1201549320:
                    if (str.equals(DriveSpUtil.KEY_SETUP_BT_SOUND_CHANNEL)) {
                        c = 3;
                        break;
                    }
                    break;
                case 1359030877:
                    if (str.equals(DriveSpUtil.KEY_CONFIG_MAP_TRAFFIC)) {
                        c = 9;
                        break;
                    }
                    break;
                case 1915570414:
                    if (str.equals(DriveSpUtil.KEY_REAL_DAY_NIGHT_MODE)) {
                        c = 16;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return;
                case 1:
                    float parseFloat = Float.parseFloat(str2);
                    PlaySoundUtils instance = PlaySoundUtils.getInstance();
                    if (parseFloat == 1.0f) {
                        z = true;
                    }
                    instance.setSilent(z);
                    return;
                case 2:
                    float parseFloat2 = Float.parseFloat(str2);
                    Application application = AMapAppGlobal.getApplication();
                    if (parseFloat2 == 1.0f) {
                        z = true;
                    }
                    DriveSpUtil.setBool(application, "traffic", z);
                    return;
                case 3:
                    if (Float.parseFloat(str2) == 1.0f) {
                        z = true;
                    }
                    re.b((String) "speaker_paly_sound", z);
                    return;
                case 4:
                    DriveUtil.setSafeHomeActivityShownTime(Long.parseLong(str2));
                    return;
                case 5:
                    DriveSpUtil.setSafeHomeUploadTime(AMapAppGlobal.getApplication(), Long.parseLong(str2) * 1000);
                    return;
                case 6:
                    try {
                        JSONObject jSONObject = new JSONObject(str2);
                        int optInt = jSONObject.optInt(DictionaryKeys.CTRLXY_X);
                        int optInt2 = jSONObject.optInt(DictionaryKeys.CTRLXY_Y);
                        DriveSpUtil.setSafeHomeEndp20x(AMapAppGlobal.getApplication(), optInt);
                        DriveSpUtil.setSafeHomeEndp20y(AMapAppGlobal.getApplication(), optInt2);
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                case 7:
                    DriveSpUtil.setSafeHomeShareId(AMapAppGlobal.getApplication(), str2);
                    return;
                case 8:
                    if (Float.parseFloat(str2) == 1.0f) {
                        z = true;
                    }
                    tf.b("SharedPreferences", "navi_traffic_state", z);
                    return;
                case 9:
                    float parseFloat3 = Float.parseFloat(str2);
                    MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                    if (parseFloat3 == 1.0f) {
                        z = true;
                    }
                    mapSharePreference.putBooleanValue("traffic", z);
                    return;
                case 10:
                    return;
                case 11:
                    return;
                case 12:
                    return;
                case 13:
                    return;
                case 14:
                    return;
                case 15:
                    return;
                case 16:
                    return;
                case 17:
                    if (str2 != null) {
                        bic.a((String) "ae8_bank_storage_data").set("bank_storage_value", str2);
                    }
                    return;
                case 18:
                    if (str2 != null) {
                        bic.a((String) "ae8_oil_station_data").set("oil_station_value", str2);
                    }
                    return;
                case 19:
                    float parseFloat4 = Float.parseFloat(str2);
                    Application application2 = AMapAppGlobal.getApplication();
                    if (parseFloat4 == 1.0f) {
                        z = true;
                    }
                    re.d(application2, z);
                    return;
                case 20:
                    if (Float.parseFloat(str2) == 1.0f) {
                        z = true;
                    }
                    re.i(AMapAppGlobal.getApplication(), z);
                    if (z) {
                        TtsManagerUtil.setVolumeGain(9);
                        return;
                    } else {
                        rw.a(AMapAppGlobal.getApplication()).a();
                        return;
                    }
                case 21:
                    if (!TextUtils.isEmpty(str2) && str2.contains("plateNum")) {
                        try {
                            JSONObject jSONObject2 = new JSONObject(str2);
                            Car car = new Car();
                            car.plateNum = jSONObject2.getString("plateNum");
                            car.vehicleType = 1;
                            if (str2.contains("vehiclePowerType")) {
                                car.vehiclePowerType = jSONObject2.getInt("vehiclePowerType");
                            }
                            int i = 100;
                            ato ato = (ato) a.a.a(ato.class);
                            if (ato != null) {
                                i = ato.a().a(car);
                            }
                            if (i == 0) {
                                DriveUtil.setAvoidLimitedPath(true);
                            }
                            return;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    return;
                case 22:
                    if (Float.parseFloat(str2) == 1.0f) {
                        z = true;
                    }
                    DriveUtil.setAvoidLimitedPath(z);
                    break;
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "setMotorInfo")
    public static String setMotorInfo(String str) {
        boolean z = true;
        if (bim.aa().a((String) "201", (String) "601", str, 1) != 0) {
            z = false;
        }
        return z ? "1" : "0";
    }

    @AjxMethod(invokeMode = "sync", value = "setShowCameraLayer")
    public void setShowCameraLayer(String str) {
        ro.a(DriveSpUtil.NAMESPACE_CAR_ADAPTER, DriveSpUtil.KEY_CRUISE_CONFIG_SHOW_CAMERA_LAYER, str);
        if (this.mCruiseEvent != null) {
            this.mCruiseEvent.refreshCameraLayer();
        }
    }
}
