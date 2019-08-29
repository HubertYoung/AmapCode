package com.alipay.mobile.common.transportext.biz.diagnose.network;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UploadManager {
    private static final String LOG_TYPE = "NetDiago";
    private static final String TAG = "DIAGNOSE-UPLOAD";

    public static void writeLog(List<String> logStrListInput, String versionInput, final int diagnoseType) {
        final String version;
        if (logStrListInput != null && !logStrListInput.isEmpty()) {
            final List logStrList = new ArrayList();
            Collections.addAll(logStrList, new String[logStrListInput.size()]);
            Collections.copy(logStrList, logStrListInput);
            if (versionInput == null) {
                version = "0.0";
            } else {
                version = versionInput;
            }
            NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                public final void run() {
                    try {
                        Performance pf = new TransportPerformance();
                        pf.setSubType(UploadManager.LOG_TYPE);
                        pf.setParam1(MonitorLoggerUtils.getLogBizType(UploadManager.LOG_TYPE));
                        pf.setParam2("INFO");
                        pf.getExtPramas().put("diagVer", version);
                        pf.getExtPramas().put("diagType", "diagnose");
                        if (NetworkUtils.isVpnUsed()) {
                            pf.getExtPramas().put("VPN", "T");
                        }
                        int count = 0;
                        boolean hasResult = false;
                        for (String s : logStrList) {
                            if (!TextUtils.isEmpty(s)) {
                                hasResult = true;
                                count++;
                                pf.addExtParam("RES_" + count, s);
                            }
                        }
                        if (hasResult) {
                            if (3 == diagnoseType) {
                                UploadManager.uploadDiagnoseByAuto(pf);
                            } else {
                                UploadManager.upload(pf);
                            }
                            LogCatUtil.debug(UploadManager.TAG, pf.toString());
                        }
                    } catch (Exception e) {
                        LogCatUtil.warn((String) UploadManager.TAG, e.toString());
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void upload(Performance pf) {
        MonitorLoggerUtils.uploadDiagLog(pf);
    }

    /* access modifiers changed from: private */
    public static void uploadDiagnoseByAuto(Performance pf) {
        MonitorLoggerUtils.uploadAutoDiagLog(pf);
    }
}
