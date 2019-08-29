package com.squareup.leakcanary;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.squareup.leakcanary.HeapDump.Listener;
import com.squareup.leakcanary.internal.DisplayLeakActivity;
import com.squareup.leakcanary.internal.HeapAnalyzerService;
import com.squareup.leakcanary.internal.LeakCanaryInternals;

public final class LeakCanary {
    public static RefWatcher install(Application application) {
        return install(application, DisplayLeakService.class, AndroidExcludedRefs.createAppDefaults().build());
    }

    public static RefWatcher install(Application application, Class<? extends AbstractAnalysisResultService> cls, ExcludedRefs excludedRefs) {
        if (isInAnalyzerProcess(application)) {
            return RefWatcher.DISABLED;
        }
        enableDisplayLeakActivity(application);
        RefWatcher androidWatcher = androidWatcher(application, new ServiceHeapDumpListener(application, cls), excludedRefs);
        ActivityRefWatcher.installOnIcsPlus(application, androidWatcher);
        return androidWatcher;
    }

    public static RefWatcher androidWatcher(Context context, Listener listener, ExcludedRefs excludedRefs) {
        AndroidDebuggerControl androidDebuggerControl = new AndroidDebuggerControl();
        AndroidHeapDumper androidHeapDumper = new AndroidHeapDumper(context);
        androidHeapDumper.cleanup();
        RefWatcher refWatcher = new RefWatcher(new AndroidWatchExecutor(), androidDebuggerControl, GcTrigger.DEFAULT, androidHeapDumper, listener, excludedRefs);
        return refWatcher;
    }

    public static void enableDisplayLeakActivity(Context context) {
        LeakCanaryInternals.setEnabled(context, DisplayLeakActivity.class, true);
    }

    public static String leakInfo(Context context, HeapDump heapDump, AnalysisResult analysisResult, boolean z) {
        String str;
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            String str2 = packageInfo.versionName;
            int i = packageInfo.versionCode;
            StringBuilder sb = new StringBuilder("In ");
            sb.append(packageName);
            sb.append(":");
            sb.append(str2);
            sb.append(":");
            sb.append(i);
            sb.append(".\n");
            String sb2 = sb.toString();
            String str3 = "";
            if (analysisResult.leakFound) {
                if (analysisResult.excludedLeak) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append("* LEAK CAN BE IGNORED.\n");
                    sb2 = sb3.toString();
                }
                StringBuilder sb4 = new StringBuilder();
                sb4.append(sb2);
                sb4.append("* ");
                sb4.append(analysisResult.className);
                String sb5 = sb4.toString();
                if (!heapDump.referenceName.equals("")) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(sb5);
                    sb6.append(" (");
                    sb6.append(heapDump.referenceName);
                    sb6.append(")");
                    sb5 = sb6.toString();
                }
                StringBuilder sb7 = new StringBuilder();
                sb7.append(sb5);
                sb7.append(" has leaked:\n");
                sb7.append(analysisResult.leakTrace.toString());
                sb7.append("\n");
                str = sb7.toString();
                if (z) {
                    StringBuilder sb8 = new StringBuilder("\n* Details:\n");
                    sb8.append(analysisResult.leakTrace.toDetailedString());
                    str3 = sb8.toString();
                }
            } else if (analysisResult.failure != null) {
                StringBuilder sb9 = new StringBuilder();
                sb9.append(sb2);
                sb9.append("* FAILURE:\n");
                sb9.append(Log.getStackTraceString(analysisResult.failure));
                sb9.append("\n");
                str = sb9.toString();
            } else {
                StringBuilder sb10 = new StringBuilder();
                sb10.append(sb2);
                sb10.append("* NO LEAK FOUND.\n\n");
                str = sb10.toString();
            }
            StringBuilder sb11 = new StringBuilder();
            sb11.append(str);
            sb11.append("* Reference Key: ");
            sb11.append(heapDump.referenceKey);
            sb11.append("\n* Device: ");
            sb11.append(Build.MANUFACTURER);
            sb11.append(Token.SEPARATOR);
            sb11.append(Build.BRAND);
            sb11.append(Token.SEPARATOR);
            sb11.append(Build.MODEL);
            sb11.append(Token.SEPARATOR);
            sb11.append(Build.PRODUCT);
            sb11.append("\n* Android Version: ");
            sb11.append(VERSION.RELEASE);
            sb11.append(" API: ");
            sb11.append(VERSION.SDK_INT);
            sb11.append(" LeakCanary: 1.3.1\n* Durations: watch=");
            sb11.append(heapDump.watchDurationMs);
            sb11.append("ms, gc=");
            sb11.append(heapDump.gcDurationMs);
            sb11.append("ms, heap dump=");
            sb11.append(heapDump.heapDumpDurationMs);
            sb11.append("ms, analysis=");
            sb11.append(analysisResult.analysisDurationMs);
            sb11.append("ms\n");
            sb11.append(str3);
            return sb11.toString();
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isInAnalyzerProcess(Context context) {
        return LeakCanaryInternals.isInServiceProcess(context, HeapAnalyzerService.class);
    }

    private LeakCanary() {
        throw new AssertionError();
    }
}
