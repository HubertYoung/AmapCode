package com.ali.user.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ali.user.mobile.log.AliUserLog;

public class UserInfoDBHelper extends SQLiteOpenHelper {
    private static SQLiteOpenHelper a;

    private UserInfoDBHelper(Context context) {
        super(context, "aliuser", null, 1);
    }

    public static SQLiteOpenHelper a(Context context) {
        if (a == null) {
            synchronized (UserInfoDBHelper.class) {
                if (a == null) {
                    a = new UserInfoDBHelper(context);
                }
            }
        }
        return a;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(UserInfoSqlHelper.a());
        } catch (Exception e) {
            AliUserLog.a("UserInfo_dbHelper", "创建数据库失败", e);
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        AliUserLog.c("UserInfo_dbHelper", String.format("onUpgrade, oldVersion:%s, newVersion:%s", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
    }
}
