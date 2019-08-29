package com.alipay.mobile.rome.longlinkservice.syncmodel;

import java.io.Serializable;

public class SyncUpMessage {
    public String biz;
    public String bizId;
    public long expireTime;
    public String msgData;
    public byte[] msgData_pb;
    public Serializable reqData;
    public long sendTime;

    public SyncUpMessage() {
    }

    public SyncUpMessage(SyncUpMessage syncUpMessage) {
        this.biz = syncUpMessage.biz;
        this.bizId = syncUpMessage.bizId;
        this.msgData = syncUpMessage.msgData;
        this.msgData_pb = syncUpMessage.msgData_pb;
        this.sendTime = syncUpMessage.sendTime;
        this.expireTime = syncUpMessage.expireTime;
        this.reqData = syncUpMessage.reqData;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[biz=");
        sb.append(this.biz);
        sb.append(",bizId=");
        sb.append(this.bizId);
        sb.append(", msgData=");
        sb.append(this.msgData);
        sb.append(", sendTime=");
        sb.append(this.sendTime);
        sb.append(", expireTime=");
        sb.append(this.expireTime);
        sb.append("]");
        return sb.toString();
    }
}
