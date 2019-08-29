package com.autonavi.map.fragmentcontainer.page;

import android.content.Context;
import android.content.res.Configuration;
import android.view.KeyEvent;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.IPresenter;

public interface IPage<Presenter extends IPresenter> {
    Context getContext();

    boolean isJustNowCreated();

    boolean isShowMapWidgets();

    boolean isShowPageHeader();

    ON_BACK_TYPE onBackPressed();

    void onConfigurationChanged(Configuration configuration);

    void onCreate(Context context);

    void onDestroy();

    boolean onKeyDown(int i, KeyEvent keyEvent);

    void onPause();

    void onResume();

    void onStart();

    void onStop();

    void onWindowFocusChanged(boolean z);
}
