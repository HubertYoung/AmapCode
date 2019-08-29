package com.alipay.mobile.nebula.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import java.util.HashMap;
import java.util.Map;

public class H5TabbarUtils {
    public static final String MATCH_TYPE_PARTHASH = "partHash";
    public static final String MATCH_TYPE_PATH = "path";
    private static final String TAG = "H5TabbarUtils";
    private static Map<String, byte[]> tabDatas = new HashMap();

    public static byte[] getTabDataByAppId(String appId) {
        H5Log.d(TAG, "getTabDataByAppId " + appId);
        if (tabDatas != null) {
            return tabDatas.get(appId);
        }
        return null;
    }

    public static void setTabData(String appId, byte[] data) {
        if (tabDatas != null) {
            H5Log.d(TAG, "setTabData " + appId);
            tabDatas.put(appId, data);
        }
    }

    public static void clearTabDataByAppId(String appId) {
        if (tabDatas != null) {
            H5Log.d(TAG, "clearTabDataByAppId do nothing" + appId);
        }
    }

    public static void clearTabDatas() {
        if (tabDatas != null) {
            H5Log.d(TAG, "clearTabDatas");
            tabDatas.clear();
        }
    }

    public static int ifUrlMatch(String appId, String entryUrl, Bundle startParams) {
        int selectedIndex = -1;
        byte[] oriData = tabDatas != null ? tabDatas.get(appId) : null;
        if (oriData != null) {
            String tmpData = new String(oriData);
            H5Log.d(TAG, "ifUrlMatch tmpData " + tmpData);
            JSONObject param = H5Utils.parseObject(tmpData);
            String matchType = H5Utils.getString(param, (String) "matchType", (String) MATCH_TYPE_PARTHASH);
            String entryUrlWithoutQuery = getUrlWithoutQuery(entryUrl, matchType);
            H5Log.d(TAG, "ifUrlMatch entryUrlWithoutQuery " + entryUrlWithoutQuery);
            JSONArray items = H5Utils.getJSONArray(param, "items", null);
            if (items != null && !items.isEmpty()) {
                int itemsLength = items.size();
                int i = 0;
                while (i < itemsLength && i < 5) {
                    JSONObject item = items.getJSONObject(i);
                    if (item != null && !item.isEmpty()) {
                        String shortUrl = item.getString("url");
                        String abUrl = getAbsoluteUrl(shortUrl, startParams);
                        String abUrlWithoutQuery = getUrlWithoutQuery(abUrl, matchType);
                        H5Log.d(TAG, "ifUrlMatch shortUrl " + shortUrl + ", abUrl " + abUrl + ", abUrlWithoutQuery " + abUrlWithoutQuery);
                        if (TextUtils.equals(entryUrlWithoutQuery, abUrlWithoutQuery)) {
                            selectedIndex = i;
                        }
                    }
                    i++;
                }
            }
        }
        H5Log.d(TAG, "ifUrlMatch final result " + selectedIndex);
        return selectedIndex;
    }

    public static String getUrlWithoutQuery(String url, String type) {
        String urlWithoutQuery = null;
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri != null) {
            if ("path".equals(type)) {
                H5Log.d(TAG, "getUrlWithoutQuery MATCH_TYPE_PATH");
                StringBuilder stringBuilder = new StringBuilder();
                String scheme = uri.getScheme();
                String authority = uri.getEncodedAuthority();
                stringBuilder.append(scheme);
                stringBuilder.append("://");
                stringBuilder.append(authority);
                urlWithoutQuery = stringBuilder.toString();
            }
            if (MATCH_TYPE_PARTHASH.equals(type)) {
                H5Log.d(TAG, "getUrlWithoutQuery MATCH_TYPE_PARTHASH");
                String query = uri.getEncodedQuery();
                if (TextUtils.isEmpty(query)) {
                    urlWithoutQuery = url;
                } else {
                    urlWithoutQuery = url.replace("?" + query, "");
                }
                String fragment = uri.getEncodedFragment();
                if (!TextUtils.isEmpty(fragment)) {
                    int indexOfQuery = fragment.indexOf("?");
                    if (indexOfQuery != -1) {
                        urlWithoutQuery = urlWithoutQuery.replace(fragment, fragment.substring(0, indexOfQuery));
                    }
                }
            }
        }
        return urlWithoutQuery;
    }

    public static String getAbsoluteUrl(String jsonUrl, Bundle activityStartParams) {
        String finalUrl = null;
        String entryUrl = H5Utils.getString(activityStartParams, (String) "url");
        boolean useNew = true;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null && BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_tabbarUrlRule"))) {
            useNew = false;
        }
        H5Log.d(TAG, "getAbsoluteUrl useNew " + useNew);
        if (useNew) {
            if (!TextUtils.isEmpty(entryUrl)) {
                finalUrl = H5Utils.getAbsoluteUrlWithURLLib(entryUrl, jsonUrl);
            }
        } else if (!TextUtils.isEmpty(entryUrl)) {
            finalUrl = H5Utils.getAbsoluteUrlV2(entryUrl, jsonUrl, null);
        }
        if (!TextUtils.isEmpty(entryUrl) && !TextUtils.isEmpty(finalUrl) && !entryUrl.startsWith(finalUrl) && neverAddHashQuery()) {
            return finalUrl;
        }
        Uri entryUri = Uri.parse(entryUrl);
        if (entryUri != null) {
            String fragmentPart = entryUri.getEncodedFragment();
            if (!TextUtils.isEmpty(fragmentPart)) {
                int indexOfQuery = fragmentPart.indexOf("?");
                if (indexOfQuery != -1) {
                    finalUrl = finalUrl + fragmentPart.substring(indexOfQuery);
                }
            }
        }
        H5Log.d(TAG, "addHashQuery : " + finalUrl);
        return finalUrl;
    }

    private static boolean neverAddHashQuery() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_neverAddHashQuery"))) {
            return true;
        }
        return false;
    }

    public static StateListDrawable generateEmptyTopDrawable() {
        return new StateListDrawable();
    }

    public static ColorStateList generateTextColor(int normalColor, int pressedColor) {
        int pressed = -16777216 | pressedColor;
        return new ColorStateList(new int[][]{new int[]{16842919}, new int[]{16842913}, new int[0]}, new int[]{pressed, pressed, -16777216 | normalColor});
    }

    public static void addCheckedState(StateListDrawable sd, Drawable pressed) {
        sd.addState(new int[]{16842919}, pressed);
        sd.addState(new int[]{16842913}, pressed);
    }

    public static void addNormalState(StateListDrawable sd, Drawable normal) {
        sd.addState(new int[0], normal);
    }

    public static StateListDrawable generateTopDrawable(Drawable normal, Drawable pressed) {
        if (normal == null) {
            return null;
        }
        if (pressed == null) {
            pressed = normal;
        }
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{16842919}, pressed);
        sd.addState(new int[]{16842913}, pressed);
        sd.addState(new int[0], normal);
        return sd;
    }
}
