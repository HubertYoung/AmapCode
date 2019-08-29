package com.alipay.android.phone.bluetoothsdk;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import java.util.ArrayList;
import java.util.List;

@TargetApi(18)
public class H5BlePlugin extends H5SimplePlugin {
    private static final String[] BLUETOOTH_STATE_STR = {CameraParams.FLASH_MODE_ON, CameraParams.FLASH_MODE_OFF, "resetting", "unauthorized", "unknown"};
    public static final String CLOSE_APDEVICE_LIB = "closeAPDeviceLib";
    public static final String CONFIG_APDEVICE_LIB = "configAPDeviceLib";
    public static final String CONNECT_APDEVICE = "connectAPDevice";
    public static final String DISCONNECT_APDEVICE = "disconnectAPDevice";
    public static final String GET_APDEVICE_INFOS = "getAPDeviceInfos";
    public static final String ON_APDEVICE_BLUETOOTH_STATE_CHANGE = "onAPDeviceBluetoothStateChange";
    public static final String ON_APDEVICE_STATE_CHANGE = "onAPDeviceStateChange";
    public static final String ON_RECEIVE_DATA_FROM_APDEVICE = "onReceiveDataFromAPDevice";
    public static final String ON_SCAN_APDEVICE_RESULT = "onScanAPDeviceResult";
    public static final String ON_SEND_DATA_TO_APDEVICE = "onSendDataToAPDevice";
    public static final String OPEN_APDEVICE_LIB = "openAPDeviceLib";
    private static final String RESULT_FAIL = "fail";
    private static final String RESULT_KEY_DEVICEID = "deviceId";
    private static final String RESULT_KEY_DEVICEINFOS = "deviceInfos";
    private static final String RESULT_KEY_DEVICENAME = "deviceName";
    private static final String RESULT_KEY_LOCALNAME = "localName";
    private static final String RESULT_KEY_MACADDR = "macAddr";
    private static final String RESULT_KEY_MANUFACTURERDATA = "manufacturerData";
    private static final String RESULT_KEY_RSSI = "RSSI";
    private static final String RESULT_KEY_SERVICEUUIDS = "serviceUUIDs";
    private static final String RESULT_KEY_STATUS = "status";
    private static final String RESULT_NO = "no";
    private static final String RESULT_OK = "ok";
    private static final String RESULT_YES = "yes";
    public static final String SEND_DATA_TO_APDEVICE = "sendDataToAPDevice";
    public static final String START_SCAN_APDEVICE = "startScanAPDevice";
    public static final String STOP_SCAN_APDEVICE = "stopScanAPDevice";
    public static final String TAG = "H5BlePlugin";
    public static List<String> list;
    private BleService bleService;
    private DeviceConnectionInterface deviceConnectionInterface = new DeviceConnectionInterface() {
        public void onConnectionStateChange(String deviceId, int state) {
            JSONObject param = new JSONObject();
            param.put((String) "deviceId", (Object) deviceId);
            if (state == 2) {
                param.put((String) "state", (Object) "connected");
            } else if (state == 1) {
                param.put((String) "state", (Object) "connecting");
            } else {
                param.put((String) "state", (Object) "disconnected");
            }
            H5Page h5Page = H5BlePlugin.this.h5Service.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BlePlugin.ON_APDEVICE_STATE_CHANGE, param, null);
            }
        }
    };
    private DeviceDataInterface deviceDataInterface = new DeviceDataInterface() {
        public void onReceiveDataFromDevice(String deviceId, String charUUID, String data) {
            JSONObject param = new JSONObject();
            param.put((String) "deviceId", (Object) deviceId);
            param.put((String) "data", (Object) data);
            param.put((String) "characteristicUUID", (Object) charUUID);
            H5Page h5Page = H5BlePlugin.this.h5Service.getTopH5Page();
            LoggerFactory.getTraceLogger().info(H5BlePlugin.TAG, "receiveDataFromDevice, device:" + deviceId);
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BlePlugin.ON_RECEIVE_DATA_FROM_APDEVICE, param, null);
            }
        }

        public void onSendDataToDevice(String deviceId, String charUUID) {
            JSONObject param = new JSONObject();
            param.put((String) "deviceId", (Object) deviceId);
            param.put((String) "characteristicUUID", (Object) charUUID);
            H5Page h5Page = H5BlePlugin.this.h5Service.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BlePlugin.ON_SEND_DATA_TO_APDEVICE, param, null);
            }
        }
    };
    private DeviceScanInterface deviceScanInterface = new DeviceScanInterface() {
        public void onDeviceFound(BluetoothDevice device, int rssi, String manufacturerData) {
            JSONObject param = new JSONObject();
            param.put((String) "deviceId", (Object) device.getAddress());
            param.put((String) H5BlePlugin.RESULT_KEY_DEVICENAME, (Object) device.getName());
            param.put((String) H5BlePlugin.RESULT_KEY_LOCALNAME, (Object) device.getName());
            param.put((String) H5BlePlugin.RESULT_KEY_RSSI, (Object) Integer.valueOf(rssi));
            param.put((String) H5BlePlugin.RESULT_KEY_SERVICEUUIDS, (Object) device.getUuids());
            param.put((String) H5BlePlugin.RESULT_KEY_MANUFACTURERDATA, (Object) manufacturerData);
            param.put((String) H5BlePlugin.RESULT_KEY_MACADDR, (Object) device.getAddress());
            H5Page h5Page = H5BlePlugin.this.h5Service.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BlePlugin.ON_SCAN_APDEVICE_RESULT, param, null);
            }
        }
    };
    private DeviceStateInterface deviceStateInterface = new DeviceStateInterface() {
        public void onDeviceStateChange(boolean enabled) {
            JSONObject param = new JSONObject();
            param.put((String) "state", (Object) enabled ? CameraParams.FLASH_MODE_ON : CameraParams.FLASH_MODE_OFF);
            H5Page h5Page = H5BlePlugin.this.h5Service.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BlePlugin.ON_APDEVICE_BLUETOOTH_STATE_CHANGE, param, null);
            }
        }
    };
    /* access modifiers changed from: private */
    public H5Service h5Service;

    static {
        ArrayList arrayList = new ArrayList();
        list = arrayList;
        arrayList.add(OPEN_APDEVICE_LIB);
        list.add(CLOSE_APDEVICE_LIB);
        list.add(START_SCAN_APDEVICE);
        list.add(STOP_SCAN_APDEVICE);
        list.add(CONFIG_APDEVICE_LIB);
        list.add(GET_APDEVICE_INFOS);
        list.add(SEND_DATA_TO_APDEVICE);
        list.add(CONNECT_APDEVICE);
        list.add(DISCONNECT_APDEVICE);
    }

    public void onPrepare(H5EventFilter filter) {
        filter.setEventsList(list);
        this.bleService = (BleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(BleService.class.getName());
        this.bleService.setDeviceInterface(this.deviceConnectionInterface, this.deviceDataInterface, this.deviceStateInterface);
        this.h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        LoggerFactory.getTraceLogger().debug(TAG, "handleEvent, event:" + action + ", params:" + event.getParam());
        if (OPEN_APDEVICE_LIB.equals(action)) {
            openApDeviceLib(context);
        } else if (CLOSE_APDEVICE_LIB.equals(action)) {
            closeApDeviceLib(context);
        } else if (START_SCAN_APDEVICE.equals(action)) {
            startScan(context);
        } else if (STOP_SCAN_APDEVICE.equals(action)) {
            stopScan(context);
        } else if (CONFIG_APDEVICE_LIB.equals(action)) {
            configApDeviceLib(event.getParam(), context);
        } else if (GET_APDEVICE_INFOS.equals(action)) {
            getApDeviceInfos(context);
        } else if (CONNECT_APDEVICE.equals(action)) {
            connectApDevice(event.getParam().getString("deviceId"), context);
        } else if (DISCONNECT_APDEVICE.equals(action)) {
            disconnectApDevice(context);
        } else if (SEND_DATA_TO_APDEVICE.equals(action)) {
            sendDataToApdevice(event.getParam().getString("data"), context);
        }
        return true;
    }

    private void openApDeviceLib(H5BridgeContext context) {
        if (this.bleService != null) {
            boolean status = this.bleService.openBluetooth();
            JSONObject result = new JSONObject();
            result.put((String) "status", (Object) getStatusStr(status));
            result.put((String) "bluetoothState", (Object) BLUETOOTH_STATE_STR[this.bleService.getBluetoothState()]);
            result.put((String) "isSupportBLE", (Object) getResultBooleanStr(this.bleService.isSupportBLE()));
            context.sendBridgeResult(result);
            MonitorHelper.logOldH5Funtion();
        }
    }

    private void closeApDeviceLib(H5BridgeContext context) {
        if (this.bleService != null) {
            boolean status = this.bleService.closeBluetooth();
            JSONObject result = new JSONObject();
            result.put((String) "status", (Object) getStatusStr(status));
            context.sendBridgeResult(result);
        }
    }

    private void getApDeviceInfos(H5BridgeContext context) {
        if (this.bleService != null) {
            List deviceList = this.bleService.getDeviceList();
            JSONObject result = new JSONObject();
            result.put((String) "status", (Object) getStatusStr(true));
            result.put((String) RESULT_KEY_DEVICEINFOS, (Object) convertBluetoothDeviceList(deviceList, this.bleService.getConnectedDeviceList()));
            context.sendBridgeResult(result);
            return;
        }
        setFailResultStatus(context);
    }

    private void configApDeviceLib(JSONObject params, H5BridgeContext context) {
        if (this.bleService != null) {
            this.bleService.configDevice(params);
            JSONObject result = new JSONObject();
            result.put((String) "status", (Object) getStatusStr(true));
            context.sendBridgeResult(result);
            return;
        }
        setFailResultStatus(context);
    }

    private void startScan(H5BridgeContext context) {
        if (this.bleService != null) {
            boolean status = this.bleService.startScan(this.deviceScanInterface);
            JSONObject result = new JSONObject();
            result.put((String) "status", (Object) getStatusStr(status));
            context.sendBridgeResult(result);
            return;
        }
        setFailResultStatus(context);
    }

    private void stopScan(H5BridgeContext context) {
        if (this.bleService != null) {
            this.bleService.stopScan();
            JSONObject result = new JSONObject();
            result.put((String) "status", (Object) getStatusStr(true));
            context.sendBridgeResult(result);
            return;
        }
        setFailResultStatus(context);
    }

    private void connectApDevice(String uuid, H5BridgeContext context) {
        if (this.bleService != null) {
            boolean status = this.bleService.connect(uuid);
            JSONObject result = new JSONObject();
            result.put((String) "status", (Object) getStatusStr(status));
            context.sendBridgeResult(result);
            return;
        }
        setFailResultStatus(context);
    }

    private void disconnectApDevice(H5BridgeContext context) {
        if (this.bleService != null) {
            this.bleService.disconnect();
            JSONObject result = new JSONObject();
            result.put((String) "status", (Object) getStatusStr(true));
            context.sendBridgeResult(result);
            return;
        }
        setFailResultStatus(context);
    }

    /* JADX WARNING: type inference failed for: r8v1, types: [java.lang.Integer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendDataToApdevice(java.lang.String r8, com.alipay.mobile.h5container.api.H5BridgeContext r9) {
        /*
            r7 = this;
            com.alipay.android.phone.bluetoothsdk.BleService r2 = r7.bleService
            if (r2 == 0) goto L_0x0048
            com.alipay.android.phone.bluetoothsdk.BleService r2 = r7.bleService
            boolean r1 = r2.sendDataToDevice(r8)
            com.alibaba.fastjson.JSONObject r0 = new com.alibaba.fastjson.JSONObject
            r0.<init>()
            java.lang.String r2 = "status"
            java.lang.String r3 = getStatusStr(r1)
            r0.put(r2, r3)
            r9.sendBridgeResult(r0)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r3 = "H5BlePlugin"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "sendDataToApDevice success, data:"
            r4.<init>(r5)
            if (r8 != 0) goto L_0x0037
            r8 = 0
        L_0x002b:
            java.lang.StringBuilder r4 = r4.append(r8)
            java.lang.String r4 = r4.toString()
            r2.info(r3, r4)
        L_0x0036:
            return
        L_0x0037:
            int r5 = r8.length()
            r6 = 20
            if (r5 <= r6) goto L_0x002b
            int r5 = r8.length()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)
            goto L_0x002b
        L_0x0048:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r3 = "H5BlePlugin"
            java.lang.String r4 = "sendDataToApDevice failed"
            r2.error(r3, r4)
            r7.setFailResultStatus(r9)
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.bluetoothsdk.H5BlePlugin.sendDataToApdevice(java.lang.String, com.alipay.mobile.h5container.api.H5BridgeContext):void");
    }

    private void setFailResultStatus(H5BridgeContext context) {
        JSONObject result = new JSONObject();
        result.put((String) "status", (Object) getStatusStr(false));
        context.sendBridgeResult(result);
    }

    private static String getStatusStr(boolean val) {
        return val ? "ok" : "fail";
    }

    private static String getResultBooleanStr(boolean val) {
        return val ? "yes" : "no";
    }

    private static JSONArray convertBluetoothDeviceList(List<BluetoothDevice> deviceList, List<BluetoothDevice> connectedDeviceList) {
        JSONArray bleDeviceList = new JSONArray();
        if (deviceList != null) {
            for (BluetoothDevice device : deviceList) {
                JSONObject bleDevice = new JSONObject();
                bleDevice.put((String) "deviceId", (Object) device.getAddress());
                if (connectedDeviceList == null || !connectedDeviceList.contains(device)) {
                    bleDevice.put((String) "state", (Object) "disconnected");
                } else {
                    bleDevice.put((String) "state", (Object) "connected");
                }
                bleDeviceList.add(JSON.toJSON(bleDevice));
            }
        }
        LoggerFactory.getTraceLogger().debug(TAG, "bleDeviceList:" + bleDeviceList);
        return bleDeviceList;
    }
}
