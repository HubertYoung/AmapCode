package com.alipay.mobile.common.transport.monitor.networkqos;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.QoeModel;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;

public class QoeManager {
    private static QoeManager b = null;
    private QoeModel[] a;
    private int c;

    public static QoeManager getInstance() {
        if (b != null) {
            return b;
        }
        synchronized (QoeManager.class) {
            try {
                if (b == null) {
                    b = new QoeManager();
                }
            }
        }
        return b;
    }

    private QoeManager() {
        this.a = null;
        this.c = 5;
        this.a = new QoeModel[this.c];
        for (int i = 0; i < this.c; i++) {
            this.a[i] = new QoeModel();
        }
    }

    public void estimate(double rtt, byte from) {
        int netType = NetworkUtils.getNetworkType(TransportEnvUtil.getContext());
        this.a[netType].estimate(rtt);
        if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
            LogCatUtil.printInfo("QoeManager", "from=" + from + ",netType=" + netType + ",input: rtt=" + rtt + ",output: rtt_o=" + this.a[netType].rtt_o + ",rtt_s=" + this.a[netType].rtt_s + ",rtt_d=" + this.a[netType].rtt_d);
        }
    }

    public double getRto() {
        return this.a[NetworkUtils.getNetworkType(TransportEnvUtil.getContext())].rtt_o;
    }
}
