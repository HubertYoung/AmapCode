package com.autonavi.minimap.ajx3;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.IActvitiyStateListener;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.ajx3.widget.AjxView;

public class AjxPageStateInvoker {
    protected boolean appSwitch;
    protected boolean isFirstResume;
    /* access modifiers changed from: private */
    public AbstractBasePage mAbstractBasePage;
    /* access modifiers changed from: private */
    public boolean mActivityPause;
    private AjxView mAjxView;
    protected boolean mCover;
    private Object mResumeData;

    /* access modifiers changed from: 0000 */
    public void windowFocusChanged(boolean z) {
    }

    public AjxPageStateInvoker(AbstractBasePage abstractBasePage) {
        this.mActivityPause = false;
        this.isFirstResume = true;
        this.mCover = false;
        this.appSwitch = false;
        this.mAbstractBasePage = abstractBasePage;
        AMapPageFramework.setPageStateListener(abstractBasePage, new IPageStateListener() {
            public void onCover() {
                AjxPageStateInvoker.this.mCover = true;
                if (AjxPageStateInvoker.this.mAbstractBasePage instanceof Ajx3Page) {
                    ((Ajx3Page) AjxPageStateInvoker.this.mAbstractBasePage).onPageCover();
                }
            }

            public void onAppear() {
                if (AjxPageStateInvoker.this.mAbstractBasePage instanceof Ajx3Page) {
                    ((Ajx3Page) AjxPageStateInvoker.this.mAbstractBasePage).onPageAppear();
                }
            }
        });
        AMapPageFramework.setActivityStateListener(this.mAbstractBasePage, new IActvitiyStateListener() {
            public void onActivityStart() {
            }

            public void onActivityStop() {
            }

            public void onActivityResume() {
                AjxPageStateInvoker.this.mActivityPause = false;
            }

            public void onActivityPause() {
                AjxPageStateInvoker.this.mActivityPause = true;
            }
        });
    }

    public AjxPageStateInvoker(AbstractBasePage abstractBasePage, AjxView ajxView) {
        this(abstractBasePage);
        this.mAjxView = ajxView;
    }

    public void setAjxView(AjxView ajxView) {
        this.mAjxView = ajxView;
    }

    public void setResumeData(Object obj) {
        this.mResumeData = obj;
    }

    public Object getResumeData() {
        return this.mResumeData;
    }

    public boolean getAppSwitch() {
        return this.appSwitch;
    }

    public void onResume() {
        if (this.isFirstResume) {
            this.isFirstResume = false;
            this.appSwitch = false;
        } else if (this.mCover) {
            this.mCover = false;
            this.appSwitch = false;
        } else {
            this.appSwitch = true;
        }
        if (this.mAjxView != null) {
            this.mAjxView.pageShow(this.appSwitch, this.mResumeData);
            this.mResumeData = null;
        }
    }

    public void resumeActivityState() {
        this.mActivityPause = false;
    }

    public void onStop() {
        boolean z = this.mActivityPause;
        if (this.mAjxView != null) {
            this.mAjxView.pageHide(z);
        }
    }

    public void onDestroy() {
        AMapPageFramework.removePageStateListener(this.mAbstractBasePage);
        AMapPageFramework.removeActivityStateListener(this.mAbstractBasePage);
    }
}
