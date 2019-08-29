package com.alipay.mobile.mrtc.api;

import android.os.Bundle;
import com.alipay.mobile.mrtc.api.report.APStatsReport;

public interface APCallListener {
    void onCallRoomInfo(APRoomInfo aPRoomInfo);

    void onEvent(int i, String str, Bundle bundle);

    void onNetworkChanged(int i);

    void onOuterInterrupt(int i);

    void onStatsReport(APStatsReport[] aPStatsReportArr);
}
