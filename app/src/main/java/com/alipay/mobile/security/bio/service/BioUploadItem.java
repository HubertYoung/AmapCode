package com.alipay.mobile.security.bio.service;

public class BioUploadItem {
    public String bisToken;
    public byte[] content;
    public byte[] contentSig;
    public boolean isNeedSendResponse = false;
    public byte[] log;
    public String publicKey;

    public String toString() {
        return "BioUploadItem{, bisToken='" + this.bisToken + '\'' + ", isNeedSendResponse=" + this.isNeedSendResponse + '}';
    }
}
