package com.alipay.mobile.common.transportext.biz.diagnose.network;

import com.alipay.mobile.common.amnet.api.AmnetNetworkDiagnoseListener;
import com.alipay.mobile.common.transportext.amnet.Notepad;
import com.alipay.mobile.common.utils.load.LibraryLoadUtils;

class PingRoute {
    public static final int ERROR_CLOSE = 5;
    public static final int ERROR_FIELDID = 3;
    public static final int ERROR_FUN = 4;
    public static final int ERROR_INVAL = 2;
    public static final int ERROR_NOPING = 1;
    public static final int ERROR_OK = 0;
    public static final int ERROR_PINGEXE = 7;
    public static final int ERROR_PINGNOTREACH = 9;
    public static final int ERROR_PINGRTT = 10;
    public static final int ERROR_PINGTIMEOUT = 8;
    public static final int ERROR_THREAD = 6;
    static final String TAG = "DIAGNOSE-PINGROUTE-JAVA";
    public static final int WAIT_SYS = 10;
    public static int continuousTimeoutNum = 11;
    private static int curLevel = 0;
    private static Notepad notepad = null;
    private static long timeBySys = 0;
    private static long timeByUsr = 0;
    public static int waiting = 12;
    private AmnetNetworkDiagnoseListener callback = null;
    private PingInf[] pingInfs = null;
    private int pingNum = 0;
    private int resultNum = 0;
    private long timeFlag = 0;
    private int typeFlag = 0;

    public class PingInf {
        public int count = 1;
        public String domain = "www.taobao.com";
        public boolean isPingRoute = false;
        public int trying = 1;
        public int waiting = 5;
    }

    private static native String nativePingIp(String str);

    private static native String nativeTraceRouteIp(String str);

    private native int open(PingInf[] pingInfArr, int i);

    static {
        LibraryLoadUtils.loadLibrary((String) "qp-ping", false);
    }

    public PingRoute(PingInf[] pingInfs2, AmnetNetworkDiagnoseListener callback2) {
        this.pingInfs = pingInfs2;
        this.callback = callback2;
    }

    public static String diagnoseByPing(String ip) {
        if (ip == null || ip.trim().length() < 7) {
            return null;
        }
        return nativePingIp(ip);
    }

    public static String diagnoseByTraceRoute(String ip) {
        if (ip == null || ip.trim().length() < 7) {
            return null;
        }
        String result = nativeTraceRouteIp(ip);
        record(0, TAG, result);
        return result;
    }

    public static void register(Notepad logInterface, int logLevel) {
        if (logInterface != null) {
            notepad = logInterface;
            curLevel = logLevel;
        }
    }

    private static void record(int level, String tag, String content) {
        String lvl;
        if (level >= curLevel) {
            switch (level) {
                case 0:
                    lvl = "DEBUG";
                    break;
                case 1:
                    lvl = "INFO";
                    break;
                case 2:
                    lvl = Configuration.LVL_WARN;
                    break;
                case 3:
                    lvl = "ERROR";
                    break;
                default:
                    lvl = "FATAL";
                    break;
            }
            if (notepad != null) {
                notepad.print(lvl, tag, content);
            }
        }
    }

    public boolean start() {
        if (this.pingInfs == null || this.pingInfs.length <= 0) {
            record(3, TAG, "[start]pingInfs is null.");
            report(true, false, true, "[start]pingInfs is null.");
            return false;
        }
        this.pingNum = this.pingInfs.length;
        int state = open(this.pingInfs, this.typeFlag);
        record(1, TAG, "[start]timeByUsr:" + timeByUsr + ",timeBySys:" + timeBySys + ". open state:" + state);
        if (state != 0) {
            return false;
        }
        return true;
    }

    public void register(long timeFlag2, int typeFlag2) {
        this.timeFlag = timeFlag2;
        this.typeFlag = typeFlag2;
        if (1 == typeFlag2) {
            timeByUsr = timeFlag2;
        } else if (2 == typeFlag2) {
            timeBySys = timeFlag2;
        }
    }

    private void report(boolean fin, boolean ok, boolean done, String summary) {
        synchronized (this) {
            this.resultNum++;
            if (this.resultNum >= this.pingNum) {
                done = true;
            }
        }
        if (1 == this.typeFlag && this.timeFlag != timeByUsr) {
            record(2, TAG, "not the same diagnose, ignore.");
        } else if (this.callback != null) {
            if (summary != null) {
                summary = "traceroute:" + summary;
            }
            this.callback.report(fin, ok, done, summary);
            record(1, TAG, "[report]" + fin + ";" + ok + ";" + done + ";" + (summary == null ? "" : summary));
        }
    }
}
