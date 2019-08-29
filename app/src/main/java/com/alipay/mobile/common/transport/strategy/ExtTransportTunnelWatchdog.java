package com.alipay.mobile.common.transport.strategy;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.ConnectionUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NwSharedSwitchUtil;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.alipay.mobile.monitor.api.MonitorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtTransportTunnelWatchdog {
    public static String DOWNGRADE_REASON_AMNETPOST = "AMNETPOST_FAILURE";
    public static String DOWNGRADE_REASON_BIND = "BIND_FAILURE";
    public static String DOWNGRADE_REASON_CRASH = "CRASH_FAILURE";
    public static String DOWNGRADE_REASON_RPC = "RPC_FAILURE";
    public static final String SP_KEY_PROC_CRASH_TICK = "proc_crash_tick";
    public static final String SP_SUB_KEY_MAX_CRASH_TIME_ALLOW = "maxCrashTimeAllow";
    public static final String SP_SUB_KEY_PROC_CRASH_COUNT = "procCrasheCount";
    private static ExtTransportTunnelWatchdog a;
    private int b;
    private long c;
    private int d;
    private long e;
    private volatile int f = 0;
    private volatile long g = 0;
    private volatile int h = 0;
    private volatile int i;
    private volatile long j = 0;
    private volatile long k = 0;
    private boolean l = false;
    private Object m = new Object();
    private String n = "";
    /* access modifiers changed from: private */
    public AtomicBoolean o = new AtomicBoolean(true);

    public static final ExtTransportTunnelWatchdog getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (ExtTransportTunnelWatchdog.class) {
            if (a != null) {
                ExtTransportTunnelWatchdog extTransportTunnelWatchdog = a;
                return extTransportTunnelWatchdog;
            }
            a = new ExtTransportTunnelWatchdog();
            return a;
        }
    }

    private ExtTransportTunnelWatchdog() {
        TransportConfigureManager configureManager = TransportConfigureManager.getInstance();
        this.b = configureManager.getIntValue(TransportConfigureItem.AMNET_DOWNGRADE_RPC_TRIGGER_COUNT);
        this.c = configureManager.getLongValue(TransportConfigureItem.AMNET_DOWNGRADE_RPC_TRIGGER_TIME) * 1000;
        if (Runtime.getRuntime().availableProcessors() <= 2 && this.c < 25000) {
            this.c = 25000;
        }
        this.d = configureManager.getIntValue(TransportConfigureItem.AMNET_DOWNGRADE_BIND_TRIGGER_COUNT);
        this.e = configureManager.getLongValue(TransportConfigureItem.AMNET_DOWNGRADE_BIND_TRIGGER_TIME) * 1000;
        LogCatUtil.debug("EXT_Watchdog", "MMTP Triggers: RPCFailures - " + this.b + " RPCFailureTime - " + this.c + " BindFailure - " + this.d + " BindFailureTime - " + this.e);
    }

    public synchronized void mrpcFailureTick() {
        if (!isDowngraded()) {
            long ts = System.currentTimeMillis();
            if (this.f == 0) {
                this.g = ts;
            }
            this.f++;
            LogCatUtil.debug("EXT_Watchdog", "MMTP failure ticked, current failures:" + this.f + " failure duration:" + (ts - this.g) + RPCDataParser.TIME_MS);
            b();
        }
    }

    public synchronized void mrpcFailureRest() {
        if (this.f != 0) {
            this.f = 0;
            this.g = 0;
            LogCatUtil.debug("EXT_Watchdog", "MMTP failure reset to 0");
        }
    }

    public boolean pushProcCrashTick(Context context) {
        try {
            b(context);
            return true;
        } catch (Throwable e2) {
            LogCatUtil.error("EXT_Watchdog", "pushProcCrashTick exception", e2);
            SharedPreUtils.removeData(context, SP_KEY_PROC_CRASH_TICK);
            return false;
        }
    }

    public synchronized void bindFailureTick() {
        if (!NetworkTunnelStrategy.getInstance().isCanUseAmnet()) {
            LogCatUtil.verbose("EXT_Watchdog", "AMNET is disabled already, no need to tick");
        } else if (isDowngraded()) {
            LogCatUtil.verbose("EXT_Watchdog", "Already downgraded, no need to tick");
        } else {
            long ts = System.currentTimeMillis();
            if (this.h == 0) {
                this.j = ts;
            }
            if (ts - this.k <= 1000) {
                LogCatUtil.verbose("EXT_Watchdog", "Ignore - Bind failure happened too frequent");
            } else if (ts - this.j > this.e) {
                this.h = 1;
                this.k = ts;
                this.j = ts;
                LogCatUtil.verbose("EXT_Watchdog", "Bind failure does not exceed configure data, Reseting counters for bind...");
            } else {
                this.h++;
                this.k = ts;
                LogCatUtil.debug("EXT_Watchdog", "IPC Bind failure ticked, current failures:" + this.h + " failure duration:" + (ts - this.j) + RPCDataParser.TIME_MS);
                b();
            }
        }
    }

    private void a() {
        if (this.h != 0) {
            this.h = 0;
            this.j = 0;
            this.k = 0;
            LogCatUtil.debug("EXT_Watchdog", "Bind failure reset to 0");
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.f > 0 && this.o.compareAndSet(true, false)) {
            NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
                public void run() {
                    ExtTransportTunnelWatchdog.this.b();
                    ExtTransportTunnelWatchdog.this.o.set(true);
                }
            }, this.c + 1000, TimeUnit.MILLISECONDS);
        }
        long ts = System.currentTimeMillis();
        if (this.f > 0 && (this.f >= this.b || ts - this.g > this.c)) {
            LogCatUtil.debug("EXT_Watchdog", "Will start downgrade due to RPC failure condition");
            startTunnelDowngrade(DOWNGRADE_REASON_RPC);
        } else if (this.h >= this.d) {
            LogCatUtil.debug("EXT_Watchdog", "Will start downgrade due to Bind failure condition");
            LogCatUtil.debug("EXT_Watchdog", String.format("bc = %d, bmc = %d, ts = %d, tms = %d", new Object[]{Integer.valueOf(this.h), Integer.valueOf(this.d), Long.valueOf(ts - this.j), Long.valueOf(this.e)}));
            startTunnelDowngrade(DOWNGRADE_REASON_BIND);
        }
    }

    public synchronized void startTunnelDowngrade(String reason) {
        a(reason, TransportEnvUtil.getContext());
    }

    private void a(String reason, Context ctx) {
        TransportConfigureManager configureManager = TransportConfigureManager.getInstance();
        String downgradeSwitch = configureManager.getStringValue((ConfigureItem) TransportConfigureItem.ALLOW_AMNET_DOWNGRADE);
        if (TextUtils.isEmpty(downgradeSwitch) || !downgradeSwitch.startsWith("T")) {
            LogCatUtil.debug("EXT_Watchdog", "Config does not allow downgrade");
            return;
        }
        synchronized (this.m) {
            if (this.l) {
                LogCatUtil.debug("EXT_Watchdog", "Tunnel is already downgraded, not need to do it again");
                mrpcFailureRest();
                return;
            }
            this.l = true;
            LogCatUtil.debug("EXT_Watchdog", "Starting tunnel switch to downgrade to SPDY");
            this.n = configureManager.getStringValue((ConfigureItem) TransportConfigureItem.AMNET_SWITCH);
            LogCatUtil.verbose("EXT_Watchdog", "Original AMNET_SWTICH:" + this.n);
            configureManager.setValue(TransportConfigureItem.AMNET_SWITCH, "0,0,0");
            int oldversion = configureManager.getLatestVersion() + 1;
            configureManager.setValue(TransportConfigureItem.VERSION, String.valueOf(oldversion));
            Map config = new HashMap();
            config.put(TransportConfigureItem.AMNET_SWITCH.getConfigName(), "0,0,0");
            config.put(TransportConfigureItem.VERSION.getConfigName(), String.valueOf(oldversion));
            configureManager.updateConfig(ctx, config, (String) "android_network_core");
            NwSharedSwitchUtil.notifySwitchUpdate();
            LogCatUtil.debug("EXT_Watchdog", "Tunnel Downgrade Done");
            a((String) "downgrade", reason, ctx);
            mrpcFailureRest();
            a();
        }
    }

    public boolean isDowngraded() {
        boolean z;
        synchronized (this.m) {
            z = this.l;
        }
        return z;
    }

    private void a(String Op, String reason, Context ctx) {
        try {
            int net0 = ConnectionUtil.getConnType(ctx);
            int net1 = ConnectionUtil.getNetworkType(ctx);
            Performance pf = new TransportPerformance();
            pf.setSubType("MMTP");
            pf.setParam1("1.0");
            pf.setParam2(net0 + "_" + net1);
            pf.setParam3(Op);
            boolean isPushAlive = MiscUtils.isPushProcessRuning(ctx);
            if (TextUtils.equals(Op, "downgrade")) {
                pf.getExtPramas().put("rpc_fc", String.valueOf(this.f));
                pf.getExtPramas().put("bind_fc", String.valueOf(this.h));
                pf.getExtPramas().put("crash_fc", String.valueOf(this.i));
                pf.getExtPramas().put("reason", reason);
                pf.getExtPramas().put("push_alive", isPushAlive ? "1" : "0");
            }
            MonitorLoggerUtils.uploadPerfLog(pf);
            LogCatUtil.debug("EXT_Watchdog", "Dumping perfLog:" + pf.toString());
            LoggerFactory.getMonitorLogger().mtBizReport("BIZ_NETWORK", "LINK_DOWNGRADE", "0", pf.getExtPramas());
            if (TextUtils.equals(Op, "downgrade") && !isPushAlive) {
                Performance performance = new Performance();
                performance.setSubType("PROCESS");
                performance.setParam1(net0 + "_" + net1);
                performance.setParam2(Op);
                performance.setParam3("dead_push_process");
                performance.getExtPramas().putAll(pf.getExtPramas());
                LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_NETWORK, performance);
            }
            boolean isBindFailed = false;
            if (TextUtils.equals(reason, DOWNGRADE_REASON_BIND) || TextUtils.equals(reason, DOWNGRADE_REASON_CRASH)) {
                isBindFailed = true;
            }
            MonitorFactory.getMonitorContext().kickOnNetworkBindService("network", isBindFailed, reason);
        } catch (Throwable ex) {
            LogCatUtil.error((String) "EXT_Watchdog", ex);
        }
    }

    public void resetDowngradeFlag() {
        synchronized (this.m) {
            LogCatUtil.debug("EXT_Watchdog", "resetting isDowngraded...");
            this.l = false;
        }
        a((String) "upgrade", (String) "swtich_update", TransportEnvUtil.getContext());
    }

    private static boolean a(Context context, long maxProcCrashTimeAllow, int procCrashCount) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(SP_SUB_KEY_MAX_CRASH_TIME_ALLOW, maxProcCrashTimeAllow);
            jsonObject.put(SP_SUB_KEY_PROC_CRASH_COUNT, procCrashCount);
            String s = jsonObject.toString();
            boolean result = SharedPreUtils.putData(context, (String) SP_KEY_PROC_CRASH_TICK, s);
            LogCatUtil.info("EXT_Watchdog", "SP_KEY_PROC_CRASH_TICK save finish, result:" + result + ".  json: " + s);
            return result;
        } catch (JSONException e2) {
            LogCatUtil.error("EXT_Watchdog", "Put json data exception", e2);
            return false;
        }
    }

    private String a(Context context) {
        String procCrashTickJson = SharedPreUtils.getStringData(context, SP_KEY_PROC_CRASH_TICK);
        if (!TextUtils.isEmpty(procCrashTickJson)) {
            return procCrashTickJson;
        }
        a(context, System.currentTimeMillis() + this.e, 0);
        LogCatUtil.info("EXT_Watchdog", "SP_KEY_PROC_CRASH_TICK no exist, regenerate the model ! ");
        return "";
    }

    private void b(Context context) {
        int procCrashCount;
        if (!TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.PROC_CRASH_HANDLE_SWITCH, "T")) {
            LogCatUtil.info("EXT_Watchdog", "pushProcCrashTick switch is off");
        } else if (!NetworkTunnelStrategy.getInstance().isCanUseAmnet()) {
            LogCatUtil.verbose("EXT_Watchdog", "AMNET is disabled already, no need to tick");
        } else if (isDowngraded()) {
            LogCatUtil.verbose("EXT_Watchdog", "Already downgraded, no need to tick");
        } else {
            String procCrashTickJson = a(context);
            if (!TextUtils.isEmpty(procCrashTickJson)) {
                try {
                    JSONObject procCrashTickJsonObj = new JSONObject(procCrashTickJson);
                    try {
                        long maxCrashTimeAllow = procCrashTickJsonObj.getLong(SP_SUB_KEY_MAX_CRASH_TIME_ALLOW);
                        int procCrashCount2 = procCrashTickJsonObj.getInt(SP_SUB_KEY_PROC_CRASH_COUNT);
                        if (System.currentTimeMillis() <= maxCrashTimeAllow) {
                            procCrashCount = procCrashCount2 + 1;
                            LogCatUtil.info("EXT_Watchdog", "increasing procCrashCount：" + procCrashCount);
                        } else {
                            procCrashCount = 0;
                            maxCrashTimeAllow = System.currentTimeMillis() + this.e;
                        }
                        a(context, maxCrashTimeAllow, procCrashCount);
                        if (procCrashCount >= this.d) {
                            this.i = procCrashCount;
                            LogCatUtil.debug("EXT_Watchdog", String.format("Will start downgrade due to proc crash condition. procCrashCount = %d, maxCrashTimeAllow = %d, mMaxBindTickAllow = %d", new Object[]{Integer.valueOf(procCrashCount), Long.valueOf(maxCrashTimeAllow), Integer.valueOf(this.d)}));
                            a(DOWNGRADE_REASON_CRASH, context);
                        }
                        this.i = 0;
                    } catch (Throwable e2) {
                        LogCatUtil.error("EXT_Watchdog", "Get data from json obj exception, procCrashTickJson: " + procCrashTickJson, e2);
                        SharedPreUtils.removeData(context, SP_KEY_PROC_CRASH_TICK);
                    }
                } catch (JSONException e3) {
                    LogCatUtil.error("EXT_Watchdog", "New JSONObject exception, procCrashTickJson: " + procCrashTickJson, e3);
                    SharedPreUtils.removeData(context, SP_KEY_PROC_CRASH_TICK);
                }
            }
        }
    }
}
