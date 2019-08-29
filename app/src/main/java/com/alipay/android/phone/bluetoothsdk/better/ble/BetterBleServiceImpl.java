package com.alipay.android.phone.bluetoothsdk.better.ble;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.bluetoothsdk.BluetoothHelper;
import com.alipay.android.phone.bluetoothsdk.H5BlePagePlugin;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.service.H5Service;
import java.util.List;

public class BetterBleServiceImpl extends BetterBleService {
    private static final String KEY_AUTO_RELEASE_BLE = "BLE_AUTO_RELEASE_ON_EXIT_H5";
    private static final String TAG = "BetterBleServiceImpl";
    private BLEManager bleManager;
    private String h5ActivityInstance;
    private H5BlePagePlugin pagePlugin;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        LoggerFactory.getTraceLogger().debug(TAG, "onCreate");
        this.bleManager = new BLEManager(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        LoggerFactory.getTraceLogger().debug(TAG, "destroy");
        this.bleManager = null;
    }

    public boolean isSupportBLE() {
        return BluetoothHelper.isSupportBLE(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    public int getBluetoothState() {
        return BluetoothHelper.getBluetoothState().ordinal();
    }

    public void openBluetoothAdapter(String h5ActivityInstance2, boolean autoCloseOnPageOff) {
        if (this.bleManager != null) {
            this.bleManager.openBluetoothAdapter();
        } else {
            this.bleManager = new BLEManager(LauncherApplicationAgent.getInstance().getApplicationContext());
            this.bleManager.openBluetoothAdapter();
        }
        this.h5ActivityInstance = h5ActivityInstance2;
        if (autoCloseOnPageOff) {
            ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
            if (configService != null) {
                String enableAutoRelease = configService.getConfig(KEY_AUTO_RELEASE_BLE);
                if (!TextUtils.isEmpty(enableAutoRelease) && Boolean.parseBoolean(enableAutoRelease)) {
                    registerH5PagePlugin();
                }
            }
        }
    }

    public void closeBluetoothAdapter() {
        if (this.bleManager != null) {
            this.bleManager.closeBluetoothAdapter();
        }
        this.h5ActivityInstance = null;
        unregisterH5PagePlugin();
    }

    public boolean isDiscovering() {
        if (this.bleManager != null) {
            return this.bleManager.isDiscovering();
        }
        return false;
    }

    public boolean isOpened() {
        if (this.bleManager != null) {
            return this.bleManager.isOpened();
        }
        return false;
    }

    public BleResult startBluetoothDevicesDiscovery(String[] serviceUUIDs, boolean allowDuplicatesKey, int interval) {
        if (this.bleManager != null) {
            return this.bleManager.startBleScan(serviceUUIDs, allowDuplicatesKey, interval);
        }
        return new BleResult(false, true, ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED);
    }

    public void stopBluetoothDevicesDiscovery() {
        if (this.bleManager != null) {
            this.bleManager.stopBleScan();
        }
    }

    public List<BleDevice> getBluetoothDevices() {
        if (this.bleManager != null) {
            return this.bleManager.getBluetoothDevices();
        }
        return null;
    }

    public List<BleDevice> getConnectedBluetoothDevices() {
        if (this.bleManager != null) {
            return this.bleManager.getConnectedBluetoothDevices();
        }
        return null;
    }

    public BleResult connectBluetoothDevice(String address) {
        if (this.bleManager != null) {
            return this.bleManager.connect(address);
        }
        return new BleResult(false, true, ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED);
    }

    public BleResult disconnectBluetoothDevice(String address) {
        if (this.bleManager != null) {
            return this.bleManager.disconnectAndClose(address);
        }
        return new BleResult(false, true, ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED);
    }

    public void setBetterBleListener(BetterBleListener betterBleListener) {
        if (this.bleManager != null) {
            this.bleManager.setBetterBleListener(betterBleListener);
        }
    }

    public BleResult getBluetoothServices(String address) {
        if (this.bleManager != null) {
            return this.bleManager.getBluetoothServices(address);
        }
        return new BleResult(false, true, ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED);
    }

    public BleResult getBluetoothCharacteristics(String address, String serviceUUID) {
        if (this.bleManager != null) {
            return this.bleManager.getBluetoothCharacteristics(address, serviceUUID);
        }
        return new BleResult(false, true, ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED);
    }

    public BleResult sendData(String address, String serviceUUID, String characteristicUUID, String data) {
        if (this.bleManager != null) {
            return this.bleManager.sendData(address, serviceUUID, characteristicUUID, data);
        }
        return new BleResult(false, true, ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED);
    }

    public BleResult readData(String address, String serviceUUID, String characteristicUUID) {
        if (this.bleManager != null) {
            return this.bleManager.readData(address, serviceUUID, characteristicUUID);
        }
        return new BleResult(false, true, ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED);
    }

    public BleResult notifyCharacteristicValueChange(String address, String serviceUUID, String characteristicUUID, String descriptorUUID, boolean enable) {
        if (this.bleManager != null) {
            return this.bleManager.notifyCharacteristicValue(address, serviceUUID, characteristicUUID, descriptorUUID, enable);
        }
        return new BleResult(false, true, ErrorConstants.ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED);
    }

    public String getH5ActivityInstance() {
        return this.h5ActivityInstance;
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
}
