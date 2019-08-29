package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.DrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.GifDrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.MemoryCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.QueryCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.MemoryExpiredStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.MemoryMonitorConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.MemoryTrimStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MemoryMonitor {
    public static final KnockOutExpiredTask KNOCK_OUT_MEM_COMMAND = new KnockOutExpiredTask(0);
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "MMMemoryMonitor");
    /* access modifiers changed from: private */
    public static final String b = (AppUtils.getApplicationContext().getPackageName() + ".monitor.action.BACKGROUND_DELAY_EVENT");
    private static final AtomicBoolean c = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public static final ScheduledExecutorService d = Executors.newScheduledThreadPool(1, new ThreadFactory() {
        public final Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "mm-knockOutExpiredMem");
            thread.setPriority(1);
            return thread;
        }
    });
    public static ScheduledFuture mScheduledFuture;

    public static class BackgroundBroadReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (!preCheck(intent)) {
                MemoryMonitor.a.d("onReceive illegal action: " + (intent != null ? intent.getAction() : "null intent") + ", background: " + ActivityHelper.isBackgroundRunning(), new Object[0]);
                return;
            }
            int level = intent.getIntExtra(H5PermissionManager.level, 0);
            MemoryMonitorConfig config = MemoryMonitor.getMemoryConfig();
            MemoryMonitor.a.d("onReceive level: " + level, new Object[0]);
            switch (level) {
                case 1:
                    MemoryMonitor.stopKnockOutMemTask();
                    handleLevel1Event(config);
                    return;
                case 2:
                    handleLevel2Event(config);
                    return;
                case 3:
                    handleLevel3Event(config);
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: protected */
        public boolean preCheck(Intent intent) {
            return ActivityHelper.isBackgroundRunning() && intent != null && MemoryMonitor.b.equals(intent.getAction()) && intent.getExtras() != null;
        }

        /* access modifiers changed from: protected */
        public void handleLevel1Event(MemoryMonitorConfig config) {
            MemoryMonitor.a.d("handleLevel1Event", new Object[0]);
            CacheContext.get().debugInfo();
            MemoryMonitor.a.d("handleLevel1Event start", new Object[0]);
            if (CacheContext.get().getMemCache(CacheContext.COMMON_NONE_BUSINESS) != null) {
                CacheContext.get().getMemCache(CacheContext.COMMON_NONE_BUSINESS).trimToSize(16777216);
            }
            trimMemory(config.getMemoryTrimStrategyForLevel1());
            if (GifDrawableCache.getInstance().getRealCache() != null) {
                GifDrawableCache.getInstance().getRealCache().trimToSize(5);
                GifDrawableCache.getInstance().getRealCache().debugInfo();
            }
        }

        /* access modifiers changed from: protected */
        public void handleLevel2Event(MemoryMonitorConfig config) {
            MemoryMonitor.a.d("handleLevel2Event", new Object[0]);
            trimMemory(config.getMemoryTrimStrategyForLevel2());
            if (GifDrawableCache.getInstance().getRealCache() != null) {
                GifDrawableCache.getInstance().getRealCache().trimToSize(0);
                GifDrawableCache.getInstance().getRealCache().debugInfo();
            }
            if (DrawableCache.get().getRealCache() != null) {
                DrawableCache.get().getRealCache().trimToSize(6);
                DrawableCache.get().getRealCache().debugInfo();
            }
            QueryCacheManager.getInstance().trimToSize(10);
        }

        /* access modifiers changed from: protected */
        public void trimMemory(List<MemoryTrimStrategy> strategies) {
            MemoryMonitor.a.d("trimMemory strategies: " + strategies, new Object[0]);
            if (strategies != null) {
                for (MemoryTrimStrategy strategy : strategies) {
                    MemoryCache cache = MemoryMonitor.b(strategy.memType);
                    if (cache != null) {
                        cache.trimToSize((int) strategy.maxSize);
                    }
                }
            }
            CacheContext.get().debugInfo();
        }

        /* access modifiers changed from: protected */
        public void handleLevel3Event(MemoryMonitorConfig config) {
            MemoryMonitor.a.d("handleLevel3Event", new Object[0]);
            trimMemory(config.getMemoryTrimStrategyForLevel3());
            if (DrawableCache.get().getRealCache() != null) {
                DrawableCache.get().getRealCache().trimToSize(0);
                DrawableCache.get().getRealCache().debugInfo();
            }
            QueryCacheManager.getInstance().trimToSize(5);
        }
    }

    private static class KnockOutExpiredTask implements Runnable {
        private KnockOutExpiredTask() {
        }

        /* synthetic */ KnockOutExpiredTask(byte b) {
            this();
        }

        public void run() {
            if (!ActivityHelper.isBackgroundRunning()) {
                MemoryMonitorConfig config = MemoryMonitor.getMemoryConfig();
                if (config != null) {
                    if (config.enableKnockOutExpiredMem == 1) {
                        List strategies = config.getExpiredStrategies();
                        MemoryMonitor.a.d("KnockOutExpiredTask strategies: " + strategies, new Object[0]);
                        if (strategies != null && !strategies.isEmpty()) {
                            for (MemoryExpiredStrategy strategy : strategies) {
                                if (strategy.memoryType == 4) {
                                    GifDrawableCache.getInstance().knockOutExpired(strategy.aliveTime);
                                } else {
                                    MemoryCache cache = MemoryMonitor.b(strategy.memoryType);
                                    if (cache != null) {
                                        cache.knockOutExpired(strategy.aliveTime);
                                    }
                                }
                            }
                        }
                    }
                    MemoryMonitor.mScheduledFuture = MemoryMonitor.d.schedule(MemoryMonitor.KNOCK_OUT_MEM_COMMAND, config.knockOutPeriod, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    public static void startMonitor() {
        if (c.compareAndSet(false, true)) {
            a.d("startMonitor enter!", new Object[0]);
            TaskScheduleManager.get().execute(new Runnable() {
                public final void run() {
                    MemoryMonitor.a.d("startMonitor registerReceiver!", new Object[0]);
                    try {
                        AppUtils.getApplicationContext().registerReceiver(new BackgroundBroadReceiver(), new IntentFilter(MemoryMonitor.b));
                    } catch (Throwable t) {
                        MemoryMonitor.a.e(t, "startMonitor registerReceiver!", new Object[0]);
                    }
                }
            });
            startKnockOutMemTask();
        }
    }

    public static void stopKnockOutMemTask() {
        a.d("stopKnockOutMemTask mScheduledFuture: " + mScheduledFuture, new Object[0]);
        if (mScheduledFuture != null) {
            mScheduledFuture.cancel(true);
            mScheduledFuture = null;
        }
    }

    public static void startKnockOutMemTask() {
        a.d("startKnockOutMemTask mScheduledFuture: " + mScheduledFuture, new Object[0]);
        if (mScheduledFuture != null) {
            mScheduledFuture.cancel(true);
        }
        mScheduledFuture = d.schedule(KNOCK_OUT_MEM_COMMAND, 5, TimeUnit.MINUTES);
    }

    public static MemoryMonitorConfig getMemoryConfig() {
        String conf = ConfigManager.getInstance().getStringValue(ConfigConstants.MEMORY_MONITOR_CONFIG, null);
        if (TextUtils.isEmpty(conf)) {
            return new MemoryMonitorConfig();
        }
        try {
            return (MemoryMonitorConfig) JSON.parseObject(conf, MemoryMonitorConfig.class);
        } catch (Throwable th) {
            return new MemoryMonitorConfig();
        }
    }

    /* access modifiers changed from: private */
    public static MemoryCache b(int memType) {
        switch (memType) {
            case 1:
                return CacheContext.get().getCommonMemCache();
            case 2:
                return CacheContext.get().getMemCache();
            case 3:
                return CacheContext.get().getSoftReferenceMemCache();
            default:
                return null;
        }
    }
}
