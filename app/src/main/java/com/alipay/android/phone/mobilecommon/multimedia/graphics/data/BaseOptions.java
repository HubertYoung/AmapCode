package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class BaseOptions {
    public boolean forceSystemDecode;
    public boolean ignoreGifAutoStart;
    public boolean ignoreNetTask;
    public boolean saveToDiskCache = true;

    public String toString() {
        return "BaseOptions{ignoreNetTask=" + this.ignoreNetTask + ", ignoreGifAutoStart=" + this.ignoreGifAutoStart + ", forceSystemDecode=" + this.forceSystemDecode + ", saveDiskCache=" + this.saveToDiskCache + '}';
    }
}
