package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.db.model.Vehicles;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class VehiclesLocalDao extends AbstractDao<Vehicles, Long> {
    public static final String TABLENAME = "VEHICLES_LOCAL";

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
        public static final Property u;
        public static final Property v;
        public static final Property w;
        public static final Property x;

        static {
            Property property = new Property(0, Long.class, "id", true, "_id");
            a = property;
            Property property2 = new Property(1, Long.class, "vehicle_id", false, "VEHICLE_ID");
            b = property2;
            Property property3 = new Property(2, String.class, Oauth2AccessToken.KEY_UID, false, "UID");
            c = property3;
            Property property4 = new Property(3, String.class, "vehicle_plateNum", false, "VEHICLE_PLATE_NUM");
            d = property4;
            Property property5 = new Property(4, String.class, "vehicle_vehiclecode", false, "VEHICLE_VEHICLECODE");
            e = property5;
            Property property6 = new Property(5, String.class, "vehicle_brandName", false, "VEHICLE_BRAND_NAME");
            f = property6;
            Property property7 = new Property(6, String.class, "vehicle_vehicleStyle", false, "VEHICLE_VEHICLE_STYLE");
            g = property7;
            Property property8 = new Property(7, String.class, "vehicle_dischargeRate", false, "VEHICLE_DISCHARGE_RATE");
            h = property8;
            Property property9 = new Property(8, String.class, "vehicle_years", false, "VEHICLE_YEARS");
            i = property9;
            Property property10 = new Property(9, String.class, "vehicle_remark", false, "VEHICLE_REMARK");
            j = property10;
            Property property11 = new Property(10, String.class, "vehicle_vehicleMsg", false, "VEHICLE_VEHICLE_MSG");
            k = property11;
            Property property12 = new Property(11, String.class, "vehicle_telephone", false, "VEHICLE_TELEPHONE");
            l = property12;
            Property property13 = new Property(12, String.class, "vehicle_vehicleLogo", false, "VEHICLE_VEHICLE_LOGO");
            m = property13;
            Property property14 = new Property(13, Integer.class, "vehicle_oftenUse", false, "VEHICLE_OFTEN_USE");
            n = property14;
            Property property15 = new Property(14, String.class, "vehicle_frameNum", false, "VEHICLE_FRAME_NUM");
            o = property15;
            Property property16 = new Property(15, String.class, "vehicle_engineNum", false, "VEHICLE_ENGINE_NUM");
            p = property16;
            Property property17 = new Property(16, String.class, "vehicle_violationUrl", false, "VEHICLE_VIOLATION_URL");
            q = property17;
            Property property18 = new Property(17, Integer.class, "vehicle_violationNum", false, "VEHICLE_VIOLATION_NUM");
            r = property18;
            Property property19 = new Property(18, String.class, "vehicle_validityPeriod", false, "VEHICLE_VALIDITY_PERIOD");
            s = property19;
            Property property20 = new Property(19, String.class, "vehicle_modificationTimes", false, "VEHICLE_MODIFICATION_TIMES");
            t = property20;
            Property property21 = new Property(20, Integer.class, "vehicle_checkReminder", false, "VEHICLE_CHECK_REMINDER");
            u = property21;
            Property property22 = new Property(21, Integer.class, "vehicle_violationReminder", false, "VEHICLE_VIOLATION_REMINDER");
            v = property22;
            Property property23 = new Property(22, Integer.class, "vehicle_limitReminder", false, "VEHICLE_LIMIT_REMINDER");
            w = property23;
            Property property24 = new Property(23, String.class, "ocrRequestId", false, "OCRREQUESTID");
            x = property24;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        Vehicles vehicles = (Vehicles) obj;
        sQLiteStatement.clearBindings();
        Long l = vehicles.id;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        Long l2 = vehicles.vehicle_id;
        if (l2 != null) {
            sQLiteStatement.bindLong(2, l2.longValue());
        }
        String str = vehicles.uid;
        if (str != null) {
            sQLiteStatement.bindString(3, str);
        }
        String str2 = vehicles.vehicle_plateNum;
        if (str2 != null) {
            sQLiteStatement.bindString(4, str2);
        }
        String str3 = vehicles.vehicle_vehiclecode;
        if (str3 != null) {
            sQLiteStatement.bindString(5, str3);
        }
        String str4 = vehicles.vehicle_brandName;
        if (str4 != null) {
            sQLiteStatement.bindString(6, str4);
        }
        String str5 = vehicles.vehicle_vehicleStyle;
        if (str5 != null) {
            sQLiteStatement.bindString(7, str5);
        }
        String str6 = vehicles.vehicle_dischargeRate;
        if (str6 != null) {
            sQLiteStatement.bindString(8, str6);
        }
        String str7 = vehicles.vehicle_years;
        if (str7 != null) {
            sQLiteStatement.bindString(9, str7);
        }
        String str8 = vehicles.vehicle_remark;
        if (str8 != null) {
            sQLiteStatement.bindString(10, str8);
        }
        String str9 = vehicles.vehicle_vehicleMsg;
        if (str9 != null) {
            sQLiteStatement.bindString(11, str9);
        }
        String str10 = vehicles.vehicle_telephone;
        if (str10 != null) {
            sQLiteStatement.bindString(12, str10);
        }
        String str11 = vehicles.vehicle_vehicleLogo;
        if (str11 != null) {
            sQLiteStatement.bindString(13, str11);
        }
        Integer num = vehicles.vehicle_oftenUse;
        if (num != null) {
            sQLiteStatement.bindLong(14, (long) num.intValue());
        }
        String str12 = vehicles.vehicle_frameNum;
        if (str12 != null) {
            sQLiteStatement.bindString(15, str12);
        }
        String str13 = vehicles.vehicle_engineNum;
        if (str13 != null) {
            sQLiteStatement.bindString(16, str13);
        }
        String str14 = vehicles.vehicle_violationUrl;
        if (str14 != null) {
            sQLiteStatement.bindString(17, str14);
        }
        Integer num2 = vehicles.vehicle_violationNum;
        if (num2 != null) {
            sQLiteStatement.bindLong(18, (long) num2.intValue());
        }
        String str15 = vehicles.vehicle_validityPeriod;
        if (str15 != null) {
            sQLiteStatement.bindString(19, str15);
        }
        String str16 = vehicles.vehicle_modificationTimes;
        if (str16 != null) {
            sQLiteStatement.bindString(20, str16);
        }
        Integer num3 = vehicles.vehicle_checkReminder;
        if (num3 != null) {
            sQLiteStatement.bindLong(21, (long) num3.intValue());
        }
        Integer num4 = vehicles.vehicle_violationReminder;
        if (num4 != null) {
            sQLiteStatement.bindLong(22, (long) num4.intValue());
        }
        Integer num5 = vehicles.vehicle_limitReminder;
        if (num5 != null) {
            sQLiteStatement.bindLong(23, (long) num5.intValue());
        }
        String str17 = vehicles.ocrRequestId;
        if (str17 != null) {
            sQLiteStatement.bindString(24, str17);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        Vehicles vehicles = (Vehicles) obj;
        if (vehicles != null) {
            return vehicles.id;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        Vehicles vehicles = (Vehicles) obj;
        int i2 = i + 0;
        String str = null;
        vehicles.id = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        vehicles.vehicle_id = cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3));
        int i4 = i + 2;
        vehicles.uid = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        vehicles.vehicle_plateNum = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        vehicles.vehicle_vehiclecode = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        vehicles.vehicle_brandName = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 6;
        vehicles.vehicle_vehicleStyle = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        vehicles.vehicle_dischargeRate = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        vehicles.vehicle_years = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        vehicles.vehicle_remark = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 10;
        vehicles.vehicle_vehicleMsg = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = i + 11;
        vehicles.vehicle_telephone = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = i + 12;
        vehicles.vehicle_vehicleLogo = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = i + 13;
        vehicles.vehicle_oftenUse = cursor.isNull(i15) ? null : Integer.valueOf(cursor.getInt(i15));
        int i16 = i + 14;
        vehicles.vehicle_frameNum = cursor.isNull(i16) ? null : cursor.getString(i16);
        int i17 = i + 15;
        vehicles.vehicle_engineNum = cursor.isNull(i17) ? null : cursor.getString(i17);
        int i18 = i + 16;
        vehicles.vehicle_violationUrl = cursor.isNull(i18) ? null : cursor.getString(i18);
        int i19 = i + 17;
        vehicles.vehicle_violationNum = cursor.isNull(i19) ? null : Integer.valueOf(cursor.getInt(i19));
        int i20 = i + 18;
        vehicles.vehicle_validityPeriod = cursor.isNull(i20) ? null : cursor.getString(i20);
        int i21 = i + 19;
        vehicles.vehicle_modificationTimes = cursor.isNull(i21) ? null : cursor.getString(i21);
        int i22 = i + 20;
        vehicles.vehicle_checkReminder = cursor.isNull(i22) ? null : Integer.valueOf(cursor.getInt(i22));
        int i23 = i + 21;
        vehicles.vehicle_violationReminder = cursor.isNull(i23) ? null : Integer.valueOf(cursor.getInt(i23));
        int i24 = i + 22;
        vehicles.vehicle_limitReminder = cursor.isNull(i24) ? null : Integer.valueOf(cursor.getInt(i24));
        int i25 = i + 23;
        if (!cursor.isNull(i25)) {
            str = cursor.getString(i25);
        }
        vehicles.ocrRequestId = str;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((Vehicles) obj).id = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public VehiclesLocalDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        AMapLog.i("zyl", "CREATE TABLE VEHICLES_LOCAL");
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"VEHICLES_LOCAL\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"VEHICLE_ID\" INTEGER,\"UID\" TEXT,\"VEHICLE_PLATE_NUM\" TEXT,\"VEHICLE_VEHICLECODE\" TEXT,\"VEHICLE_BRAND_NAME\" TEXT,\"VEHICLE_VEHICLE_STYLE\" TEXT,\"VEHICLE_DISCHARGE_RATE\" TEXT,\"VEHICLE_YEARS\" TEXT,\"VEHICLE_REMARK\" TEXT,\"VEHICLE_VEHICLE_MSG\" TEXT,\"VEHICLE_TELEPHONE\" TEXT,\"VEHICLE_VEHICLE_LOGO\" TEXT,\"VEHICLE_OFTEN_USE\" INTEGER,\"VEHICLE_FRAME_NUM\" TEXT,\"VEHICLE_ENGINE_NUM\" TEXT,\"VEHICLE_VIOLATION_URL\" TEXT,\"VEHICLE_VIOLATION_NUM\" INTEGER,\"VEHICLE_VALIDITY_PERIOD\" TEXT,\"VEHICLE_MODIFICATION_TIMES\" TEXT,\"VEHICLE_CHECK_REMINDER\" INTEGER,\"VEHICLE_VIOLATION_REMINDER\" INTEGER,\"VEHICLE_LIMIT_REMINDER\" INTEGER,\"OCRREQUESTID\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
        AMapLog.i("zyl", "CREATE TABLE VEHICLES_LOCAL END");
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"VEHICLES_LOCAL\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        Vehicles vehicles = new Vehicles();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            vehicles.id = Long.valueOf(cursor.getLong(i2));
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            vehicles.vehicle_id = Long.valueOf(cursor.getLong(i3));
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            vehicles.uid = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            vehicles.vehicle_plateNum = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            vehicles.vehicle_vehiclecode = cursor.getString(i6);
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            vehicles.vehicle_brandName = cursor.getString(i7);
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            vehicles.vehicle_vehicleStyle = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            vehicles.vehicle_dischargeRate = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            vehicles.vehicle_years = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            vehicles.vehicle_remark = cursor.getString(i11);
        }
        int i12 = i + 10;
        if (!cursor.isNull(i12)) {
            vehicles.vehicle_vehicleMsg = cursor.getString(i12);
        }
        int i13 = i + 11;
        if (!cursor.isNull(i13)) {
            vehicles.vehicle_telephone = cursor.getString(i13);
        }
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            vehicles.vehicle_vehicleLogo = cursor.getString(i14);
        }
        int i15 = i + 13;
        if (!cursor.isNull(i15)) {
            vehicles.vehicle_oftenUse = Integer.valueOf(cursor.getInt(i15));
        }
        int i16 = i + 14;
        if (!cursor.isNull(i16)) {
            vehicles.vehicle_frameNum = cursor.getString(i16);
        }
        int i17 = i + 15;
        if (!cursor.isNull(i17)) {
            vehicles.vehicle_engineNum = cursor.getString(i17);
        }
        int i18 = i + 16;
        if (!cursor.isNull(i18)) {
            vehicles.vehicle_violationUrl = cursor.getString(i18);
        }
        int i19 = i + 17;
        if (!cursor.isNull(i19)) {
            vehicles.vehicle_violationNum = Integer.valueOf(cursor.getInt(i19));
        }
        int i20 = i + 18;
        if (!cursor.isNull(i20)) {
            vehicles.vehicle_validityPeriod = cursor.getString(i20);
        }
        int i21 = i + 19;
        if (!cursor.isNull(i21)) {
            vehicles.vehicle_modificationTimes = cursor.getString(i21);
        }
        int i22 = i + 20;
        if (!cursor.isNull(i22)) {
            vehicles.vehicle_checkReminder = Integer.valueOf(cursor.getInt(i22));
        }
        int i23 = i + 21;
        if (!cursor.isNull(i23)) {
            vehicles.vehicle_violationReminder = Integer.valueOf(cursor.getInt(i23));
        }
        int i24 = i + 22;
        if (!cursor.isNull(i24)) {
            vehicles.vehicle_limitReminder = Integer.valueOf(cursor.getInt(i24));
        }
        int i25 = i + 23;
        if (!cursor.isNull(i25)) {
            vehicles.ocrRequestId = cursor.getString(i25);
        }
        return vehicles;
    }
}
