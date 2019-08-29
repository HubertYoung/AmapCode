package com.autonavi.miniapp.monitor.biz.logmonitor.util.upload;

public class UploadConstants {
    private static final String ACK_REPORT_MANUAL = "/loggw/report_egg_diangosis_upload_status.htm";
    private static final String ACK_REPORT_ORIGINI = "/loggw/report_diangosis_upload_status.htm";
    private static final String LOG_UPLOAD_MANUAL = "/loggw/eggExtLog.do";
    private static final String LOG_UPLOAD_ORIGINI = "/loggw/extLog.do";
    public static final String STATUS_FILE_UPLOADING = "206";
    public static final String STATUS_FILE_UPLOADING_RETRY = "207";
    public static final String STATUS_FILE_ZIPPING = "210";
    public static final String STATUS_NET_NOT_MATCH = "205";
    public static final String STATUS_PUSH_NOTIFIED = "209";
    public static final String STATUS_PUSH_RECEIVED = "208";
    public static final String STATUS_RESULT_FAILURE = "false";
    public static final String STATUS_RESULT_SUCCESS = "true";
    public static final String STATUS_TASK_BY_CONFIG = "204";
    public static final String STATUS_TASK_BY_PUSH = "203";

    public static String getReportUrl(boolean z) {
        return z ? "https://mdap.alipay.com/loggw/report_egg_diangosis_upload_status.htm" : "http://mdap.alipaylog.com/loggw/report_diangosis_upload_status.htm";
    }

    public static String getUploadFileUrl(boolean z) {
        return z ? "https://mdap.alipay.com/loggw/eggExtLog.do" : "http://mdap.alipaylog.com/loggw/extLog.do";
    }
}
