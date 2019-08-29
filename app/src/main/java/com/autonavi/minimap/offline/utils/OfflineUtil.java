package com.autonavi.minimap.offline.utils;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.SdCardInfo;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.ae.search.SearchEngine;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.offline.WorkThreadManager;
import com.autonavi.minimap.offline.WorkThreadManager.OfflineTask;
import com.autonavi.minimap.offline.externalimport.IExternalService;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.aos.serverkey;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class OfflineUtil {
    public static final String CDN_HEADER_MAC = "Mac";
    public static final String MANUFACTURE = Build.MANUFACTURER;
    public static final String MODEL = Build.MODEL;
    private static final String TAG = "OfflineUtil";
    private static boolean isDBException = false;
    private static boolean isWriteLog = false;
    private static Lock lock = new ReentrantLock();
    private static IExternalService mService = ((IExternalService) ank.a(IExternalService.class));
    private static String macEncoded;

    public static long checkAvailableSpace(long j, long j2) {
        return j <= j2 ? j : j2;
    }

    public static String getMacEncoded() {
        if (!TextUtils.isEmpty(macEncoded)) {
            return macEncoded;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("diu:");
        stringBuffer.append(NetworkParam.getDiu());
        stringBuffer.append(";");
        stringBuffer.append("adiu:");
        stringBuffer.append(NetworkParam.getAdiu());
        stringBuffer.append(";");
        stringBuffer.append("tid:");
        stringBuffer.append(NetworkParam.getTaobaoID());
        stringBuffer.append(";");
        stringBuffer.append("div:");
        stringBuffer.append(NetworkParam.getDiv());
        stringBuffer.append(";");
        stringBuffer.append("dibv:");
        stringBuffer.append(NetworkParam.getDibv());
        stringBuffer.append(";");
        stringBuffer.append("dic:");
        stringBuffer.append(NetworkParam.getDic());
        stringBuffer.append(";");
        stringBuffer.append("lon:");
        stringBuffer.append(LocationInstrument.getInstance().getLatestPosition().getLongitude());
        stringBuffer.append(";");
        stringBuffer.append("lat:");
        stringBuffer.append(LocationInstrument.getInstance().getLatestPosition().getLatitude());
        stringBuffer.append(";");
        stringBuffer.append("manufacture:");
        stringBuffer.append(MANUFACTURE);
        stringBuffer.append(";");
        stringBuffer.append("networktype:");
        stringBuffer.append(getNetType());
        stringBuffer.append(";");
        stringBuffer.append("model:");
        stringBuffer.append(MODEL);
        stringBuffer.append(";");
        String amapEncode = serverkey.amapEncode(stringBuffer.toString());
        macEncoded = amapEncode;
        return amapEncode;
    }

    private static String getNetType() {
        return isMobileConnected(getContext()) ? "Wifi" : "Mobile";
    }

    public static String getString(int i) {
        if (mService != null) {
            return mService.getApplication().getString(i);
        }
        return null;
    }

    public static String getString(int i, Object... objArr) {
        if (mService != null) {
            return mService.getApplication().getString(i, objArr);
        }
        return null;
    }

    public static float get2Num(float f) {
        float f2 = (f / 1024.0f) / 1024.0f;
        if (f2 == 0.0f) {
            f2 = 0.01f;
        }
        float round = ((float) Math.round(f2 * 100.0f)) / 100.0f;
        if (Float.compare(round, 0.1f) < 0) {
            return 0.1f;
        }
        return round;
    }

    public static void runOnWorkThread(Runnable runnable) {
        if (runnable != null) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                ahm.a(runnable);
            } else {
                runnable.run();
            }
        }
    }

    public static Context getContext() {
        if (mService != null) {
            return mService.getApplication().getApplicationContext();
        }
        return AMapAppGlobal.getApplication().getApplicationContext();
    }

    public static boolean isMobileConnected(Context context) {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected() || activeNetworkInfo.getType() != 0) {
            return false;
        }
        return true;
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected() || activeNetworkInfo.getType() != 1) {
            return false;
        }
        return true;
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return activeNetworkInfo;
            }
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            if (allNetworkInfo == null) {
                return null;
            }
            int i = 0;
            while (true) {
                if (i < allNetworkInfo.length) {
                    if (allNetworkInfo[i] != null && allNetworkInfo[i].isConnected()) {
                        activeNetworkInfo = allNetworkInfo[i];
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            return activeNetworkInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static GeoPoint getCurrentPosition() {
        if (mService == null || mService.getLatestPosition(5) == null) {
            return null;
        }
        return mService.getLatestPosition();
    }

    public static double[] getDpoint() {
        double[] dArr = {0.0d, 0.0d};
        GeoPoint currentPosition = getCurrentPosition();
        if (currentPosition != null) {
            DPoint a = cfg.a((long) currentPosition.x, (long) currentPosition.y);
            dArr[0] = a.x;
            dArr[1] = a.y;
        }
        return dArr;
    }

    public static GeoPoint getLatestPosition(int i) {
        if (mService != null) {
            return mService.getLatestPosition(i);
        }
        return null;
    }

    public static GeoPoint getLatestPosition() {
        if (mService != null) {
            return mService.getLatestPosition();
        }
        return null;
    }

    public static int getMapViewAdcode() {
        if (mService != null) {
            IExternalService iExternalService = mService;
            return iExternalService.getMapCenterAdcode(iExternalService.getPageContext());
        }
        GeoPoint latestPosition = getLatestPosition(5);
        if (latestPosition != null) {
            return latestPosition.getAdCode();
        }
        return -1;
    }

    public static synchronized int getCurrentCityAdcode() {
        synchronized (OfflineUtil.class) {
            GeoPoint latestPosition = getLatestPosition(5);
            if (latestPosition != null) {
                int adCode = latestPosition.getAdCode();
                return adCode;
            } else if (mService == null) {
                return -1;
            } else {
                IExternalService iExternalService = mService;
                int mapCenterAdcode = iExternalService.getMapCenterAdcode(iExternalService.getPageContext());
                return mapCenterAdcode;
            }
        }
    }

    public static ArrayList<SdCardInfo> getAllSdcardInfo() {
        if (mService == null) {
            return null;
        }
        IExternalService iExternalService = mService;
        return new ArrayList<>(iExternalService.enumExternalSDcardInfo(iExternalService.getApplication().getApplicationContext()));
    }

    public static String getStorageSizeString(long j) {
        DecimalFormat decimalFormat = new DecimalFormat(DiskFormatter.FORMAT);
        long j2 = (j / 1024) / 1024;
        if (j2 >= 1024) {
            StringBuilder sb = new StringBuilder();
            sb.append(j2 / 1024);
            sb.append(decimalFormat.format((double) ((((float) j2) % 1024.0f) / 1024.0f)));
            sb.append(getString(R.string.offline_storage_unit_gb));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(j2);
        sb2.append(getString(R.string.offline_storage_unit_mb));
        return sb2.toString();
    }

    public static String getOfflineString(int i, Object... objArr) {
        if (mService != null) {
            return mService.getApplication().getString(i, objArr);
        }
        return null;
    }

    public static String getOfflineString(int i) {
        if (mService != null) {
            return mService.getApplication().getString(i);
        }
        return null;
    }

    public static void writeLog(Throwable th) {
        if (isWriteLog) {
            try {
                log(6, generateTag(Thread.currentThread().getStackTrace()[4]), th, null);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    private static String generateTag(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        String substring = className.substring(className.lastIndexOf(".") + 1);
        if (substring.length() <= 23) {
            return substring;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(substring.substring(0, 20));
        sb.append("...");
        return sb.toString();
    }

    private static void log(int i, String str, Object obj, Throwable th) {
        String str2;
        if (mService != null) {
            if (th == null) {
                try {
                    if (obj instanceof Throwable) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(String.valueOf(obj));
                        sb.append(10);
                        sb.append(Log.getStackTraceString((Throwable) obj));
                        str2 = sb.toString();
                    } else {
                        str2 = String.valueOf(obj);
                    }
                } catch (Throwable unused) {
                    return;
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(String.valueOf(obj));
                sb2.append(10);
                sb2.append(Log.getStackTraceString(th));
                str2 = sb2.toString();
            }
            Log.println(i, str, str2);
            writeLogToFile(mService.getApplication().getApplicationContext(), str2, "offlineErrorLog.txt");
        }
    }

    private static void writeLogToFile(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            String b = PathManager.a().b();
            if (checkPathIsCanUse(b)) {
                StringBuilder sb = new StringBuilder();
                sb.append(b);
                sb.append("/autonavi/");
                final File file = new File(sb.toString(), str2);
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(format);
                sb2.append(MergeUtil.SEPARATOR_KV);
                sb2.append(str);
                try {
                    final String str3 = new String(sb2.toString().getBytes(), "utf-8");
                    WorkThreadManager.start(new OfflineTask() {
                        /* JADX WARNING: Removed duplicated region for block: B:15:0x0025 A[SYNTHETIC, Splitter:B:15:0x0025] */
                        /* JADX WARNING: Removed duplicated region for block: B:22:0x0031 A[SYNTHETIC, Splitter:B:22:0x0031] */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public final java.lang.Object doBackground() throws java.lang.Exception {
                            /*
                                r5 = this;
                                r0 = 0
                                java.io.FileWriter r1 = new java.io.FileWriter     // Catch:{ Exception -> 0x001e, all -> 0x0019 }
                                java.io.File r2 = r0     // Catch:{ Exception -> 0x001e, all -> 0x0019 }
                                r3 = 1
                                r1.<init>(r2, r3)     // Catch:{ Exception -> 0x001e, all -> 0x0019 }
                                java.lang.String r2 = r4     // Catch:{ Exception -> 0x0017 }
                                r1.write(r2)     // Catch:{ Exception -> 0x0017 }
                                java.lang.String r2 = "\r\n-------------------\r\n"
                                r1.write(r2)     // Catch:{ Exception -> 0x0017 }
                                r1.close()     // Catch:{ IOException -> 0x0029 }
                                goto L_0x002d
                            L_0x0017:
                                r2 = move-exception
                                goto L_0x0020
                            L_0x0019:
                                r1 = move-exception
                                r4 = r1
                                r1 = r0
                                r0 = r4
                                goto L_0x002f
                            L_0x001e:
                                r2 = move-exception
                                r1 = r0
                            L_0x0020:
                                r2.printStackTrace()     // Catch:{ all -> 0x002e }
                                if (r1 == 0) goto L_0x002d
                                r1.close()     // Catch:{ IOException -> 0x0029 }
                                goto L_0x002d
                            L_0x0029:
                                r1 = move-exception
                                r1.printStackTrace()
                            L_0x002d:
                                return r0
                            L_0x002e:
                                r0 = move-exception
                            L_0x002f:
                                if (r1 == 0) goto L_0x0039
                                r1.close()     // Catch:{ IOException -> 0x0035 }
                                goto L_0x0039
                            L_0x0035:
                                r1 = move-exception
                                r1.printStackTrace()
                            L_0x0039:
                                throw r0
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.utils.OfflineUtil.AnonymousClass1.doBackground():java.lang.Object");
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setIsDBException(boolean z) {
        isDBException = z;
    }

    public static boolean isDBException() {
        return isDBException;
    }

    public static void remindDBException(Exception exc) {
        if (exc != null) {
            Throwable cause = exc.getCause();
            if (cause != null) {
                writeLog(cause);
                if (cause instanceof SQLiteFullException) {
                    if (!isDBException && mService != null) {
                        IExternalService iExternalService = mService;
                        iExternalService.showToast(iExternalService.getApplication().getString(R.string.offline_storage_noenough));
                    }
                } else if (!isDBException && mService != null) {
                    IExternalService iExternalService2 = mService;
                    iExternalService2.showToast(iExternalService2.getApplication().getApplicationContext().getString(R.string.storage_error_hint));
                }
            }
        }
        isDBException = true;
    }

    public static long getFormattedToday() {
        return ahh.c(new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date(System.currentTimeMillis())));
    }

    public static boolean checkPathIsCanUse(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory() || !file.canWrite() || !file.canRead()) {
            return false;
        }
        return true;
    }

    public static String getDatabasesDir() {
        return getDatabasesDirPath();
    }

    public static double getPercentValue(double d) {
        try {
            return ahh.a(new DecimalFormat("#.#").format(d));
        } catch (Throwable th) {
            th.printStackTrace();
            return 0.0d;
        }
    }

    public static String getDatabasesDirPath() {
        if (mService == null) {
            return null;
        }
        Application application = mService.getApplication();
        if (VERSION.SDK_INT >= 17) {
            StringBuilder sb = new StringBuilder();
            sb.append(application.getApplicationInfo().dataDir);
            sb.append("/databases/");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder("/data/data/");
        sb2.append(application.getPackageName());
        sb2.append("/databases/");
        return sb2.toString();
    }

    public static String getFileMD5(File file) {
        if (mService != null) {
            return mService.getFileMD5(file);
        }
        return null;
    }

    public static String getStringMD5(String str) {
        if (mService != null) {
            return mService.getStringMD5(str);
        }
        return null;
    }

    private static int getInt(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) + ((bArr[i + 2] & 255) << 16) + ((bArr[i + 1] & 255) << 8) + ((bArr[i + 0] & 255) << 0);
    }

    public static boolean isUIThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    private static int getPoiVersion(SearchEngine searchEngine, int i) {
        if (searchEngine == null || i < 0) {
            return -1;
        }
        try {
            String dataVersion = SearchEngine.getDataVersion(i);
            if (!TextUtils.isEmpty(dataVersion)) {
                return ahh.b(dataVersion);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
