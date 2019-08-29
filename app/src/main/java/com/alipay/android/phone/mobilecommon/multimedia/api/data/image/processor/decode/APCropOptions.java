package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode;

import android.graphics.Point;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;

public class APCropOptions extends APBaseDecodeOptions {
    public static final int CROP_MODE_BOTTOM_ALIGN = 2;
    public static final int CROP_MODE_CENTER = 0;
    public static final int CROP_MODE_LEFT_ALIGN = 3;
    public static final int CROP_MODE_MANUAL = 5;
    public static final int CROP_MODE_RIGHT_ALIGN = 4;
    public static final int CROP_MODE_TOP_ALIGN = 1;
    public static final int CROP_SCALE_FIT = 0;
    public static final int CROP_SCALE_KEEP_MIN = 1;
    public boolean canUseJpgThumbnailData = true;
    public int cropMode = 0;
    public Size cutSize;
    public boolean inPerformance = true;
    public int scaleType = 0;
    public boolean smartFaceCut = false;
    public Point startPoint;
    public boolean usePreferSizeInstead = false;

    public String toString() {
        return "CropOptions{startPoint=" + this.startPoint + ", cutSize=" + this.cutSize + ", smartFaceCut=" + this.smartFaceCut + ", usePreferSizeInstead=" + this.usePreferSizeInstead + ", scaleType=" + this.scaleType + ", inPerformance=" + this.inPerformance + ", canUseJpgThumbnailData=" + this.canUseJpgThumbnailData + '}';
    }
}
