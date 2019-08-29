package com.alipay.streammedia.aiengine;

public class AINativeException extends Exception {
    private static final long serialVersionUID = 3732833696361901301L;
    private int code;
    private String name;

    public enum NativeExceptionCode {
        IO_ERROR(-1, "open/write file error!"),
        DECODE_ERROR(-3, "decode error!"),
        AIENGINE_CLASSIFY_ERROR(-7, "aiengine classify error!"),
        AIENGINE_INIT_ERROR(-8, "aiengine init error!"),
        MALLOC_ERROR(-10, "memory malloc error!"),
        PARAM_ERROR(-102, "parameter error"),
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

    public AINativeException(int code2) {
        this.code = code2;
        this.name = NativeExceptionCode.getNameByIndex(code2);
    }

    public AINativeException(NativeExceptionCode code2) {
        if (code2 != null) {
            this.code = code2.getIndex();
            this.name = code2.getName();
        }
    }

    public AINativeException(int code2, Throwable parent) {
        super("code: " + code2 + ", msg: " + NativeExceptionCode.getNameByIndex(code2), parent);
        this.code = code2;
        this.name = NativeExceptionCode.getNameByIndex(code2);
    }

    public AINativeException(NativeExceptionCode code2, Throwable parent) {
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
