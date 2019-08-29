package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.security.bio.api.BioDetector;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class SaveSyncActionDao extends AbstractDao<btj, String> {
    public static final String TABLENAME = "SAVE_SYNC_ACTION";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;

        static {
            Property property = new Property(0, String.class, "userId", false, BioDetector.EXT_KEY_USER_ID_BUNDLE);
            a = property;
            Property property2 = new Property(1, String.class, "key", true, "KEY");
            b = property2;
            Property property3 = new Property(2, Integer.TYPE, "actionType", false, "ACTION_TYPE");
            c = property3;
            Property property4 = new Property(3, Integer.TYPE, "itemType", false, "ITEM_TYPE");
            d = property4;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        btj btj = (btj) obj;
        sQLiteStatement.clearBindings();
        String str = btj.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        sQLiteStatement.bindString(2, btj.b);
        sQLiteStatement.bindLong(3, (long) btj.c);
        sQLiteStatement.bindLong(4, (long) btj.d);
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        btj btj = (btj) obj;
        if (btj != null) {
            return btj.b;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        btj btj = (btj) obj;
        int i2 = i + 0;
        btj.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        btj.b = cursor.getString(i + 1);
        btj.c = cursor.getInt(i + 2);
        btj.d = cursor.getInt(i + 3);
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((btj) obj).b;
    }

    public SaveSyncActionDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"SAVE_SYNC_ACTION\" (\"USER_ID\" TEXT,\"KEY\" TEXT PRIMARY KEY NOT NULL ,\"ACTION_TYPE\" INTEGER NOT NULL ,\"ITEM_TYPE\" INTEGER NOT NULL );");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"SAVE_SYNC_ACTION\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        return cursor.getString(i + 1);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        btj btj = new btj();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            btj.a = cursor.getString(i2);
        }
        btj.b = cursor.getString(i + 1);
        btj.c = cursor.getInt(i + 2);
        btj.d = cursor.getInt(i + 3);
        return btj;
    }
}
