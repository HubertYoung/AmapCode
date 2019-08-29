package com.autonavi.minimap.ajx3;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;

public class Ajx3PagePresenter extends AbstractBaseMapPagePresenter<Ajx3Page> {
    public Ajx3PagePresenter(Ajx3Page ajx3Page) {
        super(ajx3Page);
    }

    public void onPageCreated() {
        super.onPageCreated();
        ((Ajx3Page) this.mPage).pageCreated();
    }

    public void onPageCreated(int i, int i2) {
        super.onPageCreated(i, i2);
        ((Ajx3Page) this.mPage).pageCreated(i, i2);
    }

    public void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((Ajx3Page) this.mPage).newIntent(pageBundle);
    }

    public void onResume() {
        super.onResume();
        ((Ajx3Page) this.mPage).resume();
    }

    public void onPause() {
        super.onPause();
        ((Ajx3Page) this.mPage).pause();
    }

    public void onStart() {
        super.onStart();
        ((Ajx3Page) this.mPage).start();
    }

    public void onStop() {
        super.onStop();
        ((Ajx3Page) this.mPage).stop();
    }

    public void onDestroy() {
        super.onDestroy();
        ((Ajx3Page) this.mPage).destroy();
    }

    public ON_BACK_TYPE onBackPressed() {
        if (((Ajx3Page) this.mPage).backPressed()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        ((Ajx3Page) this.mPage).result(i, resultType, pageBundle);
    }

    public void onMapAnimationFinished(aln aln) {
        super.onMapAnimationFinished(aln);
        ((Ajx3Page) this.mPage).mapAnimationFinished(aln);
    }

    public boolean onEngineActionGesture(alg alg) {
        ((Ajx3Page) this.mPage).engineActionGesture(alg);
        return super.onEngineActionGesture(alg);
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        ((Ajx3Page) this.mPage).windowFocusChanged(z);
    }
}
