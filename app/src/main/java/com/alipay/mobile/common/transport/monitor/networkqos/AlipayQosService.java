package com.alipay.mobile.common.transport.monitor.networkqos;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo.DeviceTrafficStateInfoDelta;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;

public class AlipayQosService {
    public static final byte QOE_FROM_DIAG = 3;
    public static final byte QOE_FROM_INTERFERENCE = 6;
    public static final byte QOE_FROM_MMTP = 1;
    public static final byte QOE_FROM_NBET = 4;
    public static final byte QOE_FROM_READING = 5;
    public static final byte QOE_FROM_TCP = 2;
    public static final double QOE_NET_BAD = 5000.0d;
    public static final byte QOS_LEVEL_A = 1;
    public static final byte QOS_LEVEL_B = 2;
    public static final byte QOS_LEVEL_C = 3;
    public static final byte QOS_LEVEL_D = 4;
    private static AlipayQosService a = null;
    /* access modifiers changed from: private */
    public QoeManager b = null;
    private DeviceTrafficManager c = null;
    private double d;
    private double e;
    private double f;
    private double g;
    private double h;
    private double i;
    private int j;

    public static AlipayQosService getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (AlipayQosService.class) {
            try {
                if (a == null) {
                    a = new AlipayQosService();
                }
            }
        }
        return a;
    }

    private AlipayQosService() {
        a();
        this.b = QoeManager.getInstance();
        this.c = DeviceTrafficManager.getInstance();
    }

    private void a() {
        TransportConfigureManager configMgr = TransportConfigureManager.getInstance();
        this.d = configMgr.getDoubleValue(TransportConfigureItem.RTO_BOUND_A);
        this.e = configMgr.getDoubleValue(TransportConfigureItem.RTO_BOUND_B);
        this.f = configMgr.getDoubleValue(TransportConfigureItem.RTO_BOUND_C);
        this.g = configMgr.getDoubleValue(TransportConfigureItem.SPEED_BOUND_A);
        this.h = configMgr.getDoubleValue(TransportConfigureItem.SPEED_BOUND_B);
        this.i = configMgr.getDoubleValue(TransportConfigureItem.SPEED_BOUND_C);
        this.j = -1;
    }

    public int getQosLevel() {
        double d2;
        try {
            if (!TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NET_QOS_SWITCH), "T")) {
                LogCatUtil.debug("AlipayQosService", "qosSwitch is off,always return A level");
                return 1;
            } else if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                return 4;
            } else {
                double rto = getRto();
                double speed = getSpeed();
                double bandwidth = getBandwidth();
                if (speed > bandwidth) {
                    d2 = speed;
                } else {
                    d2 = bandwidth;
                }
                int level = a(rto, d2);
                LogCatUtil.debug("AlipayQosService", "rto=[" + String.format("%.4f", new Object[]{Double.valueOf(rto)}) + "],speed=[" + String.format("%.4f", new Object[]{Double.valueOf(speed)}) + "],bandwidth=[" + String.format("%.4f", new Object[]{Double.valueOf(bandwidth)}) + "] level=[" + level + "]");
                if (this.j - level > 2) {
                    level++;
                }
                a(rto, speed, bandwidth, level);
                if (level != 4 || NetworkUtils.getNetworkType(TransportEnvUtil.getContext()) != 4) {
                    return level;
                }
                LogCatUtil.warn((String) "AlipayQosService", (String) "Though result is D,but it's 4G now,return C instead");
                return 3;
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "AlipayQosService", ex);
            return 3;
        }
    }

    private void a(double rto, double speed, double bandwidth, int level) {
        if (level == 4) {
            LogCatUtil.warn((String) "AlipayQosService", (String) "========here level is D==========");
        }
        if (this.j != level) {
            this.j = level;
            Performance pf = new TransportPerformance();
            pf.setSubType("NETQOS");
            pf.setParam1(MonitorLoggerUtils.getLogBizType("NETQOS"));
            pf.setParam2("INFO");
            pf.setParam3(String.valueOf(level));
            pf.getExtPramas().put("RTO", String.format("%.4f", new Object[]{Double.valueOf(rto)}));
            pf.getExtPramas().put(RPCDataItems.SPEED, String.format("%.4f", new Object[]{Double.valueOf(speed)}));
            pf.getExtPramas().put("BANDWIDTH", String.format("%.4f", new Object[]{Double.valueOf(bandwidth)}));
            pf.getExtPramas().put("LEVEL", String.valueOf(level));
            pf.getExtPramas().put("NETTYPE", String.valueOf(NetworkUtils.getNetType(TransportEnvUtil.getContext())));
            LogCatUtil.debug("AlipayQosService", pf.toString());
            MonitorLoggerUtils.uploadPerfLog(pf);
        }
    }

    private int a(double rtt, double speed) {
        int rtoLevel = getRtoLevel(rtt);
        if (rtoLevel == 1) {
            return 1;
        }
        if (a(speed) < rtoLevel) {
            return rtoLevel - 1;
        }
        return rtoLevel;
    }

    public int getRtoLevel(double rtt) {
        if (rtt <= this.d) {
            return 1;
        }
        if (this.d < rtt && rtt <= this.e) {
            return 2;
        }
        if (rtt <= this.e || rtt > this.f) {
            return 4;
        }
        return 3;
    }

    private int a(double speed) {
        if (speed >= this.g) {
            return 1;
        }
        if (this.h <= speed && speed < this.g) {
            return 2;
        }
        if (this.i > speed || speed >= this.h) {
            return 4;
        }
        return 3;
    }

    public void estimate(double rtt, final byte from) {
        try {
            if (NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext()) && rtt >= 0.0d) {
                if (rtt > 5000.0d) {
                    rtt = 5000.0d;
                }
                final double finalRtt = rtt;
                NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                    public void run() {
                        AlipayQosService.this.b.estimate(QosInterfereHelper.getInstance().interferInputRtt(finalRtt), from);
                        QosInterfereHelper.getInstance().interferOutputRtt(finalRtt, AlipayQosService.this.getRto());
                    }
                });
            }
        } catch (Throwable ex) {
            LogCatUtil.error("AlipayQosService", "estimate exception", ex);
        }
    }

    public double getRto() {
        return this.b.getRto();
    }

    public double getSpeed() {
        return this.c.getSpeed();
    }

    public void setSpeed(double speed) {
        this.c.setSpeed(speed);
    }

    public double getBandwidth() {
        return this.c.getBandwidth();
    }

    public DeviceTrafficStateInfo startTrafficMonitor() {
        return this.c.startTrafficMonitor();
    }

    public DeviceTrafficStateInfoDelta stopTrafficMonitor(DeviceTrafficStateInfo startInfo) {
        try {
            return this.c.stopTrafficMonitor(startInfo);
        } catch (Throwable ex) {
            LogCatUtil.error((String) "AlipayQosService", ex);
            return null;
        }
    }

    public void estimateByStartTime(long everyStartTime, byte from) {
        double d2 = 5000.0d;
        long everyEndReadTime = System.currentTimeMillis();
        if (everyEndReadTime > everyStartTime) {
            int rtt = (int) (everyEndReadTime - everyStartTime);
            if (rtt > 60) {
                AlipayQosService instance = getInstance();
                if (((double) rtt) <= 5000.0d) {
                    d2 = (double) rtt;
                }
                instance.estimate(d2, from);
            }
        }
    }
}
