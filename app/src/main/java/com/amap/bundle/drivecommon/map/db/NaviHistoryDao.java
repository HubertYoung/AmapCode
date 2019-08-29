package com.amap.bundle.drivecommon.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.common.logging.util.avail.ExceptionData;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class NaviHistoryDao extends AbstractDao<sj, String> {
    public static final String TABLENAME = "NAVI_HISTORY";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;

        static {
            Property property = new Property(0, String.class, "key", true, "KEY");
            a = property;
            Property property2 = new Property(1, Integer.class, "type", false, ExceptionData.E_TYPE);
            b = property2;
            Property property3 = new Property(2, String.class, "extra", false, "EXTRA");
            c = property3;
            Property property4 = new Property(3, String.class, "poiJson", false, "POI_JSON");
            d = property4;
            Property property5 = new Property(4, Long.class, "updateTime", false, "UPDATE_TIME");
            e = property5;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        sj sjVar = (sj) obj;
        sQLiteStatement.clearBindings();
        String str = sjVar.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        Integer num = sjVar.b;
        if (num != null) {
            sQLiteStatement.bindLong(2, (long) num.intValue());
        }
        String str2 = sjVar.c;
        if (str2 != null) {
            sQLiteStatement.bindString(3, str2);
        }
        String str3 = sjVar.d;
        if (str3 != null) {
            sQLiteStatement.bindString(4, str3);
        }
        Long l = sjVar.e;
        if (l != null) {
            sQLiteStatement.bindLong(5, l.longValue());
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        sj sjVar = (sj) obj;
        if (sjVar != null) {
            return sjVar.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        sj sjVar = (sj) obj;
        int i2 = i + 0;
        Long l = null;
        sjVar.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        sjVar.b = cursor.isNull(i3) ? null : Integer.valueOf(cursor.getInt(i3));
        int i4 = i + 2;
        sjVar.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        sjVar.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            l = Long.valueOf(cursor.getLong(i6));
        }
        sjVar.e = l;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((sj) obj).a;
    }

    public NaviHistoryDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"NAVI_HISTORY\" (\"KEY\" TEXT PRIMARY KEY NOT NULL ,\"TYPE\" INTEGER,\"EXTRA\" TEXT,\"POI_JSON\" TEXT,\"UPDATE_TIME\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"NAVI_HISTORY\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        sj sjVar = new sj();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            sjVar.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            sjVar.b = Integer.valueOf(cursor.getInt(i3));
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            sjVar.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            sjVar.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            sjVar.e = Long.valueOf(cursor.getLong(i6));
        }
        return sjVar;
    }
}
