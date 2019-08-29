package com.autonavi.minimap.ajx3.loader.picasso;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

class Dispatcher {
    static final int AIRPLANE_MODE_CHANGE = 10;
    private static final int AIRPLANE_MODE_OFF = 0;
    private static final int AIRPLANE_MODE_ON = 1;
    private static final int BATCH_DELAY = 200;
    private static final String DISPATCHER_THREAD_NAME = "Dispatcher";
    static final int HUNTER_BATCH_COMPLETE = 8;
    static final int HUNTER_COMPLETE = 4;
    static final int HUNTER_DECODE_FAILED = 6;
    static final int HUNTER_DELAY_NEXT_BATCH = 7;
    static final int HUNTER_FAST_COMPLETE = 14;
    static final int HUNTER_RETRY = 5;
    static final int NETWORK_STATE_CHANGE = 9;
    static final int REQUEST_BATCH_RESUME = 13;
    static final int REQUEST_CANCEL = 2;
    static final int REQUEST_GCED = 3;
    static final int REQUEST_SUBMIT = 1;
    private static final int RETRY_DELAY = 500;
    static final int TAG_PAUSE = 11;
    static final int TAG_RESUME = 12;
    boolean airplaneMode;
    final List<ImageHunter> batch;
    final Cache cache;
    final Context context;
    final DispatcherThread dispatcherThread = new DispatcherThread();
    final Downloader downloader;
    final Map<Object, Action> failedActions;
    final Handler handler;
    final Map<String, ImageHunter> hunterMap;
    final Handler mainThreadHandler;
    final ExecutorService networkService;
    final ExecutorService otherService;
    final Map<Object, Action> pausedActions;
    final Set<Object> pausedTags;
    final NetworkBroadcastReceiver receiver;
    final boolean scansNetworkChanges;
    final Stats stats;

    static class DispatcherHandler extends Handler {
        private final Dispatcher dispatcher;

        public DispatcherHandler(Looper looper, Dispatcher dispatcher2) {
            super(looper);
            this.dispatcher = dispatcher2;
        }

        public void handleMessage(final Message message) {
            boolean z = false;
            switch (message.what) {
                case 1:
                    this.dispatcher.performSubmit((Action) message.obj);
                    return;
                case 2:
                    this.dispatcher.performCancel((Action) message.obj);
                    return;
                case 4:
                    this.dispatcher.performComplete((ImageHunter) message.obj);
                    return;
                case 5:
                    this.dispatcher.performRetry((ImageHunter) message.obj);
                    return;
                case 6:
                    this.dispatcher.performError((ImageHunter) message.obj, false);
                    return;
                case 7:
                    this.dispatcher.performBatchComplete();
                    return;
                case 9:
                    this.dispatcher.performNetworkStateChange((NetworkInfo) message.obj);
                    return;
                case 10:
                    Dispatcher dispatcher2 = this.dispatcher;
                    if (message.arg1 == 1) {
                        z = true;
                    }
                    dispatcher2.performAirplaneModeChange(z);
                    return;
                case 11:
                    this.dispatcher.performPauseTag(message.obj);
                    return;
                case 12:
                    this.dispatcher.performResumeTag(message.obj);
                    return;
                default:
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            StringBuilder sb = new StringBuilder("Unknown handler message received: ");
                            sb.append(message.what);
                            throw new RuntimeException(sb.toString());
                        }
                    });
                    return;
            }
        }
    }

    static class DispatcherThread extends HandlerThread {
        DispatcherThread() {
            super("Picasso-Dispatcher", 10);
        }
    }

    static class NetworkBroadcastReceiver extends BroadcastReceiver {
        static final String EXTRA_AIRPLANE_STATE = "state";
        private final Dispatcher dispatcher;

        NetworkBroadcastReceiver(Dispatcher dispatcher2) {
            this.dispatcher = dispatcher2;
        }

        /* access modifiers changed from: 0000 */
        public void register() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            if (this.dispatcher.scansNetworkChanges) {
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
            this.dispatcher.context.registerReceiver(this, intentFilter);
        }

        /* access modifiers changed from: 0000 */
        public void unregister() {
            this.dispatcher.context.unregisterReceiver(this);
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (!"android.intent.action.AIRPLANE_MODE".equals(action)) {
                    if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                        this.dispatcher.dispatchNetworkStateChange(((ConnectivityManager) Utils.getService(context, "connectivity")).getActiveNetworkInfo());
                    }
                } else if (intent.hasExtra(EXTRA_AIRPLANE_STATE)) {
                    this.dispatcher.dispatchAirplaneModeChange(intent.getBooleanExtra(EXTRA_AIRPLANE_STATE, false));
                }
            }
        }
    }

    Dispatcher(Context context2, ExecutorService executorService, ExecutorService executorService2, Handler handler2, Downloader downloader2, Cache cache2, Stats stats2) {
        this.dispatcherThread.start();
        Utils.flushStackLocalLeaks(this.dispatcherThread.getLooper());
        this.context = context2;
        this.networkService = executorService;
        this.otherService = executorService2;
        this.hunterMap = new LinkedHashMap();
        this.failedActions = new WeakHashMap();
        this.pausedActions = new WeakHashMap();
        this.pausedTags = new HashSet();
        this.handler = new DispatcherHandler(this.dispatcherThread.getLooper(), this);
        this.downloader = downloader2;
        this.mainThreadHandler = handler2;
        this.cache = cache2;
        this.stats = stats2;
        this.batch = new ArrayList(4);
        this.airplaneMode = Utils.isAirplaneModeOn(this.context);
        this.scansNetworkChanges = Utils.hasPermission(context2, "android.permission.ACCESS_NETWORK_STATE");
        this.receiver = new NetworkBroadcastReceiver(this);
        this.receiver.register();
    }

    /* access modifiers changed from: 0000 */
    public void shutdown() {
        if (this.networkService instanceof PicassoExecutorService) {
            this.networkService.shutdown();
        }
        if (this.otherService instanceof PicassoExecutorService) {
            this.otherService.shutdown();
        }
        this.downloader.shutdown();
        this.dispatcherThread.quit();
        Picasso.HANDLER.post(new Runnable() {
            public void run() {
                Dispatcher.this.receiver.unregister();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void dispatchSubmit(Action action) {
        this.handler.sendMessage(this.handler.obtainMessage(1, action));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchCancel(Action action) {
        this.handler.sendMessage(this.handler.obtainMessage(2, action));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchPauseTag(Object obj) {
        this.handler.sendMessage(this.handler.obtainMessage(11, obj));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchResumeTag(Object obj) {
        this.handler.sendMessage(this.handler.obtainMessage(12, obj));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchComplete(ImageHunter imageHunter) {
        this.handler.sendMessage(this.handler.obtainMessage(4, imageHunter));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchRetry(ImageHunter imageHunter) {
        this.handler.sendMessageDelayed(this.handler.obtainMessage(5, imageHunter), 500);
    }

    /* access modifiers changed from: 0000 */
    public void dispatchFailed(ImageHunter imageHunter) {
        this.handler.sendMessage(this.handler.obtainMessage(6, imageHunter));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchNetworkStateChange(NetworkInfo networkInfo) {
        this.handler.sendMessage(this.handler.obtainMessage(9, networkInfo));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchAirplaneModeChange(boolean z) {
        this.handler.sendMessage(this.handler.obtainMessage(10, z ? 1 : 0, 0));
    }

    /* access modifiers changed from: 0000 */
    public void performSubmit(Action action) {
        performSubmit(action, true);
    }

    /* access modifiers changed from: 0000 */
    public void performSubmit(Action action, boolean z) {
        if (this.pausedTags.contains(action.getTag())) {
            this.pausedActions.put(action.getTarget(), action);
            if (action.getPicasso().loggingEnabled) {
                String logId = action.request.logId();
                StringBuilder sb = new StringBuilder("because tag '");
                sb.append(action.getTag());
                sb.append("' is paused");
                Utils.log(DISPATCHER_THREAD_NAME, "paused", logId, sb.toString());
            }
            return;
        }
        ImageHunter imageHunter = this.hunterMap.get(action.getKey());
        if (imageHunter == null || action.noMerge) {
            ExecutorService executorService = getExecutorService(action);
            if (executorService.isShutdown()) {
                if (action.getPicasso().loggingEnabled) {
                    Utils.log(DISPATCHER_THREAD_NAME, "ignored", action.request.logId(), "because shut down");
                }
                return;
            }
            ImageHunter forRequest = ImageHunter.forRequest(action.getPicasso(), this, this.cache, this.stats, action);
            forRequest.future = executorService.submit(forRequest);
            this.hunterMap.put(action.getKey(), forRequest);
            if (z) {
                this.failedActions.remove(action.getTarget());
            }
            if (action.getPicasso().loggingEnabled) {
                Utils.log(DISPATCHER_THREAD_NAME, "enqueued", action.request.logId());
            }
            return;
        }
        imageHunter.attach(action);
    }

    /* access modifiers changed from: 0000 */
    public void performCancel(Action action) {
        String key = action.getKey();
        ImageHunter imageHunter = this.hunterMap.get(key);
        if (imageHunter != null) {
            imageHunter.detach(action);
            if (imageHunter.cancel()) {
                this.hunterMap.remove(key);
                if (action.getPicasso().loggingEnabled) {
                    Utils.log(DISPATCHER_THREAD_NAME, "canceled", action.getRequest().logId());
                }
            }
        }
        if (this.pausedTags.contains(action.getTag())) {
            this.pausedActions.remove(action.getTarget());
            if (action.getPicasso().loggingEnabled) {
                Utils.log(DISPATCHER_THREAD_NAME, "canceled", action.getRequest().logId(), "because paused request got canceled");
            }
        }
        Action remove = this.failedActions.remove(action.getTarget());
        if (remove != null && remove.getPicasso().loggingEnabled) {
            Utils.log(DISPATCHER_THREAD_NAME, "canceled", remove.getRequest().logId(), "from replaying");
        }
    }

    /* access modifiers changed from: 0000 */
    public void performPauseTag(Object obj) {
        if (this.pausedTags.add(obj)) {
            Iterator<ImageHunter> it = this.hunterMap.values().iterator();
            while (it.hasNext()) {
                ImageHunter next = it.next();
                boolean z = next.getPicasso().loggingEnabled;
                Action action = next.getAction();
                List<Action> actions = next.getActions();
                boolean z2 = actions != null && !actions.isEmpty();
                if (action != null || z2) {
                    if (action != null && action.getTag().equals(obj)) {
                        next.detach(action);
                        this.pausedActions.put(action.getTarget(), action);
                        if (z) {
                            String logId = action.request.logId();
                            StringBuilder sb = new StringBuilder("because tag '");
                            sb.append(obj);
                            sb.append("' was paused");
                            Utils.log(DISPATCHER_THREAD_NAME, "paused", logId, sb.toString());
                        }
                    }
                    if (z2) {
                        for (int size = actions.size() - 1; size >= 0; size--) {
                            Action action2 = actions.get(size);
                            if (action2.getTag().equals(obj)) {
                                next.detach(action2);
                                this.pausedActions.put(action2.getTarget(), action2);
                                if (z) {
                                    String logId2 = action2.request.logId();
                                    StringBuilder sb2 = new StringBuilder("because tag '");
                                    sb2.append(obj);
                                    sb2.append("' was paused");
                                    Utils.log(DISPATCHER_THREAD_NAME, "paused", logId2, sb2.toString());
                                }
                            }
                        }
                    }
                    if (next.cancel()) {
                        it.remove();
                        if (z) {
                            Utils.log(DISPATCHER_THREAD_NAME, "canceled", Utils.getLogIdsForHunter(next), "all actions paused");
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performResumeTag(Object obj) {
        if (this.pausedTags.remove(obj)) {
            ArrayList arrayList = null;
            Iterator<Action> it = this.pausedActions.values().iterator();
            while (it.hasNext()) {
                Action next = it.next();
                if (next.getTag().equals(obj)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(next);
                    it.remove();
                }
            }
            if (arrayList != null) {
                this.mainThreadHandler.sendMessage(this.mainThreadHandler.obtainMessage(13, arrayList));
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performRetry(ImageHunter imageHunter) {
        if (!imageHunter.isCancelled()) {
            ExecutorService executorService = getExecutorService(imageHunter);
            boolean z = false;
            if (executorService.isShutdown()) {
                performError(imageHunter, false);
                return;
            }
            NetworkInfo networkInfo = null;
            if (this.scansNetworkChanges) {
                ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getService(this.context, "connectivity");
                if (connectivityManager != null) {
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                } else {
                    return;
                }
            }
            boolean z2 = networkInfo != null && networkInfo.isConnected();
            boolean shouldRetry = imageHunter.shouldRetry(this.airplaneMode, networkInfo);
            boolean supportsReplay = imageHunter.supportsReplay();
            if (!shouldRetry) {
                if (this.scansNetworkChanges && supportsReplay) {
                    z = true;
                }
                performError(imageHunter, z);
                if (z) {
                    markForReplay(imageHunter);
                }
            } else if (!this.scansNetworkChanges || z2) {
                if (imageHunter.getPicasso().loggingEnabled) {
                    Utils.log(DISPATCHER_THREAD_NAME, "retrying", Utils.getLogIdsForHunter(imageHunter));
                }
                if (imageHunter.getException() instanceof ContentLengthException) {
                    imageHunter.networkPolicy |= NetworkPolicy.NO_CACHE.index;
                }
                imageHunter.future = executorService.submit(imageHunter);
            } else {
                performError(imageHunter, supportsReplay);
                if (supportsReplay) {
                    markForReplay(imageHunter);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performComplete(ImageHunter imageHunter) {
        this.hunterMap.remove(imageHunter.getKey());
        boolean z = imageHunter.action != null && imageHunter.action.fastMode;
        if (z) {
            performFastComplete(imageHunter);
        } else {
            batch(imageHunter);
        }
        if (imageHunter.getPicasso().loggingEnabled) {
            Utils.log(DISPATCHER_THREAD_NAME, z ? "fastcompleted" : "batched", Utils.getLogIdsForHunter(imageHunter), "for completion");
        }
    }

    /* access modifiers changed from: 0000 */
    public void performFastComplete(ImageHunter imageHunter) {
        if (!imageHunter.isCancelled()) {
            this.mainThreadHandler.sendMessage(this.mainThreadHandler.obtainMessage(14, imageHunter));
            logFast(imageHunter);
        }
    }

    /* access modifiers changed from: 0000 */
    public void performBatchComplete() {
        ArrayList arrayList = new ArrayList(this.batch);
        this.batch.clear();
        this.mainThreadHandler.sendMessage(this.mainThreadHandler.obtainMessage(8, arrayList));
        logBatch(arrayList);
    }

    /* access modifiers changed from: 0000 */
    public void performError(ImageHunter imageHunter, boolean z) {
        if (imageHunter.getPicasso().loggingEnabled) {
            String str = imageHunter.action != null && imageHunter.action.fastMode ? "fastcompleted" : "batched";
            String logIdsForHunter = Utils.getLogIdsForHunter(imageHunter);
            StringBuilder sb = new StringBuilder("for error");
            sb.append(z ? " (will replay)" : "");
            Utils.log(DISPATCHER_THREAD_NAME, str, logIdsForHunter, sb.toString());
        }
        this.hunterMap.remove(imageHunter.getKey());
        batch(imageHunter);
    }

    /* access modifiers changed from: 0000 */
    public void performAirplaneModeChange(boolean z) {
        this.airplaneMode = z;
    }

    /* access modifiers changed from: 0000 */
    public void performNetworkStateChange(NetworkInfo networkInfo) {
        if (this.networkService instanceof PicassoExecutorService) {
            ((PicassoExecutorService) this.networkService).adjustThreadCount(networkInfo);
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            flushFailedActions();
        }
    }

    private void flushFailedActions() {
        if (!this.failedActions.isEmpty()) {
            Iterator<Action> it = this.failedActions.values().iterator();
            while (it.hasNext()) {
                Action next = it.next();
                it.remove();
                if (next.getPicasso().loggingEnabled) {
                    Utils.log(DISPATCHER_THREAD_NAME, "replaying", next.getRequest().logId());
                }
                performSubmit(next, false);
            }
        }
    }

    private void markForReplay(ImageHunter imageHunter) {
        Action action = imageHunter.getAction();
        if (action != null) {
            markForReplay(action);
        }
        List<Action> actions = imageHunter.getActions();
        if (actions != null) {
            int size = actions.size();
            for (int i = 0; i < size; i++) {
                markForReplay(actions.get(i));
            }
        }
    }

    private void markForReplay(Action action) {
        Object target = action.getTarget();
        if (target != null) {
            action.willReplay = true;
            this.failedActions.put(target, action);
        }
    }

    private void batch(ImageHunter imageHunter) {
        if (!imageHunter.isCancelled()) {
            this.batch.add(imageHunter);
            if (!this.handler.hasMessages(7)) {
                this.handler.sendEmptyMessageDelayed(7, 200);
            }
        }
    }

    private void logBatch(List<ImageHunter> list) {
        if (list != null && !list.isEmpty() && list.get(0).getPicasso().loggingEnabled) {
            StringBuilder sb = new StringBuilder();
            for (ImageHunter next : list) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(Utils.getLogIdsForHunter(next));
            }
            Utils.log(DISPATCHER_THREAD_NAME, "delivered", sb.toString());
        }
    }

    private void logFast(ImageHunter imageHunter) {
        if (imageHunter != null && imageHunter.getPicasso().loggingEnabled) {
            Utils.log(DISPATCHER_THREAD_NAME, "fastcompleteddelivered", Utils.getLogIdsForHunter(imageHunter));
        }
    }

    private ExecutorService getExecutorService(Action action) {
        ExecutorService executorService = this.otherService;
        if (action == null) {
            return executorService;
        }
        String scheme = action.request.uri.getScheme();
        if ("https".equals(scheme) || "http".equals(scheme)) {
            executorService = this.networkService;
        }
        return executorService;
    }

    private ExecutorService getExecutorService(ImageHunter imageHunter) {
        ExecutorService executorService = this.otherService;
        if (imageHunter == null) {
            return executorService;
        }
        Action action = imageHunter.getAction();
        if (action != null) {
            String scheme = action.request.uri.getScheme();
            if ("https".equals(scheme) || "http".equals(scheme)) {
                executorService = this.networkService;
            }
            return executorService;
        }
        List<Action> actions = imageHunter.getActions();
        if (actions != null && !actions.isEmpty()) {
            int size = actions.size() - 1;
            if (size >= 0) {
                String scheme2 = actions.get(size).request.uri.getScheme();
                if ("https".equals(scheme2) || "http".equals(scheme2)) {
                    executorService = this.networkService;
                }
                return executorService;
            }
        }
        return executorService;
    }
}
