package com.alipay.mobile.beehive.rpc;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.mobile.beehive.api.BeehiveService;
import com.alipay.mobile.beehive.api.UserSceneExecutor;
import com.alipay.mobile.beehive.api.UserSceneExecutor.Interceptor;
import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.beehive.eventbus.ThreadMode;
import com.alipay.mobile.beehive.rpc.ext.CacheProcessor;
import com.alipay.mobile.beehive.rpc.ext.DefaultCacheProcessor;
import com.alipay.mobile.beehive.rpc.lifecycle.RpcRunnerLifeCycleManager;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.beehive.util.ServiceUtil;
import com.alipay.mobile.beehive.util.TimeCostCounter;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.RpcInvokeContext;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.framework.service.ext.SimpleRpcService;
import com.squareup.wire.Message;
import com.squareup.wire.Wire;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RpcRunner {
    private volatile Runnable deferredRpcCallbackTimeoutRunnable;
    private volatile List<Runnable> deferredRpcCallbacks;
    private volatile boolean hasDeferredRpcCallbackRun;
    private boolean isCheckRunnableClass;
    private final int[] monitorExceptionCodes = {0, 6, 13, 9, 15, 17, 18};
    private RpcService rpcService;
    private RpcTask<?> rpcTask;
    private SimpleRpcService simpleRpcInvokeService;
    private volatile Interceptor spannerInterceptor;
    private Handler uiHandler;

    public <T> RpcRunner(RpcRunnable<T> r, RpcSubscriber<T> subscriber) {
        init(new RpcRunConfig(), r, subscriber, null);
    }

    public <T> RpcRunner(RpcRunConfig config, RpcRunnable<T> r, RpcSubscriber<T> subscriber) {
        init(config, r, subscriber, null);
    }

    public <T> RpcRunner(RpcRunConfig config, RpcRunnable<T> r, RpcSubscriber<T> subscriber, BaseRpcResultProcessor p) {
        init(config, r, subscriber, p);
    }

    public <T> RpcRunner(RpcTask<T> task) {
        this.rpcTask = task;
        onAfterInit();
    }

    private <T> void init(RpcRunConfig config, RpcRunnable<T> r, RpcSubscriber<T> subscriber, BaseRpcResultProcessor p) {
        this.rpcTask = new RpcTask<>(config, r, subscriber, p);
        onAfterInit();
    }

    private void onAfterInit() {
        this.isCheckRunnableClass = false;
        this.uiHandler = new Handler(Looper.getMainLooper());
    }

    public static <T> void run(RpcRunConfig config, RpcRunnable<T> rpcRunnable, RpcSubscriber<T> rpcSubscriber, Object... params) {
        innerRunWithProcessor(config, rpcRunnable, rpcSubscriber, null, params);
    }

    public static <T> void runWithProcessor(RpcRunConfig config, RpcRunnable<T> rpcRunnable, RpcSubscriber<T> rpcSubscriber, BaseRpcResultProcessor resultProcessor, Object... params) {
        innerRunWithProcessor(config, rpcRunnable, rpcSubscriber, resultProcessor, params);
    }

    public static <T> T runSync(RpcRunConfig config, RpcRunnable<T> rpcRunnable, RpcSubscriber<T> rpcSubscriber, Object... params) {
        RpcTask task = new RpcTask(config, rpcRunnable, rpcSubscriber, null);
        task.setParams(params);
        return new RpcRunner(task).startSync(task);
    }

    public static <T> RpcRunner runSimple(RpcRunConfig config, RpcSubscriber<T> rpcSubscriber, Object request) {
        return innerRunWithProcessor(config, null, rpcSubscriber, null, request);
    }

    private static <T> RpcRunner innerRunWithProcessor(RpcRunConfig config, RpcRunnable<T> rpcRunnable, RpcSubscriber<T> rpcSubscriber, BaseRpcResultProcessor resultProcessor, Object... params) {
        if (resultProcessor == null) {
            resultProcessor = config.baseRpcResultProcessor;
        }
        RpcRunner runner = new RpcRunner(config, rpcRunnable, rpcSubscriber, resultProcessor);
        runner.start(params);
        return runner;
    }

    public void start(Object... params) {
        if (!(this.rpcTask == null || params == null)) {
            this.rpcTask.setParams(params);
        }
        start(this.rpcTask);
    }

    public <T> void start(RpcTask<T> task) {
        innerStartAsync(task);
    }

    public <T> T startSync(RpcTask<T> task) {
        return innerStartSync(task);
    }

    private <T> void innerStartAsync(final RpcTask<T> task) {
        changeTask(task);
        Runnable r = new Runnable() {
            public final void run() {
                task.setCacheFinishStatus(-1);
                if (task.shouldLoadWithCache()) {
                    RpcUtil.executeInBgThread((Runnable) new Runnable() {
                        public final void run() {
                            RpcRunner.this.loadRpcCache(task);
                        }
                    }, task.getRunConfig().taskScheduleType);
                }
                RpcRunner.this.invokeRpc(task);
            }
        };
        if (TextUtils.equals(RpcRunConfig.THREAD_NEW_BG, task.getRunConfig().threadMode)) {
            RpcUtil.executeInBgThread(r, task.getRunConfig().taskScheduleType);
        } else if (Looper.getMainLooper() == Looper.myLooper()) {
            RpcUtil.executeInBgThread(r, task.getRunConfig().taskScheduleType);
        } else {
            r.run();
        }
    }

    private <T> T innerStartSync(RpcTask<T> task) {
        changeTask(task);
        return invokeRpc(task);
    }

    private void changeTask(RpcTask<?> task) {
        checkTask(task);
        registerSubscriber(task);
        if (this.rpcTask != task) {
            this.rpcTask = task;
        }
    }

    private <T> void registerSubscriber(RpcTask<T> task) {
        if (task.getSubscriber() != null) {
            EventBusManager.getInstance().registerRaw(task.getSubscriber(), ThreadMode.UI, RpcUtil.getRpcEventName(task));
        }
    }

    private <T> void checkTask(RpcTask<T> task) {
        if (task == null) {
            throw new IllegalArgumentException("task must not be null");
        }
        if (task.getSubscriber() == null) {
            DebugUtil.log((String) RpcConstant.TAG, RpcUtil.getRpcRunnableLogString(task) + " 空subscriber, 请注意将不处理任何rpc回调");
        }
        if (task.getRunnable() != null || !TextUtils.isEmpty(task.getRunConfig().operationType)) {
            if (task.getRpcResultProcessor() == null) {
                task.setRpcResultProcessor(new DefaultRpcResultProcessor());
            }
            if (DebugUtil.isDebug() && this.isCheckRunnableClass && task.getRunnable() != null) {
                if (checkIsAnonymousClass(task.getRunnable().getClass())) {
                    throw new IllegalArgumentException("for avoid memory leak reason, runnable must not be anonymous Class");
                } else if (checkIsNotStaticInnerClass(task.getRunnable().getClass())) {
                    throw new IllegalArgumentException("for avoid memory leak reason, runnable must not be none static inner Class");
                }
            }
        } else {
            throw new IllegalArgumentException("task.runnable 和 task.runConfig.operationType 不能同时为空");
        }
    }

    /* access modifiers changed from: private */
    public <T> T invokeRpc(final RpcTask<T> task) {
        Exception exception = null;
        Object result = null;
        RpcRunnerContext context = new RpcRunnerContext();
        context.setRpcRunner(this);
        boolean isCatchException = !TextUtils.equals(task.getRunConfig().exceptionMode, RpcRunConfig.EXCEPTION_NOT_CATCH);
        try {
            this.hasDeferredRpcCallbackRun = false;
            this.deferredRpcCallbackTimeoutRunnable = null;
            if (this.deferredRpcCallbacks == null) {
                this.deferredRpcCallbacks = new ArrayList();
            }
            this.deferredRpcCallbacks.clear();
            task.setRpcFinishStatus(-1);
            postRpcEvent(RpcConstant.RPC_START, null, null, task);
            if (task.getSubscriber() != null) {
                task.getSubscriber().setTaskOnBgCallback(task);
            }
            if (!task.shouldLoadWithCache()) {
                postRpcEvent(RpcConstant.RPC_SHOW_LOADING, null, null, task);
            } else {
                this.uiHandler.postDelayed(new Runnable() {
                    public final void run() {
                        RpcRunner.this.postRpcEvent(RpcConstant.RPC_SHOW_LOADING, null, null, task);
                    }
                }, 200);
            }
            addSpannerInterceptor();
            if (task.getRunnable() == null) {
                String operationType = task.getRunConfig().operationType;
                if (TextUtils.isEmpty(operationType)) {
                    throw new IllegalArgumentException("RpcRunConfig must set rpcOperationType if RpcRunnable is null");
                } else if (task.getRunConfig().responseType == null) {
                    throw new IllegalArgumentException("RpcRunConfig.rpcResultClass must not be null if RpcRunnable is null");
                } else {
                    createSimpleRpcServiceIfNull();
                    onBeforeRpc(context);
                    Object[] requestArray = task.getParams();
                    Object rType = getResponseType(task);
                    result = executeRpcUseSimpleService(operationType, requestArray, RpcUtil.checkIsPbFormat(requestArray[0], rType), rType);
                }
            } else {
                onBeforeRpc(context);
                result = task.getRunnable().execute(task.getParams());
            }
            onRpcResult(context, result);
            try {
                removeSpannerInterceptor();
                handleRpcResult(result, null, task);
                onAfterRpc(context);
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            }
        } catch (Exception e) {
            exception = e;
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) exception);
            monitorRpcException(e);
            onRpcException(context, e);
            try {
                removeSpannerInterceptor();
                handleRpcResult(result, isCatchException ? exception : null, task);
                onAfterRpc(context);
            } catch (Exception ex2) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex2);
            }
        } catch (Throwable th) {
            try {
                removeSpannerInterceptor();
                if (!isCatchException) {
                    exception = null;
                }
                handleRpcResult(result, exception, task);
                onAfterRpc(context);
            } catch (Exception ex3) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex3);
            }
            throw th;
        }
        if (exception == null || !(exception instanceof RpcException) || isCatchException) {
            return result;
        }
        throw ((RpcException) exception);
    }

    private Object executeRpcUseSimpleService(String operationType, Object[] requestArray, boolean isPbFormat, Object resultType) {
        if (isPbFormat) {
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "SimpleRpcService use pb");
            Object req = requestArray[0];
            byte[] requestDataInByteArray = null;
            if (req instanceof Message) {
                requestDataInByteArray = ((Message) req).toByteArray();
            }
            return new Wire((Class<?>[]) new Class[0]).parseFrom(this.simpleRpcInvokeService.executeRPC(operationType, requestDataInByteArray, (Map<String, String>) null), (Class) resultType);
        }
        LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "SimpleRpcService use json");
        String requestStr = requestArray == null ? "" : JSON.toJSONString(requestArray);
        if (TextUtils.isEmpty(requestStr)) {
            requestStr = "[{}]";
        }
        String resultStr = this.simpleRpcInvokeService.executeRPC(operationType, requestStr, (Map<String, String>) null);
        LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, "resultString is json: " + resultStr.length());
        if (resultType instanceof Class) {
            return JSON.parseObject(resultStr, (Class) resultType);
        }
        if (resultType instanceof TypeReference) {
            return JSON.parseObject(resultStr, (TypeReference) resultType, new Feature[0]);
        }
        return null;
    }

    private void addSpannerInterceptor() {
        try {
            BeehiveService bs = (BeehiveService) ServiceUtil.getServiceByInterface(BeehiveService.class);
            if (bs != null) {
                UserSceneExecutor e = bs.getUserSceneExecutor();
                if (e != null) {
                    this.spannerInterceptor = new Interceptor() {
                        public final boolean handleThrottle() {
                            return false;
                        }
                    };
                    e.addThrottleInterceptor(this.spannerInterceptor);
                }
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
        }
    }

    private void removeSpannerInterceptor() {
        try {
            BeehiveService bs = (BeehiveService) ServiceUtil.getServiceByInterface(BeehiveService.class);
            if (bs != null) {
                UserSceneExecutor e = bs.getUserSceneExecutor();
                if (e != null && this.spannerInterceptor != null) {
                    e.removeThrottleInterceptor(this.spannerInterceptor);
                    this.spannerInterceptor = null;
                }
            }
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
        }
    }

    private void monitorRpcException(Exception e) {
        if (e instanceof RpcException) {
            RpcException re = (RpcException) e;
            if (re.getCode() == 10) {
                LoggerFactory.getMonitorLogger().mtBizReport("BEEHIVE_RPC", "RPC_DESERIALIZE", String.valueOf(re.getCode()), RpcUtil.buildLogExtInfo(this));
            } else if (isMonitorException(re)) {
                Map bs = RpcUtil.buildLogExtInfo(this);
                bs.put("subBizName", "CLIENT_ERROR");
                bs.put("code", String.valueOf(re.getCode()));
                LoggerFactory.getMonitorLogger().exception(e, "BEEHIVE_RPC", bs);
            }
        }
    }

    private boolean isMonitorException(RpcException re) {
        for (int c : this.monitorExceptionCodes) {
            if (re.getCode() == c) {
                return true;
            }
        }
        return false;
    }

    private <T> void handleRpcResult(final T result, final Exception ex, final RpcTask<T> task) {
        boolean isException;
        if (ex != null) {
            isException = true;
        } else {
            isException = false;
        }
        deferToCacheFinishIf(isException, task, new Runnable() {
            public final void run() {
                RpcRunner.this.postRpcEvent(RpcConstant.RPC_FINISH_START, result, ex, task);
            }
        });
        if (isException) {
            task.setRpcFinishStatus(2);
            deferToCacheFinishIf(true, task, new Runnable() {
                public final void run() {
                    RpcRunner.this.postRpcEvent(RpcConstant.RPC_EXCEPTION, null, ex, task);
                }
            });
            if (task.getSubscriber() != null) {
                task.getSubscriber().onExceptionAtBg(ex, task);
            }
            loadCacheIfConfigAfterRpc(task);
        } else if (task.getRpcResultProcessor() == null) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "rpcResultProcessor意外为空，请检查是否手动设置过!");
        } else if (result != null) {
            handleFollowAction(task, result);
            if (isResultSuccess(task, result)) {
                task.setRpcFinishStatus(0);
                if (task.getSubscriber() != null) {
                    task.getSubscriber().onSuccessAtBg(result);
                    task.getSubscriber().onDataSuccessAtBg(result);
                }
                handleRpcSuccess(result, task);
            } else {
                task.setRpcFinishStatus(1);
                if (task.getSubscriber() != null) {
                    task.getSubscriber().onFailAtBg(result);
                }
                handleRpcFail(result, task);
                loadCacheIfConfigAfterRpc(task);
            }
        } else {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, RpcUtil.getRpcRunnableLogString(task) + ":非异常, 但RPC result为空!!");
        }
        deferToCacheFinishIf(isException, task, new Runnable() {
            public final void run() {
                RpcRunner.this.postRpcEvent(RpcConstant.RPC_FINISH_END, result, ex, task);
            }
        });
        if (isException && task.shouldLoadWithCache() && !task.isCacheFinished() && this.deferredRpcCallbacks != null && !this.deferredRpcCallbacks.isEmpty()) {
            this.deferredRpcCallbackTimeoutRunnable = new Runnable() {
                public final void run() {
                    RpcRunner.this.runDeferredRpcCallbacks();
                }
            };
            this.uiHandler.postDelayed(this.deferredRpcCallbackTimeoutRunnable, 500);
            DebugUtil.log((String) RpcConstant.TAG, (String) "post deferred rpc callback timeout guard(500ms)");
        }
        task.setIsInitRun(false);
    }

    private void handleRpcSuccess(Object result, RpcTask task) {
        postRpcEvent(RpcConstant.RPC_SUCCESS, result, null, task);
        if (RpcUtil.isConfigUseCache(task.getRunConfig())) {
            saveRpcCache(result, task);
        }
    }

    private void handleRpcFail(Object result, RpcTask task) {
        postRpcEvent(RpcConstant.RPC_FAIL, result, null, task);
    }

    private <T> void loadCacheIfConfigAfterRpc(RpcTask<T> task) {
        RpcRunConfig rc = task.getRunConfig();
        if (RpcUtil.isConfigUseCache(rc) && rc.cacheMode == CacheMode.RPC_OR_CACHE) {
            loadRpcCache(task);
        }
    }

    /* access modifiers changed from: private */
    public <T> void loadRpcCache(RpcTask<T> task) {
        CacheProcessor cp = task.getRunConfig().cacheProcessor;
        if (cp == null) {
            cp = createDefaultCacheProcessor(task);
        }
        Object result = null;
        try {
            result = cp.load(task.getRunConfig().cacheKey);
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
        }
        if (task.isRpcFinishSuccess()) {
            DebugUtil.log((String) RpcConstant.TAG, "rpc先于cache返回, cache回调都会不执行!" + task.getTaskId());
            return;
        }
        postRpcEvent(RpcConstant.RPC_CACHE_FINISH_START, result, null, task);
        if (isResultSuccess(task, result)) {
            task.setCacheFinishStatus(0);
            if (task.getSubscriber() != null) {
                task.getSubscriber().onCacheSuccessAtBg(result);
                task.getSubscriber().onDataSuccessAtBg(result);
            }
            postRpcEvent(RpcConstant.RPC_CACHE_SUCCESS, result, null, task);
        } else {
            task.setCacheFinishStatus(1);
            if (result == null) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, RpcUtil.getRpcRunnableLogString(task) + " rpc缓存为空");
            } else {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, RpcUtil.getRpcRunnableLogString(task) + "%s rpc缓存不为空, 但isRpcSuccess判断为false");
            }
            if (task.getSubscriber() != null) {
                task.getSubscriber().onCacheFailAtBg(result);
            }
            postRpcEvent(RpcConstant.RPC_CACHE_FAIL, result, null, task);
        }
        postRpcEvent(RpcConstant.RPC_CACHE_FINISH_END, result, null, task);
        postRpcEvent(RpcConstant.RPC_SHOW_LOADING, null, null, task);
        runDeferredRpcCallbacks();
    }

    private void deferToCacheFinishIf(boolean flag, RpcTask<?> task, Runnable r) {
        if (!flag || !task.shouldLoadWithCache() || task.isCacheFinished()) {
            r.run();
        } else {
            this.deferredRpcCallbacks.add(r);
        }
    }

    /* access modifiers changed from: private */
    public void runDeferredRpcCallbacks() {
        try {
            if (!this.hasDeferredRpcCallbackRun) {
                if (this.deferredRpcCallbacks != null && !this.deferredRpcCallbacks.isEmpty()) {
                    for (Runnable run : this.deferredRpcCallbacks) {
                        run.run();
                        DebugUtil.log((String) RpcConstant.TAG, (String) "rpc先于cache返回, 执行defer callback ");
                    }
                }
                if (this.deferredRpcCallbackTimeoutRunnable != null) {
                    this.uiHandler.removeCallbacks(this.deferredRpcCallbackTimeoutRunnable);
                }
                this.hasDeferredRpcCallbackRun = true;
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) e);
        }
    }

    private <T> boolean isResultSuccess(RpcTask<T> task, T result) {
        try {
            return task.getRpcResultProcessor().isSuccess(result);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) e);
            return false;
        }
    }

    private void saveRpcCache(Object result, RpcTask task) {
        CacheProcessor cp = task.getRunConfig().cacheProcessor;
        if (cp == null) {
            cp = createDefaultCacheProcessor(task);
        }
        cp.save(task.getRunConfig().cacheKey, result);
    }

    /* access modifiers changed from: private */
    public void postRpcEvent(String status, Object result, Exception ex, RpcTask task) {
        RpcEventHelper.post(status, result, ex, task);
    }

    private boolean checkIsNotStaticInnerClass(Class<?> v) {
        return v.getEnclosingClass() != null && !Modifier.isStatic(v.getModifiers());
    }

    private boolean checkIsAnonymousClass(Class<?> v) {
        return v.isAnonymousClass();
    }

    private CacheProcessor createDefaultCacheProcessor(RpcTask task) {
        return new DefaultCacheProcessor(getResponseType(task));
    }

    private Object getResponseType(RpcTask task) {
        RpcRunConfig config = task.getRunConfig();
        return config.responseType != null ? config.responseType : config.cacheType;
    }

    private synchronized SimpleRpcService createSimpleRpcServiceIfNull() {
        if (this.simpleRpcInvokeService == null) {
            this.rpcService = (RpcService) ServiceUtil.getServiceByInterface(RpcService.class);
            this.simpleRpcInvokeService = (SimpleRpcService) this.rpcService.getRpcProxy(SimpleRpcService.class);
        }
        return this.simpleRpcInvokeService;
    }

    public RpcTask<?> getRpcTask() {
        return this.rpcTask;
    }

    public RpcRunConfig getRpcRunConfig() {
        if (this.rpcTask != null) {
            return this.rpcTask.getRunConfig();
        }
        return null;
    }

    public RpcSubscriber getRpcSubscriber() {
        if (this.rpcTask != null) {
            return this.rpcTask.getSubscriber();
        }
        return null;
    }

    public String getSequenceId() {
        if (this.rpcTask == null) {
            return "";
        }
        return this.rpcTask.getSequenceId();
    }

    public RpcInvokeContext getRpcInvokeContext() {
        if (this.simpleRpcInvokeService != null) {
            return this.rpcService.getRpcInvokeContext(this.simpleRpcInvokeService);
        }
        return null;
    }

    private void handleFollowAction(RpcTask task, Object result) {
        TimeCostCounter.start();
        task.setFollowAction("");
        if (result != null) {
            Object action = RpcUtil.getObjectByReflectWithBase(result, RpcConstant.RPC_RESULT_FOLLOW_ACTION_WITH_BEEHIVE);
            if (action == null) {
                action = RpcUtil.getObjectByReflectWithBase(result, RpcConstant.RPC_RESULT_FOLLOW_ACTION);
            }
            if (action != null && (action instanceof String) && !TextUtils.isEmpty((String) action)) {
                task.setFollowAction((String) action);
            }
        }
        LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "RpcRunner handleFollowAction consume: " + TimeCostCounter.end());
    }

    private void onBeforeRpc(RpcRunnerContext context) {
        TimeCostCounter.start();
        if (getRpcRunConfig().lifeCycleCallback != null) {
            getRpcRunConfig().lifeCycleCallback.onBeforeRpc(context);
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "RpcRunner RpcRunConfig onBeforeRpc consume: " + TimeCostCounter.end());
        }
        RpcRunnerLifeCycleManager.getInstance().onBeforeRpc(context);
        LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "RpcRunner global lifeCycle onBeforeRpc consume: " + TimeCostCounter.end());
    }

    private void onAfterRpc(RpcRunnerContext context) {
        TimeCostCounter.start();
        if (getRpcRunConfig().lifeCycleCallback != null) {
            getRpcRunConfig().lifeCycleCallback.onAfterRpc(context);
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "RpcRunner RpcRunConfig onAfterRpc consume: " + TimeCostCounter.end());
        }
        RpcRunnerLifeCycleManager.getInstance().onAfterRpc(context);
        LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "RpcRunner global lifeCycle onAfterRpc consume: " + TimeCostCounter.end());
    }

    private void onRpcResult(RpcRunnerContext context, Object result) {
        TimeCostCounter.start();
        if (getRpcRunConfig().lifeCycleCallback != null) {
            getRpcRunConfig().lifeCycleCallback.onRpcResult(context, result);
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "RpcRunner RpcRunConfig onRpcResult consume: " + TimeCostCounter.end());
        }
        RpcRunnerLifeCycleManager.getInstance().onRpcResult(context, result);
        LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "RpcRunner global lifeCycle onRpcResult consume: " + TimeCostCounter.end());
    }

    private void onRpcException(RpcRunnerContext context, Exception e) {
        if (getRpcRunConfig().lifeCycleCallback != null) {
            getRpcRunConfig().lifeCycleCallback.onRpcException(context, e);
        }
        RpcRunnerLifeCycleManager.getInstance().onRpcException(context, e);
    }
}
