package com.alipay.mobile.common.transport.monitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.ext.ExtTransportOffice;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.util.HashMap;
import java.util.Map;

public class NetworkServiceTracer {
    public static final String KEY_LAST_STATE_COUNT = "last_state_count";
    public static final String KEY_LAST_STATE_TS = "last_state_ts";
    public static final String REPORT_BIZ_NAME = "BIZ_NETWORK";
    public static final String REPORT_SUB_NAME_DJG = "DJG";
    public static final String REPORT_SUB_NAME_H5 = "H5";
    public static final String REPORT_SUB_NAME_NBNET_UP = "NBNET_UP";
    public static final String REPORT_SUB_NAME_RPC = "RPC";
    public static final String REPORT_SUB_NAME_RSRC = "RSRC";
    public static final String TAG = "NS_TRACER";
    public static final String TRACE_STATE_FILE = "NS_Tracer_Data";
    private static NetworkServiceTracer c;
    private long a;
    private NSTraceItem[] b;
    public int maxErrorCount = 5;

    public class NSTraceItem {
        public int errorCount;
        public long firstErrorTime;
        public int lastErrorCode;
        public String lastErrorMsg;
        public long lastErrorTime;
        public String networkType;

        public NSTraceItem() {
        }
    }

    public enum TRACE_ITEM_INDEX {
        TRACE_ITEM_RPC,
        TRACE_ITEM_H5,
        TRACE_ITEM_RSRC,
        TRACE_ITEM_DJG,
        TRACE_ITEM_NBNET_UP
    }

    public static NetworkServiceTracer getInstance() {
        synchronized (NetworkServiceTracer.class) {
            try {
                if (c == null) {
                    c = new NetworkServiceTracer();
                }
            }
        }
        return c;
    }

    private NetworkServiceTracer() {
        NSTraceItem[] nSTraceItemArr;
        TransportConfigureManager configMgr = TransportConfigureManager.getInstance();
        this.a = configMgr.getLongValue(TransportConfigureItem.NETSERVICE_REPORT_PERIOD);
        this.maxErrorCount = configMgr.getIntValue(TransportConfigureItem.NETSERVICE_REPORT_ERRCOUNT);
        a();
        Context ctx = TransportEnvUtil.getContext();
        if (ctx != null) {
            SharedPreferences spf = ctx.getSharedPreferences(TRACE_STATE_FILE, 4);
            for (NSTraceItem item : this.b) {
                item.firstErrorTime = spf.getLong(getTSKeyByName(item.networkType), 0);
                item.errorCount = spf.getInt(getCountKeyByName(item.networkType), 0);
            }
            return;
        }
        LogCatUtil.debug(TAG, "Context is not intialzied yet");
    }

    private void a() {
        this.b = new NSTraceItem[5];
        this.b[TRACE_ITEM_INDEX.TRACE_ITEM_RPC.ordinal()] = new NSTraceItem();
        this.b[TRACE_ITEM_INDEX.TRACE_ITEM_H5.ordinal()] = new NSTraceItem();
        this.b[TRACE_ITEM_INDEX.TRACE_ITEM_RSRC.ordinal()] = new NSTraceItem();
        this.b[TRACE_ITEM_INDEX.TRACE_ITEM_DJG.ordinal()] = new NSTraceItem();
        this.b[TRACE_ITEM_INDEX.TRACE_ITEM_NBNET_UP.ordinal()] = new NSTraceItem();
        NSTraceItem rpcItem = a(TRACE_ITEM_INDEX.TRACE_ITEM_RPC);
        NSTraceItem h5Item = a(TRACE_ITEM_INDEX.TRACE_ITEM_H5);
        NSTraceItem rsrcItem = a(TRACE_ITEM_INDEX.TRACE_ITEM_RSRC);
        NSTraceItem djgItem = a(TRACE_ITEM_INDEX.TRACE_ITEM_DJG);
        NSTraceItem nbnetUpItem = a(TRACE_ITEM_INDEX.TRACE_ITEM_NBNET_UP);
        rpcItem.networkType = "RPC";
        h5Item.networkType = "H5";
        rsrcItem.networkType = REPORT_SUB_NAME_RSRC;
        djgItem.networkType = REPORT_SUB_NAME_DJG;
        nbnetUpItem.networkType = REPORT_SUB_NAME_NBNET_UP;
    }

    public void recordError(byte bizType, int errCode, String errMsg, Map<String, String> extMap) {
        LogCatUtil.debug(TAG, "--->Exception reported to NSTracer, type=" + bizType);
        try {
            if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                LogCatUtil.debug(TAG, "network isn't available,need't record error");
                return;
            }
            String reportSubName = a(bizType);
            if (TextUtils.isEmpty(reportSubName)) {
                LogCatUtil.debug(TAG, "recordError unknown bizType,ignored");
                return;
            }
            NSTraceItem item = b(reportSubName);
            long nowTS = System.currentTimeMillis();
            if (item != null) {
                if (item.errorCount == 0) {
                    item.firstErrorTime = nowTS;
                }
                item.errorCount++;
                item.lastErrorCode = errCode;
                item.lastErrorMsg = errMsg;
                item.lastErrorTime = nowTS;
                a(reportSubName, extMap);
                return;
            }
            LogCatUtil.debug(TAG, "Empty item for tunnel type" + reportSubName);
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "recordError exception", ex);
        }
    }

    public void clearErrorByType(byte bizType) {
        try {
            String reportSubName = a(bizType);
            if (TextUtils.isEmpty(reportSubName)) {
                LogCatUtil.debug(TAG, "clearErrorByType unknown bizType,ignored");
                return;
            }
            NSTraceItem item = b(reportSubName);
            if (item != null) {
                item.errorCount = 0;
                item.firstErrorTime = 0;
                item.lastErrorCode = 0;
                item.lastErrorMsg = "";
                item.lastErrorTime = 0;
                LogCatUtil.debug(TAG, "Clearing error state for subtype:" + item.networkType);
                c(reportSubName);
            }
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "clearErrorByType exception", ex);
        }
    }

    private static String a(byte bizType) {
        if (bizType == 1) {
            return "RPC";
        }
        if (bizType == 2) {
            return "H5";
        }
        if (bizType == 4) {
            return REPORT_SUB_NAME_RSRC;
        }
        if (bizType == 3) {
            return REPORT_SUB_NAME_DJG;
        }
        if (bizType == 6) {
            return REPORT_SUB_NAME_NBNET_UP;
        }
        return "";
    }

    private NSTraceItem a(TRACE_ITEM_INDEX index) {
        return this.b[index.ordinal()];
    }

    private static boolean a(String opeType) {
        String rpcList = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NETSERVICE_RPC_LIST);
        if (TextUtils.isEmpty(rpcList)) {
            return false;
        }
        for (String equals : rpcList.split(",")) {
            if (TextUtils.equals(equals, opeType)) {
                LogCatUtil.info(TAG, "importRpc.opeType: " + opeType);
                return true;
            }
        }
        return false;
    }

    private NSTraceItem b(String reportSubName) {
        if (TextUtils.equals(reportSubName, "RPC")) {
            return this.b[TRACE_ITEM_INDEX.TRACE_ITEM_RPC.ordinal()];
        }
        if (TextUtils.equals(reportSubName, "H5")) {
            return this.b[TRACE_ITEM_INDEX.TRACE_ITEM_H5.ordinal()];
        }
        if (TextUtils.equals(reportSubName, REPORT_SUB_NAME_RSRC)) {
            return this.b[TRACE_ITEM_INDEX.TRACE_ITEM_RSRC.ordinal()];
        }
        if (TextUtils.equals(reportSubName, REPORT_SUB_NAME_DJG)) {
            return this.b[TRACE_ITEM_INDEX.TRACE_ITEM_DJG.ordinal()];
        }
        if (TextUtils.equals(reportSubName, REPORT_SUB_NAME_NBNET_UP)) {
            return this.b[TRACE_ITEM_INDEX.TRACE_ITEM_NBNET_UP.ordinal()];
        }
        LogCatUtil.debug(TAG, "getTraceItemByName,networkType unknown error");
        return null;
    }

    private void a(String reportSubName, Map<String, String> extMap) {
        try {
            NSTraceItem item = b(reportSubName);
            String opeType = extMap.get("Operation-Type");
            if (!TextUtils.equals(reportSubName, "RPC") || !a(opeType)) {
                if (MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NETSERVICE_UPERR_REPORT))) {
                    String djgUpBiz = extMap.get("DJG_UP_BIZ");
                    if (TextUtils.equals(reportSubName, REPORT_SUB_NAME_DJG) && (TextUtils.equals(djgUpBiz, "1") || TextUtils.equals(djgUpBiz, "2"))) {
                        LogCatUtil.debug(TAG, "DJG up ex,report rignt now");
                        a(item);
                        c(reportSubName);
                        return;
                    } else if (TextUtils.equals(reportSubName, REPORT_SUB_NAME_NBNET_UP)) {
                        LogCatUtil.debug(TAG, "nbnet_up up ex, report rignt now");
                        a(item);
                        c(reportSubName);
                        return;
                    }
                }
                long nowTS = System.currentTimeMillis();
                if (item.errorCount <= this.maxErrorCount || nowTS - item.firstErrorTime <= this.a) {
                    LogCatUtil.verbose(TAG, "Waiting for more error happened,subtype=" + item.networkType + " from begin time:" + (nowTS - item.firstErrorTime) + " ms");
                } else {
                    a(item);
                }
                c(reportSubName);
                return;
            }
            LogCatUtil.debug(TAG, "import rpc ex,report rignt now");
            a(item);
            c(reportSubName);
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "tryReport ex:" + ex.toString());
        }
    }

    private void a(NSTraceItem item) {
        String strErrorCode = String.valueOf(item.lastErrorCode);
        Map dataMap = new HashMap();
        dataMap.put("Last_error_msg", item.lastErrorMsg);
        dataMap.put("Last_error_ts", String.valueOf(item.lastErrorTime));
        LoggerFactory.getMonitorLogger().mtBizReport("BIZ_NETWORK", item.networkType, strErrorCode, dataMap);
        LogCatUtil.debug(TAG, "--->mtBizReport invoked, subname=" + item.networkType);
        item.errorCount = 0;
        item.firstErrorTime = 0;
        b();
    }

    private void b() {
        try {
            NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                public void run() {
                    SignalStateHelper.getInstance().reportNetStateInfo();
                    AlipayQosService.getInstance().getQosLevel();
                    ExtTransportOffice.getInstance().diagnoseNotify();
                }
            });
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "startNetworkDiagnose ex:" + ex.toString());
        }
    }

    private void c(String reportSubName) {
        if (this.b == null || this.b.length == 0) {
            LogCatUtil.debug(TAG, "Nothing to save...");
            return;
        }
        Editor ed = TransportEnvUtil.getContext().getSharedPreferences(TRACE_STATE_FILE, 4).edit();
        NSTraceItem item = b(reportSubName);
        LogCatUtil.verbose(TAG, "trying to persistTrace Item: " + item.networkType + " first error Time=" + item.firstErrorTime + " error count=" + item.errorCount);
        ed.putLong(getTSKeyByName(item.networkType), item.firstErrorTime);
        ed.putInt(getCountKeyByName(item.networkType), item.errorCount);
        ed.commit();
    }

    public String getTSKeyByName(String name) {
        return name + "_last_state_ts";
    }

    public String getCountKeyByName(String name) {
        return name + "_last_state_count";
    }
}
