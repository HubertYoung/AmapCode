package com.alipay.mobile.common.transportext.biz.spdy.longlink;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.ConnectionObservable;
import com.alipay.mobile.common.transportext.biz.spdy.ConnectionObservable.ConnectionEvent;
import com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.Ping;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.models.PingRecord;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.io.Closeable;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SpdyLongLinkConnManagerImpl implements SpdyLongLinkConnManager, Observer {
    public static final String FRAMEWORK_ACTIVITY_RESUME = "com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND";
    public static final String FRAMEWORK_ACTIVITY_USERLEAVEHINT = "com.alipay.mobile.framework.USERLEAVEHINT";
    private static final int NETWORK_CHANGE = 1;
    private static final int NETWORK_NO_CHANGE = 2;
    private static final int NON_NETWORK = 0;
    public static final int PING_STATUS_IDLE = 0;
    public static final int PING_STATUS_PING = 2;
    public static final int PING_STATUS_SUBMITTED = 1;
    private static final String TAG = "SpdyLongLinkConnManager";
    private static SpdyLongLinkConnManagerImpl mLongLinkConnManager;
    /* access modifiers changed from: private */
    public boolean mAppVisible = true;
    private AppVisibleReceiver mAppVisibleReceiver;
    private boolean mAttched = false;
    /* access modifiers changed from: private */
    public ReentrantLock mConnLock = new ReentrantLock();
    private Connection mConnection;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> mCurrentPingFuture;
    /* access modifiers changed from: private */
    public PingRecord mCurrentPingRecord;
    private long mLastPingEndTime = -1;
    long mLastReadDataTime = -1;
    long mLastWriteDataTime = -1;
    /* access modifiers changed from: private */
    public boolean mNetworkAvailable = true;
    private NetworkConnectivityReceiver mNetworkConnectivityReceiver;
    /* access modifiers changed from: private */
    public long mPingStartTime = 0;
    /* access modifiers changed from: private */
    public AtomicInteger mPingStatus = new AtomicInteger(0);
    private PingTask mPingTask;
    /* access modifiers changed from: private */
    public final ScheduledThreadPoolExecutor mSchedule = new ScheduledThreadPoolExecutor(1, new PingThreadFactory());
    /* access modifiers changed from: private */
    public boolean mScreenOn = true;
    private ScreenReceiver mScreenReceiver;
    /* access modifiers changed from: private */
    public final ThreadPoolExecutor mSendPingThreadPool = newSingleThreadExecutor("spdy_ping_send_thread");
    private SpdyLongLinkConnHelper mSpdyLongLinkConnHelper;
    private SubmitPingTask mSubmitPingTask;
    /* access modifiers changed from: private */
    public long mSubmitPingTime = 0;
    private ScheduledFuture<?> mWaitPingResponseFuture;

    class AppVisibleReceiver extends BroadcastReceiver {
        public AppVisibleReceiver() {
        }

        public void regiester() {
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND");
                intentFilter.addAction("com.alipay.mobile.framework.USERLEAVEHINT");
                LocalBroadcastManager.getInstance(SpdyLongLinkConnManagerImpl.this.mContext).registerReceiver(this, intentFilter);
            } catch (Throwable e) {
                LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, e);
            }
        }

        public void unregister() {
            try {
                LocalBroadcastManager.getInstance(SpdyLongLinkConnManagerImpl.this.mContext).unregisterReceiver(this);
            } catch (Throwable e) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "unregister exception : " + e.toString());
            }
        }

        public void onReceive(Context context, Intent intent) {
            LoggerFactory.getTraceLogger().info("Monitor", "onReceive:" + getClass().getSimpleName());
            String action = intent.getAction();
            if ("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND".equalsIgnoreCase(action)) {
                if (setAppVisible()) {
                    LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "AppVisibleReceiver#onReceive  收到用户回到应用的广播");
                    if (SpdyLongLinkConnManagerImpl.this.getScreenReceiver().isScreenOn() && SpdyLongLinkConnManagerImpl.this.mCurrentPingFuture == null) {
                        SpdyLongLinkConnManagerImpl.this.getNetworkConnectivityReceiver().register();
                        SpdyLongLinkConnManagerImpl.this.getSpdyLongLinkConnHelper().restore();
                        SpdyLongLinkConnManagerImpl.this.immediatelyStartPingTask();
                    }
                }
            } else if ("com.alipay.mobile.framework.USERLEAVEHINT".equalsIgnoreCase(action) && setAppUnVisible()) {
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "AppVisibleReceiver#onReceive  收到了用户暂时离开应用的广播类型");
                SpdyLongLinkConnManagerImpl.this.cancelPingTask();
                SpdyLongLinkConnManagerImpl.this.getNetworkConnectivityReceiver().unregister();
            }
        }

        private boolean setAppVisible() {
            if (SpdyLongLinkConnManagerImpl.this.mAppVisible) {
                return false;
            }
            synchronized (SpdyLongLinkConnManagerImpl.this) {
                if (SpdyLongLinkConnManagerImpl.this.mAppVisible) {
                    return false;
                }
                SpdyLongLinkConnManagerImpl.this.mAppVisible = true;
                return true;
            }
        }

        private boolean setAppUnVisible() {
            boolean z = false;
            if (SpdyLongLinkConnManagerImpl.this.mAppVisible) {
                synchronized (SpdyLongLinkConnManagerImpl.this) {
                    if (SpdyLongLinkConnManagerImpl.this.mAppVisible) {
                        SpdyLongLinkConnManagerImpl.this.mAppVisible = false;
                        z = true;
                    }
                }
            }
            return z;
        }

        public boolean isAppVisible() {
            return SpdyLongLinkConnManagerImpl.this.mAppVisible;
        }
    }

    class NetworkConnectivityReceiver extends BroadcastReceiver {
        Boolean lastConnected = null;
        int netSubType = -1;
        int netType = -1;
        boolean neverReceive = true;

        public NetworkConnectivityReceiver() {
        }

        public void register() {
            try {
                if (SpdyLongLinkConnManagerImpl.this.mContext != null) {
                    SpdyLongLinkConnManagerImpl.this.mContext.registerReceiver(SpdyLongLinkConnManagerImpl.this.getNetworkConnectivityReceiver(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                }
            } catch (Throwable e) {
                LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, e);
            }
        }

        public void unregister() {
            try {
                SpdyLongLinkConnManagerImpl.this.mContext.unregisterReceiver(this);
            } catch (Throwable e) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "NetworkConnectivityReceiver unregister exception : " + e.toString());
            }
        }

        public void onReceive(Context context, Intent intent) {
            LoggerFactory.getTraceLogger().info("Monitor", "onReceive:" + getClass().getSimpleName());
            if (SpdyLongLinkConnManagerImpl.this.mContext == null) {
                SpdyLongLinkConnManagerImpl.this.mContext = context.getApplicationContext();
            }
            if (!SpdyLongLinkConnManagerImpl.this.isCanWork()) {
                LogCatUtil.info(SpdyLongLinkConnManagerImpl.TAG, "Spdy Long link can't work");
                return;
            }
            int networkState = getNetworkState(context);
            if (networkState == 0) {
                SpdyLongLinkConnManagerImpl.this.asyncCloseConnection();
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "NetworkConnectivityReceiver#onReceive 网络不可用");
                SpdyLongLinkConnManagerImpl.this.cancelPingTask();
            } else if (networkState == 1) {
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "NetworkConnectivityReceiver#onReceive 网络变化");
                if (this.neverReceive) {
                    LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "NetworkConnectivityReceiver#onReceive Ignore first receive!");
                    this.neverReceive = false;
                } else if (SpdyLongLinkConnManagerImpl.this.getScreenReceiver().isScreenOn() && SpdyLongLinkConnManagerImpl.this.getAppVisibleReceiver().isAppVisible()) {
                    SpdyLongLinkConnManagerImpl.this.asyncCloseConnection();
                    SpdyLongLinkConnManagerImpl.this.cancelPingTask();
                    SpdyLongLinkConnManagerImpl.this.asynConnect();
                    if (SpdyLongLinkConnManagerImpl.this.mCurrentPingFuture == null) {
                        SpdyLongLinkConnManagerImpl.this.getSpdyLongLinkConnHelper().restore();
                        LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "NetworkConnectivityReceiver#onReceive immediatelyStartPingTask()");
                    }
                }
            }
        }

        private int getNetworkState(Context context) {
            NetworkInfo activeNetworkInfo = NetworkUtils.getActiveNetworkInfo(context);
            LogCatUtil.info(SpdyLongLinkConnManagerImpl.TAG, "\n ============================================================== ");
            if (activeNetworkInfo == null) {
                Log.i(SpdyLongLinkConnManagerImpl.TAG, "当前无网络!");
                this.lastConnected = Boolean.valueOf(false);
                return 0;
            }
            boolean available = activeNetworkInfo.isAvailable();
            boolean connected = activeNetworkInfo.isConnected();
            int type = activeNetworkInfo.getType();
            int subType = activeNetworkInfo.getSubtype();
            String extraInfo = activeNetworkInfo.getExtraInfo();
            if (this.netType == -1 || this.netSubType == -1 || this.lastConnected == null) {
                LogCatUtil.info(SpdyLongLinkConnManagerImpl.TAG, " New contivity broadcast！");
            } else if (this.lastConnected.booleanValue() == connected && this.netType == type && this.netSubType == subType) {
                LogCatUtil.info(SpdyLongLinkConnManagerImpl.TAG, " Old contivity broadcast！");
                return 2;
            }
            this.lastConnected = Boolean.valueOf(connected);
            this.netType = type;
            this.netSubType = subType;
            LogCatUtil.info(SpdyLongLinkConnManagerImpl.TAG, " type=[" + type + "] subType=[" + subType + "]  available=[" + available + "] connected=[" + connected + "] detailedState=[" + activeNetworkInfo.getDetailedState() + "] extraInfo=[" + extraInfo + "]");
            LogCatUtil.info(SpdyLongLinkConnManagerImpl.TAG, " activeNetworkInfo hashcode=" + activeNetworkInfo.hashCode() + "  activeNetworkInfo = [" + activeNetworkInfo.toString() + "]\n\n\n");
            if (this.lastConnected.booleanValue()) {
                return 1;
            }
            return 0;
        }

        private boolean setNetworkAvailable() {
            if (SpdyLongLinkConnManagerImpl.this.mNetworkAvailable) {
                return false;
            }
            synchronized (SpdyLongLinkConnManagerImpl.this) {
                if (SpdyLongLinkConnManagerImpl.this.mNetworkAvailable) {
                    return false;
                }
                SpdyLongLinkConnManagerImpl.this.mNetworkAvailable = true;
                return true;
            }
        }

        private boolean setNetworkInvalid() {
            boolean z = false;
            if (SpdyLongLinkConnManagerImpl.this.mNetworkAvailable) {
                synchronized (SpdyLongLinkConnManagerImpl.this) {
                    if (SpdyLongLinkConnManagerImpl.this.mNetworkAvailable) {
                        SpdyLongLinkConnManagerImpl.this.mNetworkAvailable = false;
                        z = true;
                    }
                }
            }
            return z;
        }
    }

    class PingTask implements Runnable {
        PingTask() {
        }

        public void run() {
            LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "PingTask#run start");
            if (SpdyLongLinkConnManagerImpl.this.mSubmitPingTime < SpdyLongLinkConnManagerImpl.this.mPingStartTime && SpdyLongLinkConnManagerImpl.this.mPingStartTime - SpdyLongLinkConnManagerImpl.this.mSubmitPingTime > 5) {
                SpdyLongLinkConnManagerImpl.this.cancelPingTask();
                SpdyLongLinkConnManagerImpl.this.startPing();
            } else if (!NetworkUtils.isNetworkAvailable(SpdyLongLinkConnManagerImpl.this.mContext)) {
                LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, (String) "PingTask#run Network invalid");
                SpdyLongLinkConnManagerImpl.this.cancelPingTask();
            } else if (!SpdyLongLinkConnManagerImpl.this.getSpdyLongLinkConnHelper().hasLiveSpdyConnection()) {
                LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, (String) "PingTask#run hasLiveSpdyConnection==false。");
                SpdyLongLinkConnManagerImpl.this.cancelPingTask();
                SpdyLongLinkConnManagerImpl.this.getSpdyLongLinkConnHelper().submitConnectTask();
            } else {
                try {
                    synchronized (this) {
                        if (SpdyLongLinkConnManagerImpl.this.mPingStatus.get() == 1) {
                            SpdyLongLinkConnManagerImpl.this.setPingStatusPing();
                            SpdyPingRunnable spdyPingRunnable = new SpdyPingRunnable();
                            FutureTask pingFutureTask = new FutureTask(spdyPingRunnable);
                            spdyPingRunnable.setSendPingThreadFuture(pingFutureTask);
                            SpdyLongLinkConnManagerImpl.this.mSendPingThreadPool.execute(pingFutureTask);
                            pingFutureTask.get(ExtTransportStrategy.getPingTimeOut(), TimeUnit.MILLISECONDS);
                        }
                    }
                } catch (InterruptedException e) {
                    LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, "PingTask Exception:" + e.toString());
                } catch (Throwable e2) {
                    LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, "PingTask Exception:" + e2.toString());
                    reconnect();
                }
            }
        }

        private void reconnect() {
            try {
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "Ping异常，暂停2秒");
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "reconnect exception : " + e1.toString());
            }
            SpdyLongLinkConnManagerImpl.this.closeConnection();
            SpdyLongLinkConnManagerImpl.this.getSpdyLongLinkConnHelper().submitConnectTask();
        }
    }

    class PingThreadFactory implements ThreadFactory {
        PingThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("spdy_ping_schedule_thread");
            if (SpdyLongLinkConnManagerImpl.this.mSchedule != null) {
                stringBuilder.append(new StringBuilder(MetaRecord.LOG_SEPARATOR).append(SpdyLongLinkConnManagerImpl.this.mSchedule.getActiveCount() + 1).toString());
            }
            return new Thread(r, stringBuilder.toString());
        }
    }

    class ScreenReceiver extends BroadcastReceiver {
        public ScreenReceiver() {
        }

        public void regiester() {
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                SpdyLongLinkConnManagerImpl.this.mContext.registerReceiver(this, intentFilter);
            } catch (Throwable e) {
                LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, e);
            }
        }

        public void unregister() {
            try {
                SpdyLongLinkConnManagerImpl.this.mContext.unregisterReceiver(this);
            } catch (Throwable e) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "unregister exception : " + e.toString());
            }
        }

        public void onReceive(Context context, Intent intent) {
            LoggerFactory.getTraceLogger().info("Monitor", "onReceive:" + getClass().getSimpleName());
            String action = intent.getAction();
            if (TextUtils.equals("android.intent.action.SCREEN_OFF", action)) {
                if (setScreenOff()) {
                    LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ScreenReceiver#onReceive  Intent.ACTION_SCREEN_OFF");
                    SpdyLongLinkConnManagerImpl.this.cancelPingTask();
                    SpdyLongLinkConnManagerImpl.this.getNetworkConnectivityReceiver().unregister();
                }
            } else if (TextUtils.equals("android.intent.action.SCREEN_ON", action) && setScreenOn()) {
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ScreenReceiver#onReceive  Intent.ACTION_SCREEN_ON");
                if (SpdyLongLinkConnManagerImpl.this.getAppVisibleReceiver().isAppVisible() && SpdyLongLinkConnManagerImpl.this.mCurrentPingFuture == null) {
                    SpdyLongLinkConnManagerImpl.this.getNetworkConnectivityReceiver().register();
                    SpdyLongLinkConnManagerImpl.this.getSpdyLongLinkConnHelper().restore();
                    SpdyLongLinkConnManagerImpl.this.immediatelyStartPingTask();
                }
            }
        }

        private boolean setScreenOn() {
            if (SpdyLongLinkConnManagerImpl.this.mScreenOn) {
                return false;
            }
            synchronized (SpdyLongLinkConnManagerImpl.this) {
                if (SpdyLongLinkConnManagerImpl.this.mScreenOn) {
                    return false;
                }
                SpdyLongLinkConnManagerImpl.this.mScreenOn = true;
                return true;
            }
        }

        private boolean setScreenOff() {
            boolean z = false;
            if (SpdyLongLinkConnManagerImpl.this.mScreenOn) {
                synchronized (SpdyLongLinkConnManagerImpl.this) {
                    if (SpdyLongLinkConnManagerImpl.this.mScreenOn) {
                        SpdyLongLinkConnManagerImpl.this.mScreenOn = false;
                        z = true;
                    }
                }
            }
            return z;
        }

        public boolean isScreenOn() {
            return SpdyLongLinkConnManagerImpl.this.mScreenOn;
        }
    }

    class SpdyLongLinkConnHelper {
        private int mInterval = 0;
        /* access modifiers changed from: private */
        public volatile int mReconnCount = 0;

        class ConnTimeoutCheckTask implements Runnable {
            private ScheduledFuture<?> mConnFuture;

            public ConnTimeoutCheckTask(ScheduledFuture<?> connFuture) {
                this.mConnFuture = connFuture;
            }

            public void run() {
                try {
                    this.mConnFuture.cancel(true);
                    this.mConnFuture = null;
                } catch (Throwable e) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "ConnTimeoutCheckTask#run exception : " + e.toString());
                }
            }
        }

        class ConnectTask implements Runnable {
            private ScheduledFuture<?> mConnTimeoutFuture;

            public ConnectTask() {
            }

            public void run() {
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run start");
                if (!NetworkUtils.isNetworkAvailable(SpdyLongLinkConnManagerImpl.this.mContext)) {
                    LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, (String) "ConnectTask#run network invalid. program return.");
                } else if (!SpdyLongLinkConnManagerImpl.this.isCanWork()) {
                    LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run. isCanWork==false.");
                } else {
                    try {
                        if (SpdyLongLinkConnHelper.this.hasLiveSpdyConnection()) {
                            SpdyLongLinkConnManagerImpl.this.notifyConnected();
                            LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run 连接已经存在，无需建连!! ");
                            return;
                        }
                        boolean isConnected = innerConnect();
                        if (MiscUtils.isDebugger(SpdyLongLinkConnManagerImpl.this.mContext)) {
                            LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run.  reconnect count = [" + SpdyLongLinkConnHelper.this.mReconnCount + "] ");
                        }
                        if (this.mConnTimeoutFuture != null) {
                            this.mConnTimeoutFuture.cancel(true);
                            this.mConnTimeoutFuture = null;
                        }
                        if (isConnected) {
                            LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run 重连成功，准备发起心跳。");
                            SpdyLongLinkConnManagerImpl.this.immediatelyStartPingTask();
                            SpdyLongLinkConnHelper.this.restore();
                            return;
                        }
                        LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run 重连失败.");
                        if (!NetworkUtils.isNetworkAvailable(SpdyLongLinkConnManagerImpl.this.mContext)) {
                            LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run 网络无法连接，不再发起重连.");
                        } else if (!SpdyLongLinkConnManagerImpl.this.getAppVisibleReceiver().isAppVisible() || !SpdyLongLinkConnManagerImpl.this.getScreenReceiver().isScreenOn() || !SpdyLongLinkConnManagerImpl.this.isCanWork()) {
                            LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run 手机灭屏或者支付宝压后台，不再发起重连.");
                        } else {
                            LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run 重新发起重连请求.");
                            SpdyLongLinkConnManagerImpl.this.getSpdyLongLinkConnHelper().submitConnectTask();
                            return;
                        }
                        SpdyLongLinkConnHelper.this.restore();
                        SpdyLongLinkConnManagerImpl.this.cancelPingTask();
                    } catch (Throwable e) {
                        LogCatUtil.error(SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run Exception ", e);
                    }
                }
            }

            /* JADX INFO: finally extract failed */
            private boolean innerConnect() {
                SpdyLongLinkConnManagerImpl.this.mConnLock.lock();
                try {
                    if (SpdyLongLinkConnHelper.this.hasLiveSpdyConnection()) {
                        LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "innerConnect。hasLiveSpdyConnection is true.");
                        SpdyLongLinkConnManagerImpl.this.mConnLock.unlock();
                        return true;
                    }
                    SpdyLongLinkConnManagerImpl.this.closeConnection();
                    if (((long) SpdyLongLinkConnHelper.this.mReconnCount) >= ExtTransportStrategy.getReconnectionMaxCount()) {
                        if (MiscUtils.isDebugger(SpdyLongLinkConnManagerImpl.this.mContext)) {
                            LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run 重连次数超限。 mReconnCount > " + ExtTransportStrategy.getReconnectionMaxCount() + ", return.");
                        }
                        SpdyLongLinkConnManagerImpl.this.cancelPingTask();
                        SpdyLongLinkConnManagerImpl.this.mConnLock.unlock();
                        return false;
                    }
                    LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "start connect !");
                    SpdyLongLinkConnHelper.this.mReconnCount = SpdyLongLinkConnHelper.this.mReconnCount + 1;
                    boolean connect = SpdyLongLinkConnHelper.this.connect();
                    SpdyLongLinkConnManagerImpl.this.mConnLock.unlock();
                    return connect;
                } catch (Throwable th) {
                    SpdyLongLinkConnManagerImpl.this.mConnLock.unlock();
                    throw th;
                }
            }

            public void setConnTimeoutFuture(ScheduledFuture<?> connTimeoutFuture) {
                this.mConnTimeoutFuture = connTimeoutFuture;
            }
        }

        SpdyLongLinkConnHelper() {
        }

        public void clearReconnCount() {
            this.mReconnCount = 0;
        }

        public boolean isMoreThanMaxRetryCount() {
            if (((long) this.mReconnCount) >= ExtTransportStrategy.getReconnectionMaxCount()) {
                return true;
            }
            return false;
        }

        public void submitConnectTask() {
            LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "SpdyLongLinkConnHelper#enqueueConnect  start");
            try {
                if (!NetworkUtils.isNetworkAvailable(SpdyLongLinkConnManagerImpl.this.mContext)) {
                    LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, (String) "SpdyLongLinkConnHelper#enqueueConnect   network invalid. program return.");
                } else if (!SpdyLongLinkConnManagerImpl.this.isCanWork()) {
                    LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "SpdyLongLinkConnHelper#enqueueConnect. isCanWork == false ");
                } else if (((long) this.mReconnCount) >= ExtTransportStrategy.getReconnectionMaxCount()) {
                    LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, "ConnectTask#run 重连次数超限。 mReconnCount > " + ExtTransportStrategy.getReconnectionMaxCount() + ", return.");
                } else {
                    if (this.mReconnCount <= 5) {
                        this.mInterval = this.mReconnCount;
                    } else if (this.mReconnCount > 5 && ((long) this.mReconnCount) <= ExtTransportStrategy.getReconnectionMaxCount()) {
                        this.mInterval = 5;
                    }
                    LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "SpdyLongLinkConnHelper#enqueueConnect .  mReconnCount=[" + this.mReconnCount + "] mInterval=[" + this.mInterval + "s]");
                    ConnectTask connectTask = new ConnectTask();
                    connectTask.setConnTimeoutFuture(SpdyLongLinkConnManagerImpl.this.mSchedule.schedule(new ConnTimeoutCheckTask(SpdyLongLinkConnManagerImpl.this.mSchedule.schedule(connectTask, (long) this.mInterval, TimeUnit.SECONDS)), 10, TimeUnit.SECONDS));
                }
            } catch (Throwable e) {
                LogCatUtil.error(SpdyLongLinkConnManagerImpl.TAG, "SpdyLongLinkConnHelper#enqueueConnect。  stop submit enqueueConnect task.", e);
            }
        }

        public boolean connect() {
            try {
                if (!SpdyLongLinkConnManagerImpl.this.isCanWork()) {
                    return false;
                }
                AndroidSpdyHttpClient.newInstance(SpdyLongLinkConnManagerImpl.this.mContext).connect();
                if (hasLiveSpdyConnection()) {
                    return true;
                }
                return false;
            } catch (Throwable e) {
                LogCatUtil.error(SpdyLongLinkConnManagerImpl.TAG, "SpdyLongLinkConnHelper connect fail.", e);
            }
        }

        public boolean hasLiveSpdyConnection() {
            Connection connection = SpdyLongLinkConnManagerImpl.this.getConnection();
            if (connection == null || !connection.isConnected() || !connection.isAlive()) {
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "hasLiveSpdyConnection. connection disconnect.");
            } else {
                SpdyConnection spdyConnection = connection.getSpdyConnection();
                if (spdyConnection != null && !spdyConnection.isShutdown()) {
                    return true;
                }
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "hasLiveSpdyConnection.  spdyConnection is null or is shutdown!");
            }
            return false;
        }

        public void restore() {
            this.mInterval = 0;
            this.mReconnCount = 0;
        }
    }

    class SpdyPingRunnable implements Callable<PingRecord> {
        private Future<?> sendPingThreadFuture;
        private ScheduledFuture<?> startWaitPingResponseTask;

        SpdyPingRunnable() {
        }

        private Ping ping(Connection connection) {
            try {
                return connection.getSpdyConnection().ping();
            } catch (IOException e) {
                LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, (Throwable) e);
                if (this.startWaitPingResponseTask != null) {
                    this.startWaitPingResponseTask.cancel(true);
                    this.startWaitPingResponseTask = null;
                }
                throw e;
            }
        }

        public void setSendPingThreadFuture(Future<?> sendPingThreadFuture2) {
            this.sendPingThreadFuture = sendPingThreadFuture2;
        }

        public PingRecord call() {
            SpdyLongLinkConnManagerImpl.this.mCurrentPingRecord = new PingRecord();
            SpdyLongLinkConnManagerImpl.this.mCurrentPingRecord.pingTime = System.currentTimeMillis();
            if (this.sendPingThreadFuture != null) {
                SpdyLongLinkConnManagerImpl.this.mCurrentPingRecord.sendPingThreadFuture = this.sendPingThreadFuture;
            }
            Connection connection = SpdyLongLinkConnManagerImpl.this.getConnection();
            if (connection == null || connection.getSpdyConnection() == null || !connection.isAlive()) {
                throw new IllegalStateException("SpdyPingRunnable exec fail. because connection is null.");
            }
            SpdyConnection spdyConnection = connection.getSpdyConnection();
            if (spdyConnection == null || spdyConnection.isShutdown()) {
                throw new IllegalStateException("SpdyPingRunnable exec fail. because spdyConnection is null or shutdown!");
            }
            this.startWaitPingResponseTask = SpdyLongLinkConnManagerImpl.this.startWaitPingResponseTask(SpdyLongLinkConnManagerImpl.this.mCurrentPingRecord);
            Ping ping = ping(connection);
            SpdyLongLinkConnManagerImpl.this.pingLogForFinish(ping);
            SpdyLongLinkConnManagerImpl.this.mCurrentPingRecord.ping = ping;
            return SpdyLongLinkConnManagerImpl.this.mCurrentPingRecord;
        }
    }

    class SubmitPingTask implements Runnable {
        SubmitPingTask() {
        }

        public void run() {
            run(false);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
            com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.access$002(r7.this$0, com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.access$100(r7.this$0));
            r1 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0052, code lost:
            if (r8 != false) goto L_0x0066;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
            r1 = com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy.getPingInterval(com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.access$300(r7.this$0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0062, code lost:
            if (r1 > 0) goto L_0x0066;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0064, code lost:
            r1 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0066, code lost:
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.TAG, "SubmitPingTask#run. submit schedule, interval:" + r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.access$1002(r7.this$0, com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.access$700(r7.this$0).schedule(com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.access$1100(r7.this$0), r1, java.util.concurrent.TimeUnit.MILLISECONDS));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b5, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b6, code lost:
            com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.access$200(r7.this$0);
            com.alipay.mobile.common.transport.utils.LogCatUtil.error((java.lang.String) com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.TAG, r0);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run(boolean r8) {
            /*
                r7 = this;
                java.lang.String r3 = "SpdyLongLinkConnManager"
                java.lang.String r4 = "SubmitPingTask#run. start"
                com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r3, r4)
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r3 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this
                android.content.Context r3 = r3.mContext
                boolean r3 = com.alipay.mobile.common.transport.utils.NetworkUtils.isNetworkAvailable(r3)
                if (r3 != 0) goto L_0x001b
                java.lang.String r3 = "SpdyLongLinkConnManager"
                java.lang.String r4 = "SubmitPingTask#run. Network invalid !"
                com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r3, r4)
            L_0x001a:
                return
            L_0x001b:
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r3 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this
                boolean r3 = r3.isCanWork()
                if (r3 != 0) goto L_0x002b
                java.lang.String r3 = "SpdyLongLinkConnManager"
                java.lang.String r4 = "SubmitPingTask#run. isCanWork == false!"
                com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r3, r4)
                goto L_0x001a
            L_0x002b:
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r4 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this
                monitor-enter(r4)
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r3 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this     // Catch:{ all -> 0x003c }
                java.util.concurrent.atomic.AtomicInteger r3 = r3.mPingStatus     // Catch:{ all -> 0x003c }
                int r3 = r3.get()     // Catch:{ all -> 0x003c }
                if (r3 == 0) goto L_0x003f
                monitor-exit(r4)     // Catch:{ all -> 0x003c }
                goto L_0x001a
            L_0x003c:
                r3 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x003c }
                throw r3
            L_0x003f:
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r3 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this     // Catch:{ all -> 0x003c }
                r3.setPingStatusSubmitted()     // Catch:{ all -> 0x003c }
                monitor-exit(r4)     // Catch:{ all -> 0x003c }
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r3 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r4 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this
                long r4 = r4.mPingStartTime
                r3.mSubmitPingTime = r4
                r1 = 1
                if (r8 != 0) goto L_0x0066
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r3 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this
                android.content.Context r3 = r3.mContext
                long r1 = com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy.getPingInterval(r3)
                r3 = 0
                int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r3 > 0) goto L_0x0066
                r1 = 1
            L_0x0066:
                java.lang.String r3 = "SpdyLongLinkConnManager"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                java.lang.String r5 = "SubmitPingTask#run. submit schedule, interval:"
                r4.<init>(r5)
                java.lang.StringBuilder r4 = r4.append(r1)
                java.lang.String r4 = r4.toString()
                com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r3, r4)
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r3 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this     // Catch:{ Throwable -> 0x00b5 }
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r4 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this     // Catch:{ Throwable -> 0x00b5 }
                java.util.concurrent.ScheduledThreadPoolExecutor r4 = r4.mSchedule     // Catch:{ Throwable -> 0x00b5 }
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r5 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this     // Catch:{ Throwable -> 0x00b5 }
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl$PingTask r5 = r5.getPingTask()     // Catch:{ Throwable -> 0x00b5 }
                java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x00b5 }
                java.util.concurrent.ScheduledFuture r4 = r4.schedule(r5, r1, r6)     // Catch:{ Throwable -> 0x00b5 }
                r3.mCurrentPingFuture = r4     // Catch:{ Throwable -> 0x00b5 }
            L_0x0091:
                java.lang.String r3 = "SpdyLongLinkConnManager"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                java.lang.String r5 = "SubmitPingTask#run.  scheduling  heartbeat task ,  interval="
                r4.<init>(r5)
                java.lang.StringBuilder r4 = r4.append(r1)
                java.lang.String r5 = ", startTime="
                java.lang.StringBuilder r4 = r4.append(r5)
                long r5 = java.lang.System.currentTimeMillis()
                java.lang.StringBuilder r4 = r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r3, r4)
                goto L_0x001a
            L_0x00b5:
                r0 = move-exception
                com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl r3 = com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.this
                r3.cancelPingTask()
                java.lang.String r3 = "SpdyLongLinkConnManager"
                com.alipay.mobile.common.transport.utils.LogCatUtil.error(r3, r0)
                goto L_0x0091
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl.SubmitPingTask.run(boolean):void");
        }
    }

    class WaitPingResponseTask implements Runnable {
        private PingRecord mPingRecord;

        public WaitPingResponseTask(PingRecord pingRecord) {
            this.mPingRecord = pingRecord;
        }

        public void run() {
            LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "WaitPingResponseTask#run.  start");
            stopThread();
            if (this.mPingRecord != SpdyLongLinkConnManagerImpl.this.mCurrentPingRecord) {
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "WaitPingResponseTask#run.  ping任务已经发生变化。");
            } else if (!SpdyLongLinkConnManagerImpl.this.isCanWork()) {
                SpdyLongLinkConnManagerImpl.this.cancelPingTask();
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "WaitPingResponseTask#run. isCanWork==false");
            } else if (this.mPingRecord.ping == null || !this.mPingRecord.responsed) {
                LogCatUtil.debug(SpdyLongLinkConnManagerImpl.TAG, "WaitPingResponseTask#run.  responsed=false, execute cancelPingTask().");
                SpdyLongLinkConnManagerImpl.this.closeConnection();
                reconnect();
            }
        }

        private void stopThread() {
            if (this.mPingRecord.sendPingThreadFuture != null && !this.mPingRecord.sendPingThreadFuture.isDone()) {
                try {
                    this.mPingRecord.sendPingThreadFuture.cancel(true);
                } catch (Throwable e) {
                    LogCatUtil.warn((String) SpdyLongLinkConnManagerImpl.TAG, e);
                }
            }
        }

        private void reconnect() {
            SpdyLongLinkConnManagerImpl.this.cancelPingTask();
            SpdyLongLinkConnManagerImpl.this.setPingStatusIdle();
            SpdyLongLinkConnManagerImpl.this.getSpdyLongLinkConnHelper().submitConnectTask();
        }
    }

    @TargetApi(9)
    public SpdyLongLinkConnManagerImpl() {
        try {
            this.mSchedule.setCorePoolSize(1);
            this.mSchedule.setMaximumPoolSize(1);
            this.mSchedule.setKeepAliveTime(60, TimeUnit.SECONDS);
            this.mSchedule.allowCoreThreadTimeOut(true);
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, e);
        }
    }

    public void attch(Context context) {
        if (!MiscUtils.isPushProcess(context)) {
            synchronized (this) {
                if (!this.mAttched) {
                    this.mAttched = true;
                    this.mContext = context;
                    this.mScreenReceiver = getScreenReceiver();
                    this.mAppVisibleReceiver = getAppVisibleReceiver();
                    this.mNetworkConnectivityReceiver = getNetworkConnectivityReceiver();
                    ConnectionObservable.getInstance().addObserver(this);
                }
            }
        }
    }

    public synchronized ScreenReceiver getScreenReceiver() {
        if (this.mScreenReceiver == null) {
            this.mScreenReceiver = new ScreenReceiver();
            this.mScreenReceiver.regiester();
        }
        return this.mScreenReceiver;
    }

    public synchronized AppVisibleReceiver getAppVisibleReceiver() {
        if (this.mAppVisibleReceiver == null) {
            this.mAppVisibleReceiver = new AppVisibleReceiver();
            this.mAppVisibleReceiver.regiester();
        }
        return this.mAppVisibleReceiver;
    }

    public synchronized NetworkConnectivityReceiver getNetworkConnectivityReceiver() {
        NetworkConnectivityReceiver networkConnectivityReceiver;
        try {
            if (this.mNetworkConnectivityReceiver != null) {
                networkConnectivityReceiver = this.mNetworkConnectivityReceiver;
            } else {
                this.mNetworkConnectivityReceiver = new NetworkConnectivityReceiver();
                this.mNetworkConnectivityReceiver.register();
                networkConnectivityReceiver = this.mNetworkConnectivityReceiver;
            }
        }
        return networkConnectivityReceiver;
    }

    public void resetPingStartTime() {
        this.mPingStartTime = System.currentTimeMillis();
    }

    public static final SpdyLongLinkConnManagerImpl getInstance() {
        if (mLongLinkConnManager != null) {
            return mLongLinkConnManager;
        }
        return newInstance();
    }

    public Future<?> justPing() {
        return this.mSendPingThreadPool.submit(new SpdyPingRunnable());
    }

    public void startPing() {
        if (!isCanWork()) {
            LogCatUtil.debug(TAG, "startPing.   isCanWork==false");
            return;
        }
        LogCatUtil.debug(TAG, "startPing start.  ");
        this.mSchedule.submit(getSubmitPingTask());
    }

    public void notifyNetworkActive(int ioType) {
        if (ioType == 0) {
            this.mLastReadDataTime = System.currentTimeMillis();
        } else if (ioType == 1) {
            this.mLastWriteDataTime = System.currentTimeMillis();
        }
        if (!isCanWork()) {
            LogCatUtil.debug(TAG, "notifyNetworkActive.   isCanWork == false.");
            return;
        }
        resetPingStartTime();
        if (this.mPingStatus.get() == 0) {
            startPing();
        }
    }

    public void notifyPingResponse(Ping ping) {
        LogCatUtil.debug(TAG, "notifyPingResponse start.");
        this.mLastPingEndTime = System.currentTimeMillis();
        LongLinkTransportManager.getInstance().notifyPingResponse();
        if (this.mCurrentPingRecord == null) {
            LogCatUtil.debug(TAG, "notifyPingResponse. 当前ping任务已经不存在了。");
            return;
        }
        this.mCurrentPingRecord.responsed = true;
        if (this.mWaitPingResponseFuture != null) {
            this.mWaitPingResponseFuture.cancel(true);
        }
        LogCatUtil.debug(TAG, "notifyPingResponse finish.");
        restartPing();
    }

    private static final synchronized SpdyLongLinkConnManagerImpl newInstance() {
        SpdyLongLinkConnManagerImpl spdyLongLinkConnManagerImpl;
        synchronized (SpdyLongLinkConnManagerImpl.class) {
            if (mLongLinkConnManager != null) {
                spdyLongLinkConnManagerImpl = mLongLinkConnManager;
            } else {
                spdyLongLinkConnManagerImpl = new SpdyLongLinkConnManagerImpl();
                mLongLinkConnManager = spdyLongLinkConnManagerImpl;
            }
        }
        return spdyLongLinkConnManagerImpl;
    }

    /* access modifiers changed from: private */
    public PingTask getPingTask() {
        if (this.mPingTask == null) {
            this.mPingTask = new PingTask();
        }
        return this.mPingTask;
    }

    /* access modifiers changed from: private */
    public void cancelPingTask() {
        LogCatUtil.debug(TAG, "cancelPingTask start");
        try {
            if (this.mCurrentPingFuture != null) {
                this.mCurrentPingFuture.cancel(true);
                this.mCurrentPingFuture = null;
            }
        } catch (Throwable e) {
            LogCatUtil.error((String) TAG, e);
        }
        this.mSubmitPingTime = 0;
        setPingStatusIdle();
    }

    /* access modifiers changed from: private */
    public void setPingStatusIdle() {
        LogCatUtil.debug(TAG, "ping状态：ping任务进入“空闲”状态。");
        this.mPingStatus.set(0);
    }

    /* access modifiers changed from: private */
    public void setPingStatusPing() {
        LogCatUtil.debug(TAG, "ping状态：ping任务进入“执行中”状态。");
        this.mPingStatus.set(2);
    }

    /* access modifiers changed from: private */
    public void setPingStatusSubmitted() {
        LogCatUtil.debug(TAG, "ping状态：ping任务进入”等待调度“状态。");
        this.mPingStatus.set(1);
    }

    private SubmitPingTask getSubmitPingTask() {
        if (this.mSubmitPingTask == null) {
            this.mSubmitPingTask = new SubmitPingTask();
        }
        return this.mSubmitPingTask;
    }

    /* access modifiers changed from: private */
    public ScheduledFuture<?> startWaitPingResponseTask(PingRecord pingRecord) {
        if (this.mWaitPingResponseFuture != null) {
            this.mWaitPingResponseFuture.cancel(true);
            this.mWaitPingResponseFuture = null;
        }
        this.mWaitPingResponseFuture = this.mSchedule.schedule(new WaitPingResponseTask(pingRecord), ExtTransportStrategy.getPingTimeOut(), TimeUnit.MILLISECONDS);
        return this.mWaitPingResponseFuture;
    }

    public SpdyLongLinkConnHelper getSpdyLongLinkConnHelper() {
        if (this.mSpdyLongLinkConnHelper == null) {
            this.mSpdyLongLinkConnHelper = new SpdyLongLinkConnHelper();
        }
        return this.mSpdyLongLinkConnHelper;
    }

    /* access modifiers changed from: private */
    public boolean isCanWork() {
        if (!this.mAttched) {
            return false;
        }
        if (this.mContext == null) {
            LogCatUtil.debug(TAG, "isCanWork  isPushProcess==true, return false.");
            return false;
        } else if (!NetworkTunnelStrategy.getInstance().isCanUseSpdy()) {
            LogCatUtil.debug(TAG, "isCanWork  isUseExtTransport is false, return false;");
            return false;
        } else if (!NetworkTunnelStrategy.getInstance().isCanUseSpdyLongLink()) {
            LogCatUtil.debug(TAG, "isCanWork  isCanUseSpdyLongLink() is false, return false;");
            return false;
        } else if (this.mScreenReceiver == null || !this.mScreenReceiver.isScreenOn()) {
            LogCatUtil.debug(TAG, "isCanWork  isScreenOn==false, return false;");
            return false;
        } else if (getAppVisibleReceiver().isAppVisible()) {
            return true;
        } else {
            LogCatUtil.debug(TAG, "isCanWork  isAppVisible==false, return false.");
            return false;
        }
    }

    private void restartPing() {
        resetPingStartTime();
        setPingStatusIdle();
        startPing();
    }

    /* access modifiers changed from: private */
    public void immediatelyStartPingTask() {
        resetPingStartTime();
        cancelPingTask();
        getSubmitPingTask().run(true);
    }

    private void notifyDisconnected() {
        try {
            LogCatUtil.debug(TAG, "notifyDisconnected");
            LongLinkTransportManager.getInstance().onDisconnected();
            asynConnect();
        } catch (Throwable e) {
            LogCatUtil.error((String) TAG, e);
        }
    }

    private void notifyConnecting() {
        try {
            LogCatUtil.debug(TAG, "notifyConnecting");
            LongLinkTransportManager.getInstance().onConnecting();
        } catch (Throwable e) {
            LogCatUtil.error((String) TAG, e);
        }
    }

    /* access modifiers changed from: private */
    public void notifyConnected() {
        try {
            LogCatUtil.debug(TAG, "notifyConnected");
            LongLinkTransportManager.getInstance().onConnected();
            getSpdyLongLinkConnHelper().restore();
        } catch (Throwable e) {
            LogCatUtil.error((String) TAG, e);
        }
    }

    private ThreadPoolExecutor newSingleThreadExecutor(final String threadName) {
        return new ThreadPoolExecutor(0, 1, 20, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, threadName);
                thread.setPriority(1);
                return thread;
            }
        });
    }

    /* access modifiers changed from: private */
    public void pingLogForFinish(Ping ping) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("PingTask#run. ping send finished. ");
        logBuilder.append(" send finish time = " + System.currentTimeMillis());
        LogCatUtil.debug(TAG, logBuilder.toString());
    }

    public void update(Observable observable, Object data) {
        if (data == null) {
            LogCatUtil.debug(TAG, "update.  data is null");
        } else if (!data.getClass().isAssignableFrom(ConnectionEvent.class)) {
            LogCatUtil.debug(TAG, "update.  " + data.getClass().getName() + " isAssignableFrom " + ConnectionEvent.class.getName() + " is  false .");
        } else {
            ConnectionEvent connEvent = (ConnectionEvent) data;
            Connection connection = connEvent.connection;
            if (connection == null) {
                LogCatUtil.debug(TAG, "update. connection is null");
                return;
            }
            String uriHost = connection.getRoute().getAddress().getUriHost();
            if (!ExtTransportStrategy.getSpdyUrl(this.mContext).contains(uriHost)) {
                LogCatUtil.printInfo(TAG, uriHost + " not spdy long link url!");
            } else if (connEvent.event == 1) {
                if (this.mConnection == connection) {
                    this.mConnection = null;
                    notifyDisconnected();
                }
            } else if (connEvent.event == 0) {
                this.mConnection = connection;
                notifyConnected();
            } else if (connEvent.event == 2) {
                notifyConnecting();
            }
        }
    }

    public boolean connect() {
        return getSpdyLongLinkConnHelper().connect();
    }

    public void asynConnect() {
        getSpdyLongLinkConnHelper().submitConnectTask();
    }

    public void closeConnection() {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                Util.closeQuietly((Closeable) connection);
                LogCatUtil.info(TAG, "关闭spdy长连接通道！");
            }
        } catch (Throwable e) {
            LogCatUtil.error(TAG, "关闭spdy长连接通道！", e);
        }
    }

    public Connection getConnection() {
        return this.mConnection;
    }

    public boolean isNetworkActive() {
        if (System.currentTimeMillis() - this.mLastReadDataTime <= 10000) {
            LogCatUtil.debug(TAG, "isNetworkActive == true!");
            return true;
        } else if (System.currentTimeMillis() - this.mLastPingEndTime > 10000) {
            return false;
        } else {
            LogCatUtil.debug(TAG, "isNetworkActive == true!");
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void asyncCloseConnection() {
        NetworkAsyncTaskExecutor.execute(new Runnable() {
            public void run() {
                SpdyLongLinkConnManagerImpl.this.closeConnection();
            }
        });
    }

    public int hashCode() {
        return super.hashCode();
    }
}
