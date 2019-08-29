package defpackage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.regex.Pattern;

@Deprecated
/* renamed from: aaw reason: default package */
/* compiled from: NetworkUtil */
public final class aaw {
    private static Pattern a = Pattern.compile("(http|https):\\/\\/([\\w.]+\\/?)\\S*");

    public static String a(Context context) {
        String str = "";
        if (context == null) {
            return str;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
                NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
                if (networkInfo != null && networkInfo.isConnected()) {
                    str = "wifi";
                }
                if (networkInfo2 != null && networkInfo2.isConnected()) {
                    str = "mobile";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static int b(Context context) {
        if (context == null) {
            AMapLog.w("NetworkUtil", "getNetWorkType null == context ");
            return 0;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    AMapLog.w("NetworkUtil", "getNetWorkType activeNetInfo == null ");
                    return 0;
                } else if (activeNetworkInfo.getType() == 1) {
                    return 4;
                } else {
                    if (activeNetworkInfo.getType() == 0) {
                        if (context == null) {
                            AMapLog.w("NetworkUtil", "getMobileNetType null == context");
                            return 0;
                        }
                        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                        if (telephonyManager != null) {
                            switch (telephonyManager.getNetworkType()) {
                                case 1:
                                case 2:
                                case 4:
                                case 7:
                                case 11:
                                    return 1;
                                case 3:
                                case 5:
                                case 6:
                                case 8:
                                case 9:
                                case 10:
                                case 12:
                                case 14:
                                case 15:
                                    return 2;
                                case 13:
                                    return 3;
                                default:
                                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                                        String subtypeName = activeNetworkInfo.getSubtypeName();
                                        if ("TD-SCDMA".equalsIgnoreCase(subtypeName) || "WCDMA".equalsIgnoreCase(subtypeName) || "CDMA2000".equalsIgnoreCase(subtypeName)) {
                                            return 2;
                                        }
                                    }
                                    AMapLog.w("NetworkUtil", "getMobileNetType networkInfo == null || !networkInfo.isConnected()");
                                    return 0;
                            }
                        } else {
                            AMapLog.w("NetworkUtil", "getMobileNetType NETWORK_CLASS_UNKNOWN");
                            return 0;
                        }
                    }
                }
            } catch (Exception unused) {
                AMapLog.w("NetworkUtil", "getNetWorkType Exception ");
                return 0;
            }
        }
        AMapLog.w("NetworkUtil", "getNetWorkType null == connectivityManager");
        return 0;
    }

    public static boolean a() {
        NetworkInfo e = e(AMapAppGlobal.getApplication().getApplicationContext());
        return e != null && e.isConnected();
    }

    public static boolean c(Context context) {
        NetworkInfo e = e(context);
        return e != null && e.isConnected();
    }

    public static int d(Context context) {
        NetworkInfo networkInfo;
        if (context == null) {
            return 0;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (connectivityManager == null || telephonyManager == null) {
            return 0;
        }
        try {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
            networkInfo = null;
        }
        if (networkInfo == null) {
            return 0;
        }
        try {
            int type = networkInfo.getType();
            int subtype = networkInfo.getSubtype();
            if (type == 1) {
                return 1;
            }
            if (type == 0 && subtype == 3 && !telephonyManager.isNetworkRoaming()) {
                return 3;
            }
            if (subtype == 1 || subtype == 4 || subtype == 2) {
                return 2;
            }
            return 4;
        } catch (Throwable th) {
            th.printStackTrace();
            return 4;
        }
    }

    public static boolean a(String str) {
        return a.matcher(str).matches();
    }

    public static NetworkInfo e(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return null;
            }
            return activeNetworkInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
