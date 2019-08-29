package com.alipay.android.phone.bluetoothsdk;

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
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@TargetApi(18)
public class BLEScanner {
    private static final String DEFAULT_DESCRIPTOR_UUID = "00002902-0000-1000-8000-00805f9b34fb";
    private static final String TAG = "BLEScanner";
    private BluetoothAdapter bluetoothAdapter;
    private String bluetoothDeviceAddress;
    /* access modifiers changed from: private */
    public BluetoothGatt bluetoothGatt;
    private BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "onConnectionStateChange,  status:" + status + ",newState:" + newState);
            if (!(BLEScanner.this.deviceConnectionInterface == null || newState == 2)) {
                BLEScanner.this.deviceConnectionInterface.onConnectionStateChange(gatt.getDevice().getAddress(), newState);
            }
            if (newState == 2) {
                LoggerFactory.getTraceLogger().info(BLEScanner.TAG, "Connected to GATT server.");
                LoggerFactory.getTraceLogger().info(BLEScanner.TAG, "Attempting to start service discovery:" + BLEScanner.this.bluetoothGatt.discoverServices());
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4 = true;
            super.onServicesDiscovered(gatt, status);
            LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "onServicesDiscovered, status:" + status);
            if (BLEScanner.this.deviceConnectionInterface != null) {
                BLEScanner.this.deviceConnectionInterface.onConnectionStateChange(gatt.getDevice().getAddress(), 2);
            }
            if (BLEScanner.this.bluetoothGatt.getServices() != null) {
                LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "services is not null");
                BluetoothGattService service = BLEScanner.this.bluetoothGatt.getService(UUID.fromString(BLEScanner.this.serviceUUID));
                TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                StringBuilder sb = new StringBuilder("service from serviceUUID is null:");
                if (service == null) {
                    z = true;
                } else {
                    z = false;
                }
                traceLogger.debug(BLEScanner.TAG, sb.append(z).toString());
                if (service != null) {
                    LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "notifyUUID:" + BLEScanner.this.notifyUUID);
                    if (!TextUtils.isEmpty(BLEScanner.this.notifyUUID)) {
                        BluetoothGattCharacteristic notifyCharacteristic = service.getCharacteristic(UUID.fromString(BLEScanner.this.notifyUUID));
                        if (notifyCharacteristic != null && (notifyCharacteristic.getProperties() | 16) > 0) {
                            BLEScanner.this.bluetoothGatt.setCharacteristicNotification(notifyCharacteristic, true);
                            if (!TextUtils.isEmpty(BLEScanner.this.descriptorUUID)) {
                                BluetoothGattDescriptor descriptor = notifyCharacteristic.getDescriptor(UUID.fromString(BLEScanner.this.descriptorUUID));
                                TraceLogger traceLogger2 = LoggerFactory.getTraceLogger();
                                StringBuilder sb2 = new StringBuilder("descriptor is null:");
                                if (descriptor == null) {
                                    z3 = true;
                                } else {
                                    z3 = false;
                                }
                                traceLogger2.debug(BLEScanner.TAG, sb2.append(z3).toString());
                                if (descriptor != null) {
                                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    BLEScanner.this.bluetoothGatt.writeDescriptor(descriptor);
                                }
                            }
                            LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "setCharacteristicNotification enabled");
                        }
                    }
                    LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "readUUID:" + BLEScanner.this.readUUID);
                    if (!TextUtils.isEmpty(BLEScanner.this.readUUID)) {
                        BluetoothGattCharacteristic notifyCharacteristic2 = service.getCharacteristic(UUID.fromString(BLEScanner.this.readUUID));
                        TraceLogger traceLogger3 = LoggerFactory.getTraceLogger();
                        StringBuilder sb3 = new StringBuilder("notifyCharacteristic is null:");
                        if (notifyCharacteristic2 == null) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        traceLogger3.debug(BLEScanner.TAG, sb3.append(z2).toString());
                        if (notifyCharacteristic2 != null) {
                            int charaProp = notifyCharacteristic2.getProperties();
                            LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "charaProp:" + charaProp);
                            if ((charaProp | 16) > 0) {
                                BLEScanner.this.bluetoothGatt.setCharacteristicNotification(notifyCharacteristic2, true);
                                if (!TextUtils.isEmpty(BLEScanner.this.descriptorUUID)) {
                                    BluetoothGattDescriptor descriptor2 = notifyCharacteristic2.getDescriptor(UUID.fromString(BLEScanner.this.descriptorUUID));
                                    TraceLogger traceLogger4 = LoggerFactory.getTraceLogger();
                                    StringBuilder sb4 = new StringBuilder("descriptor is null:");
                                    if (descriptor2 != null) {
                                        z4 = false;
                                    }
                                    traceLogger4.debug(BLEScanner.TAG, sb4.append(z4).toString());
                                    if (descriptor2 != null) {
                                        descriptor2.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                        BLEScanner.this.bluetoothGatt.writeDescriptor(descriptor2);
                                    }
                                }
                                LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "setCharacteristicNotification enabled");
                            }
                        }
                    }
                }
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "onCharacteristicRead, status:" + status);
            if (BLEScanner.this.deviceDataInterface != null) {
                BLEScanner.this.deviceDataInterface.onReceiveDataFromDevice(gatt.getDevice().getAddress(), characteristic.getUuid().toString(), BLEScanner.this.handleReceivedData(characteristic.getValue()));
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "onCharacteristicWrite, status:" + status);
            if (BLEScanner.this.isHexData()) {
                if (BLEScanner.this.mBufferOffset < BLEScanner.this.bytes.length) {
                    int length = Math.min(BLEScanner.this.bytes.length - BLEScanner.this.mBufferOffset, 20);
                    byte[] data = new byte[length];
                    System.arraycopy(BLEScanner.this.bytes, BLEScanner.this.mBufferOffset, data, 0, length);
                    BLEScanner.this.mBufferOffset = BLEScanner.this.mBufferOffset + length;
                    BLEScanner.this.mWriteCharacteristic.setValue(data);
                    BLEScanner.this.bluetoothGatt.writeCharacteristic(BLEScanner.this.mWriteCharacteristic);
                } else {
                    LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "数据包发送完成OnCharacteristicWrite");
                }
            }
            if (BLEScanner.this.deviceDataInterface != null) {
                BLEScanner.this.deviceDataInterface.onSendDataToDevice(gatt.getDevice().getAddress(), characteristic.getUuid().toString());
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            String receivedData = BLEScanner.this.handleReceivedData(characteristic.getValue());
            LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "onCharacteristicChanged, characteristic data:" + receivedData);
            if (BLEScanner.this.deviceDataInterface != null) {
                BLEScanner.this.deviceDataInterface.onReceiveDataFromDevice(gatt.getDevice().getAddress(), characteristic.getUuid().toString(), receivedData);
            }
        }
    };
    private BluetoothManager bluetoothManager;
    private BroadcastReceiver bluetoothReceiver;
    /* access modifiers changed from: private */
    public byte[] bytes;
    private String connType;
    private Context context;
    private String dataType = "hex";
    /* access modifiers changed from: private */
    public String descriptorUUID;
    /* access modifiers changed from: private */
    public DeviceConnectionInterface deviceConnectionInterface;
    /* access modifiers changed from: private */
    public DeviceDataInterface deviceDataInterface;
    /* access modifiers changed from: private */
    public List<BluetoothDevice> deviceList;
    /* access modifiers changed from: private */
    public DeviceScanInterface deviceScanInterface;
    /* access modifiers changed from: private */
    public DeviceStateInterface deviceStateInterface;
    private String filtDeviceId;
    private String filtType;
    private Handler handler;
    private LeScanCallback leScanCallback = new LeScanCallback() {
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            LoggerFactory.getTraceLogger().debug(BLEScanner.TAG, "onLeScan, device:" + device.getName() + ",address:" + device.getAddress());
            if (!BLEScanner.this.deviceList.contains(device)) {
                BLEScanner.this.deviceList.add(device);
            }
            if (BLEScanner.this.deviceScanInterface != null) {
                BLEScanner.this.deviceScanInterface.onDeviceFound(device, rssi, BLEScanner.this.getManufacturerData(scanRecord));
            }
        }
    };
    /* access modifiers changed from: private */
    public int mBufferOffset;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic mWriteCharacteristic;
    /* access modifiers changed from: private */
    public String notifyUUID;
    /* access modifiers changed from: private */
    public String readUUID;
    /* access modifiers changed from: private */
    public String serviceUUID;
    private String writeUUID;

    /* access modifiers changed from: private */
    public String handleReceivedData(byte[] data) {
        String result = "";
        if (!isHexData()) {
            return Base64.encodeToString(data, 0);
        }
        for (byte b : data) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            result = result + hexString.toUpperCase();
        }
        return result;
    }

    public BLEScanner(Context context2) {
        this.context = context2;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.deviceList = new ArrayList();
        this.bluetoothManager = (BluetoothManager) context2.getSystemService("bluetooth");
        this.handler = new Handler(Looper.getMainLooper());
        this.descriptorUUID = DEFAULT_DESCRIPTOR_UUID;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv.toUpperCase());
        }
        return stringBuilder.toString();
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        String hexString2 = hexString.toUpperCase();
        if (hexString2.startsWith("0X")) {
            hexString2 = hexString2.substring(2);
        }
        int length = hexString2.length() / 2;
        char[] hexChars = hexString2.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) HexUtils.HEX_CHARS.indexOf(c);
    }

    /* access modifiers changed from: private */
    public String getManufacturerData(byte[] scanRecord) {
        if (scanRecord == null) {
            return null;
        }
        int currentPos = 0;
        while (true) {
            try {
                if (currentPos < scanRecord.length) {
                    int currentPos2 = currentPos + 1;
                    try {
                        int length = scanRecord[currentPos] & 255;
                        if (length != 0) {
                            int dataLength = length - 1;
                            currentPos = currentPos2 + 1;
                            switch (scanRecord[currentPos2] & 255) {
                                case 255:
                                    String hexString = bytesToHexString(extractBytes(scanRecord, currentPos, dataLength));
                                    LoggerFactory.getTraceLogger().debug(TAG, "manu data:" + hexString);
                                    return hexString;
                                default:
                                    currentPos += dataLength;
                            }
                        }
                    } catch (Exception e) {
                        LoggerFactory.getTraceLogger().error((String) TAG, "unable to parse scan record: " + Arrays.toString(scanRecord));
                        return null;
                    }
                }
            } catch (Exception e2) {
                int i = currentPos;
                LoggerFactory.getTraceLogger().error((String) TAG, "unable to parse scan record: " + Arrays.toString(scanRecord));
                return null;
            }
        }
        return null;
    }

    private static byte[] extractBytes(byte[] scanRecord, int start, int length) {
        byte[] bytes2 = new byte[length];
        System.arraycopy(scanRecord, start, bytes2, 0, length);
        return bytes2;
    }

    private void registerReceiver() {
        if (this.bluetoothReceiver == null) {
            this.bluetoothReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE)) {
                        case 10:
                            if (BLEScanner.this.deviceStateInterface != null) {
                                BLEScanner.this.deviceStateInterface.onDeviceStateChange(false);
                                return;
                            }
                            return;
                        case 12:
                            if (BLEScanner.this.deviceStateInterface != null) {
                                BLEScanner.this.deviceStateInterface.onDeviceStateChange(true);
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

    public void open() {
        LoggerFactory.getTraceLogger().debug(TAG, "open");
        registerReceiver();
    }

    public void destroy() {
        LoggerFactory.getTraceLogger().debug(TAG, "destroy");
        unregisterReceiver();
        stopScan();
        disconnect();
        close();
    }

    public void disconnectAndClose() {
        disconnect();
        this.handler.postDelayed(new Runnable() {
            public void run() {
                BLEScanner.this.close();
            }
        }, 200);
    }

    private void unregisterReceiver() {
        LoggerFactory.getTraceLogger().debug(TAG, "unregisterReceiver");
        if (this.bluetoothReceiver != null) {
            this.context.unregisterReceiver(this.bluetoothReceiver);
        }
        this.bluetoothReceiver = null;
    }

    public boolean startScan(DeviceScanInterface deviceScanInterface2) {
        boolean z;
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return false;
        }
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("startScan, bluetoothAdapter is null:");
        if (this.bluetoothAdapter == null) {
            z = true;
        } else {
            z = false;
        }
        traceLogger.debug(TAG, sb.append(z).toString());
        this.deviceList.clear();
        this.deviceScanInterface = deviceScanInterface2;
        if (this.bluetoothAdapter == null) {
            return false;
        }
        if (!TextUtils.isEmpty(this.serviceUUID)) {
            UUID uuid = BluetoothHelper.getUUIDFromString(this.serviceUUID);
            LoggerFactory.getTraceLogger().debug(TAG, "scan, serviceUUID:" + uuid);
            return this.bluetoothAdapter.startLeScan(new UUID[]{uuid}, this.leScanCallback);
        }
        LoggerFactory.getTraceLogger().debug(TAG, "scan all devices");
        return this.bluetoothAdapter.startLeScan(this.leScanCallback);
    }

    public void stopScan() {
        if (BluetoothHelper.isSupportBLE(this.context) && this.bluetoothAdapter != null) {
            this.bluetoothAdapter.stopLeScan(this.leScanCallback);
        }
    }

    public List<BluetoothDevice> getDeviceList() {
        return this.deviceList;
    }

    public List<BluetoothDevice> getConnectedDeviceList() {
        return this.bluetoothManager.getConnectedDevices(7);
    }

    public boolean connect(String address) {
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return false;
        }
        if (this.bluetoothDeviceAddress == null || !address.equals(this.bluetoothDeviceAddress) || this.bluetoothGatt == null) {
            BluetoothDevice device = this.bluetoothAdapter.getRemoteDevice(address);
            if (device == null) {
                LoggerFactory.getTraceLogger().warn((String) TAG, (String) "Device not found.  Unable to connect.");
                return false;
            }
            this.bluetoothGatt = device.connectGatt(this.context, false, this.bluetoothGattCallback);
            LoggerFactory.getTraceLogger().debug(TAG, "Trying to create a new connection.");
            this.bluetoothDeviceAddress = address;
            return true;
        }
        LoggerFactory.getTraceLogger().debug(TAG, "Trying to use an existing bluetoothGatt for connection.");
        if (this.bluetoothGatt.connect()) {
            return true;
        }
        return false;
    }

    public void disconnect() {
        if (BluetoothHelper.isSupportBLE(this.context)) {
            if (this.bluetoothAdapter == null || this.bluetoothGatt == null) {
                LoggerFactory.getTraceLogger().warn((String) TAG, (String) "BluetoothAdapter not initialized");
            } else {
                this.bluetoothGatt.disconnect();
            }
        }
    }

    public boolean sendData(String data) {
        boolean z = false;
        if (!BluetoothHelper.isSupportBLE(this.context)) {
            return z;
        }
        LoggerFactory.getTraceLogger().debug(TAG, "sendData:" + data);
        if (this.bluetoothGatt == null) {
            return z;
        }
        BluetoothGattService bluetoothGattService = this.bluetoothGatt.getService(UUID.fromString(this.serviceUUID));
        if (bluetoothGattService == null) {
            return z;
        }
        this.mWriteCharacteristic = bluetoothGattService.getCharacteristic(UUID.fromString(this.writeUUID));
        if (isHexData()) {
            LoggerFactory.getTraceLogger().debug(TAG, "writeValue hex");
            return writeValue(data);
        }
        try {
            LoggerFactory.getTraceLogger().debug(TAG, "writeValue utf-8");
            this.mWriteCharacteristic.setValue(data.getBytes("UTF-8"));
            return this.bluetoothGatt.writeCharacteristic(this.mWriteCharacteristic);
        } catch (UnsupportedEncodingException e) {
            LoggerFactory.getTraceLogger().debug(TAG, "encode error:" + e.toString());
            return z;
        }
    }

    public void close() {
        if (BluetoothHelper.isSupportBLE(this.context) && this.bluetoothGatt != null) {
            this.bluetoothGatt.close();
            this.bluetoothGatt = null;
        }
    }

    public boolean writeValue(String sendData) {
        if (this.mWriteCharacteristic == null) {
            return false;
        }
        this.mBufferOffset = 0;
        if (sendData.length() % 2 == 0) {
            this.bytes = new byte[(sendData.length() / 2)];
            for (int i = 0; i < this.bytes.length; i++) {
                this.bytes[i] = (byte) (Integer.parseInt(sendData.substring(i * 2, (i * 2) + 2), 16) & 255);
            }
            int length = Math.min(this.bytes.length, 20);
            this.mBufferOffset += length;
            byte[] data = new byte[length];
            System.arraycopy(this.bytes, 0, data, 0, length);
            this.mWriteCharacteristic.setValue(data);
            try {
                boolean result = this.bluetoothGatt.writeCharacteristic(this.mWriteCharacteristic);
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

    /* access modifiers changed from: private */
    public boolean isHexData() {
        return "hex".equalsIgnoreCase(this.dataType);
    }

    public void configDevice(JSONObject params) {
        LoggerFactory.getTraceLogger().debug(TAG, "configDevice,params:" + params);
        if (params != null) {
            this.connType = params.getString("connType");
            this.filtDeviceId = params.getString("filtDeviceId");
            this.filtType = params.getString("filtType");
            this.serviceUUID = params.getString("serviceUUID");
            this.writeUUID = params.getString("writeUUID");
            this.readUUID = params.getString("readUUID");
            this.notifyUUID = params.getString("notifyUUID");
            if (params.containsKey("dataType")) {
                this.dataType = params.getString("dataType");
            }
            this.descriptorUUID = params.getString("descriptorUUID");
        }
    }

    public void setDeviceInterface(DeviceConnectionInterface deviceConnectionInterface2, DeviceDataInterface deviceDataInterface2, DeviceStateInterface deviceStateInterface2) {
        this.deviceConnectionInterface = deviceConnectionInterface2;
        this.deviceDataInterface = deviceDataInterface2;
        this.deviceStateInterface = deviceStateInterface2;
    }
}
