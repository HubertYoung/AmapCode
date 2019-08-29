package com.alipay.mobile.common.fgbg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.fgbg.FgBgMonitor.FgBgListener;
import com.alipay.mobile.common.fgbg.FgBgMonitor.ProcessInfo;
import com.alipay.mobile.common.fgbg.FgBgMonitor.ProcessType;
import com.alipay.mobile.common.fgbg.FgBgMonitor.ScreenListener;
import com.alipay.mobile.common.fgbg.ProcessFgBgWatcher.FgBgCallback;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Util;
import com.alipay.mobile.liteprocess.ipc.IpcMsgClient;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.HashSet;
import java.util.Set;

class FgBgMonitorImpl extends FgBgMonitor {
    /* access modifiers changed from: private */
    public static final ProcessInfo[] GET_FOREGROUND_PROCESS_LOCK = new ProcessInfo[1];
    private static final String TAG = "FgBgMonitorImpl";
    private static Messenger mClientMessenger;
    /* access modifiers changed from: private */
    public static final Handler sHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    @NonNull
    public final Context mAppContext;
    private volatile BroadcastReceiver mBroadcastReceiver = null;
    private final Set<FgBgListener> mFgBgListeners = new HashSet();
    private boolean mHasInited = false;
    private final Set<ScreenListener> mScreenListeners = new HashSet();

    class ClientHandler extends Handler {
        ClientHandler(Looper looper) {
            super(looper);
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void handleMessage(Message message) {
            final ProcessInfo processInfo;
            if (message != null) {
                Bundle data = message.getData();
                if (data != null) {
                    String processName = data.getString("key_process_name");
                    String processType = data.getString("key_process_type");
                    String activity = data.getString("key_activity");
                    if (processName == null || processType == null || activity == null) {
                        processInfo = null;
                    } else {
                        processInfo = new ProcessInfo(processName, ProcessType.valueOf(processType), activity);
                    }
                } else {
                    processInfo = null;
                }
                switch (message.what) {
                    case 0:
                        if (processInfo != null) {
                            FgBgMonitorImpl.sHandler.post(new Runnable() {
                                {
                                    if (Boolean.FALSE.booleanValue()) {
                                        Log.v("hackbyte ", ClassVerifier.class.toString());
                                    }
                                }

                                public void run() {
                                    FgBgMonitorImpl.this.notifyMoveForeground(processInfo);
                                }
                            });
                            return;
                        }
                        return;
                    case 1:
                        if (processInfo != null) {
                            FgBgMonitorImpl.sHandler.post(new Runnable() {
                                {
                                    if (Boolean.FALSE.booleanValue()) {
                                        Log.v("hackbyte ", ClassVerifier.class.toString());
                                    }
                                }

                                public void run() {
                                    FgBgMonitorImpl.this.notifyMoveBackground(processInfo);
                                }
                            });
                            return;
                        }
                        return;
                    case 3:
                        synchronized (FgBgMonitorImpl.GET_FOREGROUND_PROCESS_LOCK) {
                            FgBgMonitorImpl.GET_FOREGROUND_PROCESS_LOCK[0] = processInfo;
                            FgBgMonitorImpl.GET_FOREGROUND_PROCESS_LOCK.notifyAll();
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    FgBgMonitorImpl(@NonNull Context context) {
        this.mAppContext = context.getApplicationContext();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    private void initIfNot() {
        if (!this.mHasInited) {
            this.mHasInited = true;
            HandlerThread handlerThread = new HandlerThread("FgBgMonitor");
            handlerThread.start();
            ClientHandler handler = new ClientHandler(handlerThread.getLooper());
            if (LiteProcessInfo.g(this.mAppContext).isCurrentProcessALiteProcess()) {
                Util.setContext(this.mAppContext);
                IpcMsgClient.prepare();
                IpcMsgClient.registerRspBizHandler("FgBgMonitorService", handler);
            }
            mClientMessenger = new Messenger(handler);
            Message message = Message.obtain();
            message.what = 2;
            message.replyTo = mClientMessenger;
            FgBgMonitorService.sendMessage(this.mAppContext, message);
        }
    }

    public void registerFgBgListener(@NonNull FgBgListener listener) {
        initIfNot();
        synchronized (this.mFgBgListeners) {
            this.mFgBgListeners.add(listener);
        }
    }

    public void unregisterFgBgListener(@NonNull FgBgListener listener) {
        synchronized (this.mFgBgListeners) {
            this.mFgBgListeners.remove(listener);
        }
    }

    public void registerScreenListener(@NonNull ScreenListener listener) {
        registerScreenReceiverIfNot();
        synchronized (this.mScreenListeners) {
            this.mScreenListeners.add(listener);
        }
    }

    public void unregisterScreenListener(@NonNull ScreenListener listener) {
        synchronized (this.mScreenListeners) {
            this.mScreenListeners.remove(listener);
        }
    }

    private void registerScreenReceiverIfNot() {
        if (this.mBroadcastReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.mBroadcastReceiver = new BroadcastReceiver() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void onReceive(Context context, Intent intent) {
                    if (intent == null) {
                        return;
                    }
                    if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                        FgBgMonitorImpl.this.notifyScreenInteractive();
                    } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                        FgBgMonitorImpl.this.notifyScreenUninteractive();
                    }
                }
            };
            this.mAppContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
        }
    }

    @Nullable
    public ProcessInfo getForegroundProcess() {
        ProcessInfo processInfo;
        initIfNot();
        Message message = Message.obtain();
        message.what = 3;
        message.replyTo = mClientMessenger;
        FgBgMonitorService.sendMessage(this.mAppContext, message);
        synchronized (GET_FOREGROUND_PROCESS_LOCK) {
            if (!LoggerFactory.getProcessInfo().isMainProcess() || Looper.myLooper() != Looper.getMainLooper()) {
                try {
                    GET_FOREGROUND_PROCESS_LOCK.wait(BalloonLayout.DEFAULT_DISPLAY_DURATION);
                } catch (InterruptedException e) {
                    LoggerFactory.getTraceLogger().warn((String) TAG, "getForegroundProcess failed:" + e.toString());
                    return null;
                }
            } else {
                try {
                    GET_FOREGROUND_PROCESS_LOCK.wait(500);
                } catch (InterruptedException e2) {
                    LoggerFactory.getTraceLogger().warn((String) TAG, "getForegroundProcess 0.5s failed:" + e2.toString() + ", try no ipc call");
                    return FgBgMonitorService.getFgBgProcessNoIPC();
                } catch (Throwable t) {
                    LoggerFactory.getTraceLogger().warn((String) TAG, "getFgBgProcessNoIPC failed:" + t.toString());
                }
            }
            processInfo = GET_FOREGROUND_PROCESS_LOCK[0];
        }
        return processInfo;
    }

    /* access modifiers changed from: 0000 */
    public void onProcessFgBgWatcherInited() {
        FgBgMonitorService.initWhenMainProcess();
        ProcessFgBgWatcher.getInstance().registerCallback(new FgBgCallback() {
            private String processName = null;

            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void onMoveToForeground(String topActivityName) {
                Message message = Message.obtain();
                message.what = 0;
                Bundle bundle = new Bundle();
                bundle.putString("key_activity", topActivityName);
                bundle.putString("key_process_name", getProcessName());
                bundle.putLong("key_event_time", SystemClock.elapsedRealtime());
                message.setData(bundle);
                FgBgMonitorService.sendMessage(FgBgMonitorImpl.this.mAppContext, message);
            }

            public void onMoveToBackground(String lastTopActivityName) {
                Message message = Message.obtain();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("key_activity", lastTopActivityName);
                bundle.putString("key_process_name", getProcessName());
                bundle.putLong("key_event_time", SystemClock.elapsedRealtime());
                message.setData(bundle);
                FgBgMonitorService.sendMessage(FgBgMonitorImpl.this.mAppContext, message);
            }

            private String getProcessName() {
                if (TextUtils.isEmpty(this.processName)) {
                    this.processName = LoggerFactory.getProcessInfo().getProcessName();
                }
                return this.processName;
            }
        });
    }

    /* access modifiers changed from: private */
    public void notifyMoveForeground(@NonNull ProcessInfo processInfo) {
        Set<FgBgListener> clone;
        synchronized (this.mFgBgListeners) {
            clone = new HashSet<>(this.mFgBgListeners);
        }
        for (FgBgListener fgBgListener : clone) {
            if (fgBgListener != null) {
                fgBgListener.onMoveToForeground(processInfo);
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyMoveBackground(@NonNull ProcessInfo processInfo) {
        Set<FgBgListener> clone;
        synchronized (this.mFgBgListeners) {
            clone = new HashSet<>(this.mFgBgListeners);
        }
        for (FgBgListener fgBgListener : clone) {
            if (fgBgListener != null) {
                fgBgListener.onMoveToBackground(processInfo);
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyScreenInteractive() {
        Set<ScreenListener> clone;
        synchronized (this.mScreenListeners) {
            clone = new HashSet<>(this.mScreenListeners);
        }
        for (ScreenListener screenListener : clone) {
            if (screenListener != null) {
                screenListener.onScreenInteractive();
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyScreenUninteractive() {
        Set<ScreenListener> clone;
        synchronized (this.mScreenListeners) {
            clone = new HashSet<>(this.mScreenListeners);
        }
        for (ScreenListener screenListener : clone) {
            if (screenListener != null) {
                screenListener.onScreenUninteractive();
            }
        }
    }
}
