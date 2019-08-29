package com.alipay.mobile.mascanengine.impl;

public class MpaasScanEngineServiceImpl extends MaScanEngineServiceImpl {
    public MpaasScanEngineServiceImpl() {
        onCreate(null);
    }

    public void cleanUp() {
        onDestroy(null);
    }
}
