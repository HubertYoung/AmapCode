package com.alipay.security.mobile.module.deviceinfo;

import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.taobao.wireless.security.sdk.SecurityGuardManager;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class DeviceInfo {
    private static DeviceInfo deviceInfo = new DeviceInfo();
    private static long mMachineBootTime;
    private Map<String, String> securityGuardWuaMap = null;

    private DeviceInfo() {
    }

    public static DeviceInfo getInstance() {
        return deviceInfo;
    }

    private static String getMacAddrCompatible() {
        try {
            ArrayList<T> list = Collections.list(NetworkInterface.getNetworkInterfaces());
            if (list == null) {
                return "02:00:00:00:00:00";
            }
            for (T t : list) {
                if (t != null && t.getName() != null && t.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = t.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "02:00:00:00:00:00";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        sb.append(String.format("%02X:", new Object[]{Integer.valueOf(b & 255)}));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
            return "02:00:00:00:00:00";
        } catch (Throwable unused) {
        }
    }

    public static String getCurrentNetType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return null;
        }
        if (activeNetworkInfo.getType() == 1) {
            return "WIFI";
        }
        if (activeNetworkInfo.getType() != 0) {
            return null;
        }
        int subtype = activeNetworkInfo.getSubtype();
        if (subtype == 4 || subtype == 1 || subtype == 2 || subtype == 7 || subtype == 11) {
            return "2G";
        }
        if (subtype == 3 || subtype == 5 || subtype == 6 || subtype == 8 || subtype == 9 || subtype == 10 || subtype == 12 || subtype == 14 || subtype == 15) {
            return "3G";
        }
        return subtype == 13 ? "4G" : "UNKNOW";
    }

    public static String getPhoneIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            return nextElement.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return "";
    }

    public String getIMEI(Context context) {
        String str = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getDeviceId();
                }
            } catch (Throwable unused) {
            }
        }
        return str == null ? "" : str;
    }

    public String getIMSI(Context context) {
        String str = "";
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getSubscriberId();
                }
            } catch (Throwable unused) {
            }
        }
        return str == null ? "" : str;
    }

    public String getAirplaneMode(Context context) {
        int i;
        try {
            i = System.getInt(context.getContentResolver(), "airplane_mode_on", 0);
        } catch (Throwable unused) {
            i = 0;
        }
        return i == 1 ? "1" : "0";
    }

    public long getAvailableInternalMemorySize() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable unused) {
            return 0;
        }
    }

    public long getAvailableSDCardSize() {
        try {
            if (!"mounted".equals(Environment.getExternalStorageState())) {
                return 0;
            }
            StatFs statFs = new StatFs(CommonUtils.getExternalDirectory().getPath());
            return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
        } catch (Throwable unused) {
            return 0;
        }
    }

    public byte[] getSystemVolumeSetting(Context context) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            int i = audioManager.getRingerMode() == 0 ? 1 : 0;
            int streamVolume = audioManager.getStreamVolume(0);
            int streamVolume2 = audioManager.getStreamVolume(1);
            int streamVolume3 = audioManager.getStreamVolume(2);
            int streamVolume4 = audioManager.getStreamVolume(3);
            int streamVolume5 = audioManager.getStreamVolume(4);
            ByteBuffer allocate = ByteBuffer.allocate(24);
            allocate.order(ByteOrder.BIG_ENDIAN);
            allocate.putInt(i);
            allocate.putInt(streamVolume);
            allocate.putInt(streamVolume2);
            allocate.putInt(streamVolume3);
            allocate.putInt(streamVolume4);
            allocate.putInt(streamVolume5);
            return allocate.array();
        } catch (Throwable unused) {
            return null;
        }
    }

    public String getSupportedInstructionSets() {
        try {
            HashSet<String> hashSet = new HashSet<>();
            hashSet.add(Build.CPU_ABI);
            hashSet.add(Build.CPU_ABI2);
            if (VERSION.SDK_INT >= 21) {
                if (Build.SUPPORTED_ABIS != null) {
                    for (String add : Build.SUPPORTED_ABIS) {
                        hashSet.add(add);
                    }
                }
                if (Build.SUPPORTED_32_BIT_ABIS != null) {
                    for (String add2 : Build.SUPPORTED_32_BIT_ABIS) {
                        hashSet.add(add2);
                    }
                }
                if (Build.SUPPORTED_64_BIT_ABIS != null) {
                    for (String add3 : Build.SUPPORTED_64_BIT_ABIS) {
                        hashSet.add(add3);
                    }
                }
            }
            JSONArray jSONArray = new JSONArray();
            for (String str : hashSet) {
                if (str != null && str.length() > 0) {
                    jSONArray.put(str);
                }
            }
            return jSONArray.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public String getOperatorType(Context context) {
        String str = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getSimOperator();
                }
            } catch (Throwable unused) {
            }
        }
        return str == null ? "" : str;
    }

    public String getOperatorName(Context context) {
        String str = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getSimOperatorName();
                }
            } catch (Throwable unused) {
            }
        }
        return str == null ? "" : str;
    }

    public String getNetworkOperatorName(Context context) {
        String str = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getNetworkOperatorName();
                }
            } catch (Throwable unused) {
            }
        }
        return str == null ? "" : str;
    }

    public String getVoiceMailNumber(Context context) {
        String str = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getVoiceMailNumber();
                }
            } catch (Throwable unused) {
            }
        }
        return str == null ? "" : str;
    }

    public String getVoiceMailTag(Context context) {
        String str = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getVoiceMailAlphaTag();
                }
            } catch (Throwable unused) {
            }
        }
        return str == null ? "" : str;
    }

    public byte[] getSensorDigest(Context context) {
        if (context == null) {
            return null;
        }
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (sensorManager == null) {
                return null;
            }
            List<Sensor> sensorList = sensorManager.getSensorList(-1);
            if (sensorList == null || sensorList.size() <= 0) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (Sensor next : sensorList) {
                sb.append(next.getName());
                sb.append(next.getVersion());
                sb.append(next.getVendor());
            }
            return CommonUtils.sha1StringWithBytes(sb.toString());
        } catch (Throwable unused) {
            return null;
        }
    }

    public String getSensorInfo(Context context) {
        JSONArray jSONArray = new JSONArray();
        if (context != null) {
            try {
                SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
                if (sensorManager != null) {
                    List<Sensor> sensorList = sensorManager.getSensorList(-1);
                    if (sensorList != null && sensorList.size() > 0) {
                        for (Sensor next : sensorList) {
                            if (next != null) {
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put("name", next.getName());
                                jSONObject.put("version", next.getVersion());
                                jSONObject.put("vendor", next.getVendor());
                                jSONArray.put(jSONObject);
                            }
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return jSONArray.toString();
    }

    public String getScreenResolution(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toString(displayMetrics.widthPixels));
            sb.append("*");
            sb.append(Integer.toString(displayMetrics.heightPixels));
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public String getScreenDpi(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            StringBuilder sb = new StringBuilder();
            sb.append(displayMetrics.densityDpi);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public int getScreenWidth(Context context) {
        try {
            return context.getResources().getDisplayMetrics().widthPixels;
        } catch (Throwable unused) {
            return 0;
        }
    }

    public int getScreenHeight(Context context) {
        try {
            return context.getResources().getDisplayMetrics().heightPixels;
        } catch (Throwable unused) {
            return 0;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        if ("02:00:00:00:00:00".equals(r3) == false) goto L_0x0029;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getMACAddress(android.content.Context r3) {
        /*
            r2 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = "wifi"
            java.lang.Object r3 = r3.getSystemService(r1)     // Catch:{ Throwable -> 0x0028 }
            android.net.wifi.WifiManager r3 = (android.net.wifi.WifiManager) r3     // Catch:{ Throwable -> 0x0028 }
            android.net.wifi.WifiInfo r3 = r3.getConnectionInfo()     // Catch:{ Throwable -> 0x0028 }
            java.lang.String r3 = r3.getMacAddress()     // Catch:{ Throwable -> 0x0028 }
            if (r3 == 0) goto L_0x0023
            int r0 = r3.length()     // Catch:{ Throwable -> 0x0029 }
            if (r0 == 0) goto L_0x0023
            java.lang.String r0 = "02:00:00:00:00:00"
            boolean r0 = r0.equals(r3)     // Catch:{ Throwable -> 0x0029 }
            if (r0 == 0) goto L_0x0029
        L_0x0023:
            java.lang.String r0 = getMacAddrCompatible()     // Catch:{ Throwable -> 0x0029 }
            return r0
        L_0x0028:
            r3 = r0
        L_0x0029:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getMACAddress(android.content.Context):java.lang.String");
    }

    public boolean isConnectedWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1;
    }

    public String getSIMSerial(Context context) {
        if ("ALIPAY_INSIDE_HUAWEI_EMUI".equals(StaticConfig.d())) {
            return "";
        }
        try {
            String simSerialNumber = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
            if (simSerialNumber != null) {
                if (simSerialNumber != null) {
                    try {
                        if (simSerialNumber.length() == 0) {
                        }
                    } catch (Throwable unused) {
                    }
                }
                return simSerialNumber;
            }
            return "";
        } catch (Throwable unused2) {
            return "";
        }
    }

    public String getAndroidID(Context context) {
        String str;
        try {
            str = Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable unused) {
            str = "";
        }
        return str == null ? "" : str;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(13:0|1|2|3|4|5|6|7|(2:8|(3:10|11|(2:13|(3:63|15|16)(1:17))(0))(0))|26|27|(2:62|64)(1:65)|(1:(0))) */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:24|25) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x007a, code lost:
        if (r2 != null) goto L_0x0050;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x004d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0050 */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0060 A[SYNTHETIC, Splitter:B:36:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0065 A[SYNTHETIC, Splitter:B:40:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x006a A[SYNTHETIC, Splitter:B:44:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0072 A[SYNTHETIC, Splitter:B:52:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0077 A[SYNTHETIC, Splitter:B:56:0x0077] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getCPUSerial() {
        /*
            r8 = this;
            java.lang.String r0 = "0000000000000000"
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x006e, all -> 0x005b }
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x006e, all -> 0x005b }
            java.lang.String r4 = "/proc/cpuinfo"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x006e, all -> 0x005b }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x006e, all -> 0x005b }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0059, all -> 0x0056 }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0059, all -> 0x0056 }
            java.io.LineNumberReader r4 = new java.io.LineNumberReader     // Catch:{ Throwable -> 0x0070, all -> 0x0054 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x0070, all -> 0x0054 }
            r1 = 1
            r5 = 1
        L_0x001b:
            r6 = 100
            if (r5 >= r6) goto L_0x004a
            java.lang.String r6 = r4.readLine()     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            if (r6 == 0) goto L_0x004a
            java.lang.String r7 = "Serial"
            int r7 = r6.indexOf(r7)     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            if (r7 < 0) goto L_0x0042
            java.lang.String r5 = ":"
            int r5 = r6.indexOf(r5)     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            int r5 = r5 + r1
            int r1 = r6.length()     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            java.lang.String r1 = r6.substring(r5, r1)     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            java.lang.String r1 = r1.trim()     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            r0 = r1
            goto L_0x004a
        L_0x0042:
            int r5 = r5 + 1
            goto L_0x001b
        L_0x0045:
            r0 = move-exception
            r1 = r4
            goto L_0x005e
        L_0x0048:
            r1 = r4
            goto L_0x0070
        L_0x004a:
            r4.close()     // Catch:{ Throwable -> 0x004d }
        L_0x004d:
            r3.close()     // Catch:{ Throwable -> 0x0050 }
        L_0x0050:
            r2.close()     // Catch:{ Throwable -> 0x007d }
            goto L_0x007d
        L_0x0054:
            r0 = move-exception
            goto L_0x005e
        L_0x0056:
            r0 = move-exception
            r3 = r1
            goto L_0x005e
        L_0x0059:
            r3 = r1
            goto L_0x0070
        L_0x005b:
            r0 = move-exception
            r2 = r1
            r3 = r2
        L_0x005e:
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ Throwable -> 0x0063 }
        L_0x0063:
            if (r3 == 0) goto L_0x0068
            r3.close()     // Catch:{ Throwable -> 0x0068 }
        L_0x0068:
            if (r2 == 0) goto L_0x006d
            r2.close()     // Catch:{ Throwable -> 0x006d }
        L_0x006d:
            throw r0
        L_0x006e:
            r2 = r1
            r3 = r2
        L_0x0070:
            if (r1 == 0) goto L_0x0075
            r1.close()     // Catch:{ Throwable -> 0x0075 }
        L_0x0075:
            if (r3 == 0) goto L_0x007a
            r3.close()     // Catch:{ Throwable -> 0x007a }
        L_0x007a:
            if (r2 == 0) goto L_0x007d
            goto L_0x0050
        L_0x007d:
            if (r0 != 0) goto L_0x0081
            java.lang.String r0 = ""
        L_0x0081:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getCPUSerial():java.lang.String");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(13:0|1|2|3|4|5|6|7|(2:8|(3:10|11|(2:13|(3:62|15|16)(1:17))(0))(0))|26|27|60|(1:(0))) */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:24|25) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x007a, code lost:
        if (r2 != null) goto L_0x0050;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x004d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0050 */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0060 A[SYNTHETIC, Splitter:B:36:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0065 A[SYNTHETIC, Splitter:B:40:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x006a A[SYNTHETIC, Splitter:B:44:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0072 A[SYNTHETIC, Splitter:B:52:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0077 A[SYNTHETIC, Splitter:B:56:0x0077] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getCPUHardware() {
        /*
            r8 = this;
            java.lang.String r0 = "-1"
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x006e, all -> 0x005b }
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x006e, all -> 0x005b }
            java.lang.String r4 = "/proc/cpuinfo"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x006e, all -> 0x005b }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x006e, all -> 0x005b }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0059, all -> 0x0056 }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0059, all -> 0x0056 }
            java.io.LineNumberReader r4 = new java.io.LineNumberReader     // Catch:{ Throwable -> 0x0070, all -> 0x0054 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x0070, all -> 0x0054 }
            r1 = 1
            r5 = 1
        L_0x001b:
            r6 = 100
            if (r5 >= r6) goto L_0x004a
            java.lang.String r6 = r4.readLine()     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            if (r6 == 0) goto L_0x004a
            java.lang.String r7 = "Hardware"
            int r7 = r6.indexOf(r7)     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            if (r7 < 0) goto L_0x0042
            java.lang.String r5 = ":"
            int r5 = r6.indexOf(r5)     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            int r5 = r5 + r1
            int r1 = r6.length()     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            java.lang.String r1 = r6.substring(r5, r1)     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            java.lang.String r1 = r1.trim()     // Catch:{ Throwable -> 0x0048, all -> 0x0045 }
            r0 = r1
            goto L_0x004a
        L_0x0042:
            int r5 = r5 + 1
            goto L_0x001b
        L_0x0045:
            r0 = move-exception
            r1 = r4
            goto L_0x005e
        L_0x0048:
            r1 = r4
            goto L_0x0070
        L_0x004a:
            r4.close()     // Catch:{ Throwable -> 0x004d }
        L_0x004d:
            r3.close()     // Catch:{ Throwable -> 0x0050 }
        L_0x0050:
            r2.close()     // Catch:{ Throwable -> 0x007d }
            goto L_0x007d
        L_0x0054:
            r0 = move-exception
            goto L_0x005e
        L_0x0056:
            r0 = move-exception
            r3 = r1
            goto L_0x005e
        L_0x0059:
            r3 = r1
            goto L_0x0070
        L_0x005b:
            r0 = move-exception
            r2 = r1
            r3 = r2
        L_0x005e:
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ Throwable -> 0x0063 }
        L_0x0063:
            if (r3 == 0) goto L_0x0068
            r3.close()     // Catch:{ Throwable -> 0x0068 }
        L_0x0068:
            if (r2 == 0) goto L_0x006d
            r2.close()     // Catch:{ Throwable -> 0x006d }
        L_0x006d:
            throw r0
        L_0x006e:
            r2 = r1
            r3 = r2
        L_0x0070:
            if (r1 == 0) goto L_0x0075
            r1.close()     // Catch:{ Throwable -> 0x0075 }
        L_0x0075:
            if (r3 == 0) goto L_0x007a
            r3.close()     // Catch:{ Throwable -> 0x007a }
        L_0x007a:
            if (r2 == 0) goto L_0x007d
            goto L_0x0050
        L_0x007d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getCPUHardware():java.lang.String");
    }

    public int getCpuCount() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return Pattern.matches("cpu[0-9]+", file.getName());
                }
            }).length;
        } catch (Throwable unused) {
            return 1;
        }
    }

    public String getCpuFrequent() {
        String cpuFreq1 = getCpuFreq1();
        if (!CommonUtils.isBlank(cpuFreq1)) {
            return cpuFreq1;
        }
        return getCpuFreq2();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0047, code lost:
        if (r2 == null) goto L_0x004a;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0020 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0027 */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0038 A[SYNTHETIC, Splitter:B:27:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x003d A[SYNTHETIC, Splitter:B:31:0x003d] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0044 A[SYNTHETIC, Splitter:B:39:0x0044] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getCpuFreq1() {
        /*
            r5 = this;
            java.lang.String r0 = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x0041, all -> 0x0034 }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x0041, all -> 0x0034 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0042, all -> 0x0032 }
            r3 = 8192(0x2000, float:1.14794E-41)
            r0.<init>(r2, r3)     // Catch:{ Throwable -> 0x0042, all -> 0x0032 }
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x0030, all -> 0x002b }
            boolean r3 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r1)     // Catch:{ Throwable -> 0x0030, all -> 0x002b }
            if (r3 != 0) goto L_0x0024
            java.lang.String r1 = r1.trim()     // Catch:{ Throwable -> 0x0030, all -> 0x002b }
            r0.close()     // Catch:{ Throwable -> 0x0020 }
        L_0x0020:
            r2.close()     // Catch:{ Throwable -> 0x0023 }
        L_0x0023:
            return r1
        L_0x0024:
            r0.close()     // Catch:{ Throwable -> 0x0027 }
        L_0x0027:
            r2.close()     // Catch:{ Throwable -> 0x004a }
            goto L_0x004a
        L_0x002b:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0036
        L_0x0030:
            r1 = r0
            goto L_0x0042
        L_0x0032:
            r0 = move-exception
            goto L_0x0036
        L_0x0034:
            r0 = move-exception
            r2 = r1
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            r1.close()     // Catch:{ Throwable -> 0x003b }
        L_0x003b:
            if (r2 == 0) goto L_0x0040
            r2.close()     // Catch:{ Throwable -> 0x0040 }
        L_0x0040:
            throw r0
        L_0x0041:
            r2 = r1
        L_0x0042:
            if (r1 == 0) goto L_0x0047
            r1.close()     // Catch:{ Throwable -> 0x0047 }
        L_0x0047:
            if (r2 == 0) goto L_0x004a
            goto L_0x0027
        L_0x004a:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getCpuFreq1():java.lang.String");
    }

    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:3|4|(3:5|6|(3:8|(2:10|(2:53|16))(1:54)|52)(0))|17|18|19|20|21) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x003b */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x004e A[SYNTHETIC, Splitter:B:31:0x004e] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0053 A[SYNTHETIC, Splitter:B:35:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x005a A[SYNTHETIC, Splitter:B:43:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x005f A[SYNTHETIC, Splitter:B:47:0x005f] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getCpuFreq2() {
        /*
            r8 = this;
            java.lang.String r0 = "/proc/cpuinfo"
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x0057, all -> 0x0048 }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x0057, all -> 0x0048 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0058, all -> 0x0043 }
            r3 = 8192(0x2000, float:1.14794E-41)
            r0.<init>(r2, r3)     // Catch:{ Throwable -> 0x0058, all -> 0x0043 }
        L_0x000f:
            java.lang.String r3 = r0.readLine()     // Catch:{ Throwable -> 0x0041, all -> 0x003f }
            if (r3 == 0) goto L_0x0038
            boolean r4 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r3)     // Catch:{ Throwable -> 0x0041, all -> 0x003f }
            if (r4 != 0) goto L_0x000f
            java.lang.String r4 = ":"
            java.lang.String[] r3 = r3.split(r4)     // Catch:{ Throwable -> 0x0041, all -> 0x003f }
            if (r3 == 0) goto L_0x000f
            int r4 = r3.length     // Catch:{ Throwable -> 0x0041, all -> 0x003f }
            r5 = 1
            if (r4 <= r5) goto L_0x000f
            r4 = 0
            r4 = r3[r4]     // Catch:{ Throwable -> 0x0041, all -> 0x003f }
            java.lang.String r6 = "BogoMIPS"
            boolean r4 = r4.contains(r6)     // Catch:{ Throwable -> 0x0041, all -> 0x003f }
            if (r4 == 0) goto L_0x000f
            r1 = r3[r5]     // Catch:{ Throwable -> 0x0041, all -> 0x003f }
            java.lang.String r1 = r1.trim()     // Catch:{ Throwable -> 0x0041, all -> 0x003f }
        L_0x0038:
            r2.close()     // Catch:{ Throwable -> 0x003b }
        L_0x003b:
            r0.close()     // Catch:{ Throwable -> 0x003e }
        L_0x003e:
            return r1
        L_0x003f:
            r1 = move-exception
            goto L_0x004c
        L_0x0041:
            r1 = r0
            goto L_0x0058
        L_0x0043:
            r0 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x004c
        L_0x0048:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x004c:
            if (r2 == 0) goto L_0x0051
            r2.close()     // Catch:{ Throwable -> 0x0051 }
        L_0x0051:
            if (r0 == 0) goto L_0x0056
            r0.close()     // Catch:{ Throwable -> 0x0056 }
        L_0x0056:
            throw r1
        L_0x0057:
            r2 = r1
        L_0x0058:
            if (r2 == 0) goto L_0x005d
            r2.close()     // Catch:{ Throwable -> 0x005d }
        L_0x005d:
            if (r1 == 0) goto L_0x0062
            r1.close()     // Catch:{ Throwable -> 0x0062 }
        L_0x0062:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getCpuFreq2():java.lang.String");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:17|18|19|20|47|49) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0023 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x002a */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x003d A[SYNTHETIC, Splitter:B:29:0x003d] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0042 A[SYNTHETIC, Splitter:B:33:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0049 A[SYNTHETIC, Splitter:B:41:0x0049] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x004e A[SYNTHETIC, Splitter:B:45:0x004e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getCpuName() {
        /*
            r6 = this;
            r0 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ Throwable -> 0x0046, all -> 0x0037 }
            java.lang.String r2 = "/proc/cpuinfo"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0046, all -> 0x0037 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0047, all -> 0x0032 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0047, all -> 0x0032 }
            java.lang.String r0 = r2.readLine()     // Catch:{ Throwable -> 0x0030, all -> 0x002e }
            java.lang.String r3 = ":\\s+"
            r4 = 2
            java.lang.String[] r0 = r0.split(r3, r4)     // Catch:{ Throwable -> 0x0030, all -> 0x002e }
            if (r0 == 0) goto L_0x0027
            int r3 = r0.length     // Catch:{ Throwable -> 0x0030, all -> 0x002e }
            r4 = 1
            if (r3 <= r4) goto L_0x0027
            r0 = r0[r4]     // Catch:{ Throwable -> 0x0030, all -> 0x002e }
            r1.close()     // Catch:{ Throwable -> 0x0023 }
        L_0x0023:
            r2.close()     // Catch:{ Throwable -> 0x0026 }
        L_0x0026:
            return r0
        L_0x0027:
            r1.close()     // Catch:{ Throwable -> 0x002a }
        L_0x002a:
            r2.close()     // Catch:{ Throwable -> 0x0051 }
            goto L_0x0051
        L_0x002e:
            r0 = move-exception
            goto L_0x003b
        L_0x0030:
            r0 = r2
            goto L_0x0047
        L_0x0032:
            r2 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x003b
        L_0x0037:
            r1 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
        L_0x003b:
            if (r1 == 0) goto L_0x0040
            r1.close()     // Catch:{ Throwable -> 0x0040 }
        L_0x0040:
            if (r2 == 0) goto L_0x0045
            r2.close()     // Catch:{ Throwable -> 0x0045 }
        L_0x0045:
            throw r0
        L_0x0046:
            r1 = r0
        L_0x0047:
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ Throwable -> 0x004c }
        L_0x004c:
            if (r0 == 0) goto L_0x0051
            r0.close()     // Catch:{ Throwable -> 0x0051 }
        L_0x0051:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getCpuName():java.lang.String");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:5|6|(2:8|9)|10|11|12|13) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0028 */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003b A[SYNTHETIC, Splitter:B:22:0x003b] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0040 A[SYNTHETIC, Splitter:B:26:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0047 A[SYNTHETIC, Splitter:B:34:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x004c A[SYNTHETIC, Splitter:B:38:0x004c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getMemorySize() {
        /*
            r8 = this;
            java.lang.String r0 = "/proc/meminfo"
            r1 = 0
            r2 = 0
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x0044, all -> 0x0035 }
            r4.<init>(r0)     // Catch:{ Throwable -> 0x0044, all -> 0x0035 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0045, all -> 0x0030 }
            r5 = 8192(0x2000, float:1.14794E-41)
            r0.<init>(r4, r5)     // Catch:{ Throwable -> 0x0045, all -> 0x0030 }
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            if (r1 == 0) goto L_0x0025
            java.lang.String r5 = "\\s+"
            java.lang.String[] r1 = r1.split(r5)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            r5 = 1
            r1 = r1[r5]     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            long r5 = java.lang.Long.parseLong(r1)     // Catch:{ Throwable -> 0x002e, all -> 0x002c }
            r2 = r5
        L_0x0025:
            r4.close()     // Catch:{ Throwable -> 0x0028 }
        L_0x0028:
            r0.close()     // Catch:{ Throwable -> 0x004f }
            goto L_0x004f
        L_0x002c:
            r1 = move-exception
            goto L_0x0039
        L_0x002e:
            r1 = r0
            goto L_0x0045
        L_0x0030:
            r0 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x0039
        L_0x0035:
            r0 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
        L_0x0039:
            if (r4 == 0) goto L_0x003e
            r4.close()     // Catch:{ Throwable -> 0x003e }
        L_0x003e:
            if (r0 == 0) goto L_0x0043
            r0.close()     // Catch:{ Throwable -> 0x0043 }
        L_0x0043:
            throw r1
        L_0x0044:
            r4 = r1
        L_0x0045:
            if (r4 == 0) goto L_0x004a
            r4.close()     // Catch:{ Throwable -> 0x004a }
        L_0x004a:
            if (r1 == 0) goto L_0x004f
            r1.close()     // Catch:{ Throwable -> 0x004f }
        L_0x004f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getMemorySize():long");
    }

    public long getTotalInternalMemorySize() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable unused) {
            return 0;
        }
    }

    public long getSDCardSize() {
        try {
            if (!"mounted".equals(Environment.getExternalStorageState())) {
                return 0;
            }
            StatFs statFs = new StatFs(CommonUtils.getExternalDirectory().getPath());
            return ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount());
        } catch (Throwable unused) {
            return 0;
        }
    }

    public boolean getBluStatus() {
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && defaultAdapter.isEnabled()) {
                return true;
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public String getBluMac() {
        BluetoothAdapter bluetoothAdapter;
        String str = "";
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter != null) {
                try {
                    if (!bluetoothAdapter.isEnabled()) {
                        return "";
                    }
                } catch (Throwable unused) {
                }
            }
            str = bluetoothAdapter.getAddress();
        } catch (Throwable unused2) {
            bluetoothAdapter = null;
        }
        if (str == null || str.endsWith("00:00:00:00:00")) {
            try {
                str = getBtAddressViaReflection(bluetoothAdapter);
            } catch (Throwable unused3) {
            }
        }
        if (str == null) {
            str = "";
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        if ("02:00:00:00:00:00".equals(r0) == false) goto L_0x001f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getBluMac(android.content.Context r3) {
        /*
            r2 = this;
            java.lang.String r0 = r2.getBluMac()
            if (r0 == 0) goto L_0x0014
            int r1 = r0.length()     // Catch:{ Throwable -> 0x0024 }
            if (r1 == 0) goto L_0x0014
            java.lang.String r1 = "02:00:00:00:00:00"
            boolean r1 = r1.equals(r0)     // Catch:{ Throwable -> 0x0024 }
            if (r1 == 0) goto L_0x001f
        L_0x0014:
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch:{ Throwable -> 0x0024 }
            java.lang.String r1 = "bluetooth_address"
            java.lang.String r3 = android.provider.Settings.Secure.getString(r3, r1)     // Catch:{ Throwable -> 0x0024 }
            r0 = r3
        L_0x001f:
            if (r0 != 0) goto L_0x0024
            java.lang.String r3 = ""
            goto L_0x0025
        L_0x0024:
            r3 = r0
        L_0x0025:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getBluMac(android.content.Context):java.lang.String");
    }

    private String getBtAddressViaReflection(BluetoothAdapter bluetoothAdapter) {
        try {
            Field declaredField = BluetoothAdapter.class.getDeclaredField("mService");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(bluetoothAdapter);
            if (obj == null) {
                return null;
            }
            Method declaredMethod = obj.getClass().getDeclaredMethod("getAddress", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(obj, new Object[0]);
            if (invoke != null && (invoke instanceof String)) {
                return (String) invoke;
            }
            return null;
        } catch (Throwable unused) {
        }
    }

    public String getNetworkType(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return String.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable unused) {
        }
        return "";
    }

    public String getBandVer() {
        String str = "";
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls.newInstance(), new Object[]{"gsm.version.baseband", "no message"});
        } catch (Throwable unused) {
        }
        return str == null ? "" : str;
    }

    public String getWifiBssid(Context context) {
        String str = "";
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager.isWifiEnabled()) {
                str = wifiManager.getConnectionInfo().getBSSID();
            }
        } catch (Throwable unused) {
        }
        return str == null ? "" : str;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0043 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0047 */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004d A[Catch:{ Throwable -> 0x0066 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0068  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getKernelVersion() {
        /*
            r7 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = ""
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x006b }
            java.lang.String r3 = "/proc/version"
            r2.<init>(r3)     // Catch:{ Throwable -> 0x006b }
            r3 = 0
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0043, all -> 0x003a }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0043, all -> 0x003a }
            r5.<init>(r2)     // Catch:{ Throwable -> 0x0043, all -> 0x003a }
            r6 = 8192(0x2000, float:1.14794E-41)
            r4.<init>(r5, r6)     // Catch:{ Throwable -> 0x0043, all -> 0x003a }
        L_0x0018:
            java.lang.String r3 = r4.readLine()     // Catch:{ Throwable -> 0x0038, all -> 0x0036 }
            if (r3 == 0) goto L_0x002f
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0038, all -> 0x0036 }
            r5.<init>()     // Catch:{ Throwable -> 0x0038, all -> 0x0036 }
            r5.append(r1)     // Catch:{ Throwable -> 0x0038, all -> 0x0036 }
            r5.append(r3)     // Catch:{ Throwable -> 0x0038, all -> 0x0036 }
            java.lang.String r3 = r5.toString()     // Catch:{ Throwable -> 0x0038, all -> 0x0036 }
            r1 = r3
            goto L_0x0018
        L_0x002f:
            r4.close()     // Catch:{ Throwable -> 0x0047 }
        L_0x0032:
            r2.close()     // Catch:{ Throwable -> 0x0047 }
            goto L_0x0047
        L_0x0036:
            r0 = move-exception
            goto L_0x003c
        L_0x0038:
            r3 = r4
            goto L_0x0043
        L_0x003a:
            r0 = move-exception
            r4 = r3
        L_0x003c:
            r4.close()     // Catch:{ Throwable -> 0x0042 }
            r2.close()     // Catch:{ Throwable -> 0x0042 }
        L_0x0042:
            throw r0
        L_0x0043:
            r3.close()     // Catch:{ Throwable -> 0x0047 }
            goto L_0x0032
        L_0x0047:
            boolean r2 = com.alipay.security.mobile.module.commonutils.CommonUtils.isNotBlank(r1)     // Catch:{ Throwable -> 0x0066 }
            if (r2 == 0) goto L_0x0066
            java.lang.String r2 = "version "
            int r2 = r1.indexOf(r2)     // Catch:{ Throwable -> 0x0066 }
            int r2 = r2 + 8
            java.lang.String r1 = r1.substring(r2)     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r2 = " "
            int r2 = r1.indexOf(r2)     // Catch:{ Throwable -> 0x0066 }
            r3 = 0
            java.lang.String r1 = r1.substring(r3, r2)     // Catch:{ Throwable -> 0x0066 }
            r0 = r1
        L_0x0066:
            if (r0 != 0) goto L_0x006a
            java.lang.String r0 = ""
        L_0x006a:
            return r0
        L_0x006b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getKernelVersion():java.lang.String");
    }

    public List<String> getAllAppName(Context context) {
        ArrayList arrayList = new ArrayList();
        if (!"ALIPAY_INSIDE_HUAWEI_EMUI".equals(StaticConfig.d())) {
            try {
                List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
                if (installedPackages != null && installedPackages.size() > 0) {
                    for (PackageInfo packageInfo : installedPackages) {
                        arrayList.add(packageInfo.packageName);
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return arrayList;
    }

    public List<String> getAllAppName(Context context, boolean z) {
        ArrayList arrayList = new ArrayList();
        if (!"ALIPAY_INSIDE_HUAWEI_EMUI".equals(StaticConfig.d())) {
            try {
                List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
                if (installedPackages != null && installedPackages.size() > 0) {
                    for (PackageInfo next : installedPackages) {
                        if (z || !isSystemApp(next)) {
                            arrayList.add(next.packageName);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return arrayList;
    }

    private boolean isSystemApp(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & 1) == 0 && (packageInfo.applicationInfo.flags & 128) == 0) ? false : true;
    }

    public String getSerialNumber() {
        String str;
        try {
            if (VERSION.SDK_INT < 26) {
                str = Build.SERIAL;
            } else {
                str = Build.getSerial();
            }
        } catch (Throwable unused) {
            str = "";
        }
        return str == null ? "" : str;
    }

    public String getLanguage() {
        String str = "";
        try {
            str = Locale.getDefault().toString();
        } catch (Throwable unused) {
        }
        return str == null ? "" : str;
    }

    public String getHostName() {
        String str = "";
        try {
            str = InetAddress.getLocalHost().getHostName();
        } catch (Throwable unused) {
        }
        return str == null ? "" : str;
    }

    public String getTimeZone() {
        try {
            TimeZone timeZone = TimeZone.getDefault();
            StringBuilder sb = new StringBuilder();
            sb.append(timeZone.getDisplayName(false, 0));
            sb.append(Token.SEPARATOR);
            sb.append(timeZone.getID());
            String sb2 = sb.toString();
            return sb2 == null ? "" : sb2;
        } catch (Throwable unused) {
            return "";
        }
    }

    public String getNetworkTypeAndIpAddress(Context context) {
        try {
            String currentNetType = getCurrentNetType(context);
            String phoneIp = getPhoneIp();
            if (!CommonUtils.isNotBlank(currentNetType) || !CommonUtils.isNotBlank(phoneIp)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(currentNetType);
            sb.append(":");
            sb.append(getPhoneIp());
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public long getSystemBootTime() {
        try {
            if (mMachineBootTime == 0) {
                mMachineBootTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            }
        } catch (Throwable unused) {
        }
        return mMachineBootTime;
    }

    public byte[] getSystemLockScreenStatus(Context context) {
        boolean isKeyguardSecure = ((KeyguardManager) context.getSystemService("keyguard")).isKeyguardSecure();
        ByteBuffer allocate = ByteBuffer.allocate(9);
        allocate.order(ByteOrder.BIG_ENDIAN);
        long j = 0;
        if (!isKeyguardSecure) {
            allocate.put(0);
            allocate.putLong(0);
            return allocate.array();
        }
        String[] strArr = {"/data/system/password.key", "/data/system/gesture.key", "/data/system/gatekeeper.password.key", "/data/system/gatekeeper.gesture.key", "/data/system/gatekeeper.pattern.key"};
        for (int i = 0; i < 5; i++) {
            long j2 = -1;
            try {
                j2 = new File(strArr[i]).lastModified();
            } catch (Throwable unused) {
            }
            j = Math.max(j2, j);
        }
        allocate.put(1);
        allocate.putLong(j);
        return allocate.array();
    }

    public byte[] getBatteryLevelAndStatus(Context context) {
        byte b;
        try {
            Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = registerReceiver.getIntExtra(H5PermissionManager.level, -1);
            int intExtra2 = registerReceiver.getIntExtra("status", -1);
            if (intExtra2 != 2) {
                if (intExtra2 != 5) {
                    b = 0;
                    ByteBuffer allocate = ByteBuffer.allocate(5);
                    allocate.order(ByteOrder.BIG_ENDIAN);
                    allocate.put(b);
                    allocate.putInt(intExtra);
                    return allocate.array();
                }
            }
            b = 1;
            ByteBuffer allocate2 = ByteBuffer.allocate(5);
            allocate2.order(ByteOrder.BIG_ENDIAN);
            allocate2.put(b);
            allocate2.putInt(intExtra);
            return allocate2.array();
        } catch (Throwable unused) {
            return null;
        }
    }

    public long getSystemBootTimeLength() {
        try {
            return SystemClock.elapsedRealtime();
        } catch (Throwable unused) {
            return 0;
        }
    }

    public byte[] getEmulatorFilesExistFeature(Context context) {
        String str;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getFilesDir());
            sb.append("/sc_edge/DATA0.db");
            File file = new File(sb.toString());
            str = file.getAbsolutePath();
            try {
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb2 = new StringBuilder("getSystemLockScreenStatus() edge database exists: ");
                sb2.append(file.exists());
                sb2.append(", file path = ");
                sb2.append(str);
                f.b((String) "t0dbg", sb2.toString());
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            str = "";
        }
        try {
            StringBuilder sb3 = new StringBuilder();
            String[] strArr = {"/dev/qemu_pipe", "/dev/socket/qemud", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/genyd", "/dev/socket/baseband_genyd", str};
            StringBuilder sb4 = new StringBuilder();
            sb4.append("01");
            sb4.append(":");
            sb3.append(sb4.toString());
            for (int i = 0; i < 8; i++) {
                if (new File(strArr[i]).exists()) {
                    sb3.append("1");
                } else {
                    sb3.append("0");
                }
            }
            return CommonUtils.convertBooleanStringToBitset(sb3.toString());
        } catch (Throwable unused3) {
            return null;
        }
    }

    public byte[] getEmulatorSdkClassFeature() {
        String[] strArr = {"dalvik.system.Taint"};
        StringBuilder sb = new StringBuilder();
        sb.append("00");
        sb.append(":");
        for (int i = 0; i <= 0; i++) {
            try {
                Class.forName(strArr[0]);
                sb.append("1");
            } catch (Throwable unused) {
                sb.append("0");
            }
        }
        return CommonUtils.convertBooleanStringToBitset(sb.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0086 A[SYNTHETIC, Splitter:B:23:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x008f A[SYNTHETIC, Splitter:B:30:0x008f] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x003e A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getEmulatorFilesContentFeature() {
        /*
            r9 = this;
            java.lang.String r0 = "00"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>()
            java.lang.String r3 = "/system/build.prop"
            java.lang.String r4 = "ro.product.name=sdk"
            r2.put(r3, r4)
            java.lang.String r3 = "/proc/tty/drivers"
            java.lang.String r4 = "goldfish"
            r2.put(r3, r4)
            java.lang.String r3 = "/proc/cpuinfo"
            java.lang.String r4 = "goldfish"
            r2.put(r3, r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            java.lang.String r0 = ":"
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            r1.append(r0)
            java.util.Set r0 = r2.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x003e:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0093
            java.lang.Object r3 = r0.next()
            java.lang.String r3 = (java.lang.String) r3
            r4 = 0
            r5 = 48
            java.io.LineNumberReader r6 = new java.io.LineNumberReader     // Catch:{ Throwable -> 0x008a, all -> 0x0080 }
            java.io.InputStreamReader r7 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x008a, all -> 0x0080 }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x008a, all -> 0x0080 }
            r8.<init>(r3)     // Catch:{ Throwable -> 0x008a, all -> 0x0080 }
            r7.<init>(r8)     // Catch:{ Throwable -> 0x008a, all -> 0x0080 }
            r6.<init>(r7)     // Catch:{ Throwable -> 0x008a, all -> 0x0080 }
        L_0x005c:
            java.lang.String r4 = r6.readLine()     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            if (r4 == 0) goto L_0x0074
            java.lang.String r4 = r4.toLowerCase()     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            java.lang.Object r7 = r2.get(r3)     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            boolean r4 = r4.contains(r7)     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            if (r4 == 0) goto L_0x005c
            r5 = 49
        L_0x0074:
            r1.append(r5)
            r6.close()     // Catch:{ Throwable -> 0x003e }
            goto L_0x003e
        L_0x007b:
            r0 = move-exception
            r4 = r6
            goto L_0x0081
        L_0x007e:
            r4 = r6
            goto L_0x008a
        L_0x0080:
            r0 = move-exception
        L_0x0081:
            r1.append(r5)
            if (r4 == 0) goto L_0x0089
            r4.close()     // Catch:{ Throwable -> 0x0089 }
        L_0x0089:
            throw r0
        L_0x008a:
            r1.append(r5)
            if (r4 == 0) goto L_0x003e
            r4.close()     // Catch:{ Throwable -> 0x003e }
            goto L_0x003e
        L_0x0093:
            java.lang.String r0 = r1.toString()
            byte[] r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.convertBooleanStringToBitset(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getEmulatorFilesContentFeature():byte[]");
    }

    public byte[] getEmulatorBuildClassFeature() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("00");
        sb2.append(":");
        sb.append(sb2.toString());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("BRAND", "generic");
        linkedHashMap.put("BOARD", "unknown");
        linkedHashMap.put("DEVICE", "generic");
        linkedHashMap.put("HARDWARE", "goldfish");
        linkedHashMap.put("PRODUCT", GlobalConstants.EXCEPTIONTYPE);
        linkedHashMap.put("MODEL", GlobalConstants.EXCEPTIONTYPE);
        for (String str : linkedHashMap.keySet()) {
            char c = '0';
            try {
                String str2 = null;
                String str3 = (String) Build.class.getField(str).get(null);
                String str4 = (String) linkedHashMap.get(str);
                if (str3 != null) {
                    str2 = str3.toLowerCase();
                }
                if (str2 != null && str2.contains(str4)) {
                    c = '1';
                }
            } catch (Throwable th) {
                sb.append('0');
                throw th;
            }
            sb.append(c);
        }
        return CommonUtils.convertBooleanStringToBitset(sb.toString());
    }

    public byte[] getEmulatorSystemPropertiesFeature() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("00");
        sb2.append(":");
        sb.append(sb2.toString());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("ro.hardware", "goldfish");
        linkedHashMap.put("ro.kernel.qemu", "1");
        linkedHashMap.put("ro.product.device", "generic");
        linkedHashMap.put("ro.product.model", GlobalConstants.EXCEPTIONTYPE);
        linkedHashMap.put("ro.product.brand", "generic");
        linkedHashMap.put("ro.product.name", GlobalConstants.EXCEPTIONTYPE);
        linkedHashMap.put("ro.build.fingerprint", "test-keys");
        linkedHashMap.put("ro.product.manufacturer", "unknow");
        for (String str : linkedHashMap.keySet()) {
            char c = '0';
            String str2 = (String) linkedHashMap.get(str);
            String systemProperties = CommonUtils.getSystemProperties(str, "");
            if (systemProperties != null && systemProperties.contains(str2)) {
                c = '1';
            }
            sb.append(c);
        }
        return CommonUtils.convertBooleanStringToBitset(sb.toString());
    }

    private Map<String, String> getSecGuardWua(Context context) {
        String str;
        if (this.securityGuardWuaMap == null && CommonUtils.isAlipayWallet(context)) {
            long currentTimeMillis = System.currentTimeMillis();
            HashMap hashMap = new HashMap();
            try {
                str = SecurityGuardManager.getInstance(context.getApplicationContext()).getSecurityBodyComp().getSecurityBodyDataEx(String.valueOf(currentTimeMillis), "23067836", 80);
            } catch (Throwable unused) {
                str = "";
            }
            if (str != null) {
                try {
                    if (str.length() > 0) {
                        hashMap.put("appKey", "23067836");
                        hashMap.put("version", "2.0");
                        hashMap.put(LogItem.MM_C15_K4_TIME, String.valueOf(currentTimeMillis));
                        if (str == null) {
                            str = "";
                        }
                        hashMap.put("wua", str);
                        this.securityGuardWuaMap = hashMap;
                    }
                } catch (Throwable unused2) {
                }
            }
        }
        if (this.securityGuardWuaMap != null) {
            return this.securityGuardWuaMap;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("appKey", "");
        hashMap2.put("version", "");
        hashMap2.put(LogItem.MM_C15_K4_TIME, "");
        hashMap2.put("wua", "");
        return hashMap2;
    }

    public String getSecGuardWuaForDeviceID(Context context) {
        Map<String, String> secGuardWua = getSecGuardWua(context);
        if (secGuardWua != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("appKey", secGuardWua.get("appKey"));
                jSONObject.put("version", secGuardWua.get("version"));
                jSONObject.put(LogItem.MM_C15_K4_TIME, secGuardWua.get(LogItem.MM_C15_K4_TIME));
                jSONObject.put("wua", secGuardWua.get("wua"));
                return jSONObject.toString();
            } catch (Throwable unused) {
            }
        }
        r5 = "";
        return "";
    }

    public Map<String, String> getSecGuardWuaForRDS(Context context) {
        return getSecGuardWua(context);
    }

    public static boolean isAllowMockLocation(Context context) {
        try {
            boolean z = Secure.getInt(context.getContentResolver(), "mock_location", 0) != 0;
            if (z && VERSION.SDK_INT > 22) {
                z = false;
            }
            return z;
        } catch (Throwable unused) {
            return false;
        }
    }
}
