package com.autonavi.minimap.offline.intent.inter;

import android.app.Activity;
import com.autonavi.minimap.offline.inter.inner.IOfflineIntentDispatcher;

public interface IOfflineIntentDispatcherFactory extends bie {
    IOfflineIntentDispatcher getOfflineIntentDispatcher(Activity activity);
}
