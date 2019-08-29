package com.alipay.android.phone.mobilecommon.multimedia.graphics;

public class APImageUploadOption {
    public String bizSessionID;
    public String bizType;
    public String businessId;
    private int drawable_x;
    private int drawable_y;
    public String fileKey;
    public String fileKeyToken;
    public int fileType;
    private int image_x;
    private int image_y;
    private QUALITITY qua;
    public Boolean setPublic;

    public enum QUALITITY {
        ORIGINAL,
        HIGH,
        MIDDLE,
        LOW,
        DEFAULT
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey2) {
        this.fileKey = fileKey2;
    }

    public String getFileKeyToken() {
        return this.fileKeyToken;
    }

    public void setFileKeyToken(String fileKeyToken2) {
        this.fileKeyToken = fileKeyToken2;
    }

    public String getBizSessionID() {
        return this.bizSessionID;
    }

    public void setBizSessionID(String bizSessionID2) {
        this.bizSessionID = bizSessionID2;
    }

    public int getDrawable_y() {
        return this.drawable_y;
    }

    public void setDrawable_y(int drawable_y2) {
        this.drawable_y = drawable_y2;
    }

    public int getDrawable_x() {
        return this.drawable_x;
    }

    public void setDrawable_x(int drawable_x2) {
        this.drawable_x = drawable_x2;
    }

    public int getFileType() {
        return this.fileType;
    }

    public void setFileType(int fileType2) {
        this.fileType = fileType2;
    }

    public int getImage_x() {
        return this.image_x;
    }

    public void setImage_x(int image_x2) {
        this.image_x = image_x2;
    }

    public int getImage_y() {
        return this.image_y;
    }

    public void setImage_y(int image_y2) {
        this.image_y = image_y2;
    }

    public QUALITITY getQua() {
        return this.qua;
    }

    public void setQua(QUALITITY qua2) {
        this.qua = qua2;
    }

    public String toString() {
        return "APImageUploadOption{image_x=" + this.image_x + ", image_y=" + this.image_y + ", drawable_x=" + this.drawable_x + ", drawable_y=" + this.drawable_y + ", businessId='" + this.businessId + '\'' + ", bizType='" + this.bizType + '\'' + ", qua=" + this.qua + ", fileKey=" + this.fileKey + ", fileKeyToken=" + this.fileKeyToken + ", bizSessionID=" + this.bizSessionID + ", fileType=" + this.fileType + '}';
    }
}
