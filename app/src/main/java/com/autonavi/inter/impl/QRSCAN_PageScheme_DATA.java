package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.bundle.qrscan.page.QRScanPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amapuri://qrscan/mainView"}, module = "qrscan", pages = {"com.autonavi.minimap.bundle.qrscan.page.QRScanPage"})
@KeepName
public final class QRSCAN_PageScheme_DATA extends HashMap<String, Class<?>> {
    public QRSCAN_PageScheme_DATA() {
        put("amapuri://qrscan/mainView", QRScanPage.class);
    }
}
