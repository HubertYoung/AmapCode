package com.alipay.android.phone.inside.offlinecode.gen;

import android.util.Base64;
import com.alipay.android.phone.inside.offlinecode.utils.DesEncrypt;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class YctCodeProtocol extends ICodeProtocol {
    static final String KEY_OFFLINE_DATA_CERT_DATA = "cert_data";
    static final String KEY_OFFLINE_DATA_CERT_START_TIME = "cert_start_time";
    static final String KEY_OFFLINE_DATA_PAY_TYPE = "pay_type";
    static final String KEY_OFFLINE_DATA_SERVER_TIME_DIFF = "server_time_diff";
    static final String KEY_PRIVATE_KEY_ACCOUNT = "account_issue_sub_key";
    static final String KEY_PRIVATE_KEY_TERMINAL = "terminal_authen_sub_key";

    public String generateCode(String str, String str2) throws Exception {
        Map<String, String> offlineDataMap = getOfflineDataMap(str);
        Map<String, String> privateKeyMap = getPrivateKeyMap(str2);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write("KT".getBytes());
        byteArrayOutputStream.write(getBody(offlineDataMap, privateKeyMap));
        return HexUtils.toHexString(byteArrayOutputStream.toByteArray());
    }

    private byte[] getBody(Map<String, String> map, Map<String, String> map2) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(HexUtils.parse(map.get(KEY_OFFLINE_DATA_CERT_DATA)));
        byte[] parse = HexUtils.parse(map.get(KEY_OFFLINE_DATA_PAY_TYPE));
        byteArrayOutputStream.write(parse);
        byte[] parse2 = HexUtils.parse(getGenerateTime(map.get(KEY_OFFLINE_DATA_SERVER_TIME_DIFF)));
        byteArrayOutputStream.write(parse2);
        byte[] parse3 = HexUtils.parse(map.get(KEY_OFFLINE_DATA_CERT_START_TIME));
        byte[] parse4 = HexUtils.parse(map2.get(KEY_PRIVATE_KEY_ACCOUNT));
        byte[] parse5 = HexUtils.parse(map2.get(KEY_PRIVATE_KEY_TERMINAL));
        byte[] accountIssueMac = getAccountIssueMac(parse3, parse2, parse, parse4);
        byteArrayOutputStream.write(getTeminalAuthenMac(parse3, parse2, parse, accountIssueMac, parse5));
        byteArrayOutputStream.write(accountIssueMac);
        return Base64.encode(byteArrayOutputStream.toByteArray(), 2);
    }

    private String getGenerateTime(String str) {
        return Integer.toHexString((int) (Long.parseLong(str) + (System.currentTimeMillis() / 1000)));
    }

    private byte[] getAccountIssueMac(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(bArr);
        byteArrayOutputStream.write(bArr2);
        byteArrayOutputStream.write(bArr3);
        return calculatePboc3DesMAC(bArr4, byteArrayOutputStream.toByteArray());
    }

    private byte[] getTeminalAuthenMac(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(bArr);
        byteArrayOutputStream.write(bArr2);
        byteArrayOutputStream.write(bArr3);
        byteArrayOutputStream.write(bArr4);
        return calculatePboc3DesMAC(bArr5, byteArrayOutputStream.toByteArray());
    }

    public static byte[] calculatePboc3DesMAC(byte[] bArr, byte[] bArr2) throws Exception {
        if (bArr == null || bArr2 == null) {
            throw new RuntimeException("data or key is null.");
        } else if (bArr.length != 16) {
            throw new RuntimeException("key length is not 16 byte.");
        } else {
            byte[] bArr3 = new byte[8];
            byte[] bArr4 = new byte[8];
            System.arraycopy(bArr, 0, bArr4, 0, 8);
            int length = bArr2.length;
            int i = (length / 8) + 1;
            int i2 = length % 8;
            byte[][] bArr5 = (byte[][]) Array.newInstance(byte.class, new int[]{i, 8});
            int i3 = 0;
            while (i3 < i) {
                System.arraycopy(bArr2, i3 * 8, bArr5[i3], 0, i3 == i + -1 ? i2 : 8);
                i3++;
            }
            bArr5[i - 1][i2] = Byte.MIN_VALUE;
            byte[] xOr = DesEncrypt.xOr(bArr5[0], bArr3);
            for (int i4 = 1; i4 < i; i4++) {
                xOr = DesEncrypt.xOr(bArr5[i4], DesEncrypt.encryptForDesCbc(bArr4, xOr));
            }
            byte[] bArr6 = new byte[4];
            System.arraycopy(DesEncrypt.encryptFor3DesCbc(bArr, xOr), 0, bArr6, 0, 4);
            return bArr6;
        }
    }

    private Map<String, String> getOfflineDataMap(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        HashMap hashMap = new HashMap();
        hashMap.put(KEY_OFFLINE_DATA_PAY_TYPE, jSONObject.optString(KEY_OFFLINE_DATA_PAY_TYPE, ""));
        hashMap.put(KEY_OFFLINE_DATA_CERT_START_TIME, jSONObject.optString(KEY_OFFLINE_DATA_CERT_START_TIME, ""));
        hashMap.put(KEY_OFFLINE_DATA_CERT_DATA, jSONObject.optString(KEY_OFFLINE_DATA_CERT_DATA, ""));
        hashMap.put(KEY_OFFLINE_DATA_SERVER_TIME_DIFF, jSONObject.optString(KEY_OFFLINE_DATA_SERVER_TIME_DIFF, "0"));
        return hashMap;
    }

    private Map<String, String> getPrivateKeyMap(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        HashMap hashMap = new HashMap();
        hashMap.put(KEY_PRIVATE_KEY_ACCOUNT, jSONObject.optString(KEY_PRIVATE_KEY_ACCOUNT, ""));
        hashMap.put(KEY_PRIVATE_KEY_TERMINAL, jSONObject.optString(KEY_PRIVATE_KEY_TERMINAL, ""));
        return hashMap;
    }
}
