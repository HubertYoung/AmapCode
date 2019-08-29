package com.uc.crashsdk.export;

import java.io.File;

/* compiled from: ProGuard */
public interface ICrashClient {
    public static final int EVENT_TYPE_ADD_CRASH_STATS = 3;
    public static final int EVENT_TYPE_CRASH_RESTARTING = 2;
    public static final int EVENT_TYPE_LOG_GENERATED = 1;

    boolean onAddCrashStats(String str, int i, int i2);

    File onBeforeUploadLog(File file);

    void onCrashRestarting(boolean z);

    String onGetCallbackInfo(String str);

    void onLogGenerated(File file, String str);
}
