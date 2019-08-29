package com.autonavi.minimap.ajx3.upgrade;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.sdk.authjs.a;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.upgrade.debugupload.DebugAjxCrashUploadParam;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class Ajx3ActionLogUtil {
    private static final String AJX_BUNDLE_PAGE_ID = "ajx_bundles";
    static final int APPEND_ERROR = 9;
    static final int APPEND_SUCCESS = 10;
    static final int CHECK_AJX_ERR = 5;
    static final int CHECK_UPDATE_ERR = 11;
    static final int CHECK_UPDATE_REQUEST = 15;
    static final int CHECK_UPDATE_RESPONSE = 16;
    private static final int CSS_ERR = 3;
    static final int DOWNLOAD_ERR = 4;
    private static final int JS_ERR = 1;
    private static final int LOAD_AJX_RESOURCE_ERR = 7;
    static final int MOVE_ERROR = 8;
    private static final int OPEN_AJX_ERR = 6;
    public static final int PAGE_LOAD = 17;
    public static final int SCHEME_AJX_LOADING = 18;
    public static final int SCHEME_AJX_LOADING_CANCEL = 19;
    public static final int SCHEME_AJX_LOADING_FINISHED = 20;
    private static final int SPX_AJXVERSION_NOTMATCH = -4;
    private static final int SPX_AJX_INVALIED_PUBKEY = -11;
    private static final int SPX_BASE_AJX_INVALIED_PUBKEY = -12;
    private static final int SPX_BASE_INVALIED_SIGNATURE = -9;
    private static final int SPX_INVALIED_SIGNATURE = -8;
    private static final int SPX_MALLOC_FAILIED = -14;
    private static final int SPX_NO_AJX = -2;
    private static final int SPX_NO_BASEAJX = -3;
    private static final int SPX_OPEN_FAILED = -1;
    private static final int SPX_UPDATE_INVALIED_PUBKEY = -13;
    private static final int SPX_UPDATE_INVALIED_SIGNATURE = -10;
    private static final int SPX_WRONG_AJX_FORMAT = -5;
    private static final int SPX_WRONG_BASEAJX_FORMAT = -6;
    private static final int SPX_WRONG_UPDATEAJX_FORMAT = -7;
    static final int SUSPEND_APPEND_ERROR = 21;
    public static final int WEB_AJX_LOADING = 12;
    static final int WEB_AJX_LOADING_CANCEL = 13;
    public static final int WEB_AJX_LOADING_FINISHED = 14;
    private static final int XML_ERR = 2;

    static class H5LogListener implements AosResponseCallback<AosByteResponse> {
        private int buttonId;
        private JSONObject localParam;
        private int pageId;

        H5LogListener(int i, int i2, JSONObject jSONObject) {
            this.pageId = i;
            this.buttonId = i2;
            this.localParam = jSONObject;
        }

        public void onSuccess(AosByteResponse aosByteResponse) {
            H5LogParser h5LogParser = new H5LogParser();
            boolean z = true;
            try {
                h5LogParser.parser((byte[]) aosByteResponse.getResult());
                if (h5LogParser.errorCode == 1) {
                    z = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z && this.localParam != null) {
                LogManager.actionLog(this.pageId, this.buttonId, this.localParam);
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            LogManager.actionLog(this.pageId, this.buttonId, this.localParam);
        }
    }

    static class H5LogParser extends AbstractAOSParser {
        H5LogParser() {
        }

        public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
            parseHeader(bArr);
        }

        public String getErrorDesc(int i) {
            return String.valueOf(i);
        }
    }

    private static String getActionLeveL(int i) {
        if (!(i == 11 || i == 21)) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 6:
                    return "p1";
                case 4:
                case 5:
                case 8:
                case 9:
                    break;
                case 7:
                    return "p3";
                default:
                    return "p4 ";
            }
        }
        return "p2";
    }

    private static String getAjxErrorCodeDesc(int i) {
        switch (i) {
            case -14:
                return "ajx memory alloc failed";
            case -13:
                return "updated ajx public key verify failed";
            case -12:
                return "base ajx public key verify failed";
            case -11:
                return "ajx public key verify failed";
            case -10:
                return "updated ajx signature verify failed";
            case -9:
                return "base ajx signature verify failed";
            case -8:
                return "ajx signature verify failed";
            case -7:
                return "illegal updated ajx file ";
            case -6:
                return "illegal base ajx file ";
            case -5:
                return "illegal ajx file ";
            case -4:
                return "base ajx not match updated ajx";
            case -3:
                return "no base ajx found in app";
            case -2:
                return "no ajx found in app";
            case -1:
                return "other error type";
            default:
                return "unknown_error";
        }
    }

    private Ajx3ActionLogUtil() {
    }

    public static void actionLogAjx(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
            String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
            if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
                StringBuilder sb = new StringBuilder();
                sb.append(allAjxFileBaseVersion);
                sb.append("_");
                sb.append(allAjxLatestPatchVersion);
                allAjxFileBaseVersion = sb.toString();
            }
            jSONObject.put("itemid", allAjxFileBaseVersion);
            jSONObject.put("url", str2);
            LogManager.actionLogV2("P00380", str, jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void actionLogAjxWebCloud(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str2);
            jSONObject.put(TrafficUtil.KEYWORD, str3);
            if (!TextUtils.isEmpty(str4)) {
                jSONObject.put("content", str4);
            }
        } catch (JSONException unused) {
        }
        LogManager.actionLogV2("P00460", str, jSONObject);
    }

    public static void actionLogUrl(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            String bundleName = AjxFileInfo.getBundleName(str2);
            String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(bundleName);
            String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
            if (!TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
                StringBuilder sb = new StringBuilder();
                sb.append(baseAjxFileVersion);
                sb.append("_");
                sb.append(loadedDiffAjxFileVersion);
                jSONObject.put("itemId", sb.toString());
            } else {
                jSONObject.put("itemId", baseAjxFileVersion);
            }
            jSONObject.put("url", str2);
            LogManager.actionLogV2(LogConstant.PAGE_ID_AJX3, str, jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void actionLogOpenAjxType(int i) {
        String actionTypeBtn = getActionTypeBtn(6);
        String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(allAjxFileBaseVersion);
            sb.append("_");
            sb.append(allAjxLatestPatchVersion);
            allAjxFileBaseVersion = sb.toString();
        }
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, actionTypeBtn, allAjxFileBaseVersion, getActionTypeDesc(6), null, generateLogOther(getAjxErrorCodeDesc(i), "", "open ajx file error", String.valueOf(i), "", "p1"));
    }

    public static void actionLogOpenAjxType(int i, String str) {
        String actionTypeBtn = getActionTypeBtn(6);
        String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(allAjxFileBaseVersion);
            sb.append("_");
            sb.append(allAjxLatestPatchVersion);
            allAjxFileBaseVersion = sb.toString();
        }
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, actionTypeBtn, allAjxFileBaseVersion, getActionTypeDesc(6), null, generateLogOther(getAjxErrorCodeDesc(i), "", str, String.valueOf(i), "", "p1"));
    }

    public static void actionLogReadFailed(String str, String str2) {
        String str3;
        String actionTypeBtn = getActionTypeBtn(7);
        String bundleName = AjxFileInfo.getBundleName(str);
        String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(bundleName);
        String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
        if (!TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(baseAjxFileVersion);
            sb.append("_");
            sb.append(loadedDiffAjxFileVersion);
            str3 = sb.toString();
        } else {
            str3 = baseAjxFileVersion;
        }
        String actionTypeDesc = getActionTypeDesc(7);
        JSONObject generateLogOther = generateLogOther(str2, "", "read file error", "", "", "p3");
        String str4 = TextUtils.isEmpty(loadedDiffAjxFileVersion) ? baseAjxFileVersion : loadedDiffAjxFileVersion;
        int bundleUpdateType = Ajx3UpgradeManager.getInstance().getBundleUpdateType(bundleName);
        r1 = AJX_BUNDLE_PAGE_ID;
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, actionTypeBtn, str3, actionTypeDesc, str, generateLogOther, bundleName, str4, bundleUpdateType);
    }

    public static void actionLogJsErrorCallbackNullMsg(String str, String str2) {
        String str3;
        String bundleName = AjxFileInfo.getBundleName(str2);
        if (!TextUtils.isEmpty(bundleName)) {
            str3 = AjxFileInfo.getBaseAjxFileVersion(bundleName);
            String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
            if (!TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str3);
                sb.append("_");
                sb.append(loadedDiffAjxFileVersion);
                str3 = sb.toString();
            }
        } else {
            str3 = AjxFileInfo.getAllAjxFileBaseVersion();
            String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
            if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str3);
                sb2.append("_");
                sb2.append(allAjxLatestPatchVersion);
                str3 = sb2.toString();
            }
        }
        String str4 = str3;
        String actionTypeBtn = getActionTypeBtn(1);
        String actionTypeDesc = getActionTypeDesc(1);
        JSONObject jSONObject = null;
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            if (jSONObject2.has(H5ResourceHandlerUtil.OTHER)) {
                jSONObject = new JSONObject(jSONObject2.getString(H5ResourceHandlerUtil.OTHER));
            }
            JSONObject jSONObject3 = jSONObject;
            if (jSONObject3 != null) {
                jSONObject3.put("fe_ext", "ModuleLog can't find mErrorCallBack!");
            }
            String optString = jSONObject2.optString(a.d, "");
            String optString2 = jSONObject2.optString(LoggingSPCache.STORAGE_BUNDLEVERSION, "");
            int optInt = jSONObject2.optInt("bundleUpdateType", -1);
            if (jSONObject3 != null) {
                actionLogH5Online(AJX_BUNDLE_PAGE_ID, actionTypeBtn, str4, actionTypeDesc, null, jSONObject3, optString, optString2, optInt);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void actionLogParseFailedWithJsMsg(java.lang.String r16, java.lang.String r17) {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = ""
            java.lang.String r2 = ""
            java.lang.String r3 = "ajx_bundles"
            java.lang.String r4 = ""
            java.lang.String r5 = ""
            java.lang.String r6 = com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo.getBundleName(r2)
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x0038
            java.lang.String r0 = com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo.getBaseAjxFileVersion(r6)
            java.lang.String r7 = com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo.getLoadedDiffAjxFileVersion(r6)
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 != 0) goto L_0x0038
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r0)
            java.lang.String r0 = "_"
            r8.append(r0)
            r8.append(r7)
            java.lang.String r0 = r8.toString()
        L_0x0038:
            r9 = r0
            r0 = -1
            r7 = 0
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ Exception -> 0x00ce }
            r10 = r16
            r8.<init>(r10)     // Catch:{ Exception -> 0x00ce }
            java.lang.String r10 = "type"
            boolean r10 = r8.has(r10)     // Catch:{ Exception -> 0x00ce }
            if (r10 == 0) goto L_0x0072
            java.lang.String r10 = "type"
            java.lang.Object r10 = r8.get(r10)     // Catch:{ Exception -> 0x00ce }
            boolean r11 = r10 instanceof java.lang.String     // Catch:{ Exception -> 0x00ce }
            if (r11 == 0) goto L_0x005a
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ Exception -> 0x00ce }
            r1 = r10
            goto L_0x0072
        L_0x005a:
            boolean r11 = r10 instanceof java.lang.Integer     // Catch:{ Exception -> 0x00ce }
            if (r11 == 0) goto L_0x0072
            java.lang.Integer r10 = (java.lang.Integer) r10     // Catch:{ Exception -> 0x00ce }
            int r10 = r10.intValue()     // Catch:{ Exception -> 0x00ce }
            java.lang.String r11 = getActionTypeDesc(r10)     // Catch:{ Exception -> 0x00ce }
            java.lang.String r1 = getActionTypeBtn(r10)     // Catch:{ Exception -> 0x006f }
            r4 = r1
            r1 = r11
            goto L_0x0072
        L_0x006f:
            r1 = r11
            goto L_0x00ce
        L_0x0072:
            java.lang.String r10 = "url"
            boolean r10 = r8.has(r10)     // Catch:{ Exception -> 0x00ce }
            if (r10 == 0) goto L_0x0083
            java.lang.String r10 = "url"
            java.lang.String r10 = r8.getString(r10)     // Catch:{ Exception -> 0x00ce }
            r2 = r10
        L_0x0083:
            java.lang.String r10 = "page_id"
            boolean r10 = r8.has(r10)     // Catch:{ Exception -> 0x00ce }
            if (r10 == 0) goto L_0x0092
            java.lang.String r10 = "page_id"
            java.lang.String r10 = r8.getString(r10)     // Catch:{ Exception -> 0x00ce }
            r3 = r10
        L_0x0092:
            java.lang.String r10 = "other"
            boolean r10 = r8.has(r10)     // Catch:{ Exception -> 0x00ce }
            if (r10 == 0) goto L_0x00ae
            java.lang.String r10 = "other"
            java.lang.String r10 = r8.getString(r10)     // Catch:{ Exception -> 0x00ce }
            org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ Exception -> 0x00ce }
            r11.<init>(r10)     // Catch:{ Exception -> 0x00ce }
            r10 = r17
            fillOther(r11, r10)     // Catch:{ Exception -> 0x00ac }
            r7 = r11
            goto L_0x00ae
        L_0x00ac:
            r7 = r11
            goto L_0x00ce
        L_0x00ae:
            java.lang.String r10 = "bundleName"
            java.lang.String r11 = ""
            java.lang.String r10 = r8.optString(r10, r11)     // Catch:{ Exception -> 0x00ce }
            java.lang.String r6 = "bundleVersion"
            java.lang.String r11 = ""
            java.lang.String r6 = r8.optString(r6, r11)     // Catch:{ Exception -> 0x00cd }
            java.lang.String r5 = "bundleUpdateType"
            int r5 = r8.optInt(r5, r0)     // Catch:{ Exception -> 0x00cc }
            r11 = r2
            r8 = r4
            r15 = r5
            r14 = r6
            r12 = r7
            r13 = r10
            r10 = r1
            goto L_0x00d5
        L_0x00cc:
            r5 = r6
        L_0x00cd:
            r6 = r10
        L_0x00ce:
            r10 = r1
            r11 = r2
            r8 = r4
            r14 = r5
            r13 = r6
            r12 = r7
            r15 = -1
        L_0x00d5:
            r7 = r3
            if (r12 == 0) goto L_0x00db
            actionLogH5Online(r7, r8, r9, r10, r11, r12, r13, r14, r15)
        L_0x00db:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.upgrade.Ajx3ActionLogUtil.actionLogParseFailedWithJsMsg(java.lang.String, java.lang.String):void");
    }

    private static void fillOther(JSONObject jSONObject, String str) {
        try {
            if (TextUtils.isEmpty(jSONObject.optString("basejs_version", ""))) {
                jSONObject.put("basejs_version", Ajx.getInstance().getBaseJsVersion());
            }
            if (TextUtils.isEmpty(jSONObject.optString("time", ""))) {
                jSONObject.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            if (TextUtils.isEmpty(jSONObject.optString("apkmd5", ""))) {
                jSONObject.put("apkmd5", str);
            }
            if (!jSONObject.has("error_code")) {
                jSONObject.put("error_code", "");
            }
            if (jSONObject.has("fe_ext")) {
                Object obj = jSONObject.get("fe_ext");
                if (obj instanceof JSONObject) {
                    jSONObject.put("fe_ext", obj.toString());
                }
            }
            if (jSONObject.has("android_ext")) {
                Object obj2 = jSONObject.get("android_ext");
                if (!(obj2 instanceof JSONObject) || ((JSONObject) obj2).length() > 0) {
                    if ((obj2 instanceof String) && TextUtils.isEmpty((String) obj2)) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("current_page", AMapPageUtil.getPageContext());
                        jSONObject.put("android_ext", jSONObject2.toString());
                    }
                    return;
                }
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("current_page", AMapPageUtil.getPageContext());
                jSONObject.put("android_ext", jSONObject3.toString());
                return;
            }
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("current_page", AMapPageUtil.getPageContext());
            jSONObject.put("android_ext", jSONObject4.toString());
        } catch (Exception unused) {
        }
    }

    private static JSONObject generateLogOther(String str, String str2, String str3, String str4, String str5, String str6) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("current_page", AMapPageUtil.getPageContext());
            jSONObject.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jSONObject.put("msg", str);
            jSONObject.put("error_code", str4);
            jSONObject.put("stack", str2);
            jSONObject.put("jserror_type", str3);
            jSONObject.put("apkmd5", str5);
            jSONObject.put("error_level", "");
            jSONObject.put("fe_ext", new JSONObject());
            jSONObject.put("iOS_ext", "");
            jSONObject.put("android_ext", jSONObject2.toString());
            jSONObject.put("basejs_version", Ajx.getInstance().getBaseJsVersion());
            jSONObject.put(H5PermissionManager.level, str6);
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    public static String generateJsErrorLog(int i, String str, String str2, String str3) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String str4 = "";
            String str5 = "";
            if (jSONObject.has("message")) {
                str = jSONObject.getString("message");
            }
            String str6 = str;
            if (jSONObject.has("stack")) {
                str4 = jSONObject.getString("stack");
            }
            String str7 = str4;
            if (jSONObject.has("name")) {
                str5 = jSONObject.getString("name");
            }
            String optString = jSONObject.optString(a.d, "");
            String optString2 = jSONObject.optString(LoggingSPCache.STORAGE_BUNDLEVERSION, "");
            int optInt = jSONObject.optInt("bundleUpdateType", -1);
            JSONObject generateLogOther = generateLogOther(str6, str7, str5, "", str3, "p1");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("url", str2);
            jSONObject2.put("type", i);
            jSONObject2.put(H5ResourceHandlerUtil.OTHER, generateLogOther);
            jSONObject2.put("page_id", AJX_BUNDLE_PAGE_ID);
            jSONObject2.put(a.d, optString);
            jSONObject2.put(LoggingSPCache.STORAGE_BUNDLEVERSION, optString2);
            jSONObject2.put("bundleUpdateType", optInt);
            return jSONObject2.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String generateStatId() {
        String taobaoID = NetworkParam.getTaobaoID();
        StringBuilder sb = new StringBuilder();
        sb.append(taobaoID);
        sb.append("_");
        sb.append(System.currentTimeMillis());
        return sb.toString();
    }

    public static void actionLogAjxWeb(int i, int i2, String str, boolean z, String str2) {
        String actionTypeBtn = getActionTypeBtn(i);
        String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(allAjxFileBaseVersion);
            sb.append("_");
            sb.append(allAjxLatestPatchVersion);
            allAjxFileBaseVersion = sb.toString();
        }
        String str3 = allAjxFileBaseVersion;
        String actionTypeDesc = getActionTypeDesc(i);
        String str4 = str;
        String str5 = actionTypeDesc;
        JSONObject generateWebAjxLogOther = generateWebAjxLogOther(str4, "", str5, String.valueOf(i2), "", str2, z, null, getActionLeveL(i));
        if (10 == i) {
            try {
                generateWebAjxLogOther.put("appended_patches_version", str);
            } catch (JSONException unused) {
            }
        }
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, actionTypeBtn, str3, actionTypeDesc, null, generateWebAjxLogOther);
    }

    public static void actionLogAjxWeb(int i, int i2, String str, boolean z, String str2, JSONObject jSONObject) {
        String actionTypeBtn = getActionTypeBtn(i);
        String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(allAjxFileBaseVersion);
            sb.append("_");
            sb.append(allAjxLatestPatchVersion);
            allAjxFileBaseVersion = sb.toString();
        }
        String actionTypeDesc = getActionTypeDesc(i);
        String str3 = str;
        String str4 = actionTypeDesc;
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, actionTypeBtn, allAjxFileBaseVersion, actionTypeDesc, null, generateWebAjxLogOther(str3, "", str4, String.valueOf(i2), "", str2, z, jSONObject, getActionLeveL(i)));
    }

    public static void actionLogAjxWeb(int i, int i2, String str, String str2, boolean z, String str3, JSONObject jSONObject) {
        String actionTypeBtn = getActionTypeBtn(i);
        String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(allAjxFileBaseVersion);
            sb.append("_");
            sb.append(allAjxLatestPatchVersion);
            allAjxFileBaseVersion = sb.toString();
        }
        String actionTypeDesc = getActionTypeDesc(i);
        String str4 = str;
        String str5 = actionTypeDesc;
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, actionTypeBtn, allAjxFileBaseVersion, actionTypeDesc, str2, generateWebAjxLogOther(str4, "", str5, String.valueOf(i2), "", str3, z, jSONObject, getActionLeveL(i)));
    }

    public static JSONObject generateWebAjxLoadingAndroidExt(boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("webajx_loading_type", z ? "update_ajx" : "no_localajx");
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    public static JSONObject generateWebAjxLoadingFinishAndroidExt(boolean z, boolean z2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("is_existed_baseajx", z);
            jSONObject.put("is_download_success", z2);
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    static JSONObject generateWebAjxCheckRequestAndroidExt(boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("checkupdate_type", z ? "webajx_page" : "bundles_update");
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    public static JSONObject generateSchemeAjxCheckRequestAndroidExt() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("checkupdate_type", "scheme_update");
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    static JSONObject generateWebAjxCheckResponseAndroidExt(String str, boolean z, boolean z2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("checkupdate_type", z2 ? "webajx_page" : "bundles_update");
            jSONObject.put("response_content", str);
            jSONObject.put("is_success", z);
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    static JSONObject generateSchemeAjxCheckResponseAndroidExt(String str, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("checkupdate_type", "scheme_update");
            jSONObject.put("response_content", str);
            jSONObject.put("is_success", z);
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    private static JSONObject generateWebAjxLogOther(String str, String str2, String str3, String str4, String str5, String str6, boolean z, JSONObject jSONObject, String str7) {
        JSONObject jSONObject2 = new JSONObject();
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        try {
            jSONObject.put("current_page", AMapPageUtil.getPageContext());
            jSONObject.put("logtime", System.currentTimeMillis());
            jSONObject2.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jSONObject2.put("msg", str);
            jSONObject2.put("error_code", str4);
            jSONObject2.put("stack", str2);
            jSONObject2.put("jserror_type", str3);
            jSONObject2.put("apkmd5", str5);
            jSONObject2.put("error_level", "");
            jSONObject2.put("fe_ext", new JSONObject());
            jSONObject2.put("iOS_ext", "");
            jSONObject2.put("android_ext", jSONObject.toString());
            jSONObject2.put("basejs_version", Ajx.getInstance().getBaseJsVersion());
            jSONObject2.put("env", z ? "WEB" : "ALL");
            jSONObject2.put("stat_id", str6);
            jSONObject2.put(H5PermissionManager.level, str7);
        } catch (Exception unused) {
        }
        return jSONObject2;
    }

    public static void actionLogOpenAjxPage(String str) {
        String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(allAjxFileBaseVersion);
            sb.append("_");
            sb.append(allAjxLatestPatchVersion);
            allAjxFileBaseVersion = sb.toString();
        }
        String str2 = allAjxFileBaseVersion;
        String bundleName = AjxFileInfo.getBundleName(str);
        String baseAjxFileName = Ajx3UpgradeManager.getInstance().getBaseAjxFileName(bundleName);
        String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(bundleName);
        String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("bundlename", bundleName);
            jSONObject.put("bundletype", TextUtils.isEmpty(baseAjxFileName) ? "webajx_bundle" : "asset_bundle");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(baseAjxFileVersion);
            sb2.append("_");
            sb2.append(loadedDiffAjxFileVersion);
            jSONObject.put("bundleversion", sb2.toString());
        } catch (Exception unused) {
        }
        String str3 = str;
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, "B002", str2, "pageload", str3, generateWebAjxLogOther(str3, "", "pageload", "17", "", "", false, jSONObject, "p4"));
    }

    static void actionLogRollbackAll(String str, String str2) {
        String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(allAjxFileBaseVersion);
            sb.append("_");
            sb.append(allAjxLatestPatchVersion);
            allAjxFileBaseVersion = sb.toString();
        }
        String str3 = allAjxFileBaseVersion;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("rollback_all_id", str2);
        } catch (JSONException unused) {
        }
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, "B001", str3, str, "", jSONObject);
    }

    static void actionLogRollback(String str, JSONObject jSONObject) {
        String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(allAjxFileBaseVersion);
            sb.append("_");
            sb.append(allAjxLatestPatchVersion);
            allAjxFileBaseVersion = sb.toString();
        }
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, "B001", allAjxFileBaseVersion, str, "", jSONObject);
    }

    public static void actionLogCommon(String str, String str2, String str3, String str4, String str5) {
        String str6;
        String bundleName = AjxFileInfo.getBundleName(str);
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(bundleName)) {
            str6 = AjxFileInfo.getBaseAjxFileVersion(bundleName);
            String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
            if (!TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str6);
                sb.append("_");
                sb.append(loadedDiffAjxFileVersion);
                str6 = sb.toString();
            }
            String baseAjxFileName = Ajx3UpgradeManager.getInstance().getBaseAjxFileName(bundleName);
            String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(bundleName);
            String loadedDiffAjxFileVersion2 = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
            try {
                jSONObject.put("bundlename", bundleName);
                jSONObject.put("bundletype", TextUtils.isEmpty(baseAjxFileName) ? "webajx_bundle" : "asset_bundle");
                StringBuilder sb2 = new StringBuilder();
                sb2.append(baseAjxFileVersion);
                sb2.append("_");
                sb2.append(loadedDiffAjxFileVersion2);
                jSONObject.put("bundleversion", sb2.toString());
            } catch (Exception unused) {
            }
        } else {
            str6 = AjxFileInfo.getAllAjxFileBaseVersion();
            String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
            if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str6);
                sb3.append("_");
                sb3.append(allAjxLatestPatchVersion);
                str6 = sb3.toString();
            }
        }
        String str7 = str6;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if (!"first_render".equals(str2)) {
                if (!"page_destroy".equals(str2)) {
                    jSONObject2.put("msg", str5);
                    jSONObject2.put("logtime", System.currentTimeMillis());
                    jSONObject2.put("error_code", str2);
                    jSONObject2.put("stack", "");
                    jSONObject2.put("jserror_type", str2);
                    jSONObject2.put("apkmd5", "");
                    jSONObject2.put("error_level", "");
                    jSONObject2.put("fe_ext", new JSONObject());
                    jSONObject2.put("iOS_ext", "");
                    jSONObject2.put("android_ext", jSONObject.toString());
                    jSONObject2.put("basejs_version", Ajx.getInstance().getBaseJsVersion());
                    jSONObject2.put("env", "ALL");
                    jSONObject2.put("stat_id", "");
                    jSONObject2.put(H5PermissionManager.level, str4);
                    actionLogH5Online(AJX_BUNDLE_PAGE_ID, str3, str7, str2, str, jSONObject2);
                }
            }
            jSONObject2.put("msg", str2);
            JSONObject jSONObject3 = new JSONObject(str5);
            jSONObject2.put("livetime", jSONObject3.opt("livetime"));
            jSONObject2.put("isblank", jSONObject3.opt("isblank"));
            jSONObject2.put("rendertime", jSONObject3.opt("rendertime"));
            jSONObject2.put("logtime", System.currentTimeMillis());
            jSONObject2.put("error_code", str2);
            jSONObject2.put("stack", "");
            jSONObject2.put("jserror_type", str2);
            jSONObject2.put("apkmd5", "");
            jSONObject2.put("error_level", "");
            jSONObject2.put("fe_ext", new JSONObject());
            jSONObject2.put("iOS_ext", "");
            jSONObject2.put("android_ext", jSONObject.toString());
            jSONObject2.put("basejs_version", Ajx.getInstance().getBaseJsVersion());
            jSONObject2.put("env", "ALL");
            jSONObject2.put("stat_id", "");
            jSONObject2.put(H5PermissionManager.level, str4);
        } catch (Exception unused2) {
        }
        actionLogH5Online(AJX_BUNDLE_PAGE_ID, str3, str7, str2, str, jSONObject2);
    }

    public static void actionLogBlankPage(String str, int i, long j, long j2) {
        String str2;
        String str3 = i == 0 ? "p1" : "p4";
        String str4 = i == 0 ? "B001" : "B002";
        String bundleName = AjxFileInfo.getBundleName(str);
        if (!TextUtils.isEmpty(bundleName)) {
            String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(bundleName);
            String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
            if (!TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
                StringBuilder sb = new StringBuilder();
                sb.append(baseAjxFileVersion);
                sb.append("_");
                sb.append(loadedDiffAjxFileVersion);
                baseAjxFileVersion = sb.toString();
            }
            str2 = baseAjxFileVersion;
        } else {
            String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
            String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
            if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(allAjxFileBaseVersion);
                sb2.append("_");
                sb2.append(allAjxLatestPatchVersion);
                allAjxFileBaseVersion = sb2.toString();
            }
            str2 = allAjxFileBaseVersion;
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("current_page", AMapPageUtil.getPageContext());
            jSONObject2.put("logtime", System.currentTimeMillis());
            jSONObject.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jSONObject.put("msg", "blank_page");
            jSONObject.put("error_level", "");
            jSONObject.put("fe_ext", new JSONObject());
            jSONObject.put("iOS_ext", "");
            jSONObject.put("android_ext", jSONObject2.toString());
            jSONObject.put("basejs_version", Ajx.getInstance().getBaseJsVersion());
            jSONObject.put(H5PermissionManager.level, str3);
            jSONObject.put("blankchecktime", j2 / 1000);
            jSONObject.put("blanktime", String.format("%.4f", new Object[]{Float.valueOf((float) (((double) j) / 1000.0d))}));
        } catch (Exception unused) {
        }
        actionLogH5Online("ajx_blank_page", str4, str2, "blank_page", str, jSONObject);
    }

    static void actionLogAjxWebCloudOnLine(String str, String str2, String str3, int i) {
        actionLogH5Online("AJX_yunkong", "B001", str2, str, "", str3, String.valueOf(i));
    }

    private static String getActionTypeDesc(int i) {
        switch (i) {
            case 1:
                return "js_error";
            case 2:
                return "xml_error";
            case 3:
                return "css_error";
            case 4:
                return "download_error";
            case 5:
                return "checkajx_error";
            case 6:
                return "openajx_error";
            case 7:
                return "loadajxresource_error";
            case 8:
                return "move_error";
            case 9:
                return "append_error";
            case 10:
                return "append_success";
            case 11:
                return "checkupdate_error";
            case 12:
                return "webajx_loading";
            case 13:
                return "webajx_loading_cancel";
            case 14:
                return "webajx_loading_finish";
            case 15:
                return "checkupdate_request";
            case 16:
                return "checkupdate_response";
            case 17:
                return "pageload";
            case 18:
                return "remotescheme_loading";
            case 19:
                return "remotescheme_loading_cancel";
            case 20:
                return "remotescheme_loading_finish";
            case 21:
                return "suspend_append_error";
            default:
                return "unknown_error : ".concat(String.valueOf(i));
        }
    }

    private static String getActionTypeBtn(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 9:
            case 10:
            case 21:
                return "B002";
            case 4:
            case 5:
            case 8:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                return "B001";
            default:
                return "unknown_btn: ".concat(String.valueOf(i));
        }
    }

    public static void actionLogH5Online(String str, String str2, String str3) {
        H5LogParam h5LogParam = new H5LogParam();
        h5LogParam.page = str;
        h5LogParam.click = str2;
        h5LogParam.other = str3;
        actionLogV3(0, 0, h5LogParam, null);
    }

    private static void actionLogH5Online(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        H5LogParam h5LogParam = new H5LogParam();
        h5LogParam.page = str;
        h5LogParam.click = str2;
        h5LogParam.id = str3;
        h5LogParam.type = str4;
        h5LogParam.url = str5;
        if (!TextUtils.isEmpty(str6)) {
            h5LogParam.status = str6;
        }
        h5LogParam.other = str7;
        actionLogV3(0, 0, h5LogParam, null);
    }

    private static void actionLogH5Online(String str, String str2, String str3, String str4, String str5, JSONObject jSONObject, String str6, String str7, int i) {
        H5LogParam h5LogParam = new H5LogParam();
        h5LogParam.page = str;
        h5LogParam.click = str2;
        h5LogParam.id = str3;
        h5LogParam.type = str4;
        h5LogParam.url = str5;
        if (jSONObject != null) {
            h5LogParam.other = jSONObject.toString();
        }
        h5LogParam.bundle_name = str6;
        h5LogParam.bundle_version = str7;
        h5LogParam.bundle_update_type = i;
        actionLogV3(0, 0, h5LogParam, null);
    }

    private static void actionLogH5Online(String str, String str2, String str3, String str4, String str5, JSONObject jSONObject) {
        H5LogParam h5LogParam = new H5LogParam();
        h5LogParam.page = str;
        h5LogParam.click = str2;
        h5LogParam.id = str3;
        h5LogParam.type = str4;
        h5LogParam.url = str5;
        if (jSONObject != null) {
            h5LogParam.other = jSONObject.toString();
        }
        actionLogV3(0, 0, h5LogParam, null);
    }

    private static void uploadDebugLog(H5LogParam h5LogParam) {
        if (h5LogParam != null) {
            String json = h5LogParam.toJson();
            if (!TextUtils.isEmpty(json)) {
                AosPostRequest b = aax.b(new DebugAjxCrashUploadParam(json));
                yq.a();
                yq.a((AosRequest) b, (AosResponseCallback<T>) new AosResponseCallback<AosStringResponse>() {
                    public final void onSuccess(AosStringResponse aosStringResponse) {
                        aosStringResponse.getResult();
                    }

                    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                        aosResponseException.printStackTrace();
                    }
                });
            }
        }
    }

    private static void actionLogV3(int i, int i2, ParamEntity paramEntity, JSONObject jSONObject) {
        AosPostRequest b = aax.b(paramEntity);
        yq.a();
        yq.a((AosRequest) b, (AosResponseCallback<T>) new H5LogListener<T>(i, i2, jSONObject));
    }
}
