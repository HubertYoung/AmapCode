package com.alipay.mobilecodec.biz.beacon.model;

import java.io.Serializable;

public class DeviceRes implements Serializable {
    public String UUID;
    public String memo;
    public int refreshTime;
    public int resultCode;
    public boolean success;
}
