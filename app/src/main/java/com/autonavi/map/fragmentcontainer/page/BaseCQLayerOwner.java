package com.autonavi.map.fragmentcontainer.page;

import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

public class BaseCQLayerOwner {
    public static final int DISMISS_TYPE_NORMAL = 0;
    public static final int DISMISS_TYPE_ON_AJX_BACK = 2;
    public static final int DISMISS_TYPE_ON_BACKPRESS = 1;
    WeakReference<ViewGroup> mContainer;
    WeakReference<View> mMapBottomView;
    WeakReference<View> mMapMiddleView;
    WeakReference<View> mMapTopView;
    MapBasePage mPage;

    public enum MiddleViewSlideType {
        None,
        Alpha,
        Alpha_Press,
        Dismiss
    }

    public boolean disableCollapseWhenBack() {
        return false;
    }

    public boolean enableResetBG() {
        return true;
    }

    public bra getSlidePanelManager() {
        return null;
    }

    public boolean isCancelHandleSuspendSliding() {
        return false;
    }

    public boolean isFullScreen() {
        return false;
    }

    public void onAjxBack() {
    }

    public void onSetFullScreen(boolean z) {
    }

    public void onSlideEnd(boolean z) {
    }

    public void onSliding() {
    }

    public BaseCQLayerOwner(MapBasePage mapBasePage) {
        this.mPage = mapBasePage;
    }

    public void onInitView() {
        this.mMapTopView = new WeakReference<>(this.mPage.getTopMapInteractiveView());
        this.mMapMiddleView = new WeakReference<>(this.mPage.getMapSuspendBtnView2());
        this.mMapBottomView = new WeakReference<>(this.mPage.getBottomMapInteractiveView());
        this.mContainer = new WeakReference<>(this.mPage.getMapInteractiveView());
    }

    public MapBasePage getPage() {
        return this.mPage;
    }

    public ViewGroup getCQContainer() {
        if (this.mContainer != null) {
            return (ViewGroup) this.mContainer.get();
        }
        return null;
    }

    public View getMapTopView() {
        if (this.mMapTopView != null) {
            return (View) this.mMapTopView.get();
        }
        return null;
    }

    public View getMapMiddleView() {
        if (this.mMapMiddleView != null) {
            return (View) this.mMapMiddleView.get();
        }
        return null;
    }

    public View getMapBottomView() {
        if (this.mMapBottomView != null) {
            return (View) this.mMapBottomView.get();
        }
        return null;
    }

    public void onShowCQLayer() {
        if (getMapBottomView() != null) {
            getMapBottomView().setVisibility(4);
        }
    }

    public void onDismissCQLayer(int i) {
        if (!isFullScreen() && getMapBottomView() != null) {
            getMapBottomView().setVisibility(0);
        }
        if (this.mPage != null) {
            this.mPage.hideSyncPopupWindow();
            if (this.mPage.getPoiDetailDelegate() != null) {
                this.mPage.getPoiDetailDelegate().resetTokenPage();
            }
        }
    }

    public MiddleViewSlideType getMiddleViewSlideType() {
        return MiddleViewSlideType.Dismiss;
    }
}
