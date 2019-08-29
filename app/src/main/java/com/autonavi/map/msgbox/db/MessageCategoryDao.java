package com.autonavi.map.msgbox.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class MessageCategoryDao extends AbstractDao<btb, String> {
    public static final String TABLENAME = "MESSAGE_CATEGORY";

    public static class Properties {
        public static final Property a;
        public static final Property b;
        public static final Property c;
        public static final Property d;
        public static final Property e;
        public static final Property f;

        static {
            Property property = new Property(0, String.class, "id", true, AutoJsonUtils.JSON_ID);
            a = property;
            Property property2 = new Property(1, String.class, "name", false, "NAME");
            b = property2;
            Property property3 = new Property(2, String.class, "pattern", false, "PATTERN");
            c = property3;
            Property property4 = new Property(3, String.class, H5Param.MENU_ICON, false, "ICON");
            d = property4;
            Property property5 = new Property(4, String.class, "top_image", false, "TOP_IMAGE");
            e = property5;
            Property property6 = new Property(5, String.class, "section_type", false, "SECTION_TYPE");
            f = property6;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        btb btb = (btb) obj;
        sQLiteStatement.clearBindings();
        String str = btb.a;
        if (str != null) {
            sQLiteStatement.bindString(1, str);
        }
        String str2 = btb.b;
        if (str2 != null) {
            sQLiteStatement.bindString(2, str2);
        }
        String str3 = btb.c;
        if (str3 != null) {
            sQLiteStatement.bindString(3, str3);
        }
        String str4 = btb.d;
        if (str4 != null) {
            sQLiteStatement.bindString(4, str4);
        }
        String str5 = btb.e;
        if (str5 != null) {
            sQLiteStatement.bindString(5, str5);
        }
        String str6 = btb.f;
        if (str6 != null) {
            sQLiteStatement.bindString(6, str6);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        btb btb = (btb) obj;
        if (btb != null) {
            return btb.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        btb btb = (btb) obj;
        int i2 = i + 0;
        String str = null;
        btb.a = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        btb.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        btb.c = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        btb.d = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        btb.e = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            str = cursor.getString(i7);
        }
        btb.f = str;
    }

    public /* bridge */ /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        return ((btb) obj).a;
    }

    public MessageCategoryDao(DaoConfig daoConfig, xt xtVar) {
        super(daoConfig, xtVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("\"MESSAGE_CATEGORY\" (\"ID\" TEXT PRIMARY KEY NOT NULL ,\"NAME\" TEXT,\"PATTERN\" TEXT,\"ICON\" TEXT,\"TOP_IMAGE\" TEXT,\"SECTION_TYPE\" TEXT);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public static void b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS \"MESSAGE_CATEGORY\"");
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return cursor.getString(i2);
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        btb btb = new btb();
        int i2 = i + 0;
        if (!cursor.isNull(i2)) {
            btb.a = cursor.getString(i2);
        }
        int i3 = i + 1;
        if (!cursor.isNull(i3)) {
            btb.b = cursor.getString(i3);
        }
        int i4 = i + 2;
        if (!cursor.isNull(i4)) {
            btb.c = cursor.getString(i4);
        }
        int i5 = i + 3;
        if (!cursor.isNull(i5)) {
            btb.d = cursor.getString(i5);
        }
        int i6 = i + 4;
        if (!cursor.isNull(i6)) {
            btb.e = cursor.getString(i6);
        }
        int i7 = i + 5;
        if (!cursor.isNull(i7)) {
            btb.f = cursor.getString(i7);
        }
        return btb;
    }
}
