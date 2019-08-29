package com.amap.bundle.lotuspool.internal.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.taobao.agoo.control.data.BaseDO;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class CommandDao extends AbstractDao<Command, Void> {
    public static final String TABLENAME = "COMMAND";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;
        public static final Property f;
        public static final Property g;

        static {
            Property property = new Property(0, String.class, "dispatchId", false, "DISPATCH_ID");
            a = property;
            Property property2 = new Property(1, Long.TYPE, "dispatchIndex", false, "DISPATCH_INDEX");
            b = property2;
            Property property3 = new Property(2, Long.TYPE, "uuid", false, "UUID");
            c = property3;
            Property property4 = new Property(3, Long.TYPE, "dispatchTime", false, "TIMESTAMP");
            d = property4;
            Property property5 = new Property(4, Integer.TYPE, "dispatchType", false, "DISPATCH_TYPE");
            e = property5;
            Property property6 = new Property(5, Integer.TYPE, "index", false, "INDEX");
            f = property6;
            Property property7 = new Property(6, String.class, BaseDO.JSON_CMD, false, "CMD");
            g = property7;
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
        Command command = (Command) obj;
        sQLiteStatement.clearBindings();
        sQLiteStatement.bindString(1, command.a);
        sQLiteStatement.bindLong(2, command.b);
        sQLiteStatement.bindLong(3, command.c);
        sQLiteStatement.bindLong(4, command.d);
        sQLiteStatement.bindLong(5, (long) command.e);
        sQLiteStatement.bindLong(6, (long) command.f);
        String str = command.g;
        if (str != null) {
            sQLiteStatement.bindString(7, str);
        }
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        Command command = (Command) obj;
        command.a = cursor.getString(i + 0);
        command.b = cursor.getLong(i + 1);
        command.c = cursor.getLong(i + 2);
        command.d = cursor.getLong(i + 3);
        command.e = cursor.getInt(i + 4);
        command.f = cursor.getInt(i + 5);
        int i2 = i + 6;
        command.g = cursor.isNull(i2) ? null : cursor.getString(i2);
    }

    public CommandDao(DaoConfig daoConfig, xd xdVar) {
        super(daoConfig, xdVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("");
        sb.append("\"COMMAND\" (\"DISPATCH_ID\" TEXT NOT NULL ,\"DISPATCH_INDEX\" INTEGER NOT NULL ,\"UUID\" INTEGER NOT NULL UNIQUE ,\"TIMESTAMP\" INTEGER NOT NULL ,\"DISPATCH_TYPE\" INTEGER NOT NULL ,\"INDEX\" INTEGER NOT NULL ,\"CMD\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
        StringBuilder sb2 = new StringBuilder("CREATE INDEX ");
        sb2.append("");
        sb2.append("IDX_COMMAND_UUID ON COMMAND (\"UUID\");");
        sQLiteDatabase.execSQL(sb2.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"COMMAND\"");
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        Command command = new Command();
        command.a = cursor.getString(i + 0);
        command.b = cursor.getLong(i + 1);
        command.c = cursor.getLong(i + 2);
        command.d = cursor.getLong(i + 3);
        command.e = cursor.getInt(i + 4);
        command.f = cursor.getInt(i + 5);
        int i2 = i + 6;
        if (!cursor.isNull(i2)) {
            command.g = cursor.getString(i2);
        }
        return command;
    }
}
