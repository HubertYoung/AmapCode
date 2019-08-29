package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class RealTimeBusItemDao extends AbstractDao<btd, Void> {
    public static final String TABLENAME = "REAL_TIME_BUS_ITEM";

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

        static {
            Property property = new Property(0, String.class, AutoJsonUtils.JSON_ADCODE, false, "ADCODE");
            a = property;
            Property property2 = new Property(1, String.class, "station_id", false, "STATION_ID");
            b = property2;
            Property property3 = new Property(2, String.class, "station_name", false, "STATION_NAME");
            c = property3;
            Property property4 = new Property(3, Double.class, "station_lat", false, "STATION_LAT");
            d = property4;
            Property property5 = new Property(4, Double.class, "station_lon", false, "STATION_LON");
            e = property5;
            Property property6 = new Property(5, String.class, "bus_areacode", false, "BUS_AREACODE");
            f = property6;
            Property property7 = new Property(6, String.class, "poiid1", false, "POIID1");
            g = property7;
            Property property8 = new Property(7, String.class, "bus_id", false, "BUS_ID");
            h = property8;
            Property property9 = new Property(8, String.class, "bus_name", false, "BUS_NAME");
            i = property9;
            Property property10 = new Property(9, String.class, "bus_describe", false, "BUS_DESCRIBE");
            j = property10;
            Property property11 = new Property(10, String.class, "alert_time", false, "ALERT_TIME");
            k = property11;
            Property property12 = new Property(11, String.class, "alert_day", false, "ALERT_DAY");
            l = property12;
            Property property13 = new Property(12, String.class, "is_push", false, "IS_PUSH");
            m = property13;
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
        btd btd = (btd) obj;
        sQLiteStatement.clearBindings();
        String str = btd.adcode;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = btd.station_id;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = btd.station_name;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        Double d = btd.station_lat;
        if (d != null) {
            sQLiteStatement.bindDouble(4, d.doubleValue());
        }
        Double d2 = btd.station_lon;
        if (d2 != null) {
            sQLiteStatement.bindDouble(5, d2.doubleValue());
        }
        String str4 = btd.bus_areacode;
        if (str4 != null) {
            sQLiteStatement.bindString(6, str4);
        }
        String str5 = btd.poiid1;
        if (str5 != null) {
            sQLiteStatement.bindString(7, str5);
        }
        String str6 = btd.bus_id;
        if (str6 != null) {
            sQLiteStatement.bindString(8, str6);
        }
        String str7 = btd.bus_name;
        if (str7 != null) {
            sQLiteStatement.bindString(9, str7);
        }
        String str8 = btd.bus_describe;
        if (str8 != null) {
            sQLiteStatement.bindString(10, str8);
        }
        String str9 = btd.alert_time;
        if (str9 != null) {
            sQLiteStatement.bindString(11, str9);
        }
        String str10 = btd.alert_day;
        if (str10 != null) {
            sQLiteStatement.bindString(12, str10);
        }
        String str11 = btd.is_push;
        if (str11 != null) {
            sQLiteStatement.bindString(13, str11);
        }
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        btd btd = (btd) obj;
        int i2 = i + 0;
        String str = null;
        btd.adcode = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        btd.station_id = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        btd.station_name = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        btd.station_lat = cursor.isNull(i5) ? null : Double.valueOf(cursor.getDouble(i5));
        int i6 = i + 4;
        btd.station_lon = cursor.isNull(i6) ? null : Double.valueOf(cursor.getDouble(i6));
        int i7 = i + 5;
        btd.bus_areacode = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 6;
        btd.poiid1 = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        btd.bus_id = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        btd.bus_name = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        btd.bus_describe = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 10;
        btd.alert_time = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = i + 11;
        btd.alert_day = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            str = cursor.getString(i14);
        }
        btd.is_push = str;
    }

    public RealTimeBusItemDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"REAL_TIME_BUS_ITEM\" (\"ADCODE\" TEXT,\"STATION_ID\" TEXT,\"STATION_NAME\" TEXT,\"STATION_LAT\" REAL,\"STATION_LON\" REAL,\"BUS_AREACODE\" TEXT,\"POIID1\" TEXT,\"BUS_ID\" TEXT,\"BUS_NAME\" TEXT,\"BUS_DESCRIBE\" TEXT,\"ALERT_TIME\" TEXT,\"ALERT_DAY\" TEXT,\"IS_PUSH\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"REAL_TIME_BUS_ITEM\"");
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        btd btd = new btd();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            btd.adcode = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            btd.station_id = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            btd.station_name = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            btd.station_lat = Double.valueOf(cursor.getDouble(i5));
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            btd.station_lon = Double.valueOf(cursor.getDouble(i6));
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            btd.bus_areacode = cursor.getString(i7);
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            btd.poiid1 = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            btd.bus_id = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            btd.bus_name = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            btd.bus_describe = cursor.getString(i11);
        }
        int i12 = i + 10;
        if (!cursor.isNull(i12)) {
            btd.alert_time = cursor.getString(i12);
        }
        int i13 = i + 11;
        if (!cursor.isNull(i13)) {
            btd.alert_day = cursor.getString(i13);
        }
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            btd.is_push = cursor.getString(i14);
        }
        return btd;
    }
}
