package com.autonavi.map.widget;

import com.autonavi.common.PageBundle;

public interface ITrafficViewForFeed {

    public interface OnDismissListener {
        void onDismiss(ITrafficViewForFeed iTrafficViewForFeed);
    }

    void clearTrafficItem();

    void dismiss(boolean z);

    void onMapSurfaceChanged();

    void refreshByScreenState();

    boolean updateTrafficEvent(PageBundle pageBundle);
}
