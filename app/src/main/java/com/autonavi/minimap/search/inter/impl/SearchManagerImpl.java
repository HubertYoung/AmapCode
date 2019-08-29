package com.autonavi.minimap.search.inter.impl;

import android.app.Activity;
import com.autonavi.minimap.search.inter.ISearchManager;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class SearchManagerImpl implements ISearchManager {
    public ekx getSearchIntentDispatcher(Activity activity) {
        return new SearchIntentDispatcherImpl(activity);
    }
}
