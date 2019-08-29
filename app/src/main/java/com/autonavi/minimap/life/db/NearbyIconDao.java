package com.autonavi.minimap.life.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class NearbyIconDao extends AbstractDao<dox, Void> {
    public static final String TABLENAME = "NEARBY_ICON";

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
            Property property = new Property(0, String.class, "id", false, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, String.class, "modetype", false, "MODETYPE");
            b = property2;
            Property property3 = new Property(2, String.class, "url", false, MonitorItemConstants.KEY_URL);
            c = property3;
            Property property4 = new Property(3, String.class, "name", false, "NAME");
            d = property4;
            Property property5 = new Property(4, String.class, "announce", false, "ANNOUNCE");
            e = property5;
            Property property6 = new Property(5, String.class, "websiteName", false, "WEBSITE_NAME");
            f = property6;
            Property property7 = new Property(6, String.class, "displayName", false, "DISPLAY_NAME");
            g = property7;
            Property property8 = new Property(7, String.class, "searchName", false, "SEARCH_NAME");
            h = property8;
            Property property9 = new Property(8, String.class, "urlType", false, "URL_TYPE");
            i = property9;
            Property property10 = new Property(9, String.class, "isMarking", false, "IS_MARKING");
            j = property10;
            Property property11 = new Property(10, String.class, "isNew", false, "IS_NEW");
            k = property11;
            Property property12 = new Property(11, String.class, "iconUrl", false, "ICON_URL");
            l = property12;
            Property property13 = new Property(12, String.class, "actionType", false, "ACTION_TYPE");
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
        dox dox = (dox) obj;
        sQLiteStatement.clearBindings();
        String str = dox.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = dox.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = dox.c;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = dox.d;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
        String str5 = dox.e;
        if (str5 != null) {
            sQLiteStatement.bindString(5, str5);
        }
        String str6 = dox.f;
        if (str6 != null) {
            sQLiteStatement.bindString(6, str6);
        }
        String str7 = dox.g;
        if (str7 != null) {
            sQLiteStatement.bindString(7, str7);
        }
        String str8 = dox.h;
        if (str8 != null) {
            sQLiteStatement.bindString(8, str8);
        }
        String str9 = dox.i;
        if (str9 != null) {
            sQLiteStatement.bindString(9, str9);
        }
        String str10 = dox.j;
        if (str10 != null) {
            sQLiteStatement.bindString(10, str10);
        }
        String str11 = dox.k;
        if (str11 != null) {
            sQLiteStatement.bindString(11, str11);
        }
        String str12 = dox.l;
        if (str12 != null) {
            sQLiteStatement.bindString(12, str12);
        }
        String str13 = dox.m;
        if (str13 != null) {
            sQLiteStatement.bindString(13, str13);
        }
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        dox dox = (dox) obj;
        int i2 = i + 0;
        String str = null;
        dox.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        dox.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        dox.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        dox.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        dox.e = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        dox.f = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 6;
        dox.g = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        dox.h = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 8;
        dox.i = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 9;
        dox.j = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 10;
        dox.k = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = i + 11;
        dox.l = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            str = cursor.getString(i14);
        }
        dox.m = str;
    }

    public NearbyIconDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"NEARBY_ICON\" (\"ID\" TEXT,\"MODETYPE\" TEXT,\"URL\" TEXT,\"NAME\" TEXT,\"ANNOUNCE\" TEXT,\"WEBSITE_NAME\" TEXT,\"DISPLAY_NAME\" TEXT,\"SEARCH_NAME\" TEXT,\"URL_TYPE\" TEXT,\"IS_MARKING\" TEXT,\"IS_NEW\" TEXT,\"ICON_URL\" TEXT,\"ACTION_TYPE\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"NEARBY_ICON\"");
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        dox dox = new dox();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            dox.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            dox.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            dox.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            dox.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            dox.e = cursor.getString(i6);
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            dox.f = cursor.getString(i7);
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            dox.g = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            dox.h = cursor.getString(i9);
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            dox.i = cursor.getString(i10);
        }
        int i11 = i + 9;
        if (!cursor.isNull(i11)) {
            dox.j = cursor.getString(i11);
        }
        int i12 = i + 10;
        if (!cursor.isNull(i12)) {
            dox.k = cursor.getString(i12);
        }
        int i13 = i + 11;
        if (!cursor.isNull(i13)) {
            dox.l = cursor.getString(i13);
        }
        int i14 = i + 12;
        if (!cursor.isNull(i14)) {
            dox.m = cursor.getString(i14);
        }
        return dox;
    }
}
