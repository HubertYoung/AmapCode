package com.autonavi.mqtt;

public interface IPushCallback {
    void connectionLost(String str);

    void deliveryComplete(int i);

    void messageArrived(String str, String str2);

    void messageCleared(String str, String str2, Object obj);

    void messagePublished(String str, String str2, int i, Object obj);

    void onConnected();

    void onError(int i);

    void onLog(String str);

    void onUnInited();
}
