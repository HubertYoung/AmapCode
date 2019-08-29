package com.autonavi.minimap.life.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.sdk.location.LocationInstrument;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.Date;

public class HotelBrowseHistoryRecordDao extends AbstractDao<dow, Long> {
    public static final String TABLENAME = "hotelbrowsehistory";

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
            Property property = new Property(0, Long.class, "id", true, "_id");
            a = property;
            Property property2 = new Property(1, String.class, LocationInstrument.LOCATION_EXTRAS_KEY_POIID, false, "POIID");
            b = property2;
            Property property3 = new Property(2, String.class, "poiname", false, "POINAME");
            c = property3;
            Property property4 = new Property(3, String.class, "iconurl", false, "ICONURL");
            d = property4;
            Property property5 = new Property(4, Integer.class, "distance", false, "DISTANCE");
            e = property5;
            Property property6 = new Property(5, String.class, "hotelIsSupper", false, "HOTEL_IS_SUPPER");
            f = property6;
            Property property7 = new Property(6, String.class, "hours", false, "HOURS");
            g = property7;
            Property property8 = new Property(7, String.class, "hotelIsOverbooked", false, "HOTEL_IS_OVERBOOKED");
            h = property8;
            Property property9 = new Property(8, String.class, "star", false, "STAR");
            i = property9;
            Property property10 = new Property(9, String.class, "busiDistrict", false, "BUSI_DISTRICT");
            j = property10;
            Property property11 = new Property(10, String.class, "lowestPrice", false, "LOWEST_PRICE");
            k = property11;
            Property property12 = new Property(11, String.class, "originalPrice", false, "ORIGINAL_PRICE");
            l = property12;
            Property property13 = new Property(12, String.class, "rating", false, "RATING");
            m = property13;
            Property property14 = new Property(13, String.class, "groupFlag", false, "GROUP_FLAG");
            n = property14;
            Property property15 = new Property(14, String.class, "maxUpperLimit", false, "MAX_UPPER_LIMIT");
            o = property15;
            Property property16 = new Property(15, String.class, "wifi", false, "WIFI");
            p = property16;
            Property property17 = new Property(16, String.class, "parkType", false, "PARK_TYPE");
            q = property17;
            Property property18 = new Property(17, Double.class, "lat", false, SecureSignatureDefine.SG_KEY_SIGN_LAT);
            r = property18;
            Property property19 = new Property(18, Double.class, LocationParams.PARA_FLP_AUTONAVI_LON, false, "LON");
            s = property19;
            Property property20 = new Property(19, Date.class, "date", false, "DATE");
            t = property20;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        dow dow = (dow) obj;
        sQLiteStatement.clearBindings();
        Long l = dow.a;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String str = dow.b;
        if (str != null) {
            sQLiteStatement.bindString(2, str);
        }
        String str2 = dow.c;
        if (str2 != null) {
            sQLiteStatement.bindString(3, str2);
        }
        String str3 = dow.d;
        if (str3 != null) {
            sQLiteStatement.bindString(4, str3);
        }
        Integer num = dow.e;
        if (num != null) {
            sQLiteStatement.bindLong(5, (long) num.intValue());
        }
        String str4 = dow.f;
        if (str4 != null) {
            sQLiteStatement.bindString(6, str4);
        }
        String str5 = dow.g;
        if (str5 != null) {
            sQLiteStatement.bindString(7, str5);
        }
        String str6 = dow.h;
        if (str6 != null) {
            sQLiteStatement.bindString(8, str6);
        }
        String str7 = dow.i;
        if (str7 != null) {
            sQLiteStatement.bindString(9, str7);
        }
        String str8 = dow.j;
        if (str8 != null) {
            sQLiteStatement.bindString(10, str8);
        }
        String str9 = dow.k;
        if (str9 != null) {
            sQLiteStatement.bindString(11, str9);
        }
        String str10 = dow.l;
        if (str10 != null) {
            sQLiteStatement.bindString(12, str10);
        }
        String str11 = dow.m;
        if (str11 != null) {
            sQLiteStatement.bindString(13, str11);
        }
        String str12 = dow.n;
        if (str12 != null) {
            sQLiteStatement.bindString(14, str12);
        }
        String str13 = dow.o;
        if (str13 != null) {
            sQLiteStatement.bindString(15, str13);
        }
        String str14 = dow.p;
        if (str14 != null) {
            sQLiteStatement.bindString(16, str14);
        }
        String str15 = dow.q;
        if (str15 != null) {
            sQLiteStatement.bindString(17, str15);
        }
        Double d = dow.r;
        if (d != null) {
            sQLiteStatement.bindDouble(18, d.doubleValue());
        }
        Double d2 = dow.s;
        if (d2 != null) {
            sQLiteStatement.bindDouble(19, d2.doubleValue());
        }
        Date date = dow.t;
        if (date != null) {
            sQLiteStatement.bindLong(20, date.getTime());
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        dow dow = (dow) obj;
        if (dow != null) {
            return dow.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        dow dow = (dow) obj;
        int i2 = i + 0;
        Date date = null;
        dow.a = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        dow.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        dow.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        dow.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        dow.e = cursor.isNull(i6) ? null : Integer.valueOf(cursor.getInt(i6));
        int i7 = i + 5;
        dow.f = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 6;
        dow.g = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        dow.h = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        dow.i = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        dow.j = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 10;
        dow.k = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = i + 11;
        dow.l = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = i + 12;
        dow.m = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = i + 13;
        dow.n = cursor.isNull(i15) ? null : cursor.getString(i15);
        int i16 = i + 14;
        dow.o = cursor.isNull(i16) ? null : cursor.getString(i16);
        int i17 = i + 15;
        dow.p = cursor.isNull(i17) ? null : cursor.getString(i17);
        int i18 = i + 16;
        dow.q = cursor.isNull(i18) ? null : cursor.getString(i18);
        int i19 = i + 17;
        dow.r = cursor.isNull(i19) ? null : Double.valueOf(cursor.getDouble(i19));
        int i20 = i + 18;
        dow.s = cursor.isNull(i20) ? null : Double.valueOf(cursor.getDouble(i20));
        int i21 = i + 19;
        if (!cursor.isNull(i21)) {
            date = new Date(cursor.getLong(i21));
        }
        dow.t = date;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((dow) obj).a = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public HotelBrowseHistoryRecordDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"hotelbrowsehistory\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"POIID\" TEXT,\"POINAME\" TEXT,\"ICONURL\" TEXT,\"DISTANCE\" INTEGER,\"HOTEL_IS_SUPPER\" TEXT,\"HOURS\" TEXT,\"HOTEL_IS_OVERBOOKED\" TEXT,\"STAR\" TEXT,\"BUSI_DISTRICT\" TEXT,\"LOWEST_PRICE\" TEXT,\"ORIGINAL_PRICE\" TEXT,\"RATING\" TEXT,\"GROUP_FLAG\" TEXT,\"MAX_UPPER_LIMIT\" TEXT,\"WIFI\" TEXT,\"PARK_TYPE\" TEXT,\"LAT\" REAL,\"LON\" REAL,\"DATE\" INTEGER);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"hotelbrowsehistory\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        dow dow = new dow();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            dow.a = Long.valueOf(cursor.getLong(i2));
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            dow.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            dow.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            dow.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            dow.e = Integer.valueOf(cursor.getInt(i6));
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            dow.f = cursor.getString(i7);
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            dow.g = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            dow.h = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            dow.i = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            dow.j = cursor.getString(i11);
        }
        int i12 = i + 10;
        if (!cursor.isNull(i12)) {
            dow.k = cursor.getString(i12);
        }
        int i13 = i + 11;
        if (!cursor.isNull(i13)) {
            dow.l = cursor.getString(i13);
        }
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            dow.m = cursor.getString(i14);
        }
        int i15 = i + 13;
        if (!cursor.isNull(i15)) {
            dow.n = cursor.getString(i15);
        }
        int i16 = i + 14;
        if (!cursor.isNull(i16)) {
            dow.o = cursor.getString(i16);
        }
        int i17 = i + 15;
        if (!cursor.isNull(i17)) {
            dow.p = cursor.getString(i17);
        }
        int i18 = i + 16;
        if (!cursor.isNull(i18)) {
            dow.q = cursor.getString(i18);
        }
        int i19 = i + 17;
        if (!cursor.isNull(i19)) {
            dow.r = Double.valueOf(cursor.getDouble(i19));
        }
        int i20 = i + 18;
        if (!cursor.isNull(i20)) {
            dow.s = Double.valueOf(cursor.getDouble(i20));
        }
        int i21 = i + 19;
        if (!cursor.isNull(i21)) {
            dow.t = new Date(cursor.getLong(i21));
        }
        return dow;
    }
}
