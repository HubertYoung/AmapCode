package com.alipay.android.phone.bluetoothsdk.better.ble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.android.phone.bluetoothsdk.BluetoothHelper;
import com.alipay.android.phone.bluetoothsdk.MonitorHelper;
import com.alipay.android.phone.bluetoothsdk.ScanRecord;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@TargetApi(18)
public class BLEManager {
    private static final String DEFAULT_DESCRIPTOR_UUID = "00002902-0000-1000-8000-00805f9b34fb";
    private static final String KEY_ENABLE_CLOSE_ON_DISCONNECT = "ble_close_on_disconnect";
    private static final String KEY_LOCATION_PERMISSION_CHECK = "ble_location_permission_check";
    private static final int MIN_SCAN_INTERVAL_TIME = 100;
    private static final String TAG = "BLEManager";
    /* access modifiers changed from: private */
    public Map<String, BleDevice> allConnectedDeviceMap;
    /* access modifiers changed from: private */
    public boolean allowDuplicatesKey;
    /* access modifiers changed from: private */
    public BetterBleListener betterBleListener;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(final BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onConnectionStateChange,  status:" + status + ",newState:" + newState + ",closeDevice:" + BLEManager.this.closeDevice);
            if (newState == 2) {
                LoggerFactory.getTraceLogger().info(BLEManager.TAG, "Connected to GATT server.");
                if (!BLEManager.this.currentConnectedDeviceMap.containsKey(gatt.getDevice().getAddress())) {
                    BLEManager.this.discoverTime = System.currentTimeMillis();
                    boolean discoverResult = gatt.discoverServices();
                    LoggerFactory.getTraceLogger().info(BLEManager.TAG, "Attempting to start service discovery:" + discoverResult);
                    if (discoverResult) {
                        BLEManager.this.handler.postDelayed(new Runnable() {
                            public void run() {
                                BLEManager.this.triggerConnectedCallback(gatt);
                            }
                        }, 5000);
                    }
                    BleDevice bleDevice = BleDevice.createConnectedBleDevice(gatt.getDevice(), gatt);
                    BLEManager.this.currentConnectedDeviceMap.put(gatt.getDevice().getAddress(), bleDevice);
                    if (!BLEManager.this.allConnectedDeviceMap.containsKey(gatt.getDevice().getAddress())) {
                        BLEManager.this.allConnectedDeviceMap.put(gatt.getDevice().getAddress(), bleDevice);
                    }
                }
                if (BLEManager.this.startConnectTime != 0) {
                    MonitorHelper.logConnectBLETime(System.currentTimeMillis() - BLEManager.this.startConnectTime);
                }
                MonitorHelper.logConnectBLESucc();
                BLEManager.this.connectedTime = System.currentTimeMillis();
            } else if (newState == 0) {
                if (BLEManager.this.enableCloseOnDisconnect || BLEManager.this.closeDevice) {
                    BLEManager.this.handler.post(new Runnable() {
                        public void run() {
                            BLEManager.this.closeDevice = false;
                            BLEManager.this.close((BleDevice) BLEManager.this.allConnectedDeviceMap.get(gatt.getDevice().getAddress()));
                        }
                    });
                }
                BLEManager.this.currentConnectedDeviceMap.remove(gatt.getDevice().getAddress());
                if (BLEManager.this.connectedTime != 0) {
                    MonitorHelper.logKeepConnectTime(System.currentTimeMillis() - BLEManager.this.connectedTime);
                    BLEManager.this.connectedTime = 0;
                }
                MonitorHelper.logOnDisconnectBLE();
            }
            if (BLEManager.this.betterBleListener != null && newState != 2) {
                BLEManager.this.betterBleListener.onBluetoothConnectionStateChange(gatt.getDevice().getAddress(), newState == 2);
            }
        }

        public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onServicesDiscovered, status:" + status);
            if (gatt.getServices() != null) {
                LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "services is not null");
            }
            BLEManager.this.handler.post(new Runnable() {
                public void run() {
                    BLEManager.this.triggerConnectedCallback(gatt);
                }
            });
            if (BLEManager.this.discoverTime != 0) {
                MonitorHelper.logDiscoverServiceTime(System.currentTimeMillis() - BLEManager.this.discoverTime);
                BLEManager.this.discoverTime = 0;
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onCharacteristicRead, status:" + status);
            String receivedData = BluetoothHelper.bytesToHexString(characteristic.getValue());
            LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onCharacteristicRead, characteristic data:" + receivedData);
            if (BLEManager.this.betterBleListener != null) {
                BLEManager.this.betterBleListener.onBluetoothCharacteristicRead(gatt.getDevice().getAddress(), characteristic.getService().getUuid().toString(), characteristic.getUuid().toString(), receivedData);
                BLEManager.this.betterBleListener.onBluetoothCharacteristicValueChange(gatt.getDevice().getAddress(), characteristic.getService().getUuid().toString(), characteristic.getUuid().toString(), receivedData);
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onCharacteristicWrite, status:" + status);
            if (BLEManager.this.mBufferOffset < BLEManager.this.bytes.length) {
                int length = Math.min(BLEManager.this.bytes.length - BLEManager.this.mBufferOffset, 20);
                byte[] data = new byte[length];
                System.arraycopy(BLEManager.this.bytes, BLEManager.this.mBufferOffset, data, 0, length);
                BLEManager.this.mBufferOffset = BLEManager.this.mBufferOffset + length;
                characteristic.setValue(data);
                gatt.writeCharacteristic(characteristic);
            } else {
                LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "数据包发送完成OnCharacteristicWrite");
            }
            if (BLEManager.this.betterBleListener != null) {
                BLEManager.this.betterBleListener.onBluetoothCharacteristicWrite(gatt.getDevice().getAddress(), characteristic.getService().getUuid().toString(), characteristic.getUuid().toString());
            }
            if (BLEManager.this.writeTime != 0) {
                MonitorHelper.logWriteBLETime(System.currentTimeMillis() - BLEManager.this.writeTime);
                BLEManager.this.writeTime = 0;
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            String receivedData = BluetoothHelper.bytesToHexString(characteristic.getValue());
            LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onCharacteristicChanged, characteristic data:" + receivedData);
            if (BLEManager.this.betterBleListener != null) {
                BLEManager.this.betterBleListener.onBluetoothCharacteristicValueChange(gatt.getDevice().getAddress(), characteristic.getService().getUuid().toString(), characteristic.getUuid().toString(), receivedData);
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onDescriptorWrite");
            if (descriptor != null && descriptor.getCharacteristic() != null) {
                LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onDescriptorWrite, characteristic:" + descriptor.getCharacteristic().getUuid().toString() + ",properties:" + descriptor.getCharacteristic().getProperties() + ",status:" + status);
                if (BLEManager.this.betterBleListener != null) {
                    BLEManager.this.betterBleListener.onBluetoothDescriptorWrite(gatt.getDevice().getAddress(), descriptor.getCharacteristic().getService().getUuid().toString(), descriptor.getCharacteristic().getUuid().toString(), descriptor.getUuid().toString());
                }
            }
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onDescriptorRead");
        }
    };
    private BluetoothManager bluetoothManager;
    private BroadcastReceiver bluetoothReceiver;
    /* access modifiers changed from: private */
    public byte[] bytes;
    /* access modifiers changed from: private */
    public boolean closeDevice;
    private boolean connectedCallbackCalled;
    /* access modifiers changed from: private */
    public long connectedTime;
    private Context context;
    /* access modifiers changed from: private */
    public Map<String, BleDevice> currentConnectedDeviceMap;
    /* access modifiers changed from: private */
    public Map<String, BleDevice> deviceMap;
    /* access modifiers changed from: private */
    public long discoverTime;
    /* access modifiers changed from: private */
    public boolean enableCloseOnDisconnect;
    /* access modifiers changed from: private */
    public List<BleDevice> foundedDeviceList;
    /* access modifiers changed from: private */
    public Handler handler;
    /* access modifiers changed from: private */
    public boolean isDiscovering;
    private boolean isOpened;
    private LeScanCallback leScanCallback = new LeScanCallback() {
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "onLeScan, device:" + device.getName() + ",address:" + device.getAddress());
            if (BLEManager.this.deviceMap == null || BLEManager.this.deviceMap.isEmpty()) {
                MonitorHelper.logFirstScanTime(System.currentTimeMillis() - BLEManager.this.startBluetoothDiscoveryTime);
            }
            if (!BLEManager.this.deviceMap.containsKey(device.getAddress())) {
                ScanRecord scanData = ScanRecord.parseFromBytes(scanRecord);
                BleDevice bleDevice = BleDevice.createBleDevice(device);
                bleDevice.RSSI = rssi;
                bleDevice.manufacturerData = BluetoothHelper.getManufacturerData(scanRecord);
                bleDevice.advertisData = bleDevice.manufacturerData;
                bleDevice.advertisServiceUUIDs = scanData.getServiceUuids();
                bleDevice.serviceData = scanData.getServiceData();
                LoggerFactory.getTraceLogger().debug(BLEManager.TAG, "manufacturerData:" + bleDevice.manufacturerData + ",scanRecord:" + scanData.toString());
                BLEManager.this.deviceMap.put(device.getAddress(), bleDevice);
                if (BLEManager.this.scanInterval == 0) {
                    if (BLEManager.this.betterBleListener != null) {
                        BLEManager.this.foundedDeviceList.clear();
                        BLEManager.this.foundedDeviceList.add(bleDevice);
                        BLEManager.this.betterBleListener.onBluetoothDeviceFound(BLEManager.this.foundedDeviceList);
                    }
                } else if (!BLEManager.this.foundedDeviceList.contains(bleDevice)) {
                    BLEManager.this.foundedDeviceList.add(bleDevice);
                }
            } else if (BLEManager.this.allowDuplicatesKey) {
                BleDevice bleDevice2 = (BleDevice) BLEManager.this.deviceMap.get(device.getAddress());
                bleDevice2.RSSI = rssi;
                if (BLEManager.this.scanInterval == 0) {
                    if (BLEManager.this.betterBleListener != null) {
                        BLEManager.this.foundedDeviceList.clear();
                        BLEManager.this.foundedDeviceList.add(bleDevice2);
                        BLEManager.this.betterBleListener.onBluetoothDeviceFound(BLEManager.this.foundedDeviceList);
                    }
                } else if (!BLEManager.this.foundedDeviceList.contains(bleDevice2)) {
                    BLEManager.this.foundedDeviceList.add(bleDevice2);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public int mBufferOffset;
    private long openBluetoothAdapterTime;
    private ScanHandler scanHandler;
    /* access modifiers changed from: private */
    public int scanInterval;
    /* access modifiers changed from: private */
    public long startBluetoothDiscoveryTime;
    /* access modifiers changed from: private */
    public long startConnectTime;
    /* access modifiers changed from: private */
    public long writeTime;

    class ScanHandler extends Handler {
        public ScanHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (BLEManager.this.betterBleListener != null && !BLEManager.this.foundedDeviceList.isEmpty()) {
                BLEManager.this.betterBleListener.onBluetoothDeviceFound(BLEManager.this.foundedDeviceList);
            }
            BLEManager.this.foundedDeviceList.clear();
            sendEmptyMessageDelayed(0, (long) BLEManager.this.scanInterval);
        }
    }

    public BLEManager(Context context2) {
        this.context = context2;
        this.deviceMap = new HashMap();
        this.currentConnectedDeviceMap = new HashMap();
        this.allConnectedDeviceMap = new HashMap();
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.isDiscovering = false;
        this.closeDevice = false;
        this.allowDuplicatesKey = false;
        this.openBluetoothAdapterTime = 0;
        this.startBluetoothDiscoveryTime = 0;
        this.startConnectTime = 0;
        this.connectedTime = 0;
        this.writeTime = 0;
        this.discoverTime = 0;
        this.foundedDeviceList = new ArrayList();
        this.handler = new Handler(Looper.getMainLooper());
        this.scanHandler = new ScanHandler(Looper.getMainLooper());
        this.bluetoothManager = (BluetoothManager) context2.getSystemService("bluetooth");
        this.enableCloseOnDisconnect = enableCloseOnDisconnect();
    }

    public void setBetterBleListener(BetterBleListener betterBleListener2) {
        this.betterBleListener = betterBleListener2;
    }

    public void reset() {
        unregisterReceiver();
        stopBleScan();
    }

    public void openBluetoothAdapter() {
        LoggerFactory.getTraceLogger().debug(TAG, "openBluetoothAdapter");
        reset();
        registerReceiver();
        this.openBluetoothAdapterTime = System.currentTimeMillis();
        this.isOpened = true;
    }

    public void closeBluetoothAdapter() {
        LoggerFactory.getTraceLogger().debug(TAG, "closeBluetoothAdapter");
        unregisterReceiver();
        stopBleScan();
        disconnectAndClose();
        this.foundedDeviceList.clear();
        if (this.openBluetoothAdapterTime != 0) {
            MonitorHelper.logBleKeepTime(System.currentTimeMillis() - this.openBluetoothAdapterTime);
            this.openBluetoothAdapterTime = 0;
        }
        this.isOpened = false;
    }

    /* access modifiers changed from: private */
    public void triggerConnectedCallback(BluetoothGatt gatt) {
        if (this.betterBleListener != null && !this.connectedCallbackCalled) {
            this.betterBleListener.onBluetoothConnectionStateChange(gatt.getDevice().getAddress(), true);
            this.connectedCallbackCalled = true;
        }
    }

    private void registerReceiver() {
        if (this.bluetoothReceiver == null) {
            this.bluetoothReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE)) {
                        case 10:
                            if (BLEManager.this.betterBleListener != null) {
                                BLEManager.this.betterBleListener.onBluetoothAdapterStateChange(false, BLEManager.this.isDiscovering);
                                return;
                            }
                            return;
                        case 12:
                            if (BLEManager.this.betterBleListener != null) {
                                BLEManager.this.betterBleListener.onBluetoothAdapterStateChange(true, BLEManager.this.isDiscovering);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            this.context.registerReceiver(this.bluetoothReceiver, intentFilter);
        }
    }

    private void unregisterReceiver() {
        LoggerFactory.getTraceLogger().debug(TAG, "unregisterReceiver");
        if (this.bluetoothReceiver != null) {
            this.context.unregisterReceiver(this.bluetoothReceiver);
        }
        this.bluetoothReceiver = null;
    }

    public boolean isDiscovering() {
        return this.isDiscovering;
    }

    public boolean isOpened() {
        return this.isOpened;
    }

    public BleResult startBleScan(String[] serviceUUIDs, boolean allowDuplicatesKey2, int interval) {
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return new BleResult(false, true, ErrorConstants.ERROR_UNSUPPORT_BLE);
        }
        if (VERSION.SDK_INT >= 23 && !BluetoothHelper.hasLocationPermission(this.context)) {
            LoggerFactory.getTraceLogger().debug(TAG, "no location permission");
            if (checkLocationPermission()) {
                return new BleResult(false, true, ErrorConstants.ERROR_SCAN_LOCATION_UNAVAILABLE);
            }
        }
        LoggerFactory.getTraceLogger().debug(TAG, "startBleScan, isDiscovering:" + this.isDiscovering);
        if (this.isDiscovering) {
            return new BleResult(true, true);
        }
        this.allowDuplicatesKey = allowDuplicatesKey2;
        if (interval < 100) {
            interval = 0;
        }
        this.scanInterval = interval;
        this.deviceMap.clear();
        this.foundedDeviceList.clear();
        if (this.scanInterval > 0) {
            this.scanHandler.sendEmptyMessageDelayed(0, (long) this.scanInterval);
        }
        if (this.bluetoothAdapter == null) {
            return new BleResult(false, true);
        }
        this.startBluetoothDiscoveryTime = System.currentTimeMillis();
        if (serviceUUIDs == null || serviceUUIDs.length <= 0) {
            LoggerFactory.getTraceLogger().debug(TAG, "scan all devices");
            this.isDiscovering = this.bluetoothAdapter.startLeScan(this.leScanCallback);
            return new BleResult(this.isDiscovering, true);
        }
        UUID[] uuids = new UUID[serviceUUIDs.length];
        for (int i = 0; i < serviceUUIDs.length; i++) {
            UUID uuid = BluetoothHelper.getUUIDFromString(serviceUUIDs[i]);
            LoggerFactory.getTraceLogger().debug(TAG, "scan, serviceUUID " + i + " :" + uuid);
            if (uuid == null) {
                return new BleResult(false, true, ErrorConstants.ERROR_SCAN_INVALID_UUID);
            }
            uuids[i] = uuid;
        }
        this.isDiscovering = this.bluetoothAdapter.startLeScan(uuids, this.leScanCallback);
        return new BleResult(this.isDiscovering, true);
    }

    public void stopBleScan() {
        if (BluetoothHelper.isSupportBLE(this.context)) {
            LoggerFactory.getTraceLogger().debug(TAG, "stopBleScan");
            this.isDiscovering = false;
            this.foundedDeviceList.clear();
            this.scanHandler.removeCallbacksAndMessages(null);
            if (this.bluetoothAdapter != null) {
                this.bluetoothAdapter.stopLeScan(this.leScanCallback);
            }
        }
    }

    public List<BleDevice> getBluetoothDevices() {
        return new ArrayList(this.deviceMap.values());
    }

    public List<BleDevice> getConnectedBluetoothDevices() {
        List<BluetoothDevice> bluetoothDevices = this.bluetoothManager.getConnectedDevices(7);
        if (bluetoothDevices == null || bluetoothDevices.isEmpty()) {
            return new ArrayList();
        }
        List bleDevices = new ArrayList(bluetoothDevices.size());
        for (BluetoothDevice createBleDevice : bluetoothDevices) {
            bleDevices.add(BleDevice.createBleDevice(createBleDevice));
        }
        return bleDevices;
    }

    public BleResult connect(String address) {
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return new BleResult(false, true, ErrorConstants.ERROR_UNSUPPORT_BLE);
        }
        this.connectedCallbackCalled = false;
        LoggerFactory.getTraceLogger().debug(TAG, "connect, address:" + address);
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            LoggerFactory.getTraceLogger().debug(TAG, "address is not valid");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICEID_INVALID);
        }
        BluetoothDevice device = this.bluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "Device not found.  Unable to connect.");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICE_NOT_FOUND);
        } else if (this.bluetoothManager.getConnectionState(device, 7) == 2) {
            LoggerFactory.getTraceLogger().debug(TAG, "already connected");
            if (this.currentConnectedDeviceMap.containsKey(address)) {
                LoggerFactory.getTraceLogger().debug(TAG, "is in currentConnectedDeviceMap");
            }
            return new BleResult(true, true);
        } else {
            this.startConnectTime = System.currentTimeMillis();
            BluetoothGatt gatt = device.connectGatt(this.context, false, this.bluetoothGattCallback);
            LoggerFactory.getTraceLogger().debug(TAG, "Trying to create a new connection.");
            if (gatt == null) {
                return new BleResult(false, true, ErrorConstants.ERROR_CONNECT_FAIL);
            }
            return new BleResult(true, false);
        }
    }

    public BleResult disconnect(String address) {
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return new BleResult(false, true, ErrorConstants.ERROR_UNSUPPORT_BLE);
        }
        if (this.bluetoothAdapter == null) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "BluetoothAdapter not initialized");
            return new BleResult(false, true, ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED);
        } else if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            LoggerFactory.getTraceLogger().debug(TAG, "address is not valid");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICEID_INVALID);
        } else {
            BleDevice bleDevice = this.allConnectedDeviceMap.get(address);
            if (bleDevice == null || bleDevice.gatt == null) {
                return new BleResult(true, true);
            }
            bleDevice.gatt.disconnect();
            return new BleResult(true, false);
        }
    }

    public Collection<BleDevice> disconnectAllDevices() {
        Collection<BleDevice> bleDevices = null;
        if (BluetoothHelper.isSupportBLE(this.context)) {
            LoggerFactory.getTraceLogger().debug(TAG, "disconnectAllDevices");
            if (this.bluetoothAdapter == null) {
                LoggerFactory.getTraceLogger().warn((String) TAG, (String) "BluetoothAdapter not initialized");
            } else {
                bleDevices = new ArrayList<>(this.allConnectedDeviceMap.values());
                for (BleDevice bleDevice : bleDevices) {
                    if (bleDevice.gatt != null) {
                        bleDevice.gatt.disconnect();
                        LoggerFactory.getTraceLogger().debug(TAG, "device disconnect, deviceId:" + bleDevice.deviceId);
                    }
                }
            }
        }
        return bleDevices;
    }

    public void close(BleDevice bleDevice) {
        if (BluetoothHelper.isSupportBLE(this.context) && bleDevice != null) {
            LoggerFactory.getTraceLogger().debug(TAG, "close, device:" + bleDevice.deviceId);
            bleDevice.gatt.close();
            this.currentConnectedDeviceMap.remove(bleDevice.deviceId);
            this.allConnectedDeviceMap.remove(bleDevice.deviceId);
        }
    }

    public void closeAll(Collection<BleDevice> bleDevices) {
        if (BluetoothHelper.isSupportBLE(this.context)) {
            LoggerFactory.getTraceLogger().debug(TAG, "closeAll");
            if (bleDevices != null) {
                for (BleDevice bleDevice : bleDevices) {
                    if (bleDevice.gatt != null) {
                        bleDevice.gatt.close();
                        LoggerFactory.getTraceLogger().debug(TAG, "device close, deviceId:" + bleDevice.deviceId);
                    }
                }
            }
            this.currentConnectedDeviceMap.clear();
            this.allConnectedDeviceMap.clear();
            this.deviceMap.clear();
        }
    }

    public BleResult disconnectAndClose(String address) {
        LoggerFactory.getTraceLogger().debug(TAG, "disconnectAndClose, address:" + address);
        if (this.allConnectedDeviceMap.containsKey(address)) {
            this.closeDevice = true;
        }
        return disconnect(address);
    }

    public void disconnectAndClose() {
        LoggerFactory.getTraceLogger().debug(TAG, "disconnectAndClose");
        final Collection bleDevices = disconnectAllDevices();
        this.handler.postDelayed(new Runnable() {
            public void run() {
                BLEManager.this.closeAll(bleDevices);
            }
        }, 200);
    }

    public BleResult getBluetoothServices(String address) {
        boolean z;
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return new BleResult(false, true, ErrorConstants.ERROR_UNSUPPORT_BLE);
        }
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            LoggerFactory.getTraceLogger().debug(TAG, "address is not valid");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICEID_INVALID);
        } else if (!this.currentConnectedDeviceMap.containsKey(address)) {
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICE_NOT_FOUND);
        } else {
            BleResult bleResult = new BleResult();
            List serviceUUIDs = new ArrayList();
            List services = this.currentConnectedDeviceMap.get(address).gatt.getServices();
            if (services != null) {
                for (BluetoothGattService service : services) {
                    BleGattService bleGattService = new BleGattService();
                    bleGattService.serviceId = service.getUuid().toString();
                    if (service.getType() == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    bleGattService.isPrimary = z;
                    serviceUUIDs.add(bleGattService);
                }
            }
            bleResult.success = true;
            bleResult.syncReturn = true;
            bleResult.obj = serviceUUIDs;
            return bleResult;
        }
    }

    public BleResult getBluetoothCharacteristics(String address, String serviceId) {
        LoggerFactory.getTraceLogger().debug(TAG, "getBluetoothCharacteristics, address:" + address + ",serviceUUID:" + serviceId);
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return new BleResult(false, true, ErrorConstants.ERROR_UNSUPPORT_BLE);
        }
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            LoggerFactory.getTraceLogger().debug(TAG, "address is not valid");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICEID_INVALID);
        } else if (!this.currentConnectedDeviceMap.containsKey(address)) {
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICE_NOT_FOUND);
        } else {
            UUID serviceUUID = BluetoothHelper.getUUIDFromString(serviceId);
            if (serviceUUID == null) {
                return new BleResult(false, true, ErrorConstants.ERROR_SERVICEID_INVALID);
            }
            BleResult bleResult = new BleResult();
            List characteristics = new ArrayList();
            BluetoothGattService service = this.currentConnectedDeviceMap.get(address).gatt.getService(serviceUUID);
            if (service != null) {
                List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                if (characteristicList != null) {
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : characteristicList) {
                        characteristics.add(BleGattCharacteristic.createCharacteristic(bluetoothGattCharacteristic));
                    }
                }
            }
            bleResult.success = true;
            bleResult.syncReturn = true;
            bleResult.obj = characteristics;
            return bleResult;
        }
    }

    public BleResult readData(String address, String serviceId, String characteristicId) {
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return new BleResult(false, true, ErrorConstants.ERROR_UNSUPPORT_BLE);
        }
        LoggerFactory.getTraceLogger().debug(TAG, "readData,address:" + address + ",serviceId:" + serviceId + ",characteristicId:" + characteristicId);
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            LoggerFactory.getTraceLogger().debug(TAG, "address is not valid");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICEID_INVALID);
        }
        BleDevice bleDevice = this.currentConnectedDeviceMap.get(address);
        if (bleDevice == null) {
            LoggerFactory.getTraceLogger().debug(TAG, "not found connected device");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICE_NOT_FOUND);
        }
        UUID serviceUUID = BluetoothHelper.getUUIDFromString(serviceId);
        if (serviceUUID == null) {
            return new BleResult(false, true, ErrorConstants.ERROR_SERVICEID_INVALID);
        }
        UUID characteristicUUID = BluetoothHelper.getUUIDFromString(characteristicId);
        if (characteristicUUID == null) {
            return new BleResult(false, true, ErrorConstants.ERROR_CHARACTERISTICID_INVALID);
        }
        BluetoothGattService bluetoothGattService = bleDevice.gatt.getService(serviceUUID);
        BleResult bleResult = new BleResult(false, true);
        if (bluetoothGattService != null) {
            BluetoothGattCharacteristic readCharacteristic = bluetoothGattService.getCharacteristic(characteristicUUID);
            LoggerFactory.getTraceLogger().debug(TAG, "readData");
            if (readCharacteristic == null) {
                bleResult.error = ErrorConstants.ERROR_CHARACTERISTIC_NOT_FOUND;
                return bleResult;
            } else if ((readCharacteristic.getProperties() & 2) == 0) {
                bleResult.error = ErrorConstants.ERROR_CHARACTERISTIC_OPERATION_NOT_SUPPORT;
                return bleResult;
            } else {
                boolean ret = bleDevice.gatt.readCharacteristic(readCharacteristic);
                bleResult.success = ret;
                bleResult.syncReturn = !ret;
                if (ret) {
                    bleResult.obj = BleGattCharacteristic.createCharacteristic(readCharacteristic);
                    return bleResult;
                }
                bleResult.error = ErrorConstants.ERROR_READ_CHARACTERISTIC_FAIL;
                return bleResult;
            }
        } else {
            bleResult.error = ErrorConstants.ERROR_SERVICE_NOT_FOUND;
            return bleResult;
        }
    }

    public BleResult sendData(String address, String serviceId, String characteristicId, String data) {
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return new BleResult(false, true, ErrorConstants.ERROR_UNSUPPORT_BLE);
        }
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            LoggerFactory.getTraceLogger().debug(TAG, "address is not valid");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICEID_INVALID);
        }
        LoggerFactory.getTraceLogger().debug(TAG, "sendData:" + data);
        UUID serviceUUID = BluetoothHelper.getUUIDFromString(serviceId);
        if (serviceUUID == null) {
            return new BleResult(false, true, ErrorConstants.ERROR_SERVICEID_INVALID);
        }
        UUID characteristicUUID = BluetoothHelper.getUUIDFromString(characteristicId);
        if (characteristicUUID == null) {
            return new BleResult(false, true, ErrorConstants.ERROR_CHARACTERISTICID_INVALID);
        }
        BleDevice bleDevice = this.currentConnectedDeviceMap.get(address);
        if (bleDevice == null) {
            LoggerFactory.getTraceLogger().debug(TAG, "device not found in connected map");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICE_NOT_FOUND);
        }
        BluetoothGattService bluetoothGattService = bleDevice.gatt.getService(serviceUUID);
        BleResult bleResult = new BleResult(false, true);
        if (bluetoothGattService != null) {
            BluetoothGattCharacteristic writeCharacteristic = bluetoothGattService.getCharacteristic(characteristicUUID);
            LoggerFactory.getTraceLogger().debug(TAG, "writeValue, writeCharacteristic is null:" + (writeCharacteristic == null));
            if (writeCharacteristic != null) {
                LoggerFactory.getTraceLogger().debug(TAG, "writeCharacteristic properties:" + writeCharacteristic.getProperties());
                if ((writeCharacteristic.getProperties() & 4) > 0) {
                    LoggerFactory.getTraceLogger().debug(TAG, "set write type not response");
                    writeCharacteristic.setWriteType(1);
                }
                this.writeTime = System.currentTimeMillis();
                boolean ret = writeValue(bleDevice.gatt, writeCharacteristic, data);
                bleResult.success = ret;
                bleResult.syncReturn = !ret;
                if (ret) {
                    return bleResult;
                }
                bleResult.error = ErrorConstants.ERROR_WRITE_CHARACTERISTIC_FAIL;
                return bleResult;
            }
            bleResult.error = ErrorConstants.ERROR_CHARACTERISTIC_NOT_FOUND;
            return bleResult;
        }
        bleResult.error = ErrorConstants.ERROR_SERVICE_NOT_FOUND;
        return bleResult;
    }

    private boolean writeValue(BluetoothGatt gatt, BluetoothGattCharacteristic writeCharacteristic, String sendData) {
        this.mBufferOffset = 0;
        if (sendData != null && (sendData.startsWith("0X") || sendData.startsWith("0x"))) {
            sendData = sendData.substring(2);
        }
        if (sendData.length() % 2 == 0) {
            this.bytes = new byte[(sendData.length() / 2)];
            for (int i = 0; i < this.bytes.length; i++) {
                this.bytes[i] = (byte) (Integer.parseInt(sendData.substring(i * 2, (i * 2) + 2), 16) & 255);
            }
            int length = Math.min(this.bytes.length, 20);
            this.mBufferOffset += length;
            byte[] data = new byte[length];
            System.arraycopy(this.bytes, 0, data, 0, length);
            writeCharacteristic.setValue(data);
            try {
                boolean result = gatt.writeCharacteristic(writeCharacteristic);
                LoggerFactory.getTraceLogger().debug(TAG, "writeCharacteristic, result:" + result);
                return result;
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().debug(TAG, "write error:" + e.toString());
                return false;
            }
        } else {
            LoggerFactory.getTraceLogger().debug(TAG, "data error");
            return false;
        }
    }

    public BleResult notifyCharacteristicValue(String address, String serviceId, String characteristicId, String descriptorUUID, boolean enable) {
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return new BleResult(false, true, ErrorConstants.ERROR_UNSUPPORT_BLE);
        }
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            LoggerFactory.getTraceLogger().debug(TAG, "address is not valid");
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICEID_INVALID);
        }
        BleDevice bleDevice = this.currentConnectedDeviceMap.get(address);
        if (bleDevice == null) {
            return new BleResult(false, true, ErrorConstants.ERROR_DEVICE_NOT_FOUND);
        }
        UUID serviceUUID = BluetoothHelper.getUUIDFromString(serviceId);
        if (serviceUUID == null) {
            return new BleResult(false, true, ErrorConstants.ERROR_SERVICEID_INVALID);
        }
        UUID characteristicUUID = BluetoothHelper.getUUIDFromString(characteristicId);
        if (characteristicUUID == null) {
            return new BleResult(false, true, ErrorConstants.ERROR_CHARACTERISTICID_INVALID);
        }
        BluetoothGatt bluetoothGatt = bleDevice.gatt;
        BluetoothGattService service = bluetoothGatt.getService(serviceUUID);
        LoggerFactory.getTraceLogger().debug(TAG, "service from serviceId is null:" + (service == null));
        BleResult bleResult = new BleResult(false, true);
        if (service != null) {
            LoggerFactory.getTraceLogger().debug(TAG, "notifyUUID:" + characteristicId);
            if (TextUtils.isEmpty(characteristicId)) {
                return bleResult;
            }
            BluetoothGattCharacteristic notifyCharacteristic = service.getCharacteristic(characteristicUUID);
            if (notifyCharacteristic != null) {
                int charaProp = notifyCharacteristic.getProperties();
                LoggerFactory.getTraceLogger().debug(TAG, "characteristic properties:" + charaProp);
                if ((charaProp & 16) != 0) {
                    LoggerFactory.getTraceLogger().debug(TAG, "notify characteristic");
                    bluetoothGatt.setCharacteristicNotification(notifyCharacteristic, enable);
                    if (TextUtils.isEmpty(descriptorUUID)) {
                        descriptorUUID = DEFAULT_DESCRIPTOR_UUID;
                    }
                    BluetoothGattDescriptor descriptor = notifyCharacteristic.getDescriptor(BluetoothHelper.getUUIDFromString(descriptorUUID));
                    if (descriptor == null) {
                        List descriptors = notifyCharacteristic.getDescriptors();
                        if (descriptors != null && descriptors.size() > 0) {
                            descriptor = descriptors.get(0);
                        }
                    }
                    LoggerFactory.getTraceLogger().debug(TAG, "descriptor is null:" + (descriptor == null));
                    if (descriptor != null) {
                        descriptor.setValue(enable ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                        bluetoothGatt.writeDescriptor(descriptor);
                    }
                    LoggerFactory.getTraceLogger().debug(TAG, "setCharacteristicNotification enabled");
                    bleResult.success = true;
                    bleResult.syncReturn = false;
                    return bleResult;
                } else if ((charaProp & 32) != 0) {
                    LoggerFactory.getTraceLogger().debug(TAG, "indicate characteristic");
                    bluetoothGatt.setCharacteristicNotification(notifyCharacteristic, enable);
                    if (TextUtils.isEmpty(descriptorUUID)) {
                        descriptorUUID = DEFAULT_DESCRIPTOR_UUID;
                    }
                    BluetoothGattDescriptor descriptor2 = notifyCharacteristic.getDescriptor(BluetoothHelper.getUUIDFromString(descriptorUUID));
                    if (descriptor2 == null) {
                        List descriptors2 = notifyCharacteristic.getDescriptors();
                        if (descriptors2 != null && descriptors2.size() > 0) {
                            descriptor2 = descriptors2.get(0);
                        }
                    }
                    LoggerFactory.getTraceLogger().debug(TAG, "descriptor is null:" + (descriptor2 == null));
                    if (descriptor2 != null) {
                        descriptor2.setValue(enable ? BluetoothGattDescriptor.ENABLE_INDICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                        bluetoothGatt.writeDescriptor(descriptor2);
                    }
                    LoggerFactory.getTraceLogger().debug(TAG, "setCharacteristicNotification enabled");
                    bleResult.success = true;
                    bleResult.syncReturn = false;
                    return bleResult;
                } else {
                    bleResult.error = ErrorConstants.ERROR_CHARACTERISTIC_OPERATION_NOT_SUPPORT;
                    return bleResult;
                }
            } else {
                bleResult.error = ErrorConstants.ERROR_CHARACTERISTIC_NOT_FOUND;
                return bleResult;
            }
        } else {
            bleResult.error = ErrorConstants.ERROR_SERVICE_NOT_FOUND;
            return bleResult;
        }
    }

    private boolean checkLocationPermission() {
        ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
        if (configService != null) {
            String locationPermissionCheck = configService.getConfig(KEY_LOCATION_PERMISSION_CHECK);
            if (!TextUtils.isEmpty(locationPermissionCheck)) {
                return Boolean.parseBoolean(locationPermissionCheck);
            }
        }
        return true;
    }

    private boolean enableCloseOnDisconnect() {
        ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
        if (configService != null) {
            String autoClose = configService.getConfig(KEY_ENABLE_CLOSE_ON_DISCONNECT);
            if (!TextUtils.isEmpty(autoClose)) {
                return Boolean.parseBoolean(autoClose);
            }
        }
        return true;
    }
}
