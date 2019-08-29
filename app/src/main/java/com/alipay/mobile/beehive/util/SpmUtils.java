package com.alipay.mobile.beehive.util;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.wallet.spmtracker.SpmTracker;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import java.util.HashMap;
import java.util.Map;

public class SpmUtils {
    public static final String KEY_BEE_BIZTYPE = "beeBizType";
    public static final String KEY_BEE_SOURCEPAGE = "beeSourcePage";
    public static final String SPM_BIZTYPE = "beehive";

    public static void addSourceByH5Event(H5Event event, Bundle bundle) {
        if (bundle != null) {
            String result = "";
            H5CoreNode node = event.getTarget();
            if (node instanceof H5Page) {
                result = ((H5Page) node).getUrl();
            }
            if (!TextUtils.isEmpty(result) && !bundle.containsKey(KEY_BEE_SOURCEPAGE)) {
                bundle.putString(KEY_BEE_SOURCEPAGE, result);
            }
        }
    }

    public static void addBizTypeByApp(MicroApplication app, Bundle bundle) {
        if (bundle != null) {
            String result = "";
            if (app != null) {
                result = app.getAppId();
            }
            if (!TextUtils.isEmpty(result) && !bundle.containsKey(KEY_BEE_BIZTYPE)) {
                bundle.putString(KEY_BEE_BIZTYPE, result);
            }
        }
    }

    public static void addSourceByTopActivity(Activity topActivity, Bundle bundle) {
        if (bundle != null) {
            String result = "";
            if (topActivity != null) {
                result = topActivity.getClass().getName();
            }
            if (!TextUtils.isEmpty(result) && !bundle.containsKey(KEY_BEE_SOURCEPAGE)) {
                bundle.putString(KEY_BEE_SOURCEPAGE, result);
            }
        }
    }

    public static void addSourceAndBizTypeByTop(MicroApplication app, Activity topActivity, Bundle bundle) {
        addBizTypeByApp(app, bundle);
        addSourceByTopActivity(topActivity, bundle);
    }

    public static void onPagePause(Object page, String spmId, Map<String, String> params, Bundle bundle) {
        if (params == null) {
            params = new HashMap<>();
        }
        if (bundle != null) {
            params.put(KEY_BEE_SOURCEPAGE, bundle.getString(KEY_BEE_SOURCEPAGE));
            params.put(KEY_BEE_BIZTYPE, bundle.getString(KEY_BEE_BIZTYPE));
        }
        SpmTracker.onPagePause(page, spmId, SPM_BIZTYPE, params);
    }

    public static void onPagePause(Object page, String spmId, Bundle bundle) {
        onPagePause(page, spmId, null, bundle);
    }
}
