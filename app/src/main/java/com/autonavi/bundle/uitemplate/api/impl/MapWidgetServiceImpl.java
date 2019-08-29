package com.autonavi.bundle.uitemplate.api.impl;

import android.view.ViewGroup.MarginLayoutParams;
import com.autonavi.bundle.uitemplate.api.IMapWidgetService;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.map.fragmentcontainer.page.IPage;

public class MapWidgetServiceImpl implements IMapWidgetService {
    private float mContainerAlpha = -1.0f;
    private MarginLayoutParams mContainerMarginLayoutParams;
    private bid mCurPage;

    public void unBindMapWidgets() {
    }

    public void onBindPage(Object obj) {
        if (obj == null || !(obj instanceof bid)) {
            this.mCurPage = null;
        } else {
            this.mCurPage = (bid) obj;
        }
    }

    public Object getCurrentPage() {
        return this.mCurPage;
    }

    public void onBindMapWidgets(IWidgetProperty... iWidgetPropertyArr) {
        bez.a("IMapWidgetService", "onBindMapWidgets", new bew[0]);
        Stub.getMapWidgetManager().setHeaderVisibility(isShowPageHead());
        if (!isAjx3DialogPage()) {
            Stub.getMapWidgetManager().setContainerVisible(isShowWidgetsVisible());
        }
        Stub.getMapWidgetManager().setWidget(this.mCurPage, iWidgetPropertyArr);
    }

    private boolean isAjx3DialogPage() {
        if (this.mCurPage == null) {
            return false;
        }
        if (this.mCurPage.getClass().getSimpleName().equals("Ajx3DialogPage") || this.mCurPage.getClass().getSuperclass().getSimpleName().equals("Ajx3DialogPage")) {
            return true;
        }
        return false;
    }

    private int isShowWidgetsVisible() {
        if (this.mCurPage == null || !(this.mCurPage instanceof IPage) || !((IPage) this.mCurPage).isShowMapWidgets()) {
            return 8;
        }
        return 0;
    }

    private int isShowPageHead() {
        if (this.mCurPage == null || !(this.mCurPage instanceof IPage) || !((IPage) this.mCurPage).isShowPageHeader()) {
            return 8;
        }
        return 0;
    }

    public void saveContainerConfig() {
        this.mContainerMarginLayoutParams = Stub.getMapWidgetManager().getContainerMargin();
        this.mContainerAlpha = Stub.getMapWidgetManager().getContainerAlpha();
    }

    public void restoreContainerConfig() {
        if (this.mContainerMarginLayoutParams != null) {
            Stub.getMapWidgetManager().setContainerMargin(this.mContainerMarginLayoutParams.leftMargin, this.mContainerMarginLayoutParams.topMargin, this.mContainerMarginLayoutParams.rightMargin, this.mContainerMarginLayoutParams.bottomMargin);
        }
        if (this.mContainerAlpha >= 0.0f && this.mContainerAlpha <= 1.0f) {
            Stub.getMapWidgetManager().setContainerAlpha(this.mContainerAlpha);
        }
    }

    public void releaseContainerConfig() {
        this.mContainerMarginLayoutParams = null;
        this.mContainerAlpha = -1.0f;
    }

    public void onPageStart() {
        Stub.getMapWidgetManager().onPageStart(this.mCurPage);
    }

    public void onPageCreated() {
        Stub.getMapWidgetManager().onPageCreated(this.mCurPage);
    }

    public void onPageResume() {
        Stub.getMapWidgetManager().onPageResume(this.mCurPage);
    }

    public void onPagePause() {
        Stub.getMapWidgetManager().onPagePause(this.mCurPage);
    }

    public void onForeground() {
        Stub.getMapWidgetManager().onForeground();
    }

    public void onBackground() {
        Stub.getMapWidgetManager().onBackground();
    }

    public void onPageStop() {
        Stub.getMapWidgetManager().onPageStop(this.mCurPage);
    }

    public void onPageDestroy() {
        Stub.getMapWidgetManager().onPageDestroy(this.mCurPage);
    }
}
