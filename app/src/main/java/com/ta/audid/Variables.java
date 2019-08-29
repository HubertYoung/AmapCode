package com.ta.audid;

import android.content.Context;
import android.text.TextUtils;
import com.ta.audid.db.DBMgr;
import com.ta.audid.permission.PermissionUtils;
import com.ta.audid.upload.UtdidKeyFile;
import com.ta.audid.utils.UtdidLogger;
import java.io.File;

public class Variables {
    private static final String DATABASE_NAME = "utdid.db";
    private static final Variables mInstance = new Variables();
    private boolean bGetModeState = false;
    private volatile boolean bInit = false;
    private boolean bOldMode = false;
    private String mAppChannel = "";
    private String mAppkey = "testKey";
    private Context mContext = null;
    private DBMgr mDbMgr = null;
    private long mDeltaTime = 0;
    private File mOldModeFile = null;
    private boolean mPhoneStatePermission = false;
    private boolean mStoragePermission = false;

    private Variables() {
    }

    public static Variables getInstance() {
        return mInstance;
    }

    public synchronized void init() {
        if (!this.bInit) {
            this.mDbMgr = new DBMgr(this.mContext, DATABASE_NAME);
            this.mStoragePermission = PermissionUtils.checkStoragePermissionGranted(this.mContext);
            this.mPhoneStatePermission = PermissionUtils.checkReadPhoneStatePermissionGranted(this.mContext);
            this.bInit = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void initContext(android.content.Context r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            android.content.Context r0 = r1.mContext     // Catch:{ all -> 0x001b }
            if (r0 != 0) goto L_0x0019
            if (r2 != 0) goto L_0x0009
            monitor-exit(r1)
            return
        L_0x0009:
            android.content.Context r0 = r2.getApplicationContext()     // Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x0017
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ all -> 0x001b }
            r1.mContext = r2     // Catch:{ all -> 0x001b }
            monitor-exit(r1)
            return
        L_0x0017:
            r1.mContext = r2     // Catch:{ all -> 0x001b }
        L_0x0019:
            monitor-exit(r1)
            return
        L_0x001b:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.Variables.initContext(android.content.Context):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003e, code lost:
        return;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setOldMode(boolean r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            r1 = 1
            r5.bOldMode = r6     // Catch:{ Exception -> 0x0041 }
            java.lang.String r2 = ""
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0041 }
            boolean r4 = r5.bOldMode     // Catch:{ Exception -> 0x0041 }
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)     // Catch:{ Exception -> 0x0041 }
            r3[r0] = r4     // Catch:{ Exception -> 0x0041 }
            com.ta.audid.utils.UtdidLogger.d(r2, r3)     // Catch:{ Exception -> 0x0041 }
            java.io.File r2 = r5.mOldModeFile     // Catch:{ Exception -> 0x0041 }
            if (r2 != 0) goto L_0x0023
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0041 }
            java.lang.String r3 = com.ta.audid.upload.UtdidKeyFile.getOldModeFilePath()     // Catch:{ Exception -> 0x0041 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0041 }
            r5.mOldModeFile = r2     // Catch:{ Exception -> 0x0041 }
        L_0x0023:
            java.io.File r2 = r5.mOldModeFile     // Catch:{ Exception -> 0x0041 }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x0041 }
            if (r6 == 0) goto L_0x0034
            if (r2 != 0) goto L_0x0034
            java.io.File r6 = r5.mOldModeFile     // Catch:{ Exception -> 0x0041 }
            r6.createNewFile()     // Catch:{ Exception -> 0x0041 }
            monitor-exit(r5)
            return
        L_0x0034:
            if (r6 != 0) goto L_0x003d
            if (r2 == 0) goto L_0x003d
            java.io.File r6 = r5.mOldModeFile     // Catch:{ Exception -> 0x0041 }
            r6.delete()     // Catch:{ Exception -> 0x0041 }
        L_0x003d:
            monitor-exit(r5)
            return
        L_0x003f:
            r6 = move-exception
            goto L_0x004d
        L_0x0041:
            r6 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x003f }
            r1[r0] = r6     // Catch:{ all -> 0x003f }
            com.ta.audid.utils.UtdidLogger.d(r2, r1)     // Catch:{ all -> 0x003f }
            monitor-exit(r5)
            return
        L_0x004d:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.Variables.setOldMode(boolean):void");
    }

    public synchronized boolean getOldMode() {
        if (this.bGetModeState) {
            UtdidLogger.d((String) "", Boolean.valueOf(this.bOldMode));
            return this.bOldMode;
        }
        try {
            if (this.mOldModeFile == null) {
                this.mOldModeFile = new File(UtdidKeyFile.getOldModeFilePath());
            }
            if (this.mOldModeFile.exists()) {
                this.bOldMode = true;
                UtdidLogger.d((String) "", "old mode file");
                boolean z = this.bOldMode;
                this.bGetModeState = true;
                return z;
            }
        } catch (Exception e) {
            try {
                UtdidLogger.d((String) "", e);
            } catch (Throwable th) {
                this.bGetModeState = true;
                throw th;
            }
        }
        this.bGetModeState = true;
        this.bOldMode = false;
        UtdidLogger.d((String) "", "new mode file");
        return this.bOldMode;
    }

    public void setDebug(boolean z) {
        UtdidLogger.setDebug(z);
    }

    public Context getContext() {
        return this.mContext;
    }

    public DBMgr getDbMgr() {
        return this.mDbMgr;
    }

    public boolean userGrantStoragePermission() {
        boolean checkStoragePermissionGranted = PermissionUtils.checkStoragePermissionGranted(this.mContext);
        if (this.mStoragePermission || !checkStoragePermissionGranted) {
            this.mStoragePermission = checkStoragePermissionGranted;
            return false;
        }
        this.mStoragePermission = true;
        return true;
    }

    public boolean userGrantPhoneStatePermission() {
        boolean checkReadPhoneStatePermissionGranted = PermissionUtils.checkReadPhoneStatePermissionGranted(this.mContext);
        if (this.mPhoneStatePermission || !checkReadPhoneStatePermissionGranted) {
            this.mStoragePermission = checkReadPhoneStatePermissionGranted;
            return false;
        }
        this.mStoragePermission = true;
        return true;
    }

    public void setAppkey(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mAppkey = str;
        }
    }

    public String getAppkey() {
        return this.mAppkey;
    }

    public void setAppChannel(String str) {
        this.mAppChannel = str;
    }

    public String getAppChannel() {
        return this.mAppChannel;
    }

    public void setSystemTime(long j) {
        this.mDeltaTime = j - System.currentTimeMillis();
    }

    public long getCurrentTimeMillis() {
        return System.currentTimeMillis() + this.mDeltaTime;
    }

    public String getCurrentTimeMillisString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCurrentTimeMillis());
        return sb.toString();
    }
}
