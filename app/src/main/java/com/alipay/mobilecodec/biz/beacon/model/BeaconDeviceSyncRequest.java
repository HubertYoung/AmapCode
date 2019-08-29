package com.alipay.mobilecodec.biz.beacon.model;

import java.io.Serializable;
import java.util.List;

public class BeaconDeviceSyncRequest implements Serializable {
    public List<String> beacons;
    public String platform;
    public String productVersion;
    public String utdid;
}
