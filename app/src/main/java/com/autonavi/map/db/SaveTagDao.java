package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.security.bio.api.BioDetector;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import org.eclipse.mat.hprof.IHprofParserHandler;

public class SaveTagDao extends AbstractDao<btk, String> {
    public static final String TABLENAME = "SAVE_TAG";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;

        static {
            Property property = new Property(0, String.class, "key", true, "KEY");
            a = property;
            Property property2 = new Property(1, String.class, "userId", false, BioDetector.EXT_KEY_USER_ID_BUNDLE);
            b = property2;
            Property property3 = new Property(2, String.class, "version", false, IHprofParserHandler.VERSION);
            c = property3;
            Property property4 = new Property(3, String.class, "tagName", false, "TAG_NAME");
            d = property4;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        btk btk = (btk) obj;
        sQLiteStatement.clearBindings();
        String str = btk.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = btk.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = btk.c;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = btk.d;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        btk btk = (btk) obj;
        if (btk != null) {
            return btk.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        btk btk = (btk) obj;
        int i2 = i + 0;
        String str = null;
        btk.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        btk.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        btk.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            str = cursor.getString(i5);
        }
        btk.d = str;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((btk) obj).a;
    }

    public SaveTagDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"SAVE_TAG\" (\"KEY\" TEXT PRIMARY KEY NOT NULL ,\"USER_ID\" TEXT,\"VERSION\" TEXT,\"TAG_NAME\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"SAVE_TAG\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        btk btk = new btk();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            btk.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            btk.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            btk.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            btk.d = cursor.getString(i5);
        }
        return btk;
    }
}
