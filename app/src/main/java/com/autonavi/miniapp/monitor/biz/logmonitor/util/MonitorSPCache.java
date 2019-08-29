package com.autonavi.miniapp.monitor.biz.logmonitor.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MonitorSPCache {
    private static final String CACHE_FILE_NAME = "MonitorCache";
    public static final String DEVICE_LOGIC_REBOOT_TIME = "deviceLogicRebootTime";
    private static MonitorSPCache INSTANCE = null;
    public static final String KEY_LAST_SYNC_LOG = "lastsyncLog";
    public static final String KEY_LAST_TRAFIC_POWER_DUMP = "lastTraficPowerDump";
    public static final String KEY_SNAPSHOT_CPU_TIME = "snapshotCpuTime";
    public static final String KEY_SNAPSHOT_ELAPSE_TIME = "snapshotElapseTime";
    public static final String KEY_TOP_DRAIN_ELAPSED = "topDrainElapsed";
    public static final String KEY_TOP_DRAIN_NAME = "topDrainName";
    public static final String KEY_TOP_DRAIN_POWER = "topDrainPower";
    public static final String KEY_TOP_DRAIN_TIMESPAN = "topDrainTimespan";
    public static final String KEY_TOP_DRAIN_UPTIME = "topDrainUptime";
    public static final String KEY_TOTAL_CPU_TIME = "totalCpuTime";
    public static final String KEY_TOTAL_ELAPSE_TIME = "totalElapseTime";
    public static final String KEY_TRAFIC_CHECK_TIME = "traficCheckTime";
    public static final String MONITOR_FOREGROUND_TIME = "gotoForegroundTime";
    public static final String NETWORK_BIND_FAILED_COUNT = "networkBindFailedCount";
    public static final String NETWORK_DIAGNOSE_FAILED_TAG = "networkDiagnoseFailedTag";
    public static final String ON_FOREGROUND_TIMESPAN = "onForegroundTimespan";
    public static final String PROCESS_DATA_REPORT_TIME = "processDataReportTime";
    private Context mContext;
    private SharedPreferences mEntity;

    public static synchronized MonitorSPCache createInstance(Context context) {
        MonitorSPCache monitorSPCache;
        synchronized (MonitorSPCache.class) {
            if (INSTANCE == null) {
                INSTANCE = new MonitorSPCache(context);
            }
            monitorSPCache = INSTANCE;
        }
        return monitorSPCache;
    }

    public static MonitorSPCache getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("need createInstance befor use");
        }
        INSTANCE.prepareEntity();
        return INSTANCE;
    }

    private MonitorSPCache(Context context) {
        this.mContext = context;
    }

    private void prepareEntity() {
        this.mEntity = this.mContext.getSharedPreferences(CACHE_FILE_NAME, 4);
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
