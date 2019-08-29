package com.ali.user.mobile.accountbiz.extservice;

import com.ali.user.mobile.account.bean.DeviceInfoBean;
import com.ali.user.mobile.account.domain.MspDeviceInfoBean;

public interface DeviceService {
    MspDeviceInfoBean queryCertification();

    DeviceInfoBean queryDeviceInfo();
}
