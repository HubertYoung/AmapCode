package com.alipay.android.phone.bluetoothsdk.better.ble;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.bluetoothsdk.BluetoothState;
import com.alipay.android.phone.bluetoothsdk.Logger;
import com.alipay.android.phone.bluetoothsdk.MonitorHelper;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TargetApi(18)
public class H5BetterBlePlugin extends H5SimplePlugin {
    private static final String EVENT_BLE_CHARACTERISTIC_VALUE_CHANGE = "BLECharacteristicValueChange";
    private static final String EVENT_BLE_CONNECTION_STATE_CHANGE = "BLEConnectionStateChanged";
    private static final String EVENT_BLUETOOTH_ADAPTER_STATE_CHANGE = "bluetoothAdapterStateChange";
    private static final String EVENT_BLUETOOTH_DEVICE_FOUND = "bluetoothDeviceFound";
    private static final String FUNC_CLOSE_BLUETOOTH_ADAPTER = "closeBluetoothAdapter";
    private static final String FUNC_CONNECT_BLE_DEVICE = "connectBLEDevice";
    private static final String FUNC_DISCONNECT_BLE_DEVICE = "disconnectBLEDevice";
    private static final String FUNC_GET_BLE_DEVICE_CHARACTERISTICS = "getBLEDeviceCharacteristics";
    private static final String FUNC_GET_BLE_DEVICE_SERVICES = "getBLEDeviceServices";
    private static final String FUNC_GET_BLUETOOTH_ADAPTER_STATE = "getBluetoothAdapterState";
    private static final String FUNC_GET_BLUETOOTH_DEVICES = "getBluetoothDevices";
    private static final String FUNC_GET_CONNECTED_BLUETOOTH_DEVICES = "getConnectedBluetoothDevices";
    private static final String FUNC_NOTIFY_BLE_CHARACTERISTIC_VALUE_CHANGE = "notifyBLECharacteristicValueChange";
    private static final String FUNC_OPEN_BLUETOOTH_ADAPTER = "openBluetoothAdapter";
    private static final String FUNC_READ_BLE_CHARACTERISTIC_VALUE = "readBLECharacteristicValue";
    private static final String FUNC_START_BLUETOOTH_DEVICES_DISCOVERY = "startBluetoothDevicesDiscovery";
    private static final String FUNC_STOP_BLUETOOTH_DEVICES_DISCOVERY = "stopBluetoothDevicesDiscovery";
    private static final String FUNC_WRITE_BLE_CHARACTERISTIC_VALUE = "writeBLECharacteristicValue";
    private static final String KEY_ALLOWDUPLICATESKEY = "allowDuplicatesKey";
    private static final String KEY_AUTO_CLOSE_ON_PAGE_OFF = "autoClose";
    private static final String KEY_AVAILABLE = "available";
    private static final String KEY_CHARACTERISTIC = "characteristic";
    private static final String KEY_CHARACTERISTICS = "characteristics";
    private static final String KEY_CHARACTERISTIC_ID = "characteristicId";
    private static final String KEY_CONNECTED = "connected";
    private static final String KEY_DATA = "data";
    private static final String KEY_DESCRIPTOR_ID = "descriptorId";
    private static final String KEY_DEVICES = "devices";
    private static final String KEY_DEVICE_ID = "deviceId";
    private static final String KEY_DISCOVERING = "discovering";
    private static final String KEY_ERROR = "error";
    private static final String KEY_ERROR_MESSAGE = "errorMessage";
    private static final String KEY_INTERVAL = "interval";
    private static final String KEY_IS_SUPPORT_BLE = "isSupportBLE";
    private static final String KEY_SERVICES = "services";
    private static final String KEY_SERVICE_ID = "serviceId";
    private static final String KEY_STATE = "state";
    private static final String KEY_VALUE = "value";
    private static final int MSG_CALLBACK_CONNECT_BLE_DEVICE = 101;
    private static final int MSG_CALLBACK_DISCONNECT_BLE_DEVICE = 102;
    private static final int MSG_CALLBACK_NOTIFY_BLE_CHARACTERISTIC = 105;
    private static final int MSG_CALLBACK_READ_BLE_CHARACTERISTIC = 104;
    private static final int MSG_CALLBACK_WRITE_BLE_CHARACTERISTIC = 103;
    private static final int MSG_DELAY_TIME = 10000;
    private static final int MSG_DELAY_TIME_FOR_NOTIFY = 10000;
    private static final int MSG_OPERATION_HANDLED = 0;
    private static final int MSG_SHIFT = 100;
    private static final int MSG_TIMEOUT_CONNECT_BLE_DEVICE = 1;
    private static final int MSG_TIMEOUT_DISCONNECT_BLE_DEVICE = 2;
    private static final int MSG_TIMEOUT_NOTIFY_BLE_CHARACTERISTIC = 5;
    private static final int MSG_TIMEOUT_READ_BLE_CHARACTERISTIC = 4;
    private static final int MSG_TIMEOUT_WRITE_BLE_CHARACTERISTIC = 3;
    private static final String TAG = "H5BetterBlePlugin";
    private static List<String> list;
    private BetterBleListener betterBleListener = new BetterBleListener() {
        public void onBluetoothAdapterStateChange(boolean available, boolean discovering) {
            LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "onBluetoothAdapterStateChange, enabled:" + available + ",discovering:" + discovering);
            JSONObject param = new JSONObject();
            JSONObject data = new JSONObject();
            data.put((String) H5BetterBlePlugin.KEY_AVAILABLE, (Object) String.valueOf(available));
            data.put((String) H5BetterBlePlugin.KEY_DISCOVERING, (Object) Boolean.valueOf(discovering));
            param.put((String) "data", (Object) data);
            H5Page h5Page = H5BetterBlePlugin.this.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BetterBlePlugin.EVENT_BLUETOOTH_ADAPTER_STATE_CHANGE, param, null);
            }
        }

        public void onBluetoothDeviceFound(List<BleDevice> bleDeviceList) {
            if (bleDeviceList != null) {
                for (BleDevice bleDevice : bleDeviceList) {
                    LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "onBluetoothDeviceFound, deviceId:" + bleDevice.deviceId);
                }
            } else {
                bleDeviceList = new ArrayList<>();
            }
            JSONObject param = new JSONObject();
            JSONObject data = new JSONObject();
            data.put((String) H5BetterBlePlugin.KEY_DEVICES, JSON.toJSON(bleDeviceList));
            param.put((String) "data", (Object) data);
            H5Page h5Page = H5BetterBlePlugin.this.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BetterBlePlugin.EVENT_BLUETOOTH_DEVICE_FOUND, param, null);
            }
        }

        public void onBluetoothConnectionStateChange(String address, boolean connected) {
            LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "onBluetoothConnectionStateChange, address:" + address + ",connected:" + connected);
            LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "thread:" + Thread.currentThread().getName());
            if (connected) {
                H5BetterBlePlugin.this.handler.sendEmptyMessage(101);
            } else {
                H5BetterBlePlugin.this.handler.sendEmptyMessage(102);
            }
            JSONObject param = new JSONObject();
            JSONObject data = new JSONObject();
            data.put((String) "deviceId", (Object) address);
            data.put((String) H5BetterBlePlugin.KEY_CONNECTED, (Object) Boolean.valueOf(connected));
            param.put((String) "data", (Object) data);
            H5Page h5Page = H5BetterBlePlugin.this.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BetterBlePlugin.EVENT_BLE_CONNECTION_STATE_CHANGE, param, null);
            }
        }

        public void onBluetoothCharacteristicValueChange(String address, String serviceUUID, String characteristicUUID, String data) {
            LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "onBluetoothCharacteristicValueChange, address:" + address + ",serviceUUID:" + serviceUUID + "characteristicUUID:" + characteristicUUID + ",data:" + data);
            JSONObject param = new JSONObject();
            JSONObject dataObject = new JSONObject();
            dataObject.put((String) "deviceId", (Object) address);
            dataObject.put((String) "serviceId", (Object) serviceUUID);
            dataObject.put((String) H5BetterBlePlugin.KEY_CHARACTERISTIC_ID, (Object) characteristicUUID);
            dataObject.put((String) "value", (Object) data);
            param.put((String) "data", (Object) dataObject);
            H5Page h5Page = H5BetterBlePlugin.this.getTopH5Page();
            if (h5Page != null) {
                h5Page.getBridge().sendToWeb(H5BetterBlePlugin.EVENT_BLE_CHARACTERISTIC_VALUE_CHANGE, param, null);
            }
        }

        public void onBluetoothCharacteristicRead(String address, String serviceUUID, String characteristicUUID, String data) {
            H5BetterBlePlugin.this.handler.sendMessage(H5BetterBlePlugin.this.handler.obtainMessage(104, BleGattCharacteristic.createCharacteristic(serviceUUID, characteristicUUID, data)));
        }

        public void onBluetoothCharacteristicWrite(String address, String serviceUUID, String characteristicUUID) {
            H5BetterBlePlugin.this.handler.sendEmptyMessage(103);
        }

        public void onBluetoothDescriptorWrite(String address, String serviceUUID, String characteristicUUID, String descriptorUUID) {
            H5BetterBlePlugin.this.handler.sendEmptyMessage(105);
        }
    };
    private BetterBleService bleService;
    /* access modifiers changed from: private */
    public Map<String, List<H5BridgeContext>> h5BridgeContextMap;
    private H5Page h5Page;
    private H5Service h5Service;
    /* access modifiers changed from: private */
    public TaskHandler handler = new TaskHandler(Looper.getMainLooper());
    private List<H5Operation> operationList;
    /* access modifiers changed from: private */
    public List<BleGattCharacteristic> readCharacteristicList;

    class H5Operation {
        H5BridgeContext h5BridgeContext;
        H5Event h5Event;

        public H5Operation(H5Event h5Event2, H5BridgeContext h5BridgeContext2) {
            this.h5Event = h5Event2;
            this.h5BridgeContext = h5BridgeContext2;
        }

        public String toString() {
            return "H5Operation{h5Event=" + this.h5Event + ", h5BridgeContext=" + this.h5BridgeContext + '}';
        }
    }

    class TaskHandler extends Handler {
        public TaskHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String action = H5BetterBlePlugin.this.getActionFromMessage(msg.what);
            LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "TaskHandler, message what:" + msg.what);
            switch (msg.what) {
                case 1:
                case 2:
                case 3:
                case 4:
                    if (msg.obj != null) {
                        H5BridgeContext h5BridgeContext = (H5BridgeContext) msg.obj;
                        JSONObject result = new JSONObject();
                        result.put((String) "error", (Object) ErrorConstants.ERROR_TIMEOUT[0]);
                        result.put((String) "errorMessage", (Object) ErrorConstants.ERROR_TIMEOUT[1]);
                        if (msg.what == 4 && H5BetterBlePlugin.this.readCharacteristicList != null && H5BetterBlePlugin.this.readCharacteristicList.size() > 0) {
                            result.put((String) H5BetterBlePlugin.KEY_CHARACTERISTIC, JSON.toJSON(H5BetterBlePlugin.this.readCharacteristicList.get(0)));
                            H5BetterBlePlugin.this.readCharacteristicList.remove(0);
                        }
                        h5BridgeContext.sendBridgeResult(result);
                        LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "timeout action:" + action + ", H5BridgeContext:" + h5BridgeContext);
                        if (!TextUtils.isEmpty(action) && H5BetterBlePlugin.this.h5BridgeContextMap != null) {
                            List h5BridgeContextList = (List) H5BetterBlePlugin.this.h5BridgeContextMap.get(action);
                            if (h5BridgeContextList != null) {
                                h5BridgeContextList.remove(h5BridgeContext);
                                break;
                            }
                        }
                    }
                    break;
                case 5:
                    if (msg.obj != null) {
                        H5BridgeContext h5BridgeContext2 = (H5BridgeContext) msg.obj;
                        h5BridgeContext2.sendBridgeResult(new JSONObject());
                        LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "notify timeout, H5BridgeContext:" + h5BridgeContext2);
                        if (!TextUtils.isEmpty(action) && H5BetterBlePlugin.this.h5BridgeContextMap != null) {
                            List h5BridgeContextList2 = (List) H5BetterBlePlugin.this.h5BridgeContextMap.get(action);
                            if (h5BridgeContextList2 != null) {
                                h5BridgeContextList2.remove(h5BridgeContext2);
                                break;
                            }
                        }
                    }
                    break;
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                    if (H5BetterBlePlugin.this.h5BridgeContextMap != null) {
                        String relativeAction = H5BetterBlePlugin.this.getActionFromMessage(msg.what - 100);
                        LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "relativeAction:" + relativeAction);
                        List h5BridgeContextList3 = (List) H5BetterBlePlugin.this.h5BridgeContextMap.get(relativeAction);
                        if (h5BridgeContextList3 != null && h5BridgeContextList3.size() > 0) {
                            JSONObject result2 = new JSONObject();
                            if (msg.what == 104) {
                                result2.put((String) H5BetterBlePlugin.KEY_CHARACTERISTIC, JSON.toJSON(msg.obj));
                                if (H5BetterBlePlugin.this.readCharacteristicList != null && H5BetterBlePlugin.this.readCharacteristicList.size() > 0) {
                                    H5BetterBlePlugin.this.readCharacteristicList.remove(0);
                                }
                            }
                            H5BridgeContext h5BridgeContext3 = (H5BridgeContext) h5BridgeContextList3.get(0);
                            h5BridgeContext3.sendBridgeResult(result2);
                            h5BridgeContextList3.remove(0);
                            LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "h5BridgeContext:" + h5BridgeContext3);
                            H5BetterBlePlugin.this.handler.removeMessages(msg.what - 100, h5BridgeContext3);
                            break;
                        } else {
                            LoggerFactory.getTraceLogger().debug(H5BetterBlePlugin.TAG, "h5BridgeContextList is empty");
                            break;
                        }
                    }
                    break;
            }
            H5BetterBlePlugin.this.removeFirstOperationFromList();
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        list = arrayList;
        arrayList.add(FUNC_OPEN_BLUETOOTH_ADAPTER);
        list.add(FUNC_CLOSE_BLUETOOTH_ADAPTER);
        list.add(FUNC_GET_BLUETOOTH_ADAPTER_STATE);
        list.add(FUNC_START_BLUETOOTH_DEVICES_DISCOVERY);
        list.add(FUNC_STOP_BLUETOOTH_DEVICES_DISCOVERY);
        list.add(FUNC_GET_BLUETOOTH_DEVICES);
        list.add(FUNC_GET_CONNECTED_BLUETOOTH_DEVICES);
        list.add(FUNC_CONNECT_BLE_DEVICE);
        list.add(FUNC_DISCONNECT_BLE_DEVICE);
        list.add(FUNC_WRITE_BLE_CHARACTERISTIC_VALUE);
        list.add(FUNC_READ_BLE_CHARACTERISTIC_VALUE);
        list.add(FUNC_GET_BLE_DEVICE_SERVICES);
        list.add(FUNC_GET_BLE_DEVICE_CHARACTERISTICS);
        list.add(FUNC_NOTIFY_BLE_CHARACTERISTIC_VALUE_CHANGE);
    }

    public void onPrepare(H5EventFilter filter) {
        LoggerFactory.getTraceLogger().debug(new StringBuilder(TAG).append(this).toString(), "onPrepare");
        filter.setEventsList(list);
        this.bleService = (BetterBleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(BetterBleService.class.getName());
        this.bleService.setBetterBleListener(this.betterBleListener);
        this.h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
        this.h5BridgeContextMap = new HashMap();
        this.readCharacteristicList = new ArrayList();
        this.operationList = new ArrayList();
    }

    public void onRelease() {
        LoggerFactory.getTraceLogger().debug(new StringBuilder(TAG).append(this).toString(), "onRelease");
        super.onRelease();
        this.h5BridgeContextMap.clear();
        this.h5BridgeContextMap = null;
        this.readCharacteristicList.clear();
        this.readCharacteristicList = null;
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
        }
        this.operationList.clear();
        this.operationList = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        LoggerFactory.getTraceLogger().debug(TAG, "handleEvent, event:" + event.getAction() + ",context:" + context);
        String action = event.getAction();
        this.h5Page = event.getH5page();
        if (FUNC_OPEN_BLUETOOTH_ADAPTER.equals(action)) {
            openBluetoothAdapter(event, context);
        } else if (FUNC_CLOSE_BLUETOOTH_ADAPTER.equals(action)) {
            closeBluetoothAdapter(context);
        } else if (FUNC_GET_BLUETOOTH_ADAPTER_STATE.equals(action)) {
            getBluetoothAdapterState(context);
        } else if (FUNC_START_BLUETOOTH_DEVICES_DISCOVERY.equals(action)) {
            startBluetoothDevicesDiscovery(event.getParam(), context);
        } else if (FUNC_STOP_BLUETOOTH_DEVICES_DISCOVERY.equals(action)) {
            stopBluetoothDevicesDiscovery(context);
        } else if (FUNC_GET_BLUETOOTH_DEVICES.equals(action)) {
            getBluetoothDevices(context);
        } else if (FUNC_GET_CONNECTED_BLUETOOTH_DEVICES.equals(action)) {
            getConnectedBluetoothDevices(context);
        } else if (FUNC_GET_BLE_DEVICE_SERVICES.equals(action)) {
            getBleDeviceServices(event.getParam(), context);
        } else if (FUNC_GET_BLE_DEVICE_CHARACTERISTICS.equals(action)) {
            getBleDeviceCharacteristics(event.getParam(), context);
        } else {
            addToOperationList(event, context);
        }
        return true;
    }

    private void addToOperationList(H5Event event, H5BridgeContext context) {
        if (this.operationList != null) {
            this.operationList.add(new H5Operation(event, context));
            if (this.operationList.size() == 1) {
                handleSynchronizedOperation(this.operationList.get(0));
            }
            Logger.d(TAG, "add operationList:" + this.operationList.size());
        }
    }

    /* access modifiers changed from: private */
    public void removeFirstOperationFromList() {
        if (this.operationList != null && this.operationList.size() > 0) {
            this.operationList.remove(0);
            if (this.operationList.size() > 0) {
                handleSynchronizedOperation(this.operationList.get(0));
            }
            Logger.d(TAG, "remove operationList:" + this.operationList.size());
        }
    }

    private void handleSynchronizedOperation(H5Operation h5Operation) {
        H5Event event = h5Operation.h5Event;
        H5BridgeContext context = h5Operation.h5BridgeContext;
        String action = event.getAction();
        LoggerFactory.getTraceLogger().debug(TAG, "handleSynchronizedOperation, event:" + action + ",context:" + context);
        if (FUNC_CONNECT_BLE_DEVICE.equals(action)) {
            connectBleDevice(action, event.getParam(), context);
        } else if (FUNC_DISCONNECT_BLE_DEVICE.equals(action)) {
            disconnectBleDevice(action, event.getParam(), context);
        } else if (FUNC_WRITE_BLE_CHARACTERISTIC_VALUE.equals(action)) {
            writeBleCharacteristicValue(action, event.getParam(), context);
        } else if (FUNC_READ_BLE_CHARACTERISTIC_VALUE.equals(action)) {
            readBleCharacteristicValue(action, event.getParam(), context);
        } else if (FUNC_NOTIFY_BLE_CHARACTERISTIC_VALUE_CHANGE.equals(action)) {
            notifyBleCharacteristicValueChange(action, event.getParam(), context);
        } else {
            LoggerFactory.getTraceLogger().debug(TAG, "no operation");
        }
    }

    /* access modifiers changed from: private */
    public H5Page getTopH5Page() {
        if (this.h5Page != null && this.h5Page.getBridge() != null) {
            return this.h5Page;
        }
        if (this.h5Service != null) {
            H5BaseFragment h5BaseFragment = this.h5Service.getTopH5BaseFragment();
            if (h5BaseFragment != null) {
                return h5BaseFragment.getH5Page();
            }
        }
        return null;
    }

    private void openBluetoothAdapter(H5Event event, H5BridgeContext context) {
        boolean z;
        clearOperations();
        if (this.bleService != null) {
            boolean isSupportBle = this.bleService.isSupportBLE();
            int bluetoothState = this.bleService.getBluetoothState();
            JSONObject result = new JSONObject();
            result.put((String) KEY_IS_SUPPORT_BLE, (Object) Boolean.valueOf(isSupportBle));
            if (bluetoothState != BluetoothState.ON.ordinal()) {
                result.put((String) "error", (Object) ErrorConstants.ERROR_CODE_ARRAY[bluetoothState - 1]);
                result.put((String) "errorMessage", (Object) ErrorConstants.BLUETOOTH_STATE_STR[bluetoothState - 1]);
            }
            boolean autoClose = true;
            if (event.getParam().containsKey(KEY_AUTO_CLOSE_ON_PAGE_OFF)) {
                autoClose = event.getParam().getBooleanValue(KEY_AUTO_CLOSE_ON_PAGE_OFF);
            }
            if (isSupportBle && bluetoothState == BluetoothState.ON.ordinal()) {
                this.bleService.openBluetoothAdapter(event.getActivity() != null ? event.getActivity().toString() : "", autoClose);
            }
            context.sendBridgeResult(result);
            if (bluetoothState == BluetoothState.ON.ordinal()) {
                z = true;
            } else {
                z = false;
            }
            MonitorHelper.logBluetoothEnabled(z);
        } else {
            sendDefaultErrorBridgeResult(context, false);
        }
        MonitorHelper.logOpenBLEAdapter();
    }

    private void closeBluetoothAdapter(H5BridgeContext context) {
        if (this.bleService != null) {
            this.bleService.closeBluetoothAdapter();
            context.sendBridgeResult(new JSONObject());
        } else {
            sendDefaultErrorBridgeResult(context, false);
        }
        clearOperations();
        MonitorHelper.logCloseBLEAdapter();
    }

    private void clearOperations() {
        if (this.h5BridgeContextMap != null) {
            this.h5BridgeContextMap.clear();
        }
        if (this.readCharacteristicList != null) {
            this.readCharacteristicList.clear();
        }
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
        }
        if (this.operationList != null) {
            this.operationList.clear();
        }
    }

    private void getBluetoothAdapterState(H5BridgeContext context) {
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, false);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, false);
        } else {
            JSONObject result = new JSONObject();
            result.put((String) KEY_DISCOVERING, (Object) Boolean.valueOf(this.bleService.isDiscovering()));
            result.put((String) KEY_AVAILABLE, (Object) Boolean.valueOf(this.bleService.isSupportBLE() && this.bleService.getBluetoothState() == BluetoothState.ON.ordinal()));
            if (!this.bleService.isOpened()) {
                result.put((String) "error", (Object) ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED[0]);
                result.put((String) "errorMessage", (Object) ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED[1]);
            }
            context.sendBridgeResult(result);
        }
    }

    private void startBluetoothDevicesDiscovery(JSONObject params, H5BridgeContext context) {
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, false);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, false);
        } else {
            String[] serviceUUIDs = null;
            boolean allowDuplicatesKey = false;
            int interval = 0;
            if (params != null) {
                if (params.containsKey(KEY_SERVICES)) {
                    LoggerFactory.getTraceLogger().debug(TAG, "services:" + params.getString(KEY_SERVICES));
                    List serviceUUIDList = JSON.parseArray(params.getString(KEY_SERVICES), String.class);
                    if (serviceUUIDList != null) {
                        serviceUUIDs = new String[serviceUUIDList.size()];
                        serviceUUIDList.toArray(serviceUUIDs);
                    }
                }
                if (params.containsKey(KEY_ALLOWDUPLICATESKEY)) {
                    allowDuplicatesKey = params.getBoolean(KEY_ALLOWDUPLICATESKEY).booleanValue();
                }
                if (params.containsKey("interval")) {
                    interval = params.getIntValue("interval");
                }
            }
            sendBridgeResult(context, this.bleService.startBluetoothDevicesDiscovery(serviceUUIDs, allowDuplicatesKey, interval), false);
        }
        MonitorHelper.logStartBLEScan();
    }

    private void stopBluetoothDevicesDiscovery(H5BridgeContext context) {
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, false);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, false);
        } else {
            this.bleService.stopBluetoothDevicesDiscovery();
            context.sendBridgeResult(new JSONObject());
        }
        MonitorHelper.logStopBLEScan();
    }

    private void getBluetoothDevices(H5BridgeContext context) {
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, false);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, false);
        } else {
            List devices = this.bleService.getBluetoothDevices();
            JSONObject result = new JSONObject();
            if (devices != null) {
                result.put((String) KEY_DEVICES, JSON.toJSON(devices));
            }
            context.sendBridgeResult(result);
        }
    }

    private void getConnectedBluetoothDevices(H5BridgeContext context) {
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, false);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, false);
        } else {
            List devices = this.bleService.getConnectedBluetoothDevices();
            JSONObject result = new JSONObject();
            if (devices != null) {
                result.put((String) KEY_DEVICES, JSON.toJSON(devices));
            }
            context.sendBridgeResult(result);
        }
    }

    private void connectBleDevice(String action, JSONObject params, H5BridgeContext context) {
        MonitorHelper.logConnectBLE();
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, true);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, true);
        } else if (!params.containsKey("deviceId")) {
            sendParamLackingBridgeResult(context, true);
        } else {
            BleResult bleResult = this.bleService.connectBluetoothDevice(params.getString("deviceId"));
            if (!bleResult.syncReturn) {
                handleH5Bridge(action, context);
                return;
            }
            sendBridgeResult(context, bleResult, true);
            if (!bleResult.success) {
                MonitorHelper.logConnectBLEErr(bleResult.getErrorMessage());
            }
        }
    }

    private void disconnectBleDevice(String action, JSONObject params, H5BridgeContext context) {
        MonitorHelper.logDisconnectBLE();
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, true);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, true);
        } else if (!params.containsKey("deviceId")) {
            sendParamLackingBridgeResult(context, true);
        } else {
            BleResult bleResult = this.bleService.disconnectBluetoothDevice(params.getString("deviceId"));
            if (!bleResult.syncReturn) {
                handleH5Bridge(action, context);
                return;
            }
            sendBridgeResult(context, bleResult, true);
        }
    }

    private void writeBleCharacteristicValue(String action, JSONObject params, H5BridgeContext context) {
        MonitorHelper.logWriteDataBLE();
        if (this.bleService != null) {
            LoggerFactory.getTraceLogger().debug(TAG, "writeBleCharacteristicValue");
            if (!this.bleService.isOpened()) {
                sendBluetoothNotInitializedResult(context, true);
            } else if (!params.containsKey("deviceId") || !params.containsKey("serviceId") || !params.containsKey(KEY_CHARACTERISTIC_ID) || !params.containsKey("value")) {
                sendParamLackingBridgeResult(context, true);
            } else {
                BleResult bleResult = this.bleService.sendData(params.getString("deviceId"), params.getString("serviceId"), params.getString(KEY_CHARACTERISTIC_ID), params.getString("value"));
                if (!bleResult.syncReturn) {
                    handleH5Bridge(action, context);
                    return;
                }
                sendBridgeResult(context, bleResult, true);
                if (!bleResult.success) {
                    MonitorHelper.logWriteBLEErr(bleResult.getErrorMessage());
                }
            }
        } else {
            LoggerFactory.getTraceLogger().debug(TAG, "bleService is null");
            sendDefaultErrorBridgeResult(context, true);
        }
    }

    private void readBleCharacteristicValue(String action, JSONObject params, H5BridgeContext context) {
        MonitorHelper.logReadDataBLE();
        LoggerFactory.getTraceLogger().debug(TAG, "readBleCharacteristicValue");
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, true);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, true);
        } else if (!params.containsKey("deviceId") || !params.containsKey("serviceId") || !params.containsKey(KEY_CHARACTERISTIC_ID)) {
            sendParamLackingBridgeResult(context, true);
        } else {
            BleResult bleResult = this.bleService.readData(params.getString("deviceId"), params.getString("serviceId"), params.getString(KEY_CHARACTERISTIC_ID));
            if (bleResult.syncReturn || bleResult.obj == null) {
                sendBridgeResult(context, bleResult, true);
                if (!bleResult.success) {
                    MonitorHelper.logReadBLEErr(bleResult.getErrorMessage());
                    return;
                }
                return;
            }
            this.readCharacteristicList.add((BleGattCharacteristic) bleResult.obj);
            handleH5Bridge(action, context, bleResult.obj);
        }
    }

    private void notifyBleCharacteristicValueChange(String action, JSONObject params, H5BridgeContext context) {
        MonitorHelper.logNotifyBLE();
        if (this.bleService == null || params == null) {
            sendDefaultErrorBridgeResult(context, true);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, true);
        } else if (!params.containsKey("deviceId") || !params.containsKey("serviceId") || !params.containsKey(KEY_CHARACTERISTIC_ID)) {
            sendParamLackingBridgeResult(context, true);
        } else {
            boolean enable = true;
            if (params.containsKey(KEY_STATE)) {
                enable = params.getBoolean(KEY_STATE).booleanValue();
            }
            BleResult bleResult = this.bleService.notifyCharacteristicValueChange(params.getString("deviceId"), params.getString("serviceId"), params.getString(KEY_CHARACTERISTIC_ID), params.getString(KEY_DESCRIPTOR_ID), enable);
            if (!bleResult.syncReturn) {
                handleH5Bridge(action, context);
                return;
            }
            sendBridgeResult(context, bleResult, true);
            if (!bleResult.success) {
                MonitorHelper.logNotifyBLEErr(bleResult.getErrorMessage());
            }
        }
    }

    private void getBleDeviceServices(JSONObject params, H5BridgeContext context) {
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, false);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, false);
        } else {
            BleResult bleResult = this.bleService.getBluetoothServices(params.getString("deviceId"));
            if (bleResult.success) {
                JSONObject result = new JSONObject();
                result.put((String) KEY_SERVICES, JSON.toJSON((List) bleResult.obj));
                context.sendBridgeResult(result);
                return;
            }
            sendBridgeResult(context, bleResult, false);
        }
    }

    private void getBleDeviceCharacteristics(JSONObject params, H5BridgeContext context) {
        if (this.bleService == null) {
            sendDefaultErrorBridgeResult(context, false);
        } else if (!this.bleService.isOpened()) {
            sendBluetoothNotInitializedResult(context, false);
        } else {
            BleResult bleResult = this.bleService.getBluetoothCharacteristics(params.getString("deviceId"), params.getString("serviceId"));
            if (bleResult.success) {
                JSONObject result = new JSONObject();
                result.put((String) KEY_CHARACTERISTICS, JSON.toJSON((List) bleResult.obj));
                context.sendBridgeResult(result);
                return;
            }
            sendBridgeResult(context, bleResult, false);
        }
    }

    private void handleH5Bridge(String action, Object... params) {
        int what = getMessageWhatFromAction(action);
        H5BridgeContext context = null;
        if (params != null && params.length > 0) {
            context = params[0];
        }
        setH5BridgeContext(action, context);
        Message message = this.handler.obtainMessage(what, context);
        if (FUNC_NOTIFY_BLE_CHARACTERISTIC_VALUE_CHANGE.equals(action)) {
            this.handler.sendMessageDelayed(message, 10000);
        } else {
            this.handler.sendMessageDelayed(message, 10000);
        }
    }

    private void setH5BridgeContext(String action, H5BridgeContext context) {
        LoggerFactory.getTraceLogger().debug(TAG, "setH5BridgeContext, action:" + action + ",H5BridgeContext:" + context);
        if (!this.h5BridgeContextMap.containsKey(action)) {
            List h5BridgeContextList = new ArrayList();
            h5BridgeContextList.add(context);
            this.h5BridgeContextMap.put(action, h5BridgeContextList);
            return;
        }
        List h5BridgeContextList2 = this.h5BridgeContextMap.get(action);
        if (h5BridgeContextList2 != null) {
            h5BridgeContextList2.add(context);
            return;
        }
        List h5BridgeContextList3 = new ArrayList();
        h5BridgeContextList3.add(context);
        this.h5BridgeContextMap.put(action, h5BridgeContextList3);
    }

    private int getMessageWhatFromAction(String action) {
        if (FUNC_CONNECT_BLE_DEVICE.equals(action)) {
            return 1;
        }
        if (FUNC_DISCONNECT_BLE_DEVICE.equals(action)) {
            return 2;
        }
        if (FUNC_WRITE_BLE_CHARACTERISTIC_VALUE.equals(action)) {
            return 3;
        }
        if (FUNC_READ_BLE_CHARACTERISTIC_VALUE.equals(action)) {
            return 4;
        }
        if (FUNC_NOTIFY_BLE_CHARACTERISTIC_VALUE_CHANGE.equals(action)) {
            return 5;
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public String getActionFromMessage(int what) {
        switch (what) {
            case 1:
                return FUNC_CONNECT_BLE_DEVICE;
            case 2:
                return FUNC_DISCONNECT_BLE_DEVICE;
            case 3:
                return FUNC_WRITE_BLE_CHARACTERISTIC_VALUE;
            case 4:
                return FUNC_READ_BLE_CHARACTERISTIC_VALUE;
            case 5:
                return FUNC_NOTIFY_BLE_CHARACTERISTIC_VALUE_CHANGE;
            default:
                return null;
        }
    }

    private void sendBridgeResult(H5BridgeContext context, BleResult bleResult, boolean inOperationList) {
        JSONObject result = new JSONObject();
        if (!bleResult.success) {
            if (bleResult.error == null || bleResult.error.length <= 1) {
                result.put((String) "error", (Object) "12");
            } else {
                result.put((String) "error", (Object) bleResult.getErrorCode());
                result.put((String) "errorMessage", (Object) bleResult.getErrorMessage());
            }
        }
        context.sendBridgeResult(result);
        if (inOperationList) {
            this.handler.sendEmptyMessage(0);
        }
    }

    private void sendParamLackingBridgeResult(H5BridgeContext context, boolean inOperationList) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) ErrorConstants.ERROR_PARAM_LACK[0]);
        result.put((String) "errorMessage", (Object) ErrorConstants.ERROR_PARAM_LACK[1]);
        context.sendBridgeResult(result);
        if (inOperationList) {
            this.handler.sendEmptyMessage(0);
        }
    }

    private void sendBluetoothNotInitializedResult(H5BridgeContext context, boolean inOperationList) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED[0]);
        result.put((String) "errorMessage", (Object) ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED[1]);
        context.sendBridgeResult(result);
        if (inOperationList) {
            this.handler.sendEmptyMessage(0);
        }
    }

    private void sendDefaultErrorBridgeResult(H5BridgeContext context, boolean inOperationList) {
        JSONObject result = new JSONObject();
        result.put((String) "error", (Object) "12");
        context.sendBridgeResult(result);
        if (inOperationList) {
            this.handler.sendEmptyMessage(0);
        }
    }
}
