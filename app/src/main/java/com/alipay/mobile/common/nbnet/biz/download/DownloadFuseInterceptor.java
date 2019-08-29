package com.alipay.mobile.common.nbnet.biz.download;

import com.alipay.mobile.common.nbnet.api.NBNetInterceptor;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetFuseException;
import com.alipay.mobile.common.nbnet.biz.log.FrameworkMonitorFactory;
import com.alipay.mobile.common.nbnet.biz.util.NBNetConfigUtil;
import java.util.concurrent.TimeUnit;

public class DownloadFuseInterceptor implements NBNetInterceptor {
    private long a;
    private long b;

    public void intercept(byte transportType, String resId) {
        if (transportType == 1) {
            if (!FrameworkMonitorFactory.a().a(resId)) {
                throw new NBNetFuseException("Download trafic beyond limit. requestId=" + resId);
            }
            if (this.a == 0) {
                this.a = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - this.a > ((long) NBNetConfigUtil.h()) * TimeUnit.SECONDS.toMillis(1)) {
                this.b = 0;
                this.a = System.currentTimeMillis();
            } else if (this.b >= ((long) NBNetConfigUtil.i())) {
                throw new NBNetFuseException("DownloadCount: " + this.b + ", reach fuse download:" + NBNetConfigUtil.i());
            }
        }
    }

    public void reportTransmittedTraffic(byte bizType, String resId, long dataLen) {
        if (bizType == 1) {
            this.b++;
        }
    }

    public void reportReceivedTraffic(byte bizType, String resId, long dataLen) {
    }

    public void reportException(byte transportType, String resId, Exception exception) {
    }
}
