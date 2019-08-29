package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.mine.qrcode.QRCodeScan"}, inters = {"com.autonavi.minimap.operation.inter.IQRCodeScan"}, module = "qrcode")
@KeepName
public final class QRCODE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public QRCODE_ServiceImpl_DATA() {
        put(dtc.class, cgv.class);
    }
}
