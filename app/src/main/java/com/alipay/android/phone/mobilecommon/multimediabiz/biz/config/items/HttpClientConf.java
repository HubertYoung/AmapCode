package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class HttpClientConf {
    @JSONField(name = "nbNetDl")
    public int nbNetDLSwitch = 1;
    @JSONField(name = "nbnuss")
    public int nbNetUPSizeSwitch = 1;
    @JSONField(name = "nbNetUp")
    public int nbNetUPSwitch = 1;

    public String toString() {
        return "HttpClientConf{nbNetDLSwitch=" + this.nbNetDLSwitch + ", nbNetUPSwitch=" + this.nbNetUPSwitch + '}';
    }
}
