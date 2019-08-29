package com.autonavi.minimap.ajx3.scheme;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.AjxPageUtil;
import com.autonavi.minimap.ajx3.util.SchemeUtils;

public class Ajx3SchemeHelper {
    static final String NEW_HOST_AJX = "ajx";
    static final String NEW_HOST_AJX_ACTIVITY = "ajx-activity";
    static final String NEW_HOST_AJX_SMALL_BRIDGE = "ajx_smallbridge";
    public static final String SCHEME_HAD_CHECKED_KEY = "hadCheckedRemote";
    private static final String SCHEME_LOADER_PAGE_PATH = "path://amap_bundle_dynamic_ui/src/SchemeLoader/pages/SchemeLoader.page.js";

    static boolean checkValid(Uri uri) {
        return uri != null && !TextUtils.isEmpty(uri.getHost());
    }

    static String isLocalSupport(Uri uri) {
        String uri2 = uri.toString();
        String host = uri.getHost();
        if (uri2.indexOf(63) > 0) {
            uri2 = uri2.substring(0, uri2.indexOf(63));
            if (("ajx".equals(host) || NEW_HOST_AJX_ACTIVITY.equals(host) || NEW_HOST_AJX_SMALL_BRIDGE.equals(host)) && !TextUtils.isEmpty(uri.getQueryParameter("path"))) {
                StringBuilder sb = new StringBuilder();
                sb.append(uri2);
                sb.append("?path=");
                sb.append(uri.getQueryParameter("path"));
                uri2 = sb.toString();
            }
        }
        return SchemeUtils.isLocalSupport(uri2);
    }

    static boolean hasChecked(Uri uri) {
        return "true".equals(uri.getQueryParameter(SCHEME_HAD_CHECKED_KEY));
    }

    public static Uri addQueryParam(Uri uri, String str, String str2) {
        String str3;
        if (uri == null || TextUtils.isEmpty(uri.toString())) {
            return null;
        }
        String uri2 = uri.toString();
        if (!TextUtils.isEmpty(uri.getQueryParameter(str))) {
            str3 = uri2.substring(0, uri2.indexOf(63) + 1);
            String[] split = uri2.substring(uri2.indexOf(63) + 1).split("&");
            for (int i = 0; i < split.length; i++) {
                String str4 = split[i];
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("=");
                if (str4.contains(sb.toString())) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("=");
                    sb2.append(str2);
                    split[i] = sb2.toString();
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str3);
                sb3.append(split[i]);
                str3 = sb3.toString();
                if (i != split.length - 1) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str3);
                    sb4.append("&");
                    str3 = sb4.toString();
                }
            }
        } else {
            char c = '&';
            if (uri2.indexOf(63) < 0) {
                c = '?';
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append(uri.toString());
            sb5.append(c);
            sb5.append(str);
            sb5.append("=");
            sb5.append(str2);
            str3 = sb5.toString();
        }
        return Uri.parse(str3);
    }

    static boolean tryOpenLocalAjx(Context context, Uri uri, esk esk) {
        String isLocalSupport = isLocalSupport(uri);
        if (TextUtils.isEmpty(isLocalSupport)) {
            return false;
        }
        if (AjxFileInfo.checkIfPathConfiguredInRouterTable(AjxFileInfo.URI_TYPE_SERVICE, isLocalSupport)) {
            return doOpenAjxService(isLocalSupport, uri.getQueryParameter("data"));
        }
        Uri addQueryParam = addQueryParam(uri, "path", isLocalSupport);
        if (addQueryParam == null) {
            return false;
        }
        return doOpenAjxPage(context, addQueryParam, esk);
    }

    private static boolean doOpenAjxService(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "lifecycle", (Object) "on_open_scheme");
        jSONObject.put((String) "data", (Object) str2);
        Ajx.getInstance().startService(str, str, jSONObject.toString(), "");
        return true;
    }

    static boolean updateAjxByScheme(Uri uri, esk esk, Callback<Boolean> callback) {
        if (hasChecked(uri)) {
            return false;
        }
        JSONObject jSONObject = new JSONObject();
        String uri2 = uri.toString();
        jSONObject.put((String) "__ajx_page_scheme_key__", (Object) uri2.indexOf("?") > 0 ? uri2.substring(0, uri2.indexOf("?")) : uri2);
        jSONObject.put((String) "__ajx_page_scheme__", (Object) uri2);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", SCHEME_LOADER_PAGE_PATH);
        pageBundle.putObject("jsData", jSONObject.toJSONString());
        pageBundle.putObject(Ajx3Page.PAGE_SCHEME_RESULT, callback);
        esk.startPage(Ajx3Page.class, pageBundle);
        return true;
    }

    static Uri mergeParam2Data(Uri uri, String str) {
        String queryParameter = uri.getQueryParameter("data");
        try {
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(queryParameter)) {
                jSONObject.putAll(JSONObject.parseObject(queryParameter));
            }
            jSONObject.putAll(generateParamJson(uri.toString()));
            if (!TextUtils.isEmpty(str)) {
                jSONObject.putAll(JSONObject.parseObject(str));
            }
            String uri2 = uri.toString();
            if (uri2.indexOf("?") > 0) {
                uri2 = uri2.substring(0, uri2.indexOf(63));
            }
            String queryParameter2 = uri.getQueryParameter("path");
            String queryParameter3 = uri.getQueryParameter(SCHEME_HAD_CHECKED_KEY);
            String str2 = "?";
            if (!TextUtils.isEmpty(queryParameter2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(uri2);
                sb.append(str2);
                sb.append("path=");
                sb.append(queryParameter2);
                uri2 = sb.toString();
                str2 = "&";
            }
            if (jSONObject.size() > 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(uri2);
                sb2.append(str2);
                sb2.append("data=");
                sb2.append(jSONObject.toString());
                uri2 = sb2.toString();
                str2 = "&";
            }
            if (!TextUtils.isEmpty(queryParameter3)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(uri2);
                sb3.append(str2);
                sb3.append("hadCheckedRemote=");
                sb3.append(queryParameter3);
                uri2 = sb3.toString();
            }
            return Uri.parse(uri2);
        } catch (Exception unused) {
            return uri;
        }
    }

    public static boolean checkEffectiveVersion(String str, String str2) {
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int min = Math.min(split.length, split2.length);
        if (min <= 0) {
            return true;
        }
        for (int i = 0; i < min; i++) {
            String removeZero = removeZero(split2[i].trim());
            String removeZero2 = removeZero(split[i].trim());
            if (!TextUtils.isEmpty(removeZero) && !TextUtils.isEmpty(removeZero2)) {
                try {
                    int parseInt = Integer.parseInt(removeZero);
                    int parseInt2 = Integer.parseInt(removeZero2);
                    if (parseInt != parseInt2) {
                        return parseInt > parseInt2;
                    }
                } catch (NumberFormatException unused) {
                    if (removeZero.length() != removeZero2.length()) {
                        return removeZero.length() > removeZero2.length();
                    }
                    int compareTo = removeZero.compareTo(removeZero2);
                    if (compareTo != 0) {
                        return compareTo > 0;
                    }
                }
            }
        }
        return true;
    }

    private static String removeZero(String str) {
        while (str.length() > 1 && str.startsWith("0")) {
            str = str.substring(1);
        }
        return str;
    }

    private static JSONObject generateParamJson(String str) {
        String[] split = str.substring(str.indexOf(63) + 1).split("&");
        JSONObject jSONObject = new JSONObject();
        for (String split2 : split) {
            String[] split3 = split2.split("=");
            if (split3 != null && split3.length == 2 && !"path".equals(split3[0]) && !"data".equals(split3[0]) && !SCHEME_HAD_CHECKED_KEY.equals(split3[0])) {
                jSONObject.put(split3[0], (Object) split3[1]);
            }
        }
        return jSONObject;
    }

    private static boolean doOpenAjxPage(Context context, Uri uri, esk esk) {
        String queryParameter = uri.getQueryParameter("path");
        String queryParameter2 = uri.getQueryParameter("data");
        String queryParameter3 = uri.getQueryParameter("__bv__");
        if (!TextUtils.isEmpty(queryParameter3)) {
            StringBuilder sb = new StringBuilder();
            sb.append(queryParameter);
            sb.append("&__bv__=");
            sb.append(queryParameter3);
            queryParameter = sb.toString();
        }
        PageBundle makePageBundle = AjxPageUtil.makePageBundle(context, queryParameter);
        makePageBundle.putObject("jsData", queryParameter2);
        esk.startPageForResult(Ajx3Page.class, makePageBundle, -1);
        return true;
    }
}
