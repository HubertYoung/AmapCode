package com.autonavi.minimap.ajx3.log;

public class LogManager {
    public static final String BUILD_TYPE = "release";
    public static boolean logOpen = false;
    public static boolean mInit = false;
    public static LogConfig mLogConfig;
    public static SocketStatusListener socketStatusListener;

    public static void aLog(long j, int i, int i2, long j2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
    }

    public static final void close() {
    }

    public static final void connect(String str) {
    }

    public static final void displayCmd(String str) {
    }

    public static final void init(LogConfig logConfig) {
    }

    public static void jsErrorLog(String str) {
    }

    public static void jsErrorLog(String str, String str2) {
    }

    public static void jsPrintLog(int i, String str, String str2) {
    }

    public static void jsRuntimeExceptionLog(String str, String str2) {
    }

    public static void jsRuntimeExceptionLog(String str, String str2, String str3) {
    }

    public static void lifecycleLog(String str) {
    }

    public static void lifecycleLog(String str, String str2) {
    }

    public static final void log(LogBody logBody) {
    }

    private static String makeNotNull(String str) {
        return str == null ? "" : str;
    }

    private static native void nativeClose();

    private static native void nativeConnect(String str);

    private static native void nativeInit(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    private static native void nativeLog(long j, int i, int i2, long j2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8);

    private static native void nativeSend(String str);

    public static void performaceLog(String str) {
    }

    public static void performaceLog(String str, String str2) {
    }

    public static final void send(String str) {
    }

    public static final void setLogOpen(boolean z) {
    }

    private static void socketMsgRecv(String str) {
    }

    private static void socketStatusChange(int i) {
    }

    public static final void setSocketStatusListener(SocketStatusListener socketStatusListener2) {
        socketStatusListener = socketStatusListener2;
    }
}
