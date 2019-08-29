package com.alipay.apmobilesecuritysdk.model;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.apdidgen.ApdidManager;
import com.alipay.apmobilesecuritysdk.apdidgen.ApdidNativeBridge;
import com.alipay.apmobilesecuritysdk.commonbiz.InjectScanUtil;
import com.alipay.apmobilesecuritysdk.commonbiz.InjectScanUtil.InjectScanResult;
import com.alipay.apmobilesecuritysdk.storage.DeviceInfoStorage;
import com.alipay.apmobilesecuritysdk.storage.DeviceInfoStorageModel;
import com.alipay.apmobilesecuritysdk.type.DevType;
import com.alipay.apmobilesecuritysdk.type.DevTypeByteArray;
import com.alipay.apmobilesecuritysdk.type.DevTypeInt;
import com.alipay.apmobilesecuritysdk.type.DevTypeLong;
import com.alipay.apmobilesecuritysdk.type.DevTypeString;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.deviceinfo.DeviceInfo;
import java.util.HashMap;
import java.util.Map;

public class DeviceInfoModel {
    public static Map<String, DevType<?>> a(Context context) {
        DeviceInfo instance = DeviceInfo.getInstance();
        HashMap hashMap = new HashMap();
        DeviceInfoStorageModel a = DeviceInfoStorage.a(context);
        String imei = instance.getIMEI(context);
        String imsi = instance.getIMSI(context);
        String mACAddress = instance.getMACAddress(context);
        String bluMac = instance.getBluMac(context);
        String androidID = instance.getAndroidID(context);
        boolean z = a == null || !CommonUtils.equals(a.a(), imei) || !CommonUtils.equals(a.b(), imsi) || !CommonUtils.equals(a.c(), mACAddress) || !CommonUtils.equals(a.d(), bluMac) || !CommonUtils.equals(a.e(), androidID);
        if (a != null) {
            if (CommonUtils.isBlank(imei)) {
                imei = a.a();
            }
            if (CommonUtils.isBlank(imsi)) {
                imsi = a.b();
            }
            if (CommonUtils.isBlank(mACAddress)) {
                mACAddress = a.c();
            }
            if (CommonUtils.isBlank(bluMac)) {
                bluMac = a.d();
            }
            if (CommonUtils.isBlank(androidID)) {
                androidID = a.e();
            }
        }
        String str = mACAddress;
        String str2 = bluMac;
        byte[] convertMacAddressFromStringToBytes = CommonUtils.convertMacAddressFromStringToBytes(str);
        byte[] convertMacAddressFromStringToBytes2 = CommonUtils.convertMacAddressFromStringToBytes(str2);
        if (z) {
            DeviceInfoStorageModel deviceInfoStorageModel = new DeviceInfoStorageModel(imei, imsi, str, str2, androidID);
            DeviceInfoStorage.a(context, deviceInfoStorageModel);
        }
        hashMap.put("AD1", new DevTypeString(imei));
        hashMap.put("AD2", new DevTypeString(imsi));
        hashMap.put("AD3", new DevTypeByteArray(instance.getSensorDigest(context)));
        hashMap.put("AD6", new DevTypeInt(Integer.valueOf(instance.getScreenWidth(context))));
        hashMap.put("AD7", new DevTypeInt(Integer.valueOf(instance.getScreenHeight(context))));
        hashMap.put("AD8", new DevTypeByteArray(convertMacAddressFromStringToBytes));
        hashMap.put("AD9", new DevTypeString(instance.getSIMSerial(context)));
        hashMap.put("AD10", new DevTypeString(androidID));
        hashMap.put("AD11", new DevTypeString(instance.getCPUSerial()));
        hashMap.put("AD12", new DevTypeInt(Integer.valueOf(instance.getCpuCount())));
        hashMap.put("AD13", new DevTypeString(instance.getCpuFrequent()));
        hashMap.put("AD14", new DevTypeLong(Long.valueOf(instance.getMemorySize())));
        hashMap.put("AD15", new DevTypeLong(Long.valueOf(instance.getTotalInternalMemorySize())));
        hashMap.put("AD16", new DevTypeLong(Long.valueOf(instance.getSDCardSize())));
        hashMap.put("AD18", new DevTypeByteArray(convertMacAddressFromStringToBytes2));
        hashMap.put("AD20", new DevTypeString(instance.getBandVer()));
        hashMap.put("AD21", new DevTypeString(""));
        hashMap.put("AD23", new DevTypeString(instance.getSerialNumber()));
        hashMap.put("AD24", new DevTypeByteArray(CommonUtils.gzipCompress(instance.getSensorInfo(context))));
        hashMap.put("AD26", new DevTypeString(instance.getNetworkOperatorName(context)));
        hashMap.put("AD27", new DevTypeByteArray(instance.getEmulatorFilesExistFeature(context)));
        hashMap.put("AD28", new DevTypeByteArray(instance.getEmulatorFilesContentFeature()));
        hashMap.put("AD29", new DevTypeByteArray(instance.getEmulatorSystemPropertiesFeature()));
        hashMap.put("AD30", new DevTypeByteArray(instance.getEmulatorSdkClassFeature()));
        hashMap.put("AD31", new DevTypeByteArray(instance.getEmulatorBuildClassFeature()));
        hashMap.put("AD34", new DevTypeByteArray(instance.getSystemLockScreenStatus(context)));
        hashMap.put("AD37", new DevTypeString(instance.getTimeZone()));
        hashMap.put("AD38", new DevTypeString(instance.getLanguage()));
        hashMap.put("AD39", new DevTypeString(instance.getAirplaneMode(context)));
        return hashMap;
    }

    public static Map<String, DevType<?>> b(Context context) {
        DeviceInfo instance = DeviceInfo.getInstance();
        HashMap hashMap = new HashMap();
        hashMap.put("AD19", new DevTypeString(instance.getNetworkType(context)));
        hashMap.put("AD32", new DevTypeLong(Long.valueOf(instance.getSystemBootTime())));
        hashMap.put("AD33", new DevTypeLong(Long.valueOf(instance.getSystemBootTimeLength())));
        hashMap.put("AD35", new DevTypeByteArray(instance.getBatteryLevelAndStatus(context)));
        hashMap.put("AD36", new DevTypeString(instance.getNetworkTypeAndIpAddress(context)));
        hashMap.put("AD40", new DevTypeByteArray(instance.getSystemVolumeSetting(context)));
        hashMap.put("AD41", new DevTypeLong(Long.valueOf(instance.getAvailableInternalMemorySize())));
        hashMap.put("AD42", new DevTypeLong(Long.valueOf(instance.getAvailableSDCardSize())));
        hashMap.put("AD43", new DevTypeString(instance.getSupportedInstructionSets()));
        try {
            InjectScanResult a = InjectScanUtil.a(context);
            if (a != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(a.a);
                sb.append(":");
                sb.append(a.b);
                hashMap.put("AD48", new DevTypeString(sb.toString()));
            }
        } catch (Throwable unused) {
        }
        DeviceInfo.getInstance();
        hashMap.put("AD49", new DevTypeString(String.valueOf(DeviceInfo.isAllowMockLocation(context))));
        hashMap.put("AL3", new DevTypeByteArray(CommonUtils.convertMacAddressFromStringToBytes(instance.getWifiBssid(context))));
        hashMap.put("AD100", new DevTypeString(instance.getCPUHardware()));
        String deviceInfo = ApdidNativeBridge.getDeviceInfo("", ApdidManager.a);
        if (CommonUtils.isNotBlank(deviceInfo)) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb2 = new StringBuilder("taDeviceInfo len ");
            sb2.append(deviceInfo.length());
            f.b((String) CONST.LOG_TAG, sb2.toString());
            hashMap.put("AD101", new DevTypeString(deviceInfo));
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(context.getFilesDir());
        sb3.append("/sc_edge/");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb4);
        sb5.append("DATA0.db|");
        sb5.append(sb4);
        sb5.append("DATA1.db");
        String sb6 = sb5.toString();
        String absolutePath = context.getDatabasePath("alipayclient.db").getAbsolutePath();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(sb6);
        sb7.append(MergeUtil.SEPARATOR_KV);
        sb7.append(absolutePath);
        String sb8 = sb7.toString();
        LoggerFactory.f().b((String) CONST.LOG_TAG, "checkFileNames ".concat(String.valueOf(sb8)));
        String fileStat = ApdidNativeBridge.getFileStat(sb8, ApdidManager.a);
        if (CommonUtils.isNotBlank(fileStat)) {
            LoggerFactory.f().b((String) CONST.LOG_TAG, "taFileStat ".concat(String.valueOf(fileStat)));
            hashMap.put("AD50", new DevTypeString(fileStat));
        }
        return hashMap;
    }
}
