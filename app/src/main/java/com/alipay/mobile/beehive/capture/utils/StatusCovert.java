package com.alipay.mobile.beehive.capture.utils;

import com.alipay.android.phone.mobilecommon.multimedia.material.APDownloadStatus;
import com.alipay.mobile.beehive.capture.modle.Effect.ResLocalState;

public class StatusCovert {
    public static ResLocalState wrapDownloadState(APDownloadStatus status) {
        if (status == null) {
            return ResLocalState.NOT_EXIST;
        }
        switch (status.getStatus()) {
            case 0:
            case 1:
            case 6:
                return ResLocalState.DOWNLOADING;
            case 2:
            case 3:
                return ResLocalState.NOT_EXIST;
            case 4:
            case 5:
                return ResLocalState.EXIST;
            default:
                return ResLocalState.NOT_EXIST;
        }
    }
}
