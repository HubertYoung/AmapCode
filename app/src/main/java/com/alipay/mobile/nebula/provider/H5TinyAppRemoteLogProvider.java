package com.alipay.mobile.nebula.provider;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Event;

public interface H5TinyAppRemoteLogProvider {
    boolean isRemoteOutputConnected(H5Event h5Event);

    String sendStandardLogToRemoteOutput(H5Event h5Event, JSONObject jSONObject);
}
