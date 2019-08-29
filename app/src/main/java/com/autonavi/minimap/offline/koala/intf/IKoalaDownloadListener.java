package com.autonavi.minimap.offline.koala.intf;

public interface IKoalaDownloadListener {
    void onAfterAction(int i, Object obj);

    void onBeforeAction(int i);

    void onBind(int i);

    void onBlockComplete(int i, String str);

    void onComplete(int i);

    void onConnected(int i, String str);

    void onError(int i, Throwable th);

    void onPause(int i);

    void onPending(int i);

    void onProgress(int i, String str, long j, long j2, long j3, long j4);

    void onRemove(int i);

    void onResume(int i);
}
