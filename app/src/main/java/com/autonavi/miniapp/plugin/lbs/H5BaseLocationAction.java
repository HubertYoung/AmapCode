package com.autonavi.miniapp.plugin.lbs;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.minimap.R;

public abstract class H5BaseLocationAction {
    protected String mFinalDomain;
    protected H5BridgeContext mH5BridgeContext;
    protected H5Event mH5Event;
    protected H5Location mH5Location;
    protected H5Service mH5Service = ((H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName()));
    protected long mStartTime;
    protected String mTag;
    protected String mUrl;

    public H5BaseLocationAction(H5Event h5Event, H5BridgeContext h5BridgeContext, H5Location h5Location, long j) {
        this.mStartTime = j;
        this.mH5Event = h5Event;
        this.mH5BridgeContext = h5BridgeContext;
        this.mH5Location = h5Location;
    }

    public void handleEvent() {
        H5Page h5PageFromEvent = getH5PageFromEvent(this.mH5Event);
        if (h5PageFromEvent == null) {
            LoggerFactory.getTraceLogger().info(this.mTag, "handleEvent, h5Page == null");
            return;
        }
        this.mUrl = h5PageFromEvent.getUrl();
        this.mFinalDomain = getHost(H5UrlHelper.parseUrl(this.mUrl));
        if (isDomainValid(h5PageFromEvent)) {
            LoggerFactory.getTraceLogger().info(this.mTag, "handleEvent, isDomainValid");
            handleValidDomainEvent();
            return;
        }
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        String str = this.mTag;
        StringBuilder sb = new StringBuilder("handleEvent, isDomainInValid, mUrl=");
        sb.append(this.mUrl);
        traceLogger.info(str, sb.toString());
        handleValidDomainEvent();
    }

    private H5Page getH5PageFromEvent(H5Event h5Event) {
        if (h5Event.getTarget() instanceof H5Page) {
            return (H5Page) h5Event.getTarget();
        }
        return null;
    }

    private String getHost(Uri uri) {
        return (uri == null || TextUtils.isEmpty(uri.getHost())) ? "" : uri.getHost();
    }

    private boolean isDomainValid(H5Page h5Page) {
        if (h5Page == null || !H5Utils.getBoolean(h5Page.getParams(), (String) "isTinyApp", false)) {
            return (this.mH5Location != null && TextUtils.isEmpty(this.mFinalDomain)) || "Y".equals(this.mH5Service.getSharedData(this.mFinalDomain)) || this.mH5Service.permitLocation(this.mUrl);
        }
        LoggerFactory.getTraceLogger().info(this.mTag, "form tinyApp not show dialog");
        return true;
    }

    /* access modifiers changed from: protected */
    public void doNegativeEvent() {
        Activity activity = this.mH5Event.getActivity();
        LoggerFactory.getTraceLogger().info(this.mTag, "doNegativeEvent");
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "error", (Object) Integer.valueOf(4));
        jSONObject.put((String) "errorMessage", (Object) activity.getResources().getString(R.string.notagreeuseloc));
        this.mH5BridgeContext.sendBridgeResult(jSONObject);
    }

    /* access modifiers changed from: protected */
    public void handleValidDomainEvent() {
        LoggerFactory.getTraceLogger().info(this.mTag, "handleValidDomainEvent");
    }

    /* access modifiers changed from: protected */
    public void doPositiveEvent() {
        LoggerFactory.getTraceLogger().info(this.mTag, "doPositiveEvent");
    }
}
