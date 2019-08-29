package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class SdSpaceConf extends BaseConfig {
    @JSONField(name = "scwl")
    public String[] sdSpaceCheckWhiteList = null;

    public void updateTime() {
        super.updateTime();
    }
}
