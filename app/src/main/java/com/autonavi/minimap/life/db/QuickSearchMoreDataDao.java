package com.autonavi.minimap.life.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class QuickSearchMoreDataDao extends AbstractDao<doy, Void> {
    public static final String TABLENAME = "QUICK_SEARCH_MORE_DATA";

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
            Property property = new Property(0, String.class, "bucket", false, "BUCKET");
            a = property;
            Property property2 = new Property(1, String.class, "md5", false, "MD5");
            b = property2;
            Property property3 = new Property(2, String.class, "classify", false, "CLASSIFY");
            c = property3;
            Property property4 = new Property(3, String.class, "name", false, "NAME");
            d = property4;
            Property property5 = new Property(4, String.class, "highlight", false, "HIGHLIGHT");
            e = property5;
            Property property6 = new Property(5, String.class, "querytype", false, "QUERYTYPE");
            f = property6;
            Property property7 = new Property(6, String.class, "querykeyword", false, "QUERYKEYWORD");
            g = property7;
            Property property8 = new Property(7, String.class, "url", false, MonitorItemConstants.KEY_URL);
            h = property8;
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
        doy doy = (doy) obj;
        sQLiteStatement.clearBindings();
        String str = doy.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = doy.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = doy.c;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = doy.d;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
        String str5 = doy.e;
        if (str5 != null) {
            sQLiteStatement.bindString(5, str5);
        }
        String str6 = doy.f;
        if (str6 != null) {
            sQLiteStatement.bindString(6, str6);
        }
        String str7 = doy.g;
        if (str7 != null) {
            sQLiteStatement.bindString(7, str7);
        }
        String str8 = doy.h;
        if (str8 != null) {
            sQLiteStatement.bindString(8, str8);
        }
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        doy doy = (doy) obj;
        int i2 = i + 0;
        String str = null;
        doy.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        doy.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        doy.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        doy.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        doy.e = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        doy.f = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 6;
        doy.g = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            str = cursor.getString(i9);
        }
        doy.h = str;
    }

    public QuickSearchMoreDataDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"QUICK_SEARCH_MORE_DATA\" (\"BUCKET\" TEXT,\"MD5\" TEXT,\"CLASSIFY\" TEXT,\"NAME\" TEXT,\"HIGHLIGHT\" TEXT,\"QUERYTYPE\" TEXT,\"QUERYKEYWORD\" TEXT,\"URL\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"QUICK_SEARCH_MORE_DATA\"");
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        doy doy = new doy();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            doy.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            doy.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            doy.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            doy.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            doy.e = cursor.getString(i6);
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            doy.f = cursor.getString(i7);
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            doy.g = cursor.getString(i8);
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            doy.h = cursor.getString(i9);
        }
        return doy;
    }
}
