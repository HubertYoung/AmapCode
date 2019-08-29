package defpackage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.List;

/* renamed from: agq reason: default package */
/* compiled from: HardwareUtil */
public final class agq {
    public static boolean a(Context context) {
        try {
            return ((WifiManager) context.getSystemService("wifi")).isWifiEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean b(Context context) {
        try {
            return ((LocationManager) context.getSystemService("location")).isProviderEnabled(WidgetType.GPS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean c(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (locationManager == null) {
                return false;
            }
            List<String> allProviders = locationManager.getAllProviders();
            if (allProviders == null) {
                return false;
            }
            return allProviders.contains(WidgetType.GPS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean a(Context context, int i) {
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        if (sensorManager == null) {
            return false;
        }
        List<Sensor> sensorList = sensorManager.getSensorList(-1);
        ArrayList arrayList = new ArrayList();
        for (Sensor type : sensorList) {
            arrayList.add(Integer.valueOf(type.getType()));
        }
        if (arrayList.contains(Integer.valueOf(i))) {
            return true;
        }
        return false;
    }

    public static String d(Context context) {
        String str = "";
        try {
            String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddress == null) {
                return "";
            }
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(macAddress);
                sb.append(",23");
                str = sb.toString();
                macAddress = a(str);
            } catch (Exception unused) {
            }
            return macAddress;
        } catch (Exception unused2) {
            return str;
        }
    }

    private static String a(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        str.lastIndexOf(":");
        String substring = str.substring(str.indexOf(",") + 1);
        String[] split = str.substring(0, str.indexOf(",")).split(":");
        for (int length = split.length - 1; length >= 0; length--) {
            if (split[length].length() > 2) {
                split[length] = split[length].substring(0, 2);
            }
            stringBuffer.append(Integer.toHexString(~Integer.parseInt(split[length], 16)).toString().substring(6, 8));
        }
        stringBuffer.append(Integer.toHexString(~Integer.parseInt(substring, 10)).toString());
        return stringBuffer.toString();
    }
}
