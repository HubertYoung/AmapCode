package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode;

public class APDecodeOptions extends APBaseDecodeOptions {
    public Mode mode = new MaxLenMode(Integer.valueOf(0));

    public static final class FitRectMode extends Mode {
        public final int rectHeight;
        public final int rectWidth;

        public FitRectMode(int rectWidth2, int rectHeight2) {
            super(2);
            this.rectWidth = rectWidth2;
            this.rectHeight = rectHeight2;
        }

        public final String toString() {
            return "FitRectMode{rectWidth=" + this.rectWidth + ", rectHeight=" + this.rectHeight + '}';
        }
    }

    public static final class MaxLenMode extends Mode {
        public final Integer len;

        public MaxLenMode(Integer len2) {
            super(0);
            this.len = len2;
        }

        public final String toString() {
            return "MaxLenMode{len=" + this.len + '}';
        }
    }

    public static final class MinLenMode extends Mode {
        public final Integer len;

        public MinLenMode(Integer len2) {
            super(1);
            this.len = len2;
        }

        public final String toString() {
            return "MinLenMode{len=" + this.len + '}';
        }
    }

    static class Mode {
        public static final int TYPE_FIT_RECT = 2;
        public static final int TYPE_MAX_LEN = 0;
        public static final int TYPE_MIN_LEN = 1;
        public final int type;

        public Mode(int type2) {
            this.type = type2;
        }
    }

    public String toString() {
        return "DecodeOptions{mode=" + this.mode + '}';
    }
}
