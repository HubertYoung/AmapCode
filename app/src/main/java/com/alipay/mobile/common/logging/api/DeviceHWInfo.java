package com.alipay.mobile.common.logging.api;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build.VERSION;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;

public class DeviceHWInfo {
    public static final int DEVICEINFO_NO_INIT = -100;
    public static final int DEVICEINFO_UNKNOWN = -1;
    public static final String TAG = "DeviceHWInfo";
    private static final FileFilter a = new FileFilter() {
        public final boolean accept(File pathname) {
            String path = pathname.getName();
            if (!path.startsWith("cpu")) {
                return false;
            }
            for (int i = 3; i < path.length(); i++) {
                if (!Character.isDigit(path.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    };
    static int sCoreNum = -100;
    static String sCpuName = "-100";
    static int sFrequency = -100;
    static long sRamSize = -100;

    public static int getNumberOfCPUCores() {
        if (sCoreNum == -1) {
            return sCoreNum;
        }
        if (sCoreNum == -100) {
            synchronized (DeviceHWInfo.class) {
                if (VERSION.SDK_INT <= 10) {
                    sCoreNum = 1;
                    return 1;
                }
                try {
                    int a2 = a("/sys/devices/system/cpu/possible");
                    sCoreNum = a2;
                    if (a2 == -1) {
                        sCoreNum = a("/sys/devices/system/cpu/present");
                    }
                    if (sCoreNum == -1) {
                        sCoreNum = a();
                    }
                } catch (Throwable th) {
                    sCoreNum = -1;
                }
            }
        }
        return sCoreNum;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0028 A[SYNTHETIC, Splitter:B:15:0x0028] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int a(java.lang.String r4) {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x001d, all -> 0x0025 }
            r1.<init>(r4)     // Catch:{ IOException -> 0x001d, all -> 0x0025 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0035, all -> 0x0032 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0035, all -> 0x0032 }
            r3.<init>(r1)     // Catch:{ IOException -> 0x0035, all -> 0x0032 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0035, all -> 0x0032 }
            java.lang.String r2 = r2.readLine()     // Catch:{ IOException -> 0x0035, all -> 0x0032 }
            int r2 = b(r2)     // Catch:{ IOException -> 0x0035, all -> 0x0032 }
            r1.close()     // Catch:{ Throwable -> 0x002c }
        L_0x001b:
            r0 = r1
        L_0x001c:
            return r2
        L_0x001d:
            r2 = move-exception
        L_0x001e:
            if (r0 == 0) goto L_0x0023
            r0.close()     // Catch:{ Throwable -> 0x002e }
        L_0x0023:
            r2 = -1
            goto L_0x001c
        L_0x0025:
            r2 = move-exception
        L_0x0026:
            if (r0 == 0) goto L_0x002b
            r0.close()     // Catch:{ Throwable -> 0x0030 }
        L_0x002b:
            throw r2
        L_0x002c:
            r3 = move-exception
            goto L_0x001b
        L_0x002e:
            r2 = move-exception
            goto L_0x0023
        L_0x0030:
            r3 = move-exception
            goto L_0x002b
        L_0x0032:
            r2 = move-exception
            r0 = r1
            goto L_0x0026
        L_0x0035:
            r2 = move-exception
            r0 = r1
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.api.DeviceHWInfo.a(java.lang.String):int");
    }

    private static int b(String str) {
        if (str == null || !str.matches("0-[\\d]+$")) {
            return -1;
        }
        return Integer.valueOf(str.substring(2)).intValue() + 1;
    }

    private static int a() {
        return new File("/sys/devices/system/cpu/").listFiles(a).length;
    }

    public static int getCPUMaxFreqKHz() {
        FileInputStream stream;
        FileInputStream stream2;
        if (sFrequency == -1) {
            return sFrequency;
        }
        if (sFrequency == -100) {
            synchronized (DeviceHWInfo.class) {
                for (int i = 0; i < getNumberOfCPUCores(); i++) {
                    try {
                        File cpuInfoMaxFreqFile = new File("/sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq");
                        if (cpuInfoMaxFreqFile.exists()) {
                            byte[] buffer = new byte[128];
                            stream2 = new FileInputStream(cpuInfoMaxFreqFile);
                            stream2.read(buffer);
                            int endIndex = 0;
                            while (Character.isDigit(buffer[endIndex]) && endIndex < 128) {
                                endIndex++;
                            }
                            Integer freqBound = Integer.valueOf(Integer.parseInt(new String(buffer, 0, endIndex)));
                            if (freqBound.intValue() > sFrequency) {
                                sFrequency = freqBound.intValue();
                            }
                            try {
                                stream2.close();
                            } catch (Throwable th) {
                            }
                        }
                    } catch (NumberFormatException e) {
                        stream2.close();
                    } catch (IOException e2) {
                        sFrequency = -1;
                    } catch (Throwable th2) {
                    }
                }
                if (sFrequency == -100) {
                    stream = new FileInputStream("/proc/cpuinfo");
                    int freqBound2 = a((String) "cpu MHz", stream) * 1000;
                    if (freqBound2 > sFrequency) {
                        sFrequency = freqBound2;
                    }
                    try {
                        stream.close();
                    } catch (Throwable th3) {
                    }
                }
            }
        }
        return sFrequency;
        throw th;
        throw th;
    }

    @TargetApi(16)
    public static long getTotalMemory(Context c) {
        FileInputStream stream;
        if (sRamSize == -1) {
            return sRamSize;
        }
        if (sRamSize == -100) {
            synchronized (DeviceHWInfo.class) {
                try {
                    if (VERSION.SDK_INT >= 16) {
                        MemoryInfo memInfo = new MemoryInfo();
                        ((ActivityManager) c.getSystemService(WidgetType.ACTIVITY)).getMemoryInfo(memInfo);
                        sRamSize = memInfo.totalMem;
                    } else {
                        long totalMem = -1;
                        try {
                            stream = new FileInputStream("/proc/meminfo");
                            totalMem = ((long) a((String) "MemTotal", stream)) * 1024;
                            try {
                                stream.close();
                            } catch (Throwable th) {
                            }
                        } catch (IOException e) {
                        } catch (Throwable th2) {
                        }
                        sRamSize = totalMem;
                    }
                } catch (Throwable th3) {
                    sRamSize = -1;
                }
            }
        }
        return sRamSize;
        throw th;
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x0085 A[SYNTHETIC, Splitter:B:47:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x008a A[SYNTHETIC, Splitter:B:50:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00a1 A[SYNTHETIC, Splitter:B:62:0x00a1] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00a6 A[SYNTHETIC, Splitter:B:65:0x00a6] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x0060=Splitter:B:25:0x0060, B:67:0x00a9=Splitter:B:67:0x00a9, B:34:0x006e=Splitter:B:34:0x006e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCpuName() {
        /*
            r11 = 1
            java.lang.String r7 = sCpuName
            java.lang.String r8 = "-1"
            boolean r7 = android.text.TextUtils.equals(r7, r8)
            if (r7 == 0) goto L_0x000e
            java.lang.String r7 = sCpuName
        L_0x000d:
            return r7
        L_0x000e:
            java.lang.String r7 = sCpuName
            java.lang.String r8 = "-100"
            boolean r7 = android.text.TextUtils.equals(r7, r8)
            if (r7 == 0) goto L_0x0061
            java.lang.Class<com.alipay.mobile.common.logging.api.DeviceHWInfo> r8 = com.alipay.mobile.common.logging.api.DeviceHWInfo.class
            monitor-enter(r8)
            r4 = 0
            r1 = 0
            java.io.FileReader r5 = new java.io.FileReader     // Catch:{ Throwable -> 0x0079 }
            java.lang.String r7 = "/proc/cpuinfo"
            r5.<init>(r7)     // Catch:{ Throwable -> 0x0079 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00bf, all -> 0x00b8 }
            r2.<init>(r5)     // Catch:{ Throwable -> 0x00bf, all -> 0x00b8 }
            java.lang.String r7 = r2.readLine()     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            java.lang.String r9 = ":\\s+"
            r10 = 2
            java.lang.String[] r7 = r7.split(r9, r10)     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            r9 = 1
            r7 = r7[r9]     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            sCpuName = r7     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            java.lang.String r7 = "0"
            java.lang.String r9 = sCpuName     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            boolean r7 = r7.equals(r9)     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            if (r7 == 0) goto L_0x0058
            java.lang.String r6 = r2.readLine()     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            if (r6 == 0) goto L_0x0058
            java.lang.String r7 = ":\\s+"
            r9 = 2
            java.lang.String[] r0 = r6.split(r7, r9)     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            int r7 = r0.length     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            if (r7 <= r11) goto L_0x0058
            r7 = 1
            r7 = r0[r7]     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
            sCpuName = r7     // Catch:{ Throwable -> 0x00c2, all -> 0x00bb }
        L_0x0058:
            r5.close()     // Catch:{ Throwable -> 0x0064 }
        L_0x005b:
            r2.close()     // Catch:{ Throwable -> 0x0070 }
            r1 = r2
            r4 = r5
        L_0x0060:
            monitor-exit(r8)     // Catch:{ all -> 0x0095 }
        L_0x0061:
            java.lang.String r7 = sCpuName
            goto L_0x000d
        L_0x0064:
            r3 = move-exception
            java.lang.String r7 = "DeviceHWInfo"
            android.util.Log.w(r7, r3)     // Catch:{ all -> 0x006b }
            goto L_0x005b
        L_0x006b:
            r7 = move-exception
            r1 = r2
            r4 = r5
        L_0x006e:
            monitor-exit(r8)     // Catch:{ all -> 0x0095 }
            throw r7
        L_0x0070:
            r3 = move-exception
            java.lang.String r7 = "DeviceHWInfo"
            android.util.Log.w(r7, r3)     // Catch:{ all -> 0x006b }
            r1 = r2
            r4 = r5
            goto L_0x0060
        L_0x0079:
            r3 = move-exception
        L_0x007a:
            java.lang.String r7 = "DeviceHWInfo"
            android.util.Log.w(r7, r3)     // Catch:{ all -> 0x009e }
            java.lang.String r7 = "-1"
            sCpuName = r7     // Catch:{ all -> 0x009e }
            if (r4 == 0) goto L_0x0088
            r4.close()     // Catch:{ Throwable -> 0x0097 }
        L_0x0088:
            if (r1 == 0) goto L_0x0060
            r1.close()     // Catch:{ Throwable -> 0x008e }
            goto L_0x0060
        L_0x008e:
            r3 = move-exception
            java.lang.String r7 = "DeviceHWInfo"
            android.util.Log.w(r7, r3)     // Catch:{ all -> 0x0095 }
            goto L_0x0060
        L_0x0095:
            r7 = move-exception
            goto L_0x006e
        L_0x0097:
            r3 = move-exception
            java.lang.String r7 = "DeviceHWInfo"
            android.util.Log.w(r7, r3)     // Catch:{ all -> 0x0095 }
            goto L_0x0088
        L_0x009e:
            r7 = move-exception
        L_0x009f:
            if (r4 == 0) goto L_0x00a4
            r4.close()     // Catch:{ Throwable -> 0x00aa }
        L_0x00a4:
            if (r1 == 0) goto L_0x00a9
            r1.close()     // Catch:{ Throwable -> 0x00b1 }
        L_0x00a9:
            throw r7     // Catch:{ all -> 0x0095 }
        L_0x00aa:
            r3 = move-exception
            java.lang.String r9 = "DeviceHWInfo"
            android.util.Log.w(r9, r3)     // Catch:{ all -> 0x0095 }
            goto L_0x00a4
        L_0x00b1:
            r3 = move-exception
            java.lang.String r9 = "DeviceHWInfo"
            android.util.Log.w(r9, r3)     // Catch:{ all -> 0x0095 }
            goto L_0x00a9
        L_0x00b8:
            r7 = move-exception
            r4 = r5
            goto L_0x009f
        L_0x00bb:
            r7 = move-exception
            r1 = r2
            r4 = r5
            goto L_0x009f
        L_0x00bf:
            r3 = move-exception
            r4 = r5
            goto L_0x007a
        L_0x00c2:
            r3 = move-exception
            r1 = r2
            r4 = r5
            goto L_0x007a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.api.DeviceHWInfo.getCpuName():java.lang.String");
    }

    private static int a(String textToMatch, FileInputStream stream) {
        byte[] buffer = new byte[1024];
        try {
            int length = stream.read(buffer);
            int i = 0;
            while (i < length) {
                if (buffer[i] == 10 || i == 0) {
                    if (buffer[i] == 10) {
                        i++;
                    }
                    int j = i;
                    while (j < length) {
                        int textIndex = j - i;
                        if (buffer[j] != textToMatch.charAt(textIndex)) {
                            continue;
                            break;
                        } else if (textIndex == textToMatch.length() - 1) {
                            return a(buffer, j);
                        } else {
                            j++;
                        }
                    }
                    continue;
                }
                i++;
            }
        } catch (IOException | NumberFormatException e) {
        }
        return -1;
    }

    private static int a(byte[] buffer, int index) {
        while (index < 1024 && buffer[index] != 10) {
            if (Character.isDigit(buffer[index])) {
                int start = index;
                int index2 = index + 1;
                while (index2 < 1024 && Character.isDigit(buffer[index2])) {
                    index2++;
                }
                return Integer.parseInt(new String(buffer, 0, start, index2 - start));
            }
            index++;
        }
        return -1;
    }
}
