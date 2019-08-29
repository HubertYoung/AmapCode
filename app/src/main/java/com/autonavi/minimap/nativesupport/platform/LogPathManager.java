package com.autonavi.minimap.nativesupport.platform;

import android.app.Application;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;

public class LogPathManager {
    private static final long FILE_LENGTH = 266240;
    private static final String LOGPATH = "offlineNetworkLog.txt";
    private static final String LOGPATH2 = "offlineNetworkLog_.txt";
    public static final String OFFLINE_LOG_NAME = "offline_log_name";
    private static final String dataPath = getDataPath();

    public static String getCurrentPath() {
        boolean checkLogFileStatus = checkLogFileStatus(LOGPATH);
        if (checkLogFileStatus) {
            return LOGPATH;
        }
        boolean checkLogFileStatus2 = checkLogFileStatus(LOGPATH2);
        if (checkLogFileStatus2) {
            return LOGPATH2;
        }
        if (!checkLogFileStatus && !checkLogFileStatus2) {
            long fileLastModified = getFileLastModified(LOGPATH);
            int i = (fileLastModified > getFileLastModified(LOGPATH2) ? 1 : (fileLastModified == getFileLastModified(LOGPATH2) ? 0 : -1));
            if (i > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(dataPath);
                sb.append("/offlineNetworkLog_.txt");
                File file = new File(sb.toString());
                if (file.exists()) {
                    file.delete();
                }
                return LOGPATH2;
            } else if (i < 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(dataPath);
                sb2.append("/offlineNetworkLog.txt");
                File file2 = new File(sb2.toString());
                if (file2.exists()) {
                    file2.delete();
                }
                return LOGPATH;
            } else if (i == 0 && fileLastModified == 0) {
                return LOGPATH;
            }
        }
        return "";
    }

    public static String getUpLoadLogPath() {
        String stringValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue(OFFLINE_LOG_NAME, "");
        if (TextUtils.isEmpty(stringValue)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(dataPath);
        sb.append("/");
        sb.append(stringValue);
        return sb.toString();
    }

    private static String getDataPath() {
        Application application = AMapAppGlobal.getApplication();
        if (application != null) {
            File filesDir = application.getFilesDir();
            if (filesDir != null && filesDir.exists()) {
                return filesDir.getAbsolutePath();
            }
        }
        return "";
    }

    private static boolean checkLogFileStatus(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(dataPath);
        sb.append("/");
        sb.append(str);
        File file = new File(sb.toString());
        if (!file.exists() || file.length() < FILE_LENGTH) {
            return true;
        }
        return false;
    }

    private static long getFileLastModified(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(dataPath);
        sb.append("/");
        sb.append(str);
        File file = new File(sb.toString());
        if (file.exists()) {
            return file.lastModified();
        }
        return 0;
    }
}
