package com.amap.bundle.lotuspool.internal.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class CommandResultDao extends AbstractDao<CommandResult, Void> {
    public static final String TABLENAME = "COMMAND_RESULT";

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
            Property property3 = new Property(2, Long.TYPE, "dispatchTime", false, "TIMESTAMP");
            c = property3;
            Property property4 = new Property(3, Integer.TYPE, "dispatchType", false, "DISPATCH_TYPE");
            d = property4;
            Property property5 = new Property(4, Long.TYPE, "uuid", false, "UUID");
            e = property5;
            Property property6 = new Property(5, Integer.TYPE, "index", false, "INDEX");
            f = property6;
            Property property7 = new Property(6, String.class, "result", false, "RESULT");
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
        CommandResult commandResult = (CommandResult) obj;
        sQLiteStatement.clearBindings();
        sQLiteStatement.bindString(1, commandResult.a);
        sQLiteStatement.bindLong(2, commandResult.b);
        sQLiteStatement.bindLong(3, commandResult.d);
        sQLiteStatement.bindLong(4, (long) commandResult.e);
        sQLiteStatement.bindLong(5, commandResult.c);
        sQLiteStatement.bindLong(6, (long) commandResult.f);
        sQLiteStatement.bindString(7, commandResult.g);
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        CommandResult commandResult = (CommandResult) obj;
        commandResult.a = cursor.getString(i + 0);
        commandResult.b = cursor.getLong(i + 1);
        commandResult.d = cursor.getLong(i + 2);
        commandResult.e = cursor.getInt(i + 3);
        commandResult.c = cursor.getLong(i + 4);
        commandResult.f = cursor.getInt(i + 5);
        commandResult.g = cursor.getString(i + 6);
    }

    public CommandResultDao(DaoConfig daoConfig, xd xdVar) {
        super(daoConfig, xdVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("");
        sb.append("\"COMMAND_RESULT\" (\"DISPATCH_ID\" TEXT NOT NULL ,\"DISPATCH_INDEX\" INTEGER NOT NULL ,\"TIMESTAMP\" INTEGER NOT NULL ,\"DISPATCH_TYPE\" INTEGER NOT NULL ,\"UUID\" INTEGER NOT NULL ,\"INDEX\" INTEGER NOT NULL ,\"RESULT\" TEXT NOT NULL ,primary key(UUID,TIMESTAMP));");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"COMMAND_RESULT\"");
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        CommandResult commandResult = new CommandResult();
        commandResult.a = cursor.getString(i + 0);
        commandResult.b = cursor.getLong(i + 1);
        commandResult.d = cursor.getLong(i + 2);
        commandResult.e = cursor.getInt(i + 3);
        commandResult.c = cursor.getLong(i + 4);
        commandResult.f = cursor.getInt(i + 5);
        commandResult.g = cursor.getString(i + 6);
        return commandResult;
    }
}
