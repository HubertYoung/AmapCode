package com.ut.mini.crashhandler;

import java.util.Map;

public interface IUTCrashCaughtListner {
    Map<String, String> onCrashCaught(Thread thread, Throwable th);
}
