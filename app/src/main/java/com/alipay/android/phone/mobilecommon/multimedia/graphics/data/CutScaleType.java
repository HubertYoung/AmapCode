package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public enum CutScaleType {
    CENTER_CROP(0),
    KEEP_RATIO(1),
    NONE(2),
    NONE_SAFE(3),
    IN_SAMPLE_POWER_OF_2(4),
    IN_SAMPLE_INT(5),
    EXACTLY_STRETCHED(7),
    SCALE_KEEP_SMALL(8),
    SCALE_AUTO_LIMIT(9),
    AUTO_CUT_EXACTLY(10),
    REGION_CROP_LEFT_TOP(11),
    REGION_CROP_CENTER_TOP(12),
    REGION_CROP_RIGHT_TOP(13),
    REGION_CROP_LEFT_CENTER(14),
    REGION_CROP_CENTER_CENTER(15),
    REGION_CROP_RIGHT_CENTER(16),
    REGION_CROP_LEFT_BOTTOM(17),
    REGION_CROP_CENTER_BOTTOM(18),
    REGION_CROP_RIGHT_BOTTOM(19),
    SMART_CROP(20);
    
    private int value;

    private CutScaleType(int value2) {
        this.value = value2;
    }

    public final int getValue() {
        return this.value;
    }

    public final boolean isRegionCrop() {
        return (this.value >= REGION_CROP_LEFT_TOP.getValue() && this.value <= REGION_CROP_RIGHT_BOTTOM.getValue()) || this.value == REGION_CROP_CENTER_CENTER.value;
    }

    public final boolean isSmartCrop() {
        return equals(SMART_CROP);
    }

    public final String toString() {
        return String.valueOf(this.value);
    }
}
