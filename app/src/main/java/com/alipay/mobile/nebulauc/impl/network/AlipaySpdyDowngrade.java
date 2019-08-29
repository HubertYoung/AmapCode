package com.alipay.mobile.nebulauc.impl.network;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.storage.sp.APSharedPreferences;
import com.alipay.android.phone.mobilesdk.storage.sp.SharedPreferencesManager;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.ArrayList;
import java.util.List;

public class AlipaySpdyDowngrade {
    public static final String TAG = "AlipaySpdyDowngrade";
    private static List<String> sMainDocSpdyTable = new ArrayList();
    private static List<String> sMemoryDowngradeRules = new ArrayList();

    public static void addMemoryDowngradeRule(String url, int domainLevel) {
        String rule = convertUrl(url, domainLevel);
        if (!TextUtils.isEmpty(rule)) {
            sMemoryDowngradeRules.add(rule);
        }
    }

    public static boolean isExistMemoryDowngradeRule(String url) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null) {
            return false;
        }
        String host = uri.getHost();
        String urlWithoutQuery = handleQuery(url, uri);
        if (sMemoryDowngradeRules.isEmpty()) {
            return false;
        }
        if (sMemoryDowngradeRules.contains(host) || sMemoryDowngradeRules.contains(urlWithoutQuery)) {
            return true;
        }
        return false;
    }

    public static void addDiskDowngradeRule(String url, long seconds, int domainLevel) {
        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "h5_spdydowngrade");
        if (preferences != null) {
            String value = String.valueOf(System.currentTimeMillis()) + MergeUtil.SEPARATOR_KV + String.valueOf(seconds);
            H5Log.d(TAG, "addDiskDowngradeRule value is " + value);
            String rule = convertUrl(url, domainLevel);
            if (!TextUtils.isEmpty(rule)) {
                preferences.putString(rule, value);
                H5Log.d(TAG, "addDiskDowngradeRule commit status is " + preferences.commit());
            }
        }
    }

    public static boolean isExistDiskDowngradeRule(String url) {
        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "h5_spdydowngrade");
        if (preferences == null) {
            H5Log.d(TAG, "isExistDiskDowngradeRule preferences == null");
            return false;
        }
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null) {
            return false;
        }
        String host = uri.getHost();
        String urlWithoutPath = handleQuery(url, uri);
        String value = preferences.getString(host, "");
        if (TextUtils.isEmpty(value)) {
            value = preferences.getString(urlWithoutPath, "");
        }
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        String[] ruleArray = value.split("\\|");
        long ruleStartTime = Long.parseLong(ruleArray[0]);
        long seconds = Long.parseLong(ruleArray[1]);
        long duration = (System.currentTimeMillis() - ruleStartTime) / 1000;
        H5Log.d(TAG, "isExistDiskDowngradeRule duration is " + duration + ", seconds is " + seconds);
        if (duration <= seconds) {
            return true;
        }
        return false;
    }

    private static String convertUrl(String url, int domainLevel) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null) {
            return null;
        }
        if (domainLevel == 0) {
            String query = uri.getEncodedQuery();
            if (TextUtils.isEmpty(query)) {
                return url;
            }
            return url.replace(query, "");
        } else if (domainLevel == 1) {
            return uri.getHost();
        } else {
            return null;
        }
    }

    private static String handleQuery(String url, Uri uri) {
        String query = uri.getEncodedQuery();
        if (TextUtils.isEmpty(query)) {
            return url;
        }
        return url.replace(query, "");
    }

    public static boolean getSwitchControl() {
        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "h5_switchcontrol");
        if (preferences != null) {
            H5Log.d(TAG, "getSwitchControl preferences != null");
            return preferences.getBoolean("enableSPDY", true);
        }
        H5Log.d(TAG, "getSwitchControl preferences == null");
        return true;
    }

    public static void addSMainDocSpdyTable(String host) {
        if (sMainDocSpdyTable.size() > 200) {
            sMainDocSpdyTable.remove(0);
        }
        if (!sMainDocSpdyTable.contains(host)) {
            sMainDocSpdyTable.add(host);
        }
    }

    public static boolean isInsMainDocSpdyTable(String host) {
        return sMainDocSpdyTable.contains(host);
    }
}
