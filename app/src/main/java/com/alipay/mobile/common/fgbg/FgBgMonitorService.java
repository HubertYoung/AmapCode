package com.alipay.mobile.common.fgbg;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.fgbg.FgBgMonitor.ProcessInfo;
import com.alipay.mobile.common.fgbg.FgBgMonitor.ProcessType;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.LiteProcess;
import com.alipay.mobile.liteprocess.LiteProcessServerManager;
import com.alipay.mobile.liteprocess.ipc.IpcMsgClient;
import com.alipay.mobile.liteprocess.ipc.IpcMsgServer;
import com.alipay.mobile.quinox.asynctask.AsyncTaskExecutor;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FgBgMonitorService extends Service {
    static final String MSG_DATA_KEY_ACTIVITY = "key_activity";
    private static final String MSG_DATA_KEY_CALLING_PID = "key_calling_pid";
    static final String MSG_DATA_KEY_EVENT_TIME = "key_event_time";
    private static final String MSG_DATA_KEY_IS_LITE_PROCESS = "key_is_lite_process";
    static final String MSG_DATA_KEY_PROCESS_NAME = "key_process_name";
    static final String MSG_DATA_KEY_PROCESS_TYPE = "key_process_type";
    static final int MSG_WHAT_ADD_CALLBACK = 2;
    static final int MSG_WHAT_GET_FOREGROUND_PROCESS = 3;
    static final int MSG_WHAT_MOVE_BACKGROUND = 1;
    static final int MSG_WHAT_MOVE_FOREGROUND = 0;
    private static final ReadWriteLock PROCESS_LOCK = new ReentrantReadWriteLock();
    static final String TAG = "FgBgMonitorService";
    /* access modifiers changed from: private */
    public static final Set<Messenger> sCallbacks = new HashSet();
    /* access modifiers changed from: private */
    public static String sComingFgProcessName = null;
    /* access modifiers changed from: private */
    public static String sComingTopActivity = null;
    private static ConnectServiceFuture sConnectServiceFuture;
    /* access modifiers changed from: private */
    public static String sFgProcessName = null;
    @Nullable
    private static Handler sHandler;
    private static long sLastEventTime = 0;
    /* access modifiers changed from: private */
    public static final Set<Integer> sLiteCallbacks = new HashSet();
    /* access modifiers changed from: private */
    public static String sTopActivity = null;
    @Nullable
    private Messenger mMessenger;

    class ConnectServiceFuture implements ServiceConnection, Future<Messenger> {
        /* access modifiers changed from: private */
        public final Context mAppContext;
        private boolean mEnterWaiting;
        /* access modifiers changed from: private */
        public Messenger mResult;
        /* access modifiers changed from: private */
        public boolean mResultReceived = false;

        ConnectServiceFuture(@NonNull Context context) {
            this.mAppContext = context.getApplicationContext();
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public synchronized boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        public Messenger get() {
            try {
                return doGet(null);
            } catch (TimeoutException e) {
                throw new AssertionError(e);
            }
        }

        public Messenger get(long timeout, TimeUnit unit) {
            return doGet(Long.valueOf(TimeUnit.MILLISECONDS.convert(timeout, unit)));
        }

        public synchronized void onServiceConnected(ComponentName name, IBinder service) {
            this.mResultReceived = true;
            this.mResult = new Messenger(service);
            try {
                this.mResult.getBinder().linkToDeath(new DeathRecipient() {
                    {
                        if (Boolean.FALSE.booleanValue()) {
                            Log.v("hackbyte ", ClassVerifier.class.toString());
                        }
                    }

                    public void binderDied() {
                        synchronized (ConnectServiceFuture.this) {
                            ConnectServiceFuture.this.mResult = null;
                            ConnectServiceFuture.this.mResultReceived = false;
                            FgBgMonitorService.getHandler().post(new Runnable() {
                                {
                                    if (Boolean.FALSE.booleanValue()) {
                                        Log.v("hackbyte ", ClassVerifier.class.toString());
                                    }
                                }

                                public void run() {
                                    FgBgMonitorService.doBindService(ConnectServiceFuture.this.mAppContext);
                                }
                            });
                        }
                    }
                }, 0);
            } catch (RemoteException e) {
                LoggerFactory.getTraceLogger().error(FgBgMonitorService.TAG, "Messenger linkToDeath failed!", e);
            }
            if (this.mEnterWaiting) {
                notifyAll();
            }
            return;
        }

        public synchronized void onServiceDisconnected(ComponentName name) {
            this.mResultReceived = false;
            this.mResult = null;
        }

        private synchronized Messenger doGet(Long timeoutMs) {
            Messenger messenger;
            if (this.mResultReceived) {
                messenger = this.mResult;
            } else {
                this.mEnterWaiting = true;
                if (timeoutMs == null) {
                    wait(0);
                } else if (timeoutMs.longValue() > 0) {
                    wait(timeoutMs.longValue());
                }
                this.mEnterWaiting = false;
                if (!this.mResultReceived) {
                    throw new TimeoutException();
                }
                messenger = this.mResult;
            }
            return messenger;
        }

        public boolean isCancelled() {
            return false;
        }

        public synchronized boolean isDone() {
            return this.mResultReceived || isCancelled();
        }
    }

    public FgBgMonitorService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    static void initWhenMainProcess() {
        if (LoggerFactory.getProcessInfo().isMainProcess()) {
            IpcMsgServer.registerReqBizHandler(TAG, getHandler());
        }
    }

    static ProcessInfo getFgBgProcessNoIPC() {
        if (!LoggerFactory.getProcessInfo().isMainProcess()) {
            throw new IllegalStateException("you can't use this method if you not on main process");
        }
        try {
            PROCESS_LOCK.readLock().lock();
            String processName = sComingFgProcessName != null ? sComingFgProcessName : sFgProcessName;
            String activity = sComingTopActivity != null ? sComingTopActivity : sTopActivity;
            ProcessType processType = getProcessTypeByName(ProcessFgBgWatcher.mAppContext, sFgProcessName);
            if (processName != null && processType != null && activity != null) {
                return new ProcessInfo(processName, processType, activity);
            }
            PROCESS_LOCK.readLock().unlock();
            return null;
        } finally {
            PROCESS_LOCK.readLock().unlock();
        }
    }

    static Handler getHandler() {
        if (sHandler == null) {
            HandlerThread handlerThread = new HandlerThread(TAG);
            handlerThread.start();
            sHandler = new Handler(handlerThread.getLooper()) {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public final void handleMessage(Message msg) {
                    if (msg != null) {
                        Bundle bundle = msg.getData();
                        String topActivity = null;
                        String processName = null;
                        long eventTime = 0;
                        int callingPid = Process.myPid();
                        boolean isLiteProcess = false;
                        if (bundle != null) {
                            topActivity = bundle.getString(FgBgMonitorService.MSG_DATA_KEY_ACTIVITY, null);
                            processName = bundle.getString(FgBgMonitorService.MSG_DATA_KEY_PROCESS_NAME, null);
                            eventTime = bundle.getLong(FgBgMonitorService.MSG_DATA_KEY_EVENT_TIME, 0);
                            isLiteProcess = bundle.getBoolean(FgBgMonitorService.MSG_DATA_KEY_IS_LITE_PROCESS, false);
                            callingPid = bundle.getInt(FgBgMonitorService.MSG_DATA_KEY_CALLING_PID, callingPid);
                        }
                        switch (msg.what) {
                            case 0:
                                FgBgMonitorService.onNotifyMoveToForeground(processName, topActivity, eventTime);
                                return;
                            case 1:
                                FgBgMonitorService.onNotifyMoveToBackground(processName, topActivity, eventTime);
                                return;
                            case 2:
                                if (isLiteProcess) {
                                    FgBgMonitorService.sLiteCallbacks.add(Integer.valueOf(callingPid));
                                    return;
                                }
                                Messenger messenger = msg.replyTo;
                                if (messenger != null) {
                                    FgBgMonitorService.sCallbacks.add(messenger);
                                    return;
                                }
                                return;
                            case 3:
                                Message replayMsg = Message.obtain();
                                Bundle replyData = new Bundle();
                                replyData.putString(FgBgMonitorService.MSG_DATA_KEY_PROCESS_NAME, FgBgMonitorService.sComingFgProcessName != null ? FgBgMonitorService.sComingFgProcessName : FgBgMonitorService.sFgProcessName);
                                replyData.putString(FgBgMonitorService.MSG_DATA_KEY_ACTIVITY, FgBgMonitorService.sComingTopActivity != null ? FgBgMonitorService.sComingTopActivity : FgBgMonitorService.sTopActivity);
                                replyData.putString(FgBgMonitorService.MSG_DATA_KEY_PROCESS_TYPE, FgBgMonitorService.getProcessTypeByName(ProcessFgBgWatcher.mAppContext, FgBgMonitorService.sFgProcessName).name());
                                replayMsg.what = 3;
                                replayMsg.setData(replyData);
                                if (isLiteProcess) {
                                    LiteProcess liteProcess = LiteProcessServerManager.g().findProcessByPid(callingPid);
                                    Messenger messenger2 = liteProcess == null ? null : liteProcess.getReplyTo();
                                    if (messenger2 != null) {
                                        IpcMsgServer.reply(messenger2, FgBgMonitorService.TAG, replayMsg);
                                        return;
                                    }
                                    return;
                                } else if (msg.replyTo != null) {
                                    try {
                                        msg.replyTo.send(replayMsg);
                                        return;
                                    } catch (RemoteException e) {
                                        LoggerFactory.getTraceLogger().warn((String) FgBgMonitorService.TAG, "MSG_WHAT_GET_FOREGROUND_PROCESS send message failed! " + e.toString());
                                        return;
                                    }
                                } else {
                                    LoggerFactory.getTraceLogger().warn((String) FgBgMonitorService.TAG, (String) "MSG_WHAT_GET_FOREGROUND_PROCESS send message failed!, message.replayTo is null!");
                                    return;
                                }
                            default:
                                LoggerFactory.getTraceLogger().debug(FgBgMonitorService.TAG, "unknown message:" + String.valueOf(msg));
                                return;
                        }
                    }
                }
            };
        }
        return sHandler;
    }

    static void sendMessage(@NonNull final Context context, @NonNull final Message message) {
        Bundle bundle = message.getData();
        if (bundle == null) {
            bundle = new Bundle();
            message.setData(bundle);
        }
        bundle.putInt(MSG_DATA_KEY_CALLING_PID, Process.myPid());
        if (LoggerFactory.getProcessInfo().isMainProcess()) {
            getHandler().sendMessage(message);
        } else if (LiteProcessInfo.g(context).isCurrentProcessALiteProcess()) {
            message.replyTo = null;
            bundle.putBoolean(MSG_DATA_KEY_IS_LITE_PROCESS, true);
            IpcMsgClient.send(TAG, message);
        } else {
            bindServiceIfNot(context);
            final ConnectServiceFuture connectServiceFuture = sConnectServiceFuture;
            AsyncTaskExecutor.getInstance().executeSerially(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public final void run() {
                    Messenger serviceMessenger = null;
                    try {
                        serviceMessenger = ConnectServiceFuture.this.get(2, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        LoggerFactory.getTraceLogger().error(FgBgMonitorService.TAG, "bind service first times failed !", e);
                    }
                    if (serviceMessenger == null) {
                        FgBgMonitorService.doBindService(context);
                        try {
                            serviceMessenger = ConnectServiceFuture.this.get(10, TimeUnit.SECONDS);
                        } catch (InterruptedException | ExecutionException | TimeoutException e2) {
                            LoggerFactory.getTraceLogger().error(FgBgMonitorService.TAG, "bind service second times failed !", e2);
                        }
                    }
                    if (serviceMessenger == null) {
                        LoggerFactory.getTraceLogger().warn((String) FgBgMonitorService.TAG, "serviceMessenger get failed, send message abort!, msg:" + message.toString());
                        return;
                    }
                    try {
                        serviceMessenger.send(message);
                    } catch (RemoteException e3) {
                        LoggerFactory.getTraceLogger().warn((String) FgBgMonitorService.TAG, "serviceMessenger send message failed!, exception:" + e3.toString());
                    }
                }
            }, "FgBgServiceMessage");
        }
    }

    /* access modifiers changed from: private */
    public static void onNotifyMoveToForeground(@Nullable String processName, @Nullable String topActivity, long eventTime) {
        try {
            PROCESS_LOCK.writeLock().lock();
            if (eventTime < sLastEventTime) {
                LoggerFactory.getTraceLogger().warn((String) TAG, "ignore outdated foreground event, processName:" + processName + " curFgProcessName:" + sFgProcessName);
                return;
            }
            sLastEventTime = eventTime;
            if (TextUtils.isEmpty(topActivity)) {
                topActivity = "unknown";
            }
            if (TextUtils.isEmpty(processName)) {
                LoggerFactory.getTraceLogger().warn((String) TAG, "invalid notify foreground, processName:" + processName + " curFgProcessName:" + sFgProcessName);
            } else if (!TextUtils.equals(sFgProcessName, processName)) {
                if (!TextUtils.isEmpty(sFgProcessName)) {
                    sComingFgProcessName = processName;
                    sComingTopActivity = topActivity;
                    onNotifyMoveToBackground(sFgProcessName, sTopActivity, eventTime);
                    sComingFgProcessName = null;
                    sComingTopActivity = null;
                }
                sFgProcessName = processName;
                sTopActivity = topActivity;
                notifyMoveToFg(processName, topActivity);
            } else {
                LoggerFactory.getTraceLogger().warn((String) TAG, "process moveToForeground again, ignored! process:" + processName);
            }
            PROCESS_LOCK.writeLock().unlock();
        } finally {
            PROCESS_LOCK.writeLock().unlock();
        }
    }

    /* access modifiers changed from: private */
    public static void onNotifyMoveToBackground(@Nullable String processName, @Nullable String lastTopActivity, long eventTime) {
        try {
            PROCESS_LOCK.writeLock().lock();
            if (eventTime < sLastEventTime) {
                LoggerFactory.getTraceLogger().warn((String) TAG, "ignore outdated background event, processName:" + processName + " curFgProcessName:" + sFgProcessName);
                return;
            }
            sLastEventTime = eventTime;
            if (TextUtils.isEmpty(lastTopActivity)) {
                lastTopActivity = "unknown";
            }
            if (TextUtils.isEmpty(processName)) {
                LoggerFactory.getTraceLogger().warn((String) TAG, "invalid notify background, processName:" + processName + " curFgProcessName:" + sFgProcessName);
            } else if (sFgProcessName == null) {
                notifyMoveToBg(processName, lastTopActivity);
            } else if (TextUtils.equals(processName, sFgProcessName)) {
                String pn = sFgProcessName;
                sFgProcessName = null;
                notifyMoveToBg(pn, lastTopActivity);
            } else {
                LoggerFactory.getTraceLogger().debug(TAG, "notify background processName is not foreground processName, ignored. processName:" + processName + " curFgProcessName:" + sFgProcessName);
            }
            PROCESS_LOCK.writeLock().unlock();
        } finally {
            PROCESS_LOCK.writeLock().unlock();
        }
    }

    private static void notifyMoveToBg(@NonNull String processName, @NonNull String lastTopActivity) {
        Message message = Message.obtain();
        message.what = 1;
        Bundle bundle = new Bundle();
        bundle.putString(MSG_DATA_KEY_PROCESS_NAME, processName);
        bundle.putString(MSG_DATA_KEY_ACTIVITY, lastTopActivity);
        bundle.putString(MSG_DATA_KEY_PROCESS_TYPE, getProcessTypeByName(ProcessFgBgWatcher.mAppContext, processName).name());
        message.setData(bundle);
        notify(message);
    }

    private static void notifyMoveToFg(@NonNull String processName, @NonNull String topActivity) {
        Message message = Message.obtain();
        message.what = 0;
        Bundle bundle = new Bundle();
        bundle.putString(MSG_DATA_KEY_PROCESS_NAME, processName);
        bundle.putString(MSG_DATA_KEY_ACTIVITY, topActivity);
        bundle.putString(MSG_DATA_KEY_PROCESS_TYPE, getProcessTypeByName(ProcessFgBgWatcher.mAppContext, processName).name());
        message.setData(bundle);
        notify(message);
    }

    private static void notify(@NonNull Message message) {
        Iterator callbackIterator = sCallbacks.iterator();
        while (callbackIterator.hasNext()) {
            Messenger messenger = callbackIterator.next();
            if (messenger == null || !messenger.getBinder().isBinderAlive()) {
                callbackIterator.remove();
            } else {
                try {
                    messenger.send(Message.obtain(message));
                } catch (RemoteException e) {
                    LoggerFactory.getTraceLogger().warn((String) TAG, "send local message error:" + e.toString());
                }
            }
        }
        Iterator liteCallbackIterator = sLiteCallbacks.iterator();
        while (liteCallbackIterator.hasNext()) {
            LiteProcess liteProcess = LiteProcessServerManager.g().findProcessByPid(liteCallbackIterator.next().intValue());
            if (liteProcess == null) {
                liteCallbackIterator.remove();
            } else {
                Messenger messenger2 = liteProcess.getReplyTo();
                if (messenger2 != null) {
                    IpcMsgServer.reply(messenger2, TAG, Message.obtain(message));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static ProcessType getProcessTypeByName(Context context, String processName) {
        if (context == null || TextUtils.isEmpty(processName)) {
            return ProcessType.UNKNOWN;
        }
        String packageName = context.getPackageName();
        if (packageName.equalsIgnoreCase(processName)) {
            return ProcessType.MAIN;
        }
        if (LiteProcessInfo.g(context).isLiteProcess(processName)) {
            return ProcessType.LITE;
        }
        if ((packageName + ':' + "push").equalsIgnoreCase(processName)) {
            return ProcessType.PUSH;
        }
        if ((packageName + ':' + com.alipay.mobile.common.logging.api.ProcessInfo.ALIAS_TOOLS).equalsIgnoreCase(processName)) {
            return ProcessType.TOOLS;
        }
        if ((packageName + ':' + com.alipay.mobile.common.logging.api.ProcessInfo.ALIAS_EXT).equals(processName)) {
            return ProcessType.EXT;
        }
        if ((packageName + ':' + "sss").equals(processName)) {
            return ProcessType.SSS;
        }
        return ProcessType.UNKNOWN;
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        if (this.mMessenger == null) {
            this.mMessenger = new Messenger(getHandler());
        }
        return this.mMessenger.getBinder();
    }

    private static void bindServiceIfNot(@NonNull Context context) {
        if (sConnectServiceFuture == null) {
            sConnectServiceFuture = new ConnectServiceFuture(context);
            doBindService(context);
        }
    }

    /* access modifiers changed from: private */
    public static void doBindService(@NonNull Context context) {
        if (sConnectServiceFuture == null) {
            sConnectServiceFuture = new ConnectServiceFuture(context);
        }
        context.bindService(new Intent(context, FgBgMonitorService.class), sConnectServiceFuture, 1);
    }
}
