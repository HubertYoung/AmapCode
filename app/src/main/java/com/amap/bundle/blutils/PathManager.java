package com.amap.bundle.blutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;

public class PathManager {
    private static volatile PathManager c;
    public Context a;
    private SharedPreferences b;
    private String d;

    public enum DirType {
        WORK_ROOT("/", "autonavi_work_root"),
        OFFLINE("/data/navi/compile_v2/chn", "offline_root_path"),
        DRIVE_VOICE("/data/voice", "voice_root_path"),
        DRIVE_OFFLINE("/data/navi/compile_v2/td", "drive_offline_root_path"),
        RESOURCE("/res", "autonavi_res_path"),
        LOG(Configuration.VAL_LOG, "autonavi_log_path"),
        DATA("/data", "autonavi_data_path");
        
        private String key;
        private String path;

        private DirType(String str, String str2) {
            this.path = str;
            this.key = str2;
        }

        public final String getPath() {
            return this.path;
        }

        public final String getKey() {
            return this.key;
        }
    }

    private PathManager() {
    }

    public static PathManager a() {
        if (c == null) {
            synchronized (PathManager.class) {
                try {
                    if (c == null) {
                        c = new PathManager();
                    }
                }
            }
        }
        return c;
    }

    public final void a(Context context) {
        this.a = context.getApplicationContext();
        this.b = this.a.getSharedPreferences("base_path", 0);
        b(this.a);
        StringBuilder sb = new StringBuilder("init--mDefaultRootPath=");
        sb.append(this.d);
        AMapLog.d("PathManager", sb.toString());
        String string = this.b.getString("offline_data_storage", "");
        AMapLog.d("PathManager", "init--oldVersionPath=".concat(String.valueOf(string)));
        if (!TextUtils.isEmpty(string)) {
            a(DirType.OFFLINE, string);
            a(DirType.DRIVE_VOICE, string);
            Editor edit = this.b.edit();
            edit.remove("offline_data_storage");
            edit.apply();
        }
    }

    private void b(Context context) {
        this.d = "";
        String mapBaseDBStorage = FileUtil.getMapBaseDBStorage(context);
        AMapLog.d("PathManager", "initDefaultPath--mapBasePath=".concat(String.valueOf(mapBaseDBStorage)));
        if (!TextUtils.isEmpty(mapBaseDBStorage)) {
            this.d = mapBaseDBStorage;
            return;
        }
        String innerSDCardPath = FileUtil.getInnerSDCardPath(context);
        AMapLog.d("PathManager", "initDefaultPath--innerPath=".concat(String.valueOf(innerSDCardPath)));
        StringBuilder sb = new StringBuilder("initDefaultPath--checkPathIsCanUse=");
        sb.append(a(innerSDCardPath));
        AMapLog.d("PathManager", sb.toString());
        if (a(innerSDCardPath)) {
            FileUtil.setMapBaseDBStorage(context, innerSDCardPath);
            this.d = innerSDCardPath;
            return;
        }
        String exterSDCardPath = FileUtil.getExterSDCardPath(context);
        AMapLog.d("PathManager", "initDefaultPath--sdcardPath=".concat(String.valueOf(exterSDCardPath)));
        StringBuilder sb2 = new StringBuilder("initDefaultPath--checkPathIsCanUse=");
        sb2.append(a(exterSDCardPath));
        AMapLog.d("PathManager", sb2.toString());
        if (a(exterSDCardPath)) {
            this.d = exterSDCardPath;
        }
    }

    private static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            return false;
        }
        return true;
    }

    public final void a(DirType dirType, String str) {
        StringBuilder sb = new StringBuilder("setCurrentRootPath--key = ");
        sb.append(dirType.getKey());
        sb.append("-path=");
        sb.append(str);
        AMapLog.d("PathManager", sb.toString());
        Editor edit = this.b.edit();
        edit.putString(dirType.getKey(), str);
        edit.apply();
    }

    public final String a(DirType dirType) {
        return b(dirType, c(dirType));
    }

    public final String b(DirType dirType) {
        String string = this.b.getString(dirType.getKey(), this.d);
        AMapLog.d("PathManager", "getCurrentRootPath--workroot=".concat(String.valueOf(string)));
        return string;
    }

    public static String b(DirType dirType, String str) {
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            if (!b(str)) {
                StringBuilder sb = new StringBuilder("create work directory: ");
                sb.append(str);
                sb.append(" failed!");
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(dirType.getPath());
            str2 = sb2.toString();
            if (!b(str2)) {
                StringBuilder sb3 = new StringBuilder("create directory: ");
                sb3.append(str2);
                sb3.append(" failed!");
            }
        }
        return str2;
    }

    public final String c(DirType dirType) {
        String string = this.b.getString(dirType.getKey(), this.d);
        AMapLog.d("PathManager", "getWorkRootPath--workroot=".concat(String.valueOf(string)));
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        sb.append(FilePathHelper.APP_FOLDER);
        return sb.toString();
    }

    private static boolean b(String str) {
        File file = new File(str);
        AMapLog.d("PathManager", "createDirs---path=".concat(String.valueOf(str)));
        StringBuilder sb = new StringBuilder("createDirs---dir.exists()=");
        sb.append(file.exists());
        AMapLog.d("PathManager", sb.toString());
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    public final String b() {
        if (TextUtils.isEmpty(this.d)) {
            b(this.a);
        }
        return this.d;
    }
}
