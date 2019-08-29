package com.ut.mini.exposure;

import android.text.TextUtils;
import android.view.View;
import java.util.HashMap;
import java.util.Map;

public class ExposureUtils {
    protected static final String UT_EXPROSURE_ARGS = "UT_EXPROSURE_ARGS";
    protected static final String UT_EXPROSURE_BLOCK = "UT_EXPROSURE_BLOCK";
    protected static final String UT_EXPROSURE_VIEWID = "UT_EXPROSURE_VIEWID";
    public static final int ut_exprosure_common_info_tag = -17003;
    public static final int ut_exprosure_ignore_tag = -17004;
    public static final int ut_exprosure_tag = -17001;
    public static final int ut_exprosure_tag_for_weex = -17002;

    public static void setExposure(View view, String str, String str2, Map<String, String> map) {
        if (view == null) {
            ExpLogger.w(null, "error,view is null");
        } else if (TextUtils.isEmpty(str)) {
            ExpLogger.w(null, "error,block is empty");
        } else if (TextUtils.isEmpty(str2)) {
            ExpLogger.w(null, "error,viewId is empty");
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put(UT_EXPROSURE_BLOCK, str);
            hashMap.put(UT_EXPROSURE_VIEWID, str2);
            if (map != null) {
                hashMap.put(UT_EXPROSURE_ARGS, map);
            }
            view.setTag(ut_exprosure_tag, hashMap);
        }
    }

    protected static void clearExposureForWeex(View view) {
        if (view == null) {
            ExpLogger.w(null, "error,view is null");
            return;
        }
        view.setTag(ut_exprosure_tag_for_weex, null);
    }

    public static void setExposureForWeex(View view) {
        if (view == null) {
            ExpLogger.w(null, "error,view is null");
            return;
        }
        view.setTag(ut_exprosure_tag_for_weex, "auto");
    }

    public static boolean isExposureViewForWeex(View view) {
        return (view == null || view.getTag(ut_exprosure_tag_for_weex) == null) ? false : true;
    }

    public static boolean isExposureView(View view) {
        return (view == null || view.getTag(ut_exprosure_tag) == null) ? false : true;
    }

    public static boolean isIngoneExposureView(View view) {
        return (view == null || view.getTag(ut_exprosure_ignore_tag) == null) ? false : true;
    }

    public static void setIgnoreTagForExposureView(View view) {
        if (view != null) {
            view.setTag(ut_exprosure_ignore_tag, "user");
        }
    }

    public static void clearIgnoreTagForExposureView(View view) {
        if (view != null) {
            view.setTag(ut_exprosure_ignore_tag, null);
        }
    }
}
