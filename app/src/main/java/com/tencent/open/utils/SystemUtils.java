package com.tencent.open.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;

/* compiled from: ProGuard */
public class SystemUtils {
    public static final String ACTION_LOGIN = "action_login";
    public static final String ACTION_REQUEST_API = "action_request";
    public static final String ACTION_SHARE = "action_share";
    public static final String H5_SHARE_DATA = "h5_share_data";
    public static final String IS_LOGIN = "is_login";
    public static final String IS_QQ_MOBILE_SHARE = "is_qq_mobile_share";
    public static final String QQDATALINE_CALLBACK_ACTION = "sendToMyComputer";
    public static final String QQFAVORITES_CALLBACK_ACTION = "addToQQFavorites";
    public static final String QQ_SHARE_CALLBACK_ACTION = "shareToQQ";
    public static final String QQ_VERSION_NAME_4_2_0 = "4.2.0";
    public static final String QQ_VERSION_NAME_4_3_0 = "4.3.0";
    public static final String QQ_VERSION_NAME_4_5_0 = "4.5.0";
    public static final String QQ_VERSION_NAME_4_6_0 = "4.6.0";
    public static final String QQ_VERSION_NAME_5_0_0 = "5.0.0";
    public static final String QQ_VERSION_NAME_5_1_0 = "5.1.0";
    public static final String QQ_VERSION_NAME_5_2_0 = "5.2.0";
    public static final String QQ_VERSION_NAME_5_3_0 = "5.3.0";
    public static final String QQ_VERSION_NAME_5_9_5 = "5.9.5";
    public static final String QZONE_SHARE_CALLBACK_ACTION = "shareToQzone";
    public static final String TROOPBAR_CALLBACK_ACTION = "shareToTroopBar";

    public static String getActionFromRequestcode(int i) {
        if (i == 10103) {
            return QQ_SHARE_CALLBACK_ACTION;
        }
        if (i == 10104) {
            return QZONE_SHARE_CALLBACK_ACTION;
        }
        if (i == 10105) {
            return QQFAVORITES_CALLBACK_ACTION;
        }
        if (i == 10106) {
            return QQDATALINE_CALLBACK_ACTION;
        }
        if (i == 10107) {
            return TROOPBAR_CALLBACK_ACTION;
        }
        if (i == 11101) {
            return ACTION_LOGIN;
        }
        if (i == 10100) {
            return ACTION_REQUEST_API;
        }
        return null;
    }

    public static String getAppVersionName(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    public static int compareVersion(String str, String str2) {
        if (str == null && str2 == null) {
            return 0;
        }
        if (str != null && str2 == null) {
            return 1;
        }
        if (str == null && str2 != null) {
            return -1;
        }
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int i = 0;
        while (i < split.length && i < split2.length) {
            try {
                int parseInt = Integer.parseInt(split[i]);
                int parseInt2 = Integer.parseInt(split2[i]);
                if (parseInt < parseInt2) {
                    return -1;
                }
                if (parseInt > parseInt2) {
                    return 1;
                }
                i++;
            } catch (NumberFormatException unused) {
                return str.compareTo(str2);
            }
        }
        if (split.length > i) {
            return 1;
        }
        return split2.length > i ? -1 : 0;
    }

    public static boolean isAppSignatureValid(Context context, String str, String str2) {
        f.a("openSDK_LOG.SystemUtils", "OpenUi, validateAppSignatureForPackage");
        try {
            for (Signature charsString : context.getPackageManager().getPackageInfo(str, 64).signatures) {
                if (Util.encrypt(charsString.toCharsString()).equals(str2)) {
                    return true;
                }
            }
            return false;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    public static String getAppSignatureMD5(Context context, String str) {
        String str2;
        f.a("openSDK_LOG.SystemUtils", "OpenUi, getSignValidString");
        try {
            String packageName = context.getPackageName();
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(packageName, 64).signatures;
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(signatureArr[0].toByteArray());
            String hexString = Util.toHexString(instance.digest());
            instance.reset();
            f.a("openSDK_LOG.SystemUtils", "-->sign: ".concat(String.valueOf(hexString)));
            StringBuilder sb = new StringBuilder();
            sb.append(packageName);
            sb.append("_");
            sb.append(hexString);
            sb.append("_");
            sb.append(str);
            instance.update(Util.getBytesUTF8(sb.toString()));
            str2 = Util.toHexString(instance.digest());
            try {
                instance.reset();
                f.a("openSDK_LOG.SystemUtils", "-->signEncryped: ".concat(String.valueOf(str2)));
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            str2 = "";
            e.printStackTrace();
            f.b("openSDK_LOG.SystemUtils", "OpenUi, getSignValidString error", e);
            return str2;
        }
        return str2;
    }

    public static boolean isActivityExist(Context context, Intent intent) {
        if (context == null || intent == null || context.getPackageManager().queryIntentActivities(intent, 0).size() == 0) {
            return false;
        }
        return true;
    }

    public static String getRealPathFromUri(Activity activity, Uri uri) {
        Cursor managedQuery = activity.managedQuery(uri, new String[]{"_data"}, null, null, null);
        if (managedQuery == null) {
            return null;
        }
        int columnIndexOrThrow = managedQuery.getColumnIndexOrThrow("_data");
        managedQuery.moveToFirst();
        return managedQuery.getString(columnIndexOrThrow);
    }

    public static String getAppName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public static int compareQQVersion(Context context, String str) {
        return compareVersion(getAppVersionName(context, "com.tencent.mobileqq"), str);
    }

    public static boolean checkMobileQQ(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.tencent.mobileqq", 0);
        } catch (NameNotFoundException e) {
            f.b("openSDK_LOG.SystemUtils", "checkMobileQQ NameNotFoundException", e);
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        }
        String str = packageInfo.versionName;
        try {
            f.b("MobileQQ verson", str);
            String[] split = str.split("\\.");
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            if (parseInt > 4 || (parseInt == 4 && parseInt2 > 0)) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            f.b("openSDK_LOG.SystemUtils", "checkMobileQQ Exception", e2);
            e2.printStackTrace();
            return false;
        }
    }

    public static int getAndroidSDKVersion() {
        try {
            return Integer.valueOf(VERSION.SDK).intValue();
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public static boolean isSupportMultiTouch() {
        Method[] declaredMethods;
        boolean z = false;
        boolean z2 = false;
        for (Method method : MotionEvent.class.getDeclaredMethods()) {
            if (method.getName().equals("getPointerCount")) {
                z = true;
            }
            if (method.getName().equals("getPointerId")) {
                z2 = true;
            }
        }
        return getAndroidSDKVersion() >= 7 || (z && z2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ae A[SYNTHETIC, Splitter:B:48:0x00ae] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00b3 A[SYNTHETIC, Splitter:B:52:0x00b3] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00bb A[SYNTHETIC, Splitter:B:59:0x00bb] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00c0 A[SYNTHETIC, Splitter:B:63:0x00c0] */
    @android.annotation.SuppressLint({"SdCardPath"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean extractSecureLib(java.lang.String r8, java.lang.String r9, int r10) {
        /*
            java.lang.String r0 = "openSDK_LOG.SystemUtils"
            java.lang.String r1 = "-->extractSecureLib, libName: "
            java.lang.String r2 = java.lang.String.valueOf(r8)
            java.lang.String r1 = r1.concat(r2)
            com.tencent.open.a.f.c(r0, r1)
            android.content.Context r0 = com.tencent.open.utils.Global.getContext()
            r1 = 0
            if (r0 != 0) goto L_0x001e
            java.lang.String r8 = "openSDK_LOG.SystemUtils"
            java.lang.String r9 = "-->extractSecureLib, global context is null. "
            com.tencent.open.a.f.c(r8, r9)
            return r1
        L_0x001e:
            java.lang.String r2 = "secure_lib"
            android.content.SharedPreferences r2 = r0.getSharedPreferences(r2, r1)
            java.io.File r3 = new java.io.File
            java.io.File r4 = r0.getFilesDir()
            r3.<init>(r4, r9)
            boolean r4 = r3.exists()
            r5 = 1
            if (r4 != 0) goto L_0x0049
            java.io.File r4 = r3.getParentFile()
            if (r4 == 0) goto L_0x006d
            boolean r4 = r4.mkdirs()
            if (r4 == 0) goto L_0x006d
            r3.createNewFile()     // Catch:{ IOException -> 0x0044 }
            goto L_0x006d
        L_0x0044:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x006d
        L_0x0049:
            java.lang.String r3 = "version"
            int r3 = r2.getInt(r3, r1)
            java.lang.String r4 = "openSDK_LOG.SystemUtils"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "-->extractSecureLib, libVersion: "
            r6.<init>(r7)
            r6.append(r10)
            java.lang.String r7 = " | oldVersion: "
            r6.append(r7)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            com.tencent.open.a.f.c(r4, r6)
            if (r10 != r3) goto L_0x006d
            return r5
        L_0x006d:
            r3 = 0
            android.content.res.AssetManager r4 = r0.getAssets()     // Catch:{ Exception -> 0x00a3, all -> 0x009f }
            java.io.InputStream r8 = r4.open(r8)     // Catch:{ Exception -> 0x00a3, all -> 0x009f }
            java.io.FileOutputStream r9 = r0.openFileOutput(r9, r1)     // Catch:{ Exception -> 0x009b, all -> 0x0098 }
            a(r8, r9)     // Catch:{ Exception -> 0x0096, all -> 0x0094 }
            android.content.SharedPreferences$Editor r0 = r2.edit()     // Catch:{ Exception -> 0x0096, all -> 0x0094 }
            java.lang.String r2 = "version"
            r0.putInt(r2, r10)     // Catch:{ Exception -> 0x0096, all -> 0x0094 }
            r0.commit()     // Catch:{ Exception -> 0x0096, all -> 0x0094 }
            if (r8 == 0) goto L_0x008e
            r8.close()     // Catch:{ IOException -> 0x008e }
        L_0x008e:
            if (r9 == 0) goto L_0x0093
            r9.close()     // Catch:{ IOException -> 0x0093 }
        L_0x0093:
            return r5
        L_0x0094:
            r10 = move-exception
            goto L_0x00b9
        L_0x0096:
            r10 = move-exception
            goto L_0x009d
        L_0x0098:
            r10 = move-exception
            r9 = r3
            goto L_0x00b9
        L_0x009b:
            r10 = move-exception
            r9 = r3
        L_0x009d:
            r3 = r8
            goto L_0x00a5
        L_0x009f:
            r10 = move-exception
            r8 = r3
            r9 = r8
            goto L_0x00b9
        L_0x00a3:
            r10 = move-exception
            r9 = r3
        L_0x00a5:
            java.lang.String r8 = "openSDK_LOG.SystemUtils"
            java.lang.String r0 = "-->extractSecureLib, when copy lib execption."
            com.tencent.open.a.f.b(r8, r0, r10)     // Catch:{ all -> 0x00b7 }
            if (r3 == 0) goto L_0x00b1
            r3.close()     // Catch:{ IOException -> 0x00b1 }
        L_0x00b1:
            if (r9 == 0) goto L_0x00b6
            r9.close()     // Catch:{ IOException -> 0x00b6 }
        L_0x00b6:
            return r1
        L_0x00b7:
            r10 = move-exception
            r8 = r3
        L_0x00b9:
            if (r8 == 0) goto L_0x00be
            r8.close()     // Catch:{ IOException -> 0x00be }
        L_0x00be:
            if (r9 == 0) goto L_0x00c3
            r9.close()     // Catch:{ IOException -> 0x00c3 }
        L_0x00c3:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.SystemUtils.extractSecureLib(java.lang.String, java.lang.String, int):boolean");
    }

    @SuppressLint({"SdCardPath"})
    public static boolean isLibExtracted(String str, int i) {
        Context context = Global.getContext();
        if (context == null) {
            f.c("openSDK_LOG.SystemUtils", "-->isSecureLibExtracted, global context is null. ");
            return false;
        }
        File file = new File(context.getFilesDir(), str);
        SharedPreferences sharedPreferences = context.getSharedPreferences("secure_lib", 0);
        if (file.exists()) {
            int i2 = sharedPreferences.getInt("version", 0);
            StringBuilder sb = new StringBuilder("-->extractSecureLib, libVersion: ");
            sb.append(i);
            sb.append(" | oldVersion: ");
            sb.append(i2);
            f.c("openSDK_LOG.SystemUtils", sb.toString());
            if (i == i2) {
                return true;
            }
            Editor edit = sharedPreferences.edit();
            edit.putInt("version", i);
            edit.commit();
        }
        return false;
    }

    private static long a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr, 0, 8192);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
                j += (long) read;
            } else {
                f.c("openSDK_LOG.SystemUtils", "-->copy, copyed size is: ".concat(String.valueOf(j)));
                return j;
            }
        }
    }

    public static int getRequestCodeFromCallback(String str) {
        if (QQ_SHARE_CALLBACK_ACTION.equals(str)) {
            return Constants.REQUEST_QQ_SHARE;
        }
        if (QZONE_SHARE_CALLBACK_ACTION.equals(str)) {
            return Constants.REQUEST_QZONE_SHARE;
        }
        if (QQFAVORITES_CALLBACK_ACTION.equals(str)) {
            return Constants.REQUEST_QQ_FAVORITES;
        }
        if (QQDATALINE_CALLBACK_ACTION.equals(str)) {
            return Constants.REQUEST_SEND_TO_MY_COMPUTER;
        }
        if (TROOPBAR_CALLBACK_ACTION.equals(str)) {
            return Constants.REQUEST_SHARE_TO_TROOP_BAR;
        }
        if (ACTION_LOGIN.equals(str)) {
            return 11101;
        }
        if (ACTION_REQUEST_API.equals(str)) {
            return Constants.REQUEST_API;
        }
        return -1;
    }
}
