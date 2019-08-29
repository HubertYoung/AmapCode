package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor;

public class APImageInfo {
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 180;
    public static final int ROTATION_270 = 270;
    public static final int ROTATION_90 = 90;
    public int correctHeight;
    public int correctWidth;
    public int height;
    public int orientation = 1;
    public String path = "";
    public int rotation;
    public String type = "";
    public int width;

    public APImageInfo(int width2, int height2, int orientation2) {
        this.width = width2;
        this.height = height2;
        this.orientation = orientation2;
        this.rotation = getImageRotation(orientation2);
        reviseWidthAndHeight();
    }

    private int getImageRotation(int orientation2) {
        switch (orientation2) {
            case 1:
                return 0;
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    private void reviseWidthAndHeight() {
        switch (this.orientation) {
            case 6:
            case 8:
                this.correctWidth = this.height;
                this.correctHeight = this.width;
                return;
            default:
                this.correctWidth = this.width;
                this.correctHeight = this.height;
                return;
        }
    }
}
