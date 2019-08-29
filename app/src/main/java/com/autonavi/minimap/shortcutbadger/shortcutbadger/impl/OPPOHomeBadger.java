package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class OPPOHomeBadger extends ShortcutBadger {
    private static final String INTENT_EXTRA_BADGE_COUNT = "app_badge_count";
    private static final String INTENT_EXTRA_METHOD = "setAppBadgeCount";
    private static final String INTENT_EXTRA_OPPO_URI = "content://com.android.badge/badge";
    private static final String INTENT_EXTRA_PACKAGE_NAME = "app_badge_packageName";

    public OPPOHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) {
        executeBadge(i, getEntryActivityName());
    }

    public void executeBadge(int i, String str) {
        try {
            Bundle bundle = new Bundle();
            bundle.putInt(INTENT_EXTRA_BADGE_COUNT, i);
            bundle.putString(INTENT_EXTRA_PACKAGE_NAME, AMapAppGlobal.getApplication().getPackageName());
            AMapAppGlobal.getApplication().getContentResolver().call(Uri.parse(INTENT_EXTRA_OPPO_URI), INTENT_EXTRA_METHOD, null, bundle);
        } catch (Exception e) {
            new StringBuilder("Write unread number FAILED!!! e = ").append(e);
        }
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.oppo.launcher"});
    }
}
