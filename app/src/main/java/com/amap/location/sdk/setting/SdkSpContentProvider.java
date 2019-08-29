package com.amap.location.sdk.setting;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;

public class SdkSpContentProvider extends ContentProvider {
    public static final Uri a = Uri.parse("content://com.amap.location.sdk.sdksetting/agoo");
    private static final UriMatcher b;
    private static SharedPreferences c;

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        b = uriMatcher;
        uriMatcher.addURI("com.amap.location.sdk.sdksetting", "agoo", 20);
    }

    public boolean onCreate() {
        c = getContext().getSharedPreferences(AppInitCallback.SP_NAME_AmapCloudControlAgooXML, 0);
        return false;
    }

    public String getType(Uri uri) {
        if (b.match(uri) == 20) {
            return "vnd.android.cursor.dir/agoo";
        }
        throw new IllegalArgumentException("Unknown URI ".concat(String.valueOf(uri)));
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        a(uri, "uri");
        MatrixCursor matrixCursor = new MatrixCursor(strArr);
        Object[] objArr = new Object[strArr.length];
        if (b.match(uri) == 20) {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String str3 = strArr[i];
                if ("startTime".equals(str3)) {
                    objArr[i] = Long.valueOf(c.getLong("startTime", -1));
                } else if (AppInitCallback.SP_KEY_endTime.equals(str3)) {
                    objArr[i] = Long.valueOf(c.getLong(AppInitCallback.SP_KEY_endTime, -1));
                }
            }
        }
        matrixCursor.addRow(objArr);
        return matrixCursor;
    }

    private <T> T a(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }
}
