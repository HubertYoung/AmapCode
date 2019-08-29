package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private String initSql;

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    DBHelper(Context context, String str, int i, String str2) {
        super(context, str, null, i);
        this.initSql = str2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        this.db = sQLiteDatabase;
        if (!TextUtils.isEmpty(this.initSql)) {
            sQLiteDatabase.execSQL(this.initSql);
        }
    }

    public long insert(String str, ContentValues contentValues) {
        if (this.db == null) {
            this.db = getWritableDatabase();
        }
        return this.db.insert(str, null, contentValues);
    }

    public int delete(String str, String str2, String[] strArr) {
        if (this.db == null) {
            this.db = getWritableDatabase();
        }
        return this.db.delete(str, str2, strArr);
    }

    public int update(String str, ContentValues contentValues, String str2, String[] strArr) {
        if (this.db == null) {
            this.db = getWritableDatabase();
        }
        return this.db.update(str, contentValues, str2, strArr);
    }

    public String query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5) {
        JSONArray jSONArray;
        String[] strArr3 = strArr;
        if (this.db == null) {
            this.db = getWritableDatabase();
        }
        Cursor query = this.db.query(str, strArr3, str2, strArr2, str3, str4, str5);
        if (query == null || query.getCount() <= 0) {
            jSONArray = null;
        } else {
            jSONArray = new JSONArray();
            while (query.moveToNext()) {
                HashMap hashMap = new HashMap();
                int i = 0;
                if (strArr3 != null) {
                    int length = strArr3.length;
                    while (i < length) {
                        String str6 = strArr3[i];
                        hashMap.put(str6, query.getString(query.getColumnIndexOrThrow(str6)));
                        i++;
                    }
                } else if (query.getColumnNames() != null) {
                    while (i < query.getColumnNames().length) {
                        hashMap.put(query.getColumnName(i), query.getString(i));
                        i++;
                    }
                }
                jSONArray.put(new JSONObject(hashMap));
            }
            query.close();
        }
        if (jSONArray == null) {
            return null;
        }
        return jSONArray.toString();
    }

    /* access modifiers changed from: 0000 */
    public boolean execSQL(String str) {
        try {
            if (this.db == null) {
                this.db = getWritableDatabase();
            }
            this.db.execSQL(str);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void beginTransaction() {
        this.db.beginTransaction();
    }

    public void endTransaction() {
        this.db.endTransaction();
    }

    public void close() {
        if (this.db != null) {
            this.db.close();
        }
    }
}
