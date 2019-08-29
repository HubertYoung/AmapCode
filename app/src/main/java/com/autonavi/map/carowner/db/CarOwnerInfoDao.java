package com.autonavi.map.carowner.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.autonavi.miniapp.plugin.carowner.CarOwnerHelper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class CarOwnerInfoDao extends AbstractDao<bsy, String> {
    public static final String TABLENAME = "CAR_OWNER_INFO";

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
            Property property = new Property(0, String.class, Oauth2AccessToken.KEY_UID, true, "UID");
            a = property;
            Property property2 = new Property(1, String.class, "owner_name", false, "OWNER_NAME");
            b = property2;
            Property property3 = new Property(2, String.class, "plate_numbers", false, "PLATE_NUMBERS");
            c = property3;
            Property property4 = new Property(3, String.class, "car_brand", false, "CAR_BRAND");
            d = property4;
            Property property5 = new Property(4, String.class, "car_type", false, "CAR_TYPE");
            e = property5;
            Property property6 = new Property(5, Boolean.class, "car_license_annual_inspection", false, "CAR_LICENSE_ANNUAL_INSPECTION");
            f = property6;
            Property property7 = new Property(6, String.class, CarOwnerHelper.OPTIONAL_ITEM_VIN, false, "VIN");
            g = property7;
            Property property8 = new Property(7, String.class, "engine_numbers", false, "ENGINE_NUMBERS");
            h = property8;
            Property property9 = new Property(8, Long.class, "car_license_validity", false, "CAR_LICENSE_VALIDITY");
            i = property9;
            Property property10 = new Property(9, Boolean.class, "owner_license_annual_inspection", false, "OWNER_LICENSE_ANNUAL_INSPECTION");
            j = property10;
            Property property11 = new Property(10, Long.class, "when_get_owner_license", false, "WHEN_GET_OWNER_LICENSE");
            k = property11;
            Property property12 = new Property(11, Long.class, "owner_license_valid_date", false, "OWNER_LICENSE_VALID_DATE");
            l = property12;
            Property property13 = new Property(12, Boolean.class, "car_offence_reminder", false, "CAR_OFFENCE_REMINDER");
            m = property13;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        bsy bsy = (bsy) obj;
        sQLiteStatement.clearBindings();
        String str = bsy.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = bsy.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = bsy.c;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = bsy.d;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
        String str5 = bsy.e;
        if (str5 != null) {
            sQLiteStatement.bindString(5, str5);
        }
        Boolean bool = bsy.f;
        long j = 0;
        if (bool != null) {
            sQLiteStatement.bindLong(6, bool.booleanValue() ? 1 : 0);
        }
        String str6 = bsy.g;
        if (str6 != null) {
            sQLiteStatement.bindString(7, str6);
        }
        String str7 = bsy.h;
        if (str7 != null) {
            sQLiteStatement.bindString(8, str7);
        }
        Long l = bsy.i;
        if (l != null) {
            sQLiteStatement.bindLong(9, l.longValue());
        }
        Boolean bool2 = bsy.j;
        if (bool2 != null) {
            sQLiteStatement.bindLong(10, bool2.booleanValue() ? 1 : 0);
        }
        Long l2 = bsy.k;
        if (l2 != null) {
            sQLiteStatement.bindLong(11, l2.longValue());
        }
        Long l3 = bsy.l;
        if (l3 != null) {
            sQLiteStatement.bindLong(12, l3.longValue());
        }
        Boolean bool3 = bsy.m;
        if (bool3 != null) {
            if (bool3.booleanValue()) {
                j = 1;
            }
            sQLiteStatement.bindLong(13, j);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        bsy bsy = (bsy) obj;
        if (bsy != null) {
            return bsy.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        Boolean bool;
        Boolean bool2;
        bsy bsy = (bsy) obj;
        int i2 = i + 0;
        Boolean bool3 = null;
        bsy.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        bsy.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        bsy.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        bsy.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        bsy.e = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        boolean z = true;
        if (cursor.isNull(i7)) {
            bool = null;
        } else {
            bool = Boolean.valueOf(cursor.getShort(i7) != 0);
        }
        bsy.f = bool;
        int i8 = i + 6;
        bsy.g = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        bsy.h = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        bsy.i = cursor.isNull(i10) ? null : Long.valueOf(cursor.getLong(i10));
        int i11 = i + 9;
        if (cursor.isNull(i11)) {
            bool2 = null;
        } else {
            bool2 = Boolean.valueOf(cursor.getShort(i11) != 0);
        }
        bsy.j = bool2;
        int i12 = i + 10;
        bsy.k = cursor.isNull(i12) ? null : Long.valueOf(cursor.getLong(i12));
        int i13 = i + 11;
        bsy.l = cursor.isNull(i13) ? null : Long.valueOf(cursor.getLong(i13));
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            if (cursor.getShort(i14) == 0) {
                z = false;
            }
            bool3 = Boolean.valueOf(z);
        }
        bsy.m = bool3;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((bsy) obj).a;
    }

    public CarOwnerInfoDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"CAR_OWNER_INFO\" (\"UID\" TEXT PRIMARY KEY NOT NULL ,\"OWNER_NAME\" TEXT,\"PLATE_NUMBERS\" TEXT,\"CAR_BRAND\" TEXT,\"CAR_TYPE\" TEXT,\"CAR_LICENSE_ANNUAL_INSPECTION\" INTEGER,\"VIN\" TEXT,\"ENGINE_NUMBERS\" TEXT,\"CAR_LICENSE_VALIDITY\" INTEGER,\"OWNER_LICENSE_ANNUAL_INSPECTION\" INTEGER,\"WHEN_GET_OWNER_LICENSE\" INTEGER,\"OWNER_LICENSE_VALID_DATE\" INTEGER,\"CAR_OFFENCE_REMINDER\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"CAR_OWNER_INFO\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        bsy bsy = new bsy();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            bsy.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            bsy.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            bsy.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            bsy.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            bsy.e = cursor.getString(i6);
        }
        int i7 = i + 5;
        boolean z = true;
        if (!cursor.isNull(i7)) {
            bsy.f = Boolean.valueOf(cursor.getShort(i7) != 0);
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            bsy.g = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            bsy.h = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            bsy.i = Long.valueOf(cursor.getLong(i10));
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            bsy.j = Boolean.valueOf(cursor.getShort(i11) != 0);
        }
        int i12 = i + 10;
        if (!cursor.isNull(i12)) {
            bsy.k = Long.valueOf(cursor.getLong(i12));
        }
        int i13 = i + 11;
        if (!cursor.isNull(i13)) {
            bsy.l = Long.valueOf(cursor.getLong(i13));
        }
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            if (cursor.getShort(i14) == 0) {
                z = false;
            }
            bsy.m = Boolean.valueOf(z);
        }
        return bsy;
    }
}
