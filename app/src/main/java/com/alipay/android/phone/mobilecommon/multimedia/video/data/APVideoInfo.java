package com.alipay.android.phone.mobilecommon.multimedia.video.data;

public class APVideoInfo {
    public int correctHeight = 0;
    public int correctWidth = 0;
    public int duration = 0;
    public int height = 0;
    public String id = "";
    public int rotation = 0;
    public int width = 0;

    public String toString() {
        return "APVideoInfo{id='" + this.id + '\'' + ", width=" + this.width + ", height=" + this.height + ", duration=" + this.duration + ", rotation=" + this.rotation + ", correctWidth=" + this.correctWidth + ", correctHeight=" + this.correctHeight + '}';
    }
}
