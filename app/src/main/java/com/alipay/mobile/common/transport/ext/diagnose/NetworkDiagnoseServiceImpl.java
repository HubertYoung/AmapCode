package com.alipay.mobile.common.transport.ext.diagnose;

import com.alipay.mobile.common.transport.ext.ExtTransportOffice;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;

public class NetworkDiagnoseServiceImpl implements NetworkDiagnoseService {
    private NetworkDiagnoseService a = ExtTransportOffice.getInstance().getNetworkDiagnoseService();

    public void addDiagnoseListener(NetworkDiagnoseListener listener) {
        this.a.addDiagnoseListener(listener);
    }

    public void removeDiagnoseListener(NetworkDiagnoseListener listener) {
        this.a.removeDiagnoseListener(listener);
    }

    public void startDiagnose() {
        this.a.startDiagnose();
    }

    public void cancel() {
        this.a.cancel();
    }

    public boolean isCanDiagnose() {
        if (ExtTransportOffice.getInstance().isEnableExtTransport(TransportEnvUtil.getContext()) && this.a != null) {
            return this.a.isCanDiagnose();
        }
        return false;
    }
}
