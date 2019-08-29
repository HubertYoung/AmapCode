package com.autonavi.minimap.life.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.scansdk.constant.Constants;
import com.autonavi.minimap.life.db.model.Shortcut;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class ShortcutDao extends AbstractDao<Shortcut, Long> {
    public static final String TABLENAME = "SHORTCUT";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;
        public static final Property f;
        public static final Property g;
        public static final Property h;

        static {
            Property property = new Property(0, Long.class, "id", true, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, String.class, "displayName", false, "DISPLAY_NAME");
            b = property2;
            Property property3 = new Property(2, String.class, "searchName", false, "SEARCH_NAME");
            c = property3;
            Property property4 = new Property(3, Integer.class, "actionType", false, "ACTION_TYPE");
            d = property4;
            Property property5 = new Property(4, String.class, Constants.SERVICE_ACTION_URL, false, "ACTION_URL");
            e = property5;
            Property property6 = new Property(5, String.class, "iconUrl", false, "ICON_URL");
            f = property6;
            Property property7 = new Property(6, String.class, "iconPath", false, "ICON_PATH");
            g = property7;
            Property property8 = new Property(7, Boolean.class, "isMarking", false, "IS_MARKING");
            h = property8;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        Shortcut shortcut = (Shortcut) obj;
        sQLiteStatement.clearBindings();
        Long l = shortcut.a;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String str = shortcut.b;
        if (str != null) {
            sQLiteStatement.bindString(2, str);
        }
        String str2 = shortcut.c;
        if (str2 != null) {
            sQLiteStatement.bindString(3, str2);
        }
        Integer num = shortcut.d;
        if (num != null) {
            sQLiteStatement.bindLong(4, (long) num.intValue());
        }
        String str3 = shortcut.e;
        if (str3 != null) {
            sQLiteStatement.bindString(5, str3);
        }
        String str4 = shortcut.f;
        if (str4 != null) {
            sQLiteStatement.bindString(6, str4);
        }
        String str5 = shortcut.g;
        if (str5 != null) {
            sQLiteStatement.bindString(7, str5);
        }
        Boolean bool = shortcut.h;
        if (bool != null) {
            sQLiteStatement.bindLong(8, bool.booleanValue() ? 1 : 0);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        Shortcut shortcut = (Shortcut) obj;
        if (shortcut != null) {
            return shortcut.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        Shortcut shortcut = (Shortcut) obj;
        int i2 = i + 0;
        Boolean bool = null;
        shortcut.a = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        shortcut.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        shortcut.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        shortcut.d = cursor.isNull(i5) ? null : Integer.valueOf(cursor.getInt(i5));
        int i6 = i + 4;
        shortcut.e = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        shortcut.f = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 6;
        shortcut.g = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            bool = Boolean.valueOf(cursor.getShort(i9) != 0);
        }
        shortcut.h = bool;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((Shortcut) obj).a = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public ShortcutDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"SHORTCUT\" (\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"DISPLAY_NAME\" TEXT,\"SEARCH_NAME\" TEXT,\"ACTION_TYPE\" INTEGER,\"ACTION_URL\" TEXT,\"ICON_URL\" TEXT,\"ICON_PATH\" TEXT,\"IS_MARKING\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"SHORTCUT\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        Shortcut shortcut = new Shortcut();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            shortcut.a = Long.valueOf(cursor.getLong(i2));
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            shortcut.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            shortcut.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            shortcut.d = Integer.valueOf(cursor.getInt(i5));
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            shortcut.e = cursor.getString(i6);
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            shortcut.f = cursor.getString(i7);
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            shortcut.g = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            shortcut.h = Boolean.valueOf(cursor.getShort(i9) != 0);
        }
        return shortcut;
    }
}
