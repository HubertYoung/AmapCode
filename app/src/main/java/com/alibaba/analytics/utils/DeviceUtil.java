package com.alibaba.analytics.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.core.network.NetworkUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

public class DeviceUtil {
    private static Map<String, String> s_deviceInfoMap;

    /* JADX WARNING: Can't wrap try/catch for region: R(37:9|10|11|12|13|14|15|16|17|18|(6:20|(1:22)|23|(1:25)|26|(1:28))|29|(1:31)|32|33|(1:35)(1:36)|37|(1:39)|40|43|44|(1:46)(1:47)|50|51|52|(1:56)|57|(1:59)|60|64|65|66|(2:68|(1:70))|71|72|73|74) */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r1.put(com.alibaba.analytics.core.model.LogField.RESOLUTION.toString(), "Unknown");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        r1.put(com.alibaba.analytics.core.model.LogField.ACCESS.toString(), "Unknown");
        r1.put(com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE.toString(), "Unknown");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01be, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        r2.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01de, code lost:
        return r1;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x007e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x0142 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:48:0x017f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:71:0x01d9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.util.Map<java.lang.String, java.lang.String> getDeviceInfo(android.content.Context r7) {
        /*
            java.lang.Class<com.alibaba.analytics.utils.DeviceUtil> r0 = com.alibaba.analytics.utils.DeviceUtil.class
            monitor-enter(r0)
            java.util.Map<java.lang.String, java.lang.String> r1 = s_deviceInfoMap     // Catch:{ all -> 0x01e2 }
            if (r1 == 0) goto L_0x000b
            java.util.Map<java.lang.String, java.lang.String> r7 = s_deviceInfoMap     // Catch:{ all -> 0x01e2 }
            monitor-exit(r0)
            return r7
        L_0x000b:
            if (r7 == 0) goto L_0x01df
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x01e2 }
            r1.<init>()     // Catch:{ all -> 0x01e2 }
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.UTDID     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = com.ut.device.UTDevice.getUtdid(r7)     // Catch:{ Throwable -> 0x01dd }
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01dd }
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.IMEI     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = com.alibaba.analytics.utils.PhoneInfoUtils.getImei(r7)     // Catch:{ Throwable -> 0x01dd }
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01dd }
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.IMSI     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = com.alibaba.analytics.utils.PhoneInfoUtils.getImsi(r7)     // Catch:{ Throwable -> 0x01dd }
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01dd }
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.DEVICE_MODEL     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = android.os.Build.MODEL     // Catch:{ Throwable -> 0x01dd }
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01dd }
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.BRAND     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x01dd }
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01dd }
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.OSVERSION     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = android.os.Build.VERSION.RELEASE     // Catch:{ Throwable -> 0x01dd }
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01dd }
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.OS     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = "a"
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01dd }
            r2 = 0
            android.content.pm.PackageManager r3 = r7.getPackageManager()     // Catch:{ NameNotFoundException -> 0x007e }
            java.lang.String r4 = r7.getPackageName()     // Catch:{ NameNotFoundException -> 0x007e }
            android.content.pm.PackageInfo r3 = r3.getPackageInfo(r4, r2)     // Catch:{ NameNotFoundException -> 0x007e }
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.APPVERSION     // Catch:{ NameNotFoundException -> 0x007e }
            java.lang.String r4 = r4.toString()     // Catch:{ NameNotFoundException -> 0x007e }
            java.lang.String r3 = r3.versionName     // Catch:{ NameNotFoundException -> 0x007e }
            r1.put(r4, r3)     // Catch:{ NameNotFoundException -> 0x007e }
            goto L_0x0089
        L_0x007e:
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.APPVERSION     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r4 = "Unknown"
            r1.put(r3, r4)     // Catch:{ Throwable -> 0x01dd }
        L_0x0089:
            boolean r3 = isYunOSSystem()     // Catch:{ Throwable -> 0x01dd }
            if (r3 == 0) goto L_0x00d7
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.OS     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r4 = "y"
            r1.put(r3, r4)     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = getUUID()     // Catch:{ Throwable -> 0x01dd }
            boolean r4 = com.alibaba.analytics.utils.StringUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x01dd }
            if (r4 != 0) goto L_0x00ae
            com.alibaba.analytics.core.model.UTMCLogFields r4 = com.alibaba.analytics.core.model.UTMCLogFields.DEVICE_ID     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x01dd }
            r1.put(r4, r3)     // Catch:{ Throwable -> 0x01dd }
        L_0x00ae:
            java.lang.String r3 = "ro.yunos.version"
            java.lang.String r3 = java.lang.System.getProperty(r3)     // Catch:{ Throwable -> 0x01dd }
            boolean r4 = com.alibaba.analytics.utils.StringUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x01dd }
            if (r4 != 0) goto L_0x00c4
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.OSVERSION     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x01dd }
            r1.put(r4, r3)     // Catch:{ Throwable -> 0x01dd }
        L_0x00c4:
            java.lang.String r3 = getBuildVersion()     // Catch:{ Throwable -> 0x01dd }
            boolean r4 = com.alibaba.analytics.utils.StringUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x01dd }
            if (r4 != 0) goto L_0x00d7
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.OSVERSION     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x01dd }
            r1.put(r4, r3)     // Catch:{ Throwable -> 0x01dd }
        L_0x00d7:
            boolean r3 = isYunOSTvSystem()     // Catch:{ Throwable -> 0x01dd }
            if (r3 == 0) goto L_0x00e8
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.OS     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r4 = "a"
            r1.put(r3, r4)     // Catch:{ Throwable -> 0x01dd }
        L_0x00e8:
            android.content.res.Configuration r3 = new android.content.res.Configuration     // Catch:{ Exception -> 0x0142 }
            r3.<init>()     // Catch:{ Exception -> 0x0142 }
            android.content.ContentResolver r4 = r7.getContentResolver()     // Catch:{ Exception -> 0x0142 }
            android.provider.Settings.System.getConfiguration(r4, r3)     // Catch:{ Exception -> 0x0142 }
            java.util.Locale r4 = r3.locale     // Catch:{ Exception -> 0x0142 }
            if (r4 == 0) goto L_0x0108
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.LANGUAGE     // Catch:{ Exception -> 0x0142 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0142 }
            java.util.Locale r3 = r3.locale     // Catch:{ Exception -> 0x0142 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0142 }
            r1.put(r4, r3)     // Catch:{ Exception -> 0x0142 }
            goto L_0x0113
        L_0x0108:
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.LANGUAGE     // Catch:{ Exception -> 0x0142 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0142 }
            java.lang.String r4 = "Unknown"
            r1.put(r3, r4)     // Catch:{ Exception -> 0x0142 }
        L_0x0113:
            android.content.res.Resources r3 = r7.getResources()     // Catch:{ Exception -> 0x0142 }
            android.util.DisplayMetrics r3 = r3.getDisplayMetrics()     // Catch:{ Exception -> 0x0142 }
            int r4 = r3.widthPixels     // Catch:{ Exception -> 0x0142 }
            int r3 = r3.heightPixels     // Catch:{ Exception -> 0x0142 }
            if (r4 <= r3) goto L_0x0124
            r4 = r4 ^ r3
            r3 = r3 ^ r4
            r4 = r4 ^ r3
        L_0x0124:
            com.alibaba.analytics.core.model.LogField r5 = com.alibaba.analytics.core.model.LogField.RESOLUTION     // Catch:{ Exception -> 0x0142 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0142 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0142 }
            r6.<init>()     // Catch:{ Exception -> 0x0142 }
            r6.append(r3)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r3 = "*"
            r6.append(r3)     // Catch:{ Exception -> 0x0142 }
            r6.append(r4)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r3 = r6.toString()     // Catch:{ Exception -> 0x0142 }
            r1.put(r5, r3)     // Catch:{ Exception -> 0x0142 }
            goto L_0x014d
        L_0x0142:
            com.alibaba.analytics.core.model.LogField r3 = com.alibaba.analytics.core.model.LogField.RESOLUTION     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r4 = "Unknown"
            r1.put(r3, r4)     // Catch:{ Throwable -> 0x01dd }
        L_0x014d:
            java.lang.String[] r3 = com.alibaba.analytics.core.network.NetworkUtil.getNetworkState(r7)     // Catch:{ Exception -> 0x017f }
            com.alibaba.analytics.core.model.LogField r4 = com.alibaba.analytics.core.model.LogField.ACCESS     // Catch:{ Exception -> 0x017f }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x017f }
            r5 = r3[r2]     // Catch:{ Exception -> 0x017f }
            r1.put(r4, r5)     // Catch:{ Exception -> 0x017f }
            r2 = r3[r2]     // Catch:{ Exception -> 0x017f }
            java.lang.String r4 = "2G/3G"
            boolean r2 = r2.equals(r4)     // Catch:{ Exception -> 0x017f }
            if (r2 == 0) goto L_0x0173
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE     // Catch:{ Exception -> 0x017f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x017f }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x017f }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x017f }
            goto L_0x0195
        L_0x0173:
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE     // Catch:{ Exception -> 0x017f }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x017f }
            java.lang.String r3 = "Unknown"
            r1.put(r2, r3)     // Catch:{ Exception -> 0x017f }
            goto L_0x0195
        L_0x017f:
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.ACCESS     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = "Unknown"
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01dd }
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.ACCESS_SUBTYPE     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01dd }
            java.lang.String r3 = "Unknown"
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x01dd }
        L_0x0195:
            java.lang.String r2 = "phone"
            java.lang.Object r2 = r7.getSystemService(r2)     // Catch:{ Exception -> 0x01be }
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch:{ Exception -> 0x01be }
            java.lang.String r3 = ""
            if (r2 == 0) goto L_0x01ac
            int r4 = r2.getSimState()     // Catch:{ Exception -> 0x01be }
            r5 = 5
            if (r4 != r5) goto L_0x01ac
            java.lang.String r3 = r2.getNetworkOperatorName()     // Catch:{ Exception -> 0x01be }
        L_0x01ac:
            boolean r2 = com.alibaba.analytics.utils.StringUtils.isEmpty(r3)     // Catch:{ Exception -> 0x01be }
            if (r2 == 0) goto L_0x01b4
            java.lang.String r3 = "Unknown"
        L_0x01b4:
            com.alibaba.analytics.core.model.LogField r2 = com.alibaba.analytics.core.model.LogField.CARRIER     // Catch:{ Exception -> 0x01be }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x01be }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x01be }
            goto L_0x01c2
        L_0x01be:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ Throwable -> 0x01dd }
        L_0x01c2:
            java.lang.String r2 = "android.permission.ACCESS_WIFI_STATE"
            int r2 = r7.checkCallingOrSelfPermission(r2)     // Catch:{ Exception -> 0x01d9 }
            if (r2 != 0) goto L_0x01d9
            java.lang.String r7 = com.alibaba.analytics.core.network.NetworkUtil.getWifiAddress(r7)     // Catch:{ Exception -> 0x01d9 }
            boolean r2 = com.alibaba.analytics.utils.StringUtils.isEmpty(r7)     // Catch:{ Exception -> 0x01d9 }
            if (r2 != 0) goto L_0x01d9
            java.lang.String r2 = "_mac"
            r1.put(r2, r7)     // Catch:{ Exception -> 0x01d9 }
        L_0x01d9:
            s_deviceInfoMap = r1     // Catch:{ all -> 0x01e2 }
            monitor-exit(r0)
            return r1
        L_0x01dd:
            monitor-exit(r0)
            return r1
        L_0x01df:
            r7 = 0
            monitor-exit(r0)
            return r7
        L_0x01e2:
            r7 = move-exception
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.DeviceUtil.getDeviceInfo(android.content.Context):java.util.Map");
    }

    @Deprecated
    private static String getOsVersion() {
        String str = VERSION.RELEASE;
        if (isYunOSSystem()) {
            System.getProperty("ro.yunos.version");
            str = getBuildVersion();
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return str;
    }

    @Deprecated
    private static String getOs() {
        return (!isYunOSSystem() || isYunOSTvSystem()) ? "a" : DictionaryKeys.CTRLXY_Y;
    }

    @Deprecated
    private static void refreshNetworkStatus(Map<String, String> map, Context context) {
        try {
            String[] networkState = NetworkUtil.getNetworkState(context);
            map.put(LogField.ACCESS.toString(), networkState[0]);
            if (networkState[0].equals("2G/3G")) {
                map.put(LogField.ACCESS_SUBTYPE.toString(), networkState[1]);
            } else {
                map.put(LogField.ACCESS_SUBTYPE.toString(), "Unknown");
            }
        } catch (Exception unused) {
            map.put(LogField.ACCESS.toString(), "Unknown");
            map.put(LogField.ACCESS_SUBTYPE.toString(), "Unknown");
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            String str = "";
            if (telephonyManager != null && telephonyManager.getSimState() == 5) {
                str = telephonyManager.getNetworkOperatorName();
            }
            if (TextUtils.isEmpty(str)) {
                str = "Unknown";
            }
            map.put(LogField.CARRIER.toString(), str);
        } catch (Exception unused2) {
        }
    }

    @Deprecated
    private static String getLanguage(Context context) {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Throwable unused) {
            return "Unknown";
        }
    }

    @Deprecated
    private static String getResolution(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            if (i > i2) {
                int i3 = i ^ i2;
                i2 ^= i3;
                i = i3 ^ i2;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append("*");
            sb.append(i);
            return sb.toString();
        } catch (Exception unused) {
            r3 = "Unknown";
            return "Unknown";
        }
    }

    @Deprecated
    public static String getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo == null) {
                return "Unknown";
            }
            s_deviceInfoMap.put(LogField.APPVERSION.toString(), packageInfo.versionName);
            return packageInfo.versionName;
        } catch (Throwable unused) {
            return "Unknown";
        }
    }

    public static boolean isYunOSSystem() {
        if ((System.getProperty("java.vm.name") == null || !System.getProperty("java.vm.name").toLowerCase().contains("lemur")) && System.getProperty("ro.yunos.version") == null && TextUtils.isEmpty(SystemProperties.get("ro.yunos.build.version"))) {
            return isYunOSTvSystem();
        }
        return true;
    }

    private static boolean isYunOSTvSystem() {
        return !TextUtils.isEmpty(SystemProperties.get("ro.yunos.product.chip")) || !TextUtils.isEmpty(SystemProperties.get("ro.yunos.hardware"));
    }

    public static String getUUID() {
        String str = SystemProperties.get("ro.aliyun.clouduuid", "");
        if (TextUtils.isEmpty(str)) {
            str = SystemProperties.get("ro.sys.aliyun.clouduuid", "");
        }
        return TextUtils.isEmpty(str) ? getYunOsTvUuid() : str;
    }

    private static String getYunOsTvUuid() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String getBuildVersion() {
        try {
            Field declaredField = Build.class.getDeclaredField("YUNOS_BUILD_VERSION");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                return (String) declaredField.get(new String());
            }
        } catch (Exception unused) {
        }
        return null;
    }
}
