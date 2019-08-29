package com.alipay.mobile.beehive.rpc;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.beehive.eventbus.IEventSubscriber;
import com.alipay.mobile.beehive.rpc.action.ActionProcessor;
import com.alipay.mobile.beehive.rpc.action.DefaultActionProcessor;
import com.alipay.mobile.beehive.rpc.action.DefaultShowTypeProcessor;
import com.alipay.mobile.beehive.rpc.action.ShowTypeProcessor;
import com.alipay.mobile.beehive.rpc.action.TriggerActionCallback;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.beehive.rpc.model.OverflowConfig;
import com.alipay.mobile.beehive.util.DebugUtil;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.alipay.mobile.beehive.util.MultiThreadUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.framework.app.ui.ActivityResponsable;
import java.util.Arrays;
import java.util.List;

public abstract class RpcSubscriber<ResultType> implements IEventSubscriber, RpcRunnable<ResultType>, TriggerActionCallback {
    private ActionProcessor actionProcessor;
    private boolean canShowLoading;
    private RpcEvent<ResultType> eventData;
    private volatile boolean isCancelPending;
    private volatile boolean isLoadDataSuccess;
    private List<String> logStatus;
    private RpcUiProcessor rpcUiProcessor;
    private ShowTypeProcessor showTypeProcessor;
    private volatile RpcTask<ResultType> taskOnBgCallback;

    public RpcSubscriber() {
        init(null, null);
    }

    public RpcSubscriber(Object act) {
        init(act, null);
    }

    public RpcSubscriber(ActivityResponsable ar) {
        init(ar, null);
    }

    public RpcSubscriber(Fragment f) {
        Activity a = f.getActivity();
        if (a != null) {
            init(a, f);
        }
    }

    private void init(Object host, Fragment f) {
        if (f != null) {
            this.rpcUiProcessor = new RpcUiProcessor(f);
        } else if (host != null && (host instanceof Activity)) {
            this.rpcUiProcessor = new RpcUiProcessor((Activity) host);
        }
        if (this.actionProcessor == null) {
            this.actionProcessor = new DefaultActionProcessor(this);
        }
        if (this.showTypeProcessor == null) {
            this.showTypeProcessor = new DefaultShowTypeProcessor();
        }
        resetStatus();
        this.isCancelPending = false;
        this.logStatus = Arrays.asList(new String[]{RpcConstant.RPC_SUCCESS, RpcConstant.RPC_FAIL, RpcConstant.RPC_EXCEPTION, RpcConstant.RPC_CACHE_SUCCESS, RpcConstant.RPC_CACHE_FAIL});
    }

    public ResultType execute(Object... params) {
        return null;
    }

    public void onEvent(String eventName, Object data) {
        if (!(data instanceof RpcEvent)) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, "onEvent data is not RpcEventData or is Null, eventName=" + MiscUtil.safeToString(eventName));
            return;
        }
        RpcEvent inData = (RpcEvent) data;
        if (inData.rpcTask == null || inData.rpcTask.getSubscriber() == null) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "onEvent call: eventData || eventData.rpcTask || eventData.rpcTask.subscriber is null");
            return;
        }
        RpcTask task = inData.rpcTask;
        if (task.getSubscriber() != this) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, "onEvent call: taskId(" + task.getTaskId() + ") is conflict, subscriber not equal");
            return;
        }
        try {
            this.eventData = (RpcEvent) data;
            LoadingMode loadingMode = autoChangeLoadingMode(task.getRunConfig());
            logOnEventStatus(task, this.eventData.status);
            if (TextUtils.equals(this.eventData.status, RpcConstant.RPC_START)) {
                innerStart(task);
                onStart();
            }
            if (this.isCancelPending) {
                DebugUtil.log((String) RpcConstant.TAG, (String) "已收到过取消事件, 什么都不做");
                return;
            }
            if (TextUtils.equals(this.eventData.status, RpcConstant.RPC_SHOW_LOADING)) {
                innerShowLoadingIfCan(loadingMode);
            } else if (TextUtils.equals(this.eventData.status, RpcConstant.RPC_FINISH_START)) {
                if (this.rpcUiProcessor != null && shouldShowLoading(task)) {
                    this.rpcUiProcessor.dismissProgressDialog();
                    if (loadingMode == LoadingMode.TITLEBAR_LOADING) {
                        this.rpcUiProcessor.dismissTitleBarLoading();
                    }
                }
                onFinishStart();
            } else if (TextUtils.equals(this.eventData.status, RpcConstant.RPC_FINISH_END)) {
                innerFinish(task);
                onFinishEnd();
            } else if (TextUtils.equals(this.eventData.status, RpcConstant.RPC_SUCCESS)) {
                hideFlowTipView();
                this.isLoadDataSuccess = true;
                onSuccess(this.eventData.result);
                onDataSuccess(this.eventData.result, true);
            } else if (TextUtils.equals(this.eventData.status, RpcConstant.RPC_FAIL)) {
                hideFlowTipView();
                try {
                    LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "onFail start");
                    onFail(this.eventData.result);
                    LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "onFail end");
                } catch (Exception ex) {
                    LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
                }
            } else if (TextUtils.equals(this.eventData.status, RpcConstant.RPC_EXCEPTION)) {
                onException(this.eventData.exception, this.eventData.rpcTask);
            }
            handleCacheEvent(this.eventData);
        } catch (Exception ex2) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex2);
        }
    }

    private void innerStart(final RpcTask<?> task) {
        RpcRunConfig config = task.getRunConfig();
        LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "runConfig=" + config.toString());
        this.isCancelPending = false;
        this.canShowLoading = true;
        if (this.rpcUiProcessor != null) {
            this.rpcUiProcessor.setFlowTipHolderId(config.flowTipHolderViewId);
            if (config.flowTipViewBgColor != 0) {
                this.rpcUiProcessor.setFlowTipViewBgColor(config.flowTipViewBgColor);
            }
            if (config.loadingText != null) {
                this.rpcUiProcessor.setLoadingText(config.loadingText);
            }
            if (this.rpcUiProcessor.getRetryRunnable() == null) {
                this.rpcUiProcessor.setRetryRunnable(new Runnable() {
                    public final void run() {
                        new RpcRunner(task).start(task.getParams());
                    }
                });
            }
        } else {
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "非ui的subscriber, rpcUiProcessor=null");
        }
        setContentVisibilityIfNeed(task, 8);
    }

    private void innerFinish(final RpcTask<?> task) {
        this.canShowLoading = false;
        this.taskOnBgCallback = null;
        unregisterFromEventBus();
        MultiThreadUtil.runOnUiThread(new Runnable() {
            public final void run() {
                RpcSubscriber.this.setContentVisibilityIfNeed(task, 0);
            }
        });
    }

    private void innerShowLoadingIfCan(LoadingMode loadingMode) {
        if (this.canShowLoading) {
            this.canShowLoading = false;
            if (this.rpcUiProcessor == null) {
                return;
            }
            if (loadingMode == LoadingMode.BLOCK_LOADING) {
                this.rpcUiProcessor.showProgressDialog(false, null);
            } else if (loadingMode == LoadingMode.CANCELABLE_LOADING || loadingMode == LoadingMode.CANCELABLE_EXIT_LOADING) {
                this.rpcUiProcessor.showProgressDialog(true, new OnCancelListener() {
                    public final void onCancel(DialogInterface dialog) {
                        RpcSubscriber.this.cancelRpc();
                    }
                });
            } else if (loadingMode == LoadingMode.TITLEBAR_LOADING) {
                this.rpcUiProcessor.showTitleBarLoading();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    /* access modifiers changed from: protected */
    public void onFinishStart() {
    }

    /* access modifiers changed from: protected */
    public void onFinishEnd() {
    }

    /* access modifiers changed from: protected */
    public void onCancel() {
    }

    /* access modifiers changed from: protected */
    public void onSuccess(ResultType result) {
        RpcTask task = getRpcTask();
        if (task.getRpcResultProcessor().isEmpty(result)) {
            showEmptyView(task.getRpcResultProcessor().convertResultText(result));
        } else {
            processFollowAction(task, result);
        }
    }

    /* access modifiers changed from: protected */
    public void onFail(ResultType result) {
        LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "processFollowAction start");
        RpcTask task = getRpcTask();
        if (!processFollowAction(task, result)) {
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "processShowTypeAction start");
            if (processShowTypeAction(result)) {
                return;
            }
            if (this.rpcUiProcessor != null) {
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "rpcUiProcessor is not null");
                String tip = task.getRpcResultProcessor().convertResultText(result);
                if (shouldShowFlowTip(task, false)) {
                    LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "rpcUiProcessor show warn");
                    this.rpcUiProcessor.showWarn("", tip);
                    return;
                }
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "toast tip");
                if (!TextUtils.isEmpty(tip)) {
                    this.rpcUiProcessor.toast(tip, 0);
                } else {
                    LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "onFail 兜底显示toast失败,文案为空");
                }
            } else {
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "rpcUiProcessor is null");
            }
        }
    }

    private boolean processFollowAction(RpcTask task, ResultType result) {
        String action = task.getFollowAction();
        if (!TextUtils.isEmpty(action)) {
            return callFollowActionProcessor(result, action);
        }
        return false;
    }

    private boolean processShowTypeAction(ResultType result) {
        if (result == null || !RpcSettings.supportShowType || this.rpcUiProcessor == null || this.showTypeProcessor == null) {
            return false;
        }
        return this.showTypeProcessor.handleShowType(this.rpcUiProcessor, result);
    }

    private boolean callFollowActionProcessor(ResultType result, String action) {
        if (this.actionProcessor != null) {
            return this.actionProcessor.handleFollowAction(this.rpcUiProcessor, result, action);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onException(Exception ex, RpcTask task) {
        if (ex instanceof RpcException) {
            RpcException e = (RpcException) ex;
            if (e.getCode() == 24) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "异常为LOGIN_REFRESH_ERROR(24),不做任何处理");
                return;
            }
            String cm = task.getRunConfig().exceptionMode;
            if (TextUtils.equals(cm, RpcRunConfig.EXCEPTION_NONE) || task.getRunConfig().loadingMode == LoadingMode.UNAWARE) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "异常模式为:不处理任何异常(包括限流)");
            } else if (RpcUtil.isOverflowException(ex)) {
                if (!TextUtils.equals(cm, RpcRunConfig.EXCEPTION_NO_OVERFLOW)) {
                    handleOverflowException(e, task);
                } else {
                    LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "异常模式为:不处理限流,当前为限流异常,不处理");
                }
            } else if (!TextUtils.equals(cm, RpcRunConfig.EXCEPTION_ONLY_OVERFLOW)) {
                if (RpcUtil.isNetworkException(ex)) {
                    handleNetworkException(e, task);
                } else {
                    handleOtherException(e, task);
                }
                if (RpcUtil.isNetworkException(ex)) {
                    onNetworkException(ex, task);
                } else {
                    onNotNetworkException(ex, task);
                }
            }
        }
    }

    public void onFollowActionTrigger(Object rpcResult, FollowAction parentAction, String triggerType) {
    }

    /* access modifiers changed from: protected */
    public <T> void handleOverflowException(RpcException ex, RpcTask<T> task) {
        if (DebugUtil.isDebug()) {
            try {
                DebugUtil.log((String) RpcConstant.TAG, "rpc限流异常: " + JSON.toJSONString(ex));
            } catch (Exception e) {
            }
        }
        if (shouldShowFlowTip(task, false)) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, getExceptionLogStr("展示限流异常(空白页面)", true));
            showOverflowOnEmptyPage(ex);
            return;
        }
        LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, getExceptionLogStr("展示限流异常(有内容)", false));
        OverflowConfig config = OverflowConfig.fromRpcException(ex);
        if (!config.isSpanner || !TextUtils.equals(task.getRunConfig().exceptionMode, RpcRunConfig.EXCEPTION_NO_SPANNER_OVERFLOW_ON_CONTENT)) {
            showOverflowOnContentPage(config);
        }
    }

    public void showOverflowOnEmptyPage(RpcException ex) {
        if (this.rpcUiProcessor != null) {
            showOverflowOnEmptyPage(OverflowConfig.fromRpcException(ex));
        }
    }

    public void showOverflowOnContentPage(RpcException ex) {
        if (this.rpcUiProcessor != null) {
            showOverflowOnContentPage(OverflowConfig.fromRpcException(ex));
        }
    }

    /* access modifiers changed from: protected */
    public void showOverflowOnEmptyPage(OverflowConfig config) {
        if (this.rpcUiProcessor != null) {
            this.rpcUiProcessor.showOverflow(config.desc, config.subDesc, this.rpcUiProcessor.getRetryRunnable(), RpcUiProcessor.DEFAULT_STRING, config.waitTime, config.imgUrl);
        }
    }

    /* access modifiers changed from: protected */
    public void showOverflowOnContentPage(OverflowConfig config) {
        if (this.rpcUiProcessor != null) {
            if (config.showType == 1) {
                this.rpcUiProcessor.toast(config.desc, 1);
            } else if (config.showType == 2) {
                this.rpcUiProcessor.showOverflowDialog(config.desc, config.subDesc, this.rpcUiProcessor.getRetryRunnable(), config.waitTime, config.imgUrl);
            }
        }
    }

    /* access modifiers changed from: protected */
    public <T> void handleNetworkException(RpcException ex, RpcTask<T> task) {
        String tip;
        if (shouldShowFlowTip(task, true)) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, getExceptionLogStr("网络异常", true));
            if (this.rpcUiProcessor != null) {
                if (RpcUtil.isNetworkSlow(ex)) {
                    tip = this.rpcUiProcessor.getNetErrorSlowText();
                } else {
                    tip = this.rpcUiProcessor.getNetErrorText();
                }
                this.rpcUiProcessor.showNetworkError(tip);
                return;
            }
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "显示网络异常失败: rpcUiProcessor为空!");
            return;
        }
        LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, getExceptionLogStr("网络异常", false));
        throwExceptionAtBg(ex);
    }

    /* access modifiers changed from: protected */
    public <T> void handleOtherException(RpcException ex, RpcTask<T> task) {
        if (shouldShowFlowTip(task, false)) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, getExceptionLogStr("其它异常", true));
            if (this.rpcUiProcessor == null) {
                LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (String) "显示异常warn失败: rpcUiProcessor为空!");
            } else if (!TextUtils.isEmpty(ex.getMsg())) {
                this.rpcUiProcessor.showWarn(ex.getMsg(), "");
            } else {
                this.rpcUiProcessor.showWarn(null, null);
            }
        } else {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, getExceptionLogStr("其它异常", false));
            throwExceptionAtBg(ex);
        }
    }

    private String getExceptionLogStr(String label, boolean flag) {
        if (flag) {
            return label + "，界面无内容且设置了显示FlowTip, 内部处理";
        }
        return label + "，界面有内容或未设置显示FlowTip, 有内容状态展示";
    }

    /* access modifiers changed from: protected */
    public void throwExceptionAtBg(final RpcException ex) {
        RpcTask task = getRpcTask();
        Runnable r = new Runnable() {
            public final void run() {
                throw ex;
            }
        };
        if (task == null) {
            RpcUtil.executeInBgThread(r);
        } else {
            RpcUtil.executeInBgThread(r, task.getRunConfig().taskScheduleType);
        }
    }

    private LoadingMode autoChangeLoadingMode(RpcRunConfig config) {
        if (!this.isLoadDataSuccess || (config.loadingMode != LoadingMode.CANCELABLE_LOADING && config.loadingMode != LoadingMode.BLOCK_LOADING && config.loadingMode != LoadingMode.CANCELABLE_EXIT_LOADING)) {
            return config.loadingMode;
        }
        return LoadingMode.TITLEBAR_LOADING;
    }

    private boolean shouldShowFlowTip(RpcTask task, boolean isNetError) {
        if (task == null) {
            return false;
        }
        if (((isNetError ? task.getRunConfig().showNetError : task.getRunConfig().showWarn) || task.getRunConfig().showFlowTipOnEmpty || TextUtils.equals(task.getRunConfig().contentMode, RpcRunConfig.CONTENT_NOT_EXIST)) && !currentHasContent(task)) {
            return true;
        }
        return false;
    }

    private void hideFlowTipView() {
        if (this.rpcUiProcessor != null) {
            this.rpcUiProcessor.hideFlowTipViewIfShow();
        }
    }

    private boolean currentHasContent(RpcTask task) {
        if (this.isLoadDataSuccess) {
            return true;
        }
        if (!task.getRunConfig().isUseContentEmptyCheck()) {
            return false;
        }
        if (this.rpcUiProcessor == null || this.rpcUiProcessor.getActivity() == null || !this.rpcUiProcessor.isActivityContentVisible()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean hasContent(RpcTask task) {
        if (TextUtils.equals(task.getRunConfig().contentMode, RpcRunConfig.CONTENT_EXIST)) {
            return true;
        }
        return currentHasContent(task);
    }

    private boolean shouldShowLoading(RpcTask task) {
        LoadingMode lm = task.getRunConfig().loadingMode;
        return (lm == LoadingMode.SILENT || lm == LoadingMode.UNAWARE) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void onNetworkException(Exception ex, RpcTask task) {
    }

    /* access modifiers changed from: protected */
    public void onNotNetworkException(Exception ex, RpcTask task) {
    }

    private void handleCacheEvent(RpcEvent<ResultType> event) {
        if (TextUtils.equals(event.status, RpcConstant.RPC_CACHE_START)) {
            onCacheStart();
        } else if (TextUtils.equals(event.status, RpcConstant.RPC_CACHE_FINISH_START)) {
            onCacheFinishStart();
        } else if (TextUtils.equals(event.status, RpcConstant.RPC_CACHE_FINISH_END)) {
            onCacheFinishEnd();
        } else if (TextUtils.equals(event.status, RpcConstant.RPC_CACHE_SUCCESS)) {
            this.isLoadDataSuccess = true;
            onCacheSuccess(event.result);
            onDataSuccess(event.result, false);
        } else if (TextUtils.equals(event.status, RpcConstant.RPC_CACHE_FAIL)) {
            onCacheFail();
        }
    }

    /* access modifiers changed from: protected */
    public void onCacheStart() {
    }

    /* access modifiers changed from: protected */
    public void onCacheFinishStart() {
    }

    /* access modifiers changed from: protected */
    public void onCacheFinishEnd() {
    }

    /* access modifiers changed from: protected */
    public void onCacheSuccess(ResultType result) {
    }

    /* access modifiers changed from: protected */
    public void onCacheFail() {
    }

    /* access modifiers changed from: protected */
    public void onDataSuccess(ResultType result, boolean isFromRemoteRpc) {
    }

    /* access modifiers changed from: protected */
    public void onSuccessAtBg(ResultType result) {
    }

    /* access modifiers changed from: protected */
    public void onFailAtBg(ResultType result) {
    }

    /* access modifiers changed from: protected */
    public void onCacheSuccessAtBg(ResultType result) {
    }

    /* access modifiers changed from: protected */
    public void onDataSuccessAtBg(ResultType result) {
    }

    /* access modifiers changed from: protected */
    public void onCacheFailAtBg(ResultType result) {
    }

    /* access modifiers changed from: protected */
    public void onExceptionAtBg(Exception ex, RpcTask task) {
    }

    public void setRpcUiProcessor(RpcUiProcessor p) {
        this.rpcUiProcessor = p;
    }

    public RpcUiProcessor getRpcUiProcessor() {
        return this.rpcUiProcessor;
    }

    public RpcTask<ResultType> getRpcTask() {
        if (this.eventData != null) {
            return this.eventData.getRpcTask();
        }
        return this.taskOnBgCallback;
    }

    public RpcEvent<ResultType> getRpcEvent() {
        return this.eventData;
    }

    public Object[] getRpcRequest() {
        if (getRpcTask() != null) {
            return getRpcTask().getParams();
        }
        return null;
    }

    public ResultType getRpcResult() {
        if (this.eventData == null || this.eventData.result == null) {
            return null;
        }
        return this.eventData.result;
    }

    public void showEmptyView(String tip) {
        if (this.rpcUiProcessor != null) {
            this.rpcUiProcessor.showEmptyView(tip, "");
        }
    }

    /* access modifiers changed from: private */
    public void setContentVisibilityIfNeed(RpcTask<?> task, int v) {
        if (this.rpcUiProcessor != null && !this.isLoadDataSuccess && task.getRunConfig().autoHideContentOnRun) {
            this.rpcUiProcessor.setContentVisibility(v);
        }
    }

    private void unregisterFromEventBus() {
        EventBusManager.getInstance().unregisterRaw(this);
    }

    public void cancelRpc() {
        if (this.eventData != null && getRpcTask() != null) {
            RpcRunConfig config = getRpcTask().getRunConfig();
            if (config != null) {
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, getOnEventLog(getRpcTask(), "cancelRpc直接调用"));
                this.isCancelPending = true;
                if (config.loadingMode == LoadingMode.CANCELABLE_EXIT_LOADING && this.rpcUiProcessor != null) {
                    this.rpcUiProcessor.finishActivity();
                }
                MultiThreadUtil.runOnUiThread(new Runnable() {
                    public final void run() {
                        RpcSubscriber.this.onCancel();
                    }
                });
                innerFinish(getRpcTask());
            }
        }
    }

    public ActionProcessor getActionProcessor() {
        return this.actionProcessor;
    }

    public void setActionProcessor(ActionProcessor actionProcessor2) {
        this.actionProcessor = actionProcessor2;
    }

    public DefaultActionProcessor getDefaultActionProcessor() {
        if (this.actionProcessor instanceof DefaultActionProcessor) {
            return (DefaultActionProcessor) this.actionProcessor;
        }
        return null;
    }

    public TriggerActionCallback getTriggerActionCallback() {
        if (getDefaultActionProcessor() != null) {
            return getDefaultActionProcessor().getTriggerActionCallback();
        }
        return null;
    }

    public String getSequenceId() {
        if (getRpcTask() == null) {
            return "";
        }
        return getRpcTask().getSequenceId();
    }

    private void logOnEventStatus(RpcTask<?> task, String status) {
        if (DebugUtil.isDebug()) {
            DebugUtil.log((String) RpcConstant.TAG, getOnEventLog(task, status));
        } else if (this.logStatus != null && this.logStatus.contains(status)) {
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, getOnEventLog(task, status));
        }
    }

    private String getExceptionMsgOrOther(RpcException ex, String other) {
        if (ex == null || TextUtils.isEmpty(ex.getMsg())) {
            return other;
        }
        return ex.getMsg();
    }

    private String getOnEventLog(RpcTask<?> task, String status) {
        return String.format("rpc(runnable=%s, subscriber=%s)事件：status=%s", new Object[]{RpcUtil.getRpcRunnableLogString(task), RpcUtil.getSimpleName((Object) task.getSubscriber()), status});
    }

    public void setTaskOnBgCallback(RpcTask<ResultType> taskOnBgCallback2) {
        this.taskOnBgCallback = taskOnBgCallback2;
    }

    public boolean isLoadDataSuccess() {
        return this.isLoadDataSuccess;
    }

    public void resetStatus() {
        this.isLoadDataSuccess = false;
    }

    public ShowTypeProcessor getShowTypeProcessor() {
        return this.showTypeProcessor;
    }

    public void setShowTypeProcessor(ShowTypeProcessor showTypeProcessor2) {
        this.showTypeProcessor = showTypeProcessor2;
    }
}
