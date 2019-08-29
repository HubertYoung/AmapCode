package com.amap.bundle.drive.common.basepage;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.WindowManager;
import com.amap.bundle.drive.ajx.module.ModuleCommonBusiness;
import com.amap.bundle.utils.device.DisplayType;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.util.AjxLogUtil;

public abstract class AjxRouteCarNaviBasePage extends AjxRouteNaviBasePage {
    protected boolean mHasLaunchedNewPage = false;
    protected boolean mIsExit = false;
    protected int mLastOrientation = 0;
    public boolean mNeedBackprev;
    public boolean mWillEnterNaviEndPage = false;

    /* renamed from: com.amap.bundle.drive.common.basepage.AjxRouteCarNaviBasePage$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[DisplayType.values().length];

        static {
            try {
                a[DisplayType.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
    }

    public void pageCreated() {
        super.pageCreated();
        requestScreenOn(true);
    }

    public void resume() {
        super.resume();
    }

    public void pause() {
        super.pause();
    }

    public void stop() {
        super.stop();
    }

    public void destroy() {
        if (this.mAjxView != null) {
            this.mAjxView.orientationChange(0);
        }
        super.destroy();
    }

    public void loadNodeFragmentBundle(PageBundle pageBundle) {
        if (pageBundle.containsKey("need_backprev")) {
            this.mNeedBackprev = pageBundle.getBoolean("need_backprev", false);
        }
    }

    /* access modifiers changed from: protected */
    public boolean willEnterNaviEndPage() {
        return this.mWillEnterNaviEndPage;
    }

    public void configurationChanged(Configuration configuration) {
        if (this.mLastOrientation != configuration.orientation) {
            rotationChange();
            this.mLastOrientation = configuration.orientation;
        }
    }

    private void rotationChange() {
        Activity topActivity = AMapAppGlobal.getTopActivity();
        if (topActivity != null) {
            WindowManager windowManager = topActivity.getWindowManager();
            if (windowManager != null) {
                int i = 0;
                switch (windowManager.getDefaultDisplay().getRotation()) {
                    case 1:
                        i = 90;
                        break;
                    case 2:
                        i = 180;
                        break;
                    case 3:
                        i = -90;
                        break;
                }
                AjxLogUtil.recordLogToTagFile("NaviMonitorAJX", "AjxRouteCarNaviBasePage orientationchange ret=".concat(String.valueOf(i)));
                this.mAjxView.orientationChange(i);
            }
        }
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.mModuleVUI = (ModuleVUI) this.mAjxView.getJsModule(ModuleVUI.MODULE_NAME);
        this.mModuleCommonBusiness = (ModuleCommonBusiness) this.mAjxView.getJsModule(ModuleCommonBusiness.MODULE_NAME);
    }

    public void onPageCover() {
        anf.a(0, -1);
        super.onPageCover();
    }

    public void onPageAppear() {
        anf.a(2, 0);
        super.onPageAppear();
    }

    public void handleStartNewPage() {
        this.mHasLaunchedNewPage = true;
    }

    public void handleExit() {
        this.mIsExit = true;
    }

    /* access modifiers changed from: protected */
    public void requestScreenOrientation() {
        if (AnonymousClass1.a[agp.a(getContext()).ordinal()] != 1) {
            requestScreenOrientation(2);
        } else {
            requestScreenOrientation(2);
        }
    }
}
