package com.alipay.android.phone.mobilecommon.multimedia.graphics;

public class APTakePictureOption {
    public static final int TYPE_BITMAP = 2;
    public static final int TYPE_DATA = 1;
    private static final int TYPE_MAX = 2;
    public static final int TYPE_PATH = 0;
    private boolean bKeepPreview = false;
    private int dataType = 0;
    private int mQuality = 100;
    public boolean saveToPrivateDir = false;

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType2) {
        if (dataType2 < 0 || dataType2 > 2) {
            this.dataType = 0;
        } else {
            this.dataType = dataType2;
        }
    }

    public int getQuality() {
        return this.mQuality;
    }

    public void setQuality(int quality) {
        if (quality <= 0 || quality >= 100) {
            quality = 100;
        }
        this.mQuality = quality;
    }

    public boolean isKeepPreview() {
        return this.bKeepPreview;
    }

    public void setKeepPreview(boolean keepPreview) {
        this.bKeepPreview = keepPreview;
    }

    public String toString() {
        return "APTakePictureOption{saveToPrivateDir=" + this.saveToPrivateDir + ", mQuality=" + this.mQuality + '}';
    }
}
