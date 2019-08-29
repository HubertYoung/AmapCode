package com.alipay.mobilecodec.biz.beacon.model;

import java.io.Serializable;
import java.util.List;

public class BeaconRes implements Serializable {
    public String memo;
    public int resultCode;
    public List<DeviceRes> resultList;
    public boolean success;
}
