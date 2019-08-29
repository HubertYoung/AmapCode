package com.alipay.mobile.common.transport.concurrent;

import android.annotation.TargetApi;
import android.content.Context;
import com.ali.auth.third.login.LoginConstants;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.taobao.accs.utl.UtilityImpl;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class NetThreadPoolExeFactory {
    protected static final String PREFIX_THREAD_NAME = "HttpManager.HttpWorker";

    public class NetThreadFactory implements ThreadFactory {
        private final AtomicInteger a = new AtomicInteger(1);
        private String b;

        public NetThreadFactory(String threadName) {
            this.b = "HttpManager.HttpWorker#" + threadName + MetaRecord.LOG_SEPARATOR;
        }

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, this.b + this.a.getAndIncrement());
            setThreadPriority(thread);
            return thread;
        }

        /* access modifiers changed from: protected */
        public void setThreadPriority(Thread thread) {
            thread.setPriority(1);
        }
    }

    public class ThreadPoolConfig {
        int corePoolSize;
        RejectedExecutionHandler handler;
        long keepAliveTime;
        int maximumPoolSize;
        String netType = "";
        ThreadFactory threadFactory;
        TimeUnit timeunit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue;

        public String toString() {
            String str;
            StringBuilder append = new StringBuilder("ThreadPoolConfig{netType=").append(this.netType).append(", corePoolSize=").append(this.corePoolSize).append(", maximumPoolSize=").append(this.maximumPoolSize).append(", keepAliveTime=").append(this.keepAliveTime).append("s , workQueueSize=").append(this.workQueue != null ? Integer.valueOf(this.workQueue.size()) : "0").append(", threadFactory=").append(this.threadFactory != null ? this.threadFactory.getClass().getName() : " is null ").append(", handler=");
            if (this.handler != null) {
                str = this.handler.getClass().getName();
            } else {
                str = "is null";
            }
            return append.append(str).append('}').toString();
        }
    }

    @TargetApi(9)
    public static ActThreadPoolExecutor getBgThreadPool(Context context, RejectedExecutionHandler handler) {
        ThreadPoolConfig config = getBgThreadPoolConfig(context);
        if (handler == null) {
            config.handler = new DiscardOldestPolicy();
        } else {
            config.handler = handler;
        }
        return e(config);
    }

    @TargetApi(9)
    public static ActThreadPoolExecutor getImgThreadPool(Context context, RejectedExecutionHandler handler) {
        ThreadPoolConfig config = getImgThreadPoolConfig(context);
        if (handler == null) {
            config.handler = new DiscardOldestPolicy();
        } else {
            config.handler = handler;
        }
        return e(config);
    }

    @TargetApi(9)
    public static ActThreadPoolExecutor getAmrThreadPool(Context context, RejectedExecutionHandler handler) {
        ThreadPoolConfig config = getAmrThreadPoolConfig(context);
        if (handler == null) {
            config.handler = new DiscardOldestPolicy();
        } else {
            config.handler = handler;
        }
        return e(config);
    }

    @TargetApi(9)
    public static ActThreadPoolExecutor getAmrUrgentThreadPool(Context context, RejectedExecutionHandler handler) {
        ThreadPoolConfig config = getAmrUrgentThreadPoolConfig(context);
        if (handler == null) {
            config.handler = new DiscardOldestPolicy();
        } else {
            config.handler = handler;
        }
        return e(config);
    }

    @TargetApi(9)
    public static ActThreadPoolExecutor getFgThreadPool(Context context, RejectedExecutionHandler handler) {
        ThreadPoolConfig config = getFgThreadPoolConfig();
        if (handler == null) {
            config.handler = new DiscardOldestPolicy();
        } else {
            config.handler = handler;
        }
        return e(config);
    }

    @TargetApi(9)
    public static ActThreadPoolExecutor getH5ThreadPool(Context context, RejectedExecutionHandler handler) {
        ThreadPoolConfig config = getH5ThreadPoolConfig();
        if (handler == null) {
            config.handler = new DiscardOldestPolicy();
        } else {
            config.handler = handler;
        }
        return e(config);
    }

    @TargetApi(9)
    public static ActThreadPoolExecutor getUrgentThreadPool(Context context, RejectedExecutionHandler handler) {
        ThreadPoolConfig config = getUrgentThreadPoolConfig();
        if (handler == null) {
            config.handler = new DiscardOldestPolicy();
        } else {
            config.handler = handler;
        }
        return e(config);
    }

    @TargetApi(9)
    public static ActThreadPoolExecutor getFgMultimediaThreadPool(Context context, RejectedExecutionHandler handler) {
        ThreadPoolConfig config = getFgMultimediaThreadPoolConfig();
        if (handler == null) {
            config.handler = new DiscardOldestPolicy();
        } else {
            config.handler = handler;
        }
        return e(config);
    }

    public static final ThreadPoolConfig getFgThreadPoolConfig() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
        threadPoolConfig.netType = "all";
        threadPoolConfig.threadFactory = new NetThreadFactory("fg");
        setPoolSize(threadPoolConfig, 10);
        threadPoolConfig.keepAliveTime = 30;
        threadPoolConfig.timeunit = TimeUnit.SECONDS;
        threadPoolConfig.workQueue = new ArrayBlockingQueue(30);
        return threadPoolConfig;
    }

    public static final ThreadPoolConfig getH5ThreadPoolConfig() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
        threadPoolConfig.netType = "all";
        threadPoolConfig.threadFactory = new NetThreadFactory(LoginConstants.H5_LOGIN);
        setPoolSize(threadPoolConfig, 10);
        threadPoolConfig.keepAliveTime = 30;
        threadPoolConfig.timeunit = TimeUnit.SECONDS;
        threadPoolConfig.workQueue = new LinkedBlockingDeque();
        return threadPoolConfig;
    }

    public static final ThreadPoolConfig getBgThreadPoolConfig(Context context) {
        int netType = NetworkUtils.getNetworkType(context);
        ThreadPoolConfig bgThreadConfig = a(netType);
        bgThreadConfig.threadFactory = new NetThreadFactory("bg");
        TransportConfigureManager configureManager = TransportConfigureManager.getInstance();
        switch (netType) {
            case 1:
                int countOf2g = configureManager.getIntValue(TransportConfigureItem.BG_2G_THREAD_COUNT);
                LogCatUtil.debug("NetThreadPoolExeFactory", "bg 2g threadCount=[" + countOf2g + "]");
                setPoolSize(bgThreadConfig, countOf2g);
                break;
            case 2:
                int countOf3g = configureManager.getIntValue(TransportConfigureItem.BG_3G_THREAD_COUNT);
                LogCatUtil.debug("NetThreadPoolExeFactory", "bg 3g threadCount=[" + countOf3g + "]");
                setPoolSize(bgThreadConfig, countOf3g);
                break;
            default:
                LogCatUtil.info("NetThreadPoolExeFactory", "No default case!");
                break;
        }
        return bgThreadConfig;
    }

    public static final ThreadPoolConfig getImgThreadPoolConfig(Context context) {
        int netType = NetworkUtils.getNetworkType(context);
        ThreadPoolConfig threadPoolConfig = a(netType);
        threadPoolConfig.threadFactory = new NetThreadFactory("img");
        TransportConfigureManager config = TransportConfigureManager.getInstance();
        switch (netType) {
            case 1:
                int threadCount2 = config.getIntValue(TransportConfigureItem.IMG_2G_THREAD_COUNT);
                LogCatUtil.debug("NetThreadPoolExeFactory", "img 2g threadCount=[" + threadCount2 + "]");
                setPoolSize(threadPoolConfig, threadCount2);
                break;
            case 2:
                int threadCount22 = config.getIntValue(TransportConfigureItem.IMG_2G_THREAD_COUNT);
                LogCatUtil.debug("NetThreadPoolExeFactory", "img 3g threadCount=[" + threadCount22 + "]");
                setPoolSize(threadPoolConfig, threadCount22);
                break;
            case 3:
            case 4:
                int threadCount23 = config.getIntValue(TransportConfigureItem.IMG_4G_THREAD_COUNT);
                LogCatUtil.debug("NetThreadPoolExeFactory", "img 4g/wifi threadCount=[" + threadCount23 + "]");
                setPoolSize(threadPoolConfig, threadCount23);
                break;
        }
        return threadPoolConfig;
    }

    protected static void setPoolSize(ThreadPoolConfig threadPoolConfig, int threadCount2) {
        threadPoolConfig.corePoolSize = threadCount2;
        threadPoolConfig.maximumPoolSize = threadCount2;
    }

    public static final ThreadPoolConfig getAmrThreadPoolConfig(Context context) {
        int netType = NetworkUtils.getNetworkType(context);
        ThreadPoolConfig threadPoolConfig = a(netType);
        threadPoolConfig.threadFactory = new NetThreadFactory("amr");
        switch (netType) {
            case 1:
                LogCatUtil.debug("NetThreadPoolExeFactory", "amr 2g threadCount=[1]");
                setPoolSize(threadPoolConfig, 1);
                break;
            case 2:
                LogCatUtil.debug("NetThreadPoolExeFactory", "amr 3g threadCount=[1]");
                threadPoolConfig.corePoolSize = 1;
                threadPoolConfig.maximumPoolSize = 2;
                break;
            case 3:
            case 4:
                LogCatUtil.debug("NetThreadPoolExeFactory", "amr 4g/WI-FI threadCount=[2]");
                setPoolSize(threadPoolConfig, 2);
                break;
        }
        return threadPoolConfig;
    }

    public static final ThreadPoolConfig getAmrUrgentThreadPoolConfig(Context context) {
        ThreadPoolConfig threadPoolConfig = getAmrThreadPoolConfig(context);
        threadPoolConfig.threadFactory = new NetThreadFactory("amr-urgent");
        return threadPoolConfig;
    }

    public static final ThreadPoolConfig getUrgentThreadPoolConfig() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
        threadPoolConfig.netType = "all";
        threadPoolConfig.threadFactory = new NetThreadFactory("urgent");
        setPoolSize(threadPoolConfig, 3);
        threadPoolConfig.keepAliveTime = 20;
        threadPoolConfig.timeunit = TimeUnit.SECONDS;
        threadPoolConfig.workQueue = new ArrayBlockingQueue(30);
        return threadPoolConfig;
    }

    public static final ThreadPoolConfig getFgMultimediaThreadPoolConfig() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
        threadPoolConfig.netType = "all";
        threadPoolConfig.threadFactory = new NetThreadFactory("media");
        setPoolSize(threadPoolConfig, 10);
        threadPoolConfig.keepAliveTime = 5;
        threadPoolConfig.timeunit = TimeUnit.SECONDS;
        threadPoolConfig.workQueue = new ArrayBlockingQueue(30);
        return threadPoolConfig;
    }

    private static ThreadPoolConfig a(int netType) {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
        switch (netType) {
            case 1:
                d(threadPoolConfig);
                break;
            case 2:
                c(threadPoolConfig);
                break;
            case 3:
                a(threadPoolConfig);
                break;
            case 4:
                b(threadPoolConfig);
                break;
            default:
                c(threadPoolConfig);
                break;
        }
        return threadPoolConfig;
    }

    private static void a(ThreadPoolConfig threadPoolConfig) {
        threadPoolConfig.netType = "wifi";
        setPoolSize(threadPoolConfig, 5);
        threadPoolConfig.keepAliveTime = 5;
        threadPoolConfig.workQueue = new LinkedBlockingDeque();
    }

    private static void b(ThreadPoolConfig threadPoolConfig) {
        threadPoolConfig.netType = UtilityImpl.NET_TYPE_4G;
        setPoolSize(threadPoolConfig, 3);
        threadPoolConfig.keepAliveTime = 5;
        threadPoolConfig.workQueue = new LinkedBlockingDeque();
    }

    private static void c(ThreadPoolConfig threadPoolConfig) {
        threadPoolConfig.netType = UtilityImpl.NET_TYPE_3G;
        threadPoolConfig.corePoolSize = 1;
        threadPoolConfig.maximumPoolSize = 2;
        threadPoolConfig.keepAliveTime = 30;
        threadPoolConfig.workQueue = new LinkedBlockingDeque();
    }

    private static void d(ThreadPoolConfig threadPoolConfig) {
        threadPoolConfig.netType = UtilityImpl.NET_TYPE_2G;
        setPoolSize(threadPoolConfig, 1);
        threadPoolConfig.keepAliveTime = 60;
        threadPoolConfig.workQueue = new LinkedBlockingDeque();
    }

    @TargetApi(9)
    private static ActThreadPoolExecutor e(ThreadPoolConfig config) {
        LogCatUtil.debug("NetThreadPoolExeFactory", "createThreadPoolExeByConfig.   " + config.toString());
        ActThreadPoolExecutor executor = new ActThreadPoolExecutor(config.corePoolSize, config.maximumPoolSize, config.keepAliveTime, config.timeunit, config.workQueue, config.threadFactory, config.handler);
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }
}
