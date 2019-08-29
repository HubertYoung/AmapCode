package com.alipay.mobile.common.transportext.biz.diagnose.network;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.utils.load.LibraryLoadUtils;

public class Traceroute {
    public static final int PINGROUTE = 3;
    public static final int PINGROUTE_THRESHOLD = 4;
    public static final int PING_ONLY = 1;
    private static final String TAG = "DIAGNOSE-TRACEROUTE";
    public static final int TRACEROUTE_ONLY = 2;
    private DiagnoseStateManager diagnoseStateManager;
    private PingInf pingInf;
    private boolean running = false;
    private String time;

    public class PingInf {
        public int count = 1;
        public String domain = "www.taobao.com";
        public int threshold = 1000;
        public int timeoutNum = 6;
        public int trying = 1;
        public int type = 1;
        public int waiting = 5;
    }

    public static native void loop(int i, boolean z);

    private native int open(PingInf pingInf2);

    public Traceroute(PingInf pingInf2) {
        this.pingInf = pingInf2;
    }

    public void start() {
        this.time = NetworkDiagnoseUtil.getTime(NetworkDiagnoseUtil.FORMAT_HMS);
        if (this.pingInf == null || TextUtils.isEmpty(this.pingInf.domain)) {
            report(false, "domain info is null.");
            return;
        }
        String ip = NetworkDiagnoseUtil.dns(this.pingInf.domain);
        if (TextUtils.isEmpty(ip)) {
            report(false, this.pingInf.domain + " dns failed.");
            return;
        }
        this.pingInf.domain = ip;
        LogCatUtil.info(TAG, "the state is " + open(this.pingInf));
    }

    public void register(DiagnoseStateManager diagnoseStateManager2) {
        this.diagnoseStateManager = diagnoseStateManager2;
    }

    private void report(boolean ok, String summary) {
        String summary2;
        if (summary == null) {
            summary = "";
        }
        if (this.pingInf == null || 4 != this.pingInf.type) {
            summary2 = "traceroute:" + summary;
        } else {
            summary2 = this.time + "-" + summary;
            if (this.running) {
                notify(Configuration.IDLE);
            }
        }
        LogCatUtil.debug(TAG, "ok=" + ok + ", summary=" + summary2);
        if (this.diagnoseStateManager != null) {
            this.diagnoseStateManager.report(true, ok, false, summary2);
        }
    }

    private void notify(String info) {
        if (this.diagnoseStateManager == null || info == null) {
            StringBuilder sb = new StringBuilder("diagnoseStateManager is null or info is null. info=");
            if (info == null) {
                info = "null";
            }
            LogCatUtil.warn((String) TAG, sb.append(info).toString());
            return;
        }
        if (info.equals(Configuration.RUNNING)) {
            this.running = true;
        } else if (info.equals(Configuration.IDLE)) {
            this.running = false;
        }
        this.diagnoseStateManager.notify(info);
    }

    private void record(int level, String tag, String content) {
        if (tag == null || content == null) {
            content = "tag or content is null.";
        }
        if (level == 0) {
            LogCatUtil.debug(tag, content);
        } else if (1 == level) {
            LogCatUtil.info(tag, content);
        } else if (2 == level) {
            LogCatUtil.warn(tag, content);
        } else if (3 == level) {
            LogCatUtil.error(tag, content);
        } else if (4 == level) {
            LogCatUtil.error(tag, content);
        }
    }

    static {
        LibraryLoadUtils.loadLibrary((String) "qp-ping", false);
    }
}
