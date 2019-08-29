package com.alipay.mobile.common.logging.impl;

import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogEvent;
import com.alipay.mobile.common.logging.api.LogEvent.Level;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.logging.api.monitor.ExceptionID;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportFilter;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportFilter.Entity;
import com.alipay.mobile.common.logging.api.monitor.MonitorLogger;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.logging.render.BatteryRender;
import com.alipay.mobile.common.logging.render.DataflowRender;
import com.alipay.mobile.common.logging.render.DiagnoseRender;
import com.alipay.mobile.common.logging.render.ExceptionRender;
import com.alipay.mobile.common.logging.render.PendingRender;
import com.alipay.mobile.common.logging.render.PerformanceRender;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.crash.CrashAnalyzer;
import java.util.HashMap;
import java.util.Map;

public class MonitorLoggerImpl implements MonitorLogger {
    private LogContext a;
    private ExceptionRender b;
    private PerformanceRender c;
    private DiagnoseRender d;
    private DataflowRender e;
    private BatteryRender f;

    public MonitorLoggerImpl(LogContext logContext) {
        this.a = logContext;
        this.b = new ExceptionRender(logContext);
        this.c = new PerformanceRender(logContext);
        this.d = new DiagnoseRender(logContext);
        this.e = new DataflowRender(logContext);
        this.f = new BatteryRender(logContext);
    }

    public void crash(Throwable ex, String extParam) {
        crash(ExceptionID.MONITORPOINT_CRASH, ex, extParam);
    }

    public void crash(ExceptionID exceptionID, Throwable ex, String extParam) {
        String ext = extParam;
        try {
            String extExInfo = StatisticalExceptionHandler.getInstance().getExternalExceptionInfo(ex);
            if (!TextUtils.isEmpty(extExInfo)) {
                ext = extExInfo;
            }
        } catch (Throwable th) {
        }
        this.a.syncAppendLogEvent(new LogEvent("crash", null, Level.ERROR, this.b.a(exceptionID, ex, ext)));
        String logcatCrashInfo = "crash: " + LoggingUtil.throwableToString(ex);
        LoggerFactory.getTraceLogger().error((String) "MonitorLogger", logcatCrashInfo);
        LoggerFactory.getLogContext().flush(true);
        LoggerFactory.getLogContext().flush("applog", true);
        LoggerFactory.getLogContext().backupCurrentFile("applog", false);
        LoggingUtil.reflectErrorLogAutomationCrash(logcatCrashInfo);
        CrashAnalyzer.analyzeJavaCrash(this.a.getApplicationContext(), LoggingUtil.throwableToString(ex));
    }

    public void exception(ExceptionID exceptionID, Throwable ex) {
        if (ex != null) {
            String ext = null;
            try {
                if (exceptionID == ExceptionID.MONITORPOINT_SUB_THREAD_CRASH || exceptionID == ExceptionID.MONITORPOINT_IGNORE_CRASH || exceptionID == ExceptionID.MONITORPOINT_INVALID_CRASH || exceptionID == ExceptionID.MONITORPOINT_CRASH) {
                    ext = StatisticalExceptionHandler.getInstance().getExternalExceptionInfo(ex);
                }
            } catch (Throwable th) {
            }
            if ("Native_Crash_In_Child_Thread:".equals(ex.getMessage())) {
                this.a.syncAppendLogEvent(new LogEvent("crash", null, Level.ERROR, this.b.a(exceptionID, ex, ext)));
            } else {
                this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_EXCEPTION, null, Level.ERROR, this.b.a(exceptionID, ex, ext)));
            }
        }
    }

    public void exception(Throwable ex, String bizType, Map<String, String> extParams) {
        this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_EXCEPTION, null, Level.ERROR, this.b.a(ExceptionID.MONITORPOINT_BIZ_EXCEPTION, ex, bizType, extParams)));
    }

    public void performance(PerformanceID performanceID, Performance performance, Map<String, String> customerParams) {
        String category = LogCategory.CATEGORY_PERFORMANCE;
        if (performanceID == PerformanceID.MONITORPOINT_NETWORK) {
            category = LogCategory.CATEGORY_NETWORK;
        } else if (performanceID == PerformanceID.MONITORPOINT_WEBAPP) {
            category = LogCategory.CATEGORY_WEBAPP;
        } else if (performanceID == PerformanceID.MONITORPOINT_H5EXCEPTION) {
            category = LogCategory.CATEGORY_H5EXCEPTION;
        } else if (performanceID == PerformanceID.MONITORPOINT_SDKMONITOR) {
            category = LogCategory.CATEGORY_SDKMONITOR;
        } else if (performanceID == PerformanceID.MONITORPOINT_SYNCLINK || performanceID == PerformanceID.MONITORPOINT_SYNCPROTO) {
            category = LogCategory.CATEGORY_ROMESYNC;
        }
        this.a.appendLogEvent(new LogEvent(category, null, new Level(performance.getLoggerLevel()), null, PendingRender.a(this.c, performanceID, performance, customerParams)));
    }

    public void performance(String performanceID, Performance performance) {
        if (TextUtils.isEmpty(performanceID)) {
            performanceID = LogCategory.CATEGORY_PERFORMANCE;
        }
        this.a.appendLogEvent(new LogEvent(performanceID, null, new Level(performance.getLoggerLevel()), null, PendingRender.a(this.c, performanceID, performance, null)));
    }

    public void performance(PerformanceID performanceID, Performance performance) {
        performance(performanceID, performance, null);
    }

    public void footprint(String tag, String msg, String param1, String param2, String param3, Map<String, String> extParams) {
        this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_FOOTPRINT, null, Level.INFO, this.c.a(PerformanceID.MONITORPOINT_FOOTPRINT, tag, msg, param1, param2 + param3, extParams)));
    }

    public void apm(String type, String subType, Throwable cause, Map<String, String> params) {
        this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_APM, null, Level.INFO, this.d.a(type, subType, cause, params)));
    }

    public void dataflow(DataflowModel dataflowModel) {
        this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_DATAFLOW, null, new Level(dataflowModel.getLoggerLevel()), this.e.a(dataflowModel)));
    }

    public void battery(BatteryModel batteryModel) {
        this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_BATTERY, null, Level.INFO, this.f.a(batteryModel)));
    }

    public void mtBizReport(String bizName, String subName, String failCode, Map<String, String> extParams) {
        String logCategory = LogCategory.CATEGORY_KEYBIZTRACE;
        try {
            Entity entity = MTBizReportFilter.getEntity();
            if (entity != null) {
                if (extParams == null) {
                    extParams = new HashMap<>();
                }
                Map result = entity.onBeforeReportForUeo(bizName, subName, failCode, extParams, null);
                if (result != null && "1".equals(result.get("flag"))) {
                    logCategory = LogCategory.CATEGORY_HIGHAVAIL;
                }
            }
        } catch (Throwable t) {
            Log.e("MonitorLogger", "", t);
        }
        LogContext logContext = this.a;
        LogEvent logEvent = new LogEvent(logCategory, null, Level.INFO, this.c.a(PerformanceID.MONITORPOINT_KEYBIZTRACE, "BizCanNotUse", bizName, subName, failCode, extParams));
        logContext.appendLogEvent(logEvent);
    }

    public void keyBizTrace(String bizName, String subName, String failMsg, Map<String, String> extParams) {
        this.a.appendLogEvent(new LogEvent(LogCategory.CATEGORY_KEYBIZTRACE, null, Level.INFO, this.c.a(PerformanceID.MONITORPOINT_KEYBIZTRACE, "KeyBiz", bizName, subName, failMsg, extParams)));
    }

    public void setUploadSize(String logCategory, int size) {
        if (!TextUtils.isEmpty(logCategory) && size >= 0) {
            this.a.putContextParam(new StringBuilder(LogContext.STORAGE_LOGCATEGORY_UPLOAD_PERFIX).append(logCategory).toString(), String.valueOf(size));
        }
    }
}
