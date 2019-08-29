package com.alipay.android.phone.bluetoothsdk;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.service.H5Service;
import java.util.List;

public class BleServiceImpl extends BleService {
    private static final String TAG = "BleServiceImpl";
    private BLEScanner bleScanner;
    private H5BlePagePlugin pagePlugin;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        LoggerFactory.getTraceLogger().debug(TAG, "onCreate");
        this.bleScanner = new BLEScanner(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        LoggerFactory.getTraceLogger().debug(TAG, "destroy");
    }

    public boolean isSupportBLE() {
        return BluetoothHelper.isSupportBLE(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    public int getBluetoothState() {
        return BluetoothHelper.getBluetoothState().ordinal();
    }

    public boolean openBluetooth() {
        if (this.bleScanner != null) {
            this.bleScanner.open();
        }
        registerH5PagePlugin();
        return true;
    }

    public boolean closeBluetooth() {
        if (this.bleScanner != null) {
            this.bleScanner.destroy();
        }
        unregisterH5PagePlugin();
        return true;
    }

    private void registerH5PagePlugin() {
        if (this.pagePlugin == null) {
            this.pagePlugin = new H5BlePagePlugin();
            H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName());
            if (h5Service != null) {
                h5Service.getPluginManager().register((H5Plugin) this.pagePlugin);
            }
        }
    }

    private void unregisterH5PagePlugin() {
        if (this.pagePlugin != null) {
            H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName());
            if (h5Service != null) {
                h5Service.getPluginManager().unregister((H5Plugin) this.pagePlugin);
            }
        }
        this.pagePlugin = null;
    }

    public boolean startScan(DeviceScanInterface deviceScanInterface) {
        return this.bleScanner.startScan(deviceScanInterface);
    }

    public void stopScan() {
        this.bleScanner.stopScan();
    }

    public boolean connect(String address) {
        return this.bleScanner.connect(address);
    }

    public void disconnect() {
        this.bleScanner.disconnectAndClose();
    }

    public List<BluetoothDevice> getDeviceList() {
        return this.bleScanner.getDeviceList();
    }

    public List<BluetoothDevice> getConnectedDeviceList() {
        return this.bleScanner.getConnectedDeviceList();
    }

    public boolean sendDataToDevice(String data) {
        return this.bleScanner.sendData(data);
    }

    public void configDevice(JSONObject params) {
        this.bleScanner.configDevice(params);
    }

    public void setDeviceInterface(DeviceConnectionInterface deviceConnectionInterface, DeviceDataInterface deviceDataInterface, DeviceStateInterface deviceStateInterface) {
        this.bleScanner.setDeviceInterface(deviceConnectionInterface, deviceDataInterface, deviceStateInterface);
    }
}
