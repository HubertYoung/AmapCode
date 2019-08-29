package com.autonavi.minimap.search.inter;

import android.graphics.Rect;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface ISearchResultController {
    aea<InfoliteResult> getSearchCallBackEx(String str);

    aea<InfoliteResult> setSearchRect(Rect rect, String str);

    void setSearchRect(aea aea, Rect rect);

    aea setSearchResultListener(String str, int i, boolean z, boolean z2);
}
