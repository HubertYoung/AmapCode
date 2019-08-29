package com.ta.audid.collect;

import android.content.Context;
import com.ta.audid.utils.YunOSDeviceUtils;
import java.util.HashMap;
import java.util.Map;

public class SystemInfoModle {
    private static final String S10_Build_DISPLAY = "S10";
    private static final String S11_Build_ID = "S11";
    private static final String S12_Build_TIME = "S12";
    private static final String S13_Build_BOARD = "S13";
    private static final String S14_Build_DEVICE = "S14";
    private static final String S15_Build_MANUFACTURER = "S15";
    private static final String S16_Build_PRODUCT = "S16";
    private static final String S17_Build_INCREMENTAL = "S17";
    private static final String S18_GSM_SM_STATE = "S18";
    private static final String S19_GSM_SM_STATE2 = "S19";
    private static final String S20_KERNEL_QEMU = "S20";
    private static final String S21_USB_STATE = "S21";
    private static final String S22_WIFI_INTERFACE = "S22";
    private static final String S23_BAND_VERSON = "S23";
    private static final String S24_YUNOS_UUID = "S24";
    private static final String S25_YUNOS_VERSON = "S25";
    private static final String S26_NETWORK_BSSD = "S26";
    private static final String S27_NETWORK_TYPE = "S27";
    private static final String S28_OPERATOR_NAME = "S28";
    private static final String S29_OPERATOR_TYPE = "S29";
    private static final String S2_EMULATOR = "S2";
    private static final String S31_STRONGSEMAPHORE = "S31";
    private static final String S32_BLUETOOTH = "S32";
    private static final String S33_REMANPOWER = "S33";
    private static final String S34_FREEMEMORY = "S34";
    private static final String S36_APP_LIST = "S36";
    private static final String S3_OS_NAME = "S3";
    private static final String S4_BRAND = "S4";
    private static final String S5_MODEL = "S5";
    private static final String S6_BUILD_VERSON_RELEASE = "S6";
    private static final String S7_BUILD_VERSON_SDK = "S7";
    private static final String S8_Build_TYPE = "S8";
    private static final String S9_Build_TAGS = "S9";

    public static Map<String, String> getSystemInfoModle(Context context) {
        HashMap hashMap = new HashMap();
        if (SystemInfo.isEmulator(context)) {
            hashMap.put(S2_EMULATOR, "1");
        } else {
            hashMap.put(S2_EMULATOR, "0");
        }
        hashMap.put(S3_OS_NAME, SystemInfo.getOSName());
        hashMap.put(S4_BRAND, SystemInfo.getBrand());
        hashMap.put(S5_MODEL, SystemInfo.getModel());
        hashMap.put(S6_BUILD_VERSON_RELEASE, SystemInfo.getBuildVersionRelease());
        hashMap.put(S7_BUILD_VERSON_SDK, SystemInfo.getBuildVersionSDK());
        hashMap.put(S8_Build_TYPE, SystemInfo.getBuildType());
        hashMap.put(S9_Build_TAGS, SystemInfo.getBuildTags());
        hashMap.put(S10_Build_DISPLAY, SystemInfo.getBuildDisplay());
        hashMap.put(S11_Build_ID, SystemInfo.getBuildID());
        hashMap.put(S12_Build_TIME, SystemInfo.getBuildTime());
        hashMap.put(S13_Build_BOARD, SystemInfo.getBoard());
        hashMap.put(S14_Build_DEVICE, SystemInfo.getDevice());
        hashMap.put(S15_Build_MANUFACTURER, SystemInfo.getManufacturer());
        hashMap.put(S16_Build_PRODUCT, SystemInfo.getProduct());
        hashMap.put(S17_Build_INCREMENTAL, SystemInfo.getBuildVersionIncremental());
        hashMap.put(S18_GSM_SM_STATE, SystemInfo.getGsmSimState());
        hashMap.put(S19_GSM_SM_STATE2, SystemInfo.getGsmSimState2());
        hashMap.put(S20_KERNEL_QEMU, SystemInfo.getKernelQemu());
        hashMap.put(S21_USB_STATE, SystemInfo.getUsbState());
        hashMap.put(S22_WIFI_INTERFACE, SystemInfo.getWifiInterface());
        hashMap.put(S23_BAND_VERSON, SystemInfo.getBandVersion());
        hashMap.put(S24_YUNOS_UUID, YunOSDeviceUtils.getYunOSUuid());
        hashMap.put(S25_YUNOS_VERSON, YunOSDeviceUtils.getYunOSVersion());
        hashMap.put(S26_NETWORK_BSSD, NetworkInfo.getBssid(context));
        hashMap.put(S27_NETWORK_TYPE, NetworkInfo.getPhoneNetworkType(context));
        hashMap.put(S28_OPERATOR_NAME, NetworkInfo.getPhoneOperatorName(context));
        hashMap.put(S29_OPERATOR_TYPE, NetworkInfo.getPhoneOperatorType(context));
        hashMap.put(S31_STRONGSEMAPHORE, NetworkInfo.isStrongSemaphore(context) ? "1" : "0");
        hashMap.put(S32_BLUETOOTH, NetworkInfo.isBluetoothEnable(context) ? "1" : "0");
        hashMap.put(S33_REMANPOWER, DeviceInfo2.getBattery(context));
        hashMap.put(S34_FREEMEMORY, DeviceInfo2.getMemFreeSize(context));
        return hashMap;
    }
}
