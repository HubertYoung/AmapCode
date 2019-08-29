package com.alipay.mobile.nebulacore.util;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.ArrayList;
import java.util.List;

public class H5ScreenShotObserver {
    /* access modifiers changed from: private */
    public static final String[] a = {"_data", "date_added"};
    /* access modifiers changed from: private */
    public static final String b = Media.EXTERNAL_CONTENT_URI.toString();
    /* access modifiers changed from: private */
    public final List<H5ScreenShotListener> c = new ArrayList();
    /* access modifiers changed from: private */
    public H5ScreenShotListener d;
    /* access modifiers changed from: private */
    public Context e;
    private final ContentObserver f = new ContentObserver(new Handler(Looper.getMainLooper())) {
        public void onChange(boolean selfChange) {
            H5Log.d("H5ScreenShotObserver", "old onChange " + selfChange);
            try {
                super.onChange(selfChange);
                onChange(selfChange, null);
            } catch (Throwable throwable) {
                H5Log.e((String) "H5ScreenShotObserver", throwable);
            }
        }

        private Cursor a(Uri uri) {
            if (uri == null) {
                return H5ScreenShotObserver.this.e.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, H5ScreenShotObserver.a, null, null, "date_added DESC");
            }
            if (!uri.toString().contains(H5ScreenShotObserver.b)) {
                return null;
            }
            return H5ScreenShotObserver.this.e.getContentResolver().query(uri, H5ScreenShotObserver.a, null, null, "date_added DESC");
        }

        public void onChange(boolean selfChange, Uri uri) {
            try {
                H5Log.d("H5ScreenShotObserver", "new onChange " + selfChange + ", uri " + uri);
                if (H5ScreenShotObserver.this.e == null) {
                    H5Log.w("H5ScreenShotObserver", "new onChange mContext == null");
                    return;
                }
                Cursor cursor = a(uri);
                if (cursor == null) {
                    return;
                }
                if (!cursor.moveToFirst()) {
                    cursor.close();
                    return;
                }
                String path = cursor.getString(cursor.getColumnIndex("_data"));
                if (Math.abs((System.currentTimeMillis() / 1000) - cursor.getLong(cursor.getColumnIndex("date_added"))) <= 10 && !TextUtils.isEmpty(path) && path.toLowerCase().contains("screenshot")) {
                    H5Log.d("H5ScreenShotObserver", "onScreenShot, listeners: " + H5ScreenShotObserver.this.c.size() + ", path: " + path);
                    if (H5ScreenShotObserver.this.d != null) {
                        H5ScreenShotObserver.this.d.onScreenShot();
                    }
                    if (H5ScreenShotObserver.this.c.size() > 0) {
                        for (H5ScreenShotListener onScreenShot : H5ScreenShotObserver.this.c) {
                            onScreenShot.onScreenShot();
                        }
                    }
                }
                cursor.close();
            } catch (Throwable throwable) {
                H5Log.e((String) "H5ScreenShotObserver", throwable);
            }
        }
    };

    public interface H5ScreenShotListener {
        void onScreenShot();
    }

    public H5ScreenShotObserver(Context context) {
        this.e = context;
        if (this.e != null) {
            H5Log.d("H5ScreenShotObserver", "registerContentObserver");
            this.e.getContentResolver().registerContentObserver(Media.EXTERNAL_CONTENT_URI, false, this.f);
        }
    }

    public void setCommonListener(H5ScreenShotListener commonListener) {
        this.d = commonListener;
    }

    public void registerListener(H5ScreenShotListener listener) {
        this.c.add(listener);
    }

    public void release() {
        this.c.clear();
        if (this.e != null) {
            H5Log.d("H5ScreenShotObserver", "unregisterContentObserver");
            this.e.getContentResolver().unregisterContentObserver(this.f);
        }
        this.e = null;
    }
}
