package com.autonavi.map.fragmentcontainer.page.dialog;

import android.content.res.Configuration;
import android.view.KeyEvent;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;

public class NodeAlertDialogPresenter extends AbstractBasePresenter<NodeAlertDialogPage> {
    public NodeAlertDialogPresenter(NodeAlertDialogPage nodeAlertDialogPage) {
        super(nodeAlertDialogPage);
    }

    public void onPageCreated() {
        super.onPageCreated();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((NodeAlertDialogPage) this.mPage).onPageConfigurationChanged(configuration);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return ((NodeAlertDialogPage) this.mPage).onPageKeyDown(i, keyEvent) || super.onKeyDown(i, keyEvent);
    }

    public void onStart() {
        ((NodeAlertDialogPage) this.mPage).onPageResume();
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        ((NodeAlertDialogPage) this.mPage).onPageFinishFragment();
        ((NodeAlertDialogPage) this.mPage).onPageDestroy();
        super.onDestroy();
    }

    public ON_BACK_TYPE onBackPressed() {
        ON_BACK_TYPE onBackPressed = super.onBackPressed();
        if (onBackPressed == null || onBackPressed == ON_BACK_TYPE.TYPE_NORMAL) {
            return ((NodeAlertDialogPage) this.mPage).onPageBackPressed();
        }
        return onBackPressed;
    }
}
