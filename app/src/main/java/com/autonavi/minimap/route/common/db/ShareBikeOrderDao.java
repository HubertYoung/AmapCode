package com.autonavi.minimap.route.common.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class ShareBikeOrderDao extends AbstractDao<eab, String> {
    public static final String TABLENAME = "share_bike_orders";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;

        static {
            Property property = new Property(0, String.class, "orderId", true, "ORDER_ID");
            a = property;
            Property property2 = new Property(1, String.class, "cpSoure", false, "CP_SOURE");
            b = property2;
            Property property3 = new Property(2, String.class, "idIndex", false, "ID_INDEX");
            c = property3;
            Property property4 = new Property(3, String.class, "md5", false, "MD5");
            d = property4;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        eab eab = (eab) obj;
        sQLiteStatement.clearBindings();
        String str = eab.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = eab.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = eab.c;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = eab.d;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        eab eab = (eab) obj;
        if (eab != null) {
            return eab.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        eab eab = (eab) obj;
        int i2 = i + 0;
        String str = null;
        eab.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        eab.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        eab.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            str = cursor.getString(i5);
        }
        eab.d = str;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((eab) obj).a;
    }

    public ShareBikeOrderDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"share_bike_orders\" (\"ORDER_ID\" TEXT PRIMARY KEY NOT NULL UNIQUE ,\"CP_SOURE\" TEXT,\"ID_INDEX\" TEXT,\"MD5\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"share_bike_orders\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        eab eab = new eab();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            eab.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            eab.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            eab.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            eab.d = cursor.getString(i5);
        }
        return eab;
    }
}
