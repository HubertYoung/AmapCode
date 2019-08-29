package com.autonavi.minimap.life.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.autonavi.sdk.location.LocationInstrument;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class CinemaRecordDao extends AbstractDao<dov, Long> {
    public static final String TABLENAME = "cinema";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;

        static {
            Property property = new Property(0, Long.class, "_id", true, "_ID");
            a = property;
            Property property2 = new Property(1, String.class, LocationInstrument.LOCATION_EXTRAS_KEY_POIID, false, "POIID");
            b = property2;
            Property property3 = new Property(2, String.class, "citycode", false, "CITYCODE");
            c = property3;
            Property property4 = new Property(3, Integer.class, "clickcount", false, "CLICKCOUNT");
            d = property4;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        dov dov = (dov) obj;
        sQLiteStatement.clearBindings();
        Long l = dov.a;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String str = dov.b;
        if (str != null) {
            sQLiteStatement.bindString(2, str);
        }
        String str2 = dov.c;
        if (str2 != null) {
            sQLiteStatement.bindString(3, str2);
        }
        Integer num = dov.d;
        if (num != null) {
            sQLiteStatement.bindLong(4, (long) num.intValue());
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        dov dov = (dov) obj;
        if (dov != null) {
            return dov.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        dov dov = (dov) obj;
        int i2 = i + 0;
        Integer num = null;
        dov.a = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        dov.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        dov.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            num = Integer.valueOf(cursor.getInt(i5));
        }
        dov.d = num;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((dov) obj).a = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public CinemaRecordDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"cinema\" (\"_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"POIID\" TEXT,\"CITYCODE\" TEXT,\"CLICKCOUNT\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"cinema\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        dov dov = new dov();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            dov.a = Long.valueOf(cursor.getLong(i2));
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            dov.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            dov.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            dov.d = Integer.valueOf(cursor.getInt(i5));
        }
        return dov;
    }
}
