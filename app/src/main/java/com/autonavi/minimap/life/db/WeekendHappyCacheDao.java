package com.autonavi.minimap.life.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class WeekendHappyCacheDao extends AbstractDao<doz, String> {
    public static final String TABLENAME = "WeekendHappyCache";

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

        static {
            Property property = new Property(0, String.class, "key", true, "KEY");
            a = property;
            Property property2 = new Property(1, String.class, "AdCode", false, "AD_CODE");
            b = property2;
            Property property3 = new Property(2, String.class, "DistrictId", false, "DISTRICT_ID");
            c = property3;
            Property property4 = new Property(3, String.class, "TagId", false, "TAG_ID");
            d = property4;
            Property property5 = new Property(4, String.class, "Id", false, AutoJsonUtils.JSON_ID);
            e = property5;
            Property property6 = new Property(5, String.class, "Title", false, "TITLE");
            f = property6;
            Property property7 = new Property(6, String.class, "CoverImage", false, "COVER_IMAGE");
            g = property7;
            Property property8 = new Property(7, String.class, "DetailUrl", false, "DETAIL_URL");
            h = property8;
            Property property9 = new Property(8, String.class, "LikeTimes", false, "LIKE_TIMES");
            i = property9;
            Property property10 = new Property(9, String.class, "Source", false, "SOURCE");
            j = property10;
            Property property11 = new Property(10, String.class, "IsNew", false, "IS_NEW");
            k = property11;
            Property property12 = new Property(11, String.class, "IsHot", false, "IS_HOT");
            l = property12;
            Property property13 = new Property(12, String.class, "Tags", false, "TAGS");
            m = property13;
            Property property14 = new Property(13, String.class, "PoiId", false, "POI_ID");
            n = property14;
            Property property15 = new Property(14, String.class, "Distance", false, "DISTANCE");
            o = property15;
            Property property16 = new Property(15, String.class, "Address", false, "ADDRESS");
            p = property16;
            Property property17 = new Property(16, String.class, "Index", false, "INDEX");
            q = property17;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        doz doz = (doz) obj;
        sQLiteStatement.clearBindings();
        String str = doz.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = doz.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = doz.c;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = doz.d;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
        String str5 = doz.e;
        if (str5 != null) {
            sQLiteStatement.bindString(5, str5);
        }
        String str6 = doz.f;
        if (str6 != null) {
            sQLiteStatement.bindString(6, str6);
        }
        String str7 = doz.g;
        if (str7 != null) {
            sQLiteStatement.bindString(7, str7);
        }
        String str8 = doz.h;
        if (str8 != null) {
            sQLiteStatement.bindString(8, str8);
        }
        String str9 = doz.i;
        if (str9 != null) {
            sQLiteStatement.bindString(9, str9);
        }
        String str10 = doz.j;
        if (str10 != null) {
            sQLiteStatement.bindString(10, str10);
        }
        String str11 = doz.k;
        if (str11 != null) {
            sQLiteStatement.bindString(11, str11);
        }
        String str12 = doz.l;
        if (str12 != null) {
            sQLiteStatement.bindString(12, str12);
        }
        String str13 = doz.m;
        if (str13 != null) {
            sQLiteStatement.bindString(13, str13);
        }
        String str14 = doz.n;
        if (str14 != null) {
            sQLiteStatement.bindString(14, str14);
        }
        String str15 = doz.o;
        if (str15 != null) {
            sQLiteStatement.bindString(15, str15);
        }
        String str16 = doz.p;
        if (str16 != null) {
            sQLiteStatement.bindString(16, str16);
        }
        String str17 = doz.q;
        if (str17 != null) {
            sQLiteStatement.bindString(17, str17);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        doz doz = (doz) obj;
        if (doz != null) {
            return doz.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        doz doz = (doz) obj;
        int i2 = i + 0;
        String str = null;
        doz.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        doz.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        doz.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        doz.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        doz.e = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        doz.f = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 6;
        doz.g = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        doz.h = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        doz.i = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        doz.j = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 10;
        doz.k = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = i + 11;
        doz.l = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = i + 12;
        doz.m = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = i + 13;
        doz.n = cursor.isNull(i15) ? null : cursor.getString(i15);
        int i16 = i + 14;
        doz.o = cursor.isNull(i16) ? null : cursor.getString(i16);
        int i17 = i + 15;
        doz.p = cursor.isNull(i17) ? null : cursor.getString(i17);
        int i18 = i + 16;
        if (!cursor.isNull(i18)) {
            str = cursor.getString(i18);
        }
        doz.q = str;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((doz) obj).a;
    }

    public WeekendHappyCacheDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"WeekendHappyCache\" (\"KEY\" TEXT PRIMARY KEY NOT NULL ,\"AD_CODE\" TEXT,\"DISTRICT_ID\" TEXT,\"TAG_ID\" TEXT,\"ID\" TEXT,\"TITLE\" TEXT,\"COVER_IMAGE\" TEXT,\"DETAIL_URL\" TEXT,\"LIKE_TIMES\" TEXT,\"SOURCE\" TEXT,\"IS_NEW\" TEXT,\"IS_HOT\" TEXT,\"TAGS\" TEXT,\"POI_ID\" TEXT,\"DISTANCE\" TEXT,\"ADDRESS\" TEXT,\"INDEX\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"WeekendHappyCache\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        doz doz = new doz();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            doz.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            doz.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            doz.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            doz.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            doz.e = cursor.getString(i6);
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            doz.f = cursor.getString(i7);
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            doz.g = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            doz.h = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            doz.i = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            doz.j = cursor.getString(i11);
        }
        int i12 = i + 10;
        if (!cursor.isNull(i12)) {
            doz.k = cursor.getString(i12);
        }
        int i13 = i + 11;
        if (!cursor.isNull(i13)) {
            doz.l = cursor.getString(i13);
        }
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            doz.m = cursor.getString(i14);
        }
        int i15 = i + 13;
        if (!cursor.isNull(i15)) {
            doz.n = cursor.getString(i15);
        }
        int i16 = i + 14;
        if (!cursor.isNull(i16)) {
            doz.o = cursor.getString(i16);
        }
        int i17 = i + 15;
        if (!cursor.isNull(i17)) {
            doz.p = cursor.getString(i17);
        }
        int i18 = i + 16;
        if (!cursor.isNull(i18)) {
            doz.q = cursor.getString(i18);
        }
        return doz;
    }
}
