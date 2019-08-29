package com.autonavi.minimap.ajx3;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;

public class Ajx3DialogPagePresenter extends AbstractBasePresenter<Ajx3DialogPage> {
    public Ajx3DialogPagePresenter(Ajx3DialogPage ajx3DialogPage) {
        super(ajx3DialogPage);
    }

    public void onResume() {
        super.onResume();
        ((Ajx3DialogPage) this.mPage).resume();
    }

    public void onStop() {
        super.onStop();
        ((Ajx3DialogPage) this.mPage).stop();
    }

    public void onDestroy() {
        super.onDestroy();
        ((Ajx3DialogPage) this.mPage).destroy();
    }

    public ON_BACK_TYPE onBackPressed() {
        if (((Ajx3DialogPage) this.mPage).backPressed()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        ((Ajx3DialogPage) this.mPage).result(i, resultType, pageBundle);
    }
}
