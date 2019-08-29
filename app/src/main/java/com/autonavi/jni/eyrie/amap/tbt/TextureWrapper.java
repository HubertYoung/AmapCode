package com.autonavi.jni.eyrie.amap.tbt;

public class TextureWrapper {
    public static final int IMAGE_TYPE_BMP = 0;
    public static final int IMAGE_TYPE_JPG = 1;
    public static final int IMAGE_TYPE_PNG = 2;
    public int engineId;
    public float height;
    public boolean isNinePatch = false;
    public boolean isShouldCloseAlpha = false;
    private byte[] mData;
    private int mImgType;
    private int mSize;
    public int resId;
    public float scale;
    public int textureId;
    public int vMapId;
    public float width;
    public float xAnchor;
    public float yAnchor;

    public void setData(int i, byte[] bArr) {
        int i2;
        this.mData = bArr;
        this.mImgType = i;
        if (bArr == null) {
            i2 = 0;
        } else {
            i2 = bArr.length;
        }
        this.mSize = i2;
    }
}
