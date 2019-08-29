package com.taobao.accs.net;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import com.alipay.zoloz.toyger.bean.Config;
import com.taobao.accs.internal.AccsJobService;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.OrangeAdapter;

public abstract class HeartbeatManager {
    private static final int DEFAULT_HB_TIME = 270;
    private static final String TAG = "HeartbeatManager";
    private static final long UPGRADE_TIME = 7199000;
    protected static volatile HeartbeatManager sInstance;
    private static final int[] values = {270, 360, Config.HQ_IMAGE_WIDTH};
    private boolean justUpgrade = false;
    protected Context mContext;
    private boolean mSmartHbEnabled = true;
    private int nowLevel;
    private long setLevelTime;
    private int[] upgradeFailTimes = {0, 0, 0};

    /* access modifiers changed from: protected */
    public abstract void setInner(int i);

    protected HeartbeatManager(Context context) {
        try {
            this.mContext = context;
            this.nowLevel = 0;
            this.setLevelTime = System.currentTimeMillis();
            this.mSmartHbEnabled = OrangeAdapter.isSmartHb();
        } catch (Throwable th) {
            ALog.e(TAG, TAG, th, new Object[0]);
        }
    }

    public static HeartbeatManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (HeartbeatManager.class) {
                try {
                    if (sInstance == null) {
                        if (VERSION.SDK_INT < 21 || !isJobServiceExist(context)) {
                            ALog.i(TAG, "hb use alarm", new Object[0]);
                            sInstance = new AlarmHeartBeatMgr(context);
                        } else {
                            ALog.i(TAG, "hb use job", new Object[0]);
                            sInstance = new JobHeartBeatMgt(context);
                        }
                    }
                }
            }
        }
        return sInstance;
    }

    private static boolean isJobServiceExist(Context context) {
        try {
            return context.getPackageManager().getServiceInfo(new ComponentName(context.getPackageName(), AccsJobService.class.getName()), 0).isEnabled();
        } catch (Throwable th) {
            ALog.e(TAG, "isJobServiceExist", th, new Object[0]);
            return false;
        }
    }

    public synchronized void set() {
        try {
            if (this.setLevelTime < 0) {
                this.setLevelTime = System.currentTimeMillis();
            }
            int interval = getInterval();
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(TAG, "set ".concat(String.valueOf(interval)), new Object[0]);
            }
            setInner(interval);
        } catch (Throwable th) {
            ALog.e(TAG, "set", th, new Object[0]);
        }
    }

    public int getInterval() {
        int i = this.mSmartHbEnabled ? values[this.nowLevel] : 270;
        this.mSmartHbEnabled = OrangeAdapter.isSmartHb();
        return i;
    }

    public void onNetworkTimeout() {
        this.setLevelTime = -1;
        if (this.justUpgrade) {
            int[] iArr = this.upgradeFailTimes;
            int i = this.nowLevel;
            iArr[i] = iArr[i] + 1;
        }
        this.nowLevel = this.nowLevel > 0 ? this.nowLevel - 1 : 0;
        ALog.d(TAG, "onNetworkTimeout", new Object[0]);
    }

    public void onNetworkFail() {
        this.setLevelTime = -1;
        ALog.d(TAG, "onNetworkFail", new Object[0]);
    }

    public void onHeartbeatSucc() {
        ALog.d(TAG, "onHeartbeatSucc", new Object[0]);
        if (System.currentTimeMillis() - this.setLevelTime <= UPGRADE_TIME) {
            this.justUpgrade = false;
            this.upgradeFailTimes[this.nowLevel] = 0;
        } else if (this.nowLevel < values.length - 1 && this.upgradeFailTimes[this.nowLevel] <= 2) {
            ALog.d(TAG, "upgrade", new Object[0]);
            this.nowLevel++;
            this.justUpgrade = true;
            this.setLevelTime = System.currentTimeMillis();
        }
    }

    public void resetLevel() {
        this.nowLevel = 0;
        this.setLevelTime = System.currentTimeMillis();
        ALog.d(TAG, "resetLevel", new Object[0]);
    }
}
