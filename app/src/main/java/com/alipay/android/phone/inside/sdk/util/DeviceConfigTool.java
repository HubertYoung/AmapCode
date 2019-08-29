package com.alipay.android.phone.inside.sdk.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.taobao.accs.common.Constants;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class DeviceConfigTool {
    public static String buildEnvInfo(Context context) {
        int i;
        JSONObject jSONObject = new JSONObject();
        if (SDKStaticConfig.isCollectEnvInfoDegrade(context)) {
            return jSONObject.toString();
        }
        boolean z = false;
        try {
            Method[] methods = Class.forName("android.support.v4.content.ContextCompat").getMethods();
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            int length = methods.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Method method = methods[i2];
                if (method.getName().contains("checkSelfPermission")) {
                    if (((Integer) method.invoke(null, new Object[]{context, "android.permission.ACCESS_COARSE_LOCATION"})).intValue() == 0) {
                        jSONObject.put("lbsOpen", locationManager.isProviderEnabled(WidgetType.GPS));
                    }
                } else {
                    i2++;
                }
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(WidgetType.GPS);
            if (lastKnownLocation != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(CameraControllerManager.MY_POILOCATION_ACR, (double) lastKnownLocation.getAccuracy());
                jSONObject2.put("altitude", lastKnownLocation.getAltitude());
                jSONObject2.put("bearing", (double) lastKnownLocation.getBearing());
                jSONObject2.put("latitude", lastKnownLocation.getLatitude());
                jSONObject2.put("longitude", lastKnownLocation.getLongitude());
                jSONObject2.put("speed", (double) lastKnownLocation.getSpeed());
                jSONObject2.put("time", lastKnownLocation.getTime());
                jSONObject.put("lbsInfo", jSONObject2);
            }
        } catch (Throwable th) {
            LoggerFactory.f().c(DeviceConfigTool.class.getName(), th);
        }
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter == null) {
                jSONObject.put("bluetoothOpen", "fasle");
            } else if (defaultAdapter.isEnabled()) {
                jSONObject.put("bluetoothOpen", "true");
                jSONObject.put("bluetoothMac", defaultAdapter.getAddress());
            } else {
                jSONObject.put("bluetoothOpen", "fasle");
            }
        } catch (Throwable th2) {
            LoggerFactory.f().c(DeviceConfigTool.class.getName(), th2);
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        try {
            Method[] methods2 = Class.forName("android.support.v4.content.ContextCompat").getMethods();
            int length2 = methods2.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length2) {
                    i = 0;
                    break;
                }
                Method method2 = methods2[i3];
                if (method2.getName().contains("checkSelfPermission")) {
                    i = ((Integer) method2.invoke(null, new Object[]{context, "android.permission.ACCESS_COARSE_LOCATION"})).intValue();
                    break;
                }
                i3++;
            }
            if (i == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(telephonyManager.getDataState() == 2);
                jSONObject.put("cellConn", sb.toString());
                CellLocation cellLocation = telephonyManager.getCellLocation();
                if (cellLocation != null && (cellLocation instanceof GsmCellLocation)) {
                    jSONObject.put("cellType", "0");
                    buildGsm(jSONObject, (GsmCellLocation) cellLocation, telephonyManager);
                } else if (cellLocation != null && (cellLocation instanceof CdmaCellLocation)) {
                    jSONObject.put("cellType", "1");
                    buildCdma(jSONObject, (CdmaCellLocation) cellLocation);
                }
                jSONObject.put("simOperator", telephonyManager.getSimOperator());
            }
        } catch (Throwable th3) {
            LoggerFactory.f().c(DeviceConfigTool.class.getName(), th3);
        }
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObject3 = new JSONObject();
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                String bssid = connectionInfo.getBSSID();
                if (!TextUtils.isEmpty(bssid)) {
                    if (bssid.indexOf(58) >= 0) {
                        jSONObject3.put("wifiMac", connectionInfo.getBSSID());
                        jSONObject3.put("ssid", connectionInfo.getSSID());
                        jSONObject3.put("rssi", (double) connectionInfo.getRssi());
                    }
                }
                jSONObject3.put("wifiMac", "no_connected_wifi_0");
                jSONObject3.put("ssid", "no_connected_wifi_0");
            } else {
                jSONObject3.put("wifiMac", "no_connected_wifi");
                jSONObject3.put("ssid", "no_connected_wifi");
            }
            jSONArray.put(jSONObject3);
            List<ScanResult> scanResults = wifiManager.getScanResults();
            if (scanResults != null && !scanResults.isEmpty()) {
                int i4 = 5;
                if (scanResults.size() <= 5) {
                    i4 = scanResults.size();
                }
                for (int i5 = 0; i5 < i4; i5++) {
                    ScanResult scanResult = scanResults.get(i5);
                    String str = scanResult.BSSID;
                    if (!TextUtils.isEmpty(str) && str.indexOf(58) >= 0) {
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject4.put("wifiMac", scanResult.BSSID);
                        jSONObject4.put("ssid", scanResult.SSID);
                        jSONObject4.put("rssi", (double) scanResult.level);
                        jSONArray.put(jSONObject4);
                    }
                }
            }
            if (wifiManager.getConnectionInfo() != null) {
                z = true;
            }
            jSONObject.put("isWifiConn", z);
            jSONObject.put("wifis", jSONArray);
        } catch (Throwable th4) {
            LoggerFactory.f().c(DeviceConfigTool.class.getName(), th4);
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (1 == activeNetworkInfo.getType()) {
                    jSONObject.put("netType", activeNetworkInfo.getTypeName());
                } else {
                    jSONObject.put("netType", activeNetworkInfo.getSubtypeName());
                }
            }
        } catch (Throwable th5) {
            LoggerFactory.f().c(DeviceConfigTool.class.getName(), th5);
        }
        return jSONObject.toString();
    }

    private static void buildCdma(JSONObject jSONObject, CdmaCellLocation cdmaCellLocation) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("bsid", cdmaCellLocation.getBaseStationId());
            jSONObject2.put("nid", cdmaCellLocation.getNetworkId());
            jSONObject2.put("rssi", "-1");
            jSONObject2.put(Constants.KEY_SID, cdmaCellLocation.getSystemId());
            LinkedList linkedList = new LinkedList();
            linkedList.add(jSONObject2);
            jSONObject.put("cdmaInfos", linkedList);
        } catch (Throwable th) {
            LoggerFactory.f().c(DeviceConfigTool.class.getName(), th);
        }
    }

    private static void buildGsm(JSONObject jSONObject, GsmCellLocation gsmCellLocation, TelephonyManager telephonyManager) {
        int i;
        int i2;
        try {
            String networkOperator = telephonyManager.getNetworkOperator();
            int i3 = 5;
            if (TextUtils.isEmpty(networkOperator) || networkOperator.length() < 5) {
                i2 = -1;
                i = -1;
            } else {
                i = Integer.parseInt(networkOperator.substring(0, 3));
                i2 = Integer.parseInt(networkOperator.substring(3));
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("mnc", i2);
            jSONObject2.put("mcc", i);
            jSONObject2.put("cid", gsmCellLocation.getCid());
            jSONObject2.put("lac", gsmCellLocation.getLac());
            jSONObject2.put("rssi", -1);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject2);
            List neighboringCellInfo = telephonyManager.getNeighboringCellInfo();
            if (neighboringCellInfo != null && !neighboringCellInfo.isEmpty()) {
                if (neighboringCellInfo.size() <= 5) {
                    i3 = neighboringCellInfo.size();
                }
                for (int i4 = 0; i4 < i3; i4++) {
                    NeighboringCellInfo neighboringCellInfo2 = (NeighboringCellInfo) neighboringCellInfo.get(i4);
                    if (neighboringCellInfo2 != null) {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("mnc", i2);
                        jSONObject3.put("mcc", i);
                        jSONObject3.put("cid", neighboringCellInfo2.getCid());
                        jSONObject3.put("lac", neighboringCellInfo2.getLac());
                        jSONObject3.put("rssi", neighboringCellInfo2.getRssi());
                        jSONArray.put(jSONObject3);
                    }
                }
            }
            jSONObject.put("gsmInfos", jSONArray);
        } catch (Throwable th) {
            LoggerFactory.f().c(DeviceConfigTool.class.getName(), th);
        }
    }
}
