package com.autonavi.minimap.offline.intent.inter.impl;

import android.app.Activity;
import com.autonavi.minimap.offline.intent.inter.IOfflineIntentDispatcherFactory;
import com.autonavi.minimap.offline.inter.inner.IOfflineIntentDispatcher;

public class OfflineIntentDispatcherFactoryImpl implements IOfflineIntentDispatcherFactory {
    public IOfflineIntentDispatcher getOfflineIntentDispatcher(Activity activity) {
        return new OfflineIntentDispatcher();
    }
}
