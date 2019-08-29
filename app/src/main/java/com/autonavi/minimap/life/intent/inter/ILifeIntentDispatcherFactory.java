package com.autonavi.minimap.life.intent.inter;

import android.app.Activity;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface ILifeIntentDispatcherFactory {
    dpc a(Activity activity);
}
