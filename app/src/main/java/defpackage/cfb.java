package defpackage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.db.UserInfoDao;

@MultipleImpl(xy.class)
/* renamed from: cfb reason: default package */
/* compiled from: UserInfoDbOpenHelper */
public class cfb implements xy {
    public final void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        StringBuilder sb = new StringBuilder("oldVersion: ");
        sb.append(i);
        sb.append(", newVersion: ");
        sb.append(i2);
        AMapLog.e("UserInfoDbOpenHelper", sb.toString());
        if (i <= 2) {
            try {
                UserInfoDao.a(sQLiteDatabase);
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder("onDbUpgrade exception:");
                sb2.append(e.getMessage());
                AMapLog.debug("basemap.account", "UserInfoDbOpenHelper", sb2.toString());
                return;
            }
        }
        if (i <= 19) {
            UserInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE USER_INFO RENAME TO USER_INFO_temp");
            UserInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into USER_INFO(_ID, SN, UID, AVATAR, USERNAME, BIRTHDAY, NICK, SEX, LARGEICONURL, SMALLICONURL, MIDICONURL, EMAIL, BINDINGMOBILE, AGE, SINATOKEN, SINANAME, TOPTOKEN, TAOBAONAME, TAOBAOID, QQTOKEN, QQNAME, QQID, WXTOKEN, WXNAME, WXID, SOURCE, REPWD) select _ID, SN, UID, AVATAR, USERNAME, BIRTHDAY, NICK, SEX, LARGEICONURL, SMALLICONURL, MIDICONURL, EMAIL, BINDINGMOBILE, AGE, SINATOKEN, SINANAME, TOPTOKEN, TAOBAONAME, TAOBAOID, QQTOKEN, QQNAME, QQID, WXTOKEN, WXNAME, WXID, SOURCE, REPWD from USER_INFO_temp");
            sQLiteDatabase.execSQL("DROP TABLE USER_INFO_temp");
        }
        if (i <= 24) {
            UserInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE USER_INFO RENAME TO USER_INFO_temp");
            UserInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into USER_INFO(_ID, SN, UID, AVATAR, USERNAME, BIRTHDAY, NICK, SEX, LARGEICONURL, SMALLICONURL, MIDICONURL, EMAIL, BINDINGMOBILE, AGE, SINATOKEN, SINANAME, TOPTOKEN, TAOBAONAME, TAOBAOID, QQTOKEN, QQNAME, QQID, WXTOKEN, WXNAME, WXID, ALIPAYTOKEN, ALIPAYNAME, ALIPAYID, SOURCE, REPWD) select _ID, SN, UID, AVATAR, USERNAME, BIRTHDAY, NICK, SEX, LARGEICONURL, SMALLICONURL, MIDICONURL, EMAIL, BINDINGMOBILE, AGE, SINATOKEN, SINANAME, TOPTOKEN, TAOBAONAME, TAOBAOID, QQTOKEN, QQNAME, QQID, WXTOKEN, WXNAME, WXID, ALIPAYTOKEN, ALIPAYNAME, ALIPAYID, SOURCE, REPWD from USER_INFO_temp");
            sQLiteDatabase.execSQL("DROP TABLE USER_INFO_temp");
        }
        if (i <= 32) {
            UserInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE USER_INFO RENAME TO USER_INFO_temp");
            UserInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into USER_INFO(_ID, SN, UID, AVATAR, USERNAME, BIRTHDAY, NICK, SEX, LARGEICONURL, SMALLICONURL, MIDICONURL, EMAIL, BINDINGMOBILE, AGE, SINATOKEN, SINANAME, TOPTOKEN, TAOBAONAME, TAOBAOID, QQTOKEN, QQNAME, QQID, WXTOKEN, WXNAME, WXID, ALIPAYTOKEN, ALIPAYNAME, ALIPAYID,MEIZUID , MEIZUNAME, MEIZUTOKEN, SOURCE, REPWD) select _ID, SN, UID, AVATAR, USERNAME, BIRTHDAY, NICK, SEX, LARGEICONURL, SMALLICONURL, MIDICONURL, EMAIL, BINDINGMOBILE, AGE, SINATOKEN, SINANAME, TOPTOKEN, TAOBAONAME, TAOBAOID, QQTOKEN, QQNAME, QQID, WXTOKEN, WXNAME, WXID, ALIPAYTOKEN, ALIPAYNAME, ALIPAYID,MEIZUID , MEIZUNAME, MEIZUTOKEN, SOURCE, REPWD from USER_INFO_temp");
            sQLiteDatabase.execSQL("DROP TABLE USER_INFO_temp");
        }
        if (i < 34) {
            a(sQLiteDatabase);
        }
        if (i < 37) {
            UserInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("ALTER TABLE USER_INFO RENAME TO USER_INFO_temp");
            UserInfoDao.a(sQLiteDatabase);
            sQLiteDatabase.execSQL("insert into USER_INFO(_ID, SN, UID, AVATAR, USERNAME, BIRTHDAY, NICK, SEX, LARGEICONURL, SMALLICONURL, MIDICONURL, EMAIL, BINDINGMOBILE, AGE, SINATOKEN, SINANAME, TOPTOKEN, TAOBAONAME, TAOBAOID, QQTOKEN, QQNAME, QQID, WXTOKEN, WXNAME, WXID, ALIPAYTOKEN, ALIPAYNAME, ALIPAYID,MEIZUID , MEIZUNAME, MEIZUTOKEN, SOURCE, REPWD,LOGOID,LOGONORMAL,LOGOWEAK,_SN,_UID,_USERNAME,_BIRTHDAY,_NICK,_SEX,_EMAIL,_BINDINGMOBILE,_AGE,_SINATOKEN,_SINANAME,_TOPTOKEN,_TAOBAONAME,_TAOBAOID,_QQTOKEN,_QQNAME,_QQID,_WXTOKEN,_WXNAME,_WXID,_ALIPAYTOKEN,_ALIPAYNAME,_ALIPAYID,_MEIZUID,_MEIZUNAME,_MEIZUTOKEN) select _ID, SN, UID, AVATAR, USERNAME, BIRTHDAY, NICK, SEX, LARGEICONURL, SMALLICONURL, MIDICONURL, EMAIL, BINDINGMOBILE, AGE, SINATOKEN, SINANAME, TOPTOKEN, TAOBAONAME, TAOBAOID, QQTOKEN, QQNAME, QQID, WXTOKEN, WXNAME, WXID, ALIPAYTOKEN, ALIPAYNAME, ALIPAYID,MEIZUID , MEIZUNAME, MEIZUTOKEN, SOURCE, REPWD,LOGOID,LOGONORMAL,LOGOWEAK,_SN,_UID,_USERNAME,_BIRTHDAY,_NICK,_SEX,_EMAIL,_BINDINGMOBILE,_AGE,_SINATOKEN,_SINANAME,_TOPTOKEN,_TAOBAONAME,_TAOBAOID,_QQTOKEN,_QQNAME,_QQID,_WXTOKEN,_WXNAME,_WXID,_ALIPAYTOKEN,_ALIPAYNAME,_ALIPAYID,_MEIZUID,_MEIZUNAME,_MEIZUTOKEN from USER_INFO_temp");
            sQLiteDatabase.execSQL("DROP TABLE USER_INFO_temp");
        }
    }

    private static void a(SQLiteDatabase sQLiteDatabase) {
        Cursor cursor;
        SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
        AMapLog.e("UserInfoDbOpenHelper", "updateUserInfoDB");
        UserInfoDao.a(sQLiteDatabase);
        sQLiteDatabase2.execSQL("ALTER TABLE USER_INFO RENAME TO USER_INFO_temp");
        UserInfoDao.a(sQLiteDatabase);
        try {
            cursor = sQLiteDatabase2.rawQuery("select * from USER_INFO_temp", null);
            if (cursor == null) {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                return;
            }
            try {
                if (cursor.moveToFirst()) {
                    SQLiteStatement compileStatement = sQLiteDatabase2.compileStatement("insert into USER_INFO(_ID,SN,UID,AVATAR,USERNAME,BIRTHDAY,NICK,SEX,LARGEICONURL,SMALLICONURL,MIDICONURL,EMAIL,BINDINGMOBILE,AGE,SINATOKEN,SINANAME,TOPTOKEN,TAOBAONAME,TAOBAOID,QQTOKEN,QQNAME,QQID,WXTOKEN,WXNAME,WXID,ALIPAYTOKEN,ALIPAYNAME,ALIPAYID,MEIZUID,MEIZUNAME,MEIZUTOKEN,SOURCE,REPWD,LOGOID,LOGONORMAL,LOGOWEAK,_SN,_UID,_USERNAME,_BIRTHDAY,_NICK,_SEX,_EMAIL,_BINDINGMOBILE,_AGE,_SINATOKEN,_SINANAME,_TOPTOKEN,_TAOBAONAME,_TAOBAOID,_QQTOKEN,_QQNAME,_QQID,_WXTOKEN,_WXNAME,_WXID,_ALIPAYTOKEN,_ALIPAYNAME,_ALIPAYID,_MEIZUID,_MEIZUNAME,_MEIZUTOKEN) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    do {
                        if (!cursor.isNull(0)) {
                            compileStatement.bindLong(1, Long.valueOf(cursor.getLong(0)).longValue());
                        }
                        if (!cursor.isNull(1)) {
                            compileStatement.bindNull(2);
                        }
                        if (!cursor.isNull(2)) {
                            compileStatement.bindNull(3);
                        }
                        if (!cursor.isNull(3)) {
                            compileStatement.bindString(4, cursor.getString(3));
                        }
                        if (!cursor.isNull(4)) {
                            compileStatement.bindNull(5);
                        }
                        if (!cursor.isNull(5)) {
                            compileStatement.bindNull(6);
                        }
                        if (!cursor.isNull(6)) {
                            compileStatement.bindNull(7);
                        }
                        if (!cursor.isNull(7)) {
                            compileStatement.bindLong(8, (long) cursor.getShort(7));
                        }
                        if (!cursor.isNull(8)) {
                            compileStatement.bindString(9, cursor.getString(8));
                        }
                        if (!cursor.isNull(9)) {
                            compileStatement.bindString(10, cursor.getString(9));
                        }
                        if (!cursor.isNull(10)) {
                            compileStatement.bindString(11, cursor.getString(10));
                        }
                        if (!cursor.isNull(11)) {
                            compileStatement.bindNull(12);
                        }
                        if (!cursor.isNull(12)) {
                            compileStatement.bindNull(13);
                        }
                        if (!cursor.isNull(13)) {
                            compileStatement.bindNull(14);
                        }
                        if (!cursor.isNull(14)) {
                            compileStatement.bindNull(15);
                        }
                        if (!cursor.isNull(15)) {
                            compileStatement.bindNull(16);
                        }
                        if (!cursor.isNull(16)) {
                            compileStatement.bindNull(17);
                        }
                        if (!cursor.isNull(17)) {
                            compileStatement.bindNull(18);
                        }
                        if (!cursor.isNull(18)) {
                            compileStatement.bindNull(19);
                        }
                        if (!cursor.isNull(19)) {
                            compileStatement.bindNull(20);
                        }
                        if (!cursor.isNull(20)) {
                            compileStatement.bindNull(21);
                        }
                        if (!cursor.isNull(21)) {
                            compileStatement.bindNull(22);
                        }
                        if (!cursor.isNull(22)) {
                            compileStatement.bindNull(23);
                        }
                        if (!cursor.isNull(23)) {
                            compileStatement.bindNull(24);
                        }
                        if (!cursor.isNull(24)) {
                            compileStatement.bindNull(25);
                        }
                        if (!cursor.isNull(25)) {
                            compileStatement.bindNull(26);
                        }
                        if (!cursor.isNull(26)) {
                            compileStatement.bindNull(27);
                        }
                        if (!cursor.isNull(27)) {
                            compileStatement.bindNull(28);
                        }
                        if (!cursor.isNull(28)) {
                            compileStatement.bindString(29, cursor.getString(28));
                        }
                        if (!cursor.isNull(29)) {
                            compileStatement.bindString(30, cursor.getString(29));
                        }
                        if (!cursor.isNull(30)) {
                            compileStatement.bindString(31, cursor.getString(30));
                        }
                        if (!cursor.isNull(31)) {
                            compileStatement.bindString(32, cursor.getString(31));
                        }
                        if (!cursor.isNull(32)) {
                            compileStatement.bindLong(33, cursor.getShort(32) != 0 ? 1 : 0);
                        }
                        if (!cursor.isNull(33)) {
                            compileStatement.bindString(34, cursor.getString(33));
                        }
                        if (!cursor.isNull(34)) {
                            compileStatement.bindString(35, cursor.getString(34));
                        }
                        if (!cursor.isNull(35)) {
                            compileStatement.bindString(36, cursor.getString(35));
                        }
                        if (!cursor.isNull(1)) {
                            compileStatement.bindString(37, xu.a(cursor.getString(1)));
                        }
                        if (!cursor.isNull(2)) {
                            compileStatement.bindString(38, xu.a(cursor.getString(2)));
                        }
                        if (!cursor.isNull(4)) {
                            compileStatement.bindString(39, xu.a(cursor.getString(4)));
                        }
                        if (!cursor.isNull(5)) {
                            compileStatement.bindString(40, xu.a(cursor.getString(5)));
                        }
                        if (!cursor.isNull(6)) {
                            compileStatement.bindString(41, xu.a(cursor.getString(6)));
                        }
                        if (!cursor.isNull(7)) {
                            compileStatement.bindLong(42, (long) Integer.valueOf(cursor.getInt(7)).intValue());
                        }
                        if (!cursor.isNull(11)) {
                            compileStatement.bindString(43, xu.a(cursor.getString(11)));
                        }
                        if (!cursor.isNull(12)) {
                            compileStatement.bindString(44, xu.a(cursor.getString(12)));
                        }
                        if (!cursor.isNull(13)) {
                            compileStatement.bindString(45, xu.a(cursor.getString(13)));
                        }
                        if (!cursor.isNull(14)) {
                            compileStatement.bindString(46, xu.a(cursor.getString(14)));
                        }
                        if (!cursor.isNull(15)) {
                            compileStatement.bindString(47, xu.a(cursor.getString(15)));
                        }
                        if (!cursor.isNull(16)) {
                            compileStatement.bindString(48, xu.a(cursor.getString(16)));
                        }
                        if (!cursor.isNull(17)) {
                            compileStatement.bindString(49, xu.a(cursor.getString(17)));
                        }
                        if (!cursor.isNull(18)) {
                            compileStatement.bindString(50, xu.a(cursor.getString(18)));
                        }
                        if (!cursor.isNull(19)) {
                            compileStatement.bindString(51, xu.a(cursor.getString(19)));
                        }
                        if (!cursor.isNull(20)) {
                            compileStatement.bindString(52, xu.a(cursor.getString(20)));
                        }
                        if (!cursor.isNull(21)) {
                            compileStatement.bindString(53, xu.a(cursor.getString(21)));
                        }
                        if (!cursor.isNull(22)) {
                            compileStatement.bindString(54, xu.a(cursor.getString(22)));
                        }
                        if (!cursor.isNull(23)) {
                            compileStatement.bindString(55, xu.a(cursor.getString(23)));
                        }
                        if (!cursor.isNull(24)) {
                            compileStatement.bindString(56, xu.a(cursor.getString(24)));
                        }
                        if (!cursor.isNull(25)) {
                            compileStatement.bindString(57, xu.a(cursor.getString(25)));
                        }
                        if (!cursor.isNull(26)) {
                            compileStatement.bindString(58, xu.a(cursor.getString(26)));
                        }
                        if (!cursor.isNull(27)) {
                            compileStatement.bindString(59, xu.a(cursor.getString(27)));
                        }
                        if (!cursor.isNull(28)) {
                            compileStatement.bindString(60, xu.a(cursor.getString(28)));
                        }
                        if (!cursor.isNull(29)) {
                            compileStatement.bindString(61, xu.a(cursor.getString(29)));
                        }
                        if (!cursor.isNull(30)) {
                            compileStatement.bindString(62, xu.a(cursor.getString(30)));
                        }
                        compileStatement.executeInsert();
                    } while (cursor.moveToNext());
                }
                cursor.close();
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sQLiteDatabase2.execSQL("DROP TABLE USER_INFO_temp");
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            Throwable th22 = th;
            cursor.close();
            throw th22;
        }
    }
}
