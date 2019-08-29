package com.alipay.mobile.common.nbnet.biz;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.download.MMDPTransport;
import com.alipay.mobile.common.nbnet.biz.netlib.BasicNBNetContext;
import com.alipay.mobile.common.nbnet.biz.task.JobScheduler;
import com.alipay.mobile.common.nbnet.biz.task.JobSchedulerImpl;
import com.alipay.mobile.common.nbnet.biz.transport.NBNetDownloadTransport;
import com.alipay.mobile.common.nbnet.biz.transport.Route;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import com.alipay.mobile.common.nbnet.biz.util.URLConfigUtil;
import java.net.URL;

public class GlobalNBNetContext extends BasicNBNetContext {
    private static volatile GlobalNBNetContext a;

    public static GlobalNBNetContext a() {
        if (a != null) {
            return a;
        }
        synchronized (GlobalNBNetContext.class) {
            try {
                GlobalNBNetContext context = new GlobalNBNetContext();
                if (a == null) {
                    a = context;
                }
            }
        }
        return a;
    }

    public final MMDPTransport b() {
        if (Injection.a != null) {
            return Injection.a;
        }
        URL downloadServerURL = URLConfigUtil.c();
        return new NBNetDownloadTransport(new Route(downloadServerURL.getHost(), NBNetCommonUtil.a(downloadServerURL.getPort(), downloadServerURL.getProtocol()), null), TextUtils.equals(downloadServerURL.getProtocol(), "https"), this);
    }

    public static JobScheduler c() {
        if (Injection.b != null) {
            return Injection.b;
        }
        return new JobSchedulerImpl();
    }
}
