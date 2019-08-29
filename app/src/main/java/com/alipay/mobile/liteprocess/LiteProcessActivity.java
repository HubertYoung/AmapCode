package com.alipay.mobile.liteprocess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.perf.PerformanceLogger;
import java.util.ArrayList;
import java.util.List;

public class LiteProcessActivity extends Activity {
    public static Class[] ACTIVITY_CLASSES = {LiteProcessActivity1.class, LiteProcessActivity2.class, LiteProcessActivity3.class, LiteProcessActivity4.class, LiteProcessActivity5.class};
    private static List<String> a;
    private static boolean b = false;
    public static String fromBaseActivity = "";

    public class LiteProcessActivity1 extends LiteProcessActivity {
    }

    public class LiteProcessActivity2 extends LiteProcessActivity {
    }

    public class LiteProcessActivity3 extends LiteProcessActivity {
    }

    public class LiteProcessActivity4 extends LiteProcessActivity {
    }

    public class LiteProcessActivity5 extends LiteProcessActivity {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        PerformanceLogger.onTinyAppProcessEvent("main", "LiteProcessActivity.onCreate()");
        final Intent intent = getIntent();
        if (LoggerFactory.getProcessInfo().isMainProcess()) {
            if (a == null) {
                a = new ArrayList();
            }
            String appId = intent != null ? intent.getStringExtra("TARGETAPPID") : "";
            LoggerFactory.getTraceLogger().warn((String) Const.TAG, "LiteProcessActivity.onCreate() started in main process, aborting start process, target app id = " + appId);
            if (!a.contains(appId)) {
                a.add(appId);
                PerformanceLogger.recordAppStart(appId);
            }
            super.onCreate(savedInstanceState);
            finish();
        } else if (intent == null || intent.getAction() == null || !intent.getAction().equals(Const.RESTORE_APP) || !b) {
            b = true;
            if (intent != null) {
                try {
                    String appId2 = intent.getStringExtra("TARGETAPPID");
                    if (appId2 != null) {
                        LoggerFactory.getLogContext().putContextParam(LogContext.STORAGE_APPID, appId2);
                    }
                    Util.b(intent.getStringExtra("UID"));
                } catch (Throwable e) {
                    LoggerFactory.getTraceLogger().error(Const.TAG, "get appId when activity start failed", e);
                }
            }
            Process.setThreadPriority(-20);
            Util.setContext(getApplicationContext());
            super.onCreate(savedInstanceState);
            final String thizClassName = getClass().getName();
            LoggerFactory.getTraceLogger().debug(Const.TAG, thizClassName + " onCreate");
            finish();
            LoggerFactory.getTraceLogger().debug(Const.TAG, thizClassName + " finish");
            LiteProcessClientManager.getAsyncHandler().post(new Runnable() {
                public void run() {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, thizClassName + " run");
                    LiteProcessPipeline.litePipelineRun(LiteProcessActivity.this);
                    LiteProcessActivity.this.a(intent);
                }
            });
        } else {
            LoggerFactory.getTraceLogger().warn((String) Const.TAG, getClass().getName() + " already start, no need to restore.");
            super.onCreate(savedInstanceState);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " onResume");
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " onPause");
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " onStop");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " onDestroy");
    }

    /* access modifiers changed from: private */
    public void a(Intent intent) {
        if (intent != null) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " handleIntent " + intent.toUri(1));
            Intent intent2 = new Intent(intent);
            String action = intent2.getAction();
            if (action == null) {
                Intent intent3 = intent2;
            } else if (action.equals("START_APP")) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " startApp");
                try {
                    fromBaseActivity = intent2.getStringExtra("FROM_BASE_ACTIVITY");
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessActivity start fromBaseActivity = " + fromBaseActivity);
                } catch (Throwable t) {
                    LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                }
                LiteProcessClientManager.a(intent2);
                Intent intent4 = intent2;
            } else {
                if (action.equals(Const.RESTORE_APP)) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " restoreApp");
                    LiteProcessClientManager.a((Intent) null);
                }
                Intent intent5 = intent2;
            }
        }
    }
}
