package com.alipay.streammedia.mmengine;

public class MMNativeException extends Exception {
    private static final long serialVersionUID = 3732833696361901287L;
    private int code;
    private String name;

    public enum NativeExceptionCode {
        GIF_STOP(100, "gif play stop"),
        IO_ERROR(-1, "open/write file error!"),
        ENCODE_ERROR(-2, "encode jpg error!"),
        DECODE_ERROR(-3, "decode jpg error!"),
        PIXFMT_ERROR(-4, "not support pixfmt!"),
        YUV422_ERROR(-5, "pixfmt 422"),
        SCALE_ERROR(-6, "scale error"),
        MALLOC_ERROR(-10, "memory malloc error!"),
        PIXELS_ERROR(-100, "invalid width specified error!"),
        STATE_ERROR(-101, "invalid state"),
        PARAM_ERROR(-102, "parameter error"),
        ONLY_ONE_FRAME_IN_GIF(-103, "only one frame in gif"),
        RUNTIME_ERROR(-1000, "runtime error"),
        UNKNOWN_ERROR(-500, "unknown error");
        
        /* access modifiers changed from: private */
        public int index;
        /* access modifiers changed from: private */
        public String name;

        private NativeExceptionCode(int index2, String name2) {
            this.index = index2;
            this.name = name2;
        }

        public final int getIndex() {
            return this.index;
        }

        public final void setIndex(int index2) {
            this.index = index2;
        }

        public final String getName() {
            return this.name;
        }

        public final void setName(String name2) {
            this.name = name2;
        }

        public static String getNameByIndex(int index2) {
            NativeExceptionCode[] values;
            for (NativeExceptionCode t : values()) {
                if (t.getIndex() == index2) {
                    return t.getName();
                }
            }
            return "Unknown Error";
        }
    }

    public MMNativeException(int code2) {
        super("code: " + code2 + ", msg: " + NativeExceptionCode.getNameByIndex(code2));
        this.code = code2;
        this.name = NativeExceptionCode.getNameByIndex(code2);
    }

    public MMNativeException(NativeExceptionCode code2) {
        super(code2 == null ? "empty code" : "code: " + code2.index + ", msg: " + code2.name);
        if (code2 != null) {
            this.code = code2.getIndex();
            this.name = code2.getName();
        }
    }

    public MMNativeException(int code2, Throwable parent) {
        super("code: " + code2 + ", msg: " + NativeExceptionCode.getNameByIndex(code2), parent);
        this.code = code2;
        this.name = NativeExceptionCode.getNameByIndex(code2);
    }

    public MMNativeException(NativeExceptionCode code2, Throwable parent) {
        super(code2 == null ? "empty code" : "code: " + code2.index + ", msg: " + code2.name, parent);
        if (code2 != null) {
            this.code = code2.getIndex();
            this.name = code2.getName();
        }
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }
}
