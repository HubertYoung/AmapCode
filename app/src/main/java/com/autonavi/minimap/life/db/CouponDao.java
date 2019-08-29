package com.autonavi.minimap.life.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.sina.weibo.sdk.statistic.LogBuilder;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class CouponDao extends AbstractDao<avx, Void> {
    public static final String TABLENAME = "coupon";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;

        static {
            Property property = new Property(0, String.class, "id", false, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, Long.class, LogBuilder.KEY_START_TIME, false, "STARTTIME");
            b = property2;
            Property property3 = new Property(2, Long.class, LogBuilder.KEY_END_TIME, false, "ENDTIME");
            c = property3;
            Property property4 = new Property(3, Long.class, "addtime", false, "ADDTIME");
            d = property4;
            Property property5 = new Property(4, String.class, "json", false, "JSON");
            e = property5;
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
        avx avx = (avx) obj;
        sQLiteStatement.clearBindings();
        String str = avx.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        Long l = avx.b;
        if (l != null) {
            sQLiteStatement.bindLong(2, l.longValue());
        }
        Long l2 = avx.c;
        if (l2 != null) {
            sQLiteStatement.bindLong(3, l2.longValue());
        }
        Long l3 = avx.d;
        if (l3 != null) {
            sQLiteStatement.bindLong(4, l3.longValue());
        }
        String str2 = avx.e;
        if (str2 != null) {
            sQLiteStatement.bindString(5, str2);
        }
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        avx avx = (avx) obj;
        int i2 = i + 0;
        String str = null;
        avx.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        avx.b = cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3));
        int i4 = i + 2;
        avx.c = cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4));
        int i5 = i + 3;
        avx.d = cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5));
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            str = cursor.getString(i6);
        }
        avx.e = str;
    }

    public CouponDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"coupon\" (\"ID\" TEXT,\"STARTTIME\" INTEGER,\"ENDTIME\" INTEGER,\"ADDTIME\" INTEGER,\"JSON\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"coupon\"");
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        avx avx = new avx();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            avx.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            avx.b = Long.valueOf(cursor.getLong(i3));
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            avx.c = Long.valueOf(cursor.getLong(i4));
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            avx.d = Long.valueOf(cursor.getLong(i5));
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            avx.e = cursor.getString(i6);
        }
        return avx;
    }
}
