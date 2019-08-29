package com.autonavi.jni.drive.offline;

public interface HttpRequestObserver {
    void onRequestFailed(int i);

    void onRequestFinished();

    void onRequestReceiveData(byte[] bArr, int i);
}
