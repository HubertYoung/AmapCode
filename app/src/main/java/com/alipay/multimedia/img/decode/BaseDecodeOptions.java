package com.alipay.multimedia.img.decode;

import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.img.utils.GifUtils;

class BaseDecodeOptions {
    public static final int FORMAT_BITMAP = 0;
    public static final int FORMAT_YUV = 1;
    public boolean autoRotate = true;
    public boolean autoUseAshmem;
    public Options baseOptions;
    public boolean canUseJpgThumbnailData;
    public Integer forceRotate;
    public boolean frameCheck;
    public int frameIndex;
    public boolean inPreferQualityOverSpeed = false;
    public Config inPreferredConfig = Config.ARGB_8888;
    public int resultFormat = 0;

    BaseDecodeOptions() {
        boolean z = true;
        this.autoUseAshmem = VERSION.SDK_INT >= 21 ? false : z;
        this.frameIndex = 0;
        this.frameCheck = GifUtils.getFrameCheckSwitch();
        this.canUseJpgThumbnailData = StaticOptions.useThumbnailData;
    }
}
