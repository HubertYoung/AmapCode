package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;

public class CodeConverter {
    public static RETCODE toImageRetCode(int fileCode) {
        switch (fileCode) {
            case 0:
                return RETCODE.SUC;
            case 1:
                return RETCODE.DOWNLOAD_FAILED;
            case 2:
                return RETCODE.DOWNLOAD_FAILED;
            case 3:
                return RETCODE.INCONSISTENT_SIZE;
            case 4:
                return RETCODE.MD5_FAILED;
            case 5:
                return RETCODE.CANCEL;
            case 6:
                return RETCODE.INCONSISTENT_SIZE;
            case 7:
                return RETCODE.PARAM_ERROR;
            case 8:
                return RETCODE.REUSE;
            case 9:
                return RETCODE.INVALID_NETWORK;
            case 10:
                return RETCODE.INVALID_NETWORK;
            case 11:
                return RETCODE.FILE_NOT_EXIST;
            case 12:
                return RETCODE.SPACE_NOT_ENOUGH;
            case 2000:
                return RETCODE.CURRENT_LIMIT;
            default:
                return RETCODE.DOWNLOAD_FAILED;
        }
    }
}
