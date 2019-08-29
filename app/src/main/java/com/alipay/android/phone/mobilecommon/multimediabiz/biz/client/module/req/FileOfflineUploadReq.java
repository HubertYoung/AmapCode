package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

import java.util.List;

public class FileOfflineUploadReq extends AbstractHttpReq {
    public List<String> downloadHeaders;
    public String downloadUrl;
    public String md5;
    public List<String> notifyHeaders;
    public String notifyUrl;
    public long size;
    public boolean synchoronous = false;
    public String token;
    public String type;

    public String toString() {
        return "FileOfflineUploadReq{token='" + this.token + '\'' + ", downloadUrl='" + this.downloadUrl + '\'' + ", synchoronous=" + this.synchoronous + ", md5='" + this.md5 + '\'' + ", size=" + this.size + ", type='" + this.type + '\'' + ", downloadHeaders=" + this.downloadHeaders + ", notifyUrl='" + this.notifyUrl + '\'' + ", notifyHeaders=" + this.notifyHeaders + '}';
    }
}
