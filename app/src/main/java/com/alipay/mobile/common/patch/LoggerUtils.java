package com.alipay.mobile.common.patch;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;

public class LoggerUtils {
    public static final String DO_PATCHER_FAIL = "doPatcher-Fail";
    public static final String DO_PATCHER_START = "doPatcher-Start";
    public static final String DO_PATCHER_SUCCESS = "doPatcher-Success";
    public static final String PATCHER_SERVICE_START = "patcherService-Start";
    public static final String VERIFY_NEW_FILE_MD5_FAIL = "verifyNewFileMD5-Fail";
    public static final String VERIFY_NEW_FILE_MD5_SUCCESS = "verifyNewFileMD5-Success";

    public LoggerUtils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void logDoPatchServiceStart(String tag) {
        Behavor behavor = new Behavor();
        behavor.setSeedID("patcher");
        behavor.setUserCaseID("UC-PATCH-1");
        behavor.setParam1("patcherService-Start-" + tag);
        LoggerFactory.getBehavorLogger().event("event", behavor);
    }

    public static void writePatchLogStart(String tag) {
        Behavor behavor = new Behavor();
        behavor.setSeedID("patcher");
        behavor.setUserCaseID("UC-PATCH-1");
        behavor.setParam1("doPatcher-Start-" + tag);
        LoggerFactory.getBehavorLogger().event("event", behavor);
    }

    public static void writePatchLogSuccess(String tag) {
        Behavor behavor = new Behavor();
        behavor.setSeedID("patcher");
        behavor.setUserCaseID("UC-PATCH-1");
        behavor.setParam1("doPatcher-Success-" + tag);
        LoggerFactory.getBehavorLogger().event("event", behavor);
    }

    public static void writePatchLogFail(String tag) {
        Behavor behavor = new Behavor();
        behavor.setSeedID("patcher");
        behavor.setUserCaseID("UC-PATCH-1");
        behavor.setParam1("doPatcher-Fail-" + tag);
        LoggerFactory.getBehavorLogger().event("event", behavor);
    }

    public static void logVerifyNewFileMD5Success(String tag) {
        Behavor behavor = new Behavor();
        behavor.setSeedID("patcher");
        behavor.setUserCaseID("UC-PATCH-1");
        behavor.setParam1("verifyNewFileMD5-Success-" + tag);
        LoggerFactory.getBehavorLogger().event("event", behavor);
    }

    public static void logVerifyNewFileMD5Fail(String tag) {
        Behavor behavor = new Behavor();
        behavor.setSeedID("patcher");
        behavor.setUserCaseID("UC-PATCH-1");
        behavor.setParam1("verifyNewFileMD5-Fail-" + tag);
        LoggerFactory.getBehavorLogger().event("event", behavor);
    }

    public static void writePatchLog(String log) {
        Behavor behavor = new Behavor();
        behavor.setSeedID("patcher");
        behavor.setUserCaseID("UC-PATCH-1");
        behavor.setParam1(log);
        LoggerFactory.getBehavorLogger().event("event", behavor);
    }
}
