package com.autonavi.map.carowner.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.autonavi.map.db.model.CarOwnerInformation;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class CarOwnerInformationDao extends AbstractDao<CarOwnerInformation, String> {
    public static final String TABLENAME = "CAR_OWNER_INFORMATION";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;

        static {
            Property property = new Property(0, String.class, Oauth2AccessToken.KEY_UID, true, "UID");
            a = property;
            Property property2 = new Property(1, String.class, "driver_licenceValidityDate", false, "DRIVER_LICENCE_VALIDITY_DATE");
            b = property2;
            Property property3 = new Property(2, String.class, "driver_dateTime", false, "DRIVER_DATE_TIME");
            c = property3;
            Property property4 = new Property(3, Integer.class, "driver_driverReminderSetting", false, "DRIVER_DRIVER_REMINDER_SETTING");
            d = property4;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        CarOwnerInformation carOwnerInformation = (CarOwnerInformation) obj;
        sQLiteStatement.clearBindings();
        String str = carOwnerInformation.uid;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = carOwnerInformation.driver_licenceValidityDate;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = carOwnerInformation.driver_dateTime;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        Integer num = carOwnerInformation.driver_driverReminderSetting;
        if (num != null) {
            sQLiteStatement.bindLong(4, (long) num.intValue());
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        CarOwnerInformation carOwnerInformation = (CarOwnerInformation) obj;
        if (carOwnerInformation != null) {
            return carOwnerInformation.uid;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        CarOwnerInformation carOwnerInformation = (CarOwnerInformation) obj;
        int i2 = i + 0;
        Integer num = null;
        carOwnerInformation.uid = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        carOwnerInformation.driver_licenceValidityDate = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        carOwnerInformation.driver_dateTime = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            num = Integer.valueOf(cursor.getInt(i5));
        }
        carOwnerInformation.driver_driverReminderSetting = num;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((CarOwnerInformation) obj).uid;
    }

    public CarOwnerInformationDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"CAR_OWNER_INFORMATION\" (\"UID\" TEXT PRIMARY KEY NOT NULL ,\"DRIVER_LICENCE_VALIDITY_DATE\" TEXT,\"DRIVER_DATE_TIME\" TEXT,\"DRIVER_DRIVER_REMINDER_SETTING\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"CAR_OWNER_INFORMATION\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        CarOwnerInformation carOwnerInformation = new CarOwnerInformation();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            carOwnerInformation.uid = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            carOwnerInformation.driver_licenceValidityDate = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            carOwnerInformation.driver_dateTime = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            carOwnerInformation.driver_driverReminderSetting = Integer.valueOf(cursor.getInt(i5));
        }
        return carOwnerInformation;
    }
}
