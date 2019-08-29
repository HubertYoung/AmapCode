package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimedia.material.APFilterInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.material.Defaults;
import java.util.List;

public class VideoFiltersConf {
    public List<APFilterInfo> mFilterInfos = JSON.parseArray((String) Defaults.DEFAULT_FILTER_JSON, APFilterInfo.class);

    public String toString() {
        return "VideoFiltersConf{mFilterInfos=" + this.mFilterInfos + '}';
    }
}
