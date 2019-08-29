package com.alipay.mobile.framework.service.common.threadpool;

import android.util.Log;
import android.util.Pair;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.framework.service.common.threadpool.TaskPoolRunnable.TaskType;
import java.util.HashMap;
import java.util.Map;

public class TaskPoolDiagnose {
    private static int a = 1;

    public TaskPoolDiagnose() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void shouldNotBeInvoked(TaskType taskType, String className, String methodName) {
        String stackTrace = LoggingUtil.stackTraceToString(taskType + ": developer should not invoke this. ");
        LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", stackTrace);
        if (a <= 5) {
            Map params = new HashMap();
            params.put("taskType", String.valueOf(taskType));
            params.put("className", className);
            params.put("methodName", methodName);
            params.put("stackTrace", stackTrace);
            LoggerFactory.getMonitorLogger().footprint("TaskScheduleService", "shouldNotBeInvoked", null, null, null, params);
            a++;
        }
    }

    public static void taskWasDiscard(TaskType type, Runnable runnable) {
        String runnableName;
        if (type != null && a <= 5) {
            Map params = new HashMap();
            params.put("taskType", String.valueOf(type));
            if (runnable != null) {
                if (runnable instanceof TaskPoolRunnable) {
                    runnableName = ((TaskPoolRunnable) runnable).getTaskName();
                } else {
                    runnableName = runnable.getClass().getName();
                }
                params.put("runnable", runnableName);
            }
            LoggerFactory.getMonitorLogger().footprint("TaskScheduleService", "discardTask", null, null, null, params);
            a++;
        }
    }

    public static void waitOrSpendLongTime(boolean isSpend, TaskType taskType, String className, String methodName, long waitTime, long spendTime) {
        if (a <= 5) {
            Map params = new HashMap();
            params.put("taskType", String.valueOf(taskType));
            params.put("className", className);
            params.put("methodName", methodName);
            params.put("waitTime", String.valueOf(waitTime));
            params.put("spendTime", String.valueOf(spendTime));
            LoggerFactory.getMonitorLogger().footprint("TaskScheduleService", isSpend ? "spendLongTime" : "waitLongTime", null, null, null, params);
            a++;
        }
    }

    public static void invokerThrowsException(TaskType taskType, String className, String methodName, Throwable tr) {
        String stackTrace = taskType + ": developer invoked throws exception. " + LoggingUtil.throwableToString(tr);
        LoggerFactory.getTraceLogger().error((String) "TaskScheduleService", stackTrace);
        if (a <= 5) {
            Map params = new HashMap();
            params.put("taskType", String.valueOf(taskType));
            params.put("className", className);
            params.put("methodName", methodName);
            params.put("stackTrace", stackTrace);
            LoggerFactory.getMonitorLogger().footprint("TaskScheduleService", "invokerThrowsException", null, null, null, params);
            a++;
        }
    }

    public static boolean isShutdownCheckInvoker(Pair<String, String> backTrack) {
        if (backTrack != null && "finalize".equals(backTrack.second) && "java.util.concurrent.ThreadPoolExecutor".equals(backTrack.first)) {
            return false;
        }
        return true;
    }
}
