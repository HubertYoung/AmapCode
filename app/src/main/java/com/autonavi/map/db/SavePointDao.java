package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class SavePointDao extends AbstractDao<bth, String> {
    public static final String TABLENAME = "SAVE_POINT";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;
        public static final Property f;

        static {
            Property property = new Property(0, String.class, "key", true, "KEY");
            a = property;
            Property property2 = new Property(1, String.class, "userId", false, BioDetector.EXT_KEY_USER_ID_BUNDLE);
            b = property2;
            Property property3 = new Property(2, String.class, "poiJson", false, "POI_JSON");
            c = property3;
            Property property4 = new Property(3, String.class, "commonName", false, "COMMON_NAME");
            d = property4;
            Property property5 = new Property(4, String.class, TrafficUtil.POIID, false, "POI_ID");
            e = property5;
            Property property6 = new Property(5, Long.class, "createTime", false, "CREATE_TIME");
            f = property6;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        bth bth = (bth) obj;
        sQLiteStatement.clearBindings();
        String str = bth.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = bth.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = bth.c;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = bth.d;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
        String str5 = bth.e;
        if (str5 != null) {
            sQLiteStatement.bindString(5, str5);
        }
        Long l = bth.f;
        if (l != null) {
            sQLiteStatement.bindLong(6, l.longValue());
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        bth bth = (bth) obj;
        if (bth != null) {
            return bth.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        bth bth = (bth) obj;
        int i2 = i + 0;
        Long l = null;
        bth.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        bth.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        bth.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        bth.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        bth.e = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            l = Long.valueOf(cursor.getLong(i7));
        }
        bth.f = l;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((bth) obj).a;
    }

    public SavePointDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"SAVE_POINT\" (\"KEY\" TEXT PRIMARY KEY NOT NULL ,\"USER_ID\" TEXT,\"POI_JSON\" TEXT,\"COMMON_NAME\" TEXT,\"POI_ID\" TEXT,\"CREATE_TIME\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"SAVE_POINT\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        bth bth = new bth();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            bth.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            bth.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            bth.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            bth.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            bth.e = cursor.getString(i6);
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            bth.f = Long.valueOf(cursor.getLong(i7));
        }
        return bth;
    }
}
