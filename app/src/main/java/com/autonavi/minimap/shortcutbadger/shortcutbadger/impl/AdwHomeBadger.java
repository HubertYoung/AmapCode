package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.Context;
import android.content.Intent;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadgeException;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class AdwHomeBadger extends ShortcutBadger {
    public static final String COUNT = "COUNT";
    public static final String INTENT_UPDATE_COUNTER = "org.adw.launcher.counter.SEND";
    public static final String PACKAGENAME = "PNAME";

    public void executeBadge(int i, String str) throws ShortcutBadgeException {
    }

    public AdwHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) throws ShortcutBadgeException {
        Intent intent = new Intent(INTENT_UPDATE_COUNTER);
        intent.putExtra(PACKAGENAME, getContextPackageName());
        intent.putExtra(COUNT, i);
        this.mContext.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"org.adw.launcher", "org.adwfreak.launcher"});
    }
}
