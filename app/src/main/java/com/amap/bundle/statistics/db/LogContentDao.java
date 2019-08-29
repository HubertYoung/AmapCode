package com.amap.bundle.statistics.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class LogContentDao extends AbstractDao<afg, Long> {
    public static final String TABLENAME = "logcontent";

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

        static {
            Property property = new Property(0, Long.class, "id", true, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, String.class, "pageid", false, "PAGEID");
            b = property2;
            Property property3 = new Property(2, String.class, "buttonid", false, "BUTTONID");
            c = property3;
            Property property4 = new Property(3, Long.class, "stepid", false, "STEPID");
            d = property4;
            Property property5 = new Property(4, Long.class, "time", false, SecureSignatureDefine.SG_KEY_SIGN_TIME);
            e = property5;
            Property property6 = new Property(5, Long.class, "sessionid", false, "SESSIONID");
            f = property6;
            Property property7 = new Property(6, Integer.class, DictionaryKeys.CTRLXY_X, false, "X");
            g = property7;
            Property property8 = new Property(7, Integer.class, DictionaryKeys.CTRLXY_Y, false, "Y");
            h = property8;
            Property property9 = new Property(8, String.class, "param", false, "PARAM");
            i = property9;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        afg afg = (afg) obj;
        sQLiteStatement.clearBindings();
        Long l = afg.a;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String str = afg.b;
        if (str != null) {
            sQLiteStatement.bindString(2, str);
        }
        String str2 = afg.c;
        if (str2 != null) {
            sQLiteStatement.bindString(3, str2);
        }
        Long l2 = afg.d;
        if (l2 != null) {
            sQLiteStatement.bindLong(4, l2.longValue());
        }
        Long l3 = afg.e;
        if (l3 != null) {
            sQLiteStatement.bindLong(5, l3.longValue());
        }
        Long l4 = afg.f;
        if (l4 != null) {
            sQLiteStatement.bindLong(6, l4.longValue());
        }
        Integer num = afg.g;
        if (num != null) {
            sQLiteStatement.bindLong(7, (long) num.intValue());
        }
        Integer num2 = afg.h;
        if (num2 != null) {
            sQLiteStatement.bindLong(8, (long) num2.intValue());
        }
        String str3 = afg.i;
        if (str3 != null) {
            sQLiteStatement.bindString(9, str3);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        afg afg = (afg) obj;
        if (afg != null) {
            return afg.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        afg afg = (afg) obj;
        int i2 = i + 0;
        String str = null;
        afg.a = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        afg.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        afg.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        afg.d = cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5));
        int i6 = i + 4;
        afg.e = cursor.isNull(i6) ? null : Long.valueOf(cursor.getLong(i6));
        int i7 = i + 5;
        afg.f = cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7));
        int i8 = i + 6;
        afg.g = cursor.isNull(i8) ? null : Integer.valueOf(cursor.getInt(i8));
        int i9 = i + 7;
        afg.h = cursor.isNull(i9) ? null : Integer.valueOf(cursor.getInt(i9));
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            str = cursor.getString(i10);
        }
        afg.i = str;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((afg) obj).a = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public LogContentDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"logcontent\" (\"ID\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"PAGEID\" TEXT,\"BUTTONID\" TEXT,\"STEPID\" INTEGER,\"TIME\" INTEGER,\"SESSIONID\" INTEGER,\"X\" INTEGER,\"Y\" INTEGER,\"PARAM\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"logcontent\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        afg afg = new afg();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            afg.a = Long.valueOf(cursor.getLong(i2));
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            afg.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            afg.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            afg.d = Long.valueOf(cursor.getLong(i5));
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            afg.e = Long.valueOf(cursor.getLong(i6));
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            afg.f = Long.valueOf(cursor.getLong(i7));
        }
        int i8 = i + 6;
        if (!cursor.isNull(i8)) {
            afg.g = Integer.valueOf(cursor.getInt(i8));
        }
        int i9 = i + 7;
        if (!cursor.isNull(i9)) {
            afg.h = Integer.valueOf(cursor.getInt(i9));
        }
        int i10 = i + 8;
        if (!cursor.isNull(i10)) {
            afg.i = cursor.getString(i10);
        }
        return afg;
    }
}
