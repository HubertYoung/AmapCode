package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.security.bio.api.BioDetector;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import org.eclipse.mat.hprof.IHprofParserHandler;

public class SaveRouteDao extends AbstractDao<bti, String> {
    public static final String TABLENAME = "SAVE_ROUTE";

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
        public static final Property r;
        public static final Property s;
        public static final Property t;

        static {
            Property property = new Property(0, String.class, "key", true, "KEY");
            a = property;
            Property property2 = new Property(1, String.class, "userId", false, BioDetector.EXT_KEY_USER_ID_BUNDLE);
            b = property2;
            Property property3 = new Property(2, Integer.TYPE, "routeType", false, "ROUTE_TYPE");
            c = property3;
            Property property4 = new Property(3, Integer.TYPE, "startX", false, "START_X");
            d = property4;
            Property property5 = new Property(4, Integer.TYPE, "startY", false, "START_Y");
            e = property5;
            Property property6 = new Property(5, Integer.TYPE, "endX", false, "END_X");
            f = property6;
            Property property7 = new Property(6, Integer.TYPE, "endY", false, "END_Y");
            g = property7;
            Property property8 = new Property(7, String.class, "method", false, RPCDataItems.REQUEST_METHOD);
            h = property8;
            Property property9 = new Property(8, String.class, "version", false, IHprofParserHandler.VERSION);
            i = property9;
            Property property10 = new Property(9, String.class, "routeName", false, "ROUTE_NAME");
            j = property10;
            Property property11 = new Property(10, Integer.TYPE, "routeLength", false, "ROUTE_LENGTH");
            k = property11;
            Property property12 = new Property(11, String.class, "fromPoiJson", false, "FROM_POI_JSON");
            l = property12;
            Property property13 = new Property(12, String.class, "toPoiJson", false, "TO_POI_JSON");
            m = property13;
            Property property14 = new Property(13, String.class, "midPoiJson", false, "MID_POI_JSON");
            n = property14;
            Property property15 = new Property(14, Boolean.TYPE, "hasMidPoi", false, "HAS_MID_POI");
            o = property15;
            Property property16 = new Property(15, String.class, "routeNote", false, "ROUTE_NOTE");
            p = property16;
            Property property17 = new Property(16, String.class, "dataJson", false, "DATA_JSON");
            q = property17;
            Property property18 = new Property(17, Long.class, "createTime", false, "CREATE_TIME");
            r = property18;
            Property property19 = new Property(18, Integer.class, "transferred", false, "TRANSFERRED");
            s = property19;
            Property property20 = new Property(19, Integer.class, "costTime", false, "COST_TIME");
            t = property20;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        bti bti = (bti) obj;
        sQLiteStatement.clearBindings();
        String str = bti.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = bti.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        sQLiteStatement.bindLong(3, (long) bti.c);
        sQLiteStatement.bindLong(4, (long) bti.d);
        sQLiteStatement.bindLong(5, (long) bti.e);
        sQLiteStatement.bindLong(6, (long) bti.f);
        sQLiteStatement.bindLong(7, (long) bti.g);
        String str3 = bti.h;
        if (str3 != null) {
            sQLiteStatement.bindString(8, str3);
        }
        String str4 = bti.i;
        if (str4 != null) {
            sQLiteStatement.bindString(9, str4);
        }
        String str5 = bti.j;
        if (str5 != null) {
            sQLiteStatement.bindString(10, str5);
        }
        sQLiteStatement.bindLong(11, (long) bti.l);
        String str6 = bti.m;
        if (str6 != null) {
            sQLiteStatement.bindString(12, str6);
        }
        String str7 = bti.n;
        if (str7 != null) {
            sQLiteStatement.bindString(13, str7);
        }
        String str8 = bti.o;
        if (str8 != null) {
            sQLiteStatement.bindString(14, str8);
        }
        sQLiteStatement.bindLong(15, bti.p ? 1 : 0);
        String str9 = bti.q;
        if (str9 != null) {
            sQLiteStatement.bindString(16, str9);
        }
        String str10 = bti.r;
        if (str10 != null) {
            sQLiteStatement.bindString(17, str10);
        }
        Long l = bti.s;
        if (l != null) {
            sQLiteStatement.bindLong(18, l.longValue());
        }
        Integer num = bti.t;
        if (num != null) {
            sQLiteStatement.bindLong(19, (long) num.intValue());
        }
        sQLiteStatement.bindLong(20, (long) bti.u);
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        bti bti = (bti) obj;
        if (bti != null) {
            return bti.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        bti bti = (bti) obj;
        int i2 = i + 0;
        Integer num = null;
        bti.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        bti.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        bti.c = cursor.getInt(i + 2);
        bti.d = cursor.getInt(i + 3);
        bti.e = cursor.getInt(i + 4);
        bti.f = cursor.getInt(i + 5);
        bti.g = cursor.getInt(i + 6);
        int i4 = i + 7;
        bti.h = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 8;
        bti.i = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 9;
        bti.j = cursor.isNull(i6) ? null : cursor.getString(i6);
        bti.l = cursor.getInt(i + 10);
        int i7 = i + 11;
        bti.m = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 12;
        bti.n = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 13;
        bti.o = cursor.isNull(i9) ? null : cursor.getString(i9);
        bti.p = cursor.getShort(i + 14) != 0;
        int i10 = i + 15;
        bti.q = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 16;
        bti.r = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 17;
        bti.s = cursor.isNull(i12) ? null : Long.valueOf(cursor.getLong(i12));
        int i13 = i + 18;
        bti.t = cursor.isNull(i13) ? null : Integer.valueOf(cursor.getInt(i13));
        int i14 = i + 19;
        if (!cursor.isNull(i14)) {
            num = Integer.valueOf(cursor.getInt(i14));
        }
        bti.u = num.intValue();
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((bti) obj).a;
    }

    public SaveRouteDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"SAVE_ROUTE\" (\"KEY\" TEXT PRIMARY KEY NOT NULL ,\"USER_ID\" TEXT,\"ROUTE_TYPE\" INTEGER NOT NULL ,\"START_X\" INTEGER NOT NULL ,\"START_Y\" INTEGER NOT NULL ,\"END_X\" INTEGER NOT NULL ,\"END_Y\" INTEGER NOT NULL ,\"METHOD\" TEXT,\"VERSION\" TEXT,\"ROUTE_NAME\" TEXT,\"ROUTE_LENGTH\" INTEGER NOT NULL ,\"FROM_POI_JSON\" TEXT,\"TO_POI_JSON\" TEXT,\"MID_POI_JSON\" TEXT,\"HAS_MID_POI\" INTEGER NOT NULL ,\"ROUTE_NOTE\" TEXT,\"DATA_JSON\" TEXT,\"CREATE_TIME\" INTEGER,\"TRANSFERRED\" INTEGER NOT NULL DEFAULT 0,\"COST_TIME\" INTEGER NOT NULL DEFAULT 0);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"SAVE_ROUTE\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        bti bti = new bti();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            bti.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            bti.b = cursor.getString(i3);
        }
        bti.c = cursor.getInt(i + 2);
        bti.d = cursor.getInt(i + 3);
        bti.e = cursor.getInt(i + 4);
        bti.f = cursor.getInt(i + 5);
        bti.g = cursor.getInt(i + 6);
        int i4 = i + 7;
        if (!cursor.isNull(i4)) {
            bti.h = cursor.getString(i4);
        }
        int i5 = i + 8;
        if (!cursor.isNull(i5)) {
            bti.i = cursor.getString(i5);
        }
        int i6 = i + 9;
        if (!cursor.isNull(i6)) {
            bti.j = cursor.getString(i6);
        }
        bti.l = cursor.getInt(i + 10);
        int i7 = i + 11;
        if (!cursor.isNull(i7)) {
            bti.m = cursor.getString(i7);
        }
        int i8 = i + 12;
        if (!cursor.isNull(i8)) {
            bti.n = cursor.getString(i8);
        }
        int i9 = i + 13;
        if (!cursor.isNull(i9)) {
            bti.o = cursor.getString(i9);
        }
        bti.p = cursor.getShort(i + 14) != 0;
        int i10 = i + 15;
        if (!cursor.isNull(i10)) {
            bti.q = cursor.getString(i10);
        }
        int i11 = i + 16;
        if (!cursor.isNull(i11)) {
            bti.r = cursor.getString(i11);
        }
        int i12 = i + 17;
        if (!cursor.isNull(i12)) {
            bti.s = Long.valueOf(cursor.getLong(i12));
        }
        int i13 = i + 18;
        if (!cursor.isNull(i13)) {
            bti.t = Integer.valueOf(cursor.getInt(i13));
        }
        int i14 = i + 19;
        if (!cursor.isNull(i14)) {
            bti.u = cursor.getInt(i14);
        }
        return bti;
    }
}
