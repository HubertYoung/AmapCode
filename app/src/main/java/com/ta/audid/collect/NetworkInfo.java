package com.ta.audid.collect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.ta.audid.utils.UtdidLogger;
import com.ta.utdid2.android.utils.StringUtils;

public class NetworkInfo {
    public static String getBssid(Context context) {
        try {
            String bssid = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getBSSID();
            if (StringUtils.isBlank(bssid)) {
                bssid = "";
            }
            return bssid;
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getPhoneNetworkType(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return String.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Exception unused) {
        }
        return "";
    }

    public static String getPhoneOperatorName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return telephonyManager != null ? telephonyManager.getSimOperatorName() : "";
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getPhoneOperatorType(Context context) {
        if (context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return telephonyManager.getSimOperator();
            }
            return null;
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getRssi(Context context) {
        if (context == null) {
            return "";
        }
        try {
            int rssi = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getRssi();
            if (rssi <= 0 && rssi >= -50) {
                return "1";
            }
            if (rssi < -50 && rssi >= -70) {
                return "2";
            }
            if (rssi >= -70 || rssi < -80) {
                return (rssi >= -80 || rssi < -100) ? "5" : "4";
            }
            return "3";
        } catch (Exception unused) {
            return "";
        }
    }

    public static boolean isStrongSemaphore(Context context) {
        if (context == null) {
            return false;
        }
        try {
            int rssi = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getRssi();
            return rssi <= 0 && rssi >= -70;
        } catch (Exception e) {
            UtdidLogger.d((String) "", e);
        }
    }

    public static boolean isBluetoothEnable(Context context) {
        if (context != null && VERSION.SDK_INT >= 18) {
            try {
                BluetoothAdapter adapter = ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter();
                if (adapter == null || !adapter.isEnabled()) {
                    return false;
                }
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }
}
