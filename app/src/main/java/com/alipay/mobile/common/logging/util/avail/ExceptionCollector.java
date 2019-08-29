package com.alipay.mobile.common.logging.util.avail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExceptionCollector {
    private static ExceptionCollector a;
    private List<Long> b;
    private List<ExceptionData> c;
    private Context d;

    public static ExceptionCollector getInstance(Context context) {
        if (a == null) {
            synchronized (ExceptionCollector.class) {
                try {
                    if (a == null) {
                        a = new ExceptionCollector(context);
                    }
                }
            }
        }
        return a;
    }

    private ExceptionCollector(Context context) {
        if (context != null) {
            this.d = context.getApplicationContext();
        }
        if (this.d == null) {
            this.d = context;
        }
    }

    public synchronized void recordNewLaunchTime(long launchTime) {
        try {
            LoggerFactory.getTraceLogger().info("ExceptionCollector", "recordNewLaunchTime: " + launchTime);
            List newLaunchTimes = new ArrayList();
            newLaunchTimes.addAll(getLaunchTimes());
            newLaunchTimes.add(Long.valueOf(launchTime));
            while (newLaunchTimes.size() > 3) {
                newLaunchTimes.remove(0);
            }
            b(newLaunchTimes);
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "ExceptionCollector", tr);
        }
        return;
    }

    public synchronized void recordException(String exceptionType) {
        try {
            LoggerFactory.getTraceLogger().info("ExceptionCollector", "recordException: " + exceptionType);
            if (ExceptionData.isValidExceptionType(exceptionType)) {
                List launchTimes = getLaunchTimes();
                if (launchTimes.size() > 0) {
                    recordException(exceptionType, launchTimes.get(launchTimes.size() - 1).longValue());
                }
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "ExceptionCollector", tr);
        }
        return;
    }

    public synchronized void recordException(String type, long clientLaunchTime) {
        if (clientLaunchTime > 0) {
            try {
                if (ExceptionData.isValidExceptionType(type)) {
                    if (LoggerFactory.getProcessInfo().isMainProcess() || !LoggerFactory.getProcessInfo().isMainProcessExist()) {
                        ExceptionData newExceptionData = new ExceptionData();
                        newExceptionData.setExceptionType(type);
                        newExceptionData.setClientLaunchTime(clientLaunchTime);
                        LoggerFactory.getTraceLogger().info("ExceptionCollector", "recordException: " + newExceptionData.getExceptionType() + " launchTime: " + newExceptionData.getClientLaunchTime());
                        List<ExceptionData> newExceptionList = new ArrayList<>();
                        newExceptionList.addAll(a(true));
                        newExceptionList.add(newExceptionData);
                        Collections.sort(newExceptionList);
                        List<Long> launchTimes = getLaunchTimes();
                        ArrayList arrayList = new ArrayList();
                        int sum = 0;
                        int[] counts = new int[launchTimes.size()];
                        for (int i = 0; i < launchTimes.size(); i++) {
                            for (ExceptionData exceptionData : newExceptionList) {
                                if (exceptionData.getClientLaunchTime() == launchTimes.get((launchTimes.size() - i) - 1).longValue()) {
                                    arrayList.add(exceptionData);
                                    counts[i] = counts[i] + 1;
                                }
                            }
                            if (counts[i] > 0) {
                                sum += counts[i];
                            } else {
                                int tempSum = 0;
                                for (int i2 = 0; i2 < counts.length; i2++) {
                                    tempSum += counts[i2];
                                }
                                if (tempSum > 0) {
                                    break;
                                }
                            }
                        }
                        if (sum >= 3) {
                            a((List<ExceptionData>) arrayList);
                            newExceptionList.clear();
                        }
                        ArrayList arrayList2 = new ArrayList();
                        for (ExceptionData exceptionData2 : newExceptionList) {
                            for (Long longValue : launchTimes) {
                                if (exceptionData2.getClientLaunchTime() == longValue.longValue()) {
                                    arrayList2.add(exceptionData2);
                                }
                            }
                        }
                        a(arrayList2, true);
                    } else {
                        Intent intent = new Intent();
                        intent.setClassName(this.d, LogContext.MAIN_SERVICE_CLASS_NAME);
                        intent.setPackage(this.d.getPackageName());
                        intent.setAction("ExceptionCollector_recordException");
                        Bundle extras = new Bundle();
                        extras.putString("exceptionType", type);
                        extras.putLong("clientLaunchTime", clientLaunchTime);
                        intent.putExtras(extras);
                        this.d.startService(intent);
                    }
                }
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().warn((String) "ExceptionCollector", tr);
            }
        }
        return;
    }

    public synchronized void clearException(String exceptionType) {
        boolean syncUpdate = false;
        synchronized (this) {
            try {
                LoggerFactory.getTraceLogger().info("ExceptionCollector", "clearException: " + exceptionType);
                if (ExceptionData.isValidExceptionType(exceptionType)) {
                    boolean shouldUpdate = false;
                    List exceptionList = new ArrayList();
                    exceptionList.addAll(a(false));
                    Iterator iterator = exceptionList.iterator();
                    while (iterator.hasNext()) {
                        ExceptionData exceptionData = (ExceptionData) iterator.next();
                        if (exceptionType == null || exceptionType.equals(exceptionData.getExceptionType())) {
                            iterator.remove();
                            shouldUpdate = true;
                        }
                    }
                    if (shouldUpdate) {
                        if (!ExceptionData.TYPE_START_APP_FAIL.equals(exceptionType)) {
                            syncUpdate = true;
                        }
                        a(exceptionList, syncUpdate);
                    }
                }
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().warn((String) "ExceptionCollector", tr);
            }
        }
        return;
    }

    private static void a(List<ExceptionData> exceptionList) {
        if (exceptionList.isEmpty()) {
            LoggerFactory.getTraceLogger().info("ExceptionCollector", "reportUnAvail but exceptions is empty");
            return;
        }
        LoggerFactory.getTraceLogger().info("ExceptionCollector", "reportUnAvail");
        Map extParams = new HashMap();
        JSONArray exceptionArray = new JSONArray();
        for (ExceptionData exceptionData : exceptionList) {
            exceptionArray.put(exceptionData.toJsonObject());
        }
        extParams.put("Exceptions", exceptionArray.toString());
        LoggerFactory.getMonitorLogger().mtBizReport(MTBizReportName.MTBIZ_FRAME, "CONT_UNAVAIL", "1000", extParams);
    }

    public List<Long> getLaunchTimes() {
        if (this.b == null) {
            this.b = Collections.synchronizedList(new ArrayList());
        } else if (LoggerFactory.getProcessInfo().isMainProcess()) {
            return this.b;
        }
        try {
            SharedPreferences sp = a(this.d);
            if (sp != null) {
                String launchTimes = sp.getString("launchTimes", "");
                LoggerFactory.getTraceLogger().info("ExceptionCollector", "getLaunchTimes: " + launchTimes);
                String[] launchTimeSplit = launchTimes.split(",");
                this.b.clear();
                for (String launchTime : launchTimeSplit) {
                    if (!TextUtils.isEmpty(launchTime)) {
                        this.b.add(Long.valueOf(Long.parseLong(launchTime)));
                    }
                }
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "ExceptionCollector", tr);
        }
        return this.b;
    }

    private void b(List<Long> newLaunchTimes) {
        if (this.b != newLaunchTimes) {
            this.b.clear();
            this.b.addAll(newLaunchTimes);
        }
        try {
            SharedPreferences sp = a(this.d);
            if (sp != null) {
                String launchTimes = "";
                for (int i = 0; i < this.b.size(); i++) {
                    launchTimes = launchTimes + String.valueOf(this.b.get(i));
                    if (i < this.b.size() - 1) {
                        launchTimes = launchTimes + ",";
                    }
                }
                sp.edit().putString("launchTimes", launchTimes).apply();
                LoggerFactory.getTraceLogger().info("ExceptionCollector", "updateLaunchTimes: " + launchTimes);
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "ExceptionCollector", tr);
        }
    }

    private List<ExceptionData> a(boolean force) {
        if (this.c == null) {
            this.c = Collections.synchronizedList(new ArrayList());
        } else if (!force) {
            return this.c;
        }
        try {
            SharedPreferences sp = a(this.d);
            if (sp != null) {
                String exceptionString = sp.getString("exceptions", "");
                LoggerFactory.getTraceLogger().info("ExceptionCollector", "getExceptions: " + exceptionString);
                if (!TextUtils.isEmpty(exceptionString)) {
                    this.c.clear();
                    JSONArray exceptionArray = new JSONArray(exceptionString);
                    for (int i = 0; i < exceptionArray.length(); i++) {
                        JSONObject object = exceptionArray.getJSONObject(i);
                        ExceptionData exceptionData = new ExceptionData();
                        exceptionData.parse(object);
                        this.c.add(exceptionData);
                    }
                }
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "ExceptionCollector", tr);
        }
        return this.c;
    }

    private void a(List<ExceptionData> newExceptionList, boolean sync) {
        if (this.c != newExceptionList) {
            this.c.clear();
            this.c.addAll(newExceptionList);
        }
        try {
            SharedPreferences sp = a(this.d);
            if (sp != null) {
                JSONArray exceptionArray = new JSONArray();
                for (ExceptionData exceptionData : this.c) {
                    exceptionArray.put(exceptionData.toJsonObject());
                }
                if (sync) {
                    sp.edit().putString("exceptions", exceptionArray.toString()).commit();
                } else {
                    sp.edit().putString("exceptions", exceptionArray.toString()).apply();
                }
                LoggerFactory.getTraceLogger().info("ExceptionCollector", "updateExceptions: " + exceptionArray);
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "ExceptionCollector", tr);
        }
    }

    private static SharedPreferences a(Context context) {
        try {
            if (LoggerFactory.getProcessInfo().isMainProcess()) {
                return context.getSharedPreferences("logging_avail_analysis", 0);
            }
            return context.getSharedPreferences("logging_avail_analysis", 4);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error("ExceptionCollector", "readAndParseStrategy", e);
            return null;
        }
    }
}
