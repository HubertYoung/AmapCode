package com.ut.mini.anti_cheat;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import com.alibaba.analytics.utils.Logger;

class ScreenshotDetector {
    private static final long TIME_MAX = 30;
    private ContentObserver contentObserver;
    private ScreenshotListener listener;
    /* access modifiers changed from: private */
    public Context mContext;

    class FileData {
        /* access modifiers changed from: private */
        public final long date;
        /* access modifiers changed from: private */
        public final String path;

        public FileData(String str, long j) {
            this.path = str;
            this.date = j;
        }
    }

    public interface ScreenshotListener {
        void onScreenCaptured(String str);

        void onScreenCapturedWithDeniedPermission();
    }

    ScreenshotDetector(Context context) {
        this.mContext = context;
        try {
            this.contentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
                public boolean deliverSelfNotifications() {
                    return super.deliverSelfNotifications();
                }

                public void onChange(boolean z) {
                    super.onChange(z);
                }

                public void onChange(boolean z, Uri uri) {
                    Logger.d();
                    super.onChange(z, uri);
                    FileData access$100 = ScreenshotDetector.this.getFilePathFromContentResolver(ScreenshotDetector.this.mContext, uri);
                    if (ScreenshotDetector.this.isValidScreenshot(access$100)) {
                        ScreenshotDetector.this.onScreenCaptured(access$100.path);
                    }
                }
            };
        } catch (Throwable unused) {
        }
    }

    public void start(ScreenshotListener screenshotListener) {
        this.listener = screenshotListener;
        try {
            this.mContext.getContentResolver().registerContentObserver(Media.EXTERNAL_CONTENT_URI, true, this.contentObserver);
        } catch (Throwable unused) {
        }
    }

    public void stop() {
        try {
            this.mContext.getContentResolver().unregisterContentObserver(this.contentObserver);
        } catch (Throwable unused) {
        }
    }

    /* access modifiers changed from: private */
    public void onScreenCaptured(String str) {
        Logger.d();
        if (this.listener != null) {
            this.listener.onScreenCaptured(str);
        }
    }

    /* access modifiers changed from: private */
    public boolean isValidScreenshot(FileData fileData) {
        if (fileData == null || TextUtils.isEmpty(fileData.path)) {
            return false;
        }
        Logger.d((String) "", "data.path", fileData.path);
        if (fileData.path.toLowerCase().contains("screenshots")) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        Logger.d((String) "", "localtime", Long.valueOf(currentTimeMillis), "data.date", Long.valueOf(fileData.date));
        if (Math.abs(currentTimeMillis - fileData.date) < TIME_MAX) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public FileData getFilePathFromContentResolver(Context context, Uri uri) {
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_display_name", "_data", "date_added"}, null, null, "date_added DESC");
            if (query != null && query.moveToFirst()) {
                String string = query.getString(query.getColumnIndex("_data"));
                long j = query.getLong(query.getColumnIndex("date_added"));
                query.close();
                return new FileData(string, j);
            }
        } catch (Throwable th) {
            Logger.d((String) "", th);
        }
        return null;
    }
}
