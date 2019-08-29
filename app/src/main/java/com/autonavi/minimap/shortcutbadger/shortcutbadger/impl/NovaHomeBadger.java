package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadgeException;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

public class NovaHomeBadger extends ShortcutBadger {
    private static final String CONTENT_URI = "content://com.teslacoilsw.notifier/unread_count";
    private static final String COUNT = "count";
    private static final String TAG = "tag";

    public NovaHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) throws ShortcutBadgeException {
        executeBadge(i, getEntryActivityName());
    }

    public void executeBadge(int i, String str) throws ShortcutBadgeException {
        try {
            ContentValues contentValues = new ContentValues();
            StringBuilder sb = new StringBuilder();
            sb.append(getContextPackageName());
            sb.append("/");
            sb.append(getEntryActivityName());
            contentValues.put("tag", sb.toString());
            contentValues.put("count", Integer.valueOf(i));
            this.mContext.getContentResolver().insert(Uri.parse(CONTENT_URI), contentValues);
        } catch (IllegalArgumentException unused) {
        } catch (Exception e) {
            throw new ShortcutBadgeException(e.getMessage());
        }
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.teslacoilsw.launcher"});
    }
}
