package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class HWHomeBadger extends ShortcutBadger {
    private static final String INTENT_ACTION = "com.sonyericsson.home.action.UPDATE_BADGE";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "class";
    private static final String INTENT_EXTRA_HW_URI = "content://com.huawei.android.launcher.settings/badge/";
    private static final String INTENT_EXTRA_MESSAGE = "change_badge";
    private static final String INTENT_EXTRA_PACKAGE_NAME = "package";
    private static final String INTENT_EXTRA_SHOW_MESSAGE = "badgenumber";

    public HWHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) {
        executeBadge(i, getEntryActivityName());
    }

    public void executeBadge(int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_EXTRA_PACKAGE_NAME, this.mContext.getPackageName());
        bundle.putString(INTENT_EXTRA_ACTIVITY_NAME, getEntryActivityName());
        bundle.putInt(INTENT_EXTRA_SHOW_MESSAGE, i);
        this.mContext.getContentResolver().call(Uri.parse(INTENT_EXTRA_HW_URI), INTENT_EXTRA_MESSAGE, null, bundle);
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.huawei.android.launcher"});
    }
}
