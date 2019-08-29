package com.ta.audid.collect;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.ta.audid.utils.FileUtils;
import com.ta.audid.utils.UtdidLogger;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.android.utils.SystemProperties;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.List;
import java.util.regex.Pattern;

public class DeviceInfo2 {
    public static String getPhoneNumber(Context context) {
        return "";
    }

    public static String getIMEI(Context context) {
        String deviceId;
        String str = "";
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                deviceId = telephonyManager != null ? telephonyManager.getDeviceId() : str;
            } catch (Exception e) {
                e = e;
                UtdidLogger.i("", e.toString());
                return str;
            }
            try {
                return StringUtils.isBlank(deviceId) ? "" : deviceId;
            } catch (Exception e2) {
                Exception exc = e2;
                str = deviceId;
                e = exc;
                UtdidLogger.i("", e.toString());
                return str;
            }
        }
        return str;
    }

    public static String getIMSI(Context context) {
        String subscriberId;
        String str = "";
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                subscriberId = telephonyManager != null ? telephonyManager.getSubscriberId() : str;
            } catch (Exception e) {
                e = e;
                UtdidLogger.i("", e.toString());
                return str;
            }
            try {
                return StringUtils.isBlank(subscriberId) ? "" : subscriberId;
            } catch (Exception e2) {
                Exception exc = e2;
                str = subscriberId;
                e = exc;
                UtdidLogger.i("", e.toString());
                return str;
            }
        }
        return str;
    }

    public static String getWifiMacID(Context context) {
        if (VERSION.SDK_INT >= 23) {
            return getWifiMacID23();
        }
        return getWifiMacID22(context);
    }

    private static String getWifiMacID23() {
        try {
            byte[] hardwareAddress = NetworkInterface.getByName("wlan0").getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < hardwareAddress.length) {
                Object[] objArr = new Object[2];
                objArr[0] = Byte.valueOf(hardwareAddress[i]);
                objArr[1] = i < hardwareAddress.length - 1 ? ":" : "";
                sb.append(String.format("%02X%s", objArr));
                i++;
            }
            return sb.toString();
        } catch (Exception e) {
            UtdidLogger.i("", e);
            return "";
        }
    }

    private static String getWifiMacID22(Context context) {
        if (context != null) {
            try {
                WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
                if (connectionInfo == null) {
                    return "";
                }
                String macAddress = connectionInfo.getMacAddress();
                if (TextUtils.isEmpty(macAddress)) {
                    macAddress = "";
                }
                return macAddress;
            } catch (Throwable unused) {
            }
        }
        return "";
    }

    public static String getBluetoothMac() {
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                if (defaultAdapter.isEnabled()) {
                    String address = defaultAdapter.getAddress();
                    if (StringUtils.isBlank(address)) {
                        address = "";
                    }
                    return address;
                }
            }
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getAndroidID(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getSerialNum() {
        return SystemProperties.get("ro.serialno", "");
    }

    public static String getSimSerialNum(Context context) {
        String str;
        try {
            str = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
        } catch (Exception unused) {
            str = "";
        }
        return StringUtils.isBlank(str) ? "" : str;
    }

    public static String getNandID() {
        return FileUtils.readFileLine("/sys/block/mmcblk0/device/cid");
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.InputStreamReader] */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.InputStreamReader] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x004f */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005b A[SYNTHETIC, Splitter:B:29:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0060 A[SYNTHETIC, Splitter:B:33:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0066 A[SYNTHETIC, Splitter:B:39:0x0066] */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCPUSerial() {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            java.lang.String r4 = "cat /proc/cpuinfo | grep Serial"
            java.lang.Process r3 = r3.exec(r4)     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            java.io.InputStream r3 = r3.getInputStream()     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            java.io.LineNumberReader r3 = new java.io.LineNumberReader     // Catch:{ Throwable -> 0x0055, all -> 0x0053 }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0055, all -> 0x0053 }
            r1 = 0
        L_0x001c:
            r4 = 100
            if (r1 >= r4) goto L_0x004c
            java.lang.String r4 = r3.readLine()     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            if (r4 == 0) goto L_0x0047
            java.lang.String r5 = "Serial"
            int r5 = r4.indexOf(r5)     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            if (r5 < 0) goto L_0x0047
            java.lang.String r5 = ":"
            int r5 = r4.indexOf(r5)     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            int r5 = r5 + 1
            int r6 = r4.length()     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            java.lang.String r4 = r4.substring(r5, r6)     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            java.lang.String r4 = r4.trim()     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            r0 = r4
            goto L_0x004c
        L_0x0044:
            r0 = move-exception
            r1 = r3
            goto L_0x0059
        L_0x0047:
            int r1 = r1 + 1
            int r1 = r1 + 1
            goto L_0x001c
        L_0x004c:
            r2.close()     // Catch:{ Exception -> 0x004f }
        L_0x004f:
            r3.close()     // Catch:{ Exception -> 0x0069 }
            goto L_0x0069
        L_0x0053:
            r0 = move-exception
            goto L_0x0059
        L_0x0055:
            r1 = r2
            goto L_0x0064
        L_0x0057:
            r0 = move-exception
            r2 = r1
        L_0x0059:
            if (r2 == 0) goto L_0x005e
            r2.close()     // Catch:{ Exception -> 0x005e }
        L_0x005e:
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ Exception -> 0x0063 }
        L_0x0063:
            throw r0
        L_0x0064:
            if (r1 == 0) goto L_0x0069
            r1.close()     // Catch:{ Exception -> 0x0069 }
        L_0x0069:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.collect.DeviceInfo2.getCPUSerial():java.lang.String");
    }

    public static String getCpuCount() {
        try {
            return String.valueOf(new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public final boolean accept(File file) {
                    return Pattern.matches("cpu[0-9]+", file.getName());
                }
            }).length);
        } catch (Exception unused) {
            return "1";
        }
    }

    public static String getMaxCpuFreq() {
        return FileUtils.readFileLine("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
    }

    public static String getMinCpuFreq() {
        return FileUtils.readFileLine("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq");
    }

    public static String getMemTotalSize() {
        String readFileLine = FileUtils.readFileLine("/proc/meminfo");
        return readFileLine != null ? readFileLine.split("\\s+")[1] : "";
    }

    public static String getMemFreeSize(Context context) {
        try {
            MemoryInfo memoryInfo = new MemoryInfo();
            ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getMemoryInfo(memoryInfo);
            return String.valueOf(memoryInfo.availMem >> 10);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getLowMem(Context context) {
        try {
            MemoryInfo memoryInfo = new MemoryInfo();
            ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getMemoryInfo(memoryInfo);
            return String.valueOf(memoryInfo.threshold >> 10);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getMemThreshold(Context context) {
        try {
            MemoryInfo memoryInfo = new MemoryInfo();
            ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getMemoryInfo(memoryInfo);
            return memoryInfo.lowMemory ? "1" : "0";
        } catch (Exception unused) {
            return "0";
        }
    }

    public static String getTotalExternalMemorySize(Context context) {
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return String.valueOf(((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()));
        } catch (Throwable unused) {
            return "";
        }
    }

    private String getAvailableSize() {
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return String.valueOf(((long) statFs.getBlockCount()) * ((long) statFs.getAvailableBlocks()));
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getScreenDpi(Context context) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getResources().getDisplayMetrics().densityDpi);
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getScreenResolution(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toString(displayMetrics.widthPixels));
            sb.append("*");
            sb.append(Integer.toString(displayMetrics.heightPixels));
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public static boolean checkTfCard(Context context) {
        try {
            if (VERSION.SDK_INT >= 24) {
                return checkTfCardN(context);
            }
            return checkTfCard0(context);
        } catch (Exception unused) {
            return false;
        }
    }

    @TargetApi(24)
    private static boolean checkTfCardN(Context context) {
        List<StorageVolume> storageVolumes = ((StorageManager) context.getSystemService("storage")).getStorageVolumes();
        if (storageVolumes != null) {
            for (StorageVolume next : storageVolumes) {
                if (next.isRemovable() && next.getState().equals("mounted")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkTfCard0(Context context) {
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        try {
            Method method = StorageManager.class.getMethod("getVolumeList", new Class[0]);
            method.setAccessible(true);
            Object[] objArr = (Object[]) method.invoke(storageManager, new Object[0]);
            if (objArr != null) {
                for (Object obj : objArr) {
                    if (((Boolean) obj.getClass().getMethod("isRemovable", new Class[0]).invoke(obj, new Object[0])).booleanValue() && ((String) obj.getClass().getMethod("getState", new Class[0]).invoke(obj, new Object[0])).equals("mounted")) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static boolean checkSensor(Context context, int i) {
        List<Sensor> sensorList = ((SensorManager) context.getSystemService("sensor")).getSensorList(i);
        return sensorList != null && sensorList.size() > 0;
    }

    public static boolean hasGPSDevice(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager == null) {
            return false;
        }
        List<String> allProviders = locationManager.getAllProviders();
        if (allProviders == null) {
            return false;
        }
        return allProviders.contains(WidgetType.GPS);
    }

    public static String getBattery(Context context) {
        if (VERSION.SDK_INT < 21) {
            return getBattery0();
        }
        int batteryL = getBatteryL(context);
        if (batteryL > 0) {
            return String.valueOf(batteryL);
        }
        return getBattery0();
    }

    @TargetApi(21)
    private static int getBatteryL(Context context) {
        try {
            return ((BatteryManager) context.getSystemService("batterymanager")).getIntProperty(4);
        } catch (Throwable unused) {
            return 0;
        }
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.InputStreamReader] */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.InputStreamReader] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x004f */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005b A[SYNTHETIC, Splitter:B:29:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0060 A[SYNTHETIC, Splitter:B:33:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0066 A[SYNTHETIC, Splitter:B:39:0x0066] */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getBattery0() {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            java.lang.String r4 = "cat /sys/class/power_supply/battery/uevent"
            java.lang.Process r3 = r3.exec(r4)     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            java.io.InputStream r3 = r3.getInputStream()     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0064, all -> 0x0057 }
            java.io.LineNumberReader r3 = new java.io.LineNumberReader     // Catch:{ Throwable -> 0x0055, all -> 0x0053 }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0055, all -> 0x0053 }
            r1 = 0
        L_0x001c:
            r4 = 100
            if (r1 >= r4) goto L_0x004c
            java.lang.String r4 = r3.readLine()     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            if (r4 == 0) goto L_0x004c
            java.lang.String r5 = "POWER_SUPPLY_CAPACITY"
            boolean r5 = r4.contains(r5)     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            if (r5 == 0) goto L_0x0047
            java.lang.String r5 = "="
            int r5 = r4.indexOf(r5)     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            int r5 = r5 + 1
            int r6 = r4.length()     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            java.lang.String r4 = r4.substring(r5, r6)     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            java.lang.String r4 = r4.trim()     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            r0 = r4
            goto L_0x004c
        L_0x0044:
            r0 = move-exception
            r1 = r3
            goto L_0x0059
        L_0x0047:
            int r1 = r1 + 1
            int r1 = r1 + 1
            goto L_0x001c
        L_0x004c:
            r2.close()     // Catch:{ Exception -> 0x004f }
        L_0x004f:
            r3.close()     // Catch:{ Exception -> 0x0069 }
            goto L_0x0069
        L_0x0053:
            r0 = move-exception
            goto L_0x0059
        L_0x0055:
            r1 = r2
            goto L_0x0064
        L_0x0057:
            r0 = move-exception
            r2 = r1
        L_0x0059:
            if (r2 == 0) goto L_0x005e
            r2.close()     // Catch:{ Exception -> 0x005e }
        L_0x005e:
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ Exception -> 0x0063 }
        L_0x0063:
            throw r0
        L_0x0064:
            if (r1 == 0) goto L_0x0069
            r1.close()     // Catch:{ Exception -> 0x0069 }
        L_0x0069:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.collect.DeviceInfo2.getBattery0():java.lang.String");
    }
}
