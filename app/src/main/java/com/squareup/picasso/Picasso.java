package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.internal.view.SupportMenu;
import android.widget.ImageView;
import android.widget.RemoteViews;
import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

public class Picasso {
    static final Handler HANDLER = new Handler(Looper.getMainLooper()) {
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 3) {
                int i2 = 0;
                if (i == 8) {
                    List list = (List) message.obj;
                    int size = list.size();
                    while (i2 < size) {
                        BitmapHunter bitmapHunter = (BitmapHunter) list.get(i2);
                        bitmapHunter.picasso.complete(bitmapHunter);
                        i2++;
                    }
                } else if (i != 13) {
                    StringBuilder sb = new StringBuilder("Unknown handler message received: ");
                    sb.append(message.what);
                    throw new AssertionError(sb.toString());
                } else {
                    List list2 = (List) message.obj;
                    int size2 = list2.size();
                    while (i2 < size2) {
                        Action action = (Action) list2.get(i2);
                        action.picasso.resumeAction(action);
                        i2++;
                    }
                }
            } else {
                Action action2 = (Action) message.obj;
                if (action2.getPicasso().loggingEnabled) {
                    Utils.log("Main", "canceled", action2.request.logId(), "target got garbage collected");
                }
                action2.picasso.cancelExistingRequest(action2.getTarget());
            }
        }
    };
    static final String TAG = "Picasso";
    static volatile Picasso singleton;
    final Cache cache;
    private final CleanupThread cleanupThread;
    final Context context;
    final Config defaultBitmapConfig;
    final Dispatcher dispatcher;
    boolean indicatorsEnabled;
    private final Listener listener;
    volatile boolean loggingEnabled;
    final ReferenceQueue<Object> referenceQueue;
    private final List<RequestHandler> requestHandlers;
    private final RequestTransformer requestTransformer;
    boolean shutdown;
    final Stats stats;
    final Map<Object, Action> targetToAction;
    final Map<ImageView, DeferredRequestCreator> targetToDeferredRequestCreator;

    public static class Builder {
        private Cache cache;
        private final Context context;
        private Config defaultBitmapConfig;
        private Downloader downloader;
        private boolean indicatorsEnabled;
        private Listener listener;
        private boolean loggingEnabled;
        private List<RequestHandler> requestHandlers;
        private ExecutorService service;
        private RequestTransformer transformer;

        public Builder(Context context2) {
            if (context2 == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context2.getApplicationContext();
        }

        public Builder defaultBitmapConfig(Config config) {
            if (config == null) {
                throw new IllegalArgumentException("Bitmap config must not be null.");
            }
            this.defaultBitmapConfig = config;
            return this;
        }

        public Builder downloader(Downloader downloader2) {
            if (downloader2 == null) {
                throw new IllegalArgumentException("Downloader must not be null.");
            } else if (this.downloader != null) {
                throw new IllegalStateException("Downloader already set.");
            } else {
                this.downloader = downloader2;
                return this;
            }
        }

        public Builder executor(ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("Executor service must not be null.");
            } else if (this.service != null) {
                throw new IllegalStateException("Executor service already set.");
            } else {
                this.service = executorService;
                return this;
            }
        }

        public Builder memoryCache(Cache cache2) {
            if (cache2 == null) {
                throw new IllegalArgumentException("Memory cache must not be null.");
            } else if (this.cache != null) {
                throw new IllegalStateException("Memory cache already set.");
            } else {
                this.cache = cache2;
                return this;
            }
        }

        public Builder listener(Listener listener2) {
            if (listener2 == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            } else if (this.listener != null) {
                throw new IllegalStateException("Listener already set.");
            } else {
                this.listener = listener2;
                return this;
            }
        }

        public Builder requestTransformer(RequestTransformer requestTransformer) {
            if (requestTransformer == null) {
                throw new IllegalArgumentException("Transformer must not be null.");
            } else if (this.transformer != null) {
                throw new IllegalStateException("Transformer already set.");
            } else {
                this.transformer = requestTransformer;
                return this;
            }
        }

        public Builder addRequestHandler(RequestHandler requestHandler) {
            if (requestHandler == null) {
                throw new IllegalArgumentException("RequestHandler must not be null.");
            }
            if (this.requestHandlers == null) {
                this.requestHandlers = new ArrayList();
            }
            if (this.requestHandlers.contains(requestHandler)) {
                throw new IllegalStateException("RequestHandler already registered.");
            }
            this.requestHandlers.add(requestHandler);
            return this;
        }

        @Deprecated
        public Builder debugging(boolean z) {
            return indicatorsEnabled(z);
        }

        public Builder indicatorsEnabled(boolean z) {
            this.indicatorsEnabled = z;
            return this;
        }

        public Builder loggingEnabled(boolean z) {
            this.loggingEnabled = z;
            return this;
        }

        public Picasso build() {
            Context context2 = this.context;
            if (this.downloader == null) {
                this.downloader = Utils.createDefaultDownloader(context2);
            }
            if (this.cache == null) {
                this.cache = new LruCache(context2);
            }
            if (this.service == null) {
                this.service = new PicassoExecutorService();
            }
            if (this.transformer == null) {
                this.transformer = RequestTransformer.IDENTITY;
            }
            Stats stats = new Stats(this.cache);
            Context context3 = context2;
            Dispatcher dispatcher = new Dispatcher(context3, this.service, Picasso.HANDLER, this.downloader, this.cache, stats);
            Picasso picasso = new Picasso(context3, dispatcher, this.cache, this.listener, this.transformer, this.requestHandlers, stats, this.defaultBitmapConfig, this.indicatorsEnabled, this.loggingEnabled);
            return picasso;
        }
    }

    static class CleanupThread extends Thread {
        private final Handler handler;
        private final ReferenceQueue<Object> referenceQueue;

        CleanupThread(ReferenceQueue<Object> referenceQueue2, Handler handler2) {
            this.referenceQueue = referenceQueue2;
            this.handler = handler2;
            setDaemon(true);
            setName("Picasso-refQueue");
        }

        public void run() {
            Process.setThreadPriority(10);
            while (true) {
                try {
                    RequestWeakReference requestWeakReference = (RequestWeakReference) this.referenceQueue.remove(1000);
                    Message obtainMessage = this.handler.obtainMessage();
                    if (requestWeakReference != null) {
                        obtainMessage.what = 3;
                        obtainMessage.obj = requestWeakReference.action;
                        this.handler.sendMessage(obtainMessage);
                    } else {
                        obtainMessage.recycle();
                    }
                } catch (InterruptedException unused) {
                    return;
                } catch (Exception e) {
                    this.handler.post(new Runnable() {
                        public void run() {
                            throw new RuntimeException(e);
                        }
                    });
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void shutdown() {
            interrupt();
        }
    }

    public interface Listener {
        void onImageLoadFailed(Picasso picasso, Uri uri, Exception exc);
    }

    public enum LoadedFrom {
        MEMORY(-16711936),
        DISK(-16776961),
        NETWORK(SupportMenu.CATEGORY_MASK);
        
        final int debugColor;

        private LoadedFrom(int i) {
            this.debugColor = i;
        }
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH
    }

    public interface RequestTransformer {
        public static final RequestTransformer IDENTITY = new RequestTransformer() {
            public final Request transformRequest(Request request) {
                return request;
            }
        };

        Request transformRequest(Request request);
    }

    Picasso(Context context2, Dispatcher dispatcher2, Cache cache2, Listener listener2, RequestTransformer requestTransformer2, List<RequestHandler> list, Stats stats2, Config config, boolean z, boolean z2) {
        this.context = context2;
        this.dispatcher = dispatcher2;
        this.cache = cache2;
        this.listener = listener2;
        this.requestTransformer = requestTransformer2;
        this.defaultBitmapConfig = config;
        ArrayList arrayList = new ArrayList((list != null ? list.size() : 0) + 7);
        arrayList.add(new ResourceRequestHandler(context2));
        if (list != null) {
            arrayList.addAll(list);
        }
        arrayList.add(new ContactsPhotoRequestHandler(context2));
        arrayList.add(new MediaStoreRequestHandler(context2));
        arrayList.add(new ContentStreamRequestHandler(context2));
        arrayList.add(new AssetRequestHandler(context2));
        arrayList.add(new FileRequestHandler(context2));
        arrayList.add(new NetworkRequestHandler(dispatcher2.downloader, stats2));
        this.requestHandlers = Collections.unmodifiableList(arrayList);
        this.stats = stats2;
        this.targetToAction = new WeakHashMap();
        this.targetToDeferredRequestCreator = new WeakHashMap();
        this.indicatorsEnabled = z;
        this.loggingEnabled = z2;
        this.referenceQueue = new ReferenceQueue<>();
        this.cleanupThread = new CleanupThread(this.referenceQueue, HANDLER);
        this.cleanupThread.start();
    }

    public void cancelRequest(ImageView imageView) {
        cancelExistingRequest(imageView);
    }

    public void cancelRequest(Target target) {
        cancelExistingRequest(target);
    }

    public void cancelRequest(RemoteViews remoteViews, int i) {
        cancelExistingRequest(new RemoteViewsTarget(remoteViews, i));
    }

    public void cancelTag(Object obj) {
        Utils.checkMain();
        ArrayList arrayList = new ArrayList(this.targetToAction.values());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Action action = (Action) arrayList.get(i);
            if (action.getTag().equals(obj)) {
                cancelExistingRequest(action.getTarget());
            }
        }
    }

    public void pauseTag(Object obj) {
        this.dispatcher.dispatchPauseTag(obj);
    }

    public void resumeTag(Object obj) {
        this.dispatcher.dispatchResumeTag(obj);
    }

    public RequestCreator load(Uri uri) {
        return new RequestCreator(this, uri, 0);
    }

    public RequestCreator load(String str) {
        if (str == null) {
            return new RequestCreator(this, null, 0);
        }
        if (str.trim().length() != 0) {
            return load(Uri.parse(str));
        }
        throw new IllegalArgumentException("Path must not be empty.");
    }

    public RequestCreator load(File file) {
        if (file == null) {
            return new RequestCreator(this, null, 0);
        }
        return load(Uri.fromFile(file));
    }

    public RequestCreator load(int i) {
        if (i != 0) {
            return new RequestCreator(this, null, i);
        }
        throw new IllegalArgumentException("Resource ID must not be zero.");
    }

    public void invalidate(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri == null");
        }
        this.cache.clearKeyUri(uri.toString());
    }

    public void invalidate(String str) {
        if (str == null) {
            throw new IllegalArgumentException("path == null");
        }
        invalidate(Uri.parse(str));
    }

    public void invalidate(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        invalidate(Uri.fromFile(file));
    }

    @Deprecated
    public boolean isDebugging() {
        return areIndicatorsEnabled() && isLoggingEnabled();
    }

    @Deprecated
    public void setDebugging(boolean z) {
        setIndicatorsEnabled(z);
    }

    public void setIndicatorsEnabled(boolean z) {
        this.indicatorsEnabled = z;
    }

    public boolean areIndicatorsEnabled() {
        return this.indicatorsEnabled;
    }

    public void setLoggingEnabled(boolean z) {
        this.loggingEnabled = z;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public StatsSnapshot getSnapshot() {
        return this.stats.createSnapshot();
    }

    public void shutdown() {
        if (this == singleton) {
            throw new UnsupportedOperationException("Default singleton instance cannot be shutdown.");
        } else if (!this.shutdown) {
            this.cache.clear();
            this.cleanupThread.shutdown();
            this.stats.shutdown();
            this.dispatcher.shutdown();
            for (DeferredRequestCreator cancel : this.targetToDeferredRequestCreator.values()) {
                cancel.cancel();
            }
            this.targetToDeferredRequestCreator.clear();
            this.shutdown = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public List<RequestHandler> getRequestHandlers() {
        return this.requestHandlers;
    }

    /* access modifiers changed from: 0000 */
    public Request transformRequest(Request request) {
        Request transformRequest = this.requestTransformer.transformRequest(request);
        if (transformRequest != null) {
            return transformRequest;
        }
        StringBuilder sb = new StringBuilder("Request transformer ");
        sb.append(this.requestTransformer.getClass().getCanonicalName());
        sb.append(" returned null for ");
        sb.append(request);
        throw new IllegalStateException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public void defer(ImageView imageView, DeferredRequestCreator deferredRequestCreator) {
        this.targetToDeferredRequestCreator.put(imageView, deferredRequestCreator);
    }

    /* access modifiers changed from: 0000 */
    public void enqueueAndSubmit(Action action) {
        Object target = action.getTarget();
        if (!(target == null || this.targetToAction.get(target) == action)) {
            cancelExistingRequest(target);
            this.targetToAction.put(target, action);
        }
        submit(action);
    }

    /* access modifiers changed from: 0000 */
    public void submit(Action action) {
        this.dispatcher.dispatchSubmit(action);
    }

    /* access modifiers changed from: 0000 */
    public Bitmap quickMemoryCacheCheck(String str) {
        Bitmap bitmap = this.cache.get(str);
        if (bitmap != null) {
            this.stats.dispatchCacheHit();
        } else {
            this.stats.dispatchCacheMiss();
        }
        return bitmap;
    }

    /* access modifiers changed from: 0000 */
    public void complete(BitmapHunter bitmapHunter) {
        Action action = bitmapHunter.getAction();
        List<Action> actions = bitmapHunter.getActions();
        boolean z = true;
        boolean z2 = actions != null && !actions.isEmpty();
        if (action == null && !z2) {
            z = false;
        }
        if (z) {
            Uri uri = bitmapHunter.getData().uri;
            Exception exception = bitmapHunter.getException();
            Bitmap result = bitmapHunter.getResult();
            LoadedFrom loadedFrom = bitmapHunter.getLoadedFrom();
            if (action != null) {
                deliverAction(result, loadedFrom, action);
            }
            if (z2) {
                int size = actions.size();
                for (int i = 0; i < size; i++) {
                    deliverAction(result, loadedFrom, actions.get(i));
                }
            }
            if (!(this.listener == null || exception == null)) {
                this.listener.onImageLoadFailed(this, uri, exception);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void resumeAction(Action action) {
        Bitmap quickMemoryCacheCheck = MemoryPolicy.shouldReadFromMemoryCache(action.memoryPolicy) ? quickMemoryCacheCheck(action.getKey()) : null;
        if (quickMemoryCacheCheck != null) {
            deliverAction(quickMemoryCacheCheck, LoadedFrom.MEMORY, action);
            if (this.loggingEnabled) {
                String logId = action.request.logId();
                StringBuilder sb = new StringBuilder("from ");
                sb.append(LoadedFrom.MEMORY);
                Utils.log("Main", "completed", logId, sb.toString());
            }
        } else {
            enqueueAndSubmit(action);
            if (this.loggingEnabled) {
                Utils.log("Main", "resumed", action.request.logId());
            }
        }
    }

    private void deliverAction(Bitmap bitmap, LoadedFrom loadedFrom, Action action) {
        if (!action.isCancelled()) {
            if (!action.willReplay()) {
                this.targetToAction.remove(action.getTarget());
            }
            if (bitmap == null) {
                action.error();
                if (this.loggingEnabled) {
                    Utils.log("Main", "errored", action.request.logId());
                }
            } else if (loadedFrom == null) {
                throw new AssertionError("LoadedFrom cannot be null.");
            } else {
                action.complete(bitmap, loadedFrom);
                if (this.loggingEnabled) {
                    Utils.log("Main", "completed", action.request.logId(), "from ".concat(String.valueOf(loadedFrom)));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void cancelExistingRequest(Object obj) {
        Utils.checkMain();
        Action remove = this.targetToAction.remove(obj);
        if (remove != null) {
            remove.cancel();
            this.dispatcher.dispatchCancel(remove);
        }
        if (obj instanceof ImageView) {
            DeferredRequestCreator remove2 = this.targetToDeferredRequestCreator.remove((ImageView) obj);
            if (remove2 != null) {
                remove2.cancel();
            }
        }
    }

    public static Picasso with(Context context2) {
        if (singleton == null) {
            synchronized (Picasso.class) {
                if (singleton == null) {
                    singleton = new Builder(context2).build();
                }
            }
        }
        return singleton;
    }

    public static void setSingletonInstance(Picasso picasso) {
        synchronized (Picasso.class) {
            if (singleton != null) {
                throw new IllegalStateException("Singleton instance already exists.");
            }
            singleton = picasso;
        }
    }
}
