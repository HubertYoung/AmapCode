package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor;

public class APImagePlaceHolderRect {
    public int cropLeft;
    public int cropTop;
    public int dstHeight;
    public int dstWidth;
    public int retCode;
    public int srcHeight;
    public int srcWidth;

    public String toString() {
        return "APImagePlaceHolderRect{srcWidth=" + this.srcWidth + ", srcHeight=" + this.srcHeight + ", dstWidth=" + this.dstWidth + ", dstHeight=" + this.dstHeight + ", cropLeft=" + this.cropLeft + ", cropTop=" + this.cropTop + ", retCode=" + this.retCode + '}';
    }
}
