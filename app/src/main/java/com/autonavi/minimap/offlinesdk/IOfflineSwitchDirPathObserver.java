package com.autonavi.minimap.offlinesdk;

public interface IOfflineSwitchDirPathObserver {
    void onSwitchDirPathFailed(int i);

    void onSwitchDirPathFinished();

    void onSwitchDirPathProcessing(int i);
}
