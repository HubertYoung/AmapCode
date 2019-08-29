package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class RouteHistoryDao extends AbstractDao<btf, String> {
    public static final String TABLENAME = "RouteHistory";

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
            Property property2 = new Property(1, String.class, "routeName", false, "ROUTE_NAME");
            b = property2;
            Property property3 = new Property(2, Integer.class, "routeType", false, "ROUTE_TYPE");
            c = property3;
            Property property4 = new Property(3, String.class, "method", false, RPCDataItems.REQUEST_METHOD);
            d = property4;
            Property property5 = new Property(4, Integer.class, "startX", false, "START_X");
            e = property5;
            Property property6 = new Property(5, Integer.class, "startY", false, "START_Y");
            f = property6;
            Property property7 = new Property(6, Integer.class, "endX", false, "END_X");
            g = property7;
            Property property8 = new Property(7, Integer.class, "endY", false, "END_Y");
            h = property8;
            Property property9 = new Property(8, String.class, "fromPoiJson", false, "FROM_POI_JSON");
            i = property9;
            Property property10 = new Property(9, String.class, "midPoiJson", false, "MID_POI_JSON");
            j = property10;
            Property property11 = new Property(10, String.class, "toPoiJson", false, "TO_POI_JSON");
            k = property11;
            Property property12 = new Property(11, Long.class, "updateTime", false, "UPDATE_TIME");
            l = property12;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        btf btf = (btf) obj;
        sQLiteStatement.clearBindings();
        String str = btf.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = btf.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        Integer num = btf.c;
        if (num != null) {
            sQLiteStatement.bindLong(3, (long) num.intValue());
        }
        String str3 = btf.d;
        if (str3 != null) {
            sQLiteStatement.bindString(4, str3);
        }
        Integer num2 = btf.e;
        if (num2 != null) {
            sQLiteStatement.bindLong(5, (long) num2.intValue());
        }
        Integer num3 = btf.f;
        if (num3 != null) {
            sQLiteStatement.bindLong(6, (long) num3.intValue());
        }
        Integer num4 = btf.g;
        if (num4 != null) {
            sQLiteStatement.bindLong(7, (long) num4.intValue());
        }
        Integer num5 = btf.h;
        if (num5 != null) {
            sQLiteStatement.bindLong(8, (long) num5.intValue());
        }
        String str4 = btf.i;
        if (str4 != null) {
            sQLiteStatement.bindString(9, str4);
        }
        String str5 = btf.j;
        if (str5 != null) {
            sQLiteStatement.bindString(10, str5);
        }
        String str6 = btf.k;
        if (str6 != null) {
            sQLiteStatement.bindString(11, str6);
        }
        Long l = btf.l;
        if (l != null) {
            sQLiteStatement.bindLong(12, l.longValue());
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        btf btf = (btf) obj;
        if (btf != null) {
            return btf.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        btf btf = (btf) obj;
        int i2 = i + 0;
        Long l = null;
        btf.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        btf.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        btf.c = cursor.isNull(i4) ? null : Integer.valueOf(cursor.getInt(i4));
        int i5 = i + 3;
        btf.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        btf.e = cursor.isNull(i6) ? null : Integer.valueOf(cursor.getInt(i6));
        int i7 = i + 5;
        btf.f = cursor.isNull(i7) ? null : Integer.valueOf(cursor.getInt(i7));
        int i8 = i + 6;
        btf.g = cursor.isNull(i8) ? null : Integer.valueOf(cursor.getInt(i8));
        int i9 = i + 7;
        btf.h = cursor.isNull(i9) ? null : Integer.valueOf(cursor.getInt(i9));
        int i10 = i + 8;
        btf.i = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        btf.j = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 10;
        btf.k = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = i + 11;
        if (!cursor.isNull(i13)) {
            l = Long.valueOf(cursor.getLong(i13));
        }
        btf.l = l;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((btf) obj).a;
    }

    public RouteHistoryDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"RouteHistory\" (\"ID\" TEXT PRIMARY KEY NOT NULL UNIQUE ,\"ROUTE_NAME\" TEXT,\"ROUTE_TYPE\" INTEGER,\"METHOD\" TEXT,\"START_X\" INTEGER,\"START_Y\" INTEGER,\"END_X\" INTEGER,\"END_Y\" INTEGER,\"FROM_POI_JSON\" TEXT,\"MID_POI_JSON\" TEXT,\"TO_POI_JSON\" TEXT,\"UPDATE_TIME\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"RouteHistory\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        btf btf = new btf();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            btf.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            btf.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            btf.c = Integer.valueOf(cursor.getInt(i4));
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            btf.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            btf.e = Integer.valueOf(cursor.getInt(i6));
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            btf.f = Integer.valueOf(cursor.getInt(i7));
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            btf.g = Integer.valueOf(cursor.getInt(i8));
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            btf.h = Integer.valueOf(cursor.getInt(i9));
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            btf.i = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            btf.j = cursor.getString(i11);
        }
        int i12 = i + 10;
        if (!cursor.isNull(i12)) {
            btf.k = cursor.getString(i12);
        }
        int i13 = i + 11;
        if (!cursor.isNull(i13)) {
            btf.l = Long.valueOf(cursor.getLong(i13));
        }
        return btf;
    }
}
