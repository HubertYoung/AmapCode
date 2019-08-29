package com.alipay.mobile.common.transport.httpdns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import java.util.concurrent.TimeUnit;

public class NetworkManager {
    public static final String TAG = "HTTP_DNS_NetManager";
    public static final long changeInterval = 3600000;
    public static final long maxTimesLimit = 12;
    private Context a;
    private BroadcastReceiver b;
    private byte c;
    public long changeBegin;
    public int changeCount;
    private String d;
    private String e;
    Boolean lastConnected;
    int netSubType;
    int netType;
    boolean neverReceive;

    class Singleton {
        static NetworkManager instance = new NetworkManager();

        private Singleton() {
        }
    }

    public static NetworkManager getInstance() {
        return Singleton.instance;
    }

    private NetworkManager() {
        this.changeBegin = -1;
        this.changeCount = -1;
        this.a = null;
        this.b = null;
        this.lastConnected = null;
        this.neverReceive = true;
        this.netType = -1;
        this.netSubType = -1;
        this.c = -1;
        this.d = "";
        this.e = "";
    }

    public synchronized void setNetworkContext(Context context) {
        if (b(context)) {
            this.a = context;
            c();
            this.b = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (intent != null) {
                        LogCatUtil.info("monitor", "onReceive at: " + getClass().getName() + ", Intent: " + intent);
                        try {
                            if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) || NetworkManager.this.a(context)) {
                            }
                        } catch (Throwable ex) {
                            LogCatUtil.error(NetworkManager.TAG, "setNetworkContext exception ", ex);
                        }
                    }
                }
            };
            IntentFilter mFilter = new IntentFilter();
            mFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(this.b, mFilter);
        }
    }

    /* access modifiers changed from: private */
    public boolean a(final Context context) {
        if (c(context) == 1) {
            HttpDns.getInstance().getGetAllByNameHelper().clearCache();
            LogCatUtil.debug(TAG, "NetworkManager#onReceive 网络变化");
            if (this.neverReceive) {
                LogCatUtil.debug(TAG, "NetworkManager#onReceive first receive,ignore");
                this.neverReceive = false;
                NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
                    public void run() {
                        NetworkManager.this.a(context, true);
                    }
                }, 10, TimeUnit.SECONDS);
                return true;
            }
            NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                public void run() {
                    NetworkManager.this.a(context, NetworkManager.this.neverReceive);
                }
            });
            if (!a()) {
                return true;
            }
            LogCatUtil.info(TAG, "网络切换，发送强请求");
            HttpDns.getInstance().delayRequestStrong(1);
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void a(Context context, boolean firstTime) {
        try {
            if (!MiscUtils.isInAlipayClient(context)) {
                String netinfoDetail = d(context);
                if (!TextUtils.isEmpty(netinfoDetail)) {
                    a(netinfoDetail, firstTime);
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "ex:" + ex.toString());
        }
    }

    private boolean b(Context context) {
        if (context != null && this.a == null && AlipayHttpDnsClient.getDnsClient().getFlag() == 0) {
            return true;
        }
        return false;
    }

    private boolean a() {
        if (!MiscUtils.isAtFrontDesk(this.a)) {
            LogCatUtil.warn((String) TAG, (String) "wallet isn't at front desk,httpdns ignore netchange");
            return false;
        } else if (b()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean b() {
        this.changeCount = d();
        int i = this.changeCount + 1;
        this.changeCount = i;
        a(i);
        if (((long) this.changeCount) > 12) {
            this.changeBegin = f();
            if (this.changeBegin + 3600000 < System.currentTimeMillis()) {
                a(1);
                e();
                LogCatUtil.info(TAG, "interval more than one hour,set changeCount 1");
            } else {
                LogCatUtil.info(TAG, "netchange exceeds 12 ,ignore it");
                return false;
            }
        }
        this.changeCount = d();
        LogCatUtil.info(TAG, "httpdns network change,changeCount= " + this.changeCount);
        return true;
    }

    private void c() {
        this.changeBegin = f();
        if (this.changeBegin == -1) {
            e();
        }
        this.changeCount = d();
        if (this.changeCount == -1) {
            a(0);
        }
    }

    public void close() {
        if (this.a != null) {
            LogCatUtil.info(TAG, "httpdns manager close");
            try {
                this.a.unregisterReceiver(this.b);
            } catch (IllegalArgumentException ex) {
                LogCatUtil.warn((String) TAG, "httpdns exception: " + ex.toString());
            }
            this.b = null;
        }
    }

    private int c(Context context) {
        NetworkInfo activeNetworkInfo = NetworkUtils.getActiveNetworkInfo(context);
        if (activeNetworkInfo == null) {
            LogCatUtil.info(TAG, "当前无网络!");
            this.lastConnected = Boolean.valueOf(false);
            return 0;
        }
        boolean available = activeNetworkInfo.isAvailable();
        boolean connected = activeNetworkInfo.isConnected();
        int type = activeNetworkInfo.getType();
        int subType = activeNetworkInfo.getSubtype();
        String extraInfo = activeNetworkInfo.getExtraInfo();
        if (!a(connected, type, subType)) {
            return 2;
        }
        this.lastConnected = Boolean.valueOf(connected);
        this.netType = type;
        this.netSubType = subType;
        LogCatUtil.info(TAG, " type=[" + type + "] subType=[" + subType + "]  available=[" + available + "] connected=[" + connected + "] detailedState=[" + activeNetworkInfo.getDetailedState() + "] extraInfo=[" + extraInfo + "]");
        LogCatUtil.info(TAG, " activeNetworkInfo hashcode=" + activeNetworkInfo.hashCode() + "  activeNetworkInfo = [" + activeNetworkInfo.toString() + "]\n");
        if (this.lastConnected.booleanValue()) {
            return 1;
        }
        return 0;
    }

    private boolean a(boolean connected, int type, int subType) {
        if (this.netType == -1 || this.netSubType == -1 || this.lastConnected == null) {
            LogCatUtil.info(TAG, " New contivity broadcast！");
        } else if (this.lastConnected.booleanValue() == connected && this.netType == type && this.netSubType == subType) {
            LogCatUtil.info(TAG, " Old contivity broadcast！");
            return false;
        }
        return true;
    }

    private void a(int count) {
        SharedPreUtils.putData(this.a, (String) "http_dns_netchangecount", count);
    }

    private int d() {
        return SharedPreUtils.getIntData(this.a, "http_dns_netchangecount");
    }

    private void e() {
        SharedPreUtils.putData(this.a, (String) "dns_netchange_begin", System.currentTimeMillis());
    }

    private long f() {
        return SharedPreUtils.getLonggData(this.a, "dns_netchange_begin");
    }

    private String d(Context context) {
        String netinfoDetail;
        try {
            NetworkInfo activeNetworkInfo = NetworkUtils.getActiveNetworkInfo(context);
            if (activeNetworkInfo != null) {
                this.c = 2;
                this.d = this.e;
                this.e = activeNetworkInfo.getTypeName();
                netinfoDetail = activeNetworkInfo.toString();
                if (activeNetworkInfo.getType() == 1) {
                    WifiInfo wifiInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
                    if (wifiInfo != null) {
                        String bssid = wifiInfo.getBSSID();
                        if (!TextUtils.isEmpty(bssid)) {
                            netinfoDetail = netinfoDetail + " bssid=[" + bssid + "]";
                        }
                        String ssid = wifiInfo.getSSID();
                        if (TextUtils.isEmpty(activeNetworkInfo.getExtraInfo()) && !TextUtils.isEmpty(ssid)) {
                            netinfoDetail = netinfoDetail + " ssid=[" + ssid + "]";
                        }
                    }
                }
            } else if (this.c == 1) {
                LogCatUtil.debug(TAG, "network is not available,repeat broadcast,ignore");
                return null;
            } else {
                this.c = 1;
                this.d = this.e;
                this.e = "no net";
                netinfoDetail = "current no net";
            }
            if (!TextUtils.isEmpty(this.d)) {
                return netinfoDetail + " lastTypeName=[" + this.d + "]";
            }
            return netinfoDetail;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "getNetinfoDetail ex:" + ex.toString());
            return "";
        }
    }

    private void a(String netinfoDetail, boolean firstTime) {
        Performance pf = new TransportPerformance();
        pf.setSubType("NetChange");
        pf.setParam1("1.0");
        pf.setParam2(this.d);
        pf.setParam3(this.e);
        pf.getExtPramas().put(RPCDataItems.NetInfo, netinfoDetail);
        if (MiscUtils.isAtFrontDesk(TransportEnvUtil.getContext())) {
            pf.getExtPramas().put("Ground", "Fg");
        } else {
            pf.getExtPramas().put("Ground", "Bg");
        }
        if (NetworkUtils.isVpnUsed()) {
            pf.getExtPramas().put("VPN", "T");
        }
        if (firstTime) {
            pf.getExtPramas().put("First", "T");
        }
        MonitorLoggerUtils.uploadPerfLog(pf);
        LogCatUtil.debug(TAG, pf.toString());
    }
}
