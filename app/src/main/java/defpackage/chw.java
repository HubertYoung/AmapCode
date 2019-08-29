package defpackage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: chw reason: default package */
/* compiled from: AdiuCpHelper */
public final class chw {
    public static synchronized void a(Context context, String str, String str2) {
        synchronized (chw.class) {
            StringBuilder sb = new StringBuilder("content://");
            sb.append(context.getPackageName());
            sb.append(".adiuprovider");
            String sb2 = sb.toString();
            ContentResolver contentResolver = context.getContentResolver();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("/");
            sb3.append(str);
            Uri parse = Uri.parse(sb3.toString());
            ContentValues contentValues = new ContentValues();
            contentValues.put(str, str2);
            try {
                contentResolver.insert(parse, contentValues);
            } catch (SecurityException unused) {
            }
        }
    }

    public static synchronized List<String> a(Context context, String str) {
        ArrayList arrayList;
        Cursor cursor;
        synchronized (chw.class) {
            StringBuilder sb = new StringBuilder("content://");
            sb.append(context.getPackageName());
            sb.append(".adiuprovider");
            String sb2 = sb.toString();
            ContentResolver contentResolver = context.getContentResolver();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("/");
            sb3.append(str);
            Uri parse = Uri.parse(sb3.toString());
            arrayList = null;
            try {
                cursor = contentResolver.query(parse, null, null, null, null);
            } catch (SecurityException unused) {
                cursor = null;
            }
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex("cursor_value");
                    do {
                        String string = cursor.getString(columnIndex);
                        if (!TextUtils.isEmpty(string)) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(string);
                        }
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
        }
        return arrayList;
    }
}
