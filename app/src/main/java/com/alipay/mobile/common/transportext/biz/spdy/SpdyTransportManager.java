package com.alipay.mobile.common.transportext.biz.spdy;

import android.content.Context;
import com.alipay.mobile.common.transport.concurrent.TaskExecutorManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.ExtTransportClient;
import com.alipay.mobile.common.transport.utils.AppStartNetWorkingHelper;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportManagerAdapter;
import com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl;

public class SpdyTransportManager extends ExtTransportManagerAdapter {
    private boolean initd = false;
    private AndroidSpdyHttpClient mAndroidSpdyHttpClient;
    /* access modifiers changed from: private */
    public Context mContext;

    public void init(Context context) {
        if (!this.initd) {
            this.initd = true;
            this.mContext = context;
            initSpdyLongLinkConnManager();
            preCreateSpdyonnection();
        }
    }

    public ExtTransportClient getExtTransportClient(Context context, TransportContext transportContext) {
        if (!this.initd) {
            init(context);
        }
        return getAndroidSpdyHttpClient();
    }

    private void preCreateSpdyonnection() {
        final AndroidSpdyHttpClient androidSpdyHttpClient = getAndroidSpdyHttpClient();
        if (!androidSpdyHttpClient.isExecutedPreConnect()) {
            AppStartNetWorkingHelper.runOnAppStart(new Runnable() {
                public void run() {
                    androidSpdyHttpClient.asynPreConnect(TaskExecutorManager.getInstance(SpdyTransportManager.this.mContext).getBgExecutor());
                }
            }, this.mContext);
        }
    }

    private void initSpdyLongLinkConnManager() {
        if (!MiscUtils.isPushProcess(this.mContext)) {
            SpdyLongLinkConnManagerImpl.getInstance().attch(this.mContext);
        }
    }

    private AndroidSpdyHttpClient getAndroidSpdyHttpClient() {
        if (this.mAndroidSpdyHttpClient != null) {
            return this.mAndroidSpdyHttpClient;
        }
        this.mAndroidSpdyHttpClient = AndroidSpdyHttpClient.newInstance(this.mContext);
        return this.mAndroidSpdyHttpClient;
    }
}
