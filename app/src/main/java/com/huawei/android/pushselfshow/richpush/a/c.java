package com.huawei.android.pushselfshow.richpush.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class c implements a {
    public Cursor a(Context context, Uri uri, String str, String[] strArr) throws Exception {
        return context.getContentResolver().query(uri, null, null, strArr, null);
    }

    public void a(Context context, Uri uri, String str, ContentValues contentValues) throws Exception {
        context.getContentResolver().insert(uri, contentValues);
    }

    public void a(Context context, Uri uri, String str, String str2, String[] strArr) throws Exception {
        context.getContentResolver().delete(uri, str2, strArr);
    }
}
