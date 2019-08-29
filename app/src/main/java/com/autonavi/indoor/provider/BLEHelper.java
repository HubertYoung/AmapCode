package com.autonavi.indoor.provider;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.support.annotation.RequiresPermission;
import com.autonavi.indoor.util.L;

public class BLEHelper {
    private final Context context;

    public BLEHelper(Context context2) {
        this.context = context2;
    }

    @TargetApi(18)
    public boolean hasBluetooth() {
        if (this.context == null) {
            if (L.isLogging) {
                L.d((String) "Can't get BLE because context is null");
            }
            return false;
        } else if (VERSION.SDK_INT < 18) {
            return false;
        } else {
            return this.context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
        }
    }

    public boolean checkPermissions() {
        if (this.context == null) {
            if (L.isLogging) {
                L.d((String) "Can't get BLE because context is null");
            }
            return false;
        }
        PackageManager packageManager = this.context.getPackageManager();
        int checkPermission = packageManager.checkPermission("android.permission.BLUETOOTH", this.context.getPackageName());
        int checkPermission2 = packageManager.checkPermission("android.permission.BLUETOOTH_ADMIN", this.context.getPackageName());
        if (checkPermission == 0 && checkPermission2 == 0) {
            return true;
        }
        return false;
    }

    @TargetApi(18)
    @RequiresPermission("android.permission.BLUETOOTH")
    public boolean isBluetoothEnabled() {
        if (this.context == null) {
            if (L.isLogging) {
                L.d((String) "Can't get BLE because context is null");
            }
            return false;
        } else if (VERSION.SDK_INT < 18 || !checkPermissions()) {
            return false;
        } else {
            BluetoothAdapter adapter = ((BluetoothManager) this.context.getSystemService("bluetooth")).getAdapter();
            if (adapter == null || !adapter.isEnabled()) {
                return false;
            }
            return true;
        }
    }

    @TargetApi(18)
    @RequiresPermission(allOf = {"android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN"})
    public boolean enable() {
        if (this.context == null) {
            if (L.isLogging) {
                L.d((String) "Can't get BLE because context is null");
            }
            return false;
        } else if (VERSION.SDK_INT < 18) {
            return false;
        } else {
            BluetoothAdapter adapter = ((BluetoothManager) this.context.getSystemService("bluetooth")).getAdapter();
            if (adapter.getState() == 12 || adapter.enable()) {
                return true;
            }
            return false;
        }
    }

    @TargetApi(18)
    public BluetoothAdapter getBluetoothAdapter() {
        if (this.context == null) {
            if (L.isLogging) {
                L.d((String) "Can't get BLE because context is null");
            }
            return null;
        } else if (VERSION.SDK_INT < 18) {
            return null;
        } else {
            return ((BluetoothManager) this.context.getSystemService("bluetooth")).getAdapter();
        }
    }
}
