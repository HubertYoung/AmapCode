package com.amap.bundle.drive.offline;

import android.net.NetworkInfo;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IDriveOfflineManager extends bie {
    void destroy();

    void init();

    void onConnectionChanged(NetworkInfo networkInfo);
}
