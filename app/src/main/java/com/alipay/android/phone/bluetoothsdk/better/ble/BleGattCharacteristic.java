package com.alipay.android.phone.bluetoothsdk.better.ble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattCharacteristic;
import com.alipay.android.phone.bluetoothsdk.BluetoothHelper;

@TargetApi(18)
public class BleGattCharacteristic {
    public String characteristicId;
    public CharacteristicProperty properties;
    public String serviceId;
    public String value;

    public static BleGattCharacteristic createCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        BleGattCharacteristic characteristic = new BleGattCharacteristic();
        characteristic.characteristicId = bluetoothGattCharacteristic.getUuid().toString();
        characteristic.serviceId = bluetoothGattCharacteristic.getService().getUuid().toString();
        characteristic.value = BluetoothHelper.bytesToHexString(bluetoothGattCharacteristic.getValue());
        characteristic.properties = new CharacteristicProperty();
        characteristic.properties.read = (bluetoothGattCharacteristic.getProperties() & 2) != 0;
        CharacteristicProperty characteristicProperty = characteristic.properties;
        if ((bluetoothGattCharacteristic.getProperties() & 8) == 0 && (bluetoothGattCharacteristic.getProperties() & 4) == 0) {
            z = false;
        } else {
            z = true;
        }
        characteristicProperty.write = z;
        CharacteristicProperty characteristicProperty2 = characteristic.properties;
        if ((bluetoothGattCharacteristic.getProperties() & 32) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        characteristicProperty2.indicate = z2;
        CharacteristicProperty characteristicProperty3 = characteristic.properties;
        if ((bluetoothGattCharacteristic.getProperties() & 16) == 0) {
            z3 = false;
        }
        characteristicProperty3.notify = z3;
        return characteristic;
    }

    public static BleGattCharacteristic createCharacteristic(String serviceId2, String characteristicId2, String value2) {
        BleGattCharacteristic characteristic = new BleGattCharacteristic();
        characteristic.serviceId = serviceId2;
        characteristic.characteristicId = characteristicId2;
        characteristic.value = value2;
        return characteristic;
    }
}
