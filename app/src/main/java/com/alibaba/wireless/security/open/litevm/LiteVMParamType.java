package com.alibaba.wireless.security.open.litevm;

public class LiteVMParamType {

    public enum PARAM_TYPE {
        PARAM_TYPE_INT(1),
        PARAM_TYPE_UINT(2),
        PARAM_TYPE_LONG(3),
        PARAM_TYPE_ULONG(4),
        PARAM_TYPE_LONGLONG(5),
        PARAM_TYPE_ULONGLONG(6),
        PARAM_TYPE_STRING(7),
        PARAM_TYPE_DATA(8);
        
        private int mValue;

        private PARAM_TYPE(int i) {
            this.mValue = i;
        }

        public final int getValue() {
            return this.mValue;
        }
    }
}
