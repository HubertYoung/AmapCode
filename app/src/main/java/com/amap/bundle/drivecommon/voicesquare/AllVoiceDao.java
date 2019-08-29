package com.amap.bundle.drivecommon.voicesquare;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class AllVoiceDao extends AbstractDao<tw, Long> {
    public static final String TABLENAME = "allvoice";

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
        public static final Property n;
        public static final Property o;

        static {
            Property property = new Property(0, Long.class, "id", true, "_id");
            a = property;
            Property property2 = new Property(1, String.class, "name", false, "name");
            b = property2;
            Property property3 = new Property(2, String.class, "url", false, "url");
            c = property3;
            Property property4 = new Property(3, String.class, "version", false, "version");
            d = property4;
            Property property5 = new Property(4, String.class, "subname", false, "subname");
            e = property5;
            Property property6 = new Property(5, Long.TYPE, "dataSize", false, "dataSize");
            f = property6;
            Property property7 = new Property(6, Integer.TYPE, "recommendFlag", false, "recommendFlag");
            g = property7;
            Property property8 = new Property(7, String.class, "md5", false, "md5");
            h = property8;
            Property property9 = new Property(8, String.class, "subImage", false, "subImage");
            i = property9;
            Property property10 = new Property(9, String.class, "image", false, "image");
            j = property10;
            Property property11 = new Property(10, String.class, "name2", false, "name2");
            k = property11;
            Property property12 = new Property(11, String.class, "tryUrl", false, "tryUrl");
            l = property12;
            Property property13 = new Property(12, String.class, "desc", false, "desc");
            m = property13;
            Property property14 = new Property(13, Integer.TYPE, "type", false, "type");
            n = property14;
            Property property15 = new Property(14, String.class, "srcCode", false, "srcCode");
            o = property15;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        tw twVar = (tw) obj;
        sQLiteStatement.clearBindings();
        Long l = twVar.a;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String str = twVar.c;
        if (str != null) {
            sQLiteStatement.bindString(2, str);
        }
        String str2 = twVar.d;
        if (str2 != null) {
            sQLiteStatement.bindString(3, str2);
        }
        String str3 = twVar.e;
        if (str3 != null) {
            sQLiteStatement.bindString(4, str3);
        }
        String str4 = twVar.f;
        if (str4 != null) {
            sQLiteStatement.bindString(5, str4);
        }
        sQLiteStatement.bindLong(6, twVar.g);
        sQLiteStatement.bindLong(7, (long) twVar.h);
        String str5 = twVar.i;
        if (str5 != null) {
            sQLiteStatement.bindString(8, str5);
        }
        String str6 = twVar.j;
        if (str6 != null) {
            sQLiteStatement.bindString(9, str6);
        }
        String str7 = twVar.k;
        if (str7 != null) {
            sQLiteStatement.bindString(10, str7);
        }
        String str8 = twVar.l;
        if (str8 != null) {
            sQLiteStatement.bindString(11, str8);
        }
        String str9 = twVar.m;
        if (str9 != null) {
            sQLiteStatement.bindString(12, str9);
        }
        String str10 = twVar.n;
        if (str10 != null) {
            sQLiteStatement.bindString(13, str10);
        }
        sQLiteStatement.bindLong(14, (long) twVar.o);
        String str11 = twVar.p;
        if (str11 != null) {
            sQLiteStatement.bindString(15, str11);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        tw twVar = (tw) obj;
        if (twVar != null) {
            return twVar.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        tw twVar = (tw) obj;
        int i2 = i + 0;
        String str = null;
        twVar.a = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        twVar.c = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 2;
        twVar.d = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 3;
        twVar.e = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 4;
        twVar.f = cursor.isNull(i6) ? null : cursor.getString(i6);
        twVar.g = cursor.getLong(i + 5);
        twVar.h = cursor.getInt(i + 6);
        int i7 = i + 7;
        twVar.i = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 8;
        twVar.j = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 9;
        twVar.k = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 10;
        twVar.l = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 11;
        twVar.m = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = i + 12;
        twVar.n = cursor.isNull(i12) ? null : cursor.getString(i12);
        twVar.o = cursor.getInt(i + 13);
        int i13 = i + 14;
        if (!cursor.isNull(i13)) {
            str = cursor.getString(i13);
        }
        twVar.p = str;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((tw) obj).a = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public AllVoiceDao(DaoConfig daoConfig, tz tzVar) {
        super(daoConfig, tzVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("'allvoice' ('_id' INTEGER PRIMARY KEY AUTOINCREMENT ,'name' TEXT,'url' TEXT,'version' TEXT,'subname' TEXT,'dataSize' INTEGER NOT NULL DEFAULT 0 ,'recommendFlag' INTEGER NOT NULL DEFAULT 0 ,'md5' TEXT,'subImage' TEXT,'image' TEXT,'name2' TEXT,'tryUrl' TEXT,'desc' TEXT,'type' INTEGER NOT NULL DEFAULT 8,'srcCode' TEXT NOT NULL DEFAULT 0);");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public /* synthetic */ Object readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    public /* synthetic */ Object readEntity(Cursor cursor, int i) {
        Cursor cursor2 = cursor;
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 2;
        int i5 = i + 3;
        int i6 = i + 4;
        int i7 = i + 7;
        int i8 = i + 8;
        int i9 = i + 9;
        int i10 = i + 10;
        int i11 = i + 11;
        int i12 = i + 12;
        int i13 = i + 14;
        tw twVar = new tw(cursor2.isNull(i2) ? null : Long.valueOf(cursor2.getLong(i2)), cursor2.isNull(i3) ? null : cursor2.getString(i3), cursor2.isNull(i4) ? null : cursor2.getString(i4), cursor2.isNull(i5) ? null : cursor2.getString(i5), cursor2.isNull(i6) ? null : cursor2.getString(i6), cursor2.getLong(i + 5), cursor2.getInt(i + 6), cursor2.isNull(i7) ? null : cursor2.getString(i7), cursor2.isNull(i8) ? null : cursor2.getString(i8), cursor2.isNull(i9) ? null : cursor2.getString(i9), cursor2.isNull(i10) ? null : cursor2.getString(i10), cursor2.isNull(i11) ? null : cursor2.getString(i11), cursor2.isNull(i12) ? null : cursor2.getString(i12), cursor2.getInt(i + 13), cursor2.isNull(i13) ? null : cursor2.getString(i13));
        return twVar;
    }
}
