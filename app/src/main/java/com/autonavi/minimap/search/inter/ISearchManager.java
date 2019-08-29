package com.autonavi.minimap.search.inter;

import android.app.Activity;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface ISearchManager {
    ekx getSearchIntentDispatcher(Activity activity);
}
