package com.alipay.mobile.common.logging.render;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.DeviceInfo;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.helper.DeviceHWRenderHelper;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.NetUtil;

public class BehavorRender extends BaseRender {
    public BehavorRender(LogContext logContext) {
        super(logContext);
    }

    public final String a(String behavorID, Behavor behavor, PendingRender pendingRender) {
        StringBuilder msg = new StringBuilder();
        String header = null;
        if (behavor.getExtParams() != null) {
            header = behavor.getExtParams().get(Performance.KEY_LOG_HEADER);
        }
        if (!TextUtils.isEmpty(header)) {
            msg.append(header);
        } else {
            msg.append("D-VM");
        }
        if (pendingRender == null || pendingRender.b == null) {
            LoggingUtil.appendParam(msg, LoggingUtil.getNowTime());
        } else {
            LoggingUtil.appendParam(msg, pendingRender.b);
        }
        LoggingUtil.appendParam(msg, this.b.getProductId());
        LoggingUtil.appendParam(msg, this.b.getProductVersion());
        LoggingUtil.appendParam(msg, "2");
        LoggingUtil.appendParam(msg, this.b.getClientId());
        LoggingUtil.appendParam(msg, this.b.getSessionId());
        LoggingUtil.appendParam(msg, this.b.getUserId());
        if (!TextUtils.isEmpty(behavorID)) {
            LoggingUtil.appendParam(msg, behavorID);
        } else {
            LoggingUtil.appendParam(msg, "event");
        }
        LoggingUtil.appendParam(msg, behavor.getAbTestInfo());
        if (!TextUtils.isEmpty(behavor.getRefer())) {
            LoggingUtil.appendParam(msg, behavor.getRefer());
        } else if (pendingRender != null) {
            LoggingUtil.appendParam(msg, pendingRender.h);
        } else {
            LoggingUtil.appendParam(msg, this.b.getContextParam(LogContext.LOCAL_STORAGE_REFER));
        }
        if (behavor.getAppID() != null) {
            LoggingUtil.appendParam(msg, behavor.getAppID());
        } else if (pendingRender != null) {
            LoggingUtil.appendParam(msg, pendingRender.g);
        } else {
            LoggingUtil.appendParam(msg, this.b.getStorageParam(LogContext.STORAGE_APPID));
        }
        LoggingUtil.appendParam(msg, behavor.getAppVersion());
        LoggingUtil.appendParam(msg, behavor.getxPath());
        LoggingUtil.appendParam(msg, behavor.getEntityContentId());
        LoggingUtil.appendParam(msg, behavor.getSeedID());
        LoggingUtil.appendParam(msg, behavor.getLoggerLevel());
        if (behavor.getRenderBizType() != null) {
            LoggingUtil.appendParam(msg, behavor.getRenderBizType());
        } else {
            LoggingUtil.appendParam(msg, behavor.getBehaviourPro());
        }
        LoggingUtil.appendParam(msg, behavor.getLogPro());
        LoggingUtil.appendParam(msg, behavor.getParam1());
        LoggingUtil.appendParam(msg, behavor.getParam2());
        LoggingUtil.appendParam(msg, behavor.getParam3());
        if (behavor.getLegacyParam() != null) {
            LoggingUtil.appendParam(msg, behavor.getLegacyParam());
        } else {
            LoggingUtil.appendExtParam(msg, behavor.getExtParams());
        }
        LoggingUtil.appendParam(msg, this.b.getSourceId());
        LoggingUtil.appendParam(msg, behavor.getPageStayTime());
        LoggingUtil.appendParam(msg, this.b.getDeviceId());
        LoggingUtil.appendParam(msg, behavor.getUserCaseID());
        LoggingUtil.appendParam(msg, behavor.getPageId());
        if (behavor.getRefViewID() != null) {
            LoggingUtil.appendParam(msg, behavor.getRefViewID());
        } else if (pendingRender != null) {
            LoggingUtil.appendParam(msg, pendingRender.c);
        } else {
            LoggingUtil.appendParam(msg, this.b.getContextParam(LogContext.STORAGE_REFVIEWID));
        }
        if (behavor.getViewID() != null) {
            LoggingUtil.appendParam(msg, behavor.getViewID());
        } else if (pendingRender != null) {
            LoggingUtil.appendParam(msg, pendingRender.d);
        } else {
            LoggingUtil.appendParam(msg, this.b.getContextParam(LogContext.STORAGE_VIEWID));
        }
        if (behavor.getTrackId() != null) {
            LoggingUtil.appendParam(msg, behavor.getTrackId());
        } else if (pendingRender != null) {
            LoggingUtil.appendParam(msg, pendingRender.e);
        } else {
            LoggingUtil.appendParam(msg, this.b.getStorageParam(LogContext.LOCAL_STORAGE_ACTIONID));
        }
        if (behavor.getTrackToken() != null) {
            LoggingUtil.appendParam(msg, behavor.getTrackToken());
        } else if (pendingRender != null) {
            LoggingUtil.appendParam(msg, pendingRender.f);
        } else {
            LoggingUtil.appendParam(msg, this.b.getStorageParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN));
        }
        LoggingUtil.appendParam(msg, LogStrategyManager.getInstance().getHitTestRate(behavor.getBehaviourPro(), behavor.getLoggerLevel()));
        LoggingUtil.appendParam(msg, Build.MODEL);
        LoggingUtil.appendParam(msg, VERSION.RELEASE);
        LoggingUtil.appendParam(msg, NetUtil.getNetworkTypeOptimized(this.b.getApplicationContext()));
        LoggingUtil.appendParam(msg, this.b.getReleaseCode());
        LoggingUtil.appendParam(msg, this.b.getChannelId());
        LoggingUtil.appendParam(msg, this.b.getLanguage());
        LoggingUtil.appendParam(msg, this.b.getHotpatchVersion());
        LoggingUtil.appendParam(msg, String.valueOf(DeviceHWRenderHelper.a()));
        LoggingUtil.appendParam(msg, String.valueOf(DeviceHWRenderHelper.b()));
        LoggingUtil.appendParam(msg, String.valueOf(DeviceHWRenderHelper.a(this.b.getApplicationContext())));
        LoggingUtil.appendExtParam(msg, this.b.getBizExternParams());
        LoggingUtil.appendParam(msg, behavor.getSpmStatus());
        LoggingUtil.appendParam(msg, DeviceInfo.getInstance(this.b.getApplicationContext()).getResolution());
        LoggingUtil.appendParam(msg, LoggerFactory.getProcessInfo().getProcessAlias());
        LoggingUtil.appendParam(msg, a());
        return msg.append("$$").toString();
    }
}
