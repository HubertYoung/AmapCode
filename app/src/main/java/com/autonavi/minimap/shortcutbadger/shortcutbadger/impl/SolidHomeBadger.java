package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.Context;
import android.content.Intent;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadgeException;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class SolidHomeBadger extends ShortcutBadger {
    private static final String CLASS = "com.majeur.launcher.intent.extra.BADGE_CLASS";
    private static final String COUNT = "com.majeur.launcher.intent.extra.BADGE_COUNT";
    private static final String INTENT_UPDATE_COUNTER = "com.majeur.launcher.intent.action.UPDATE_BADGE";
    private static final String PACKAGENAME = "com.majeur.launcher.intent.extra.BADGE_PACKAGE";

    public SolidHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) throws ShortcutBadgeException {
        executeBadge(i, getEntryActivityName());
    }

    public void executeBadge(int i, String str) throws ShortcutBadgeException {
        Intent intent = new Intent(INTENT_UPDATE_COUNTER);
        intent.putExtra(PACKAGENAME, str);
        intent.putExtra(COUNT, i);
        intent.putExtra(CLASS, getEntryActivityName());
        this.mContext.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.majeur.launcher"});
    }
}
