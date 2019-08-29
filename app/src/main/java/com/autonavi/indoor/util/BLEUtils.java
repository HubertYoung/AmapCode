package com.autonavi.indoor.util;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.RequiresPermission;
import com.autonavi.indoor.entity.Beacon;
import java.util.Locale;

public class BLEUtils {

    public enum Proximity {
        FAR,
        IMMEDIATE,
        NEAR,
        UNKNOWN
    }

    public interface RestartCompletedListener {
        void onRestartCompleted();
    }

    @RequiresPermission("android.permission.BLUETOOTH")
    public static Beacon createFromScanData(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        long currentTimeMillis = System.currentTimeMillis();
        int i2 = ((bArr[25] & 255) * 256) + (bArr[26] & 255);
        int i3 = ((bArr[27] & 255) * 256) + (bArr[28] & 255);
        if (i3 == 11669 || i2 == 2080 || i2 == 1796 || bluetoothDevice == null) {
            return null;
        }
        byte b = bArr[29];
        byte[] bArr2 = new byte[16];
        System.arraycopy(bArr, 9, bArr2, 0, 16);
        String bytesToHexString = bytesToHexString(bArr2);
        StringBuilder sb = new StringBuilder();
        sb.append(bytesToHexString.substring(0, 32));
        Beacon beacon = new Beacon(sb.toString().toUpperCase(Locale.getDefault()), bluetoothDevice.getName(), bluetoothDevice.getAddress(), i2, i3, b, i, currentTimeMillis);
        return beacon;
    }

    public static double calculateDistance(double d, double d2, double d3) {
        return Math.pow(10.0d, ((d2 - d3) / 10.0d) / d);
    }

    public static double calculateDistance(int i, int i2) {
        if (i2 == 0 || i == 0) {
            return -1.0d;
        }
        double d = (((double) i2) * 1.0d) / ((double) i);
        if (d < 1.0d) {
            return Math.pow(d, 10.0d);
        }
        return (Math.pow(d, 7.7095d) * 0.89976d) + 0.111d;
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static String normalizeProximityUUID(String str) {
        String replace = str.replace("-", "");
        if (replace.length() != 32) {
            return "";
        }
        return String.format("%s-%s-%s-%s-%s", new Object[]{replace.substring(0, 8), replace.substring(8, 12), replace.substring(12, 16), replace.substring(16, 20), replace.substring(20, 32)});
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public static int normalize16BitUnsignedInt(int i) {
        return Math.max(1, Math.min(i, 65535));
    }
}
