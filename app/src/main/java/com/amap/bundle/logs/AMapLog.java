package com.amap.bundle.logs;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import com.autonavi.jni.alc.ALCManager;
import com.autonavi.jni.alc.OptRecordMan;
import com.autonavi.jni.alc.inter.IALCRecordAppender;
import com.autonavi.minimap.alc.ALCRuntimeException;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.autonavi.minimap.alc.model.ALCTriggerType;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class AMapLog {
    private static final String BELONG_AJX = "D2";
    private static final String BELONG_NATIVE = "D1";
    private static boolean DEBUG = false;
    public static final String GROUP_AJX = "T7";
    public static final String GROUP_BASEMAP = "T2";
    public static final String GROUP_COMMON = "T1";
    public static final String GROUP_DRIVE = "T3";
    public static final String GROUP_MINIAPP = "T8";
    public static final String GROUP_OFFLINE = "T6";
    public static final String GROUP_ROUTE = "T5";
    public static final String GROUP_SEARCH = "T4";
    public static final int PLAYBACK_APP_ENTER_FOREGROUND = 2;
    public static final int PLAYBACK_APP_LAUNCH = 1;
    private static final String TAG = "AMapLog";
    private static IALCRecordAppender mALCListener = null;
    private static volatile boolean mBInit = false;
    /* access modifiers changed from: private */
    public static List<b> mListeners = new ArrayList(5);
    private static int mStackTraceOffset = -1;

    public static class a implements IALCRecordAppender {
        public final int getType() {
            return 2;
        }

        public final void write(String str, int i) {
            synchronized (AMapLog.mListeners) {
                Iterator it = AMapLog.mListeners.iterator();
                while (it.hasNext()) {
                    it.next();
                }
            }
        }
    }

    public interface b {
    }

    public static void fatal(String str, String str2, String str3) {
        if (!isLogValid(str2, str3) || !wp.b(str, DEBUG)) {
            StringBuilder sb = new StringBuilder("invalid param,groupName:");
            sb.append(str);
            sb.append(",tag:");
            sb.append(str2);
            sb.append(",msg:");
            sb.append(str3);
            assertError(sb.toString());
            return;
        }
        cjy.a(ALCLogLevel.LOG_FATAL, wp.a(str, false), str, str2, getFunctionName(), getFunPosition(), str3);
    }

    public static void error(String str, String str2, String str3) {
        if (!isLogValid(str2, str3) || !wp.b(str, DEBUG)) {
            StringBuilder sb = new StringBuilder("invalid param,groupName:");
            sb.append(str);
            sb.append(",tag:");
            sb.append(str2);
            sb.append(",msg:");
            sb.append(str3);
            assertError(sb.toString());
            return;
        }
        cjy.a(ALCLogLevel.LOG_ERROR, wp.a(str, DEBUG), str, str2, getFunctionName(), getFunPosition(), str3);
    }

    public static void warning(String str, String str2, String str3) {
        if (!isLogValid(str2, str3) || !wp.b(str, DEBUG)) {
            StringBuilder sb = new StringBuilder("invalid param,groupName:");
            sb.append(str);
            sb.append(",tag:");
            sb.append(str2);
            sb.append(",msg:");
            sb.append(str3);
            assertError(sb.toString());
            return;
        }
        cjy.a(ALCLogLevel.LOG_WARN, wp.a(str, DEBUG), str, str2, getFunctionName(), getFunPosition(), str3);
    }

    public static void info(String str, String str2, String str3) {
        if (!isLogValid(str2, str3) || !wp.b(str, DEBUG)) {
            StringBuilder sb = new StringBuilder("invalid param,groupName:");
            sb.append(str);
            sb.append(",tag:");
            sb.append(str2);
            sb.append(",msg:");
            sb.append(str3);
            assertError(sb.toString());
            return;
        }
        cjy.a(ALCLogLevel.LOG_INFO, wp.a(str, DEBUG), str, str2, getFunctionName(), getFunPosition(), str3);
    }

    public static void debug(String str, String str2, String str3) {
        if (!isLogValid(str2, str3) || !wp.b(str, DEBUG)) {
            StringBuilder sb = new StringBuilder("invalid param,groupName:");
            sb.append(str);
            sb.append(",tag:");
            sb.append(str2);
            sb.append(",msg:");
            sb.append(str3);
            assertError(sb.toString());
            return;
        }
        if (DEBUG) {
            cjy.a(ALCLogLevel.LOG_DEBUG, wp.a(str, DEBUG), str, str2, getFunctionName(), getFunPosition(), str3);
        }
    }

    public static void trace(String str, String str2, String str3) {
        if (!isLogValid(str2, str3) || !wp.b(str, DEBUG)) {
            StringBuilder sb = new StringBuilder("invalid param,groupName:");
            sb.append(str);
            sb.append(",tag:");
            sb.append(str2);
            sb.append(",msg:");
            sb.append(str3);
            assertError(sb.toString());
            return;
        }
        cjy.a(ALCLogLevel.LOG_TRACING, wp.a(str, DEBUG), str, str2, getFunctionName(), getFunPosition(), str3);
    }

    public static void performance(String str, String str2, String str3) {
        if (!isLogValid(str2, str3) || !wp.b(str, DEBUG)) {
            StringBuilder sb = new StringBuilder("invalid param,groupName:");
            sb.append(str);
            sb.append(",tag:");
            sb.append(str2);
            sb.append(",msg:");
            sb.append(str3);
            assertError(sb.toString());
            return;
        }
        cjy.a(ALCLogLevel.LOG_PERFORMANCE, wp.a(str, DEBUG), str, str2, getFunctionName(), getFunPosition(), str3);
    }

    public static boolean initPlayback(String str, int i, int i2, int i3, String str2) {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("initPlayback()-path:");
            sb.append(str);
            sb.append(",fileSize:");
            sb.append(i);
            sb.append(",maxDisk:");
            sb.append(i2);
            sb.append(",valid time:");
            sb.append(i3);
            sb.append(",config:");
            sb.append(str2);
            debug("paas.logs", TAG, sb.toString());
        }
        return OptRecordMan.getInstance().init(str, i, i2, i3, str2);
    }

    public static boolean playback(long j, int i, int i2, String str) {
        return OptRecordMan.getInstance().playback(j, i, i2, str);
    }

    public static void playbackAppAction(int i) {
        if (i <= 0 || i > 2) {
            error("paas.logs", TAG, "playbackAppAction()-errorAction:".concat(String.valueOf(i)));
            return;
        }
        if (DEBUG) {
            debug("paas.logs", TAG, "playbackAppAction():".concat(String.valueOf(i)));
        }
        OptRecordMan.getInstance().appAction(i);
    }

    public static void playbackFeedback(String str) {
        if (DEBUG) {
            debug("paas.logs", TAG, "playbackFeedback():".concat(String.valueOf(str)));
        }
        OptRecordMan.getInstance().feedback(str);
    }

    public static void flushData() {
        if (mBInit) {
            if (DEBUG) {
                debug("paas.logs", TAG, "flushData()");
            }
            ALCManager.getInstance().uninit();
        }
    }

    public static void upload(ALCTriggerType aLCTriggerType) {
        if (aLCTriggerType != null && mBInit) {
            if (DEBUG) {
                StringBuilder sb = new StringBuilder("upload():");
                sb.append(aLCTriggerType.name());
                debug("paas.logs", TAG, sb.toString());
            }
            ALCManager.getInstance().upload(aLCTriggerType);
        }
    }

    public static String getALCEngineVersion() {
        return !mBInit ? "" : ALCManager.getInstance().getALCVersion();
    }

    public static void setSwitchConsole(boolean z) {
        if (mBInit) {
            ALCManager.getInstance().setSwitchRecordConsole(z);
        }
    }

    public static void setSwitchFile(boolean z) {
        if (mBInit) {
            ALCManager.getInstance().setSwitchRecordStorage(z);
        }
    }

    public static void switchGroupEnable(long j, boolean z) {
        if (mBInit) {
            ALCManager.getInstance().setCustomGroup(j, z);
        }
    }

    public static void addLogListener(b bVar) {
        if (mBInit && bVar != null) {
            synchronized (mListeners) {
                if (mListeners.size() == 0) {
                    if (mALCListener == null) {
                        mALCListener = new a();
                    }
                    ALCManager.getInstance().setRecordListener(mALCListener, true);
                }
                if (!mListeners.contains(bVar)) {
                    mListeners.add(bVar);
                }
            }
        }
    }

    public static void removeLogListener(b bVar) {
        synchronized (mListeners) {
            if (mListeners.contains(bVar)) {
                mListeners.remove(bVar);
            }
            if (mListeners.size() == 0) {
                ALCManager.getInstance().removeRecordListener(mALCListener);
            }
        }
    }

    @Deprecated
    public static void e(String str, String str2) {
        if (DEBUG) {
            isLogValid(str, str2);
        }
    }

    @Deprecated
    public static void e(String str, String str2, boolean z) {
        if (DEBUG && isLogValid(str, str2)) {
            if (z) {
                cjy.c(formatLog(str, str2));
                return;
            }
            e(str, str2);
        }
    }

    @Deprecated
    public static void w(String str, String str2) {
        if (DEBUG) {
            isLogValid(str, str2);
        }
    }

    @Deprecated
    public static void w(String str, String str2, boolean z) {
        if (DEBUG && isLogValid(str, str2) && z) {
            cjy.b(formatLog(str, str2));
        }
    }

    @Deprecated
    public static void i(String str, String str2) {
        if (DEBUG) {
            isLogValid(str, str2);
        }
    }

    @Deprecated
    public static void i(String str, String str2, boolean z) {
        if (DEBUG && isLogValid(str, str2)) {
            if (z) {
                cjy.a(ALCLogLevel.LOG_INFO, 32768, (String) "amap", (String) "", 0, formatLog(str, str2));
                return;
            }
            i(str, str2);
        }
    }

    @Deprecated
    public static void d(String str, String str2) {
        if (DEBUG) {
            isLogValid(str, str2);
        }
    }

    @Deprecated
    public static void d(String str, String str2, boolean z) {
        if (DEBUG && isLogValid(str, str2)) {
            if (z) {
                cjy.a(formatLog(str, str2));
                return;
            }
            d(str, str2);
        }
    }

    @Deprecated
    public static void logFatalNative(String str, String str2, String str3, String str4) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4) && mBInit) {
            cjy.a(ALCLogLevel.P1, str, (String) BELONG_NATIVE, str2, str3, str4);
        }
    }

    @Deprecated
    public static void logErrorNative(String str, String str2, String str3, String str4) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4) && mBInit) {
            cjy.a(ALCLogLevel.P2, str, (String) BELONG_NATIVE, str2, str3, str4);
        }
    }

    @Deprecated
    public static void logNormalNative(String str, String str2, String str3, String str4) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4) && mBInit) {
            cjy.a(ALCLogLevel.P3, str, (String) BELONG_NATIVE, str2, str3, str4);
        }
    }

    public static void appendFile(String str, String str2) {
        if (isLogValid(str, str2)) {
            ALCManager.getInstance().setCustomGroup(wp.a(str, DEBUG), true);
            cjy.a(ALCLogLevel.LOG_ERROR, wp.a(str, DEBUG), str, getFunctionName(), getFunPosition(), str2);
        }
    }

    private static boolean isLogValid(String str, String str2) {
        return mBInit && !TextUtils.isEmpty(str2);
    }

    private static String formatLog(String str, String str2) {
        StringBuilder sb = new StringBuilder(" [");
        sb.append(str);
        sb.append("] ");
        sb.append(str2);
        return sb.toString();
    }

    private static String getFunctionName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (mStackTraceOffset == -1) {
            int stackTraceOffset = getStackTraceOffset(stackTrace);
            if (stackTraceOffset == -1) {
                stackTraceOffset = 5;
            }
            mStackTraceOffset = stackTraceOffset;
        }
        if (mStackTraceOffset >= stackTrace.length) {
            return "";
        }
        String stackTraceElement = stackTrace[mStackTraceOffset].toString();
        return stackTraceElement.substring(0, stackTraceElement.lastIndexOf("("));
    }

    private static int getFunPosition() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (mStackTraceOffset == -1) {
            int stackTraceOffset = getStackTraceOffset(stackTrace);
            if (stackTraceOffset == -1) {
                stackTraceOffset = 5;
            }
            mStackTraceOffset = stackTraceOffset;
        }
        if (mStackTraceOffset >= stackTrace.length) {
            return -1;
        }
        return stackTrace[mStackTraceOffset].getLineNumber();
    }

    private static int getStackTraceOffset(StackTraceElement[] stackTraceElementArr) {
        for (int i = 3; i < stackTraceElementArr.length; i++) {
            if (!stackTraceElementArr[i].getClassName().equals(AMapLog.class.getName())) {
                return i;
            }
        }
        return -1;
    }

    private static void assertError(String str) {
        if (DEBUG && mBInit) {
            throw new InvalidParameterException(str);
        }
    }

    public static void init(@NotNull Context context, @NotNull wq wqVar) {
        if (context == null || wqVar == null) {
            StringBuilder sb = new StringBuilder("日志初始化失败-参数错误,content:");
            sb.append(context);
            sb.append(",logConfig:");
            sb.append(wqVar);
            throw new InvalidParameterException(sb.toString());
        }
        DEBUG = wqVar.a;
        try {
            System.loadLibrary("alclog");
            if (context == null) {
                throw new ALCRuntimeException("applicationContext is null");
            }
            ALCManager.getInstance().init(wqVar);
            boolean z = true;
            mBInit = true;
            ALCManager.getInstance().setExpirationTime(wqVar.h);
            if (DEBUG) {
                LongSparseArray longSparseArray = wqVar.m;
                if (longSparseArray != null && longSparseArray.size() > 0) {
                    int size = longSparseArray.size();
                    for (int i = 0; i < size; i++) {
                        switchGroupEnable(longSparseArray.keyAt(i), ((Boolean) longSparseArray.valueAt(i)).booleanValue());
                    }
                }
                ALCManager.getInstance().setSwitchRecordConsole((wqVar.l & 1) == 1);
                ALCManager instance = ALCManager.getInstance();
                if ((wqVar.l & 2) != 2) {
                    z = false;
                }
                instance.setSwitchRecordStorage(z);
            }
        } catch (Throwable th) {
            Log.getStackTraceString(th);
            if (DEBUG) {
                throw new RuntimeException(th);
            }
        }
    }
}
