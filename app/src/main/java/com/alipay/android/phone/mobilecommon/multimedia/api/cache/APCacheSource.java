package com.alipay.android.phone.mobilecommon.multimedia.api.cache;

public class APCacheSource {
    public static final int TYPE_FILE = 0;
    public static final int TYPE_IMAGE = 1;
    public String businessId = "mm-other";
    public byte[] rawData;
    public String savePath;
    public int type;

    public String toString() {
        return "APCacheSource{rawData=" + this.rawData + "rawData.length=" + (this.rawData == null ? 0 : this.rawData.length) + ", type=" + this.type + ", savePath=" + this.savePath + ", businessId='" + this.businessId + '\'' + '}';
    }
}
