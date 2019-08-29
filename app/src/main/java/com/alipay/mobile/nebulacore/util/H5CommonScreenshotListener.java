package com.alipay.mobile.nebulacore.util;

import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.dev.H5BugmeLogCollector;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebulacore.dev.trace.H5PerformanceUtils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.util.H5ScreenShotObserver.H5ScreenShotListener;
import java.util.List;

public class H5CommonScreenshotListener implements H5ScreenShotListener {
    public void onScreenShot() {
        H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                try {
                    if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_flush_ucLog"))) {
                        H5Service h5Service = H5ServiceUtils.getH5Service();
                        if (h5Service != null) {
                            H5Page h5Page = h5Service.getTopH5Page();
                            if (h5Page != null) {
                                h5Page.sendEvent("flushUcLog", null);
                            }
                        }
                    }
                    H5BugmeLogCollector.flushAppLog();
                    List<String> traces = H5PerformanceUtils.getAllThreadsTraces();
                    if (traces != null) {
                        H5Log.d("H5CommonScreenshotListener", "All Threads Traces: ###" + traces.size());
                        for (String trace : traces) {
                            H5Log.d("H5CommonScreenshotListener", trace);
                        }
                    }
                } catch (Throwable throwable) {
                    H5Log.e((String) "H5CommonScreenshotListener", throwable);
                }
            }
        });
    }
}
