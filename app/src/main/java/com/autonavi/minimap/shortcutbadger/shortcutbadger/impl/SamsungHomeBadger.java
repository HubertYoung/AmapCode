package com.autonavi.minimap.shortcutbadger.shortcutbadger.impl;

import android.content.ContentValues;
import android.content.Context;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadgeException;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadger;
import java.util.Arrays;
import java.util.List;

@Deprecated
public class SamsungHomeBadger extends ShortcutBadger {
    private static final String[] CONTENT_PROJECTION = {"_id", "class"};
    private static final String CONTENT_URI = "content://com.sec.badge/apps?notify=true";

    public SamsungHomeBadger(Context context) {
        super(context);
    }

    public void executeBadge(int i) throws ShortcutBadgeException {
        executeBadge(i, getEntryActivityName());
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0069 A[SYNTHETIC, Splitter:B:23:0x0069] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executeBadge(int r12, java.lang.String r13) throws com.autonavi.minimap.shortcutbadger.shortcutbadger.ShortcutBadgeException {
        /*
            r11 = this;
            java.lang.String r0 = "content://com.sec.badge/apps?notify=true"
            android.net.Uri r0 = android.net.Uri.parse(r0)
            android.content.Context r1 = r11.mContext
            android.content.ContentResolver r7 = r1.getContentResolver()
            r8 = 0
            java.lang.String[] r3 = CONTENT_PROJECTION     // Catch:{ all -> 0x0065 }
            java.lang.String r4 = "package=?"
            r9 = 1
            java.lang.String[] r5 = new java.lang.String[r9]     // Catch:{ all -> 0x0065 }
            java.lang.String r1 = r11.getContextPackageName()     // Catch:{ all -> 0x0065 }
            r10 = 0
            r5[r10] = r1     // Catch:{ all -> 0x0065 }
            r6 = 0
            r1 = r7
            r2 = r0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0065 }
            if (r1 == 0) goto L_0x005e
            r2 = 0
        L_0x0025:
            boolean r3 = r1.moveToNext()     // Catch:{ all -> 0x005c }
            if (r3 == 0) goto L_0x0052
            int r3 = r1.getInt(r10)     // Catch:{ all -> 0x005c }
            android.content.ContentValues r4 = r11.getContentValues(r12, r10)     // Catch:{ all -> 0x005c }
            java.lang.String r5 = "_id=?"
            java.lang.String[] r6 = new java.lang.String[r9]     // Catch:{ all -> 0x005c }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x005c }
            r6[r10] = r3     // Catch:{ all -> 0x005c }
            r7.update(r0, r4, r5, r6)     // Catch:{ all -> 0x005c }
            java.lang.String r3 = "class"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ all -> 0x005c }
            java.lang.String r3 = r1.getString(r3)     // Catch:{ all -> 0x005c }
            boolean r3 = r13.equals(r3)     // Catch:{ all -> 0x005c }
            if (r3 == 0) goto L_0x0025
            r2 = 1
            goto L_0x0025
        L_0x0052:
            if (r2 != 0) goto L_0x005e
            android.content.ContentValues r12 = r11.getContentValues(r12, r9)     // Catch:{ all -> 0x005c }
            r7.insert(r0, r12)     // Catch:{ all -> 0x005c }
            goto L_0x005e
        L_0x005c:
            r12 = move-exception
            goto L_0x0067
        L_0x005e:
            if (r1 == 0) goto L_0x0064
            r1.close()     // Catch:{ Throwable -> 0x0063 }
        L_0x0063:
            return
        L_0x0064:
            return
        L_0x0065:
            r12 = move-exception
            r1 = r8
        L_0x0067:
            if (r1 == 0) goto L_0x006c
            r1.close()     // Catch:{ Throwable -> 0x006c }
        L_0x006c:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.SamsungHomeBadger.executeBadge(int, java.lang.String):void");
    }

    private ContentValues getContentValues(int i, boolean z) {
        ContentValues contentValues = new ContentValues();
        if (z) {
            contentValues.put("package", getContextPackageName());
            contentValues.put("class", getEntryActivityName());
        }
        contentValues.put("badgecount", Integer.valueOf(i));
        return contentValues;
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.sec.android.app.launcher", "com.sec.android.app.twlauncher"});
    }
}
