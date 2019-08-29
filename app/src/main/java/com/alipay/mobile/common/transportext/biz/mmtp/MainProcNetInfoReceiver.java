package com.alipay.mobile.common.transportext.biz.mmtp;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.alipay.mobile.common.adapter.RigorousNetworkConnReceiver;
import com.alipay.mobile.common.amnet.ipcapi.pushproc.OutEventNotifyService;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.httpdns.HttpDns;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.SignalStateHelper;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import java.util.concurrent.TimeUnit;

public class MainProcNetInfoReceiver extends RigorousNetworkConnReceiver {
    private static MainProcNetInfoReceiver INSTANCE = null;
    private static final byte NETSTATE_CONTECT = 2;
    private static final byte NETSTATE_NO_NET = 1;
    private static final byte NETSTATE_UNKNOWN = -1;
    private static final String TAG = "MainProcNetInfoReceiver";
    public static final long changeInterval = 3600000;
    private int MAXNUM = 30;
    private int changeCount = 0;
    private String curTypeName = "";
    private long firstChangeTime = System.currentTimeMillis();
    private byte lastNetState = -1;
    private String lastTypeName = "";
    /* access modifiers changed from: private */
    public boolean neverReceive = true;

    public static MainProcNetInfoReceiver getInstance(Context context) {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (MainProcNetInfoReceiver.class) {
            if (INSTANCE == null) {
                INSTANCE = new MainProcNetInfoReceiver(context);
            }
        }
        return INSTANCE;
    }

    private MainProcNetInfoReceiver(Context context) {
        super(context);
        LogCatUtil.verbose(TAG, "MainProcNetInfoReceiver init");
    }

    /* access modifiers changed from: protected */
    public void onReceivee(Context context, Intent intent) {
        if (context != null && intent != null) {
            try {
                notifyNetInfo(intent);
                perfNetworkInfo(context);
                HttpDns.getInstance().getGetAllByNameHelper().clearCache();
            } catch (Throwable ex) {
                LogCatUtil.error(TAG, "onReceivee exception", ex);
            }
        }
    }

    private void notifyNetInfo(final Intent intent) {
        try {
            LogCatUtil.debug(TAG, "==notifyNetInfo==");
            NetworkAsyncTaskExecutor.executeLowPri(new Runnable() {
                public void run() {
                    try {
                        ((OutEventNotifyService) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(OutEventNotifyService.class)).receiveNetInfo(intent);
                    } catch (Throwable e) {
                        LogCatUtil.error(MainProcNetInfoReceiver.TAG, "notifyNetInfo inner exception", e);
                    }
                }
            });
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "notifyNetInfo exception", ex);
        }
    }

    private void perfNetworkInfo(final Context context) {
        if (this.neverReceive) {
            LogCatUtil.debug(TAG, "first receive,record after 10s");
            this.neverReceive = false;
            NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
                public void run() {
                    MainProcNetInfoReceiver.this.doPerfNetworkInfo(context, true);
                }
            }, 10, TimeUnit.SECONDS);
            return;
        }
        NetworkAsyncTaskExecutor.executeIO(new Runnable() {
            public void run() {
                MainProcNetInfoReceiver.this.doPerfNetworkInfo(context, MainProcNetInfoReceiver.this.neverReceive);
            }
        });
    }

    /* access modifiers changed from: private */
    public void doPerfNetworkInfo(Context context, boolean firstTime) {
        String netinfoDetail = getNetinfoDetail(context);
        if (!TextUtils.isEmpty(netinfoDetail) && shouldReport()) {
            reportNetinfo(netinfoDetail, firstTime);
        }
    }

    private boolean shouldReport() {
        this.changeCount++;
        if (this.changeCount > this.MAXNUM) {
            if (this.firstChangeTime + 3600000 < System.currentTimeMillis()) {
                this.changeCount = 0;
                this.firstChangeTime = System.currentTimeMillis();
            } else {
                LogCatUtil.debug(TAG, "netchange count is out " + this.MAXNUM + ",don't report any more");
                return false;
            }
        }
        return true;
    }

    private String getNetinfoDetail(Context context) {
        String netinfoDetail;
        try {
            NetworkInfo activeNetworkInfo = NetworkUtils.getActiveNetworkInfo(context);
            if (activeNetworkInfo != null) {
                this.lastNetState = 2;
                this.lastTypeName = this.curTypeName;
                this.curTypeName = activeNetworkInfo.getTypeName();
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
            } else if (this.lastNetState == 1) {
                LogCatUtil.debug(TAG, "network is not available,repeat broadcast,ignore");
                return null;
            } else {
                this.lastNetState = 1;
                this.lastTypeName = this.curTypeName;
                this.curTypeName = "无网络";
                netinfoDetail = "当前无网络";
            }
            if (!TextUtils.isEmpty(this.lastTypeName)) {
                return netinfoDetail + " lastTypeName=[" + this.lastTypeName + "]";
            }
            return netinfoDetail;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "getNetinfoDetail ex:" + ex.toString());
            return "";
        }
    }

    private void reportNetinfo(String netinfoDetail, boolean firstTime) {
        Performance pf = new TransportPerformance();
        pf.setSubType("NetChange");
        pf.setParam1(MonitorLoggerUtils.getLogBizType("NetChange"));
        pf.setParam2(this.lastTypeName);
        pf.setParam3(this.curTypeName);
        pf.getExtPramas().put(RPCDataItems.NetInfo, netinfoDetail);
        if (MiscUtils.isAtFrontDesk(ExtTransportEnv.getAppContext())) {
            pf.getExtPramas().put("Ground", "Fg");
        } else {
            pf.getExtPramas().put("Ground", "Bg");
        }
        String latLng = getLatLng();
        if (!TextUtils.isEmpty(latLng)) {
            pf.getExtPramas().put("Loc", latLng);
        }
        if (NetworkUtils.isVpnUsed()) {
            pf.getExtPramas().put("VPN", "T");
        }
        if (firstTime) {
            pf.getExtPramas().put("First", "T");
        }
        MonitorLoggerUtils.uploadPerfLog(pf);
        LogCatUtil.debug(TAG, pf.toString());
        SignalStateHelper.getInstance().reportNetStateInfo();
    }

    private String getLatLng() {
        try {
            String lat = DeviceInfo.getInstance().getLatitude();
            String lng = DeviceInfo.getInstance().getLongitude();
            if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) {
                return "";
            }
            return lat + "_" + lng;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
            return "";
        }
    }
}
