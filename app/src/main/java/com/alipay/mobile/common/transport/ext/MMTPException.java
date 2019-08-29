package com.alipay.mobile.common.transport.ext;

import android.text.TextUtils;
import java.io.IOException;

public class MMTPException extends IOException {
    public static final int MMTP_EXP_BASE = 1000;
    public static final int MMTP_EXP_DOWNGRADED = 1003;
    public static final int MMTP_EXP_GENERIC = 1002;
    public static final int MMTP_EXP_NONE = 1000;
    public static final int MMTP_EXP_TIMEOUT = 1001;
    private static String[] a = {"ssl", "tls", "cert"};
    public int errorCode;
    public String errorMesage;

    public MMTPException() {
    }

    public MMTPException(String detailMessage) {
        super(detailMessage);
    }

    public MMTPException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public MMTPException(Throwable throwable) {
        super(throwable);
    }

    public MMTPException(int errorCode2, String errorMesage2) {
        super("errorCode=[" + errorCode2 + "] errorMessage=[" + errorMesage2 + "]");
        this.errorCode = errorCode2;
        this.errorMesage = errorMesage2;
    }

    public String getErrorInfo() {
        return "errorCode=[" + this.errorCode + "] errorMessage=[" + this.errorMesage + "]";
    }

    public static final boolean isTlsError(String errorMsg) {
        if (TextUtils.isEmpty(errorMsg)) {
            return false;
        }
        String lErrMSg = errorMsg.trim().toLowerCase();
        for (String sslErrorKey : a) {
            if (lErrMSg.contains(sslErrorKey)) {
                return true;
            }
        }
        return false;
    }
}
