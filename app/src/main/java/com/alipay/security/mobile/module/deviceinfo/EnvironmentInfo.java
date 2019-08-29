package com.alipay.security.mobile.module.deviceinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.io.File;

public class EnvironmentInfo {
    private static EnvironmentInfo environmentInfo = new EnvironmentInfo();

    public String getOSName() {
        return "android";
    }

    private EnvironmentInfo() {
    }

    public static EnvironmentInfo getInstance() {
        return environmentInfo;
    }

    public boolean isRooted() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < 5) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(strArr[i]);
                sb.append("su");
                if (new File(sb.toString()).exists()) {
                    return true;
                }
                i++;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public boolean isEmulator(Context context) {
        try {
            if (!Build.HARDWARE.contains("goldfish") && !Build.PRODUCT.contains(GlobalConstants.EXCEPTIONTYPE)) {
                if (!Build.FINGERPRINT.contains("generic")) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                    if (telephonyManager == null || !CommonUtils.isZero(telephonyManager.getDeviceId())) {
                        return CommonUtils.isBlank(Secure.getString(context.getContentResolver(), "android_id"));
                    }
                    return true;
                }
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public String getProductBoard() {
        return Build.BOARD;
    }

    public String getProductBrand() {
        return Build.BRAND;
    }

    public String getProductDevice() {
        return Build.DEVICE;
    }

    public String getBuildDisplayId() {
        return Build.DISPLAY;
    }

    public String getBuildVersionIncremental() {
        return VERSION.INCREMENTAL;
    }

    public String getProductManufacturer() {
        return Build.MANUFACTURER;
    }

    public String getProductModel() {
        return Build.MODEL;
    }

    public String getProductName() {
        return Build.PRODUCT;
    }

    public String getBuildVersionRelease() {
        return VERSION.RELEASE;
    }

    public String getBuildVersionSDK() {
        return VERSION.SDK;
    }

    public String getBuildTags() {
        return Build.TAGS;
    }

    public String getKernelQemu() {
        return CommonUtils.getSystemProperties("ro.kernel.qemu", "0");
    }

    public String getNetworkConnectionType(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager.getActiveNetworkInfo().isConnected()) {
                if (CommonUtils.equalsIgnoreCase(connectivityManager.getActiveNetworkInfo().getTypeName(), "WIFI")) {
                    return "WIFI";
                }
                return connectivityManager.getActiveNetworkInfo().getExtraInfo();
            }
        } catch (Exception unused) {
        }
        return "";
    }

    public String getGsmSimState() {
        return CommonUtils.getSystemProperties("gsm.sim.state", "");
    }

    public String getGsmSimState2() {
        return CommonUtils.getSystemProperties("gsm.sim.state.2", "");
    }

    public String getWifiInterface() {
        return CommonUtils.getSystemProperties("wifi.interface", "");
    }

    public String getUsbState() {
        return CommonUtils.getSystemProperties("sys.usb.state", "");
    }
}
