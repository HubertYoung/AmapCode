package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.Context;
import android.content.Intent;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class SonyHomeBadger extends ShortcutBadger {
    private static final String INTENT_ACTION = "com.sonyericsson.home.action.UPDATE_BADGE";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME";
    private static final String INTENT_EXTRA_MESSAGE = "com.sonyericsson.home.intent.extra.badge.MESSAGE";
    private static final String INTENT_EXTRA_PACKAGE_NAME = "com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME";
    private static final String INTENT_EXTRA_SHOW_MESSAGE = "com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE";

    public SonyHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) {
        executeBadge(i, getEntryActivityName());
    }

    public void executeBadge(int i, String str) {
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(INTENT_EXTRA_PACKAGE_NAME, getContextPackageName());
        intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, str);
        intent.putExtra(INTENT_EXTRA_MESSAGE, String.valueOf(i));
        intent.putExtra(INTENT_EXTRA_SHOW_MESSAGE, i > 0);
        this.mContext.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.sonyericsson.home"});
    }
}
