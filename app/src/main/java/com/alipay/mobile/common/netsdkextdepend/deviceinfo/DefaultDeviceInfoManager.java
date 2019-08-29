package com.alipay.mobile.common.netsdkextdepend.deviceinfo;

import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.netsdkextdepend.selfutil.EnvUtil;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoManagerAdapter;

public class DefaultDeviceInfoManager extends DeviceInfoManagerAdapter {
    public String getDeviceId() {
        return DeviceInfo.createInstance(EnvUtil.getContext()).getmDid();
    }

    public String getClientId() {
        return DeviceInfo.createInstance(EnvUtil.getContext()).getClientId();
    }

    public String getLatitude() {
        return DeviceInfo.createInstance(EnvUtil.getContext()).getLatitude();
    }

    public String getLongitude() {
        return DeviceInfo.createInstance(EnvUtil.getContext()).getLongitude();
    }
}
