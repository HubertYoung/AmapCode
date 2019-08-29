package com.autonavi.map.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.common.logging.util.avail.ExceptionData;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class VehiclesReminderMsgDao extends AbstractDao<btl, Long> {
    public static final String TABLENAME = "VEHICLES_REMINDER_MSG";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;

        static {
            Property property = new Property(0, Long.class, "id", true, "_id");
            a = property;
            Property property2 = new Property(1, Long.class, "vehicle_id", false, "VEHICLE_ID");
            b = property2;
            Property property3 = new Property(2, String.class, "msg", false, "MSG");
            c = property3;
            Property property4 = new Property(3, Integer.class, "type", false, ExceptionData.E_TYPE);
            d = property4;
            Property property5 = new Property(4, String.class, "schemaUrl", false, "SCHEMA_URL");
            e = property5;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        btl btl = (btl) obj;
        sQLiteStatement.clearBindings();
        Long l = btl.a;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        Long l2 = btl.b;
        if (l2 != null) {
            sQLiteStatement.bindLong(2, l2.longValue());
        }
        String str = btl.c;
        if (str != null) {
            sQLiteStatement.bindString(3, str);
        }
        Integer num = btl.d;
        if (num != null) {
            sQLiteStatement.bindLong(4, (long) num.intValue());
        }
        String str2 = btl.e;
        if (str2 != null) {
            sQLiteStatement.bindString(5, str2);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        btl btl = (btl) obj;
        if (btl != null) {
            return btl.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        btl btl = (btl) obj;
        int i2 = i + 0;
        String str = null;
        btl.a = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        btl.b = cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3));
        int i4 = i + 2;
        btl.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        btl.d = cursor.isNull(i5) ? null : Integer.valueOf(cursor.getInt(i5));
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            str = cursor.getString(i6);
        }
        btl.e = str;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((btl) obj).a = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public VehiclesReminderMsgDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"VEHICLES_REMINDER_MSG\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"VEHICLE_ID\" INTEGER,\"MSG\" TEXT,\"TYPE\" INTEGER,\"SCHEMA_URL\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"VEHICLES_REMINDER_MSG\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        btl btl = new btl();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            btl.a = Long.valueOf(cursor.getLong(i2));
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            btl.b = Long.valueOf(cursor.getLong(i3));
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            btl.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            btl.d = Integer.valueOf(cursor.getInt(i5));
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            btl.e = cursor.getString(i6);
        }
        return btl;
    }
}
