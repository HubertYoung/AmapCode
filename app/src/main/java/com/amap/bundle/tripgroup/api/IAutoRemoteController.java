package com.amap.bundle.tripgroup.api;

import com.autonavi.link.connect.model.DiscoverInfo;
import java.io.IOException;
import java.util.Map;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepImplementations
@KeepName
public interface IAutoRemoteController extends bie, esc {

    public enum ConnectionType {
        WIFI,
        BLUETOOTH
    }

    boolean IsBtConnected();

    boolean IsWifiConnected();

    void addAlinkWifiConnectionListener(AlinkConnectionListener alinkConnectionListener);

    void checkNeedStartBluetoothServer();

    void doConnectBt(String str);

    byte[] getBytes(String str, Map<String, String> map) throws IOException;

    DiscoverInfo getWifiDiscoverInfo();

    boolean hasBoundToAuto();

    void init();

    boolean isNewAmapSDK();

    boolean isParied(String str);

    boolean lowVersionAutoConnected();

    boolean pairDevice(String str);

    byte[] postBytes(String str, Map<String, String> map, byte[] bArr) throws IOException;

    void promptToEnableBluetoothBeforePairing(String str);

    void reconnectBt();

    void release();

    void removeAlinkWifiConnectionListener(AlinkConnectionListener alinkConnectionListener);

    void removeRemoteControlConnectListener(aga aga);

    void restoreViewByConnectionState();

    void setAutoRemoteViewUpdateListener(afv afv);

    void setRemoteControlConnectListener(aga aga);

    void startAlinkWifi(afw afw);

    void stopALinkBt();

    void stopALinkWifi();
}
