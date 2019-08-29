package com.autonavi.minimap.offline.service;

public interface IDownloadApkConstant {
    public static final int DOWNLOAD_OP_BIND = 0;
    public static final int DOWNLOAD_OP_PAUSE = 2;
    public static final int DOWNLOAD_OP_REMOVE = 5;
    public static final int DOWNLOAD_OP_RESUME = 3;
    public static final int DOWNLOAD_OP_START = 1;
    public static final int DOWNLOAD_OP_STOP = 4;
    public static final int DOWNLOAD_OP_UNBIND = 6;
    public static final int DOWNLOAD_RET_COMPLETE = 3;
    public static final int DOWNLOAD_RET_CONNECTED = 1;
    public static final int DOWNLOAD_RET_DOWNLOADING = 2;
    public static final int DOWNLOAD_RET_PAUSE = 5;
    public static final int DOWNLOAD_RET_PENDING = 0;
    public static final int DOWNLOAD_RET_REMOVE = 6;
    public static final int DOWNLOAD_RET_RESUME = 4;
}
