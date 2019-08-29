package com.autonavi.common.tool.thirdparty.df;

import com.autonavi.common.tool.CrashLogUtil;

public final class AmapDf {
    public static final int CODE_BLOCK_FULL = 1;
    public static final int CODE_BLOCK_ZERO = 9;
    public static final int CODE_INODES_FULL = 2;
    public static final int CODE_MOUNT_POINT_ERROR = 5;
    public static final int CODE_PARAM_INVALID = 4;
    public static final int CODE_PERMISION_DENY = 3;
    public static final int CODE_ROOTFS_NOTSUPPORT = 7;
    public static final int CODE_STATFS_ERROR = 6;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_UNKNOW_ERROR = 8;
    public static final int OPT_INNODE = 2;
    public static final int OPT_KBLOCK = 1;

    public final native int checkDf(String str, int i);

    public final native AmapUInfo getUInfo(String str, int i);

    static {
        try {
            System.loadLibrary(CrashLogUtil.DUMPCRASH_VERSION);
        } catch (Throwable unused) {
        }
    }
}
