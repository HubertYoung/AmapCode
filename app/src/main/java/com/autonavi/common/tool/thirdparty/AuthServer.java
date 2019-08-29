package com.autonavi.common.tool.thirdparty;

import com.autonavi.common.tool.CrashLogUtil;

public class AuthServer {
    public native String[] getAuthServers();

    static {
        try {
            System.loadLibrary(CrashLogUtil.DUMPCRASH_VERSION);
        } catch (Throwable unused) {
        }
    }
}
