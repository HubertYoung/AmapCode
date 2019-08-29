package com.amap.bundle.drivecommon.voicesquare;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class DownloadVoiceDao extends AbstractDao<ua, Long> {
    public static final String TABLENAME = "downloadvoice";

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
        public static final Property p;
        public static final Property q;
        public static final Property r;

        static {
            Property property = new Property(0, Long.class, "id", true, "_id");
            a = property;
            Property property2 = new Property(1, String.class, "subName", false, "subName");
            b = property2;
            Property property3 = new Property(2, Integer.TYPE, "status", false, "status");
            c = property3;
            Property property4 = new Property(3, Long.TYPE, "dataSize", false, "dataSize");
            d = property4;
            Property property5 = new Property(4, Long.TYPE, "dataDownloadedSize", false, "dataDownloadedSize");
            e = property5;
            Property property6 = new Property(5, String.class, "dataPath", false, "dataPath");
            f = property6;
            Property property7 = new Property(6, String.class, "tmpDataPath", false, "tmpDataPath");
            g = property7;
            Property property8 = new Property(7, String.class, "updateTime", false, "updateTime");
            h = property8;
            Property property9 = new Property(8, Integer.TYPE, "data1", false, "data1");
            i = property9;
            Property property10 = new Property(9, Integer.TYPE, "data2", false, "data2");
            j = property10;
            Property property11 = new Property(10, Integer.TYPE, "data3", false, "data3");
            k = property11;
            Property property12 = new Property(11, Integer.TYPE, "data4", false, "data4");
            l = property12;
            Property property13 = new Property(12, Integer.TYPE, "data5", false, "data5");
            m = property13;
            Property property14 = new Property(13, String.class, "text1", false, "text1");
            n = property14;
            Property property15 = new Property(14, String.class, "text2", false, "text2");
            o = property15;
            Property property16 = new Property(15, String.class, "text3", false, "text3");
            p = property16;
            Property property17 = new Property(16, String.class, "text4", false, "text4");
            q = property17;
            Property property18 = new Property(17, String.class, "text5", false, "text5");
            r = property18;
        }
    }

    public boolean isEntityUpdateable() {
        return true;
    }

    public /* synthetic */ void bindValues(SQLiteStatement sQLiteStatement, Object obj) {
        ua uaVar = (ua) obj;
        sQLiteStatement.clearBindings();
        Long l = uaVar.a;
        if (l != null) {
            sQLiteStatement.bindLong(1, l.longValue());
        }
        String str = uaVar.b;
        if (str != null) {
            sQLiteStatement.bindString(2, str);
        }
        sQLiteStatement.bindLong(3, (long) uaVar.c);
        sQLiteStatement.bindLong(4, uaVar.d);
        sQLiteStatement.bindLong(5, uaVar.e);
        String str2 = uaVar.f;
        if (str2 != null) {
            sQLiteStatement.bindString(6, str2);
        }
        String str3 = uaVar.g;
        if (str3 != null) {
            sQLiteStatement.bindString(7, str3);
        }
        String str4 = uaVar.h;
        if (str4 != null) {
            sQLiteStatement.bindString(8, str4);
        }
        sQLiteStatement.bindLong(9, (long) uaVar.i);
        sQLiteStatement.bindLong(10, (long) uaVar.j);
        sQLiteStatement.bindLong(11, (long) uaVar.k);
        sQLiteStatement.bindLong(12, (long) uaVar.l);
        sQLiteStatement.bindLong(13, (long) uaVar.m);
        String str5 = uaVar.n;
        if (str5 != null) {
            sQLiteStatement.bindString(14, str5);
        }
        String str6 = uaVar.o;
        if (str6 != null) {
            sQLiteStatement.bindString(15, str6);
        }
        String str7 = uaVar.p;
        if (str7 != null) {
            sQLiteStatement.bindString(16, str7);
        }
        String str8 = uaVar.q;
        if (str8 != null) {
            sQLiteStatement.bindString(17, str8);
        }
        String str9 = uaVar.r;
        if (str9 != null) {
            sQLiteStatement.bindString(18, str9);
        }
    }

    public /* bridge */ /* synthetic */ Object getKey(Object obj) {
        ua uaVar = (ua) obj;
        if (uaVar != null) {
            return uaVar.a;
        }
        return null;
    }

    public /* synthetic */ void readEntity(Cursor cursor, Object obj, int i) {
        ua uaVar = (ua) obj;
        int i2 = i + 0;
        String str = null;
        uaVar.a = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        uaVar.b = cursor.isNull(i3) ? null : cursor.getString(i3);
        uaVar.c = cursor.getInt(i + 2);
        uaVar.d = cursor.getLong(i + 3);
        uaVar.e = cursor.getLong(i + 4);
        int i4 = i + 5;
        uaVar.f = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 6;
        uaVar.g = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = i + 7;
        uaVar.h = cursor.isNull(i6) ? null : cursor.getString(i6);
        uaVar.i = cursor.getInt(i + 8);
        uaVar.j = cursor.getInt(i + 9);
        uaVar.k = cursor.getInt(i + 10);
        uaVar.l = cursor.getInt(i + 11);
        uaVar.m = cursor.getInt(i + 12);
        int i7 = i + 13;
        uaVar.n = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 14;
        uaVar.o = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 15;
        uaVar.p = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 16;
        uaVar.q = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = i + 17;
        if (!cursor.isNull(i11)) {
            str = cursor.getString(i11);
        }
        uaVar.r = str;
    }

    public /* synthetic */ Object updateKeyAfterInsert(Object obj, long j) {
        ((ua) obj).a = Long.valueOf(j);
        return Long.valueOf(j);
    }

    public DownloadVoiceDao(DaoConfig daoConfig, tz tzVar) {
        super(daoConfig, tzVar);
    }

    public static void a(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append("IF NOT EXISTS ");
        sb.append("'downloadvoice' ('_id' INTEGER PRIMARY KEY AUTOINCREMENT ,'subName' TEXT,'status' INTEGER NOT NULL DEFAULT 0 ,'dataSize' INTEGER NOT NULL DEFAULT 0 ,'dataDownloadedSize' INTEGER NOT NULL DEFAULT 0 ,'dataPath' TEXT,'tmpDataPath' TEXT,'updateTime' TEXT,'data1' INTEGER NOT NULL DEFAULT 0 ,'data2' INTEGER NOT NULL DEFAULT 0 ,'data3' INTEGER NOT NULL DEFAULT 0 ,'data4' INTEGER NOT NULL DEFAULT 0 ,'data5' INTEGER NOT NULL DEFAULT 0 ,'text1' TEXT,'text2' TEXT,'text3' TEXT,'text4' TEXT,'text5' TEXT);");
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
        Long valueOf = cursor2.isNull(i2) ? null : Long.valueOf(cursor2.getLong(i2));
        int i3 = i + 1;
        String string = cursor2.isNull(i3) ? null : cursor2.getString(i3);
        int i4 = cursor2.getInt(i + 2);
        long j = cursor2.getLong(i + 3);
        long j2 = cursor2.getLong(i + 4);
        int i5 = i + 5;
        String string2 = cursor2.isNull(i5) ? null : cursor2.getString(i5);
        int i6 = i + 6;
        String string3 = cursor2.isNull(i6) ? null : cursor2.getString(i6);
        int i7 = i + 7;
        String string4 = cursor2.isNull(i7) ? null : cursor2.getString(i7);
        int i8 = cursor2.getInt(i + 8);
        int i9 = cursor2.getInt(i + 9);
        int i10 = cursor2.getInt(i + 10);
        int i11 = cursor2.getInt(i + 11);
        int i12 = cursor2.getInt(i + 12);
        int i13 = i + 13;
        String string5 = cursor2.isNull(i13) ? null : cursor2.getString(i13);
        int i14 = i + 14;
        String string6 = cursor2.isNull(i14) ? null : cursor2.getString(i14);
        int i15 = i + 15;
        String string7 = cursor2.isNull(i15) ? null : cursor2.getString(i15);
        int i16 = i + 16;
        int i17 = i + 17;
        ua uaVar = new ua(valueOf, string, i4, j, j2, string2, string3, string4, i8, i9, i10, i11, i12, string5, string6, string7, cursor2.isNull(i16) ? null : cursor2.getString(i16), cursor2.isNull(i17) ? null : cursor2.getString(i17));
        return uaVar;
    }
}
