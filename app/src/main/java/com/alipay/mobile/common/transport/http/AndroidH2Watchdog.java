package com.alipay.mobile.common.transport.http;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.NetworkServiceTracer;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;

public class AndroidH2Watchdog {
    private static AndroidH2Watchdog a = null;
    private int b = 0;
    private int c = 0;
    private final int d = 3;

    public static AndroidH2Watchdog getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (AndroidH2Watchdog.class) {
            try {
                if (a == null) {
                    a = new AndroidH2Watchdog();
                }
            }
        }
        return a;
    }

    private AndroidH2Watchdog() {
    }

    public void resetFailCount(byte bizType) {
        LogCatUtil.printInfo("AndroidH2Watchdog", "resetFailCount,bizType:" + String.valueOf(bizType));
        if (1 == bizType) {
            a();
        } else {
            b();
        }
    }

    public synchronized void reportH2Error(byte bizType, String errcode, String errmsg, Throwable exception) {
        try {
            if (a(exception)) {
                a(bizType, errmsg, true);
            } else if (NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                if (bizType == 1) {
                    this.b++;
                } else {
                    this.c++;
                }
                LogCatUtil.debug("AndroidH2Watchdog", "reportH2Error bizType:" + bizType + ",errcode:" + errcode + ",errmsg:" + errmsg + ",rpcFailureCount:" + this.b + ",rsrcFailureCount:" + this.c);
                a(bizType, errmsg, false);
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "AndroidH2Watchdog", "reportH2Error ex:" + ex.toString());
        }
        return;
    }

    private synchronized void a(byte bizType, String errmsg, boolean downgradeRightNow) {
        try {
            TransportConfigureManager configureManager = TransportConfigureManager.getInstance();
            if (!TextUtils.equals(configureManager.getStringValue((ConfigureItem) TransportConfigureItem.H2_DOWNGRADE_SWITCH), "T")) {
                LogCatUtil.debug("AndroidH2Watchdog", "h2DownSwitch off");
            } else if (bizType == 1) {
                b(errmsg, configureManager, downgradeRightNow);
            } else {
                a(errmsg, configureManager, downgradeRightNow);
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "AndroidH2Watchdog", "checkIfDowngrade ex:" + ex.toString());
        }
        return;
    }

    private void a(String errmsg, TransportConfigureManager configureManager, boolean downgradeRightNow) {
        if (!MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.GO_URLCONNECTION_SWITCH)) {
            LogCatUtil.debug("AndroidH2Watchdog", "rsrcGoH2Switch is off");
            return;
        }
        if (downgradeRightNow) {
            LogCatUtil.debug("AndroidH2Watchdog", "rsrc fatal error,downgrade right now");
            this.c = 4;
        }
        if (this.c > 3) {
            synchronized (AndroidH2Watchdog.class) {
                LogCatUtil.debug("AndroidH2Watchdog", "RSRC tunnel downgrade to http1.1,original RSRC H2 SWTICH:" + configureManager.getStringValue((ConfigureItem) TransportConfigureItem.GO_URLCONNECTION_SWITCH));
                configureManager.setValue(TransportConfigureItem.GO_URLCONNECTION_SWITCH, "0");
                configureManager.setValue(TransportConfigureItem.VERSION, String.valueOf(configureManager.getLatestVersion() + 1));
                b();
                a(NetworkServiceTracer.REPORT_SUB_NAME_RSRC, errmsg);
            }
        }
    }

    private void b(String errmsg, TransportConfigureManager configureManager, boolean downgradeRightNow) {
        if (!MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), configureManager.getStringValue((ConfigureItem) TransportConfigureItem.RPC_GO_H2_SWITCH))) {
            LogCatUtil.debug("AndroidH2Watchdog", "rpcGoH2Switch is off");
            return;
        }
        if (downgradeRightNow) {
            LogCatUtil.debug("AndroidH2Watchdog", "rpc fatal error,downgrade right now");
            this.b = 4;
        }
        if (this.b > 3) {
            synchronized (AndroidH2Watchdog.class) {
                LogCatUtil.debug("AndroidH2Watchdog", "RPC tunnel downgrade to http1.1, original RPC H2 SWTICH:" + configureManager.getStringValue((ConfigureItem) TransportConfigureItem.RPC_GO_H2_SWITCH));
                configureManager.setValue(TransportConfigureItem.RPC_GO_H2_SWITCH, "0");
                configureManager.setValue(TransportConfigureItem.VERSION, String.valueOf(configureManager.getLatestVersion() + 1));
                a();
                a("RPC", errmsg);
            }
        }
    }

    private void a() {
        this.b = 0;
    }

    private void b() {
        this.c = 0;
    }

    private static void a(String bizType, String errmsg) {
        Performance pf = new TransportPerformance();
        pf.setSubType("H2");
        pf.setParam1("RPC");
        pf.setParam2("downgrade");
        pf.getExtPramas().put("bizType", bizType);
        pf.getExtPramas().put("errmsg", errmsg);
        MonitorLoggerUtils.uploadPerfLog(pf);
        LogCatUtil.debug("AndroidH2Watchdog", "Dumping perfLog:" + pf.toString());
    }

    private static boolean a(Throwable exception) {
        try {
            Throwable rootCause = MiscUtils.getRootCause(exception);
            if (rootCause == null) {
                return false;
            }
            String s = rootCause.toString();
            if (TextUtils.isEmpty(s) || !s.contains("stream was reset")) {
                return false;
            }
            return true;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "AndroidH2Watchdog", "isFatalError ex:" + ex);
            return false;
        }
    }
}
