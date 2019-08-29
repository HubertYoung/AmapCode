package com.sina.weibo.sdk.statistic;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.MD5;
import com.sina.weibo.sdk.utils.Utility;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

class WBAgentHandler {
    private static int MAX_CACHE_SIZE = 5;
    private static CopyOnWriteArrayList<PageLog> mActivePages;
    private static WBAgentHandler mInstance;
    private static Timer mLogTimer;
    private static Map<String, PageLog> mPages;
    private static Timer mTimer;

    public static synchronized WBAgentHandler getInstance() {
        WBAgentHandler wBAgentHandler;
        synchronized (WBAgentHandler.class) {
            try {
                if (mInstance == null) {
                    mInstance = new WBAgentHandler();
                }
                wBAgentHandler = mInstance;
            }
        }
        return wBAgentHandler;
    }

    private WBAgentHandler() {
        mActivePages = new CopyOnWriteArrayList<>();
        mPages = new HashMap();
        LogUtil.i(WBAgent.TAG, "init handler");
    }

    public void onPageStart(String str) {
        if (!StatisticConfig.ACTIVITY_DURATION_OPEN) {
            PageLog pageLog = new PageLog(str);
            pageLog.setType(LogType.FRAGMENT);
            synchronized (mPages) {
                mPages.put(str, pageLog);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(", ");
            sb.append(pageLog.getStartTime() / 1000);
            LogUtil.d(WBAgent.TAG, sb.toString());
        }
    }

    public void onPageEnd(String str) {
        if (!StatisticConfig.ACTIVITY_DURATION_OPEN) {
            if (mPages.containsKey(str)) {
                PageLog pageLog = mPages.get(str);
                pageLog.setDuration(System.currentTimeMillis() - pageLog.getStartTime());
                synchronized (mActivePages) {
                    mActivePages.add(pageLog);
                }
                synchronized (mPages) {
                    mPages.remove(str);
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(", ");
                sb.append(pageLog.getStartTime() / 1000);
                sb.append(", ");
                sb.append(pageLog.getDuration() / 1000);
                LogUtil.d(WBAgent.TAG, sb.toString());
            } else {
                LogUtil.e(WBAgent.TAG, "please call onPageStart before onPageEnd");
            }
            if (mActivePages.size() >= MAX_CACHE_SIZE) {
                synchronized (mActivePages) {
                    saveActivePages(mActivePages);
                    mActivePages.clear();
                }
            }
        }
    }

    public void onResume(Context context) {
        if (LogReport.getPackageName() == null) {
            LogReport.setPackageName(context.getPackageName());
        }
        if (mTimer == null) {
            mTimer = timerTask(context, 500, StatisticConfig.getUploadInterval());
        }
        long currentTimeMillis = System.currentTimeMillis();
        String name = context.getClass().getName();
        checkNewSession(context, currentTimeMillis);
        if (StatisticConfig.ACTIVITY_DURATION_OPEN) {
            PageLog pageLog = new PageLog(name, currentTimeMillis);
            pageLog.setType(LogType.ACTIVITY);
            synchronized (mPages) {
                mPages.put(name, pageLog);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(", ");
        sb.append(currentTimeMillis / 1000);
        LogUtil.d(WBAgent.TAG, sb.toString());
    }

    public void onPause(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        String name = context.getClass().getName();
        StringBuilder sb = new StringBuilder("update last page endtime:");
        sb.append(currentTimeMillis / 1000);
        LogUtil.i(WBAgent.TAG, sb.toString());
        PageLog.updateSession(context, null, Long.valueOf(0), Long.valueOf(currentTimeMillis));
        if (StatisticConfig.ACTIVITY_DURATION_OPEN) {
            if (mPages.containsKey(name)) {
                PageLog pageLog = mPages.get(name);
                pageLog.setDuration(currentTimeMillis - pageLog.getStartTime());
                synchronized (mActivePages) {
                    mActivePages.add(pageLog);
                }
                synchronized (mPages) {
                    mPages.remove(name);
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(name);
                sb2.append(", ");
                sb2.append(pageLog.getStartTime() / 1000);
                sb2.append(", ");
                sb2.append(pageLog.getDuration() / 1000);
                LogUtil.d(WBAgent.TAG, sb2.toString());
            } else {
                LogUtil.e(WBAgent.TAG, "please call onResume before onPause");
            }
            if (mActivePages.size() >= MAX_CACHE_SIZE) {
                synchronized (mActivePages) {
                    saveActivePages(mActivePages);
                    mActivePages.clear();
                }
            }
        }
        checkAppStatus(context);
    }

    public void onEvent(String str, String str2, Map<String, String> map) {
        EventLog eventLog = new EventLog(str, str2, map);
        eventLog.setType(LogType.EVENT);
        synchronized (mActivePages) {
            mActivePages.add(eventLog);
        }
        if (map == null) {
            StringBuilder sb = new StringBuilder("event--- page:");
            sb.append(str);
            sb.append(" ,event name:");
            sb.append(str2);
            LogUtil.d(WBAgent.TAG, sb.toString());
        } else {
            StringBuilder sb2 = new StringBuilder("event--- page:");
            sb2.append(str);
            sb2.append(" ,event name:");
            sb2.append(str2);
            sb2.append(" ,extend:");
            sb2.append(map.toString());
            LogUtil.d(WBAgent.TAG, sb2.toString());
        }
        if (mActivePages.size() >= MAX_CACHE_SIZE) {
            synchronized (mActivePages) {
                saveActivePages(mActivePages);
                mActivePages.clear();
            }
        }
    }

    public void uploadAppLogs(final Context context) {
        long currentTimeMillis = System.currentTimeMillis() - LogReport.getTime(context);
        if (LogReport.getTime(context) <= 0 || currentTimeMillis >= StatisticConfig.MIN_UPLOAD_INTERVAL) {
            WBAgentExecutor.execute(new Runnable() {
                public void run() {
                    LogReport.uploadAppLogs(context, WBAgentHandler.this.getLogsInMemory());
                }
            });
            return;
        }
        timerTask(context, StatisticConfig.MIN_UPLOAD_INTERVAL - currentTimeMillis, 0);
    }

    public void onStop(Context context) {
        checkAppStatus(context);
    }

    private void checkAppStatus(Context context) {
        if (isBackground(context)) {
            synchronized (mActivePages) {
                saveActivePages(mActivePages);
                mActivePages.clear();
            }
        }
    }

    private boolean isBackground(Context context) {
        for (RunningAppProcessInfo next : ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
            if (next.processName.equals(context.getPackageName())) {
                if (next.importance == 400) {
                    StringBuilder sb = new StringBuilder("后台:");
                    sb.append(next.processName);
                    LogUtil.i(WBAgent.TAG, sb.toString());
                    return true;
                }
                StringBuilder sb2 = new StringBuilder("前台:");
                sb2.append(next.processName);
                LogUtil.i(WBAgent.TAG, sb2.toString());
                return false;
            }
        }
        return false;
    }

    public void onKillProcess() {
        LogUtil.i(WBAgent.TAG, "save applogs and close timer and shutdown thread executor");
        synchronized (mActivePages) {
            saveActivePages(mActivePages);
        }
        mInstance = null;
        closeTimer();
        WBAgentExecutor.shutDownExecutor();
    }

    private void checkNewSession(Context context, long j) {
        if (PageLog.isNewSession(context, j)) {
            PageLog pageLog = new PageLog(context);
            pageLog.setType(LogType.SESSION_END);
            PageLog pageLog2 = new PageLog(context, j);
            pageLog2.setType(LogType.SESSION_START);
            synchronized (mActivePages) {
                if (pageLog.getEndTime() > 0) {
                    mActivePages.add(pageLog);
                } else {
                    LogUtil.d(WBAgent.TAG, "is a new install");
                }
                mActivePages.add(pageLog2);
            }
            StringBuilder sb = new StringBuilder("last session--- starttime:");
            sb.append(pageLog.getStartTime());
            sb.append(" ,endtime:");
            sb.append(pageLog.getEndTime());
            LogUtil.d(WBAgent.TAG, sb.toString());
            StringBuilder sb2 = new StringBuilder("is a new session--- starttime:");
            sb2.append(pageLog2.getStartTime());
            LogUtil.d(WBAgent.TAG, sb2.toString());
            return;
        }
        LogUtil.i(WBAgent.TAG, "is not a new session");
    }

    private synchronized void saveActivePages(CopyOnWriteArrayList<PageLog> copyOnWriteArrayList) {
        final String pageLogs = LogBuilder.getPageLogs(copyOnWriteArrayList);
        WBAgentExecutor.execute(new Runnable() {
            public void run() {
                LogFileUtil.writeToFile(LogFileUtil.getAppLogPath(LogFileUtil.ANALYTICS_FILE_NAME), pageLogs, true);
            }
        });
    }

    /* access modifiers changed from: private */
    public synchronized String getLogsInMemory() {
        String str;
        String pageLogs;
        str = "";
        if (mActivePages.size() > 0) {
            synchronized (mActivePages) {
                pageLogs = LogBuilder.getPageLogs(mActivePages);
                mActivePages.clear();
            }
            str = pageLogs;
        }
        return str;
    }

    private Timer timerTask(final Context context, long j, long j2) {
        Timer timer = new Timer();
        AnonymousClass3 r1 = new TimerTask() {
            public void run() {
                LogReport.uploadAppLogs(context, WBAgentHandler.this.getLogsInMemory());
            }
        };
        if (j2 == 0) {
            timer.schedule(r1, j);
        } else {
            timer.schedule(r1, j, j2);
        }
        return timer;
    }

    private void closeTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void registerApptoAd(final Context context, final String str, Map<String, String> map) {
        try {
            final AdEventLog adEventLog = new AdEventLog();
            adEventLog.setType(LogType.APP_AD_START);
            if (isFirstStartBoolean(context)) {
                adEventLog.setmEvent_id("1");
            }
            adEventLog.setmImei(MD5.hexdigest(getImei(context)));
            adEventLog.setmStart_time(System.currentTimeMillis());
            adEventLog.setmExtend(map);
            String aid = Utility.getAid(context, str);
            if (TextUtils.isEmpty(aid)) {
                AnonymousClass4 r6 = new TimerTask() {
                    public void run() {
                        adEventLog.setmAid(Utility.getAid(context, str));
                        WBAgentHandler.this.uploadAdlog(context, adEventLog);
                    }
                };
                Timer timer = new Timer();
                mLogTimer = timer;
                timer.schedule(r6, 5000);
                return;
            }
            adEventLog.setmAid(aid);
            uploadAdlog(context, adEventLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getImei(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception unused) {
            return "";
        }
    }

    public void uploadAdlog(final Context context, AdEventLog adEventLog) {
        mActivePages.add(adEventLog);
        WBAgentExecutor.execute(new Runnable() {
            public void run() {
                LogReport.uploadAppLogs(context, WBAgentHandler.this.getLogsInMemory());
            }
        });
    }

    public static boolean isFirstStartBoolean(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WBConstants.THIRD_APP_IS_FIRST, 0);
        boolean z = sharedPreferences.getBoolean(WBConstants.THIRD_APP_IS_FIRST_KEY, true);
        if (z) {
            Editor edit = sharedPreferences.edit();
            edit.putBoolean(WBConstants.THIRD_APP_IS_FIRST_KEY, false);
            edit.commit();
        }
        return z;
    }
}
