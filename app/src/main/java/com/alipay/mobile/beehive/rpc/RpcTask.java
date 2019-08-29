package com.alipay.mobile.beehive.rpc;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;

public class RpcTask<ResultType> {
    public static final int FINISH_EXCEPTION = 2;
    public static final int FINISH_FAIL = 1;
    public static final int FINISH_NOT_YET = -1;
    public static final int FINISH_SUCCESS = 0;
    private volatile int cacheFinishStatus;
    private volatile String followAction;
    private boolean isInitRun = true;
    private Object[] params;
    private volatile int rpcFinishStatus;
    private BaseRpcResultProcessor rpcResultProcessor;
    private RpcRunConfig runConfig;
    private RpcRunnable<ResultType> runnable;
    private RpcSubscriber<ResultType> subscriber;
    private String taskId;

    public RpcTask(RpcRunConfig config, RpcRunnable<ResultType> r, RpcSubscriber<ResultType> subscriber2, BaseRpcResultProcessor processor) {
        this.runConfig = config;
        this.runnable = r;
        this.subscriber = subscriber2;
        this.rpcResultProcessor = processor;
        this.taskId = createTaskId();
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String createTaskId() {
        return String.valueOf(System.currentTimeMillis()) + AUScreenAdaptTool.PREFIX_ID + Integer.toHexString(hashCode());
    }

    public String getSequenceId() {
        return this.taskId;
    }

    public Object[] getParams() {
        return this.params;
    }

    public void setParams(Object[] params2) {
        this.params = params2;
    }

    public RpcRunConfig getRunConfig() {
        return this.runConfig;
    }

    public void setRpcResultProcessor(BaseRpcResultProcessor rpcResultProcessor2) {
        this.rpcResultProcessor = rpcResultProcessor2;
    }

    public RpcRunnable<ResultType> getRunnable() {
        return this.runnable;
    }

    public RpcSubscriber<ResultType> getSubscriber() {
        return this.subscriber;
    }

    public BaseRpcResultProcessor getRpcResultProcessor() {
        return this.rpcResultProcessor;
    }

    public void setRunConfig(RpcRunConfig runConfig2) {
        this.runConfig = runConfig2;
    }

    public void setSubscriber(RpcSubscriber<ResultType> subscriber2) {
        this.subscriber = subscriber2;
    }

    public boolean isRpcFinishSuccess() {
        return this.rpcFinishStatus == 0;
    }

    public boolean isRpcFinishException() {
        return this.rpcFinishStatus == 2;
    }

    public void setRpcFinishStatus(int v) {
        this.rpcFinishStatus = v;
    }

    public void setCacheFinishStatus(int v) {
        this.cacheFinishStatus = v;
    }

    public boolean isCacheFinished() {
        return this.cacheFinishStatus == 0 || this.cacheFinishStatus == 1;
    }

    public boolean isInitRun() {
        return this.isInitRun;
    }

    public void setIsInitRun(boolean isInitRun2) {
        this.isInitRun = isInitRun2;
    }

    public boolean shouldLoadWithCache() {
        return RpcUtil.isConfigUseCache(this.runConfig) && this.runConfig.cacheMode == CacheMode.CACHE_AND_RPC && this.isInitRun;
    }

    public String getFollowAction() {
        return this.followAction;
    }

    public void setFollowAction(String followAction2) {
        this.followAction = followAction2;
    }
}
