package com.alipay.mobile.map.model;

public class WifiItemInfo implements Comparable<WifiItemInfo> {
    private Integer level;
    private String mac;
    private String ssid;

    public String getMac() {
        return this.mac;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer num) {
        this.level = num;
    }

    public int compareTo(WifiItemInfo wifiItemInfo) {
        return wifiItemInfo.getLevel().compareTo(getLevel());
    }
}
