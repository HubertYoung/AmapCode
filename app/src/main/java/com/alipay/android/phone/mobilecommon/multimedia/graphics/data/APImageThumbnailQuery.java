package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APImageThumbnailQuery extends APImageQuery {
    public Integer expectHeight;
    public Integer expectWidth;
    public Integer minHeight;
    public Integer minWidth;

    public APImageThumbnailQuery(String path) {
        super(path);
        this.expectWidth = null;
        this.expectHeight = null;
        this.minWidth = null;
        this.minHeight = null;
        this.queryType = 5;
    }

    public String getQueryKey() {
        if (this.minHeight == null || this.minWidth == null) {
            return super.getQueryKey();
        }
        return super.getQueryKey() + this.minWidth.intValue() + this.minHeight.intValue();
    }
}
