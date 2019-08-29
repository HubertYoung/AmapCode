package com.alipay.mobile.nebula.util;

import android.os.Build;
import android.os.Build.VERSION;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class H5DeviceHelper {
    public static final String TAG = "H5DeviceHelper";
    private static String sAbi;
    private static String[] sAbiList;
    private static String sArch;
    private static String sCpuAbi;
    private static String sCpuHardware;
    private static Boolean sIsX86 = null;
    private static String[] sSupportedABIs;

    public static synchronized boolean x86(int version) {
        boolean z;
        synchronized (H5DeviceHelper.class) {
            try {
                if (sIsX86 != null) {
                    z = sIsX86.booleanValue();
                } else if (version == 1) {
                    z = isX86Device();
                } else {
                    z = isX86DeviceV2();
                }
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
                z = false;
            }
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0079 A[Catch:{ Throwable -> 0x00f0, all -> 0x0111 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00d9  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0126  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isX86Device() {
        /*
            r9 = 1
            r10 = 0
            r8 = 0
            r6 = 0
            r3 = 0
            java.lang.Runtime r11 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0121 }
            java.lang.String r12 = "getprop ro.product.cpu.abi"
            java.lang.Process r8 = r11.exec(r12)     // Catch:{ Throwable -> 0x0121 }
            java.io.InputStreamReader r7 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0121 }
            java.io.InputStream r11 = r8.getInputStream()     // Catch:{ Throwable -> 0x0121 }
            r7.<init>(r11)     // Catch:{ Throwable -> 0x0121 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0123, all -> 0x011a }
            r4.<init>(r7)     // Catch:{ Throwable -> 0x0123, all -> 0x011a }
            java.lang.String r0 = r4.readLine()     // Catch:{ Throwable -> 0x00b6, all -> 0x011d }
            if (r0 == 0) goto L_0x0038
            boolean r11 = r0.isEmpty()     // Catch:{ Throwable -> 0x00b6, all -> 0x011d }
            if (r11 != 0) goto L_0x0038
            java.lang.String r11 = "x86"
            boolean r11 = r0.contains(r11)     // Catch:{ Throwable -> 0x00b6, all -> 0x011d }
            if (r11 == 0) goto L_0x00a6
            r11 = 1
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r11)     // Catch:{ Throwable -> 0x00b6, all -> 0x011d }
            sIsX86 = r11     // Catch:{ Throwable -> 0x00b6, all -> 0x011d }
        L_0x0038:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r4)
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r7)
            if (r8 == 0) goto L_0x0044
            r8.destroy()
            r8 = 0
        L_0x0044:
            java.lang.Runtime r11 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0113, all -> 0x00fd }
            java.lang.String r12 = "getprop ro.product.cpu.abilist"
            java.lang.Process r8 = r11.exec(r12)     // Catch:{ Throwable -> 0x0113, all -> 0x00fd }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0113, all -> 0x00fd }
            java.io.InputStream r11 = r8.getInputStream()     // Catch:{ Throwable -> 0x0113, all -> 0x00fd }
            r6.<init>(r11)     // Catch:{ Throwable -> 0x0113, all -> 0x00fd }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0117, all -> 0x010e }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x0117, all -> 0x010e }
            java.lang.String r1 = r3.readLine()     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            if (r1 == 0) goto L_0x0080
            boolean r11 = r1.isEmpty()     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            if (r11 != 0) goto L_0x0080
            java.lang.String r11 = ","
            java.lang.String[] r0 = r1.split(r11)     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            r11 = 0
            r11 = r0[r11]     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            java.lang.String r12 = "x86"
            boolean r11 = r11.contains(r12)     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            if (r11 == 0) goto L_0x00dd
            r11 = 1
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r11)     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            sIsX86 = r11     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
        L_0x0080:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r3)
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r6)
            if (r8 == 0) goto L_0x008b
            r8.destroy()
        L_0x008b:
            java.lang.String r11 = "os.arch"
            java.lang.String r2 = java.lang.System.getProperty(r11)
            if (r2 == 0) goto L_0x010c
            java.lang.String r11 = "x86"
            boolean r11 = r2.contains(r11)
            if (r11 == 0) goto L_0x010c
        L_0x009b:
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
            sIsX86 = r9
            boolean r9 = r9.booleanValue()
            return r9
        L_0x00a6:
            java.lang.String r11 = "arm"
            boolean r11 = r0.contains(r11)     // Catch:{ Throwable -> 0x00b6, all -> 0x011d }
            if (r11 == 0) goto L_0x0038
            r11 = 0
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r11)     // Catch:{ Throwable -> 0x00b6, all -> 0x011d }
            sIsX86 = r11     // Catch:{ Throwable -> 0x00b6, all -> 0x011d }
            goto L_0x0038
        L_0x00b6:
            r5 = move-exception
            r3 = r4
            r6 = r7
        L_0x00b9:
            java.lang.String r11 = "H5DeviceHelper"
            java.lang.String r12 = "exception detail"
            com.alipay.mobile.nebula.util.H5Log.e(r11, r12, r5)     // Catch:{ all -> 0x00d0 }
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r3)
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r6)
            if (r8 == 0) goto L_0x0126
            r8.destroy()
            r8 = 0
            r4 = r3
            r7 = r6
            goto L_0x0044
        L_0x00d0:
            r9 = move-exception
        L_0x00d1:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r3)
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r6)
            if (r8 == 0) goto L_0x00dc
            r8.destroy()
        L_0x00dc:
            throw r9
        L_0x00dd:
            r11 = 0
            r11 = r0[r11]     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            java.lang.String r12 = "arm"
            boolean r11 = r11.contains(r12)     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            if (r11 == 0) goto L_0x0080
            r11 = 0
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r11)     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            sIsX86 = r11     // Catch:{ Throwable -> 0x00f0, all -> 0x0111 }
            goto L_0x0080
        L_0x00f0:
            r11 = move-exception
        L_0x00f1:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r3)
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r6)
            if (r8 == 0) goto L_0x008b
            r8.destroy()
            goto L_0x008b
        L_0x00fd:
            r9 = move-exception
            r3 = r4
            r6 = r7
        L_0x0100:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r3)
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r6)
            if (r8 == 0) goto L_0x010b
            r8.destroy()
        L_0x010b:
            throw r9
        L_0x010c:
            r9 = r10
            goto L_0x009b
        L_0x010e:
            r9 = move-exception
            r3 = r4
            goto L_0x0100
        L_0x0111:
            r9 = move-exception
            goto L_0x0100
        L_0x0113:
            r11 = move-exception
            r3 = r4
            r6 = r7
            goto L_0x00f1
        L_0x0117:
            r11 = move-exception
            r3 = r4
            goto L_0x00f1
        L_0x011a:
            r9 = move-exception
            r6 = r7
            goto L_0x00d1
        L_0x011d:
            r9 = move-exception
            r3 = r4
            r6 = r7
            goto L_0x00d1
        L_0x0121:
            r5 = move-exception
            goto L_0x00b9
        L_0x0123:
            r5 = move-exception
            r6 = r7
            goto L_0x00b9
        L_0x0126:
            r4 = r3
            r7 = r6
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.util.H5DeviceHelper.isX86Device():boolean");
    }

    private static boolean isX86DeviceV2() {
        sIsX86 = Boolean.valueOf(isArchContains("x86"));
        H5Log.d(TAG, "isX86DeviceV2 " + sIsX86);
        return sIsX86.booleanValue();
    }

    private static boolean isArchContains(String keyword) {
        if (sArch == null) {
            sArch = System.getProperty("os.arch");
        }
        if (sArch != null && sArch.toLowerCase().contains(keyword)) {
            return true;
        }
        if (sAbi == null) {
            try {
                sAbi = Build.CPU_ABI;
            } catch (Exception e) {
                H5Log.e(TAG, "exception ", e);
            }
        }
        if (sAbi != null && sAbi.toLowerCase().contains(keyword)) {
            return true;
        }
        if (sSupportedABIs == null && VERSION.SDK_INT >= 21) {
            try {
                sSupportedABIs = (String[]) Build.class.getField("SUPPORTED_ABIS").get(null);
            } catch (Exception e2) {
                H5Log.e(TAG, "exception ", e2);
            }
        }
        if (sSupportedABIs != null && sSupportedABIs.length > 0 && sSupportedABIs[0] != null && sSupportedABIs[0].toLowerCase().contains(keyword)) {
            return true;
        }
        if (sCpuAbi == null) {
            sCpuAbi = getFromSystemProp("ro.product.cpu.abi");
        }
        if (sCpuAbi != null && sCpuAbi.toLowerCase().contains(keyword)) {
            return true;
        }
        if (sAbiList == null) {
            String abilist = getFromSystemProp("ro.product.cpu.abilist");
            if (!(abilist == null || abilist.length() == 0)) {
                sAbiList = abilist.split(",");
            }
        }
        return sAbiList != null && sAbiList.length > 0 && sAbiList[0] != null && sAbiList[0].toLowerCase().contains(keyword);
    }

    private static String getFromSystemProp(String key) {
        InputStreamReader propISR;
        FileInputStream propFIS = null;
        InputStreamReader propISR2 = null;
        BufferedReader propBR = null;
        try {
            FileInputStream propFIS2 = new FileInputStream(new File("/system/build.prop"));
            try {
                propISR = new InputStreamReader(propFIS2);
            } catch (Throwable th) {
                th = th;
                propFIS = propFIS2;
                H5IOUtils.closeQuietly(propBR);
                H5IOUtils.closeQuietly(propISR2);
                H5IOUtils.closeQuietly(propFIS);
                throw th;
            }
            try {
                BufferedReader propBR2 = new BufferedReader(propISR);
                while (true) {
                    try {
                        String line = propBR2.readLine();
                        if (line == null) {
                            H5IOUtils.closeQuietly(propBR2);
                            H5IOUtils.closeQuietly(propISR);
                            H5IOUtils.closeQuietly(propFIS2);
                            BufferedReader bufferedReader = propBR2;
                            InputStreamReader inputStreamReader = propISR;
                            FileInputStream fileInputStream = propFIS2;
                            break;
                        } else if (line.contains(key)) {
                            String[] cols = line.split("=");
                            if (cols.length == 2 && cols[0].trim().equals(key)) {
                                String trim = cols[1].trim();
                                H5IOUtils.closeQuietly(propBR2);
                                H5IOUtils.closeQuietly(propISR);
                                H5IOUtils.closeQuietly(propFIS2);
                                BufferedReader bufferedReader2 = propBR2;
                                InputStreamReader inputStreamReader2 = propISR;
                                FileInputStream fileInputStream2 = propFIS2;
                                return trim;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        propBR = propBR2;
                        propISR2 = propISR;
                        propFIS = propFIS2;
                        H5IOUtils.closeQuietly(propBR);
                        H5IOUtils.closeQuietly(propISR2);
                        H5IOUtils.closeQuietly(propFIS);
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                propISR2 = propISR;
                propFIS = propFIS2;
                H5IOUtils.closeQuietly(propBR);
                H5IOUtils.closeQuietly(propISR2);
                H5IOUtils.closeQuietly(propFIS);
                throw th;
            }
        } catch (Throwable th4) {
            e = th4;
            H5Log.e((String) TAG, "getFromSystemProp exception: " + e.getMessage());
            H5IOUtils.closeQuietly(propBR);
            H5IOUtils.closeQuietly(propISR2);
            H5IOUtils.closeQuietly(propFIS);
            return null;
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0037  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCpuHardware() {
        /*
            java.lang.String r6 = sCpuHardware
            if (r6 == 0) goto L_0x0007
            java.lang.String r2 = sCpuHardware
        L_0x0006:
            return r2
        L_0x0007:
            r2 = 0
            android.os.StrictMode$ThreadPolicy r5 = android.os.StrictMode.allowThreadDiskReads()
            r0 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x004b }
            java.io.FileReader r6 = new java.io.FileReader     // Catch:{ Exception -> 0x004b }
            java.lang.String r7 = "/proc/cpuinfo"
            r6.<init>(r7)     // Catch:{ Exception -> 0x004b }
            r1.<init>(r6)     // Catch:{ Exception -> 0x004b }
        L_0x0019:
            java.lang.String r4 = r1.readLine()     // Catch:{ Exception -> 0x0065, all -> 0x0062 }
            if (r4 != 0) goto L_0x003c
            java.lang.String r6 = "H5DeviceHelper"
            java.lang.String r7 = "/proc/cpuinfo lacks a Hardware entry, use Build.BOARD"
            com.alipay.mobile.nebula.util.H5Log.w(r6, r7)     // Catch:{ Exception -> 0x0065, all -> 0x0062 }
        L_0x0026:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r1)
            android.os.StrictMode.setThreadPolicy(r5)
            r0 = r1
        L_0x002d:
            boolean r6 = android.text.TextUtils.isEmpty(r2)
            if (r6 == 0) goto L_0x0035
            java.lang.String r2 = android.os.Build.BOARD
        L_0x0035:
            if (r2 != 0) goto L_0x0039
            java.lang.String r2 = ""
        L_0x0039:
            sCpuHardware = r2
            goto L_0x0006
        L_0x003c:
            java.lang.String r6 = "Hardware\t: "
            boolean r6 = r4.startsWith(r6)     // Catch:{ Exception -> 0x0065, all -> 0x0062 }
            if (r6 == 0) goto L_0x0019
            r6 = 11
            java.lang.String r2 = r4.substring(r6)     // Catch:{ Exception -> 0x0065, all -> 0x0062 }
            goto L_0x0026
        L_0x004b:
            r3 = move-exception
        L_0x004c:
            java.lang.String r6 = "H5DeviceHelper"
            java.lang.String r7 = "Cannot get Hardware from /proc/cpuinfo"
            com.alipay.mobile.nebula.util.H5Log.w(r6, r7, r3)     // Catch:{ all -> 0x005a }
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r0)
            android.os.StrictMode.setThreadPolicy(r5)
            goto L_0x002d
        L_0x005a:
            r6 = move-exception
        L_0x005b:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r0)
            android.os.StrictMode.setThreadPolicy(r5)
            throw r6
        L_0x0062:
            r6 = move-exception
            r0 = r1
            goto L_0x005b
        L_0x0065:
            r3 = move-exception
            r0 = r1
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.util.H5DeviceHelper.getCpuHardware():java.lang.String");
    }

    public static boolean isFoldingScreen() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            String phoneInfo = Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL + MetaRecord.LOG_SEPARATOR + VERSION.SDK_INT;
            JSONArray jsonArray = H5Utils.parseArray(h5ConfigProvider.getConfigWithProcessCache("h5_folder_screen_phone"));
            if (jsonArray != null && !jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (phoneInfo.equalsIgnoreCase(jsonArray.getString(i))) {
                        H5Log.d(TAG, "h5_folder_screen_phone contain phone " + phoneInfo);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
