package com.alipay.mobile.common.transportext.biz.sync;

import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.utils.ConnectivityHelper;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.shared.spdy.SpdyAvalibleListener;
import java.util.Observable;

public class SyncSpdyAvalibleListener implements SpdyAvalibleListener {
    public void update(Observable observable, Object data) {
        if (ConnectivityHelper.isShowRedText()) {
            LogCatUtil.info(HttpWorker.TAG, "SyncSpdyAvalibleListener: invoke checkLinkState");
            SyncManager.checkLinkState(4);
        }
    }
}
