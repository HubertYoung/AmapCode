package com.autonavi.link.connect.model;

import java.io.Serializable;

public class DiscoverInfo implements Serializable {
    public String IP = "";
    public String appId = "";
    public String appName = "";
    public String appVersion = "";
    public String deviceName = "";
    public String httpPort = "";
    public String sdkVersion = "";

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.deviceName);
        sb.append(this.appId);
        sb.append(this.httpPort);
        sb.append(this.IP);
        return sb.toString();
    }
}
