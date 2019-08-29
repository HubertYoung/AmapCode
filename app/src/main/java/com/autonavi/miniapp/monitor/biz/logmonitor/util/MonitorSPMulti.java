package com.autonavi.miniapp.monitor.biz.logmonitor.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MonitorSPMulti {
    public static final String AUTOSTART_REBOOT_TIME = "autostart_reboot_time";
    public static final String AUTOSTART_STATUS = "autostart_status";
    private static MonitorSPMulti INSTANCE = null;
    private static final String SP_FILE_NAME = "MonitorMulti";
    private Context mContext;
    private SharedPreferences mEntity;

    public static synchronized MonitorSPMulti createInstance(Context context) {
        MonitorSPMulti monitorSPMulti;
        synchronized (MonitorSPMulti.class) {
            if (INSTANCE == null) {
                INSTANCE = new MonitorSPMulti(context);
            }
            monitorSPMulti = INSTANCE;
        }
        return monitorSPMulti;
    }

    public static MonitorSPMulti getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("need createInstance befor use");
        }
        INSTANCE.prepareEntity();
        return INSTANCE;
    }

    private MonitorSPMulti(Context context) {
        this.mContext = context;
    }

    private void prepareEntity() {
        this.mEntity = this.mContext.getSharedPreferences(SP_FILE_NAME, 4);
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
