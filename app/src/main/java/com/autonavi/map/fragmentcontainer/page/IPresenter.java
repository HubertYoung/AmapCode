package com.autonavi.map.fragmentcontainer.page;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPage;

public interface IPresenter<Page extends IPage> {
    void onActivityResult(int i, int i2, Intent intent);

    ON_BACK_TYPE onBackPressed();

    void onConfigurationChanged(Configuration configuration);

    void onDestroy();

    boolean onKeyDown(int i, KeyEvent keyEvent);

    void onNewIntent(PageBundle pageBundle);

    void onPageCreated();

    void onPageCreated(int i, int i2);

    void onPause();

    void onResult(int i, ResultType resultType, PageBundle pageBundle);

    void onResume();

    void onStart();

    void onStop();

    void onWindowFocusChanged(boolean z);
}
