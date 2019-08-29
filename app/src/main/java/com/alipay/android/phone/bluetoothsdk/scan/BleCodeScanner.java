package com.alipay.android.phone.bluetoothsdk.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.bluetoothsdk.BluetoothHelper;
import com.alipay.android.phone.bluetoothsdk.Logger;
import com.alipay.android.phone.bluetoothsdk.ScanRecord;
import com.alipay.android.phone.bluetoothsdk.better.ble.BleDevice;
import com.alipay.android.phone.bluetoothsdk.scan.message.protocol.MessageComposer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@TargetApi(18)
public class BleCodeScanner {
    private static final int MANU_ID1 = 1;
    private static final int MANU_ID2 = 2;
    private static final String TAG = "BleCodeScanner";
    private static final double coefficient1 = 0.42093d;
    private static final double coefficient2 = 6.9476d;
    private static final double coefficient3 = 0.54992d;
    private BluetoothAdapter bluetoothAdapter;
    private Set<String> codeSet;
    private Context context;
    /* access modifiers changed from: private */
    public HashMap<String, BleDevice> deviceMap;
    private long lastCommitTime;
    private LeScanCallback leScanCallback = new LeScanCallback() {
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (!BleCodeScanner.this.deviceMap.containsKey(device.getAddress())) {
                ScanRecord record = ScanRecord.parseFromBytes(scanRecord);
                Logger.d(BleCodeScanner.TAG, "scanRecord length:" + scanRecord.length + ",record:" + record.toString());
                BleCodeScanner.this.handleAdvertiseData(record);
                BleCodeScanner.this.deviceMap.put(device.getAddress(), BleDevice.createBleDevice(device));
                return;
            }
            BleCodeScanner.this.deviceMap.get(device.getAddress());
        }
    };
    private MessageComposer messageComposer;
    private ScanListener scanListener;

    public interface ScanListener {
        void onCodeFound(String str);
    }

    public BleCodeScanner(Context context2) {
        this.context = context2;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.deviceMap = new HashMap<>();
        this.codeSet = new HashSet();
        this.lastCommitTime = 0;
        this.messageComposer = MessageComposer.getComposer();
    }

    public void setScanListener(ScanListener scanListener2) {
        this.scanListener = scanListener2;
    }

    public boolean startScan() {
        if (!BluetoothHelper.isSupportBLE(this.context) || this.bluetoothAdapter == null || !this.bluetoothAdapter.isEnabled()) {
            return false;
        }
        return this.bluetoothAdapter.startLeScan(new UUID[]{BleAdvertiser.UUID_SERVER}, this.leScanCallback);
    }

    public void stopScan() {
        if (this.bluetoothAdapter != null) {
            this.bluetoothAdapter.stopLeScan(this.leScanCallback);
        }
        this.deviceMap.clear();
        this.codeSet.clear();
    }

    /* access modifiers changed from: private */
    public void handleAdvertiseData(ScanRecord scanRecord) {
        if (scanRecord != null && scanRecord.getManufacturerSpecificData() != null) {
            String result = null;
            if (scanRecord.getManufacturerSpecificData(1) != null) {
                result = this.messageComposer.receiveFragment(scanRecord.getManufacturerSpecificData(1));
                Logger.d(TAG, "manuData1:" + BluetoothHelper.bytesToHexString(scanRecord.getManufacturerSpecificData(1)));
            }
            if (scanRecord.getManufacturerSpecificData(2) != null) {
                result = this.messageComposer.receiveFragment(scanRecord.getManufacturerSpecificData(2));
                Logger.d(TAG, "manuData2:" + BluetoothHelper.bytesToHexString(scanRecord.getManufacturerSpecificData(2)));
            }
            if (!TextUtils.isEmpty(result)) {
                Logger.d(TAG, "found qrcode:" + result);
                if (this.scanListener != null) {
                    this.scanListener.onCodeFound(result);
                }
            }
        }
    }

    private double calculateDistance(int txPowerLevel, double rssi) {
        if (rssi == 0.0d) {
            return -1.0d;
        }
        int txPower = -75;
        if (txPowerLevel == 3) {
            txPower = -56;
        } else if (txPowerLevel == 1) {
            txPower = -75;
        } else if (txPowerLevel == 2) {
            txPower = -66;
        }
        double ratio = (rssi * 1.0d) / ((double) txPower);
        if (ratio < 1.0d) {
            return Math.pow(ratio, 10.0d);
        }
        return (coefficient1 * Math.pow(ratio, coefficient2)) + coefficient3;
    }

    private double calculateDistance2(int txPowerLevel, double rssi) {
        return Math.pow(10.0d, ((((double) txPowerLevel) - rssi) - 40.0d) / 25.0d);
    }
}
