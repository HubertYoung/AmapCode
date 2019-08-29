package com.alipay.mobile.common.transport.strategy;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.utils.config.ConfigureChangedListener;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.util.Observable;

public class NetworkTunnelStrategy {
    public static final int TUNNEL_TYPE_AMNET = 3;
    public static final int TUNNEL_TYPE_ORIGINAL = 1;
    public static final int TUNNEL_TYPE_SPDY = 2;
    public static final int TUNNEL_TYPE_UNINIT = -1;
    private static NetworkTunnelStrategy a;
    private NetworkTunnelChangedObservible b;
    private NetworkConfigureChangedListener c;
    /* access modifiers changed from: private */
    public int d = -1;
    /* access modifiers changed from: private */
    public int e = -1;
    private boolean f = false;
    private boolean g = false;
    private boolean h = false;
    /* access modifiers changed from: private */
    public Context i;
    private String j;
    private boolean k = false;

    class NetworkConfigureChangedListener implements ConfigureChangedListener {
        NetworkConfigureChangedListener() {
        }

        public void update(Observable observable, Object data) {
            try {
                LogCatUtil.debug("NetworkTunnelStrategy", "update change");
                if (NetworkTunnelStrategy.this.i == null) {
                    LogCatUtil.error((String) "NetworkTunnelStrategy", (String) "Context is null. so dangerous!!");
                    return;
                }
                NetworkTunnelStrategy.this.c();
                if (NetworkTunnelStrategy.this.e != NetworkTunnelStrategy.this.d) {
                    int localCurrentTunnlType = NetworkTunnelStrategy.this.d;
                    NetworkTunnelStrategy.this.d = NetworkTunnelStrategy.this.e;
                    TunnelChangeEventModel tunnelChangeEventModel = new TunnelChangeEventModel();
                    tunnelChangeEventModel.currentTunnelType = localCurrentTunnlType;
                    tunnelChangeEventModel.newTunnelType = NetworkTunnelStrategy.this.e;
                    NetworkTunnelStrategy.this.notifyTunnelChanged(tunnelChangeEventModel);
                }
            } catch (Throwable e) {
                LogCatUtil.error((String) "NetworkTunnelStrategy", e);
            }
        }
    }

    class NetworkTunnelChangedObservible extends Observable {
        NetworkTunnelChangedObservible() {
        }

        public void notifyObservers(Object data) {
            setChanged();
            super.notifyObservers(data);
        }
    }

    public static final NetworkTunnelStrategy getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (NetworkTunnelStrategy.class) {
            try {
                if (a != null) {
                    NetworkTunnelStrategy networkTunnelStrategy = a;
                    return networkTunnelStrategy;
                }
                a = new NetworkTunnelStrategy();
                return a;
            }
        }
    }

    private NetworkTunnelStrategy() {
        n();
    }

    public void init(Context context, String utdid) {
        if (!this.k) {
            this.k = true;
            this.i = context;
            this.j = utdid;
            b();
        }
    }

    public boolean isUse4Utdid(String utdid, int grayValue1, int grayValue2) {
        return StrategyUtil.grayscaleUtdid(utdid, new int[]{grayValue1, grayValue2});
    }

    private final boolean a() {
        if (MiscUtils.grayscaleUtdid(this.j, TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.AMNET_SWITCH))) {
            LogCatUtil.info("NetworkTunnelStrategy", "isEnabledAmnet is true, utdid=" + this.j);
            return true;
        }
        LogCatUtil.info("NetworkTunnelStrategy", "isEnabledAmnet is false, utdid=" + this.j);
        return false;
    }

    public boolean isUseExtTransport(TransportContext netContext) {
        if (!a(netContext.api) || !d()) {
            return false;
        }
        return true;
    }

    private static boolean a(String operationType) {
        return StrategyUtil.isUse4OperationType(TransportConfigureManager.getInstance().getStringValueList(TransportConfigureItem.OPERATION_TYPE_LIST, ","), operationType);
    }

    private void b() {
        c();
        this.d = this.e;
    }

    /* access modifiers changed from: private */
    public void c() {
        LogCatUtil.debug("NetworkTunnelStrategy", "updateTunnlType");
        TransportConfigureManager transportConfigureManager = TransportConfigureManager.getInstance();
        this.e = -1;
        if (e()) {
            this.g = true;
            this.e = 2;
            if (transportConfigureManager.equalsString(TransportConfigureItem.H5_SPDY_SWITCH, "T")) {
                this.h = true;
            } else {
                this.h = false;
            }
        } else {
            this.g = false;
            this.h = false;
        }
        if (f()) {
            this.e = 3;
            this.f = true;
        } else {
            this.f = false;
        }
        if (this.e == -1) {
            this.e = 1;
        }
    }

    private final boolean d() {
        if (this.d == 2 || this.d == 3) {
            return true;
        }
        return false;
    }

    public boolean isCanUseSpdyDataTunel() {
        if (isCanUseSpdy() && this.d == 2) {
            return true;
        }
        return false;
    }

    public boolean isCanUseSpdy() {
        return this.g;
    }

    private boolean e() {
        if (!m()) {
            return false;
        }
        if (!g()) {
            LogCatUtil.debug("NetworkTunnelStrategy", "isUseSpdy4Devices == false");
            return false;
        } else if (!i() || !k()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isCanUseAmnet() {
        if (this.d == 3) {
            return true;
        }
        return false;
    }

    public boolean isCanUseAmnetOnOnlyPush() {
        if (TransportStrategy.isEnabledOnlyPush()) {
            return true;
        }
        return isCanUseAmnet();
    }

    private boolean f() {
        if (a() && h() && j() && l()) {
            return true;
        }
        return false;
    }

    private static boolean g() {
        TransportConfigureManager configMng = TransportConfigureManager.getInstance();
        return a(configMng.getStringValue((ConfigureItem) TransportConfigureItem.SPDY_BLACK_LIST_PHONE_BRAND), configMng.getStringValue((ConfigureItem) TransportConfigureItem.SPDY_BLACK_LIST_PHONE_MODEL), configMng.getStringValue((ConfigureItem) TransportConfigureItem.SPDY_BLACK_LIST_CPU_MODEL));
    }

    private static boolean h() {
        TransportConfigureManager configMng = TransportConfigureManager.getInstance();
        return a(configMng.getStringValue((ConfigureItem) TransportConfigureItem.AMNET_BLACK_LIST_PHONE_BRAND), configMng.getStringValue((ConfigureItem) TransportConfigureItem.AMNET_BLACK_LIST_PHONE_MODEL), configMng.getStringValue((ConfigureItem) TransportConfigureItem.AMNET_BLACK_LIST_CPU_MODEL));
    }

    private static boolean a(String brandBlackList, String modelBlackList, String cpuModelBackList) {
        if (!StrategyUtil.isUse4Brand(brandBlackList)) {
            LogCatUtil.info("NetworkTunnelStrategy", "isUse4Brand is false. brandBlackList=[" + brandBlackList + "] ");
            return false;
        } else if (!StrategyUtil.isUse4Model(modelBlackList)) {
            LogCatUtil.info("NetworkTunnelStrategy", "isUse4Brand is false. isUse4Model=[" + modelBlackList + "] ");
            return false;
        } else if (StrategyUtil.isUse4Hardware(cpuModelBackList)) {
            return true;
        } else {
            LogCatUtil.info("NetworkTunnelStrategy", "isUse4Hardware is false. cpuModelBackList=[" + cpuModelBackList + "] ");
            return false;
        }
    }

    private boolean i() {
        if (this.i == null) {
            return false;
        }
        if (StrategyUtil.isUse4Net(this.i, TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SPDY_DISABLED_NET_KEY))) {
            return true;
        }
        return false;
    }

    private boolean j() {
        if (this.i == null) {
            LogCatUtil.error((String) "NetworkTunnelStrategy", (String) "mContext is null. so return false");
            return false;
        }
        if (StrategyUtil.isUse4Net(this.i, TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.AMNET_DISABLED_NET_KEY))) {
            return true;
        }
        return false;
    }

    private static boolean k() {
        if (!StrategyUtil.isUse4SdkVersion(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SPDY_DISABLED_SDK_VERSION))) {
            return false;
        }
        return true;
    }

    private static boolean l() {
        if (!StrategyUtil.isUse4SdkVersion(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.AMNET_DISABLED_SDK_VERSION))) {
            return false;
        }
        return true;
    }

    private boolean m() {
        String openSwitch = getConfigureManager().getStringValue((ConfigureItem) TransportConfigureItem.SPDY_SWITCH);
        if (TextUtils.isEmpty(openSwitch) || !openSwitch.startsWith("T")) {
            return false;
        }
        return true;
    }

    public final TransportConfigureManager getConfigureManager() {
        return TransportConfigureManager.getInstance();
    }

    public int getConnFailMaxTime() {
        return TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.CONN_FAIL_MAX_TIMES);
    }

    public void addNetworkTunnelChangedListener(NetworkTunnelChangedListener networkTunnelChangedListener) {
        LogCatUtil.printInfo("NetworkTunnelStrategy", "addNetworkTunnelChangedListener: " + networkTunnelChangedListener.getClass().getName());
        o().addObserver(networkTunnelChangedListener);
    }

    public void removeNetworkTunnelChangedListener(NetworkTunnelChangedListener networkTunnelChangedListener) {
        o().deleteObserver(networkTunnelChangedListener);
    }

    public void notifyNetworkTunnelChangedEvent(int tunnelType) {
        o().notifyObservers(Integer.valueOf(tunnelType));
    }

    public ConfigureChangedListener getConfigureChangedListener() {
        if (this.c == null) {
            this.c = new NetworkConfigureChangedListener();
        }
        return this.c;
    }

    public boolean isCanUseSpdyForH5() {
        return this.h;
    }

    private void n() {
        TransportConfigureManager.getInstance().addConfigureChangedListener(getConfigureChangedListener());
    }

    private NetworkTunnelChangedObservible o() {
        if (this.b == null) {
            this.b = new NetworkTunnelChangedObservible();
        }
        return this.b;
    }

    public void notifyTunnelChanged(TunnelChangeEventModel tunnelChangeEventModel) {
        try {
            o().notifyObservers(tunnelChangeEventModel);
            a(tunnelChangeEventModel);
        } catch (Throwable e2) {
            LogCatUtil.error("NetworkTunnelStrategy", " notifyTunnelChanged exception ", e2);
        }
    }

    public void notifyFirstTunnelChanged() {
        TunnelChangeEventModel tunnelChangeEventModel = new TunnelChangeEventModel();
        tunnelChangeEventModel.currentTunnelType = -1;
        tunnelChangeEventModel.newTunnelType = this.d;
        notifyTunnelChanged(tunnelChangeEventModel);
        LogCatUtil.info("NetworkTunnelStrategy", "notifyFirstTunnelChanged finish.  newTunnelType = " + this.d);
    }

    public boolean isCanUseSpdyLongLink() {
        return TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.SPDY_LONGLINK_SWITCH, "T");
    }

    private static void a(TunnelChangeEventModel tunnelChangeEventModel) {
        try {
            Performance pf = new TransportPerformance();
            pf.setSubType("TunnelChange");
            String oldTun = "";
            String newTun = "";
            if (tunnelChangeEventModel.currentTunnelType == 3) {
                oldTun = "AMNET";
            } else if (tunnelChangeEventModel.currentTunnelType == 2) {
                oldTun = "SPDY";
            }
            if (tunnelChangeEventModel.newTunnelType == 3) {
                newTun = "AMNET";
            } else if (tunnelChangeEventModel.newTunnelType == 2) {
                newTun = "SPDY";
            }
            pf.getExtPramas().put("oldTun", oldTun);
            pf.getExtPramas().put("newTun", newTun);
            pf.setParam1("1.0");
            pf.setParam2(oldTun);
            pf.setParam3(newTun);
            LogCatUtil.debug("NetworkTunnelStrategy", "networkTunnel switch perflog:" + pf.toString());
            MonitorLoggerUtils.uploadPerfLog(pf);
        } catch (Throwable ex) {
            LogCatUtil.error("NetworkTunnelStrategy", "perfLog exception", ex);
        }
    }

    public int getCurrentDataTunnlType() {
        return this.d;
    }
}
