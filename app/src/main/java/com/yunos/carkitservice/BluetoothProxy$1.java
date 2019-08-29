package com.yunos.carkitservice;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothProxy$1 extends BroadcastReceiver {
    final /* synthetic */ fbr a;

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.bluetooth.device.action.FOUND".equals(action)) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            StringBuilder sb = new StringBuilder(String.valueOf(bluetoothDevice.getName()));
            sb.append("\n");
            sb.append(bluetoothDevice.getAddress());
        } else if ("android.bluetooth.device.action.CLASS_CHANGED".equals(action)) {
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            StringBuilder sb2 = new StringBuilder(String.valueOf(bluetoothDevice2.getName()));
            sb2.append("\n");
            sb2.append(bluetoothDevice2.getAddress());
            new StringBuilder("class=").append(((BluetoothClass) intent.getParcelableExtra("android.bluetooth.device.extra.CLASS")).getDeviceClass());
        } else if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(action)) {
            BluetoothDevice bluetoothDevice3 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            this.a.a.a(bluetoothDevice3.getAddress());
            StringBuilder sb3 = new StringBuilder("acl disconnect");
            sb3.append(bluetoothDevice3.getName());
            sb3.append("\n");
            sb3.append(bluetoothDevice3.getAddress());
        } else if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
            BluetoothDevice bluetoothDevice4 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            StringBuilder sb4 = new StringBuilder(String.valueOf(bluetoothDevice4.getName()));
            sb4.append("\n");
            sb4.append(bluetoothDevice4.getAddress());
            "bond state=".concat(String.valueOf(intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", 0)));
        } else {
            if ("android.bluetooth.device.action.ACL_CONNECTED".equals(action)) {
                BluetoothDevice bluetoothDevice5 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                StringBuilder sb5 = new StringBuilder("acl connect");
                sb5.append(bluetoothDevice5.getName());
                sb5.append("\n");
                sb5.append(bluetoothDevice5.getAddress());
                this.a.a.a(bluetoothDevice5);
            }
        }
    }
}
