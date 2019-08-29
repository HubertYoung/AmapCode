package com.autonavi.minimap.drive.navi.navitts.download;

import android.text.TextUtils;
import java.io.IOException;

public class NaviTtsDownloadException extends Exception {
    private NaviTtsErrorType errorType = NaviTtsErrorType.network_exception;

    public NaviTtsDownloadException(NaviTtsErrorType naviTtsErrorType) {
        this.errorType = naviTtsErrorType;
    }

    public NaviTtsDownloadException(Throwable th) {
        super(th);
        if (th instanceof IOException) {
            String message = th.getMessage();
            if (!TextUtils.isEmpty(message) && message.indexOf("ENOSPC") != -1) {
                this.errorType = NaviTtsErrorType.download_not_enoughspace;
            } else if (TextUtils.isEmpty(message) || message.indexOf("EBUSY") == -1) {
                this.errorType = NaviTtsErrorType.network_exception;
            } else {
                this.errorType = NaviTtsErrorType.file_io_exception;
            }
        } else {
            this.errorType = NaviTtsErrorType.network_exception;
        }
    }

    public NaviTtsErrorType getErrorType() {
        return this.errorType;
    }
}
