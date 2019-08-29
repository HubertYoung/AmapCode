package com.alipay.android.phone.bluetoothsdk.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.AdvertiseSettings.Builder;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import com.alipay.android.phone.bluetoothsdk.BluetoothHelper;
import com.alipay.android.phone.bluetoothsdk.Logger;
import com.alipay.android.phone.bluetoothsdk.scan.message.protocol.MessageDivider;
import com.alipay.android.phone.bluetoothsdk.scan.message.protocol.MessageFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@TargetApi(21)
public class BleAdvertiser {
    private static final String TAG = "BleAdvertiser";
    public static final UUID UUID_SERVER = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    private List<BleCallback> bleCallbackList = new ArrayList();
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private Context context;

    class BleCallback extends AdvertiseCallback {
        private BleCallback() {
        }

        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            Logger.d(BleAdvertiser.TAG, "BLE advertisement added successfully");
        }

        public void onStartFailure(int errorCode) {
            Logger.e(BleAdvertiser.TAG, "Failed to add BLE advertisement, reason: " + errorCode);
        }
    }

    public BleAdvertiser(Context context2) {
        this.context = context2;
        this.bluetoothManager = (BluetoothManager) context2.getSystemService("bluetooth");
        this.bluetoothAdapter = this.bluetoothManager.getAdapter();
    }

    public boolean startMultipleAdvertise(String message) {
        AdvertiseData scanResponseData;
        if (!BluetoothHelper.isSupportBLE(this.context) || this.bluetoothAdapter == null || !this.bluetoothAdapter.isEnabled() || !this.bluetoothAdapter.isMultipleAdvertisementSupported()) {
            return false;
        }
        MessageDivider messageDivider = new MessageDivider();
        messageDivider.divide(message);
        List messageFragments = messageDivider.getFragments();
        for (int i = 0; i < messageFragments.size(); i += 2) {
            MessageFragment messageFragment1 = messageFragments.get(i);
            MessageFragment messageFragment2 = null;
            if (i < messageFragments.size() - 1) {
                messageFragment2 = messageFragments.get(i + 1);
            }
            ParcelUuid uuid = new ParcelUuid(UUID_SERVER);
            AdvertiseSettings settings = new Builder().setConnectable(true).setAdvertiseMode(1).setTxPowerLevel(3).build();
            AdvertiseData advertiseData = new AdvertiseData.Builder().setIncludeDeviceName(false).setIncludeTxPowerLevel(false).addServiceUuid(uuid).addManufacturerData(1, messageFragment1.getData()).build();
            Log.d(TAG, "messageFragment1 data:" + BluetoothHelper.bytesToHexString(messageFragment1.getData()));
            if (messageFragment2 != null) {
                scanResponseData = new AdvertiseData.Builder().setIncludeTxPowerLevel(false).addManufacturerData(2, messageFragment2.getData()).build();
                Log.d(TAG, "messageFragment2 data:" + BluetoothHelper.bytesToHexString(messageFragment2.getData()));
            } else {
                scanResponseData = new AdvertiseData.Builder().setIncludeTxPowerLevel(false).build();
            }
            BleCallback bleCallback = new BleCallback();
            this.bluetoothAdapter.getBluetoothLeAdvertiser().startAdvertising(settings, advertiseData, scanResponseData, bleCallback);
            this.bleCallbackList.add(bleCallback);
        }
        return true;
    }

    public void stopMultipleAdvertise() {
        if (!this.bleCallbackList.isEmpty()) {
            BluetoothLeAdvertiser bluetoothLeAdvertiser = this.bluetoothAdapter.getBluetoothLeAdvertiser();
            for (BleCallback bleCallback : this.bleCallbackList) {
                bluetoothLeAdvertiser.stopAdvertising(bleCallback);
            }
            this.bleCallbackList.clear();
        }
    }
}
