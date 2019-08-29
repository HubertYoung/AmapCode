package com.autonavi.miniapp.monitor.biz.logmonitor.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class MonitorSPPrivate {
    public static final String CLIENT_STARTUP_TIME = "clientStartupTime";
    public static final String DEVICE_REBOOT_TIME = "deviceRebootTime";
    private static MonitorSPPrivate INSTANCE = null;
    public static final String PROCESS_LAUNCH_FIRSTLY = "processLaunchFirstly";
    public static final String PROCESS_LAUNCH_TIME = "processLaunchTime";
    public static final String REVERT_STEP_PROVIDER = "revertStepProvider";
    private static final String SP_FILE_NAME = "MonitorPrivate_";
    private Context mContext;
    private SharedPreferences mEntity;
    private String spFileName = SP_FILE_NAME;

    public static synchronized MonitorSPPrivate createInstance(Context context) {
        MonitorSPPrivate monitorSPPrivate;
        synchronized (MonitorSPPrivate.class) {
            if (INSTANCE == null) {
                INSTANCE = new MonitorSPPrivate(context);
            }
            monitorSPPrivate = INSTANCE;
        }
        return monitorSPPrivate;
    }

    public static MonitorSPPrivate getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("need createInstance befor use");
        }
        INSTANCE.prepareEntity();
        return INSTANCE;
    }

    private MonitorSPPrivate(Context context) {
        this.mContext = context;
        StringBuilder sb = new StringBuilder();
        sb.append(this.spFileName);
        sb.append(LoggerFactory.getProcessInfo().getProcessAlias());
        this.spFileName = sb.toString();
    }

    private void prepareEntity() {
        if (this.mEntity == null) {
            this.mEntity = this.mContext.getSharedPreferences(this.spFileName, 0);
        }
    }

    public SharedPreferences getEntity() {
        return this.mEntity;
    }

    public Editor getEditor() {
        return this.mEntity.edit();
    }

    public void removeCommit(String str) {
        this.mEntity.edit().remove(str).commit();
    }

    public void removeApply(String str) {
        this.mEntity.edit().remove(str).apply();
    }

    public void putStringCommit(String str, String str2) {
        this.mEntity.edit().putString(str, str2).commit();
    }

    public void putStringApply(String str, String str2) {
        this.mEntity.edit().putString(str, str2).apply();
    }

    public String getString(String str, String str2) {
        return this.mEntity.getString(str, str2);
    }

    public void putIntCommit(String str, int i) {
        this.mEntity.edit().putInt(str, i).commit();
    }

    public void putIntApply(String str, int i) {
        this.mEntity.edit().putInt(str, i).apply();
    }

    public int getInt(String str, int i) {
        return this.mEntity.getInt(str, i);
    }

    public void putLongCommit(String str, long j) {
        this.mEntity.edit().putLong(str, j).commit();
    }

    public void putLongApply(String str, long j) {
        this.mEntity.edit().putLong(str, j).apply();
    }

    public long getLong(String str, long j) {
        return this.mEntity.getLong(str, j);
    }

    public void putBooleanCommit(String str, boolean z) {
        this.mEntity.edit().putBoolean(str, z).commit();
    }

    public void putBooleanApply(String str, boolean z) {
        this.mEntity.edit().putBoolean(str, z).apply();
    }

    public boolean getBoolean(String str, boolean z) {
        return this.mEntity.getBoolean(str, z);
    }
}
