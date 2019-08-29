package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.Context;
import android.content.Intent;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadgeException;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class ApexHomeBadger extends ShortcutBadger {
    private static final String CLASS = "class";
    private static final String COUNT = "count";
    private static final String INTENT_UPDATE_COUNTER = "com.anddoes.launcher.COUNTER_CHANGED";
    private static final String PACKAGENAME = "package";

    public ApexHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) throws ShortcutBadgeException {
        executeBadge(i, getEntryActivityName());
    }

    public void executeBadge(int i, String str) throws ShortcutBadgeException {
        Intent intent = new Intent(INTENT_UPDATE_COUNTER);
        intent.putExtra(PACKAGENAME, getContextPackageName());
        intent.putExtra("count", i);
        intent.putExtra(CLASS, str);
        this.mContext.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.anddoes.launcher"});
    }
}
