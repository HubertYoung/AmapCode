package com.autonavi.minimap.ajx3.platform.ackor;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public abstract class IDeviceService {
    protected static final int E_ORIENTATION_LANDSCAPE_LEFT = 3;
    protected static final int E_ORIENTATION_LANDSCAPE_RIGHT = 4;
    protected static final int E_ORIENTATION_PORTRAIT = 1;
    protected static final int E_ORIENTATION_PORTRAIT_UPSIDE_DOWN = 2;

    @KeepClassMembers
    @Keep
    public interface IComponentMeasurement {
        float[] measure(String str, String str2);
    }

    @KeepClassMembers
    @Keep
    public interface IImgMeasurement {
        float[] measure(String str, float f, int i, float f2, int i2, int i3);
    }

    @KeepClassMembers
    @Keep
    public interface INativeFont {
        public static final int kFontStyleBold = 1;
        public static final int kFontStyleItalic = 2;
        public static final int kFontStyleNormal = 0;
        public static final int kFontStyleUnderline = 4;

        int getBaselineOfFirstLine(String str, int i, float f, boolean z, int i2);

        int getFontHeight();

        int getStringWrapHeight(String str, int i, float f, boolean z, int i2);

        int getStringWrapWidth(String str);
    }

    @KeepClassMembers
    @Keep
    public interface INativeImage {
        int height();

        int width();
    }

    @KeepClassMembers
    @Keep
    public interface ITextMeasurement {
        float getBaselineOfFirstLine(String str, int i, int i2, int i3, boolean z, float f, int i4, boolean z2, float f2, int i5, float f3, int i6, long j, int i7, String str2, int i8);

        float[] measure(String str, int i, int i2, int i3, boolean z, float f, int i4, boolean z2, float f2, int i5, float f3, int i6, long j, int i7, String str2, int i8);

        void onRelease(long j);
    }

    @KeepClassMembers
    @Keep
    public static class MeasureMode {
        public static final int MEASURE_MODE_AT_MOST = 2;
        public static final int MEASURE_MODE_EXACTLY = 1;
        public static final int MEASURE_MODE_UNDEFINED = 0;
    }

    @KeepClassMembers
    @Keep
    public static class TextOverflow {
        public static final int TEXT_OVERFLOW_CLIP = 1;
        public static final int TEXT_OVERFLOW_ELLIPSIS = 2;
        public static final int TEXT_OVERFLOW_UNDEFINED = 0;
    }

    /* access modifiers changed from: protected */
    public abstract IComponentMeasurement createComponentMeasurement();

    /* access modifiers changed from: protected */
    public abstract IImgMeasurement createImgMeasurement();

    /* access modifiers changed from: protected */
    public abstract INativeFont createNativeFont(int i, int i2, boolean z);

    /* access modifiers changed from: protected */
    public abstract INativeImage createNativeImage(String str);

    /* access modifiers changed from: protected */
    public abstract ITextMeasurement createTextMeasurement();

    /* access modifiers changed from: protected */
    public abstract void destroyNativeFont(INativeFont iNativeFont);

    /* access modifiers changed from: protected */
    public abstract void destroyNativeImage(INativeImage iNativeImage);

    /* access modifiers changed from: protected */
    public abstract float getDeviceDensisty();

    /* access modifiers changed from: protected */
    public abstract int getDeviceHeight();

    /* access modifiers changed from: protected */
    public abstract int getDeviceOrientation();

    /* access modifiers changed from: protected */
    public abstract int getDeviceWidth();

    /* access modifiers changed from: 0000 */
    public int getStringWrapHeight(String str, int i, float f, boolean z, int i2) {
        return 0;
    }
}
