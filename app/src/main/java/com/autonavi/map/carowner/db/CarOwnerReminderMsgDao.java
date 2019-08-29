package com.autonavi.map.carowner.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class CarOwnerReminderMsgDao extends AbstractDao<bsz, Long> {
    public static final String TABLENAME = "CAR_OWNER_REMINDER_MSG";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;

        static {
            Property property = new Property(0, Long.class, "id", true, "_id");
            a = property;
            Property property2 = new Property(1, String.class, Oauth2AccessToken.KEY_UID, false, "UID");
            b = property2;
            Property property3 = new Property(2, String.class, "msg", false, "MSG");
            c = property3;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        bsz bsz = (bsz) obj;
        sQLiteStatement.clearBindings();
        Long l = bsz.a;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String str = bsz.b;
        if (str != null) {
            sQLiteStatement.bindString(2, str);
        }
        String str2 = bsz.c;
        if (str2 != null) {
            sQLiteStatement.bindString(3, str2);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        bsz bsz = (bsz) obj;
        if (bsz != null) {
            return bsz.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        bsz bsz = (bsz) obj;
        int i2 = i + 0;
        String str = null;
        bsz.a = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        bsz.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            str = cursor.getString(i4);
        }
        bsz.c = str;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((bsz) obj).a = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public CarOwnerReminderMsgDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"CAR_OWNER_REMINDER_MSG\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"UID\" TEXT,\"MSG\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"CAR_OWNER_REMINDER_MSG\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        bsz bsz = new bsz();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            bsz.a = Long.valueOf(cursor.getLong(i2));
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            bsz.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            bsz.c = cursor.getString(i4);
        }
        return bsz;
    }
}
