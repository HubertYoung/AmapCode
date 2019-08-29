package com.autonavi.minimap.offline.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.amap.bundle.drivecommon.voicesquare.DownloadVoiceDao;
import com.autonavi.minimap.offline.utils.OfflineLog;
import java.io.File;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public final class OfflineDbHelper {
    private static final String TAG = "OfflineDbHelper";
    private static volatile OfflineDbHelper sInstance;
    private ReentrantLock lock = new ReentrantLock();
    private DownloadVoiceDao mDownloadVoiceDao;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private OfflineDbHelper() {
        try {
            tz tzVar = tx.a().a;
            if (tzVar != null) {
                this.mDownloadVoiceDao = tzVar.b;
                OfflineLog.d(TAG, "OfflineDbHelper newInstance");
            }
        } catch (Exception unused) {
        }
    }

    public static synchronized OfflineDbHelper getInstance() {
        OfflineDbHelper offlineDbHelper;
        synchronized (OfflineDbHelper.class) {
            if (sInstance == null) {
                sInstance = new OfflineDbHelper();
            }
            offlineDbHelper = sInstance;
        }
        return offlineDbHelper;
    }

    private static void updateLocale(Context context, String str) {
        SQLiteDatabase openDatabase;
        try {
            String path = context.getDatabasePath(str).getPath();
            if (new File(path).exists()) {
                openDatabase = SQLiteDatabase.openDatabase(path, null, 16, null);
                if (openDatabase != null) {
                    try {
                        String locale = Locale.getDefault().toString();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("locale", locale);
                        StringBuilder sb = new StringBuilder("locale!='");
                        sb.append(locale);
                        sb.append("'");
                        openDatabase.update("android_metadata", contentValues, sb.toString(), null);
                        openDatabase.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        openDatabase.close();
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    public final DownloadVoiceDao getDownloadVoiceDao() {
        return this.mDownloadVoiceDao;
    }
}
