package com.alipay.mobile.mrtc.api;

public class APRoomInfo {
    private String rToken;
    private String roomId;

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId2) {
        this.roomId = roomId2;
    }

    public String getToken() {
        return this.rToken;
    }

    public void setToken(String rToken2) {
        this.rToken = rToken2;
    }

    public String toString() {
        return "APRoomInfo{roomId='" + this.roomId + '\'' + ", rToken='" + this.rToken + '\'' + '}';
    }
}
