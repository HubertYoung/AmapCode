package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class VIVOHomeBadger extends ShortcutBadger {
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 16777216;
    private static final String INTENT_ACTION = "launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM";
    private static final String INTENT_EXTRA_ACTIVITY_CLASSNAME = "className";
    private static final String INTENT_EXTRA_PACKAGE_NAME = "packageName";
    private static final String INTENT_EXTRA_SHOW_MESSAGE = "notificationNum";

    public VIVOHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) {
        executeBadge(i, getEntryActivityName());
    }

    public void executeBadge(int i, String str) {
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra("packageName", this.mContext.getPackageName());
        intent.putExtra("className", str);
        intent.putExtra(INTENT_EXTRA_SHOW_MESSAGE, i);
        if (VERSION.SDK_INT >= 26) {
            intent.setFlags(16777216);
        }
        this.mContext.sendBroadcast(intent);
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.bbk.launcher2"});
    }
}
