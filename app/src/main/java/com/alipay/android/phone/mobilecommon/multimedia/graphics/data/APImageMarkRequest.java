package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APImageMarkRequest {
    public static final int PERCENT_MAX = 100;
    public static final int PERCENT_MIN = 1;
    public static final int POS_CENTER_BOTTOM = 8;
    public static final int POS_CENTER_CENTER = 5;
    public static final int POS_CENTER_TOP = 2;
    public static final int POS_LEFT_BOTTOM = 7;
    public static final int POS_LEFT_CENTER = 4;
    public static final int POS_LEFT_TOP = 1;
    public static final int POS_RIGHT_BOTTOM = 9;
    public static final int POS_RIGHT_CENTER = 6;
    public static final int POS_RIGHT_TOP = 3;
    public static final int TRANSPARENCY_MAX = 100;
    public static final int TRANSPARENCY_MIN = 1;
    private Integer markHeight;
    private String markId;
    private Integer markWidth;
    private Integer paddingX;
    private Integer paddingY;
    private Integer percent;
    private Integer position;
    private Integer transparency;

    public static class Builder {
        /* access modifiers changed from: private */
        public Integer markHeight;
        /* access modifiers changed from: private */
        public String markId;
        /* access modifiers changed from: private */
        public Integer markWidth;
        /* access modifiers changed from: private */
        public Integer paddingX;
        /* access modifiers changed from: private */
        public Integer paddingY;
        /* access modifiers changed from: private */
        public Integer percent;
        /* access modifiers changed from: private */
        public Integer position;
        /* access modifiers changed from: private */
        public Integer transparency;

        public Builder markId(String markId2) {
            this.markId = markId2;
            return this;
        }

        public Builder position(Integer position2) {
            this.position = position2;
            return this;
        }

        public Builder transparency(Integer transparency2) {
            this.transparency = transparency2;
            return this;
        }

        public Builder markWidth(Integer markWidth2) {
            this.markWidth = markWidth2;
            return this;
        }

        public Builder markHeight(Integer markHeight2) {
            this.markHeight = markHeight2;
            return this;
        }

        public Builder paddingX(Integer paddingX2) {
            this.paddingX = paddingX2;
            return this;
        }

        public Builder paddingY(Integer paddingY2) {
            this.paddingY = paddingY2;
            return this;
        }

        public Builder percent(Integer percent2) {
            this.percent = percent2;
            return this;
        }

        public APImageMarkRequest build() {
            return new APImageMarkRequest(this);
        }
    }

    public String getMarkId() {
        return this.markId;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Integer getTransparency() {
        return this.transparency;
    }

    public Integer getMarkWidth() {
        return this.markWidth;
    }

    public Integer getMarkHeight() {
        return this.markHeight;
    }

    public Integer getPaddingX() {
        return this.paddingX;
    }

    public Integer getPaddingY() {
        return this.paddingY;
    }

    public Integer getPercent() {
        return this.percent;
    }

    public APImageMarkRequest(Builder builder) {
        this.markId = builder.markId;
        this.position = builder.position;
        this.transparency = builder.transparency;
        this.markWidth = builder.markWidth;
        this.markHeight = builder.markHeight;
        this.paddingX = builder.paddingX;
        this.paddingY = builder.paddingY;
        this.percent = builder.percent;
    }

    public String toString() {
        return "APImageMarkRequest{markId='" + this.markId + '\'' + "position='" + this.position + '\'' + "transparency='" + this.transparency + '\'' + "markWidth='" + this.markWidth + '\'' + "markHeight='" + this.markHeight + '\'' + "paddingX='" + this.paddingX + '\'' + "paddingY='" + this.paddingY + '\'' + "percent='" + this.percent + '\'' + '}';
    }
}
