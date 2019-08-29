package com.alipay.mobile.common.logging;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessInfoImpl implements ProcessInfo {
    private Context a;
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    private String l = "";
    private String m = "";
    private boolean n = false;
    private boolean o = false;
    private boolean p = false;
    private boolean q = false;
    private boolean r = false;
    private Map<String, String> s = null;
    private Bundle t = null;
    private Uri u = null;

    public ProcessInfoImpl(Context context) {
        if (context != null) {
            this.a = context;
            this.b = context.getPackageName();
            this.d = a();
            this.e = this.b;
            this.f = this.b + ":push";
            this.g = this.b + ":tools";
            this.h = this.b + ":ext";
            this.n = this.e.equals(this.d);
            this.o = this.f.equals(this.d);
            this.p = this.g.equals(this.d);
            this.q = this.h.equals(this.d);
            this.r = this.d.startsWith(this.b + ":lite");
            if (this.r) {
                Log.i("mytest", "loggingi mIsLiteProcess: " + this.r);
            }
            if (this.n) {
                this.c = "main";
            } else if (this.o) {
                this.c = "push";
            } else if (this.p) {
                this.c = ProcessInfo.ALIAS_TOOLS;
            } else if (this.q) {
                this.c = ProcessInfo.ALIAS_EXT;
            } else {
                Log.e("ProcessInfo", "unknown process: " + this.d);
                if (TextUtils.isEmpty(this.d)) {
                    this.c = "unknown";
                } else {
                    this.c = this.d.replace(this.b + ":", "");
                }
            }
            this.i = this.b + "-" + this.c;
            this.j = this.b + "-main";
            this.k = this.b + "-push";
            this.l = this.b + "-tools";
            this.m = this.b + "-ext";
        }
    }

    private String a() {
        String processName = null;
        try {
            Class clazz = getClass().getClassLoader().loadClass("android.app.ActivityThread");
            Method method = clazz.getDeclaredMethod("currentActivityThread", new Class[0]);
            method.setAccessible(true);
            Object object = method.invoke(null, new Object[0]);
            Method method2 = clazz.getDeclaredMethod("getProcessName", new Class[0]);
            method2.setAccessible(true);
            processName = (String) method2.invoke(object, new Object[0]);
        } catch (Throwable t2) {
            Log.e("ProcessInfo", "getCurrentProcessName 1", t2);
        }
        if (!TextUtils.isEmpty(processName)) {
            return processName;
        }
        try {
            Method method3 = getClass().getClassLoader().loadClass("android.ddm.DdmHandleAppName").getDeclaredMethod("getAppName", new Class[0]);
            method3.setAccessible(true);
            processName = (String) method3.invoke(null, new Object[0]);
        } catch (Throwable t3) {
            Log.e("ProcessInfo", "getCurrentProcessName 2", t3);
        }
        if (!TextUtils.isEmpty(processName)) {
            return processName;
        }
        return getProcessNameById(getProcessId());
    }

    public void prepareStartupReason() {
        if (this.s == null) {
            this.s = a(Looper.getMainLooper());
            try {
                NativeCrashHandlerApi.addCrashHeadInfo("StartupReason", this.s.get(ProcessInfo.SR_TO_STRING));
                NativeCrashHandlerApi.addCrashHeadInfo("StartupAction", this.s.get(ProcessInfo.SR_ACTION_NAME));
                NativeCrashHandlerApi.addCrashHeadInfo("StartupComponent", this.s.get(ProcessInfo.SR_COMPONENT_NAME));
                if (!TextUtils.isEmpty(this.s.get(LogContext.STORAGE_APPID))) {
                    LoggerFactory.getLogContext().putContextParam(LogContext.STORAGE_APPID, this.s.get("TARGETAPPID"));
                }
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().error("ProcessInfo", "add CrashHeader StartupReason", tr);
            }
        }
    }

    private Map<String, String> a(Looper looper) {
        ConcurrentHashMap concurrentHashMap;
        try {
            Field mQueueField = Looper.class.getDeclaredField("mQueue");
            mQueueField.setAccessible(true);
            Object queue = mQueueField.get(looper);
            Field mMessagesField = MessageQueue.class.getDeclaredField("mMessages");
            mMessagesField.setAccessible(true);
            Object mMessage = mMessagesField.get(queue);
            Field objField = Message.class.getDeclaredField("obj");
            objField.setAccessible(true);
            Object obj = objField.get(mMessage);
            int checkCount = 0;
            String startRecordName = obj == null ? null : obj.getClass().getSimpleName();
            while (!a(startRecordName)) {
                checkCount++;
                Field nextField = Message.class.getDeclaredField(AudioUtils.CMDNEXT);
                nextField.setAccessible(true);
                mMessage = nextField.get(mMessage);
                if (mMessage == null || checkCount >= 5) {
                    break;
                }
                obj = objField.get(mMessage);
                startRecordName = obj == null ? null : obj.getClass().getSimpleName();
            }
            if (obj == null) {
                throw new RuntimeException("Got empty message obj, retry count:" + checkCount);
            }
            concurrentHashMap = new ConcurrentHashMap();
            concurrentHashMap.put(ProcessInfo.SR_RECORD_TYPE, String.valueOf(startRecordName));
            concurrentHashMap.put(ProcessInfo.SR_TO_STRING, obj.toString());
            if (ProcessInfo.RECORD_ACTIVITY.equals(startRecordName)) {
                Intent intent = a(obj, "intent");
                if (intent != null) {
                    this.t = intent.getExtras();
                    this.u = intent.getData();
                    String component = intent.getComponent().getClassName();
                    if (!TextUtils.isEmpty(component)) {
                        concurrentHashMap.put(ProcessInfo.SR_COMPONENT_NAME, component);
                    }
                    String action = intent.getAction();
                    if (!TextUtils.isEmpty(action)) {
                        concurrentHashMap.put(ProcessInfo.SR_ACTION_NAME, action);
                    }
                    String appId = intent.getStringExtra("TARGETAPPID");
                    if (!TextUtils.isEmpty(appId)) {
                        concurrentHashMap.put("TARGETAPPID", appId);
                    }
                } else {
                    Field activityInfoField = obj.getClass().getDeclaredField("activityInfo");
                    activityInfoField.setAccessible(true);
                    concurrentHashMap.put(ProcessInfo.SR_COMPONENT_NAME, ((ActivityInfo) activityInfoField.get(obj)).name);
                }
            } else if (ProcessInfo.RECORD_RECEIVER.equals(startRecordName)) {
                Intent intent2 = a(obj, "intent");
                if (intent2 != null) {
                    this.t = intent2.getExtras();
                    this.u = intent2.getData();
                    String component2 = intent2.getComponent().getClassName();
                    if (!TextUtils.isEmpty(component2)) {
                        concurrentHashMap.put(ProcessInfo.SR_COMPONENT_NAME, component2);
                    }
                    String action2 = intent2.getAction();
                    if (!TextUtils.isEmpty(action2)) {
                        concurrentHashMap.put(ProcessInfo.SR_ACTION_NAME, action2);
                    }
                } else {
                    Field infoField = obj.getClass().getDeclaredField("info");
                    infoField.setAccessible(true);
                    concurrentHashMap.put(ProcessInfo.SR_COMPONENT_NAME, ((ActivityInfo) infoField.get(obj)).name);
                }
            } else if (ProcessInfo.RECORD_SERVICE_CREATE.equals(startRecordName)) {
                Intent intent3 = a(obj, "intent");
                if (intent3 != null) {
                    this.t = intent3.getExtras();
                    this.u = intent3.getData();
                    String component3 = intent3.getComponent().getClassName();
                    if (!TextUtils.isEmpty(component3)) {
                        concurrentHashMap.put(ProcessInfo.SR_COMPONENT_NAME, component3);
                    }
                    String action3 = intent3.getAction();
                    if (!TextUtils.isEmpty(action3)) {
                        concurrentHashMap.put(ProcessInfo.SR_ACTION_NAME, action3);
                    }
                } else {
                    Field infoField2 = obj.getClass().getDeclaredField("info");
                    infoField2.setAccessible(true);
                    concurrentHashMap.put(ProcessInfo.SR_COMPONENT_NAME, ((ServiceInfo) infoField2.get(obj)).name);
                }
                if (this.t == null) {
                    try {
                        Field nextField2 = Message.class.getDeclaredField(AudioUtils.CMDNEXT);
                        nextField2.setAccessible(true);
                        Object nextMsg = nextField2.get(mMessage);
                        if (nextMsg != null) {
                            Object nextObj = objField.get(nextMsg);
                            if (nextObj != null) {
                                String nextName = nextObj.getClass().getSimpleName();
                                Intent nextIntent = null;
                                if (ProcessInfo.RECORD_SERVICE_BIND.equals(nextName)) {
                                    nextIntent = a(nextObj, "intent");
                                } else if (ProcessInfo.RECORD_SERVICE_ARGS.equals(nextName)) {
                                    nextIntent = a(nextObj, "args");
                                }
                                if (nextIntent != null) {
                                    this.t = nextIntent.getExtras();
                                    this.u = nextIntent.getData();
                                }
                            }
                        }
                    } catch (Throwable t2) {
                        Log.w("ProcessInfo", t2);
                    }
                }
            } else if (ProcessInfo.RECORD_SERVICE_BIND.equals(startRecordName)) {
                Intent intent4 = a(obj, "intent");
                if (intent4 != null) {
                    this.t = intent4.getExtras();
                    this.u = intent4.getData();
                    String component4 = intent4.getComponent().getClassName();
                    if (!TextUtils.isEmpty(component4)) {
                        concurrentHashMap.put(ProcessInfo.SR_COMPONENT_NAME, component4);
                    }
                    String action4 = intent4.getAction();
                    if (!TextUtils.isEmpty(action4)) {
                        concurrentHashMap.put(ProcessInfo.SR_ACTION_NAME, action4);
                    }
                }
            } else if (ProcessInfo.RECORD_SERVICE_ARGS.equals(startRecordName)) {
                Intent intent5 = a(obj, "args");
                if (intent5 != null) {
                    this.t = intent5.getExtras();
                    this.u = intent5.getData();
                    String component5 = intent5.getComponent().getClassName();
                    if (!TextUtils.isEmpty(component5)) {
                        concurrentHashMap.put(ProcessInfo.SR_COMPONENT_NAME, component5);
                    }
                    String action5 = intent5.getAction();
                    if (!TextUtils.isEmpty(action5)) {
                        concurrentHashMap.put(ProcessInfo.SR_ACTION_NAME, action5);
                    }
                }
            } else if (ProcessInfo.RECORD_BACKUP_AGENT.equals(startRecordName)) {
                Field appInfoField = obj.getClass().getDeclaredField("appInfo");
                appInfoField.setAccessible(true);
                Field backupModeField = obj.getClass().getDeclaredField("backupMode");
                backupModeField.setAccessible(true);
                concurrentHashMap.put(ProcessInfo.SR_ACTION_NAME, String.valueOf(((Integer) backupModeField.get(obj)).intValue()));
                concurrentHashMap.put(ProcessInfo.SR_COMPONENT_NAME, ((ApplicationInfo) appInfoField.get(obj)).backupAgentName);
            } else if (ProcessInfo.RECORD_PROVIDER.equals(startRecordName) || ProcessInfo.RECORD_NEW_INTENT.equals(startRecordName) || ProcessInfo.RECORD_APP_BIND.equals(startRecordName)) {
                Log.i("ProcessInfo", "Type no need.");
            } else {
                Log.i("ProcessInfo", "Type unknown.");
            }
            for (String key : concurrentHashMap.keySet()) {
                StringBuilder sb = new StringBuilder("Key=");
                Log.i("ProcessInfo", sb.append(key).append(", content=").append((String) concurrentHashMap.get(key)).toString());
            }
            return concurrentHashMap;
        } catch (Throwable tr) {
            Log.w("ProcessInfo", tr);
            return null;
        }
    }

    private static boolean a(String startRecordName) {
        return ProcessInfo.RECORD_ACTIVITY.equals(startRecordName) || ProcessInfo.RECORD_RECEIVER.equals(startRecordName) || ProcessInfo.RECORD_SERVICE_CREATE.equals(startRecordName) || ProcessInfo.RECORD_SERVICE_BIND.equals(startRecordName) || ProcessInfo.RECORD_SERVICE_ARGS.equals(startRecordName) || ProcessInfo.RECORD_BACKUP_AGENT.equals(startRecordName) || ProcessInfo.RECORD_PROVIDER.equals(startRecordName) || ProcessInfo.RECORD_NEW_INTENT.equals(startRecordName) || ProcessInfo.RECORD_APP_BIND.equals(startRecordName);
    }

    private static Intent a(Object object, String fieldName) {
        Field intentField = object.getClass().getDeclaredField(fieldName);
        intentField.setAccessible(true);
        return (Intent) intentField.get(object);
    }

    public String getPackageName() {
        return this.b;
    }

    public String getProcessAlias() {
        return this.c;
    }

    public String getMainProcessName() {
        return this.e;
    }

    public String getPushProcessName() {
        return this.f;
    }

    public String getToolsProcessName() {
        return this.g;
    }

    public String getExtProcessName() {
        return this.h;
    }

    public String getProcessName() {
        return this.d;
    }

    public String getMainProcessTag() {
        return this.j;
    }

    public String getPushProcessTag() {
        return this.k;
    }

    public String getToolsProcessTag() {
        return this.l;
    }

    public String getExtProcessTag() {
        return this.m;
    }

    public String getProcessTag() {
        return this.i;
    }

    public Set<Integer> getProcessIdsByName(String processName) {
        Set pids = new HashSet();
        if (!TextUtils.isEmpty(processName)) {
            try {
                for (RunningAppProcessInfo process : ((ActivityManager) this.a.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                    if (processName.equals(process.processName)) {
                        pids.add(Integer.valueOf(process.pid));
                    }
                }
            } catch (Throwable t2) {
                Log.e("ProcessInfo", "getProcessIdsByName: " + t2);
            }
        }
        return pids;
    }

    public int getProcessIdByName(String processName) {
        int pid = -1;
        if (TextUtils.isEmpty(processName)) {
            return -1;
        }
        try {
            Iterator<RunningAppProcessInfo> it = ((ActivityManager) this.a.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                RunningAppProcessInfo process = it.next();
                if (processName.equals(process.processName)) {
                    pid = process.pid;
                    break;
                }
            }
        } catch (Throwable t2) {
            Log.e("ProcessInfo", "getProcessIdByName: " + t2);
        }
        return pid;
    }

    public String getProcessNameById(int processId) {
        String processName = null;
        try {
            Iterator<RunningAppProcessInfo> it = ((ActivityManager) this.a.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                RunningAppProcessInfo process = it.next();
                if (process.pid == processId) {
                    processName = process.processName;
                    break;
                }
            }
        } catch (Throwable t2) {
            Log.e("ProcessInfo", "getProcessNameById: " + t2);
        }
        if (processName == null) {
            return "";
        }
        return processName;
    }

    public int getMainProcessId() {
        return this.n ? getProcessId() : getProcessIdByName(this.e);
    }

    public boolean isMainProcessExist() {
        return this.n || getProcessIdByName(this.e) > 0;
    }

    public int getPushProcessId() {
        return this.o ? getProcessId() : getProcessIdByName(this.f);
    }

    public boolean isPushProcessExist() {
        return this.o || getProcessIdByName(this.f) > 0;
    }

    public int getToolsProcessId() {
        return this.p ? getProcessId() : getProcessIdByName(this.g);
    }

    public boolean isToolsProcessExist() {
        return this.p || getProcessIdByName(this.g) > 0;
    }

    public int getExtProcessId() {
        return this.q ? getProcessId() : getProcessIdByName(this.h);
    }

    public boolean isExtProcessExist() {
        return this.q || getProcessIdByName(this.h) > 0;
    }

    public int getProcessId() {
        return Process.myPid();
    }

    public int getUserId() {
        return Process.myUid();
    }

    public int getThreadId() {
        return Process.myTid();
    }

    public boolean isMainProcess() {
        return this.n;
    }

    public boolean isPushProcess() {
        return this.o;
    }

    public boolean isToolsProcess() {
        return this.p;
    }

    public boolean isExtProcess() {
        return this.q;
    }

    public boolean isLiteProcess() {
        return this.r;
    }

    public Map<String, String> getStartupReason() {
        return this.s;
    }

    public Bundle getStartupBundle() {
        return this.t;
    }

    public Uri getStartupData() {
        return this.u;
    }
}
