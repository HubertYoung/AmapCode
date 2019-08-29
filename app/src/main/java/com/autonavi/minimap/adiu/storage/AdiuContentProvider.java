package com.autonavi.minimap.adiu.storage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base;
import java.util.List;

public class AdiuContentProvider extends ContentProvider {
    private static final String a = "AdiuContentProvider";
    private chx b;

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public boolean onCreate() {
        this.b = chx.a(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String[] split = uri.getPath().split("/");
        if (split != null && split.length >= 2) {
            String str3 = split[1];
            if (!TextUtils.isEmpty(str3)) {
                this.b.b = str3;
                List<String> a2 = this.b.a();
                if (a2 != null && a2.size() > 0) {
                    MatrixCursor matrixCursor = new MatrixCursor(new String[]{"cursor_value"});
                    for (String str4 : a2) {
                        matrixCursor.addRow(new Object[]{str4});
                    }
                    return matrixCursor;
                }
            }
        }
        return null;
    }

    public String getType(Uri uri) {
        String[] split = uri.getPath().split("/");
        if (split != null && split.length >= 2) {
            String str = split[1];
            this.b.b = str;
            if (!TextUtils.isEmpty(str)) {
                List<String> a2 = this.b.a();
                if (a2 != null) {
                    return a2.get(0);
                }
            }
        }
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        String[] split = uri.getPath().split("/");
        if (split != null && split.length >= 2) {
            String str = split[1];
            if (!TextUtils.isEmpty(str)) {
                String asString = contentValues.getAsString(str);
                this.b.b = str;
                chx chx = this.b;
                if (chx.a != null) {
                    chx.a.clear();
                    chx.a.add(asString);
                }
                chx.a(asString, LZMA_Base.kMatchMaxLen);
            }
        }
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        insert(uri, contentValues);
        return 0;
    }
}
