package com.alipay.mobile.monitor.track.spm.merge;

import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.SpmLogCator;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import org.json.JSONObject;

public class MergeUtil {
    public static final String KEY_EXPOSED = "exposed";
    public static final String KEY_RID = "rid";
    public static String MERGE_CONFIG = null;
    public static final String SEPARATOR_ITEM = "&";
    public static final String SEPARATOR_KV = "|";
    public static final String SEPARATOR_PARAM = ";";
    public static final String SEPARATOR_REQUEST = "__";
    public static final String SEPARATOR_RID = ":";
    private static int a = -1;
    private static int b = -1;
    private static String c = "-1";

    public static int getMergedMaxSize() {
        if (a != -1) {
            return a;
        }
        if (!TextUtils.isEmpty(MERGE_CONFIG)) {
            try {
                a(MERGE_CONFIG);
                return a;
            } catch (Exception e) {
                SpmLogCator.error((String) "MergeUtil", "parseConfig exception:" + e.toString());
            }
        }
        return 14336;
    }

    public static int getMergedMaxCount() {
        if (b != -1) {
            return b;
        }
        if (!TextUtils.isEmpty(MERGE_CONFIG)) {
            try {
                a(MERGE_CONFIG);
                return b;
            } catch (Exception e) {
                SpmLogCator.error((String) "MergeUtil", "parseConfig exception:" + e.toString());
            }
        }
        return 50;
    }

    private static void a(String config) {
        JSONObject jsonObject = new JSONObject(config);
        c = jsonObject.getString(FunctionSupportConfiger.SWITCH_TAG);
        a = jsonObject.getInt("size");
        b = jsonObject.getInt(NewHtcHomeBadger.COUNT);
    }

    public static String isMergeActived() {
        if (!"-1".equals(c)) {
            return c;
        }
        if (!TextUtils.isEmpty(MERGE_CONFIG)) {
            try {
                a(MERGE_CONFIG);
                return c;
            } catch (Exception e) {
                SpmLogCator.error((String) "MergeUtil", "parseConfig exception:" + e.toString());
            }
        }
        return "1";
    }

    public static String getMergeBlackList() {
        return "cityid";
    }
}
