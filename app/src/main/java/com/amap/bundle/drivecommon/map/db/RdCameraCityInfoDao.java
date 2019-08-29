package com.amap.bundle.drivecommon.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.amap.bundle.drivecommon.map.db.model.RdCameraCityInfo;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class RdCameraCityInfoDao extends AbstractDao<RdCameraCityInfo, String> {
    public static final String TABLENAME = "RDCAMERA_CITYINFO";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;
        public static final Property f;
        public static final Property g;
        public static final Property h;

        static {
            Property property = new Property(0, String.class, "cityId", true, "CITY_ID");
            a = property;
            Property property2 = new Property(1, String.class, "cityName", false, "CITY_NAME");
            b = property2;
            Property property3 = new Property(2, String.class, "name", false, "NAME");
            c = property3;
            Property property4 = new Property(3, String.class, "carNumberPrefix", false, "CAR_NUMBER_PREFIX");
            d = property4;
            Property property5 = new Property(4, Integer.class, "carCodeLen", false, "CAR_CODE_LEN");
            e = property5;
            Property property6 = new Property(5, Integer.class, "carEngineLen", false, "CAR_ENGINE_LEN");
            f = property6;
            Property property7 = new Property(6, Integer.class, "carOwnerLen", false, "CAR_OWNER_LEN");
            g = property7;
            Property property8 = new Property(7, Integer.class, "proxyEnable", false, "PROXY_ENABLE");
            h = property8;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        RdCameraCityInfo rdCameraCityInfo = (RdCameraCityInfo) obj;
        sQLiteStatement.clearBindings();
        String str = rdCameraCityInfo.cityId;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = rdCameraCityInfo.cityName;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = rdCameraCityInfo.name;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = rdCameraCityInfo.carNumberPrefix;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
        Integer num = rdCameraCityInfo.carCodeLen;
        if (num != null) {
            sQLiteStatement.bindLong(5, (long) num.intValue());
        }
        Integer num2 = rdCameraCityInfo.carEngineLen;
        if (num2 != null) {
            sQLiteStatement.bindLong(6, (long) num2.intValue());
        }
        Integer num3 = rdCameraCityInfo.carOwnerLen;
        if (num3 != null) {
            sQLiteStatement.bindLong(7, (long) num3.intValue());
        }
        Integer num4 = rdCameraCityInfo.proxyEnable;
        if (num4 != null) {
            sQLiteStatement.bindLong(8, (long) num4.intValue());
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        RdCameraCityInfo rdCameraCityInfo = (RdCameraCityInfo) obj;
        if (rdCameraCityInfo != null) {
            return rdCameraCityInfo.cityId;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        RdCameraCityInfo rdCameraCityInfo = (RdCameraCityInfo) obj;
        int i2 = i + 0;
        Integer num = null;
        rdCameraCityInfo.cityId = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        rdCameraCityInfo.cityName = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        rdCameraCityInfo.name = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        rdCameraCityInfo.carNumberPrefix = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        rdCameraCityInfo.carCodeLen = cursor.isNull(i6) ? null : Integer.valueOf(cursor.getInt(i6));
        int i7 = i + 5;
        rdCameraCityInfo.carEngineLen = cursor.isNull(i7) ? null : Integer.valueOf(cursor.getInt(i7));
        int i8 = i + 6;
        rdCameraCityInfo.carOwnerLen = cursor.isNull(i8) ? null : Integer.valueOf(cursor.getInt(i8));
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            num = Integer.valueOf(cursor.getInt(i9));
        }
        rdCameraCityInfo.proxyEnable = num;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((RdCameraCityInfo) obj).cityId;
    }

    public RdCameraCityInfoDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"RDCAMERA_CITYINFO\" (\"CITY_ID\" TEXT PRIMARY KEY NOT NULL ,\"CITY_NAME\" TEXT,\"NAME\" TEXT,\"CAR_NUMBER_PREFIX\" TEXT,\"CAR_CODE_LEN\" INTEGER,\"CAR_ENGINE_LEN\" INTEGER,\"CAR_OWNER_LEN\" INTEGER,\"PROXY_ENABLE\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"RDCAMERA_CITYINFO\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        RdCameraCityInfo rdCameraCityInfo = new RdCameraCityInfo();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            rdCameraCityInfo.cityId = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            rdCameraCityInfo.cityName = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            rdCameraCityInfo.name = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            rdCameraCityInfo.carNumberPrefix = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            rdCameraCityInfo.carCodeLen = Integer.valueOf(cursor.getInt(i6));
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            rdCameraCityInfo.carEngineLen = Integer.valueOf(cursor.getInt(i7));
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            rdCameraCityInfo.carOwnerLen = Integer.valueOf(cursor.getInt(i8));
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            rdCameraCityInfo.proxyEnable = Integer.valueOf(cursor.getInt(i9));
        }
        return rdCameraCityInfo;
    }
}
