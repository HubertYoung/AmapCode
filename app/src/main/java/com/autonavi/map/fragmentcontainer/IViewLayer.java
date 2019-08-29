package com.autonavi.map.fragmentcontainer;

import android.content.res.Configuration;
import android.view.View;

public interface IViewLayer {
    View getView();

    boolean onBackPressed();

    void onConfigurationChanged(Configuration configuration);

    void showBackground(boolean z);
}
