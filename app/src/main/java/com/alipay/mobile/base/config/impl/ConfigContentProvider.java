package com.alipay.mobile.base.config.impl;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class ConfigContentProvider extends ContentProvider {
    public boolean onCreate() {
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(projection);
        Object[] value = new Object[projection.length];
        value[0] = ((ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName())).getConfig(projection[0]);
        cursor.addRow(value);
        return cursor;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
