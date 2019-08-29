package com.autonavi.bundle.uitemplate.mapwidget.widget.homecorp;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

public class HomeCorpMapWidget extends AbstractMapWidget<HomeCorpWidgetPresenter> {
    private cyl mFrequentLocationView;

    public HomeCorpMapWidget(Context context) {
        super(context);
    }

    public void onInit(Context context) {
        if (getPresenter() != null) {
            ((HomeCorpWidgetPresenter) getPresenter()).addListenerType(-1);
        }
    }

    public View createContentView(Context context) {
        if (this.mContentView != null) {
            return this.mContentView;
        }
        if (this.mFrequentLocationView == null) {
            this.mFrequentLocationView = new cyl(AMapPageUtil.getPageContext());
        }
        this.mContentView = this.mFrequentLocationView.a;
        return this.mContentView;
    }

    public cyl getFrequentLocationView() {
        return this.mFrequentLocationView;
    }
}
