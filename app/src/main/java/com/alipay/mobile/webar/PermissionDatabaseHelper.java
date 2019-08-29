package com.alipay.mobile.webar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.Date;

public class PermissionDatabaseHelper extends SQLiteOpenHelper {

    public enum PERMISSION_TYPE {
        ;

        private PERMISSION_TYPE(String str) {
        }
    }

    public PermissionDatabaseHelper(Context context) {
        super(context, "com_alipay_mobile_webar_permission_database.db", null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS permission (_id integer primary key autoincrement, url varchar(2048), type integer, expiration INTEGER, allow char(1))");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public final void a() {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.delete("permission", "expiration < ?", new String[]{String.valueOf(new Date().getTime())});
            return;
        }
        H5Log.e((String) "PermissionDatabaseHelper", (String) "db is null, deleteExpiration fail");
    }

    public final boolean a(String url, PERMISSION_TYPE type) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Cursor Cursor = db.query("permission", new String[]{"_id"}, "url = ? and type = ? and allow = 1 and expiration >= ?", new String[]{url, type.ordinal(), String.valueOf(new Date().getTime())}, null, null, null, null);
            int count = Cursor.getCount();
            Cursor.close();
            return count > 0;
        }
        H5Log.e((String) "PermissionDatabaseHelper", (String) "db is null, isPermissionAllow fail");
        return false;
    }

    public final void a(String url, PERMISSION_TYPE type, long time) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.delete("permission", "url = ? and type = ? ", new String[]{url, type.ordinal()});
            ContentValues cv = new ContentValues();
            cv.put("url", url);
            cv.put("type", Integer.valueOf(type.ordinal()));
            cv.put("expiration", Long.valueOf(time));
            cv.put("allow", Integer.valueOf(1));
            db.insert("permission", null, cv);
            return;
        }
        H5Log.e((String) "PermissionDatabaseHelper", (String) "db is null, savePermission fail");
    }
}
