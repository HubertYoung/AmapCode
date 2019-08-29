package com.alipay.mobile.beehive.rpc.action;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.beehive.rpc.RpcSubscriber;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.rpc.RpcUtil;
import com.alipay.mobile.beehive.rpc.model.FollowAction;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;
import java.util.List;
import java.util.Map;

public class DefaultActionProcessor implements ActionProcessor {
    private Object rpcResult;
    private RpcSubscriber subscriber;
    private TriggerActionCallback triggerActionCallback;

    public DefaultActionProcessor(RpcSubscriber subscriber2) {
        this.subscriber = subscriber2;
        setTriggerActionCallback(subscriber2);
    }

    public boolean handleFollowAction(RpcUiProcessor rr, Object rpcResult2, String action) {
        this.rpcResult = rpcResult2;
        try {
            if (TextUtils.isEmpty(action)) {
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "followAction是空串");
                return false;
            }
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "followAction=" + action);
            FollowAction ra = (FollowAction) JSON.parseObject(action, (TypeReference<T>) new TypeReference<FollowAction>() {
            }, new Feature[0]);
            if (ra != null) {
                return handleAction(rr, ra);
            }
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "followAction反解json失败，检查json格式是否正确");
            Map ext = RpcUtil.buildLogExtInfo(rr);
            ext.put(RpcConstant.RPC_RESULT_FOLLOW_ACTION, action);
            LoggerFactory.getMonitorLogger().mtBizReport("BEEHIVE_RPC", "FOLLOW_ACTION_DESERIALIZE", H5AppPrepareData.PREPARE_FAIL, ext);
            return false;
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return false;
        }
    }

    public void runTriggerActions(RpcUiProcessor rr, FollowAction parentAction, String triggerType) {
        List triggerActions = parentAction.triggerActions;
        if (triggerActions != null) {
            for (FollowAction fa : triggerActions) {
                if (fa != null && ActionUtil.equalTriggerType(fa, triggerType)) {
                    handleAction(rr, fa);
                }
            }
        }
        if (this.triggerActionCallback != null) {
            this.triggerActionCallback.onFollowActionTrigger(this.rpcResult, parentAction, triggerType);
            LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "onFollowActionTrigger: action=" + parentAction.type + ",triggerType=" + triggerType);
        }
    }

    /* access modifiers changed from: protected */
    public boolean handleAction(RpcUiProcessor rr, FollowAction action) {
        boolean validType = true;
        try {
            String type = action.type;
            if (TextUtils.equals(type, "toast")) {
                ToastHandler.run(this, rr, action);
            } else if (TextUtils.equals(type, "alert")) {
                AlertHandler.run(this, rr, action);
            } else if (TextUtils.equals(type, "link")) {
                LinkHandler.run(rr, action);
            } else if (TextUtils.equals(type, ActionConstant.TYPE_FINISH_PAGE)) {
                FinishPageHandler.run(rr, action);
            } else if (TextUtils.equals(type, "showWarn")) {
                ExceptionViewHandler.run(this, rr, action);
            } else if (TextUtils.equals(type, ActionConstant.TYPE_RETRY)) {
                RetryHandler.run(rr, action);
            } else if (TextUtils.equals(type, ActionConstant.TYPE_BIG_ALERT)) {
                BigAlertHandler.run(this, rr, action);
            } else if (TextUtils.equals(type, ActionConstant.TYPE_SHOW_EXCEPTION_VIEW)) {
                ExceptionViewHandler.run(this, rr, action);
            } else if (TextUtils.equals(type, ActionConstant.TYPE_CERTIFY)) {
                CertifyHandler.run(this, rr, action);
            } else {
                validType = false;
            }
            if (!validType) {
                LoggerFactory.getTraceLogger().info(RpcConstant.TAG, "未识别的的action,type=" + action.type);
            }
            runTriggerActions(rr, action, "auto");
            return validType;
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().warn((String) RpcConstant.TAG, (Throwable) ex);
            return false;
        }
    }

    public TriggerActionCallback getTriggerActionCallback() {
        return this.triggerActionCallback;
    }

    public void setTriggerActionCallback(TriggerActionCallback triggerActionCallback2) {
        this.triggerActionCallback = triggerActionCallback2;
    }

    public RpcSubscriber getRpcSubscriber() {
        return this.subscriber;
    }
}
