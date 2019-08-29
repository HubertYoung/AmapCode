package com.alipay.android.phone.mobilesdk.socketcraft.monitor;

public class DataflowMonitorModel {
    public static final String METHOD_NAME_CLOSE = "close";
    public static final String METHOD_NAME_CONNECTION = "connect";
    public static final String METHOD_NAME_RECEIVE = "receive";
    public static final String METHOD_NAME_SEND = "send";
    public String methodName;
    public String ownerId;
    public int receiveSize = 0;
    public int sendSize = 0;
    public String url;

    public DataflowMonitorModel(String url2, String ownerId2, String methodName2, int sendSize2, int receiveSize2) {
        this.url = url2;
        this.ownerId = ownerId2;
        this.methodName = methodName2;
        this.sendSize = sendSize2;
        this.receiveSize = receiveSize2;
    }

    public String toString() {
        return "DataflowMonitorModel{url='" + this.url + '\'' + ", ownerId=" + this.ownerId + ", methodName=" + this.methodName + ", sendSize=" + this.sendSize + ", receiveSize=" + this.receiveSize + '}';
    }
}
