package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadgeException;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class XiaomiHomeBadger extends ShortcutBadger {
    public static final String EXTRA_UPDATE_APP_COMPONENT_NAME = "android.intent.extra.update_application_component_name";
    public static final String EXTRA_UPDATE_APP_MSG_TEXT = "android.intent.extra.update_application_message_text";
    public static final String INTENT_ACTION = "android.intent.action.APPLICATION_MESSAGE_UPDATE";

    public XiaomiHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) throws ShortcutBadgeException {
        executeBadge(i, getEntryActivityName());
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.miui.miuilite", "com.miui.home", "com.miui.miuihome", "com.miui.miuihome2", "com.miui.mihome", "com.miui.mihome2"});
    }

    public void executeBadge(int i, String str) throws ShortcutBadgeException {
        String str2;
        String a = elz.a("ro.miui.ui.version.code");
        if (!TextUtils.isEmpty(a) && Integer.parseInt(a) < 4) {
            Intent intent = new Intent(INTENT_ACTION);
            StringBuilder sb = new StringBuilder();
            sb.append(this.mContext.getPackageName());
            sb.append("/");
            sb.append(str);
            intent.putExtra(EXTRA_UPDATE_APP_COMPONENT_NAME, sb.toString());
            if (i == 0) {
                str2 = "";
            } else {
                str2 = String.valueOf(i);
            }
            intent.putExtra(EXTRA_UPDATE_APP_MSG_TEXT, str2);
            this.mContext.sendBroadcast(intent);
        }
    }
}
