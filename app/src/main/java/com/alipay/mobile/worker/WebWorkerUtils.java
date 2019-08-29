package com.alipay.mobile.worker;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Stack;

public class WebWorkerUtils {
    public static void workerErrorLogMonitor(String param4) {
        try {
            H5LogData logData = H5LogData.seedId("TINY_APP_WEBVIEW_WORKER_FAIL");
            H5Page h5Page = a();
            if (!(h5Page == null || h5Page.getParams() == null)) {
                logData.param4().add(H5Utils.getString(h5Page.getParams(), (String) "appId"), null).add("msg", param4);
            }
            H5LogUtil.logNebulaTech(logData);
        } catch (Throwable e) {
            H5Log.e((String) "WebWorkerUtils", "workerErrorLogMonitor...e=" + e);
        }
    }

    private static H5Page a() {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            try {
                Stack sessions = h5Service.getSessions();
                if (sessions != null) {
                    synchronized (sessions) {
                        int index = sessions.size() - 1;
                        while (index >= 0) {
                            H5Session session = (H5Session) sessions.get(index);
                            if (session == null || TextUtils.isEmpty(session.getServiceWorkerID())) {
                                index--;
                            } else {
                                H5Page topPage = session.getTopPage();
                                return topPage;
                            }
                        }
                    }
                }
            } catch (Throwable throwable) {
                H5Log.e((String) "WebWorkerUtils", throwable);
            }
        }
        return null;
    }
}
