package com.autonavi.minimap.life.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class WeekendHappyFavouriteDao extends AbstractDao<dpa, String> {
    public static final String TABLENAME = "WeekendHappyFavourite";

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
            Property property = new Property(0, String.class, "Id", true, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, Boolean.class, "IsFavourite", false, "IS_FAVOURITE");
            b = property2;
            Property property3 = new Property(2, String.class, "Title", false, "TITLE");
            c = property3;
            Property property4 = new Property(3, String.class, "CoverImage", false, "COVER_IMAGE");
            d = property4;
            Property property5 = new Property(4, String.class, "DetailUrl", false, "DETAIL_URL");
            e = property5;
            Property property6 = new Property(5, String.class, "LikeTimes", false, "LIKE_TIMES");
            f = property6;
            Property property7 = new Property(6, String.class, "Source", false, "SOURCE");
            g = property7;
            Property property8 = new Property(7, String.class, "IsNew", false, "IS_NEW");
            h = property8;
            Property property9 = new Property(8, String.class, "IsHot", false, "IS_HOT");
            i = property9;
            Property property10 = new Property(9, String.class, "Tags", false, "TAGS");
            j = property10;
            Property property11 = new Property(10, String.class, "PoiId", false, "POI_ID");
            k = property11;
            Property property12 = new Property(11, String.class, "Distance", false, "DISTANCE");
            l = property12;
            Property property13 = new Property(12, String.class, "Address", false, "ADDRESS");
            m = property13;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        dpa dpa = (dpa) obj;
        sQLiteStatement.clearBindings();
        String str = dpa.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        Boolean bool = dpa.b;
        if (bool != null) {
            sQLiteStatement.bindLong(2, bool.booleanValue() ? 1 : 0);
        }
        String str2 = dpa.c;
        if (str2 != null) {
            sQLiteStatement.bindString(3, str2);
        }
        String str3 = dpa.d;
        if (str3 != null) {
            sQLiteStatement.bindString(4, str3);
        }
        String str4 = dpa.e;
        if (str4 != null) {
            sQLiteStatement.bindString(5, str4);
        }
        String str5 = dpa.f;
        if (str5 != null) {
            sQLiteStatement.bindString(6, str5);
        }
        String str6 = dpa.g;
        if (str6 != null) {
            sQLiteStatement.bindString(7, str6);
        }
        String str7 = dpa.h;
        if (str7 != null) {
            sQLiteStatement.bindString(8, str7);
        }
        String str8 = dpa.i;
        if (str8 != null) {
            sQLiteStatement.bindString(9, str8);
        }
        String str9 = dpa.j;
        if (str9 != null) {
            sQLiteStatement.bindString(10, str9);
        }
        String str10 = dpa.k;
        if (str10 != null) {
            sQLiteStatement.bindString(11, str10);
        }
        String str11 = dpa.l;
        if (str11 != null) {
            sQLiteStatement.bindString(12, str11);
        }
        String str12 = dpa.m;
        if (str12 != null) {
            sQLiteStatement.bindString(13, str12);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        dpa dpa = (dpa) obj;
        if (dpa != null) {
            return dpa.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        Boolean bool;
        dpa dpa = (dpa) obj;
        int i2 = i + 0;
        String str = null;
        dpa.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        if (cursor.isNull(i3)) {
            bool = null;
        } else {
            bool = Boolean.valueOf(cursor.getShort(i3) != 0);
        }
        dpa.b = bool;
        int i4 = i + 2;
        dpa.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        dpa.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        dpa.e = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        dpa.f = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 6;
        dpa.g = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        dpa.h = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        dpa.i = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        dpa.j = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 10;
        dpa.k = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = i + 11;
        dpa.l = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            str = cursor.getString(i14);
        }
        dpa.m = str;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((dpa) obj).a;
    }

    public WeekendHappyFavouriteDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"WeekendHappyFavourite\" (\"ID\" TEXT PRIMARY KEY NOT NULL ,\"IS_FAVOURITE\" INTEGER,\"TITLE\" TEXT,\"COVER_IMAGE\" TEXT,\"DETAIL_URL\" TEXT,\"LIKE_TIMES\" TEXT,\"SOURCE\" TEXT,\"IS_NEW\" TEXT,\"IS_HOT\" TEXT,\"TAGS\" TEXT,\"POI_ID\" TEXT,\"DISTANCE\" TEXT,\"ADDRESS\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"WeekendHappyFavourite\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        dpa dpa = new dpa();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            dpa.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            dpa.b = Boolean.valueOf(cursor.getShort(i3) != 0);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            dpa.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            dpa.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            dpa.e = cursor.getString(i6);
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            dpa.f = cursor.getString(i7);
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            dpa.g = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            dpa.h = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            dpa.i = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            dpa.j = cursor.getString(i11);
        }
        int i12 = i + 10;
        if (!cursor.isNull(i12)) {
            dpa.k = cursor.getString(i12);
        }
        int i13 = i + 11;
        if (!cursor.isNull(i13)) {
            dpa.l = cursor.getString(i13);
        }
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            dpa.m = cursor.getString(i14);
        }
        return dpa;
    }
}
