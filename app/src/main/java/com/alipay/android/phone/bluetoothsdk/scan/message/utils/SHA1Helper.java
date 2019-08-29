package com.alipay.android.phone.bluetoothsdk.scan.message.utils;

import android.util.Log;
import com.alipay.android.phone.bluetoothsdk.scan.message.utils.EncoderHandler.EncodeType;
import java.security.NoSuchAlgorithmException;

public class SHA1Helper {
    public static final String TAG = "SHA1Helper";

    public static byte[] getTopSevenHexEncode(String data) {
        Log.d(TAG, "the data to be encoded is " + data);
        return getTopSevenHexEncode(data == null ? null : data.getBytes());
    }

    public static byte[] getTopSevenHexEncode(byte[] dataBytes) {
        try {
            byte[] dataEncoded = EncoderHandler.encode(EncodeType.SHA1, dataBytes);
            if (dataEncoded == null || dataEncoded.length == 0) {
                return null;
            }
            byte[] resultCodeBytes = new byte[4];
            System.arraycopy(dataEncoded, 0, resultCodeBytes, 0, 4);
            return resultCodeBytes;
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "getTopSevenHexEncode:" + dataBytes, e);
            return null;
        } catch (NullPointerException e2) {
            Log.e(TAG, "getTopSevenHexEncode:" + dataBytes, e2);
            return null;
        }
    }
}
