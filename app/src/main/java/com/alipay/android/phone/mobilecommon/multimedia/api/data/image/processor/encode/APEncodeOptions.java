package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode;

public class APEncodeOptions {
    public static final int DEFAULT_MAX_LEN = 1280;
    public static final int QUALITY_AR = 2;
    @Deprecated
    public static final int QUALITY_HEIGHT = 1;
    public static final int QUALITY_HIGH = 1;
    public static final int QUALITY_NORMAL = 0;
    public static final int QUALITY_ORIGINAL = 3;
    public boolean autoRotate = true;
    public Integer forceRotate;
    public boolean jniDebug = false;
    public APMode mode = new APMaxLenMode(1280);
    public int outFormat = 0;
    public String outputFile = null;
    public int quality = 0;
    public boolean requireOutputInfo = false;

    public String toString() {
        return "APEncodeOptions{mode=" + this.mode + ", quality=" + this.quality + ", outFormat=" + this.outFormat + ", outputFile='" + this.outputFile + '\'' + ", requireOutputInfo=" + this.requireOutputInfo + ", jniDebug=" + this.jniDebug + ", autoRotate=" + this.autoRotate + ", forceRotate=" + this.forceRotate + '}';
    }
}
