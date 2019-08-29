package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class H5WebStorageDao extends AbstractDao<bta, Void> {
    public static final String TABLENAME = "H5_WEB_STORAGE";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;

        static {
            Property property = new Property(0, String.class, "namespace", false, "NAMESPACE");
            a = property;
            Property property2 = new Property(1, String.class, "key", false, "KEY");
            b = property2;
            Property property3 = new Property(2, String.class, "value", false, "VALUE");
            c = property3;
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        return null;
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* bridge */ /* synthetic */ Object readKey(Cursor cursor, int i) {
        return null;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return null;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        bta bta = (bta) obj;
        sQLiteStatement.clearBindings();
        String str = bta.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = bta.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = bta.c;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        bta bta = (bta) obj;
        int i2 = i + 0;
        String str = null;
        bta.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        bta.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            str = cursor.getString(i4);
        }
        bta.c = str;
    }

    public H5WebStorageDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"H5_WEB_STORAGE\" (\"NAMESPACE\" TEXT,\"KEY\" TEXT,\"VALUE\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"H5_WEB_STORAGE\"");
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        bta bta = new bta();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            bta.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            bta.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            bta.c = cursor.getString(i4);
        }
        return bta;
    }
}
