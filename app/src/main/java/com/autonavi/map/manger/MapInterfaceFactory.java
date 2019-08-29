package com.autonavi.map.manger;

import android.app.Activity;
import android.content.Intent;

public abstract class MapInterfaceFactory {
    public static MapInterfaceFactory instance;

    public abstract IIntentUtil getIntentUtil(Activity activity, Intent intent);

    public static MapInterfaceFactory getInstance() {
        return instance;
    }
}
