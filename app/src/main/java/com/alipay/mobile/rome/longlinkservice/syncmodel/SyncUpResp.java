package com.alipay.mobile.rome.longlinkservice.syncmodel;

public class SyncUpResp<K> {
    public String biz;
    public String bizId;
    public String msgId;
    public K respData;

    public SyncUpResp() {
    }

    public SyncUpResp(SyncUpResp<K> syncUpResp) {
        this.biz = syncUpResp.biz;
        this.bizId = syncUpResp.bizId;
        this.msgId = syncUpResp.msgId;
        this.respData = syncUpResp.respData;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[biz=");
        sb.append(this.biz);
        sb.append(",bizId=");
        sb.append(this.bizId);
        sb.append(", msgData=");
        sb.append(this.msgId);
        return sb.toString();
    }
}
