package com.alipay.mobile.common.transportext.biz.mmtp;

import com.alipay.mobile.common.transport.monitor.SignalStateHelper;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LinkStateListener {
    private static LinkStateListener INSTANCE = null;
    private static final String TAG = LinkStateListener.class.getSimpleName();
    public static final long changeInterval = 3600000;
    private int MAXNUM;
    private int SCHEDULE_STATE_BUSY;
    private int SCHEDULE_STATE_IDLE;
    private int diagnoseNum;
    private long firstDiagnoseTime;
    private ScheduledFuture<?> future;
    private boolean initOk;
    private int scheduleState;

    public static LinkStateListener getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (LinkStateListener.class) {
            if (INSTANCE == null) {
                INSTANCE = new LinkStateListener();
            }
        }
        return INSTANCE;
    }

    private LinkStateListener() {
        this.initOk = false;
        this.SCHEDULE_STATE_IDLE = 1;
        this.SCHEDULE_STATE_BUSY = 2;
        this.MAXNUM = 15;
        this.initOk = false;
        this.scheduleState = this.SCHEDULE_STATE_IDLE;
        this.diagnoseNum = 0;
        this.firstDiagnoseTime = System.currentTimeMillis();
    }

    public void change(int state) {
        LogCatUtil.debug(TAG, "--change-->state = " + state);
        if (state != 4 && MiscUtils.isAtFrontDesk(ExtTransportEnv.getAppContext())) {
            startCheckNetState();
        }
    }

    private void startCheckNetState() {
        this.initOk = false;
        synchronized (this) {
            if (this.scheduleState == this.SCHEDULE_STATE_BUSY) {
                LogCatUtil.debug(TAG, "schedule task is busy...");
                return;
            }
            this.scheduleState = this.SCHEDULE_STATE_BUSY;
            LogCatUtil.debug(TAG, "schedule task start...");
            this.future = NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
                public void run() {
                    LinkStateListener.this.checkInitState();
                }
            }, 30, TimeUnit.SECONDS);
        }
    }

    public void notifyInitOk() {
        LogCatUtil.debug(TAG, "--notifyInitOk--");
        this.initOk = true;
        if (this.future != null) {
            this.future.cancel(true);
            this.scheduleState = this.SCHEDULE_STATE_IDLE;
            LogCatUtil.debug(TAG, "--cancle diagnose task--");
        }
    }

    /* access modifiers changed from: private */
    public void checkInitState() {
        if (this.initOk) {
            LogCatUtil.debug(TAG, "initOk,no need to diagnose");
            if (this.future != null) {
                this.future.cancel(true);
                LogCatUtil.debug(TAG, "--cancle diagnose task--");
            }
            this.scheduleState = this.SCHEDULE_STATE_IDLE;
            return;
        }
        try {
            if (this.diagnoseNum > this.MAXNUM) {
                if (this.firstDiagnoseTime + 3600000 < System.currentTimeMillis()) {
                    this.diagnoseNum = 0;
                    this.firstDiagnoseTime = System.currentTimeMillis();
                } else {
                    LogCatUtil.debug(TAG, "diagnoseNum is out " + this.MAXNUM);
                    this.scheduleState = this.SCHEDULE_STATE_IDLE;
                    this.diagnoseNum++;
                    return;
                }
            }
            LogCatUtil.debug(TAG, "init has not success,start diagnose");
            NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                public void run() {
                    SignalStateHelper.getInstance().reportNetStateInfo();
                    AlipayQosService.getInstance().getQosLevel();
                }
            });
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "checkInitState", ex);
        } finally {
            this.scheduleState = this.SCHEDULE_STATE_IDLE;
            this.diagnoseNum++;
        }
    }
}
