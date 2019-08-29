package com.ta.audid.collect;

import android.content.Context;
import android.text.TextUtils;
import com.ta.audid.store.SdcardDeviceModle;
import com.ta.audid.utils.UmidUtils;
import com.ta.utdid2.android.utils.BuildCompatUtils;
import java.util.HashMap;
import java.util.Map;

public class DeviceInfoModle {
    private static final String D10_PHONENUMBER = "D10";
    private static final String D11_CPUCOUNT = "D11";
    private static final String D12_MAXCPUFREQ = "D12";
    private static final String D13_MEM_SIZE = "D13";
    private static final String D14_EXTMEM_SIZE = "D14";
    private static final String D15_SCREEN_DPI = "D15";
    private static final String D16_SCREEN_RESOLUTION = "D16";
    private static final String D17_UMID = "D17";
    private static final String D18_TFCARD = "D18";
    private static final String D19_GRAVITY = "D19";
    private static final String D1_IMEI = "D1";
    private static final String D20_FINGERPRINT = "D20";
    private static final String D21_GYROSCOPE = "D21";
    private static final String D22_GPS = "D22";
    private static final String D2_IMSI = "D2";
    private static final String D3_WIFI_MAC = "D3";
    private static final String D4_BLUETOOTH_MAC = "D4";
    private static final String D5_GSID = "D5";
    private static final String D6_SN = "D6";
    private static final String D7_SSN = "D7";
    private static final String D8_MMC_CID = "D8";
    private static final String D9_CPU_SERIAL = "D9";

    static Map<String, String> getDeviceInfoModle(Context context) {
        HashMap hashMap = new HashMap();
        if (BuildCompatUtils.isAtLeastQ()) {
            putMapWithoutEmptyValue(hashMap, D5_GSID, DeviceInfo2.getAndroidID(context));
            return hashMap;
        }
        String imei = DeviceInfo2.getIMEI(context);
        String imsi = DeviceInfo2.getIMSI(context);
        if (TextUtils.isEmpty(imei)) {
            imei = SdcardDeviceModle.getModuleImei();
        }
        if (TextUtils.isEmpty(imsi)) {
            imsi = SdcardDeviceModle.getModuleImsi();
        }
        putMapWithoutEmptyValue(hashMap, D1_IMEI, imei);
        putMapWithoutEmptyValue(hashMap, D2_IMSI, imsi);
        putMapWithoutEmptyValue(hashMap, D3_WIFI_MAC, DeviceInfo2.getWifiMacID(context));
        putMapWithoutEmptyValue(hashMap, D4_BLUETOOTH_MAC, DeviceInfo2.getBluetoothMac());
        putMapWithoutEmptyValue(hashMap, D5_GSID, DeviceInfo2.getAndroidID(context));
        putMapWithoutEmptyValue(hashMap, D6_SN, DeviceInfo2.getSerialNum());
        putMapWithoutEmptyValue(hashMap, D7_SSN, DeviceInfo2.getSimSerialNum(context));
        putMapWithoutEmptyValue(hashMap, D8_MMC_CID, DeviceInfo2.getNandID());
        putMapWithoutEmptyValue(hashMap, D9_CPU_SERIAL, DeviceInfo2.getCPUSerial());
        putMapWithoutEmptyValue(hashMap, D10_PHONENUMBER, DeviceInfo2.getPhoneNumber(context));
        hashMap.put(D11_CPUCOUNT, DeviceInfo2.getCpuCount());
        hashMap.put(D12_MAXCPUFREQ, DeviceInfo2.getMaxCpuFreq());
        hashMap.put(D13_MEM_SIZE, DeviceInfo2.getMemTotalSize());
        hashMap.put(D14_EXTMEM_SIZE, DeviceInfo2.getTotalExternalMemorySize(context));
        hashMap.put(D15_SCREEN_DPI, DeviceInfo2.getScreenDpi(context));
        hashMap.put(D16_SCREEN_RESOLUTION, DeviceInfo2.getScreenResolution(context));
        hashMap.put(D17_UMID, UmidUtils.getUmidToken(context));
        hashMap.put(D18_TFCARD, DeviceInfo2.checkTfCard(context) ? "1" : "0");
        hashMap.put(D19_GRAVITY, DeviceInfo2.checkSensor(context, 9) ? "1" : "0");
        hashMap.put(D21_GYROSCOPE, DeviceInfo2.checkSensor(context, 4) ? "1" : "0");
        hashMap.put(D22_GPS, DeviceInfo2.hasGPSDevice(context) ? "1" : "0");
        return hashMap;
    }

    private static void putMapWithoutEmptyValue(Map<String, String> map, String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            map.put(str, str2);
        }
    }
}
