package com.autonavi.minimap.ajx3.util;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenThirdAppUtil {
    public static final String KEY_HTML_STRING = "htmlString";
    public static final String KEY_LOADING_TIME = "key_loading_time";
    public static final String KEY_RIGHT_BTN_TITLE = "right_btn_name";
    public static final String KEY_RIGHT_BTN_URL = "rightBtnUrl";
    public static final String KEY_SHOW_BOTTOM_BAR = "show_bottom_bar";
    public static final String KEY_SHOW_LOADING_ANIM = "show_loading_anim";
    public static final String KEY_SHOW_RIGHT_BTN = "show_right_btn_for_other";
    public static final String KEY_SHOW_TITLE = "show_title";
    public static final String KEY_SUPPORT_ZOOM = "support_zoom";
    public static final String KEY_SWITCH_ONLINE = "switch_online";
    public static final String KEY_THIRDPARY_NAME = "thirdpart_name";
    public static final String KEY_TITLE = "title";
    public static final String KEY_URI = "uri";
    public static final String KEY_URL = "url";
    public static final String KEY_USE_WEB_TITLE = "use_web_title";
    public static final String SEARCH_THIRD_PART_WEB_FRAGMENT_ACTION = "amap.search.action.thirdpartweb";

    public static void openThirdApp(String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (!TextUtils.isEmpty(str) && pageContext != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String optString = jSONObject.optString("package");
                jSONObject.optString("version");
                String optString2 = jSONObject.optString("andh");
                String optString3 = jSONObject.optString("wapUrl");
                String optString4 = jSONObject.optString("appName");
                int optInt = jSONObject.optInt("loadingTime");
                Boolean valueOf = Boolean.valueOf(optInt != 0);
                String optString5 = jSONObject.optString("isout", "0");
                boolean optBoolean = jSONObject.optBoolean("isNeedReplace", false);
                Intent intent = new Intent();
                if (optString != null && !"".equals(optString)) {
                    intent.setPackage(optString);
                }
                try {
                    if (TextUtils.isEmpty(optString2)) {
                        throw new ActivityNotFoundException();
                    }
                    if (optString2.startsWith(AjxHttpLoader.DOMAIN_HTTP) || optString2.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
                        intent.setAction("android.intent.action.VIEW");
                    }
                    intent.setData(Uri.parse(optString2));
                    intent.addFlags(268435456);
                    if (pageContext.getActivity() != null) {
                        pageContext.getActivity().startActivity(intent);
                    }
                } catch (ActivityNotFoundException unused) {
                    if (optString5.equals("1")) {
                        Uri parse = Uri.parse(optString3);
                        if (pageContext.getActivity() != null) {
                            pageContext.getActivity().startActivity(new Intent("android.intent.action.VIEW", parse));
                        }
                        return;
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putString("title", optString4);
                    pageBundle.putString("url", optString3);
                    pageBundle.putBoolean(KEY_SHOW_LOADING_ANIM, valueOf.booleanValue());
                    pageBundle.putInt(KEY_LOADING_TIME, optInt);
                    pageBundle.putBoolean(KEY_USE_WEB_TITLE, true);
                    pageBundle.putString(KEY_THIRDPARY_NAME, optString4);
                    JSONObject optJSONObject = jSONObject.optJSONObject("showButton");
                    if (optJSONObject != null) {
                        String optString6 = optJSONObject.optString("buttonText");
                        String optString7 = optJSONObject.optString("localFile");
                        if (!TextUtils.isEmpty(optString6)) {
                            pageBundle.putBoolean(KEY_SHOW_RIGHT_BTN, true);
                            pageBundle.putString(KEY_RIGHT_BTN_TITLE, optString6);
                            if (!TextUtils.isEmpty(optString7)) {
                                pageBundle.putString(KEY_RIGHT_BTN_URL, optString7);
                            }
                        }
                    }
                    if (NetworkReachability.b() || NetworkReachability.a()) {
                        if (optBoolean) {
                            pageContext.finish();
                        }
                        pageContext.startPage((String) SEARCH_THIRD_PART_WEB_FRAGMENT_ACTION, pageBundle);
                        return;
                    }
                    ToastHelper.showToast(pageContext.getActivity().getString(R.string.net_error_message));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openOuterApp(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.addFlags(268435456);
        try {
            AMapAppGlobal.getApplication().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
