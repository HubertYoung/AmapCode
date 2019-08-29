package com.alipay.deviceid.module.senative;

import android.content.Context;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class DeviceIdUtil {
    private static DeviceIdUtil _instance = null;
    private static boolean isLoad = true;
    private static final String mVersion = "1.0";
    private Context mContext = null;
    private int netType = -1;

    static {
        try {
            System.loadLibrary("deviceid_1.0");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private DeviceIdUtil() {
    }

    public static synchronized DeviceIdUtil getInstance(Context context) {
        DeviceIdUtil deviceIdUtil;
        synchronized (DeviceIdUtil.class) {
            try {
                if (_instance == null) {
                    DeviceIdUtil deviceIdUtil2 = new DeviceIdUtil();
                    _instance = deviceIdUtil2;
                    deviceIdUtil2.loadSo(context);
                    _instance.mContext = context;
                }
                deviceIdUtil = _instance;
            }
        }
        return deviceIdUtil;
    }

    private native String getMappedIpAddressNative(String str, String str2, int i);

    private String getPhoneIp() {
        String str;
        String str2 = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            str = null;
            while (networkInterfaces.hasMoreElements()) {
                try {
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    String name = nextElement.getName();
                    if (name != null && nextElement.isUp() && !name.startsWith("ppp") && !name.startsWith("p2p") && !name.startsWith("lo")) {
                        Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress nextElement2 = inetAddresses.nextElement();
                            if (!nextElement2.isLoopbackAddress() && (nextElement2 instanceof Inet4Address)) {
                                String str3 = nextElement2.getHostAddress().toString();
                                if (str3 != null && str3.length() > 0) {
                                    if (name != null && name.startsWith("rmnet")) {
                                        str2 = str3;
                                    } else if (name != null && name.startsWith("wlan")) {
                                        str = str3;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
            str = null;
        }
        if (str != null && str.length() > 0) {
            this.netType = 2;
            return str;
        } else if (str2 == null || str2.length() <= 0) {
            return "";
        } else {
            this.netType = 1;
            return str2;
        }
    }

    private native int init(Object obj);

    private void loadSo(Context context) {
        if (!isLoad) {
            new a(context).a((String) "deviceid", (String) "1.0");
        }
    }

    private native byte[] zipAndEncryptData(Object obj, byte[] bArr);

    public native String getErrorCode();

    public String getRealIpAddress(String str, int i) {
        String phoneIp = getPhoneIp();
        return (str == null || str.length() == 0) ? "" : (phoneIp == null || phoneIp.length() <= 0 || this.netType != 1) ? (phoneIp == null || phoneIp.length() <= 0 || this.netType != 2) ? "" : getMappedIpAddressNative("0.0.0.0", str, i) : getMappedIpAddressNative(phoneIp, str, i);
    }

    public native int getVersion();

    public int initialize() {
        return init(this.mContext);
    }

    public byte[] packageDevideData(byte[] bArr) {
        return zipAndEncryptData(this.mContext, bArr);
    }
}
