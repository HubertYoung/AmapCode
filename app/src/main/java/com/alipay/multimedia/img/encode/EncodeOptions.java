package com.alipay.multimedia.img.encode;

import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.img.encode.mode.MaxLenMode;
import com.alipay.multimedia.img.encode.mode.Mode;

public class EncodeOptions {
    public static final int DEFAULT_MAX_LEN = 1280;
    public static final int QUALITY_AR = 2;
    @Deprecated
    public static final int QUALITY_HEIGHT = 1;
    public static final int QUALITY_HIGH = 1;
    public static final int QUALITY_NORMAL = 0;
    public boolean autoRotate = true;
    public Integer forceRotate;
    public Mode mode = new MaxLenMode(1280);
    public int outFormat = 0;
    public String outputFile = null;
    public int quality = 0;
    public boolean requireOutputInfo = false;

    public String toString() {
        return "EncodeOptions{mode=" + this.mode + ", quality=" + this.quality + ", outFormat=" + this.outFormat + ", outputFile='" + this.outputFile + '\'' + ", requireOutputInfo=" + this.requireOutputInfo + ", jniDebug=" + StaticOptions.jniDebug + ", autoRotate=" + this.autoRotate + ", forceRotate=" + this.forceRotate + '}';
    }
}
