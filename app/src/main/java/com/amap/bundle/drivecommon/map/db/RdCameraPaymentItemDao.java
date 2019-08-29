package com.amap.bundle.drivecommon.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class RdCameraPaymentItemDao extends AbstractDao<RdCameraPaymentItem, Void> {
    public static final String TABLENAME = "RDCAMERA_PAYMENT";

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

        static {
            Property property = new Property(0, Long.class, "navi_timestamp", false, "NAVI_TIMESTAMP");
            a = property;
            Property property2 = new Property(1, String.class, H5PageData.KEY_UC_START, false, "START");
            b = property2;
            Property property3 = new Property(2, String.class, "end", false, "END");
            c = property3;
            Property property4 = new Property(3, Float.class, "distance", false, "DISTANCE");
            d = property4;
            Property property5 = new Property(4, Float.class, "cost_time", false, "COST_TIME");
            e = property5;
            Property property6 = new Property(5, Float.class, "speed", false, RPCDataItems.SPEED);
            f = property6;
            Property property7 = new Property(6, String.class, "st_point", false, "ST_POINT");
            g = property7;
            Property property8 = new Property(7, String.class, "end_point", false, "END_POINT");
            h = property8;
            Property property9 = new Property(8, String.class, "violation_point", false, "VIOLATION_POINT");
            i = property9;
            Property property10 = new Property(9, String.class, "path_points", false, "PATH_POINTS");
            j = property10;
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
        RdCameraPaymentItem rdCameraPaymentItem = (RdCameraPaymentItem) obj;
        sQLiteStatement.clearBindings();
        Long l = rdCameraPaymentItem.navi_timestamp;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String str = rdCameraPaymentItem.start;
        if (str != null) {
            sQLiteStatement.bindString(2, str);
        }
        String str2 = rdCameraPaymentItem.end;
        if (str2 != null) {
            sQLiteStatement.bindString(3, str2);
        }
        Float f = rdCameraPaymentItem.distance;
        if (f != null) {
            sQLiteStatement.bindDouble(4, (double) f.floatValue());
        }
        Float f2 = rdCameraPaymentItem.cost_time;
        if (f2 != null) {
            sQLiteStatement.bindDouble(5, (double) f2.floatValue());
        }
        Float f3 = rdCameraPaymentItem.speed;
        if (f3 != null) {
            sQLiteStatement.bindDouble(6, (double) f3.floatValue());
        }
        String str3 = rdCameraPaymentItem.st_point;
        if (str3 != null) {
            sQLiteStatement.bindString(7, str3);
        }
        String str4 = rdCameraPaymentItem.end_point;
        if (str4 != null) {
            sQLiteStatement.bindString(8, str4);
        }
        String str5 = rdCameraPaymentItem.violation_point;
        if (str5 != null) {
            sQLiteStatement.bindString(9, str5);
        }
        String str6 = rdCameraPaymentItem.path_points;
        if (str6 != null) {
            sQLiteStatement.bindString(10, str6);
        }
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        RdCameraPaymentItem rdCameraPaymentItem = (RdCameraPaymentItem) obj;
        int i2 = i + 0;
        String str = null;
        rdCameraPaymentItem.navi_timestamp = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        rdCameraPaymentItem.start = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        rdCameraPaymentItem.end = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        rdCameraPaymentItem.distance = cursor.isNull(i5) ? null : Float.valueOf(cursor.getFloat(i5));
        int i6 = i + 4;
        rdCameraPaymentItem.cost_time = cursor.isNull(i6) ? null : Float.valueOf(cursor.getFloat(i6));
        int i7 = i + 5;
        rdCameraPaymentItem.speed = cursor.isNull(i7) ? null : Float.valueOf(cursor.getFloat(i7));
        int i8 = i + 6;
        rdCameraPaymentItem.st_point = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        rdCameraPaymentItem.end_point = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        rdCameraPaymentItem.violation_point = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            str = cursor.getString(i11);
        }
        rdCameraPaymentItem.path_points = str;
    }

    public RdCameraPaymentItemDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"RDCAMERA_PAYMENT\" (\"NAVI_TIMESTAMP\" INTEGER,\"START\" TEXT,\"END\" TEXT,\"DISTANCE\" REAL,\"COST_TIME\" REAL,\"SPEED\" REAL,\"ST_POINT\" TEXT,\"END_POINT\" TEXT,\"VIOLATION_POINT\" TEXT,\"PATH_POINTS\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"RDCAMERA_PAYMENT\"");
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        RdCameraPaymentItem rdCameraPaymentItem = new RdCameraPaymentItem();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            rdCameraPaymentItem.navi_timestamp = Long.valueOf(cursor.getLong(i2));
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            rdCameraPaymentItem.start = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            rdCameraPaymentItem.end = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            rdCameraPaymentItem.distance = Float.valueOf(cursor.getFloat(i5));
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            rdCameraPaymentItem.cost_time = Float.valueOf(cursor.getFloat(i6));
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            rdCameraPaymentItem.speed = Float.valueOf(cursor.getFloat(i7));
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            rdCameraPaymentItem.st_point = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            rdCameraPaymentItem.end_point = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            rdCameraPaymentItem.violation_point = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            rdCameraPaymentItem.path_points = cursor.getString(i11);
        }
        return rdCameraPaymentItem;
    }
}
