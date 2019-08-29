package com.alipay.mobile.common.patch;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class Constants {
    public static final int ERROR_CANCEL = 110;
    public static final int ERROR_DOWNLOAD = 102;
    public static final int ERROR_DO_PATCH = 104;
    public static final int ERROR_FILE_NOT_EXISTS = 106;
    public static final int ERROR_IO = 107;
    public static final int ERROR_LOAD_SO = 109;
    public static final int ERROR_PARM = 101;
    public static final int ERROR_STORAGE = 105;
    public static final int ERROR_VERIFY = 108;
    public static final int ERROR_VERIFY_MD5 = 103;

    public Constants() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
