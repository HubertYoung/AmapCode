package com.autonavi.server.aos;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.server.aos.ServerkeyConfig;
import com.autonavi.jni.server.aos.ServerkeyGeoPoint;
import com.autonavi.jni.server.aos.ServerkeyNative;

public class serverkey {
    static {
        ServerkeyConfig.IS_DEBUG = anj.a();
        ServerkeyConfig.IS_LOG_ON = b.a;
        ServerkeyConfig.APP_APPLICATION = AMapAppGlobal.getApplication();
    }

    public static String sign(byte[] bArr) {
        return ServerkeyNative.sign(bArr);
    }

    public static String getAosChannel() {
        return ServerkeyNative.getAosChannel();
    }

    public static String getAosKey() {
        return ServerkeyNative.getAosKey();
    }

    public static String getSsoKey() {
        return ServerkeyNative.getSsoKey();
    }

    public static String getSinaCustomKey() {
        return ServerkeyNative.getSinaCustomKey();
    }

    public static String getSinaSecret() {
        return ServerkeyNative.getSinaSecret();
    }

    public static String getTaobaoCustomKey() {
        return ServerkeyNative.getTaobaoCustomKey();
    }

    public static String getTaobaoSecret() {
        return ServerkeyNative.getTaobaoSecret();
    }

    public static String get360CustomKey() {
        return ServerkeyNative.get360CustomKey();
    }

    public static String get360Secret() {
        return ServerkeyNative.get360Secret();
    }

    public static String getQQCustomKey() {
        return ServerkeyNative.getQQCustomKey();
    }

    public static String getQQSecret() {
        return ServerkeyNative.getQQSecret();
    }

    public static String getLWCustomKey() {
        return ServerkeyNative.getLWCustomKey();
    }

    public static String getLWSecret() {
        return ServerkeyNative.getLWSecret();
    }

    public static String getYXCustomKey() {
        return ServerkeyNative.getYXCustomKey();
    }

    public static String getYXSecret() {
        return ServerkeyNative.getYXSecret();
    }

    public static String getWXCustomKey() {
        return ServerkeyNative.getWXCustomKey();
    }

    public static String getWXSecret() {
        return ServerkeyNative.getWXSecret();
    }

    public static String getSpm(String str, String str2, String str3, String str4, String str5) {
        return ServerkeyNative.getSpm(str, str2, str3, str4, str5);
    }

    public static int translatePointLocal(int i, int i2, ServerkeyGeoPoint serverkeyGeoPoint) {
        return ServerkeyNative.translatePointLocal(i, i2, serverkeyGeoPoint);
    }

    @Deprecated
    public static String amapEncode(String str) {
        return ServerkeyNative.amapEncode(str);
    }

    @Deprecated
    public static String amapEncode(String str, String str2) {
        return ServerkeyNative.amapEncode(str, str2);
    }

    @Deprecated
    public static String amapDecode(String str) {
        return ServerkeyNative.amapDecode(str);
    }

    @Deprecated
    public static String amapDecode(String str, String str2) {
        return ServerkeyNative.amapDecode(str, str2);
    }

    @Deprecated
    public static byte[] amapEncodeBinary(byte[] bArr) {
        return ServerkeyNative.amapEncodeBinary(bArr);
    }

    public static String amapEncodeV2(String str) {
        return ServerkeyNative.amapEncodeV2(str);
    }

    public static String amapEncodeV2(String str, String str2) {
        return ServerkeyNative.amapEncodeV2(str, str2);
    }

    public static String amapDecodeV2(String str) {
        return ServerkeyNative.amapDecodeV2(str);
    }

    public static String amapDecodeV2(String str, String str2) {
        return ServerkeyNative.amapDecodeV2(str, str2);
    }

    public static byte[] amapEncodeBinaryV2(byte[] bArr) {
        return ServerkeyNative.amapEncodeBinaryV2(bArr);
    }

    public static String getVersion() {
        return ServerkeyNative.getVersion();
    }
}
