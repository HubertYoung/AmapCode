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

public class RunHistoryDao extends AbstractDao<btg, String> {
    public static final String TABLENAME = "run_table";

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
        public static final Property m;
        public static final Property n;
        public static final Property o;
        public static final Property p;
        public static final Property q;

        static {
            Property property = new Property(0, String.class, "id", true, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, Integer.class, "timeSeconds", false, "TIME_SECONDS");
            b = property2;
            Property property3 = new Property(2, Integer.class, "runDistance", false, "RUN_DISTANCE");
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
            Property property9 = new Property(8, String.class, "runPoi", false, "RUN_POI");
            i = property9;
            Property property10 = new Property(9, Integer.class, "type", false, ExceptionData.E_TYPE);
            j = property10;
            Property property11 = new Property(10, Integer.TYPE, "deleted", false, "DELETED");
            k = property11;
            Property property12 = new Property(11, Integer.class, "his_distance", false, "HIS_DISTANCE");
            l = property12;
            Property property13 = new Property(12, Integer.class, "ranking", false, "RANKING");
            m = property13;
            Property property14 = new Property(13, Integer.class, "percent", false, "PERCENT");
            n = property14;
            Property property15 = new Property(14, String.class, "achievementImg", false, "ACHIEVEMENTIMG");
            o = property15;
            Property property16 = new Property(15, Integer.class, "IsExistAch", false, "ISEXISTACH");
            p = property16;
            Property property17 = new Property(16, Integer.class, "Rising", false, "Rising");
            q = property17;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        btg btg = (btg) obj;
        sQLiteStatement.clearBindings();
        String str = btg.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        Integer num = btg.b;
        if (num != null) {
            sQLiteStatement.bindLong(2, (long) num.intValue());
        }
        Integer num2 = btg.c;
        if (num2 != null) {
            sQLiteStatement.bindLong(3, (long) num2.intValue());
        }
        Integer num3 = btg.d;
        if (num3 != null) {
            sQLiteStatement.bindLong(4, (long) num3.intValue());
        }
        Double d = btg.e;
        if (d != null) {
            sQLiteStatement.bindDouble(5, d.doubleValue());
        }
        Long l = btg.f;
        if (l != null) {
            sQLiteStatement.bindLong(6, l.longValue());
        }
        Long l2 = btg.g;
        if (l2 != null) {
            sQLiteStatement.bindLong(7, l2.longValue());
        }
        String str2 = btg.h;
        if (str2 != null) {
            sQLiteStatement.bindString(8, str2);
        }
        String str3 = btg.i;
        if (str3 != null) {
            sQLiteStatement.bindString(9, str3);
        }
        Integer num4 = btg.j;
        if (num4 != null) {
            sQLiteStatement.bindLong(10, (long) num4.intValue());
        }
        sQLiteStatement.bindLong(11, (long) btg.k);
        Integer num5 = btg.l;
        if (num5 != null) {
            sQLiteStatement.bindLong(12, (long) num5.intValue());
        }
        Integer num6 = btg.m;
        if (num6 != null) {
            sQLiteStatement.bindLong(13, (long) num6.intValue());
        }
        Integer num7 = btg.n;
        if (num7 != null) {
            sQLiteStatement.bindLong(14, (long) num7.intValue());
        }
        String str4 = btg.q;
        if (str4 != null) {
            sQLiteStatement.bindString(15, str4);
        }
        Integer num8 = btg.o;
        if (num8 != null) {
            sQLiteStatement.bindLong(16, (long) num8.intValue());
        }
        Integer num9 = btg.p;
        if (num9 != null) {
            sQLiteStatement.bindLong(17, (long) num9.intValue());
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        btg btg = (btg) obj;
        if (btg != null) {
            return btg.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        btg btg = (btg) obj;
        int i2 = i + 0;
        Integer num = null;
        btg.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        btg.b = cursor.isNull(i3) ? null : Integer.valueOf(cursor.getInt(i3));
        int i4 = i + 2;
        btg.c = cursor.isNull(i4) ? null : Integer.valueOf(cursor.getInt(i4));
        int i5 = i + 3;
        btg.d = cursor.isNull(i5) ? null : Integer.valueOf(cursor.getInt(i5));
        int i6 = i + 4;
        btg.e = cursor.isNull(i6) ? null : Double.valueOf(cursor.getDouble(i6));
        int i7 = i + 5;
        btg.f = cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7));
        int i8 = i + 6;
        btg.g = cursor.isNull(i8) ? null : Long.valueOf(cursor.getLong(i8));
        int i9 = i + 7;
        btg.h = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        btg.i = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        btg.j = cursor.isNull(i11) ? null : Integer.valueOf(cursor.getInt(i11));
        btg.k = cursor.getInt(i + 10);
        int i12 = i + 11;
        btg.l = cursor.isNull(i12) ? null : Integer.valueOf(cursor.getInt(i12));
        int i13 = i + 12;
        btg.m = cursor.isNull(i13) ? null : Integer.valueOf(cursor.getInt(i13));
        int i14 = i + 13;
        btg.n = cursor.isNull(i14) ? null : Integer.valueOf(cursor.getInt(i14));
        int i15 = i + 14;
        btg.q = cursor.isNull(i15) ? null : cursor.getString(i15);
        int i16 = i + 15;
        btg.o = cursor.isNull(i16) ? null : Integer.valueOf(cursor.getInt(i16));
        int i17 = i + 16;
        if (!cursor.isNull(i17)) {
            num = Integer.valueOf(cursor.getInt(i17));
        }
        btg.p = num;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((btg) obj).a;
    }

    public RunHistoryDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"run_table\" (\"ID\" TEXT PRIMARY KEY NOT NULL UNIQUE ,\"TIME_SECONDS\" INTEGER,\"RUN_DISTANCE\" INTEGER,\"CALORIE\" INTEGER,\"AVERAGE_SPEED\" REAL,\"START_TIME\" INTEGER,\"END_TIME\" INTEGER,\"TRACE_VIEW_URL\" TEXT,\"RUN_POI\" TEXT,\"TYPE\" INTEGER,\"DELETED\" INTEGER NOT NULL DEFAULT 0,\"HIS_DISTANCE\" INTEGER,\"RANKING\" INTEGER,\"PERCENT\" INTEGER,\"ACHIEVEMENTIMG\" TEXT,\"ISEXISTACH\" INTEGER,\"RISING\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"run_table\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        btg btg = new btg();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            btg.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            btg.b = Integer.valueOf(cursor.getInt(i3));
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            btg.c = Integer.valueOf(cursor.getInt(i4));
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            btg.d = Integer.valueOf(cursor.getInt(i5));
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            btg.e = Double.valueOf(cursor.getDouble(i6));
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            btg.f = Long.valueOf(cursor.getLong(i7));
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            btg.g = Long.valueOf(cursor.getLong(i8));
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            btg.h = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            btg.i = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            btg.j = Integer.valueOf(cursor.getInt(i11));
        }
        btg.k = cursor.getInt(i + 10);
        int i12 = i + 11;
        if (!cursor.isNull(i12)) {
            btg.l = Integer.valueOf(cursor.getInt(i12));
        }
        int i13 = i + 12;
        if (!cursor.isNull(i13)) {
            btg.m = Integer.valueOf(cursor.getInt(i13));
        }
        int i14 = i + 13;
        if (!cursor.isNull(i14)) {
            btg.n = Integer.valueOf(cursor.getInt(i14));
        }
        int i15 = i + 14;
        if (!cursor.isNull(i15)) {
            btg.q = cursor.getString(i15);
        }
        int i16 = i + 15;
        if (!cursor.isNull(i16)) {
            btg.o = Integer.valueOf(cursor.getInt(i16));
        }
        int i17 = i + 16;
        if (!cursor.isNull(i17)) {
            btg.p = Integer.valueOf(cursor.getInt(i17));
        }
        return btg;
    }
}
