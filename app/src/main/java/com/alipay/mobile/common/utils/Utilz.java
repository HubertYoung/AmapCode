package com.alipay.mobile.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utilz {
    static int sFrequency = -1;

    public Utilz() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static String getCurrentFomatTime(String timeStyle) {
        return new SimpleDateFormat(timeStyle).format(Long.valueOf(Calendar.getInstance().getTimeInMillis()));
    }

    public static long getTotalMemory() {
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            String tmp = Pattern.compile("[^0-9]").matcher(localBufferedReader.readLine()).replaceAll("").trim();
            localBufferedReader.close();
            return Long.parseLong(tmp);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) "Utilz", (Throwable) e);
            return 0;
        }
    }

    public static synchronized int getCPUFrequencyMax() {
        int i;
        synchronized (Utilz.class) {
            if (sFrequency == -1) {
                sFrequency = a("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
                if (-1 != sFrequency) {
                    sFrequency /= 1000;
                }
            }
            i = sFrequency;
        }
        return i;
    }

    private static int a(String pSystemFile) {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(pSystemFile);
            try {
                StringBuilder sb = new StringBuilder();
                Scanner sc = new Scanner(fileInputStream2);
                while (sc.hasNextLine()) {
                    sb.append(sc.nextLine());
                }
                int parseInt = Integer.parseInt(sb.toString());
                try {
                    fileInputStream2.close();
                } catch (Exception e) {
                    LoggerFactory.getTraceLogger().warn((String) "Utilz", (Throwable) e);
                }
                FileInputStream fileInputStream3 = fileInputStream2;
                return parseInt;
            } catch (Exception e2) {
                e = e2;
                fileInputStream = fileInputStream2;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                try {
                    fileInputStream.close();
                } catch (Exception e3) {
                    LoggerFactory.getTraceLogger().warn((String) "Utilz", (Throwable) e3);
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            try {
                LoggerFactory.getTraceLogger().warn((String) "Utilz", (Throwable) e);
                try {
                    fileInputStream.close();
                } catch (Exception e5) {
                    LoggerFactory.getTraceLogger().warn((String) "Utilz", (Throwable) e5);
                }
                return -1;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream.close();
                throw th;
            }
        }
    }

    public static boolean isTopActivity(Activity activity) {
        if (activity != null) {
            List tasksInfo = ((ActivityManager) activity.getSystemService(WidgetType.ACTIVITY)).getRunningTasks(1);
            if (tasksInfo.size() > 0) {
                ComponentName component = tasksInfo.get(0).topActivity;
                if (activity.getPackageName().equals(component.getPackageName()) && activity.getClass().getName().equals(component.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
