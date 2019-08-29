package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;

public interface H5NebulaDebugProvider {
    void getPhoneStateInfo4Debug(H5Event h5Event, H5BridgeContext h5BridgeContext);

    void openDebugSetting();
}
