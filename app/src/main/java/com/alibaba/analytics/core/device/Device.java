package com.alibaba.analytics.core.device;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.network.NetworkUtil;
import com.alibaba.analytics.utils.Base64_2;
import com.alibaba.analytics.utils.PhoneInfoUtils2;
import com.alibaba.analytics.utils.StringUtils;
import com.alibaba.analytics.utils.SystemUtils;
import com.alipay.android.phone.a.a.a;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.ut.device.UTDevice;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.TimeZone;

public class Device {
    private static String PATH_AND_BIN_MONKEY = "/system/bin/monkey";
    private static String PATH_AND_BIN_SETPROP = "/system/bin/setprop";
    private static DeviceInfo mDeviceInfo;

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void saveDeviceMetadataToNewPPC(android.content.Context r2, java.lang.String r3, java.lang.String r4) {
        /*
            if (r2 == 0) goto L_0x004b
            boolean r0 = com.alibaba.analytics.utils.StringUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x004b
            boolean r0 = com.alibaba.analytics.utils.StringUtils.isEmpty(r4)
            if (r0 != 0) goto L_0x004b
            com.alibaba.analytics.core.device.PersistentConfiguration r2 = com.alibaba.analytics.core.device.HardConfig.getNewDevicePersistentConfig(r2)
            if (r2 == 0) goto L_0x004b
            r0 = 0
            java.lang.String r1 = "UTF-8"
            byte[] r3 = r3.getBytes(r1)     // Catch:{ UnsupportedEncodingException -> 0x002c }
            java.lang.String r3 = com.alibaba.analytics.utils.Base64_2.encodeBase64String(r3)     // Catch:{ UnsupportedEncodingException -> 0x002c }
            java.lang.String r1 = "UTF-8"
            byte[] r4 = r4.getBytes(r1)     // Catch:{ UnsupportedEncodingException -> 0x002a }
            java.lang.String r4 = com.alibaba.analytics.utils.Base64_2.encodeBase64String(r4)     // Catch:{ UnsupportedEncodingException -> 0x002a }
            goto L_0x0032
        L_0x002a:
            r4 = move-exception
            goto L_0x002e
        L_0x002c:
            r4 = move-exception
            r3 = r0
        L_0x002e:
            r4.printStackTrace()
            r4 = r0
        L_0x0032:
            boolean r0 = com.alibaba.analytics.utils.StringUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x004b
            boolean r0 = com.alibaba.analytics.utils.StringUtils.isEmpty(r4)
            if (r0 != 0) goto L_0x004b
            java.lang.String r0 = "EI"
            r2.putString(r0, r3)
            java.lang.String r3 = "SI"
            r2.putString(r3, r4)
            r2.commit()
        L_0x004b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.device.Device.saveDeviceMetadataToNewPPC(android.content.Context, java.lang.String, java.lang.String):void");
    }

    private static void _checkIMEISI(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Alvin3", 0);
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("UTCommon", 0);
        if (sharedPreferences2 != null && sharedPreferences != null) {
            String string = sharedPreferences.getString("EI", null);
            String string2 = sharedPreferences.getString("SI", null);
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                String string3 = sharedPreferences2.getString("EI", null);
                String string4 = sharedPreferences2.getString("SI", null);
                if (!string.equals(string3)) {
                    Editor edit = sharedPreferences2.edit();
                    edit.putString("EI", string);
                    edit.commit();
                }
                if (!string2.equals(string4)) {
                    Editor edit2 = sharedPreferences2.edit();
                    edit2.putString("SI", string2);
                    edit2.commit();
                }
            }
        }
    }

    static DeviceInfo getDeviceMetadataFromPPC(Context context) {
        String str;
        String str2;
        String str3;
        String str4;
        if (context != null) {
            _checkIMEISI(context);
            PersistentConfiguration newDevicePersistentConfig = HardConfig.getNewDevicePersistentConfig(context);
            String str5 = null;
            if (newDevicePersistentConfig != null) {
                String string = newDevicePersistentConfig.getString("EI");
                String string2 = newDevicePersistentConfig.getString("SI");
                if (!StringUtils.isEmpty(string) && !StringUtils.isEmpty(string2) && !StringUtils.isEmpty(string)) {
                    try {
                        str3 = new String(Base64_2.decode(string.getBytes("UTF-8")), "UTF-8");
                        try {
                            str2 = new String(Base64_2.decode(string2.getBytes("UTF-8")), "UTF-8");
                            try {
                                str4 = new String(Base64_2.decode(string.getBytes("UTF-8")), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e = e;
                            } catch (IOException e2) {
                                e = e2;
                                e.printStackTrace();
                                str4 = null;
                                DeviceInfo deviceInfo = new DeviceInfo();
                                deviceInfo.setDeviceId(str4);
                                deviceInfo.setImei(str3);
                                deviceInfo.setImsi(str2);
                                return deviceInfo;
                            }
                        } catch (UnsupportedEncodingException e3) {
                            e = e3;
                            str2 = null;
                            e.printStackTrace();
                            str4 = null;
                            DeviceInfo deviceInfo2 = new DeviceInfo();
                            deviceInfo2.setDeviceId(str4);
                            deviceInfo2.setImei(str3);
                            deviceInfo2.setImsi(str2);
                            return deviceInfo2;
                        } catch (IOException e4) {
                            e = e4;
                            str2 = null;
                            e.printStackTrace();
                            str4 = null;
                            DeviceInfo deviceInfo22 = new DeviceInfo();
                            deviceInfo22.setDeviceId(str4);
                            deviceInfo22.setImei(str3);
                            deviceInfo22.setImsi(str2);
                            return deviceInfo22;
                        }
                    } catch (UnsupportedEncodingException e5) {
                        e = e5;
                        str3 = null;
                        str2 = null;
                        e.printStackTrace();
                        str4 = null;
                        DeviceInfo deviceInfo222 = new DeviceInfo();
                        deviceInfo222.setDeviceId(str4);
                        deviceInfo222.setImei(str3);
                        deviceInfo222.setImsi(str2);
                        return deviceInfo222;
                    } catch (IOException e6) {
                        e = e6;
                        str3 = null;
                        str2 = null;
                        e.printStackTrace();
                        str4 = null;
                        DeviceInfo deviceInfo2222 = new DeviceInfo();
                        deviceInfo2222.setDeviceId(str4);
                        deviceInfo2222.setImei(str3);
                        deviceInfo2222.setImsi(str2);
                        return deviceInfo2222;
                    }
                    if (!StringUtils.isEmpty(str3) && !StringUtils.isEmpty(str2) && !StringUtils.isEmpty(str4)) {
                        DeviceInfo deviceInfo22222 = new DeviceInfo();
                        deviceInfo22222.setDeviceId(str4);
                        deviceInfo22222.setImei(str3);
                        deviceInfo22222.setImsi(str2);
                        return deviceInfo22222;
                    }
                }
            }
            PersistentConfiguration devicePersistentConfig = HardConfig.getDevicePersistentConfig(context);
            if (devicePersistentConfig != null) {
                String string3 = devicePersistentConfig.getString("EI");
                String string4 = devicePersistentConfig.getString("SI");
                String string5 = devicePersistentConfig.getString("DID");
                if (!StringUtils.isEmpty(string3) && !StringUtils.isEmpty(string4)) {
                    try {
                        str = new String(Base64_2.decode(string3.getBytes("UTF-8")), "UTF-8");
                        try {
                            str5 = new String(Base64_2.decode(string4.getBytes("UTF-8")), "UTF-8");
                        } catch (UnsupportedEncodingException e7) {
                            e = e7;
                            e.printStackTrace();
                            DeviceInfo deviceInfo3 = new DeviceInfo();
                            deviceInfo3.setDeviceId(string5);
                            deviceInfo3.setImei(string3);
                            deviceInfo3.setImsi(string4);
                            saveDeviceMetadataToNewPPC(context, str, str5);
                            return deviceInfo3;
                        } catch (IOException e8) {
                            e = e8;
                            e.printStackTrace();
                            DeviceInfo deviceInfo32 = new DeviceInfo();
                            deviceInfo32.setDeviceId(string5);
                            deviceInfo32.setImei(string3);
                            deviceInfo32.setImsi(string4);
                            saveDeviceMetadataToNewPPC(context, str, str5);
                            return deviceInfo32;
                        }
                    } catch (UnsupportedEncodingException e9) {
                        e = e9;
                        str = null;
                        e.printStackTrace();
                        DeviceInfo deviceInfo322 = new DeviceInfo();
                        deviceInfo322.setDeviceId(string5);
                        deviceInfo322.setImei(string3);
                        deviceInfo322.setImsi(string4);
                        saveDeviceMetadataToNewPPC(context, str, str5);
                        return deviceInfo322;
                    } catch (IOException e10) {
                        e = e10;
                        str = null;
                        e.printStackTrace();
                        DeviceInfo deviceInfo3222 = new DeviceInfo();
                        deviceInfo3222.setDeviceId(string5);
                        deviceInfo3222.setImei(string3);
                        deviceInfo3222.setImsi(string4);
                        saveDeviceMetadataToNewPPC(context, str, str5);
                        return deviceInfo3222;
                    }
                    DeviceInfo deviceInfo32222 = new DeviceInfo();
                    deviceInfo32222.setDeviceId(string5);
                    deviceInfo32222.setImei(string3);
                    deviceInfo32222.setImsi(string4);
                    saveDeviceMetadataToNewPPC(context, str, str5);
                    return deviceInfo32222;
                }
            }
        }
        DeviceInfo deviceInfo4 = new DeviceInfo();
        String imei = PhoneInfoUtils2.getImei(context);
        String imsi = PhoneInfoUtils2.getImsi(context);
        deviceInfo4.setImei(imei);
        deviceInfo4.setImsi(imsi);
        deviceInfo4.setDeviceId(imei);
        saveDeviceMetadataToNewPPC(context, imei, imsi);
        return deviceInfo4;
    }

    private static DeviceInfo _initDeviceMetadata(Context context) {
        if (context == null) {
            return null;
        }
        DeviceInfo deviceMetadataFromPPC = getDeviceMetadataFromPPC(context);
        deviceMetadataFromPPC.setUtdid(UTDevice.getUtdid(context));
        if (StringUtils.isEmpty(deviceMetadataFromPPC.getImei())) {
            deviceMetadataFromPPC.setImei(PhoneInfoUtils2.getImei(context));
        }
        if (StringUtils.isEmpty(deviceMetadataFromPPC.getImsi())) {
            deviceMetadataFromPPC.setImsi(PhoneInfoUtils2.getImsi(context));
        }
        return deviceMetadataFromPPC;
    }

    public static synchronized DeviceInfo getDevice(Context context) {
        synchronized (Device.class) {
            if (mDeviceInfo != null) {
                DeviceInfo deviceInfo = mDeviceInfo;
                return deviceInfo;
            } else if (context == null) {
                return null;
            } else {
                DeviceInfo _initDeviceMetadata = _initDeviceMetadata(context);
                if (_initDeviceMetadata != null) {
                    try {
                        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                        if (telephonyManager == null) {
                            return null;
                        }
                        _initDeviceMetadata.setDeviceModel(Build.MODEL);
                        try {
                            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                            String str = packageInfo.versionName;
                            _initDeviceMetadata.setVersionCode(String.valueOf(packageInfo.versionCode));
                            _initDeviceMetadata.setAppVersion(str);
                        } catch (Exception unused) {
                            _initDeviceMetadata.setVersionCode("Unknown");
                            _initDeviceMetadata.setAppVersion("Unknown");
                        }
                        _initDeviceMetadata.setBrand(Build.BRAND);
                        _initDeviceMetadata.setOsName(a.a);
                        if (isYunOSSystem()) {
                            _initDeviceMetadata.setOsName("aliyunos");
                        }
                        _initDeviceMetadata.setOsVersion(VERSION.RELEASE);
                        Configuration configuration = new Configuration();
                        System.getConfiguration(context.getContentResolver(), configuration);
                        if (configuration.locale != null) {
                            _initDeviceMetadata.setCountry(configuration.locale.getCountry());
                            _initDeviceMetadata.setLanguage(configuration.locale.toString());
                            Calendar instance = Calendar.getInstance(configuration.locale);
                            if (instance != null) {
                                TimeZone timeZone = instance.getTimeZone();
                                if (timeZone != null) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(timeZone.getRawOffset() / 3600000);
                                    _initDeviceMetadata.setTimezone(sb.toString());
                                } else {
                                    _initDeviceMetadata.setTimezone("8");
                                }
                            } else {
                                _initDeviceMetadata.setTimezone("8");
                            }
                        } else {
                            _initDeviceMetadata.setCountry("Unknown");
                            _initDeviceMetadata.setLanguage("Unknown");
                            _initDeviceMetadata.setTimezone("8");
                        }
                        try {
                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
                            int i = displayMetrics.widthPixels;
                            int i2 = displayMetrics.heightPixels;
                            _initDeviceMetadata.setScreenWidth(i);
                            _initDeviceMetadata.setScreenHeight(i2);
                            if (i > i2) {
                                int i3 = i ^ i2;
                                i2 ^= i3;
                                i = i3 ^ i2;
                            }
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(i2);
                            sb2.append("*");
                            sb2.append(i);
                            _initDeviceMetadata.setResolution(sb2.toString());
                        } catch (Exception unused2) {
                            _initDeviceMetadata.setResolution("Unknown");
                        }
                        _initDeviceMetadata.setAccess(NetworkUtil.getAccess(Variables.getInstance().getContext()));
                        _initDeviceMetadata.setAccessSubType(NetworkUtil.getAccess(Variables.getInstance().getContext()));
                        String networkOperatorName = telephonyManager.getNetworkOperatorName();
                        if (StringUtils.isEmpty(networkOperatorName)) {
                            networkOperatorName = "";
                        }
                        _initDeviceMetadata.setCarrier(networkOperatorName);
                        _initDeviceMetadata.setCpu(SystemUtils.getCpuInfo());
                        _initDeviceMetadata.setBinTime(String.valueOf(new File(PATH_AND_BIN_SETPROP).lastModified()).concat("_").concat(String.valueOf(new File(PATH_AND_BIN_MONKEY).lastModified())));
                        if (VERSION.SDK_INT >= 9) {
                            _initDeviceMetadata.setSerialNo(getSerialNo());
                        }
                    } catch (Exception unused3) {
                        return null;
                    }
                }
                mDeviceInfo = _initDeviceMetadata;
                return _initDeviceMetadata;
            }
        }
    }

    @TargetApi(9)
    private static String getSerialNo() {
        try {
            return Build.SERIAL;
        } catch (Throwable unused) {
            return "";
        }
    }

    private static boolean isYunOSSystem() {
        return (System.getProperty("java.vm.name") != null && System.getProperty("java.vm.name").toLowerCase().contains("lemur")) || System.getProperty("ro.yunos.version") != null;
    }
}
