package com.autonavi.minimap.route.net.base.resp;

import com.alibaba.fastjson.JSON;
import com.amap.bundle.aosservice.response.AosResponse;

public class BaseBeanResponse extends AosResponse<String> {
    public <T> T a(Class<T> cls) {
        return JSON.parseObject((String) super.getResult(), cls);
    }

    public /* synthetic */ Object parseResult() {
        return getResponseBodyString();
    }
}
