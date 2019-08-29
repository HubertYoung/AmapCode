package com.autonavi.indoor.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.RequiresPermission;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alibaba.analytics.core.device.Constants;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.amap.location.common.a;
import java.util.List;

public class DeviceUtils {
    private static final String NETSTATE = "android.permission.ACCESS_NETWORK_STATE";
    private static final String PHONESTATE = "android.permission.READ_PHONE_STATE";
    private static final String WIFISTATE = "android.permission.ACCESS_WIFI_STATE";
    static String mDeviceId;
    static String mMacAddress;
    static String mReslution;
    static String mSubscriberId;
    static String networkOperatorName;

    public static String getDeviceID(Context context) {
        try {
            if (mDeviceId != null && !"".equals(mDeviceId)) {
                return mDeviceId;
            }
            mDeviceId = a.a(context);
            return mDeviceId;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static String getSubscriberId(Context context) {
        if (mSubscriberId != null && !"".equals(mSubscriberId)) {
            return mSubscriberId;
        }
        String d = a.d(context);
        mSubscriberId = d;
        if (d == null) {
            mSubscriberId = "";
        }
        return mSubscriberId;
    }

    public static String getReslution(Context context) {
        StringBuilder sb;
        try {
            if (mReslution != null && !"".equals(mReslution)) {
                return mReslution;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            if (i2 > i) {
                sb = new StringBuilder();
                sb.append(i);
                sb.append("*");
                sb.append(i2);
            } else {
                sb = new StringBuilder();
                sb.append(i2);
                sb.append("*");
                sb.append(i);
            }
            mReslution = sb.toString();
            return mReslution;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String getDeviceMac(Context context) {
        try {
            if (mMacAddress != null && !"".equals(mMacAddress)) {
                return mMacAddress;
            }
            mMacAddress = a.f(context);
            return mMacAddress;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static List<ScanResult> doChooseSort(List<ScanResult> list) {
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            for (int i2 = 1; i2 < size - i; i2++) {
                int i3 = i2 - 1;
                if (list.get(i3).level > list.get(i2).level) {
                    list.set(i3, list.get(i2));
                    list.set(i2, list.get(i3));
                }
            }
        }
        return list;
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String getWifiMacs(Context context) {
        StringBuilder sb = new StringBuilder();
        if (context != null) {
            try {
                if (context.checkCallingOrSelfPermission(WIFISTATE) == 0) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager.isWifiEnabled()) {
                        List<ScanResult> scanResults = wifiManager.getScanResults();
                        if (scanResults != null) {
                            if (scanResults.size() != 0) {
                                List<ScanResult> doChooseSort = doChooseSort(scanResults);
                                int i = 0;
                                boolean z = true;
                                while (i < doChooseSort.size() && i < 10) {
                                    ScanResult scanResult = doChooseSort.get(i);
                                    if (z) {
                                        z = false;
                                    } else {
                                        sb.append(";");
                                    }
                                    sb.append(scanResult.BSSID);
                                    i++;
                                }
                            }
                        }
                        return sb.toString();
                    }
                    return sb.toString();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static int getActiveNetWorkType(Context context) {
        if (context == null || context.checkCallingOrSelfPermission(NETSTATE) != 0) {
            return -1;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return -1;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return -1;
        }
        return activeNetworkInfo.getType();
    }

    public static int getNetWorkType(Context context) {
        if (context.checkCallingOrSelfPermission(PHONESTATE) != 0) {
            return -1;
        }
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkType();
    }

    public static String getNetworkOperatorName(Context context) {
        if (context.checkCallingOrSelfPermission(PHONESTATE) != 0) {
            return networkOperatorName;
        }
        String simOperatorName = ((TelephonyManager) context.getSystemService("phone")).getSimOperatorName();
        networkOperatorName = simOperatorName;
        return simOperatorName;
    }

    public static String getMNC(Context context) {
        String str;
        try {
            if (context.checkCallingOrSelfPermission(PHONESTATE) != 0) {
                return "";
            }
            String networkOperator = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
            if (TextUtils.isEmpty(networkOperator) && networkOperator.length() < 3) {
                return "";
            }
            str = networkOperator.substring(3);
            return str;
        } catch (Throwable th) {
            th.printStackTrace();
            str = "";
        }
    }

    public static String getCoreMNC(Context context) {
        try {
            return getMNC(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String getConnectWifi(Context context) {
        String str = null;
        if (context != null) {
            try {
                if (context.checkCallingOrSelfPermission(WIFISTATE) == 0) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager.isWifiEnabled()) {
                        str = wifiManager.getConnectionInfo().getBSSID();
                    }
                    return str;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public static String getCellInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            if (context.checkCallingOrSelfPermission(PHONESTATE) != 0) {
                return sb.toString();
            }
            CellLocation cellLocation = ((TelephonyManager) context.getSystemService("phone")).getCellLocation();
            if (cellLocation instanceof GsmCellLocation) {
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                int cid = gsmCellLocation.getCid();
                sb.append(gsmCellLocation.getLac());
                sb.append(Constants.SEPARATOR);
                sb.append(cid);
                sb.append("&bttype=gsm");
            } else if (cellLocation instanceof CdmaCellLocation) {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                int systemId = cdmaCellLocation.getSystemId();
                int networkId = cdmaCellLocation.getNetworkId();
                int baseStationId = cdmaCellLocation.getBaseStationId();
                sb.append(systemId);
                sb.append(Constants.SEPARATOR);
                sb.append(networkId);
                sb.append(Constants.SEPARATOR);
                sb.append(baseStationId);
                sb.append("&bttype=cdma");
            }
            return sb.toString();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
