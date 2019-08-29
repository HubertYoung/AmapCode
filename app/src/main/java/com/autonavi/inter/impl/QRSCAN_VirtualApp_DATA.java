package com.autonavi.inter.impl;

import com.autonavi.minimap.bundle.qrscan.QRScanVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class QRSCAN_VirtualApp_DATA extends ArrayList<Class<?>> {
    public QRSCAN_VirtualApp_DATA() {
        add(QRScanVApp.class);
    }
}
