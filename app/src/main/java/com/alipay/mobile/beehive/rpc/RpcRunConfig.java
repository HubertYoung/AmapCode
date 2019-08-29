package com.alipay.mobile.beehive.rpc;

import com.alipay.mobile.beehive.rpc.ext.CacheProcessor;
import com.alipay.mobile.beehive.rpc.lifecycle.RpcRunnerLifeCycleCallback;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;

public class RpcRunConfig {
    public static final String CONTENT_EXIST = "content_exist";
    public static final String CONTENT_NOT_EXIST = "content_not_exist";
    public static final String CONTENT_UNKNOWN = "content_unknown";
    public static final String EXCEPTION_ALL = "exception_all";
    public static final String EXCEPTION_NONE = "exception_none";
    public static final String EXCEPTION_NOT_CATCH = "exception_not_catch";
    public static final String EXCEPTION_NO_OVERFLOW = "exception_no_overflow";
    public static final String EXCEPTION_NO_SPANNER_OVERFLOW_ON_CONTENT = "exception_no_spanner_overflow_on_content";
    public static final String EXCEPTION_ONLY_OVERFLOW = "exception_only_overflow";
    public static final String THREAD_AUTO = "thread_auto";
    public static final String THREAD_NEW_BG = "thread_new_bg";
    public boolean autoHideContentOnRun = false;
    public boolean autoModifyLoadingOnCache = true;
    public BaseRpcResultProcessor baseRpcResultProcessor = null;
    public String cacheKey = "";
    public CacheMode cacheMode = CacheMode.CACHE_AND_RPC;
    public CacheProcessor<?> cacheProcessor;
    public Object cacheType;
    public String contentMode = CONTENT_UNKNOWN;
    public String exceptionMode = EXCEPTION_ALL;
    public int flowTipHolderViewId;
    public int flowTipViewBgColor;
    private boolean isUseContentEmptyCheck = false;
    public RpcRunnerLifeCycleCallback lifeCycleCallback;
    public LoadingMode loadingMode = LoadingMode.CANCELABLE_LOADING;
    public String loadingText;
    public String operationType;
    public Object responseType = null;
    public boolean showFlowTipOnEmpty = false;
    public boolean showNetError = false;
    public boolean showWarn = false;
    public ScheduleType taskScheduleType = ScheduleType.RPC;
    public String threadMode = THREAD_AUTO;

    public static RpcRunConfig createFullGetConfig(String cacheKey2, Class<?> cacheType2) {
        RpcRunConfig config = new RpcRunConfig();
        config.loadingMode = LoadingMode.CANCELABLE_EXIT_LOADING;
        config.cacheMode = CacheMode.CACHE_AND_RPC;
        config.cacheKey = cacheKey2;
        config.cacheType = cacheType2;
        config.contentMode = CONTENT_NOT_EXIST;
        config.showFlowTipOnEmpty = true;
        return config;
    }

    public static RpcRunConfig createFullGetConfig() {
        return createFullGetConfig("", null);
    }

    public static RpcRunConfig createPartGetConfig(String cacheKey2, Class<?> cacheType2) {
        RpcRunConfig config = new RpcRunConfig();
        config.loadingMode = LoadingMode.TITLEBAR_LOADING;
        config.contentMode = CONTENT_EXIST;
        config.showFlowTipOnEmpty = false;
        config.cacheMode = CacheMode.CACHE_AND_RPC;
        config.cacheKey = cacheKey2;
        config.cacheType = cacheType2;
        return config;
    }

    public static RpcRunConfig createPartGetConfig() {
        return createPartGetConfig("", null);
    }

    public static RpcRunConfig createPostConfig() {
        RpcRunConfig config = new RpcRunConfig();
        config.loadingMode = LoadingMode.CANCELABLE_LOADING;
        config.contentMode = CONTENT_EXIST;
        config.cacheMode = CacheMode.NONE;
        return config;
    }

    public static RpcRunConfig createBackgroundConfig() {
        RpcRunConfig config = new RpcRunConfig();
        config.loadingMode = LoadingMode.UNAWARE;
        config.exceptionMode = EXCEPTION_NONE;
        config.cacheMode = CacheMode.NONE;
        return config;
    }

    public RpcRunConfig withCache(CacheMode cm, String cacheKey2, Class<?> cacheType2) {
        this.cacheMode = cm;
        this.cacheKey = cacheKey2;
        this.cacheType = cacheType2;
        return this;
    }

    public void setIsUseContentEmptyCheck(boolean v) {
        this.isUseContentEmptyCheck = v;
    }

    public boolean isUseContentEmptyCheck() {
        return this.isUseContentEmptyCheck;
    }

    public String toString() {
        try {
            return MiscUtil.safeToString(this, "loadingMode=" + MiscUtil.safeToString(this.loadingMode) + ",showNetError=" + this.showNetError + ",showWarn=" + this.showWarn + ",showFlowTipOnEmpty=" + this.showFlowTipOnEmpty + ",cacheMode=" + MiscUtil.safeToString(this.cacheMode) + ",cacheKey=" + this.cacheKey + ",flowTipHolderViewId=" + this.flowTipHolderViewId + ",exceptionMode=" + this.exceptionMode);
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return super.toString();
        }
    }
}
