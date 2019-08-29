package com.autonavi.minimap.drive.navi.navitts.download;

public enum NaviTtsErrorType {
    network_exception(5),
    file_io_exception(6),
    download_not_enoughspace(7),
    MD5_ERROR(13);
    
    private String mMessage;
    private int mTypeTag;

    private NaviTtsErrorType(int i) {
        this.mTypeTag = i;
    }

    public final String getMessage() {
        return this.mMessage;
    }
}
