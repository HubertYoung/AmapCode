package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HttpTransListener implements TransportCallback {
    private static final Logger logger = Logger.getLogger((String) "HttpTransListener");
    private Set<ImageLoadReq> loadReqs;
    private int mProgress = -1;

    public HttpTransListener(Set<ImageLoadReq> loadReqs2) {
        this.loadReqs = Collections.synchronizedSet(new HashSet(loadReqs2));
    }

    public void onCancelled(Request request) {
        logger.i("onCancelled " + request, new Object[0]);
    }

    public void onPreExecute(Request request) {
        logger.i("onPreExecute " + request, new Object[0]);
    }

    public void onPostExecute(Request request, Response response) {
        logger.i("onPostExecute " + request, new Object[0]);
    }

    public void onProgressUpdate(Request request, double percent) {
        int progress = (int) (100.0d * percent);
        if (this.mProgress != progress) {
            if (this.mProgress <= 1 || this.mProgress >= 99) {
                logger.d("onProgressUpdate " + request + ", percent: " + percent, new Object[0]);
            } else {
                logger.p("onProgressUpdate " + request + ", percent: " + percent, new Object[0]);
            }
            this.mProgress = progress;
            if (this.loadReqs != null && !this.loadReqs.isEmpty()) {
                for (ImageLoadReq loadReq : this.loadReqs) {
                    if (loadReq.downLoadCallback != null) {
                        loadReq.downLoadCallback.onProcess(loadReq.source, this.mProgress);
                    }
                }
            }
        }
    }

    public void onFailed(Request request, int code, String msg) {
        logger.i("onFailed " + request + ", code: " + code + ", msg: " + msg, new Object[0]);
    }
}
