package com.alipay.android.phone.bluetoothsdk;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;

public class MonitorHelper {
    private static Behavor getBluetoothBehavor() {
        Behavor behavor = new Behavor();
        behavor.setParam3("bluetooth");
        return behavor;
    }

    public static void logOpenBLEAdapter() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("openBLEAdapter");
        behavor.setUserCaseID("openBLEAdapter");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logStartBLEScan() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("startBLEScan");
        behavor.setUserCaseID("startBLEScan");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logConnectBLE() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("connectBLE");
        behavor.setUserCaseID("connectBLE");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logDisconnectBLE() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("disconnectBLE");
        behavor.setUserCaseID("disconnectBLE");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logCloseBLEAdapter() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("closeBLEAdapter");
        behavor.setUserCaseID("closeBLEAdapter");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logConnectBLEErr(String errorMessage) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("connectBLEErr");
        behavor.setUserCaseID("connectBLEErr");
        behavor.setParam1(errorMessage);
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logWriteBLEErr(String errorMessage) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("writeBLEErr");
        behavor.setUserCaseID("writeBLEErr");
        behavor.setParam1(errorMessage);
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logReadBLEErr(String errorMessage) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("readBLEErr");
        behavor.setUserCaseID("readBLEErr");
        behavor.setParam1(errorMessage);
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logBleKeepTime(long persistTime) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("bleKeepTime");
        behavor.setUserCaseID("bleKeepTime");
        behavor.setParam1(String.valueOf(persistTime));
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logFirstScanTime(long time) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("firstScanTime");
        behavor.setUserCaseID("firstScanTime");
        behavor.setParam1(String.valueOf(time));
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logConnectBLETime(long time) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("connectBLETime");
        behavor.setUserCaseID("connectBLETime");
        behavor.setParam1(String.valueOf(time));
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logKeepConnectTime(long time) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("keepConnectTime");
        behavor.setUserCaseID("keepConnectTime");
        behavor.setParam1(String.valueOf(time));
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logWriteBLETime(long time) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("writeBLETime");
        behavor.setUserCaseID("writeBLETime");
        behavor.setParam1(String.valueOf(time));
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logOnDisconnectBLE() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("onDisconnectBLE");
        behavor.setUserCaseID("onDisconnectBLE");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logConnectBLESucc() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("connectBLESucc");
        behavor.setUserCaseID("connectBLESucc");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logReadDataBLE() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("readDataBLE");
        behavor.setUserCaseID("readDataBLE");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logWriteDataBLE() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("writeDataBLE");
        behavor.setUserCaseID("writeDataBLE");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logNotifyBLE() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("notifyBLE");
        behavor.setUserCaseID("notifyBLE");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logNotifyBLEErr(String errorMessage) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("notifyBLEErr");
        behavor.setUserCaseID("notifyBLEErr");
        behavor.setParam1(errorMessage);
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logBluetoothEnabled(boolean enabled) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("bluetoothEnabled");
        behavor.setUserCaseID("bluetoothEnabled");
        behavor.setParam1(String.valueOf(enabled));
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logStopBLEScan() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("stopBLEScan");
        behavor.setUserCaseID("stopBLEScan");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logStartBeaconDiscovery() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("startBeaconDiscovery");
        behavor.setUserCaseID("startBeaconDiscovery");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logStopBeaconDiscovery() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("stopBeaconDiscovery");
        behavor.setUserCaseID("stopBeaconDiscovery");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logGetBeacons() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("getBeacons");
        behavor.setUserCaseID("getBeacons");
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logDiscoverServiceTime(long time) {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID("discoverServiceTime");
        behavor.setUserCaseID("discoverServiceTime");
        behavor.setParam1(String.valueOf(time));
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }

    public static void logOldH5Funtion() {
        Behavor behavor = getBluetoothBehavor();
        behavor.setSeedID(H5BlePlugin.OPEN_APDEVICE_LIB);
        behavor.setUserCaseID(H5BlePlugin.OPEN_APDEVICE_LIB);
        LoggerFactory.getBehavorLogger().event("clicked", behavor);
    }
}
