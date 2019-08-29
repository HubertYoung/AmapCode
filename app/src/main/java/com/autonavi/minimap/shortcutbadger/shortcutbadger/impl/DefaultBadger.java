package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.Context;
import android.content.Intent;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.ArrayList;
import java.util.List;

public class DefaultBadger extends ShortcutBadger {
    private static final String INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";
    private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
    private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";

    public DefaultBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) {
        executeBadge(i, getEntryActivityName());
    }

    public void executeBadge(int i, String str) {
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(INTENT_EXTRA_BADGE_COUNT, i);
        intent.putExtra(INTENT_EXTRA_PACKAGENAME, getContextPackageName());
        intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, str);
        this.mContext.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return new ArrayList(0);
    }
}
