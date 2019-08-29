package com.autonavi.map.fragmentcontainer.page;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPage;

public abstract class AbstractBasePresenter<Page extends IPage> implements IPresenter {
    public Page mPage;

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onDestroy() {
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return false;
    }

    public void onNewIntent(PageBundle pageBundle) {
    }

    public void onPageCreated() {
    }

    public void onPageCreated(int i, int i2) {
    }

    public void onPause() {
    }

    public void onResult(int i, ResultType resultType, PageBundle pageBundle) {
    }

    public void onResume() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onWindowFocusChanged(boolean z) {
    }

    public AbstractBasePresenter(Page page) {
        this.mPage = page;
    }

    public ON_BACK_TYPE onBackPressed() {
        return ON_BACK_TYPE.TYPE_NORMAL;
    }
}
