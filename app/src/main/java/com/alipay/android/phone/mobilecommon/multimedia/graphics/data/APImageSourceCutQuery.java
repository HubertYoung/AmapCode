package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APImageSourceCutQuery extends APImageQuery {
    private Integer height;
    public Integer minHeight;
    public Integer minWidth;
    private Integer width;

    public APImageSourceCutQuery(String path) {
        super(path);
        this.width = Integer.valueOf(0);
        this.height = Integer.valueOf(0);
        this.queryType = 1;
    }

    public APImageSourceCutQuery(String path, CutScaleType type, Integer minWidth2, Integer minHeight2) {
        super(path);
        this.width = Integer.valueOf(0);
        this.height = Integer.valueOf(0);
        this.cutScaleType = type;
        this.minWidth = minWidth2;
        this.minHeight = minHeight2;
        this.queryType = 1;
    }

    public String getQueryKey() {
        if (this.minWidth == null || this.minHeight == null) {
            return super.getQueryKey();
        }
        return super.getQueryKey() + this.minWidth.intValue() + this.minHeight.intValue();
    }

    public String toString() {
        return "APImageSourceCutQuery{minWidth=" + this.minWidth + ", minHeight=" + this.minHeight + ", width=" + this.width + ", height=" + this.height + '}';
    }
}
