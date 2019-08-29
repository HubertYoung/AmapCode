package com.autonavi.bundle.routecommon.model;

import com.alipay.mobile.mrtc.api.constants.APCallCode;

public enum OfflineMsgCode {
    CODE_NATIVE_POI_FORCE(-103, null),
    CODE_NATIVE_POI_NODATA(-100, "网络不畅，且无离线导航数据，请检查网络后重试。"),
    CODE_NATIVE_POI_SUCCESS(-101, "网络不畅，自动转为离线搜索"),
    CODE_NATIVE_POI_NORESULT(-102, "网络不畅，且无离线数据，请检查网络后重试。"),
    CODE_NATIVE_POI_NORESULT_INOFFLIEDATA(-103, "网络不畅且离线地图未找到结果，请检查网络后重试。"),
    CODE_NATIVE_TBT_SUCCESS(-110, "网络不畅，已自动转为离线路线规划"),
    CODE_NATIVE_TBT_NODATA(-111, "网络不畅，且无离线导航数据，请检查网络后重试。"),
    CODE_NATIVE_TBT_NORESULT(-112, "网络不畅，且无沿途离线数据包，无法规划路线，请检查网络后重试。"),
    CODE_NATIVE_TBT_NEEDREBOOT(APCallCode.CALL_ERROR_INNER, "离线导航引擎已经下载完成，需要重启后才可生效，若不重启不能使用离线导航功能。"),
    CODE_NATIVE_TBT_SUCCESS_OFFLINE_PREFERRED(-114, "您选择了离线优先，已优先为您规划离线路线"),
    CODE_NATIVE_TBT_NAVI_OFFLINE(-120, "网络不畅，自动转为离线导航。"),
    CODE_NATIVE_TBT_NAVI_OFFLINE_AVOIDJAM(-121, "网络不畅，已为您切换至离线导航。");
    
    private int nCode;
    private String strCodeMsg;

    private OfflineMsgCode(int i, String str) {
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
