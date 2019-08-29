package com.alipay.bis.common.service.facade.gw.zim;

public class ZimInitGwRequest {
    public String bizData;
    public String channel;
    public String merchant;
    public String metaInfo;
    public String produceNode;
    public String productName;
    public String zimId;

    public String toString() {
        return "ZimInitGwRequest{zimId='" + this.zimId + '\'' + ", channel='" + this.channel + '\'' + ", merchant='" + this.merchant + '\'' + ", productName='" + this.productName + '\'' + ", produceNode='" + this.produceNode + '\'' + ", bizData='" + this.bizData + '\'' + ", metaInfo='" + this.metaInfo + '\'' + '}';
    }
}
