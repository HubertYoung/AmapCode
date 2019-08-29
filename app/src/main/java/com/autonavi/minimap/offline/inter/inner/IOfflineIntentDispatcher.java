package com.autonavi.minimap.offline.inter.inner;

import android.content.Intent;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IOfflineIntentDispatcher extends bie {
    boolean dispatch(Intent intent);

    void doOpenFeatureOfflineEnlargement();

    void doOpenFeatureOfflineMap();

    void doOpenFeatureOfflineNavi();

    void doOpenFeatureOfflineQuickNavi();
}
