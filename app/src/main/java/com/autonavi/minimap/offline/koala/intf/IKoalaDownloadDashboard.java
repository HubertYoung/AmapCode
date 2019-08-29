package com.autonavi.minimap.offline.koala.intf;

import com.autonavi.minimap.offline.koala.model.KoalaDownloadProfile;

public interface IKoalaDownloadDashboard {
    void bind(IKoalaDownloadListener iKoalaDownloadListener);

    void destroy();

    void forcePersistence();

    KoalaDownloadProfile getProfile(int i);

    boolean hasRunningTask();

    boolean isBind(IKoalaDownloadListener iKoalaDownloadListener);

    boolean isRunning(int i);

    void pause(int i);

    void pauseAll();

    void remove(int i);

    void removeAll();

    void resume(int i);

    void resumeAll();

    void start(String str);

    void start(String[] strArr);

    void stop(int i);

    void stopAll();

    void unbind(IKoalaDownloadListener iKoalaDownloadListener);
}
