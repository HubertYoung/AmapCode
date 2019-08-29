package com.alipay.mobile.common.transport.ext.diagnose.eastereggs;

import com.alipay.mobile.common.transport.ext.ExtTransportOffice;

public class NetDiagnoseService {
    private static NetDiagnoseService a;

    public static NetDiagnoseService getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (NetDiagnoseService.class) {
            if (a != null) {
                NetDiagnoseService netDiagnoseService = a;
                return netDiagnoseService;
            }
            a = new NetDiagnoseService();
            return a;
        }
    }

    private NetDiagnoseService() {
    }

    public void launch(DiagnoseResult diagnoseResult) {
        ExtTransportOffice.getInstance().diagnoseForEasterEggs(diagnoseResult);
    }
}
