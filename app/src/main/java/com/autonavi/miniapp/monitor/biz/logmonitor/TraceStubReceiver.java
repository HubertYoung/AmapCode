package com.autonavi.miniapp.monitor.biz.logmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus;
import com.autonavi.miniapp.monitor.api.analysis.diagnose.UploadTaskStatus.Code;
import com.autonavi.miniapp.monitor.biz.apm.util.APMTimer;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.StackTracer;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.ThreadDumpHelper;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.tracing.TracingUploader;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UserDiagnostician.DiagnoseTask;

public class TraceStubReceiver extends BroadcastReceiver {
    public static final String ACTION_DUMP_SENSOR = "monitor.action.dump.sensor";
    public static final String ACTION_DUMP_STACKTRACER = "monitor.action.dump.stacktracer";
    public static final String ACTION_DUMP_TRACEVIEW = "monitor.action.dump.traceview";
    public static final String ACTION_MONITOR_POWER = "monitor.action.MONITOR_POWER";
    private static final String TAG = "TraceStubReceiver";

    public void onReceive(final Context context, Intent intent) {
        try {
            LoggerFactory.getTraceLogger().info(TAG, "onReceive: ".concat(String.valueOf(intent)));
            String action = intent.getAction();
            if (ACTION_DUMP_TRACEVIEW.equals(action)) {
                if (intent.getExtras() != null) {
                    final DiagnoseTask parseDiagnoseTaskByIntent = parseDiagnoseTaskByIntent(intent.getExtras());
                    StringBuilder sb = new StringBuilder();
                    sb.append(parseDiagnoseTaskByIntent.taskID);
                    sb.append("_");
                    sb.append(LoggerFactory.getProcessInfo().getProcessTag());
                    TracingUploader tracingUploader = new TracingUploader(context, sb.toString(), parseDiagnoseTaskByIntent);
                    tracingUploader.setUploadTaskStatus(new UploadTaskStatus() {
                        public void onSuccess(String str) {
                            UserDiagnostician.getInstance().ackResult(parseDiagnoseTaskByIntent, Code.RESULT_SUCCESS, str);
                        }

                        public void onFail(Code code, String str) {
                            UserDiagnostician.getInstance().ackResult(parseDiagnoseTaskByIntent, code, str);
                        }
                    });
                    tracingUploader.tracingAndUpload();
                    LoggerFactory.getTraceLogger().info(TAG, "traceview in wallet_process");
                }
            } else if (ACTION_DUMP_STACKTRACER.equals(action)) {
                if (intent.getExtras() != null) {
                    final DiagnoseTask parseDiagnoseTaskByIntent2 = parseDiagnoseTaskByIntent(intent.getExtras());
                    APMTimer.getInstance().post(new Runnable() {
                        public void run() {
                            StackTracer.getInstance().startStackTracer(context, parseDiagnoseTaskByIntent2, new UploadTaskStatus() {
                                public void onSuccess(String str) {
                                    UserDiagnostician.getInstance().ackResult(parseDiagnoseTaskByIntent2, Code.RESULT_SUCCESS, str);
                                }

                                public void onFail(Code code, String str) {
                                    UserDiagnostician.getInstance().ackResult(parseDiagnoseTaskByIntent2, code, str);
                                }
                            });
                        }
                    });
                }
            } else if (ACTION_MONITOR_POWER.equals(action)) {
                LoggerFactory.getTraceLogger().info(TAG, "tracethread in wallet_process");
                APMTimer.getInstance().post(new Runnable() {
                    public void run() {
                        new ThreadDumpHelper(context).logAllThreadsTraces(context, LoggerFactory.getProcessInfo().getProcessName());
                    }
                });
            } else {
                ACTION_DUMP_SENSOR.equals(action);
            }
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "onReceive", th);
        }
    }

    private DiagnoseTask parseDiagnoseTaskByIntent(Bundle bundle) {
        DiagnoseTask diagnoseTask = new DiagnoseTask();
        try {
            diagnoseTask.userID = bundle.getString(LoggingSPCache.STORAGE_USERID, diagnoseTask.userID);
            diagnoseTask.taskID = bundle.getString("taskID", diagnoseTask.taskID);
            diagnoseTask.taskType = bundle.getString("type", diagnoseTask.taskType);
            diagnoseTask.fileName = bundle.getString("fileName", diagnoseTask.fileName);
            diagnoseTask.networkCondition = bundle.getString("networkCondition", diagnoseTask.networkCondition);
            diagnoseTask.isForceUpload = bundle.getBoolean("isForceUpload", diagnoseTask.isForceUpload);
            diagnoseTask.fromTime = bundle.getLong("fromTime", diagnoseTask.fromTime);
            diagnoseTask.toTime = bundle.getLong("toTime", diagnoseTask.toTime);
            try {
                diagnoseTask.fromType = Code.valueOf(bundle.getString(H5Param.FROM_TYPE, Code.NONE.toString()));
            } catch (Throwable unused) {
            }
            try {
                diagnoseTask.traceTime = bundle.getLong(UserDiagnostician.KEY_TRACE_TIME, diagnoseTask.traceTime);
                diagnoseTask.traceSize = bundle.getInt(UserDiagnostician.KEY_TRACE_SIZE, diagnoseTask.traceSize);
                diagnoseTask.stackTracerTime = bundle.getLong(UserDiagnostician.KEY_STACK_TRACER_TIME, diagnoseTask.stackTracerTime);
                diagnoseTask.stackTracerInterval = bundle.getLong(UserDiagnostician.KEY_STACK_TRACER_INTERVAL, diagnoseTask.stackTracerInterval);
            } catch (Throwable unused2) {
            }
            try {
                diagnoseTask.retrieveFilePath = bundle.getString(UserDiagnostician.KEY_RETRIEVE_FILE_PATH, diagnoseTask.retrieveFilePath);
            } catch (Throwable unused3) {
            }
            diagnoseTask.isPositive = bundle.getBoolean("isPositive", diagnoseTask.isPositive);
        } catch (Throwable unused4) {
            LoggerFactory.getTraceLogger().error((String) TAG, "parseDiagnoseTaskByIntent: ".concat(String.valueOf(bundle)));
        }
        return diagnoseTask;
    }
}
