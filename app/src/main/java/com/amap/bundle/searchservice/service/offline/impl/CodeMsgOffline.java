package com.amap.bundle.searchservice.service.offline.impl;

public enum CodeMsgOffline {
    CODE_NATIVE_POI_FORCE(-103, null),
    CODE_NATIVE_POI_NODATA(-100, "网络不畅，且无离线导航数据，请检查网络后重试。"),
    CODE_NATIVE_POI_SUCCESS(-101, "网络不畅，自动转为离线搜索"),
    CODE_NATIVE_POI_NORESULT(-102, "网络不畅，且无离线数据，请检查网络后重试。"),
    CODE_NATIVE_POI_NORESULT_INOFFLIEDATA(-103, "网络不畅且离线地图未找到结果，请检查网络后重试。");
    
    private int nCode;
    private String strCodeMsg;

    private CodeMsgOffline(int i, String str) {
        this.nCode = i;
        this.strCodeMsg = str;
    }

    public final int getnCode() {
        return this.nCode;
    }

    public final void setnCode(int i) {
        this.nCode = i;
    }

    public final String getStrCodeMsg() {
        return this.strCodeMsg;
    }

    public final void setStrCodeMsg(String str) {
        this.strCodeMsg = str;
    }
}
