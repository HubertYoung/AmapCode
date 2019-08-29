package com.alipay.android.phone.bluetoothsdk;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build.VERSION;
import com.alipay.android.phone.androidannotations.utils.PermissionUtils;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Arrays;
import java.util.UUID;

public class BluetoothHelper {
    private static final String TAG = "BluetoothHelper";

    public static boolean isSupportBLE(Context context) {
        boolean support = context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
        LoggerFactory.getTraceLogger().debug(TAG, "support ble:" + support);
        return support;
    }

    @TargetApi(21)
    public static boolean supportAdvertise() {
        if (VERSION.SDK_INT < 21) {
            return false;
        }
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null || bluetoothAdapter.getBluetoothLeAdvertiser() == null || !bluetoothAdapter.isMultipleAdvertisementSupported()) {
            return false;
        }
        return true;
    }

    public static BluetoothState getBluetoothState() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return BluetoothState.UNKNOWN;
        }
        int state = bluetoothAdapter.getState();
        if (state == 12) {
            return BluetoothState.ON;
        }
        if (state == 11) {
            return BluetoothState.RESETTING;
        }
        return BluetoothState.OFF;
    }

    public static boolean openBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }
        return false;
    }

    public static boolean closeBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.disable();
        }
        return false;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return "";
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv.toUpperCase());
        }
        return stringBuilder.toString();
    }

    public static String bytesToHexStringInReverse(byte[] src) {
        return bytesToHexString(littleToBig(src));
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        String hexString2 = hexString.toUpperCase();
        if (hexString2.startsWith("0X") || hexString2.startsWith("0x")) {
            hexString2 = hexString2.substring(2);
        }
        int length = hexString2.length() / 2;
        char[] hexChars = hexString2.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte[] hexStringToBytesInReverse(String hexString) {
        return littleToBig(hexStringToBytes(hexString));
    }

    private static byte[] littleToBig(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException();
        }
        byte[] temp = new byte[bytes.length];
        for (int i = bytes.length - 1; i >= 0; i--) {
            temp[i] = bytes[(bytes.length - 1) - i];
        }
        return temp;
    }

    private static byte charToByte(char c) {
        return (byte) HexUtils.HEX_CHARS.indexOf(c);
    }

    public static String getManufacturerData(byte[] scanRecord) {
        if (scanRecord == null) {
            return "";
        }
        int currentPos = 0;
        while (true) {
            try {
                if (currentPos < scanRecord.length) {
                    int currentPos2 = currentPos + 1;
                    try {
                        int length = scanRecord[currentPos] & 255;
                        if (length != 0) {
                            int dataLength = length - 1;
                            currentPos = currentPos2 + 1;
                            switch (scanRecord[currentPos2] & 255) {
                                case 255:
                                    String hexString = bytesToHexString(extractBytes(scanRecord, currentPos, dataLength));
                                    LoggerFactory.getTraceLogger().debug(TAG, "manu data:" + hexString);
                                    return hexString;
                                default:
                                    currentPos += dataLength;
                            }
                        }
                    } catch (Exception e) {
                        LoggerFactory.getTraceLogger().error((String) TAG, "unable to parse scan record: " + Arrays.toString(scanRecord));
                        return "";
                    }
                }
            } catch (Exception e2) {
                int i = currentPos;
                LoggerFactory.getTraceLogger().error((String) TAG, "unable to parse scan record: " + Arrays.toString(scanRecord));
                return "";
            }
        }
        return "";
    }

    private static byte[] extractBytes(byte[] scanRecord, int start, int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(scanRecord, start, bytes, 0, length);
        return bytes;
    }

    public static UUID getUUIDFromString(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (Exception e) {
            try {
                return BluetoothUuid.parseUuidFrom(hexStringToBytesInReverse(uuidString)).getUuid();
            } catch (Exception e2) {
                return null;
            }
        }
    }

    public static boolean hasLocationPermission(Context context) {
        if (!PermissionUtils.hasSelfPermissions(context, "android.permission.ACCESS_COARSE_LOCATION")) {
            if (!PermissionUtils.hasSelfPermissions(context, "android.permission.ACCESS_FINE_LOCATION")) {
                return false;
            }
        }
        return true;
    }
}
