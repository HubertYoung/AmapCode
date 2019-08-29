package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.common.logging.util.avail.ExceptionData;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class RideHistoryDao extends AbstractDao<bte, String> {
    public static final String TABLENAME = "ride_table";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;
        public static final Property f;
        public static final Property g;
        public static final Property h;
        public static final Property i;
        public static final Property j;
        public static final Property k;
        public static final Property l;

        static {
            Property property = new Property(0, String.class, "id", true, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, Integer.class, "timeSeconds", false, "TIME_SECONDS");
            b = property2;
            Property property3 = new Property(2, Integer.class, "rideDistance", false, "RIDE_DISTANCE");
            c = property3;
            Property property4 = new Property(3, Integer.class, "calorie", false, "CALORIE");
            d = property4;
            Property property5 = new Property(4, Double.class, "averageSpeed", false, "AVERAGE_SPEED");
            e = property5;
            Property property6 = new Property(5, Long.class, "startTime", false, "START_TIME");
            f = property6;
            Property property7 = new Property(6, Long.class, AppInitCallback.SP_KEY_endTime, false, "END_TIME");
            g = property7;
            Property property8 = new Property(7, String.class, "traceViewURl", false, "TRACE_VIEW_URL");
            h = property8;
            Property property9 = new Property(8, String.class, "ridePoi", false, "RIDE_POI");
            i = property9;
            Property property10 = new Property(9, Integer.class, "type", false, ExceptionData.E_TYPE);
            j = property10;
            Property property11 = new Property(10, Integer.TYPE, "deleted", false, "DELETED");
            k = property11;
            Property property12 = new Property(11, Double.class, "maxSpeed", false, "MAX_SPEED");
            l = property12;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        bte bte = (bte) obj;
        sQLiteStatement.clearBindings();
        String str = bte.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        Integer num = bte.b;
        if (num != null) {
            sQLiteStatement.bindLong(2, (long) num.intValue());
        }
        Integer num2 = bte.c;
        if (num2 != null) {
            sQLiteStatement.bindLong(3, (long) num2.intValue());
        }
        Integer num3 = bte.d;
        if (num3 != null) {
            sQLiteStatement.bindLong(4, (long) num3.intValue());
        }
        Double d = bte.e;
        if (d != null) {
            sQLiteStatement.bindDouble(5, d.doubleValue());
        }
        Long l = bte.f;
        if (l != null) {
            sQLiteStatement.bindLong(6, l.longValue());
        }
        Long l2 = bte.g;
        if (l2 != null) {
            sQLiteStatement.bindLong(7, l2.longValue());
        }
        String str2 = bte.h;
        if (str2 != null) {
            sQLiteStatement.bindString(8, str2);
        }
        String str3 = bte.i;
        if (str3 != null) {
            sQLiteStatement.bindString(9, str3);
        }
        Integer num4 = bte.j;
        if (num4 != null) {
            sQLiteStatement.bindLong(10, (long) num4.intValue());
        }
        sQLiteStatement.bindLong(11, (long) bte.k);
        Double d2 = bte.l;
        if (d2 != null) {
            sQLiteStatement.bindDouble(12, d2.doubleValue());
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        bte bte = (bte) obj;
        if (bte != null) {
            return bte.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        bte bte = (bte) obj;
        int i2 = i + 0;
        Double d = null;
        bte.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        bte.b = cursor.isNull(i3) ? null : Integer.valueOf(cursor.getInt(i3));
        int i4 = i + 2;
        bte.c = cursor.isNull(i4) ? null : Integer.valueOf(cursor.getInt(i4));
        int i5 = i + 3;
        bte.d = cursor.isNull(i5) ? null : Integer.valueOf(cursor.getInt(i5));
        int i6 = i + 4;
        bte.e = cursor.isNull(i6) ? null : Double.valueOf(cursor.getDouble(i6));
        int i7 = i + 5;
        bte.f = cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7));
        int i8 = i + 6;
        bte.g = cursor.isNull(i8) ? null : Long.valueOf(cursor.getLong(i8));
        int i9 = i + 7;
        bte.h = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        bte.i = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        bte.j = cursor.isNull(i11) ? null : Integer.valueOf(cursor.getInt(i11));
        bte.k = cursor.getInt(i + 10);
        int i12 = i + 11;
        if (!cursor.isNull(i12)) {
            d = Double.valueOf(cursor.getDouble(i12));
        }
        bte.l = d;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((bte) obj).a;
    }

    public RideHistoryDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"ride_table\" (\"ID\" TEXT PRIMARY KEY NOT NULL UNIQUE ,\"TIME_SECONDS\" INTEGER,\"RIDE_DISTANCE\" INTEGER,\"CALORIE\" INTEGER,\"AVERAGE_SPEED\" REAL,\"START_TIME\" INTEGER,\"END_TIME\" INTEGER,\"TRACE_VIEW_URL\" TEXT,\"RIDE_POI\" TEXT,\"TYPE\" INTEGER,\"DELETED\" INTEGER NOT NULL DEFAULT 0 ,\"MAX_SPEED\" REAL);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"ride_table\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        bte bte = new bte();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            bte.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            bte.b = Integer.valueOf(cursor.getInt(i3));
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            bte.c = Integer.valueOf(cursor.getInt(i4));
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            bte.d = Integer.valueOf(cursor.getInt(i5));
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            bte.e = Double.valueOf(cursor.getDouble(i6));
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            bte.f = Long.valueOf(cursor.getLong(i7));
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            bte.g = Long.valueOf(cursor.getLong(i8));
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            bte.h = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            bte.i = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            bte.j = Integer.valueOf(cursor.getInt(i11));
        }
        bte.k = cursor.getInt(i + 10);
        int i12 = i + 11;
        if (!cursor.isNull(i12)) {
            bte.l = Double.valueOf(cursor.getDouble(i12));
        }
        return bte;
    }
}
